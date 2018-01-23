////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtils;

/**
 * Checks for empty line separators after header, package, all import declarations,
 * fields, constructors, methods, nested classes,
 * static initializers and instance initializers.
 *
 * <p> By default the check will check the following statements:
 *  {@link TokenTypes#PACKAGE_DEF PACKAGE_DEF},
 *  {@link TokenTypes#IMPORT IMPORT},
 *  {@link TokenTypes#CLASS_DEF CLASS_DEF},
 *  {@link TokenTypes#INTERFACE_DEF INTERFACE_DEF},
 *  {@link TokenTypes#STATIC_INIT STATIC_INIT},
 *  {@link TokenTypes#INSTANCE_INIT INSTANCE_INIT},
 *  {@link TokenTypes#METHOD_DEF METHOD_DEF},
 *  {@link TokenTypes#CTOR_DEF CTOR_DEF},
 *  {@link TokenTypes#VARIABLE_DEF VARIABLE_DEF}.
 * </p>
 *
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
 * class Foo
 * {
 *     public static final int FOO_CONST = 1;
 *     public void foo() {} //should be separated from previous statement.
 * }
 * </pre>
 *
 * <p> An example of how to configure the check with default parameters is:
 * </p>
 *
 * <pre>
 * &lt;module name="EmptyLineSeparator"/&gt;
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
 * class Foo
 * {
 *     public static final int FOO_CONST = 1;
 *
 *     public void foo() {}
 * }
 * </pre>
 * <p> An example how to check empty line after
 * {@link TokenTypes#VARIABLE_DEF VARIABLE_DEF} and
 * {@link TokenTypes#METHOD_DEF METHOD_DEF}:
 * </p>
 *
 * <pre>
 * &lt;module name="EmptyLineSeparator"&gt;
 *    &lt;property name="tokens" value="VARIABLE_DEF, METHOD_DEF"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>
 * An example how to allow no empty line between fields:
 * </p>
 * <pre>
 * &lt;module name="EmptyLineSeparator"&gt;
 *    &lt;property name="allowNoEmptyLineBetweenFields" value="true"/&gt;
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
 * class Foo
 * {
 *     public static final int FOO_CONST = 1;
 *
 *
 *
 *     public void foo() {}
 * }
 * </pre>
 * <p>
 * An example how to disallow multiple empty lines between class members:
 * </p>
 * <pre>
 * &lt;module name="EmptyLineSeparator"&gt;
 *    &lt;property name="allowMultipleEmptyLines" value="false"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>
 * An example how to disallow multiple empty line inside methods, constructors, etc.:
 * </p>
 * <pre>
 * &lt;module name="EmptyLineSeparator"&gt;
 *    &lt;property name="allowMultipleEmptyLinesInsideClassMembers" value="false"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p> The check is valid only for statements that have body:
 * {@link TokenTypes#CLASS_DEF},
 * {@link TokenTypes#INTERFACE_DEF},
 * {@link TokenTypes#ENUM_DEF},
 * {@link TokenTypes#STATIC_INIT},
 * {@link TokenTypes#INSTANCE_INIT},
 * {@link TokenTypes#METHOD_DEF},
 * {@link TokenTypes#CTOR_DEF}
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
 * class Foo
 * {
 *
 *     public void foo() {
 *
 *
 *          System.out.println(1); // violation since method has 2 empty lines subsequently
 *     }
 * }
 * </pre>
 * @author maxvetrenko
 * @author <a href="mailto:nesterenko-aleksey@list.ru">Aleksey Nesterenko</a>
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

    /** Allows no empty line between fields. */
    private boolean allowNoEmptyLineBetweenFields;

    /** Allows multiple empty lines between class members. */
    private boolean allowMultipleEmptyLines = true;

    /** Allows multiple empty lines inside class members. */
    private boolean allowMultipleEmptyLinesInsideClassMembers = true;

    /**
     * Allow no empty line between fields.
     * @param allow
     *        User's value.
     */
    public final void setAllowNoEmptyLineBetweenFields(boolean allow) {
        allowNoEmptyLineBetweenFields = allow;
    }

    /**
     * Allow multiple empty lines between class members.
     * @param allow User's value.
     */
    public void setAllowMultipleEmptyLines(boolean allow) {
        allowMultipleEmptyLines = allow;
    }

    /**
     * Allow multiple empty lines inside class members.
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
        return CommonUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (hasMultipleLinesBefore(ast)) {
            log(ast.getLineNo(), MSG_MULTIPLE_LINES, ast.getText());
        }
        if (!allowMultipleEmptyLinesInsideClassMembers) {
            processMultipleLinesInside(ast);
        }

        DetailAST nextToken = ast.getNextSibling();
        while (nextToken != null && isComment(nextToken)) {
            nextToken = nextToken.getNextSibling();
        }
        if (nextToken != null) {
            final int astType = ast.getType();
            switch (astType) {
                case TokenTypes.VARIABLE_DEF:
                    processVariableDef(ast, nextToken);
                    break;
                case TokenTypes.IMPORT:
                    processImport(ast, nextToken, astType);
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
    }

    /**
     * Log violation in case there are multiple empty lines inside constructor,
     * initialization block or method.
     * @param ast the ast to check.
     */
    private void processMultipleLinesInside(DetailAST ast) {
        final int astType = ast.getType();
        if (astType != TokenTypes.CLASS_DEF && isClassMemberBlock(astType)) {
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
                if (ast.getFirstChild().getChildCount() == 0 && !isPrecededByJavadoc(ast)) {
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
     * @param astType token Type
     */
    private void processImport(DetailAST ast, DetailAST nextToken, int astType) {
        if (astType != nextToken.getType() && !hasEmptyLineAfter(ast)) {
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
        return allowNoEmptyLineBetweenFields
                    && detailAST.getType() != TokenTypes.VARIABLE_DEF
                    && detailAST.getType() != TokenTypes.RCURLY
                || !allowNoEmptyLineBetweenFields
                    && detailAST.getType() != TokenTypes.RCURLY;
    }

    /**
     * Checks if a token has empty two previous lines and multiple empty lines is not allowed.
     * @param token DetailAST token
     * @return true, if token has empty two lines before and allowMultipleEmptyLines is false
     */
    private boolean hasNotAllowedTwoEmptyLinesBefore(DetailAST token) {
        boolean result = false;
        if (!allowMultipleEmptyLines && hasEmptyLineBefore(token)) {
            final List<EnumSet<LineFlag>> lineFlags = categorizeLines();
            for (int i = token.getLineNo() - 2; i > 0 && !result; i--) {
                final EnumSet<LineFlag> flags = lineFlags.get(i);
                if (flags.contains(LineFlag.EMPTY)) {
                    // 1 line above line being checked is empty.
                    final EnumSet<LineFlag> previousLineFlags = lineFlags.get(i - 1);
                    if (previousLineFlags.contains(LineFlag.EMPTY)) {
                        // 2 lines above the line being checked
                        // and after the previous token are empty.
                        result = true;
                    }
                }
                else if (flags.contains(LineFlag.CODE)) {
                    // We've hit some code, there aren't multiple adjacent empty lines.
                    break;
                }
            }
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
        if (startLine <= endLine) {
            final FileContents fileContents = getFileContents();
            for (int line = startLine; line <= endLine; line++) {
                // Check, if the line is blank. Lines are numbered from 0, so subtract 1
                if (fileContents.lineIsBlank(line - 1)) {
                    result = true;
                    break;
                }
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
            final List<EnumSet<LineFlag>> flags = categorizeLines();
            for (int i = lineNo - 2; i >= 0; i--) {
                final EnumSet<LineFlag> lineFlags = flags.get(i);
                if (!lineFlags.contains(LineFlag.COMMENT)) {
                    if (lineFlags.contains(LineFlag.EMPTY)) {
                        // Line is empty.
                        result = true;
                        break;
                    }
                }
            }
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
                && JavadocUtils.isJavadocComment(previous.getFirstChild().getText())) {
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

    /**
     * Parses the files lines and assigns them flags depending on whether the line is code,
     * comment, and/or empty.
     *
     * @return list of flags for each line.
     */
    private List<EnumSet<LineFlag>> categorizeLines() {
        final EmptyLineSeparatorParser parser = new EmptyLineSeparatorParser();
        parser.processLines(getLines());
        return parser.getFlags();
    }

    /**
     * Flags that apply to source code file lines.
     */
    private enum LineFlag {

        /**
         * Line contains code.
         */
        CODE,

        /**
         * Line contains a comment.
         */
        COMMENT,

        /**
         * Line leaves a comment open.
         */
        COMMENT_OPEN,

        /**
         * Line is empty. It doesn't contain any comments or code, but may contain whitespace.
         */
        EMPTY
    }

    /**
     * Parses a Java source file to assign each line a set of flags based on whether they have code,
     * comments, and if they leave a multi-line comment open.
     */
    private static class EmptyLineSeparatorParser {

        /**
         * List of flags for each line.
         */
        private final List<EnumSet<LineFlag>> flags = new ArrayList<>();

        /**
         * The flags for the previously parsed line.
         */
        private EnumSet<LineFlag> previousLineFlags;

        /**
         * The flags for the line currently being processed.
         */
        private EnumSet<LineFlag> currentLineFlags;

        /**
         * The current line being processed.
         */
        private String currentLine;

        /**
         * The index of the current character being checked.
         */
        private int currentCharIndex;

        /**
         * Processes each given line up to the specified line.
         *
         * @param lines      the lines to process.
         */
        public void processLines(final String... lines) {
            for (final String line : lines) {
                processLine(line);
            }
        }

        /**
         * Processes the given line.
         *
         * @param line the line to process.
         */
        public void processLine(final String line) {
            currentLine = line;
            currentCharIndex = 0;
            startLine();
            while (currentCharIndex < line.length()) {
                processChar();
                currentCharIndex++;
            }
            endLine();
        }

        /**
         * Method called at the start of each line.
         */
        private void startLine() {
            currentLineFlags = EnumSet.noneOf(LineFlag.class);
            if (previousLineFlags != null && previousLineFlags.contains(LineFlag.COMMENT_OPEN)) {
                currentLineFlags.add(LineFlag.COMMENT);
                currentLineFlags.add(LineFlag.COMMENT_OPEN);
            }
            else {
                currentLineFlags.add(LineFlag.EMPTY);
            }
        }

        /**
         * Processes the current character.
         */
        private void processChar() {
            checkEmpty();
        }

        /**
         * Checks whether the current character means that the line is not empty.
         */
        private void checkEmpty() {
            if (!Character.isWhitespace(currentChar())) {
                // Line contains non-whitespace character, it isn't empty.
                currentLineFlags.remove(LineFlag.EMPTY);
                checkQuoteEscape();
            }
        }

        /**
         * Checks whether the current character escapes the following character in a string literal.
         */
        private void checkQuoteEscape() {
            if (currentChar() == '\\') {
                // Skip current and next char, it won't terminate the string.
                currentCharIndex++;
            }
            else {
                checkEndComment();
            }
        }

        /**
         * Checks whether the current character terminates a multi-line comment.
         */
        private void checkEndComment() {
            if (currentLineFlags.contains(LineFlag.COMMENT_OPEN) && currentChar() == '*'
                    && lineHasNextChar() && nextChar() == '/') {
                // End of multi-line comment.
                currentLineFlags.remove(LineFlag.COMMENT_OPEN);
                // Skip current and next char.
                currentCharIndex++;
            }
            else {
                checkStartComment();
            }
        }

        /**
         * Checks whether the current character marks the start of a multi-line comment.
         */
        private void checkStartComment() {
            if (currentChar() == '/' && lineHasNextChar() && nextChar() == '*') {
                // Start of multi-line comment.
                currentLineFlags.add(LineFlag.COMMENT);
                currentLineFlags.add(LineFlag.COMMENT_OPEN);
                // Skip next character.
                currentCharIndex++;
            }
            else {
                checkStartSingleLineComment();
            }
        }

        /**
         * Checks whether the current character marks the start of a single-line comment.
         */
        private void checkStartSingleLineComment() {
            if (currentChar() == '/' && lineHasNextChar()
                && !currentLineFlags.contains(LineFlag.COMMENT_OPEN)) {
                // nextChar() can only be another slash for a valid Java file
                // since it's not a multi-line comment.
                currentLineFlags.add(LineFlag.COMMENT);

                // Skip the rest of the line.
                currentCharIndex = currentLine.length();
            }
            else {
                checkCode();
            }
        }

        /**
         * Checks whether the current character is normal source code.
         */
        private void checkCode() {
            if (!currentLineFlags.contains(LineFlag.COMMENT_OPEN)) {
                // Line contains non-whitespace character outside of comment, this is code.
                currentLineFlags.add(LineFlag.CODE);
            }
        }

        /**
         * Method called after the current line has finished processing.
         */
        private void endLine() {
            flags.add(currentLineFlags);
            previousLineFlags = currentLineFlags;
            currentLineFlags = null;
        }

        /**
         * Returns the current character being processed.
         *
         * @return the current character.
         */
        private char currentChar() {
            return currentLine.charAt(currentCharIndex);
        }

        /**
         * Returns the character that immediately follows the character currently being processed.
         *
         * @return the next character.
         */
        private char nextChar() {
            return currentLine.charAt(currentCharIndex + 1);
        }

        /**
         * Determines whether the current line has a character immediately following the character
         * currently being processed.
         *
         * @return whether there is a next character.
         */
        private boolean lineHasNextChar() {
            return currentCharIndex < currentLine.length() - 1;
        }

        /**
         * Returns the flags for the lines that have been processed.
         *
         * @return the line flags.
         */
        public List<EnumSet<LineFlag>> getFlags() {
            return Collections.unmodifiableList(flags);
        }

    }
}
