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

package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;

public class NonemptyBlocksKrStyleTest extends AbstractGoogleModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule412nonemptyblocks";
    }

    @Test
    public void leftAndRightCurlyBraces() throws Exception {
        verifyWithWholeConfig(getPath("InputNonemptyBlocksLeftRightCurly.java"));
    }

    @Test
    public void leftAndRightCurlyBracesFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedNonemptyBlocksLeftRightCurly.java"));
    }

    @Test
    public void leftCurlyAnnotations() throws Exception {
        verifyWithWholeConfig(getPath("InputLeftCurlyAnnotations.java"));
    }

    @Test
    public void leftCurlyAnnotationsFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedLeftCurlyAnnotations.java"));
    }

    @Test
    public void leftCurlyMethods() throws Exception {
        verifyWithWholeConfig(getPath("InputLeftCurlyMethod.java"));
    }

    @Test
    public void leftCurlyMethodsFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedLeftCurlyMethod.java"));
    }

    @Test
    public void rightCurly() throws Exception {
        verifyWithWholeConfig(getPath("InputRightCurly.java"));
    }

    @Test
    public void rightCurlyFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedRightCurly.java"));
    }

    @Test
    public void rightCurlyLiteralDoDefault() throws Exception {
        verifyWithWholeConfig(getPath("InputRightCurlyDoWhile.java"));
    }

    @Test
    public void rightCurlyLiteralDoDefaultFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedRightCurlyDoWhile.java"));
    }

    @Test
    public void rightCurlyOther() throws Exception {
        verifyWithWholeConfig(getPath("InputRightCurlyOther.java"));
    }

    @Test
    public void rightCurlyOtherFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedRightCurlyOther.java"));
    }

    @Test
    public void rightCurlyLiteralDo() throws Exception {
        verifyWithWholeConfig(getPath("InputRightCurlyDoWhile2.java"));
    }

    @Test
    public void rightCurlyLiteralDoFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedRightCurlyDoWhile2.java"));
    }

    @Test
    public void rightCurlySwitch() throws Exception {
        verifyWithWholeConfig(getPath("InputRightCurlySwitchCase.java"));
    }

    @Test
    public void rightCurlySwitchFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedRightCurlySwitchCase.java"));
    }

    @Test
    public void rightCurlySwitchCases() throws Exception {
        verifyWithWholeConfig(getPath("InputRightCurlySwitchCasesBlocks.java"));
    }

    @Test
    public void rightCurlySwitchCasesFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedRightCurlySwitchCasesBlocks.java"));
    }

    @Test
    public void tryCatchIfElse() throws Exception {
        verifyWithWholeConfig(getPath("InputTryCatchIfElse.java"));
    }

    @Test
    public void tryCatchIfElseFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedTryCatchIfElse.java"));
    }

    @Test
    public void tryCatchIfElse2() throws Exception {
        verifyWithWholeConfig(getPath("InputTryCatchIfElse2.java"));
    }

    @Test
    public void tryCatchIfElse2Formatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedTryCatchIfElse2.java"));
    }

}
