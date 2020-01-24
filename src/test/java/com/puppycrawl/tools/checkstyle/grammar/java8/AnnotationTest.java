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

public class AnnotationTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar/java8";
    }

    @Test
    public void testSimpleTypeAnnotation()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAnnotations1.java"), expected);
    }

    @Test
    public void testAnnotationOnClass()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAnnotations2.java"), expected);
    }

    @Test
    public void testClassCastTypeAnnotation()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAnnotations3.java"), expected);
    }

    @Test
    public void testMethodParametersTypeAnnotation()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAnnotations4.java"), expected);
    }

    @Test
    public void testAnnotationInThrows()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAnnotations5.java"), expected);
    }

    @Test
    public void testAnnotationInGeneric()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAnnotations6.java"), expected);
    }

    @Test
    public void testAnnotationOnConstructorCall()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAnnotations7.java"), expected);
    }

    @Test
    public void testAnnotationNestedCall()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAnnotations8.java"), expected);
    }

    @Test
    public void testAnnotationOnWildcards()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAnnotations9.java"), expected);
    }

    @Test
    public void testAnnotationInCatchParameters()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAnnotations10.java"), expected);
    }

    @Test
    public void testAnnotationInTypeParameters()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAnnotations11.java"), expected);
    }

    @Test
    public void testAnnotationOnVarargs()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAnnotations12.java"), expected);
    }

}
