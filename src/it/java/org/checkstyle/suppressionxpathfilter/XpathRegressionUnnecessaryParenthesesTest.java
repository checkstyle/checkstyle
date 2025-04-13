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
import com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck;

public class XpathRegressionUnnecessaryParenthesesTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return UnnecessaryParenthesesCheck.class.getSimpleName();
    }

    @Test
    public void testClassFields() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathUnnecessaryParenthesesClassFields.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(UnnecessaryParenthesesCheck.class);

        final String[] expectedViolation = {
            "4:13: " + getCheckMessage(UnnecessaryParenthesesCheck.class,
                UnnecessaryParenthesesCheck.MSG_ASSIGN),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathUnnecessaryParenthesesClassFields']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='a']]"
                + "/ASSIGN/EXPR",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathUnnecessaryParenthesesClassFields']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='a']]"
                + "/ASSIGN/EXPR/LPAREN"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testConditionals() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathUnnecessaryParenthesesConditionals.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(UnnecessaryParenthesesCheck.class);

        final String[] expectedViolation = {
            "5:13: " + getCheckMessage(UnnecessaryParenthesesCheck.class,
                UnnecessaryParenthesesCheck.MSG_EXPR),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathUnnecessaryParenthesesConditionals']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/LITERAL_IF/EXPR",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathUnnecessaryParenthesesConditionals']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/LITERAL_IF/EXPR/LPAREN"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testLambdas() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathUnnecessaryParenthesesLambdas.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(UnnecessaryParenthesesCheck.class);

        final String[] expectedViolation = {
            "7:35: " + getCheckMessage(UnnecessaryParenthesesCheck.class,
                UnnecessaryParenthesesCheck.MSG_LAMBDA),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathUnnecessaryParenthesesLambdas']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='predicate']]"
                + "/ASSIGN/LAMBDA"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testLocalVariables() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathUnnecessaryParenthesesLocalVariables.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(UnnecessaryParenthesesCheck.class);

        final String[] expectedViolation = {
            "5:18: " + getCheckMessage(UnnecessaryParenthesesCheck.class,
                UnnecessaryParenthesesCheck.MSG_IDENT, "a"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathUnnecessaryParenthesesLocalVariables']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='b']]"
                + "/ASSIGN/EXPR/PLUS/IDENT[@text='a']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testStringLiteral() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathUnnecessaryParenthesesStringLiteral.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(UnnecessaryParenthesesCheck.class);

        final String[] expectedViolation = {
            "5:23: " + getCheckMessage(UnnecessaryParenthesesCheck.class,
                UnnecessaryParenthesesCheck.MSG_STRING, "\"Checkstyle\""),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathUnnecessaryParenthesesStringLiteral']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='str']]"
                + "/ASSIGN/EXPR/PLUS/STRING_LITERAL[@text='Checkstyle']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testMethodDef() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathUnnecessaryParenthesesMethodDef.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(UnnecessaryParenthesesCheck.class);

        final String[] expectedViolation = {
            "5:18: " + getCheckMessage(UnnecessaryParenthesesCheck.class,
                UnnecessaryParenthesesCheck.MSG_LITERAL, "10"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathUnnecessaryParenthesesMethodDef']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='a']]"
                + "/ASSIGN/EXPR/PLUS/NUM_INT[@text='10']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testReturnExpr() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathUnnecessaryParenthesesReturnExpr.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(UnnecessaryParenthesesCheck.class);

        final String[] expectedViolation = {
            "5:16: " + getCheckMessage(UnnecessaryParenthesesCheck.class,
                UnnecessaryParenthesesCheck.MSG_RETURN),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathUnnecessaryParenthesesReturnExpr']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/LITERAL_RETURN/EXPR",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathUnnecessaryParenthesesReturnExpr']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/LITERAL_RETURN/EXPR/LPAREN"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testExprWithMethodParam() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathUnnecessaryParenthesesExprWithMethodParam.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(UnnecessaryParenthesesCheck.class);

        final String[] expectedViolation = {
            "5:17: " + getCheckMessage(UnnecessaryParenthesesCheck.class,
                UnnecessaryParenthesesCheck.MSG_ASSIGN),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathUnnecessaryParenthesesExprWithMethodParam']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='c']]"
                + "/ASSIGN/EXPR",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathUnnecessaryParenthesesExprWithMethodParam']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='c']]"
                + "/ASSIGN/EXPR/LPAREN"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
