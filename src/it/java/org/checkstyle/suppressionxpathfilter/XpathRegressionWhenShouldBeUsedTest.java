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
///////////////////////////////////////////////////////////////////////////////////////////////

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.WhenShouldBeUsedCheck;

public class XpathRegressionWhenShouldBeUsedTest
        extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return WhenShouldBeUsedCheck.class.getSimpleName();
    }

    @Test
    public void testSimple() throws Exception {
        final File fileToProcess =
                new File(getNonCompilablePath(
                        "InputXpathWhenShouldBeUsedSimple.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(WhenShouldBeUsedCheck.class);
        final String[] expectedViolation = {
            "7:13: " + getCheckMessage(WhenShouldBeUsedCheck.class,
                    WhenShouldBeUsedCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
               "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathWhenShouldBeUsedSimple']]"
               + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_SWITCH/SWITCH_RULE"
               + "[./LITERAL_CASE/PATTERN_VARIABLE_DEF/IDENT[@text='s']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathWhenShouldBeUsedSimple']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/"
                        + "LITERAL_SWITCH/SWITCH_RULE/LITERAL_CASE"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testNested() throws Exception {
        final File fileToProcess =
                new File(getNonCompilablePath(
                        "InputXpathWhenShouldBeUsedNested.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(WhenShouldBeUsedCheck.class);
        final String[] expectedViolation = {
            "10:21: " + getCheckMessage(WhenShouldBeUsedCheck.class,
                    WhenShouldBeUsedCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathWhenShouldBeUsedNested']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/"
                + "VARIABLE_DEF[./IDENT[@text='x']]/ASSIGN/EXPR/LITERAL_SWITCH"
                + "/SWITCH_RULE/SLIST/"
                + "LITERAL_SWITCH/SWITCH_RULE[./LITERAL_CASE/PATTERN_VARIABLE_DEF/"
                + "IDENT[@text='_']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathWhenShouldBeUsedNested']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/VARIABLE_DEF"
                + "[./IDENT[@text='x']]/ASSIGN/EXPR/LITERAL_SWITCH/"
                + "SWITCH_RULE/SLIST/"
                + "LITERAL_SWITCH/SWITCH_RULE/LITERAL_CASE"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
