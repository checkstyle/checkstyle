////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.sizes.LambdaBodyLengthCheck;

public class XpathRegressionLambdaBodyLengthTest
        extends AbstractXpathTestSupport {

    private static final Class<LambdaBodyLengthCheck> CLASS =
        LambdaBodyLengthCheck.class;

    @Override
    protected String getCheckName() {
        return CLASS.getSimpleName();
    }

    @Test
    public void testDefault() throws Exception {
        final File fileToProcess = new File(getPath(
            "SuppressionXpathRegressionLambdaBodyLength1.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(CLASS);
        final String[] expectedViolation = {
            "7:48: " + getCheckMessage(CLASS, LambdaBodyLengthCheck.MSG_KEY, 11, 10),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionLambdaBodyLength1']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST"
                + "/VARIABLE_DEF[./IDENT[@text='trimmer']]/ASSIGN/LAMBDA");

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testMaxIsNotDefault() throws Exception {
        final File fileToProcess = new File(getPath(
            "SuppressionXpathRegressionLambdaBodyLength2.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(CLASS);
        moduleConfig.addAttribute("max", "5");
        final String[] expectedViolation = {
            "7:25: " + getCheckMessage(CLASS, LambdaBodyLengthCheck.MSG_KEY, 6, 5),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionLambdaBodyLength2']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST"
                + "/VARIABLE_DEF[./IDENT[@text='r']]/ASSIGN/LAMBDA");

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
