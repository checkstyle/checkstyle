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

import static com.puppycrawl.tools.checkstyle.checks.sizes.RecordComponentNumberCheck.MSG_PACKAGE_COMPONENTS;
import static com.puppycrawl.tools.checkstyle.checks.sizes.RecordComponentNumberCheck.MSG_PRIVATE_COMPONENTS;
import static com.puppycrawl.tools.checkstyle.checks.sizes.RecordComponentNumberCheck.MSG_PROTECTED_COMPONENTS;
import static com.puppycrawl.tools.checkstyle.checks.sizes.RecordComponentNumberCheck.MSG_PUBLIC_COMPONENTS;
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
            "57:5: " + getCheckMessage(MSG_PUBLIC_COMPONENTS, 14, max),
            "70:9: " + getCheckMessage(MSG_PRIVATE_COMPONENTS, 14, max),
            "76:13: " + getCheckMessage(MSG_PRIVATE_COMPONENTS, 14, max),
            "82:17: " + getCheckMessage(MSG_PUBLIC_COMPONENTS, 11, max),
            "101:5: " + getCheckMessage(MSG_PUBLIC_COMPONENTS, 15, max),
            "122:5: " + getCheckMessage(MSG_PRIVATE_COMPONENTS, 15, max),
            "132:5: " + getCheckMessage(MSG_PROTECTED_COMPONENTS, 15, max),
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
            "12:1: " + getCheckMessage(MSG_PUBLIC_COMPONENTS, 15, max),
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
        checkConfig.addAttribute("maxInPrivateRecord", "1");
        checkConfig.addAttribute("maxInPackageRecord", "1");
        checkConfig.addAttribute("maxInProtectedRecord", "1");
        checkConfig.addAttribute("maxInPublicRecord", "1");
        final int max = 1;

        final String[] expected = {
            "29:5: " + getCheckMessage(MSG_PUBLIC_COMPONENTS, 2, max),
            "33:5: " + getCheckMessage(MSG_PUBLIC_COMPONENTS, 3, max),
            "37:5: " + getCheckMessage(MSG_PUBLIC_COMPONENTS, 5, max),
            "53:5: " + getCheckMessage(MSG_PUBLIC_COMPONENTS, 7, max),
            "58:5: " + getCheckMessage(MSG_PUBLIC_COMPONENTS, 14, max),
            "67:9: " + getCheckMessage(MSG_PACKAGE_COMPONENTS, 3, max),
            "71:9: " + getCheckMessage(MSG_PRIVATE_COMPONENTS, 14, max),
            "77:13: " + getCheckMessage(MSG_PRIVATE_COMPONENTS, 14, max),
            "83:17: " + getCheckMessage(MSG_PUBLIC_COMPONENTS, 6, max),
            "97:5: " + getCheckMessage(MSG_PUBLIC_COMPONENTS, 4, max),
            "101:5: " + getCheckMessage(MSG_PUBLIC_COMPONENTS, 15, max),
            "111:5: " + getCheckMessage(MSG_PUBLIC_COMPONENTS, 3, max),
            "115:5: " + getCheckMessage(MSG_PUBLIC_COMPONENTS, 6, max),
            "126:5: " + getCheckMessage(MSG_PRIVATE_COMPONENTS, 2, max),
        };

        verify(checkConfig,
                getNonCompilablePath("InputRecordComponentNumberMax1.java"), expected);
    }

    @Test
    public void testRecordComponentNumberMax20() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RecordComponentNumberCheck.class);
        checkConfig.addAttribute("maxInPrivateRecord", "20");
        checkConfig.addAttribute("maxInPackageRecord", "20");
        checkConfig.addAttribute("maxInProtectedRecord", "20");
        checkConfig.addAttribute("maxInPublicRecord", "20");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig,
                getNonCompilablePath("InputRecordComponentNumberMax20.java"), expected);
    }

    @Test
    public void testRecordComponentNumberDifferentMaxForScope() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RecordComponentNumberCheck.class);
        checkConfig.addAttribute("maxInPrivateRecord", "2");
        checkConfig.addAttribute("maxInPackageRecord", "1");
        checkConfig.addAttribute("maxInProtectedRecord", "3");
        checkConfig.addAttribute("maxInPublicRecord", "5");

        final String[] expected = {
            "32:5: " + getCheckMessage(MSG_PRIVATE_COMPONENTS, 3, 2),
            "36:5: " + getCheckMessage(MSG_PACKAGE_COMPONENTS, 5, 1),
            "52:5: " + getCheckMessage(MSG_PUBLIC_COMPONENTS, 7, 5),
            "57:5: " + getCheckMessage(MSG_PUBLIC_COMPONENTS, 14, 5),
            "66:9: " + getCheckMessage(MSG_PACKAGE_COMPONENTS, 3, 1),
            "70:9: " + getCheckMessage(MSG_PRIVATE_COMPONENTS, 14, 2),
            "76:13: " + getCheckMessage(MSG_PRIVATE_COMPONENTS, 14, 2),
            "82:17: " + getCheckMessage(MSG_PUBLIC_COMPONENTS, 11, 5),
            "101:5: " + getCheckMessage(MSG_PUBLIC_COMPONENTS, 15, 5),
            "113:5: " + getCheckMessage(MSG_PUBLIC_COMPONENTS, 6, 5),
            "122:5: " + getCheckMessage(MSG_PRIVATE_COMPONENTS, 15, 2),
            "132:5: " + getCheckMessage(MSG_PROTECTED_COMPONENTS, 15, 3),
        };

        verify(checkConfig,
                getNonCompilablePath("InputRecordComponentNumberDifferentMaxForScope.java"),
                expected);
    }
}
