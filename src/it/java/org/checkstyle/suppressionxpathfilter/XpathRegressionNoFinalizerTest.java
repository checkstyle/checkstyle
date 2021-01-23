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
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.NoFinalizerCheck;

public class XpathRegressionNoFinalizerTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return NoFinalizerCheck.class.getSimpleName();
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess = new File(
                getPath("SuppressionXpathRegressionNoFinalizer1.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(NoFinalizerCheck.class);

        final String[] expectedViolation = {
            "8:5: " + getCheckMessage(NoFinalizerCheck.class,
                    NoFinalizerCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionNoFinalizer1']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='finalize']]",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionNoFinalizer1']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='finalize']]/MODIFIERS",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionNoFinalizer1']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='finalize']]/MODIFIERS/LITERAL_PROTECTED"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess = new File(
                getPath("SuppressionXpathRegressionNoFinalizer2.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(NoFinalizerCheck.class);

        final String[] expectedViolation = {
            "10:9: " + getCheckMessage(NoFinalizerCheck.class,
                    NoFinalizerCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionNoFinalizer2']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='InnerClass']]/OBJBLOCK/"
                + "METHOD_DEF[./IDENT[@text='finalize']]",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionNoFinalizer2']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='InnerClass']]/OBJBLOCK/"
                + "METHOD_DEF[./IDENT[@text='finalize']]/MODIFIERS",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionNoFinalizer2']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='InnerClass']]/OBJBLOCK/"
                + "METHOD_DEF[./IDENT[@text='finalize']]/MODIFIERS/"
                + "ANNOTATION[./IDENT[@text='Override']]",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionNoFinalizer2']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='InnerClass']]/OBJBLOCK/"
                + "METHOD_DEF[./IDENT[@text='finalize']]/MODIFIERS/"
                + "ANNOTATION[./IDENT[@text='Override']]/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
