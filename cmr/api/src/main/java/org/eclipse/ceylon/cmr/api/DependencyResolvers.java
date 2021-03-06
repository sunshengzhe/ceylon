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

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.ceylon.cmr.spi.Node;
import org.eclipse.ceylon.model.cmr.ArtifactResult;

/**
 * Plugable dependencies utils mechanism.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class DependencyResolvers {
    private final List<DependencyResolver> resolvers = new CopyOnWriteArrayList<>();

    public void addResolver(DependencyResolver resolver) {
        addResolver(resolver, resolvers.size());
    }

    public void addResolver(DependencyResolver resolver, int index) {
        resolvers.add(index, resolver);
    }

    public void removeResolver(DependencyResolver resolver) {
        resolvers.remove(resolver);
    }

    public ModuleInfo resolve(ArtifactResult result, Overrides overrides) {
        for (DependencyResolver dr : resolvers) {
            try {
                ModuleInfo info = dr.resolve(result, overrides);
                if (info != null) {
                    return info;
                }
            } catch (Exception ex) {}
        }
        return null;
    }

    public Node descriptor(Node artifact) {
        for (DependencyResolver dr : resolvers) {
            try {
                Node descriptor = dr.descriptor(artifact);
                if (descriptor != null)
                    return descriptor;
            } catch (Exception ex) {}
        }
        return null;
    }

    @Override
    public String toString() {
        return resolvers.toString();
    }
}

