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

package com.puppycrawl.tools.checkstyle.checks.imports;

import static com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck.MSG_ORDERING;
import static com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck.MSG_SEPARATED_IN_GROUP;
import static com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck.MSG_SEPARATION;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class ImportOrderCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/imports/importorder";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "java.io.IOException"),
            "15:1: " + getCheckMessage(MSG_ORDERING, "java.io.IOException"),
            "19:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "javax.net.ssl.TrustManager"),
            "22:1: " + getCheckMessage(MSG_ORDERING, "java.util.Set"),
            "23:1: " + getCheckMessage(MSG_ORDERING, "java.util.Map"),
            "24:1: " + getCheckMessage(MSG_ORDERING, "com.sun.security.auth.UserPrincipal"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "19:1: " + getCheckMessage(MSG_ORDERING, "java.lang.Math.PI"),
            "22:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "java.net.URL"),
            "27:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "javax.net.ssl.X509TrustManager"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "21:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "java.io.File"),
            "23:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "java.io.IOException"),
            "27:1: " + getCheckMessage(MSG_ORDERING, "javax.swing.WindowConstants.EXIT_ON_CLOSE"),
            "29:1: " + getCheckMessage(MSG_SEPARATION, "org.w3c.dom.Element"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "24:1: " + getCheckMessage(MSG_ORDERING, "javax.swing.JComponent"),
            "27:1: " + getCheckMessage(MSG_ORDERING, "javax.swing.JComponent"),
            "28:1: " + getCheckMessage(MSG_ORDERING, "com.sun.source.tree.Tree"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "javax.swing.JComponent"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "19:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "java.util.Set"),
            "20:1: " + getCheckMessage(MSG_ORDERING, "java.lang.Math.sin"),
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {

        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expected = {

        };

        verifyWithInlineConfigParser(getPath("Example8.java"), expected);
    }

    @Test
    public void testExample9() throws Exception {
        final String[] expected = {
            "21:1: " + getCheckMessage(MSG_ORDERING,
                    "java.lang.Character.UnicodeBlock.BASIC_LATIN"),
        };

        verifyWithInlineConfigParser(getPath("Example9.java"), expected);
    }

    @Test
    public void testExample10() throws Exception {
        final String[] expected = {
            "18:1: " + getCheckMessage(MSG_SEPARATION, "javax.swing.WindowConstants.*"),
        };

        verifyWithInlineConfigParser(getPath("Example10.java"), expected);
    }

    @Test
    public void testExample11() throws Exception {
        final String[] expected = {

        };

        verifyWithInlineConfigParser(getPath("Example11.java"), expected);
    }

    @Test
    public void testExample12() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_ORDERING, "java.io.File.createTempFile"),
        };

        verifyWithInlineConfigParser(getPath("Example12.java"), expected);
    }
}
