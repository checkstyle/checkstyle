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
import com.puppycrawl.tools.checkstyle.checks.whitespace.SeparatorWrapCheck;

public class XpathRegressionSeparatorWrapTest extends AbstractXpathTestSupport {

    private final String checkName = SeparatorWrapCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testClass() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathSeparatorWrapClass.java"));
        final DefaultConfiguration moduleConfig =
            createModuleConfig(SeparatorWrapCheck.class);

        final String[] expectedViolation = {
            "10:17: " + getCheckMessage(SeparatorWrapCheck.class,
                SeparatorWrapCheck.MSG_LINE_PREVIOUS, ","),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathSeparatorWrapClass']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testMethod']]"
                + "/PARAMETERS/COMMA"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testInterface() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathSeparatorWrapInterface.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(SeparatorWrapCheck.class);
        moduleConfig.addProperty("tokens", "ELLIPSIS");
        final String[] expectedViolation = {
            "9:13: " + getCheckMessage(SeparatorWrapCheck.class,
                SeparatorWrapCheck.MSG_LINE_PREVIOUS, "..."),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/INTERFACE_DEF"
                + "[./IDENT[@text='InputXpathSeparatorWrapInterface']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testMethod2']]"
                + "/PARAMETERS/PARAMETER_DEF"
                + "[./IDENT[@text='parameters']]/ELLIPSIS"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testMethod() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathSeparatorWrapMethod.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(SeparatorWrapCheck.class);
        moduleConfig.addProperty("tokens", "DOT");

        final String[] expectedViolation = {
            "9:13: " + getCheckMessage(SeparatorWrapCheck.class,
                SeparatorWrapCheck.MSG_LINE_PREVIOUS, "."),
        };
        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                + "'InputXpathSeparatorWrapMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method1']]/SLIST"
                + "/VARIABLE_DEF[./IDENT[@text='stringLength']]"
                + "/ASSIGN/EXPR",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                + "'InputXpathSeparatorWrapMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method1']]/SLIST"
                + "/VARIABLE_DEF[./IDENT[@text='stringLength']]/ASSIGN"
                + "/EXPR/DOT[./IDENT[@text='stringArray']]"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }
}
