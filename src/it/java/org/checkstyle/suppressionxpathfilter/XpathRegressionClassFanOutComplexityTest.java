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
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.metrics.ClassFanOutComplexityCheck;

public class XpathRegressionClassFanOutComplexityTest extends AbstractXpathTestSupport {

    private final String checkName = ClassFanOutComplexityCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testInClass() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathClassFanOutComplexityClass.java")
        );
        final DefaultConfiguration moduleConfig =
            createModuleConfig(ClassFanOutComplexityCheck.class);
        moduleConfig.addProperty("max", "0");

        final String[] expectedViolation = {
            "6:1: " + getCheckMessage(ClassFanOutComplexityCheck.class,
                ClassFanOutComplexityCheck.MSG_KEY, 4, 0),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathClassFanOutComplexityClass']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathClassFanOutComplexityClass']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathClassFanOutComplexityClass']]/MODIFIERS/LITERAL_PUBLIC"

        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testInInterface() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathClassFanOutComplexityInterface.java")
        );
        final DefaultConfiguration moduleConfig =
            createModuleConfig(ClassFanOutComplexityCheck.class);
        moduleConfig.addProperty("max", "0");

        final String[] expectedViolation = {
            "7:1: " + getCheckMessage(ClassFanOutComplexityCheck.class,
                ClassFanOutComplexityCheck.MSG_KEY, 1, 0),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/INTERFACE_DEF"
                + "[./IDENT[@text='BadInterface']]",
            "/COMPILATION_UNIT/INTERFACE_DEF"
                + "[./IDENT[@text='BadInterface']]/MODIFIERS",
            "/COMPILATION_UNIT/INTERFACE_DEF"
                + "[./IDENT[@text='BadInterface']]/LITERAL_INTERFACE"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testInEnum() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathClassFanOutComplexityEnum.java")
        );
        final DefaultConfiguration moduleConfig =
            createModuleConfig(ClassFanOutComplexityCheck.class);
        moduleConfig.addProperty("max", "0");

        final String[] expectedViolation = {
            "9:1: " + getCheckMessage(ClassFanOutComplexityCheck.class,
                ClassFanOutComplexityCheck.MSG_KEY, 1, 0),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/ENUM_DEF"
                + "[./IDENT[@text='MyEnum']]",
            "/COMPILATION_UNIT/ENUM_DEF"
                + "[./IDENT[@text='MyEnum']]/MODIFIERS",
            "/COMPILATION_UNIT/ENUM_DEF"
                + "[./IDENT[@text='MyEnum']]/ENUM"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
