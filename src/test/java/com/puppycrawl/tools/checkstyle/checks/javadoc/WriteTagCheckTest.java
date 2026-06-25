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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck.MSG_KEY_UNCLOSED_HTML_TAG;
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
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Unit test for WriteTagCheck.
 */
public class WriteTagCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
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
            "16: " + getCheckMessage(MSG_WRITE_TAG, "@author", "Daniel Grenner"),
        };
        verifyWithInlineConfigParserTwice(getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testMissingFormat() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_WRITE_TAG, "@author", "Daniel Grenner"),
        };
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagMissingFormat.java"), expected);
    }

    @Test
    public void testTagIncomplete() throws Exception {
        final String[] expected = {
            "17: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                "This class needs more code..."),
        };
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagIncomplete.java"), expected);
    }

    @Test
    public void testDoubleTag() throws Exception {
        final String[] expected = {
            "19: " + getCheckMessage(MSG_WRITE_TAG, "@doubletag", "first text"),
            "20: " + getCheckMessage(MSG_WRITE_TAG, "@doubletag", "second text"),
        };
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagDoubleTag.java"), expected);
    }

    @Test
    public void testEmptyTag() throws Exception {
        final String[] expected = {
            "21: " + getCheckMessage(MSG_WRITE_TAG, "@emptytag", ""),
        };
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagEmptyTag.java"), expected);
    }

    @Test
    public void testMissingTag() throws Exception {
        final String[] expected = {
            "21: " + getCheckMessage(MSG_MISSING_TAG, "@missingtag"),
        };
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagMissingTag.java"), expected);
    }

    @Test
    public void testInterface() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_WRITE_TAG, "@author", "Daniel Grenner"),
        };
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagInterface.java"), expected
        );
    }

    @Test
    public void testBlockComment() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagBlockComment.java"), expected
        );
    }

    @Test
    public void testMethod() throws Exception {
        final String[] expected = {
            "27: " + getCheckMessage(MSG_WRITE_TAG, "@todo",
                    "Add a constructor comment"),
            "39: " + getCheckMessage(MSG_WRITE_TAG, "@todo", "Add a comment"),
        };
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagMethod.java"), expected);
    }

    @Test
    public void testSeverity() throws Exception {
        final String[] expected = {
            "17: " + getCheckMessage(MSG_WRITE_TAG, "@author", "Daniel Grenner"),
        };
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagSeverity.java"), expected);
    }

    @Test
    public void testResetSeverityLevel() throws Exception {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final DefaultLogger logger = new DefaultLogger(outputStream,
                AbstractAutomaticBean.OutputStreamOptions.CLOSE);
        verifyWithInlineConfigParserAndDefaultLogger(
                getPath("InputWriteTagResetSeverity.java"),
                getPath("ExpectedWriteTagResetSeverity.txt"),
                logger,
                outputStream);
    }

    @Test
    public void testIgnoreMissing() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(getPath("InputWriteTagIgnore.java"), expected);
    }

    @Test
    public void testRegularEx() throws Exception {
        final String[] expected = {
            "17: " + getCheckMessage(MSG_WRITE_TAG, "@author", "Daniel Grenner"),
        };

        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagRegularExpression.java"), expected);
    }

    @Test
    public void testRegularExError() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
        };
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagExpressionError.java"), expected);
    }

    @Test
    public void testEnumsAndAnnotations() throws Exception {
        final String[] expected = {
            "17: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                    "This enum needs more code..."),
            "22: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                    "This enum constant needs more code..."),
            "29: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                    "This annotation needs more code..."),
            "34: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
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
            "20: " + getCheckMessage(MSG_TAG_FORMAT, "@incomplete", "\\S"),
            "27: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                    "Failed to recognize 'record' introduced in Java 14."),
            "38: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                    "Failed to recognize 'record' introduced in Java 14."),
            "49: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                    "Failed to recognize 'record' introduced in Java 14."),
            "63: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete",
                    "Failed to recognize 'record' introduced in Java 14."),
        };
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagRecordsAndCompactCtors.java"), expected);
    }

    @Test
    public void testTagTypeAuthor() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_WRITE_TAG, "@author",
                "<p>Jane Doe</p>     {@summary a concise summary}"),
        };
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagTypeAuthor.java"), expected);
    }

    @Test
    public void testTagTypeCustom() throws Exception {
        final String[] expected = {
            "17: " + getCheckMessage(MSG_WRITE_TAG, "@customBlock",
                "{@customInline {@nested <br>}}"),
        };
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagTypeCustom.java"), expected);
    }

    @Test
    public void testTagTypeDeprecated() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_WRITE_TAG, "@deprecated",
                "{@systemProperty x}"),
        };
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagTypeDeprecated.java"), expected);
    }

    @Test
    public void testTagTypeParam() throws Exception {
        final String[] expected = {
            "19: " + getCheckMessage(MSG_WRITE_TAG, "@param",
                "<T> type {@index i}"),
            "20: " + getCheckMessage(MSG_WRITE_TAG, "@param",
                "<U> {@linkplain java.lang.String#trim() link}"),
            "21: " + getCheckMessage(MSG_WRITE_TAG, "@param",
                "var1 <p><i>html</i></p> <h2>title</h2>"),
            "22: " + getCheckMessage(MSG_WRITE_TAG, "@param",
                "var2 {@link java.lang.String#trim() variable}"),
            "23: " + getCheckMessage(MSG_WRITE_TAG, "@param",
                "var3 {@return {@code null} or {@literal \"x\"}}"),
            "24: " + getCheckMessage(MSG_WRITE_TAG, "@param",
                "var4     <!-- @@ -->"),
        };
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagTypeParam.java"), expected);
    }

    @Test
    public void testTagTypeReturn() throws Exception {
        final String[] expected = {
            "17: " + getCheckMessage(MSG_WRITE_TAG, "@return",
                "value of type {@code String}"),
        };
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagTypeReturn.java"), expected);
    }

    @Test
    public void testTagTypeSee() throws Exception {
        final String[] expected = {
            "19: " + getCheckMessage(MSG_WRITE_TAG, "@see",
                "java.io.Closeable#close()"),
            "20: " + getCheckMessage(MSG_WRITE_TAG, "@see",
                "<a href=\"https://docs.oracle.com/en/java/\">ref</a>"),
            "21: " + getCheckMessage(MSG_WRITE_TAG, "@see",
                "\"text\""),
        };
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagTypeSee.java"), expected);
    }

    @Test
    public void testTagTypeSince() throws Exception {
        final String[] expected = {
            "19: " + getCheckMessage(MSG_WRITE_TAG, "@since",
                "2.2.2 (2019-12-31)"),
        };
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagTypeSince.java"), expected);
    }

    @Test
    public void testTagTypeThrows() throws Exception {
        final String[] expected = {
            "20: " + getCheckMessage(MSG_WRITE_TAG, "@throws",
                "Exception when i is {@value Integer#MAX_VALUE}"),
        };
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagTypeThrows.java"), expected);
    }

    @Test
    public void testNonTightHtml() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(MSG_KEY_UNCLOSED_HTML_TAG, "p"),
        };
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagNonTightHtml.java"), expected);
    }

    @Test
    public void testPackageInfo() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
            getPath("package-info.java"), expected);
    }

    @Test
    public void testTwoClasses() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete", "test"),
            "25: " + getCheckMessage(MSG_MISSING_TAG, "@incomplete"),
        };
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagTwoClasses.java"), expected);
    }

    @Test
    public void testAnnotationProcessing() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete", "test"),
        };
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagAnnotationProcessing.java"), expected);
    }

    @Test
    public void testAnnotationValue() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete", "test"),
        };
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagAnnotationValue.java"), expected);
    }

    @Test
    public void testMultipleAnnotations() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagMultipleAnnotationsNoJavadoc.java"),
            expected);
    }

    @Test
    public void testBetweenAnnotations() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_WRITE_TAG, "@incomplete", "test"),
        };
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagSingleAnnotation.java"), expected);
    }

    @Test
    public void testIssue20875() throws Exception {
        final String[] expected = {
            "18: " + getCheckMessage(MSG_MISSING_TAG, "@since"),
        };
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagIssue20875.java"), expected);
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
                assertWithMessage("error message %s", i)
                        .that(actual)
                        .isEqualTo(expectedResult);
            }

            assertWithMessage("unexpected output: %s", lnr.readLine())
                    .that(errs)
                    .isAtMost(expected.length);
        }
        checker.destroy();
    }

}
