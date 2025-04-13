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

import static com.puppycrawl.tools.checkstyle.checks.coding.SimplifyBooleanExpressionCheck.MSG_KEY;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.SimplifyBooleanExpressionCheck;

public class XpathRegressionSimplifyBooleanExpressionTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return SimplifyBooleanExpressionCheck.class.getSimpleName();
    }

    @Test
    public void testSimple() throws Exception {
        final String fileName = "InputXpathSimplifyBooleanExpressionSimple.java";
        final File fileToProcess = new File(getPath(fileName));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(SimplifyBooleanExpressionCheck.class);

        final String[] expectedViolations = {
            "8:13: " + getCheckMessage(SimplifyBooleanExpressionCheck.class, MSG_KEY),
        };

        final List<String> expectedXpathQuery = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathSimplifyBooleanExpressionSimple']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_IF/EXPR",
                 "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathSimplifyBooleanExpressionSimple']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_IF/EXPR/LNOT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQuery);
    }

    @Test
    public void testAnonymous() throws Exception {
        final String fileName =
                "InputXpathSimplifyBooleanExpressionAnonymous.java";
        final File fileToProcess = new File(getPath(fileName));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(SimplifyBooleanExpressionCheck.class);

        final String[] expectedViolations = {
            "8:19: " + getCheckMessage(SimplifyBooleanExpressionCheck.class, MSG_KEY),
        };

        final List<String> expectedXpathQuery = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathSimplifyBooleanExpressionAnonymous']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Inner']]/OBJBLOCK/METHOD_DEF"
                + "[./IDENT[@text='test']]/SLIST/LITERAL_IF/EXPR",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathSimplifyBooleanExpressionAnonymous']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Inner']]/OBJBLOCK/METHOD_DEF"
                + "[./IDENT[@text='test']]/SLIST/LITERAL_IF/EXPR/EQUAL[./IDENT[@text='a']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQuery);
    }

    @Test
    public void testInterface() throws Exception {
        final String fileName =
                "InputXpathSimplifyBooleanExpressionInterface.java";
        final File fileToProcess = new File(getPath(fileName));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(SimplifyBooleanExpressionCheck.class);

        final String[] expectedViolations = {
            "7:20: " + getCheckMessage(SimplifyBooleanExpressionCheck.class, MSG_KEY),
        };

        final List<String> expectedXpathQuery = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathSimplifyBooleanExpressionInterface']]"
                + "/OBJBLOCK/INTERFACE_DEF[./IDENT[@text='Inner']]/OBJBLOCK/METHOD_DEF[./IDENT"
                + "[@text='test']]/SLIST/LITERAL_IF/EXPR/LNOT/NOT_EQUAL[./IDENT[@text='b']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQuery);
    }
}
