////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
                "SuppressionXpathRegressionMissingJavadocTypeClass.java"
        ));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MissingJavadocTypeCheck.class);

        final String[] expectedViolation = {
            "3:1: " + getCheckMessage(MissingJavadocTypeCheck.class,
                    MissingJavadocTypeCheck.MSG_JAVADOC_MISSING,
                    "ClassMissingJavadocType"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionMissingJavadocTypeClass']]",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionMissingJavadocTypeClass']]"
                        + "/MODIFIERS",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionMissingJavadocTypeClass']]"
                        + "/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testScope() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionMissingJavadocTypeScope.java"
        ));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MissingJavadocTypeCheck.class);
        moduleConfig.addAttribute("scope", "private");

        final String[] expectedViolation = {
            "7:5: " + getCheckMessage(MissingJavadocTypeCheck.class,
                    MissingJavadocTypeCheck.MSG_JAVADOC_MISSING,
                    "ScopeMissingJavadocType"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionMissingJavadocTypeScope']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionMissingJavadocTypeScope']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]/MODIFIERS",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionMissingJavadocTypeScope']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]/MODIFIERS/LITERAL_PRIVATE"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testExcluded() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionMissingJavadocTypeExcluded.java"
        ));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MissingJavadocTypeCheck.class);
        moduleConfig.addAttribute("scope", "private");
        moduleConfig.addAttribute("excludeScope", "package");

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(MissingJavadocTypeCheck.class,
                    MissingJavadocTypeCheck.MSG_JAVADOC_MISSING,
                    "ExcludedMissingJavadocType"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionMissingJavadocTypeExcluded']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionMissingJavadocTypeExcluded']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]/MODIFIERS",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionMissingJavadocTypeExcluded']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]/MODIFIERS/LITERAL_PRIVATE"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testAnnotation() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionMissingJavadocTypeAnnotation.java"
        ));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MissingJavadocTypeCheck.class);
        moduleConfig.addAttribute("skipAnnotations", "TestAnnotation");

        final String[] expectedViolation = {
            "5:5: " + getCheckMessage(MissingJavadocTypeCheck.class,
                    MissingJavadocTypeCheck.MSG_JAVADOC_MISSING,
                    "AnnotationMissingJavadocType"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text="
                        + "'SuppressionXpathRegressionMissingJavadocTypeAnnotation']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='innerClass']]",
                "/CLASS_DEF[./IDENT[@text="
                        + "'SuppressionXpathRegressionMissingJavadocTypeAnnotation']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='innerClass']]/MODIFIERS",
                "/CLASS_DEF[./IDENT[@text="
                        + "'SuppressionXpathRegressionMissingJavadocTypeAnnotation']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='innerClass']]"
                        + "/MODIFIERS/ANNOTATION[./IDENT[@text='TestAnnotation2']]",
                "/CLASS_DEF[./IDENT[@text="
                        + "'SuppressionXpathRegressionMissingJavadocTypeAnnotation']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='innerClass']]"
                        + "/MODIFIERS/ANNOTATION[./IDENT[@text='TestAnnotation2']]/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testToken() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionMissingJavadocTypeToken.java"
        ));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MissingJavadocTypeCheck.class);
        moduleConfig.addAttribute("tokens", "INTERFACE_DEF");

        final String[] expectedViolation = {
            "3:1: " + getCheckMessage(MissingJavadocTypeCheck.class,
                    MissingJavadocTypeCheck.MSG_JAVADOC_MISSING,
                    "TokenMissingJavadocType"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/INTERFACE_DEF[./IDENT[@text="
                        + "'SuppressionXpathRegressionMissingJavadocTypeToken']]",
                "/INTERFACE_DEF[./IDENT[@text="
                        + "'SuppressionXpathRegressionMissingJavadocTypeToken']]"
                        + "/MODIFIERS",
                "/INTERFACE_DEF[./IDENT[@text="
                        + "'SuppressionXpathRegressionMissingJavadocTypeToken']]"
                        + "/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
