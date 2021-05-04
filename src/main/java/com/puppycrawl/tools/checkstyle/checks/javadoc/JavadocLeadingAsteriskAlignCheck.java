///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.GlobalStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <p>
 * Checks that the
 * <a href="https://docs.oracle.com/en/java/javase/14/docs/specs/javadoc/doc-comment-spec.html#leading-asterisks">
 * leading asterisks</a> are aligned vertically under the second asterisk ( &#42; )
 * of opening Javadoc tag. The alignment of closing Javadoc tag is also checked.
 * If the line does not contain leading asterisk then that line will be ignored.
 * </p>
 * <p>
 * If you're using tabs then specify the the tab width in the
 * <a href="https://checkstyle.org/config.html#tabWidth">tabWidth</a> property.
 * The default value of {@code tabWidth} is 8.
 * </p>
 * <ul>
 * <li>
 * Property {@code violateExecutionOnNonTightHtml} - Control when to print violations if the
 * Javadoc being examined by this check violates the tight html rules defined at
 * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">Tight-HTML Rules</a>.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * </ul>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code javadoc.asterisk.indentation}
 * </li>
 * <li>
 * {@code javadoc.missed.html.close}
 * </li>
 * <li>
 * {@code javadoc.parse.rule.error}
 * </li>
 * <li>
 * {@code javadoc.unclosedHtml}
 * </li>
 * <li>
 * {@code javadoc.wrong.singleton.html.tag}
 * </li>
 * </ul>
 *
 * @since 10.16.0
 */
@GlobalStatefulCheck
public class JavadocLeadingAsteriskAlignCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "javadoc.asterisk.indentation";

    /** Specifies the column number of starting block of the javadoc comment with tabs expanded. */
    private int commentBeginColNumber;

    /** Specifies the line number of starting block of the javadoc comment. */
    private int commentBeginLineNumber;

    /**
     * Specifies the column number of the leading asterisk
     * where tabs are converted to a single whitespace.
     */
    private int singleSpaceAsteriskColNumber;

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocTokenTypes.LEADING_ASTERISK,
        };
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    // get the starting block of javadoc
    @Override
    public void beginJavadocTree(DetailNode rootAst) {
        final String startLine = getLines()[rootAst.getLineNumber() - 1];
        commentBeginLineNumber = rootAst.getLineNumber();
        commentBeginColNumber = CommonUtil.lengthExpandedTabs(
            startLine, rootAst.getColumnNumber() - 1, getTabWidth());
    }

    // process leading asterisks of the javadoc
    @Override
    public void visitJavadocToken(DetailNode ast) {
        if (ast.getLineNumber() != commentBeginLineNumber) {
            final int asteriskColNumber = getAsteriskColumnNumberWithTabsExpanded(ast.getText());

            checkAsteriskAlignment(
                    ast.getLineNumber(),
                    commentBeginColNumber,
                    asteriskColNumber);
        }
    }

    // process the end block of javadoc
    @Override
    public void finishJavadocTree(DetailNode rootAst) {
        final DetailAST javadocEndToken = getBlockCommentAst().getLastChild();

        if (commentBeginLineNumber != javadocEndToken.getLineNo()) {
            final String lastLine = getLastLine(javadocEndToken);
            final int asteriskColNumber = getAsteriskColumnNumberWithTabsExpanded(lastLine);

            checkAsteriskAlignment(
                    javadocEndToken.getLineNo(),
                    commentBeginColNumber,
                    asteriskColNumber);
        }
    }

    /**
     * Processes and sets the last line of javadoc which includes comment's ending token.
     *
     * @param endToken javadoc ending token
     * @return last line of javadoc comment including ending token
     */
    private String getLastLine(DetailAST endToken) {
        final String lastLine;

        if (endToken.getColumnNo() == 0) {
            lastLine = endToken.getText();
        }
        else {
            final String blockComment = JavadocUtil.getBlockCommentContent(getBlockCommentAst());
            final List<String> commentTextLineList = Arrays.asList(blockComment.split("\n"));
            lastLine = commentTextLineList.get(commentTextLineList.size() - 1) + "*/";
        }

        return lastLine;
    }

    /**
     * Processes and returns the column number of leading asterisk from javadoc comment text
     * with tabs expanded.
     * Also sets 'singleSpaceAsteriskColNumber' if the leading asterisk is found.
     *
     * @param line single line from block comment text
     * @return column number of leading asterisk with tabs expanded
     */
    private int getAsteriskColumnNumberWithTabsExpanded(String line) {
        int columnNumber = getAsteriskColumnNumber(line);

        if (columnNumber != -1) {
            singleSpaceAsteriskColNumber = columnNumber;
            columnNumber = CommonUtil.lengthExpandedTabs(line, columnNumber, getTabWidth());
        }

        return columnNumber;
    }

    /**
     * Processes and returns the column number of leading asterisk without tabs expanded.
     * Returns -1 if there is no leading asterisk found.
     *
     * @param line javadoc comment line
     * @return asterisk's column number
     */
    private static int getAsteriskColumnNumber(String line) {
        final Pattern pattern = Pattern.compile("^(\\s*)\\*");
        final Matcher matcher = pattern.matcher(line);
        final int columnNumber;

        if (matcher.find()) {
            columnNumber = matcher.group(1).length() + 1;
        }
        else {
            columnNumber = -1;
        }

        return columnNumber;
    }

    /**
     * Checks alignment of asterisks and logs violations.
     *
     * @param lineNumber line number of current comment line
     * @param commentBeginColumn column number of javadoc starting token
     * @param asteriskColumn column number of current line's leading asterisk
     */
    private void checkAsteriskAlignment(int lineNumber,
                                        int commentBeginColumn,
                                        int asteriskColumn) {

        if (asteriskColumn != -1) {
            final int columnDifference = commentBeginColumn - asteriskColumn;
            if (columnDifference != 0) {
                log(
                    lineNumber,
                    singleSpaceAsteriskColNumber - 1,
                    MSG_KEY,
                    asteriskColumn,
                    commentBeginColNumber);
            }
        }
    }
}
