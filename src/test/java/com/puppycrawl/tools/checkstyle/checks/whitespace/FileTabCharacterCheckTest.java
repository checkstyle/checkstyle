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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck.MSG_CONTAINS_TAB;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck.MSG_FILE_CONTAINS_TAB;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.FileText;

public class FileTabCharacterCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/filetabcharacter";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "22:25: " + getCheckMessage(MSG_FILE_CONTAINS_TAB),
        };
        verifyWithInlineConfigParser(
                getPath("InputFileTabCharacterSimple.java"),
            expected);
    }

    @Test
    public void testCustomMessage() throws Exception {
        final String msgFileContainsTab =
            "File contains tab characters (this is the first instance) :)";
        final String[] expected = {
            "14:25: " + msgFileContainsTab,
        };
        verifyWithInlineConfigParser(
            getPath("InputFileTabCharacterCustomMessage.java"),
            expected);
    }

    @Test
    public void testVerbose() throws Exception {
        final String[] expected = {
            "22:25: " + getCheckMessage(MSG_CONTAINS_TAB),
            "148:35: " + getCheckMessage(MSG_CONTAINS_TAB),
            "151:28: " + getCheckMessage(MSG_CONTAINS_TAB),
            "159:9: " + getCheckMessage(MSG_CONTAINS_TAB),
            "160:10: " + getCheckMessage(MSG_CONTAINS_TAB),
            "161:1: " + getCheckMessage(MSG_CONTAINS_TAB),
            "162:3: " + getCheckMessage(MSG_CONTAINS_TAB),
            "163:3: " + getCheckMessage(MSG_CONTAINS_TAB),
        };
        verifyWithInlineConfigParser(
                getPath("InputFileTabCharacterSimple1.java"),
            expected);
    }

    @Test
    public void testFileExtensionsDefault() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_CONTAINS_TAB),
        };
        verifyWithInlineConfigParser(
                getPath("InputFileTabCharacterExtensionsDefault.java"), expected);
    }

    @Test
    public void testFileExtensionsEmpty() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_CONTAINS_TAB),
        };
        verifyWithInlineConfigParser(
                getPath("InputFileTabCharacterExtensionsEmpty.java"), expected);
    }

    @Test
    public void testFileExtensionsJava() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_CONTAINS_TAB),
        };
        verifyWithInlineConfigParser(
                getPath("InputFileTabCharacterExtensionsJava.java"), expected);
    }

    @Test
    public void testFileExtensionsDot() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_CONTAINS_TAB),
        };
        verifyWithInlineConfigParser(
                getPath("InputFileTabCharacterExtensionsDot.java"), expected);
    }

    @Test
    public void testFileExtensionsMultiple() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_CONTAINS_TAB),
        };
        verifyWithInlineConfigParser(
                getPath("InputFileTabCharacterExtensionsMultiple.java"), expected);
    }

    @Test
    public void testFileExtensionsNonMatching() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputFileTabCharacterExtensionsNonMatching.java"), expected);
    }

    @Test
    public void testFileExtensionsCase() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputFileTabCharacterExtensionsCase.java"), expected);
    }

    @Test
    public void testFileExtensionsCompound() throws Exception {
        final String[] expected = {
            "9:1: " + getCheckMessage(MSG_CONTAINS_TAB),
        };
        verifyWithInlineConfigParser(
                getPath("InputFileTabCharacterExtensionsCompound.java.txt"), expected);
    }

    @Test
    public void testFileExtensionsCompoundMismatch() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputFileTabCharacterExtensionsCompoundMismatch.txt"), expected);
    }

    @Test
    public void testFileExtensionsAfterFailedSetter() throws Exception {
        final FileTabCharacterCheck check = new FileTabCharacterCheck();
        check.configure(createModuleConfig(FileTabCharacterCheck.class));
        getExpectedThrowable(NullPointerException.class,
                () -> check.setFileExtensions("java", null));
        final File file = new File(getPath("InputFileTabCharacterExtensionsJava.java"));
        final FileText fileText = new FileText(file, StandardCharsets.UTF_8.name());
        final NullPointerException exception = getExpectedThrowable(NullPointerException.class,
                () -> check.process(file, fileText));
        assertWithMessage("A matching extension must not hide the later null after a failed setter")
                .that(exception.getClass())
                .isEqualTo(NullPointerException.class);
    }

}
