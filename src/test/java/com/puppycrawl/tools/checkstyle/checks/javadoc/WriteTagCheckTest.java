///
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
///

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.WriteTagCheck.MSG_MISSING_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.WriteTagCheck.MSG_TAG_FORMAT;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.WriteTagCheck.MSG_WRITE_TAG;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.PackageObjectFactory;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import de.thetaphi.forbiddenapis.SuppressForbidden;

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
        verifyWithInlineConfigParserTwice(getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testMissingFormat() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(MSG_WRITE_TAG, "@author", "Daniel Grenner"),
        };
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagMissingFormat.java"), expected);
    }

    @Test
    public void testTagIncomplete() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                "This class needs more code..."),
        };
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagIncomplete.java"), expected);
    }

    @Test
    public void testDoubleTag() throws Exception {
        final String[] expected = {
            "18: " + getCheckMessage(MSG_WRITE_TAG, "@doubletag", "first text"),
            "19: " + getCheckMessage(MSG_WRITE_TAG, "@doubletag", "second text"),
        };
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagDoubleTag.java"), expected);
    }

    @Test
    public void testEmptyTag() throws Exception {
        final String[] expected = {
            "19: " + getCheckMessage(MSG_WRITE_TAG, "@emptytag", ""),
        };
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagEmptyTag.java"), expected);
    }

    @Test
    public void testMissingTag() throws Exception {
        final String[] expected = {
            "20: " + getCheckMessage(MSG_MISSING_TAG, "@missingtag"),
        };
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagMissingTag.java"), expected);
    }

    @Test
    public void testMethod() throws Exception {
        final String[] expected = {
            "24: " + getCheckMessage(MSG_WRITE_TAG, "@todo",
                    "Add a constructor comment"),
            "36: " + getCheckMessage(MSG_WRITE_TAG, "@todo", "Add a comment"),
        };
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagMethod.java"), expected);
    }

    @Test
    public void testSeverity() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_WRITE_TAG, "@author", "Daniel Grenner"),
        };
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagSeverity.java"), expected);
    }

    /**
     * Reason for low level testing:
     * There is no direct way to fetch severity level directly.
     * This is an exceptional case in which the logs are fetched indirectly using default
     * logger listener in order to check for the severity level being reset by logging twice.
     * First log should be the tag's severity level then it should reset the severity level back to
     * error which is then checked upon using the log's severity level.
     * This test needs to use a forbidden api {@code ByteArrayOutputStream#toString()}
     * to get the logs as a string from the output stream
     */
    @Test
    @SuppressForbidden
    public void testResetSeverityLevel() throws Exception {

        final Checker checker = new Checker();

        final TreeWalker treeWalker = new TreeWalker();
        final PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<>(), Thread.currentThread().getContextClassLoader());

        treeWalker.setModuleFactory(factory);
        treeWalker.finishLocalSetup();

        final DefaultConfiguration writeTagConfig = createModuleConfig(WriteTagCheck.class);
        writeTagConfig.addProperty("tag", "@author");
        writeTagConfig.addProperty("tagFormat", "Mohanad");
        writeTagConfig.addProperty("tagSeverity", "warning");

        treeWalker.setupChild(writeTagConfig);

        checker.addFileSetCheck(treeWalker);

        final ByteArrayOutputStream out = TestUtil.getInternalState(this, "stream");
        final DefaultLogger logger = new DefaultLogger(out,
                AbstractAutomaticBean.OutputStreamOptions.CLOSE);
        checker.addListener(logger);

        execute(checker, getPath("InputWriteTagResetSeverity.java"));

        final String output = out.toString();

        // logs severity levels are between square brackets []
        final Pattern severityPattern = Pattern.compile("\\[(ERROR|WARN|INFO|IGNORE)]");

        final Matcher matcher = severityPattern.matcher(output);

        // First log is just the normal tag one
        final boolean firstMatchFound = matcher.find();
        assertWithMessage("Severity level should be wrapped in a square bracket []")
                .that(firstMatchFound)
                .isTrue();

        final String tagExpectedSeverityLevel = "warn";
        final String firstSeverityLevel = matcher.group(1).toLowerCase(Locale.ENGLISH);

        assertWithMessage("First log should have an error severity level")
                .that(firstSeverityLevel)
                .isEqualTo(tagExpectedSeverityLevel);

        // Now we check for the second log which should log error  if
        // the previous log did not have an issue while resetting the original severity level
        final boolean secondMatchFound = matcher.find();
        assertWithMessage("Severity level should be wrapped in a square bracket []")
                .that(secondMatchFound)
                .isTrue();

        final String expectedSeverityLevelAfterReset = "error";

        final String secondSeverityLevel = matcher.group(1).toLowerCase(Locale.ENGLISH);

        assertWithMessage("Second violation's severity level"
                + " should have been reset back to default (error)")
                .that(secondSeverityLevel)
                .isEqualTo(expectedSeverityLevelAfterReset);

    }

    @Test
    public void testIgnoreMissing() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(getPath("InputWriteTagIgnore.java"), expected);
    }

    @Test
    public void testRegularEx() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagRegularExpression.java"), expected);
    }

    @Test
    public void testRegularExError() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
        };
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagExpressionError.java"), expected);
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
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagEnumsAndAnnotations.java"), expected);
    }

    @Test
    public void testNoJavadocs() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParserTwice(getPath("InputWriteTagNoJavadoc.java"), expected);
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
        verifyWithInlineConfigParserTwice(
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
