package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.MethodLengthCheck;

public class MethodLengthCheckTest extends BaseCheckTestCase
{
    public MethodLengthCheckTest(String aName)
    {
        super(aName);
    }

    public void testIt() throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(MethodLengthCheck.class.getName());
        checkConfig.addProperty("max", "19");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSimple.java");
        final String[] expected = {
            "80: Method length is 20 lines (max allowed is 19)."
        };
        verify(c, fname, expected);
    }
}