/*
 * Copyright (c) 2004, 2005, Oracle and/or its affiliates. All rights reserved.
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

package com.redhat.ceylon.langtools.tools.apt.mirror.declaration;


import java.util.Collection;

import com.redhat.ceylon.langtools.mirror.declaration.*;
import com.redhat.ceylon.langtools.mirror.type.ReferenceType;
import com.redhat.ceylon.langtools.mirror.util.DeclarationVisitor;
import com.redhat.ceylon.langtools.tools.apt.mirror.AptEnv;
import com.redhat.ceylon.langtools.tools.javac.code.*;
import com.redhat.ceylon.langtools.tools.javac.code.Symbol.*;

import java.util.ArrayList;


/**
 * Implementation of TypeParameterDeclaration
 */
@SuppressWarnings("deprecation")
public class TypeParameterDeclarationImpl extends DeclarationImpl
                                          implements TypeParameterDeclaration
{
    protected TypeSymbol sym;


    TypeParameterDeclarationImpl(AptEnv env, TypeSymbol sym) {
        super(env, sym);
        this.sym = sym;
    }


    /**
     * Returns the type parameter's name along with any "extends" clause.
     * Class names are qualified.  No implicit "extends Object" is added.
     */
    public String toString() {
        return toString(env, (Type.TypeVar) sym.type);
    }

    /**
     * {@inheritDoc}
     */
    public Collection<ReferenceType> getBounds() {
        ArrayList<ReferenceType> res = new ArrayList<ReferenceType>();
        for (Type t : env.jctypes.getBounds((Type.TypeVar) sym.type)) {
            res.add((ReferenceType) env.typeMaker.getType(t));
        }
        return res;
    }

    /**
     * {@inheritDoc}
     */
    public Declaration getOwner() {
        Symbol owner = sym.owner;
        return ((owner.kind & Kinds.TYP) != 0)
               ? env.declMaker.getTypeDeclaration((ClassSymbol) owner)
               : env.declMaker.getExecutableDeclaration((MethodSymbol) owner);
    }



    /**
     * {@inheritDoc}
     */
    public void accept(DeclarationVisitor v) {
        v.visitTypeParameterDeclaration(this);
    }


    /**
     * Returns the type parameter's name along with any "extends" clause.
     * See {@link #toString()} for details.
     */
    static String toString(AptEnv env, Type.TypeVar tv) {
        StringBuilder s = new StringBuilder();
        s.append(tv);
        boolean first = true;
        for (Type bound : getExtendsBounds(env, tv)) {
            s.append(first ? " extends " : " & ");
            s.append(env.typeMaker.typeToString(bound));
            first = false;
        }
        return s.toString();
    }

    /**
     * Returns the bounds of a type variable, eliding java.lang.Object
     * if it appears alone.
     */
    private static Iterable<Type> getExtendsBounds(AptEnv env,
                                                   Type.TypeVar tv) {
        return (tv.getUpperBound().tsym == env.symtab.objectType.tsym)
               ? com.redhat.ceylon.langtools.tools.javac.util.List.<Type>nil()
               : env.jctypes.getBounds(tv);
    }
}
