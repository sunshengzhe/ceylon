/********************************************************************************
 * Copyright (c) 2011-2017 Red Hat Inc. and/or its affiliates and others
 *
 * This program and the accompanying materials are made available under the 
 * terms of the Apache License, Version 2.0 which is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * SPDX-License-Identifier: Apache-2.0 
 ********************************************************************************/
package org.eclipse.ceylon.cmr.api;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * <p>Represents the overriding of an artifact. Supports:</p>
 * <ul>
 * <li>Addition and removal of dependencies</li>
 * <li>sharing and unsharing of dependencies</li>
 * <li>making dependencies optional or non-optional</li>
 * <li>Replacing the module entirely</li>
 * </ul>
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class ArtifactOverrides {
    private static final Logger log = Logger.getLogger(ArtifactOverrides.class.getName());

    private ArtifactContext owner;
    private Set<DependencyOverride> add = new HashSet<>();
    private Set<DependencyOverride> remove = new HashSet<>();
    private Map<String, Boolean> share = new HashMap<>();
    private Map<String, Boolean> optional = new HashMap<>();
    private String version;
    private String classifier;
    private DependencyOverride replace;
    private String filter;

    public ArtifactOverrides(ArtifactContext owner) {
        this.owner = owner;
    }

    public void addShareOverride(ArtifactContext context, boolean share){
        this.share.put(context.getName(), share);
    }

    public void addOptionalOverride(ArtifactContext context, boolean optional){
        this.optional.put(context.getName(), optional);
    }
    
    public boolean isShareOverridden(ArtifactContext context){
        return share.containsKey(context.getName());
    }
    
    public boolean isShared(ArtifactContext context){
        Boolean ret = share.get(context.getName());
        return ret != null && ret.booleanValue();
    }

    public boolean isOptionalOverridden(ArtifactContext context){
        return optional.containsKey(context.getName());
    }

    public boolean isOptional(ArtifactContext context){
        Boolean ret = optional.get(context.getName());
        return ret != null && ret.booleanValue();
    }
    
    public boolean hasVersion(){
        return version != null;
    }
    
    public String getVersion(){
        return version;
    }
    
    public void setVersion(String version){
        this.version = version;
    }
    
    public boolean hasClassifier(){
        return classifier != null;
    }
    
    public String getClassifier(){
        return classifier;
    }
    
    public void setClassifier(String classifier) {
        this.classifier = classifier;
    }

    public void addOverride(DependencyOverride override) {
        switch (override.getType()) {
            case ADD:
                add.add(override);
                break;
            case REMOVE:
                remove.add(override);
                break;
            case REPLACE:
                if (replace != null) {
                    log.warning(String.format("Replace for %s is already defined: %s, current: %s", owner, replace.getArtifactContext(), override.getArtifactContext()));
                }
                replace = override;
                break;
        }
    }

    public ArtifactContext getOwner() {
        return owner;
    }

    public Set<DependencyOverride> getAdd() {
        return add;
    }

    public boolean isRemoved(ArtifactContext mc) {
        for (DependencyOverride override : remove) {
            // match with optional version
            if (override.matches(mc)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAddedOrUpdated(ArtifactContext mc) {
        for (DependencyOverride override : add) {
            // match just the name, so we can update with another version
            if (override.matchesName(mc)) {
                return true;
            }
        }
        return false;
    }

    public DependencyOverride getReplace() {
        return replace;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

}
