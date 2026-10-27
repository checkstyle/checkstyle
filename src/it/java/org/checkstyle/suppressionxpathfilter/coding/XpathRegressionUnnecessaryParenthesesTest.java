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

package org.checkstyle.suppressionxpathfilter.coding;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck;

public class XpathRegressionUnnecessaryParenthesesTest extends AbstractXpathTestSupport {

    private final String checkName = UnnecessaryParenthesesCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/unnecessaryparentheses";
    }

    @Test
    public void testClassFields() throws Exception {
        final String fileToProcess =
            getPath("InputXpathUnnecessaryParenthesesClassFields.java");

        final String[] expectedViolation = {
            "18:13: " + getCheckMessage(UnnecessaryParenthesesCheck.class,
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

        verifyXpathWithInlineConfigParser(fileToProcess, expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testLambdas() throws Exception {
        final String fileToProcess =
            getPath("InputXpathUnnecessaryParenthesesLambdas.java");

        final String[] expectedViolation = {
            "22:35: " + getCheckMessage(UnnecessaryParenthesesCheck.class,
                UnnecessaryParenthesesCheck.MSG_LAMBDA),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathUnnecessaryParenthesesLambdas']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='predicate']]"
                + "/ASSIGN/LAMBDA"
        );

        verifyXpathWithInlineConfigParser(fileToProcess, expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testConditionals() throws Exception {
        final String fileToProcess =
            getPath("InputXpathUnnecessaryParenthesesConditionals.java");

        final String[] expectedViolation = {
            "19:13: " + getCheckMessage(UnnecessaryParenthesesCheck.class,
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

        verifyXpathWithInlineConfigParser(fileToProcess, expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testLocalVariables() throws Exception {
        final String fileToProcess =
            getPath("InputXpathUnnecessaryParenthesesLocalVariables.java");

        final String[] expectedViolation = {
            "19:18: " + getCheckMessage(UnnecessaryParenthesesCheck.class,
                UnnecessaryParenthesesCheck.MSG_IDENT, "a"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathUnnecessaryParenthesesLocalVariables']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='b']]"
                + "/ASSIGN/EXPR/PLUS/IDENT[@text='a']"
        );

        verifyXpathWithInlineConfigParser(fileToProcess, expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testStringLiteral() throws Exception {
        final String fileToProcess =
            getPath("InputXpathUnnecessaryParenthesesStringLiteral.java");

        final String[] expectedViolation = {
            "20:23: " + getCheckMessage(UnnecessaryParenthesesCheck.class,
                UnnecessaryParenthesesCheck.MSG_STRING, "\"Checkstyle\""),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathUnnecessaryParenthesesStringLiteral']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='str']]"
                + "/ASSIGN/EXPR/PLUS/STRING_LITERAL[@text='Checkstyle']"
        );

        verifyXpathWithInlineConfigParser(fileToProcess, expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testMethodDef() throws Exception {
        final String fileToProcess =
            getPath("InputXpathUnnecessaryParenthesesMethodDef.java");

        final String[] expectedViolation = {
            "19:18: " + getCheckMessage(UnnecessaryParenthesesCheck.class,
                UnnecessaryParenthesesCheck.MSG_LITERAL, "10"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathUnnecessaryParenthesesMethodDef']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='a']]"
                + "/ASSIGN/EXPR/PLUS/NUM_INT[@text='10']"
        );

        verifyXpathWithInlineConfigParser(fileToProcess, expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testReturnExpr() throws Exception {
        final String fileToProcess =
            getPath("InputXpathUnnecessaryParenthesesReturnExpr.java");

        final String[] expectedViolation = {
            "19:16: " + getCheckMessage(UnnecessaryParenthesesCheck.class,
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

        verifyXpathWithInlineConfigParser(fileToProcess, expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testExprWithMethodParam() throws Exception {
        final String fileToProcess =
            getPath("InputXpathUnnecessaryParenthesesExprWithMethodParam.java");

        final String[] expectedViolation = {
            "19:17: " + getCheckMessage(UnnecessaryParenthesesCheck.class,
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

        verifyXpathWithInlineConfigParser(fileToProcess, expectedXpathQueries, expectedViolation);
    }

}
