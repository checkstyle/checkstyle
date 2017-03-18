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

package com.google.checkstyle.test.chapter4formatting.rule413emptyblocks;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.blocks.EmptyCatchBlockCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class EmptyCatchBlockTest extends BaseCheckTestSupport {

    @Override
    protected String getPath(String fileName) throws IOException {
        return super.getPath("chapter4formatting" + File.separator + "rule413emptyblocks"
                + File.separator + fileName);
    }

    @Test
    public void emptyBlockTestCatch() throws Exception {

        final String[] expected = {
            "28: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
            "49: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
            "71: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
            "79: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
            "83: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
        };

        final Configuration checkConfig = getCheckConfig("EmptyCatchBlock");
        final String filePath = getPath("InputEmptyBlockCatch.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testNoViolations() throws Exception {

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getCheckConfig("EmptyCatchBlock");
        final String filePath = getPath("InputEmptyCatchBlockNoViolations.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testViolationsByComment() throws Exception {

        final String[] expected = {
            "19: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
            "27: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
        };

        final Configuration checkConfig = getCheckConfig("EmptyCatchBlock");
        final String filePath = getPath("InputEmptyCatchBlockViolationsByComment.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testViolationsByVariableName() throws Exception {
        final String[] expected = {
            "19: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
            "35: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
            "51: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
            "58: " + getCheckMessage(EmptyCatchBlockCheck.class, "catch.block.empty"),
        };

        final Configuration checkConfig = getCheckConfig("EmptyCatchBlock");
        final String filePath = getPath("InputEmptyCatchBlockViolationsByVariableName.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
