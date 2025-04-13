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
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;

public class XpathRegressionConstantNameTest extends AbstractXpathTestSupport {

    private static final Class<ConstantNameCheck> CLASS = ConstantNameCheck.class;
    private static final String PATTERN = "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$";
    private final String checkName = CLASS.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testLowercase() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathConstantNameLowercase.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(CLASS);

        final String[] expectedViolation = {
            "4:29: " + getCheckMessage(CLASS, MSG_INVALID_PATTERN, "number", PATTERN),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathConstantNameLowercase']]"
                + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='number']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testCamelCase() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathConstantNameCamelCase.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(CLASS);

        final String[] expectedViolation = {
            "4:29: " + getCheckMessage(CLASS,
                MSG_INVALID_PATTERN, "badConstant", PATTERN),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathConstantNameCamelCase']]"
                + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='badConstant']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testWithBeginningUnderscore() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathConstantNameWithBeginningUnderscore.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(CLASS);

        final String[] expectedViolation = {
            "4:33: " + getCheckMessage(CLASS, MSG_INVALID_PATTERN, "_CONSTANT", PATTERN),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathConstantNameWithBeginningUnderscore']]"
                + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='_CONSTANT']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testWithTwoUnderscores() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathConstantNameWithTwoUnderscores.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(CLASS);

        final String[] expectedViolation = {
            "4:30: " + getCheckMessage(CLASS, MSG_INVALID_PATTERN, "BAD__NAME", PATTERN),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathConstantNameWithTwoUnderscores']]"
                + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='BAD__NAME']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }
}
