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
import com.puppycrawl.tools.checkstyle.checks.design.ThrowsCountCheck;

public class XpathRegressionThrowsCountTest extends AbstractXpathTestSupport {

    private final String checkName = ThrowsCountCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testDefault() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathThrowsCountDefault.java"));
        final DefaultConfiguration moduleConfig =
                createModuleConfig(ThrowsCountCheck.class);
        final String[] expectedViolation = {
            "4:30: " + getCheckMessage(ThrowsCountCheck.class,
                        ThrowsCountCheck.MSG_KEY, 5, 4),
        };
        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                    + "/CLASS_DEF[./IDENT[@text='InputXpathThrowsCountDefault']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='myFunction']]"
                    + "/LITERAL_THROWS[./IDENT[@text='CloneNotSupportedException']]"

        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testCustomMax() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathThrowsCountCustomMax.java"));
        final DefaultConfiguration moduleConfig =
                createModuleConfig(ThrowsCountCheck.class);

        moduleConfig.addProperty("max", "2");

        final String[] expectedViolation = {
            "4:30: " + getCheckMessage(ThrowsCountCheck.class,
                        ThrowsCountCheck.MSG_KEY, 3, 2),
        };
        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                    + "/INTERFACE_DEF[./IDENT[@text='InputXpathThrowsCountCustomMax']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='myFunction']]"
                    + "/LITERAL_THROWS[./IDENT[@text='IllegalStateException']]"

        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testPrivateMethods() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathThrowsCountPrivateMethods.java"));
        final DefaultConfiguration moduleConfig =
                createModuleConfig(ThrowsCountCheck.class);

        moduleConfig.addProperty("ignorePrivateMethods", "false");

        final String[] expectedViolation = {
            "9:40: " + getCheckMessage(ThrowsCountCheck.class,
                        ThrowsCountCheck.MSG_KEY, 5, 4),
        };
        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                    + "/CLASS_DEF[./IDENT[@text='InputXpathThrowsCountPrivateMethods']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='myFunc']]"
                    + "/SLIST/VARIABLE_DEF[./IDENT[@text='foo']]"
                    + "/ASSIGN/EXPR/LITERAL_NEW[./IDENT[@text='myClass']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='privateFunc']]"
                    + "/LITERAL_THROWS[./IDENT[@text='CloneNotSupportedException']]"

        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
