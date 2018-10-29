////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.MethodParamPadCheck;

public class XpathRegressionMethodParamPadTest extends AbstractXpathTestSupport {

    private final String checkName = MethodParamPadCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionMethodParamPadOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MethodParamPadCheck.class);

        final String[] expectedViolation = {
            "4:37: " + getCheckMessage(MethodParamPadCheck.class,
                MethodParamPadCheck.MSG_WS_PRECEDED, "("),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/CLASS_DEF[@text='SuppressionXpathRegressionMethodParamPadOne']/OBJBLOCK"
                + "/METHOD_DEF[@text='InputMethodParamPad']/LPAREN"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionMethodParamPadTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MethodParamPadCheck.class);

        final String[] expectedViolation = {
            "5:13: " + getCheckMessage(MethodParamPadCheck.class,
                MethodParamPadCheck.MSG_LINE_PREVIOUS, "("),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/CLASS_DEF[@text='SuppressionXpathRegressionMethodParamPadTwo']/OBJBLOCK"
                + "/METHOD_DEF[@text='sayHello']/LPAREN"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionMethodParamPadThree.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MethodParamPadCheck.class);
        moduleConfig.addAttribute("option", "space");

        final String[] expectedViolation = {
            "4:25: " + getCheckMessage(MethodParamPadCheck.class,
                MethodParamPadCheck.MSG_WS_NOT_PRECEDED, "("),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/CLASS_DEF[@text='SuppressionXpathRegressionMethodParamPadThree']/OBJBLOCK"
                + "/METHOD_DEF[@text='sayHello']/LPAREN"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
