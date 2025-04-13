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

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.LocalVariableNameCheck;

public class XpathRegressionLocalVariableNameTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return LocalVariableNameCheck.class.getSimpleName();
    }

    @Test
    public void testMethod() throws Exception {
        final File fileToProcess = new File(getPath("InputXpathLocalVariableNameMethod.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(LocalVariableNameCheck.class);

        final String pattern = "^([a-z][a-zA-Z0-9]*|_)$";
        final String[] expectedViolations = {
            "5:9: " + getCheckMessage(LocalVariableNameCheck.class, MSG_INVALID_PATTERN,
                "VAR", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
              + "@text='InputXpathLocalVariableNameMethod']]"
              + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='MyMethod']]"
              + "/SLIST/VARIABLE_DEF/IDENT[@text='VAR']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQueries);

    }

    @Test
    public void testIteration() throws Exception {
        final File fileToProcess = new File(getPath("InputXpathLocalVariableNameIteration.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(LocalVariableNameCheck.class);

        final String pattern = "^([a-z][a-zA-Z0-9]*|_)$";
        final String[] expectedViolations = {
            "7:14: " + getCheckMessage(LocalVariableNameCheck.class, MSG_INVALID_PATTERN,
                "var_1", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
              + "@text='InputXpathLocalVariableNameIteration']]"
              + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='MyMethod']]/"
              + "SLIST/LITERAL_FOR/FOR_INIT/VARIABLE_DEF/IDENT[@text='var_1']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQueries);

    }

    @Test
    public void testInnerClass() throws Exception {
        final File fileToProcess = new File(getPath("InputXpathLocalVariableNameInnerClass.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(LocalVariableNameCheck.class);

        final String pattern = "^([a-z][a-zA-Z0-9]*|_)$";
        final String[] expectedViolations = {
            "6:11: " + getCheckMessage(LocalVariableNameCheck.class, MSG_INVALID_PATTERN,
                "VAR", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
              + "@text='InputXpathLocalVariableNameInnerClass']]"
              + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='InnerClass']]/OBJBLOCK/"
              + "METHOD_DEF[./IDENT[@text='myMethod']]/SLIST/VARIABLE_DEF/IDENT[@text='VAR']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQueries);

    }

}
