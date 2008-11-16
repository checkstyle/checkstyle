package com.puppycrawl.tools.checkstyle.checks.header;

import com.puppycrawl.tools.checkstyle.BaseFileSetCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class CrossLanguageRegexpHeaderCheckTest
    extends BaseFileSetCheckTestSupport
{
    @Test
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

    @Test
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

    @Test
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
