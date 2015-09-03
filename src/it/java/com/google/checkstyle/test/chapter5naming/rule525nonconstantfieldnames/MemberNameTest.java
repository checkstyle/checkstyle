package com.google.checkstyle.test.chapter5naming.rule525nonconstantfieldnames;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class MemberNameTest extends BaseCheckTestSupport{

    private static ConfigurationBuilder builder;
    private static final String MSG_KEY = "name.invalidPattern";
    private static Configuration checkConfig;
    private static String format;

    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException {
        builder = new ConfigurationBuilder(new File("src/it/"));
        checkConfig = builder.getCheckConfig("MemberName");
        format = checkConfig.getAttribute("format");
    }

    @Test
    public void memberNameTest() throws Exception {

        final String[] expected = {
            "5:16: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "mPublic", format),
            "6:19: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "mProtected", format),
            "7:9: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "mPackage", format),
            "8:17: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "mPrivate", format),
            "10:16: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "_public", format),
            "11:19: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "prot_ected", format),
            "12:9: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "package_", format),
            "13:17: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "priva$te", format),
            "20:9: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "ABC", format),
            "21:15: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "C_D_E", format),
            "23:16: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "$mPublic", format),
            "24:19: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "mPro$tected", format),
            "25:9: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "mPackage$", format),
        };

        String filePath = builder.getFilePath("MemberNameInput_Basic");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void simpleTest() throws Exception {

        final String[] expected = {
            "12:17: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "bad$Static", format),
            "17:17: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "bad_Member", format),
            "19:17: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "m", format),
            "21:19: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "m_M", format),
            "24:19: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "m$nts", format),
            "35:9: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "mTest1", format),
            "37:16: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "mTest2", format),
            "39:16: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "$mTest2", format),
            "41:16: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "mTes$t2", format),
            "43:16: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "mTest2$", format),
            "77:21: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "bad$Static", format),
            "79:22: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "sum_Created", format),
            "82:21: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "bad_Member", format),
            "84:21: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "m", format),
            "86:23: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "m_M", format),
            "89:23: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "m$nts", format),
            "93:13: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "mTest1", format),
            "95:20: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "mTest2", format),
            "97:20: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "$mTest2", format),
            "99:20: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "mTes$t2", format),
            "101:20: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "mTest2$", format),
            "107:25: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "bad$Static", format),
            "109:25: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "sum_Created", format),
            "112:25: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "bad_Member", format),
            "114:25: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "m", format),
            "116:25: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "m_M", format),
            "119:27: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "m$nts", format),
            "123:25: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "mTest1", format),
            "125:25: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "mTest2", format),
            "127:25: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "$mTest2", format),
            "129:25: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "mTes$t2", format),
            "131:25: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "mTest2$", format),
        };

        String filePath = builder.getFilePath("MemberNameInput_Simple");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
