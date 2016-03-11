////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.grammars.java8;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class LambdaTest extends BaseCheckTestSupport {
    @Override
    protected String getNonCompilablePath(String filename) throws IOException {
        return super.getNonCompilablePath("grammars" + File.separator
                + "java8" + File.separator + filename);
    }

    @Test
    public void testLambdaInVariableInitialization()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputLambda1.java"), expected);

    }

    @Test
    public void testWithoutArgsOneLineLambdaBody()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputLambda2.java"), expected);

    }

    @Test
    public void testWithoutArgsFullLambdaBody()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputLambda3.java"), expected);

    }

    @Test
    public void testWithOneArgWithOneLineBody()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputLambda4.java"), expected);

    }

    @Test
    public void testWithOneArgWithFullBody()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputLambda5.java"), expected);

    }

    @Test
    public void testWithOneArgWithoutTypeOneLineBody()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputLambda6.java"), expected);

    }

    @Test
    public void testWithOneArgWithoutTypeFullBody()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputLambda7.java"), expected);

    }

    @Test
    public void testWithFewArgsWithoutTypeOneLineBody()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputLambda8.java"), expected);

    }

    @Test
    public void testWithFewArgsWithoutTypeFullBody()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputLambda9.java"), expected);

    }

    @Test
    public void testWithOneArgWithoutParenthesesWithoutTypeOneLineBody()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputLambda10.java"), expected);

    }

    @Test
    public void testWithOneArgWithoutParenthesesWithoutTypeFullBody()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputLambda11.java"), expected);

    }

    @Test
    public void testWithFewArgWIthTypeOneLine()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputLambda12.java"), expected);

    }

    @Test
    public void testWithFewArgWithTypeFullBody()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputLambda13.java"), expected);

    }

    @Test
    public void testWIthMultilineBody()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputLambda14.java"), expected);

    }

    @Test
    public void testCasesFromSpec()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputLambda15.java"), expected);

    }

    @Test
    public void testWithTypecast()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputLambda16.java"), expected);

    }

    @Test
    public void testInAssignment()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputLambda17.java"), expected);

    }

    @Test
    public void testInTernary()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputLambda18.java"), expected);

    }

}
