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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Maintains information about the variable.
 */
public class VariableDesc {

    /**
     * The name of the variable.
     */
    private final String name;

    /**
     * Ast of type {@link TokenTypes#TYPE}.
     */
    private final DetailAST typeAst;

    /**
     * The scope of variable is determined by the ast of type
     * {@link TokenTypes#SLIST} or {@link TokenTypes#LITERAL_FOR}
     * or {@link TokenTypes#OBJBLOCK} which is enclosing the variable.
     */
    private final DetailAST scope;

    /**
     * Is an instance variable or a class variable.
     */
    private boolean instVarOrClassVar;

    /**
     * Is the variable used.
     */
    private boolean used;

    /**
     * Create a new VariableDesc instance.
     *
     * @param name name of the variable
     * @param typeAst ast of type {@link TokenTypes#TYPE}
     * @param scope ast of type {@link TokenTypes#SLIST} or
     *              {@link TokenTypes#LITERAL_FOR} or {@link TokenTypes#OBJBLOCK}
     *              which is enclosing the variable
     */
    public VariableDesc(String name, DetailAST typeAst, DetailAST scope) {
        this.name = name;
        this.typeAst = typeAst;
        this.scope = scope;
    }

    /**
     * Get the name of variable.
     *
     * @return name of variable
     */
    public String getName() {
        return name;
    }

    /**
     * Get the associated ast node of type {@link TokenTypes#TYPE}.
     *
     * @return the associated ast node of type {@link TokenTypes#TYPE}
     */
    public DetailAST getTypeAst() {
        return typeAst;
    }

    /**
     * Get ast of type {@link TokenTypes#SLIST}
     * or {@link TokenTypes#LITERAL_FOR} or {@link TokenTypes#OBJBLOCK}
     * which is enclosing the variable i.e. its scope.
     *
     * @return the scope associated with the variable
     */
    public DetailAST getScope() {
        return scope;
    }

    /**
     * Register the variable as used.
     */
    public void registerAsUsed() {
        used = true;
    }

    /**
     * Register the variable as an instance variable or
     * class variable.
     */
    public void registerAsInstOrClassVar() {
        instVarOrClassVar = true;
    }

    /**
     * Is the variable used or not.
     *
     * @return true if variable is used
     */
    public boolean isUsed() {
        return used;
    }

    /**
     * Is an instance variable or a class variable.
     *
     * @return true if is an instance variable or a class variable
     */
    public boolean isInstVarOrClassVar() {
        return instVarOrClassVar;
    }
}
