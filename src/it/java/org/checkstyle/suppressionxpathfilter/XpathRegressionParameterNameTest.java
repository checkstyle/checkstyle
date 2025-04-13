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
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ParameterNameCheck;

public class XpathRegressionParameterNameTest extends AbstractXpathTestSupport {

    private final String checkName = ParameterNameCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testDefaultPattern() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathParameterNameDefaultPattern.java"));

        final String pattern = "^[a-z][a-zA-Z0-9]*$";
        final DefaultConfiguration moduleConfig =
            createModuleConfig(ParameterNameCheck.class);

        final String[] expectedViolation = {
            "5:22: " + getCheckMessage(ParameterNameCheck.class,
                AbstractNameCheck.MSG_INVALID_PATTERN, "v_1", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT"
                + "/CLASS_DEF[./IDENT[@text"
                + "='InputXpathParameterNameDefaultPattern']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method1']]"
                + "/PARAMETERS/PARAMETER_DEF/IDENT[@text='v_1']"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testDifferentPattern() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathParameterNameDifferentPattern.java"));

        final String pattern = "^[a-z][_a-zA-Z0-9]+$";
        final DefaultConfiguration moduleConfig =
            createModuleConfig(ParameterNameCheck.class);
        moduleConfig.addProperty("format", "^[a-z][_a-zA-Z0-9]+$");

        final String[] expectedViolation = {
            "5:22: " + getCheckMessage(ParameterNameCheck.class,
                AbstractNameCheck.MSG_INVALID_PATTERN, "V2", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT"
                + "/CLASS_DEF[./IDENT[@text"
                + "='InputXpathParameterNameDifferentPattern']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method2']]"
                + "/PARAMETERS/PARAMETER_DEF/IDENT[@text='V2']"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testIgnoreOverridden() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathParameterNameIgnoreOverridden.java"));

        final String pattern = "^[a-z][a-zA-Z0-9]*$";
        final DefaultConfiguration moduleConfig =
            createModuleConfig(ParameterNameCheck.class);
        moduleConfig.addProperty("ignoreOverridden", "true");

        final String[] expectedViolation = {
            "5:22: " + getCheckMessage(ParameterNameCheck.class,
                AbstractNameCheck.MSG_INVALID_PATTERN, "V2", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT"
                + "/CLASS_DEF[./IDENT[@text"
                + "='InputXpathParameterNameIgnoreOverridden']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method2']]"
                + "/PARAMETERS/PARAMETER_DEF/IDENT[@text='V2']"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testAccessModifiers() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathParameterNameAccessModifier.java"));

        final String pattern = "^[a-z][a-z0-9][a-zA-Z0-9]*$";
        final DefaultConfiguration moduleConfig =
            createModuleConfig(ParameterNameCheck.class);
        moduleConfig.addProperty("format", "^[a-z][a-z0-9][a-zA-Z0-9]*$");
        moduleConfig.addProperty("accessModifiers", "public");

        final String[] expectedViolation = {
            "8:29: " + getCheckMessage(ParameterNameCheck.class,
                AbstractNameCheck.MSG_INVALID_PATTERN, "b", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT"
                + "/CLASS_DEF[./IDENT[@text"
                + "='InputXpathParameterNameAccessModifier']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method2']]"
                + "/PARAMETERS/PARAMETER_DEF/IDENT[@text='b']"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }
}
