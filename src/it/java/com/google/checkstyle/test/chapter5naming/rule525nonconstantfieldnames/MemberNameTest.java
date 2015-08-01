package com.google.checkstyle.test.chapter5naming.rule525nonconstantfieldnames;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.naming.TypeNameCheck;

public class MemberNameTest extends BaseCheckTestSupport{
    
	private static ConfigurationBuilder builder;
	private Class<TypeNameCheck> clazz = TypeNameCheck.class;
	private String msgKey = "name.invalidPattern";
	private static Configuration checkConfig;
	private static String format;

    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException, IOException {
        builder = new ConfigurationBuilder(new File("src/it/"));
        checkConfig = builder.getCheckConfig("MemberName");
        format = checkConfig.getAttribute("format");
    }

    @Test
    public void memberNameTest() throws IOException, Exception {
        
        final String[] expected = {
            "5:16: " + getCheckMessage(checkConfig.getMessages(), msgKey, "mPublic", format),
            "6:19: " + getCheckMessage(checkConfig.getMessages(), msgKey, "mProtected", format),
            "7:9: " + getCheckMessage(checkConfig.getMessages(), msgKey, "mPackage", format),
            "8:17: " + getCheckMessage(checkConfig.getMessages(), msgKey, "mPrivate", format),
            "10:16: " + getCheckMessage(checkConfig.getMessages(), msgKey, "_public", format),
            "11:19: " + getCheckMessage(checkConfig.getMessages(), msgKey, "prot_ected", format),
            "12:9: " + getCheckMessage(checkConfig.getMessages(), msgKey, "package_", format),
            "13:17: " + getCheckMessage(checkConfig.getMessages(), msgKey, "priva$te", format),
            "20:9: " + getCheckMessage(checkConfig.getMessages(), msgKey, "ABC", format),
            "21:15: " + getCheckMessage(checkConfig.getMessages(), msgKey, "C_D_E", format),
            "23:16: " + getCheckMessage(checkConfig.getMessages(), msgKey, "$mPublic", format),
            "24:19: " + getCheckMessage(checkConfig.getMessages(), msgKey, "mPro$tected", format),
            "25:9: " + getCheckMessage(checkConfig.getMessages(), msgKey, "mPackage$", format),
        };

        String filePath = builder.getFilePath("MemberNameInput_Basic");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void simpleTest() throws IOException, Exception {
        
        final String[] expected = {
            "12:17: " + getCheckMessage(checkConfig.getMessages(), msgKey, "bad$Static", format),
            "17:17: " + getCheckMessage(checkConfig.getMessages(), msgKey, "bad_Member", format),
            "19:17: " + getCheckMessage(checkConfig.getMessages(), msgKey, "m", format),
            "21:19: " + getCheckMessage(checkConfig.getMessages(), msgKey, "m_M", format),
            "24:19: " + getCheckMessage(checkConfig.getMessages(), msgKey, "m$nts", format),
            "35:9: " + getCheckMessage(checkConfig.getMessages(), msgKey, "mTest1", format),
            "37:16: " + getCheckMessage(checkConfig.getMessages(), msgKey, "mTest2", format),
            "39:16: " + getCheckMessage(checkConfig.getMessages(), msgKey, "$mTest2", format),
            "41:16: " + getCheckMessage(checkConfig.getMessages(), msgKey, "mTes$t2", format),
            "43:16: " + getCheckMessage(checkConfig.getMessages(), msgKey, "mTest2$", format),
            "77:21: " + getCheckMessage(checkConfig.getMessages(), msgKey, "bad$Static", format),
            "79:22: " + getCheckMessage(checkConfig.getMessages(), msgKey, "sum_Created", format),
            "82:21: " + getCheckMessage(checkConfig.getMessages(), msgKey, "bad_Member", format),
            "84:21: " + getCheckMessage(checkConfig.getMessages(), msgKey, "m", format),
            "86:23: " + getCheckMessage(checkConfig.getMessages(), msgKey, "m_M", format),
            "89:23: " + getCheckMessage(checkConfig.getMessages(), msgKey, "m$nts", format),
            "93:13: " + getCheckMessage(checkConfig.getMessages(), msgKey, "mTest1", format),
            "95:20: " + getCheckMessage(checkConfig.getMessages(), msgKey, "mTest2", format),
            "97:20: " + getCheckMessage(checkConfig.getMessages(), msgKey, "$mTest2", format),
            "99:20: " + getCheckMessage(checkConfig.getMessages(), msgKey, "mTes$t2", format),
            "101:20: " + getCheckMessage(checkConfig.getMessages(), msgKey, "mTest2$", format),
            "107:25: " + getCheckMessage(checkConfig.getMessages(), msgKey, "bad$Static", format),
            "109:25: " + getCheckMessage(checkConfig.getMessages(), msgKey, "sum_Created", format),
            "112:25: " + getCheckMessage(checkConfig.getMessages(), msgKey, "bad_Member", format),
            "114:25: " + getCheckMessage(checkConfig.getMessages(), msgKey, "m", format),
            "116:25: " + getCheckMessage(checkConfig.getMessages(), msgKey, "m_M", format),
            "119:27: " + getCheckMessage(checkConfig.getMessages(), msgKey, "m$nts", format),
            "123:25: " + getCheckMessage(checkConfig.getMessages(), msgKey, "mTest1", format),
            "125:25: " + getCheckMessage(checkConfig.getMessages(), msgKey, "mTest2", format),
            "127:25: " + getCheckMessage(checkConfig.getMessages(), msgKey, "$mTest2", format),
            "129:25: " + getCheckMessage(checkConfig.getMessages(), msgKey, "mTes$t2", format),
            "131:25: " + getCheckMessage(checkConfig.getMessages(), msgKey, "mTest2$", format),
        };

        String filePath = builder.getFilePath("MemberNameInput_Simple");
        
        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
