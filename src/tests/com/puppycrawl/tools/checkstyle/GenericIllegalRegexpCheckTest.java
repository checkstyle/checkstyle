package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.GenericIllegalRegexpCheck;

public class GenericIllegalRegexpCheckTest
    extends BaseCheckTestCase
{
    public void testIt()
            throws Exception
    {
        final String illegal = "System\\.(out)|(err)\\.print(ln)?\\(";
        final DefaultConfiguration checkConfig =
            createCheckConfig(GenericIllegalRegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSemantic.java");
        final String[] expected = {
            "69: Line matches the illegal pattern '" + illegal + "'."
        };
        verify(c, fname, expected);
    }
}
