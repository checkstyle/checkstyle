////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.Utils;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks for empty catch blocks. There are two options to make validation more precise:
 * </p>
 * <p>
 * <b>exceptionVariableName</b> - the name of variable associated with exception,
 * if Check meets variable name matching specified value - empty block is suppressed.<br>
 *  default value: &quot;^$&quot;
 * </p>
 * <p>
 * <b>commentFormat</b> - the format of the first comment inside empty catch
 * block, if Check meets comment inside empty catch block matching specified format
 *  - empty block is suppressed. If it is multi-line comment - only its first line is analyzed.<br>
 * default value: &quot;.*&quot;<br>
 * So, by default Check allows empty catch block with any comment inside.
 * </p>
 * <p>
 * If both options are specified - they are applied by <b>any of them is matching</b>.
 * </p>
 * Examples:
 * <p>
 * To configure the Check to suppress empty catch block if exception's variable name is
 *  <b>expected</b> or <b>ignore</b>:
 * </p>
 * <pre>
 * &lt;module name=&quot;EmptyCatchBlock&quot;&gt;
 *    &lt;property name=&quot;exceptionVariableName&quot; value=&quot;ignore|expected;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Such empty blocks would be both suppressed:<br>
 * </p>
 * <pre>
 * {@code
 * try {
 *     throw new RuntimeException();
 * } catch (RuntimeException expected) {
 * }
 * }
 * {@code
 * try {
 *     throw new RuntimeException();
 * } catch (RuntimeException ignore) {
 * }
 * }
 * </pre>
 * <p>
 * To configure the Check to suppress empty catch block if single-line comment inside
 *  is &quot;//This is expected&quot;:
 * </p>
 * <pre>
 * &lt;module name=&quot;EmptyCatchBlock&quot;&gt;
 *    &lt;property name=&quot;commentFormat&quot; value=&quot;This is expected&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Such empty block would be suppressed:<br>
 * </p>
 * <pre>
 * {@code
 * try {
 *     throw new RuntimeException();
 * } catch (RuntimeException e) {
 *     //This is expected
 * }
 * }
 * </pre>
 * <p>
 * To configure the Check to suppress empty catch block if single-line comment inside
 *  is &quot;//This is expected&quot; or exception's variable name is &quot;myException&quot;:
 * </p>
 * <pre>
 * &lt;module name=&quot;EmptyCatchBlock&quot;&gt;
 *    &lt;property name=&quot;commentFormat&quot; value=&quot;This is expected&quot;/&gt;
 *    &lt;property name=&quot;exceptionVariableName&quot; value=&quot;myException&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Such empty blocks would be both suppressed:<br>
 * </p>
 * <pre>
 * {@code
 * try {
 *     throw new RuntimeException();
 * } catch (RuntimeException e) {
 *     //This is expected
 * }
 * }
 * {@code
 * try {
 *     throw new RuntimeException();
 * } catch (RuntimeException myException) {
 *
 * }
 * }
 * </pre>
 * @author <a href="mailto:nesterenko-aleksey@list.ru">Aleksey Nesterenko</a>
 */
public class EmptyCatchBlockCheck extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_CATCH_BLOCK_EMPTY = "catch.block.empty";

    /** Format of skipping exception's variable name. */
    private String exceptionVariableName = "^$";

    /** Format of comment. */
    private String commentFormat = ".*";

    /**
     * Regular expression pattern compiled from exception's variable name.
     */
    private Pattern variableNameRegexp = Pattern.compile(exceptionVariableName);

    /**
     * Regular expression pattern compiled from comment's format.
     */
    private Pattern commentRegexp = Pattern.compile(commentFormat);

    /**
     * Setter for exception's variable name format.
     * @param exceptionVariableName
     *        format of exception's variable name.
     * @throws org.apache.commons.beanutils.ConversionException
     *         if unable to create Pattern object.
     */
    public void setExceptionVariableName(String exceptionVariableName) {
        this.exceptionVariableName = exceptionVariableName;
        variableNameRegexp = Utils.createPattern(exceptionVariableName);
    }

    /**
     * Setter for comment format.
     * @param commentFormat
     *        format of comment.
     * @throws org.apache.commons.beanutils.ConversionException
     *         if unable to create Pattern object.
     */
    public void setCommentFormat(String commentFormat) {
        this.commentFormat = commentFormat;
        commentRegexp = Utils.createPattern(commentFormat);
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.LITERAL_CATCH,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
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
     * @param catchAst {@link TokenTypes#LITERAL_CATCH LITERAL_CATCH}
     */
    private void visitCatchBlock(DetailAST catchAst) {
        if (isEmptyCatchBlock(catchAst)) {
            final String commentContent = getCommentFirstLine(catchAst);
            if (isVerifiable(catchAst, commentContent)) {
                log(catchAst.getLineNo(), MSG_KEY_CATCH_BLOCK_EMPTY);
            }
        }
    }

    /**
     * Gets the first line of comment in catch block. If comment is single-line -
     *  returns it fully, else if comment is multi-line - returns the first line.
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
            final String[] lines = commentContent.split(System.getProperty("line.separator"));
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
     * @param emptyCatchAst empty catch {@link TokenTypes#LITERAL_CATCH LITERAL_CATCH} block.
     * @param commentContent text of comment.
     * @return true if empty catch block is verifiable by Check.
     */
    private boolean isVerifiable(DetailAST emptyCatchAst, String commentContent) {
        final String variableName = getExceptionVariableName(emptyCatchAst);
        final boolean isMatchingVariableName = variableNameRegexp
                .matcher(variableName).find();
        final boolean isMatchingCommentContent = !commentContent.isEmpty()
                 && commentRegexp.matcher(commentContent).find();
        return !isMatchingVariableName && !isMatchingCommentContent;
    }

    /**
     * Checks if catch block is empty or contains only comments.
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
     * @param catchAst {@link TokenTypes#LITERAL_CATCH LITERAL_CATCH}
     * @return Variable's name associated with exception.
     */
    private static String getExceptionVariableName(DetailAST catchAst) {
        final DetailAST parameterDef = catchAst.findFirstToken(TokenTypes.PARAMETER_DEF);
        final DetailAST variableName = parameterDef.findFirstToken(TokenTypes.IDENT);
        return variableName.getText();
    }

}
