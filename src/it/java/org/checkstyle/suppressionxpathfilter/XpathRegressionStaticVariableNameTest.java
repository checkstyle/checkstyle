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
///////////////////////////////////////////////////////////////////////////////////////////////

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.StaticVariableNameCheck;

public class XpathRegressionStaticVariableNameTest extends AbstractXpathTestSupport {

    private final String checkName = StaticVariableNameCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testStaticVariableName() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathStaticVariableName.java"));

        final String pattern = "^[a-z][a-zA-Z0-9]*$";
        final DefaultConfiguration moduleConfig =
                createModuleConfig(StaticVariableNameCheck.class);

        final String[] expectedViolation = {
            "6:24: " + getCheckMessage(StaticVariableNameCheck.class,
                        AbstractNameCheck.MSG_INVALID_PATTERN, "NUM2", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text"
                        + "='InputXpathStaticVariableName']]"
                        + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='NUM2']"

        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testInnerClassField() throws Exception {
        final File fileToProcess =
                new File(getNonCompilablePath(
                        "InputXpathStaticVariableNameInnerClassField.java"));

        final String pattern = "^[a-z][a-zA-Z0-9]*$";
        final DefaultConfiguration moduleConfig =
                createModuleConfig(StaticVariableNameCheck.class);

        final String[] expectedViolation = {
            "14:24: " + getCheckMessage(StaticVariableNameCheck.class,
                        AbstractNameCheck.MSG_INVALID_PATTERN, "NUM3", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text"
                        + "='InputXpathStaticVariableNameInnerClassField']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='outerMethod']]"
                        + "/SLIST/CLASS_DEF[./IDENT[@text='MyLocalClass']]"
                        + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='NUM3']"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testNoAccessModifier() throws Exception {
        final File fileToProcess =
                new File(getNonCompilablePath(
                        "InputXpathStaticVariableNameNoAccessModifier.java"));

        final String pattern = "^[a-z][a-zA-Z0-9]*$";
        final DefaultConfiguration moduleConfig =
                createModuleConfig(StaticVariableNameCheck.class);

        final String[] expectedViolation = {
            "6:19: " + getCheckMessage(StaticVariableNameCheck.class,
                        AbstractNameCheck.MSG_INVALID_PATTERN, "NUM3", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text"
                        + "='InputXpathStaticVariableNameNoAccessModifier']]"
                        + "/OBJBLOCK/INSTANCE_INIT/SLIST/VARIABLE_DEF/IDENT[@text='NUM3']"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
