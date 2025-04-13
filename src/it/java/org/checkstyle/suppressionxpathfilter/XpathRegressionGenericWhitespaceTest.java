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
import com.puppycrawl.tools.checkstyle.checks.whitespace.GenericWhitespaceCheck;

public class XpathRegressionGenericWhitespaceTest extends AbstractXpathTestSupport {

    private final String checkName = GenericWhitespaceCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testProcessEnd() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathGenericWhitespaceEnd.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(GenericWhitespaceCheck.class);

        final String[] expectedViolation = {
            "6:22: " + getCheckMessage(GenericWhitespaceCheck.class,
                GenericWhitespaceCheck.MSG_WS_PRECEDED, ">"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathGenericWhitespaceEnd']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='bad']]"
                + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='cls']]"
                + "/TYPE[./IDENT[@text='Class']]/TYPE_ARGUMENTS/GENERIC_END"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testProcessNestedGenericsOne() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathGenericWhitespaceNestedOne.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(GenericWhitespaceCheck.class);

        final String[] expectedViolation = {
            "6:22: " + getCheckMessage(GenericWhitespaceCheck.class,
                GenericWhitespaceCheck.MSG_WS_NOT_PRECEDED, "&"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathGenericWhitespaceNestedOne']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='bad']]/TYPE_PARAMETERS"
                + "/TYPE_PARAMETER[./IDENT[@text='E']]"
                + "/TYPE_UPPER_BOUNDS[./IDENT[@text='Enum']]/TYPE_ARGUMENTS/GENERIC_END"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testProcessNestedGenericsTwo() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathGenericWhitespaceNestedTwo.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(GenericWhitespaceCheck.class);

        final String[] expectedViolation = {
            "6:22: " + getCheckMessage(GenericWhitespaceCheck.class,
                GenericWhitespaceCheck.MSG_WS_FOLLOWED, ">"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathGenericWhitespaceNestedTwo']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='bad']]/TYPE_PARAMETERS"
                + "/TYPE_PARAMETER[./IDENT[@text='E']]"
                + "/TYPE_UPPER_BOUNDS[./IDENT[@text='Enum']]/TYPE_ARGUMENTS/GENERIC_END"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testProcessNestedGenericsThree() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathGenericWhitespaceNestedThree.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(GenericWhitespaceCheck.class);

        final String[] expectedViolation = {
            "6:22: " + getCheckMessage(GenericWhitespaceCheck.class,
                GenericWhitespaceCheck.MSG_WS_FOLLOWED, ">"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathGenericWhitespaceNestedThree']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='bad']]/TYPE_PARAMETERS"
                + "/TYPE_PARAMETER[./IDENT[@text='E']]"
                + "/TYPE_UPPER_BOUNDS[./IDENT[@text='Enum']]/TYPE_ARGUMENTS/GENERIC_END"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testProcessSingleGenericOne() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathGenericWhitespaceSingleOne.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(GenericWhitespaceCheck.class);

        final String[] expectedViolation = {
            "6:37: " + getCheckMessage(GenericWhitespaceCheck.class,
                GenericWhitespaceCheck.MSG_WS_FOLLOWED, ">"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathGenericWhitespaceSingleOne']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='bad']]/ASSIGN/EXPR/METHOD_CALL"
                + "/DOT[./IDENT[@text='Collections']]"
                + "/TYPE_ARGUMENTS/GENERIC_END"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testProcessSingleGenericTwo() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathGenericWhitespaceSingleTwo.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(GenericWhitespaceCheck.class);

        final String[] expectedViolation = {
            "6:7: " + getCheckMessage(GenericWhitespaceCheck.class,
                GenericWhitespaceCheck.MSG_WS_ILLEGAL_FOLLOW, ">"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathGenericWhitespaceSingleTwo']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='bad']]/TYPE_PARAMETERS/GENERIC_END"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testProcessStartOne() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathGenericWhitespaceStartOne.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(GenericWhitespaceCheck.class);

        final String[] expectedViolation = {
            "6:11: " + getCheckMessage(GenericWhitespaceCheck.class,
                GenericWhitespaceCheck.MSG_WS_NOT_PRECEDED, "<"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathGenericWhitespaceStartOne']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='bad']]/TYPE_PARAMETERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathGenericWhitespaceStartOne']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='bad']]/TYPE_PARAMETERS/GENERIC_START"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testProcessStartTwo() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathGenericWhitespaceStartTwo.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(GenericWhitespaceCheck.class);

        final String[] expectedViolation = {
            "6:34: " + getCheckMessage(GenericWhitespaceCheck.class,
                GenericWhitespaceCheck.MSG_WS_PRECEDED, "<"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathGenericWhitespaceStartTwo']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='bad']]/PARAMETERS"
                + "/PARAMETER_DEF[./IDENT[@text='consumer']]"
                + "/TYPE[./IDENT[@text='Consumer']]/TYPE_ARGUMENTS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathGenericWhitespaceStartTwo']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='bad']]/PARAMETERS"
                + "/PARAMETER_DEF[./IDENT[@text='consumer']]"
                + "/TYPE[./IDENT[@text='Consumer']]/TYPE_ARGUMENTS/GENERIC_START"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testProcessStartThree() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathGenericWhitespaceStartThree.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(GenericWhitespaceCheck.class);

        final String[] expectedViolation = {
            "6:5: " + getCheckMessage(GenericWhitespaceCheck.class,
                GenericWhitespaceCheck.MSG_WS_FOLLOWED, "<"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathGenericWhitespaceStartThree']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='bad']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathGenericWhitespaceStartThree']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='bad']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathGenericWhitespaceStartThree']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='bad']]/TYPE_PARAMETERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathGenericWhitespaceStartThree']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='bad']]/TYPE_PARAMETERS/GENERIC_START"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

}
