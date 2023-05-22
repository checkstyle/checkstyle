///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.NoCodeInFileCheck.MSG_KEY_NO_CODE;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class NoCodeInFileCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/nocodeinfile";
    }

    @Test
    public void testGetRequiredTokens() {
        final NoCodeInFileCheck checkObj = new NoCodeInFileCheck();
        assertWithMessage("Required tokens array is not empty")
                .that(checkObj.getRequiredTokens())
                .isEmpty();
    }

    @Test
    public void testGetAcceptableTokens() {
        final NoCodeInFileCheck checkObj = new NoCodeInFileCheck();
        assertWithMessage("Acceptable tokens array is not empty")
                .that(checkObj.getAcceptableTokens())
                .isEmpty();
    }

    @Test
    public void testBlank() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoCodeInFileCheck.class);
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_NO_CODE),
        };
        verify(checkConfig, getPath("InputNoCodeInFile1.java"), expected);
    }

    @Test
    public void testSingleLineComment() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoCodeInFileCheck.class);
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_NO_CODE),
        };
        verify(checkConfig, getPath("InputNoCodeInFile2.java"), expected);
    }

    @Test
    public void testMultiLineComment() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_NO_CODE),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoCodeInFile3.java"), expected);
    }

    @Test
    public void testFileContainingCode() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputNoCodeInFile4.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testBothSingleLineAndMultiLineComment() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_NO_CODE),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoCodeInFile5.java"), expected);
    }
}
