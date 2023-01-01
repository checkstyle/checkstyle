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

package com.puppycrawl.tools.checkstyle.checks.blocks;

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks for empty catch blocks.
 * By default, check allows empty catch block with any comment inside.
 * </p>
 * <p>
 * There are two options to make validation more precise: <b>exceptionVariableName</b> and
 * <b>commentFormat</b>.
 * If both options are specified - they are applied by <b>any of them is matching</b>.
 * </p>
 * <ul>
 * <li>
 * Property {@code exceptionVariableName} - Specify the RegExp for the name of the variable
 * associated with exception. If check meets variable name matching specified value - empty
 * block is suppressed.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^$"}.
 * </li>
 * <li>
 * Property {@code commentFormat} - Specify the RegExp for the first comment inside empty
 * catch block. If check meets comment inside empty catch block matching specified format
 * - empty block is suppressed. If it is multi-line comment - only its first line is analyzed.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code ".*"}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;EmptyCatchBlock&quot;/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * try {
 *   throw new RuntimeException();
 * } catch (RuntimeException expected) {
 * } // violation
 *
 * try {
 *   throw new RuntimeException();
 * } catch (RuntimeException ignore) {
 *   // no handling
 * } // ok, catch block has comment
 *
 * try {
 *   throw new RuntimeException();
 * } catch (RuntimeException o) {
 * } // violation
 *
 * try {
 *   throw new RuntimeException();
 * } catch (RuntimeException ex) {
 *   // This is expected
 * } // ok
 * </pre>
 * <p>
 * To configure the check to suppress empty catch block if exception's variable name is
 * {@code expected} or {@code ignore} or there's any comment inside:
 * </p>
 * <pre>
 * &lt;module name=&quot;EmptyCatchBlock&quot;&gt;
 *   &lt;property name=&quot;exceptionVariableName&quot; value=&quot;expected|ignore&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Such empty blocks would be both suppressed:
 * </p>
 * <pre>
 * try {
 *   throw new RuntimeException();
 * } catch (RuntimeException expected) {
 * } // ok
 *
 * try {
 *   throw new RuntimeException();
 * } catch (RuntimeException ignore) {
 *   // no handling
 * } // ok
 *
 * try {
 *   throw new RuntimeException();
 * } catch (RuntimeException o) {
 * } // violation
 *
 * try {
 *   throw new RuntimeException();
 * } catch (RuntimeException ex) {
 *   // This is expected
 * } // ok
 * </pre>
 * <p>
 * To configure the check to suppress empty catch block if single-line comment inside
 * is &quot;//This is expected&quot;:
 * </p>
 * <pre>
 * &lt;module name=&quot;EmptyCatchBlock&quot;&gt;
 *   &lt;property name=&quot;commentFormat&quot; value=&quot;This is expected&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Such empty block would be suppressed:
 * </p>
 * <pre>
 * try {
 *   throw new RuntimeException();
 * } catch (RuntimeException expected) {
 * } // violation
 *
 * try {
 *   throw new RuntimeException();
 * } catch (RuntimeException ignore) {
 *   // no handling
 * } // violation
 *
 * try {
 *   throw new RuntimeException();
 * } catch (RuntimeException o) {
 * } // violation
 *
 * try {
 *   throw new RuntimeException();
 * } catch (RuntimeException ex) {
 *   // This is expected
 * } // ok
 * </pre>
 * <p>
 * To configure the check to suppress empty catch block if single-line comment inside
 * is &quot;//This is expected&quot; or exception's
 * variable name is &quot;myException&quot; (any option is matching):
 * </p>
 * <pre>
 * &lt;module name=&quot;EmptyCatchBlock&quot;&gt;
 *   &lt;property name=&quot;commentFormat&quot; value=&quot;This is expected&quot;/&gt;
 *   &lt;property name=&quot;exceptionVariableName&quot; value=&quot;myException&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Such empty blocks would be suppressed:
 * </p>
 * <pre>
 * try {
 *   throw new RuntimeException();
 * } catch (RuntimeException e) {
 *   //This is expected
 * }
 * ...
 * try {
 *   throw new RuntimeException();
 * } catch (RuntimeException e) {
 *   //   This is expected
 * }
 * ...
 * try {
 *   throw new RuntimeException();
 * } catch (RuntimeException e) {
 *   // This is expected
 *   // some another comment
 * }
 * ...
 * try {
 *   throw new RuntimeException();
 * } catch (RuntimeException e) {
 *   &#47;* This is expected *&#47;
 * }
 * ...
 * try {
 *   throw new RuntimeException();
 * } catch (RuntimeException e) {
 *   &#47;*
 *   *
 *   * This is expected
 *   * some another comment
 *   *&#47;
 * }
 * ...
 * try {
 *   throw new RuntimeException();
 * } catch (RuntimeException myException) {
 *
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
 * {@code catch.block.empty}
 * </li>
 * </ul>
 *
 * @since 6.4
 */
@StatelessCheck
public class EmptyCatchBlockCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_CATCH_BLOCK_EMPTY = "catch.block.empty";

    /**
     * A pattern to split on line ends.
     */
    private static final Pattern LINE_END_PATTERN = Pattern.compile("\\r?+\\n|\\r");

    /**
     * Specify the RegExp for the name of the variable associated with exception.
     * If check meets variable name matching specified value - empty block is suppressed.
     */
    private Pattern exceptionVariableName = Pattern.compile("^$");

    /**
     * Specify the RegExp for the first comment inside empty catch block.
     * If check meets comment inside empty catch block matching specified format - empty
     * block is suppressed. If it is multi-line comment - only its first line is analyzed.
     */
    private Pattern commentFormat = Pattern.compile(".*");

    /**
     * Setter to specify the RegExp for the name of the variable associated with exception.
     * If check meets variable name matching specified value - empty block is suppressed.
     *
     * @param exceptionVariablePattern
     *        pattern of exception's variable name.
     */
    public void setExceptionVariableName(Pattern exceptionVariablePattern) {
        exceptionVariableName = exceptionVariablePattern;
    }

    /**
     * Setter to specify the RegExp for the first comment inside empty catch block.
     * If check meets comment inside empty catch block matching specified format - empty
     * block is suppressed. If it is multi-line comment - only its first line is analyzed.
     *
     * @param commentPattern
     *        pattern of comment.
     */
    public void setCommentFormat(Pattern commentPattern) {
        commentFormat = commentPattern;
    }

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
            TokenTypes.LITERAL_CATCH,
        };
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void visitToken(DetailAST ast) {
        visitCatchBlock(ast);
    }

    /**
     * Visits catch ast node, if it is empty catch block - checks it according to
     *  Check's options. If exception's variable name or comment inside block are matching
     *   specified regexp - skips from consideration, else - puts violation.
     *
     * @param catchAst {@link TokenTypes#LITERAL_CATCH LITERAL_CATCH}
     */
    private void visitCatchBlock(DetailAST catchAst) {
        if (isEmptyCatchBlock(catchAst)) {
            final String commentContent = getCommentFirstLine(catchAst);
            if (isVerifiable(catchAst, commentContent)) {
                log(catchAst.findFirstToken(TokenTypes.SLIST), MSG_KEY_CATCH_BLOCK_EMPTY);
            }
        }
    }

    /**
     * Gets the first line of comment in catch block. If comment is single-line -
     *  returns it fully, else if comment is multi-line - returns the first line.
     *
     * @param catchAst {@link TokenTypes#LITERAL_CATCH LITERAL_CATCH}
     * @return the first line of comment in catch block, "" if no comment was found.
     */
    private static String getCommentFirstLine(DetailAST catchAst) {
        final DetailAST slistToken = catchAst.getLastChild();
        final DetailAST firstElementInBlock = slistToken.getFirstChild();
        String commentContent = "";
        if (firstElementInBlock.getType() == TokenTypes.SINGLE_LINE_COMMENT) {
            commentContent = firstElementInBlock.getFirstChild().getText();
        }
        else if (firstElementInBlock.getType() == TokenTypes.BLOCK_COMMENT_BEGIN) {
            commentContent = firstElementInBlock.getFirstChild().getText();
            final String[] lines = LINE_END_PATTERN.split(commentContent);
            for (String line : lines) {
                if (!line.isEmpty()) {
                    commentContent = line;
                    break;
                }
            }
        }
        return commentContent;
    }

    /**
     * Checks if current empty catch block is verifiable according to Check's options
     *  (exception's variable name and comment format are both in consideration).
     *
     * @param emptyCatchAst empty catch {@link TokenTypes#LITERAL_CATCH LITERAL_CATCH} block.
     * @param commentContent text of comment.
     * @return true if empty catch block is verifiable by Check.
     */
    private boolean isVerifiable(DetailAST emptyCatchAst, String commentContent) {
        final String variableName = getExceptionVariableName(emptyCatchAst);
        final boolean isMatchingVariableName = exceptionVariableName
                .matcher(variableName).find();
        final boolean isMatchingCommentContent = !commentContent.isEmpty()
                 && commentFormat.matcher(commentContent).find();
        return !isMatchingVariableName && !isMatchingCommentContent;
    }

    /**
     * Checks if catch block is empty or contains only comments.
     *
     * @param catchAst {@link TokenTypes#LITERAL_CATCH LITERAL_CATCH}
     * @return true if catch block is empty.
     */
    private static boolean isEmptyCatchBlock(DetailAST catchAst) {
        boolean result = true;
        final DetailAST slistToken = catchAst.findFirstToken(TokenTypes.SLIST);
        DetailAST catchBlockStmt = slistToken.getFirstChild();
        while (catchBlockStmt.getType() != TokenTypes.RCURLY) {
            if (catchBlockStmt.getType() != TokenTypes.SINGLE_LINE_COMMENT
                 && catchBlockStmt.getType() != TokenTypes.BLOCK_COMMENT_BEGIN) {
                result = false;
                break;
            }
            catchBlockStmt = catchBlockStmt.getNextSibling();
        }
        return result;
    }

    /**
     * Gets variable's name associated with exception.
     *
     * @param catchAst {@link TokenTypes#LITERAL_CATCH LITERAL_CATCH}
     * @return Variable's name associated with exception.
     */
    private static String getExceptionVariableName(DetailAST catchAst) {
        final DetailAST parameterDef = catchAst.findFirstToken(TokenTypes.PARAMETER_DEF);
        final DetailAST variableName = parameterDef.findFirstToken(TokenTypes.IDENT);
        return variableName.getText();
    }

}
