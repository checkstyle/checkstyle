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
    private static final String CODE_SNIPPET_START = "xdoc section - start";

    /** Ending delimiter for code snippets. */
    private static final String CODE_SNIPPET_END = "xdoc section - end";

    /** File extension for .properties files. */
    private static final String PROPERTIES_EXTENSION = ".properties";

    /** The {@code prettyprint} language class for XML content. */
    private static final String LANGUAGE_XML = "language-xml";

    /** The path of the last file. */
    private String lastPath = "";

    /** The line contents of the last file. */
    private List<String> lastLines = new ArrayList<>();

    /**
     * Creates a new {@code ExampleMacro} instance.
     */
    public ExampleMacro() {
        // no code by default
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
            writeConfigSnippet(sink, lines, path);
        }
        else if ("code".equals(type)) {
            writeCodeSnippet(sink, lines, path);
        }
        else if ("raw".equals(type)) {
            final String content = String.join(ModuleJavadocParsingUtil.NEWLINE, lines);
            writeSnippet(sink, content, getExtension(path), false);
        }
        else {
            final String message = String.format(Locale.ROOT, "Unknown example type: %s", type);
            throw new MacroExecutionException(message);
        }
    }

    /**
     * Extracts the config snippet from the given lines and writes it to the sink.
     *
     * @param sink the sink to write to.
     * @param lines the lines of the source file.
     * @param path the file path, used for extension detection and error messages.
     * @throws MacroExecutionException if the config block is invalid or empty.
     */
    private static void writeConfigSnippet(Sink sink, List<String> lines, String path)
            throws MacroExecutionException {
        final String extension = getExtension(path);
        final String config;
        try {
            config = getConfigSnippet(lines, extension);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            final String message = String.format(Locale.ROOT,
                    "%s (in %s)", illegalArgumentException.getMessage(), path
            );
            throw new MacroExecutionException(message, illegalArgumentException);
        }

        if (config.isBlank()) {
            final String message = String.format(Locale.ROOT,
                    "Empty config snippet from %s, check"
                            + " for xml config snippet delimiters in input file.", path
            );
            throw new MacroExecutionException(message);
        }

        writeSnippet(sink, config, extension, true);
    }

    /**
     * Extracts the code snippet from the given lines and writes it to the sink.
     *
     * @param sink the sink to write to.
     * @param lines the lines of the source file.
     * @param path the file path, used for tab-replacement detection and error messages.
     * @throws MacroExecutionException if the code snippet is empty.
     */
    private static void writeCodeSnippet(Sink sink, List<String> lines, String path)
            throws MacroExecutionException {
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

        writeSnippet(sink, code, getExtension(path), false);
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
     * Extracts the file extension (including the leading dot) from a file path, or an
     * empty string if the path has no extension.
     *
     * @param path the file path.
     * @return the file extension, e.g. {@code ".xml"}, or {@code ""} if none.
     */
    private static String getExtension(String path) {
        final int dotIndex = path.lastIndexOf('.');
        String result = "";
        if (dotIndex >= 0) {
            result = path.substring(dotIndex);
        }
        return result;
    }

    /**
     * Extracts the configuration snippet from the given lines.
     *
     * @param lines the lines to extract the config from.
     * @param extension the file extension (e.g. {@code ".xml"}), used to decide which
     *     delimiter conventions are valid.
     * @return the configuration snippet.
     * @throws IllegalArgumentException if the config block is invalid.
     */
    private static String getConfigSnippet(Collection<String> lines, String extension) {
        final List<String> linesList = new ArrayList<>(lines);
        final InlineConfigUtils.MatchedDelimiter matched =
                InlineConfigUtils.matchDelimiter(linesList, extension);

        if (matched == null) {
            final String message = String.format(Locale.ROOT,
                    "No valid config block found. Expected the first line to be %s.",
                    InlineConfigUtils.describeExpectedDelimiters(extension)
            );
            throw new IllegalArgumentException(message);
        }

        final int endIndex = InlineConfigUtils.getConfigEndIndex(linesList, matched);
        if (endIndex <= 0) {
            final String message = String.format(Locale.ROOT,
                    "Config start delimiter found but no matching end delimiter \"%s\".",
                    matched.end()
            );
            throw new IllegalArgumentException(message);
        }

        final List<String> configLines = linesList.subList(1, endIndex);
        final String result;
        if (PROPERTIES_EXTENSION.equals(extension)) {
            result = String.join(ModuleJavadocParsingUtil.NEWLINE,
                    InlineConfigUtils.stripPropertiesCommentPrefix(configLines));
        }
        else {
            result = String.join(ModuleJavadocParsingUtil.NEWLINE, configLines);
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
     *
     * @param line the line to check.
     * @return true if the line contains a start delimiter.
     */
    private static boolean hasCodeSnippetStart(String line) {
        return line.contains(CODE_SNIPPET_START);
    }

    /**
     * Checks if the line contains a code snippet end delimiter.
     *
     * @param line the line to check.
     * @return true if the line contains an end delimiter.
     */
    private static boolean hasCodeSnippetEnd(String line) {
        return line.contains(CODE_SNIPPET_END);
    }

    /**
     * Writes the given snippet inside a formatted source block, choosing syntax
     * highlighting based on the source file's extension. Config snippets (the content
     * inside a Java-comment-style config block) are always XML regardless of the
     * target file's extension, except for {@code .properties} files, whose bare
     * {@code #}-comment config block is not highlighted at all.
     *
     * @param sink the sink to write to.
     * @param snippet the snippet to write.
     * @param extension the source file extension, e.g. {@code ".xml"}, {@code ".sql"}.
     * @param isConfig true if the snippet is a config block rather than a code block.
     */
    private static void writeSnippet(Sink sink, String snippet, String extension,
                                     boolean isConfig) {
        sink.rawText("<div class=\"wrapper\">");

        final String languageClass = determineLanguageClass(extension, isConfig);
        final String codeOpenTag;
        if (languageClass == null) {
            codeOpenTag = "<pre class=\"prettyprint\"><code>";
        }
        else {
            codeOpenTag = "<pre class=\"prettyprint\"><code class=\"" + languageClass + "\">";
        }

        sink.rawText(codeOpenTag + ModuleJavadocParsingUtil.NEWLINE);
        sink.rawText(escapeHtml(snippet).trim() + ModuleJavadocParsingUtil.NEWLINE);
        sink.rawText("</code></pre>");
        sink.rawText("</div>");
    }

    /**
     * Determines the {@code prettyprint} language class to use for a snippet, based on
     * the source file's extension.
     *
     * @param extension the source file extension, e.g. {@code ".xml"}, {@code ".sql"}.
     * @param isConfig true if the snippet is a config block (always XML syntax, except
     *     for {@code .properties} files.
     * @return the language class to apply, or {@code null} if the snippet should not be
     *     syntax-highlighted.
     */
    private static String determineLanguageClass(String extension, boolean isConfig) {
        final String result;
        if (PROPERTIES_EXTENSION.equals(extension)) {
            result = null;
        }
        else if (isConfig) {
            // Config blocks are always <module> XML, regardless of target file type.
            result = LANGUAGE_XML;
        }
        else if (".xml".equals(extension)) {
            result = LANGUAGE_XML;
        }
        else if (".sql".equals(extension)) {
            result = "language-sql";
        }
        else {
            result = "language-java";
        }
        return result;
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

}
