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
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.TypeNameCheck;

public class XpathRegressionTypeNameTest extends AbstractXpathTestSupport {

    private final String checkName = TypeNameCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void test1() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionTypeName1.java"));

        final String pattern = "^[A-Z][a-zA-Z0-9]*$";
        final DefaultConfiguration moduleConfig =
                createModuleConfig(TypeNameCheck.class);

        final String[] expectedViolation = {
            "5:19: " + getCheckMessage(TypeNameCheck.class,
                        AbstractNameCheck.MSG_INVALID_PATTERN, "SecondName_", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text"
                        + "='SuppressionXpathRegressionTypeName1']]"
                        + "/OBJBLOCK/CLASS_DEF/IDENT[@text='SecondName_']"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void test2() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionTypeName2.java"));

        final String pattern = "^I_[a-zA-Z0-9]*$";
        final DefaultConfiguration moduleConfig =
                createModuleConfig(TypeNameCheck.class);
        moduleConfig.addProperty("format", "^I_[a-zA-Z0-9]*$");
        moduleConfig.addProperty("tokens", "INTERFACE_DEF");

        final String[] expectedViolation = {
            "6:15: " + getCheckMessage(TypeNameCheck.class,
                        AbstractNameCheck.MSG_INVALID_PATTERN, "SecondName", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text"
                        + "='SuppressionXpathRegressionTypeName2']]"
                        + "/OBJBLOCK/INTERFACE_DEF/IDENT[@text='SecondName']"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
