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
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.design.MutableExceptionCheck;

public class XpathRegressionMutableExceptionTest extends AbstractXpathTestSupport {

    private final String checkName = MutableExceptionCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testDefault() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathMutableExceptionDefault.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(MutableExceptionCheck.class);

        final String[] expectedViolation = {
            "5:9: " + getCheckMessage(MutableExceptionCheck.class,
                MutableExceptionCheck.MSG_KEY, "code"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMutableExceptionDefault']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='FooException']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='code']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMutableExceptionDefault']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='FooException']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='code']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMutableExceptionDefault']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='FooException']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='code']]/MODIFIERS/LITERAL_PRIVATE"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testClassName() throws Exception {
        final String classFormat = "^.*ExceptionClassName$";
        final File fileToProcess =
            new File(getPath("InputXpathMutableExceptionClassName.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(MutableExceptionCheck.class);
        moduleConfig.addProperty("format", classFormat);

        final String[] expectedViolation = {
            "4:3: " + getCheckMessage(MutableExceptionCheck.class,
                MutableExceptionCheck.MSG_KEY, "code"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMutableExceptionClassName']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='code']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMutableExceptionClassName']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='code']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMutableExceptionClassName']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='code']]/TYPE",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMutableExceptionClassName']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='code']]/TYPE/LITERAL_INT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testExtendedClassName() throws Exception {
        final String extendedClassNameFormat = "^.*Throwable$";
        final File fileToProcess =
            new File(getPath("InputXpathMutableExceptionExtendedClassName.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(MutableExceptionCheck.class);
        moduleConfig.addProperty("extendedClassNameFormat", extendedClassNameFormat);

        final String[] expectedViolation = {
            "6:9: " + getCheckMessage(MutableExceptionCheck.class,
                MutableExceptionCheck.MSG_KEY, "code"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMutableExceptionExtendedClassName']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='FooException']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='code']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMutableExceptionExtendedClassName']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='FooException']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='code']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMutableExceptionExtendedClassName']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='FooException']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='code']]/MODIFIERS/LITERAL_PRIVATE");

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }
}
