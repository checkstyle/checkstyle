////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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

package com.google.checkstyle.test.chapter4formatting.rule413emptyblocks;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.blocks.EmptyBlockCheck;

public class EmptyBlockTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule413emptyblocks";
    }

    @Test
    public void testEmptyBlock() throws Exception {
        final String[] expected = {
            "13:12: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "STATIC_INIT"),
            "19:21: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "22:34: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "24:41: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "while"),
            "25:62: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "for"),
            "26:21: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "27:12: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "do"),
            "28:20: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "switch"),
            "53:12: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "STATIC_INIT"),
            "68:25: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "71:38: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "73:45: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "while"),
            "74:66: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "for"),
            "75:25: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "76:16: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "do"),
            "77:24: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "switch"),
            "98:29: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "101:42: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "103:49: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "while"),
            "104:70: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "for"),
            "105:29: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "106:20: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "do"),
            "107:28: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "switch"),
            "126:16: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "else"),
            "172:28: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "173:14: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "else"),
            "175:21: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "179:14: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "else"),
            "181:21: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "182:26: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "195:20: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "else"),
            "241:32: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "242:18: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "else"),
            "244:25: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "248:18: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "else"),
            "250:25: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "251:30: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "264:24: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "else"),
            "310:36: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "311:22: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "else"),
            "313:29: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "317:22: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "else"),
            "319:29: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
            "320:34: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "if"),
        };

        final Configuration checkConfig = getModuleConfig("EmptyBlock");
        final String filePath = getPath("InputEmptyBlockBasic.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testEmptyBlockCatch() throws Exception {
        final String[] expected = {
            "29:17: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "finally"),
            "50:21: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "finally"),
            "72:21: " + getCheckMessage(EmptyBlockCheck.class, "block.empty", "finally"),
        };

        final Configuration checkConfig = getModuleConfig("EmptyBlock");
        final String filePath = getPath("InputEmptyBlock.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
