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

package com.google.checkstyle.test.chapter4formatting.rule413emptyblocks;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;

public class EmptyBlocksMayBeConciseTest extends AbstractGoogleModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule413emptyblocks";
    }

    @Test
    void emptyBlocksAndCatchBlocks() throws Exception {
        verifyWithWholeConfig(getPath("InputEmptyBlocksAndCatchBlocks.java"));
    }

    @Test
    void emptyBlocksAndCatchBlocksFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedEmptyBlocksAndCatchBlocks.java"));
    }

    @Test
    void emptyFinallyBlocks() throws Exception {
        verifyWithWholeConfig(getPath("InputEmptyFinallyBlocks.java"));
    }

    @Test
    void emptyCatchBlockNoViolations() throws Exception {
        verifyWithWholeConfig(getPath("InputEmptyBlocksAndCatchBlocksNoViolations.java"));
    }

    @Test
    void emptyCatchBlockViolationsByComment() throws Exception {
        verifyWithWholeConfig(getPath("InputEmptyCatchBlockViolationsByComment.java"));
    }

    @Test
    void emptyCatchBlockViolationsByVariableName() throws Exception {
        verifyWithWholeConfig(getPath("InputEmptyCatchBlockViolationsByVariableName.java"));
    }

    @Test
    void emptyCatchEmptyComment() throws Exception {
        verifyWithWholeConfig(getPath("InputEmptyCatchEmptyComment.java"));
    }

    @Test
    void formattedEmptyCatchEmptyComment() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedEmptyCatchEmptyComment.java"));
    }
}
