/********************************************************************************
 * Copyright (c) 2011-2017 Red Hat Inc. and/or its affiliates and others
 *
 * This program and the accompanying materials are made available under the 
 * terms of the Apache License, Version 2.0 which is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * SPDX-License-Identifier: Apache-2.0 
 ********************************************************************************/
package org.eclipse.ceylon.model.typechecker.model;

import java.util.List;

public class InterfaceAlias extends Interface {
    
    @Override
    public boolean isAlias() {
        return true;
    }
    
    @Override
    public boolean isEmptyType() {
        Type et = getExtendedType();
        if (et!=null) {
            Type.checkDepth();
            Type.incDepth();
            try {
                return et.getDeclaration().isEmptyType();
            }
            finally {
                Type.decDepth();
            }
        }
        else {
            return false;
        }
    }
    
    @Override
    public boolean isTupleType() {
        Type et = getExtendedType();
        if (et!=null) {
            Type.checkDepth();
            Type.incDepth();
            try {
                return et.getDeclaration().isTupleType();
            }
            finally {
                Type.decDepth();
            }
        }
        else {
            return false;
        }
    }
    
    @Override
    public boolean isSequentialType() {
        Type et = getExtendedType();
        if (et!=null) {
            Type.checkDepth();
            Type.incDepth();
            try {
                return et.getDeclaration().isSequentialType();
            }
            finally {
                Type.decDepth();
            }
        }
        else {
            return false;
        }
    }
    
    @Override
    public boolean isSequenceType() {
        Type et = getExtendedType();
        if (et!=null) {
            Type.checkDepth();
            Type.incDepth();
            try {
                return et.getDeclaration().isSequenceType();
            }
            finally {
                Type.decDepth();
            }
        }
        else {
            return false;
        }
    }
    
    @Override
    void collectSupertypeDeclarations(
            List<TypeDeclaration> results) {
        Type et = getExtendedType();
        if (et!=null) { 
            et.getDeclaration()
                .collectSupertypeDeclarations(results);
        }
    }
        
    @Override
    public boolean inherits(TypeDeclaration dec) {
        Type et = getExtendedType();
        if (et!=null) {
            Type.checkDepth();
            Type.incDepth();
            try {
                return et.getDeclaration().inherits(dec);
            }
            finally {
                Type.decDepth();
            }
        }
        return false;
    }
    
}
