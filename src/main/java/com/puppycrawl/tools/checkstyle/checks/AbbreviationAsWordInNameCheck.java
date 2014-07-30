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
package com.puppycrawl.tools.checkstyle.checks;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Check name of the targeted item to validate
 * abbreviations(capital letters) length in it.
 * </p>
 * <p>
 * Option targets- it's array of type forbidden annotation's target.
 * </p>
 * <p>
 * Option allowedAbbreviations - list of abbreviations that
 * must be skipped for checking.
 * </p>
 * <p>
 * Option ignoreFinal allow to skip variables with final declarations.
 * </p>
 * <p>
 * Option ignoreStatic allow to skip variables with static declarations.
 * </p>
 * <p>
 * Option ignoreOverriddenMethod - Allows to
 * ignore methods tagged with '@Override' annotation
 * (that usually mean inherited name).
 * </p>
 * <p>
 * Option allowedAbbreviationLength indicates on the allowed amount of capital
 * letters in abbreviations in the classes, interfaces,
 * variables and methods names. Default value is '3'.
 * </p>
 * <p>
 * To configure to check variables definitions forbid skipping
 * variables with static declarations
 * and allow 'NUMBER' abbreviation.
 * </p>
 * <pre>
 * &lt;module name="AbbreviationAsWordInName"&gt;
 *     &lt;property name="targets" value="VARIABLE_DEF"/&gt;
 *     &lt;property name="ignoreStatic" value="false"/&gt;
 *     &lt;property name="allowedAbbreviations" value="NUMBER"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author Roman Ivanov, Daniil Yaroslvtsev, Baratali Izmailov
 */
public class AbbreviationAsWordInNameCheck extends Check
{

    /**
     * Warning message key.
     */
    public static final String MSG_KEY = "abbreviation.as.word";

    /**
     * The default value of "allowedAbbreviationLength" option.
     */
    private static final int DEFAULT_ALLOWED_ABBREVIATIONS_LENGTH = 3;

    /**
     * mAnnotationTargets is array of type forbidden annotation's target.
     */
    private int[] mTargets = new int[0];

    /**
     * Variable indicates on the allowed amount of capital letters in
     * abbreviations in the classes, interfaces, variables and methods names.
     */
    private int mAllowedAbbreviationLength =
            DEFAULT_ALLOWED_ABBREVIATIONS_LENGTH;

    /**
     * Set of allowed abbreviation to ignore in check.
     */
    private Set<String> mAllowedAbbreviations = new HashSet<String>();

    /** Allows to ignore variables with 'final' modifier. */
    private boolean mIgnoreFinal = true;

    /** Allows to ignore variables with 'static' modifier. */
    private boolean mIgnoreStatic = true;

    /** Allows to ignore methods with '@Override' annotation. */
    private boolean mIgnoreOverriddenMethods = true;

    /**
     * Sets ignore option for variables with 'final' modifier.
     * @param aIgnoreFinal
     *        Defines if ignore variables with 'final' modifier or not.
     */
    public void setIgnoreFinal(boolean aIgnoreFinal)
    {
        this.mIgnoreFinal = aIgnoreFinal;
    }

    /**
     * Sets ignore option for variables with 'static' modifier.
     * @param aIgnoreStatic
     *        Defines if ignore variables with 'static' modifier or not.
     */
    public void setIgnoreStatic(boolean aIgnoreStatic)
    {
        this.mIgnoreStatic = aIgnoreStatic;
    }

    /**
     * Sets ignore option for methods with "@Override" annotation.
     * @param aIgnoreOverriddenMethods
     *        Defines if ignore methods with "@Override" annotation or not.
     */
    public void setIgnoreOverriddenMethods(boolean aIgnoreOverriddenMethods)
    {
        this.mIgnoreOverriddenMethods = aIgnoreOverriddenMethods;
    }

    /**
     * Targets for a Check.
     * @param aTargets - array of type's names
     */
    public void setTargets(String[] aTargets)
    {
        if (aTargets != null) {
            mTargets = new int[aTargets.length];
            for (int i = 0; i < aTargets.length; i++) {
                mTargets[i] = TokenTypes.getTokenId(aTargets[i]);
            }
        }
    }

    /**
     * Allowed abbreviation length in names.
     * @param aAllowedAbbreviationLength
     *            amount of allowed capital letters in abbreviation.
     */
    public void setAllowedAbbreviationLength(int aAllowedAbbreviationLength)
    {
        mAllowedAbbreviationLength = aAllowedAbbreviationLength;
    }

    /**
     * Set a list of abbreviations that must be skipped for checking.
     * Abbreviations should be separated by comma, no spaces is allowed.
     * @param aAllowedAbbreviations
     *        an string of abbreviations that must be skipped from checking,
     *        each abbreviation separated by comma.
     */
    public void setAllowedAbbreviations(String aAllowedAbbreviations)
    {
        if (aAllowedAbbreviations != null) {
            mAllowedAbbreviations = new HashSet<String>(
                    Arrays.asList(aAllowedAbbreviations.split(",")));
        }
    }

    @Override
    public int[] getDefaultTokens()
    {
        return mTargets;
    }

    @Override
    public void visitToken(DetailAST aAst)
    {

        if (!isIgnoreSituation(aAst)) {

            final DetailAST nameAst = aAst.findFirstToken(TokenTypes.IDENT);
            final String typeName = nameAst.getText();

            final String abbr = getDisallowedAbbreviation(typeName);
            if (abbr != null) {
                log(nameAst.getLineNo(), MSG_KEY, mAllowedAbbreviationLength);
            }
        }
    }

    /**
     * Checks if it is an ignore situation.
     * @param aAst input DetailAST node.
     * @return true if it is an ignore situation found for given input DetailAST
     *         node.
     */
    private boolean isIgnoreSituation(DetailAST aAst)
    {
        final DetailAST modifiers = aAst.getFirstChild();

        boolean result = false;
        if (aAst.getType() == TokenTypes.VARIABLE_DEF) {
            if ((mIgnoreFinal || mIgnoreStatic)
                    && isInterfaceDeclaration(aAst))
            {
                // field declarations in interface are static/final
                result = true;
            }
            else {
                result = (mIgnoreFinal
                          && modifiers.branchContains(TokenTypes.FINAL))
                    || (mIgnoreStatic
                        && modifiers.branchContains(TokenTypes.LITERAL_STATIC));
            }
        }
        else if (aAst.getType() == TokenTypes.METHOD_DEF) {
            result = mIgnoreOverriddenMethods
                    && hasOverrideAnnotation(modifiers);
        }
        return result;
    }

    /**
     * Check that variable definition in interface definition.
     * @param aVariableDefAst variable definition.
     * @return true if variable definition(aVaribaleDefAst) is in interface
     * definition.
     */
    private static boolean isInterfaceDeclaration(DetailAST aVariableDefAst)
    {
        boolean result = false;
        final DetailAST astBlock = aVariableDefAst.getParent();
        if (astBlock != null) {
            final DetailAST astParent2 = astBlock.getParent();
            if (astParent2 != null
                    && astParent2.getType() == TokenTypes.INTERFACE_DEF)
            {
                result = true;
            }
        }
        return result;
    }

    /**
     * Checks that the method has "@Override" annotation.
     * @param aMethodModifiersAST
     *        A DetailAST nod is related to the given method modifiers
     *        (MODIFIERS type).
     * @return true if method has "@Override" annotation.
     */
    private static boolean hasOverrideAnnotation(DetailAST aMethodModifiersAST)
    {
        boolean result = false;
        for (DetailAST child : getChildren(aMethodModifiersAST)) {
            if (child.getType() == TokenTypes.ANNOTATION) {
                final String annotationText =
                        child.findFirstToken(TokenTypes.IDENT).getText();
                if ("Override".equals(annotationText)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Gets the disallowed abbreviation contained in given String.
     * @param aString
     *        the given String.
     * @return the disallowed abbreviation contained in given String as a
     *         separate String.
     */
    private String getDisallowedAbbreviation(String aString)
    {
        int beginIndex = 0;
        boolean abbrStarted = false;
        String result = null;

        for (int index = 0; index < aString.length(); index++) {
            final char symbol = aString.charAt(index);

            if (Character.isUpperCase(symbol)) {
                if (!abbrStarted) {
                    abbrStarted = true;
                    beginIndex = index;
                }
            }
            else {
                if (abbrStarted) {
                    abbrStarted = false;

                    // -1 as a first capital is usually beginning of next word
                    final int endIndex = index - 1;
                    final int abbrLength = endIndex - beginIndex;
                    if (abbrLength > mAllowedAbbreviationLength) {
                        result = aString.substring(beginIndex, endIndex);
                        if (!mAllowedAbbreviations.contains(result)) {
                            break;
                        }
                        else {
                            result = null;
                        }
                    }
                    beginIndex = -1;
                }
            }
        }
        if (abbrStarted) {
            final int endIndex = aString.length();
            final int abbrLength = endIndex - beginIndex;
            if (abbrLength > 1 && abbrLength > mAllowedAbbreviationLength) {
                result = aString.substring(beginIndex, endIndex);
                if (mAllowedAbbreviations.contains(result)) {
                    result = null;
                }
            }
        }
        return result;
    }

    /**
     * Gets all the children which are one level below on the current DetailAST
     * parent node.
     * @param aNode
     *        Current parent node.
     * @return The list of children one level below on the current parent node.
     */
    private static List<DetailAST> getChildren(final DetailAST aNode)
    {
        final List<DetailAST> result = new LinkedList<DetailAST>();
        DetailAST curNode = aNode.getFirstChild();
        while (curNode != null) {
            result.add(curNode);
            curNode = curNode.getNextSibling();
        }
        return result;
    }

}
