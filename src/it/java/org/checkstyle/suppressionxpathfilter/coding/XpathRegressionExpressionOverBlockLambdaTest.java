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

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.ExpressionOverBlockLambdaCheck;

public class XpathRegressionExpressionOverBlockLambdaTest
    extends AbstractXpathTestSupport {

    private final String checkName =
            ExpressionOverBlockLambdaCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/expressionoverblocklambda";
    }

    @Test
    public void testDefault() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathExpressionOverBlockLambdaDefault.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ExpressionOverBlockLambdaCheck.class);

        final String[] expectedViolation = {
            "4:21: " + getCheckMessage(ExpressionOverBlockLambdaCheck.class,
                    ExpressionOverBlockLambdaCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathExpressionOverBlockLambdaDefault']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='a']]/ASSIGN/LAMBDA"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testMethodCall() throws Exception {
        final File fileToProcess = new File(
                getPath(
                    "InputXpathExpressionOver"
                    + "BlockLambdaMethodCall.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(
                    ExpressionOverBlockLambdaCheck.class);

        final String[] expectedViolation = {
            "7:24: " + getCheckMessage(
                    ExpressionOverBlockLambdaCheck.class,
                    ExpressionOverBlockLambdaCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries =
            List.of(
                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text="
                    + "'InputXpathExpressionOver"
                    + "BlockLambdaMethodCall']]"
                    + "/OBJBLOCK/METHOD_DEF"
                    + "[./IDENT[@text='testMethod']]"
                    + "/SLIST/EXPR/METHOD_CALL/ELIST",
                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text="
                    + "'InputXpathExpressionOver"
                    + "BlockLambdaMethodCall']]"
                    + "/OBJBLOCK/METHOD_DEF"
                    + "[./IDENT[@text='testMethod']]"
                    + "/SLIST/EXPR/METHOD_CALL"
                    + "/ELIST/LAMBDA"
                    + "[./IDENT[@text='s']]"
            );

        runVerifications(moduleConfig, fileToProcess,
                expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testLocalVar() throws Exception {
        final File fileToProcess = new File(
                getPath(
                    "InputXpathExpressionOver"
                    + "BlockLambdaLocalVar.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(
                    ExpressionOverBlockLambdaCheck.class);

        final String[] expectedViolation = {
            "8:15: " + getCheckMessage(
                    ExpressionOverBlockLambdaCheck.class,
                    ExpressionOverBlockLambdaCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries =
                Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text="
                        + "'InputXpathExpressionOver"
                        + "BlockLambdaLocalVar']]"
                        + "/OBJBLOCK/METHOD_DEF"
                        + "[./IDENT[@text='testLocal']]"
                        + "/SLIST/VARIABLE_DEF"
                        + "[./IDENT[@text='f']]"
                        + "/ASSIGN/LAMBDA"
                        + "[./IDENT[@text='x']]"
        );

        runVerifications(moduleConfig, fileToProcess,
                expectedViolation, expectedXpathQueries);
    }

}
