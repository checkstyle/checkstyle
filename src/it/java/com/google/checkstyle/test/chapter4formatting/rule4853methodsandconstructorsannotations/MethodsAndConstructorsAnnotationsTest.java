///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

package com.google.checkstyle.test.chapter4formatting.rule4853methodsandconstructorsannotations;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck;
import com.puppycrawl.tools.checkstyle.checks.javadoc.InvalidJavadocPositionCheck;

public class MethodsAndConstructorsAnnotationsTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule4853methodsandconstructorsannotations";
    }

    @Test
    public void testAnnotation() throws Exception {
        final Class<AnnotationLocationCheck> clazz1 = AnnotationLocationCheck.class;
        final Class<InvalidJavadocPositionCheck> clazz2 = InvalidJavadocPositionCheck.class;

        final Configuration annotationConfig = getModuleConfig("AnnotationLocation",
                "AnnotationLocationMostCases");
        final Configuration invalidJavadocPositionConfig =
                getModuleConfig("InvalidJavadocPosition");
        final List<Configuration> childrenConfigs = new ArrayList<>();
        childrenConfigs.add(annotationConfig);
        childrenConfigs.add(invalidJavadocPositionConfig);
        final Configuration treeWalkerConfig = createTreeWalkerConfig(childrenConfigs);

        final String annotationAloneMsg = "annotation.location.alone";
        final String invalidJavadocPositionMsg = "invalid.position";

        final String[] expected = {
            "20:22: " + getCheckMessage(clazz1, annotationAloneMsg, "SomeAnnotation2"),
            "25:5: " + getCheckMessage(clazz2, invalidJavadocPositionMsg),
            "31:22: " + getCheckMessage(clazz1, annotationAloneMsg, "SomeAnnotation2"),
            "32:5: " + getCheckMessage(clazz2, invalidJavadocPositionMsg),
            "55:22: " + getCheckMessage(clazz1, annotationAloneMsg, "SomeAnnotation2"),
            "60:5: " + getCheckMessage(clazz2, invalidJavadocPositionMsg),
            "65:22: " + getCheckMessage(clazz1, annotationAloneMsg, "SomeAnnotation2"),
            "66:5: " + getCheckMessage(clazz2, invalidJavadocPositionMsg),
        };

        final String filePath = getPath("InputMethodsAndConstructorsAnnotations.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(treeWalkerConfig, filePath, expected, warnList);
    }
}
