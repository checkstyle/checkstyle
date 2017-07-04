////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import static com.puppycrawl.tools.checkstyle.checks.sizes.MethodCountCheck.MSG_MANY_METHODS;
import static com.puppycrawl.tools.checkstyle.checks.sizes.MethodCountCheck.MSG_PACKAGE_METHODS;
import static com.puppycrawl.tools.checkstyle.checks.sizes.MethodCountCheck.MSG_PRIVATE_METHODS;
import static com.puppycrawl.tools.checkstyle.checks.sizes.MethodCountCheck.MSG_PROTECTED_METHODS;
import static com.puppycrawl.tools.checkstyle.checks.sizes.MethodCountCheck.MSG_PUBLIC_METHODS;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class MethodCountCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "sizes" + File.separator
                + "methodcount" + File.separator
                + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final MethodCountCheck checkObj = new MethodCountCheck();
        final int[] expected = {TokenTypes.METHOD_DEF};
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testGetAcceptableTokens() {
        final MethodCountCheck methodCountCheckObj =
            new MethodCountCheck();
        final int[] actual = methodCountCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_DEF,
        };

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testDefaults() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodCountCheck.class);

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputMethodCount.java"), expected);
    }

    @Test
    public void testThrees() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodCountCheck.class);
        checkConfig.addAttribute("maxPrivate", "3");
        checkConfig.addAttribute("maxPackage", "3");
        checkConfig.addAttribute("maxProtected", "3");
        checkConfig.addAttribute("maxPublic", "3");
        checkConfig.addAttribute("maxTotal", "3");

        final String[] expected = {
            "3: " + getCheckMessage(MSG_PACKAGE_METHODS, 5, 3),
            "3: " + getCheckMessage(MSG_PRIVATE_METHODS, 5, 3),
            "3: " + getCheckMessage(MSG_PROTECTED_METHODS, 5, 3),
            "3: " + getCheckMessage(MSG_PUBLIC_METHODS, 5, 3),
            "3: " + getCheckMessage(MSG_MANY_METHODS, 20, 3),
            "9: " + getCheckMessage(MSG_PUBLIC_METHODS, 5, 3),
            "9: " + getCheckMessage(MSG_MANY_METHODS, 5, 3),
            "45: " + getCheckMessage(MSG_PUBLIC_METHODS, 5, 3),
            "45: " + getCheckMessage(MSG_MANY_METHODS, 5, 3),
        };

        verify(checkConfig, getPath("InputMethodCount.java"), expected);
    }

    @Test
    public void testEnum() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodCountCheck.class);
        checkConfig.addAttribute("maxPrivate", "0");
        checkConfig.addAttribute("maxTotal", "2");

        final String[] expected = {
            "9: " + getCheckMessage(MSG_PRIVATE_METHODS, 1, 0),
            "9: " + getCheckMessage(MSG_MANY_METHODS, 3, 2),
        };

        verify(checkConfig, getPath("InputMethodCount2.java"), expected);
    }

    @Test
    public void testWithPackageModifier() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(MethodCountCheck.class);
        checkConfig.addAttribute("maxPrivate", "0");
        checkConfig.addAttribute("maxTotal", "2");

        final String[] expected = {
            "3: " + getCheckMessage(MSG_MANY_METHODS, 5, 2),
        };

        verify(checkConfig, getPath("InputMethodCount3.java"), expected);
    }
}
