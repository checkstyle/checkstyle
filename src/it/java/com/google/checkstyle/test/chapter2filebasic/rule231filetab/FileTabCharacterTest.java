package com.google.checkstyle.test.chapter2filebasic.rule231filetab;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck;

public class FileTabCharacterTest extends BaseCheckTestSupport{
    
    static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }
    
    @Override
    protected DefaultConfiguration createCheckerConfig(
        Configuration aCheckConfig)
    {
        final DefaultConfiguration dc = new DefaultConfiguration("root");
        dc.addChild(aCheckConfig);
        return dc;
    }

    @Test
    public void fileTabTest() throws IOException, Exception {

        final DefaultConfiguration checkConfig = createConfig(true);
        final String[] expected = {
            "8:25: " + getCheckMessage(FileTabCharacterCheck.class, "containsTab"),
            "51:5: " + getCheckMessage(FileTabCharacterCheck.class, "containsTab"),
            "121:35: " + getCheckMessage(FileTabCharacterCheck.class, "containsTab"),
            "122:64: " + getCheckMessage(FileTabCharacterCheck.class, "containsTab"),
            "130:9: " + getCheckMessage(FileTabCharacterCheck.class, "containsTab"),
            "131:10: " + getCheckMessage(FileTabCharacterCheck.class, "containsTab"),
            "132:1: " + getCheckMessage(FileTabCharacterCheck.class, "containsTab"),
            "133:3: " + getCheckMessage(FileTabCharacterCheck.class, "containsTab"),
            "134:3: " + getCheckMessage(FileTabCharacterCheck.class, "containsTab"),
        };
        
        String filePath = builder.getFilePath("FileTabCharacterInput");
        Integer[] warnList = builder.getLinesWithWarn(filePath);
		verify(createChecker(checkConfig),filePath,
            expected, warnList);
    } 

    /**
     * Creates a configuration that is functionally close to that in the docs.
     */
    private DefaultConfiguration createConfig(boolean verbose)
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FileTabCharacterCheck.class);
        checkConfig.addAttribute("eachLine", Boolean.toString(verbose));
        return checkConfig;
    }
}
