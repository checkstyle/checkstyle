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

package com.puppycrawl.tools.checkstyle.checks.naming;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Validates abbreviations (consecutive capital letters) length in
 * identifier name, it also allows to enforce camel case naming. Please read more at
 * <a href="https://checkstyle.org/styleguides/google-java-style-20180523/javaguide.html#s5.3-camel-case">
 * Google Style Guide</a> to get to know how to avoid long abbreviations in names.
 * </p>
 * <p>
 * {@code allowedAbbreviationLength} specifies how many consecutive capital letters are
 * allowed in the identifier.
 * A value of <i>3</i> indicates that up to 4 consecutive capital letters are allowed,
 * one after the other, before a violation is printed. The identifier 'MyTEST' would be
 * allowed, but 'MyTESTS' would not be.
 * A value of <i>0</i> indicates that only 1 consecutive capital letter is allowed. This
 * is what should be used to enforce strict camel casing. The identifier 'MyTest' would
 * be allowed, but 'MyTEst' would not be.
 * </p>
 * <p>
 * {@code ignoreFinal}, {@code ignoreStatic}, and {@code ignoreStaticFinal}
 * control whether variables with the respective modifiers are to be ignored.
 * Note that a variable that is both static and final will always be considered under
 * {@code ignoreStaticFinal} only, regardless of the values of {@code ignoreFinal}
 * and {@code ignoreStatic}. So for example if {@code ignoreStatic} is true but
 * {@code ignoreStaticFinal} is false, then static final variables will not be ignored.
 * </p>
 * <ul>
 * <li>
 * Property {@code allowedAbbreviationLength} - Indicate the number of consecutive capital
 * letters allowed in targeted identifiers (abbreviations in the classes, interfaces, variables
 * and methods names, ... ).
 * Type is {@code int}.
 * Default value is {@code 3}.
 * </li>
 * <li>
 * Property {@code allowedAbbreviations} - Specify abbreviations that must be skipped for checking.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code ""}.
 * </li>
 * <li>
 * Property {@code ignoreFinal} - Allow to skip variables with {@code final} modifier.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code ignoreStatic} - Allow to skip variables with {@code static} modifier.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code ignoreStaticFinal} - Allow to skip variables with both {@code static} and
 * {@code final} modifiers.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code ignoreOverriddenMethods} - Allow to ignore methods tagged with {@code @Override}
 * annotation (that usually mean inherited name).
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CLASS_DEF">
 * CLASS_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INTERFACE_DEF">
 * INTERFACE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_DEF">
 * ENUM_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ANNOTATION_DEF">
 * ANNOTATION_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ANNOTATION_FIELD_DEF">
 * ANNOTATION_FIELD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#PARAMETER_DEF">
 * PARAMETER_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#VARIABLE_DEF">
 * VARIABLE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#PATTERN_VARIABLE_DEF">
 * PATTERN_VARIABLE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RECORD_DEF">
 * RECORD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RECORD_COMPONENT_DEF">
 * RECORD_COMPONENT_DEF</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="AbbreviationAsWordInName"/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public class MyClass extends SuperClass { // OK, camel case
 *   int CURRENT_COUNTER; // violation, at most 4 consecutive capital letters allowed
 *   static int GLOBAL_COUNTER; // OK, static is ignored
 *   final Set&lt;String&gt; stringsFOUND = new HashSet&lt;&gt;(); // OK, final is ignored
 *
 *   &#64;Override
 *   void printCOUNTER() { // OK, overridden method is ignored
 *     System.out.println(CURRENT_COUNTER); // OK, only definitions are checked
 *   }
 *
 *   void incrementCOUNTER() { // violation, at most 4 consecutive capital letters allowed
 *     CURRENT_COUNTER++; // OK, only definitions are checked
 *   }
 *
 *   static void incrementGLOBAL() { // violation, static method is not ignored
 *     GLOBAL_COUNTER++; // OK, only definitions are checked
 *   }
 *
 * }
 * </pre>
 * <p>
 * To configure to include static variables and methods tagged with
 * {@code @Override} annotation.
 * </p>
 * <p>Configuration:</p>
 * <pre>
 * &lt;module name="AbbreviationAsWordInName"&gt;
 *   &lt;property name="ignoreStatic" value="false"/&gt;
 *   &lt;property name="ignoreOverriddenMethods" value="false"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * public class MyClass extends SuperClass { // OK, camel case
 *   int CURRENT_COUNTER; // violation, at most 4 consecutive capital letters allowed
 *   static int GLOBAL_COUNTER; // violation, static is not ignored
 *   final Set&lt;String&gt; stringsFOUND = new HashSet&lt;&gt;(); // OK, final is ignored
 *
 *   &#64;Override
 *   void printCOUNTER() { // violation, overridden method is not ignored
 *     System.out.println(CURRENT_COUNTER); // OK, only definitions are checked
 *   }
 *
 *   void incrementCOUNTER() { // violation, at most 4 consecutive capital letters allowed
 *     CURRENT_COUNTER++; // OK, only definitions are checked
 *   }
 *
 *   static void incrementGLOBAL() { // violation, at most 4 consecutive capital letters allowed
 *     GLOBAL_COUNTER++; // OK, only definitions are checked
 *   }
 *
 * }
 * </pre>
 * <p>
 * To configure to check all variables and identifiers
 * (including ones with the static modifier) and enforce
 * no abbreviations (essentially camel case) except for
 * words like 'XML' and 'URL'.
 * </p>
 * <p>Configuration:</p>
 * <pre>
 * &lt;module name="AbbreviationAsWordInName"&gt;
 *   &lt;property name="tokens" value="VARIABLE_DEF,CLASS_DEF"/&gt;
 *   &lt;property name="ignoreStatic" value="false"/&gt;
 *   &lt;property name="allowedAbbreviationLength" value="0"/&gt;
 *   &lt;property name="allowedAbbreviations" value="XML,URL,O"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * public class MyClass { // OK
 *   int firstNum; // OK
 *   int secondNUM; // violation, it allowed only 1 consecutive capital letter
 *   static int thirdNum; // OK, the static modifier would be checked
 *   static int fourthNUm; // violation, the static modifier would be checked,
 *                         // and only 1 consecutive capital letter is allowed
 *   String firstXML; // OK, XML abbreviation is allowed
 *   String firstURL; // OK, URL abbreviation is allowed
 *   final int TOTAL = 5; // OK, final is ignored
 *   static final int LIMIT = 10; // OK, static final is ignored
 *   void newOAuth2Client() {} // OK, O abbreviation is allowed
 *   void OAuth2() {} // OK, O abbreviation is allowed
 *   void OAUth2() {} // violation, OA abbreviation is not allowed
 *                    // split occurs as 'OA', 'Uth2'
 *
 * }
 * </pre>
 * <p>
 * To configure to check variables, excluding fields with
 * the static modifier, and allow abbreviations up to 2
 * consecutive capital letters ignoring the longer word 'CSV'.
 * </p>
 * <p>Configuration:</p>
 * <pre>
 * &lt;module name="AbbreviationAsWordInName"&gt;
 *   &lt;property name="tokens" value="VARIABLE_DEF"/&gt;
 *   &lt;property name="ignoreStatic" value="true"/&gt;
 *   &lt;property name="allowedAbbreviationLength" value="1"/&gt;
 *   &lt;property name="allowedAbbreviations" value="CSV"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * public class MyClass { // OK, ignore checking the class name
 *   int firstNum; // OK, abbreviation "N" is of allowed length 1
 *   int secondNUm; // OK
 *   int secondMYNum; // violation, found "MYN" but only
 *                    // 2 consecutive capital letters are allowed
 *   int thirdNUM; // violation, found "NUM" but it is allowed
 *                 // only 2 consecutive capital letters
 *   static int fourthNUM; // OK, variables with static modifier
 *                         // would be ignored
 *   String firstCSV; // OK, CSV abbreviation is allowed
 *   String firstXML; // violation, XML abbreviation is not allowed
 *   final int TOTAL = 5; // OK, final is ignored
 *   static final int LIMIT = 10; // OK, static final is ignored
 * }
 * </pre>
 * <p>
 * To configure to check variables, enforcing no abbreviations
 * except for variables that are both static and final.
 * </p>
 * <p>Configuration:</p>
 * <pre>
 * &lt;module name="AbbreviationAsWordInName"&gt;
 *     &lt;property name="tokens" value="VARIABLE_DEF"/&gt;
 *     &lt;property name="ignoreFinal" value="false"/&gt;
 *     &lt;property name="ignoreStatic" value="false"/&gt;
 *     &lt;property name="ignoreStaticFinal" value="true"/&gt;
 *     &lt;property name="allowedAbbreviationLength" value="0"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * public class MyClass {
 *     public int counterXYZ = 1;                // violation
 *     public final int customerID = 2;          // violation
 *     public static int nextID = 3;             // violation
 *     public static final int MAX_ALLOWED = 4;  // OK, ignored
 * }
 * </pre>
 * <p>
 * To configure to check variables, enforcing no abbreviations
 * and ignoring static (but non-final) variables only.
 * </p>
 * <p>Configuration:</p>
 * <pre>
 * &lt;module name="AbbreviationAsWordInName"&gt;
 *     &lt;property name="tokens" value="VARIABLE_DEF"/&gt;
 *     &lt;property name="ignoreFinal" value="false"/&gt;
 *     &lt;property name="ignoreStatic" value="true"/&gt;
 *     &lt;property name="ignoreStaticFinal" value="false"/&gt;
 *     &lt;property name="allowedAbbreviationLength" value="0"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * public class MyClass {
 *     public int counterXYZ = 1;                // violation
 *     public final int customerID = 2;          // violation
 *     public static int nextID = 3;             // OK, ignored
 *     public static final int MAX_ALLOWED = 4;  // violation
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code abbreviation.as.word}
 * </li>
 * </ul>
 *
 * @since 5.8
 */
@StatelessCheck
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
     * Indicate the number of consecutive capital letters allowed in
     * targeted identifiers (abbreviations in the classes, interfaces, variables
     * and methods names, ... ).
     */
    private int allowedAbbreviationLength =
            DEFAULT_ALLOWED_ABBREVIATIONS_LENGTH;

    /**
     * Specify abbreviations that must be skipped for checking.
     */
    private Set<String> allowedAbbreviations = new HashSet<>();

    /** Allow to skip variables with {@code final} modifier. */
    private boolean ignoreFinal = true;

    /** Allow to skip variables with {@code static} modifier. */
    private boolean ignoreStatic = true;

    /** Allow to skip variables with both {@code static} and {@code final} modifiers. */
    private boolean ignoreStaticFinal = true;

    /**
     * Allow to ignore methods tagged with {@code @Override} annotation (that
     * usually mean inherited name).
     */
    private boolean ignoreOverriddenMethods = true;

    /**
     * Setter to allow to skip variables with {@code final} modifier.
     *
     * @param ignoreFinal
     *        Defines if ignore variables with 'final' modifier or not.
     */
    public void setIgnoreFinal(boolean ignoreFinal) {
        this.ignoreFinal = ignoreFinal;
    }

    /**
     * Setter to allow to skip variables with {@code static} modifier.
     *
     * @param ignoreStatic
     *        Defines if ignore variables with 'static' modifier or not.
     */
    public void setIgnoreStatic(boolean ignoreStatic) {
        this.ignoreStatic = ignoreStatic;
    }

    /**
     * Setter to allow to skip variables with both {@code static} and {@code final} modifiers.
     *
     * @param ignoreStaticFinal
     *        Defines if ignore variables with both 'static' and 'final' modifiers or not.
     */
    public void setIgnoreStaticFinal(boolean ignoreStaticFinal) {
        this.ignoreStaticFinal = ignoreStaticFinal;
    }

    /**
     * Setter to allow to ignore methods tagged with {@code @Override}
     * annotation (that usually mean inherited name).
     *
     * @param ignoreOverriddenMethods
     *        Defines if ignore methods with "@Override" annotation or not.
     */
    public void setIgnoreOverriddenMethods(boolean ignoreOverriddenMethods) {
        this.ignoreOverriddenMethods = ignoreOverriddenMethods;
    }

    /**
     * Setter to indicate the number of consecutive capital letters allowed
     * in targeted identifiers (abbreviations in the classes, interfaces,
     * variables and methods names, ... ).
     *
     * @param allowedAbbreviationLength amount of allowed capital letters in
     *        abbreviation.
     */
    public void setAllowedAbbreviationLength(int allowedAbbreviationLength) {
        this.allowedAbbreviationLength = allowedAbbreviationLength;
    }

    /**
     * Setter to specify abbreviations that must be skipped for checking.
     *
     * @param allowedAbbreviations abbreviations that must be
     *        skipped from checking.
     */
    public void setAllowedAbbreviations(String... allowedAbbreviations) {
        if (allowedAbbreviations != null) {
            this.allowedAbbreviations =
                Arrays.stream(allowedAbbreviations).collect(Collectors.toSet());
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
            TokenTypes.PATTERN_VARIABLE_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.RECORD_COMPONENT_DEF,
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
            TokenTypes.PATTERN_VARIABLE_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.RECORD_COMPONENT_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (!isIgnoreSituation(ast)) {
            final DetailAST nameAst = ast.findFirstToken(TokenTypes.IDENT);
            final String typeName = nameAst.getText();

            final String abbr = getDisallowedAbbreviation(typeName);
            if (abbr != null) {
                log(nameAst, MSG_KEY, typeName, allowedAbbreviationLength + 1);
            }
        }
    }

    /**
     * Checks if it is an ignore situation.
     *
     * @param ast input DetailAST node.
     * @return true if it is an ignore situation found for given input DetailAST
     *         node.
     */
    private boolean isIgnoreSituation(DetailAST ast) {
        final DetailAST modifiers = ast.getFirstChild();

        final boolean result;
        if (ast.getType() == TokenTypes.VARIABLE_DEF) {
            if (isInterfaceDeclaration(ast)) {
                // field declarations in interface are static/final
                result = ignoreStaticFinal;
            }
            else {
                result = hasIgnoredModifiers(modifiers);
            }
        }
        else if (ast.getType() == TokenTypes.METHOD_DEF) {
            result = ignoreOverriddenMethods && hasOverrideAnnotation(modifiers);
        }
        else {
            result = CheckUtil.isReceiverParameter(ast);
        }
        return result;
    }

    /**
     * Checks if a variable is to be ignored based on its modifiers.
     *
     * @param modifiers modifiers of the variable to be checked
     * @return true if there is a modifier to be ignored
     */
    private boolean hasIgnoredModifiers(DetailAST modifiers) {
        final boolean isStatic = modifiers.findFirstToken(TokenTypes.LITERAL_STATIC) != null;
        final boolean isFinal = modifiers.findFirstToken(TokenTypes.FINAL) != null;
        final boolean result;
        if (isStatic && isFinal) {
            result = ignoreStaticFinal;
        }
        else {
            result = ignoreStatic && isStatic || ignoreFinal && isFinal;
        }
        return result;
    }

    /**
     * Check that variable definition in interface or @interface definition.
     *
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
     *
     * @param methodModifiersAST
     *        A DetailAST nod is related to the given method modifiers
     *        (MODIFIERS type).
     * @return true if method has "@Override" annotation.
     */
    private static boolean hasOverrideAnnotation(DetailAST methodModifiersAST) {
        boolean result = false;
        for (DetailAST child : getChildren(methodModifiersAST)) {
            final DetailAST annotationIdent = child.findFirstToken(TokenTypes.IDENT);

            if (annotationIdent != null && "Override".equals(annotationIdent.getText())) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Gets the disallowed abbreviation contained in given String.
     *
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
                result = getAbbreviationIfIllegal(str, beginIndex, endIndex);
                if (result != null) {
                    break;
                }
                beginIndex = -1;
            }
        }
        // if abbreviation at the end of name (example: scaleX)
        if (abbrStarted) {
            final int endIndex = str.length() - 1;
            result = getAbbreviationIfIllegal(str, beginIndex, endIndex);
        }
        return result;
    }

    /**
     * Get Abbreviation if it is illegal, where {@code beginIndex} and {@code endIndex} are
     * inclusive indexes of a sequence of consecutive upper-case characters.
     *
     * @param str name
     * @param beginIndex begin index
     * @param endIndex end index
     * @return the abbreviation if it is bigger than required and not in the
     *         ignore list, otherwise {@code null}
     */
    private String getAbbreviationIfIllegal(String str, int beginIndex, int endIndex) {
        String result = null;
        final int abbrLength = endIndex - beginIndex;
        if (abbrLength > allowedAbbreviationLength) {
            final String abbr = getAbbreviation(str, beginIndex, endIndex);
            if (!allowedAbbreviations.contains(abbr)) {
                result = abbr;
            }
        }
        return result;
    }

    /**
     * Gets the abbreviation, where {@code beginIndex} and {@code endIndex} are
     * inclusive indexes of a sequence of consecutive upper-case characters.
     * <p>
     * The character at {@code endIndex} is only included in the abbreviation if
     * it is the last character in the string; otherwise it is usually the first
     * capital in the next word.
     * </p>
     * <p>
     * For example, {@code getAbbreviation("getXMLParser", 3, 6)} returns "XML"
     * (not "XMLP"), and so does {@code getAbbreviation("parseXML", 5, 7)}.
     * </p>
     *
     * @param str name
     * @param beginIndex begin index
     * @param endIndex end index
     * @return the specified abbreviation
     */
    private static String getAbbreviation(String str, int beginIndex, int endIndex) {
        final String result;
        if (endIndex == str.length() - 1) {
            result = str.substring(beginIndex);
        }
        else {
            result = str.substring(beginIndex, endIndex);
        }
        return result;
    }

    /**
     * Gets all the children which are one level below on the current DetailAST
     * parent node.
     *
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
