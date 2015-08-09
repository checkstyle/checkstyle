////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle;

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
    /** prevent instantiation */
    private ScopeUtils() {
    }

    /**
     * Returns the Scope specified by the modifier set.
     *
     * @param aMods root node of a modifier set
     * @return a {@code Scope} value
     */
    public static Scope getScopeFromMods(DetailAST aMods) {
        Scope retVal = Scope.PACKAGE; // default scope
        for (AST token = aMods.getFirstChild();
            token != null;
            token = token.getNextSibling()) {
            if ("public".equals(token.getText())) {
                retVal = Scope.PUBLIC;
                break;
            }
            else if ("protected".equals(token.getText())) {
                retVal = Scope.PROTECTED;
                break;
            }
            else if ("private".equals(token.getText())) {
                retVal = Scope.PRIVATE;
                break;
            }
        }
        return retVal;
    }

    /**
     * Returns the scope of the surrounding "block".
     * @param aAST the node to return the scope for
     * @return the Scope of the surrounding block
     */
    public static Scope getSurroundingScope(DetailAST aAST) {
        Scope retVal = null;
        for (DetailAST token = aAST.getParent();
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
                if (retVal == null || retVal.isIn(modScope)) {
                    retVal = modScope;
                }
            }
            else if (type == TokenTypes.LITERAL_NEW) {
                retVal = Scope.ANONINNER;
                break; //because Scope.ANONINNER is not in any other Scope
            }
        }

        return retVal;
    }

    /**
     * Returns whether a node is directly contained within an interface block.
     *
     * @param aAST the node to check if directly contained within an interface
     * block
     * @return a {@code boolean} value
     */
    public static boolean inInterfaceBlock(DetailAST aAST) {
        boolean retVal = false;

        // Loop up looking for a containing interface block
        for (DetailAST token = aAST.getParent();
             token != null;
             token = token.getParent()) {
            final int type = token.getType();
            if (type == TokenTypes.CLASS_DEF
                || type == TokenTypes.ENUM_DEF
                || type == TokenTypes.ANNOTATION_DEF) {
                break; // in a class, enum or annotation
            }
            else if (type == TokenTypes.LITERAL_NEW) {
                break; // inner implementation
            }
            else if (type == TokenTypes.INTERFACE_DEF) {
                retVal = true;
                break;
            }
        }

        return retVal;
    }

    /**
     * Returns whether a node is directly contained within an annotation block.
     *
     * @param aAST the node to check if directly contained within an annotation
     * block
     * @return a {@code boolean} value
     */
    public static boolean inAnnotationBlock(DetailAST aAST) {
        boolean retVal = false;

        // Loop up looking for a containing interface block
        for (DetailAST token = aAST.getParent();
             token != null;
             token = token.getParent()) {
            final int type = token.getType();
            if (type == TokenTypes.CLASS_DEF
                || type == TokenTypes.ENUM_DEF
                || type == TokenTypes.INTERFACE_DEF) {
                break; // in a class, enum or interface
            }
            else if (type == TokenTypes.LITERAL_NEW) {
                break; // inner implementation
            }
            else if (type == TokenTypes.ANNOTATION_DEF) {
                retVal = true;
                break;
            }
        }

        return retVal;
    }

    /**
     * Returns whether a node is directly contained within an interface or
     * annotation block.
     *
     * @param aAST the node to check if directly contained within an interface
     * or annotation block
     * @return a {@code boolean} value
     */
    public static boolean inInterfaceOrAnnotationBlock(DetailAST aAST) {
        return inInterfaceBlock(aAST) || inAnnotationBlock(aAST);
    }

    /**
     * Returns whether a node is directly contained within an enum block.
     *
     * @param aAST the node to check if directly contained within an enum
     * block
     * @return a {@code boolean} value
     */
    public static boolean inEnumBlock(DetailAST aAST) {
        boolean retVal = false;

        // Loop up looking for a containing interface block
        for (DetailAST token = aAST.getParent();
             token != null;
             token = token.getParent()) {
            final int type = token.getType();
            if (type == TokenTypes.INTERFACE_DEF
                || type == TokenTypes.ANNOTATION_DEF
                || type == TokenTypes.CLASS_DEF) {
                break; // in an interface, annotation or class
            }
            else if (type == TokenTypes.LITERAL_NEW) {
                break; // inner implementation, enums can't be inner classes
            }
            else if (type == TokenTypes.ENUM_DEF) {
                retVal = true;
                break;
            }
        }

        return retVal;
    }

    /**
     * Returns whether the scope of a node is restricted to a code block.
     * A code block is a method or constructor body, or a initialiser block.
     *
     * @param aAST the node to check
     * @return a {@code boolean} value
     */
    public static boolean inCodeBlock(DetailAST aAST) {
        boolean retVal = false;

        // Loop up looking for a containing code block
        for (DetailAST token = aAST.getParent();
             token != null;
             token = token.getParent()) {
            final int type = token.getType();
            if (type == TokenTypes.METHOD_DEF
                || type == TokenTypes.CTOR_DEF
                || type == TokenTypes.INSTANCE_INIT
                || type == TokenTypes.STATIC_INIT) {
                retVal = true;
                break;
            }
        }

        return retVal;
    }

    /**
     * Returns whether a node is contained in the outer most type block.
     *
     * @param aAST the node to check
     * @return a {@code boolean} value
     */
    public static boolean isOuterMostType(DetailAST aAST) {
        boolean retVal = true;
        for (DetailAST parent = aAST.getParent();
             parent != null;
             parent = parent.getParent()) {
            if (parent.getType() == TokenTypes.CLASS_DEF
                || parent.getType() == TokenTypes.INTERFACE_DEF
                || parent.getType() == TokenTypes.ANNOTATION_DEF
                || parent.getType() == TokenTypes.ENUM_DEF) {
                retVal = false;
                break;
            }
        }

        return retVal;
    }

    /**
     * Determines whether a node is a local variable definition.
     * I.e. if it is declared in a code block, a for initializer,
     * or a catch parameter.
     * @param aAST the node to check.
     * @return whether aAST is a local variable definition.
     */
    public static boolean isLocalVariableDef(DetailAST aAST) {
        // variable declaration?
        if (aAST.getType() == TokenTypes.VARIABLE_DEF) {
            final DetailAST parent = aAST.getParent();
            final int type = parent.getType();
            return type == TokenTypes.SLIST
                    || type == TokenTypes.FOR_INIT
                    || type == TokenTypes.FOR_EACH_CLAUSE;
        }
        // catch parameter?
        if (aAST.getType() == TokenTypes.PARAMETER_DEF) {
            final DetailAST parent = aAST.getParent();
            return parent.getType() == TokenTypes.LITERAL_CATCH;
        }
        return false;
    }
}
