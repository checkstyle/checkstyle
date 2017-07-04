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

import static com.puppycrawl.tools.checkstyle.checks.sizes.OuterTypeNumberCheck.MSG_KEY;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class OuterTypeNumberCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "sizes" + File.separator
                + "outertypenumber" + File.separator
                + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final OuterTypeNumberCheck checkObj = new OuterTypeNumberCheck();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
        };
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testGetAcceptableTokens() {
        final OuterTypeNumberCheck outerTypeNumberObj =
            new OuterTypeNumberCheck();
        final int[] actual = outerTypeNumberObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF, TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
        };

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OuterTypeNumberCheck.class);
        final String[] expected = {
            "6:1: " + getCheckMessage(MSG_KEY, 3, 1),
        };
        verify(checkConfig, getPath("InputOuterTypeNumberSimple.java"), expected);
    }

    @Test
    public void testMax30() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OuterTypeNumberCheck.class);
        checkConfig.addAttribute("max", "30");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputOuterTypeNumberSimple.java"), expected);
    }

    @Test
    public void testWithInnerClass() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OuterTypeNumberCheck.class);
        checkConfig.addAttribute("max", "1");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputOuterTypeNumberEmptyInner.java"), expected);
    }
}
