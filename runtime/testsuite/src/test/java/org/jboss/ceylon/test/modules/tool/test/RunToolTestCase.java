package org.jboss.ceylon.test.modules.tool.test;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.ceylon.common.tool.AbstractToolTest;
import org.eclipse.ceylon.common.tool.OptionArgumentException;
import org.eclipse.ceylon.common.tool.ToolLoader;
import org.eclipse.ceylon.common.tool.ToolModel;
import org.eclipse.ceylon.common.tools.CeylonToolLoader;
import org.junit.Assert;

import org.junit.Test;

import ceylon.modules.bootstrap.CeylonRunTool;

public class RunToolTestCase extends AbstractToolTest {

    protected final static String OUT_EXPECTED_DEFAULT = "Hello, world";
    protected final static String OUT_EXPECTED_WITH_ARG = "Hello, Tako";
    
    protected final static String dir = "testsuite/src";
    protected final static String destDirGeneral = "build/test-cars";
    protected final static String cacheDirGeneral = "build/test-cache";
    protected final String destDir;
    protected final String cacheDir;
    
    protected final ToolLoader pluginLoader = new CeylonToolLoader(null);
    
    public RunToolTestCase() {
        Package pakage = getClass().getPackage();
        String moduleName = pakage.getName();
        int lastDot = moduleName.lastIndexOf('.');
        if(lastDot == -1){
            destDir = destDirGeneral + File.separator + moduleName;
        } else {
            destDir = destDirGeneral + File.separator + moduleName.substring(lastDot+1);
        }
        if(lastDot == -1){
            cacheDir = cacheDirGeneral + File.separator + moduleName;
        } else {
            cacheDir = cacheDirGeneral + File.separator + moduleName.substring(lastDot+1);
        }
        new File(cacheDir).mkdirs();
    }
    
    private List<String> options(String... strings){
        List<String> ret = new ArrayList<String>(strings.length+2);
        ret.add("--sysrep");
        ret.add("../dist/dist/repo");
        ret.add("--cacherep");
        ret.add(cacheDir);
        ret.add("--rep");
        ret.add("testsuite/src/test/resources/repo");
        for(String s : strings)
            ret.add(s);
        return ret;
    }
    
    @Test
    public void testNoArgs() throws Exception {
        ToolModel<CeylonRunTool> model = pluginLoader.loadToolModel("run");
        Assert.assertNotNull(model);
        try {
            CeylonRunTool tool = pluginFactory.bindArguments(model, getMainTool(), Collections.<String>emptyList());
            Assert.fail();
        } catch (OptionArgumentException e) {
            // asserting this is thrown
        }
    }
    
    @Test
    public void testModuleVersion() throws Exception {
        ToolModel<CeylonRunTool> model = pluginLoader.loadToolModel("run");
        Assert.assertNotNull(model);
        CeylonRunTool tool = pluginFactory.bindArguments(model, getMainTool(), options("hello/1.0.0"));
        assertOutput(tool, OUT_EXPECTED_DEFAULT);
    }
    
    @Test
    public void testModuleVersionArgs() throws Exception {
        ToolModel<CeylonRunTool> model = pluginLoader.loadToolModel("run");
        Assert.assertNotNull(model);
        CeylonRunTool tool = pluginFactory.bindArguments(model, getMainTool(), options("hello/1.0.0", "Tako"));
        assertOutput(tool, OUT_EXPECTED_WITH_ARG);
    }
    
    @Test
    public void testModuleNoVersion() throws Exception {
        ToolModel<CeylonRunTool> model = pluginLoader.loadToolModel("run");
        Assert.assertNotNull(model);
        CeylonRunTool tool = pluginFactory.bindArguments(model, getMainTool(), options("hello"));
        assertOutput(tool, OUT_EXPECTED_DEFAULT);
    }
    
    @Test
    public void testModuleNoVersionArgs() throws Exception {
        ToolModel<CeylonRunTool> model = pluginLoader.loadToolModel("run");
        Assert.assertNotNull(model);
        CeylonRunTool tool = pluginFactory.bindArguments(model, getMainTool(), options("hello", "Tako"));
        assertOutput(tool, OUT_EXPECTED_WITH_ARG);
    }
    
    @Test
    public void testModuleVersionFunction() throws Exception {
        ToolModel<CeylonRunTool> model = pluginLoader.loadToolModel("run");
        Assert.assertNotNull(model);
        CeylonRunTool tool = pluginFactory.bindArguments(model, getMainTool(), options("--run=hello.hello", "hello/1.0.0"));
        assertOutput(tool, OUT_EXPECTED_DEFAULT);
    }
    
    @Test
    public void testDefault() throws Exception {
        ToolModel<CeylonRunTool> model = pluginLoader.loadToolModel("run");
        Assert.assertNotNull(model);
        CeylonRunTool tool = pluginFactory.bindArguments(model, getMainTool(), options("default"));
        assertOutput(tool, OUT_EXPECTED_DEFAULT);
    }
    
    @Test
    public void testDefaultArg() throws Exception {
        ToolModel<CeylonRunTool> model = pluginLoader.loadToolModel("run");
        Assert.assertNotNull(model);
        CeylonRunTool tool = pluginFactory.bindArguments(model, getMainTool(), options("default", "Tako"));
        assertOutput(tool, OUT_EXPECTED_WITH_ARG);
    }
    
    @Test
    public void testDefaultFunction() throws Exception {
        ToolModel<CeylonRunTool> model = pluginLoader.loadToolModel("run");
        Assert.assertNotNull(model);
        CeylonRunTool tool = pluginFactory.bindArguments(model, getMainTool(), options("--run=hello", "default"));
        assertOutput(tool, OUT_EXPECTED_DEFAULT);
    }
    
    @Test
    public void testQuotedModuleName() throws Exception {
        ToolModel<CeylonRunTool> model = pluginLoader.loadToolModel("run");
        Assert.assertNotNull(model);
        CeylonRunTool tool = pluginFactory.bindArguments(model, getMainTool(), options("foo.long.module/1.0.0"));
        assertOutput(tool, OUT_EXPECTED_DEFAULT);
    }

    @Test
    public void testQuotedModuleNameKeywordFunction() throws Exception {
        ToolModel<CeylonRunTool> model = pluginLoader.loadToolModel("run");
        Assert.assertNotNull(model);
        CeylonRunTool tool = pluginFactory.bindArguments(model, getMainTool(), options("--run", "foo.long.module::do", "foo.long.module/1.0.0"));
        assertOutput(tool, OUT_EXPECTED_DEFAULT);
    }

    @Test
    public void testQuotedModuleNameNoVersion() throws Exception {
        ToolModel<CeylonRunTool> model = pluginLoader.loadToolModel("run");
        Assert.assertNotNull(model);
        CeylonRunTool tool = pluginFactory.bindArguments(model, getMainTool(), options("foo.long.module"));
        assertOutput(tool, OUT_EXPECTED_DEFAULT);
    }

    @Test
    public void testQuotedModuleNameVersion() throws Exception {
        ToolModel<CeylonRunTool> model = pluginLoader.loadToolModel("run");
        Assert.assertNotNull(model);
        CeylonRunTool tool = pluginFactory.bindArguments(model, getMainTool(), options("--run=foo.long.module::run", "foo.long.module/1.0.0"));
        assertOutput(tool, OUT_EXPECTED_DEFAULT);
    }

    @Test
    public void testCompileForce() throws Exception {
        ToolModel<CeylonRunTool> model = pluginLoader.loadToolModel("run");
        Assert.assertNotNull(model);
        CeylonRunTool tool = pluginFactory.bindArguments(model, getMainTool(), 
                options("--offline",
                        "--rep", "build/test/modules",
                        "--compile=force",
                        "--compiler-arguments=--sysrep",
                        "--compiler-arguments=../dist/dist/repo",
                        "--compiler-arguments=--offline",
                        "--compiler-arguments=--source",
                        "--compiler-arguments=testsuite/src/test/resources/source",
                        "--compiler-arguments=--out",
                        "--compiler-arguments=build/test/modules",
                        "bug7035/1"));
        tool.run();

        // run it again, should force
        tool = pluginFactory.bindArguments(model, getMainTool(), 
                options("--offline",
                        "--rep", "build/test/modules",
                        "--compile=force",
                        "--compiler-arguments=--sysrep",
                        "--compiler-arguments=../dist/dist/repo",
                        "--compiler-arguments=--offline",
                        "--compiler-arguments=--source",
                        "--compiler-arguments=testsuite/src/test/resources/source",
                        "--compiler-arguments=--out",
                        "--compiler-arguments=build/test/modules",
                        "bug7035/1"));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);
        tool.setOut(out);
        tool.run();
        assertTrue(baos.size() > 0);
        assertTrue(baos.toString().contains("Source found for module bug7035, compiling..."));
    }

    private void assertOutput(CeylonRunTool tool, String txt) throws IOException {
        PrintStream oldout = System.out;
        PrintStream out = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            out = new PrintStream(baos);
            System.setOut(out);
            tool.run();
            String output = baos.toString();
            Assert.assertTrue(output.contains(txt));
        } finally {
            System.setOut(oldout);
            if (out != null) {
                out.close();
            }
        }
    }
}
