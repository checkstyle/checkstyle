package com.google.checkstyle.test.chapter5naming.rule521packagenames;

import java.io.File;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class PackageNameTest extends BaseCheckTestSupport{

	private static final String MSG_KEY = "name.invalidPattern";
	private static ConfigurationBuilder builder;
	private static Configuration checkConfig;
	private static String format;


    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException {
        builder = new ConfigurationBuilder(new File("src/it/"));
        checkConfig = builder.getCheckConfig("PackageName");
        format = checkConfig.getAttribute("format");
    }

    @Test
    public void goodPackageNameTest() throws Exception {


        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        String filePath = builder.getFilePath("PackageNameInputGood");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void badPackageNameTest() throws Exception {

        String packagePath =
                "com.google.checkstyle.test.chapter5naming.rule521packageNamesCamelCase";
        String msg = getCheckMessage(checkConfig.getMessages(), MSG_KEY, packagePath, format);

        final String[] expected = {
            "1:9: " + msg,
        };

        String filePath = builder.getFilePath("PackageNameInputBad");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void badPackageName2Test() throws Exception {


        String packagePath = "com.google.checkstyle.test.chapter5naming.rule521_packagenames";
        String msg = getCheckMessage(checkConfig.getMessages(), MSG_KEY, packagePath, format);

        final String[] expected = {
            "1:9: " + msg,
        };

        String filePath = builder.getFilePath("BadPackageNameInput2");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void badPackageName3Test() throws Exception {


        String packagePath = "com.google.checkstyle.test.chapter5naming.rule521$packagenames";
        String msg = getCheckMessage(checkConfig.getMessages(), MSG_KEY, packagePath, format);

        final String[] expected = {
            "1:9: " + msg,
        };

        String filePath = builder.getFilePath("PackageBadNameInput3");

        Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
