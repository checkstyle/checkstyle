///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks for empty line separators before package, all import declarations,
 * fields, constructors, methods, nested classes,
 * static initializers and instance initializers.
 * </div>
 *
 * <p>
 * Checks for empty line separators before not only statements but
 * implementation and documentation comments and blocks as well.
 * </p>
 *
 * <p>
 * ATTENTION: empty line separator is required between token siblings,
 * not after line where token is found.
 * If token does not have a sibling of the same type, then empty line
 * is required at its end (for example for CLASS_DEF it is after '}').
 * Also, trailing comments are skipped.
 * </p>
 * <ul>
 * <li>
 * Property {@code allowMultipleEmptyLines} - Allow multiple empty lines between class members.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code allowMultipleEmptyLinesInsideClassMembers} - Allow multiple
 * empty lines inside class members.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code allowNoEmptyLineBetweenFields} - Allow no empty line between fields.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
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
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#STATIC_INIT">
 * STATIC_INIT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INSTANCE_INIT">
 * INSTANCE_INIT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_DEF">
 * CTOR_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#VARIABLE_DEF">
 * VARIABLE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RECORD_DEF">
 * RECORD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#COMPACT_CTOR_DEF">
 * COMPACT_CTOR_DEF</a>.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code empty.line.separator}
 * </li>
 * <li>
 * {@code empty.line.separator.multiple.lines}
 * </li>
 * <li>
 * {@code empty.line.separator.multiple.lines.after}
 * </li>
 * <li>
 * {@code empty.line.separator.multiple.lines.inside}
 * </li>
 * </ul>
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

    /** Allow no empty line between fields. */
    private boolean allowNoEmptyLineBetweenFields;

    /** Allow multiple empty lines between class members. */
    private boolean allowMultipleEmptyLines = true;

    /** Allow multiple empty lines inside class members. */
    private boolean allowMultipleEmptyLinesInsideClassMembers = true;

    /**
     * Setter to allow no empty line between fields.
     *
     * @param allow
     *        User's value.
     * @since 5.8
     */
    public final void setAllowNoEmptyLineBetweenFields(boolean allow) {
        allowNoEmptyLineBetweenFields = allow;
    }

    /**
     * Setter to allow multiple empty lines between class members.
     *
     * @param allow User's value.
     * @since 6.3
     */
    public void setAllowMultipleEmptyLines(boolean allow) {
        allowMultipleEmptyLines = allow;
    }

    /**
     * Setter to allow multiple empty lines inside class members.
     *
     * @param allow User's value.
     * @since 6.18
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
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
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
            log(ast, MSG_MULTIPLE_LINES, ast.getText());
        }
        if (!allowMultipleEmptyLinesInsideClassMembers) {
            processMultipleLinesInside(ast);
        }
        if (ast.getType() == TokenTypes.PACKAGE_DEF) {
            checkCommentInModifiers(ast);
        }
        DetailAST nextToken = ast.getNextSibling();
        while (nextToken != null && TokenUtil.isCommentType(nextToken.getType())) {
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
                        final DetailAST result = getLastElementBeforeEmptyLines(ast,
                                nextToken.getLineNo());
                        log(result, MSG_MULTIPLE_LINES_AFTER, result.getText());
                    }
                }
                else if (!hasEmptyLineAfter(ast)) {
                    log(nextToken, MSG_SHOULD_BE_SEPARATED,
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
        comment.ifPresent(commentValue -> {
            log(commentValue, MSG_SHOULD_BE_SEPARATED, commentValue.getText());
        });
    }

    /**
     * Log violation in case there are multiple empty lines inside constructor,
     * initialization block or method.
     *
     * @param ast the ast to check.
     */
    private void processMultipleLinesInside(DetailAST ast) {
        final int astType = ast.getType();
        if (isClassMemberBlock(astType)) {
            final List<Integer> emptyLines = getEmptyLines(ast);
            final List<Integer> emptyLinesToLog = getEmptyLinesToLog(emptyLines);
            for (Integer lineNo : emptyLinesToLog) {
                log(getLastElementBeforeEmptyLines(ast, lineNo), MSG_MULTIPLE_LINES_INSIDE);
            }
        }
    }

    /**
     * Returns the element after which empty lines exist.
     *
     * @param ast the ast to check.
     * @param line the empty line which gives violation.
     * @return The DetailAST after which empty lines are present.
     */
    private static DetailAST getLastElementBeforeEmptyLines(DetailAST ast, int line) {
        DetailAST result = ast;
        if (ast.getFirstChild().getLineNo() <= line) {
            result = ast.getFirstChild();
            while (result.getNextSibling() != null
                    && result.getNextSibling().getLineNo() <= line) {
                result = result.getNextSibling();
            }
            if (result.hasChildren()) {
                result = getLastElementBeforeEmptyLines(result, line);
            }
        }

        if (result.getNextSibling() != null) {
            final Optional<DetailAST> postFixNode = getPostFixNode(result.getNextSibling());
            if (postFixNode.isPresent()) {
                // A post fix AST will always have a sibling METHOD CALL
                // METHOD CALL will at least have two children
                // The first child is DOT in case of POSTFIX which have at least 2 children
                // First child of DOT again puts us back to normal AST tree which will
                // recurse down below from here
                final DetailAST firstChildAfterPostFix = postFixNode.orElseThrow();
                result = getLastElementBeforeEmptyLines(firstChildAfterPostFix, line);
            }
        }
        return result;
    }

    /**
     * Gets postfix Node from AST if present.
     *
     * @param ast the AST used to get postfix Node.
     * @return Optional postfix node.
     */
    private static Optional<DetailAST> getPostFixNode(DetailAST ast) {
        Optional<DetailAST> result = Optional.empty();
        if (ast.getType() == TokenTypes.EXPR
            // EXPR always has at least one child
            && ast.getFirstChild().getType() == TokenTypes.METHOD_CALL) {
            // METHOD CALL always has at two least child
            final DetailAST node = ast.getFirstChild().getFirstChild();
            if (node.getType() == TokenTypes.DOT) {
                result = Optional.of(node);
            }
        }
        return result;
    }

    /**
     * Whether the AST is a class member block.
     *
     * @param astType the AST to check.
     * @return true if the AST is a class member block.
     */
    private static boolean isClassMemberBlock(int astType) {
        return TokenUtil.isOfType(astType,
            TokenTypes.STATIC_INIT, TokenTypes.INSTANCE_INIT, TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF, TokenTypes.COMPACT_CTOR_DEF);
    }

    /**
     * Get list of empty lines.
     *
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

        for (int lineNo = ast.getLineNo(); lineNo <= lastTokenLineNo; lineNo++) {
            if (CommonUtil.isBlank(getLine(lineNo))) {
                emptyLines.add(lineNo);
            }
        }
        return emptyLines;
    }

    /**
     * Get list of empty lines to log.
     *
     * @param emptyLines list of empty lines.
     * @return list of empty lines to log.
     */
    private static List<Integer> getEmptyLinesToLog(Iterable<Integer> emptyLines) {
        final List<Integer> emptyLinesToLog = new ArrayList<>();
        int previousEmptyLineNo = -1;
        for (int emptyLineNo : emptyLines) {
            if (previousEmptyLineNo + 1 == emptyLineNo) {
                emptyLinesToLog.add(previousEmptyLineNo);
            }
            previousEmptyLineNo = emptyLineNo;
        }
        return emptyLinesToLog;
    }

    /**
     * Whether the token has not allowed multiple empty lines before.
     *
     * @param ast the ast to check.
     * @return true if the token has not allowed multiple empty lines before.
     */
    private boolean hasMultipleLinesBefore(DetailAST ast) {
        return (ast.getType() != TokenTypes.VARIABLE_DEF || isTypeField(ast))
                && hasNotAllowedTwoEmptyLinesBefore(ast);
    }

    /**
     * Process Package.
     *
     * @param ast token
     * @param nextToken next token
     */
    private void processPackage(DetailAST ast, DetailAST nextToken) {
        if (ast.getLineNo() > 1 && !hasEmptyLineBefore(ast)) {
            if (CheckUtil.isPackageInfo(getFilePath())) {
                if (!ast.getFirstChild().hasChildren() && !isPrecededByJavadoc(ast)) {
                    log(ast, MSG_SHOULD_BE_SEPARATED, ast.getText());
                }
            }
            else {
                log(ast, MSG_SHOULD_BE_SEPARATED, ast.getText());
            }
        }
        if (isLineEmptyAfterPackage(ast)) {
            final DetailAST elementAst = getViolationAstForPackage(ast);
            log(elementAst, MSG_SHOULD_BE_SEPARATED, elementAst.getText());
        }
        else if (ast.getLineNo() > 1 && !hasEmptyLineAfter(ast)) {
            log(nextToken, MSG_SHOULD_BE_SEPARATED, nextToken.getText());
        }
    }

    /**
     * Checks if there is another element at next line of package declaration.
     *
     * @param ast Package ast.
     * @return true, if there is an element.
     */
    private static boolean isLineEmptyAfterPackage(DetailAST ast) {
        DetailAST nextElement = ast;
        final int lastChildLineNo = ast.getLastChild().getLineNo();
        while (nextElement.getLineNo() < lastChildLineNo + 1
                && nextElement.getNextSibling() != null) {
            nextElement = nextElement.getNextSibling();
        }
        return nextElement.getLineNo() == lastChildLineNo + 1;
    }

    /**
     * Gets the Ast on which violation is to be given for package declaration.
     *
     * @param ast Package ast.
     * @return Violation ast.
     */
    private static DetailAST getViolationAstForPackage(DetailAST ast) {
        DetailAST nextElement = ast;
        final int lastChildLineNo = ast.getLastChild().getLineNo();
        while (nextElement.getLineNo() < lastChildLineNo + 1) {
            nextElement = nextElement.getNextSibling();
        }
        return nextElement;
    }

    /**
     * Process Import.
     *
     * @param ast token
     * @param nextToken next token
     */
    private void processImport(DetailAST ast, DetailAST nextToken) {
        if (!TokenUtil.isOfType(nextToken, TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT)
            && !hasEmptyLineAfter(ast)) {
            log(nextToken, MSG_SHOULD_BE_SEPARATED, nextToken.getText());
        }
    }

    /**
     * Process Variable.
     *
     * @param ast token
     * @param nextToken next Token
     */
    private void processVariableDef(DetailAST ast, DetailAST nextToken) {
        if (isTypeField(ast) && !hasEmptyLineAfter(ast)
                && isViolatingEmptyLineBetweenFieldsPolicy(nextToken)) {
            log(nextToken, MSG_SHOULD_BE_SEPARATED,
                    nextToken.getText());
        }
    }

    /**
     * Checks whether token placement violates policy of empty line between fields.
     *
     * @param detailAST token to be analyzed
     * @return true if policy is violated and warning should be raised; false otherwise
     */
    private boolean isViolatingEmptyLineBetweenFieldsPolicy(DetailAST detailAST) {
        return detailAST.getType() != TokenTypes.RCURLY
                && (!allowNoEmptyLineBetweenFields
                    || !TokenUtil.isOfType(detailAST, TokenTypes.COMMA, TokenTypes.VARIABLE_DEF));
    }

    /**
     * Checks if a token has empty two previous lines and multiple empty lines is not allowed.
     *
     * @param token DetailAST token
     * @return true, if token has empty two lines before and allowMultipleEmptyLines is false
     */
    private boolean hasNotAllowedTwoEmptyLinesBefore(DetailAST token) {
        return !allowMultipleEmptyLines && hasEmptyLineBefore(token)
                && isPrePreviousLineEmpty(token);
    }

    /**
     * Check if group of comments located right before token has more than one previous empty line.
     *
     * @param token DetailAST token
     */
    private void checkComments(DetailAST token) {
        if (!allowMultipleEmptyLines) {
            if (TokenUtil.isOfType(token,
                TokenTypes.PACKAGE_DEF, TokenTypes.IMPORT,
                TokenTypes.STATIC_IMPORT, TokenTypes.STATIC_INIT)) {
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
     *
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
     *
     * @param token DetailAST token.
     * @return true, if token has empty lines before.
     */
    private boolean isPrePreviousLineEmpty(DetailAST token) {
        boolean result = false;
        final int lineNo = token.getLineNo();
        // 3 is the number of the pre-previous line because the numbering starts from zero.
        final int number = 3;
        if (lineNo >= number) {
            final String prePreviousLine = getLine(lineNo - number);

            result = CommonUtil.isBlank(prePreviousLine);

            if (!result && token.getPreviousSibling() != null
                    && token.getPreviousSibling().getType() != TokenTypes.BLOCK_COMMENT_BEGIN
                    && token.findFirstToken(TokenTypes.TYPE) != null) {
                final DetailAST beginningCommentBlock = token.findFirstToken(TokenTypes.TYPE)
                    .findFirstToken(TokenTypes.BLOCK_COMMENT_BEGIN);

                if (beginningCommentBlock != null) {
                    result = CommonUtil.isBlank(
                        getLine(beginningCommentBlock.getLineNo() - number));
                }
            }
        }
        return result;
    }

    /**
     * Checks if token have empty line after.
     *
     * @param token token.
     * @return true if token have empty line after.
     */
    private boolean hasEmptyLineAfter(DetailAST token) {
        DetailAST lastToken = token.getLastChild().getLastChild();
        if (lastToken == null) {
            lastToken = token.getLastChild();
        }
        DetailAST nextToken = token.getNextSibling();
        if (TokenUtil.isCommentType(nextToken.getType())) {
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
            .filter(token -> TokenUtil.isCommentType(token.getType()))
            .filter(comment -> comment.getLineNo() == packageDef.getLineNo() + 1);
    }

    /**
     * Checks, whether there are empty lines within the specified line range. Line numbering is
     * started from 1 for parameter values
     *
     * @param startLine number of the first line in the range
     * @param endLine number of the second line in the range
     * @return {@code true} if found any blank line within the range, {@code false}
     *         otherwise
     */
    private boolean hasEmptyLine(int startLine, int endLine) {
        // Initial value is false - blank line not found
        boolean result = false;
        for (int line = startLine; line <= endLine; line++) {
            // Check, if the line is blank. Lines are numbered from 0, so subtract 1
            if (CommonUtil.isBlank(getLine(line - 1))) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Checks if a token has an empty line before.
     *
     * @param token token.
     * @return true, if token have empty line before.
     */
    private boolean hasEmptyLineBefore(DetailAST token) {
        boolean result = false;
        final int lineNo = token.getLineNo();
        if (lineNo != 1) {
            // [lineNo - 2] is the number of the previous line as the numbering starts from zero.
            final String lineBefore = getLine(lineNo - 2);

            result = CommonUtil.isBlank(lineBefore);

            if (!result && token.getPreviousSibling() != null
                    && token.getPreviousSibling().getType() != TokenTypes.BLOCK_COMMENT_BEGIN
                    && token.findFirstToken(TokenTypes.TYPE) != null) {
                final DetailAST beginningCommentBlock = token.findFirstToken(TokenTypes.TYPE)
                    .findFirstToken(TokenTypes.BLOCK_COMMENT_BEGIN);

                if (beginningCommentBlock != null) {
                    result = CommonUtil.isBlank(
                        getLine(beginningCommentBlock.getLineNo() - 2));
                }
            }
        }
        return result;
    }

    /**
     * Check if token is comment, which starting in beginning of line.
     *
     * @param comment comment token for check.
     * @return true, if token is comment, which starting in beginning of line.
     */
    private boolean isCommentInBeginningOfLine(DetailAST comment) {
        // comment.getLineNo() - 1 is the number of the previous line as the numbering starts
        // from zero.
        boolean result = false;
        if (comment != null) {
            final String lineWithComment = getLine(comment.getLineNo() - 1).trim();
            result = lineWithComment.startsWith("//") || lineWithComment.startsWith("/*");
        }
        return result;
    }

    /**
     * Check if token is preceded by javadoc comment.
     *
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
     * If variable definition is a type field.
     *
     * @param variableDef variable definition.
     * @return true variable definition is a type field.
     */
    private static boolean isTypeField(DetailAST variableDef) {
        return TokenUtil.isTypeDeclaration(variableDef.getParent().getParent().getType());
    }

}
