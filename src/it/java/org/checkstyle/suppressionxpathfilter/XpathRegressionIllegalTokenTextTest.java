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
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck;

public class XpathRegressionIllegalTokenTextTest extends AbstractXpathTestSupport {

    private final String checkName = IllegalTokenTextCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testField() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathIllegalTokenTextField.java"));
        final DefaultConfiguration moduleConfig =
            createModuleConfig(IllegalTokenTextCheck.class);
        moduleConfig.addProperty("format", "12345");
        moduleConfig.addProperty("tokens", "NUM_INT");
        final String[] expectedViolation = {
            "4:33: " + getCheckMessage(IllegalTokenTextCheck.class,
                IllegalTokenTextCheck.MSG_KEY, "12345"),
        };
        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT"
                + "/CLASS_DEF[./IDENT[@text='InputXpathIllegalTokenTextField']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='illegalNumber']]"
                + "/ASSIGN/EXPR[./NUM_INT[@text='12345']]",
            "/COMPILATION_UNIT"
                + "/CLASS_DEF[./IDENT[@text='InputXpathIllegalTokenTextField']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='illegalNumber']]"
                + "/ASSIGN/EXPR/NUM_INT[@text='12345']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testMethod() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathIllegalTokenTextMethod.java"));
        final DefaultConfiguration moduleConfig =
            createModuleConfig(IllegalTokenTextCheck.class);
        moduleConfig.addProperty("format", "forbiddenText");
        moduleConfig.addProperty("tokens", "STRING_LITERAL");
        final String[] expectedViolation = {
            "5:32: " + getCheckMessage(IllegalTokenTextCheck.class,
                IllegalTokenTextCheck.MSG_KEY, "forbiddenText"),
        };
        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT"
                + "/CLASS_DEF[./IDENT[@text='InputXpathIllegalTokenTextMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='myMethod']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='illegalString']]"
                + "/ASSIGN/EXPR[./STRING_LITERAL[@text='forbiddenText']]",
            "/COMPILATION_UNIT"
                + "/CLASS_DEF[./IDENT[@text='InputXpathIllegalTokenTextMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='myMethod']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='illegalString']]"
                + "/ASSIGN/EXPR/STRING_LITERAL[@text='forbiddenText']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testInterface() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathIllegalTokenTextInterface.java"));
        final DefaultConfiguration moduleConfig =
            createModuleConfig(IllegalTokenTextCheck.class);
        moduleConfig.addProperty("format", "invalidIdentifier");
        moduleConfig.addProperty("tokens", "IDENT");
        final String[] expectedViolation = {
            "4:10: " + getCheckMessage(IllegalTokenTextCheck.class,
                IllegalTokenTextCheck.MSG_KEY, "invalidIdentifier"),
        };
        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT"
                + "/INTERFACE_DEF[./IDENT[@text='InputXpathIllegalTokenTextInterface']]"
                + "/OBJBLOCK/METHOD_DEF/IDENT[@text='invalidIdentifier']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

}
