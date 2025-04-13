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
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractClassNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ClassTypeParameterNameCheck;

public class XpathRegressionClassTypeParameterNameTest extends AbstractXpathTestSupport {

    private final String checkName = ClassTypeParameterNameCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testClass() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathClassTypeParameterNameClass.java"));
        final String pattern = "^[A-Z]$";
        final DefaultConfiguration moduleConfig =
                createModuleConfig(ClassTypeParameterNameCheck.class);

        final String[] expectedViolation = {
            "5:20: " + getCheckMessage(ClassTypeParameterNameCheck.class,
                        AbstractClassNameCheck.MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "abc", pattern),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathClassTypeParameterNameClass']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='MyClass2']]"
                        + "/TYPE_PARAMETERS/TYPE_PARAMETER[./IDENT[@text='abc']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathClassTypeParameterNameClass']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='MyClass2']]/TYPE_PARAMETERS"
                        + "/TYPE_PARAMETER/IDENT[@text='abc']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testInterface() throws Exception {
        final String pattern = "^[A-Z]{2,}$";
        final File fileToProcess =
                new File(getPath("InputXpathClassTypeParameterNameInterface.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ClassTypeParameterNameCheck.class);
        moduleConfig.addProperty("format", pattern);

        final String[] expectedViolation = {
            "9:20: " + getCheckMessage(ClassTypeParameterNameCheck.class,
                        AbstractClassNameCheck.MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "aBc", pattern),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/INTERFACE_DEF"
                        + "[./IDENT[@text='InputXpathClassTypeParameterNameInterface']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='MyClass2']]"
                        + "/TYPE_PARAMETERS/TYPE_PARAMETER[./IDENT[@text='aBc']]",
                "/COMPILATION_UNIT/INTERFACE_DEF"
                        + "[./IDENT[@text='InputXpathClassTypeParameterNameInterface']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='MyClass2']]"
                        + "/TYPE_PARAMETERS/TYPE_PARAMETER/IDENT[@text='aBc']"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testMethod() throws Exception {
        final String pattern = "(^[A-Z][0-9]?)$|([A-Z][a-zA-Z0-9]*[T]$)";
        final File fileToProcess =
                new File(getPath("InputXpathClassTypeParameterNameMethod.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ClassTypeParameterNameCheck.class);
        moduleConfig.addProperty("format", pattern);

        final String[] expectedViolation = {
            "12:24: " + getCheckMessage(ClassTypeParameterNameCheck.class,
                        AbstractClassNameCheck.MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "ABC", pattern),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathClassTypeParameterNameMethod']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='myTest2']]/SLIST/CLASS_DEF"
                        + "[./IDENT[@text='MyClass2']]/TYPE_PARAMETERS"
                        + "/TYPE_PARAMETER[./IDENT[@text='ABC']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathClassTypeParameterNameMethod']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='myTest2']]/SLIST/CLASS_DEF"
                        + "[./IDENT[@text='MyClass2']]/TYPE_PARAMETERS"
                        + "/TYPE_PARAMETER/IDENT[@text='ABC']"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
