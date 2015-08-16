package com.google.checkstyle.test.base;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class ConfigValidationTest extends BaseCheckTestSupport {
    @Test
    public void testGoogleChecks() throws IOException, Exception {
        ConfigurationBuilder builder = new ConfigurationBuilder(new File("src/it/"));
        final Configuration checkerConfig = builder.config;
        final Checker c = new Checker();
        c.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        c.configure(checkerConfig);
        c.addListener(new BriefLogger(stream));

        final List<File> files = builder.getFiles();
        
        //runs over all input files;
        //as severity level is "warning", no errors expected
        verify(c, files.toArray(new File[files.size()]), "", new String[0], null);
    }
}
