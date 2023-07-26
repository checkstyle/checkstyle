///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
import org.apache.maven.doxia.sink.impl.SinkEventAttributeSet;
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
            final String config = getSnippetBetweenDelimiters(lines,
                    XML_CONFIG_START, XML_CONFIG_END);
            writeSnippet(sink, config);
        }
        else if ("code".equals(type)) {
            final String code = getSnippetBetweenDelimiters(lines,
                    CODE_SNIPPET_START, CODE_SNIPPET_END);
            writeSnippet(sink, code);
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
     * Extract a snippet between the given start and end delimiters.
     * The lines containing the delimiter are not included in the snippet.
     *
     * @param lines the lines to extract the snippet from.
     * @param startingDelimiter the starting delimiter.
     * @param endingDelimiter the ending delimiter.
     * @return the snippet.
     */
    private static String getSnippetBetweenDelimiters(
            Collection<String> lines, String startingDelimiter, String endingDelimiter) {
        return lines.stream()
                .dropWhile(line -> !line.contains(startingDelimiter))
                .skip(1)
                .takeWhile(line -> !line.contains(endingDelimiter))
                .collect(Collectors.joining(NEWLINE));
    }

    /**
     * Write the given snippet to the file inside a source block.
     *
     * @param sink the sink to write to.
     * @param snippet the snippet to write.
     */
    private static void writeSnippet(Sink sink, String snippet) {
        sink.verbatim(SinkEventAttributeSet.BOXED);
        final String text = NEWLINE
                + String.join(NEWLINE, snippet.stripTrailing(), INDENTATION);
        sink.text(text);
        sink.verbatim_();
    }
}
