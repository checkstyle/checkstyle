package com.google.checkstyle.test.chapter3filestructure.rule333orderingandsoacing;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck;

public class CustomImportOrderTest extends BaseCheckTestSupport{

    static ConfigurationBuilder builder;
    Class<CustomImportOrderCheck> clazz = CustomImportOrderCheck.class;
    String msgSeparator = "custom.import.order.line.separator";
    String msgLex = "custom.import.order.lex";
    String msgOrder = "custom.import.order";
    String msgNongroup = "custom.import.order.nongroup.import";

    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void customImportTest_1() throws IOException, Exception {
        
        final String[] expected = {
            "4: " + getCheckMessage(clazz, msgLex, "java.awt.Button.ABORT"),
            "7: " + getCheckMessage(clazz, msgOrder, "STANDARD_JAVA_PACKAGE"),
            "8: " + getCheckMessage(clazz, msgOrder, "STANDARD_JAVA_PACKAGE"),
            "9: " + getCheckMessage(clazz, msgOrder, "STANDARD_JAVA_PACKAGE"),
            "10: " + getCheckMessage(clazz, msgOrder, "STANDARD_JAVA_PACKAGE"),
            "11: " + getCheckMessage(clazz, msgOrder, "STANDARD_JAVA_PACKAGE"),
            "12: " + getCheckMessage(clazz, msgOrder, "STANDARD_JAVA_PACKAGE"),
            "13: " + getCheckMessage(clazz, msgOrder, "STANDARD_JAVA_PACKAGE"),
            "14: " + getCheckMessage(clazz, msgOrder, "STANDARD_JAVA_PACKAGE"),
            "15: " + getCheckMessage(clazz, msgOrder, "STANDARD_JAVA_PACKAGE"),
            "16: " + getCheckMessage(clazz, msgOrder, "STANDARD_JAVA_PACKAGE"),
        };
        
        Configuration checkConfig = builder.getCheckConfig("CustomImportOrder");
        String filePath = builder.getFilePath("CustomImportOrderInput_1");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void customImportTest_2() throws IOException, Exception {
        
        final String[] expected = {
            "4: " + getCheckMessage(clazz, msgLex, "java.awt.Button.ABORT"),
            "7: " + getCheckMessage(clazz, msgOrder, "STANDARD_JAVA_PACKAGE"),
            "8: " + getCheckMessage(clazz, msgOrder, "STANDARD_JAVA_PACKAGE"),
            "9: " + getCheckMessage(clazz, msgOrder, "STANDARD_JAVA_PACKAGE"),
            "10: " + getCheckMessage(clazz, msgOrder, "STANDARD_JAVA_PACKAGE"),
            "11: " + getCheckMessage(clazz, msgOrder, "STANDARD_JAVA_PACKAGE"),
            "14: " + getCheckMessage(clazz, msgSeparator, "com.sun.xml.internal.xsom.impl.scd.Iterators"),
            "16: " + getCheckMessage(clazz, msgOrder, "SPECIAL_IMPORTS"),
        };

        Configuration checkConfig = builder.getCheckConfig("CustomImportOrder");
        String filePath = builder.getFilePath("CustomImportOrderInput_2");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void customImportTest_3() throws IOException, Exception {
        
        final String[] expected = {
                "4: " + getCheckMessage(clazz, msgLex, "java.awt.Button.ABORT"),
                "8: " + getCheckMessage(clazz, msgOrder, "STANDARD_JAVA_PACKAGE"),
                "9: " + getCheckMessage(clazz, msgOrder, "STANDARD_JAVA_PACKAGE"),
                "10: " + getCheckMessage(clazz, msgOrder, "STANDARD_JAVA_PACKAGE"),
                "11: " + getCheckMessage(clazz, msgOrder, "STANDARD_JAVA_PACKAGE"),
                "14: " + getCheckMessage(clazz, msgSeparator, "com.sun.xml.internal.xsom.impl.scd.Iterators"),
                "16: " + getCheckMessage(clazz, msgOrder, "SPECIAL_IMPORTS"),
        };

        Configuration checkConfig = builder.getCheckConfig("CustomImportOrder");
        String filePath = builder.getFilePath("CustomImportOrderInput_3");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
    @Test
    public void validTest() throws IOException, Exception {
        
        final String[] expected = {
        };
        
        Configuration checkConfig = builder.getCheckConfig("CustomImportOrder");
        String filePath = builder.getFilePath("CustomImportOrderValidInput");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
