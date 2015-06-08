package com.google.checkstyle.test.chapter7javadoc.rule712paragraphs;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration; 
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck;
import com.google.checkstyle.test.base.BaseCheckTestSupport;

public class JavadocParagraphTest extends BaseCheckTestSupport{

    static ConfigurationBuilder builder;

    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/"));
    }

    @Test
    public void javadocParagraphCorrectTest() throws IOException, Exception {

        final String[] expected = {
        };

        Configuration checkConfig = builder.getCheckConfig("JavadocParagraph");
        String filePath = builder.getFilePath("InputCorrectJavadocParagraphCheck");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
    
    @Test
    public void javadocParagraphIncorrectTest() throws IOException, Exception {

        String msgBefore = getCheckMessage(JavadocParagraphCheck.class, "javadoc.paragraph.line.before");
        String msgRed = getCheckMessage(JavadocParagraphCheck.class, "javadoc.paragraph.redundant.paragraph");

        final String[] expected = {
        	"5: " + msgBefore,
        	"6: " + msgBefore,
        	"12: " + msgBefore,
        	"21: " + msgBefore,
        	"30: " + msgRed,
        	"31: " + msgBefore,
        	"32: " + msgBefore,
        	"33: " + msgBefore,
        	"37: " + msgBefore,
        	"43: " + msgRed,
        	"48: " + msgBefore,
        	"49: " + msgBefore,
        	"59: " + msgRed,
        	"68: " + msgBefore,
        	"73: " + msgBefore,
        };

        Configuration checkConfig = builder.getCheckConfig("JavadocParagraph");
        String filePath = builder.getFilePath("InputIncorrectJavadocParagraphCheck");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}


