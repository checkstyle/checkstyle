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
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck;

public class XpathRegressionUnnecessaryParenthesesTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return UnnecessaryParenthesesCheck.class.getSimpleName();
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess = new File(
            getPath("SuppressionXpathRegressionUnnecessaryParentheses1.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(UnnecessaryParenthesesCheck.class);

        final String[] expectedViolation = {
            "4:13: " + getCheckMessage(UnnecessaryParenthesesCheck.class,
                UnnecessaryParenthesesCheck.MSG_ASSIGN),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionUnnecessaryParentheses1']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='a']]"
                + "/ASSIGN/EXPR",

            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionUnnecessaryParentheses1']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='a']]"
                + "/ASSIGN/EXPR/LPAREN"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess = new File(
            getPath("SuppressionXpathRegressionUnnecessaryParentheses2.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(UnnecessaryParenthesesCheck.class);

        final String[] expectedViolation = {
            "5:13: " + getCheckMessage(UnnecessaryParenthesesCheck.class,
                UnnecessaryParenthesesCheck.MSG_EXPR),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionUnnecessaryParentheses2']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/LITERAL_IF/EXPR",

            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionUnnecessaryParentheses2']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/LITERAL_IF/EXPR/LPAREN"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess = new File(
            getPath("SuppressionXpathRegressionUnnecessaryParentheses3.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(UnnecessaryParenthesesCheck.class);

        final String[] expectedViolation = {
            "7:35: " + getCheckMessage(UnnecessaryParenthesesCheck.class,
                UnnecessaryParenthesesCheck.MSG_LAMBDA),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionUnnecessaryParentheses3']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='predicate']]"
                + "/ASSIGN/LAMBDA"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testFour() throws Exception {
        final File fileToProcess = new File(
            getPath("SuppressionXpathRegressionUnnecessaryParentheses4.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(UnnecessaryParenthesesCheck.class);

        final String[] expectedViolation = {
            "5:18: " + getCheckMessage(UnnecessaryParenthesesCheck.class,
                UnnecessaryParenthesesCheck.MSG_IDENT, "a"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionUnnecessaryParentheses4']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='b']]"
                + "/ASSIGN/EXPR/PLUS/IDENT[@text='a']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testFive() throws Exception {
        final File fileToProcess = new File(
            getPath("SuppressionXpathRegressionUnnecessaryParentheses5.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(UnnecessaryParenthesesCheck.class);

        final String[] expectedViolation = {
            "5:23: " + getCheckMessage(UnnecessaryParenthesesCheck.class,
                UnnecessaryParenthesesCheck.MSG_STRING, "\"Checkstyle\""),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionUnnecessaryParentheses5']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='str']]"
                + "/ASSIGN/EXPR/PLUS/STRING_LITERAL[@text='Checkstyle']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testSix() throws Exception {
        final File fileToProcess = new File(
            getPath("SuppressionXpathRegressionUnnecessaryParentheses6.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(UnnecessaryParenthesesCheck.class);

        final String[] expectedViolation = {
            "5:18: " + getCheckMessage(UnnecessaryParenthesesCheck.class,
                UnnecessaryParenthesesCheck.MSG_LITERAL, "10"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionUnnecessaryParentheses6']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='a']]"
                + "/ASSIGN/EXPR/PLUS/NUM_INT[@text='10']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testSeven() throws Exception {
        final File fileToProcess = new File(
            getPath("SuppressionXpathRegressionUnnecessaryParentheses7.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(UnnecessaryParenthesesCheck.class);

        final String[] expectedViolation = {
            "5:16: " + getCheckMessage(UnnecessaryParenthesesCheck.class,
                UnnecessaryParenthesesCheck.MSG_RETURN),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionUnnecessaryParentheses7']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/LITERAL_RETURN/EXPR",

            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionUnnecessaryParentheses7']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/LITERAL_RETURN/EXPR/LPAREN"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testEight() throws Exception {
        final File fileToProcess = new File(
            getPath("SuppressionXpathRegressionUnnecessaryParentheses8.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(UnnecessaryParenthesesCheck.class);

        final String[] expectedViolation = {
            "5:17: " + getCheckMessage(UnnecessaryParenthesesCheck.class,
                UnnecessaryParenthesesCheck.MSG_ASSIGN),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionUnnecessaryParentheses8']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='c']]"
                + "/ASSIGN/EXPR",

            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionUnnecessaryParentheses8']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='c']]"
                + "/ASSIGN/EXPR/LPAREN"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
