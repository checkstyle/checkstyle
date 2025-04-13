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
import com.puppycrawl.tools.checkstyle.checks.blocks.AvoidNestedBlocksCheck;

public class XpathRegressionAvoidNestedBlocksTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return AvoidNestedBlocksCheck.class.getSimpleName();
    }

    @Test
    public void testEmpty() throws Exception {
        final File fileToProcess = new File(
                getPath("InputXpathAvoidNestedBlocksEmpty.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AvoidNestedBlocksCheck.class);

        final String[] expectedViolation = {
            "6:9: " + getCheckMessage(AvoidNestedBlocksCheck.class,
                    AvoidNestedBlocksCheck.MSG_KEY_BLOCK_NESTED),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathAvoidNestedBlocksEmpty']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='empty']]/SLIST/SLIST"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testVariableAssignment() throws Exception {
        final File fileToProcess = new File(
                getPath("InputXpathAvoidNestedBlocksVariable.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AvoidNestedBlocksCheck.class);

        final String[] expectedViolation = {
            "7:9: " + getCheckMessage(AvoidNestedBlocksCheck.class,
                    AvoidNestedBlocksCheck.MSG_KEY_BLOCK_NESTED),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathAvoidNestedBlocksVariable']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='varAssign']]/SLIST/SLIST"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testSwitchAllowInSwitchCaseFalse() throws Exception {
        final File fileToProcess = new File(getPath(
                "InputXpathAvoidNestedBlocksNotAllowedInSwitchCase.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AvoidNestedBlocksCheck.class);

        final String[] expectedViolation = {
            "9:21: " + getCheckMessage(AvoidNestedBlocksCheck.class,
                    AvoidNestedBlocksCheck.MSG_KEY_BLOCK_NESTED),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathAvoidNestedBlocksNotAllowedInSwitchCase"
                        + "']]/OBJBLOCK/METHOD_DEF"
                        + "[./IDENT[@text='s']]/SLIST/LITERAL_SWITCH/CASE_GROUP/SLIST",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathAvoidNestedBlocksNotAllowedInSwitchCase"
                        + "']]/OBJBLOCK/METHOD_DEF"
                        + "[./IDENT[@text='s']]/SLIST/LITERAL_SWITCH/CASE_GROUP/SLIST/SLIST"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testSwitchAllowInSwitchCaseTrue() throws Exception {
        final File fileToProcess = new File(
                getPath("InputXpathAvoidNestedBlocksAllowedInSwitchCase.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AvoidNestedBlocksCheck.class);
        moduleConfig.addProperty("allowInSwitchCase", "true");

        final String[] expectedViolation = {
            "11:13: " + getCheckMessage(AvoidNestedBlocksCheck.class,
                    AvoidNestedBlocksCheck.MSG_KEY_BLOCK_NESTED),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathAvoidNestedBlocksAllowedInSwitchCase"
                        + "']]/OBJBLOCK/METHOD_DEF[./IDENT[@text='s']]"
                        + "/SLIST/LITERAL_SWITCH/CASE_GROUP/SLIST/SLIST"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testSwitchWithBreakOutside() throws Exception {
        final File fileToProcess = new File(
                getPath("InputXpathAvoidNestedBlocksBreakOutside.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AvoidNestedBlocksCheck.class);

        final String[] expectedViolation = {
            "8:21: " + getCheckMessage(AvoidNestedBlocksCheck.class,
                    AvoidNestedBlocksCheck.MSG_KEY_BLOCK_NESTED),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathAvoidNestedBlocksBreakOutside']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='s']]"
                        + "/SLIST/LITERAL_SWITCH/CASE_GROUP/SLIST",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathAvoidNestedBlocksBreakOutside']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='s']]"
                        + "/SLIST/LITERAL_SWITCH/CASE_GROUP/SLIST/SLIST"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
