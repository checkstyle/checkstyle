///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.grammar.java21;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractTreeTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

public class Java21AstRegressionTest extends AbstractTreeTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar/java21";
    }

    @Test
    public void testUnnamedVariableBasic() throws Exception {
        verifyAst(
                getPath(
                        "ExpectedUnnamedVariableBasic.txt"),
                getPath(
                        "InputUnnamedVariableBasic.java"));
    }

    @Test
    public void testUnnamedVariableSwitch() throws Exception {
        verifyAst(
                getPath(
                        "ExpectedUnnamedVariableSwitch.txt"),
                getPath(
                        "InputUnnamedVariableSwitch.java"));
    }

    @Test
    public void testTextBlockConsecutiveEscapes() throws Exception {
        verifyAst(
                getPath(
                        "ExpectedTextBlockConsecutiveEscapes.txt"),
                getPath(
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
                new File(getPath("InputTextBlockParsingFail.java.fail"));

        final Throwable throwable =
                getExpectedThrowable(CheckstyleException.class,
                        () -> JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS),
                        "Exception should be thrown due to parsing failure."
                );

        assertWithMessage("Cause of CheckstyleException should be IllegalStateException.")
            .that(throwable.getCause())
            .isInstanceOf(IllegalStateException.class);

        assertWithMessage("Message of IllegalStateException should contain the parsing failure.")
                .that(throwable.getCause().getMessage())
                .contains("13:14: mismatched input '}\\n"
                        + "            ' expecting TEXT_BLOCK_LITERAL_END");

    }
}
