///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
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
 * public void notFoo() {} // Violation. Has to be after foo(int i, String s)
 * public void foo(String s, int i) {}
 * </pre>
 * <p>
 * Example of correct grouping of overloaded methods:
 * </p>
 * <pre>
 * public void foo(int i) {}
 * public void foo(String s) {}
 * public void foo(String s, int i) {}
 * public void foo(int i, String s) {}
 * public void notFoo() {}
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
 * protected void foo(String s, int i) {} // Violation. Has to be after foo(String s)
 * private void foo(byte b) {} // Violation. Has to be after foo(boolean b)
 * private static void foo(String s) {} // OK
 * private static void notFoo(String s) {} // Violation. Has to be after static foo(int i)
 * private static void foo(int i) {}
 * </pre>
 * <p>
 * Example of compliant code
 * </p>
 * <pre>
 * public void foo(int i) {}
 * public void foo(String s) {}
 * public void notFoo() {}
 * private void foo(boolean b) {}
 * private void foo(String s, int i) {}
 * public void notFoo2() {}
 * private static void foo(String s) {}
 * private static void foo(String s, int i) {}
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
@StatelessCheck
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
            if (CommonUtil.isBlank(trimmed)) {
                modifierGroups[index] = DEFAULT_MODIFIER_GROUP_PATTERN;
            }
            else {
                final Pattern pattern = Pattern.compile(trimmed);
                modifierGroups[index] = pattern;
            }
        }
    }

    /**
     * Checks if at least one group is defined.
     *
     * @return true if at least one custom group is defined.
     */
    private boolean isGroupsDefined() {
        return modifierGroups.length > 0;
    }

    /**
     * Checks that if overload methods are grouped together they should not be
     * separated from each other.
     *
     * @param objectBlock is a class, interface or enum object block.
     */
    private void checkOverloadMethodsGrouping(DetailAST objectBlock) {
        final int allowedDistance = 1;
        final Map<OverloadDescriptor, OverloadDescriptor> knownMethods = new HashMap<>();
        DetailAST currentToken = objectBlock.getFirstChild();

        int currentIndex = 0;
        while (currentToken != null) {
            if (currentToken.getType() == TokenTypes.METHOD_DEF) {
                currentIndex++;
                final OverloadDescriptor methodKey =
                    extractMethodDescription(currentToken, currentIndex);
                final OverloadDescriptor previous = knownMethods.put(methodKey, methodKey);
                if (previous != null) {
                    final int previousIndex = previous.getIndex();
                    if (currentIndex - previousIndex > allowedDistance) {
                        final int previousLineWithOverloadMethod = previous.getAst().getLineNo();
                        log(methodKey.getAst(), MSG_KEY, previousLineWithOverloadMethod);
                        knownMethods.put(previous, previous);
                    }
                }
            }
            currentToken = currentToken.getNextSibling();
        }
    }

    /**
     * Get the currentToken text and if needed prefixes
     * it according to if the method is static or instance.
     *
     * @param currentToken assumed to be a method
     * @param index        the index of the method in the file relative to
     *                     its own modifier group
     * @return MethodKey instance to which the method belongs
     */
    private OverloadDescriptor extractMethodDescription(DetailAST currentToken, int index) {
        final DetailAST method = currentToken.findFirstToken(TokenTypes.IDENT);
        final String methodName = method.getText();

        OverloadDescriptor overloadDescriptor = null;

        // fast path
        if (!isGroupsDefined()) {
            overloadDescriptor = new OverloadDescriptor(DEFAULT_MODIFIER_GROUP_PATTERN,
                                                        methodName, currentToken, index);
        }

        if (overloadDescriptor == null) {
            final String methodModifiers = extractModifiersString(currentToken);
            for (Pattern pattern : modifierGroups) {
                final Matcher matcher = pattern.matcher(methodModifiers);
                if (matcher.find()) {
                    // method belongs to the first overload group which matches its modifiers
                    overloadDescriptor =
                        new OverloadDescriptor(pattern, methodName, currentToken, index);
                    break;
                }
            }
        }

        if (overloadDescriptor == null) {
            // no custom group found, overloaded method added to the default, match all, group
            overloadDescriptor =
                new OverloadDescriptor(DEFAULT_MODIFIER_GROUP_PATTERN, methodName,
                                       currentToken, index);
        }

        return overloadDescriptor;
    }

    /**
     * Extract the modifier(s) of a method definition into a string.
     *
     * @param token expected to be TokenType.METHOD_DEF .
     * @return modifier string for {@code token} with 'package' scope added if needed
     */
    private static String extractModifiersString(final DetailAST token) {
        final DetailAST modifiersAst = token.findFirstToken(TokenTypes.MODIFIERS);
        final List<String> collectedModifiers = new LinkedList<>();
        DetailAST modifier = modifiersAst.getFirstChild();
        boolean foundScopeModifier = false;
        while (modifier != null) {
            if (modifier.getType() != TokenTypes.ANNOTATION) {
                // skipping annotations

                final String currModifierText = modifier.getText();
                if (!foundScopeModifier) {
                    // no need to test again if scope modifier already found
                    foundScopeModifier = isScopeModifier(currModifierText);
                }

                collectedModifiers.add(currModifierText);
            }

            // advance element in loop
            modifier = modifier.getNextSibling();
        }

        if (!foundScopeModifier) {
            // prepend string 'package' to mark default scope.
            // the package scope modifier is placed at the beginning to conform
            // with modifiers order as suggested in sections 8.1.1, 8.3.1 and 8.4.3 of the JLS
            collectedModifiers.add(0, "package");
        }

        return String.join(" ", collectedModifiers);
    }

    /**
     * Check if a string is one of the standard java scope modifiers.
     *
     * @param modifier the modifier to test
     * @return true of matches either of the strings 'public', 'protected', 'private'.
     */
    private static boolean isScopeModifier(String modifier) {
        return "public".equals(modifier)
            || "protected".equals(modifier)
            || "private".equals(modifier);
    }

    /**
     * Holds information about an overload method, the group it belongs to
     * and where it was found.
     * <br> Note: {@code index} and {@code lineNo} do not participate
     * in {@link #equals(Object)} or {@link #hashCode()}.
     */
    static final class OverloadDescriptor {
        /**
         * Overload group definition.
         */
        private final Pattern pattern;

        /**
         * Method name.
         */
        private final String methodName;

        /**
         * Note: does not participate in {@link #equals(Object)} or {@link #hashCode()}.
         */
        private final int index;

        /**
         * Note: does not participate in {@link #equals(Object)} or {@link #hashCode()}.
         */
        private final DetailAST ast;

        /**
         * A container class to represent a regex pattern and the method metadata
         * that it matches the modifiers for.
         *
         * @param pattern    the pattern which matched the modifiers.
         * @param methodName name of method, may not be null.
         * @param index      order of appearance in overload group.
         * @param ast        the matching ast of the method
         */
        OverloadDescriptor(Pattern pattern,
                           String methodName, DetailAST ast, int index) {
            this.pattern =
                Objects.requireNonNull(pattern, "pattern may not be null");
            this.methodName = Objects.requireNonNull(methodName, "methodName may not be null");
            this.ast = ast;
            this.index = index;
        }

        /**
         * Note: does not participate in {@link #equals(Object)} or {@link #hashCode()}.
         *
         * @return method index in block
         */
        public int getIndex() {
            return index;
        }

        /**
         * Note: does not participate in {@link #equals(Object)} or {@link #hashCode()}.
         *
         * @return method ast
         */
        public DetailAST getAst() {
            return ast;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            final OverloadDescriptor other = (OverloadDescriptor) o;
            return Objects.equals(pattern, other.pattern)
                && Objects.equals(methodName, other.methodName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(pattern, methodName);
        }
    }
}
