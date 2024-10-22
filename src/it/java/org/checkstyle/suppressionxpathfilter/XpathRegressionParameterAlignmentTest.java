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

import static com.puppycrawl.tools.checkstyle.checks.coding.ParameterAlignmentCheck.MSG_KEY;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.ParameterAlignmentCheck;

public class XpathRegressionParameterAlignmentTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return ParameterAlignmentCheck.class.getSimpleName();
    }

    @Test
    public void testMethods() throws Exception {
        final String filePath =
                getPath("InputXpathParameterAlignmentMethods.java");
        final File fileToProcess = new File(filePath);

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ParameterAlignmentCheck.class);

        final String[] expectedViolations = {
            "17:9: " + getCheckMessage(ParameterAlignmentCheck.class, MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathParameterAlignmentMethods']]"
            + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='myMethod2']]/PARAMETERS/"
            + "PARAMETER_DEF[./IDENT[@text='b']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathParameterAlignmentMethods']]"
            + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='myMethod2']]/PARAMETERS/"
            + "PARAMETER_DEF[./IDENT[@text='b']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathParameterAlignmentMethods']]"
            + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='myMethod2']]/PARAMETERS/"
            + "PARAMETER_DEF[./IDENT[@text='b']]/TYPE",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathParameterAlignmentMethods']]"
            + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='myMethod2']]/PARAMETERS/"
            + "PARAMETER_DEF[./IDENT[@text='b']]/TYPE/LITERAL_LONG"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQueries);

    }

    @Test
    public void testCtor() throws Exception {
        final String filePath =
                getPath("InputXpathParameterAlignmentCtor.java");
        final File fileToProcess = new File(filePath);

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ParameterAlignmentCheck.class);

        final String[] expectedViolations = {
            "17:13: " + getCheckMessage(ParameterAlignmentCheck.class, MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathParameterAlignmentCtor']]"
            + "/OBJBLOCK/CTOR_DEF[./IDENT[@text='InputXpathParameterAlignmentCtor']]"
            + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='b']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathParameterAlignmentCtor']]"
            + "/OBJBLOCK/CTOR_DEF[./IDENT[@text='InputXpathParameterAlignmentCtor']]"
            + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='b']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathParameterAlignmentCtor']]"
            + "/OBJBLOCK/CTOR_DEF[./IDENT[@text='InputXpathParameterAlignmentCtor']]"
            + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='b']]/TYPE",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathParameterAlignmentCtor']]"
            + "/OBJBLOCK/CTOR_DEF[./IDENT[@text='InputXpathParameterAlignmentCtor']]"
            + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='b']]/TYPE/LITERAL_INT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQueries);
    }

}
