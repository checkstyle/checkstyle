///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package org.checkstyle.suppressionxpathfilter.coding;

import java.io.File;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.FinalPatternVariableCheck;

public class XpathRegressionFinalPatternVariableTest extends AbstractXpathTestSupport {

    private final String checkName = FinalPatternVariableCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/finalpatternvariable";
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess = new File(
                getPath("InputXpathFinalPatternVariableOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(FinalPatternVariableCheck.class);

        final String[] expectedViolation = {
            "5:35: " + getCheckMessage(FinalPatternVariableCheck.class,
                    FinalPatternVariableCheck.MSG_KEY, "s"),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathFinalPatternVariableOne']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                    + "/SLIST/LITERAL_IF/EXPR/LITERAL_INSTANCEOF[./IDENT[@text='obj']]"
                    + "/PATTERN_VARIABLE_DEF/IDENT[@text='s']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess = new File(
                getPath("InputXpathFinalPatternVariableTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(FinalPatternVariableCheck.class);

        final String[] expectedViolation = {
            "5:36: " + getCheckMessage(FinalPatternVariableCheck.class,
                    FinalPatternVariableCheck.MSG_KEY, "i"),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathFinalPatternVariableTwo']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                    + "/SLIST/LITERAL_IF/EXPR/LITERAL_INSTANCEOF[./IDENT[@text='obj']]"
                    + "/PATTERN_VARIABLE_DEF/IDENT[@text='i']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess = new File(
                getPath("InputXpathFinalPatternVariableThree.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(FinalPatternVariableCheck.class);

        final String[] expectedViolation = {
            "7:38: " + getCheckMessage(FinalPatternVariableCheck.class,
                    FinalPatternVariableCheck.MSG_KEY, "x"),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathFinalPatternVariableThree']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                    + "/SLIST/LITERAL_IF/EXPR/LITERAL_INSTANCEOF[./IDENT[@text='obj']]"
                    + "/RECORD_PATTERN_DEF/RECORD_PATTERN_COMPONENTS"
                    + "/PATTERN_VARIABLE_DEF/IDENT[@text='x']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

}
