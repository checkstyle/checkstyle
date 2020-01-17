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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <p>
 * Checks for empty line separators after header, package, all import declarations,
 * fields, constructors, methods, nested classes,
 * static initializers and instance initializers.
 * </p>
 * <p>
 * ATTENTION: empty line separator is required between token siblings,
 * not after line where token is found.
 * If token does not have same type sibling then empty line
 * is required at its end (for example for CLASS_DEF it is after '}').
 * Also, trailing comments are skipped.
 * </p>
 * <ul>
 * <li>
 * Property {@code allowNoEmptyLineBetweenFields} - Allow no empty line between fields.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code allowMultipleEmptyLines} - Allow multiple empty lines between class members.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code allowMultipleEmptyLinesInsideClassMembers} - Allow multiple
 * empty lines inside class members.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#PACKAGE_DEF">
 * PACKAGE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#IMPORT">
 * IMPORT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#STATIC_IMPORT">
 * STATIC_IMPORT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CLASS_DEF">
 * CLASS_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INTERFACE_DEF">
 * INTERFACE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_DEF">
 * ENUM_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * STATIC_INIT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INSTANCE_INIT">
 * INSTANCE_INIT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_DEF">
 * CTOR_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#VARIABLE_DEF">
 * VARIABLE_DEF</a>.
 * </li>
 * </ul>
 * <p>
 * Example of declarations without empty line separator:
 * </p>
 *
 * <pre>
 * ///////////////////////////////////////////////////
 * //HEADER
 * ///////////////////////////////////////////////////
 * package com.puppycrawl.tools.checkstyle.whitespace;
 * import java.io.Serializable;
 * class Foo {
 *   public static final int FOO_CONST = 1;
 *   public void foo() {} //should be separated from previous statement.
 * }
 * </pre>
 *
 * <p>
 * To configure the check with default parameters:
 * </p>
 *
 * <pre>
 * &lt;module name=&quot;EmptyLineSeparator&quot;/&gt;
 * </pre>
 *
 * <p>
 * Example of declarations with empty line separator
 * that is expected by the Check by default:
 * </p>
 *
 * <pre>
 * ///////////////////////////////////////////////////
 * //HEADER
 * ///////////////////////////////////////////////////
 *
 * package com.puppycrawl.tools.checkstyle.whitespace;
 *
 * import java.io.Serializable;
 *
 * class Foo {
 *   public static final int FOO_CONST = 1;
 *
 *   public void foo() {}
 * }
 * </pre>
 * <p>
 * To check empty line after
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#VARIABLE_DEF">
 * VARIABLE_DEF</a> and
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>:
 * </p>
 *
 * <pre>
 * &lt;module name=&quot;EmptyLineSeparator&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;VARIABLE_DEF, METHOD_DEF&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>
 * To allow no empty line between fields:
 * </p>
 * <pre>
 * &lt;module name="EmptyLineSeparator"&gt;
 *   &lt;property name="allowNoEmptyLineBetweenFields" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>
 * Example of declarations with multiple empty lines between class members (allowed by default):
 * </p>
 *
 * <pre>
 * ///////////////////////////////////////////////////
 * //HEADER
 * ///////////////////////////////////////////////////
 *
 *
 * package com.puppycrawl.tools.checkstyle.whitespace;
 *
 *
 *
 * import java.io.Serializable;
 *
 *
 * class Foo {
 *   public static final int FOO_CONST = 1;
 *
 *
 *
 *   public void foo() {} //should be separated from previous statement.
 * }
 * </pre>
 * <p>
 * To disallow multiple empty lines between class members:
 * </p>
 * <pre>
 * &lt;module name=&quot;EmptyLineSeparator&quot;&gt;
 *   &lt;property name=&quot;allowMultipleEmptyLines&quot; value=&quot;false&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>
 * To disallow multiple empty lines inside constructor, initialization block and method:
 * </p>
 * <pre>
 * &lt;module name="EmptyLineSeparator"&gt;
 *   &lt;property name="allowMultipleEmptyLinesInsideClassMembers" value="false"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>
 * The check is valid only for statements that have body:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CLASS_DEF">
 * CLASS_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INTERFACE_DEF">
 * INTERFACE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_DEF">
 * ENUM_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * STATIC_INIT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INSTANCE_INIT">
 * INSTANCE_INIT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_DEF">
 * CTOR_DEF</a>.
 * </p>
 * <p>
 * Example of declarations with multiple empty lines inside method:
 * </p>
 *
 * <pre>
 * ///////////////////////////////////////////////////
 * //HEADER
 * ///////////////////////////////////////////////////
 *
 * package com.puppycrawl.tools.checkstyle.whitespace;
 *
 * class Foo {
 *
 *   public void foo() {
 *
 *
 *     System.out.println(1); // violation since method has 2 empty lines subsequently
 *   }
 * }
 * </pre>
 *
 * @since 5.8
 */
@StatelessCheck
public class EmptyLineSeparatorCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message empty.line.separator in "messages.properties"
     * file.
     */
    public static final String MSG_SHOULD_BE_SEPARATED = "empty.line.separator";

    /**
     * A key is pointing to the warning message empty.line.separator.multiple.lines
     *  in "messages.properties"
     * file.
     */
    public static final String MSG_MULTIPLE_LINES = "empty.line.separator.multiple.lines";

    /**
     * A key is pointing to the warning message empty.line.separator.lines.after
     * in "messages.properties" file.
     */
    public static final String MSG_MULTIPLE_LINES_AFTER =
            "empty.line.separator.multiple.lines.after";

    /**
     * A key is pointing to the warning message empty.line.separator.multiple.lines.inside
     * in "messages.properties" file.
     */
    public static final String MSG_MULTIPLE_LINES_INSIDE =
            "empty.line.separator.multiple.lines.inside";

    /** List of AST token types, which can not have comment nodes to check inside. */
    private static final List<Integer> TOKEN_TYPES_WITHOUT_COMMENTS_TO_CHECK_INSIDE =
            Arrays.asList(TokenTypes.PACKAGE_DEF, TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT,
                    TokenTypes.STATIC_INIT);

    /** Allow no empty line between fields. */
    private boolean allowNoEmptyLineBetweenFields;

    /** Allow multiple empty lines between class members. */
    private boolean allowMultipleEmptyLines = true;

    /** Allow multiple empty lines inside class members. */
    private boolean allowMultipleEmptyLinesInsideClassMembers = true;

    /**
     * Setter to allow no empty line between fields.
     * @param allow
     *        User's value.
     */
    public final void setAllowNoEmptyLineBetweenFields(boolean allow) {
        allowNoEmptyLineBetweenFields = allow;
    }

    /**
     * Setter to allow multiple empty lines between class members.
     * @param allow User's value.
     */
    public void setAllowMultipleEmptyLines(boolean allow) {
        allowMultipleEmptyLines = allow;
    }

    /**
     * Setter to allow multiple empty lines inside class members.
     * @param allow User's value.
     */
    public void setAllowMultipleEmptyLinesInsideClassMembers(boolean allow) {
        allowMultipleEmptyLinesInsideClassMembers = allow;
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.STATIC_INIT,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.VARIABLE_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        checkComments(ast);
        if (hasMultipleLinesBefore(ast)) {
            log(ast.getLineNo(), MSG_MULTIPLE_LINES, ast.getText());
        }
        if (!allowMultipleEmptyLinesInsideClassMembers) {
            processMultipleLinesInside(ast);
        }
        if (ast.getType() == TokenTypes.PACKAGE_DEF) {
            checkCommentInModifiers(ast);
        }
        DetailAST nextToken = ast.getNextSibling();
        while (nextToken != null && isComment(nextToken)) {
            nextToken = nextToken.getNextSibling();
        }
        if (nextToken != null) {
            checkToken(ast, nextToken);
        }
    }

    /**
     * Checks that token and next token are separated.
     *
     * @param ast token to validate
     * @param nextToken next sibling of the token
     */
    private void checkToken(DetailAST ast, DetailAST nextToken) {
        final int astType = ast.getType();
        switch (astType) {
            case TokenTypes.VARIABLE_DEF:
                processVariableDef(ast, nextToken);
                break;
            case TokenTypes.IMPORT:
            case TokenTypes.STATIC_IMPORT:
                processImport(ast, nextToken);
                break;
            case TokenTypes.PACKAGE_DEF:
                processPackage(ast, nextToken);
                break;
            default:
                if (nextToken.getType() == TokenTypes.RCURLY) {
                    if (hasNotAllowedTwoEmptyLinesBefore(nextToken)) {
                        log(ast.getLineNo(), MSG_MULTIPLE_LINES_AFTER, ast.getText());
                    }
                }
                else if (!hasEmptyLineAfter(ast)) {
                    log(nextToken.getLineNo(), MSG_SHOULD_BE_SEPARATED,
                        nextToken.getText());
                }
        }
    }

    /**
     * Checks that packageDef token is separated from comment in modifiers.
     *
     * @param packageDef package def token
     */
    private void checkCommentInModifiers(DetailAST packageDef) {
        final Optional<DetailAST> comment = findCommentUnder(packageDef);
        if (comment.isPresent()) {
            log(comment.get().getLineNo(), MSG_SHOULD_BE_SEPARATED, comment.get().getText());
        }
    }

    /**
     * Log violation in case there are multiple empty lines inside constructor,
     * initialization block or method.
     * @param ast the ast to check.
     */
    private void processMultipleLinesInside(DetailAST ast) {
        final int astType = ast.getType();
        if (isClassMemberBlock(astType)) {
            final List<Integer> emptyLines = getEmptyLines(ast);
            final List<Integer> emptyLinesToLog = getEmptyLinesToLog(emptyLines);

            for (Integer lineNo : emptyLinesToLog) {
                // Checkstyle counts line numbers from 0 but IDE from 1
                log(lineNo + 1, MSG_MULTIPLE_LINES_INSIDE);
            }
        }
    }

    /**
     * Whether the AST is a class member block.
     * @param astType the AST to check.
     * @return true if the AST is a class member block.
     */
    private static boolean isClassMemberBlock(int astType) {
        return astType == TokenTypes.STATIC_INIT
                || astType == TokenTypes.INSTANCE_INIT
                || astType == TokenTypes.METHOD_DEF
                || astType == TokenTypes.CTOR_DEF;
    }

    /**
     * Get list of empty lines.
     * @param ast the ast to check.
     * @return list of line numbers for empty lines.
     */
    private List<Integer> getEmptyLines(DetailAST ast) {
        final DetailAST lastToken = ast.getLastChild().getLastChild();
        int lastTokenLineNo = 0;
        if (lastToken != null) {
            // -1 as count starts from 0
            // -2 as last token line cannot be empty, because it is a RCURLY
            lastTokenLineNo = lastToken.getLineNo() - 2;
        }
        final List<Integer> emptyLines = new ArrayList<>();
        final FileContents fileContents = getFileContents();

        for (int lineNo = ast.getLineNo(); lineNo <= lastTokenLineNo; lineNo++) {
            if (fileContents.lineIsBlank(lineNo)) {
                emptyLines.add(lineNo);
            }
        }
        return emptyLines;
    }

    /**
     * Get list of empty lines to log.
     * @param emptyLines list of empty lines.
     * @return list of empty lines to log.
     */
    private static List<Integer> getEmptyLinesToLog(List<Integer> emptyLines) {
        final List<Integer> emptyLinesToLog = new ArrayList<>();
        if (emptyLines.size() >= 2) {
            int previousEmptyLineNo = emptyLines.get(0);
            for (int emptyLineNo : emptyLines) {
                if (previousEmptyLineNo + 1 == emptyLineNo) {
                    emptyLinesToLog.add(emptyLineNo);
                }
                previousEmptyLineNo = emptyLineNo;
            }
        }
        return emptyLinesToLog;
    }

    /**
     * Whether the token has not allowed multiple empty lines before.
     * @param ast the ast to check.
     * @return true if the token has not allowed multiple empty lines before.
     */
    private boolean hasMultipleLinesBefore(DetailAST ast) {
        boolean result = false;
        if ((ast.getType() != TokenTypes.VARIABLE_DEF
            || isTypeField(ast))
                && hasNotAllowedTwoEmptyLinesBefore(ast)) {
            result = true;
        }
        return result;
    }

    /**
     * Process Package.
     * @param ast token
     * @param nextToken next token
     */
    private void processPackage(DetailAST ast, DetailAST nextToken) {
        if (ast.getLineNo() > 1 && !hasEmptyLineBefore(ast)) {
            if (getFileContents().getFileName().endsWith("package-info.java")) {
                if (!ast.getFirstChild().hasChildren() && !isPrecededByJavadoc(ast)) {
                    log(ast.getLineNo(), MSG_SHOULD_BE_SEPARATED, ast.getText());
                }
            }
            else {
                log(ast.getLineNo(), MSG_SHOULD_BE_SEPARATED, ast.getText());
            }
        }
        if (!hasEmptyLineAfter(ast)) {
            log(nextToken.getLineNo(), MSG_SHOULD_BE_SEPARATED, nextToken.getText());
        }
    }

    /**
     * Process Import.
     * @param ast token
     * @param nextToken next token
     */
    private void processImport(DetailAST ast, DetailAST nextToken) {
        if (nextToken.getType() != TokenTypes.IMPORT
                && nextToken.getType() != TokenTypes.STATIC_IMPORT && !hasEmptyLineAfter(ast)) {
            log(nextToken.getLineNo(), MSG_SHOULD_BE_SEPARATED, nextToken.getText());
        }
    }

    /**
     * Process Variable.
     * @param ast token
     * @param nextToken next Token
     */
    private void processVariableDef(DetailAST ast, DetailAST nextToken) {
        if (isTypeField(ast) && !hasEmptyLineAfter(ast)
                && isViolatingEmptyLineBetweenFieldsPolicy(nextToken)) {
            log(nextToken.getLineNo(), MSG_SHOULD_BE_SEPARATED,
                    nextToken.getText());
        }
    }

    /**
     * Checks whether token placement violates policy of empty line between fields.
     * @param detailAST token to be analyzed
     * @return true if policy is violated and warning should be raised; false otherwise
     */
    private boolean isViolatingEmptyLineBetweenFieldsPolicy(DetailAST detailAST) {
        return detailAST.getType() != TokenTypes.RCURLY
                && (!allowNoEmptyLineBetweenFields
                    || detailAST.getType() != TokenTypes.VARIABLE_DEF);
    }

    /**
     * Checks if a token has empty two previous lines and multiple empty lines is not allowed.
     * @param token DetailAST token
     * @return true, if token has empty two lines before and allowMultipleEmptyLines is false
     */
    private boolean hasNotAllowedTwoEmptyLinesBefore(DetailAST token) {
        return !allowMultipleEmptyLines && hasEmptyLineBefore(token)
                && isPrePreviousLineEmpty(token);
    }

    /**
     * Check if group of comments located right before token has more than one previous empty line.
     * @param token DetailAST token
     */
    private void checkComments(DetailAST token) {
        if (!allowMultipleEmptyLines) {
            if (TOKEN_TYPES_WITHOUT_COMMENTS_TO_CHECK_INSIDE.contains(token.getType())) {
                DetailAST previousNode = token.getPreviousSibling();
                while (isCommentInBeginningOfLine(previousNode)) {
                    if (hasEmptyLineBefore(previousNode) && isPrePreviousLineEmpty(previousNode)) {
                        log(previousNode, MSG_MULTIPLE_LINES, previousNode.getText());
                    }
                    previousNode = previousNode.getPreviousSibling();
                }
            }
            else {
                checkCommentsInsideToken(token);
            }
        }
    }

    /**
     * Check if group of comments located at the start of token has more than one previous empty
     * line.
     * @param token DetailAST token
     */
    private void checkCommentsInsideToken(DetailAST token) {
        final List<DetailAST> childNodes = new LinkedList<>();
        DetailAST childNode = token.getLastChild();
        while (childNode != null) {
            if (childNode.getType() == TokenTypes.MODIFIERS) {
                for (DetailAST node = token.getFirstChild().getLastChild();
                         node != null;
                         node = node.getPreviousSibling()) {
                    if (isCommentInBeginningOfLine(node)) {
                        childNodes.add(node);
                    }
                }
            }
            else if (isCommentInBeginningOfLine(childNode)) {
                childNodes.add(childNode);
            }
            childNode = childNode.getPreviousSibling();
        }
        for (DetailAST node : childNodes) {
            if (hasEmptyLineBefore(node) && isPrePreviousLineEmpty(node)) {
                log(node, MSG_MULTIPLE_LINES, node.getText());
            }
        }
    }

    /**
     * Checks if a token has empty pre-previous line.
     * @param token DetailAST token.
     * @return true, if token has empty lines before.
     */
    private boolean isPrePreviousLineEmpty(DetailAST token) {
        boolean result = false;
        final int lineNo = token.getLineNo();
        // 3 is the number of the pre-previous line because the numbering starts from zero.
        final int number = 3;
        if (lineNo >= number) {
            final String prePreviousLine = getLines()[lineNo - number];
            result = CommonUtil.isBlank(prePreviousLine);
        }
        return result;
    }

    /**
     * Checks if token have empty line after.
     * @param token token.
     * @return true if token have empty line after.
     */
    private boolean hasEmptyLineAfter(DetailAST token) {
        DetailAST lastToken = token.getLastChild().getLastChild();
        if (lastToken == null) {
            lastToken = token.getLastChild();
        }
        DetailAST nextToken = token.getNextSibling();
        if (isComment(nextToken)) {
            nextToken = nextToken.getNextSibling();
        }
        // Start of the next token
        final int nextBegin = nextToken.getLineNo();
        // End of current token.
        final int currentEnd = lastToken.getLineNo();
        return hasEmptyLine(currentEnd + 1, nextBegin - 1);
    }

    /**
     * Finds comment in next sibling of given packageDef.
     *
     * @param packageDef token to check
     * @return comment under the token
     */
    private static Optional<DetailAST> findCommentUnder(DetailAST packageDef) {
        return Optional.ofNullable(packageDef.getNextSibling())
            .map(sibling -> sibling.findFirstToken(TokenTypes.MODIFIERS))
            .map(DetailAST::getFirstChild)
            .filter(EmptyLineSeparatorCheck::isComment)
            .filter(comment -> comment.getLineNo() == packageDef.getLineNo() + 1);
    }

    /**
     * Checks, whether there are empty lines within the specified line range. Line numbering is
     * started from 1 for parameter values
     * @param startLine number of the first line in the range
     * @param endLine number of the second line in the range
     * @return {@code true} if found any blank line within the range, {@code false}
     *         otherwise
     */
    private boolean hasEmptyLine(int startLine, int endLine) {
        // Initial value is false - blank line not found
        boolean result = false;
        final FileContents fileContents = getFileContents();
        for (int line = startLine; line <= endLine; line++) {
            // Check, if the line is blank. Lines are numbered from 0, so subtract 1
            if (fileContents.lineIsBlank(line - 1)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Checks if a token has a empty line before.
     * @param token token.
     * @return true, if token have empty line before.
     */
    private boolean hasEmptyLineBefore(DetailAST token) {
        boolean result = false;
        final int lineNo = token.getLineNo();
        if (lineNo != 1) {
            // [lineNo - 2] is the number of the previous line as the numbering starts from zero.
            final String lineBefore = getLines()[lineNo - 2];
            result = CommonUtil.isBlank(lineBefore);
        }
        return result;
    }

    /**
     * Check if token is comment, which starting in beginning of line.
     * @param comment comment token for check.
     * @return true, if token is comment, which starting in beginning of line.
     */
    private boolean isCommentInBeginningOfLine(DetailAST comment) {
        // [comment.getLineNo() - 1] is the number of the previous line as the numbering starts
        // from zero.
        boolean result = false;
        if (comment != null) {
            final String lineWithComment = getLines()[comment.getLineNo() - 1].trim();
            result = lineWithComment.startsWith("//") || lineWithComment.startsWith("/*");
        }
        return result;
    }

    /**
     * Check if token is preceded by javadoc comment.
     * @param token token for check.
     * @return true, if token is preceded by javadoc comment.
     */
    private static boolean isPrecededByJavadoc(DetailAST token) {
        boolean result = false;
        final DetailAST previous = token.getPreviousSibling();
        if (previous.getType() == TokenTypes.BLOCK_COMMENT_BEGIN
                && JavadocUtil.isJavadocComment(previous.getFirstChild().getText())) {
            result = true;
        }
        return result;
    }

    /**
     * Check if token is a comment.
     * @param ast ast node
     * @return true, if given ast is comment.
     */
    private static boolean isComment(DetailAST ast) {
        return ast.getType() == TokenTypes.SINGLE_LINE_COMMENT
                   || ast.getType() == TokenTypes.BLOCK_COMMENT_BEGIN;
    }

    /**
     * If variable definition is a type field.
     * @param variableDef variable definition.
     * @return true variable definition is a type field.
     */
    private static boolean isTypeField(DetailAST variableDef) {
        final int parentType = variableDef.getParent().getParent().getType();
        return parentType == TokenTypes.CLASS_DEF;
    }

}
