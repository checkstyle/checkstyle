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

public class MethodReferencesTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar/java8";
    }

    @Test
    public void testCanParse()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMethodReferences.java"), expected);
    }

    @Test
    public void testFromSpec()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMethodReferences2.java"), expected);
    }

    @Test
    public void testGenericInPostfixExpressionBeforeReference()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMethodReferences3.java"), expected);
    }

    @Test
    public void testArrayAfterGeneric()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMethodReferences4.java"), expected);
    }

    @Test
    public void testFromHibernate()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMethodReferences5.java"), expected);
    }

    @Test
    public void testFromSpring()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMethodReferences6.java"), expected);
    }

    @Test
    public void testMethodReferences7()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMethodReferences7.java"), expected);
    }

}
