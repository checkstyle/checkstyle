////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.meta;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocMetadataScraperTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/meta/javadocmetadatascraper";
    }

    @Test
    public void testAtclauseOrderCheck() throws Exception {
        JavadocMetadataScraper.resetModuleDetailsStore();
        verifyWithInlineConfigParser(getPath("InputJavadocMetadataScraperAtclauseOrderCheck.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
        assertEquals(readFile(getPath("ExpectedJavadocMetadataScraperAtclauseOrderCheck.txt")),
                convertToString(JavadocMetadataScraper.getModuleDetailsStore()),
                "expected correct parse");
    }

    @Test
    public void testAnnotationUseStyleCheck() throws Exception {
        JavadocMetadataScraper.resetModuleDetailsStore();
        verifyWithInlineConfigParser(
                getPath("InputJavadocMetadataScraperAnnotationUseStyleCheck.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
        assertEquals(readFile(getPath("ExpectedJavadocMetadataScraperAnnotationUseStyleCheck.txt")),
                convertToString(JavadocMetadataScraper.getModuleDetailsStore()),
                "expected correct parse");
    }

    @Test
    public void testBeforeExecutionExclusionFileFilter() throws Exception {
        JavadocMetadataScraper.resetModuleDetailsStore();
        verifyWithInlineConfigParser(
                getPath("InputJavadocMetadataScraperBeforeExecutionExclusionFileFilter.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
        assertEquals(
                readFile(getPath(
                        "ExpectedJavadocMetadataScraperBeforeExecutionExclusionFileFilter.txt")),
                convertToString(JavadocMetadataScraper.getModuleDetailsStore()),
                "expected correct parse");
    }

    @Test
    public void testNoCodeInFileCheck() throws Exception {
        JavadocMetadataScraper.resetModuleDetailsStore();
        verifyWithInlineConfigParser(getPath("InputJavadocMetadataScraperNoCodeInFileCheck.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
        assertEquals(readFile(getPath("ExpectedJavadocMetadataScraperNoCodeInFileCheck.txt")),
                convertToString(JavadocMetadataScraper.getModuleDetailsStore()),
                "expected correct parse");
    }

    @Test
    public void testPropertyMisplacedDefaultValueCheck() {
        JavadocMetadataScraper.resetModuleDetailsStore();
        final CheckstyleException exc = assertThrows(CheckstyleException.class, () -> {
            verifyWithInlineConfigParser(
                    getPath("InputJavadocMetadataScraperPropertyMisplacedDefaultValueCheck.java"),
                    CommonUtil.EMPTY_STRING_ARRAY);
        });
        assertThat(exc.getCause()).isInstanceOf(MetadataGenerationException.class);
        assertThat(exc.getCause().getMessage())
                .isEqualTo("Default value for property 'misplacedDefaultValue' is missing");
    }

    @Test
    public void testPropertyMisplacedTypeCheck() {
        JavadocMetadataScraper.resetModuleDetailsStore();
        final CheckstyleException exc = assertThrows(CheckstyleException.class, () -> {
            verifyWithInlineConfigParser(
                    getPath("InputJavadocMetadataScraperPropertyMisplacedTypeCheck.java"),
                    CommonUtil.EMPTY_STRING_ARRAY);
            fail("Exception expected");
        });
        assertThat(exc.getCause()).isInstanceOf(MetadataGenerationException.class);
        assertThat(exc.getCause().getMessage())
                .isEqualTo("Type for property 'misplacedType' is missing");
    }

    @Test
    public void testPropertyMissingDefaultValueCheck() {
        JavadocMetadataScraper.resetModuleDetailsStore();
        final CheckstyleException exc = assertThrows(CheckstyleException.class, () -> {
            verifyWithInlineConfigParser(
                    getPath("InputJavadocMetadataScraperPropertyMissingDefaultValueCheck.java"),
                    CommonUtil.EMPTY_STRING_ARRAY);
            fail("Exception expected");
        });
        assertThat(exc.getCause()).isInstanceOf(MetadataGenerationException.class);
        assertThat(exc.getCause().getMessage())
                .isEqualTo("Default value for property 'missingDefaultValue' is missing");
    }

    @Test
    public void testPropertyMissingTypeCheck() {
        JavadocMetadataScraper.resetModuleDetailsStore();
        final CheckstyleException exc = assertThrows(CheckstyleException.class, () -> {
            verifyWithInlineConfigParser(
                    getPath("InputJavadocMetadataScraperPropertyMissingTypeCheck.java"),
                    CommonUtil.EMPTY_STRING_ARRAY);
            fail("Exception expected");
        });
        assertThat(exc.getCause()).isInstanceOf(MetadataGenerationException.class);
        assertThat(exc.getCause().getMessage())
                .isEqualTo("Type for property 'missingType' is missing");
    }

    @Test
    public void testPropertyWithNoCodeTagCheck() throws Exception {
        JavadocMetadataScraper.resetModuleDetailsStore();
        verifyWithInlineConfigParser(
                getPath("InputJavadocMetadataScraperPropertyWithNoCodeTagCheck.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
        assertEquals(
                readFile(getPath("ExpectedJavadocMetadataScraperPropertyWithNoCodeTagCheck.txt")),
                convertToString(JavadocMetadataScraper.getModuleDetailsStore()),
                "expected correct parse");
    }

    @Test
    public void testRightCurlyCheck() throws Exception {
        JavadocMetadataScraper.resetModuleDetailsStore();
        verifyWithInlineConfigParser(getPath("InputJavadocMetadataScraperRightCurlyCheck.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
        assertEquals(readFile(getPath("ExpectedJavadocMetadataScraperRightCurlyCheck.txt")),
                convertToString(JavadocMetadataScraper.getModuleDetailsStore()),
                "expected correct parse");
    }

    @Test
    public void testSummaryJavadocCheck() throws Exception {
        JavadocMetadataScraper.resetModuleDetailsStore();
        verifyWithInlineConfigParser(getPath("InputJavadocMetadataScraperSummaryJavadocCheck.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
        assertEquals(readFile(getPath("ExpectedJavadocMetadataScraperSummaryJavadocCheck.txt")),
                convertToString(JavadocMetadataScraper.getModuleDetailsStore()),
                "expected correct parse");
    }

    @Test
    public void testSuppressWarningsFilter() throws Exception {
        JavadocMetadataScraper.resetModuleDetailsStore();
        verifyWithInlineConfigParser(
                getPath("InputJavadocMetadataScraperSuppressWarningsFilter.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
        assertEquals(readFile(getPath("ExpectedJavadocMetadataScraperSuppressWarningsFilter.txt")),
                convertToString(JavadocMetadataScraper.getModuleDetailsStore()),
                "expected correct parse");
    }

    @Test
    public void testWriteTagCheck() throws Exception {
        JavadocMetadataScraper.resetModuleDetailsStore();
        verifyWithInlineConfigParser(getPath("InputJavadocMetadataScraperWriteTagCheck.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
        assertEquals(readFile(getPath("ExpectedJavadocMetadataScraperWriteTagCheck.txt")),
                convertToString(JavadocMetadataScraper.getModuleDetailsStore()),
                "expected correct parse");
    }

    private static String convertToString(Map<String, ModuleDetails> moduleDetailsStore) {
        final StringBuilder builder = new StringBuilder();
        for (Entry<String, ModuleDetails> entry : moduleDetailsStore.entrySet()) {
            final ModuleDetails details = entry.getValue();

            append(builder, "Key: ", split(entry.getKey(), 70));
            append(builder, "Name: ", details.getName());
            append(builder, "FullQualifiedName: ", split(details.getFullQualifiedName(), 70));
            append(builder, "Parent: ", details.getParent());
            append(builder, "Description: ", details.getDescription());
            append(builder, "ModuleType: ", details.getModuleType());

            for (ModulePropertyDetails property : details.getProperties()) {
                append(builder, "Property Type: ", split(property.getType(), 70));
                append(builder, "Property DefaultValue: ", split(property.getDefaultValue(), 70));
                append(builder, "Property ValidationType: ", property.getValidationType());
                append(builder, "Property Description: ", property.getDescription());
            }

            for (String key : details.getViolationMessageKeys()) {
                append(builder, "ViolationMessageKey: ", key);
            }
        }
        return builder.toString();
    }

    private static void append(StringBuilder builder, String title, Object object) {
        builder.append(title).append(object).append('\n');
    }

    private static String split(String string, int size) {
        final StringBuilder builder = new StringBuilder();
        if (string == null) {
            builder.append("null");
        }
        else {
            final int length = string.length();
            int position = 0;
            while (position < length) {
                if (position != 0) {
                    builder.append("<split>\n");
                }
                builder.append(string.substring(position, Math.min(length, position + size)));
                position += size;
            }
        }
        return builder.toString();
    }
}
