package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.FileLengthCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

public class FileLengthCheckTest
    extends BaseCheckTestCase
{
    private void runIt(String aMax, String[] aExpected) throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FileLengthCheck.class);
        checkConfig.addAttribute("max", aMax);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSimple.java");
        verify(c, fname, aExpected);
    }

    public void testAlarm() throws Exception
    {
        final String[] expected = {
            "1: File length is 198 lines (max allowed is 20)."
        };
        runIt("20", expected);
    }

    public void testOK() throws Exception
    {
        final String[] expected = {
        };
        runIt("2000", expected);
    }

    public void testArgs() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FileLengthCheck.class);
        try {
            checkConfig.addAttribute("max", "abc");
            createChecker(checkConfig);
            fail("Should indicate illegal args");
        }
        catch (CheckstyleException ex)
        {
            // Expected Exception because of illegal argument for "max"
        }
    }


}
