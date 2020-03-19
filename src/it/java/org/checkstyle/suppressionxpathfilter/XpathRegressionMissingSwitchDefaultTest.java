////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.MissingSwitchDefaultCheck;

public class XpathRegressionMissingSwitchDefaultTest extends AbstractXpathTestSupport {

    private final Class<MissingSwitchDefaultCheck> clss = MissingSwitchDefaultCheck.class;

    @Override
    protected String getCheckName() {
        return clss.getSimpleName();
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionMissingSwitchDefaultOne.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(clss);
        final String[] expectedViolation = {
            "6:9: " + getCheckMessage(clss, MissingSwitchDefaultCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionMissingSwitchDefaultOne']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test1']]"
                        + "/SLIST/LITERAL_SWITCH"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionMissingSwitchDefaultTwo.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(clss);

        final String[] expectedViolation = {
            "12:17: " + getCheckMessage(clss, MissingSwitchDefaultCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionMissingSwitchDefaultTwo']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test2']]"
                        + "/SLIST/LITERAL_SWITCH/CASE_GROUP/SLIST",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionMissingSwitchDefaultTwo']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test2']]"
                        + "/SLIST/LITERAL_SWITCH/CASE_GROUP/SLIST/"
                        + "LITERAL_SWITCH"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
