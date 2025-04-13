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
            "22:31: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "TreeSet"),
            "24:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "26:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "LinkedHashMap"),
            "28:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "TreeMap"),
            "31:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.TreeSet"),
            "34:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "36:10: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "40:11: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "44:13: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "25:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "35:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "37:10: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "41:11: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "45:13: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "23:31: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "TreeSet"),
            "25:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "27:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "LinkedHashMap"),
            "29:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "TreeMap"),
            "32:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.TreeSet"),
            "35:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "45:13: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "24:31: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "TreeSet"),
            "26:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "28:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "LinkedHashMap"),
            "30:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "TreeMap"),
            "33:5: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.TreeSet"),
            "36:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "38:10: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "42:11: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "46:13: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "62:19: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Gitter"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "24:31: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "TreeSet"),
            "26:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "36:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "java.util.HashSet"),
            "38:10: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
            "46:13: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "HashMap"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "48:21: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Boolean"),
            "56:25: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Boolean"),
            "56:56: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Boolean"),
            "67:28: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Foo"),
            "67:39: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Boolean"),
            "73:18: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Foo"),
            "73:38: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Boolean"),
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "71:10: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Optional"),
            "73:3: " + getCheckMessage(IllegalTypeCheck.MSG_KEY, "Optional"),
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }
}
