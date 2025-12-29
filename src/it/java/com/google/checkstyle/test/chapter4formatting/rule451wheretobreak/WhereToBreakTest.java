///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;

public class WhereToBreakTest extends AbstractGoogleModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule451wheretobreak";
    }

    @Test
    public void operatorWrap() throws Exception {
        verifyWithWholeConfig(getPath("InputOperatorWrap.java"));
    }

    @Test
    public void operatorWrapFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedOperatorWrap.java"));
    }

    @Test
    public void methodParamPad() throws Exception {
        verifyWithWholeConfig(getPath("InputMethodParamPad.java"));
    }

    @Test
    public void methodParamPadFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedMethodParamPad.java"));
    }

    @Test
    public void separatorWrapDot() throws Exception {
        verifyWithWholeConfig(getPath("InputSeparatorWrap.java"));
    }

    @Test
    public void separatorWrapDotFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedSeparatorWrap.java"));
    }

    @Test
    public void separatorWrapComma() throws Exception {
        verifyWithWholeConfig(getPath("InputSeparatorWrapComma.java"));
    }

    @Test
    public void separatorWrapCommaFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedSeparatorWrapComma.java"));
    }

    @Test
    public void separatorWrapMethodRef() throws Exception {
        verifyWithWholeConfig(getPath("InputSeparatorWrapMethodRef.java"));
    }

    @Test
    public void separatorWrapMethodRefFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedSeparatorWrapMethodRef.java"));
    }

    @Test
    public void ellipsis() throws Exception {
        verifyWithWholeConfig(getPath("InputSeparatorWrapEllipsis.java"));
    }

    @Test
    public void ellipsisFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedSeparatorWrapEllipsis.java"));
    }

    @Test
    public void arrayDeclarator() throws Exception {
        verifyWithWholeConfig(getPath("InputSeparatorWrapArrayDeclarator.java"));
    }

    @Test
    public void arrayDeclaratorFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedSeparatorWrapArrayDeclarator.java"));
    }

    @Test
    public void lambdaBodyWrap() throws Exception {
        verifyWithWholeConfig(getPath("InputLambdaBodyWrap.java"));
    }

    @Test
    public void lambdaBodyWrapFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedLambdaBodyWrap.java"));
    }

    @Test
    public void illegalLineBreakAroundLambda() throws Exception {
        verifyWithWholeConfig(getPath("InputIllegalLineBreakAroundLambda.java"));
    }

    @Test
    public void formattedIllegalLineBreakAroundLambda() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedIllegalLineBreakAroundLambda.java"));
    }

    @Test
    public void noWrappingAfterRecordName1() throws Exception {
        verifyWithWholeConfig(getPath("InputNoWrappingAfterRecordName.java"));
    }

    @Test
    public void noWrappingAfterRecordNameFormatted1() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedNoWrappingAfterRecordName.java"));
    }

    @Test
    public void noWrappingAfterRecordName2() throws Exception {
        verifyWithWholeConfig(getPath("InputNoWrappingAfterRecordName2.java"));
    }

    @Test
    public void noWrappingAfterRecordNameFormatted2() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedNoWrappingAfterRecordName2.java"));
    }
}
