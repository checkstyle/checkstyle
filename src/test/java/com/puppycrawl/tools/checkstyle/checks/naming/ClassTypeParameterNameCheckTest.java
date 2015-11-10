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

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ClassTypeParameterNameCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "naming" + File.separator + filename);
    }

    @Test
    public void testGetInterfaceRequiredTokens() {
        final InterfaceTypeParameterNameCheck checkObj =
            new InterfaceTypeParameterNameCheck();
        final int[] expected = {TokenTypes.TYPE_PARAMETER};
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testGetMethodRequiredTokens() {
        final MethodTypeParameterNameCheck checkObj =
            new MethodTypeParameterNameCheck();
        final int[] expected = {TokenTypes.TYPE_PARAMETER};
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testGetClassRequiredTokens() {
        final ClassTypeParameterNameCheck checkObj =
            new ClassTypeParameterNameCheck();
        final int[] expected = {TokenTypes.TYPE_PARAMETER};
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testClassDefault()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ClassTypeParameterNameCheck.class);

        final String pattern = "^[A-Z]$";

        final String[] expected = {
            "5:38: " + getCheckMessage(MSG_INVALID_PATTERN, "t", pattern),
            "13:14: " + getCheckMessage(MSG_INVALID_PATTERN, "foo", pattern),
            "27:24: " + getCheckMessage(MSG_INVALID_PATTERN, "foo", pattern),
        };
        verify(checkConfig, getPath("InputTypeParameterName.java"), expected);
    }

    @Test
    public void testMethodDefault()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodTypeParameterNameCheck.class);

        final String pattern = "^[A-Z]$";

        final String[] expected = {
            "7:13: " + getCheckMessage(MSG_INVALID_PATTERN, "TT", pattern),
            "9:6: " + getCheckMessage(MSG_INVALID_PATTERN, "e_e", pattern),
            "19:6: " + getCheckMessage(MSG_INVALID_PATTERN, "Tfo$o2T", pattern),
            "23:6: " + getCheckMessage(MSG_INVALID_PATTERN, "foo", pattern),
            "28:10: " + getCheckMessage(MSG_INVALID_PATTERN, "_fo", pattern),
        };
        verify(checkConfig, getPath("InputTypeParameterName.java"), expected);
    }

    @Test
    public void testInterfaceDefault()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(InterfaceTypeParameterNameCheck.class);

        final String pattern = "^[A-Z]$";

        final String[] expected = {
            "48:15: " + getCheckMessage(MSG_INVALID_PATTERN, "Input", pattern),
        };
        verify(checkConfig, getPath("InputTypeParameterName.java"), expected);
    }

    @Test
    public void testClassFooName()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ClassTypeParameterNameCheck.class);
        checkConfig.addAttribute("format", "^foo$");

        final String pattern = "^foo$";

        final String[] expected = {
            "5:38: " + getCheckMessage(MSG_INVALID_PATTERN, "t", pattern),
            "33:18: " + getCheckMessage(MSG_INVALID_PATTERN, "T", pattern),
        };
        verify(checkConfig, getPath("InputTypeParameterName.java"), expected);
    }

    @Test
    public void testMethodFooName()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodTypeParameterNameCheck.class);
        checkConfig.addAttribute("format", "^foo$");

        final String pattern = "^foo$";

        final String[] expected = {
            "7:13: " + getCheckMessage(MSG_INVALID_PATTERN, "TT", pattern),
            "9:6: " + getCheckMessage(MSG_INVALID_PATTERN, "e_e", pattern),
            "19:6: " + getCheckMessage(MSG_INVALID_PATTERN, "Tfo$o2T", pattern),
            "28:10: " + getCheckMessage(MSG_INVALID_PATTERN, "_fo", pattern),
            "35:6: " + getCheckMessage(MSG_INVALID_PATTERN, "E", pattern),
            "37:14: " + getCheckMessage(MSG_INVALID_PATTERN, "T", pattern),
            //"40:14: Name 'EE' must match pattern '^foo$'.",
        };
        verify(checkConfig, getPath("InputTypeParameterName.java"), expected);
    }

    @Test
    public void testInterfaceFooName()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(InterfaceTypeParameterNameCheck.class);
        checkConfig.addAttribute("format", "^foo$");

        final String pattern = "^foo$";

        final String[] expected = {
            "48:15: " + getCheckMessage(MSG_INVALID_PATTERN, "Input", pattern),
            "52:24: " + getCheckMessage(MSG_INVALID_PATTERN, "T", pattern),
        };
        verify(checkConfig, getPath("InputTypeParameterName.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final ClassTypeParameterNameCheck typeParameterNameCheckObj =
            new ClassTypeParameterNameCheck();
        final int[] actual = typeParameterNameCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.TYPE_PARAMETER,
        };
        assertArrayEquals(expected, actual);
    }
}
