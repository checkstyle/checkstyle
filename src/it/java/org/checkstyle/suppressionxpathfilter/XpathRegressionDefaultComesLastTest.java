////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.DefaultComesLastCheck;

public class XpathRegressionDefaultComesLastTest extends XpathTestSupport {

    @Test
    public void testOne() throws Exception {
        final String checkName = DefaultComesLastCheck.class.getSimpleName();
        final File fileToProcess =
                new File(getPath(checkName,
                        "SuppressionXpathRegressionDefaultComesLastOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(DefaultComesLastCheck.class);

        final String[] expectedViolation = {
            "8:13: " + getCheckMessage(DefaultComesLastCheck.class,
                    DefaultComesLastCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/CLASS_DEF[@text='SuppressionXpathRegressionDefaultComesLastOne']/OBJBLOCK"
                + "/METHOD_DEF[@text='test']/SLIST/LITERAL_SWITCH/CASE_GROUP",
            "/CLASS_DEF[@text='SuppressionXpathRegressionDefaultComesLastOne']/OBJBLOCK"
                + "/METHOD_DEF[@text='test']/SLIST/LITERAL_SWITCH/CASE_GROUP"
                + "/LITERAL_DEFAULT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final String checkName = DefaultComesLastCheck.class.getSimpleName();
        final File fileToProcess =
                new File(getPath(checkName,
                        "SuppressionXpathRegressionDefaultComesLastTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(DefaultComesLastCheck.class);
        moduleConfig.addAttribute("skipIfLastAndSharedWithCase", "true");

        final String[] expectedViolation = {
            "15:13: " + getCheckMessage(DefaultComesLastCheck.class,
                DefaultComesLastCheck.MSG_KEY_SKIP_IF_LAST_AND_SHARED_WITH_CASE),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/CLASS_DEF[@text='SuppressionXpathRegressionDefaultComesLastTwo']/OBJBLOCK"
                + "/METHOD_DEF[@text='test']/SLIST/LITERAL_SWITCH/CASE_GROUP/LITERAL_DEFAULT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
