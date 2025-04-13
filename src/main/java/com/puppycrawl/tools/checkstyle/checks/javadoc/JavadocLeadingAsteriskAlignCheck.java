///
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
///

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
 * <div>
 * Checks the alignment of
 * <a href="https://docs.oracle.com/en/java/javase/14/docs/specs/javadoc/doc-comment-spec.html#leading-asterisks">
 * leading asterisks</a> in a Javadoc comment. The Check ensures that leading asterisks
 * are aligned vertically under the first asterisk ( &#42; )
 * of opening Javadoc tag. The alignment of closing Javadoc tag ( &#42;/ ) is also checked.
 * If a closing Javadoc tag contains non-whitespace character before it
 * then it's alignment will be ignored.
 * If the ending javadoc line contains a leading asterisk, then that leading asterisk's alignment
 * will be considered, the closing Javadoc tag will be ignored.
 * </div>
 *
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
 * @since 10.18.0
 */
@GlobalStatefulCheck
public class JavadocLeadingAsteriskAlignCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "javadoc.asterisk.indentation";

    /** Specifies the line number of starting block of the javadoc comment. */
    private int javadocStartLineNumber;

    /** Specifies the column number of starting block of the javadoc comment with tabs expanded. */
    private int expectedColumnNumberTabsExpanded;

    /**
     * Specifies the column number of the leading asterisk
     * without tabs expanded.
     */
    private int expectedColumnNumberWithoutExpandedTabs;

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
        expectedColumnNumberTabsExpanded = CommonUtil.lengthExpandedTabs(
            startLine, rootAst.getColumnNumber() - 1, getTabWidth());
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        // this method checks the alignment of leading asterisks.
        final boolean isJavadocStartingLine = ast.getLineNumber() == javadocStartLineNumber;

        if (!isJavadocStartingLine) {
            final Optional<Integer> leadingAsteriskColumnNumber =
                                        getAsteriskColumnNumber(ast.getText());

            leadingAsteriskColumnNumber
                    .map(columnNumber -> expandedTabs(ast.getText(), columnNumber))
                    .filter(columnNumber -> {
                        return !hasValidAlignment(expectedColumnNumberTabsExpanded, columnNumber);
                    })
                    .ifPresent(columnNumber -> {
                        logViolation(ast.getLineNumber(),
                                columnNumber,
                                expectedColumnNumberTabsExpanded);
                    });
        }
    }

    @Override
    public void finishJavadocTree(DetailNode rootAst) {
        // this method checks the alignment of closing javadoc tag.
        final DetailAST javadocEndToken = getBlockCommentAst().getLastChild();
        final String lastLine = fileLines[javadocEndToken.getLineNo() - 1];
        final Optional<Integer> endingBlockColumnNumber = getAsteriskColumnNumber(lastLine);

        endingBlockColumnNumber
                .map(columnNumber -> expandedTabs(lastLine, columnNumber))
                .filter(columnNumber -> {
                    return !hasValidAlignment(expectedColumnNumberTabsExpanded, columnNumber);
                })
                .ifPresent(columnNumber -> {
                    logViolation(javadocEndToken.getLineNo(),
                            columnNumber,
                            expectedColumnNumberTabsExpanded);
                });
    }

    /**
     * Processes and returns the column number of
     * leading asterisk with tabs expanded.
     * Also sets 'expectedColumnNumberWithoutExpandedTabs' if the leading asterisk is present.
     *
     * @param line javadoc comment line
     * @param columnNumber column number of leading asterisk
     * @return column number of leading asterisk with tabs expanded
     */
    private int expandedTabs(String line, int columnNumber) {
        expectedColumnNumberWithoutExpandedTabs = columnNumber - 1;
        return CommonUtil.lengthExpandedTabs(
                    line, columnNumber, getTabWidth());
    }

    /**
     * Processes and returns an OptionalInt containing
     * the column number of leading asterisk without tabs expanded.
     *
     * @param line javadoc comment line
     * @return asterisk's column number
     */
    private static Optional<Integer> getAsteriskColumnNumber(String line) {
        final Pattern pattern = Pattern.compile("^(\\s*)\\*");
        final Matcher matcher = pattern.matcher(line);

        // We may not always have a leading asterisk because a javadoc line can start with
        // a non-whitespace character or the javadoc line can be empty.
        // In such cases, there is no leading asterisk and Optional will be empty.
        return Optional.of(matcher)
                .filter(Matcher::find)
                .map(matcherInstance -> matcherInstance.group(1))
                .map(groupLength -> groupLength.length() + 1);
    }

    /**
     * Checks alignment of asterisks and logs violations.
     *
     * @param lineNumber line number of current comment line
     * @param asteriskColNumber column number of leading asterisk
     * @param expectedColNumber column number of javadoc starting token
     */
    private void logViolation(int lineNumber,
                              int asteriskColNumber,
                              int expectedColNumber) {

        log(lineNumber,
            expectedColumnNumberWithoutExpandedTabs,
            MSG_KEY,
            asteriskColNumber,
            expectedColNumber);
    }

    /**
     * Checks the column difference between
     * expected column number and leading asterisk column number.
     *
     * @param expectedColNumber column number of javadoc starting token
     * @param asteriskColNumber column number of leading asterisk
     * @return true if the asterisk is aligned properly, false otherwise
     */
    private static boolean hasValidAlignment(int expectedColNumber,
                                             int asteriskColNumber) {
        return expectedColNumber - asteriskColNumber == 0;
    }
}
