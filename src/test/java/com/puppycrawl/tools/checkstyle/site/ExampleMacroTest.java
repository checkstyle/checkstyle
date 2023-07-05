package com.puppycrawl.tools.checkstyle.site;

import java.io.File;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Set;

import org.apache.maven.doxia.module.xdoc.XdocParser;
import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.PlexusContainer;
import org.apache.maven.doxia.parser.Parser;
import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.doxia.sink.SinkFactory;
import org.codehaus.plexus.util.ReaderFactory;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.internal.utils.XdocUtil;

public class ExampleMacroTest {
    @Test
    public void executeMacroForAllTemplates() throws Exception {
        final PlexusContainer plexus = new DefaultPlexusContainer();
        final File userDir = new File(System.getProperty ("user.dir"));

        final Set<Path> templatesFilePaths = XdocUtil.getXdocsTemplatesFilePaths();
        for (Path path : templatesFilePaths) {
            final File inputFile = new File(userDir, path.toString());
            final File outputFile = new File(userDir, path.toString().replace(".template", ""));

            final SinkFactory sinkFactory = (SinkFactory) plexus.lookup(SinkFactory.ROLE, "xdoc");
            final Sink sink = sinkFactory.createSink(outputFile.getParentFile(), outputFile.getName());
            final XdocParser parser = (XdocParser) plexus.lookup(Parser.ROLE, "xdoc");
            final Reader reader = ReaderFactory.newReader(inputFile, "UTF-8");
            parser.parse(reader, sink);
        }
    }
}
