package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class RequiredRegexpCheckTest
    extends BaseCheckTestCase
{
    public void testExistingInDoc()
            throws Exception
    {
        final String required = "Test case file";
        final DefaultConfiguration checkConfig =
            createCheckConfig(RequiredRegexpCheck.class);
        checkConfig.addAttribute("format", required);
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    public void testExistingInCode()
            throws Exception
    {
        final String required = "package";
        final DefaultConfiguration checkConfig =
            createCheckConfig(RequiredRegexpCheck.class);
        checkConfig.addAttribute("format", required);
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    public void testMissing()
            throws Exception
    {
        final String required = "This text is not in the file";
        final DefaultConfiguration checkConfig =
            createCheckConfig(RequiredRegexpCheck.class);
        checkConfig.addAttribute("format", required);
        final String[] expected = {
            "0: Required pattern '" + required + "' missing in file."
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }
}
