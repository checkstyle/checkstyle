////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck.MSG_KEY;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class LineLengthCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/sizes/linelength";
    }

    @Test
    public void testGetRequiredTokens() {
        final LineLengthCheck checkObj = new LineLengthCheck();
        assertArrayEquals(
            "LineLengthCheck#getRequiredTokens should return empty array by default",
            CommonUtil.EMPTY_INT_ARRAY, checkObj.getRequiredTokens());
    }

    @Test
    public void testGetAcceptableTokens() {
        final LineLengthCheck checkObj = new LineLengthCheck();
        assertArrayEquals(
            "LineLengthCheck#getAcceptabletokens should return empty array by default",
            CommonUtil.EMPTY_INT_ARRAY, checkObj.getAcceptableTokens());
    }

    @Test
    public void testSimple()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(LineLengthCheck.class);
        checkConfig.addAttribute("max", "80");
        checkConfig.addAttribute("ignorePattern", "^.*is OK.*regexp.*$");
        final String[] expected = {
            "18: " + getCheckMessage(MSG_KEY, 80, 81),
            "145: " + getCheckMessage(MSG_KEY, 80, 83),
        };
        verify(checkConfig, getPath("InputLineLengthSimple.java"), expected);
    }

    @Test
    public void shouldLogActualLineLength()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(LineLengthCheck.class);
        checkConfig.addAttribute("max", "80");
        checkConfig.addAttribute("ignorePattern", "^.*is OK.*regexp.*$");
        checkConfig.addMessage("maxLineLen", "{0},{1}");
        final String[] expected = {
            "18: 80,81",
            "145: 80,83",
        };
        verify(checkConfig, getPath("InputLineLengthSimple.java"), expected);
    }

    @Test
    public void shouldNotLogLongImportStatements() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(LineLengthCheck.class);
        checkConfig.addAttribute("max", "80");
        final String[] expected = {
            "9: " + getCheckMessage(MSG_KEY, 80, 87),
        };
        verify(checkConfig, getPath("InputLineLengthLongImportStatements.java"), expected);
    }

    @Test
    public void shouldNotLogLongPackageStatements() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(LineLengthCheck.class);
        checkConfig.addAttribute("max", "80");
        final String[] expected = {
            "7: " + getCheckMessage(MSG_KEY, 80, 88),
        };
        verify(checkConfig, getNonCompilablePath("InputLineLengthLongPackageStatement.java"),
                expected);
    }

}
