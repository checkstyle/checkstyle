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

package com.google.checkstyle.test.chapter4formatting.rule413emptyblocks;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;

public class EmptyBlocksTest extends AbstractGoogleModuleTestSupport {

    private static final String[] MODULES = {
        "EmptyBlock",
        "EmptyCatchBlock",
    };

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule413emptyblocks";
    }

    @Test
    public void testEmptyBlocksAndCatchBlocks() throws Exception {
        final String filePath = getPath("InputEmptyBlocksAndCatchBlocks.java");

        verifyWithGoogleConfigParser(MODULES, filePath);
    }

    @Test
    public void testEmptyFinallyBlocks() throws Exception {
        final String filePath = getPath("InputEmptyFinallyBlocks.java");

        verifyWithGoogleConfigParser(MODULES, filePath);
    }

    @Test
    public void testEmptyCatchBlockNoViolations() throws Exception {
        final String filePath = getPath("InputEmptyBlocksAndCatchBlocksNoViolations.java");

        verifyWithGoogleConfigParser(MODULES, filePath);
    }

    @Test
    public void testEmptyCatchBlockViolationsByComment() throws Exception {
        final String filePath = getPath("InputEmptyCatchBlockViolationsByComment.java");

        verifyWithGoogleConfigParser(MODULES, filePath);
    }

    @Test
    public void testEmptyCatchBlockViolationsByVariableName() throws Exception {
        final String filePath = getPath("InputEmptyCatchBlockViolationsByVariableName.java");

        verifyWithGoogleConfigParser(MODULES, filePath);
    }
}
