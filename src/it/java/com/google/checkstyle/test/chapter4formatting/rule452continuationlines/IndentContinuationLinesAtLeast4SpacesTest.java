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

package com.google.checkstyle.test.chapter4formatting.rule452continuationlines;

import com.google.checkstyle.test.base.AbstractIndentationTestSupport;
import org.junit.jupiter.api.Test;

public class IndentContinuationLinesAtLeast4SpacesTest extends AbstractIndentationTestSupport {

    private static final String[] MODULES = {
        "Indentation",
    };

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule4841indentation";
    }

    @Test
    public void testCorrectClass() throws Exception {
        final String filePath = getPath("InputIndentationCorrectClass.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testCorrectField() throws Exception {
        final String filePath = getPath("InputIndentationCorrectFieldAndParameter.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testCorrectFor() throws Exception {
        final String filePath = getPath("InputIndentationCorrectForAndParameter.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testCorrectIf() throws Exception {
        final String filePath = getPath("InputIndentationCorrectIfAndParameter.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testCorrectNewKeyword() throws Exception {
        final String filePath = getPath("InputIndentationCorrectNewChildren.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testCorrect() throws Exception {
        final String filePath = getPath("InputIndentationCorrect.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testCorrectReturn() throws Exception {
        final String filePath = getPath("InputIndentationCorrectReturnAndParameter.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testCorrectWhile() throws Exception {
        final String filePath = getPath("InputIndentationCorrectWhileDoWhileAndParameter.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testCorrectChained() throws Exception {
        final String filePath = getPath("ClassWithChainedMethodsCorrect.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testWarnChained() throws Exception {
        final String filePath = getPath("ClassWithChainedMethods.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testCorrectAnnotationArrayInit() throws Exception {
        final String filePath = getPath("InputIndentationCorrectAnnotationArrayInit.java");
        verifyWithConfigParser(MODULES, filePath);
    }

}
