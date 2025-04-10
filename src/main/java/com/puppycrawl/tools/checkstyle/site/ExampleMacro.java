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
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    /** Path prefix for source examples. */
    private static final String SRC_XDOCS_EXAMPLES = "src/xdocs-examples/";

    /** File name prefix for example files. */
    private static final String EXAMPLE_PREFIX = "Example";

    /** HTML for a horizontal rule separator. */
    private static final String HR_SEPARATOR = "<hr class=\"example-separator\"/>";

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
            lines = readFile(SRC_XDOCS_EXAMPLES + path);
            lastPath = path;
            lastLines = lines;
        }

        if ("config".equals(type)) {
            processConfig(sink, path, lines);
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

            final int lastIndex = path.lastIndexOf('/');
            final String fileName = path.substring(lastIndex + 1);
            if (fileName.startsWith(EXAMPLE_PREFIX)) {
                final String dirPath = path.substring(0, lastIndex);
                addHrSeparator(sink, fileName, dirPath);
            }
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
     * Processes configuration-type snippets.
     *
     * @param sink  The Sink object to write to.
     * @param path  The file path.
     * @param lines The content lines of the file.
     * @throws MacroExecutionException If the configuration snippet is invalid.
     */
    private static void processConfig(Sink sink, String path, List<String> lines)
            throws MacroExecutionException {
        final String config = getConfigSnippet(lines);
        if (config.isBlank()) {
            final String message = String.format(Locale.ROOT,
                    "Empty config snippet from %s, "
                            + "check for xml config snippet delimiters in input file.", path);
            throw new MacroExecutionException(message);
        }
        writeSnippet(sink, config);
    }

    /**
     * Safely returns the file name (as a string) from the given file path.
     *
     * @param filePath the file path to check.
     * @return the file name as a String, or an empty string if unavailable.
     */
    private static String getFileNameSafe(Path filePath) {
        String result = "";
        final Path namePath = filePath.getFileName();
        if (namePath != null) {
            result = namePath.toString();
        }
        return result;
    }

    /**
     * Safely extracts the example number from the file name of the given file path.
     *
     * @param filePath the file path to check.
     * @return the numeric part of the file name, or 0 if not found.
     */
    private static int getExampleNumberSafe(Path filePath) {
        return extractExampleNumber(getFileNameSafe(filePath));
    }

    /**
     * Adds a horizontal rule separator, unless the current file is the last example.
     *
     * @param sink     the sink to write to.
     * @param fileName the current example file name.
     * @param dirPath  the directory path containing the examples.
     */
    private static void addHrSeparator(Sink sink, String fileName, String dirPath) {
        List<Path> examples = new ArrayList<>();
        boolean hrNeeded = true;

        try (Stream<Path> stream = Files.list(Path.of(SRC_XDOCS_EXAMPLES + dirPath))) {
            if (stream != null) {
                examples = stream
                        .filter(filePath -> {
                            final String name = getFileNameSafe(filePath);
                            return name.startsWith(EXAMPLE_PREFIX) && name.endsWith(".java");
                        })
                        .sorted(Comparator.comparingInt(ExampleMacro::getExampleNumberSafe))
                        .collect(Collectors.toCollection(ArrayList::new));
            }
        }
        catch (IOException ex) {
            hrNeeded = true;
        }

        if (!examples.isEmpty()) {
            final String lastExampleFile =
                    getFileNameSafe(examples.get(examples.size() - 1));
            hrNeeded = !fileName.equals(lastExampleFile);
        }
        if (dirPath.contains("resources/") && !dirPath.contains("noncompilable")
                && !fileName.equals(getFileNameSafe(examples.get(examples.size() - 1)))) {
            hrNeeded = true;
        }

        if (hrNeeded) {
            sink.rawText(HR_SEPARATOR);
        }
    }

    /**
     * Helper method to extract a numeric value from an example file name.
     * Assumes file names have the format "ExampleX.java", where X is a number.
     *
     * @param fileName the file name to process.
     * @return the numeric part of the example file name or 0 if not found.
     */
    private static int extractExampleNumber(String fileName) {
        int exampleNumber = 0;
        try {
            final String numberStr = fileName.replaceAll("[^0-9]", "");
            exampleNumber = Integer.parseInt(numberStr);
        }
        catch (NumberFormatException ignored) {
            // Default value of exampleNumber (0) will be used
        }
        return exampleNumber;
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
