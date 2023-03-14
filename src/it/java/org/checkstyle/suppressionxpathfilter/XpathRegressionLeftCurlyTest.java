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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck;
import com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyOption;

public class XpathRegressionLeftCurlyTest extends AbstractXpathTestSupport {

    private final String checkName = LeftCurlyCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionLeftCurlyOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(LeftCurlyCheck.class);

        final String[] expectedViolation = {
            "4:1: " + getCheckMessage(LeftCurlyCheck.class,
                LeftCurlyCheck.MSG_KEY_LINE_PREVIOUS, "{", 1),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='SuppressionXpathRegressionLeftCurlyOne']]/OBJBLOCK",
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='SuppressionXpathRegressionLeftCurlyOne']]/OBJBLOCK/LCURLY"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionLeftCurlyTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(LeftCurlyCheck.class);
        moduleConfig.addProperty("option", LeftCurlyOption.NL.toString());

        final String[] expectedViolation = {
            "3:53: " + getCheckMessage(LeftCurlyCheck.class,
                LeftCurlyCheck.MSG_KEY_LINE_NEW, "{", 53),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='SuppressionXpathRegressionLeftCurlyTwo']]/OBJBLOCK",
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='SuppressionXpathRegressionLeftCurlyTwo']]/OBJBLOCK/LCURLY"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionLeftCurlyThree.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(LeftCurlyCheck.class);

        final String[] expectedViolation = {
            "5:19: " + getCheckMessage(LeftCurlyCheck.class,
                LeftCurlyCheck.MSG_KEY_LINE_BREAK_AFTER, "{", 19),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionLeftCurlyThree']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='sample']]/SLIST/LITERAL_IF/SLIST"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
