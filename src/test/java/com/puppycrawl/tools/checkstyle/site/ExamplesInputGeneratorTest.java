package com.puppycrawl.tools.checkstyle.site;

import java.io.File;
import java.io.Reader;

import org.apache.maven.doxia.module.xdoc.XdocParser;
import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.PlexusContainer;
import org.apache.maven.doxia.parser.Parser;
import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.doxia.sink.SinkFactory;
import org.codehaus.plexus.util.ReaderFactory;
import org.junit.jupiter.api.Test;

public class ExamplesInputGeneratorTest {
    @Test
    public void swapMacrosWithExamples() throws Exception {
        PlexusContainer plexus = new DefaultPlexusContainer();
        File userDir = new File(System.getProperty ("user.dir"));
        File inputFile = new File( userDir, "src/xdocs/checks/naming/abstractclassname.xml");
        File outputFile = new File( userDir,  "test.xdoc");

        SinkFactory sinkFactory = (SinkFactory) plexus.lookup(SinkFactory.ROLE, "xdoc");
        Sink sink = sinkFactory.createSink(outputFile.getParentFile(), outputFile.getName());
        Parser parser = (XdocParser) plexus.lookup(Parser.ROLE, "xdoc");
        Reader reader = ReaderFactory.newReader(inputFile, "UTF-8");
        parser.parse(reader, sink);
    }
}
