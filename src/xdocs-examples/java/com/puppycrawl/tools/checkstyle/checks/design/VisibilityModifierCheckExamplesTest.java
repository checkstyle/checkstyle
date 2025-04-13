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

package com.puppycrawl.tools.checkstyle.checks.design;

import static com.puppycrawl.tools.checkstyle.checks.design.VisibilityModifierCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class VisibilityModifierCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/design/visibilitymodifier";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "23:7: " + getCheckMessage(MSG_KEY, "field1"),
            "25:20: " + getCheckMessage(MSG_KEY, "field2"),
            "27:14: " + getCheckMessage(MSG_KEY, "field3"),
            "34:20: " + getCheckMessage(MSG_KEY, "field5"),
            "37:33: " + getCheckMessage(MSG_KEY, "notes"),
            "40:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "43:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "46:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "49:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "53:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "27:20: " + getCheckMessage(MSG_KEY, "field2"),
            "30:14: " + getCheckMessage(MSG_KEY, "field3"),
            "37:20: " + getCheckMessage(MSG_KEY, "field5"),
            "40:33: " + getCheckMessage(MSG_KEY, "notes"),
            "43:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "46:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "49:45: " + getCheckMessage(MSG_KEY, "objects1"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "25:7: " + getCheckMessage(MSG_KEY, "field1"),
            "30:14: " + getCheckMessage(MSG_KEY, "field3"),
            "37:20: " + getCheckMessage(MSG_KEY, "field5"),
            "40:33: " + getCheckMessage(MSG_KEY, "notes"),
            "43:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "46:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "49:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "52:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "56:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "25:7: " + getCheckMessage(MSG_KEY, "field1"),
            "27:20: " + getCheckMessage(MSG_KEY, "field2"),
            "30:14: " + getCheckMessage(MSG_KEY, "field3"),
            "33:15: " + getCheckMessage(MSG_KEY, "serialVersionUID"),
            "38:20: " + getCheckMessage(MSG_KEY, "field5"),
            "41:33: " + getCheckMessage(MSG_KEY, "notes"),
            "44:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "47:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "50:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "53:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "57:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "25:7: " + getCheckMessage(MSG_KEY, "field1"),
            "27:20: " + getCheckMessage(MSG_KEY, "field2"),
            "30:14: " + getCheckMessage(MSG_KEY, "field3"),
            "36:20: " + getCheckMessage(MSG_KEY, "field5"),
            "38:33: " + getCheckMessage(MSG_KEY, "notes"),
            "41:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "44:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "47:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "50:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "54:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "28:7: " + getCheckMessage(MSG_KEY, "field1"),
            "30:20: " + getCheckMessage(MSG_KEY, "field2"),
            "33:14: " + getCheckMessage(MSG_KEY, "field3"),
            "39:20: " + getCheckMessage(MSG_KEY, "field5"),
            "41:33: " + getCheckMessage(MSG_KEY, "notes"),
            "44:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "47:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "50:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "53:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "57:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "28:7: " + getCheckMessage(MSG_KEY, "field1"),
            "30:20: " + getCheckMessage(MSG_KEY, "field2"),
            "33:14: " + getCheckMessage(MSG_KEY, "field3"),
            "39:20: " + getCheckMessage(MSG_KEY, "field5"),
            "41:33: " + getCheckMessage(MSG_KEY, "notes"),
            "44:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "47:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "50:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "53:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "57:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expected = {
            "26:7: " + getCheckMessage(MSG_KEY, "field1"),
            "28:20: " + getCheckMessage(MSG_KEY, "field2"),
            "31:14: " + getCheckMessage(MSG_KEY, "field3"),
            "37:20: " + getCheckMessage(MSG_KEY, "field5"),
            "39:33: " + getCheckMessage(MSG_KEY, "notes"),
            "42:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "45:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "48:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "58:17: " + getCheckMessage(MSG_KEY, "testString"),
        };

        verifyWithInlineConfigParser(getPath("Example8.java"), expected);
    }

    @Test
    public void testExample9() throws Exception {
        final String[] expected = {
            "23:7: " + getCheckMessage(MSG_KEY, "field1"),
            "25:20: " + getCheckMessage(MSG_KEY, "field2"),
            "28:14: " + getCheckMessage(MSG_KEY, "field3"),
            "34:20: " + getCheckMessage(MSG_KEY, "field5"),
            "36:33: " + getCheckMessage(MSG_KEY, "notes"),
            "39:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "42:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "45:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "48:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "52:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example9.java"), expected);
    }

    @Test
    public void testExample10() throws Exception {
        final String[] expected = {
            "25:7: " + getCheckMessage(MSG_KEY, "field1"),
            "27:20: " + getCheckMessage(MSG_KEY, "field2"),
            "30:14: " + getCheckMessage(MSG_KEY, "field3"),
            "36:20: " + getCheckMessage(MSG_KEY, "field5"),
            "38:33: " + getCheckMessage(MSG_KEY, "notes"),
            "41:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "44:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "47:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "50:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "57:17: " + getCheckMessage(MSG_KEY, "testString"),
        };

        verifyWithInlineConfigParser(getPath("Example10.java"), expected);
    }

    @Test
    public void testExample11() throws Exception {
        final String[] expected = {
            "22:20: " + getCheckMessage(MSG_KEY, "someIntValue"),
            "24:37: " + getCheckMessage(MSG_KEY, "includes"),
            "26:33: " + getCheckMessage(MSG_KEY, "notes"),
            "28:27: " + getCheckMessage(MSG_KEY, "value"),
            "30:21: " + getCheckMessage(MSG_KEY, "list"),
        };

        verifyWithInlineConfigParser(getPath("Example11.java"), expected);
    }

    @Test
    public void testExample12() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(getPath("Example12.java"), expected);
    }
}
