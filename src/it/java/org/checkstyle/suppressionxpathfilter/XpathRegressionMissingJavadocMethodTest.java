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
import com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocMethodCheck;

public class XpathRegressionMissingJavadocMethodTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return MissingJavadocMethodCheck.class.getSimpleName();
    }

    @Test
    public void testMissingJavadocMethodCtor() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathMissingJavadocMethodCtor.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(MissingJavadocMethodCheck.class);
        moduleConfig.addProperty("tokens", "CTOR_DEF");

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(MissingJavadocMethodCheck.class,
                MissingJavadocMethodCheck.MSG_JAVADOC_MISSING),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathMissingJavadocMethodCtor']]"
                + "/OBJBLOCK/CTOR_DEF[."
                + "/IDENT[@text='InputXpathMissingJavadocMethodCtor']]",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMissingJavadocMethodCtor']]"
                + "/OBJBLOCK/CTOR_DEF[."
                + "/IDENT[@text='InputXpathMissingJavadocMethodCtor']]"
                + "/MODIFIERS",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMissingJavadocMethodCtor']]"
                + "/OBJBLOCK/CTOR_DEF[."
                + "/IDENT[@text='InputXpathMissingJavadocMethodCtor']]"
                + "/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testMissingJavadocMethod() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathMissingJavadocMethod.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(MissingJavadocMethodCheck.class);
        moduleConfig.addProperty("tokens", "METHOD_DEF");

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(MissingJavadocMethodCheck.class,
                MissingJavadocMethodCheck.MSG_JAVADOC_MISSING),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMissingJavadocMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMissingJavadocMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/MODIFIERS",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMissingJavadocMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
