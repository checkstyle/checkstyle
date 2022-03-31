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
 * Maintains information about the type of declaration.
 * Any ast node of type {@link TokenTypes#CLASS_DEF} or {@link TokenTypes#INTERFACE_DEF}
 * or {@link TokenTypes#ENUM_DEF} or {@link TokenTypes#ANNOTATION_DEF}
 * or {@link TokenTypes#RECORD_DEF} is considered as a type declaration.
 */
public abstract class AbstractTypeDeclarationDescription {

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
    private final DetailAST typeDeclarationAst;


    protected AbstractTypeDeclarationDescription(String qualifiedName, int depth,
                                                 DetailAST typeDeclarationAst) {
        this.qualifiedName = qualifiedName;
        this.depth = depth;
        this.typeDeclarationAst = typeDeclarationAst;
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
    public DetailAST getTypeDeclarationAst() {
        return typeDeclarationAst;
    }
}
