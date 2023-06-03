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

import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class VisibilityModifierCheckExamplesTest extends AbstractModuleTestSupport {
   @Override
    protected String getResourceLocation() {
        return "xdocs-test";
    }

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/design/visibilitymodifier";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "35:7: " + getCheckMessage(MSG_KEY, "field1"),
            "37:20: " + getCheckMessage(MSG_KEY, "field2"),
            "39:14: " + getCheckMessage(MSG_KEY, "field3"),
            "45:20: " + getCheckMessage(MSG_KEY, "field5"),
            "47:33: " + getCheckMessage(MSG_KEY, "notes"),
            "49:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "51:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "53:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "56:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "59:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "37:20: " + getCheckMessage(MSG_KEY, "field2"),
            "39:14: " + getCheckMessage(MSG_KEY, "field3"),
            "45:20: " + getCheckMessage(MSG_KEY, "field5"),
            "47:33: " + getCheckMessage(MSG_KEY, "notes"),
            "49:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "51:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "53:45: " + getCheckMessage(MSG_KEY, "objects1"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "35:7: " + getCheckMessage(MSG_KEY, "field1"),
            "39:14: " + getCheckMessage(MSG_KEY, "field3"),
            "45:20: " + getCheckMessage(MSG_KEY, "field5"),
            "47:33: " + getCheckMessage(MSG_KEY, "notes"),
            "49:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "51:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "53:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "56:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "59:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "35:7: " + getCheckMessage(MSG_KEY, "field1"),
            "37:20: " + getCheckMessage(MSG_KEY, "field2"),
            "39:14: " + getCheckMessage(MSG_KEY, "field3"),
            "41:15: " + getCheckMessage(MSG_KEY, "serialVersionUID"),
            "45:20: " + getCheckMessage(MSG_KEY, "field5"),
            "47:33: " + getCheckMessage(MSG_KEY, "notes"),
            "49:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "51:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "53:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "56:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "59:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "35:7: " + getCheckMessage(MSG_KEY, "field1"),
            "37:20: " + getCheckMessage(MSG_KEY, "field2"),
            "39:14: " + getCheckMessage(MSG_KEY, "field3"),
            "45:20: " + getCheckMessage(MSG_KEY, "field5"),
            "47:33: " + getCheckMessage(MSG_KEY, "notes"),
            "49:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "51:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "53:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "56:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "59:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "28:7: " + getCheckMessage(MSG_KEY, "field1"),
            "30:20: " + getCheckMessage(MSG_KEY, "field2"),
            "32:14: " + getCheckMessage(MSG_KEY, "field3"),
            "42:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "46:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "49:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "52:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "28:7: " + getCheckMessage(MSG_KEY, "field1"),
            "30:20: " + getCheckMessage(MSG_KEY, "field2"),
            "32:14: " + getCheckMessage(MSG_KEY, "field3"),
            "42:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "46:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "49:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "52:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expected = {
            "34:7: " + getCheckMessage(MSG_KEY, "field1"),
            "36:20: " + getCheckMessage(MSG_KEY, "field2"),
            "38:14: " + getCheckMessage(MSG_KEY, "field3"),
            "44:20: " + getCheckMessage(MSG_KEY, "field5"),
            "46:33: " + getCheckMessage(MSG_KEY, "notes"),
            "48:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "50:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "52:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "61:17: " + getCheckMessage(MSG_KEY, "testString"),
        };

        verifyWithInlineConfigParser(getPath("Example8.java"), expected);
    }

    @Test
    public void testExample9() throws Exception {
        final String[] expected = {
            "35:7: " + getCheckMessage(MSG_KEY, "field1"),
            "37:20: " + getCheckMessage(MSG_KEY, "field2"),
            "39:14: " + getCheckMessage(MSG_KEY, "field3"),
            "45:20: " + getCheckMessage(MSG_KEY, "field5"),
            "47:33: " + getCheckMessage(MSG_KEY, "notes"),
            "49:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "51:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "53:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "56:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "59:10: " + getCheckMessage(MSG_KEY, "shortCustomAnnotated"),
        };

        verifyWithInlineConfigParser(getPath("Example9.java"), expected);
    }

    @Test
    public void testExample10() throws Exception {
        final String[] expected = {
            "34:7: " + getCheckMessage(MSG_KEY, "field1"),
            "36:20: " + getCheckMessage(MSG_KEY, "field2"),
            "38:14: " + getCheckMessage(MSG_KEY, "field3"),
            "44:20: " + getCheckMessage(MSG_KEY, "field5"),
            "46:33: " + getCheckMessage(MSG_KEY, "notes"),
            "48:28: " + getCheckMessage(MSG_KEY, "mySet1"),
            "50:37: " + getCheckMessage(MSG_KEY, "mySet2"),
            "52:45: " + getCheckMessage(MSG_KEY, "objects1"),
            "55:10: " + getCheckMessage(MSG_KEY, "annotatedString"),
            "61:17: " + getCheckMessage(MSG_KEY, "testString"),
        };

        verifyWithInlineConfigParser(getPath("Example10.java"), expected);
    }

    @Test
    public void testExample11() throws Exception {
        final String[] expected = {
            "31:20: " + getCheckMessage(MSG_KEY, "someIntValue"),
            "32:37: " + getCheckMessage(MSG_KEY, "includes"),
            "33:33: " + getCheckMessage(MSG_KEY, "notes"),
            "34:27: " + getCheckMessage(MSG_KEY, "value"),
            "35:21: " + getCheckMessage(MSG_KEY, "list"),
        };

        verifyWithInlineConfigParser(getPath("Example11.java"), expected);
    }

    @Test
    public void testExample12() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(getPath("Example12.java"), expected);
    }
}
