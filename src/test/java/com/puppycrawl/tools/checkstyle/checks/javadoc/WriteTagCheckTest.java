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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.WriteTagCheck.MSG_MISSING_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.WriteTagCheck.MSG_TAG_FORMAT;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.WriteTagCheck.MSG_WRITE_TAG;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Unit test for WriteTagCheck.
 */
public class WriteTagCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/writetag";
    }

    @Test
    public void testDefaultSettings() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputWriteTagDefault.java"), expected);
    }

    @Test
    public void testTag() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(MSG_WRITE_TAG, "@author", "Daniel Grenner"),
        };
        verifyWithInlineConfigParser(getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testMissingFormat() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(MSG_WRITE_TAG, "@author", "Daniel Grenner"),
        };
        verifyWithInlineConfigParser(getPath("InputWriteTagMissingFormat.java"), expected);
    }

    @Test
    public void testTagIncomplete() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                "This class needs more code..."),
        };
        verifyWithInlineConfigParser(getPath("InputWriteTagIncomplete.java"), expected);
    }

    @Test
    public void testDoubleTag() throws Exception {
        final String[] expected = {
            "18: " + getCheckMessage(MSG_WRITE_TAG, "@doubletag", "first text"),
            "19: " + getCheckMessage(MSG_WRITE_TAG, "@doubletag", "second text"),
        };
        verifyWithInlineConfigParser(getPath("InputWriteTagDoubleTag.java"), expected);
    }

    @Test
    public void testEmptyTag() throws Exception {
        final String[] expected = {
            "19: " + getCheckMessage(MSG_WRITE_TAG, "@emptytag", ""),
        };
        verifyWithInlineConfigParser(getPath("InputWriteTagEmptyTag.java"), expected);
    }

    @Test
    public void testMissingTag() throws Exception {
        final String[] expected = {
            "20: " + getCheckMessage(MSG_MISSING_TAG, "@missingtag"),
        };
        verifyWithInlineConfigParser(getPath("InputWriteTagMissingTag.java"), expected);
    }

    @Test
    public void testMethod() throws Exception {
        final String[] expected = {
            "24: " + getCheckMessage(MSG_WRITE_TAG, "@todo",
                    "Add a constructor comment"),
            "36: " + getCheckMessage(MSG_WRITE_TAG, "@todo", "Add a comment"),
        };
        verifyWithInlineConfigParser(getPath("InputWriteTagMethod.java"), expected);
    }

    @Test
    public void testSeverity() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_WRITE_TAG, "@author", "Daniel Grenner"),
        };
        verifyWithInlineConfigParser(getPath("InputWriteTagSeverity.java"), expected);
    }

    @Test
    public void testIgnoreMissing() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputWriteTagIgnore.java"), expected);
    }

    @Test
    public void testRegularEx() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputWriteTagRegularExpression.java"), expected);
    }

    @Test
    public void testRegularExError() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
        };
        verifyWithInlineConfigParser(getPath("InputWriteTagExpressionError.java"), expected);
    }

    @Test
    public void testEnumsAndAnnotations() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                    "This enum needs more code..."),
            "21: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                    "This enum constant needs more code..."),
            "28: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                    "This annotation needs more code..."),
            "33: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                    "This annotation field needs more code..."),
        };
        verifyWithInlineConfigParser(getPath("InputWriteTagEnumsAndAnnotations.java"), expected);
    }

    @Test
    public void testNoJavadocs() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputWriteTagNoJavadoc.java"), expected);
    }

    @Test
    public void testWriteTagRecordsAndCompactCtors() throws Exception {
        final String[] expected = {
            "19: " + getCheckMessage(MSG_TAG_FORMAT, "@incomplete", "\\S"),
            "26: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                    "Failed to recognize 'record' introduced in Java 14."),
            "37: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                    "Failed to recognize 'record' introduced in Java 14."),
            "48: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                    "Failed to recognize 'record' introduced in Java 14."),
            "62: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                    "Failed to recognize 'record' introduced in Java 14."),
        };
        verifyWithInlineConfigParser(
            getNonCompilablePath("InputWriteTagRecordsAndCompactCtors.java"), expected);
    }

    @Override
    protected void verify(Checker checker,
                          File[] processedFiles,
                          String messageFileName,
                          String... expected)
            throws Exception {
        getStream().flush();
        final List<File> theFiles = new ArrayList<>();
        Collections.addAll(theFiles, processedFiles);
        final int errs = checker.process(theFiles);

        // process each of the lines
        try (ByteArrayInputStream localStream =
                new ByteArrayInputStream(getStream().toByteArray());
            LineNumberReader lnr = new LineNumberReader(
                new InputStreamReader(localStream, StandardCharsets.UTF_8))) {
            for (int i = 0; i < expected.length; i++) {
                final String expectedResult = messageFileName + ":" + expected[i];
                final String actual = lnr.readLine();
                assertWithMessage("error message " + i)
                        .that(actual)
                        .isEqualTo(expectedResult);
            }

            assertWithMessage("unexpected output: " + lnr.readLine())
                    .that(errs)
                    .isAtMost(expected.length);
        }
        checker.destroy();
    }

}
