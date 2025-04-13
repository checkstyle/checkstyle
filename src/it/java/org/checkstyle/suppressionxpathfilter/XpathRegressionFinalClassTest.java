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

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class XpathRegressionFinalClassTest extends AbstractXpathTestSupport {
    private final String checkName = FinalClassCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testDefault() throws Exception {
        final File fileToProcess = new File(getPath(
            "InputXpathFinalClassDefault.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(FinalClassCheck.class);

        final String[] expectedViolation = {
            "3:1: " + getCheckMessage(FinalClassCheck.class,
                FinalClassCheck.MSG_KEY, "InputXpathFinalClassDefault"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathFinalClassDefault']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathFinalClassDefault']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathFinalClassDefault']]/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testInnerClass() throws Exception {
        final File fileToProcess = new File(getPath(
            "InputXpathFinalClassInnerClass.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(FinalClassCheck.class);

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(FinalClassCheck.class,
                FinalClassCheck.MSG_KEY, "Test"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathFinalClassInnerClass']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathFinalClassInnerClass']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathFinalClassInnerClass']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]/LITERAL_CLASS"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }
}

