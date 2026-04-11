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

package com.openjdk.checkstyle.test.chapter3formatting.rule37indentation;

import org.junit.jupiter.api.Test;

import com.openjdk.checkstyle.test.base.AbstractOpenJdkModuleTestSupport;

public class IndentationTest extends AbstractOpenJdkModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/openjdk/checkstyle/test/chapter3formatting/rule37indentation";
    }

    @Test
    public void testIndentationValid() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationValid.java"));
    }

    @Test
    public void testIndentationInvalid() throws Exception {
        verifyWithWholeConfig(getPath("InputIndentationInvalid.java"));
    }

    @Test
    public void testFileTabCharacterValid() throws Exception {
        verifyWithWholeConfig(getPath("InputFileTabCharacterValid.java"));
    }

    @Test
    public void testFileTabCharacterInvalid() throws Exception {
        verifyWithWholeConfig(getPath("InputFileTabCharacterInvalid.java"));
    }

    @Test
    public void testThrowsIndentValid() throws Exception {
        verifyWithWholeConfig(getPath("InputThrowsIndentValid.java"));
    }

    @Test
    public void testThrowsIndentInvalid() throws Exception {
        verifyWithWholeConfig(getPath("InputThrowsIndentInvalid.java"));
    }

    @Test
    public void testLineWrappingIndentationValid() throws Exception {
        verifyWithWholeConfig(getPath("InputLineWrappingIndentationValid.java"));
    }

    @Test
    public void testLineWrappingIndentationInvalid() throws Exception {
        verifyWithWholeConfig(getPath("InputLineWrappingIndentationInvalid.java"));
    }

    @Test
    public void testArrayInitIndentValid() throws Exception {
        verifyWithWholeConfig(getPath("InputArrayInitIndentValid.java"));
    }

    @Test
    public void testArrayInitIndentInvalid() throws Exception {
        verifyWithWholeConfig(getPath("InputArrayInitIndentInvalid.java"));
    }

}
