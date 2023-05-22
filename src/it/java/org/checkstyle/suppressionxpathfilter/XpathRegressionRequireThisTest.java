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
import com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck;

public class XpathRegressionRequireThisTest extends AbstractXpathTestSupport {

    private final String checkName = RequireThisCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionRequireThisOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(RequireThisCheck.class);
        moduleConfig.addProperty("validateOnlyOverlapping", "false");

        final String[] expectedViolation = {
            "7:9: " + getCheckMessage(RequireThisCheck.class,
                RequireThisCheck.MSG_VARIABLE, "age", ""),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionRequireThisOne']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='changeAge']]/SLIST/EXPR/ASSIGN"
                + "/IDENT[@text='age']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionRequireThisTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(RequireThisCheck.class);
        moduleConfig.addProperty("validateOnlyOverlapping", "false");

        final String[] expectedViolation = {
            "9:9: " + getCheckMessage(RequireThisCheck.class,
                RequireThisCheck.MSG_METHOD, "method1", ""),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionRequireThisTwo']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='method2']]/SLIST/EXPR"
                + "/METHOD_CALL/IDENT[@text='method1']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
