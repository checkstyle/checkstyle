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

package org.checkstyle.suppressionxpathfilter.indentation;

import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.indentation.CommentsIndentationCheck;

public class XpathRegressionCommentsIndentationTest extends AbstractXpathTestSupport {

    private final String checkName = CommentsIndentationCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/indentation/commentsindentation";
    }

    @Test
    public void testSingleLine() throws Exception {
        final String[] expectedViolation = {
            "12:9: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 11, 8, 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathCommentsIndentationSingleLine']]"
                + "/OBJBLOCK/SINGLE_LINE_COMMENT[./COMMENT_CONTENT[@text=' Comment // warn\\n']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathCommentsIndentationSingleLine.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testBlock() throws Exception {
        final String[] expectedViolation = {
            "11:11: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.block", 14, 10, 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathCommentsIndentationBlock']]/OBJBLOCK/"
                + "VARIABLE_DEF[./IDENT[@text='f']]/TYPE/BLOCK_COMMENT_BEGIN[./COMMENT_CONTENT"
                + "[@text=' // warn\\n           * Javadoc comment\\n           ']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathCommentsIndentationBlock.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testSeparator() throws Exception {
        final String[] expectedViolation = {
            "15:13: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 17, 12, 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathCommentsIndentationSeparator']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]/MODIFIERS/SINGLE_LINE_COMMENT"
                + "[./COMMENT_CONTENT[@text='///////////// Comment separator // warn\\n']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathCommentsIndentationSeparator.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testDistributedStatement() throws Exception {
        final String[] expectedViolation = {
            "17:25: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 15, 24, 8),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathCommentsIndentationDistributedStatement']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]/SLIST/SINGLE_LINE_COMMENT"
                + "[./COMMENT_CONTENT[@text=' Comment // warn\\n']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathCommentsIndentationDistributedStatement.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testSingleLineBlock() throws Exception {
        final String[] expectedViolation = {
            "13:1: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 14, 0, 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathCommentsIndentationSingleLineBlock']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]/SLIST/SINGLE_LINE_COMMENT"
                + "[./COMMENT_CONTENT[@text=' block Comment // warn\\n']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathCommentsIndentationSingleLineBlock.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testNonEmptyCase() throws Exception {
        final String[] expectedViolation = {
            "17:20: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", "16, 18", 19, "16, 12"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathCommentsIndentationNonEmptyCase']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]/SLIST/LITERAL_SWITCH/"
                + "CASE_GROUP/SINGLE_LINE_COMMENT[./COMMENT_CONTENT[@text=' Comment // warn\\n']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathCommentsIndentationNonEmptyCase.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testEmptyCase() throws Exception {
        final String[] expectedViolation = {
            "16:1: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", "15, 17", 0, "12, 12"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathCommentsIndentationEmptyCase']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]/SLIST/LITERAL_SWITCH/"
                + "CASE_GROUP/SINGLE_LINE_COMMENT[./COMMENT_CONTENT[@text=' Comment // warn\\n']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathCommentsIndentationEmptyCase.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testWithinBlockStatement() throws Exception {
        final String[] expectedViolation = {
            "13:9: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 14, 8, 12),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathCommentsIndentationWithinBlockStatement']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]/SLIST/VARIABLE_DEF"
                + "[./IDENT[@text='s']]/ASSIGN/EXPR/PLUS[./STRING_LITERAL[@text='O']]"
                + "/SINGLE_LINE_COMMENT[./COMMENT_CONTENT[@text=' Comment // warn\\n']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathCommentsIndentationWithinBlockStatement.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
