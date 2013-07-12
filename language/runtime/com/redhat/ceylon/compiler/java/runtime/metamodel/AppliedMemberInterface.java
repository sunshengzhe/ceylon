package com.redhat.ceylon.compiler.java.runtime.metamodel;

import ceylon.language.metamodel.Interface;
import ceylon.language.metamodel.InterfaceType$impl;
import ceylon.language.metamodel.Member$impl;
import ceylon.language.metamodel.MemberInterface$impl;
import ceylon.language.metamodel.declaration.InterfaceDeclaration;

import com.redhat.ceylon.compiler.java.metadata.Ignore;
import com.redhat.ceylon.compiler.java.metadata.TypeInfo;
import com.redhat.ceylon.compiler.java.runtime.model.TypeDescriptor;
import com.redhat.ceylon.compiler.typechecker.model.ProducedType;

public class AppliedMemberInterface<Container, Type> 
    extends AppliedClassOrInterfaceType<Type>
    implements ceylon.language.metamodel.MemberInterface<Container, Type> {

    @Ignore
    private TypeDescriptor $reifiedContainer;

    AppliedMemberInterface(@Ignore TypeDescriptor $reifiedContainer,
                       @Ignore TypeDescriptor $reifiedType,
                       ProducedType producedType) {
        super($reifiedType, producedType);
        this.$reifiedContainer = $reifiedContainer;
    }

    @Override
    @Ignore
    public InterfaceType$impl<Type> $ceylon$language$metamodel$InterfaceType$impl() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Ignore
    public Member$impl<Container, Interface<? extends Type>> $ceylon$language$metamodel$Member$impl() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Ignore
    public MemberInterface$impl<Container, Type> $ceylon$language$metamodel$MemberInterface$impl() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Ignore
    public Interface<? extends Type> $call() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public Interface<? extends Type> $call(Object arg0) {
        return new AppliedInterfaceType(null, super.producedType);
    }

    @Override
    @Ignore
    public Interface<? extends Type> $call(Object arg0, Object arg1) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public Interface<? extends Type> $call(Object arg0, Object arg1, Object arg2) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public Interface<? extends Type> $call(Object... args) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public short $getVariadicParameterIndex() {
        return -1;
    }

    @Override
    @TypeInfo("ceylon.language.metamodel.declaration::InterfaceDeclaration")
    public InterfaceDeclaration getDeclaration() {
        return (InterfaceDeclaration) super.getDeclaration();
    }
    
    @Override
    public TypeDescriptor $getType() {
        return TypeDescriptor.klass(AppliedMemberInterface.class, $reifiedContainer, $reifiedType);
    }
}
