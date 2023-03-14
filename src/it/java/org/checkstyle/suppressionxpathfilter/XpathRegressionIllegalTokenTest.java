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
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenCheck;

public class XpathRegressionIllegalTokenTest extends AbstractXpathTestSupport {

    private final String checkName = IllegalTokenCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionIllegalToken1.java"));
        final DefaultConfiguration moduleConfig =
                createModuleConfig(IllegalTokenCheck.class);
        final String[] expectedViolation = {
            "5:10: " + getCheckMessage(IllegalTokenCheck.class,
                        IllegalTokenCheck.MSG_KEY, "outer:"),
        };
        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                    + "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionIllegalToken1']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='myTest']]"
                    + "/SLIST/LABELED_STAT[./IDENT[@text='outer']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionIllegalToken2.java"));
        final DefaultConfiguration moduleConfig =
                createModuleConfig(IllegalTokenCheck.class);

        moduleConfig.addProperty("tokens", "LITERAL_NATIVE");

        final String[] expectedViolation = {
            "4:10: " + getCheckMessage(IllegalTokenCheck.class,
                        IllegalTokenCheck.MSG_KEY, "native"),
        };
        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionIllegalToken2']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='myTest']]"
                        + "/MODIFIERS/LITERAL_NATIVE"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
