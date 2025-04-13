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
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceBeforeCheck;

public class XpathRegressionNoWhitespaceBeforeTest extends AbstractXpathTestSupport {

    private final String checkName = NoWhitespaceBeforeCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testNoWhitespaceBefore() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathNoWhitespaceBefore.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(NoWhitespaceBeforeCheck.class);

        final String[] expectedViolation = {
            "4:13: " + getCheckMessage(NoWhitespaceBeforeCheck.class,
                NoWhitespaceBeforeCheck.MSG_KEY, ";"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNoWhitespaceBefore']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='bad']]/SEMI"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testTokens() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathNoWhitespaceBeforeTokens.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(NoWhitespaceBeforeCheck.class);
        moduleConfig.addProperty("tokens", "DOT");

        final String[] expectedViolation = {
            "4:17: " + getCheckMessage(NoWhitespaceBeforeCheck.class,
                NoWhitespaceBeforeCheck.MSG_KEY, "."),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNoWhitespaceBeforeTokens']]"
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
            new File(getPath("InputXpathNoWhitespaceBeforeLineBreaks.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(NoWhitespaceBeforeCheck.class);
        moduleConfig.addProperty("allowLineBreaks", "false");

        final String[] expectedViolation = {
            "6:13: " + getCheckMessage(NoWhitespaceBeforeCheck.class,
                NoWhitespaceBeforeCheck.MSG_KEY, ","),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNoWhitespaceBeforeLineBreaks']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='array']]"
                + "/ASSIGN/ARRAY_INIT/COMMA"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }
}
