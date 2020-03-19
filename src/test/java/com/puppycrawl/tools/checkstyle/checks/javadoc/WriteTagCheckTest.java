////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.WriteTagCheck.MSG_MISSING_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.WriteTagCheck.MSG_TAG_FORMAT;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.WriteTagCheck.MSG_WRITE_TAG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        verify(checkConfig, getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testTag() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        checkConfig.addAttribute("tag", "@author");
        checkConfig.addAttribute("tagFormat", "\\S");
        final String[] expected = {
            "10: " + getCheckMessage(MSG_WRITE_TAG, "@author", "Daniel Grenner"),
        };
        verify(checkConfig, getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testMissingFormat() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        checkConfig.addAttribute("tag", "@author");
        final String[] expected = {
            "10: " + getCheckMessage(MSG_WRITE_TAG, "@author", "Daniel Grenner"),
        };
        verify(checkConfig, getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testTagIncomplete() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        checkConfig.addAttribute("tag", "@incomplete");
        checkConfig.addAttribute("tagFormat", "\\S");
        final String[] expected = {
            "11: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                "This class needs more code..."),
        };
        verify(checkConfig, getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testDoubleTag() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        checkConfig.addAttribute("tag", "@doubletag");
        checkConfig.addAttribute("tagFormat", "\\S");
        final String[] expected = {
            "12: " + getCheckMessage(MSG_WRITE_TAG, "@doubletag", "first text"),
            "13: " + getCheckMessage(MSG_WRITE_TAG, "@doubletag", "second text"),
        };
        verify(checkConfig, getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testEmptyTag() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        checkConfig.addAttribute("tag", "@emptytag");
        checkConfig.addAttribute("tagFormat", "");
        final String[] expected = {
            "14: " + getCheckMessage(MSG_WRITE_TAG, "@emptytag", ""),
        };
        verify(checkConfig, getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testMissingTag() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        checkConfig.addAttribute("tag", "@missingtag");
        final String[] expected = {
            "16: " + getCheckMessage(MSG_MISSING_TAG, "@missingtag"),
        };
        verify(checkConfig, getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testMethod() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        checkConfig.addAttribute("tag", "@todo");
        checkConfig.addAttribute("tagFormat", "\\S");
        checkConfig.addAttribute("tokens",
            "INTERFACE_DEF, CLASS_DEF, METHOD_DEF, CTOR_DEF");
        checkConfig.addAttribute("severity", "ignore");
        final String[] expected = {
            "19: " + getCheckMessage(MSG_WRITE_TAG, "@todo", "Add a constructor comment"),
            "30: " + getCheckMessage(MSG_WRITE_TAG, "@todo", "Add a comment"),
        };
        verify(checkConfig, getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testSeverity() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        checkConfig.addAttribute("tag", "@author");
        checkConfig.addAttribute("tagFormat", "\\S");
        checkConfig.addAttribute("severity", "ignore");
        final String[] expected = {
            "10: " + getCheckMessage(MSG_WRITE_TAG, "@author", "Daniel Grenner"),
        };
        verify(checkConfig, getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testIgnoreMissing() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        checkConfig.addAttribute("tag", "@todo2");
        checkConfig.addAttribute("tagFormat", "\\S");
        checkConfig.addAttribute("severity", "ignore");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testRegularEx()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        checkConfig.addAttribute("tag", "@author");
        checkConfig.addAttribute("tagFormat", "0*");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testRegularExError()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        checkConfig.addAttribute("tag", "@author");
        checkConfig.addAttribute("tagFormat", "ABC");
        final String[] expected = {
            "10: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
        };
        verify(checkConfig, getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testEnumsAndAnnotations() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        checkConfig.addAttribute("tag", "@incomplete");
        checkConfig.addAttribute("tagFormat", ".*");
        checkConfig.addAttribute("severity", "ignore");
        checkConfig.addAttribute("tagSeverity", "error");
        checkConfig.addAttribute("tokens",
            "ANNOTATION_DEF, ENUM_DEF, ANNOTATION_FIELD_DEF, ENUM_CONSTANT_DEF");
        final String[] expected = {
            "9: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete", "This enum needs more code..."),
            "13: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                "This enum constant needs more code..."),
            "19: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                "This annotation needs more code..."),
            "23: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                "This annotation field needs more code..."),
        };
        verify(checkConfig, getPath("InputWriteTag2.java"), expected);
    }

    @Test
    public void testNoJavadocs() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WriteTagCheck.class);
        final String[] expected = {
            "3: " + getCheckMessage(MSG_MISSING_TAG, "null"),
        };
        verify(checkConfig, getPath("InputWriteTag3.java"), expected);
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
                assertEquals(expectedResult, actual, "error message " + i);
            }

            assertTrue(expected.length >= errs, "unexpected output: " + lnr.readLine());
        }
        checker.destroy();
    }

}
