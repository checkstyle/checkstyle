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

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.GlobalStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks the alignment of
 * <a href="https://docs.oracle.com/en/java/javase/14/docs/specs/javadoc/doc-comment-spec.html#leading-asterisks">
 * leading asterisks</a> in a Javadoc comment. The Check ensures that leading asterisks
 * are aligned vertically under the second asterisk ( &#42; )
 * of opening Javadoc tag. The alignment of closing Javadoc tag ( &#42;/ ) is also checked.
 * If a line contains a non-whitespace character before the leading asterisk then that line will be
 * ignored. Same applies for closing Javadoc tag.
 * If the ending javadoc line contains a leading asterisk, then that leading asterisk's alignment
 * will be considered, the closing Javadoc tag will be ignored.
 * </p>
 * <p>
 * If you're using tabs then specify the the tab width in the
 * <a href="https://checkstyle.org/config.html#tabWidth">tabWidth</a> property.
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
 * @since 10.17.0
 */
@GlobalStatefulCheck
public class JavadocLeadingAsteriskAlignCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "javadoc.asterisk.indentation";

    /** Specifies the column number of starting block of the javadoc comment with tabs expanded. */
    private int expectedColumnNumber;

    /** Specifies the line number of starting block of the javadoc comment. */
    private int javadocStartLineNumber;

    /**
     * Specifies the column number of the leading asterisk
     * where tabs are converted to a single whitespace.
     */
    private int singleSpaceAsteriskColNumber;

    /** Specifies the lines of the file being processed. */
    private String[] fileLines;

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

    @Override
    public void beginJavadocTree(DetailNode rootAst) {
        // this method processes and sets information of starting javadoc tag.
        fileLines = getLines();
        final String startLine = fileLines[rootAst.getLineNumber() - 1];
        javadocStartLineNumber = rootAst.getLineNumber();
        expectedColumnNumber = CommonUtil.lengthExpandedTabs(
            startLine, rootAst.getColumnNumber() - 1, getTabWidth());
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        // this method checks the alignment of leading asterisks.
        final boolean isJavadocStartingLine = ast.getLineNumber() == javadocStartLineNumber;
        if (!isJavadocStartingLine) {
            final Optional<Integer> asteriskColumnNumber =
                                        getAsteriskColumnNumberWithTabsExpanded(ast.getText());

            asteriskColumnNumber.ifPresent(integer -> {
                checkAsteriskAlignment(ast.getLineNumber(),
                        expectedColumnNumber,
                        integer);
            });
        }
    }

    @Override
    public void finishJavadocTree(DetailNode rootAst) {
        // this method checks the alignment of closing javadoc tag.
        final DetailAST javadocEndToken = getBlockCommentAst().getLastChild();
        final String lastLine = fileLines[javadocEndToken.getLineNo() - 1];
        final Optional<Integer> asteriskColumnNumber =
                    getAsteriskColumnNumberWithTabsExpanded(lastLine);

        asteriskColumnNumber.ifPresent(integer -> {
            checkAsteriskAlignment(javadocEndToken.getLineNo(),
                    expectedColumnNumber,
                    integer);
        });
    }

    /**
     * Processes and returns an Optional containing
     * the column number of leading asterisk with tabs expanded.
     * Also sets 'singleSpaceAsteriskColNumber' if the leading asterisk is present.
     *
     * @param line single line from block comment text
     * @return column number of leading asterisk with tabs expanded
     */
    private Optional<Integer> getAsteriskColumnNumberWithTabsExpanded(String line) {
        Optional<Integer> columnNumber = getAsteriskColumnNumber(line);

        if (columnNumber.isPresent()) {
            singleSpaceAsteriskColNumber = columnNumber.orElseThrow();
            columnNumber = Optional.of(CommonUtil.lengthExpandedTabs(
                        line, columnNumber.orElseThrow(), getTabWidth()));
        }

        return columnNumber;
    }

    /**
     * Processes and returns an Optional containing
     * the column number of leading asterisk without tabs expanded.
     *
     * @param line javadoc comment line
     * @return asterisk's column number
     */
    private static Optional<Integer> getAsteriskColumnNumber(String line) {
        final Pattern pattern = Pattern.compile("^(\\s*)\\*");
        final Matcher matcher = pattern.matcher(line);
        final Optional<Integer> columnNumber;

        // We may not always have a leading asterisk because a javadoc line can start with
        // a non-whitespace character or the javadoc line can be empty.
        // In such cases, there is no leading asterisk and Optional will be empty.
        if (matcher.find()) {
            columnNumber = Optional.of(matcher.group(1).length() + 1);
        }
        else {
            columnNumber = Optional.empty();
        }

        return columnNumber;
    }

    /**
     * Checks alignment of asterisks and logs violations.
     *
     * @param lineNumber line number of current comment line
     * @param expectedColNumber column number of javadoc starting token
     * @param asteriskColNumber leading asterisk's column number
     */
    private void checkAsteriskAlignment(int lineNumber,
                                        int expectedColNumber,
                                        int asteriskColNumber) {

        final boolean hasValidAlignment = checkColumnDifference(expectedColNumber,
                                                                asteriskColNumber);

        if (!hasValidAlignment) {
            log(lineNumber,
                singleSpaceAsteriskColNumber - 1,
                MSG_KEY,
                asteriskColNumber,
                expectedColNumber);
        }
    }

    /**
     * Checks the column difference between
     * expected column number and leading asterisk column number.
     *
     * @param expectedColNumber column number of javadoc starting token
     * @param asteriskColNumber column number of leading asterisk
     * @return true if the asterisk is aligned properly, false otherwise
     */
    private static boolean checkColumnDifference(int expectedColNumber,
                                                 int asteriskColNumber) {
        return expectedColNumber - asteriskColNumber == 0;
    }
}
