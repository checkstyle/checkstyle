///
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
///

package com.puppycrawl.tools.checkstyle.internal.utils;

import java.io.File;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Set;

import org.apache.maven.doxia.parser.Parser;
import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.doxia.sink.SinkFactory;
import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.util.ReaderFactory;

import com.puppycrawl.tools.checkstyle.site.XdocsTemplateParser;
import com.puppycrawl.tools.checkstyle.site.XdocsTemplateSinkFactory;

/**
 * Generates xdoc content from xdoc templates.
 * This module will be removed once
 * <a href="https://github.com/checkstyle/checkstyle/issues/13426">#13426</a> is resolved.
 */
public final class XdocGenerator {
    private static final String XDOCS_TEMPLATE_HINT = "xdocs-template";

    private XdocGenerator() {
    }

    public static void generateXdocContent(File temporaryFolder) throws Exception {
        final PlexusContainer plexus = new DefaultPlexusContainer();
        final Set<Path> templatesFilePaths = XdocUtil.getXdocsTemplatesFilePaths();

        for (Path path : templatesFilePaths) {
            final String pathToFile = path.toString();
            final File inputFile = new File(pathToFile);
            final File outputFile = new File(pathToFile.replace(".template", ""));
            final File tempFile = new File(temporaryFolder, outputFile.getName());
            tempFile.deleteOnExit();
            final XdocsTemplateSinkFactory sinkFactory = (XdocsTemplateSinkFactory)
                    plexus.lookup(SinkFactory.ROLE, XDOCS_TEMPLATE_HINT);
            final Sink sink = sinkFactory.createSink(tempFile.getParentFile(),
                    tempFile.getName(), String.valueOf(StandardCharsets.UTF_8));
            final XdocsTemplateParser parser = (XdocsTemplateParser)
                    plexus.lookup(Parser.ROLE, XDOCS_TEMPLATE_HINT);
            try (Reader reader = ReaderFactory.newReader(inputFile,
                    String.valueOf(StandardCharsets.UTF_8))) {
                parser.parse(reader, sink);
            }
            finally {
                sink.close();
            }
            final StandardCopyOption copyOption = StandardCopyOption.REPLACE_EXISTING;
            Files.copy(tempFile.toPath(), outputFile.toPath(), copyOption);
        }
    }
}
