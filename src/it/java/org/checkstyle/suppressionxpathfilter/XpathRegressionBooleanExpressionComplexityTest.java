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
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.metrics.BooleanExpressionComplexityCheck;

public class XpathRegressionBooleanExpressionComplexityTest
    extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return BooleanExpressionComplexityCheck.class.getSimpleName();
    }

    @Test
    public void testCatchBlock() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathBooleanExpressionComplexityCatchBlock.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(BooleanExpressionComplexityCheck.class);

        final String[] expectedViolationMessages = {
            "10:23: " + getCheckMessage(BooleanExpressionComplexityCheck.class,
                BooleanExpressionComplexityCheck.MSG_KEY, 11, 3),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathBooleanExpressionComplexityCatchBlock']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='methodOne']]/SLIST"
                + "/LITERAL_TRY/LITERAL_CATCH/SLIST/VARIABLE_DEF"
                + "[./IDENT[@text='d']]/ASSIGN"
        );

        runVerifications(moduleConfig, fileToProcess,
            expectedViolationMessages, expectedXpathQueries);
    }

    @Test
    public void testClassFields() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathBooleanExpressionComplexityClassFields.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(BooleanExpressionComplexityCheck.class);

        final String[] expectedViolationMessages = {
            "9:19: " + getCheckMessage(BooleanExpressionComplexityCheck.class,
                BooleanExpressionComplexityCheck.MSG_KEY, 11, 3),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathBooleanExpressionComplexityClassFields']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='methodTwo']]/SLIST/VARIABLE_DEF"
                + "[./IDENT[@text='d']]/ASSIGN"
        );

        runVerifications(moduleConfig, fileToProcess,
            expectedViolationMessages, expectedXpathQueries);
    }

    @Test
    public void testConditionals() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathBooleanExpressionComplexityConditionals.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(BooleanExpressionComplexityCheck.class);

        final String[] expectedViolationMessages = {
            "9:9: " + getCheckMessage(BooleanExpressionComplexityCheck.class,
                BooleanExpressionComplexityCheck.MSG_KEY, 4, 3),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathBooleanExpressionComplexityConditionals']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='methodThree']]/SLIST/LITERAL_IF"
        );

        runVerifications(moduleConfig, fileToProcess,
            expectedViolationMessages, expectedXpathQueries);
    }
}
