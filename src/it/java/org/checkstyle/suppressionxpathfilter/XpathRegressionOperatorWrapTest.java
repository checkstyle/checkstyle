///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.OperatorWrapCheck;

public class XpathRegressionOperatorWrapTest extends AbstractXpathTestSupport {

    private final String checkName = OperatorWrapCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOperatorWrapNewLine() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionOperatorWrapNewLine.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(OperatorWrapCheck.class);

        final String[] expectedViolation = {
            "6:19: " + getCheckMessage(OperatorWrapCheck.class,
                        OperatorWrapCheck.MSG_LINE_NEW, "+"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text"
                        + "='SuppressionXpathRegressionOperatorWrapNewLine']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                        + "/SLIST/VARIABLE_DEF[./IDENT[@text='x']]"
                        + "/ASSIGN/EXPR/MINUS[./NUM_INT[@text='4']]"
                        + "/MINUS[./NUM_INT[@text='3']]"
                        + "/PLUS[./NUM_INT[@text='1']]"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testOperatorWrapPreviousLine() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionOperatorWrapPreviousLine.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(OperatorWrapCheck.class);
        moduleConfig.addProperty("tokens", "ASSIGN");
        moduleConfig.addProperty("option", "eol");

        final String[] expectedViolation = {
            "5:11: " + getCheckMessage(OperatorWrapCheck.class,
                        OperatorWrapCheck.MSG_LINE_PREVIOUS, "="),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
             "/COMPILATION_UNIT"
                + "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionOperatorWrapPreviousLine']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='b']]"
                + "/ASSIGN"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
