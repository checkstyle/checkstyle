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

import java.io.File;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Set;

import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.PlexusContainer;
import org.apache.maven.doxia.parser.Parser;
import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.doxia.sink.SinkFactory;
import org.codehaus.plexus.util.ReaderFactory;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.internal.utils.XdocUtil;

public class XdocsTemplateParserTest {
    private static final String XDOCS_ENCODING = "UTF-8";
    private static final String XDOCS_HINT = "xdocs-template";

    @Test
    public void parseAllTemplates() throws Exception {
        final PlexusContainer plexus = new DefaultPlexusContainer();
        final File userDir = new File(System.getProperty ("user.dir"));
        final Set<Path> templatesFilePaths = XdocUtil.getXdocsTemplatesFilePaths();

        for (Path path : templatesFilePaths) {
            final String pathToFile = path.toString();
            if (!pathToFile.contains("constantname")) {
                continue;
            }
            final File inputFile = new File(userDir, pathToFile);
            final File outputFile = new File(userDir, pathToFile.replace(".template", ""));

            final XdocsTemplateSinkFactory sinkFactory = (XdocsTemplateSinkFactory)
                    plexus.lookup(SinkFactory.ROLE, XDOCS_HINT);
            final Sink sink = sinkFactory.createSink(outputFile.getParentFile(),
                    outputFile.getName(), XDOCS_ENCODING);
            final XdocsTemplateParser parser = (XdocsTemplateParser)
                    plexus.lookup(Parser.ROLE, XDOCS_HINT);
            final Reader reader = ReaderFactory.newReader(inputFile, XDOCS_ENCODING);

            parser.parse(reader, sink);
        }
    }
}
