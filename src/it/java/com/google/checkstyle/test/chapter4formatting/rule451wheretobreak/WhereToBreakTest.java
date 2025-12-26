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
    void operatorWrap() throws Exception {
        verifyWithWholeConfig(getPath("InputOperatorWrap.java"));
    }

    @Test
    void operatorWrapFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedOperatorWrap.java"));
    }

    @Test
    void methodParamPad() throws Exception {
        verifyWithWholeConfig(getPath("InputMethodParamPad.java"));
    }

    @Test
    void methodParamPadFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedMethodParamPad.java"));
    }

    @Test
    void separatorWrapDot() throws Exception {
        verifyWithWholeConfig(getPath("InputSeparatorWrap.java"));
    }

    @Test
    void separatorWrapDotFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedSeparatorWrap.java"));
    }

    @Test
    void separatorWrapComma() throws Exception {
        verifyWithWholeConfig(getPath("InputSeparatorWrapComma.java"));
    }

    @Test
    void separatorWrapCommaFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedSeparatorWrapComma.java"));
    }

    @Test
    void separatorWrapMethodRef() throws Exception {
        verifyWithWholeConfig(getPath("InputSeparatorWrapMethodRef.java"));
    }

    @Test
    void separatorWrapMethodRefFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedSeparatorWrapMethodRef.java"));
    }

    @Test
    void ellipsis() throws Exception {
        verifyWithWholeConfig(getPath("InputSeparatorWrapEllipsis.java"));
    }

    @Test
    void ellipsisFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedSeparatorWrapEllipsis.java"));
    }

    @Test
    void arrayDeclarator() throws Exception {
        verifyWithWholeConfig(getPath("InputSeparatorWrapArrayDeclarator.java"));
    }

    @Test
    void arrayDeclaratorFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedSeparatorWrapArrayDeclarator.java"));
    }

    @Test
    void lambdaBodyWrap() throws Exception {
        verifyWithWholeConfig(getPath("InputLambdaBodyWrap.java"));
    }

    @Test
    void lambdaBodyWrapFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedLambdaBodyWrap.java"));
    }

    @Test
    void illegalLineBreakAroundLambda() throws Exception {
        verifyWithWholeConfig(getPath("InputIllegalLineBreakAroundLambda.java"));
    }

    @Test
    void formattedIllegalLineBreakAroundLambda() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedIllegalLineBreakAroundLambda.java"));
    }

    @Test
    void noWrappingAfterRecordName1() throws Exception {
        verifyWithWholeConfig(getPath("InputNoWrappingAfterRecordName.java"));
    }

    @Test
    void noWrappingAfterRecordNameFormatted1() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedNoWrappingAfterRecordName.java"));
    }

    @Test
    void noWrappingAfterRecordName2() throws Exception {
        verifyWithWholeConfig(getPath("InputNoWrappingAfterRecordName2.java"));
    }

    @Test
    void noWrappingAfterRecordNameFormatted2() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedNoWrappingAfterRecordName2.java"));
    }
}
