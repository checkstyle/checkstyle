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

import static com.puppycrawl.tools.checkstyle.checks.coding.ParameterAssignmentCheck.MSG_KEY;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.ParameterAssignmentCheck;

public class XpathRegressionParameterAssignmentTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return ParameterAssignmentCheck.class.getSimpleName();
    }

    @Test
    public void testMethods() throws Exception {
        final String filePath =
                getPath("InputXpathParameterAssignmentMethods.java");
        final File fileToProcess = new File(filePath);

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ParameterAssignmentCheck.class);

        final String[] expectedViolations = {
            "9:15: " + getCheckMessage(ParameterAssignmentCheck.class, MSG_KEY, "field"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
            + "@text='InputXpathParameterAssignmentMethods']]"
            + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='Test1']]/SLIST/EXPR"
            + "[./PLUS_ASSIGN/IDENT[@text='field']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
            + "@text='InputXpathParameterAssignmentMethods']]"
            + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='Test1']]"
            + "/SLIST/EXPR/PLUS_ASSIGN[./IDENT[@text='field']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQueries);

    }

    @Test
    public void testLambdas() throws Exception {
        final String filePath =
                getPath("InputXpathParameterAssignmentLambdas.java");
        final File fileToProcess = new File(filePath);

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ParameterAssignmentCheck.class);

        final String[] expectedViolations = {
            "9:32: " + getCheckMessage(ParameterAssignmentCheck.class, MSG_KEY, "q"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
            + "@text='InputXpathParameterAssignmentLambdas']]"
            + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='obj1']]"
            + "/ASSIGN/LAMBDA[./IDENT[@text='q']]/EXPR",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
            + "@text='InputXpathParameterAssignmentLambdas']]"
            + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='obj1']]/ASSIGN/LAMBDA[./IDENT["
            + "@text='q']]/EXPR/POST_INC[./IDENT[@text='q']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQueries);
    }

    @Test
    public void testCtor() throws Exception {
        final String filePath =
                getPath("InputXpathParameterAssignmentCtor.java");
        final File fileToProcess = new File(filePath);

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ParameterAssignmentCheck.class);

        final String[] expectedViolations = {
            "9:15: " + getCheckMessage(ParameterAssignmentCheck.class, MSG_KEY, "field"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
            + "@text='InputXpathParameterAssignmentCtor']]"
            + "/OBJBLOCK/CTOR_DEF[./IDENT["
            + "@text='InputXpathParameterAssignmentCtor']]"
            + "/SLIST/EXPR[./PLUS_ASSIGN/IDENT[@text='field']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
            + "@text='InputXpathParameterAssignmentCtor']]"
            + "/OBJBLOCK/CTOR_DEF[./IDENT["
            + "@text='InputXpathParameterAssignmentCtor']]"
            + "/SLIST/EXPR/PLUS_ASSIGN[./IDENT[@text='field']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQueries);
    }

}
