package com.google.checkstyle.test.chapter2filebasic.rule21filename;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.OuterTypeFilenameCheck;

public class OuterTypeFilenameTest extends BaseCheckTestSupport{
    
    static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void outerTypeFilenameTest_1() throws IOException, Exception {

        String msg = getCheckMessage(OuterTypeFilenameCheck.class, "type.file.mismatch");

        final String[] expected = {};
        
        Configuration checkConfig = builder.getCheckConfig("OuterTypeFilename");
        String filePath = builder.getFilePath("OuterTypeFilenameInput_1");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    } 
    
    @Test
    public void outerTypeFilenameTest_2() throws IOException, Exception {
         
        final String[] expected = {};
        
        Configuration checkConfig = builder.getCheckConfig("OuterTypeFilename");
        String filePath = builder.getFilePath("OuterTypeFilenameInput_2");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
    
    @Test
    public void outerTypeFilenameTest_3() throws IOException, Exception {
         
        final String[] expected = {
            "3: " + getCheckMessage(OuterTypeFilenameCheck.class, "type.file.mismatch"), 
        };
        
        Configuration checkConfig = builder.getCheckConfig("OuterTypeFilename");
        String filePath = builder.getFilePath("OuterTypeFilenameInput_3");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
