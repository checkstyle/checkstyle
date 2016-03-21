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

public class MethodReferencesTest extends BaseCheckTestSupport {
    @Override
    protected String getNonCompilablePath(String filename) throws IOException {
        return super.getNonCompilablePath("grammars" + File.separator
                + "java8" + File.separator + filename);
    }

    @Test
    public void testCanParse()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputMethodReferences.java"), expected);

    }

    @Test
    public void testFromSpec()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputMethodReferences2.java"), expected);

    }

    @Test
    public void testGenericInPostfixExpressionBeforeReference()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputMethodReferences3.java"), expected);

    }

    @Test
    public void testArrayAfterGeneric()
            throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputMethodReferences4.java"), expected);

    }

    @Test
    public void testFromHiernate()
            throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputMethodReferences5.java"), expected);

    }

    @Test
    public void testFromSpring()
            throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputMethodReferences6.java"), expected);
    }

    @Test
    public void testMethodReferences7()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputMethodReferences7.java"), expected);

    }
}
