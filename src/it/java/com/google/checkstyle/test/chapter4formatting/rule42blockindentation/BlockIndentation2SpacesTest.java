///
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
///

package com.google.checkstyle.test.chapter4formatting.rule42blockindentation;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractIndentationTestSupport;

public class BlockIndentation2SpacesTest extends AbstractIndentationTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule42blockindentation";
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
        verifyWithWholeConfig(getPath("ClassWithChainedMethodsCorrect.java"));
    }

    @Test
    public void testWarnChained() throws Exception {
        verifyWithWholeConfig(getPath("ClassWithChainedMethods.java"));
    }

    @Test
    public void testCorrectAnnotationArrayInit() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCorrectAnnotationArrayInit.java"));
    }

    @Test
    public void testCodeBlocks() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationCodeBlocks.java"));
    }

    @Test
    public void testCodeBlocksFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedIndentationCodeBlocks.java"));
    }

    @Test
    public void testFastMatcher() throws Exception {
        verifyWithWholeConfig(getPath("InputFastMatcher.java"));
    }

}
