///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package org.checkstyle.suppressionxpathfilter.annotation;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.annotation.OpenjdkAnnotationLocationCheck;

public class XpathRegressionOpenjdkAnnotationLocationTest extends AbstractXpathTestSupport {

    private final String checkName = OpenjdkAnnotationLocationCheck.class.getSimpleName();

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/annotation/openjdkannotationlocation";
    }

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testClass() throws Exception {
        final File fileToProcess = new File(getPath(
                "InputXpathOpenjdkAnnotationLocation1.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(OpenjdkAnnotationLocationCheck.class);

        final String[] expectedViolation = {
            "6:1: " + getCheckMessage(OpenjdkAnnotationLocationCheck.class,
                    OpenjdkAnnotationLocationCheck.MSG_KEY_ANNOTATION_ON_TARGET_LINE,
                    "ClassAnnotation"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathOpenjdkAnnotationLocation1']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathOpenjdkAnnotationLocation1']]"
                        + "/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathOpenjdkAnnotationLocation1']]/"
                        + "MODIFIERS/ANNOTATION[./IDENT[@text='ClassAnnotation']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathOpenjdkAnnotationLocation1']]/"
                        + "MODIFIERS/ANNOTATION[./IDENT[@text='ClassAnnotation']]/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testEnum() throws Exception {
        final File fileToProcess = new File(getPath(
                "InputXpathOpenjdkAnnotationLocation2.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(OpenjdkAnnotationLocationCheck.class);

        final String[] expectedViolation = {
            "6:1: " + getCheckMessage(OpenjdkAnnotationLocationCheck.class,
                    OpenjdkAnnotationLocationCheck.MSG_KEY_ANNOTATION_ON_TARGET_LINE,
                    "EnumAnnotation"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/ENUM_DEF[./IDENT[@text='"
                        + "InputXpathOpenjdkAnnotationLocation2']]",
                "/COMPILATION_UNIT/ENUM_DEF"
                        + "[./IDENT[@text='InputXpathOpenjdkAnnotationLocation2']]"
                        + "/MODIFIERS",
                "/COMPILATION_UNIT/ENUM_DEF"
                        + "[./IDENT[@text='InputXpathOpenjdkAnnotationLocation2']]"
                        + "/MODIFIERS/ANNOTATION[./IDENT[@text='EnumAnnotation']]",
                "/COMPILATION_UNIT/ENUM_DEF"
                        + "[./IDENT[@text='InputXpathOpenjdkAnnotationLocation2']]"
                        + "/MODIFIERS/ANNOTATION[./IDENT[@text='EnumAnnotation']]/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);

    }

    @Test
    public void testMethod() throws Exception {
        final File fileToProcess = new File(getPath(
                "InputXpathOpenjdkAnnotationLocation3.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(OpenjdkAnnotationLocationCheck.class);
        moduleConfig.addProperty("tokens", "METHOD_DEF");

        final String[] expectedViolation = {
            "7:5: " + getCheckMessage(OpenjdkAnnotationLocationCheck.class,
                    OpenjdkAnnotationLocationCheck.MSG_KEY_ANNOTATION_ON_TARGET_LINE,
                    "MethodAnnotation"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathOpenjdkAnnotationLocation3']]/"
                        + "OBJBLOCK/METHOD_DEF[./IDENT[@text='foo1']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathOpenjdkAnnotationLocation3']]/"
                        + "OBJBLOCK/METHOD_DEF[./IDENT[@text='foo1']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathOpenjdkAnnotationLocation3']]/"
                        + "OBJBLOCK/METHOD_DEF[./IDENT[@text='foo1']]/MODIFIERS/"
                        + "ANNOTATION[./IDENT[@text='MethodAnnotation']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathOpenjdkAnnotationLocation3']]/"
                        + "OBJBLOCK/METHOD_DEF[./IDENT[@text='foo1']]/MODIFIERS/"
                        + "ANNOTATION[./IDENT[@text='MethodAnnotation']]/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);

    }
}
