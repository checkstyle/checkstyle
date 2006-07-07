////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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
        if (objBlock == null) {
            return false;
        }
        int constructorCount = 0;
        DetailAST child = (DetailAST) objBlock.getFirstChild();
        while (child != null) {
            if (child.getType() == TokenTypes.CTOR_DEF) {
                constructorCount++;
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
        // implicit, no parameter constructor?
        return ((constructorCount == 0) && (aParameterCount == 0));
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
        final DetailAST definer = getDefiner(aAST);
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
        final DetailAST definer = getDefiner(aAST);
        return ((definer != null)
            && Utils.hasImplements(definer, "javax.ejb.SessionBean"));
    }

    /**
     * Determines whether an AST node is in the definition of an
     * EJB class.
     * @param aAST the AST to check.
     * @return true if aAST is in the definition of a
     * an EJB class.
     */
    public static boolean isInEJB(DetailAST aAST)
    {
        final DetailAST definer = getDefiner(aAST);
        return (
            (definer != null)
                && (Utils.hasImplements(definer, "javax.ejb.SessionBean")
                    || Utils.hasImplements(definer, "javax.ejb.EntityBean")
                    || (Utils
                        .hasImplements(definer, "javax.ejb.MessageDrivenBean")
                        && Utils.hasImplements(
                            definer,
                            "javax.jms.MessageListener"))));
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
            DetailAST child = (DetailAST) implementsAST.getFirstChild();
            while (child != null) {
                if ((child.getType() == TokenTypes.IDENT)
                    || (child.getType() == TokenTypes.DOT))
                {
                    final String name = Utils.constructDottedName(child);
                    if (name.equals(aInterface)
                        || name.equals(shortName))
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
     * Determines whether an AST node declares an extension of a class or
     * interface.
     * @param aAST the AST to check.
     * @param aClassOrInterface the class or interface to check.
     * @return if the class defined by aAST implements declares an
     * extension of aClassOrInterface.
     */
    public static boolean hasExtends(DetailAST aAST, String aClassOrInterface)
    {
        final String shortName =
            com.puppycrawl.tools.checkstyle.api.Utils.baseClassname(
                aClassOrInterface);
        final DetailAST extendsAST =
            aAST.findFirstToken(TokenTypes.EXTENDS_CLAUSE);
        if (extendsAST != null) {
            DetailAST child = (DetailAST) extendsAST.getFirstChild();
            while (child != null) {
                if ((child.getType() == TokenTypes.IDENT)
                    || (child.getType() == TokenTypes.DOT))
                {
                    final String name = Utils.constructDottedName(child);
                    if (name.equals(aClassOrInterface)
                        || name.equals(shortName))
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
     * Determines whether an AST node declares a throw of an Exception.
     * @param aAST the AST to check.
     * @param aException the name of the Exception to check.
     * @return if the class defined by aAST implements declares a throw
     * of aException.
     */
    public static boolean hasThrows(DetailAST aAST, String aException)
    {
        final String shortName =
            com.puppycrawl.tools.checkstyle.api.Utils.baseClassname(
                aException);
        final DetailAST throwsAST =
            aAST.findFirstToken(TokenTypes.LITERAL_THROWS);
        if (throwsAST != null) {
            DetailAST child = (DetailAST) throwsAST.getFirstChild();
            while (child != null) {
                if ((child.getType() == TokenTypes.IDENT)
                    || (child.getType() == TokenTypes.DOT))
                {
                    final String name = Utils.constructDottedName(child);
                    if (name.equals(aException)
                        || name.equals(shortName))
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

    /**
     * Builds the dotted name String representation of the object contained
     * within an AST.
     *
     * @param aAST the AST containing the entire hierarcy of the object
     * @return the dotted name String representation of the object contained
     * within aAST.
     */
    public static String constructDottedName(DetailAST aAST)
    {
        String result;

        if (aAST.getType() == TokenTypes.DOT) {
            final DetailAST left = (DetailAST) aAST.getFirstChild();
            final DetailAST right = (DetailAST) left.getNextSibling();

            result =
                constructDottedName(left) + "." + constructDottedName(right);
        }
        else {
            result = aAST.getText();
        }

        return result;
    }

    /**
     * Tests whether two method definition ASTs have the same parameter lists
     * according to type.
     * @param aMethodAST1 the first method AST to test.
     * @param aMethodAST2 the second method AST to test.
     * @return true if aMethodAST1 and aMethodAST2 have the same
     * parameter lists.
     */
    public static boolean sameParameters(
        DetailAST aMethodAST1,
        DetailAST aMethodAST2)
    {
        final DetailAST params1 =
            aMethodAST1.findFirstToken(TokenTypes.PARAMETERS);
        final DetailAST params2 =
            aMethodAST2.findFirstToken(TokenTypes.PARAMETERS);
        if (params1.getChildCount() != params2.getChildCount()) {
            return false;
        }
        DetailAST param1 = (DetailAST) params1.getFirstChild();
        DetailAST param2 = (DetailAST) params2.getFirstChild();
        while (param1 != null) {
            if ((param1.getType() == TokenTypes.PARAMETER_DEF)
                && (param2.getType() == TokenTypes.PARAMETER_DEF))
            {
                final DetailAST type1 = param1.findFirstToken(TokenTypes.TYPE);
                final DetailAST type2 = param2.findFirstToken(TokenTypes.TYPE);
                if (!equalTypes(type1, type2)) {
                    return false;
                }
            }
            param1 = (DetailAST) param1.getNextSibling();
            param2 = (DetailAST) param2.getNextSibling();
        }
        return true;
    }

    /**
     * Tests whether two type AST nodes have the same type.
     * @param aTypeAST1 the first type AST to test.
     * @param aTypeAST2 the second type AST to test.
     * @return true if aTypeAST1 and aTypeAST2 have the same type.
     */
    public static boolean equalTypes(
        DetailAST aTypeAST1,
        DetailAST aTypeAST2)
    {
        final DetailAST child1 = (DetailAST) aTypeAST1.getFirstChild();
        final DetailAST child2 = (DetailAST) aTypeAST2.getFirstChild();
        final String name1 = Utils.constructDottedName(child1);
        final String name2 = Utils.constructDottedName(child2);
        return name1.equals(name2);
    }
}
