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

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.NumericalCharacterCaseCheck;

public class XpathRegressionNumericalCharacterCaseTest extends AbstractXpathTestSupport {

    private final String checkName = NumericalCharacterCaseCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testIntLiteralPrefix() throws Exception {
        final File fileProcess =
                new File(getPath("InputXpathNumericalCharacterCaseInt.java"));
        final DefaultConfiguration config =
                createModuleConfig(NumericalCharacterCaseCheck.class);

        final String[] expected = {
            "4:13: " + getCheckMessage(
                    NumericalCharacterCaseCheck.class,
                    NumericalCharacterCaseCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathNumericalCharacterCaseInt']]"
                        + "/OBJBLOCK/VARIABLE_DEF"
                        + "[./IDENT[@text='a']]/ASSIGN/EXPR"
                        + "[./NUM_INT[@text='0X1A']]",

                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathNumericalCharacterCaseInt']]"
                        + "/OBJBLOCK/VARIABLE_DEF"
                        + "[./IDENT[@text='a']]/ASSIGN/EXPR"
                        + "/NUM_INT[@text='0X1A']"
        );

        runVerifications(config, fileProcess, expected, expectedXpathQueries);
    }

    @Test
    public void testLongLiteralPrefix() throws Exception {
        final File fileProcess =
                new File(getPath("InputXpathNumericalCharacterCaseLong.java"));
        final DefaultConfiguration config =
                createModuleConfig(NumericalCharacterCaseCheck.class);

        final String[] expected = {
            "4:14: " + getCheckMessage(
                    NumericalCharacterCaseCheck.class,
                    NumericalCharacterCaseCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathNumericalCharacterCaseLong']]"
                        + "/OBJBLOCK/VARIABLE_DEF"
                        + "[./IDENT[@text='a']]/ASSIGN/EXPR"
                        + "[./NUM_LONG[@text='0X10L']]",

                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathNumericalCharacterCaseLong']]"
                        + "/OBJBLOCK/VARIABLE_DEF"
                        + "[./IDENT[@text='a']]/ASSIGN/EXPR"
                        + "/NUM_LONG[@text='0X10L']"
        );

        runVerifications(config, fileProcess, expected, expectedXpathQueries);
    }

    @Test
    public void testFloatLiteralInfixAndSuffix() throws Exception {
        final File fileProcess =
                new File(getPath("InputXpathNumericalCharacterCaseFloat.java"));
        final DefaultConfiguration config =
                createModuleConfig(NumericalCharacterCaseCheck.class);

        final String[] expected = {
            "4:15: " + getCheckMessage(
                    NumericalCharacterCaseCheck.class,
                    NumericalCharacterCaseCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathNumericalCharacterCaseFloat']]"
                        + "/OBJBLOCK/VARIABLE_DEF"
                        + "[./IDENT[@text='a']]/ASSIGN/EXPR"
                        + "[./NUM_FLOAT[@text='1.2E3F']]",

                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathNumericalCharacterCaseFloat']]"
                        + "/OBJBLOCK/VARIABLE_DEF"
                        + "[./IDENT[@text='a']]/ASSIGN/EXPR"
                        + "/NUM_FLOAT[@text='1.2E3F']"
        );

        runVerifications(config, fileProcess, expected, expectedXpathQueries);
    }

    @Test
    public void testDoubleLiteralSuffix() throws Exception {
        final File fileProcess =
                new File(getPath("InputXpathNumericalCharacterCaseDouble.java"));
        final DefaultConfiguration config =
                createModuleConfig(NumericalCharacterCaseCheck.class);

        final String[] expected = {
            "4:16: " + getCheckMessage(
                    NumericalCharacterCaseCheck.class,
                    NumericalCharacterCaseCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathNumericalCharacterCaseDouble']]"
                        + "/OBJBLOCK/VARIABLE_DEF"
                        + "[./IDENT[@text='a']]/ASSIGN/EXPR"
                        + "[./NUM_DOUBLE[@text='10D']]",

                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathNumericalCharacterCaseDouble']]"
                        + "/OBJBLOCK/VARIABLE_DEF"
                        + "[./IDENT[@text='a']]/ASSIGN/EXPR"
                        + "/NUM_DOUBLE[@text='10D']"
        );

        runVerifications(config, fileProcess, expected, expectedXpathQueries);
    }
}
