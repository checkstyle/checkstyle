package com.puppycrawl.tools.checkstyle.checks.header;

import java.io.File;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class CrossLanguageRegexpHeaderCheckTest
    extends BaseCheckTestCase
{
    protected DefaultConfiguration createCheckerConfig(
        Configuration aCheckConfig)
    {
        final DefaultConfiguration dc = new DefaultConfiguration("root");
        dc.addChild(aCheckConfig);
        return dc;
    }

    public void testComplexHeaderConfigOnProperties()
    throws Exception
    {
        final DefaultConfiguration checkConfig = createComplexHeaderConfig();
        final String[] expected = {
                "1: Missing a header - not enough lines in file.",
        };
        final File[] files = new File[] {
                new File(getPath("header/H1.properties")),
                new File(getPath("header/H2.properties")),
                new File(getPath("header/H1.java")),
                new File(getPath("header/H1.xml")),
        };
        verify(
                createChecker(checkConfig),
                files,
                getPath("header/H2.properties"),
                expected);
    }

    public void testComplexHeaderConfigOnJava()
    throws Exception
    {
        final DefaultConfiguration checkConfig = createComplexHeaderConfig();
        final String[] expected = {
                "2: Line does not match expected header line of '\\W*\\(C\\) \\d\\d\\d\\d correct header$'.",
        };
        final File[] files = new File[] {
                new File(getPath("header/H1.properties")),
                new File(getPath("header/H1.java")),
                new File(getPath("header/H2.java")),
                new File(getPath("header/H1.xml")),
        };
        verify(
                createChecker(checkConfig),
                files,
                getPath("header/H2.java"),
                expected);
    }

    public void testComplexHeaderConfigOnXml()
    throws Exception
    {
        final DefaultConfiguration checkConfig = createComplexHeaderConfig();
        final String[] expected = {
                "2: Line does not match expected header line of '^\\W*$'."
        };
        final File[] files = new File[] {
                new File(getPath("header/H1.properties")),
                new File(getPath("header/H1.java")),
                new File(getPath("header/H1.xml")),
                new File(getPath("header/H2.xml")),
        };
        verify(
                createChecker(checkConfig),
                files,
                getPath("header/H2.xml"),
                expected);
    }


    /**
     * Creates a configuration that is functionally close to that in the docs. 
     */
    private DefaultConfiguration createComplexHeaderConfig() {
        final DefaultConfiguration checkConfig = createCheckConfig(CrossLanguageRegexpHeaderCheck.class);
        checkConfig.addAttribute("header", 
                "^<\\?xml.*\\?>$\n" +
                "^\\W*$\n" +
                "\\W*\\(C\\) \\d\\d\\d\\d correct header$\n" +
                "^\\W*$\n");
        checkConfig.addAttribute("multiLines", "1");
        return checkConfig;
    }
}
