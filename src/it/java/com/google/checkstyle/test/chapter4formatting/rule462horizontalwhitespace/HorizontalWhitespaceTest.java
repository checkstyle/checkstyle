///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule462horizontalwhitespace";
    }

    @Test
    public void testWhitespaceAroundBasic() throws Exception {
        verifyWithWholeConfig(getPath("InputWhitespaceAroundBasic.java"));
    }

    @Test
    public void testWhitespaceAroundEmptyTypesCycles() throws Exception {
        verifyWithWholeConfig(getPath("InputWhitespaceAroundEmptyTypesAndCycles.java"));
    }

    @Test
    public void testWhitespaceAfterBad() throws Exception {
        verifyWithWholeConfig(getPath("InputWhitespaceAfterBad.java"));
    }

    @Test
    public void testWhitespaceAfterGood() throws Exception {
        verifyWithWholeConfig(getPath("InputWhitespaceAfterGood.java"));
    }

    @Test
    public void testParenPad() throws Exception {
        verifyWithWholeConfig(getPath("InputParenPad.java"));
    }

    @Test
    public void testNoWhitespaceBeforeEmptyForLoop() throws Exception {
        verifyWithWholeConfig(getPath("InputNoWhitespaceBeforeEmptyForLoop.java"));
    }

    @Test
    public void testNoWhitespaceBeforeColonOfLabel() throws Exception {
        verifyWithWholeConfig(getPath("InputNoWhitespaceBeforeColonOfLabel.java"));
    }

    @Test
    public void testNoWhitespaceBeforeAnnotations() throws Exception {
        verifyWithWholeConfig(getPath("InputNoWhitespaceBeforeAnnotations.java"));
    }

    @Test
    public void testNoWhitespaceBeforeCaseDefaultColon() throws Exception {
        verifyWithWholeConfig(getPath("InputNoWhitespaceBeforeCaseDefaultColon.java"));
    }

    @Test
    public void testMethodParamPad() throws Exception {
        verifyWithWholeConfig(getPath("InputMethodParamPad.java"));
    }

    @Test
    public void testWhitespaceAroundGenerics() throws Exception {
        verifyWithWholeConfig(getPath("InputWhitespaceAroundGenerics.java"));
    }

    @Test
    public void testGenericWhitespace() throws Exception {
        verifyWithWholeConfig(getPath("InputGenericWhitespace.java"));
    }

    @Test
    public void genericEndsTheLine() throws Exception {
        verifyWithWholeConfig(getPath("InputGenericWhitespaceEndsTheLine.java"));
    }
}
