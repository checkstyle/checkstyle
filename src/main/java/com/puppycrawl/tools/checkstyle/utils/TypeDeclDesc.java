////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.utils;

import java.util.ArrayDeque;
import java.util.Deque;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Maintains information about the type declaration.
 * Any ast node of type {@link TokenTypes#CLASS_DEF} or {@link TokenTypes#INTERFACE_DEF}
 * or {@link TokenTypes#ENUM_DEF} or {@link TokenTypes#ANNOTATION_DEF}
 * or {@link TokenTypes#RECORD_DEF} is considered as a type declaration.
 */
public class TypeDeclDesc {

    /**
     * Complete type declaration name with package name and outer type declaration name.
     */
    private final String qualifiedName;

    /**
     * Depth of nesting of type declaration.
     */
    private final int depth;

    /**
     * Type declaration ast node.
     */
    private final DetailAST typeDeclAst;

    /**
     * A stack of type declaration's instance and static variables.
     */
    private final Deque<VariableDesc> instanceAndClassVarStack;

    /** Is type declaration declared as final. */
    private final boolean declaredAsFinal;

    /** Is type declaration declared as abstract. */
    private final boolean declaredAsAbstract;

    /**
     * Does type declaration have non-private ctors.
     * Not applicable for interfaces.
     */
    private boolean withNonPrivateCtor;

    /**
     * Does type declaration have private ctors.
     * Not applicable for interfaces.
     */
    private boolean withPrivateCtor;

    /** Does class have nested subclass. */
    private boolean withNestedSubclass;

    /** Does class have anonymous inner class. */
    private boolean withAnonymousInnerClass;

    /**
     * Create a new TypeDeclDesc instance.
     *
     * @param qualifiedName qualified name
     * @param depth depth of nesting
     * @param typeDeclAst type declaration ast node
     */
    public TypeDeclDesc(String qualifiedName, int depth,
        DetailAST typeDeclAst) {
        this.qualifiedName = qualifiedName;
        this.depth = depth;
        this.typeDeclAst = typeDeclAst;
        instanceAndClassVarStack = new ArrayDeque<>();
        final DetailAST modifiers = typeDeclAst.findFirstToken(TokenTypes.MODIFIERS);
        declaredAsFinal = modifiers.findFirstToken(TokenTypes.FINAL) != null;
        declaredAsAbstract = modifiers.findFirstToken(TokenTypes.ABSTRACT) != null;
    }

    /**
     * Get the complete type declaration name i.e. type declaration name with package name
     * and outer type declaration name.
     *
     * @return qualified class name
     */
    public String getQualifiedName() {
        return qualifiedName;
    }

    /**
     * Get the depth of type declaration.
     *
     * @return the depth of nesting of type declaration
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Get the type declaration ast node.
     *
     * @return ast node of the type declaration
     */
    public DetailAST getTypeDeclAst() {
        return typeDeclAst;
    }

    /**
     * Get the copy of variables in instanceAndClassVar stack with updated scope.
     *
     * @param literalNewAst ast node of type {@link TokenTypes#LITERAL_NEW}
     * @return copy of variables in instanceAndClassVar stack with updated scope.
     */
    public Deque<VariableDesc> getUpdatedCopyOfVarStack(DetailAST literalNewAst) {
        final DetailAST updatedScope = literalNewAst.getLastChild();
        final Deque<VariableDesc> instAndClassVarDeque = new ArrayDeque<>();
        instanceAndClassVarStack.forEach(instVar -> {
            final VariableDesc variableDesc = new VariableDesc(instVar.getName(),
                instVar.getTypeAst(), updatedScope);
            variableDesc.registerAsInstOrClassVar();
            instAndClassVarDeque.push(variableDesc);
        });
        return instAndClassVarDeque;
    }

    /**
     * Add an instance variable or class variable to the stack.
     *
     * @param variableDesc variable to be added
     */
    public void addInstOrClassVar(VariableDesc variableDesc) {
        instanceAndClassVarStack.push(variableDesc);
    }

    /**
     *  Does class have private ctors.
     *
     *  @return true if class has private ctors
     */
    public boolean isWithPrivateCtor() {
        return withPrivateCtor;
    }

    /**
     *  Does class have non-private ctors.
     *
     *  @return true if class has non-private ctors
     */
    public boolean isWithNonPrivateCtor() {
        return withNonPrivateCtor;
    }

    /**
     * Does class have nested subclass.
     *
     * @return true if class has nested subclass
     */
    public boolean isWithNestedSubclass() {
        return withNestedSubclass;
    }

    /**
     *  Is class declared as final.
     *
     *  @return true if class is declared as final
     */
    public boolean isDeclaredAsFinal() {
        return declaredAsFinal;
    }

    /**
     *  Is class declared as abstract.
     *
     *  @return true if class is declared as final
     */
    public boolean isDeclaredAsAbstract() {
        return declaredAsAbstract;
    }

    /**
     * Does class have an anonymous inner class.
     *
     * @return true if class has anonymous inner class
     */
    public boolean isWithAnonymousInnerClass() {
        return withAnonymousInnerClass;
    }

    /**
     * Adds private ctor.
     */
    public void registerPrivateCtor() {
        withPrivateCtor = true;
    }

    /**
     * Adds non-private ctor.
     */
    public void registerNonPrivateCtor() {
        withNonPrivateCtor = true;
    }

    /**
     * Adds nested subclass.
     */
    public void registerNestedSubclass() {
        withNestedSubclass = true;
    }

    /**
     * Adds anonymous inner class.
     */
    public void registerAnonymousInnerClass() {
        withAnonymousInnerClass = true;
    }
}
