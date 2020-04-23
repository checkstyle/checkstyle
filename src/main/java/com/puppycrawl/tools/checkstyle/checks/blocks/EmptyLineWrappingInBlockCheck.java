////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.blocks;

import java.util.Locale;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks if empty/blank line is present or not before/after the code blocks. This check supports
 * if-else, try-catch-finally blocks, while-loops, for-loops,
 * method definitions, class definitions, constructor definitions,
 * instance, static initialization blocks, annotation definitions and enum definitions.
 * </p>
 * <ul>
 * <li>
 * Property {@code topSeparator} - Specify the policy of empty line on top of the code block.
 * Default value is {@code empty_line}.
 * </li>
 * <li>
 * Property {@code bottomSeparator} - Specify the policy of empty line at the bottom top of the
 * code block.
 * Default value is {@code empty_line}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CLASS_DEF">
 * CLASS_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INTERFACE_DEF">
 * INTERFACE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ANNOTATION_DEF">
 * ANNOTATION_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_DEF">
 * ENUM_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_CONSTANT_DEF">
 * ENUM_CONSTANT_DEF</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="EmptyLineWrappingInBlock"/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public void test1() { // OK
 *
 *   if(true) { // OK
 *
 *   }
 * }
 * public void test2() { // OK
 *
 * }
 * </pre>
 * <p>
 * To configure the check with {@code empty_line} policy at the top separator.
 * </p>
 * <pre>
 * &lt;module name="EmptyLineWrappingInBlock"&gt;
 *   &lt;property name="topSeparator" value="empty_line"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 *
 * public void test1() {} // OK
 * public void test2() { // violation, expected an empty line before the block
 *   if(true) { // violation, expected an empty line before the block
 *     // Some Code here
 *   }
 *
 *   if(false) { // OK
 *     // Some Code here
 *   }
 * }
 * </pre>
 *
 * @since 8.33
 */
@StatelessCheck
public class EmptyLineWrappingInBlockCheck extends AbstractCheck {

    /**
     * A key pointing to the warning message in "message.properties" file.
     */
    public static final String MSG_NO_EMPTY_LINE_BEFORE = "block.no-line.wrap.before";

    /**
     * A key pointing to the warning message in "message.properties" file.
     */
    public static final String MSG_NO_EMPTY_LINE_AFTER = "block.no-line.wrap.after";

    /**
     * A key pointing to the warning message in "message.properties" file.
     */
    public static final String MSG_EMPTY_LINE_BEFORE = "block.line.wrap.before";

    /**
     * A key pointing to the warning message in "message.properties" file.
     */
    public static final String MSG_EMPTY_LINE_AFTER = "block.line.wrap.after";

    /**
     * Represents the prefix for start of a multiline comment.
     */
    private static final String MULTILINE_COMMENT_START ="/*";

    /**
     * Represents the suffix for end of multiline comment.
     */
    private static final String MULTILINE_COMMENT_END = "*/";

    /**
     * Represents the prefix for inline comment.
     */
    private static final String INLINE_COMMENT_START = "//";

    /**
     * Specify the policy of empty line on top of the code block.
     */
    private EmptyLineWrappingInBlockOption topSeparator =
        EmptyLineWrappingInBlockOption.EMPTY_LINE;

    /**
     * Specify the policy of empty line at the bottom top of the code block.
     */
    private EmptyLineWrappingInBlockOption bottomSeparator =
        EmptyLineWrappingInBlockOption.EMPTY_LINE;

    /**
     * Setter to specify the policy of empty line on top of the code block.
     *
     * @param option string to decode option from
     */
    public void setTopSeparator(String option) {
        topSeparator = EmptyLineWrappingInBlockOption.valueOf(
            option.trim().toUpperCase(Locale.ENGLISH)
        );
    }

    /**
     * Setter to specify the policy of empty line at the bottom top of the code block.
     *
     * @param option string to decode option from
     */
    public void setBottomSeparator(String option) {
        bottomSeparator = EmptyLineWrappingInBlockOption.valueOf(
            option.trim().toUpperCase(Locale.ENGLISH)
        );
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ENUM_CONSTANT_DEF
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.ANNOTATION_ARRAY_INIT,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ARRAY_INIT,
            TokenTypes.CASE_GROUP,
            TokenTypes.CLASS_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.LAMBDA,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_DEFAULT,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_NEW,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.METHOD_DEF,
            TokenTypes.SLIST,
            TokenTypes.STATIC_INIT,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        DetailAST rightCurly = findRightCurly(ast);

        if(rightCurly != null) {
            if (hasEmptyLineAfter(rightCurly)) {
                if (bottomSeparator == EmptyLineWrappingInBlockOption.NO_EMPTY_LINE) {
                    log(ast, MSG_NO_EMPTY_LINE_AFTER);
                }
            }
            else if (bottomSeparator == EmptyLineWrappingInBlockOption.EMPTY_LINE) {
                log(ast, MSG_EMPTY_LINE_AFTER);
            }
            if (hasEmptyLineBefore(ast)) {
                if (topSeparator == EmptyLineWrappingInBlockOption.NO_EMPTY_LINE) {
                    log(ast, MSG_NO_EMPTY_LINE_BEFORE);
                }
            }
            else if (topSeparator == EmptyLineWrappingInBlockOption.EMPTY_LINE) {
                log(ast, MSG_EMPTY_LINE_BEFORE);
            }
        }
    }

    /**
     * Checks whether an empty/blank line is present before the given block.
     *
     * @param ast the current node
     * @return true if an empty/blank line is present before the given block.
     */
    private boolean hasEmptyLineBefore(DetailAST ast) {
        boolean result = false;
        int index = ast.getLineNo() - 2;
        if (index > 0) {
            final FileContents fileContents = getFileContents();
            String line = fileContents.getLine(index);

            // We need to iterate backwards to find the previous non-commented line.
            if(line.trim().endsWith(MULTILINE_COMMENT_END)
                || line.trim().startsWith(INLINE_COMMENT_START)) {
                // We need to step backwards until we find the start of a multiline comment
                // or inline comments
                while(!CommonUtil.isBlank(line)
                      && (line.trim().startsWith(MULTILINE_COMMENT_START)
                          || line.trim().charAt(0) == '*')
                          || line.trim().startsWith(INLINE_COMMENT_START)) {
                    index--;
                    line = fileContents.getLine(index);
                }
            }
            result = CommonUtil.isBlank(line);
        }
        return result;
    }

    /**
     * Checks whether an empty/blank line is present after the given block.
     *
     * @param rightCurly the rightCuryl node of the current code block.
     * @return true if an empty/blank line is present after the given block.
     */
    private boolean hasEmptyLineAfter(DetailAST rightCurly) {
        boolean result = false;
        final FileContents fileContents = getFileContents();
        final int index = rightCurly.getLineNo();
        if (index < fileContents.getLines().length) {
            final String text = fileContents.getLine(index);
            if (text.length() == 0) {
                result = true;
            }
            else {
                result = fileContents.lineIsBlank(index);
            }
        }
        return result;
    }

    /**
     * Finds the right curly brace of a code block associated with the specified token.
     *
     * @param ast the token to process
     * @return the token for the left curly brace if found, {@code null} otherwise
     */
    private static DetailAST findRightCurly(DetailAST ast)  {
        DetailAST block = null;
        switch (ast.getType()) {
            case TokenTypes.ANNOTATION_ARRAY_INIT:
            case TokenTypes.ARRAY_INIT:
                block = ast;
                break;

            case TokenTypes.INTERFACE_DEF:
            case TokenTypes.CLASS_DEF:
            case TokenTypes.ANNOTATION_DEF:
            case TokenTypes.ENUM_DEF:
            case TokenTypes.ENUM_CONSTANT_DEF:
            case TokenTypes.LITERAL_NEW:
                block = ast.findFirstToken(TokenTypes.OBJBLOCK);
                break;

            case TokenTypes.LITERAL_SWITCH:
                block = ast;
                break;

            case TokenTypes.LITERAL_ELSE:
                // Special handling for the "else if" case.
                final DetailAST candidate = ast.getFirstChild();
                if (candidate.getType() == TokenTypes.SLIST) {
                    block = candidate;
                }
                break;

            case TokenTypes.SLIST:
                // The scope block is a SLIST with another SLIST as the parent.
                if (ast.getParent().getType() == TokenTypes.SLIST) {
                    block = ast;
                }
                break;

            case TokenTypes.CASE_GROUP:
                // The CASE_GROUP is the parent of 'case', 'default' and SLIST
                // parent.
                block = ast.getLastChild().findFirstToken(TokenTypes.SLIST);
                break;

            case TokenTypes.LITERAL_CASE:
            case TokenTypes.LITERAL_DEFAULT:
                // The 'case' and 'default' are siblings for SLIST parent.
                block = ast.getParent().getLastChild().findFirstToken(TokenTypes.SLIST);
                break;

            default:
                // All the rest: for/while/if/STATIC_INIT,lambda,etc.
                block = ast.findFirstToken(TokenTypes.SLIST);
                break;
        }

        // The right curly brace is always the last child.
        DetailAST rightCurly = null;
        if (block != null) {
            rightCurly = block.getLastChild();
        }

        return rightCurly;
    }
}
