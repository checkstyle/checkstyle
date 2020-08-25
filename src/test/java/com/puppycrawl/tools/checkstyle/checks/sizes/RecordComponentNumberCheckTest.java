////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.sizes.RecordComponentNumberCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class RecordComponentNumberCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/sizes/recordcomponentnumber";
    }

    @Test
    public void testGetRequiredTokens() {
        final RecordComponentNumberCheck checkObj = new RecordComponentNumberCheck();
        final int[] actual = checkObj.getRequiredTokens();
        final int[] expected = {
            TokenTypes.RECORD_DEF,
        };

        assertArrayEquals(expected, actual, "Default required tokens are invalid");

    }

    @Test
    public void testGetAcceptableTokens() {
        final RecordComponentNumberCheck checkObj = new RecordComponentNumberCheck();
        final int[] actual = checkObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.RECORD_DEF,
        };

        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RecordComponentNumberCheck.class);

        final int max = 8;

        final String[] expected = {
            "53:5: " + getCheckMessage(MSG_KEY, 14, max),
            "66:9: " + getCheckMessage(MSG_KEY, 14, max),
            "72:13: " + getCheckMessage(MSG_KEY, 14, max),
            "78:17: " + getCheckMessage(MSG_KEY, 11, max),
            "97:5: " + getCheckMessage(MSG_KEY, 15, max),
            "118:5: " + getCheckMessage(MSG_KEY, 15, max),
            "128:5: " + getCheckMessage(MSG_KEY, 15, max),
        };

        verify(checkConfig,
                getNonCompilablePath("InputRecordComponentNumber.java"), expected);
    }

    @Test
    public void testRecordComponentNumberTopLevel1() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RecordComponentNumberCheck.class);

        final int max = 8;

        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY, 15, max),
        };

        verify(checkConfig,
                getNonCompilablePath("InputRecordComponentNumberTopLevel1.java"),
                expected);
    }

    @Test
    public void testRecordComponentNumberTopLevel2() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RecordComponentNumberCheck.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig,
                getNonCompilablePath("InputRecordComponentNumberTopLevel2.java"),
                expected);
    }

    @Test
    public void testRecordComponentNumberMax1() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RecordComponentNumberCheck.class);
        checkConfig.addAttribute("max", "1");

        final int max = 1;

        final String[] expected = {
            "25:5: " + getCheckMessage(MSG_KEY, 2, max),
            "29:5: " + getCheckMessage(MSG_KEY, 3, max),
            "33:5: " + getCheckMessage(MSG_KEY, 5, max),
            "49:5: " + getCheckMessage(MSG_KEY, 7, max),
            "54:5: " + getCheckMessage(MSG_KEY, 14, max),
            "63:9: " + getCheckMessage(MSG_KEY, 3, max),
            "67:9: " + getCheckMessage(MSG_KEY, 14, max),
            "73:13: " + getCheckMessage(MSG_KEY, 14, max),
            "79:17: " + getCheckMessage(MSG_KEY, 6, max),
            "93:5: " + getCheckMessage(MSG_KEY, 4, max),
            "97:5: " + getCheckMessage(MSG_KEY, 15, max),
            "107:5: " + getCheckMessage(MSG_KEY, 3, max),
            "111:5: " + getCheckMessage(MSG_KEY, 6, max),
            "122:5: " + getCheckMessage(MSG_KEY, 2, max),
        };

        verify(checkConfig,
                getNonCompilablePath("InputRecordComponentNumberMax1.java"), expected);
    }

    @Test
    public void testRecordComponentNumberMax20() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RecordComponentNumberCheck.class);
        checkConfig.addAttribute("max", "20");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig,
                getNonCompilablePath("InputRecordComponentNumberMax20.java"), expected);
    }

    @Test
    public void testRecordComponentNumberPrivateModifier() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RecordComponentNumberCheck.class);
        checkConfig.addAttribute("accessModifiers", "private");

        final int max = 8;

        final String[] expected = {
            "67:9: " + getCheckMessage(MSG_KEY, 14, max),
            "73:13: " + getCheckMessage(MSG_KEY, 14, max),
            "119:5: " + getCheckMessage(MSG_KEY, 15, max),
        };

        verify(checkConfig,
            getNonCompilablePath("InputRecordComponentNumberPrivateModifier.java"), expected);
    }
}
