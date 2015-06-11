package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck;
import com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck;
import com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyOption;

public class LeftCurlyRightCurlyTest extends BaseCheckTestSupport{
    
    static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void leftCurlyBracesTest() throws IOException, Exception {
        
        String checkMessage = getCheckMessage(LeftCurlyCheck.class, "line.previous", "{");
        final String[] expected = {
            "4:1: " + checkMessage,
            "7:5: " + checkMessage,
            "13:5: " + checkMessage,
            "26:5: " + checkMessage,
            "43:5: " + checkMessage,
            "61:5: " + checkMessage,
            "97:5: " + checkMessage,
        };
        
        Configuration checkConfig = builder.getCheckConfig("LeftCurly");
        String filePath = builder.getFilePath("LeftCurlyInput_Braces");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
    
    @Test
    public void leftCurlyAnnotationsTest() throws IOException, Exception {
        
        String checkMessagePrevious = getCheckMessage(LeftCurlyCheck.class, "line.previous", "{");
        final String[] expected = {
            "10:1: " + checkMessagePrevious,
            "14:5: " + checkMessagePrevious,
            "21:5: " + checkMessagePrevious,
            "27:5: " + checkMessagePrevious,
            "50:5: " + checkMessagePrevious,
        };
        
        Configuration checkConfig = builder.getCheckConfig("LeftCurly");
        String filePath = builder.getFilePath("LeftCurlyInput_Annotations");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
    
    @Test
    public void leftCurlyMethodsTest() throws IOException, Exception {
        

        String checkMessagePrevious = getCheckMessage(LeftCurlyCheck.class, "line.previous", "{");
        final String[] expected = {
            "4:1: " + checkMessagePrevious,
            "9:5: " + checkMessagePrevious,
            "16:5: " + checkMessagePrevious,
            "19:5: " + checkMessagePrevious,
            "23:5: " + checkMessagePrevious,
            "31:1: " + checkMessagePrevious,
            "33:5: " + checkMessagePrevious,
            "38:9: " + checkMessagePrevious,
            "41:9: " + checkMessagePrevious,
            "45:9: " + checkMessagePrevious,
            "57:5: " + checkMessagePrevious,
            "61:5: " + checkMessagePrevious,
            "69:5: " + checkMessagePrevious,
            "72:5: " + checkMessagePrevious, 
            "76:5: " + checkMessagePrevious,
         };

        Configuration checkConfig = builder.getCheckConfig("LeftCurly");
        String filePath = builder.getFilePath("LeftCurlyInput_Method");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void rightCurlyTestAlone() throws Exception {
        DefaultConfiguration newCheckConfig = createCheckConfig(RightCurlyCheck.class);
        newCheckConfig.addAttribute("option", RightCurlyOption.ALONE.toString());
        newCheckConfig.addAttribute("tokens", "CLASS_DEF, METHOD_DEF, CTOR_DEF");
        
        String checkMessageNew = getCheckMessage(RightCurlyCheck.class, "line.new", "}");
        final String[] expected = {
            "97:6: " + checkMessageNew,
            "108:6: " + checkMessageNew,
            "122:6: " + checkMessageNew,
        };

        String filePath = builder.getFilePath("RightCurlyInput_Other");
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(newCheckConfig, filePath, expected, warnList);
    }
}


