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

import static com.puppycrawl.tools.checkstyle.checks.naming.LocalFinalVariableNameCheck.MSG_INVALID_PATTERN;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.LocalFinalVariableNameCheck;

public class XpathRegressionLocalFinalVariableNameTest extends AbstractXpathTestSupport {

    private final String checkName = LocalFinalVariableNameCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testResource() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathLocalFinalVariableNameResource.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(LocalFinalVariableNameCheck.class);
        moduleConfig.addProperty("format", "^[A-Z][A-Z0-9]*$");
        moduleConfig.addProperty("tokens", "PARAMETER_DEF,RESOURCE");

        final String[] expectedViolation = {
            "7:21: " + getCheckMessage(LocalFinalVariableNameCheck.class,
                    MSG_INVALID_PATTERN, "scanner", "^[A-Z][A-Z0-9]*$"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathLocalFinalVariableNameResource']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='MyMethod']]/SLIST/LITERAL_TRY"
                        + "/RESOURCE_SPECIFICATION/RESOURCES/RESOURCE/IDENT[@text='scanner']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testVariable() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathLocalFinalVariableNameVar.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(LocalFinalVariableNameCheck.class);
        moduleConfig.addProperty("format", "^[A-Z][a-z0-9]*$");

        final String[] expectedViolation = {
            "5:19: " + getCheckMessage(LocalFinalVariableNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR1", "^[A-Z][a-z0-9]*$"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathLocalFinalVariableNameVar']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='MyMethod']]/SLIST/VARIABLE_DEF"
                        + "/IDENT[@text='VAR1']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testInnerClass() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathLocalFinalVariableNameInner.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(LocalFinalVariableNameCheck.class);
        moduleConfig.addProperty("format", "^[A-Z][a-z0-9]*$");

        final String[] expectedViolation = {
            "8:23: " + getCheckMessage(LocalFinalVariableNameCheck.class,
                        MSG_INVALID_PATTERN, "VAR1", "^[A-Z][a-z0-9]*$"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathLocalFinalVariableNameInner']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='InnerClass']]/OBJBLOCK"
                        + "/METHOD_DEF[./IDENT[@text='MyMethod']]/SLIST/VARIABLE_DEF"
                        + "/IDENT[@text='VAR1']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
