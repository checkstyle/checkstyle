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
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalTypeCheck;

public class XpathRegressionIllegalTypeTest extends AbstractXpathTestSupport {

    private final String checkName = IllegalTypeCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathIllegalTypeOne.java"));
        final DefaultConfiguration moduleConfig =
            createModuleConfig(IllegalTypeCheck.class);
        moduleConfig.addProperty("tokens", "METHOD_DEF");
        final String[] expectedViolation = {
            "4:23: " + getCheckMessage(IllegalTypeCheck.class,
                IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
        };
        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT"
                + "/CLASS_DEF[./IDENT[@text='InputXpathIllegalTypeOne']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='typeParam']]/TYPE_PARAMETERS/TYPE_PARAMETER"
                + "[./IDENT[@text='T']]/TYPE_UPPER_BOUNDS/DOT"
                + "[./IDENT[@text='HashSet']]/DOT/IDENT[@text='java']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathIllegalTypeTwo.java"));
        final DefaultConfiguration moduleConfig =
            createModuleConfig(IllegalTypeCheck.class);

        moduleConfig.addProperty("illegalClassNames", "Boolean");

        final String[] expectedViolation = {
            "6:20: " + getCheckMessage(IllegalTypeCheck.class,
                IllegalTypeCheck.MSG_KEY, "Boolean"),
        };
        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathIllegalTypeTwo']"
                + "]/OBJBLOCK/METHOD_DEF[./IDENT[@text='typeParam']]/TYPE_PARAMETERS/"
                + "TYPE_PARAMETER[./IDENT[@text='T']]/TYPE_UPPER_BOUNDS/IDENT[@text='Boolean']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }
}
