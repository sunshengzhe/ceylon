/*
 * Copyright (c) 2004, 2009, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.redhat.ceylon.langtools.tools.apt.mirror.type;


import java.util.Collection;

import com.redhat.ceylon.langtools.mirror.declaration.*;
import com.redhat.ceylon.langtools.mirror.type.*;
import com.redhat.ceylon.langtools.mirror.util.TypeVisitor;
import com.redhat.ceylon.langtools.tools.apt.mirror.AptEnv;
import com.redhat.ceylon.langtools.tools.javac.code.Type;
import com.redhat.ceylon.langtools.tools.javac.code.Symbol.TypeSymbol;

import java.util.ArrayList;


/**
 * Implementation of TypeVariable
 */
@SuppressWarnings("deprecation")
public class TypeVariableImpl extends TypeMirrorImpl implements TypeVariable {

    protected Type.TypeVar type;


    TypeVariableImpl(AptEnv env, Type.TypeVar type) {
        super(env, type);
        this.type = type;
    }


    /**
     * Returns the simple name of this type variable.  Bounds are
     * not included.
     */
    public String toString() {
        return type.tsym.name.toString();
    }

    /**
     * {@inheritDoc}
     */
    public TypeParameterDeclaration getDeclaration() {
        TypeSymbol sym = type.tsym;
        return env.declMaker.getTypeParameterDeclaration(sym);
    }

    /**
     * {@inheritDoc}
     */
    public void accept(TypeVisitor v) {
        v.visitTypeVariable(this);
    }
}
