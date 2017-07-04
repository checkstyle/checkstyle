////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import antlr.collections.AST;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Contains utility methods for working on scope.
 *
 * @author Oliver Burn
 */
public final class ScopeUtils {
    /** Prevent instantiation. */
    private ScopeUtils() {
    }

    /**
     * Returns the Scope specified by the modifier set.
     *
     * @param aMods root node of a modifier set
     * @return a {@code Scope} value
     */
    public static Scope getScopeFromMods(DetailAST aMods) {
        // default scope
        Scope returnValue = Scope.PACKAGE;
        for (AST token = aMods.getFirstChild(); token != null
                && returnValue == Scope.PACKAGE;
                token = token.getNextSibling()) {
            if ("public".equals(token.getText())) {
                returnValue = Scope.PUBLIC;
            }
            else if ("protected".equals(token.getText())) {
                returnValue = Scope.PROTECTED;
            }
            else if ("private".equals(token.getText())) {
                returnValue = Scope.PRIVATE;
            }
        }
        return returnValue;
    }

    /**
     * Returns the scope of the surrounding "block".
     * @param node the node to return the scope for
     * @return the Scope of the surrounding block
     */
    public static Scope getSurroundingScope(DetailAST node) {
        Scope returnValue = null;
        for (DetailAST token = node.getParent();
             token != null;
             token = token.getParent()) {
            final int type = token.getType();
            if (type == TokenTypes.CLASS_DEF
                || type == TokenTypes.INTERFACE_DEF
                || type == TokenTypes.ANNOTATION_DEF
                || type == TokenTypes.ENUM_DEF) {
                final DetailAST mods =
                    token.findFirstToken(TokenTypes.MODIFIERS);
                final Scope modScope = getScopeFromMods(mods);
                if (returnValue == null || returnValue.isIn(modScope)) {
                    returnValue = modScope;
                }
            }
            else if (type == TokenTypes.LITERAL_NEW) {
                returnValue = Scope.ANONINNER;
                // because Scope.ANONINNER is not in any other Scope
                break;
            }
        }

        return returnValue;
    }

    /**
     * Returns whether a node is directly contained within an interface block.
     *
     * @param node the node to check if directly contained within an interface block.
     * @return a {@code boolean} value
     */
    public static boolean isInInterfaceBlock(DetailAST node) {
        boolean returnValue = false;

        // Loop up looking for a containing interface block
        for (DetailAST token = node.getParent();
             token != null && !returnValue;
             token = token.getParent()) {

            final int type = token.getType();

            if (type == TokenTypes.INTERFACE_DEF) {
                returnValue = true;
            }
            else if (type == TokenTypes.CLASS_DEF
                || type == TokenTypes.ENUM_DEF
                || type == TokenTypes.ANNOTATION_DEF
                || type == TokenTypes.LITERAL_NEW) {
                break;
            }
        }

        return returnValue;
    }

    /**
     * Returns whether a node is directly contained within an annotation block.
     *
     * @param node the node to check if directly contained within an annotation block.
     * @return a {@code boolean} value
     */
    public static boolean isInAnnotationBlock(DetailAST node) {
        boolean returnValue = false;

        // Loop up looking for a containing interface block
        for (DetailAST token = node.getParent();
             token != null && !returnValue;
             token = token.getParent()) {
            final int type = token.getType();
            if (type == TokenTypes.ANNOTATION_DEF) {
                returnValue = true;
            }
            else if (type == TokenTypes.CLASS_DEF
                || type == TokenTypes.ENUM_DEF
                || type == TokenTypes.INTERFACE_DEF
                || type == TokenTypes.LITERAL_NEW) {
                break;
            }

        }

        return returnValue;
    }

    /**
     * Returns whether a node is directly contained within an interface or
     * annotation block.
     *
     * @param node the node to check if directly contained within an interface
     *     or annotation block.
     * @return a {@code boolean} value
     */
    public static boolean isInInterfaceOrAnnotationBlock(DetailAST node) {
        return isInInterfaceBlock(node) || isInAnnotationBlock(node);
    }

    /**
     * Returns whether a node is directly contained within an enum block.
     *
     * @param node the node to check if directly contained within an enum block.
     * @return a {@code boolean} value
     */
    public static boolean isInEnumBlock(DetailAST node) {
        boolean returnValue = false;

        // Loop up looking for a containing interface block
        for (DetailAST token = node.getParent();
             token != null && !returnValue;
             token = token.getParent()) {
            final int type = token.getType();
            if (type == TokenTypes.ENUM_DEF) {
                returnValue = true;
            }
            else if (type == TokenTypes.INTERFACE_DEF
                || type == TokenTypes.ANNOTATION_DEF
                || type == TokenTypes.CLASS_DEF
                || type == TokenTypes.LITERAL_NEW) {
                break;
            }
        }

        return returnValue;
    }

    /**
     * Returns whether the scope of a node is restricted to a code block.
     * A code block is a method or constructor body, an initializer block, or lambda body.
     *
     * @param node the node to check
     * @return a {@code boolean} value
     */
    public static boolean isInCodeBlock(DetailAST node) {
        boolean returnValue = false;

        // Loop up looking for a containing code block
        for (DetailAST token = node.getParent();
             token != null;
             token = token.getParent()) {
            final int type = token.getType();
            if (type == TokenTypes.METHOD_DEF
                    || type == TokenTypes.CTOR_DEF
                    || type == TokenTypes.INSTANCE_INIT
                    || type == TokenTypes.STATIC_INIT
                    || type == TokenTypes.LAMBDA) {
                returnValue = true;
                break;
            }
        }

        return returnValue;
    }

    /**
     * Returns whether a node is contained in the outer most type block.
     *
     * @param node the node to check
     * @return a {@code boolean} value
     */
    public static boolean isOuterMostType(DetailAST node) {
        boolean returnValue = true;
        for (DetailAST parent = node.getParent();
             parent != null;
             parent = parent.getParent()) {
            if (parent.getType() == TokenTypes.CLASS_DEF
                || parent.getType() == TokenTypes.INTERFACE_DEF
                || parent.getType() == TokenTypes.ANNOTATION_DEF
                || parent.getType() == TokenTypes.ENUM_DEF) {
                returnValue = false;
                break;
            }
        }

        return returnValue;
    }

    /**
     * Determines whether a node is a local variable definition.
     * I.e. if it is declared in a code block, a for initializer,
     * or a catch parameter.
     * @param node the node to check.
     * @return whether aAST is a local variable definition.
     */
    public static boolean isLocalVariableDef(DetailAST node) {
        boolean localVariableDef = false;
        // variable declaration?
        if (node.getType() == TokenTypes.VARIABLE_DEF) {
            final DetailAST parent = node.getParent();
            final int type = parent.getType();
            localVariableDef = type == TokenTypes.SLIST
                    || type == TokenTypes.FOR_INIT
                    || type == TokenTypes.FOR_EACH_CLAUSE;
        }
        // catch parameter?
        if (node.getType() == TokenTypes.PARAMETER_DEF) {
            final DetailAST parent = node.getParent();
            localVariableDef = parent.getType() == TokenTypes.LITERAL_CATCH;
        }

        if (node.getType() == TokenTypes.RESOURCE) {
            localVariableDef = true;
        }
        return localVariableDef;
    }

    /**
     * Determines whether a node is a class field definition.
     * I.e. if a variable is not declared in a code block, a for initializer,
     * or a catch parameter.
     * @param node the node to check.
     * @return whether a node is a class field definition.
     */
    public static boolean isClassFieldDef(DetailAST node) {
        return node.getType() == TokenTypes.VARIABLE_DEF && !isLocalVariableDef(node);
    }

    /**
     * Checks whether ast node is in a specific scope.
     * @param ast the node to check.
     * @param scope a {@code Scope} value.
     * @return true if the ast node is in the scope.
     */
    public static boolean isInScope(DetailAST ast, Scope scope) {
        final Scope surroundingScopeOfAstToken = getSurroundingScope(ast);
        return surroundingScopeOfAstToken == scope;
    }
}
