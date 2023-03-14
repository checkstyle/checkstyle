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
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks that overloaded methods are grouped together. Overloaded methods have the same
 * name but different signatures where the signature can differ by the number of
 * input parameters or type of input parameters or both.
 * </p>
 * <ul>
 * <li>
 * Property {@code modifierGroups} - control how different types of overloaded methods
 * can be grouped by their modifiers.
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
 * public void foo() {} // OK
 * public final void foo(int i) {} // OK
 * void notFoo() {} // OK
 * public static void foo(long l) {} // Violation, should be after 'foo(int i)'
 * protected void foo(String s) {} // OK
 * void foo(byte b) {} // OK
 * private static void foo(String s, int i) { } // OK
 * public void foo(String a, String b) {} // OK
 * private void notFoo(int i) {} // Violation, should be after 'notFoo()'
 * public final void foo(long x, long y) {} // Violation, should be after 'foo(String a, String b)'
 * </pre>
 * <p>
 * Example of correct grouping of overloaded methods:
 * </p>
 * <pre>
 * public void foo(int i) {} // OK
 * void foo(String s) {} // OK
 * private void foo(String s, int i) {} // OK
 * public void foo(int i, String s) {} // OK
 * public void notFoo() {} // OK
 * private void notFoo(int x) {} // OK
 * </pre>
 * <p>
 * To configure the check to group static and private methods separately within their own
 * individual groups
 * </p>
 * <pre>
 * &lt;module name="OverloadMethodsDeclarationOrder"&gt;
 *   &lt;property name="modifierGroups" value=".*static.*, ^public .*, (protected|package),
 *   private"&gt;&lt;/property&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example of code with violation
 * </p>
 * <pre>
 * public void foo() {} // OK
 * public final void foo(int i) {} // OK
 * void notFoo() {} // OK
 * public static void foo(long l) {} // OK
 * protected void foo(String s) {} // OK
 * void foo(byte b) {} // OK
 * private static void foo(String s, int i) { } // Violation, should be after 'foo(long l)'
 * public void foo(String a, String b) {} // Violation, should be after 'foo()'
 * private void notFoo(int i) {} // OK
 * public final void foo(long x, long y) {} // Violation, should be after 'foo(int i)'
 * </pre>
 * <p>
 * Example of compliant code
 * </p>
 * <pre>
 * public void foo() {} // OK
 * public void foo(String a, String b) {} // OK
 * public final void foo(int i) {} // OK
 * public final void foo(long x, long y) {} // OK
 * void notFoo() {} // OK
 * public static void foo(long l) {} // OK
 * private static void foo(String s, int i) { } // OK
 * protected void foo(String s) {} // OK
 * void foo(byte b) {} // OK
 * private void notFoo(int i) {} // OK
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
     * String used as separator between the regex pattern that matches a method's modifiers
     * and the name of the method.
     */
    private static final String SEPARATOR = ":";

    /**
     * The abstract modifier.
     */
    private static final String ABSTRACT_MODIFIER = "abstract";

    /**
     * Control how different types of overloaded methods can be grouped by their modifiers.
     */
    private Pattern[] modifierGroups = new Pattern[0];

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
     * Setter to control how different types of overloaded methods can be grouped by their
     * modifiers.
     *
     * @param modifierGroupRegex the array of regex patterns
     */
    public void setModifierGroups(String... modifierGroupRegex) {
        modifierGroups = new Pattern[modifierGroupRegex.length];
        for (int index = 0; index < modifierGroupRegex.length; index++) {
            modifierGroups[index] = CommonUtil.createPattern(modifierGroupRegex[index]);
        }
    }

    /**
     * Checks that if overload methods are grouped together they should not be
     * separated from each other.
     *
     * @param objectBlock
     *        is a class, interface or enum object block.
     */
    private void checkOverloadMethodsGrouping(DetailAST objectBlock) {
        final int allowedDistance = 1;
        final Map<String, IndexAndLine> knownMethods = new HashMap<>();
        DetailAST currentToken = objectBlock.getFirstChild();

        int currentIndex = 0;
        while (currentToken != null) {
            if (currentToken.getType() == TokenTypes.METHOD_DEF) {
                currentIndex++;
                final int modifierGroupIndex = findModifierGroupPatternIndex(currentToken);
                final String methodKey =
                    String.format(Locale.ROOT, "%d%s%s", modifierGroupIndex, SEPARATOR,
                                  currentToken.findFirstToken(TokenTypes.IDENT).getText());
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
     * Get the index of the first matching {@code modifierGroup} for the modifiers on the method.
     *
     * @param currentToken assumed to be a method
     * @return index of the first modifier group that matches the modifiers on the method, or -1
     *     if only the default group matches.
     */
    private int findModifierGroupPatternIndex(DetailAST currentToken) {
        final String methodModifiers = getAllModifiersFrom(currentToken);
        int modifierIndex = -1;
        for (int index = 0; index < modifierGroups.length; index++) {
            final Pattern pattern = modifierGroups[index];
            final Matcher matcher = pattern.matcher(methodModifiers);
            if (matcher.find()) {
                modifierIndex = index;
                break;
            }
        }
        return modifierIndex;
    }

    /**
     * Extract the modifier(s) of a method definition into a string.
     *
     * @param token expected to be TokenType.METHOD_DEF .
     * @return modifier string for {@code token} with 'package' scope added if needed
     */
    private static String getAllModifiersFrom(final DetailAST token) {
        final List<String> collectedModifiers = getExplicitModifiersFrom(token);
        final String scope = ScopeUtil.getScope(token).getName();
        if (!collectedModifiers.contains(scope)) {
            collectedModifiers.add(0, scope);
        }

        // methods in interfaces can only have public, abstract, default, private,
        // and static modifiers. a method can be abstract if and only if the only
        // other modifier present is the public scope. If that is the case, add the implicit
        // abstract modifier to the list
        if (ScopeUtil.isInInterfaceBlock(token)
            && List.of(Scope.PUBLIC.getName()).equals(collectedModifiers)) {
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
    private static List<String> getExplicitModifiersFrom(final DetailAST token) {
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
