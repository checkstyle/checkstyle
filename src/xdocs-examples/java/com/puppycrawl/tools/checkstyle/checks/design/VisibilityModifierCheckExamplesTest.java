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

package com.puppycrawl.tools.checkstyle.checks.design;

import static com.puppycrawl.tools.checkstyle.checks.design.VisibilityModifierCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class VisibilityModifierCheckExamplesTest extends AbstractModuleTestSupport {
    @Override
    protected String getResourceLocation() {
        return "xdocs-examples";
    }

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/design/visibilitymodifier";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "18:7: " + getCheckMessage(MSG_KEY, "field1"),
            "19:20: " + getCheckMessage(MSG_KEY, "field2"),
            "20:14: " + getCheckMessage(MSG_KEY, "field3"),
            "24:20: " + getCheckMessage(MSG_KEY, "field5"),
            "26:33: " + getCheckMessage(MSG_KEY, "notes"),
            "28:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "30:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "32:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "34:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "37:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "20:20: " + getCheckMessage(MSG_KEY, "field2"),
            "22:14: " + getCheckMessage(MSG_KEY, "field3"),
            "26:20: " + getCheckMessage(MSG_KEY, "field5"),
            "28:33: " + getCheckMessage(MSG_KEY, "notes"),
            "30:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "32:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "34:45: " + getCheckMessage(MSG_KEY, "objects1"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "19:7: " + getCheckMessage(MSG_KEY, "field1"),
            "22:14: " + getCheckMessage(MSG_KEY, "field3"),
            "26:20: " + getCheckMessage(MSG_KEY, "field5"),
            "28:33: " + getCheckMessage(MSG_KEY, "notes"),
            "30:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "32:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "34:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "36:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "39:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "19:7: " + getCheckMessage(MSG_KEY, "field1"),
            "20:20: " + getCheckMessage(MSG_KEY, "field2"),
            "22:14: " + getCheckMessage(MSG_KEY, "field3"),
            "24:15: " + getCheckMessage(MSG_KEY, "serialVersionUID"),
            "27:20: " + getCheckMessage(MSG_KEY, "field5"),
            "29:33: " + getCheckMessage(MSG_KEY, "notes"),
            "31:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "33:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "35:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "37:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "40:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "19:7: " + getCheckMessage(MSG_KEY, "field1"),
            "20:20: " + getCheckMessage(MSG_KEY, "field2"),
            "22:14: " + getCheckMessage(MSG_KEY, "field3"),
            "25:20: " + getCheckMessage(MSG_KEY, "field5"),
            "26:33: " + getCheckMessage(MSG_KEY, "notes"),
            "28:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "30:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "32:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "34:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "37:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "21:7: " + getCheckMessage(MSG_KEY, "field1"),
            "22:20: " + getCheckMessage(MSG_KEY, "field2"),
            "24:14: " + getCheckMessage(MSG_KEY, "field3"),
            "27:20: " + getCheckMessage(MSG_KEY, "field5"),
            "28:33: " + getCheckMessage(MSG_KEY, "notes"),
            "30:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "32:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "34:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "36:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "39:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "21:7: " + getCheckMessage(MSG_KEY, "field1"),
            "22:20: " + getCheckMessage(MSG_KEY, "field2"),
            "24:14: " + getCheckMessage(MSG_KEY, "field3"),
            "27:20: " + getCheckMessage(MSG_KEY, "field5"),
            "28:33: " + getCheckMessage(MSG_KEY, "notes"),
            "30:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "32:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "34:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "36:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "39:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expected = {
            "19:7: " + getCheckMessage(MSG_KEY, "field1"),
            "20:20: " + getCheckMessage(MSG_KEY, "field2"),
            "22:14: " + getCheckMessage(MSG_KEY, "field3"),
            "25:20: " + getCheckMessage(MSG_KEY, "field5"),
            "26:33: " + getCheckMessage(MSG_KEY, "notes"),
            "28:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "30:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "32:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "39:17: " + getCheckMessage(MSG_KEY, "testString"),
        };

        verifyWithInlineConfigParser(getPath("Example8.java"), expected);
    }

    @Test
    public void testExample9() throws Exception {
        final String[] expected = {
            "18:7: " + getCheckMessage(MSG_KEY, "field1"),
            "19:20: " + getCheckMessage(MSG_KEY, "field2"),
            "21:14: " + getCheckMessage(MSG_KEY, "field3"),
            "24:20: " + getCheckMessage(MSG_KEY, "field5"),
            "25:33: " + getCheckMessage(MSG_KEY, "notes"),
            "27:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "29:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "31:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "33:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "36:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example9.java"), expected);
    }

    @Test
    public void testExample10() throws Exception {
        final String[] expected = {
            "19:7: " + getCheckMessage(MSG_KEY, "field1"),
            "20:20: " + getCheckMessage(MSG_KEY, "field2"),
            "22:14: " + getCheckMessage(MSG_KEY, "field3"),
            "25:20: " + getCheckMessage(MSG_KEY, "field5"),
            "26:33: " + getCheckMessage(MSG_KEY, "notes"),
            "28:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "30:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "32:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "34:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "39:17: " + getCheckMessage(MSG_KEY, "testString"),
        };

        verifyWithInlineConfigParser(getPath("Example10.java"), expected);
    }

    @Test
    public void testExample11() throws Exception {
        final String[] expected = {
            "17:20: " + getCheckMessage(MSG_KEY, "someIntValue"),
            "18:37: " + getCheckMessage(MSG_KEY, "includes"),
            "19:33: " + getCheckMessage(MSG_KEY, "notes"),
            "20:27: " + getCheckMessage(MSG_KEY, "value"),
            "21:21: " + getCheckMessage(MSG_KEY, "list"),
        };

        verifyWithInlineConfigParser(getPath("Example11.java"), expected);
    }

    @Test
    public void testExample12() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(getPath("Example12.java"), expected);
    }
}
