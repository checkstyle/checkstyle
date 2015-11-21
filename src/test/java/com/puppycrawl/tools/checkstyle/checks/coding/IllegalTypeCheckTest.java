////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.IllegalTypeCheck.MSG_KEY;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class IllegalTypeCheckTest extends BaseCheckTestSupport {
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createCheckConfig(IllegalTypeCheck.class);
    }

    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "coding" + File.separator + filename);
    }

    @Test
    public void testValidateAbstractClassNamesSetToTrue() throws Exception {
        checkConfig.addAttribute("validateAbstractClassNames", "true");
        final String[] expected = {
            "27:5: " + getCheckMessage(MSG_KEY, "AbstractClass"),
            "29:37: " + getCheckMessage(MSG_KEY, "AbstractClass"),
            "33:12: " + getCheckMessage(MSG_KEY, "AbstractClass"),
        };

        verify(checkConfig, getPath("InputIllegalTypeAbstractClassNames.java"), expected);
    }

    @Test
    public void testValidateAbstractClassNamesSetToFalse() throws Exception {
        checkConfig.addAttribute("validateAbstractClassNames", "false");
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputIllegalTypeAbstractClassNames.java"), expected);
    }

    @Test
    public void testDefaults() throws Exception {
        final String[] expected = {
            "16:13: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "17:13: " + getCheckMessage(MSG_KEY, "TreeSet"),
        };

        verify(checkConfig, getPath("InputIllegalType.java"), expected);
    }

    @Test
    public void testIgnoreMethodNames() throws Exception {
        checkConfig.addAttribute("ignoredMethodNames", "table2");
        checkConfig.addAttribute("validateAbstractClassNames", "true");
        final String[] expected = {
            "6:13: " + getCheckMessage(MSG_KEY, "AbstractClass"),
            "9:13: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.coding.InputIllegalType.AbstractClass"),
            "16:13: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
        };

        verify(checkConfig, getPath("InputIllegalType.java"), expected);
    }

    @Test
    public void testFormat() throws Exception {
        checkConfig.addAttribute("format", "^$");

        final String[] expected = {
            "16:13: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "17:13: " + getCheckMessage(MSG_KEY, "TreeSet"),
        };

        verify(checkConfig, getPath("InputIllegalType.java"), expected);
    }

    @Test
    public void testLegalAbstractClassNames() throws Exception {
        checkConfig.addAttribute("validateAbstractClassNames", "true");
        checkConfig.addAttribute("legalAbstractClassNames", "AbstractClass");

        final String[] expected = {
            "9:13: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.coding.InputIllegalType.AbstractClass"),
            "16:13: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "17:13: " + getCheckMessage(MSG_KEY, "TreeSet"),
        };

        verify(checkConfig, getPath("InputIllegalType.java"), expected);
    }

    @Test
    public void testSameFileNameFalsePositive() throws Exception {
        checkConfig.addAttribute("illegalClassNames", "java.util.GregorianCalendar, SubCalendar, "
                + "java.util.List");

        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_KEY, "SubCalendar"),
            "27:5: " + getCheckMessage(MSG_KEY, "java.util.List"),
        };

        verify(checkConfig, getPath("InputIllegalTypeSameFileName.java"), expected);
    }

    @Test
    public void testSameFileNameGeneral() throws Exception {
        checkConfig.addAttribute("illegalClassNames",
            "List, InputGregorianCalendar, java.io.File, ArrayList");
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_KEY, "InputGregorianCalendar"),
            "16:23: " + getCheckMessage(MSG_KEY, "InputGregorianCalendar"),
            "24:9: " + getCheckMessage(MSG_KEY, "List"),
            "25:9: " + getCheckMessage(MSG_KEY, "java.io.File"),
            "27:5: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "28:13: " + getCheckMessage(MSG_KEY, "ArrayList"),
        };
        verify(checkConfig, getPath("InputIllegalTypeSameFileName.java"), expected);
    }

    @Test
    public void testStarImports() throws Exception {
        checkConfig.addAttribute("illegalClassNames", "List");

        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_KEY, "List"),
        };

        verify(checkConfig, getPath("InputIllegalTypeStarImports.java"), expected);
    }

    @Test
    public void testStaticImports() throws Exception {
        checkConfig.addAttribute("illegalClassNames", "SomeStaticClass");
        checkConfig.addAttribute("ignoredMethodNames", "foo1");

        final String[] expected = {
            "13:6: " + getCheckMessage(MSG_KEY, "SomeStaticClass"),
            "15:31: " + getCheckMessage(MSG_KEY, "SomeStaticClass"),
        };

        verify(checkConfig, getPath("InputIllegalTypeStaticImports.java"), expected);
    }

    @Test
    public void testMemberModifiers() throws Exception {
        checkConfig.addAttribute("validateAbstractClassNames", "true");
        checkConfig.addAttribute("memberModifiers", "LITERAL_PRIVATE, LITERAL_PROTECTED,"
                + " LITERAL_STATIC");
        final String[] expected = {
            "6:13: " + getCheckMessage(MSG_KEY, "AbstractClass"),
            "9:13: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.coding."
                    + "InputIllegalTypeMemberModifiers.AbstractClass"),
            "16:13: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "17:13: " + getCheckMessage(MSG_KEY, "TreeSet"),
            "23:15: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.coding."
                    + "InputIllegalTypeMemberModifiers.AbstractClass"),
            "25:25: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "33:15: " + getCheckMessage(MSG_KEY, "AbstractClass"),
        };

        verify(checkConfig, getPath("InputIllegalTypeMemberModifiers.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final IllegalTypeCheck check = new IllegalTypeCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
    }

    @Test
    public void testImproperToken() throws Exception {
        final IllegalTypeCheck check = new IllegalTypeCheck();

        final DetailAST classDefAst = new DetailAST();
        classDefAst.setType(TokenTypes.CLASS_DEF);

        try {
            check.visitToken(classDefAst);
            Assert.fail();
        }
        catch (IllegalStateException ex) {
            // it is OK
        }
    }
}
