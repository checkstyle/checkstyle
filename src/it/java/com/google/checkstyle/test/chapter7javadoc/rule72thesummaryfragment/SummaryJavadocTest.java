///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.google.checkstyle.test.chapter7javadoc.rule72thesummaryfragment;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class SummaryJavadocTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter7javadoc/rule72thesummaryfragment";
    }

    @Test
    public void testCorrect() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getModuleConfig("SummaryJavadoc");
        final String filePath = getPath("InputCorrectSummaryJavaDocCheck.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testIncorrect() throws Exception {
        final String msgFirstSentence = getCheckMessage(SummaryJavadocCheck.class,
            "summary.first.sentence");
        final String msgForbiddenFragment = getCheckMessage(SummaryJavadocCheck.class,
            "summary.javaDoc");
        final String msgMissingDoc = getCheckMessage(SummaryJavadocCheck.class,
            "summary.javaDoc.missing");

        final String[] expected = {
            "14: " + msgMissingDoc,
            "32: " + msgMissingDoc,
            "37: " + msgFirstSentence,
            "47: " + msgForbiddenFragment,
            "53: " + msgMissingDoc,
            "58: " + msgMissingDoc,
            "69: " + msgMissingDoc,
            "83: " + msgForbiddenFragment,
            "103: " + msgMissingDoc,
        };

        final Configuration checkConfig = getModuleConfig("SummaryJavadoc");
        final String filePath = getPath("InputIncorrectSummaryJavaDocCheck.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
