package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.OperatorWrapCheck;
import com.puppycrawl.tools.checkstyle.checks.OperatorWrapOption;

public class OperatorWrapCheckTest
    extends BaseCheckTestCase
{
    public OperatorWrapCheckTest(String aName)
    {
        super(aName);
    }

    public void testDefault()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(OperatorWrapCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputOpWrap.java");
        final String[] expected = {
            "15:19: '+' should be on a new line.",
            "16:15: '-' should be on a new line.",
            "24:18: '&&' should be on a new line.",

        };
        verify(c, fname, expected);
    }

    public void testOpWrapEOL()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(OperatorWrapCheck.class.getName());
        checkConfig.addProperty("option", OperatorWrapOption.EOL.toString());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputOpWrap.java");
        final String[] expected = {
            "18:13: '-' should be on the previous line.",
            "22:13: '&&' should be on the previous line.",
            "27:13: '&&' should be on the previous line.",
        };
        verify(c, fname, expected);
    }    
}
