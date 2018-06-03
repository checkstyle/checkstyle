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

package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

import org.junit.Test;

import com.google.checkstyle.test.base.AbstractIndentationTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class IndentationTest extends AbstractIndentationTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule4841indentation";
    }

    @Test
    public void testCorrectClass() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getModuleConfig("Indentation");
        final String filePath = getPath("InputIndentationCorrectClass.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testCorrectField() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getModuleConfig("Indentation");
        final String filePath = getPath("InputIndentationCorrectFieldAndParameter.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testCorrectFor() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getModuleConfig("Indentation");
        final String filePath = getPath("InputIndentationCorrectForAndParameter.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testCorrectIf() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getModuleConfig("Indentation");
        final String filePath = getPath("InputIndentationCorrectIfAndParameter.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testCorrect() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getModuleConfig("Indentation");
        final String filePath = getPath("InputIndentationCorrect.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testCorrectReturn() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getModuleConfig("Indentation");
        final String filePath = getPath("InputIndentationCorrectReturnAndParameter.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testCorrectWhile() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getModuleConfig("Indentation");
        final String filePath = getPath("InputIndentationCorrectWhileDoWhileAndParameter.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
