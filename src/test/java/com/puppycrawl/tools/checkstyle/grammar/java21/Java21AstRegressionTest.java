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

package com.puppycrawl.tools.checkstyle.grammar.java21;

import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.Assert.assertThrows;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractTreeTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

public class Java21AstRegressionTest extends AbstractTreeTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar/java21";
    }

    @Test
    public void testUnnamedVariableBasic() throws Exception {
        verifyAst(
                getNonCompilablePath(
                        "ExpectedUnnamedVariableBasic.txt"),
                getNonCompilablePath(
                        "InputUnnamedVariableBasic.java"));
    }

    @Test
    public void testUnnamedVariableSwitch() throws Exception {
        verifyAst(
                getNonCompilablePath(
                        "ExpectedUnnamedVariableSwitch.txt"),
                getNonCompilablePath(
                        "InputUnnamedVariableSwitch.java"));
    }

    @Test
    public void testTextBlockConsecutiveEscapes() throws Exception {
        verifyAst(
                getNonCompilablePath(
                        "ExpectedTextBlockConsecutiveEscapes.txt"),
                getNonCompilablePath(
                        "InputTextBlockConsecutiveEscapes.java"));
    }

    /**
     * Unusual test case, but important to prevent regressions. We need to
     * make sure that we only consume legal escapes in text blocks, and
     * don't unintentionally parse something that should be an
     * escape as regular text.
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void testTextBlockParsingFail() throws Exception {
        final File file =
                new File(getNonCompilablePath("InputTextBlockParsingFail.java.fail"));

        final Throwable throwable =
                assertThrows("Exception should be thrown due to parsing failure.",
                        CheckstyleException.class,
                        () -> JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS)
                );

        final String incorrectThrowableCauseMessage =
                "Cause of CheckstyleException should be IllegalStateException.";

        assertWithMessage(incorrectThrowableCauseMessage)
                .that(throwable.getCause())
                .isInstanceOf(IllegalStateException.class);

        final String incorrectParsingFailureMessage =
                "Message of IllegalStateException should contain the parsing failure.";

        assertWithMessage(incorrectParsingFailureMessage)
                .that(throwable.getCause().getMessage())
                .contains("13:14: mismatched input '}\\n"
                        + "            ' expecting TEXT_BLOCK_LITERAL_END");

    }
}
