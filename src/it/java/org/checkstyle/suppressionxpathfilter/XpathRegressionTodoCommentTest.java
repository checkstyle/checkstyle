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

package org.checkstyle.suppressionxpathfilter;

import static com.puppycrawl.tools.checkstyle.checks.TodoCommentCheck.MSG_KEY;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.TodoCommentCheck;

public class XpathRegressionTodoCommentTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return TodoCommentCheck.class.getSimpleName();
    }

    @Test
    public void testSingleLine() throws Exception {
        final String[] expectedViolation = {
            "11:7: " + getCheckMessage(TodoCommentCheck.class, MSG_KEY, "FIXME:"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                        + "'InputXpathTodoCommentSingleLine']]/OBJBLOCK/"
                        + "SINGLE_LINE_COMMENT/COMMENT_CONTENT[@text=' warn FIXME:\\n']");

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathTodoCommentSingleLine.java"),
                expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testBlock() throws Exception {
        final String[] expectedViolation = {
            "11:7: " + getCheckMessage(TodoCommentCheck.class, MSG_KEY, "FIXME:"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                        + "'InputXpathTodoCommentBlock']]/"
                        + "OBJBLOCK/BLOCK_COMMENT_BEGIN/COMMENT_CONTENT"
                        + "[@text=' // warn\\n     * FIXME:\\n     * TODO\\n     ']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathTodoCommentBlock.java"),
                expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testInsideMethod() throws Exception {
        final String[] expectedViolation = {
            "12:11: " + getCheckMessage(TodoCommentCheck.class, MSG_KEY, "FIXME:"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                        + "'InputXpathTodoCommentInsideMethod']]/OBJBLOCK/"
                        + "METHOD_DEF[./IDENT[@text='method']]/SLIST/"
                        + "SINGLE_LINE_COMMENT/COMMENT_CONTENT[@text=' FIXME: //warn\\n']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathTodoCommentInsideMethod.java"),
                expectedXpathQueries, expectedViolation);
    }

}
