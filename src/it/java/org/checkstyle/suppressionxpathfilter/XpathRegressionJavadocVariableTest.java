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
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocVariableCheck;

public class XpathRegressionJavadocVariableTest extends AbstractXpathTestSupport {

    private final String checkName = JavadocVariableCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testPrivateClassFields() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathJavadocVariablePrivateClassFields.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(JavadocVariableCheck.class);

        final String[] expectedViolation = {
            "5:5: " + getCheckMessage(JavadocVariableCheck.class,
                JavadocVariableCheck.MSG_JAVADOC_MISSING),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathJavadocVariablePrivateClassFields']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='age']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathJavadocVariablePrivateClassFields']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='age']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathJavadocVariablePrivateClassFields']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='age']]/MODIFIERS/LITERAL_PRIVATE"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testInnerClassFields() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathJavadocVariableInnerClassFields.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(JavadocVariableCheck.class);

        final String[] expectedViolation = {
            "6:9: " + getCheckMessage(JavadocVariableCheck.class,
                JavadocVariableCheck.MSG_JAVADOC_MISSING),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathJavadocVariableInnerClassFields']]/OBJBLOCK"
                + "/CLASS_DEF[./IDENT[@text='InnerInner2']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='fData']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathJavadocVariableInnerClassFields']]/OBJBLOCK"
                + "/CLASS_DEF[./IDENT[@text='InnerInner2']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='fData']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathJavadocVariableInnerClassFields']]/OBJBLOCK"
                + "/CLASS_DEF[./IDENT[@text='InnerInner2']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='fData']]/MODIFIERS"
                + "/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
