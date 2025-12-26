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

package com.google.checkstyle.test.chapter4formatting.rule4861blockcommentstyle;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;

public class BlockCommentStyleTest extends AbstractGoogleModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule4861blockcommentstyle";
    }

    @Test
    void commentIsAtTheEndOfBlock() throws Exception {
        verifyWithWholeConfig(getPath("InputCommentsIndentationCommentIsAtTheEndOfBlock.java"));
    }

    @Test
    void commentIsAtTheEndOfBlockFormatted() throws Exception {
        verifyWithWholeConfig(
                getPath("InputFormattedCommentsIndentationCommentIsAtTheEndOfBlock.java"));
    }

    @Test
    void commentIsInsideSwitchBlock() throws Exception {
        verifyWithWholeConfig(getPath("InputCommentsIndentationInSwitchBlock.java"));
    }

    @Test
    void commentIsInsideSwitchBlockFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedCommentsIndentationInSwitchBlock.java"));
    }

    @Test
    void commentIsInsideEmptyBlock() throws Exception {
        verifyWithWholeConfig(getPath("InputCommentsIndentationInEmptyBlock.java"));
    }

    @Test
    void commentIsInsideEmptyBlockFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedCommentsIndentationInEmptyBlock.java"));
    }

    @Test
    void surroundingCode() throws Exception {
        verifyWithWholeConfig(getPath("InputCommentsIndentationSurroundingCode.java"));
    }

    @Test
    void surroundingCodeFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedCommentsIndentationSurroundingCode.java"));
    }

}
