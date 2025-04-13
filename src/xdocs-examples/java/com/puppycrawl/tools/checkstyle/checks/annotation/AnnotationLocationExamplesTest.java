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

import static com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck.MSG_KEY_ANNOTATION_LOCATION_ALONE;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class AnnotationLocationExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/annotation/annotationlocation";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "31:3: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "SuppressWarnings"),
            "33:3: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "SuppressWarnings"),
            "35:12: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Mock"),

        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {

        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "24:3: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Nonnull"),
            "26:3: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Override"),
            "36:3: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Nonnull"),
            "36:12: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Mock"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "33:3: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "SuppressWarnings"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }
}
