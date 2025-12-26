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

package com.google.checkstyle.test.chapter4formatting.rule452indentcontinuationlines;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractIndentationTestSupport;

public class IndentContinuationLinesAtLeast4SpacesTest extends AbstractIndentationTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule452indentcontinuationlines";
    }

    @Test
    void correctClass() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrectClass.java"));
    }

    @Test
    void correctField() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrectFieldAndParameter.java"));
    }

    @Test
    void correctFor() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrectForAndParameter.java"));
    }

    @Test
    void correctIf() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrectIfAndParameter.java"));
    }

    @Test
    void correctNewKeyword() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrectNewChildren.java"));
    }

    @Test
    void correct() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrect.java"));
    }

    @Test
    void correctReturn() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrectReturnAndParameter.java"));
    }

    @Test
    void correctWhile() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrectWhileDoWhileAndParameter.java"));
    }

    @Test
    void correctChained() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedClassWithChainedMethods2.java"));
    }

    @Test
    void warnChained() throws Exception {
        verifyWithWholeConfig(getPath("InputClassWithChainedMethods2.java"));
    }

    @Test
    void correctAnnotationArrayInit() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrectAnnotationArrayInit.java"));
    }

    @Test
    void fastMatcher() throws Exception {
        verifyWithWholeConfig(getPath("InputFastMatcher.java"));
    }
}
