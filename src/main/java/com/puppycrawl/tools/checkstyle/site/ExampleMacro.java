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
import java.util.List;
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
    private static final String CONFIG_DELIMITER_START = "/*xml";
    /** Ending delimiter for config snippets. */
    private static final String CONFIG_DELIMITER_END = "*/";
    /** Starting delimiter for code snippets. */
    private static final String CODE_DELIMITER_START = "// xdoc section -- start";
    /** Ending delimiter for code snippets. */
    private static final String CODE_DELIMITER_END = "// xdoc section -- end";
    /** Newline character. */
    private static final String NEWLINE = "\n";

    @Override
    public void execute(Sink sink, MacroRequest request) throws MacroExecutionException {
        final String examplePath = (String) request.getParameter("path");
        final String exampleType = (String) request.getParameter("type");
        final List<String> lines = readFile(examplePath);

        if ("config".equals(exampleType)) {
            writeConfigSnippet(sink, lines);
        }
        else if ("code".equals(exampleType)) {
            writeCodeSnippet(sink, lines);
        }
        else {
            throw new MacroExecutionException("Unknown example type: " + exampleType);
        }
    }

    /**
     *
     * @param sink
     * @param lines
     */
    private static void writeConfigSnippet(Sink sink, List<String> lines) {
        final String config = getConfig(lines);
        writeSnippet(sink, config);
    }

    /**
     * Extract the config snippet between the start and end delimiters.
     *
     * @param lines the lines to extract the snippet from.
     * @return the config snippet.
     */
    private static String getConfig(List<String> lines) {
        return lines.stream()
                .dropWhile(line -> !line.contains(CONFIG_DELIMITER_START))
                .skip(1)
                .takeWhile(line -> !line.contains(CONFIG_DELIMITER_END))
                .collect(Collectors.joining(NEWLINE));
    }

    /**
     * Extract and write the code snippet between the start and end delimiters.
     *
     * @param sink the sink to write to.
     * @param lines the lines to extract the snippet from.
     */
    private static void writeCodeSnippet(Sink sink, List<String> lines) {
        final String code = getCode(lines);
        writeSnippet(sink, code);
    }

    /**
     * Extract the code snippet between the start and end delimiters.
     *
     * @param lines the lines to extract the snippet from.
     * @return the code snippet.
     */
    private static String getCode(List<String> lines) {
        return lines.stream()
                .dropWhile(line -> !line.contains(CODE_DELIMITER_START))
                .skip(1)
                .takeWhile(line -> !line.contains(CODE_DELIMITER_END))
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
        final String text = NEWLINE + String.join(NEWLINE, snippet.stripTrailing()) + NEWLINE;
        sink.text(text);
        sink.verbatim_();
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
        catch (IOException ex) {
            throw new MacroExecutionException("Failed to read " + path, ex);
        }
    }
}
