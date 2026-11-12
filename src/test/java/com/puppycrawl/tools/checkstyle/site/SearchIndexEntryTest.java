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

package com.puppycrawl.tools.checkstyle.site;

import static com.google.common.truth.Truth.assertWithMessage;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link SearchIndexEntry}.
 */
public class SearchIndexEntryTest {

    @Test
    public void testRecordProperties() {
        final SearchIndexEntry entry = new SearchIndexEntry(
                "TypeNameCheck",
                "checks/naming/typename.html",
                "naming",
                "check",
                "Checks that type names conform to a specified pattern.",
                "type,name,pattern",
                "3.0",
                100
        );

        assertWithMessage("Invalid title")
                .that(entry.title())
                .isEqualTo("TypeNameCheck");
        assertWithMessage("Invalid url")
                .that(entry.url())
                .isEqualTo("checks/naming/typename.html");
        assertWithMessage("Invalid category")
                .that(entry.category())
                .isEqualTo("naming");
        assertWithMessage("Invalid type")
                .that(entry.type())
                .isEqualTo("check");
        assertWithMessage("Invalid description")
                .that(entry.description())
                .isEqualTo("Checks that type names conform to a specified pattern.");
        assertWithMessage("Invalid keywords")
                .that(entry.keywords())
                .isEqualTo("type,name,pattern");
        assertWithMessage("Invalid since")
                .that(entry.since())
                .isEqualTo("3.0");
        assertWithMessage("Invalid weight")
                .that(entry.weight())
                .isEqualTo(100);
    }

    @Test
    public void testToJson() {
        final SearchIndexEntry entry = new SearchIndexEntry(
                "TypeNameCheck",
                "checks/naming/typename.html",
                "naming",
                "check",
                "Checks that type names conform to a specified pattern.",
                "type,name,pattern",
                "3.0",
                100
        );
        final String expected = "{"
                + "\"title\":\"TypeNameCheck\","
                + "\"url\":\"checks/naming/typename.html\","
                + "\"category\":\"naming\","
                + "\"type\":\"check\","
                + "\"description\":\"Checks that type names conform to a specified pattern.\","
                + "\"since\":\"3.0\","
                + "\"weight\":100,"
                + "\"keywords\":\"type,name,pattern\""
                + "}";

        assertWithMessage("Serialized JSON output is invalid")
                .that(entry.toJson())
                .isEqualTo(expected);
    }

    @Test
    public void testToJsonWithNullFields() {
        final SearchIndexEntry entry = new SearchIndexEntry(
                null, null, null, null, null, null, null, 0);
        final String expected = "{"
                + "\"title\":\"\","
                + "\"url\":\"\","
                + "\"category\":\"\","
                + "\"type\":\"\","
                + "\"description\":\"\","
                + "\"since\":\"\","
                + "\"weight\":0,"
                + "\"keywords\":\"\""
                + "}";

        assertWithMessage("Serialized JSON output with null fields is invalid")
                .that(entry.toJson())
                .isEqualTo(expected);
    }

    @Test
    public void testToJsonWithEmptyStringsAndNegativeWeight() {
        final SearchIndexEntry entry = new SearchIndexEntry(
                "", "", "", "", "", "", "", -1);
        final String expected = "{"
                + "\"title\":\"\","
                + "\"url\":\"\","
                + "\"category\":\"\","
                + "\"type\":\"\","
                + "\"description\":\"\","
                + "\"since\":\"\","
                + "\"weight\":-1,"
                + "\"keywords\":\"\""
                + "}";

        assertWithMessage("Serialized JSON output with empty strings is invalid")
                .that(entry.toJson())
                .isEqualTo(expected);
    }

    @Test
    public void testToJsonEscapingQuotesAndBackslashes() {
        final SearchIndexEntry entry = new SearchIndexEntry(
                "\"Quotes\"",
                "checks/misc/test\\path",
                "misc",
                "check",
                "\"escaped\" and \\backslash\\ with /slash/",
                "\"key\",\\word\\",
                "1.0",
                50
        );
        final String expected = "{"
                + "\"title\":\"\\\"Quotes\\\"\","
                + "\"url\":\"checks/misc/test\\\\path\","
                + "\"category\":\"misc\","
                + "\"type\":\"check\","
                + "\"description\":\"\\\"escaped\\\" and \\\\backslash\\\\ with /slash/\","
                + "\"since\":\"1.0\","
                + "\"weight\":50,"
                + "\"keywords\":\"\\\"key\\\",\\\\word\\\\\""
                + "}";

        assertWithMessage("Serialized JSON output with quotes and backslashes is invalid")
                .that(entry.toJson())
                .isEqualTo(expected);
    }

    @Test
    public void testToJsonEscapingControlCharacters() {
        final SearchIndexEntry entry = new SearchIndexEntry(
                "Title\tWith\tTabs",
                "url\nwith\nnewlines",
                "category\rwith\rcr",
                "type",
                "Multi\nline\r\ndescription\twith\ttabs",
                "key\nwords",
                "1.0",
                10
        );
        final String expected = "{"
                + "\"title\":\"Title\\tWith\\tTabs\","
                + "\"url\":\"url\\nwith\\nnewlines\","
                + "\"category\":\"category\\rwith\\rcr\","
                + "\"type\":\"type\","
                + "\"description\":\"Multi\\nline\\r\\ndescription\\twith\\ttabs\","
                + "\"since\":\"1.0\","
                + "\"weight\":10,"
                + "\"keywords\":\"key\\nwords\""
                + "}";

        assertWithMessage("Serialized JSON output with control characters is invalid")
                .that(entry.toJson())
                .isEqualTo(expected);
    }

    @Test
    public void testToJsonPreservesUnicodeCharacters() {
        final String accented = "Caf" + (char) 0x00E9;
        final String chinese = Character.toString(0x4E2D) + Character.toString(0x6587);
        final String emoji = Character.toString(0x1F600);
        final String boundaryChar = Character.toString(128);
        final String unicodeText = accented + " " + chinese + " " + emoji + " " + boundaryChar;

        final SearchIndexEntry entry = new SearchIndexEntry(
                accented,
                "checks/naming/test.html",
                "naming",
                "check",
                unicodeText,
                accented,
                "14.5",
                10
        );
        final String expected = "{"
                + "\"title\":\"" + accented + "\","
                + "\"url\":\"checks/naming/test.html\","
                + "\"category\":\"naming\","
                + "\"type\":\"check\","
                + "\"description\":\"" + unicodeText + "\","
                + "\"since\":\"14.5\","
                + "\"weight\":10,"
                + "\"keywords\":\"" + accented + "\""
                + "}";

        assertWithMessage("Serialized JSON output with Unicode characters is invalid")
                .that(entry.toJson())
                .isEqualTo(expected);
    }

}
