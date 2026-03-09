///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class IllegalTypeCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/illegaltype";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "16:31: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "TreeSet"),
            "18:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "20:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "LinkedHashMap"),
            "23:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "TreeMap"),
            "26:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.TreeSet"),
            "29:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "31:10: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "35:11: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "39:13: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "20:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "31:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "33:10: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "37:11: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "41:13: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "17:31: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "TreeSet"),
            "19:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "21:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "LinkedHashMap"),
            "24:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "TreeMap"),
            "27:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.TreeSet"),
            "30:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "40:13: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "18:31: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "TreeSet"),
            "20:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "22:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "LinkedHashMap"),
            "25:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "TreeMap"),
            "28:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.TreeSet"),
            "31:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "33:10: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "37:11: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "41:13: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "57:19: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Gitter"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "18:31: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "TreeSet"),
            "20:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "31:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "33:10: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "41:13: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "43:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Boolean"),
            "51:25: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Boolean"),
            "51:56: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Boolean"),
            "62:28: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Foo"),
            "62:39: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Boolean"),
            "68:18: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Foo"),
            "68:38: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Boolean"),
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "74:10: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Optional"),
            "76:3: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Optional"),
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expected = {
            "75:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "var"),
        };

        verifyWithInlineConfigParser(getPath("Example8.java"), expected);
    }

    @Test
    public void testExample9() throws Exception {
        final String[] expected = {
            "20:31: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "TreeSet"),
            "22:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "24:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "LinkedHashMap"),
            "27:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "TreeMap"),
            "30:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.TreeSet"),
            "33:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "35:10: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "39:11: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "43:13: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "78:10: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "AbstractSet"),
        };

        verifyWithInlineConfigParser(getPath("Example9.java"), expected);
    }
}
