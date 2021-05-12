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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyLineSeparatorCheck;

public class XpathRegressionEmptyLineSeparatorTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return EmptyLineSeparatorCheck.class.getSimpleName();
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess = new File(
                getPath("SuppressionXpathRegressionEmptyLineSeparator1.java")
        );

        final DefaultConfiguration moduleConfig =
                createModuleConfig(EmptyLineSeparatorCheck.class);
        moduleConfig.addAttribute("tokens", "PACKAGE_DEF");

        final String[] expectedViolation = {
            "4:1: " + getCheckMessage(EmptyLineSeparatorCheck.class,
                    EmptyLineSeparatorCheck.MSG_SHOULD_BE_SEPARATED, "package"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/PACKAGE_DEF"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess = new File(
                getPath("SuppressionXpathRegressionEmptyLineSeparator2.java")
        );

        final DefaultConfiguration moduleConfig =
                createModuleConfig(EmptyLineSeparatorCheck.class);
        moduleConfig.addAttribute("allowMultipleEmptyLines", "false");

        final String[] expectedViolation = {
            "6:1: " + getCheckMessage(EmptyLineSeparatorCheck.class,
                    EmptyLineSeparatorCheck.MSG_MULTIPLE_LINES, "package"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/PACKAGE_DEF"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess = new File(
                getPath("SuppressionXpathRegressionEmptyLineSeparator3.java")
        );

        final DefaultConfiguration moduleConfig =
                createModuleConfig(EmptyLineSeparatorCheck.class);
        moduleConfig.addAttribute("tokens", "METHOD_DEF");

        final String[] expectedViolation = {
            "9:5: " + getCheckMessage(EmptyLineSeparatorCheck.class,
                    EmptyLineSeparatorCheck.MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionEmptyLineSeparator3']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo1']]",

                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionEmptyLineSeparator3']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo1']]"
                        + "/MODIFIERS",

                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionEmptyLineSeparator3']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo1']]"
                        + "/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testFour() throws Exception {
        final File fileToProcess = new File(
                getPath("SuppressionXpathRegressionEmptyLineSeparator4.java")
        );

        final DefaultConfiguration moduleConfig =
                createModuleConfig(EmptyLineSeparatorCheck.class);
        moduleConfig.addAttribute("allowMultipleEmptyLines", "false");

        final String[] expectedViolation = {
            "12:25: " + getCheckMessage(EmptyLineSeparatorCheck.class,
                    EmptyLineSeparatorCheck.MSG_MULTIPLE_LINES_AFTER, "}"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionEmptyLineSeparator4']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo1']]/SLIST/RCURLY"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testFive() throws Exception {
        final File fileToProcess = new File(
                getPath("SuppressionXpathRegressionEmptyLineSeparator5.java")
        );

        final DefaultConfiguration moduleConfig =
                createModuleConfig(EmptyLineSeparatorCheck.class);
        moduleConfig.addAttribute("allowMultipleEmptyLines", "false");
        moduleConfig.addAttribute("allowMultipleEmptyLinesInsideClassMembers", "false");

        final String[] expectedViolation = {
            "14:15: " + getCheckMessage(EmptyLineSeparatorCheck.class,
                    EmptyLineSeparatorCheck.MSG_MULTIPLE_LINES_INSIDE),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionEmptyLineSeparator5']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo1']]/SLIST/LITERAL_TRY/SLIST"
                        + "/SINGLE_LINE_COMMENT/COMMENT_CONTENT[@text=' warn\\n']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
