package com.google.checkstyle.test.base;

import static org.apache.commons.lang3.ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY;
import static org.apache.commons.lang3.ArrayUtils.EMPTY_STRING_ARRAY;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class ConfigValidationTest extends BaseCheckTestSupport {
    @Test
    public void testGoogleChecks() throws Exception {
        ConfigurationBuilder builder = new ConfigurationBuilder(new File("src/it/"));
        final Configuration checkerConfig = builder.config;
        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(new BriefLogger(stream));

        final List<File> files = builder.getFiles();
        
        //runs over all input files;
        //as severity level is "warning", no errors expected
        verify(checker, files.toArray(new File[files.size()]), "",
                EMPTY_STRING_ARRAY, EMPTY_INTEGER_OBJECT_ARRAY);
    }
}
