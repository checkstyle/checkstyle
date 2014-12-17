////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.coding;

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.AbstractFormatCheck;
import com.puppycrawl.tools.checkstyle.checks.CheckUtils;
import java.util.Set;

/**
 * <p>
 * Checks that particular class are never used as types in variable
 * declarations, return values or parameters. Includes
 * a pattern check that by default disallows abstract classes.
 * </p>
 * <p>
 * Rationale:
 * Helps reduce coupling on concrete classes. In addition abstract
 * classes should be thought of a convenience base class
 * implementations of interfaces and as such are not types themselves.
 * </p>
 * Check has following properties:
 * <p>
 * <b>format</b> - Pattern for illegal class names.
 * </p>
 * <p>
 * <b>legalAbstractClassNames</b> - Abstract classes that may be used as types.
 * </p>
 * <p>
 * <b>illegalClassNames</b> - Classes that should not be used as types in variable
   declarations, return values or parameters.
 * It is possible to set illegal class names via short or
 * <a href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html#jls-6.7">
 *  canonical</a> name.
 *  Specifying illegal type invokes analyzing imports and Check puts violations at
 *   corresponding declarations
 *  (of variables, methods or parameters). This helps to avoid ambiguous cases, e.g.:
 * <p>
 * <code>java.awt.List</code> was set as illegal class name, then, code like:
 * <p>
 * <code>
 * import java.util.List;<br>
 * ...<br>
 * List list; //No violation here
 * </code>
 * </p>
 * will be ok.
 * </p>
 * <p>
 * <b>ignoredMethodNames</b> - Methods that should not be checked..
 * </p>
 *
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 * @author <a href="mailto:nesterenko-aleksey@list.ru">Aleksey Nesterenko</a>
 */
public final class IllegalTypeCheck extends AbstractFormatCheck
{
    /** Default value of pattern for illegal class name. */
    private static final String DEFAULT_FORMAT = "^(.*[\\.])?Abstract.*$";
    /** Abstract classes legal by default. */
    private static final String[] DEFAULT_LEGAL_ABSTRACT_NAMES = {};
    /** Types illegal by default. */
    private static final String[] DEFAULT_ILLEGAL_TYPES = {
        "GregorianCalendar",
        "Hashtable",
        "HashSet",
        "HashMap",
        "ArrayList",
        "LinkedList",
        "LinkedHashMap",
        "LinkedHashSet",
        "TreeSet",
        "TreeMap",
        "Vector",
        "java.util.GregorianCalendar",
        "java.util.Hashtable",
        "java.util.HashSet",
        "java.util.HashMap",
        "java.util.ArrayList",
        "java.util.LinkedList",
        "java.util.LinkedHashMap",
        "java.util.LinkedHashSet",
        "java.util.TreeSet",
        "java.util.TreeMap",
        "java.util.Vector",
    };

    /** Default ignored method names. */
    private static final String[] DEFAULT_IGNORED_METHOD_NAMES = {
        "getInitialContext",
        "getEnvironment",
    };

    /** illegal classes. */
    private final Set<String> mIllegalClassNames = Sets.newHashSet();
    /** legal abstract classes. */
    private final Set<String> mLegalAbstractClassNames = Sets.newHashSet();
    /** methods which should be ignored. */
    private final Set<String> mIgnoredMethodNames = Sets.newHashSet();

    /** Creates new instance of the check. */
    public IllegalTypeCheck()
    {
        super(DEFAULT_FORMAT);
        setIllegalClassNames(DEFAULT_ILLEGAL_TYPES);
        setLegalAbstractClassNames(DEFAULT_LEGAL_ABSTRACT_NAMES);
        setIgnoredMethodNames(DEFAULT_IGNORED_METHOD_NAMES);
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.VARIABLE_DEF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.IMPORT,
        };
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        switch (aAST.getType()) {
        case TokenTypes.METHOD_DEF:
            visitMethodDef(aAST);
            break;
        case TokenTypes.VARIABLE_DEF:
            visitVariableDef(aAST);
            break;
        case TokenTypes.PARAMETER_DEF:
            visitParameterDef(aAST);
            break;
        case TokenTypes.IMPORT:
            visitImport(aAST);
            break;
        default:
            throw new IllegalStateException(aAST.toString());
        }
    }

    /**
     * Checks return type of a given method.
     * @param aMethodDef method for check.
     */
    private void visitMethodDef(DetailAST aMethodDef)
    {
        if (isCheckedMethod(aMethodDef)) {
            checkClassName(aMethodDef);
        }
    }

    /**
     * Checks type of parameters.
     * @param aParamDef parameter list for check.
     */
    private void visitParameterDef(DetailAST aParamDef)
    {
        final DetailAST grandParentAST = aParamDef.getParent().getParent();

        if ((grandParentAST.getType() == TokenTypes.METHOD_DEF)
            && isCheckedMethod(grandParentAST))
        {
            checkClassName(aParamDef);
        }
    }

    /**
     * Checks type of given variable.
     * @param aVariableDef variable to check.
     */
    private void visitVariableDef(DetailAST aVariableDef)
    {
        checkClassName(aVariableDef);
    }

    /**
     * Checks imported type (as static and star imports are not supported by Check,
     *  only type is in the consideration).<br>
     * If this type is illegal due to Check's options - puts violation on it.
     * @param aImport {@link TokenTypes#IMPORT Import}
     */
    private void visitImport(DetailAST aImport)
    {
        if (!isStarImport(aImport)) {
            final String canonicalName = getCanonicalName(aImport);
            extendIllegalClassNamesWithShortName(canonicalName);
        }
    }

    /**
     * Checks if current import is star import. E.g.:
     * <p>
     * <code>
     * import java.util.*;
     * </code>
     * </p>
     * @param aImport {@link TokenTypes#IMPORT Import}
     * @return true if it is star import
     */
    private static boolean isStarImport(DetailAST aImport)
    {
        boolean result = false;
        DetailAST toVisit = aImport;
        while (toVisit != null) {
            toVisit = getNextSubTreeNode(toVisit, aImport);
            if (toVisit != null && toVisit.getType() == TokenTypes.STAR) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Checks type of given method, parameter or variable.
     * @param aAST node to check.
     */
    private void checkClassName(DetailAST aAST)
    {
        final DetailAST type = aAST.findFirstToken(TokenTypes.TYPE);
        final FullIdent ident = CheckUtils.createFullType(type);

        if (isMatchingClassName(ident.getText())) {
            log(ident.getLineNo(), ident.getColumnNo(),
                "illegal.type", ident.getText());
        }
    }

    /**
     * @param aClassName class name to check.
     * @return true if given class name is one of illegal classes
     *         or if it matches to abstract class names pattern.
     */
    private boolean isMatchingClassName(String aClassName)
    {
        final String shortName = aClassName.substring(aClassName.lastIndexOf(".") + 1);
        return (mIllegalClassNames.contains(aClassName)
                || mIllegalClassNames.contains(shortName))
            || (!mLegalAbstractClassNames.contains(aClassName)
                && getRegexp().matcher(aClassName).find());
    }

    /**
     * Extends illegal class names set via imported short type name.
     * @param aCanonicalName
     *  <a href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html#jls-6.7">
     *  Canonical</a> name of imported type.
     */
    private void extendIllegalClassNamesWithShortName(String aCanonicalName)
    {
        if (mIllegalClassNames.contains(aCanonicalName)) {
            final String shortName = aCanonicalName.
                substring(aCanonicalName.lastIndexOf(".") + 1);
            mIllegalClassNames.add(shortName);
        }
    }

    /**
     * Gets imported type's
     * <a href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html#jls-6.7">
     *  canonical name</a>.
     * @param aImport {@link TokenTypes#IMPORT Import}
     * @return Imported canonical type's name.
     */
    private static String getCanonicalName(DetailAST aImport)
    {
        final StringBuilder canonicalNameBuilder = new StringBuilder();
        DetailAST toVisit = aImport;
        while (toVisit != null) {
            toVisit = getNextSubTreeNode(toVisit, aImport);
            if (toVisit != null
                   && (toVisit.getType() == TokenTypes.IDENT
                      || toVisit.getType() == TokenTypes.STAR))
            {
                canonicalNameBuilder.append(toVisit.getText());
                final DetailAST nextSubTreeNode = getNextSubTreeNode(toVisit, aImport);
                if (nextSubTreeNode.getType() != TokenTypes.SEMI) {
                    canonicalNameBuilder.append('.');
                }
            }
        }
        return canonicalNameBuilder.toString();
    }

    /**
     * Gets the next node of a syntactical tree (child of a current node or
     * sibling of a current node, or sibling of a parent of a current node)
     * @param aCurrentNodeAst Current node in considering
     * @param aSubTreeRootAst SubTree root
     * @return Current node after bypassing, if current node reached the root of a subtree
     *        method returns null
     */
    private static DetailAST
        getNextSubTreeNode(DetailAST aCurrentNodeAst, DetailAST aSubTreeRootAst)
    {
        DetailAST currentNode = aCurrentNodeAst;
        DetailAST toVisitAst = currentNode.getFirstChild();
        while (toVisitAst == null) {
            toVisitAst = currentNode.getNextSibling();
            if (toVisitAst == null) {
                if (currentNode.getParent().equals(aSubTreeRootAst)) {
                    break;
                }
                currentNode = currentNode.getParent();
            }
        }
        currentNode = toVisitAst;
        return currentNode;
    }

    /**
     * @param aAST method def to check.
     * @return true if we should check this method.
     */
    private boolean isCheckedMethod(DetailAST aAST)
    {
        final String methodName =
            aAST.findFirstToken(TokenTypes.IDENT).getText();
        return !mIgnoredMethodNames.contains(methodName);
    }

    /**
     * Set the list of illegal variable types.
     * @param aClassNames array of illegal variable types
     */
    public void setIllegalClassNames(String[] aClassNames)
    {
        mIllegalClassNames.clear();
        for (String name : aClassNames) {
            mIllegalClassNames.add(name);
        }
    }

    /**
     * Get the list of illegal variable types.
     * @return array of illegal variable types
     */
    public String[] getIllegalClassNames()
    {
        return mIllegalClassNames.toArray(
            new String[mIllegalClassNames.size()]);
    }

    /**
     * Set the list of ignore method names.
     * @param aMethodNames array of ignored method names
     */
    public void setIgnoredMethodNames(String[] aMethodNames)
    {
        mIgnoredMethodNames.clear();
        for (String element : aMethodNames) {
            mIgnoredMethodNames.add(element);
        }
    }

    /**
     * Get the list of ignored method names.
     * @return array of ignored method names
     */
    public String[] getIgnoredMethodNames()
    {
        return mIgnoredMethodNames.toArray(
            new String[mIgnoredMethodNames.size()]);
    }

    /**
     * Set the list of legal abstract class names.
     * @param aClassNames array of legal abstract class names
     */
    public void setLegalAbstractClassNames(String[] aClassNames)
    {
        mLegalAbstractClassNames.clear();
        for (String element : aClassNames) {
            mLegalAbstractClassNames.add(element);
        }
    }

    /**
     * Get the list of legal abstract class names.
     * @return array of legal abstract class names
     */
    public String[] getLegalAbstractClassNames()
    {
        return mLegalAbstractClassNames.toArray(
            new String[mLegalAbstractClassNames.size()]);
    }
}
