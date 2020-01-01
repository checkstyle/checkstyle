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
import com.puppycrawl.tools.checkstyle.checks.coding.AvoidInlineConditionalsCheck;

public class XpathRegressionAvoidInlineConditionalsTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return AvoidInlineConditionalsCheck.class.getSimpleName();
    }

    @Test
    public void testInlineConditionalsVariableDef() throws Exception {
        final File fileToProcess = new File(
                getPath("SuppressionXpathRegressionAvoidInlineConditionalsVariableDef.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AvoidInlineConditionalsCheck.class);

        final String[] expectedViolation = {
            "5:50: " + getCheckMessage(AvoidInlineConditionalsCheck.class,
                AvoidInlineConditionalsCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text='"
                        + "SuppressionXpathRegressionAvoidInlineConditionalsVariableDef']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='substring']]/SLIST"
                        + "/VARIABLE_DEF[./IDENT[@text='b']]/ASSIGN/EXPR",
                "/CLASS_DEF[./IDENT[@text='"
                        + "SuppressionXpathRegressionAvoidInlineConditionalsVariableDef']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='substring']]/SLIST"
                        + "/VARIABLE_DEF[./IDENT[@text='b']]/ASSIGN/EXPR/QUESTION"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testInlineConditionalsAssign() throws Exception {
        final File fileToProcess = new File(
                getPath("SuppressionXpathRegressionAvoidInlineConditionalsAssign.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AvoidInlineConditionalsCheck.class);

        final String[] expectedViolation = {
            "7:43: " + getCheckMessage(AvoidInlineConditionalsCheck.class,
                AvoidInlineConditionalsCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/CLASS_DEF[./IDENT[@text='"
                        + "SuppressionXpathRegressionAvoidInlineConditionalsAssign']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='setB']]/SLIST"
                        + "/EXPR/ASSIGN[./IDENT[@text='b']]/QUESTION"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testInlineConditionalsAssert() throws Exception {
        final File fileToProcess = new File(
                getPath("SuppressionXpathRegressionAvoidInlineConditionalsAssert.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AvoidInlineConditionalsCheck.class);

        final String[] expectedViolation = {
            "8:31: " + getCheckMessage(AvoidInlineConditionalsCheck.class,
                AvoidInlineConditionalsCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text='"
                        + "SuppressionXpathRegressionAvoidInlineConditionalsAssert']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='assertA']]/SLIST"
                        + "/LITERAL_ASSERT/EXPR[./QUESTION/METHOD_CALL/DOT/IDENT[@text='a']]",
                "/CLASS_DEF[./IDENT[@text='"
                        + "SuppressionXpathRegressionAvoidInlineConditionalsAssert']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='assertA']]/SLIST"
                        + "/LITERAL_ASSERT/EXPR/QUESTION"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
