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
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.ExplicitInitializationCheck;

public class XpathRegressionExplicitInitializationTest extends XpathTestSupport {

    @Test
    public void testOne() throws Exception {
        final String checkName = ExplicitInitializationCheck.class.getSimpleName();
        final File fileToProcess =
                new File(getPath(checkName,
                        "SuppressionXpathRegressionExplicitOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ExplicitInitializationCheck.class);

        final String[] expectedViolation = {
            "4:17: " + getCheckMessage(ExplicitInitializationCheck.class,
                ExplicitInitializationCheck.MSG_KEY, "a", 0),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/CLASS_DEF[@text='SuppressionXpathRegressionExplicitOne']"
                        + "/OBJBLOCK/VARIABLE_DEF[@text='a']/IDENT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final String checkName = ExplicitInitializationCheck.class.getSimpleName();
        final File fileToProcess =
                new File(getPath(checkName,
                        "SuppressionXpathRegressionExplicitTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ExplicitInitializationCheck.class);

        final String[] expectedViolation = {
            "6:20: " + getCheckMessage(ExplicitInitializationCheck.class,
                ExplicitInitializationCheck.MSG_KEY, "bar", "null"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/CLASS_DEF[@text='SuppressionXpathRegressionExplicitTwo']/OBJBLOCK"
                        + "/VARIABLE_DEF[@text='bar']/IDENT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
