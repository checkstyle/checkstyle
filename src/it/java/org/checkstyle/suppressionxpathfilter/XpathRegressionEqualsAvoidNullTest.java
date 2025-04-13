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
import com.puppycrawl.tools.checkstyle.checks.coding.EqualsAvoidNullCheck;

public class XpathRegressionEqualsAvoidNullTest extends AbstractXpathTestSupport {

    private static final Class<EqualsAvoidNullCheck> CLAZZ = EqualsAvoidNullCheck.class;

    @Override
    protected String getCheckName() {
        return CLAZZ.getSimpleName();
    }

    @Test
    public void testEquals() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathEqualsAvoidNull.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(CLAZZ);

        final String[] expectedViolation = {
            "6:26: " + getCheckMessage(CLAZZ,
                    EqualsAvoidNullCheck.MSG_EQUALS_AVOID_NULL),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsAvoidNull']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/EXPR",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsAvoidNull']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/EXPR/METHOD_CALL");

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testEqualsIgnoreCase() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathEqualsAvoidNullIgnoreCase.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(CLAZZ);

        final String[] expectedViolation = {
            "6:36: " + getCheckMessage(CLAZZ,
                    EqualsAvoidNullCheck.MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsAvoidNullIgnoreCase']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/EXPR",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsAvoidNullIgnoreCase']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/EXPR/METHOD_CALL");

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
