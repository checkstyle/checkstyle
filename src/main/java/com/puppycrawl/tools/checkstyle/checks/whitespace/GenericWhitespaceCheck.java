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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import java.util.stream.IntStream;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CodePointUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks that the whitespace around the Generic tokens (angle brackets)
 * "&lt;" and "&gt;" are correct to the <i>typical</i> convention.
 * The convention is not configurable.
 * </p>
 * <p>
 * Left angle bracket ("&lt;"):
 * </p>
 * <ul>
 * <li> should be preceded with whitespace only
 *   in generic methods definitions.</li>
 * <li> should not be preceded with whitespace
 *   when it is preceded method name or constructor.</li>
 * <li> should not be preceded with whitespace when following type name.</li>
 * <li> should not be followed with whitespace in all cases.</li>
 * </ul>
 * <p>
 * Right angle bracket ("&gt;"):
 * </p>
 * <ul>
 * <li> should not be preceded with whitespace in all cases.</li>
 * <li> should be followed with whitespace in almost all cases,
 *   except diamond operators and when preceding method name or constructor.</li></ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;GenericWhitespace&quot;/&gt;
 * </pre>
 * <p>
 * Examples with correct spacing:
 * </p>
 * <pre>
 * // Generic methods definitions
 * public void &lt;K, V extends Number&gt; boolean foo(K, V) {}
 * // Generic type definition
 * class name&lt;T1, T2, ..., Tn&gt; {}
 * // Generic type reference
 * OrderedPair&lt;String, Box&lt;Integer&gt;&gt; p;
 * // Generic preceded method name
 * boolean same = Util.&lt;Integer, String&gt;compare(p1, p2);
 * // Diamond operator
 * Pair&lt;Integer, String&gt; p1 = new Pair&lt;&gt;(1, "apple");
 * // Method reference
 * List&lt;T&gt; list = ImmutableList.Builder&lt;T&gt;::new;
 * // Method reference
 * sort(list, Comparable::&lt;String&gt;compareTo);
 * // Constructor call
 * MyClass obj = new &lt;String&gt;MyClass();
 * </pre>
 * <p>
 * Examples with incorrect spacing:
 * </p>
 * <pre>
 * List&lt; String&gt; l; // violation, "&lt;" followed by whitespace
 * Box b = Box. &lt;String&gt;of("foo"); // violation, "&lt;" preceded with whitespace
 * public&lt;T&gt; void foo() {} // violation, "&lt;" not preceded with whitespace
 *
 * List a = new ArrayList&lt;&gt; (); // violation, "&gt;" followed by whitespace
 * Map&lt;Integer, String&gt;m; // violation, "&gt;" not followed by whitespace
 * Pair&lt;Integer, Integer &gt; p; // violation, "&gt;" preceded with whitespace
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code ws.followed}
 * </li>
 * <li>
 * {@code ws.illegalFollow}
 * </li>
 * <li>
 * {@code ws.notPreceded}
 * </li>
 * <li>
 * {@code ws.preceded}
 * </li>
 * </ul>
 *
 * @since 5.0
 */
@FileStatefulCheck
public class GenericWhitespaceCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WS_PRECEDED = "ws.preceded";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WS_FOLLOWED = "ws.followed";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WS_NOT_PRECEDED = "ws.notPreceded";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WS_ILLEGAL_FOLLOW = "ws.illegalFollow";

    /** Open angle bracket literal. */
    private static final String OPEN_ANGLE_BRACKET = "<";

    /** Close angle bracket literal. */
    private static final String CLOSE_ANGLE_BRACKET = ">";

    /** Used to count the depth of a Generic expression. */
    private int depth;

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
        return new int[] {TokenTypes.GENERIC_START, TokenTypes.GENERIC_END};
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        // Reset for each tree, just increase there are violations in preceding
        // trees.
        depth = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.GENERIC_START:
                processStart(ast);
                depth++;
                break;
            case TokenTypes.GENERIC_END:
                processEnd(ast);
                depth--;
                break;
            default:
                throw new IllegalArgumentException("Unknown type " + ast);
        }
    }

    /**
     * Checks the token for the end of Generics.
     *
     * @param ast the token to check
     */
    private void processEnd(DetailAST ast) {
        final int[] line = getLineCodePoints(ast.getLineNo() - 1);
        final int before = ast.getColumnNo() - 1;
        final int after = ast.getColumnNo() + 1;

        if (before >= 0 && CommonUtil.isCodePointWhitespace(line, before)
                && !containsWhitespaceBefore(before, line)) {
            log(ast, MSG_WS_PRECEDED, CLOSE_ANGLE_BRACKET);
        }

        if (after < line.length) {
            // Check if the last Generic, in which case must be a whitespace
            // or a '(),[.'.
            if (depth == 1) {
                processSingleGeneric(ast, line, after);
            }
            else {
                processNestedGenerics(ast, line, after);
            }
        }
    }

    /**
     * Process Nested generics.
     *
     * @param ast token
     * @param line unicode code points array of line
     * @param after position after
     */
    private void processNestedGenerics(DetailAST ast, int[] line, int after) {
        // In a nested Generic type, so can only be a '>' or ',' or '&'

        // In case of several extends definitions:
        //
        //   class IntEnumValueType<E extends Enum<E> & IntEnum>
        //                                          ^
        //   should be whitespace if followed by & -+
        //
        final int indexOfAmp = IntStream.range(after, line.length)
                .filter(index -> line[index] == '&')
                .findFirst()
                .orElse(-1);
        if (indexOfAmp >= 1
            && containsWhitespaceBetween(after, indexOfAmp, line)) {
            if (indexOfAmp - after == 0) {
                log(ast, MSG_WS_NOT_PRECEDED, "&");
            }
            else if (indexOfAmp - after != 1) {
                log(ast, MSG_WS_FOLLOWED, CLOSE_ANGLE_BRACKET);
            }
        }
        else if (line[after] == ' ') {
            log(ast, MSG_WS_FOLLOWED, CLOSE_ANGLE_BRACKET);
        }
    }

    /**
     * Process Single-generic.
     *
     * @param ast token
     * @param line unicode code points array of line
     * @param after position after
     */
    private void processSingleGeneric(DetailAST ast, int[] line, int after) {
        final char charAfter = Character.toChars(line[after])[0];
        if (isGenericBeforeMethod(ast) || isGenericBeforeCtor(ast)) {
            if (Character.isWhitespace(charAfter)) {
                log(ast, MSG_WS_FOLLOWED, CLOSE_ANGLE_BRACKET);
            }
        }
        else if (!isCharacterValidAfterGenericEnd(charAfter)) {
            log(ast, MSG_WS_ILLEGAL_FOLLOW, CLOSE_ANGLE_BRACKET);
        }
    }

    /**
     * Checks if generic is before constructor invocation.
     *
     * @param ast ast
     * @return true if generic before a constructor invocation
     */
    private static boolean isGenericBeforeCtor(DetailAST ast) {
        final DetailAST parent = ast.getParent();
        return parent.getParent().getType() == TokenTypes.LITERAL_NEW
                && (parent.getNextSibling().getType() == TokenTypes.IDENT
                    || parent.getNextSibling().getType() == TokenTypes.DOT);
    }

    /**
     * Is generic before method reference.
     *
     * @param ast ast
     * @return true if generic before a method ref
     */
    private static boolean isGenericBeforeMethod(DetailAST ast) {
        return ast.getParent().getParent().getParent().getType() == TokenTypes.METHOD_CALL
                || isAfterMethodReference(ast);
    }

    /**
     * Checks if current generic end ('&gt;') is located after
     * {@link TokenTypes#METHOD_REF method reference operator}.
     *
     * @param genericEnd {@link TokenTypes#GENERIC_END}
     * @return true if '&gt;' follows after method reference.
     */
    private static boolean isAfterMethodReference(DetailAST genericEnd) {
        return genericEnd.getParent().getParent().getType() == TokenTypes.METHOD_REF;
    }

    /**
     * Checks the token for the start of Generics.
     *
     * @param ast the token to check
     */
    private void processStart(DetailAST ast) {
        final int[] line = getLineCodePoints(ast.getLineNo() - 1);
        final int before = ast.getColumnNo() - 1;
        final int after = ast.getColumnNo() + 1;

        // Need to handle two cases as in:
        //
        //   public static <T> Callable<T> callable(Runnable task, T result)
        //                 ^           ^
        //      ws reqd ---+           +--- whitespace NOT required
        //
        if (before >= 0) {
            // Detect if the first case
            final DetailAST parent = ast.getParent();
            final DetailAST grandparent = parent.getParent();
            if (grandparent.getType() == TokenTypes.CTOR_DEF
                    || grandparent.getType() == TokenTypes.METHOD_DEF
                    || isGenericBeforeCtor(ast)) {
                // Require whitespace
                if (!CommonUtil.isCodePointWhitespace(line, before)) {
                    log(ast, MSG_WS_NOT_PRECEDED, OPEN_ANGLE_BRACKET);
                }
            }
            // Whitespace not required
            else if (CommonUtil.isCodePointWhitespace(line, before)
                && !containsWhitespaceBefore(before, line)) {
                log(ast, MSG_WS_PRECEDED, OPEN_ANGLE_BRACKET);
            }
        }

        if (after < line.length
                && CommonUtil.isCodePointWhitespace(line, after)) {
            log(ast, MSG_WS_FOLLOWED, OPEN_ANGLE_BRACKET);
        }
    }

    /**
     * Returns whether the specified string contains only whitespace between
     * specified indices.
     *
     * @param fromIndex the index to start the search from. Inclusive
     * @param toIndex the index to finish the search. Exclusive
     * @param line the unicode code points array of line to check
     * @return whether there are only whitespaces (or nothing)
     */
    private static boolean containsWhitespaceBetween(int fromIndex, int toIndex, int... line) {
        boolean result = true;
        for (int i = fromIndex; i < toIndex; i++) {
            if (!CommonUtil.isCodePointWhitespace(line, i)) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * Returns whether the specified string contains only whitespace up to specified index.
     *
     * @param before the index to finish the search. Exclusive
     * @param line   the unicode code points array of line to check
     * @return {@code true} if there are only whitespaces,
     *     false if there is nothing before or some other characters
     */
    private static boolean containsWhitespaceBefore(int before, int... line) {
        return before != 0 && CodePointUtil.hasWhitespaceBefore(before, line);
    }

    /**
     * Checks whether given character is valid to be right after generic ends.
     *
     * @param charAfter character to check
     * @return checks if given character is valid
     */
    private static boolean isCharacterValidAfterGenericEnd(char charAfter) {
        return charAfter == '(' || charAfter == ')'
            || charAfter == ',' || charAfter == '['
            || charAfter == '.' || charAfter == ':'
            || charAfter == ';'
            || Character.isWhitespace(charAfter);
    }

}
