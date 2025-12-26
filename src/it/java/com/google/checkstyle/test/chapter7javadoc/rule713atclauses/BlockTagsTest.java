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

package com.google.checkstyle.test.chapter7javadoc.rule713atclauses;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;

public class BlockTagsTest extends AbstractGoogleModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/google/checkstyle/test/chapter7javadoc/rule713atclauses";
    }

    @Test
    void correctAtClauseOrder1() throws Exception {
        verifyWithWholeConfig(getPath("InputCorrectAtClauseOrderCheck1.java"));
    }

    @Test
    void correctAtClauseOrder2() throws Exception {
        verifyWithWholeConfig(getPath("InputCorrectAtClauseOrderCheck2.java"));
    }

    @Test
    void correctAtClauseOrder3() throws Exception {
        verifyWithWholeConfig(getPath("InputCorrectAtClauseOrderCheck3.java"));
    }

    @Test
    void incorrectAtClauseOrder1() throws Exception {
        verifyWithWholeConfig(getPath("InputIncorrectAtClauseOrderCheck1.java"));
    }

    @Test
    void incorrectAtClauseOrder2() throws Exception {
        verifyWithWholeConfig(getPath("InputIncorrectAtClauseOrderCheck2.java"));
    }

    @Test
    void incorrectAtClauseOrder3() throws Exception {
        verifyWithWholeConfig(getPath("InputIncorrectAtClauseOrderCheck3.java"));
    }

    @Test
    void javadocTagContinuationIndentation() throws Exception {
        verifyWithWholeConfig(getPath("InputJavaDocTagContinuationIndentation.java"));
    }

    @Test
    void javadocTagContinuationIndentationFormatted() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedJavaDocTagContinuationIndentation.java"));
    }

    @Test
    void nonEmptyAtclauseDescription() throws Exception {
        verifyWithWholeConfig(getPath("InputNonEmptyAtclauseDescription.java"));
    }

    @Test
    void nonEmptyAtclauseDescriptionSpaceSequence() throws Exception {
        verifyWithWholeConfig(getPath("InputNonEmptyAtclauseDescriptionSpaceSeq.java"));
    }
}
