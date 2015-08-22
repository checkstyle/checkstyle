package com.google.checkstyle.test.chapter3filestructure.rule333orderingandsoacing;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck;

public class CustomImportOrderTest extends BaseCheckTestSupport{

    private static final String MSG_SEPARATOR = "custom.import.order.line.separator";
    private static final String MSG_LEX = "custom.import.order.lex";
    private static final String MSG_ORDER = "custom.import.order";
    static ConfigurationBuilder builder;
    final Class<CustomImportOrderCheck> clazz = CustomImportOrderCheck.class;
    String msgNongroup = "custom.import.order.nongroup.import";

    /** Shortcuts to make code more compact */
    private static final String STD = CustomImportOrderCheck.STANDARD_JAVA_PACKAGE_RULE_GROUP;
    private static final String SPECIAL = CustomImportOrderCheck.SPECIAL_IMPORTS_RULE_GROUP;

    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void customImportTest_1() throws IOException, Exception {
        
        final String[] expected = {
            "4: " + getCheckMessage(clazz, MSG_LEX, "java.awt.Button.ABORT", "java.io.File.createTempFile"),
            "7: " + getCheckMessage(clazz, MSG_ORDER, STD, SPECIAL, "java.awt.Button"),
            "8: " + getCheckMessage(clazz, MSG_ORDER, STD, SPECIAL, "java.awt.Frame"),
            "9: " + getCheckMessage(clazz, MSG_ORDER, STD, SPECIAL, "java.awt.Dialog"),
            "10: " + getCheckMessage(clazz, MSG_ORDER, STD, SPECIAL, "java.awt.event.ActionEvent"),
            "11: " + getCheckMessage(clazz, MSG_ORDER, STD, SPECIAL, "javax.swing.JComponent"),
            "12: " + getCheckMessage(clazz, MSG_ORDER, STD, SPECIAL, "javax.swing.JTable"),
            "13: " + getCheckMessage(clazz, MSG_ORDER, STD, SPECIAL, "java.io.File"),
            "14: " + getCheckMessage(clazz, MSG_ORDER, STD, SPECIAL, "java.io.IOException"),
            "15: " + getCheckMessage(clazz, MSG_ORDER, STD, SPECIAL, "java.io.InputStream"),
            "16: " + getCheckMessage(clazz, MSG_ORDER, STD, SPECIAL, "java.io.Reader"),
        };
        
        Configuration checkConfig = builder.getCheckConfig("CustomImportOrder");
        String filePath = builder.getFilePath("CustomImportOrderInput_1");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void customImportTest_2() throws IOException, Exception {
        
        final String[] expected = {
            "4: " + getCheckMessage(clazz, MSG_LEX, "java.awt.Button.ABORT", "java.io.File.createTempFile"),
            "7: " + getCheckMessage(clazz, MSG_ORDER, STD, SPECIAL, "java.util.List"),
            "8: " + getCheckMessage(clazz, MSG_ORDER, STD, SPECIAL, "java.util.StringTokenizer"),
            "9: " + getCheckMessage(clazz, MSG_ORDER, STD, SPECIAL, "java.util.*"),
            "10: " + getCheckMessage(clazz, MSG_ORDER, STD, SPECIAL, "java.util.concurrent.AbstractExecutorService"),
            "11: " + getCheckMessage(clazz, MSG_ORDER, STD, SPECIAL, "java.util.concurrent.*"),
            "14: " + getCheckMessage(clazz, MSG_SEPARATOR, "com.sun.xml.internal.xsom.impl.scd.Iterators"),
            "16: " + getCheckMessage(clazz, MSG_ORDER, SPECIAL, STD, "com.google.common.reflect.*"),
        };

        Configuration checkConfig = builder.getCheckConfig("CustomImportOrder");
        String filePath = builder.getFilePath("CustomImportOrderInput_2");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void customImportTest_3() throws IOException, Exception {
        
        final String[] expected = {
                "4: " + getCheckMessage(clazz, MSG_LEX, "java.awt.Button.ABORT", "java.io.File.createTempFile"),
                "8: " + getCheckMessage(clazz, MSG_ORDER, STD, SPECIAL, "java.util.StringTokenizer"),
                "9: " + getCheckMessage(clazz, MSG_ORDER, STD, SPECIAL, "java.util.*"),
                "10: " + getCheckMessage(clazz, MSG_ORDER, STD, SPECIAL, "java.util.concurrent.AbstractExecutorService"),
                "11: " + getCheckMessage(clazz, MSG_ORDER, STD, SPECIAL, "java.util.concurrent.*"),
                "14: " + getCheckMessage(clazz, MSG_SEPARATOR, "com.sun.xml.internal.xsom.impl.scd.Iterators"),
                "16: " + getCheckMessage(clazz, MSG_ORDER, SPECIAL, STD, "com.google.common.reflect.*"),
        };

        Configuration checkConfig = builder.getCheckConfig("CustomImportOrder");
        String filePath = builder.getFilePath("CustomImportOrderInput_3");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
    @Test
    public void validTest() throws IOException, Exception {
        
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        
        Configuration checkConfig = builder.getCheckConfig("CustomImportOrder");
        String filePath = builder.getFilePath("CustomImportOrderValidInput");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
