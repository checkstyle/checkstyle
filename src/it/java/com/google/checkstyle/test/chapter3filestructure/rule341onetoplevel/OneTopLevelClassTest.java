package com.google.checkstyle.test.chapter3filestructure.rule341onetoplevel;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.design.OneTopLevelClassCheck;

public class OneTopLevelClassTest extends BaseCheckTestSupport{
    
    static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void badTest() throws IOException, Exception {
        
        Class<OneTopLevelClassCheck> clazz = OneTopLevelClassCheck.class;
        String messageKey = "one.top.level.class";

        final String[] expected = {
            "25: " + getCheckMessage(clazz, messageKey, "NoSuperClone"),
            "33: " + getCheckMessage(clazz, messageKey, "InnerClone"),
            "50: " + getCheckMessage(clazz, messageKey, "CloneWithTypeArguments"),
            "55: " + getCheckMessage(clazz, messageKey, "CloneWithTypeArgumentsAndNoSuper"),
            "60: " + getCheckMessage(clazz, messageKey, "MyClassWithGenericSuperMethod"),
            "77: " + getCheckMessage(clazz, messageKey, "AnotherClass"),
        };
        
        Configuration checkConfig = builder.getCheckConfig("OneTopLevelClass");
        String filePath = builder.getFilePath("OneTopLevelClassInput_Basic");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void goodTest() throws IOException, Exception {
        
        final String[] expected = {};
        
        Configuration checkConfig = builder.getCheckConfig("OneTopLevelClass");
        String filePath = builder.getFilePath("OneTopLevelClassInputGood");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
    
    @Test
    public void bad2Test() throws IOException, Exception {
    	
    	Class<OneTopLevelClassCheck> clazz = OneTopLevelClassCheck.class;
        String messageKey = "one.top.level.class";
        
        final String[] expected = {
            "4: " + getCheckMessage(clazz, messageKey, "FooEnum"),
        };
        
        Configuration checkConfig = builder.getCheckConfig("OneTopLevelClass");
        String filePath = builder.getFilePath("OneTopLevelClassBad2");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
    
    @Test
    public void bad3Test() throws IOException, Exception {
        
    	Class<OneTopLevelClassCheck> clazz = OneTopLevelClassCheck.class;
        String messageKey = "one.top.level.class";
        
        final String[] expected = {
        	"5: " + getCheckMessage(clazz, messageKey, "FooIn"),
            "7: " + getCheckMessage(clazz, messageKey, "FooClass"),
        };
        
        Configuration checkConfig = builder.getCheckConfig("OneTopLevelClass");
        String filePath = builder.getFilePath("OneTopLevelClassBad3");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
