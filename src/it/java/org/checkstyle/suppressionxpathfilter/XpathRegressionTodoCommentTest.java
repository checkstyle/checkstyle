////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package org.checkstyle.suppressionxpathfilter;

import static com.puppycrawl.tools.checkstyle.checks.TodoCommentCheck.MSG_KEY;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.TodoCommentCheck;

public class XpathRegressionTodoCommentTest extends AbstractXpathTestSupport {
    private final String checkName = TodoCommentCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionTodoCommentOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(TodoCommentCheck.class);
        moduleConfig.addAttribute("format", "FIXME:");

        final String[] expectedViolation = {
            "4:7: " + getCheckMessage(TodoCommentCheck.class, MSG_KEY, "FIXME:"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/CLASS_DEF[./IDENT[@text="
                        + "'SuppressionXpathRegressionTodoCommentOne']]/OBJBLOCK/"
                        + "SINGLE_LINE_COMMENT/COMMENT_CONTENT[@text=' warn FIXME:\\n']");

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionTodoCommentTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(TodoCommentCheck.class);
        moduleConfig.addAttribute("format", "FIXME:");

        final String[] expectedViolation = {
            "4:7: " + getCheckMessage(TodoCommentCheck.class, MSG_KEY, "FIXME:"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/CLASS_DEF[./IDENT[@text="
                        + "'SuppressionXpathRegressionTodoCommentTwo']]/"
                        + "OBJBLOCK/BLOCK_COMMENT_BEGIN/COMMENT_CONTENT"
                        + "[@text=' // warn\\n     * FIXME:\\n     * TODO\\n     ']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
