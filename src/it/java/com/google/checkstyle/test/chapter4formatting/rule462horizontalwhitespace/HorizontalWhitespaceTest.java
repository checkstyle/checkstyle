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

package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;

public class HorizontalWhitespaceTest extends AbstractGoogleModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule462horizontalwhitespace";
    }

    @Test
    public void whitespaceAroundBasic() throws Exception {
        verifyWithWholeConfig(getPath("InputWhitespaceAroundBasic.java"));
    }

    @Test
    public void whitespaceAroundBasicFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedWhitespaceAroundBasic.java"));
    }

    @Test
    public void whitespaceAroundEmptyTypesCycles() throws Exception {
        verifyWithWholeConfig(getPath("InputWhitespaceAroundEmptyTypesAndCycles.java"));
    }

    @Test
    public void whitespaceAroundEmptyTypesCyclesFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedWhitespaceAroundEmptyTypesAndCycles.java"));
    }

    @Test
    public void whitespaceAfterBad() throws Exception {
        verifyWithWholeConfig(getPath("InputWhitespaceAfterBad.java"));
    }

    @Test
    public void whitespaceAfterBadFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedWhitespaceAfterBad.java"));
    }

    @Test
    public void whitespaceAfterGood() throws Exception {
        verifyWithWholeConfig(getPath("InputWhitespaceAfterGood.java"));
    }

    @Test
    public void whitespaceAfterGoodFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedWhitespaceAfterGood.java"));
    }

    @Test
    public void parenPad() throws Exception {
        verifyWithWholeConfig(getPath("InputParenPad.java"));
    }

    @Test
    public void parenPadFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedParenPad.java"));
    }

    @Test
    public void noWhitespaceBeforeEmptyForLoop() throws Exception {
        verifyWithWholeConfig(getPath("InputNoWhitespaceBeforeEmptyForLoop.java"));
    }

    @Test
    public void noWhitespaceBeforeEmptyForLoopFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedNoWhitespaceBeforeEmptyForLoop.java"));
    }

    @Test
    public void noWhitespaceBeforeColonOfLabel() throws Exception {
        verifyWithWholeConfig(getPath("InputNoWhitespaceBeforeColonOfLabel.java"));
    }

    @Test
    public void noWhitespaceBeforeColonOfLabelFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedNoWhitespaceBeforeColonOfLabel.java"));
    }

    @Test
    public void noWhitespaceBeforeAnnotations() throws Exception {
        verifyWithWholeConfig(getPath("InputNoWhitespaceBeforeAnnotations.java"));
    }

    @Test
    public void noWhitespaceBeforeAnnotationsFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedNoWhitespaceBeforeAnnotations.java"));
    }

    @Test
    public void noWhitespaceBeforeEllipsis() throws Exception {
        verifyWithWholeConfig(getPath("InputNoWhitespaceBeforeEllipsis.java"));
    }

    @Test
    public void noWhitespaceBeforeEllipsisFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedNoWhitespaceBeforeEllipsis.java"));
    }

    @Test
    public void noWhitespaceBeforeCaseDefaultColon() throws Exception {
        verifyWithWholeConfig(getPath("InputNoWhitespaceBeforeCaseDefaultColon.java"));
    }

    @Test
    public void noWhitespaceBeforeCaseDefaultColonFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedNoWhitespaceBeforeCaseDefaultColon.java"));
    }

    @Test
    public void methodParamPad() throws Exception {
        verifyWithWholeConfig(getPath("InputMethodParamPad2.java"));
    }

    @Test
    public void methodParamPadFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedMethodParamPad2.java"));
    }

    @Test
    public void whitespaceAroundGenerics() throws Exception {
        verifyWithWholeConfig(getPath("InputWhitespaceAroundGenerics.java"));
    }

    @Test
    public void whitespaceAroundGenericsFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedWhitespaceAroundGenerics.java"));
    }

    @Test
    public void genericWhitespace() throws Exception {
        verifyWithWholeConfig(getPath("InputGenericWhitespace.java"));
    }

    @Test
    public void genericWhitespaceFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedGenericWhitespace.java"));
    }

    @Test
    public void genericEndsTheLine() throws Exception {
        verifyWithWholeConfig(getPath("InputGenericWhitespaceEndsTheLine.java"));
    }

    @Test
    public void genericEndsTheLineFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedGenericWhitespaceEndsTheLine.java"));
    }

    @Test
    public void whitespaceAroundWhen() throws Exception {
        verifyWithWholeConfig(getPath("InputWhitespaceAroundWhen.java"));
    }

    @Test
    public void whitespaceInsideArrayInitializer() throws Exception {
        verifyWithWholeConfig(getPath("InputWhitespaceInsideArrayInitializer.java"));
    }

    @Test
    public void whitespaceInsideArrayInitializerFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedWhitespaceInsideArrayInitializer.java"));
    }

    @Test
    public void whitespaceAroundWhenFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedWhitespaceAroundWhen.java"));
    }

    @Test
    public void whitespaceAroundArrow() throws Exception {
        verifyWithWholeConfig(getPath("InputWhitespaceAroundArrow.java"));
    }

    @Test
    public void formattedWhitespaceAroundArrow() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedWhitespaceAroundArrow.java"));
    }

    @Test
    public void whitespaceAfterDoubleSlashes() throws Exception {
        verifyWithWholeConfig(getPath("InputWhitespaceAfterDoubleSlashes.java"));
    }

    @Test
    public void whitespaceAfterDoubleSlashesFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedWhitespaceAfterDoubleSlashes.java"));
    }

    @Test
    public void whitespaceBeforeLeftCurlyOfEmptyBlocks() throws Exception {
        verifyWithWholeConfig(getPath("InputWhitespaceBeforeLeftCurlyOfEmptyBlock.java"));
    }

    @Test
    public void whitespaceBeforeLeftCurlyOfEmptyBlocksFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedWhitespaceBeforeLeftCurlyOfEmptyBlock.java"));
    }
}
