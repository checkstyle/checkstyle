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

package com.puppycrawl.tools.checkstyle.grammar.java8;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class LambdaTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar/java8";
    }

    @Test
    public void testLambdaInVariableInitialization()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputLambda1.java"), expected);
    }

    @Test
    public void testWithoutArgsOneLineLambdaBody()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputLambda2.java"), expected);
    }

    @Test
    public void testWithoutArgsFullLambdaBody()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputLambda3.java"), expected);
    }

    @Test
    public void testWithOneArgWithOneLineBody()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputLambda4.java"), expected);
    }

    @Test
    public void testWithOneArgWithFullBody()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputLambda5.java"), expected);
    }

    @Test
    public void testWithOneArgWithoutTypeOneLineBody()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputLambda6.java"), expected);
    }

    @Test
    public void testWithOneArgWithoutTypeFullBody()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputLambda7.java"), expected);
    }

    @Test
    public void testWithFewArgsWithoutTypeOneLineBody()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputLambda8.java"), expected);
    }

    @Test
    public void testWithFewArgsWithoutTypeFullBody()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputLambda9.java"), expected);
    }

    @Test
    public void testWithOneArgWithoutParenthesesWithoutTypeOneLineBody()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputLambda10.java"), expected);
    }

    @Test
    public void testWithOneArgWithoutParenthesesWithoutTypeFullBody()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputLambda11.java"), expected);
    }

    @Test
    public void testWithFewArgWithTypeOneLine()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputLambda12.java"), expected);
    }

    @Test
    public void testWithFewArgWithTypeFullBody()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputLambda13.java"), expected);
    }

    @Test
    public void testWithMultilineBody()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputLambda14.java"), expected);
    }

    @Test
    public void testCasesFromSpec()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputLambda15.java"), expected);
    }

    @Test
    public void testWithTypecast()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputLambda16.java"), expected);
    }

    @Test
    public void testInAssignment()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputLambda17.java"), expected);
    }

    @Test
    public void testInTernary()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputLambda18.java"), expected);
    }

}
