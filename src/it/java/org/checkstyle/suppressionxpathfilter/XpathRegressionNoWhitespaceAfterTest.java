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

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceAfterCheck;

public class XpathRegressionNoWhitespaceAfterTest extends AbstractXpathTestSupport {

    private final String checkName = NoWhitespaceAfterCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testNoWhitespaceAfter() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathNoWhitespaceAfter.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(NoWhitespaceAfterCheck.class);

        final String[] expectedViolation = {
            "4:15: " + getCheckMessage(NoWhitespaceAfterCheck.class,
                NoWhitespaceAfterCheck.MSG_KEY, "-"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNoWhitespaceAfter']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='bad']]/ASSIGN/EXPR",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNoWhitespaceAfter']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='bad']]/ASSIGN/EXPR/UNARY_MINUS["
                + "./NUM_INT[@text='1']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testTokens() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathNoWhitespaceAfterTokens.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(NoWhitespaceAfterCheck.class);
        moduleConfig.addProperty("tokens", "DOT");

        final String[] expectedViolation = {
            "4:16: " + getCheckMessage(NoWhitespaceAfterCheck.class,
                NoWhitespaceAfterCheck.MSG_KEY, "."),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNoWhitespaceAfterTokens']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/TYPE/DOT[./IDENT[@text='String']]"
                + "/DOT[./IDENT[@text='java']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testAllowLineBreaks() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathNoWhitespaceAfterLineBreaks.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(NoWhitespaceAfterCheck.class);
        moduleConfig.addProperty("allowLineBreaks", "false");

        final String[] expectedViolation = {
            "6:13: " + getCheckMessage(NoWhitespaceAfterCheck.class,
                NoWhitespaceAfterCheck.MSG_KEY, "."),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNoWhitespaceAfterLineBreaks']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='s']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNoWhitespaceAfterLineBreaks']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='s']]"
                + "/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNoWhitespaceAfterLineBreaks']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='s']]/TYPE",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNoWhitespaceAfterLineBreaks']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='s']]"
                + "/TYPE/DOT[./IDENT[@text='String']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }
}
