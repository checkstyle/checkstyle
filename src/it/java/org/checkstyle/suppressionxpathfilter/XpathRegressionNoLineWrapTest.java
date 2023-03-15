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
import com.puppycrawl.tools.checkstyle.checks.whitespace.NoLineWrapCheck;

public class XpathRegressionNoLineWrapTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return NoLineWrapCheck.class.getSimpleName();
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess = new File(
                getPath("SuppressionXpathRegressionNoLineWrap1.java")
        );

        final DefaultConfiguration moduleConfig =
                createModuleConfig(NoLineWrapCheck.class);

        final String[] expectedViolation = {
            "1:1: " + getCheckMessage(NoLineWrapCheck.class,
                    NoLineWrapCheck.MSG_KEY, "package"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT", "/COMPILATION_UNIT/PACKAGE_DEF"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess = new File(
                getPath("SuppressionXpathRegressionNoLineWrap2.java")
        );

        final DefaultConfiguration moduleConfig =
                createModuleConfig(NoLineWrapCheck.class);
        moduleConfig.addProperty("tokens", "METHOD_DEF");

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(NoLineWrapCheck.class,
                    NoLineWrapCheck.MSG_KEY, "METHOD_DEF"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='SuppressionXpathRegressionNoLineWrap2']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test2']]",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='SuppressionXpathRegressionNoLineWrap2']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test2']]"
                    + "/MODIFIERS",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='SuppressionXpathRegressionNoLineWrap2']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test2']]"
                    + "/MODIFIERS/LITERAL_PUBLIC"

                );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess = new File(
                getPath("SuppressionXpathRegressionNoLineWrap3.java")
        );

        final DefaultConfiguration moduleConfig =
                createModuleConfig(NoLineWrapCheck.class);
        moduleConfig.addProperty("tokens", "CTOR_DEF");

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(NoLineWrapCheck.class,
                    NoLineWrapCheck.MSG_KEY, "CTOR_DEF"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='SuppressionXpathRegressionNoLineWrap3']]"
                    + "/OBJBLOCK/CTOR_DEF[./IDENT[@text='SuppressionXpathRegressionNoLineWrap3']]",

                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='SuppressionXpathRegressionNoLineWrap3']]"
                    + "/OBJBLOCK/CTOR_DEF[./IDENT[@text='SuppressionXpathRegressionNoLineWrap3']]"
                    + "/MODIFIERS",

                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='SuppressionXpathRegressionNoLineWrap3']]"
                    + "/OBJBLOCK/CTOR_DEF/IDENT[@text='SuppressionXpathRegressionNoLineWrap3']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

}
