////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
            "57:5: " + getCheckMessage(MSG_KEY, 14, max),
            "70:9: " + getCheckMessage(MSG_KEY, 14, max),
            "76:13: " + getCheckMessage(MSG_KEY, 14, max),
            "82:17: " + getCheckMessage(MSG_KEY, 11, max),
            "101:5: " + getCheckMessage(MSG_KEY, 15, max),
            "122:5: " + getCheckMessage(MSG_KEY, 15, max),
            "132:5: " + getCheckMessage(MSG_KEY, 15, max),
        };

        verifyWithInlineConfigParser(checkConfig,
                getNonCompilablePath("InputRecordComponentNumber.java"), expected);
    }

    @Test
    public void testRecordComponentNumberTopLevel1() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RecordComponentNumberCheck.class);

        final int max = 8;

        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_KEY, 15, max),
        };

        verifyWithInlineConfigParser(checkConfig,
                getNonCompilablePath("InputRecordComponentNumberTopLevel1.java"),
                expected);
    }

    @Test
    public void testRecordComponentNumberTopLevel2() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RecordComponentNumberCheck.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(checkConfig,
                getNonCompilablePath("InputRecordComponentNumberTopLevel2.java"),
                expected);
    }

    @Test
    public void testRecordComponentNumberMax1() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RecordComponentNumberCheck.class);
        checkConfig.addProperty("max", "1");

        final int max = 1;

        final String[] expected = {
            "28:5: " + getCheckMessage(MSG_KEY, 2, max),
            "32:5: " + getCheckMessage(MSG_KEY, 3, max),
            "36:5: " + getCheckMessage(MSG_KEY, 5, max),
            "52:5: " + getCheckMessage(MSG_KEY, 7, max),
            "57:5: " + getCheckMessage(MSG_KEY, 14, max),
            "66:9: " + getCheckMessage(MSG_KEY, 3, max),
            "70:9: " + getCheckMessage(MSG_KEY, 14, max),
            "76:13: " + getCheckMessage(MSG_KEY, 14, max),
            "82:17: " + getCheckMessage(MSG_KEY, 6, max),
            "96:5: " + getCheckMessage(MSG_KEY, 4, max),
            "100:5: " + getCheckMessage(MSG_KEY, 15, max),
            "110:5: " + getCheckMessage(MSG_KEY, 3, max),
            "114:5: " + getCheckMessage(MSG_KEY, 6, max),
            "125:5: " + getCheckMessage(MSG_KEY, 2, max),
        };

        verifyWithInlineConfigParser(checkConfig,
                getNonCompilablePath("InputRecordComponentNumberMax1.java"), expected);
    }

    @Test
    public void testRecordComponentNumberMax20() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RecordComponentNumberCheck.class);
        checkConfig.addProperty("max", "20");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(checkConfig,
                getNonCompilablePath("InputRecordComponentNumberMax20.java"), expected);
    }

    @Test
    public void testRecordComponentNumberPrivateModifier() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RecordComponentNumberCheck.class);
        checkConfig.addProperty("accessModifiers", "private");

        final int max = 8;

        final String[] expected = {
            "70:9: " + getCheckMessage(MSG_KEY, 14, max),
            "76:13: " + getCheckMessage(MSG_KEY, 14, max),
            "122:5: " + getCheckMessage(MSG_KEY, 15, max),
        };

        verifyWithInlineConfigParser(checkConfig,
            getNonCompilablePath("InputRecordComponentNumberPrivateModifier.java"), expected);
    }
}
