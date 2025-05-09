///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.meta;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.meta.JavadocMetadataScraper.MSG_DESC_MISSING;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

class JavadocMetadataScraperTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/meta/javadocmetadatascraper";
    }

    @Test
    void atclauseOrderCheck() throws Exception {
        JavadocMetadataScraper.resetModuleDetailsStore();
        verifyWithInlineConfigParser(getPath("InputJavadocMetadataScraperAtclauseOrderCheck.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
        assertWithMessage("expected correct parse")
                .that(convertToString(JavadocMetadataScraper.getModuleDetailsStore()))
                .isEqualTo(
                        readFile(getPath("ExpectedJavadocMetadataScraperAtclauseOrderCheck.txt")));
    }

    @Test
    void annotationUseStyleCheck() throws Exception {
        JavadocMetadataScraper.resetModuleDetailsStore();
        verifyWithInlineConfigParser(
                getPath("InputJavadocMetadataScraperAnnotationUseStyleCheck.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
        assertWithMessage("expected correct parse")
                .that(convertToString(JavadocMetadataScraper.getModuleDetailsStore()))
                .isEqualTo(readFile(
                        getPath("ExpectedJavadocMetadataScraperAnnotationUseStyleCheck.txt")));
    }

    @Test
    void beforeExecutionExclusionFileFilter() throws Exception {
        JavadocMetadataScraper.resetModuleDetailsStore();
        verifyWithInlineConfigParser(
                getPath("InputJavadocMetadataScraperBeforeExecutionExclusionFileFilter.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
        assertWithMessage("expected correct parse")
                .that(convertToString(JavadocMetadataScraper.getModuleDetailsStore()))
                .isEqualTo(readFile(getPath(
                        "ExpectedJavadocMetadataScraperBeforeExecutionExclusionFileFilter.txt")));
    }

    @Test
    void noCodeInFileCheck() throws Exception {
        JavadocMetadataScraper.resetModuleDetailsStore();
        verifyWithInlineConfigParser(getPath("InputJavadocMetadataScraperNoCodeInFileCheck.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
        assertWithMessage("expected correct parse")
                .that(convertToString(JavadocMetadataScraper.getModuleDetailsStore()))
                .isEqualTo(
                        readFile(getPath("ExpectedJavadocMetadataScraperNoCodeInFileCheck.txt")));
    }

    @Test
    void propertyMisplacedDefaultValueCheck() {
        JavadocMetadataScraper.resetModuleDetailsStore();
        final CheckstyleException exc = assertThatExceptionOfType(CheckstyleException.class).isThrownBy(() ->
                verifyWithInlineConfigParser(
                        getPath("InputJavadocMetadataScraperPropertyMisplacedDefaultValueCheck.java"),
                        CommonUtil.EMPTY_STRING_ARRAY)).actual();
        assertThat(exc.getCause()).isInstanceOf(MetadataGenerationException.class);
        assertThat(exc.getCause().getMessage())
                .isEqualTo("Default value for property 'misplacedDefaultValue' is missing");
    }

    @Test
    void propertyMisplacedTypeCheck() {
        JavadocMetadataScraper.resetModuleDetailsStore();
        verifyWithInlineConfigParser(
                    getPath("InputJavadocMetadataScraperPropertyMisplacedTypeCheck.java"),
                    CommonUtil.EMPTY_STRING_ARRAY);
        final CheckstyleException exc = assertThatExceptionOfType(CheckstyleException.class).isThrownBy(() ->
                assertWithMessage("Exception expected").fail()).actual();
        assertThat(exc.getCause()).isInstanceOf(MetadataGenerationException.class);
        assertThat(exc.getCause().getMessage())
                .isEqualTo("Type for property 'misplacedType' is missing");
    }

    @Test
    void propertyMissingDefaultValueCheck() {
        JavadocMetadataScraper.resetModuleDetailsStore();
        verifyWithInlineConfigParser(
                    getPath("InputJavadocMetadataScraperPropertyMissingDefaultValueCheck.java"),
                    CommonUtil.EMPTY_STRING_ARRAY);
        final CheckstyleException exc = assertThatExceptionOfType(CheckstyleException.class).isThrownBy(() ->
                assertWithMessage("Exception expected").fail()).actual();
        assertThat(exc.getCause()).isInstanceOf(MetadataGenerationException.class);
        assertThat(exc.getCause().getMessage())
                .isEqualTo("Default value for property 'missingDefaultValue' is missing");
    }

    @Test
    void propertyMissingTypeCheck() {
        JavadocMetadataScraper.resetModuleDetailsStore();
        verifyWithInlineConfigParser(
                    getPath("InputJavadocMetadataScraperPropertyMissingTypeCheck.java"),
                    CommonUtil.EMPTY_STRING_ARRAY);
        final CheckstyleException exc = assertThatExceptionOfType(CheckstyleException.class).isThrownBy(() ->
                assertWithMessage("Exception expected").fail()).actual();
        assertThat(exc.getCause()).isInstanceOf(MetadataGenerationException.class);
        assertThat(exc.getCause().getMessage())
                .isEqualTo("Type for property 'missingType' is missing");
    }

    @Test
    void propertyWithNoCodeTagCheck() throws Exception {
        JavadocMetadataScraper.resetModuleDetailsStore();
        verifyWithInlineConfigParser(
                getPath("InputJavadocMetadataScraperPropertyWithNoCodeTagCheck.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
        assertWithMessage("expected correct parse")
                .that(convertToString(JavadocMetadataScraper.getModuleDetailsStore()))
                .isEqualTo(readFile(
                        getPath("ExpectedJavadocMetadataScraperPropertyWithNoCodeTagCheck.txt")));
    }

    @Test
    void rightCurlyCheck() throws Exception {
        JavadocMetadataScraper.resetModuleDetailsStore();
        verifyWithInlineConfigParser(getPath("InputJavadocMetadataScraperRightCurlyCheck.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
        assertWithMessage("expected correct parse")
                .that(convertToString(JavadocMetadataScraper.getModuleDetailsStore()))
                .isEqualTo(readFile(getPath("ExpectedJavadocMetadataScraperRightCurlyCheck.txt")));
    }

    @Test
    void summaryJavadocCheck() throws Exception {
        JavadocMetadataScraper.resetModuleDetailsStore();
        verifyWithInlineConfigParser(getPath("InputJavadocMetadataScraperSummaryJavadocCheck.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
        assertWithMessage("expected correct parse")
                .that(convertToString(JavadocMetadataScraper.getModuleDetailsStore()))
                .isEqualTo(
                        readFile(getPath("ExpectedJavadocMetadataScraperSummaryJavadocCheck.txt")));
    }

    @Test
    void suppressWarningsFilter() throws Exception {
        JavadocMetadataScraper.resetModuleDetailsStore();
        verifyWithInlineConfigParser(
                getPath("InputJavadocMetadataScraperSuppressWarningsFilter.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
        assertWithMessage("expected correct parse")
                .that(convertToString(JavadocMetadataScraper.getModuleDetailsStore()))
                .isEqualTo(readFile(
                        getPath("ExpectedJavadocMetadataScraperSuppressWarningsFilter.txt")));
    }

    @Test
    void writeTagCheck() throws Exception {
        JavadocMetadataScraper.resetModuleDetailsStore();
        verifyWithInlineConfigParser(getPath("InputJavadocMetadataScraperWriteTagCheck.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
        assertWithMessage("expected correct parse")
                .that(convertToString(JavadocMetadataScraper.getModuleDetailsStore()))
                .isEqualTo(readFile(getPath("ExpectedJavadocMetadataScraperWriteTagCheck.txt")));
    }

    @Test
    void emptyDescription() throws Exception {
        JavadocMetadataScraper.resetModuleDetailsStore();

        final String[] expected = {
            "19: " + getCheckMessage(MSG_DESC_MISSING,
                    "InputJavadocMetadataScraperAbstractSuperCheck"),
        };
        verifyWithInlineConfigParser(getPath(
                "InputJavadocMetadataScraperAbstractSuperCheck.java"), expected);
    }

    private static String convertToString(Map<String, ModuleDetails> moduleDetailsStore) {
        final StringBuilder builder = new StringBuilder(128);
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

    private static String split(String text, int size) {
        final StringBuilder builder = new StringBuilder(80);
        if (text == null) {
            builder.append("null");
        }
        else {
            final int length = text.length();
            int position = 0;
            while (position < length) {
                if (position != 0) {
                    builder.append("<split>\n");
                }
                builder.append(text, position, Math.min(length, position + size));
                position += size;
            }
        }
        return builder.toString();
    }
}
