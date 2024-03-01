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

import static com.puppycrawl.tools.checkstyle.checks.sizes.ExecutableStatementCountCheck.MSG_KEY;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.sizes.ExecutableStatementCountCheck;

public class XpathRegressionExecutableStatementCountTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return ExecutableStatementCountCheck.class.getSimpleName();
    }

    @Test
    public void testDefaultConfig() throws Exception {
        final String filePath =
                getPath("SuppressionXpathRegressionExecutableStatementCountDefault.java");
        final File fileToProcess = new File(filePath);

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ExecutableStatementCountCheck.class);

        moduleConfig.addProperty("max", "0");

        final String[] expectedViolations = {
            "4:5: " + getCheckMessage(ExecutableStatementCountCheck.class, MSG_KEY, 3, 0),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
            +"@text='SuppressionXpathRegressionExecutableStatementCountDefault']]"
            +"/OBJBLOCK/METHOD_DEF[./IDENT[@text='ElseIfLadder']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
            +"@text='SuppressionXpathRegressionExecutableStatementCountDefault']]"
            +"/OBJBLOCK/METHOD_DEF[./IDENT[@text='ElseIfLadder']]"
            +"/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
            +"@text='SuppressionXpathRegressionExecutableStatementCountDefault']]"
            +"/OBJBLOCK/METHOD_DEF[./IDENT[@text='ElseIfLadder']]"
            +"/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQueries);

    }

    @Test
    public void testCustomMax() throws Exception {
        final String filePath =
                getPath("SuppressionXpathRegressionExecutableStatementCountCustomMax.java");
        final File fileToProcess = new File(filePath);

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ExecutableStatementCountCheck.class);

        moduleConfig.addProperty("max", "0");
        moduleConfig.addProperty("tokens", "CTOR_DEF");

        final String[] expectedViolations = {
            "4:5: " + getCheckMessage(ExecutableStatementCountCheck.class, MSG_KEY, 2, 0),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
            +"@text='SuppressionXpathRegressionExecutableStatementCountCustomMax']]"
            +"/OBJBLOCK/CTOR_DEF[./IDENT["
            +"@text='SuppressionXpathRegressionExecutableStatementCountCustomMax']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
            +"@text='SuppressionXpathRegressionExecutableStatementCountCustomMax']]"
            +"/OBJBLOCK/CTOR_DEF[./IDENT["
            +"@text='SuppressionXpathRegressionExecutableStatementCountCustomMax']]"
            +"/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
            +"@text='SuppressionXpathRegressionExecutableStatementCountCustomMax']]"
            +"/OBJBLOCK/CTOR_DEF[./IDENT["
            +"@text='SuppressionXpathRegressionExecutableStatementCountCustomMax']]"
            +"/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQueries);
    }

    @Test
    public void testLambdas() throws Exception {
        final String filePath =
                getPath("SuppressionXpathRegressionExecutableStatementCountLambdas.java");
        final File fileToProcess = new File(filePath);

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ExecutableStatementCountCheck.class);

        moduleConfig.addProperty("max", "1");
        moduleConfig.addProperty("tokens", "LAMBDA");

        final String[] expectedViolations = {
            "7:22: " + getCheckMessage(ExecutableStatementCountCheck.class, MSG_KEY, 2, 1),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='SuppressionXpathRegressionExecutableStatementCountLambdas']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='c']]/ASSIGN/LAMBDA"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQueries);
    }

}
