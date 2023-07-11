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
import org.codehaus.plexus.component.annotations.Component;

import org.apache.maven.doxia.sink.impl.SinkEventAttributeSet;

@Component(role = Macro.class, hint = "example")
public class ExampleMacro extends AbstractMacro {
    private static final String CONFIG_DELIMITER_START = "/*xml";
    private static final String CONFIG_DELIMITER_END = "*/";
    private static final String CODE_DELIMITER_START = "// xdoc section -- start";
    private static final String CODE_DELIMITER_END = "// xdoc section -- end";

    @Override
    public void execute(Sink sink, MacroRequest request) throws MacroExecutionException {
        final String examplePath = (String) request.getParameter("path");
        final String exampleType = (String) request.getParameter("type");
        final List<String> lines = readFile(examplePath);

        if ("config".equals(exampleType)) {
            placeConfigSnippet(sink, lines);
        }
        else if ("code".equals(exampleType)) {
            placeCodeSnippet(sink, lines);
        }
        else {
            throw new MacroExecutionException("Unknown example type: " + exampleType);
        }
    }

    private static void placeConfigSnippet(Sink sink, List<String> lines) {
        final String config = getConfig(lines);
        placeSnippet(sink, config);
    }

    private static String getConfig(List<String> lines) {
        return lines.stream()
                .dropWhile(line -> !line.contains(CONFIG_DELIMITER_START))
                .skip(1)
                .takeWhile(line -> !line.contains(CONFIG_DELIMITER_END))
                .collect(Collectors.joining("\n"));
    }

    private static void placeCodeSnippet(Sink sink, List<String> lines) {
        final String code = getCode(lines);
        placeSnippet(sink, code);
    }

    private static String getCode(List<String> lines) {
        return lines.stream()
                .dropWhile(line -> !line.contains(CODE_DELIMITER_START))
                .skip(1)
                .takeWhile(line -> !line.contains(CODE_DELIMITER_END))
                .collect(Collectors.joining("\n"));
    }

    private static void placeSnippet(Sink sink, String config) {
        sink.verbatim(SinkEventAttributeSet.BOXED);
        final String text = "\n" + String.join("\n", config.stripTrailing()) + "\n";
        sink.text(text);
        sink.verbatim_();
    }

    private static List<String> readFile(String examplePath) throws MacroExecutionException {
        try {
            final Path exampleFilePath = Path.of(examplePath);
            return Files.readAllLines(exampleFilePath);
        }
        catch (IOException ex) {
            throw new MacroExecutionException("Failed to read " + examplePath, ex);
        }
    }
}
