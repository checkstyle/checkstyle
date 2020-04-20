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
}
