package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

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
        final String fname = getPath("InputSemantic.java");
        final String[] expected = {
            "69: Line matches the illegal pattern '" + illegal + "'."
        };
        verify(checkConfig, fname, expected);
    }
}
