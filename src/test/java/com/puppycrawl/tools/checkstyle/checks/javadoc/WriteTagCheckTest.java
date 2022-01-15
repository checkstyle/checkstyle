////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

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
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
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
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputWriteTagDefault.java"), expected);
    }

    @Test
    public void testTag() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        checkConfig.addProperty("tag", "@author");
        checkConfig.addProperty("tagFormat", "\\S");
        final String[] expected = {
            "15: " + getCheckMessage(MSG_WRITE_TAG, "@author", "Daniel Grenner // violation"),
        };
        verify(checkConfig, getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testMissingFormat() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        checkConfig.addProperty("tag", "@author");
        final String[] expected = {
            "15: " + getCheckMessage(MSG_WRITE_TAG, "@author", "Daniel Grenner // violation"),
        };
        verify(checkConfig, getPath("InputWriteTagMissingFormat.java"), expected);
    }

    @Test
    public void testTagIncomplete() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        checkConfig.addProperty("tag", "@incomplete");
        checkConfig.addProperty("tagFormat", "\\S");
        final String[] expected = {
            "16: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                "This class needs more code... // violation"),
        };
        verify(checkConfig, getPath("InputWriteTagIncomplete.java"), expected);
    }

    @Test
    public void testDoubleTag() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        checkConfig.addProperty("tag", "@doubletag");
        checkConfig.addProperty("tagFormat", "\\S");
        final String[] expected = {
            "17: " + getCheckMessage(MSG_WRITE_TAG, "@doubletag", "first text  // violation"),
            "18: " + getCheckMessage(MSG_WRITE_TAG, "@doubletag", "second text  // violation"),
        };
        verify(checkConfig, getPath("InputWriteTagDoubleTag.java"), expected);
    }

    @Test
    public void testEmptyTag() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        checkConfig.addProperty("tag", "@emptytag");
        checkConfig.addProperty("tagFormat", "");
        final String[] expected = {
            "19: " + getCheckMessage(MSG_WRITE_TAG, "@emptytag", "// violation"),
        };
        verify(checkConfig, getPath("InputWriteTagEmptyTag.java"), expected);
    }

    @Test
    public void testMissingTag() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        checkConfig.addProperty("tag", "@missingtag");
        final String[] expected = {
            "20: " + getCheckMessage(MSG_MISSING_TAG, "@missingtag"),
        };
        verify(checkConfig, getPath("InputWriteTagMissingTag.java"), expected);
    }

    @Test
    public void testMethod() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        checkConfig.addProperty("tag", "@todo");
        checkConfig.addProperty("tagFormat", "\\S");
        checkConfig.addProperty("tokens",
            "INTERFACE_DEF, CLASS_DEF, METHOD_DEF, CTOR_DEF");
        checkConfig.addProperty("severity", "ignore");
        final String[] expected = {
            "24: " + getCheckMessage(MSG_WRITE_TAG, "@todo",
                    "Add a constructor comment  // violation"),
            "35: " + getCheckMessage(MSG_WRITE_TAG, "@todo", "Add a comment  // violation"),
        };
        verify(checkConfig, getPath("InputWriteTagMethod.java"), expected);
    }

    @Test
    public void testSeverity() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        checkConfig.addProperty("tag", "@author");
        checkConfig.addProperty("tagFormat", "\\S");
        checkConfig.addProperty("severity", "ignore");
        final String[] expected = {
            "15: " + getCheckMessage(MSG_WRITE_TAG, "@author", "Daniel Grenner  // violation"),
        };
        verify(checkConfig, getPath("InputWriteTagSeverity.java"), expected);
    }

    @Test
    public void testIgnoreMissing() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        checkConfig.addProperty("tag", "@todo2");
        checkConfig.addProperty("tagFormat", "\\S");
        checkConfig.addProperty("severity", "ignore");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputWriteTagIgnore.java"), expected);
    }

    @Test
    public void testRegularEx()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        checkConfig.addProperty("tag", "@author");
        checkConfig.addProperty("tagFormat", "0*");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputWriteTagRegularExpression.java"), expected);
    }

    @Test
    public void testRegularExError() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        checkConfig.addProperty("tag", "@author");
        checkConfig.addProperty("tagFormat", "ABC");
        final String[] expected = {
            "15: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
        };
        verify(checkConfig, getPath("InputWriteTagExpressionError.java"), expected);
    }

    @Test
    public void testEnumsAndAnnotations() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        checkConfig.addProperty("tag", "@incomplete");
        checkConfig.addProperty("tagFormat", ".*");
        checkConfig.addProperty("tagSeverity", "error");
        checkConfig.addProperty("tokens",
            "ANNOTATION_DEF, ENUM_DEF, ANNOTATION_FIELD_DEF, ENUM_CONSTANT_DEF");
        final String[] expected = {
            "15: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                    "This enum needs more code... // violation"),
            "19: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                "This enum constant needs more code... // violation"),
            "25: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                "This annotation needs more code... // violation"),
            "29: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                "This annotation field needs more code... // violation"),
        };
        verify(checkConfig, getPath("InputWriteTagEnumsAndAnnotations.java"), expected);
    }

    @Test
    public void testNoJavadocs() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        final String[] expected = {
            "13: " + getCheckMessage(MSG_MISSING_TAG, "null"),
        };
        verify(checkConfig, getPath("InputWriteTagNoJavadoc.java"), expected);
    }

    @Test
    public void testWriteTagRecordsAndCompactCtors() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        checkConfig.addProperty("tag", "@incomplete");
        checkConfig.addProperty("tagSeverity", "error");
        checkConfig.addProperty("tagFormat", "\\S");
        checkConfig.addProperty("tokens",
            "INTERFACE_DEF , CLASS_DEF , ENUM_DEF , ANNOTATION_DEF, RECORD_DEF,"
                + " COMPACT_CTOR_DEF, CTOR_DEF");

        final String[] expected = {
            "14: " + getCheckMessage(MSG_MISSING_TAG, "@incomplete"),
            "16: " + getCheckMessage(MSG_TAG_FORMAT, "@incomplete", "\\S"),
            "22: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete", ".// violation"),
            "28: " + getCheckMessage(MSG_MISSING_TAG, "@incomplete"),
            "31: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete", "// violation"),
            "37: " + getCheckMessage(MSG_MISSING_TAG, "@incomplete"),
            "40: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete", "// violation"),
            "47: " + getCheckMessage(MSG_MISSING_TAG, "@incomplete"),
            "48: " + getCheckMessage(MSG_MISSING_TAG, "@incomplete"),
            "51: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete", "// violation"),
        };
        verify(checkConfig,
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
