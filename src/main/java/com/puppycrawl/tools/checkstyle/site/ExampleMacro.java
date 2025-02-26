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

/**
 * A macro that inserts a snippet of code or configuration from a file.
 */
@Component(role = Macro.class, hint = "example")
public class ExampleMacro extends AbstractMacro {

    /** Starting delimiter for config snippets. */
    private static final String XML_CONFIG_START = "/*xml";

    /** Ending delimiter for config snippets. */
    private static final String XML_CONFIG_END = "*/";

    /** Starting delimiter for code snippets. */
    private static final String CODE_SNIPPET_START = "// xdoc section -- start";

    /** Ending delimiter for code snippets. */
    private static final String CODE_SNIPPET_END = "// xdoc section -- end";

    /** Newline character. */
    private static final String NEWLINE = System.lineSeparator();

    /** Eight whitespace characters. All example source tags are indented 8 spaces. */
    private static final String INDENTATION = "        ";

    /** The pattern of xml code blocks. */
    private static final Pattern XML_PATTERN = Pattern.compile(
            "^\\s*(<!DOCTYPE\\s+.*?>|<\\?xml\\s+.*?>|<module\\s+.*?>)\\s*",
            Pattern.DOTALL
    );

    /** The path of the last file. */
    private String lastPath = "";

    /** The line contents of the last file. */
    private List<String> lastLines = new ArrayList<>();

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
            final String config = getConfigSnippet(lines);

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
            final String content = String.join(NEWLINE, lines);
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
     * Extract a configuration snippet from the given lines. Config delimiters use the whole
     * line for themselves and have no indentation. We use equals() instead of contains()
     * to be more strict because some examples contain those delimiters.
     *
     * @param lines the lines to extract the snippet from.
     * @return the configuration snippet.
     */
    private static String getConfigSnippet(Collection<String> lines) {
        return lines.stream()
                .dropWhile(line -> !XML_CONFIG_START.equals(line))
                .skip(1)
                .takeWhile(line -> !XML_CONFIG_END.equals(line))
                .collect(Collectors.joining(NEWLINE));
    }

    /**
     * Extract a code snippet from the given lines. Code delimiters can be indented, so
     * we use contains() instead of equals().
     *
     * @param lines the lines to extract the snippet from.
     * @return the code snippet.
     */
    private static String getCodeSnippet(Collection<String> lines) {
        return lines.stream()
                .dropWhile(line -> !line.contains(CODE_SNIPPET_START))
                .skip(1)
                .takeWhile(line -> !line.contains(CODE_SNIPPET_END))
                .collect(Collectors.joining(NEWLINE));
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
        sink.rawText("<pre class=\"prettyprint\"><code class=\"" + languageClass + "\">" + NEWLINE);
        sink.rawText(escapeHtml(snippet).trim() + NEWLINE);
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
