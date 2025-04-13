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
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.LambdaParameterNameCheck;

public class XpathRegressionLambdaParameterNameTest extends AbstractXpathTestSupport {

    private final String checkName = LambdaParameterNameCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testDefault() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathLambdaParameterNameDefault.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(LambdaParameterNameCheck.class);
        final String defaultPattern = "^([a-z][a-zA-Z0-9]*|_)$";

        final String[] expectedViolation = {
            "7:44: " + getCheckMessage(LambdaParameterNameCheck.class,
                AbstractNameCheck.MSG_INVALID_PATTERN, "S", defaultPattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathLambdaParameterNameDefault']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/VARIABLE_DEF["
                + "./IDENT[@text='trimmer']]/ASSIGN/LAMBDA/IDENT[@text='S']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testNonDefaultPattern() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathLambdaParameterNameNonDefaultPattern.java"));

        final String nonDefaultPattern = "^_[a-zA-Z0-9]*$";

        final DefaultConfiguration moduleConfig =
            createModuleConfig(LambdaParameterNameCheck.class);
        moduleConfig.addProperty("format", nonDefaultPattern);

        final String[] expectedViolation = {
            "7:45: " + getCheckMessage(LambdaParameterNameCheck.class,
                AbstractNameCheck.MSG_INVALID_PATTERN, "s", nonDefaultPattern),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathLambdaParameterNameNonDefaultPattern']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/"
                + "VARIABLE_DEF[./IDENT[@text='trimmer']]/ASSIGN/LAMBDA/PARAMETERS",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathLambdaParameterNameNonDefaultPattern']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/"
                + "VARIABLE_DEF[./IDENT[@text='trimmer']]/ASSIGN/LAMBDA/PARAMETERS"
                + "/PARAMETER_DEF[./IDENT[@text='s']]",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathLambdaParameterNameNonDefaultPattern']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST"
                + "/VARIABLE_DEF[./IDENT[@text='trimmer']]/ASSIGN/LAMBDA/PARAMETERS"
                + "/PARAMETER_DEF[./IDENT[@text='s']]/MODIFIERS",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathLambdaParameterNameNonDefaultPattern']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/"
                + "VARIABLE_DEF[./IDENT[@text='trimmer']]/ASSIGN/LAMBDA/PARAMETERS"
                + "/PARAMETER_DEF[./IDENT[@text='s']]/TYPE",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathLambdaParameterNameNonDefaultPattern']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/"
                + "VARIABLE_DEF[./IDENT[@text='trimmer']]/ASSIGN/LAMBDA/PARAMETERS"
                + "/PARAMETER_DEF/IDENT[@text='s']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }
}
