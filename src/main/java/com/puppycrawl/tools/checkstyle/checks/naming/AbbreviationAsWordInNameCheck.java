////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.naming;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * <p>
 * The Check validate abbreviations(consecutive capital letters) length in
 * identifier name, it also allows to enforce camel case naming. Please read more at
 * <a href="http://checkstyle.sourceforge.net/reports/google-java-style.html#s5.3-camel-case">
 * Google Style Guide</a> to get to know how to avoid long abbreviations in names.
 * </p>
 * <p>
 * Option {@code allowedAbbreviationLength} indicates on the allowed amount of capital
 * letters in abbreviations in the classes, interfaces,
 * variables and methods names. Default value is '3'.
 * </p>
 * <p>
 * Option {@code allowedAbbreviations} - list of abbreviations that
 * must be skipped for checking. Abbreviations should be separated by comma,
 * no spaces are allowed.
 * </p>
 * <p>
 * Option {@code ignoreFinal} allow to skip variables with {@code final} modifier.
 * Default value is {@code true}.
 * </p>
 * <p>
 * Option {@code ignoreStatic} allow to skip variables with {@code static} modifier.
 * Default value is {@code true}.
 * </p>
 * <p>
 * Option {@code ignoreOverriddenMethod} - Allows to
 * ignore methods tagged with {@code @Override} annotation
 * (that usually mean inherited name). Default value is {@code true}.
 * </p>
 * Default configuration
 * <pre>
 * &lt;module name="AbbreviationAsWordInName" /&gt;
 * </pre>
 * <p>
 * To configure to check variables and classes identifiers, do not ignore
 * variables with static modifier
 * and allow no abbreviations (enforce camel case phrase) but allow XML and URL abbreviations.
 * </p>
 * <pre>
 * &lt;module name="AbbreviationAsWordInName"&gt;
 *     &lt;property name="tokens" value="VARIABLE_DEF,CLASS_DEF"/&gt;
 *     &lt;property name="ignoreStatic" value="false"/&gt;
 *     &lt;property name="allowedAbbreviationLength" value="1"/&gt;
 *     &lt;property name="allowedAbbreviations" value="XML,URL"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author Roman Ivanov, Daniil Yaroslvtsev, Baratali Izmailov
 */
public class AbbreviationAsWordInNameCheck extends AbstractCheck {

    /**
     * Warning message key.
     */
    public static final String MSG_KEY = "abbreviation.as.word";

    /**
     * The default value of "allowedAbbreviationLength" option.
     */
    private static final int DEFAULT_ALLOWED_ABBREVIATIONS_LENGTH = 3;

    /**
     * Variable indicates on the allowed amount of capital letters in
     * abbreviations in the classes, interfaces, variables and methods names.
     */
    private int allowedAbbreviationLength =
            DEFAULT_ALLOWED_ABBREVIATIONS_LENGTH;

    /**
     * Set of allowed abbreviation to ignore in check.
     */
    private Set<String> allowedAbbreviations = new HashSet<>();

    /** Allows to ignore variables with 'final' modifier. */
    private boolean ignoreFinal = true;

    /** Allows to ignore variables with 'static' modifier. */
    private boolean ignoreStatic = true;

    /** Allows to ignore methods with '@Override' annotation. */
    private boolean ignoreOverriddenMethods = true;

    /**
     * Sets ignore option for variables with 'final' modifier.
     * @param ignoreFinal
     *        Defines if ignore variables with 'final' modifier or not.
     */
    public void setIgnoreFinal(boolean ignoreFinal) {
        this.ignoreFinal = ignoreFinal;
    }

    /**
     * Sets ignore option for variables with 'static' modifier.
     * @param ignoreStatic
     *        Defines if ignore variables with 'static' modifier or not.
     */
    public void setIgnoreStatic(boolean ignoreStatic) {
        this.ignoreStatic = ignoreStatic;
    }

    /**
     * Sets ignore option for methods with "@Override" annotation.
     * @param ignoreOverriddenMethods
     *        Defines if ignore methods with "@Override" annotation or not.
     */
    public void setIgnoreOverriddenMethods(boolean ignoreOverriddenMethods) {
        this.ignoreOverriddenMethods = ignoreOverriddenMethods;
    }

    /**
     * Allowed abbreviation length in names.
     * @param allowedAbbreviationLength
     *            amount of allowed capital letters in abbreviation.
     */
    public void setAllowedAbbreviationLength(int allowedAbbreviationLength) {
        this.allowedAbbreviationLength = allowedAbbreviationLength;
    }

    /**
     * Set a list of abbreviations that must be skipped for checking.
     * Abbreviations should be separated by comma, no spaces is allowed.
     * @param allowedAbbreviations
     *        an string of abbreviations that must be skipped from checking,
     *        each abbreviation separated by comma.
     */
    public void setAllowedAbbreviations(String... allowedAbbreviations) {
        if (allowedAbbreviations != null) {
            this.allowedAbbreviations = Sets.newHashSet(allowedAbbreviations);
        }
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.METHOD_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {

        if (!isIgnoreSituation(ast)) {

            final DetailAST nameAst = ast.findFirstToken(TokenTypes.IDENT);
            final String typeName = nameAst.getText();

            final String abbr = getDisallowedAbbreviation(typeName);
            if (abbr != null) {
                log(nameAst.getLineNo(), MSG_KEY, typeName, allowedAbbreviationLength);
            }
        }
    }

    /**
     * Checks if it is an ignore situation.
     * @param ast input DetailAST node.
     * @return true if it is an ignore situation found for given input DetailAST
     *         node.
     */
    private boolean isIgnoreSituation(DetailAST ast) {
        final DetailAST modifiers = ast.getFirstChild();

        boolean result = false;
        if (ast.getType() == TokenTypes.VARIABLE_DEF) {
            if ((ignoreFinal || ignoreStatic)
                    && isInterfaceDeclaration(ast)) {
                // field declarations in interface are static/final
                result = true;
            }
            else {
                result = ignoreFinal
                          && modifiers.branchContains(TokenTypes.FINAL)
                    || ignoreStatic
                        && modifiers.branchContains(TokenTypes.LITERAL_STATIC);
            }
        }
        else if (ast.getType() == TokenTypes.METHOD_DEF) {
            result = ignoreOverriddenMethods
                    && hasOverrideAnnotation(modifiers);
        }
        return result;
    }

    /**
     * Check that variable definition in interface or @interface definition.
     * @param variableDefAst variable definition.
     * @return true if variable definition(variableDefAst) is in interface
     *     or @interface definition.
     */
    private static boolean isInterfaceDeclaration(DetailAST variableDefAst) {
        boolean result = false;
        final DetailAST astBlock = variableDefAst.getParent();
        final DetailAST astParent2 = astBlock.getParent();

        if (astParent2.getType() == TokenTypes.INTERFACE_DEF
                || astParent2.getType() == TokenTypes.ANNOTATION_DEF) {
            result = true;
        }
        return result;
    }

    /**
     * Checks that the method has "@Override" annotation.
     * @param methodModifiersAST
     *        A DetailAST nod is related to the given method modifiers
     *        (MODIFIERS type).
     * @return true if method has "@Override" annotation.
     */
    private static boolean hasOverrideAnnotation(DetailAST methodModifiersAST) {
        boolean result = false;
        for (DetailAST child : getChildren(methodModifiersAST)) {
            if (child.getType() == TokenTypes.ANNOTATION) {
                final DetailAST annotationIdent = child.findFirstToken(TokenTypes.IDENT);

                if (annotationIdent != null && "Override".equals(annotationIdent.getText())) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Gets the disallowed abbreviation contained in given String.
     * @param str
     *        the given String.
     * @return the disallowed abbreviation contained in given String as a
     *         separate String.
     */
    private String getDisallowedAbbreviation(String str) {
        int beginIndex = 0;
        boolean abbrStarted = false;
        String result = null;

        for (int index = 0; index < str.length(); index++) {
            final char symbol = str.charAt(index);

            if (Character.isUpperCase(symbol)) {
                if (!abbrStarted) {
                    abbrStarted = true;
                    beginIndex = index;
                }
            }
            else if (abbrStarted) {
                abbrStarted = false;

                final int endIndex = index - 1;
                // -1 as a first capital is usually beginning of next word
                result = getAbbreviationIfIllegal(str, beginIndex, endIndex);
                if (result != null) {
                    break;
                }
                beginIndex = -1;
            }
        }
        // if abbreviation at the end of name and it is not single character (example: scaleX)
        if (abbrStarted && beginIndex != str.length() - 1) {
            final int endIndex = str.length();
            result = getAbbreviationIfIllegal(str, beginIndex, endIndex);
        }
        return result;
    }

    /**
     * Get Abbreviation if it is illegal.
     * @param str name
     * @param beginIndex begin index
     * @param endIndex end index
     * @return true is abbreviation is bigger that required and not in ignore list
     */
    private String getAbbreviationIfIllegal(String str, int beginIndex, int endIndex) {
        String result = null;
        final int abbrLength = endIndex - beginIndex;
        if (abbrLength > allowedAbbreviationLength) {
            final String abbr = str.substring(beginIndex, endIndex);
            if (!allowedAbbreviations.contains(abbr)) {
                result = abbr;
            }
        }
        return result;
    }

    /**
     * Gets all the children which are one level below on the current DetailAST
     * parent node.
     * @param node
     *        Current parent node.
     * @return The list of children one level below on the current parent node.
     */
    private static List<DetailAST> getChildren(final DetailAST node) {
        final List<DetailAST> result = new LinkedList<>();
        DetailAST curNode = node.getFirstChild();
        while (curNode != null) {
            result.add(curNode);
            curNode = curNode.getNextSibling();
        }
        return result;
    }

}
