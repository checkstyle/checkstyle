package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.FileLengthCheck;

public class FileLengthCheckTest
        extends BaseCheckTestCase
{
    public FileLengthCheckTest(String aName)
    {
        super(aName);
    }

    private void runIt(String aMax, String[] aExpected) throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(FileLengthCheck.class.getName());
        checkConfig.addProperty("max", aMax);
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
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(FileLengthCheck.class.getName());
        try {
            checkConfig.addProperty("max", "abc");
            createChecker(checkConfig);
            fail("Should indicate illegal args");
        }
        catch (Exception ex)
        {
            // Expected Exception because of illegal argument for "max"

            // TODO: I would have expected to receive a ConversionException here
            // but in fact I do get an InvocationTargetException - another
            // mystery in beanutils?
        }
    }


}
