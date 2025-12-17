///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class IllegalTypeCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/illegaltype";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "16:31: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "TreeSet"),
            "18:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "20:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "LinkedHashMap"),
            "22:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "TreeMap"),
            "25:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.TreeSet"),
            "28:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "30:10: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "34:11: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "38:13: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "20:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "30:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "32:10: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "36:11: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "40:13: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "17:31: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "TreeSet"),
            "19:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "21:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "LinkedHashMap"),
            "23:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "TreeMap"),
            "26:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.TreeSet"),
            "29:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "39:13: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "18:31: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "TreeSet"),
            "20:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "22:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "LinkedHashMap"),
            "24:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "TreeMap"),
            "27:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.TreeSet"),
            "30:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "32:10: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "36:11: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "40:13: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "56:19: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Gitter"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "18:31: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "TreeSet"),
            "20:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "30:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "32:10: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "40:13: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "42:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Boolean"),
            "50:25: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Boolean"),
            "50:56: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Boolean"),
            "61:28: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Foo"),
            "61:39: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Boolean"),
            "67:18: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Foo"),
            "67:38: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Boolean"),
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "73:10: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Optional"),
            "75:3: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Optional"),
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expected = {
            "74:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "var"),
        };

        verifyWithInlineConfigParser(getPath("Example8.java"), expected);
    }

    @Test
    public void testExample9() throws Exception {
        final String[] expected = {
            "20:31: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "TreeSet"),
            "22:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "24:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "LinkedHashMap"),
            "26:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "TreeMap"),
            "29:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.TreeSet"),
            "32:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "34:10: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "38:11: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "42:13: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "77:10: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "AbstractSet"),
        };

        verifyWithInlineConfigParser(getPath("Example9.java"), expected);
    }
}
