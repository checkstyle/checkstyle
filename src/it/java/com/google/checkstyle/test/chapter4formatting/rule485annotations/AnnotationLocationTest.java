////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

package com.google.checkstyle.test.chapter4formatting.rule485annotations;

import org.junit.Test;

import com.google.checkstyle.test.base.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck;

public class AnnotationLocationTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule485annotations";
    }

    @Test
    public void testAnnotation() throws Exception {
        final Class<AnnotationLocationCheck> clazz = AnnotationLocationCheck.class;
        getCheckMessage(clazz, "annotation.location.alone");
        final Configuration checkConfig = getModuleConfig("AnnotationLocation",
                "AnnotationLocationMostCases");

        final String msgLocationAlone = "annotation.location.alone";
        final String msgLocation = "annotation.location";
        final String[] expected = {
            "3: " + getCheckMessage(clazz, msgLocationAlone, "MyAnnotation1"),
            "20: " + getCheckMessage(clazz, msgLocation, "MyAnnotation1", "8", "4"),
            "27: " + getCheckMessage(clazz, msgLocation, "MyAnnotation2", "7", "4"),
            "31: " + getCheckMessage(clazz, msgLocation, "MyAnnotation2", "8", "4"),
            "32: " + getCheckMessage(clazz, msgLocation, "MyAnnotation3", "6", "4"),
            "33: " + getCheckMessage(clazz, msgLocation, "MyAnnotation4", "10", "4"),
            "54: " + getCheckMessage(clazz, msgLocation, "MyAnnotation2", "12", "8"),
            "58: " + getCheckMessage(clazz, msgLocation, "MyAnnotation2", "12", "8"),
            "78: " + getCheckMessage(clazz, msgLocation, "MyAnnotation2", "11", "8"),
            "81: " + getCheckMessage(clazz, msgLocation, "MyAnnotation2", "10", "8"),
            "90: " + getCheckMessage(clazz, msgLocation, "MyAnnotation2", "1", "0"),
        };

        final String filePath = getPath("InputAnnotationLocation.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testAnnotationVariables() throws Exception {
        final Class<AnnotationLocationCheck> clazz = AnnotationLocationCheck.class;
        getCheckMessage(clazz, "annotation.location.alone");
        final Configuration checkConfig = getModuleConfig("AnnotationLocation",
                "AnnotationLocationVariables");

        final String msgLocation = "annotation.location";
        final String[] expected = {
            "63: " + getCheckMessage(clazz, msgLocation, "MyAnnotation2", "7", "4"),
        };

        final String filePath = getPath("InputAnnotationLocationVariables.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
