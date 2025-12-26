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

package com.google.checkstyle.test.chapter7javadoc.rule711generalform;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;

public class GeneralFormTest extends AbstractGoogleModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/google/checkstyle/test/chapter7javadoc/rule711generalform";
    }

    @Test
    void singleLineJavadocAndInvalidJavadocPosition() throws Exception {
        verifyWithWholeConfig(getPath("InputSingleLineJavadocAndInvalidJavadocPosition.java"));
    }

    @Test
    void singleLineJavadocAndInvalidJavadocPositionFormatted() throws Exception {
        verifyWithWholeConfig(
                getPath("InputFormattedSingleLineJavadocAndInvalidJavadocPosition.java"));
    }

    @Test
    void javadocPositionOnCompactConstructorInRecord() throws Exception {
        verifyWithWholeConfig(getPath("InputJavadocPositionOnConstructorInRecord.java"));
    }

    @Test
    void formattedJavadocPositionOnCompactConstructorInRecord() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedJavadocPositionOnConstructorInRecord.java"));
    }

    @Test
    void javadocPositionOnCompactConstructorWithAnnotation() throws Exception {
        verifyWithWholeConfig(
            getPath("InputJavadocPositionOnCompactConstructorsWithAnnotation.java"));
    }

    @Test
    void formattedJavadocPositionOnCompactConstructorWithAnnotation() throws Exception {
        verifyWithWholeConfig(
            getPath("InputFormattedJavadocPositionOnCompactConstructorsWithAnnotation.java"));
    }

    @Test
    void javadocPositionOnCanonicalConstructorWithAnnotation() throws Exception {
        verifyWithWholeConfig(
            getPath("InputJavadocPositionOnCanonicalConstructorsWithAnnotation.java"));
    }

    @Test
    void formattedJavadocPositionOnCanonicalConstructorWithAnnotation()
            throws Exception {
        verifyWithWholeConfig(
            getPath("InputFormattedJavadocPositionOnCanonicalConstructorsWithAnnotation.java"));
    }

    @Test
    void recordClassJavadocPosition() throws Exception {
        verifyWithWholeConfig(getPath("InputRecordClassJavadocPosition.java"));
    }

    @Test
    void formattedRecordClassJavadocPosition() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedRecordClassJavadocPosition.java"));
    }

    @Test
    void correctJavadocLeadingAsteriskAlignment() throws Exception {
        verifyWithWholeConfig(getPath("InputCorrectJavadocLeadingAsteriskAlignment.java"));
    }

    @Test
    void formattedCorrectJavadocLeadingAsteriskAlignment() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedCorrectJavadocLeadingAsteriskAlignment.java"));
    }

    @Test
    void incorrectJavadocLeadingAsteriskAlignment() throws Exception {
        verifyWithWholeConfig(getPath("InputIncorrectJavadocLeadingAsteriskAlignment.java"));
    }

    @Test
    void formattedIncorrectJavadocLeadingAsteriskAlignment() throws Exception {
        verifyWithWholeConfig(
                    getPath("InputFormattedIncorrectJavadocLeadingAsteriskAlignment.java"));
    }
}
