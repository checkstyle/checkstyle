///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.google.checkstyle.test.chapter4formatting.rule489textblocks;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;

public class TextBlocksTest extends AbstractGoogleModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule489textblocks";
    }

    @Test
    public void testTextBlocksGeneralForm() throws Exception {
        verifyWithWholeConfig(getPath("InputTextBlocksGeneralForm.java"));
    }

    @Test
    public void testFormattedTextBlocksGeneralForm() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedTextBlocksGeneralForm.java"));
    }

    @Test
    public void testTextBlocksIndentation() throws Exception {
        verifyWithWholeConfig(getPath("InputTextBlocksIndentation.java"));
    }

    @Test
    public void testFormattedTextBlocksIndentation() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedTextBlocksIndentation.java"));
    }

    @Test
    public void testTextBlocksInAnnotation() throws Exception {
        verifyWithWholeConfig(getPath("InputTextBlocksInAnnotation.java"));
    }

    @Test
    public void testFormattedTextBlocksInAnnotation() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedTextBlocksInAnnotation.java"));
    }

    @Test
    public void testTextBlocksTernary() throws Exception {
        verifyWithWholeConfig(getPath("InputTextBlocksTernary.java"));
    }

    @Test
    public void testFormattedTextBlocksTernary() throws Exception {
        verifyWithWholeConfig(getPath("InputFormattedTextBlocksTernary.java"));
    }

}
