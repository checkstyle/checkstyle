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

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.indentation.CommentsIndentationCheck;

public class XpathRegressionCommentsIndentationTest extends AbstractXpathTestSupport {

    private final String checkName = CommentsIndentationCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testSingleLine() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionCommentsIndentation"
                                        + "SingleLine.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(CommentsIndentationCheck.class);

        final String[] expectedViolation = {
            "5:9: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 4, 8, 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='SuppressionXpathRegressionCommentsIndentationSingleLine']]"
                + "/OBJBLOCK/SINGLE_LINE_COMMENT[./COMMENT_CONTENT[@text=' Comment // warn\\n']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testBlock() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionCommentsIndentation"
                                        + "Block.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(CommentsIndentationCheck.class);

        final String[] expectedViolation = {
            "4:11: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.block", 7, 10, 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='SuppressionXpathRegressionCommentsIndentationBlock']]/OBJBLOCK/"
                + "VARIABLE_DEF[./IDENT[@text='f']]/TYPE/BLOCK_COMMENT_BEGIN[./COMMENT_CONTENT"
                + "[@text=' // warn\\n           * Javadoc comment\\n           ']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testSeparator() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionCommentsIndentation"
                                        + "Separator.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(CommentsIndentationCheck.class);

        final String[] expectedViolation = {
            "8:13: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 10, 12, 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='SuppressionXpathRegressionCommentsIndentationSeparator']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]/MODIFIERS/SINGLE_LINE_COMMENT"
                + "[./COMMENT_CONTENT[@text='///////////// Comment separator // warn\\n']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testDistributedStatement() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionCommentsIndentation"
                                        + "DistributedStatement.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(CommentsIndentationCheck.class);

        final String[] expectedViolation = {
            "10:25: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 8, 24, 8),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='SuppressionXpathRegressionCommentsIndentationDistributedStatement']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]/SLIST/SINGLE_LINE_COMMENT"
                + "[./COMMENT_CONTENT[@text=' Comment // warn\\n']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testSingleLineBlock() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionCommentsIndentation"
                                        + "SingleLineBlock.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(CommentsIndentationCheck.class);

        final String[] expectedViolation = {
            "6:1: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 7, 0, 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='SuppressionXpathRegressionCommentsIndentationSingleLineBlock']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]/SLIST/SINGLE_LINE_COMMENT"
                + "[./COMMENT_CONTENT[@text=' block Comment // warn\\n']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testNonEmptyCase() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionCommentsIndentation"
                                        + "NonEmptyCase.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(CommentsIndentationCheck.class);

        final String[] expectedViolation = {
            "10:20: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", "9, 11", 19, "16, 12"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='SuppressionXpathRegressionCommentsIndentationNonEmptyCase']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]/SLIST/LITERAL_SWITCH/"
                + "CASE_GROUP/SINGLE_LINE_COMMENT[./COMMENT_CONTENT[@text=' Comment // warn\\n']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testEmptyCase() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionCommentsIndentation"
                                        + "EmptyCase.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(CommentsIndentationCheck.class);

        final String[] expectedViolation = {
            "9:1: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", "8, 10", 0, "12, 12"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='SuppressionXpathRegressionCommentsIndentationEmptyCase']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]/SLIST/LITERAL_SWITCH/"
                + "CASE_GROUP/SINGLE_LINE_COMMENT[./COMMENT_CONTENT[@text=' Comment // warn\\n']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testWithinBlockStatement() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionCommentsIndentation"
                                        + "WithinBlockStatement.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(CommentsIndentationCheck.class);

        final String[] expectedViolation = {
            "6:9: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 7, 8, 12),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='SuppressionXpathRegressionCommentsIndentationWithinBlockStatement']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]/SLIST/VARIABLE_DEF"
                + "[./IDENT[@text='s']]/ASSIGN/EXPR/PLUS[./STRING_LITERAL[@text='O']]"
                + "/SINGLE_LINE_COMMENT[./COMMENT_CONTENT[@text=' Comment // warn\\n']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
