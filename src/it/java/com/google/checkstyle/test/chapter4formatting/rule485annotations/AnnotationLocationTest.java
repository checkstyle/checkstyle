package com.google.checkstyle.test.chapter4formatting.rule485annotations;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck;

public class AnnotationLocationTest extends BaseCheckTestSupport{
    
    private static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder() {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void annotationTest() throws Exception {
        
        Class<AnnotationLocationCheck> clazz = AnnotationLocationCheck.class;
        getCheckMessage(clazz, "annotation.location.alone");
        Configuration checkConfig = builder.getCheckConfig("AnnotationLocation");

        String msgLocationAlone = "annotation.location.alone";
        String msgLocation = "annotation.location";
        final String[] expected = {
            "3: " + getCheckMessage(clazz, msgLocationAlone, "MyAnnotation1"),
            "20: " + getCheckMessage(clazz, msgLocation, "MyAnnotation1", "8", "4"),
            "27: " + getCheckMessage(clazz, msgLocation, "MyAnnotation2", "7", "4"),
            "31: " + getCheckMessage(clazz, msgLocation, "MyAnnotation2", "8", "4"),
            "32: " + getCheckMessage(clazz, msgLocation, "MyAnnotation3", "6", "4"),
            "33: " + getCheckMessage(clazz, msgLocation, "MyAnnotation4", "10", "4"),
            "54: " + getCheckMessage(clazz, msgLocation, "MyAnnotation2", "12", "8"),
            "58: " + getCheckMessage(clazz, msgLocation, "MyAnnotation2", "12", "8"),
            "78: " + getCheckMessage(clazz, msgLocation, "MyAnnotation2", "11", "8"),
            "81: " + getCheckMessage(clazz, msgLocation, "MyAnnotation2", "10", "8"),
            "90: " + getCheckMessage(clazz, msgLocation, "MyAnnotation2", "1", "0"),
        };

        String filePath = builder.getFilePath("AnnotationLocationInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
