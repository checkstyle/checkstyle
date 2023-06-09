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
            "33:7: " + getCheckMessage(MSG_KEY, "field1"),
            "35:20: " + getCheckMessage(MSG_KEY, "field2"),
            "37:14: " + getCheckMessage(MSG_KEY, "field3"),
            "43:20: " + getCheckMessage(MSG_KEY, "field5"),
            "45:33: " + getCheckMessage(MSG_KEY, "notes"),
            "47:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "49:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "51:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "54:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "57:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "35:20: " + getCheckMessage(MSG_KEY, "field2"),
            "37:14: " + getCheckMessage(MSG_KEY, "field3"),
            "43:20: " + getCheckMessage(MSG_KEY, "field5"),
            "45:33: " + getCheckMessage(MSG_KEY, "notes"),
            "47:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "49:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "51:45: " + getCheckMessage(MSG_KEY, "objects1"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "33:7: " + getCheckMessage(MSG_KEY, "field1"),
            "37:14: " + getCheckMessage(MSG_KEY, "field3"),
            "43:20: " + getCheckMessage(MSG_KEY, "field5"),
            "45:33: " + getCheckMessage(MSG_KEY, "notes"),
            "47:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "49:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "51:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "54:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "57:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "33:7: " + getCheckMessage(MSG_KEY, "field1"),
            "35:20: " + getCheckMessage(MSG_KEY, "field2"),
            "37:14: " + getCheckMessage(MSG_KEY, "field3"),
            "39:15: " + getCheckMessage(MSG_KEY, "serialVersionUID"),
            "43:20: " + getCheckMessage(MSG_KEY, "field5"),
            "45:33: " + getCheckMessage(MSG_KEY, "notes"),
            "47:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "49:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "51:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "54:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "57:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "33:7: " + getCheckMessage(MSG_KEY, "field1"),
            "35:20: " + getCheckMessage(MSG_KEY, "field2"),
            "37:14: " + getCheckMessage(MSG_KEY, "field3"),
            "43:20: " + getCheckMessage(MSG_KEY, "field5"),
            "45:33: " + getCheckMessage(MSG_KEY, "notes"),
            "47:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "49:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "51:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "54:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "57:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "26:7: " + getCheckMessage(MSG_KEY, "field1"),
            "28:20: " + getCheckMessage(MSG_KEY, "field2"),
            "30:14: " + getCheckMessage(MSG_KEY, "field3"),
            "36:20: " + getCheckMessage(MSG_KEY, "field5"),
            "38:33: " + getCheckMessage(MSG_KEY, "notes"),
            "40:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "42:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "44:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "47:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "50:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "27:7: " + getCheckMessage(MSG_KEY, "field1"),
            "29:20: " + getCheckMessage(MSG_KEY, "field2"),
            "31:14: " + getCheckMessage(MSG_KEY, "field3"),
            "37:20: " + getCheckMessage(MSG_KEY, "field5"),
            "39:33: " + getCheckMessage(MSG_KEY, "notes"),
            "41:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "43:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "45:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "48:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "51:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expected = {
            "32:7: " + getCheckMessage(MSG_KEY, "field1"),
            "34:20: " + getCheckMessage(MSG_KEY, "field2"),
            "36:14: " + getCheckMessage(MSG_KEY, "field3"),
            "42:20: " + getCheckMessage(MSG_KEY, "field5"),
            "44:33: " + getCheckMessage(MSG_KEY, "notes"),
            "46:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "48:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "50:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "59:17: " + getCheckMessage(MSG_KEY, "testString"),
        };

        verifyWithInlineConfigParser(getPath("Example8.java"), expected);
    }

    @Test
    public void testExample9() throws Exception {
        final String[] expected = {
            "33:7: " + getCheckMessage(MSG_KEY, "field1"),
            "35:20: " + getCheckMessage(MSG_KEY, "field2"),
            "37:14: " + getCheckMessage(MSG_KEY, "field3"),
            "43:20: " + getCheckMessage(MSG_KEY, "field5"),
            "45:33: " + getCheckMessage(MSG_KEY, "notes"),
            "47:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "49:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "51:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "54:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "57:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example9.java"), expected);
    }

    @Test
    public void testExample10() throws Exception {
        final String[] expected = {
            "32:7: " + getCheckMessage(MSG_KEY, "field1"),
            "34:20: " + getCheckMessage(MSG_KEY, "field2"),
            "36:14: " + getCheckMessage(MSG_KEY, "field3"),
            "42:20: " + getCheckMessage(MSG_KEY, "field5"),
            "44:33: " + getCheckMessage(MSG_KEY, "notes"),
            "46:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "48:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "50:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "53:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "59:17: " + getCheckMessage(MSG_KEY, "testString"),
        };

        verifyWithInlineConfigParser(getPath("Example10.java"), expected);
    }

    @Test
    public void testExample11() throws Exception {
        final String[] expected = {
            "30:20: " + getCheckMessage(MSG_KEY, "someIntValue"),
            "31:37: " + getCheckMessage(MSG_KEY, "includes"),
            "32:33: " + getCheckMessage(MSG_KEY, "notes"),
            "33:27: " + getCheckMessage(MSG_KEY, "value"),
            "34:21: " + getCheckMessage(MSG_KEY, "list"),
        };

        verifyWithInlineConfigParser(getPath("Example11.java"), expected);
    }

    @Test
    public void testExample12() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(getPath("Example12.java"), expected);
    }
}
