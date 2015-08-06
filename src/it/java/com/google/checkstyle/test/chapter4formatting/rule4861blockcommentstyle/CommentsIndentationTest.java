package com.google.checkstyle.test.chapter4formatting.rule4861blockcommentstyle;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.indentation.CommentsIndentationCheck;

public class CommentsIndentationTest extends BaseCheckTestSupport {

    static ConfigurationBuilder builder;

    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void commentsIndentationTest() throws Exception {

        final String[] expected = {
            "1: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single", 2, 1, 0),
            "13: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single", 14, 8, 6),
            "23: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.block", 24, 8, 4),
            "25: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.block", 27, 8, 4),
            "28: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.block", 31, 8, 4),
            "50: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single", 51, 23, 19),
            "51: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.block", 53, 19, 32),
        };

        Configuration checkConfig = builder.getCheckConfig("CommentsIndentation");
        String filePath = builder.getFilePath("CommentsIndentationInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
