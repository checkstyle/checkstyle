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

import static com.puppycrawl.tools.checkstyle.checks.blocks.NeedBracesCheck.MSG_KEY_NEED_BRACES;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.blocks.NeedBracesCheck;

public class XpathRegressionNeedBracesTest extends AbstractXpathTestSupport {
    private final String checkName = NeedBracesCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testDo() throws Exception {
        final File fileToProcess = new File(getPath(
            "InputXpathNeedBracesDo.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(NeedBracesCheck.class);

        final String[] expectedViolation = {
            "13:9: " + getCheckMessage(NeedBracesCheck.class, MSG_KEY_NEED_BRACES, "do"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNeedBracesDo']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_DO"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testSingleLine() throws Exception {
        final File fileToProcess = new File(getPath(
            "InputXpathNeedBracesSingleLine.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(NeedBracesCheck.class);
        moduleConfig.addProperty("allowSingleLineStatement", "true");

        final String[] expectedViolation = {
            "16:9: " + getCheckMessage(NeedBracesCheck.class, MSG_KEY_NEED_BRACES, "if"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNeedBracesSingleLine']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_IF"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testSingleLineLambda() throws Exception {
        final File fileToProcess = new File(getPath(
            "InputXpathNeedBracesSingleLineLambda.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(NeedBracesCheck.class);
        moduleConfig.addProperty("tokens", "LAMBDA");
        moduleConfig.addProperty("allowSingleLineStatement", "true");

        final String[] expectedViolation = {
            "4:29: " + getCheckMessage(NeedBracesCheck.class, MSG_KEY_NEED_BRACES, "->"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNeedBracesSingleLineLambda']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='r3']]/ASSIGN/LAMBDA"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testEmptyLoopBody() throws Exception {
        final File fileToProcess = new File(getPath(
            "InputXpathNeedBracesEmptyLoopBody.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(NeedBracesCheck.class);

        final String[] expectedViolation = {
            "9:9: " + getCheckMessage(NeedBracesCheck.class, MSG_KEY_NEED_BRACES, "while"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNeedBracesEmptyLoopBody']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_WHILE"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }
}
