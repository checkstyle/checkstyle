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

package com.puppycrawl.tools.checkstyle.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Utility class for inline configuration parsing shared between
 * ExampleMacro and InlineConfigParser.
 *
 * <p>Supports multiple config-delimiter conventions so that legacy examples using the
 * historical {@code /*xml} Java-comment style keep working regardless of target file
 * extension, while newer examples for non-Java targets (.xml, .properties) that need a
 * genuinely valid comment in their own file format can opt into a type-appropriate
 * delimiter instead.
 */
public final class InlineConfigUtils {

    /** Legacy/default config comment prefix — a loose Java block comment. */
    public static final String JAVA_CONFIG_PREFIX = "/*";

    /** Legacy/default config start delimiter for XML-style (module) config. */
    public static final String JAVA_XML_CONFIG_START = "/*xml";

    /** Legacy/default config end delimiter. */
    public static final String JAVA_CONFIG_END = "*/";

    /** Config start delimiter for XML target files that need well-formed XML content. */
    public static final String XML_TARGET_CONFIG_START = "<!--xml";

    /** Config end delimiter for XML target files. */
    public static final String XML_TARGET_CONFIG_END = "-->";

    /** Comment prefix for .properties target files. */
    public static final String PROPERTIES_COMMENT_PREFIX = "#";

    /** File extension for XML files. */
    private static final String XML_FILE_EXTENSION = ".xml";

    /** File extension for properties files. */
    private static final String PROPERTIES_FILE_EXTENSION = ".properties";

    /** Separator for delimiter descriptions. */
    private static final String DELIMITER_SEPARATOR = ", or \"";

    /** Prevent instantiation. */
    private InlineConfigUtils() {
    }

    /**
     * Matches the first line of a file against every delimiter convention valid for that
     * file's type, and returns the match, or {@code null} if none apply.
     *
     * <p>For {@code .properties} files there is no explicit start/end marker pair: any
     * leading line starting with {@code #} is treated as the start of the config block,
     * and the block runs until the first blank line (see {@link #getConfigEndIndex}).
     *
     * @param lines the lines of the file.
     * @param filePath the file path, used to decide which non-Java conventions are valid.
     * @return the matched delimiter, or {@code null} if the first line matches nothing.
     */
    public static MatchedDelimiter matchDelimiter(List<String> lines, String filePath) {
        MatchedDelimiter result = null;
        if (!lines.isEmpty()) {
            final String first = lines.getFirst();
            if (first.startsWith(JAVA_CONFIG_PREFIX)) {
                result = new MatchedDelimiter(JAVA_CONFIG_END, JAVA_XML_CONFIG_START.equals(first));
            }
            else if (filePath.endsWith(XML_FILE_EXTENSION)
                    && XML_TARGET_CONFIG_START.equals(first)) {
                result = new MatchedDelimiter(XML_TARGET_CONFIG_END, true);
            }
            else if (filePath.endsWith(PROPERTIES_FILE_EXTENSION)
                    && first.startsWith(PROPERTIES_COMMENT_PREFIX)) {
                // No explicit end marker: the leading "#" comment block, up to the
                // first blank line, IS the config.
                result = new MatchedDelimiter(null, true);
            }
        }
        return result;
    }

    /**
     * Finds the index of the line where a matched config block ends. For delimiter-based
     * matches, this is the first line starting with the end delimiter. For properties-style
     * matches (no explicit end delimiter), this is the first blank line after the start,
     * or the end of the file if there is no blank line.
     *
     * @param lines the lines of the file, including the start line at index 0.
     * @param matched the matched delimiter describing how to find the end.
     * @return the index of the line where the config block ends (exclusive), or -1 if a
     *     delimiter-based end could not be found.
     */
    public static int getConfigEndIndex(Iterable<String> lines, MatchedDelimiter matched) {
        final int result;
        if (matched.end() == null) {
            int index = 0;
            final Iterator<String> iterator = lines.iterator();
            while (iterator.hasNext() && !iterator.next().isEmpty()) {
                index++;
            }
            result = index;
        }
        else {
            result = indexOfStartingWith(lines, matched.end());
        }
        return result;
    }

    /**
     * Finds the index of the first line that starts with the given prefix.
     *
     * @param lines the lines to search.
     * @param prefix the prefix to search for.
     * @return the index of the first matching line, or -1 if not found.
     */
    private static int indexOfStartingWith(Iterable<String> lines, String prefix) {
        int result = -1;
        int index = 0;
        for (String line : lines) {
            if (line.startsWith(prefix)) {
                result = index;
                break;
            }
            index++;
        }
        return result;
    }

    /**
     * Builds a human-readable description of every delimiter convention valid for the
     * given file's type, for use in error messages.
     *
     * @param filePath the file path.
     * @return a description of valid start delimiters for this file type.
     */
    public static String describeExpectedDelimiters(String filePath) {
        final StringBuilder builder = new StringBuilder(160);
        builder.append("\"/*xml\" or \"/*\" (Java-comment style)");
        if (filePath.endsWith(XML_FILE_EXTENSION)) {
            builder.append(DELIMITER_SEPARATOR).append(XML_TARGET_CONFIG_START)
                    .append("\" (XML-comment style)");
        }
        if (filePath.endsWith(PROPERTIES_FILE_EXTENSION)) {
            builder.append(DELIMITER_SEPARATOR)
                    .append(PROPERTIES_COMMENT_PREFIX.charAt(0))
                    .append("\" leading comment block, ending at the first blank line");
        }
        return builder.toString();
    }

    /**
     * Strips the leading {@code #} comment marker from each line in a properties-style
     * config block. Used because config lines in {@code .properties} files must themselves
     * be valid properties-file comments.
     *
     * @param lines the lines to process.
     * @return the lines with a leading {@code #} removed where present.
     */
    public static List<String> stripPropertiesCommentPrefix(Collection<String> lines) {
        return lines.stream()
                .map(InlineConfigUtils::stripLeadingHash)
                .toList();
    }

    /**
     * Strips a leading hash character from the given line if present.
     *
     * @param line the line to process.
     * @return the line with leading hash removed, or the original line if no hash.
     */
    private static String stripLeadingHash(String line) {
        String result = line;
        if (line.startsWith(PROPERTIES_COMMENT_PREFIX)) {
            result = line.substring(1);
        }
        return result;
    }

    /**
     * Describes which delimiter convention matched the first line of a file, so callers
     * can locate the end of the config block and know whether the config content should
     * be parsed as XML module config or the legacy bare key=value format.
     *
     * @param end the end delimiter to search for, or {@code null} if the config block
     *     ends implicitly at the first blank line (properties-style).
     * @param xmlStyleConfig true if the config content is XML ({@code <module>} form),
     *     false if it is the legacy bare key=value form.
     */
    public record MatchedDelimiter(String end, boolean xmlStyleConfig) {
    }

}
