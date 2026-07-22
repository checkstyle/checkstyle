///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
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

    /** Specifies the lines of the file being processed. */
    private String[] fileLines;

    /**
     * Creates a new {@code JavadocLeadingAsteriskAlignCheck} instance.
     */
    public JavadocLeadingAsteriskAlignCheck() {
        // no code by default
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.LEADING_ASTERISK,
            JavadocCommentsTokenTypes.LEADING_ASTERISKS,
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
            final int columnNumberTabsExpanded = getColumnNumberTabsExpanded(ast);

            if (!hasValidAlignment(expectedColumnNumberTabsExpanded, columnNumberTabsExpanded)) {
                log(ast, MSG_KEY, columnNumberTabsExpanded, expectedColumnNumberTabsExpanded);
            }
        }
    }

    @Override
    public void finishJavadocTree(DetailNode rootAst) {
        // this method checks the alignment of closing javadoc tag.
        final DetailAST javadocEndToken = getBlockCommentAst().getLastChild();
        final String lastLine = fileLines[javadocEndToken.getLineNo() - 1];
        final Optional<Integer> endingBlockColumnNumber = getAsteriskColumnNumber(lastLine);

        endingBlockColumnNumber
                .filter(columnNumber -> columnNumber - 1 == javadocEndToken.getColumnNo())
                .ifPresent(columnNumber -> {
                    final int columnNumberTabsExpanded = CommonUtil.lengthExpandedTabs(
                            lastLine, columnNumber, getTabWidth());

                    if (!hasValidAlignment(
                            expectedColumnNumberTabsExpanded, columnNumberTabsExpanded)) {
                        log(javadocEndToken, MSG_KEY,
                                columnNumberTabsExpanded, expectedColumnNumberTabsExpanded);
                    }
                });
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
     * Returns the tab-expanded, one-based column number of the leading asterisk node.
     *
     * @param ast leading asterisk node
     * @return tab-expanded column number
     */
    private int getColumnNumberTabsExpanded(DetailNode ast) {
        return 1 + CommonUtil.lengthExpandedTabs(
                fileLines[ast.getLineNumber() - 1],
                ast.getColumnNumber(),
                getTabWidth());
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
