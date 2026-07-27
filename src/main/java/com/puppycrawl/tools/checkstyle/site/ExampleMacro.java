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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.maven.doxia.macro.AbstractMacro;
import org.apache.maven.doxia.macro.Macro;
import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.macro.MacroRequest;
import org.apache.maven.doxia.sink.Sink;
import org.codehaus.plexus.component.annotations.Component;

import com.puppycrawl.tools.checkstyle.utils.InlineConfigUtils;

/**
 * A macro that inserts a snippet of code or configuration from a file.
 */
@Component(role = Macro.class, hint = "example")
public class ExampleMacro extends AbstractMacro {

    /** Starting delimiter for code snippets. */
    private static final String CODE_SNIPPET_START = "xdoc section -- start";

    /** Ending delimiter for code snippets. */
    private static final String CODE_SNIPPET_END = "xdoc section -- end";

    /** The pattern of xml code blocks. */
    private static final Pattern XML_PATTERN = Pattern.compile(
            "^\\s*(<!DOCTYPE\\s+.*?>|<\\?xml\\s+.*?>|<module\\s+.*?>)\\s*",
            Pattern.DOTALL
    );

    /** The path of the last file. */
    private String lastPath = "";

    /** The line contents of the last file. */
    private List<String> lastLines = new ArrayList<>();

    /** Default constructor. */
    public ExampleMacro() {
        // Default constructor
    }

    @Override
    public void execute(Sink sink, MacroRequest request) throws MacroExecutionException {
        final String path = (String) request.getParameter("path");
        final String type = (String) request.getParameter("type");

        List<String> lines = lastLines;
        if (!path.equals(lastPath)) {
            lines = readFile("src/xdocs-examples/" + path);
            lastPath = path;
            lastLines = lines;
        }

        if ("config".equals(type)) {
            final String config = getConfigSnippet(lines, path);

            if (config.isBlank()) {
                final String message = String.format(Locale.ROOT,
                        "Empty config snippet from %s, check"
                                + " for xml config snippet delimiters in input file.", path
                );
                throw new MacroExecutionException(message);
            }

            writeSnippet(sink, config);
        }
        else if ("code".equals(type)) {
            String code = getCodeSnippet(lines);
            // Replace tabs with spaces for FileTabCharacterCheck examples
            if (path.contains("filetabcharacter")) {
                code = code.replace("\t", "  ");
            }

            if (code.isBlank()) {
                final String message = String.format(Locale.ROOT,
                        "Empty code snippet from %s, check"
                                + " for code snippet delimiters in input file.", path
                );
                throw new MacroExecutionException(message);
            }

            writeSnippet(sink, code);
        }
        else if ("raw".equals(type)) {
            final String content = String.join(ModuleJavadocParsingUtil.NEWLINE, lines);
            writeSnippet(sink, content);
        }
        else {
            final String message = String.format(Locale.ROOT, "Unknown example type: %s", type);
            throw new MacroExecutionException(message);
        }
    }

    /**
     * Read the file at the given path and returns its contents as a list of lines.
     *
     * @param path the path to the file to read.
     * @return the contents of the file as a list of lines.
     * @throws MacroExecutionException if the file could not be read.
     */
    private static List<String> readFile(String path) throws MacroExecutionException {
        try {
            final Path exampleFilePath = Path.of(path);
            return Files.readAllLines(exampleFilePath);
        }
        catch (IOException ioException) {
            final String message = String.format(Locale.ROOT, "Failed to read %s", path);
            throw new MacroExecutionException(message, ioException);
        }
    }

    /**
     * Extracts the configuration snippet from the given lines.
     *
     * @param lines the lines to extract the config from.
     * @param path the file path, used for error messages.
     * @return the configuration snippet.
     * @throws MacroExecutionException if the config block is invalid.
     */
    private static String getConfigSnippet(Collection<String> lines, String path)
            throws MacroExecutionException {
        final List<String> linesList = new ArrayList<>(lines);
        final InlineConfigUtils.MatchedDelimiter matched =
                InlineConfigUtils.matchDelimiter(linesList, path);

        if (matched == null) {
            final String message = String.format(Locale.ROOT,
                    "No valid config block found in %s. Expected the first line to be %s.",
                    path, InlineConfigUtils.describeExpectedDelimiters(path)
            );
            throw new MacroExecutionException(message);
        }

        final String endDelimiter = matched.end();
        final int endIndex = indexOfStartingWith(linesList, endDelimiter);
        if (endIndex <= 0) {
            final String message = String.format(Locale.ROOT,
                    "Config start delimiter found in %s but no matching end delimiter \"%s\".",
                    path, endDelimiter
            );
            throw new MacroExecutionException(message);
        }

        final List<String> configLines = linesList.subList(1, endIndex);
        final String result;
        if (InlineConfigUtils.PROPERTIES_CONFIG_END.equals(matched.end())) {
            result = String.join(ModuleJavadocParsingUtil.NEWLINE,
                    InlineConfigUtils.stripPropertiesCommentPrefix(configLines));
        }
        else {
            result = String.join(ModuleJavadocParsingUtil.NEWLINE, configLines);
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
    private static int indexOfStartingWith(List<String> lines, String prefix) {
        int result = -1;
        for (int index = 0; index < lines.size(); index++) {
            if (lines.get(index).startsWith(prefix)) {
                result = index;
                break;
            }
        }
        return result;
    }

    /**
     * Extract a code snippet from the given lines. Code delimiters can be indented, so
     * we use contains() instead of equals(). If the delimiters are not found, returns
     * the file content excluding the XML config block (if present).
     *
     * @param lines the lines to extract the snippet from.
     * @return the code snippet.
     */
    private static String getCodeSnippet(Collection<String> lines) {
        final String snippet = lines.stream()
                .dropWhile(line -> !hasCodeSnippetStart(line))
                .skip(1)
                .takeWhile(line -> !hasCodeSnippetEnd(line))
                .collect(Collectors.joining(ModuleJavadocParsingUtil.NEWLINE));

        // If no snippet was found (markers not present), return the file content
        // excluding the XML config block (if present)
        final String result;
        if (snippet.isBlank()) {
            final List<String> linesList = new ArrayList<>(lines);
            final int configEndIndex = linesList.indexOf(InlineConfigUtils.JAVA_CONFIG_END);
            if (configEndIndex >= 0) {
                // XML config block is present, return content after it
                result = String.join(ModuleJavadocParsingUtil.NEWLINE,
                        linesList.stream()
                                .skip(configEndIndex + 1)
                                .toList());
            }
            else {
                // No XML config block, return entire file
                result = String.join(ModuleJavadocParsingUtil.NEWLINE, linesList);
            }
        }
        else {
            result = snippet;
        }

        return result;
    }

    /**
     * Checks if the line contains a code snippet start delimiter.
     * Handles different comment formats for different file types.
     *
     * @param line the line to check.
     * @return true if the line contains a start delimiter.
     */
    private static boolean hasCodeSnippetStart(String line) {
        return line.contains(CODE_SNIPPET_START)
                || line.contains("<!--xdoc section &#45;&#45; start-->");
    }

    /**
     * Checks if the line contains a code snippet end delimiter.
     * Handles different comment formats for different file types.
     *
     * @param line the line to check.
     * @return true if the line contains an end delimiter.
     */
    private static boolean hasCodeSnippetEnd(String line) {
        return line.contains(CODE_SNIPPET_END)
                || line.contains("<!--xdoc section &#45;&#45; end-->");
    }

    /**
     * Writes the given snippet inside a formatted source block.
     *
     * @param sink the sink to write to.
     * @param snippet the snippet to write.
     */
    private static void writeSnippet(Sink sink, String snippet) {
        sink.rawText("<div class=\"wrapper\">");
        final boolean isXml = isXml(snippet);

        final String languageClass;
        if (isXml) {
            languageClass = "language-xml";
        }
        else {
            languageClass = "language-java";
        }
        sink.rawText("<pre class=\"prettyprint\"><code class=\"" + languageClass + "\">"
            + ModuleJavadocParsingUtil.NEWLINE);
        sink.rawText(escapeHtml(snippet).trim() + ModuleJavadocParsingUtil.NEWLINE);
        sink.rawText("</code></pre>");
        sink.rawText("</div>");
    }

    /**
     * Escapes HTML special characters in the snippet.
     *
     * @param snippet the snippet to escape.
     * @return the escaped snippet.
     */
    private static String escapeHtml(String snippet) {
        return snippet.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    /**
     * Determines if the given snippet is likely an XML fragment.
     *
     * @param snippet the code snippet to analyze.
     * @return {@code true} if the snippet appears to be XML, otherwise {@code false}.
     */
    private static boolean isXml(String snippet) {
        return XML_PATTERN.matcher(snippet.trim()).matches();
    }

}
