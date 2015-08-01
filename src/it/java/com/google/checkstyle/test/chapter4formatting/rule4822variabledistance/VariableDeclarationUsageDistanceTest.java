package com.google.checkstyle.test.chapter4formatting.rule4822variabledistance;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.coding.VariableDeclarationUsageDistanceCheck;

public class VariableDeclarationUsageDistanceTest extends BaseCheckTestSupport{
    
    static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void arrayTypeStyleTest() throws IOException, Exception {
        
        String msg = "variable.declaration.usage.distance";
        String msgExt = "variable.declaration.usage.distance.extend";
        Class<VariableDeclarationUsageDistanceCheck> clazz = VariableDeclarationUsageDistanceCheck.class;

        final String[] expected = {
                "71: " + getCheckMessage(clazz, msgExt, "count", 4, 3),
                "219: " + getCheckMessage(clazz, msgExt, "t", 5, 3),
                "479: " + getCheckMessage(clazz, msgExt, "myOption", 7, 3),
                "491: " + getCheckMessage(clazz, msgExt, "myOption", 6, 3),
        };
        
        Configuration checkConfig = builder.getCheckConfig("VariableDeclarationUsageDistance");
        String filePath = builder.getFilePath("InputVariableDeclarationUsageDistanceCheck");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
