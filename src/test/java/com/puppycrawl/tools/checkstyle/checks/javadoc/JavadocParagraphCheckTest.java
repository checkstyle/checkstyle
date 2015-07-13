////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Before;
import org.junit.Test;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck.MSG_LINE_BEFORE;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck.MSG_REDUNDANT_PARAGRAPH;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck.MSG_TAG_AFTER;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck.MSG_MISPLACED_TAG;

public class JavadocParagraphCheckTest extends BaseCheckTestSupport {

    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createCheckConfig(JavadocParagraphCheck.class);
    }

    @Test
    public void testCorrect() throws Exception {
        final String[] expected = {};

        verify(checkConfig, getPath("javadoc/InputCorrectJavaDocParagraphCheck.java"), expected);
    }

    @Test
    public void testIncorrect() throws Exception {
        final String[] expected = {
            "7: " + getCheckMessage(MSG_MISPLACED_TAG),
            "7: " + getCheckMessage(MSG_LINE_BEFORE),
            "8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "8: " + getCheckMessage(MSG_LINE_BEFORE),
            "14: " + getCheckMessage(MSG_MISPLACED_TAG),
            "14: " + getCheckMessage(MSG_LINE_BEFORE),
            "16: " + getCheckMessage(MSG_MISPLACED_TAG),
            "23: " + getCheckMessage(MSG_LINE_BEFORE),
            "25: " + getCheckMessage(MSG_MISPLACED_TAG),
            "32: " + getCheckMessage(MSG_MISPLACED_TAG),
            "32: " + getCheckMessage(MSG_LINE_BEFORE),
            "32: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "33: " + getCheckMessage(MSG_MISPLACED_TAG),
            "33: " + getCheckMessage(MSG_LINE_BEFORE),
            "34: " + getCheckMessage(MSG_MISPLACED_TAG),
            "34: " + getCheckMessage(MSG_LINE_BEFORE),
            "35: " + getCheckMessage(MSG_MISPLACED_TAG),
            "35: " + getCheckMessage(MSG_LINE_BEFORE),
            "39: " + getCheckMessage(MSG_MISPLACED_TAG),
            "39: " + getCheckMessage(MSG_LINE_BEFORE),
            "45: " + getCheckMessage(MSG_MISPLACED_TAG),
            "45: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "48: " + getCheckMessage(MSG_MISPLACED_TAG),
            "50: " + getCheckMessage(MSG_MISPLACED_TAG),
            "50: " + getCheckMessage(MSG_LINE_BEFORE),
            "51: " + getCheckMessage(MSG_MISPLACED_TAG),
            "51: " + getCheckMessage(MSG_LINE_BEFORE),
            "61: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "62: " + getCheckMessage(MSG_TAG_AFTER),
            "70: " + getCheckMessage(MSG_MISPLACED_TAG),
            "70: " + getCheckMessage(MSG_LINE_BEFORE),
            "72: " + getCheckMessage(MSG_MISPLACED_TAG),
            "75: " + getCheckMessage(MSG_MISPLACED_TAG),
            "75: " + getCheckMessage(MSG_LINE_BEFORE),
        };
        verify(checkConfig, getPath("javadoc/InputIncorrectJavaDocParagraphCheck.java"), expected);
    }

    @Test
    public void testAllowNewlineParagraph() throws Exception {
        checkConfig.addAttribute("allowNewlineParagraph", "false");
        final String[] expected = {
            "7: " + getCheckMessage(MSG_LINE_BEFORE),
            "8: " + getCheckMessage(MSG_LINE_BEFORE),
            "14: " + getCheckMessage(MSG_LINE_BEFORE),
            "23: " + getCheckMessage(MSG_LINE_BEFORE),
            "32: " + getCheckMessage(MSG_LINE_BEFORE),
            "32: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "33: " + getCheckMessage(MSG_LINE_BEFORE),
            "34: " + getCheckMessage(MSG_LINE_BEFORE),
            "35: " + getCheckMessage(MSG_LINE_BEFORE),
            "39: " + getCheckMessage(MSG_LINE_BEFORE),
            "45: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "50: " + getCheckMessage(MSG_LINE_BEFORE),
            "51: " + getCheckMessage(MSG_LINE_BEFORE),
            "61: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "62: " + getCheckMessage(MSG_TAG_AFTER),
            "70: " + getCheckMessage(MSG_LINE_BEFORE),
            "75: " + getCheckMessage(MSG_LINE_BEFORE),
        };
        verify(checkConfig, getPath("javadoc/InputIncorrectJavaDocParagraphCheck.java"), expected);
    }
}
