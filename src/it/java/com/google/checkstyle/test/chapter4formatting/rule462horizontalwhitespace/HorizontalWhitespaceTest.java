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
    void whitespaceAroundBasic() throws Exception {
        verifyWithWholeConfig(getPath("InputWhitespaceAroundBasic.java"));
    }

    @Test
    void whitespaceAroundBasicFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedWhitespaceAroundBasic.java"));
    }

    @Test
    void whitespaceAroundEmptyTypesCycles() throws Exception {
        verifyWithWholeConfig(getPath("InputWhitespaceAroundEmptyTypesAndCycles.java"));
    }

    @Test
    void whitespaceAroundEmptyTypesCyclesFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedWhitespaceAroundEmptyTypesAndCycles.java"));
    }

    @Test
    void whitespaceAfterBad() throws Exception {
        verifyWithWholeConfig(getPath("InputWhitespaceAfterBad.java"));
    }

    @Test
    void whitespaceAfterBadFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedWhitespaceAfterBad.java"));
    }

    @Test
    void whitespaceAfterGood() throws Exception {
        verifyWithWholeConfig(getPath("InputWhitespaceAfterGood.java"));
    }

    @Test
    void whitespaceAfterGoodFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedWhitespaceAfterGood.java"));
    }

    @Test
    void parenPad() throws Exception {
        verifyWithWholeConfig(getPath("InputParenPad.java"));
    }

    @Test
    void parenPadFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedParenPad.java"));
    }

    @Test
    void noWhitespaceBeforeEmptyForLoop() throws Exception {
        verifyWithWholeConfig(getPath("InputNoWhitespaceBeforeEmptyForLoop.java"));
    }

    @Test
    void noWhitespaceBeforeEmptyForLoopFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedNoWhitespaceBeforeEmptyForLoop.java"));
    }

    @Test
    void noWhitespaceBeforeColonOfLabel() throws Exception {
        verifyWithWholeConfig(getPath("InputNoWhitespaceBeforeColonOfLabel.java"));
    }

    @Test
    void noWhitespaceBeforeColonOfLabelFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedNoWhitespaceBeforeColonOfLabel.java"));
    }

    @Test
    void noWhitespaceBeforeAnnotations() throws Exception {
        verifyWithWholeConfig(getPath("InputNoWhitespaceBeforeAnnotations.java"));
    }

    @Test
    void noWhitespaceBeforeAnnotationsFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedNoWhitespaceBeforeAnnotations.java"));
    }

    @Test
    void noWhitespaceBeforeEllipsis() throws Exception {
        verifyWithWholeConfig(getPath("InputNoWhitespaceBeforeEllipsis.java"));
    }

    @Test
    void noWhitespaceBeforeEllipsisFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedNoWhitespaceBeforeEllipsis.java"));
    }

    @Test
    void noWhitespaceBeforeCaseDefaultColon() throws Exception {
        verifyWithWholeConfig(getPath("InputNoWhitespaceBeforeCaseDefaultColon.java"));
    }

    @Test
    void noWhitespaceBeforeCaseDefaultColonFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedNoWhitespaceBeforeCaseDefaultColon.java"));
    }

    @Test
    void methodParamPad() throws Exception {
        verifyWithWholeConfig(getPath("InputMethodParamPad2.java"));
    }

    @Test
    void methodParamPadFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedMethodParamPad2.java"));
    }

    @Test
    void whitespaceAroundGenerics() throws Exception {
        verifyWithWholeConfig(getPath("InputWhitespaceAroundGenerics.java"));
    }

    @Test
    void whitespaceAroundGenericsFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedWhitespaceAroundGenerics.java"));
    }

    @Test
    void genericWhitespace() throws Exception {
        verifyWithWholeConfig(getPath("InputGenericWhitespace.java"));
    }

    @Test
    void genericWhitespaceFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedGenericWhitespace.java"));
    }

    @Test
    void genericEndsTheLine() throws Exception {
        verifyWithWholeConfig(getPath("InputGenericWhitespaceEndsTheLine.java"));
    }

    @Test
    void genericEndsTheLineFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedGenericWhitespaceEndsTheLine.java"));
    }

    @Test
    void whitespaceAroundWhen() throws Exception {
        verifyWithWholeConfig(getPath("InputWhitespaceAroundWhen.java"));
    }

    @Test
    void whitespaceInsideArrayInitializer() throws Exception {
        verifyWithWholeConfig(getPath("InputWhitespaceInsideArrayInitializer.java"));
    }

    @Test
    void whitespaceInsideArrayInitializerFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedWhitespaceInsideArrayInitializer.java"));
    }

    @Test
    void whitespaceAroundWhenFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedWhitespaceAroundWhen.java"));
    }

    @Test
    void whitespaceAroundArrow() throws Exception {
        verifyWithWholeConfig(getPath("InputWhitespaceAroundArrow.java"));
    }

    @Test
    void formattedWhitespaceAroundArrow() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedWhitespaceAroundArrow.java"));
    }

    @Test
    void whitespaceAfterDoubleSlashes() throws Exception {
        verifyWithWholeConfig(getPath("InputWhitespaceAfterDoubleSlashes.java"));
    }

    @Test
    void whitespaceAfterDoubleSlashesFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedWhitespaceAfterDoubleSlashes.java"));
    }

    @Test
    void whitespaceBeforeLeftCurlyOfEmptyBlocks() throws Exception {
        verifyWithWholeConfig(getPath("InputWhitespaceBeforeLeftCurlyOfEmptyBlock.java"));
    }

    @Test
    void whitespaceBeforeLeftCurlyOfEmptyBlocksFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedWhitespaceBeforeLeftCurlyOfEmptyBlock.java"));
    }
}
