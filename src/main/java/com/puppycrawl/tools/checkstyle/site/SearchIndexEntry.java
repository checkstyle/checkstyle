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

/**
 * Represents a single entry in the client-side search index.
 *
 * @param title display name
 * @param url relative URL
 * @param category category label
 * @param type document type
 * @param description short description
 * @param keywords comma-separated keywords
 */
public record SearchIndexEntry(String title, String url, String category,
                            String type, String description, String keywords) {

    /** String literal for comma. */
    private static final String COMMA = ",";

    /** Maximum ASCII value for the lookup table. */
    private static final int ASCII_MAX = 128;

    /** Lookup table for JSON escape sequences for characters &lt; 128 (ASCII range). */
    private static final String[] JSON_ESCAPES;

    static {
        JSON_ESCAPES = new String[ASCII_MAX];
        JSON_ESCAPES['\\'] = "\\\\";
        JSON_ESCAPES['\"'] = "\\\"";
        JSON_ESCAPES['\n'] = "\\n";
        JSON_ESCAPES['\r'] = "\\r";
        JSON_ESCAPES['\t'] = "\\t";
    }

    /**
     * Creates a new {@code SearchIndexEntry} instance.
     *
     * @param title the title of the entry
     * @param url the URL of the entry
     * @param category the category of the entry
     * @param type the type of the entry
     * @param description the description of the entry
     * @param keywords the keywords of the entry
     */
    public SearchIndexEntry {
    }

    /**
     * Serialises this entry to a compact JSON object string.
     *
     * @return JSON object string
     */
    public String toJson() {
        return "{"
            + "\"title\":" + jsonString(title) + COMMA
            + "\"url\":" + jsonString(url) + COMMA
            + "\"category\":" + jsonString(category) + COMMA
            + "\"type\":" + jsonString(type) + COMMA
            + "\"description\":" + jsonString(description) + COMMA
            + "\"keywords\":" + jsonString(keywords)
            + "}";
    }

    /**
     * Wraps a string value in JSON double quotes and escapes special characters.
     *
     * @param value the raw string to encode
     * @return JSON-safe quoted string, or {@code ""} if value is null
     */
    private static String jsonString(String value) {
        String result = "\"\"";
        if (value != null) {
            final int length = value.length();
            final StringBuilder sb = new StringBuilder(length + 2);
            sb.append('\"');
            for (int index = 0; index < length; index++) {
                final char ch = value.charAt(index);
                final String escape;
                if (ch < ASCII_MAX) {
                    escape = JSON_ESCAPES[ch];
                }
                else {
                    escape = null;
                }
                if (escape == null) {
                    sb.append(ch);
                }
                else {
                    sb.append(escape);
                }
            }
            sb.append('\"');
            result = sb.toString();
        }
        return result;
    }

}
