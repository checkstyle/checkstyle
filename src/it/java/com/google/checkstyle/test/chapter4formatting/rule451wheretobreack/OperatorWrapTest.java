package com.google.checkstyle.test.chapter4formatting.rule451wheretobreack;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck;
import com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyOption;
import com.puppycrawl.tools.checkstyle.checks.whitespace.OperatorWrapCheck;
import com.puppycrawl.tools.checkstyle.checks.whitespace.WrapOption;

public class OperatorWrapTest extends BaseCheckTestSupport{
    
    static ConfigurationBuilder builder;
    
    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void operatorWrapTest() throws IOException, Exception {
        
        Class<OperatorWrapCheck> clazz = OperatorWrapCheck.class;
        String messageKey = "line.new";

        final String[] expected = {
            "10:27: " + getCheckMessage(clazz, messageKey, "+"),
            "11:28: " + getCheckMessage(clazz, messageKey, "-"),
            "19:27: " + getCheckMessage(clazz, messageKey, "&&"),
            "53:42: " + getCheckMessage(clazz, messageKey, "?"),
            "57:27: " + getCheckMessage(clazz, messageKey, "!="),
            "63:30: " + getCheckMessage(clazz, messageKey, "=="),
            "69:27: " + getCheckMessage(clazz, messageKey, ">"),
            "75:35: " + getCheckMessage(clazz, messageKey, "||"),
            "98:46: " + getCheckMessage(clazz, messageKey, "?"),
            "102:31: " + getCheckMessage(clazz, messageKey, "!="),
            "108:34: " + getCheckMessage(clazz, messageKey, "=="),
            "114:31: " + getCheckMessage(clazz, messageKey, ">"),
            "120:39: " + getCheckMessage(clazz, messageKey, "||"),
            "144:46: " + getCheckMessage(clazz, messageKey, "?"),
            "148:31: " + getCheckMessage(clazz, messageKey, "!="),
            "154:34: " + getCheckMessage(clazz, messageKey, "=="),
            "160:31: " + getCheckMessage(clazz, messageKey, ">"),
            "166:39: " + getCheckMessage(clazz, messageKey, "||"),
            "185:38: " + getCheckMessage(clazz, messageKey, "?"),
        };

        Configuration checkConfig = builder.getCheckConfig("OperatorWrap");
        String filePath = builder.getFilePath("OperatorWrapInput");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
    
    @Test
    public void operatorWrapTestAssign() throws Exception {
        DefaultConfiguration newCheckConfig = createCheckConfig(OperatorWrapCheck.class);
        newCheckConfig.addAttribute("option", WrapOption.EOL.toString());
        newCheckConfig.addAttribute("tokens", "ASSIGN, DIV_ASSIGN, PLUS_ASSIGN, MINUS_ASSIGN,"
                + "STAR_ASSIGN, MOD_ASSIGN, SR_ASSIGN, BSR_ASSIGN, SL_ASSIGN, BXOR_ASSIGN,"
                + "BOR_ASSIGN, BAND_ASSIGN");
        String messageKey = "line.previous";
        Class<OperatorWrapCheck> clazz = OperatorWrapCheck.class;
        
        final String[] expected = {
            "28:13: " + getCheckMessage(clazz, messageKey, "="),
            "177:9: " + getCheckMessage(clazz, messageKey, "="),
            "207:14: " + getCheckMessage(clazz, messageKey, "+="),
            "211:14: " + getCheckMessage(clazz, messageKey, "-="),
            "215:14: " + getCheckMessage(clazz, messageKey, "/="),
            "219:14: " + getCheckMessage(clazz, messageKey, "*="),
            "223:14: " + getCheckMessage(clazz, messageKey, "%="),
            "227:14: " + getCheckMessage(clazz, messageKey, "^="),
            "231:14: " + getCheckMessage(clazz, messageKey, "|="),
            "235:14: " + getCheckMessage(clazz, messageKey, "&="),
            "239:13: " + getCheckMessage(clazz, messageKey, ">>="),
            "243:13: " + getCheckMessage(clazz, messageKey, ">>>="),
            "247:13: " + getCheckMessage(clazz, messageKey, "<<="),
            "257:18: " + getCheckMessage(clazz, messageKey, "+="),
            "261:18: " + getCheckMessage(clazz, messageKey, "-="),
            "265:18: " + getCheckMessage(clazz, messageKey, "/="),
            "269:18: " + getCheckMessage(clazz, messageKey, "*="),
            "273:18: " + getCheckMessage(clazz, messageKey, "%="),
            "277:18: " + getCheckMessage(clazz, messageKey, "^="),
            "281:18: " + getCheckMessage(clazz, messageKey, "|="),
            "285:18: " + getCheckMessage(clazz, messageKey, "&="),
            "289:17: " + getCheckMessage(clazz, messageKey, ">>="),
            "293:17: " + getCheckMessage(clazz, messageKey, ">>>="),
            "297:17: " + getCheckMessage(clazz, messageKey, "<<="),
            "308:18: " + getCheckMessage(clazz, messageKey, "+="),
            "312:18: " + getCheckMessage(clazz, messageKey, "-="),
            "316:18: " + getCheckMessage(clazz, messageKey, "/="),
            "320:18: " + getCheckMessage(clazz, messageKey, "*="),
            "324:18: " + getCheckMessage(clazz, messageKey, "%="),
            "328:18: " + getCheckMessage(clazz, messageKey, "^="),
            "332:18: " + getCheckMessage(clazz, messageKey, "|="),
            "336:18: " + getCheckMessage(clazz, messageKey, "&="),
            "340:17: " + getCheckMessage(clazz, messageKey, ">>="),
            "344:17: " + getCheckMessage(clazz, messageKey, ">>>="),
            "348:17: " + getCheckMessage(clazz, messageKey, "<<="),
        };

        String filePath = builder.getFilePath("OperatorWrapAssignInput");
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(newCheckConfig, filePath, expected, warnList);
    }
}