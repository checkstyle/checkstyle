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
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.UpperEllCheck;

public class XpathRegressionUpperEllTest extends AbstractXpathTestSupport {

    private final String checkName = UpperEllCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testUpperEllOne() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionUpperEllFirst.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(UpperEllCheck.class);

        final String[] expectedViolation = {
            "4:16: " + getCheckMessage(UpperEllCheck.class,
                    UpperEllCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionUpperEllFirst']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='bad']]/ASSIGN/EXPR[./NUM_LONG[@text='0l']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionUpperEllFirst']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='bad']]/ASSIGN/EXPR"
                + "/NUM_LONG[@text='0l']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testUpperEllTwo() throws Exception {
        final File fileToProcess =
            new File(getPath("SuppressionXpathRegressionUpperEllSecond.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(UpperEllCheck.class);

        final String[] expectedViolation = {
            "6:21: " + getCheckMessage(UpperEllCheck.class,
                                       UpperEllCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/INTERFACE_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionUpperEllSecond']]/OBJBLOCK/METHOD_DEF"
                + "[./IDENT[@text='test']]/SLIST/VARIABLE_DEF[./IDENT[@text='var2']]/ASSIGN/EXPR"
                + "[./NUM_LONG[@text='508987l']]",
            "/COMPILATION_UNIT/INTERFACE_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionUpperEllSecond']]/OBJBLOCK/METHOD_DEF"
                + "[./IDENT[@text='test']]/SLIST/VARIABLE_DEF[./IDENT[@text='var2']]/ASSIGN/EXPR"
                + "/NUM_LONG[@text='508987l']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                         expectedXpathQueries);
    }
}
