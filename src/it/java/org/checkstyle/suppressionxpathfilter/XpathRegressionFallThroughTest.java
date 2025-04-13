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
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.FallThroughCheck;

public class XpathRegressionFallThroughTest extends AbstractXpathTestSupport {

    private final String checkName = FallThroughCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testFallThrough() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathFallThrough.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(FallThroughCheck.class);

        final String[] expectedViolation = {
            "11:13: " + getCheckMessage(FallThroughCheck.class,
                FallThroughCheck.MSG_FALL_THROUGH),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathFallThrough']]"
                + "/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_SWITCH/CASE_GROUP["
                + "./LITERAL_CASE/EXPR/NUM_INT[@text='2']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathFallThrough']]"
                + "/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_SWITCH/CASE_GROUP/LITERAL_CASE"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testDefaultCase() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathFallThroughDefaultCase.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(FallThroughCheck.class);
        moduleConfig.addProperty("checkLastCaseGroup", "true");

        final String[] expectedViolation = {
            "10:17: " + getCheckMessage(FallThroughCheck.class,
                FallThroughCheck.MSG_FALL_THROUGH_LAST),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathFallThroughDefaultCase']]"
                + "/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='methodFallThruCustomWords']]/SLIST/LITERAL_WHILE/SLIST"
                + "/LITERAL_SWITCH/CASE_GROUP[./SLIST/EXPR/POST_INC/IDENT[@text='i']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathFallThroughDefaultCase']]"
                + "/OBJBLOCK/METHOD_DEF["
                + "./IDENT[@text='methodFallThruCustomWords']]/SLIST/LITERAL_WHILE/SLIST"
                + "/LITERAL_SWITCH/CASE_GROUP/LITERAL_DEFAULT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
