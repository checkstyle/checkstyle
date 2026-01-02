///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractIndentationTestSupport;

public class IndentationTest extends AbstractIndentationTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule4841indentation";
    }

    @Test
    public void testCorrectClass() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrectClass.java"));
    }

    @Test
    public void testCorrectField() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrectFieldAndParameter.java"));
    }

    @Test
    public void testCorrectFor() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrectForAndParameter.java"));
    }

    @Test
    public void testCorrectIf() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrectIfAndParameter.java"));
    }

    @Test
    public void testCorrectNewKeyword() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrectNewChildren.java"));
    }

    @Test
    public void testCorrect() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrect.java"));
    }

    @Test
    public void testCorrectReturn() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrectReturnAndParameter.java"));
    }

    @Test
    public void testCorrectWhile() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrectWhileDoWhileAndParameter.java"));
    }

    @Test
    public void testCorrectChained() throws Exception {
        verifyWithWholeConfig(getPath("InputClassWithChainedMethodsCorrect.java"));
    }

    @Test
    public void testWarnChained() throws Exception {
        verifyWithWholeConfig(getPath("InputClassWithChainedMethods.java"));
    }

    @Test
    public void testWarnChainedFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedClassWithChainedMethods.java"));
    }

    @Test
    public void testCorrectAnnotationArrayInit() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrectAnnotationArrayInit.java"));
    }

    @Test
    public void testFastMatcher() throws Exception {
        verifyWithWholeConfig(getPath("InputFastMatcher.java"));
    }

    @Test
    public void testSwitchOnTheStartOfTheLine() throws Exception {
        verifyWithWholeConfig(getPath("InputSwitchOnStartOfTheLine.java"));
    }

    @Test
    public void testFormattedSwitchOnTheStartOfTheLine() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedSwitchOnStartOfTheLine.java"));
    }

    @Test
    public void testSingleSwitchStatementWithoutCurly() throws Exception {
        verifyWithWholeConfig(getPath("InputSingleSwitchStatementWithoutCurly.java"));
    }

    @Test
    public void testFormattedSingleSwitchStatementWithoutCurly() throws Exception {
        verifyWithWholeConfig(
            getPath("InputFormattedSingleSwitchStatementWithoutCurly.java"));
    }

    @Test
    public void testSwitchWrappingIndentation() throws Exception {
        verifyWithWholeConfig(getPath("InputSwitchWrappingIndentation.java"));
    }

    @Test
    public void testFormattedSwitchWrappingIndentation() throws Exception {
        verifyWithWholeConfig(
            getPath("InputFormattedSwitchWrappingIndentation.java"));
    }

    @Test
    public void testMultilineParameters() throws Exception {
        verifyWithWholeConfig(getPath("InputCatchParametersOnNewLine.java"));
    }

    @Test
    public void testFormattedMultilineParameters() throws Exception {
        verifyWithWholeConfig(
            getPath("InputFormattedCatchParametersOnNewLine.java"));
    }

    @Test
    public void testNewKeywordChildren() throws Exception {
        verifyWithWholeConfig(getPath("InputNewKeywordChildren.java"));
    }

    @Test
    public void testFormattedNewKeywordChildren() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedNewKeywordChildren.java"));
    }

    @Test
    public void testLambdaChild() throws Exception {
        verifyWithWholeConfig(getPath("InputLambdaChild.java"));
    }

    @Test
    public void testFormattedLambdaChild() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedLambdaChild.java"));
    }

    @Test
    public void testLambdaAndChildOnTheSameLine() throws Exception {
        verifyWithWholeConfig(getPath("InputLambdaAndChildOnTheSameLine.java"));
    }

    @Test
    public void testFormattedLambdaAndChildOnTheSameLine() throws Exception {
        verifyWithWholeConfig(
            getPath("InputFormattedLambdaAndChildOnTheSameLine.java"));
    }

    @Test
    public void testAnnotationArrayInitMultiline1() throws Exception {
        verifyWithWholeConfig(getPath("InputAnnotationArrayInitMultiline.java"));
    }

    @Test
    public void testFormattedAnnotationArrayInitMultiline1() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedAnnotationArrayInitMultiline.java"));
    }

    @Test
    public void testAnnotationArrayInitMultiline2() throws Exception {
        verifyWithWholeConfig(getPath("InputAnnotationArrayInitMultiline2.java"));
    }

    @Test
    public void testFormattedAnnotationArrayInitMultiline2() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedAnnotationArrayInitMultiline2.java"));
    }

    @Test
    public void testLineBreakAfterLeftCurlyOfBlockInSwitch() throws Exception {
        verifyWithWholeConfig(getPath("InputLineBreakAfterLeftCurlyOfBlockInSwitch.java"));
    }

    @Test
    public void testFormattedLineBreakAfterLeftCurlyOfBlockInSwitch() throws Exception {
        verifyWithWholeConfig(
            getPath("InputFormattedLineBreakAfterLeftCurlyOfBlockInSwitch.java"));
    }
}
