///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.google.checkstyle.test.chapter4formatting.rule413emptyblocks;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.blocks.EmptyCatchBlockCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class EmptyCatchBlockTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule413emptyblocks";
    }

    @Test
    public void testEmptyBlockCatch() throws Exception {
        final String[] expected = {
            "28:31: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
            "49:35: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
            "71:35: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
            "79:31: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
            "83:40: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
        };

        final Configuration checkConfig = getModuleConfig("EmptyCatchBlock");
        final String filePath = getPath("InputEmptyBlockCatch.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testNoViolations() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getModuleConfig("EmptyCatchBlock");
        final String filePath = getPath("InputEmptyCatchBlockNoViolations.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testViolationsByComment() throws Exception {
        final String[] expected = {
            "20:9: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
            "28:18: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
        };

        final Configuration checkConfig = getModuleConfig("EmptyCatchBlock");
        final String filePath = getPath("InputEmptyCatchBlockViolationsByComment.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testViolationsByVariableName() throws Exception {
        final String[] expected = {
            "20:9: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
            "36:18: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
            "52:18: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
            "59:18: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
        };

        final Configuration checkConfig = getModuleConfig("EmptyCatchBlock");
        final String filePath = getPath("InputEmptyCatchBlockViolationsByVariableName.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
