///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;

public class WhereToBreakTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule451wheretobreak";
    }

    @Test
    public void testOperatorWrap() throws Exception {
        verifyWithWholeConfig(getPath("InputOperatorWrap.java"));
    }

    @Test
    public void testOperatorWrapFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedOperatorWrap.java"));
    }

    @Test
    public void testMethodParamPad() throws Exception {
        verifyWithWholeConfig(getPath("InputMethodParamPad.java"));
    }

    @Test
    public void testMethodParamPadFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedMethodParamPad.java"));
    }

    @Test
    public void testSeparatorWrapDot() throws Exception {
        verifyWithWholeConfig(getPath("InputSeparatorWrap.java"));
    }

    @Test
    public void testSeparatorWrapDotFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedSeparatorWrap.java"));
    }

    @Test
    public void testSeparatorWrapComma() throws Exception {
        verifyWithWholeConfig(getPath("InputSeparatorWrapComma.java"));
    }

    @Test
    public void testSeparatorWrapCommaFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedSeparatorWrapComma.java"));
    }

    @Test
    public void testSeparatorWrapMethodRef() throws Exception {
        verifyWithWholeConfig(getPath("InputSeparatorWrapMethodRef.java"));
    }

    @Test
    public void testSeparatorWrapMethodRefFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedSeparatorWrapMethodRef.java"));
    }

    @Test
    public void testEllipsis() throws Exception {
        verifyWithWholeConfig(getPath("InputSeparatorWrapEllipsis.java"));
    }

    @Test
    public void testEllipsisFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedSeparatorWrapEllipsis.java"));
    }

    @Test
    public void testArrayDeclarator() throws Exception {
        verifyWithWholeConfig(getPath("InputSeparatorWrapArrayDeclarator.java"));
    }

    @Test
    public void testArrayDeclaratorFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedSeparatorWrapArrayDeclarator.java"));
    }

    @Test
    public void testLambdaBodyWrap() throws Exception {
        verifyWithWholeConfig(getPath("InputLambdaBodyWrap.java"));
    }

    @Test
    public void testLambdaBodyWrapFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedLambdaBodyWrap.java"));
    }
}
