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
import com.puppycrawl.tools.checkstyle.checks.sizes.MethodCountCheck;

public class XpathRegressionMethodCountTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return MethodCountCheck.class.getSimpleName();
    }

    @Test
    public void testDefault() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathMethodCountDefault.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(MethodCountCheck.class);
        moduleConfig.addProperty("maxTotal", "1");

        final String[] expectedViolation = {
            "3:1: " + getCheckMessage(MethodCountCheck.class,
                MethodCountCheck.MSG_MANY_METHODS, 2, 1),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMethodCountDefault']]",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMethodCountDefault']]"
                + "/MODIFIERS",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMethodCountDefault']]"
                + "/LITERAL_CLASS"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testPrivate() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathMethodCountPrivate.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(MethodCountCheck.class);
        moduleConfig.addProperty("maxPrivate", "1");

        final String[] expectedViolation = {
            "3:1: " + getCheckMessage(MethodCountCheck.class,
                MethodCountCheck.MSG_PRIVATE_METHODS, 2, 1),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMethodCountPrivate']]",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMethodCountPrivate']]"
                + "/MODIFIERS",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMethodCountPrivate']]"
                + "/LITERAL_CLASS"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testPackage() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathMethodCountDefault.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(MethodCountCheck.class);
        moduleConfig.addProperty("maxPackage", "1");

        final String[] expectedViolation = {
            "3:1: " + getCheckMessage(MethodCountCheck.class,
                MethodCountCheck.MSG_PACKAGE_METHODS, 2, 1),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMethodCountDefault']]",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMethodCountDefault']]"
                + "/MODIFIERS",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMethodCountDefault']]"
                + "/LITERAL_CLASS"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testProtected() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathMethodCountProtected.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(MethodCountCheck.class);
        moduleConfig.addProperty("maxProtected", "1");

        final String[] expectedViolation = {
            "3:1: " + getCheckMessage(MethodCountCheck.class,
                MethodCountCheck.MSG_PROTECTED_METHODS, 2, 1),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMethodCountProtected']]",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMethodCountProtected']]"
                + "/MODIFIERS",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMethodCountProtected']]"
                + "/LITERAL_CLASS"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testPublic() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathMethodCountPublic.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(MethodCountCheck.class);
        moduleConfig.addProperty("maxPublic", "1");

        final String[] expectedViolation = {
            "3:1: " + getCheckMessage(MethodCountCheck.class,
                MethodCountCheck.MSG_PUBLIC_METHODS, 2, 1),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMethodCountPublic']]",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMethodCountPublic']]"
                + "/MODIFIERS",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMethodCountPublic']]"
                + "/LITERAL_CLASS"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
