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

package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

import static com.puppycrawl.tools.checkstyle.checks.indentation.IndentationCheck.MSG_CHILD_ERROR;
import static com.puppycrawl.tools.checkstyle.checks.indentation.IndentationCheck.MSG_ERROR;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractIndentationTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.indentation.IndentationCheck;
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
    public void testCorrectNewKeyword() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getModuleConfig("Indentation");
        final String filePath = getPath("InputIndentationCorrectNewChildren.java");

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

    @Test
    public void testCorrectChained() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getModuleConfig("Indentation");
        final String filePath = getPath("ClassWithChainedMethodsCorrect.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testWarnChained() throws Exception {
        final String[] expected = {
            "18:5: " + getCheckMessage(IndentationCheck.class,
                    MSG_CHILD_ERROR, "method call", 4, 8),
            "23:5: " + getCheckMessage(IndentationCheck.class, MSG_ERROR, ".", 4, 8),
            "24:5: " + getCheckMessage(IndentationCheck.class, MSG_ERROR, ".", 4, 8),
            "27:5: " + getCheckMessage(IndentationCheck.class, MSG_ERROR, "new", 4, 8),
        };

        final Configuration checkConfig = getModuleConfig("Indentation");
        final String filePath = getPath("ClassWithChainedMethods.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testCorrectAnnotationArrayInit() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getModuleConfig("Indentation");
        final String filePath = getPath("InputIndentationCorrectAnnotationArrayInit.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
