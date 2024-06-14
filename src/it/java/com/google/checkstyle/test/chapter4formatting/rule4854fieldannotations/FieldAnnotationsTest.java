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

package com.google.checkstyle.test.chapter4formatting.rule4854fieldannotations;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck;
import com.puppycrawl.tools.checkstyle.checks.javadoc.InvalidJavadocPositionCheck;

public class FieldAnnotationsTest extends AbstractGoogleModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule4854fieldannotations";
    }

    @Test
    public void testAnnotations() throws Exception {
        final Class<InvalidJavadocPositionCheck> clazz1 = InvalidJavadocPositionCheck.class;
        final Class<AnnotationLocationCheck> clazz2 = AnnotationLocationCheck.class;

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
            "33:5: " + getCheckMessage(clazz1, invalidJavadocPositionMsg),
            "40:5: " + getCheckMessage(clazz1, invalidJavadocPositionMsg),
            "48:22: " + getCheckMessage(clazz2, annotationAloneMsg, "SomeAnnotation2"),
            "51:22: " + getCheckMessage(clazz2, annotationAloneMsg, "SomeAnnotation2"),
            "53:5: " + getCheckMessage(clazz2, annotationAloneMsg, "SomeAnnotation3"),
        };

        final String filePath = getPath("InputFieldAnnotations.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(treeWalkerConfig, filePath, expected, warnList);
    }
}
