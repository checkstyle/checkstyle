///////////////////////////////////////////////////////////////////////////////////////////////
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

package org.checkstyle.suppressionxpathfilter.coding;

import java.io.File;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.PatternVariableAssignmentCheck;

public class XpathRegressionPatternVariableAssignmentTest extends AbstractXpathTestSupport {
    private final String checkName = PatternVariableAssignmentCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/patternvariableassignment";
    }

    @Test
    public void testMethod() throws Exception {
        final File fileToProcess =
                new File(getPath(
                        "InputXpathPatternVariableAssignmentMethod.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(PatternVariableAssignmentCheck.class);

        final String[] expectedViolation = {
            "8:7: " + getCheckMessage(PatternVariableAssignmentCheck.class,
                    PatternVariableAssignmentCheck.MSG_KEY,
                    "s"),
        };

        final List<String> expectedXpathQueries = List.of("/COMPILATION_UNIT/CLASS_DEF["
            + "./IDENT[@text='InputXpathPatternVariableAssignmentMethod']]/OBJBLOCK/METHOD_DEF["
            + "./IDENT[@text='test']]/SLIST/LITERAL_IF/SLIST/EXPR/ASSIGN/IDENT[@text='s']");

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testLambda() throws Exception {
        final File fileToProcess =
                new File(getPath(
                        "InputXpathPatternVariableAssignmentLambda.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(PatternVariableAssignmentCheck.class);

        final String[] expectedViolation = {
            "12:17: " + getCheckMessage(PatternVariableAssignmentCheck.class,
                    PatternVariableAssignmentCheck.MSG_KEY,
                    "x"),
        };

        final List<String> expectedXpathQueries = List.of("/COMPILATION_UNIT/CLASS_DEF["
            + "./IDENT[@text='InputXpathPatternVariableAssignmentLambda']]/OBJBLOCK/METHOD_DEF["
            + "./IDENT[@text='foo']]/SLIST/EXPR/METHOD_CALL/ELIST/LAMBDA["
            + "./IDENT[@text='item']]/SLIST/LITERAL_IF/SLIST/EXPR/ASSIGN/IDENT[@text='x']");

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testClass() throws Exception {
        final File fileToProcess =
                new File(getPath(
                        "InputXpathPatternVariableAssignmentClass.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(PatternVariableAssignmentCheck.class);

        final String[] expectedViolation = {
            "10:21: " + getCheckMessage(PatternVariableAssignmentCheck.class,
                    PatternVariableAssignmentCheck.MSG_KEY,
                    "x"),
        };

        final List<String> expectedXpathQueries = List.of("/COMPILATION_UNIT/CLASS_DEF["
            + "./IDENT[@text='InputXpathPatternVariableAssignmentClass']]/OBJBLOCK/METHOD_DEF["
            + "./IDENT[@text='foo']]/SLIST/VARIABLE_DEF[./IDENT[@text='annClass']]/ASSIGN/EXPR/"
            + "LITERAL_NEW[./IDENT[@text='AnonymousClass']]/OBJBLOCK/METHOD_DEF["
            + "./IDENT[@text='test']]/SLIST/LITERAL_IF/SLIST/EXPR/ASSIGN/IDENT[@text='x']");

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
