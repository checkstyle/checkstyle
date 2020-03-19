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
import com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck;

public class XpathRegressionFinalClassTest extends AbstractXpathTestSupport {
    private final String checkName = FinalClassCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionFinalClass1.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(FinalClassCheck.class);

        final String[] expectedViolation = {
            "3:1: " + getCheckMessage(FinalClassCheck.class,
                    FinalClassCheck.MSG_KEY, "SuppressionXpathRegressionFinalClass1"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT["
                        + "@text='SuppressionXpathRegressionFinalClass1']]",
                "/CLASS_DEF[./IDENT["
                        + "@text='SuppressionXpathRegressionFinalClass1']]/MODIFIERS",
                "/CLASS_DEF[./IDENT["
                        + "@text='SuppressionXpathRegressionFinalClass1']]/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionFinalClass2.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(FinalClassCheck.class);

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(FinalClassCheck.class,
                    FinalClassCheck.MSG_KEY, "Test"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT["
                        + "@text='SuppressionXpathRegressionFinalClass2']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]",
                "/CLASS_DEF[./IDENT["
                        + "@text='SuppressionXpathRegressionFinalClass2']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]/MODIFIERS",
                "/CLASS_DEF[./IDENT["
                        + "@text='SuppressionXpathRegressionFinalClass2']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]/LITERAL_CLASS"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}

