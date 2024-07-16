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

package com.google.checkstyle.test.chapter7javadoc.rule713blocktags;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;

public class BlockTagsTest extends AbstractGoogleModuleTestSupport {

    private static final String[] MODULES = {
        "AtclauseOrder",
        "JavadocTagContinuationIndentation",
        "NonEmptyAtclauseDescription",
    };

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter7javadoc/rule713blocktags";
    }

    @Test
    public void testCorrectAtClauseOrder1() throws Exception {
        final String filePath = getPath("InputCorrectAtClauseOrderCheck1.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testCorrectAtClauseOrder2() throws Exception {
        final String filePath = getPath("InputCorrectAtClauseOrderCheck2.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testCorrectAtClauseOrder3() throws Exception {
        final String filePath = getPath("InputCorrectAtClauseOrderCheck3.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testIncorrectAtClauseOrder1() throws Exception {
        final String filePath = getPath("InputIncorrectAtClauseOrderCheck1.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testIncorrectAtClauseOrder2() throws Exception {
        final String filePath = getPath("InputIncorrectAtClauseOrderCheck2.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testIncorrectAtClauseOrder3() throws Exception {
        final String filePath = getPath("InputIncorrectAtClauseOrderCheck3.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testJavadocTagContinuationIndentation() throws Exception {
        final String filePath = getPath("InputJavaDocTagContinuationIndentation.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testNonEmptyAtclauseDescription() throws Exception {
        final String filePath = getPath("InputNonEmptyAtclauseDescription.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testNonEmptyAtclauseDescriptionSpaceSequence() throws Exception {
        final String filePath = getPath("InputNonEmptyAtclauseDescriptionSpaceSeq.java");
        verifyWithConfigParser(MODULES, filePath);
    }
}
