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

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.HexLiteralCaseCheck;

public class XpathRegressionHexLiteralCaseTest extends AbstractXpathTestSupport {

    private final String checkName = HexLiteralCaseCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testHexLiteralCaseCheck() throws Exception {
        final File fileProcess =
                new File(getPath("InputXpathHexLiteralCase.java"));
        final DefaultConfiguration config =
                createModuleConfig(HexLiteralCaseCheck.class);

        final String[] expected = {
            "4:14: " + getCheckMessage(HexLiteralCaseCheck.class,
                      HexLiteralCaseCheck.MSG_KEY),
        };
        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathHexLiteralCase']]"
                        + "/OBJBLOCK/VARIABLE_DEF"
                        + "[./IDENT[@text='i']]/ASSIGN/EXPR[./NUM_INT[@text='0xa']]",

                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathHexLiteralCase']]"
                        + "/OBJBLOCK/VARIABLE_DEF"
                        + "[./IDENT[@text='i']]/ASSIGN/EXPR/NUM_INT[@text='0xa']"
        );
        runVerifications(config, fileProcess, expected, expectedXpathQueries);
    }

    @Test
    public void testLongHexLiteralCaseCheck() throws Exception {
        final File fileProcess =
                new File(getPath("InputXpathHexLiteralCaseLong.java"));
        final DefaultConfiguration config =
                createModuleConfig(HexLiteralCaseCheck.class);

        final String[] expected = {
            "4:15: " + getCheckMessage(HexLiteralCaseCheck.class,
                    HexLiteralCaseCheck.MSG_KEY),
        };
        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathHexLiteralCaseLong']]"
                        + "/OBJBLOCK/VARIABLE_DEF"
                        + "[./IDENT[@text='i']]/ASSIGN/EXPR[./NUM_LONG[@text='0x00efL']]",

                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathHexLiteralCaseLong']]"
                        + "/OBJBLOCK/VARIABLE_DEF"
                        + "[./IDENT[@text='i']]/ASSIGN/EXPR/NUM_LONG[@text='0x00efL']"
        );
        runVerifications(config, fileProcess, expected, expectedXpathQueries);
    }

    @Test
    public void testHexLiteralCaseCheckTwo() throws Exception {
        final File fileProcess =
                new File(getPath("InputXpathHexLiteralCaseTwo.java"));
        final DefaultConfiguration config =
                createModuleConfig(HexLiteralCaseCheck.class);

        final String[] expected = {
            "4:14: " + getCheckMessage(HexLiteralCaseCheck.class,
                    HexLiteralCaseCheck.MSG_KEY),
        };
        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathHexLiteralCaseTwo']]"
                        + "/OBJBLOCK/VARIABLE_DEF"
                        + "[./IDENT[@text='a']]/ASSIGN/EXPR[./NUM_INT[@text='0xFa1']]",

                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathHexLiteralCaseTwo']]"
                        + "/OBJBLOCK/VARIABLE_DEF"
                        + "[./IDENT[@text='a']]/ASSIGN/EXPR/NUM_INT[@text='0xFa1']"
        );
        runVerifications(config, fileProcess, expected, expectedXpathQueries);
    }

}
