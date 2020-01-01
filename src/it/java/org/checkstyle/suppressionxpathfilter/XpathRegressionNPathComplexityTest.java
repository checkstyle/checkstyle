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
import com.puppycrawl.tools.checkstyle.checks.metrics.NPathComplexityCheck;

// -@cs[AbbreviationAsWordInName] Test should be named as its main class.
public class XpathRegressionNPathComplexityTest extends AbstractXpathTestSupport {

    private final String checkName = NPathComplexityCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionNPathComplexityOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(NPathComplexityCheck.class);
        moduleConfig.addAttribute("max", "0");

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(NPathComplexityCheck.class,
                NPathComplexityCheck.MSG_KEY, 3, 0),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionNPathComplexityOne']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='test']]",
            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionNPathComplexityOne']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS",
            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionNPathComplexityOne']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionNPathComplexityTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(NPathComplexityCheck.class);
        moduleConfig.addAttribute("max", "0");

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(NPathComplexityCheck.class,
                NPathComplexityCheck.MSG_KEY, 3, 0),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionNPathComplexityTwo']]"
                + "/OBJBLOCK/STATIC_INIT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
