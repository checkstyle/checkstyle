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
import com.puppycrawl.tools.checkstyle.checks.naming.PatternVariableNameCheck;

public class XpathRegressionPatternVariableNameTest extends AbstractXpathTestSupport {

    private final String checkName = PatternVariableNameCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getNonCompilablePath(
                        "InputXpathPatternVariableNameOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(PatternVariableNameCheck.class);
        final String defaultPattern = "^([a-z][a-zA-Z0-9]*|_)$";

        final String[] expectedViolation = {
            "6:33: " + getCheckMessage(PatternVariableNameCheck.class,
                    AbstractNameCheck.MSG_INVALID_PATTERN,
                    "STRING1", defaultPattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathPatternVariableNameOne']]"
                + "/OBJBLOCK/CTOR_DEF[./IDENT[@text='MyClass']]/SLIST/LITERAL_IF/EXPR/"
                + "LITERAL_INSTANCEOF[./IDENT[@text='o1']]/PATTERN_VARIABLE_DEF/"
                + "IDENT[@text='STRING1']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getNonCompilablePath(
                        "InputXpathPatternVariableNameTwo.java"));

        final String nonDefaultPattern = "^_[a-zA-Z0-9]*$";

        final DefaultConfiguration moduleConfig =
                createModuleConfig(PatternVariableNameCheck.class);
        moduleConfig.addProperty("format", nonDefaultPattern);

        final String[] expectedViolation = {
            "6:34: " + getCheckMessage(PatternVariableNameCheck.class,
                    AbstractNameCheck.MSG_INVALID_PATTERN, "s", nonDefaultPattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathPatternVariableNameTwo']]"
                + "/OBJBLOCK/CTOR_DEF[./IDENT[@text='MyClass']]/SLIST/LITERAL_IF/EXPR/"
                + "LITERAL_INSTANCEOF[./IDENT[@text='o1']]/"
                + "PATTERN_VARIABLE_DEF/IDENT[@text='s']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess =
                new File(getNonCompilablePath(
                        "InputXpathPatternVariableNameThree.java"));

        final String nonDefaultPattern = "^[a-z](_?[a-zA-Z0-9]+)*$";

        final DefaultConfiguration moduleConfig =
                createModuleConfig(PatternVariableNameCheck.class);
        moduleConfig.addProperty("format", nonDefaultPattern);

        final String[] expectedViolation = {
            "6:34: " + getCheckMessage(PatternVariableNameCheck.class,
                AbstractNameCheck.MSG_INVALID_PATTERN, "STR", nonDefaultPattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathPatternVariableNameThree']]"
                    + "/OBJBLOCK/CTOR_DEF[./IDENT[@text='MyClass']]/SLIST/LITERAL_IF/"
                    + "EXPR/LITERAL_INSTANCEOF[./IDENT[@text='o1']]/"
                    + "PATTERN_VARIABLE_DEF/IDENT[@text='STR']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testFour() throws Exception {
        final File fileToProcess =
                new File(getNonCompilablePath(
                        "InputXpathPatternVariableNameFour.java"));

        final String nonDefaultPattern = "^[a-z][_a-zA-Z0-9]{2,}$";

        final DefaultConfiguration moduleConfig =
                createModuleConfig(PatternVariableNameCheck.class);
        moduleConfig.addProperty("format", nonDefaultPattern);

        final String[] expectedViolation = {
            "6:34: " + getCheckMessage(PatternVariableNameCheck.class,
                AbstractNameCheck.MSG_INVALID_PATTERN, "st", nonDefaultPattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathPatternVariableNameFour']]"
                    + "/OBJBLOCK/CTOR_DEF[./IDENT[@text='MyClass']]/SLIST/LITERAL_IF/EXPR/"
                    + "LITERAL_INSTANCEOF[./IDENT[@text='o1']]/"
                    + "PATTERN_VARIABLE_DEF/IDENT[@text='st']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
