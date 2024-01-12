///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.design.InnerTypeLastCheck.MSG_KEY;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.design.InnerTypeLastCheck;

public class XpathRegressionInnerTypeLastTest extends AbstractXpathTestSupport {

    private final String checkName = InnerTypeLastCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionInnerTypeLastOne.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(InnerTypeLastCheck.class);

        final String[] expectedViolations = {
            "8:5: " + getCheckMessage(InnerTypeLastCheck.class, MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionInnerTypeLastOne']]"
                        + "/OBJBLOCK/CTOR_DEF"
                        + "[./IDENT[@text='SuppressionXpathRegressionInnerTypeLastOne']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionInnerTypeLastOne']]"
                        + "/OBJBLOCK/CTOR_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionInnerTypeLastOne']]"
                        + "/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionInnerTypeLastOne']]"
                        + "/OBJBLOCK/CTOR_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionInnerTypeLastOne']]"
                        + "/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {

        final File filetoProcess =
                new File(getPath("SuppressionXpathRegressionInnerTypeLastTwo.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(InnerTypeLastCheck.class);

        final String[] expectedViolations = {
            "7:5: " + getCheckMessage(InnerTypeLastCheck.class, MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionInnerTypeLastTwo']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='innerMethod']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionInnerTypeLastTwo']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT"
                        + "[@text='innerMethod']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionInnerTypeLastTwo']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT"
                        + "[@text='innerMethod']]/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, filetoProcess, expectedViolations,
                expectedXpathQueries);

    }
}
