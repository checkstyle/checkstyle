////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck;
import com.puppycrawl.tools.checkstyle.checks.whitespace.PadOption;
import com.puppycrawl.tools.checkstyle.checks.whitespace.ParenPadCheck;

public class XpathRegressionParenPadTest extends AbstractXpathTestSupport {

    private final String checkName = ParenPadCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testLeftFollowed() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionParenPadLeftFollowed.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ParenPadCheck.class);

        final String[] expectedViolation = {
            "5:12: " + getCheckMessage(ParenPadCheck.class,
                    AbstractParenPadCheck.MSG_WS_FOLLOWED, "("),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionParenPadLeftFollowed']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST/LITERAL_IF/LPAREN"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testLeftNotFollowed() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionParenPadLeftNotFollowed.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ParenPadCheck.class);
        moduleConfig.addAttribute("option", PadOption.SPACE.toString());

        final String[] expectedViolation = {
            "5:12: " + getCheckMessage(ParenPadCheck.class,
                    AbstractParenPadCheck.MSG_WS_NOT_FOLLOWED, "("),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionParenPadLeftNotFollowed']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST/LITERAL_IF/LPAREN"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testRightPreceded() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionParenPadRightPreceded.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ParenPadCheck.class);

        final String[] expectedViolation = {
            "5:19: " + getCheckMessage(ParenPadCheck.class,
                    AbstractParenPadCheck.MSG_WS_PRECEDED, ")"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionParenPadRightPreceded']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST/LITERAL_IF/RPAREN"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testRightNotPreceded() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionParenPadRightNotPreceded.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ParenPadCheck.class);
        moduleConfig.addAttribute("option", PadOption.SPACE.toString());

        final String[] expectedViolation = {
            "5:19: " + getCheckMessage(ParenPadCheck.class,
                    AbstractParenPadCheck.MSG_WS_NOT_PRECEDED, ")"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionParenPadRightNotPreceded']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST/LITERAL_IF/RPAREN"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
