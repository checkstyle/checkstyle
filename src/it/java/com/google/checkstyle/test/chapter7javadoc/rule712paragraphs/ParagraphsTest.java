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

package com.google.checkstyle.test.chapter7javadoc.rule712paragraphs;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;

public class ParagraphsTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter7javadoc/rule712paragraphs";
    }

    @Test
    public void testJavadocParagraphCorrect() throws Exception {
        verifyWithWholeConfig(getPath("InputCorrectJavadocParagraph.java"));
    }

    @Test
    public void testJavadocParagraphCorrectFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedCorrectJavadocParagraph.java"));
    }

    @Test
    public void testJavadocParagraphIncorrect() throws Exception {
        verifyWithWholeConfig(getPath("InputIncorrectJavadocParagraph.java"));
    }

    @Test
    public void testJavadocParagraphIncorrectFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedIncorrectJavadocParagraph.java"));
    }

    @Test
    public void testRequireEmptyLineBeforeBlockTagGroupCorrect() throws Exception {
        verifyWithWholeConfig(getPath("InputCorrectRequireEmptyLineBeforeBlockTagGroup.java"));
    }

    @Test
    public void testRequireEmptyLineBeforeBlockTagGroupCorrectFormatted() throws Exception {
        verifyWithWholeConfig(
            getPath("InputFormattedCorrectRequireEmptyLineBeforeBlockTagGroup.java"));
    }

    @Test
    public void testRequireEmptyLineBeforeBlockTagGroupIncorrect() throws Exception {
        verifyWithWholeConfig(getPath("InputIncorrectRequireEmptyLineBeforeBlockTagGroup.java"));
    }

    @Test
    public void testRequireEmptyLineBeforeBlockTagGroupIncorrectFormatted() throws Exception {
        verifyWithWholeConfig(
            getPath("InputFormattedIncorrectRequireEmptyLineBeforeBlockTagGroup.java"));
    }

}
