

package org.eclipse.ceylon.cmr.impl;

import static org.eclipse.ceylon.cmr.api.ArtifactContext.getSuffixFromFilename;
import static org.eclipse.ceylon.cmr.resolver.javascript.JavaScriptResolver.readNpmDescriptor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import org.eclipse.ceylon.cmr.api.ArtifactContext;
import org.eclipse.ceylon.cmr.spi.ContentHandle;
import org.eclipse.ceylon.cmr.spi.ContentOptions;
import org.eclipse.ceylon.cmr.spi.Node;
import org.eclipse.ceylon.cmr.spi.OpenNode;
import org.eclipse.ceylon.common.Constants;
import org.eclipse.ceylon.common.log.Logger;
import org.eclipse.ceylon.model.cmr.RepositoryException;

/**
 * NPM content store.
 *
 * @author Tako Schotanus (tako@ceylon-lang.org)
 */
public class NpmContentStore extends AbstractContentStore {
    
    private final File out;
    private final FileContentStore[] stores;
    private final FileContentStore outstore;
    private String npmCommand;
    private String path;
    
    public NpmContentStore(File[] roots, File out, Logger log, boolean offline) {
        super(log, offline, -1);
        assert(roots.length > 0);
        this.stores = new FileContentStore[roots.length];
        int i = 0;
        for (File root : roots) {
            stores[i++] = new FileContentStore(root);
        }
        this.out = out;
        if (out != null) {
            outstore = new FileContentStore(out);
        } else {
            outstore = null;
        }
    }

    public Iterable<File> getBaseDirectories() {
        ArrayList<File> baseDirectories = new ArrayList<>(stores.length);
        for (FileContentStore store : stores) {
            for (File baseDir : store.getBaseDirectories()) {
                baseDirectories.add(baseDir);
            }
        }
        baseDirectories.add(out);
        return baseDirectories;
    }
    
    public OpenNode createRoot() {
        return new RootNode(this, this);
    }

    public OpenNode find(Node parent, String child) {
        DefaultNode node = null;
        if (!hasContent(child) //TODO: this test looks like rubbish to me!
                || parent.getLabel().startsWith("@")
                || parent instanceof RootNode) { //RootNode has an empty label
            node = new DefaultNode(child);
            node.setContentMarker();
            return node;
        } else {
            if (getSuffixFromFilename(child)
                    .equals(ArtifactContext.JS)) {
                String artifactName = getTrueArtifactName(parent);
                if (artifactName != null) {
                    child = artifactName;
                }
            }
            for (FileContentStore store : stores) {
                OpenNode result = store.find(parent, child);
                if (result != null) {
                    return result;
                }
            }
            installNpmModule(parent);
            return outstore.find(parent, child);
        }
    }

    public ContentHandle peekContent(Node node) {
        for (FileContentStore store : stores) {
            ContentHandle result = store.peekContent(node);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private String getTrueArtifactName(Node parent) {
        final Node node;
        try {
            node = parent.getChild("package.json");
        } catch (NullPointerException ex) {
            return null;
        }
        try {
            File json = node.getContent(File.class);
            if (json.exists() && json.isFile() && json.canRead()) {
                //Parse json, get "main", that's the file we need
                Map<String,Object> descriptor = readNpmDescriptor(json);
                Object main = descriptor.get("main");
                if (main == null) {
                    return "index.js";
                } else if (main instanceof String) {
                    String string = (String) main; 
                    if (string.endsWith(".js")) {
                        return string;
                    } else {
                        //TODO: this is rubbish, but I don't understand    
                        //      what the rules really are
                        if (string.equals("lib") || string.endsWith("/lib")) {
                            return string + "/index.js";
                        }
                        else {
                            return string + ".js";
                        }
                    }
                } else {
                    throw new RepositoryException("unexpected value for 'main' in npm descriptor: " + json);
                }
            } else {
                throw new RepositoryException("npm descriptor not found: " + json);
            }
        } catch (IOException ex) {
            throw new RepositoryException("error reading npm descriptor: " + out + "/package.json", ex);
        }
    }
    
    public ContentHandle getContent(Node node) throws IOException {
        ContentHandle result = peekContent(node);
        if (result != null) {
            return result;
        }
        
        if (outstore != null) {
            installNpmModule(node);
            return outstore.getContent(node);
        } else {
            // Until now we used peekContent() which doesn't throw
            // so we re-try with any store to get the error we want
            return stores[0].getContent(node);
        }
    }

    public void installNpmModule(Node node) {
        try {
            if (!out.exists()) {
                out.mkdirs();
            }
            ArtifactContext ac = ArtifactContext.fromNode(node);
            if (ac != null) {
                String name = ac.getName();
                if (name.contains(":")) {
                    name = "@" + name.replace(':', '/');
                }
                String version = ac.getVersion();
                String module = version.isEmpty() ? name : name + "@" + version;
                if (log != null) {
                    log.debug("installing npm module " + module + " in " + out);
                }
                String npmCmd = npmCommand != null ? npmCommand : 
                    System.getProperty(Constants.PROP_CEYLON_EXTCMD_NPM, "npm");
                ProcessBuilder pb = new ProcessBuilder()
                        .command(npmCmd, "install", "--silent", "--no-bin-links", module)
                        .directory(out.getParentFile())
                        .inheritIO();
                Map<String, String> env = pb.environment();
                String pathVariableName = "PATH";
                for (String key : env.keySet()) {
                    if (key.equalsIgnoreCase("path")) {
                        pathVariableName = key;
                        break;
                    }
                }
                String pathForRunningNpm = path != null ? path : 
                    System.getProperty(Constants.PROP_CEYLON_EXTCMD_PATH, System.getenv("PATH"));
                env.put(pathVariableName, pathForRunningNpm);
                
                Process p = pb.start();
                p.waitFor();
                if (p.exitValue() != 0) {
                    throw new RepositoryException("npm installer for '" + name + "' failed with exit code: " + p.exitValue());
                }
            }
        } catch (InterruptedException | IOException ex) {
            throw new RepositoryException("error running npm installer (make sure 'npm' is installed and available in your PATH)", ex);
        }
    }

    public ContentHandle putContent(Node node, InputStream stream, ContentOptions options) throws IOException {
        if (outstore != null) {
            return outstore.putContent(node, stream, options);
        } else {
            return null;  // no output specified
        }
    }

    public OpenNode create(Node parent, String child) {
        if (outstore != null) {
            return outstore.create(parent, child);
        } else {
            return null;  // no output specified
        }
    }

    public Iterable<? extends OpenNode> find(Node parent) {
        return Collections.emptyList(); // cannot find all children
    }
    
    public String getDisplayString() {
        String name = "npm";
        if (offline) {
            name += " (offline)";
        }
        return name;
    }

    @Override
    public boolean isHerd() {
        return false;
    }

    @Override
    public boolean canHandleFolders() {
        return false;
    }

    public void setNpmCommand(String npmCommand) {
        this.npmCommand = npmCommand;
    }

    public void setPathForRunningNpm(String path) {
        this.path = path;
    }
}
