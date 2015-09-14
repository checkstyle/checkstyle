package com.google.checkstyle.test.chapter7javadoc.rule713atclauses;

import java.io.File;

import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTagContinuationIndentationCheck;

public class JavadocTagContinuationIndentationTest extends BaseCheckTestSupport{

    @Test
    public void testWithDefaultConfiguration() throws Exception {
        ConfigurationBuilder builder = new ConfigurationBuilder(new File("src/it/"));
        String msg = getCheckMessage(JavadocTagContinuationIndentationCheck.class,
                "tag.continuation.indent", 4);

        final String[] expected = {
            "47: " + msg,
            "109: " + msg,
            "112: " + msg,
            "203: " + msg,
            "206: " + msg,
            "221: " + msg,
            "223: " + msg,
            "285: " + msg,
            "288: " + msg,
            "290: " + msg,
            "310: " + msg,
            "322: " + msg,
        };

        Configuration checkConfig = builder.getCheckConfig("JavadocTagContinuationIndentation");
        String filePath = builder.getFilePath("InputJavaDocTagContinuationIndentation");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
