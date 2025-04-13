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
import com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocTypeCheck;

public class XpathRegressionMissingJavadocTypeTest extends AbstractXpathTestSupport {

    private final String checkName = MissingJavadocTypeCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testClass() throws Exception {
        final File fileToProcess = new File(getPath(
                "InputXpathMissingJavadocTypeClass.java"
        ));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MissingJavadocTypeCheck.class);

        final String[] expectedViolation = {
            "3:1: " + getCheckMessage(MissingJavadocTypeCheck.class,
                    MissingJavadocTypeCheck.MSG_JAVADOC_MISSING,
                    "ClassMissingJavadocType"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMissingJavadocTypeClass']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMissingJavadocTypeClass']]"
                        + "/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMissingJavadocTypeClass']]"
                        + "/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testScope() throws Exception {
        final File fileToProcess = new File(getPath(
                "InputXpathMissingJavadocTypeScope.java"
        ));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MissingJavadocTypeCheck.class);
        moduleConfig.addProperty("scope", "private");

        final String[] expectedViolation = {
            "7:5: " + getCheckMessage(MissingJavadocTypeCheck.class,
                    MissingJavadocTypeCheck.MSG_JAVADOC_MISSING,
                    "ScopeMissingJavadocType"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMissingJavadocTypeScope']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMissingJavadocTypeScope']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMissingJavadocTypeScope']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]/MODIFIERS/LITERAL_PRIVATE"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testExcluded() throws Exception {
        final File fileToProcess = new File(getPath(
                "InputXpathMissingJavadocTypeExcluded.java"
        ));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MissingJavadocTypeCheck.class);
        moduleConfig.addProperty("scope", "private");
        moduleConfig.addProperty("excludeScope", "package");

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(MissingJavadocTypeCheck.class,
                    MissingJavadocTypeCheck.MSG_JAVADOC_MISSING,
                    "ExcludedMissingJavadocType"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMissingJavadocTypeExcluded']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMissingJavadocTypeExcluded']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMissingJavadocTypeExcluded']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]/MODIFIERS/LITERAL_PRIVATE"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testAnnotation() throws Exception {
        final File fileToProcess = new File(getPath(
                "InputXpathMissingJavadocTypeAnnotation.java"
        ));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MissingJavadocTypeCheck.class);
        moduleConfig.addProperty("skipAnnotations", "TestAnnotation");

        final String[] expectedViolation = {
            "5:5: " + getCheckMessage(MissingJavadocTypeCheck.class,
                    MissingJavadocTypeCheck.MSG_JAVADOC_MISSING,
                    "AnnotationMissingJavadocType"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                        + "'InputXpathMissingJavadocTypeAnnotation']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='innerClass']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                        + "'InputXpathMissingJavadocTypeAnnotation']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='innerClass']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                        + "'InputXpathMissingJavadocTypeAnnotation']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='innerClass']]"
                        + "/MODIFIERS/ANNOTATION[./IDENT[@text='TestAnnotation2']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                        + "'InputXpathMissingJavadocTypeAnnotation']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='innerClass']]"
                        + "/MODIFIERS/ANNOTATION[./IDENT[@text='TestAnnotation2']]/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testToken() throws Exception {
        final File fileToProcess = new File(getPath(
                "InputXpathMissingJavadocTypeToken.java"
        ));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MissingJavadocTypeCheck.class);
        moduleConfig.addProperty("tokens", "INTERFACE_DEF");

        final String[] expectedViolation = {
            "3:1: " + getCheckMessage(MissingJavadocTypeCheck.class,
                    MissingJavadocTypeCheck.MSG_JAVADOC_MISSING,
                    "TokenMissingJavadocType"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT[@text="
                        + "'InputXpathMissingJavadocTypeToken']]",
                "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT[@text="
                        + "'InputXpathMissingJavadocTypeToken']]"
                        + "/MODIFIERS",
                "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT[@text="
                        + "'InputXpathMissingJavadocTypeToken']]"
                        + "/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
