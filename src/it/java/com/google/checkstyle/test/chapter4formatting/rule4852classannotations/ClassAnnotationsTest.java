///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.google.checkstyle.test.chapter4formatting.rule4852classannotations;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;

public class ClassAnnotationsTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule4852classannotations";
    }

    @Test
    public void testAnnotation() throws Exception {
        final String filePath = getPath("InputClassAnnotations.java");
        verifyWithWholeConfig(filePath);
    }

    @Test
    public void testAnnotationFormatted() throws Exception {
        final String filePath = getPath("InputFormattedClassAnnotations.java");
        verifyWithWholeConfig(filePath);
    }

    @Test
    public void testAnnotation2() throws Exception {
        final String filePath = getPath("InputClassAnnotation2.java");
        verifyWithWholeConfig(filePath);
    }

    @Test
    public void testAnnotation2Formatted() throws Exception {
        final String filePath = getPath("InputFormattedClassAnnotation2.java");
        verifyWithWholeConfig(filePath);
    }

    @Test
    public void testPackageAnnotation() throws Exception {
        final String filePath = getPath("package-info.java");
        verifyWithWholeConfig(filePath);
    }

    @Test
    public void testPackageAnnotation2() throws Exception {
        final String filePath = getPath("sample1/package-info.java");
        verifyWithWholeConfig(filePath);
    }

    @Test
    public void testPackageAnnotation3() throws Exception {
        final String filePath = getPath("sample2/package-info.java");
        verifyWithWholeConfig(filePath);
    }

    @Test
    public void testPackageAnnotation4() throws Exception {
        final String filePath = getPath("sample3/package-info.java");
        verifyWithWholeConfig(filePath);
    }

    @Test
    public void testPackageAnnotation5() throws Exception {
        final String filePath = getPath("sample4/package-info.java");
        verifyWithWholeConfig(filePath);
    }

    @Test
    public void testPackageAnnotation6() throws Exception {
        final String filePath = getPath("sample5/package-info.java");
        verifyWithWholeConfig(filePath);
    }
}
