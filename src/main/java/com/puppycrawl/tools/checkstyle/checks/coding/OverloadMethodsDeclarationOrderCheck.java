///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks that overloaded methods are grouped together. Overloaded methods have the same
 * name but different signatures where the signature can differ by the number of
 * input parameters or type of input parameters or both.
 * </p>
 * <ul>
 * <li>
 * Property {@code modifierGroups} - custom overload groups,
 * allows for the definitions of multiple overloading method groups.
 * Type is {@code java.util.regex.Pattern[]}.
 * Default value is {@code ""}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="OverloadMethodsDeclarationOrder"/&gt;
 * </pre>
 * <p>
 * Example of incorrect grouping of overloaded methods:
 * </p>
 * <pre>
 * public void foo(int i) {} // OK
 * public void foo(String s) {} // OK
 * public void notFoo() {} // Violation, should be after 'foo(int i, String s)'
 * public void foo(String s, int i) {} // OK
 * </pre>
 * <p>
 * Example of correct grouping of overloaded methods:
 * </p>
 * <pre>
 * public void foo(int i) {} // OK
 * public void foo(String s) {} // OK
 * public void foo(String s, int i) {} // OK
 * public void foo(int i, String s) {} // OK
 * public void notFoo() {} // OK
 * </pre>
 * <p>
 * To configure the check to group static and private methods separately within their own
 * individual groups
 * </p>
 * <pre>
 * &lt;module name="OverloadMethodsDeclarationOrder"&gt;
 *   &lt;property name="modifierGroups" value="static, private"&gt;&lt;/property&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example of code with violation
 * </p>
 * <pre>
 * public void foo(int i) {} // OK
 * public void foo(String s) {} // OK
 * public void notFoo() {} // OK
 * private void foo(boolean b) {} // OK
 * protected void foo(String s, int i) {} // Violation, should be after 'foo(String s)'
 * private void foo(byte b) {} // Violation, should be after 'foo(boolean b)'
 * private static void foo(String s) {} // OK
 * private static void notFoo(String s) {} // Violation, should be after 'static foo(int i)'
 * private static void foo(int i) {} // OK
 * </pre>
 * <p>
 * Example of compliant code
 * </p>
 * <pre>
 * public void foo(int i) {} // OK
 * public void foo(String s) {} // OK
 * public void notFoo() {} // OK
 * private void foo(boolean b) {} // OK
 * private void foo(String s, int i) {} // OK
 * public void notFoo2() {} // OK
 * private static void foo(String s) {} // OK
 * private static void foo(String s, int i) {} // OK
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code overload.methods.declaration}
 * </li>
 * </ul>
 *
 * @since 5.8
 */
@FileStatefulCheck
public class OverloadMethodsDeclarationOrderCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "overload.methods.declaration";

    /**
     * A match all pattern for the DEFAULT_OVERLOAD_GROUP.
     */
    private static final Pattern DEFAULT_MODIFIER_GROUP_PATTERN = Pattern.compile(".*");

    /**
     * Empty array of pattern type needed to initialize check.
     */
    private static final Pattern[] EMPTY_PATTERN_ARRAY = new Pattern[0];

    /**
     * String used as separator between the regex pattern that matches a method's modifiers
     * and the name of the method.
     */
    private static final String SEPARATOR = ":";

    /**
     * The abstract modifier.
     */
    private static final String ABSTRACT_MODIFIER = "abstract";

    /**
     * The public modifier.
     */
    private static final String PUBLIC_MODIFIER = "public";

    /**
     * The package modifier.
     */
    private static final String PACKAGE_MODIFIER = "package";

    /**
     * The protected modifier.
     */
    private static final String PROTECTED_MODIFIER = "protected";

    /**
     * The private modifier.
     */
    private static final String PRIVATE_MODIFIER = "private";

    /**
     * The set of valid scope modifiers.
     */
    private static final Set<String> SCOPE_MODIFIERS =
        Set.of(PUBLIC_MODIFIER, PROTECTED_MODIFIER, PRIVATE_MODIFIER);

    /**
     * Custom overload groups, allows for the definitions of multiple overloading method groups.
     */
    private Pattern[] modifierGroups = EMPTY_PATTERN_ARRAY;

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.OBJBLOCK,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final int parentType = ast.getParent().getType();

        final int[] tokenTypes = {
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.LITERAL_NEW,
            TokenTypes.RECORD_DEF,
        };

        if (TokenUtil.isOfType(parentType, tokenTypes)) {
            checkOverloadMethodsGrouping(ast);
        }
    }

    /**
     * Setter to custom overload groups, allows for the definitions of multiple overloading
     * method groups.
     *
     * @param modifierGroupRegex the array of regex patterns
     */
    public void setModifierGroups(String modifierGroupRegex) {
        final String[] split = modifierGroupRegex.split(",");
        modifierGroups = new Pattern[split.length];
        for (int index = 0; index < split.length; index++) {
            final String trimmed = split[index].trim();
            final Pattern pattern = Pattern.compile(trimmed);
            modifierGroups[index] = pattern;
        }
    }

    /**
     * Checks that if overload methods are grouped together they should not be
     * separated from each other.
     *
     * @param objectBlock is a class, interface or enum object block.
     */
    private void checkOverloadMethodsGrouping(DetailAST objectBlock) {
        final int allowedDistance = 1;
        final Map<String, IndexAndLine> knownMethods = new HashMap<>();
        DetailAST currentToken = objectBlock.getFirstChild();

        int currentIndex = 0;
        while (currentToken != null) {
            if (currentToken.getType() == TokenTypes.METHOD_DEF) {
                currentIndex++;
                final String methodKey = extractMethodDescription(currentToken);
                final IndexAndLine indexAndLine =
                    new IndexAndLine(currentIndex, currentToken.getLineNo());
                final IndexAndLine previousMeta = knownMethods.put(methodKey, indexAndLine);
                if (previousMeta != null
                    && currentIndex - previousMeta.getIndex() > allowedDistance) {
                    final int previousLineWithOverloadMethod = previousMeta.getLineNo();
                    log(currentToken, MSG_KEY, previousLineWithOverloadMethod);
                }
            }
            currentToken = currentToken.getNextSibling();
        }
    }

    /**
     * Compare the modifiers on a given method with the {@code modifierGroups} and find the
     * first match to create a unique identifier for the group and method name combination.
     *
     * @param currentToken assumed to be a method
     * @return string that uniquely represents the {@code modifierGroup} and method name
     */
    private String extractMethodDescription(DetailAST currentToken) {
        final DetailAST method = currentToken.findFirstToken(TokenTypes.IDENT);
        final String methodName = method.getText();

        String descriptor = DEFAULT_MODIFIER_GROUP_PATTERN + SEPARATOR + methodName;
        final String methodModifiers = extractModifiersString(currentToken);
        for (Pattern pattern : modifierGroups) {
            final Matcher matcher = pattern.matcher(methodModifiers);
            if (matcher.find()) {
                // method belongs to the first overload group which matches its modifiers
                descriptor = pattern.pattern() + SEPARATOR + methodName;
                break;
            }
        }
        return descriptor;
    }

    /**
     * Extract the modifier(s) of a method definition into a string.
     *
     * @param token expected to be TokenType.METHOD_DEF .
     * @return modifier string for {@code token} with 'package' scope added if needed
     */
    private static String extractModifiersString(final DetailAST token) {
        final List<String> collectedModifiers = getModifiers(token);
        final boolean isInsideInterface =
            token.getParent().getParent().getType() == TokenTypes.INTERFACE_DEF;

        if (collectedModifiers.stream().noneMatch(SCOPE_MODIFIERS::contains)) {
            String defaultModifier = PACKAGE_MODIFIER;
            if (isInsideInterface) {
                defaultModifier = PUBLIC_MODIFIER;
            }
            collectedModifiers.add(0, defaultModifier);
        }

        // methods in interfaces can only have public, abstract, default, private,
        // static modifiers. abstract is only valid when used with just the public
        // modifier, so simply add it to the end of the list if it is missing
        if (isInsideInterface && missingAbstractModifier(collectedModifiers)) {
            collectedModifiers.add(ABSTRACT_MODIFIER);
        }

        return String.join(" ", collectedModifiers);
    }

    /**
     * Get the modifiers associated with a method.
     *
     * @param token the method token
     * @return a list of modifiers found on the method, never empty
     */
    private static List<String> getModifiers(final DetailAST token) {
        final List<String> collectedModifiers = new LinkedList<>();
        final DetailAST modifiersAst = token.findFirstToken(TokenTypes.MODIFIERS);
        DetailAST modifier = modifiersAst.getFirstChild();
        while (modifier != null) {
            // skipping annotations
            if (modifier.getType() != TokenTypes.ANNOTATION) {
                collectedModifiers.add(modifier.getText());
            }
            // advance element in loop
            modifier = modifier.getNextSibling();
        }
        return collectedModifiers;
    }

    /**
     * Determine if the {@code abstract} modifier is missing from the list of modifiers. This
     * method is only assumed to be called on a collection of modifiers describing a method
     * within an interface. {@code abstract} is considered missing if the current list of modifiers
     * has only one element, and that element is the literal string {@code public}. Based on the
     * <a href="https://docs.oracle.com/javase/specs/jls/se9/html/jls-9.html#jls-9.4">JLS</a>
     * this is the only valid combination use of the {@code abstract} modifier when inside of
     * an interface.
     *
     * @param modifiers the list of modifiers for the method
     * @return {@code true} if {@code abstract} should be added to the modifier list
     */
    private static boolean missingAbstractModifier(final List<String> modifiers) {
        return modifiers.size() == 1 && PUBLIC_MODIFIER.equals(modifiers.get(0));
    }

    /**
     * Stores the {@code index} of a method in a class and the {@code lineNo}
     * on which the method appears.
     */
    private static final class IndexAndLine {
        /**
         * The index of method within the containing class.
         */
        private final int index;

        /**
         * The line number on which the method appears in the class.
         */
        private final int lineNo;

        /**
         * Create a new composite tuple containing the {@code index} and {@code lineNo}
         * of a method within a class.
         *
         * @param index the index of the method within the class
         * @param lineNo the line number of the method in the class
         */
        private IndexAndLine(int index, int lineNo) {
            this.index = index;
            this.lineNo = lineNo;
        }

        /**
         * Getter for the index.
         *
         * @return the method's index
         */
        public int getIndex() {
            return index;
        }

        /**
         * Getter for the line number.
         *
         * @return the method's line number
         */
        public int getLineNo() {
            return lineNo;
        }
    }
}
