////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2003  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.j2ee;

import antlr.collections.AST;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * J2EE check utility methods.
 * @author Rick Giles
 */
public class Utils
{
    /**
     * Determines whether an AST node has a definition of a public method.
     * @param aAST the node to check. Normally aAST is a CLASS_DEF.
     * @param aName the name of the method.
     * @return true if aAST has a definition of a public method with name
     * aName.
     */
    public static boolean hasPublicMethod(DetailAST aAST, String aName)
    {
        final DetailAST objBlock = aAST.findFirstToken(TokenTypes.OBJBLOCK);
        if (objBlock != null) {
            DetailAST child = (DetailAST) objBlock.getFirstChild();
            while (child != null) {
                if ((child.getType() == TokenTypes.METHOD_DEF)
                    && Utils.isPublicMethod(child, aName))
                {
                         return true;
                }
                child = (DetailAST) child.getNextSibling();
            }
        }
        return false;
    }

    /**
     * Determines whether an AST node has a definition of a public method.
     * @param aAST the node to check. Normally aAST is a CLASS_DEF.
     * @param aName the name of the method.
     * @param aIsVoid designates whether the method is void.
     * @return true if aAST has a definition of a public method with name
     * aName and that is void according to aIsVoid.
     */
    public static boolean hasPublicMethod(
        DetailAST aAST,
        String aName,
        boolean aIsVoid)
    {
        final DetailAST objBlock = aAST.findFirstToken(TokenTypes.OBJBLOCK);
        if (objBlock != null) {
            DetailAST child = (DetailAST) objBlock.getFirstChild();
            while (child != null) {
                if ((child.getType() == TokenTypes.METHOD_DEF)
                    && Utils.isPublicMethod(
                        child,
                        aName,
                        aIsVoid))
                {
                         return true;
                }
                child = (DetailAST) child.getNextSibling();
            }
        }
        return false;
    }

    /**
     * Determines whether an AST node has a definition of a public method.
     * @param aAST the node to check. Normally aAST is a CLASS_DEF.
     * @param aName the name of the method.
     * @param aIsVoid designates whether the method is void.
     * @param aParameterCount the number of method parameters.
     * @return true if aAST has a definition of a public method with name
     * aName and that is void according to aIsVoid.
     */
    public static boolean hasPublicMethod(
        DetailAST aAST,
        String aName,
        boolean aIsVoid,
        int aParameterCount)
    {
        final DetailAST objBlock = aAST.findFirstToken(TokenTypes.OBJBLOCK);
        if (objBlock != null) {
            DetailAST child = (DetailAST) objBlock.getFirstChild();
            while (child != null) {
                if ((child.getType() == TokenTypes.METHOD_DEF)
                    && Utils.isPublicMethod(
                        child,
                        aName,
                        aIsVoid,
                        aParameterCount))
                {
                    return true;
                }
                child = (DetailAST) child.getNextSibling();
            }
        }
        return false;
    }

   /**
    * Determines whether an AST defines a class with a public constructor
    * with a given number of parameters.
    * @param aAST the AST to check.
    * @param aParameterCount the number of parameters
    * @return true if aAST defines a class with a public constructor
    * with aParameterCount parameters.
    */
    public static boolean hasPublicConstructor(
        DetailAST aAST,
        int aParameterCount)
    {
        final DetailAST objBlock = aAST.findFirstToken(TokenTypes.OBJBLOCK);
        if (objBlock != null) {
            DetailAST child = (DetailAST) objBlock.getFirstChild();
            while (child != null) {
                if (child.getType() == TokenTypes.CTOR_DEF) {
                    final DetailAST parameters =
                        child.findFirstToken(TokenTypes.PARAMETERS);
                    if (Utils.isPublic(child)
                        && (parameters.getChildCount() == aParameterCount))
                    {
                        return true;
                    }
                }
                child = (DetailAST) child.getNextSibling();
            }
        }
        return false;
    }

    /**
     * Determines whether an AST node is in the definition of a
     * class that implements javax.ejb.EntityBean.
     * @param aAST the AST to check.
     * @return true if aAST is in the definition of a
     * class that implements javax.ejb.SessionBean.
     */
    public static boolean implementsEntityBean(DetailAST aAST)
    {
        DetailAST definer = getDefiner(aAST);
        return ((definer != null)
            && Utils.hasImplements(definer, "javax.ejb.EntityBean"));
    }

    /**
     * Determines whether an AST node is in the definition of a
     * class that implements javax.ejb.SessionBean.
     * @param aAST the AST to check.
     * @return true if aAST is in the definition of a
     * class that implements javax.ejb.SessionBean.
     */
    public static boolean implementsSessionBean(DetailAST aAST)
    {
        DetailAST definer = getDefiner(aAST);
        return ((definer != null)
            && Utils.hasImplements(definer, "javax.ejb.SessionBean"));
    }

    /**
     * Finds the DetailAST for the class definition of an AST.
     * @param aAST the AST for the search.
     * @return the class definition AST for aAST.
     */
    private static DetailAST getDefiner(DetailAST aAST)
    {
        DetailAST definer = aAST.getParent();
        while ((definer != null)) {
            if (definer.getType() == TokenTypes.CLASS_DEF) {
                break;
            }
            definer = definer.getParent();
        }
        return definer;
    }

    /**
     * Determines whether an AST defines an abstract element.
     * @param aAST the AST to check.
     * @return true if aAST defines an abstract element.
     */
    public static boolean isAbstract(DetailAST aAST)
    {
        final DetailAST mods = aAST.findFirstToken(TokenTypes.MODIFIERS);
        return ((mods != null)
            && mods.branchContains(TokenTypes.ABSTRACT));
    }

    /**
     * Determines whether an AST defines a final element.
     * @param aAST the AST to check.
     * @return true if aAST defines a final element.
     */
    public static boolean isFinal(DetailAST aAST)
    {
        final DetailAST mods = aAST.findFirstToken(TokenTypes.MODIFIERS);
        return ((mods != null)
            && mods.branchContains(TokenTypes.FINAL));
    }

    /**
     * Determines whether an AST defines a public element.
     * @param aAST the AST to check.
     * @return true if aAST defines a public element.
     */
    public static boolean isPublic(DetailAST aAST)
    {
        final DetailAST mods = aAST.findFirstToken(TokenTypes.MODIFIERS);
        return ((mods != null)
            && mods.branchContains(TokenTypes.LITERAL_PUBLIC));
    }

    /**
     * Determines whether an AST defines a static element.
     * @param aAST the AST to check.
     * @return true if aAST defines a static element.
     */
    public static boolean isStatic(DetailAST aAST)
    {
        final DetailAST mods = aAST.findFirstToken(TokenTypes.MODIFIERS);
        return ((mods != null)
            && mods.branchContains(TokenTypes.LITERAL_STATIC));
    }

    /**
     * Determines whether an AST defines a void method.
     * @param aAST the AST to check.
     * @return true if aAST defines a void method.
     */
    public static boolean isVoid(DetailAST aAST)
    {
        final DetailAST type = aAST.findFirstToken(TokenTypes.TYPE);
        return ((type != null)
            && type.branchContains(TokenTypes.LITERAL_VOID));
    }

    /**
     * Determines whether an AST node declares an implementation of an
     * interface.
     * @param aAST the AST to check.
     * @param aInterface the interface to check.
     * @return if the class defined by aAST implements declares an
     * implementation of aInterface.
     */
    public static boolean hasImplements(DetailAST aAST, String aInterface)
    {
        final String shortName =
            com.puppycrawl.tools.checkstyle.api.Utils.baseClassname(aInterface);
        final DetailAST implementsAST =
            aAST.findFirstToken(TokenTypes.IMPLEMENTS_CLAUSE);
        if (implementsAST != null) {
            AST child = implementsAST.getFirstChild();
            while (child != null) {
                if (child.getType() == TokenTypes.IDENT) {
                    final String name = child.getText();
                    if (name.equals(aInterface)
                        || name.equals(shortName))
                    {
                        return true;
                    }
                }
                child = child.getNextSibling();
            }
        }
        return false;
    }

    /**
     * Determines whether an AST node defines a public method.
     * @param aAST the node to check. Normally aAST is a METHOD_DEF.
     * @param aName the name of the method.
     * @param aIsVoid designates whether the method is void.
     * @param aParameterCount the number of method parameters.
     * @return true if aAST is the definition of a public method with name
     * aName and that is void according to aIsVoid.
     */
    public static boolean isPublicMethod(
        DetailAST aAST,
        String aName,
        boolean aIsVoid,
        int aParameterCount)
    {
        final DetailAST nameNode = aAST.findFirstToken(TokenTypes.IDENT);
        if (nameNode != null) {
            final String name = nameNode.getText();
            if (name.equals(aName)
                && isPublic(aAST)
                && (isVoid(aAST) == aIsVoid))
            {
                final DetailAST parameters =
                    aAST.findFirstToken(TokenTypes.PARAMETERS);
                if (parameters.getChildCount() == aParameterCount) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines whether an AST node defines a public method.
     * @param aAST the node to check. Normally aAST is a METHOD_DEF.
     * @param aName the name of the method.
     * @param aIsVoid designates whether the method is void.
     * @return true if aAST is the definition of a public method with name
     * aName and that is void according to aIsVoid.
     */
    public static boolean isPublicMethod(
        DetailAST aAST,
        String aName,
        boolean aIsVoid)
    {
        final DetailAST nameNode = aAST.findFirstToken(TokenTypes.IDENT);
        if (nameNode != null) {
            final String name = nameNode.getText();
            if (name.equals(aName)
                && isPublic(aAST)
                && (isVoid(aAST) == aIsVoid))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether an AST node defines a public method.
     * @param aAST the node to check. Normally aAST is a METHOD_DEF.
     * @param aName the name of the method.
     * @return true if aAST is the definition of a public method with name
     * aName and that is void according to aIsVoid.
     */
    public static boolean isPublicMethod(DetailAST aAST, String aName)
    {
        final DetailAST nameNode = aAST.findFirstToken(TokenTypes.IDENT);
        if (nameNode != null) {
            final String name = nameNode.getText();
            if (name.equals(aName) && isPublic(aAST)) {
                return true;
            }
        }
        return false;
    }
}
