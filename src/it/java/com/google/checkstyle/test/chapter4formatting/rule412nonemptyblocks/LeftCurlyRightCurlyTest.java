package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

import static com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck.MSG_KEY_LINE_PREVIOUS;
import static com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck.MSG_KEY_LINE_ALONE;
import static com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck.MSG_KEY_LINE_NEW;

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

public class LeftCurlyRightCurlyTest extends BaseCheckTestSupport {
    
    static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void leftCurlyBracesTest() throws IOException, Exception {

        final String[] expected = {
            "4:1: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 1),
            "7:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "13:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "26:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "43:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "61:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "97:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
        };

        Configuration checkConfig = builder.getCheckConfig("LeftCurly");
        String filePath = builder.getFilePath("LeftCurlyInput_Braces");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
    
    @Test
    public void leftCurlyAnnotationsTest() throws IOException, Exception {

        final String[] expected = {
            "10:1: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 1),
            "14:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "21:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "27:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "50:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
        };
        
        Configuration checkConfig = builder.getCheckConfig("LeftCurly");
        String filePath = builder.getFilePath("LeftCurlyInput_Annotations");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
    
    @Test
    public void leftCurlyMethodsTest() throws IOException, Exception {

        final String[] expected = {
            "4:1: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 1),
            "9:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "16:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "19:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "23:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "31:1: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 1),
            "33:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "38:9: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 9),
            "41:9: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 9),
            "45:9: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 9),
            "57:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "61:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "69:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "72:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "76:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
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

        final String[] expected = {
            "97:5: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_ALONE, "}", 5),
            "97:6: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_NEW, "}", 6),
            "108:5: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_ALONE, "}", 5),
            "108:6: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_NEW, "}", 6),
            "122:6: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_NEW, "}", 6),
        };

        String filePath = builder.getFilePath("RightCurlyInput_Other");
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(newCheckConfig, filePath, expected, warnList);
    }
}
