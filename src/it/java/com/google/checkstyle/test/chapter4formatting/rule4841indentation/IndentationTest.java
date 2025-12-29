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

package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractIndentationTestSupport;

public class IndentationTest extends AbstractIndentationTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule4841indentation";
    }

    @Test
    public void correctClass() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrectClass.java"));
    }

    @Test
    public void correctField() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrectFieldAndParameter.java"));
    }

    @Test
    public void correctFor() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrectForAndParameter.java"));
    }

    @Test
    public void correctIf() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrectIfAndParameter.java"));
    }

    @Test
    public void correctNewKeyword() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrectNewChildren.java"));
    }

    @Test
    public void correct() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrect.java"));
    }

    @Test
    public void correctReturn() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrectReturnAndParameter.java"));
    }

    @Test
    public void correctWhile() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrectWhileDoWhileAndParameter.java"));
    }

    @Test
    public void correctChained() throws Exception {
        verifyWithWholeConfig(getPath("InputClassWithChainedMethodsCorrect.java"));
    }

    @Test
    public void warnChained() throws Exception {
        verifyWithWholeConfig(getPath("InputClassWithChainedMethods.java"));
    }

    @Test
    public void warnChainedFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedClassWithChainedMethods.java"));
    }

    @Test
    public void correctAnnotationArrayInit() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrectAnnotationArrayInit.java"));
    }

    @Test
    public void fastMatcher() throws Exception {
        verifyWithWholeConfig(getPath("InputFastMatcher.java"));
    }

    @Test
    public void switchOnTheStartOfTheLine() throws Exception {
        verifyWithWholeConfig(getPath("InputSwitchOnStartOfTheLine.java"));
    }

    @Test
    public void formattedSwitchOnTheStartOfTheLine() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedSwitchOnStartOfTheLine.java"));
    }

    @Test
    public void singleSwitchStatementWithoutCurly() throws Exception {
        verifyWithWholeConfig(getPath("InputSingleSwitchStatementWithoutCurly.java"));
    }

    @Test
    public void formattedSingleSwitchStatementWithoutCurly() throws Exception {
        verifyWithWholeConfig(
            getPath("InputFormattedSingleSwitchStatementWithoutCurly.java"));
    }

    @Test
    public void switchWrappingIndentation() throws Exception {
        verifyWithWholeConfig(getPath("InputSwitchWrappingIndentation.java"));
    }

    @Test
    public void formattedSwitchWrappingIndentation() throws Exception {
        verifyWithWholeConfig(
            getPath("InputFormattedSwitchWrappingIndentation.java"));
    }

    @Test
    public void multilineParameters() throws Exception {
        verifyWithWholeConfig(getPath("InputCatchParametersOnNewLine.java"));
    }

    @Test
    public void formattedMultilineParameters() throws Exception {
        verifyWithWholeConfig(
            getPath("InputFormattedCatchParametersOnNewLine.java"));
    }

    @Test
    public void newKeywordChildren() throws Exception {
        verifyWithWholeConfig(getPath("InputNewKeywordChildren.java"));
    }

    @Test
    public void formattedNewKeywordChildren() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedNewKeywordChildren.java"));
    }

    @Test
    public void lambdaChild() throws Exception {
        verifyWithWholeConfig(getPath("InputLambdaChild.java"));
    }

    @Test
    public void formattedLambdaChild() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedLambdaChild.java"));
    }

    @Test
    public void lambdaAndChildOnTheSameLine() throws Exception {
        verifyWithWholeConfig(getPath("InputLambdaAndChildOnTheSameLine.java"));
    }

    @Test
    public void formattedLambdaAndChildOnTheSameLine() throws Exception {
        verifyWithWholeConfig(
            getPath("InputFormattedLambdaAndChildOnTheSameLine.java"));
    }

    @Test
    public void annotationArrayInitMultiline1() throws Exception {
        verifyWithWholeConfig(getPath("InputAnnotationArrayInitMultiline.java"));
    }

    @Test
    public void formattedAnnotationArrayInitMultiline1() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedAnnotationArrayInitMultiline.java"));
    }

    @Test
    public void annotationArrayInitMultiline2() throws Exception {
        verifyWithWholeConfig(getPath("InputAnnotationArrayInitMultiline2.java"));
    }

    @Test
    public void formattedAnnotationArrayInitMultiline2() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedAnnotationArrayInitMultiline2.java"));
    }

    @Test
    public void lineBreakAfterLeftCurlyOfBlockInSwitch() throws Exception {
        verifyWithWholeConfig(getPath("InputLineBreakAfterLeftCurlyOfBlockInSwitch.java"));
    }

    @Test
    public void formattedLineBreakAfterLeftCurlyOfBlockInSwitch() throws Exception {
        verifyWithWholeConfig(
            getPath("InputFormattedLineBreakAfterLeftCurlyOfBlockInSwitch.java"));
    }
}
