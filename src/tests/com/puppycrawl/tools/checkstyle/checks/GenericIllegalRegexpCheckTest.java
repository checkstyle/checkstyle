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
        final String[] expected = {
            "69: Line matches the illegal pattern '" + illegal + "'."
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    public void testMessageProperty()
        throws Exception
    {
        final String illegal = "System\\.(out)|(err)\\.print(ln)?\\(";
        final String message = "Bad line :(";
        final DefaultConfiguration checkConfig =
            createCheckConfig(GenericIllegalRegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("message", message);
        final String[] expected = {
            "69: " + message,
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }
    
    
    public void testIgnoreCaseTrue()
            throws Exception
    {
        final String illegal = "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(";
        final DefaultConfiguration checkConfig =
            createCheckConfig(GenericIllegalRegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreCase", "true");
        final String[] expected = {
            "69: Line matches the illegal pattern '" + illegal + "'."
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    public void testIgnoreCaseFalse()
            throws Exception
    {
        final String illegal = "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(";
        final DefaultConfiguration checkConfigTrue =
            createCheckConfig(GenericIllegalRegexpCheck.class);
        checkConfigTrue.addAttribute("format", illegal);
        checkConfigTrue.addAttribute("ignoreCase", "true");
        final String[] expectedTrue = {
            "69: Line matches the illegal pattern '" + illegal + "'."};
        verify(checkConfigTrue, getPath("InputSemantic.java"), expectedTrue);
        
        final DefaultConfiguration checkConfigFalse =
            createCheckConfig(GenericIllegalRegexpCheck.class);
        checkConfigFalse.addAttribute("format", illegal);
        checkConfigFalse.addAttribute("ignoreCase", "false");
        final String[] expectedFalse = {};
        verify(checkConfigFalse, getPath("InputSemantic.java"), expectedFalse);
    }
}
