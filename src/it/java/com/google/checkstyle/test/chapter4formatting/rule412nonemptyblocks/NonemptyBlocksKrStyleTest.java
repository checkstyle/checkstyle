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

package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;

public class NonemptyBlocksKrStyleTest extends AbstractGoogleModuleTestSupport {

    private static final String[] MODULES = {
        "LeftCurly",
        "RightCurlySame",
        "RightCurlyAlone",
    };

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule412nonemptyblocks";
    }

    @Test
    public void testLeftAndRightCurlyBraces() throws Exception {
        final String filePath = getPath("InputNonemptyBlocksLeftRightCurly.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testLeftCurlyAnnotations() throws Exception {
        final String filePath = getPath("InputLeftCurlyAnnotations.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testLeftCurlyMethods() throws Exception {
        final String filePath = getPath("InputLeftCurlyMethod.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testRightCurly() throws Exception {
        final String filePath = getPath("InputRightCurly.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testRightCurlyLiteralDoDefault() throws Exception {
        final String filePath = getPath("InputRightCurlyDoWhile.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testRightCurlyOther() throws Exception {
        final String filePath = getPath("InputRightCurlyOther.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testRightCurlyLiteralDo() throws Exception {
        final String filePath = getPath("InputRightCurlyDoWhile2.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testRightCurlySwitch() throws Exception {
        final String filePath = getPath("InputRightCurlySwitchCase.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testRightCurlySwitchCases() throws Exception {
        final String filePath = getPath("InputRightCurlySwitchCasesBlocks.java");
        verifyWithConfigParser(MODULES, filePath);
    }

}
