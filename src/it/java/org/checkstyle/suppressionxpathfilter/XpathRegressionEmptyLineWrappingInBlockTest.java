////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.blocks.EmptyLineWrappingInBlockCheck;
import com.puppycrawl.tools.checkstyle.checks.blocks.EmptyLineWrappingInBlockOption;

public class XpathRegressionEmptyLineWrappingInBlockTest extends AbstractXpathTestSupport {

    private static final String ALL_TOKENS = "CLASS_DEF, CTOR_DEF, ENUM_DEF, INTERFACE_DEF, "
        + "LITERAL_DO, LITERAL_TRY, LITERAL_ELSE, LITERAL_FOR, LITERAL_IF, LITERAL_SWITCH, "
        + "LITERAL_WHILE, METHOD_DEF, STATIC_INIT";

    private final String checkName = EmptyLineWrappingInBlockCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testTopSeparator() throws Exception {
        final File fileToProcess = new File(getPath(
            "SuppressionXpathRegressionEmptyLineWrappingInBlockTopSeparator.java"
        ));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(EmptyLineWrappingInBlockCheck.class);
        moduleConfig.addAttribute("tokens", ALL_TOKENS);
        moduleConfig.addAttribute("topSeparator",
            EmptyLineWrappingInBlockOption.EMPTY_LINE.toString());

        final String[] expectedViolation = {
            "6:5: " + getCheckMessage(EmptyLineWrappingInBlockCheck.class,
                EmptyLineWrappingInBlockCheck.MSG_EMPTY_LINE_BEFORE),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/CLASS_DEF[."
                + "/IDENT[@text='SuppressionXpathRegressionEmptyLineWrappingInBlockTopSeparator']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test2']]",
            "/CLASS_DEF[."
                + "/IDENT[@text='SuppressionXpathRegressionEmptyLineWrappingInBlockTopSeparator']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test2']]/MODIFIERS",
            "/CLASS_DEF[."
                + "/IDENT[@text='SuppressionXpathRegressionEmptyLineWrappingInBlockTopSeparator']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test2']]/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testBottomSeparator() throws Exception {
        final File fileToProcess = new File(getPath(
            "SuppressionXpathRegressionEmptyLineWrappingInBlockBottomSeparator.java"
        ));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(EmptyLineWrappingInBlockCheck.class);
        moduleConfig.addAttribute("tokens", ALL_TOKENS);
        moduleConfig.addAttribute("bottomSeparator",
            EmptyLineWrappingInBlockOption.EMPTY_LINE.toString());

        final String[] expectedViolation = {
            "6:5: " + getCheckMessage(EmptyLineWrappingInBlockCheck.class,
                EmptyLineWrappingInBlockCheck.MSG_EMPTY_LINE_AFTER),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/CLASS_DEF[./IDENT[@text="
                + "'SuppressionXpathRegressionEmptyLineWrappingInBlockBottomSeparator']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test2']]",
            "/CLASS_DEF[./IDENT[@text="
                + "'SuppressionXpathRegressionEmptyLineWrappingInBlockBottomSeparator']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test2']]/MODIFIERS",
            "/CLASS_DEF[./IDENT[@text="
                + "'SuppressionXpathRegressionEmptyLineWrappingInBlockBottomSeparator']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test2']]/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }
}
