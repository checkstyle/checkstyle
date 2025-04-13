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

package com.puppycrawl.tools.checkstyle.checks.annotation;

import static com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationUseStyleCheck.MSG_KEY_ANNOTATION_INCORRECT_STYLE;
import static com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationUseStyleCheck.MSG_KEY_ANNOTATION_PARENS_MISSING;
import static com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationUseStyleCheck.MSG_KEY_ANNOTATION_PARENS_PRESENT;
import static com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationUseStyleCheck.MSG_KEY_ANNOTATION_TRAILING_COMMA_MISSING;
import static com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationUseStyleCheck.MSG_KEY_ANNOTATION_TRAILING_COMMA_PRESENT;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class AnnotationUseStyleCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/annotation/annotationusestyle";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "25:1: " + getCheckMessage(MSG_KEY_ANNOTATION_INCORRECT_STYLE, "COMPACT_NO_ARRAY"),
            "26:1: " + getCheckMessage(MSG_KEY_ANNOTATION_PARENS_PRESENT),
            "28:40: " + getCheckMessage(MSG_KEY_ANNOTATION_TRAILING_COMMA_PRESENT),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(MSG_KEY_ANNOTATION_INCORRECT_STYLE, "EXPANDED"),
            "19:1: " + getCheckMessage(MSG_KEY_ANNOTATION_INCORRECT_STYLE, "EXPANDED"),
            "26:1: " + getCheckMessage(MSG_KEY_ANNOTATION_PARENS_PRESENT),
            "28:40: " + getCheckMessage(MSG_KEY_ANNOTATION_TRAILING_COMMA_PRESENT),

        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_KEY_ANNOTATION_PARENS_MISSING),
            "25:1: " + getCheckMessage(MSG_KEY_ANNOTATION_INCORRECT_STYLE, "COMPACT"),
            "28:1: " + getCheckMessage(MSG_KEY_ANNOTATION_INCORRECT_STYLE, "COMPACT"),

        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "19:34: " + getCheckMessage(MSG_KEY_ANNOTATION_TRAILING_COMMA_MISSING),
            "26:37: " + getCheckMessage(MSG_KEY_ANNOTATION_TRAILING_COMMA_MISSING),

        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }
}
