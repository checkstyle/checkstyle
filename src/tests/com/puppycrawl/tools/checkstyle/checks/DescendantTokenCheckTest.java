package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import java.io.File;

public class DescendantTokenCheckTest extends BaseCheckTestCase
{
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }
    
    public void testMaximumNumber()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_NATIVE");
        checkConfig.addAttribute("limitedTokens", "LITERAL_NATIVE");
        checkConfig.addAttribute("maximumNumber", "0");
        final String[] expected = {
            "20:12: Count of 1 for 'LITERAL_NATIVE' descendant 'LITERAL_NATIVE' exceeds maximum count 0.",
        };
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }
     
    public void testMessage()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_NATIVE");
        checkConfig.addAttribute("limitedTokens", "LITERAL_NATIVE");
        checkConfig.addAttribute("maximumNumber", "0");
        checkConfig.addAttribute("maximumMessage", "Using ''native'' is not allowed.");
        final String[] expected = {
            "20:12: Using 'native' is not allowed.",
        };
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }

    public void testMinimumNumber()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_SWITCH");
        checkConfig.addAttribute("limitedTokens", "LITERAL_DEFAULT");
        checkConfig.addAttribute("minimumNumber", "2");
        final String[] expected = {
            "11:9: Count of 1 for 'LITERAL_SWITCH' descendant 'LITERAL_DEFAULT' is less than minimum count 2.",
        };
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }
     
    public void testMinimumDepth()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_SWITCH");
        checkConfig.addAttribute("limitedTokens", "LITERAL_DEFAULT");
        checkConfig.addAttribute("maximumNumber", "0");
        checkConfig.addAttribute("minimumDepth", "3");
        final String[] expected = {};
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }
     
    public void testMaximumDepth()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_SWITCH");
        checkConfig.addAttribute("limitedTokens", "LITERAL_DEFAULT");
        checkConfig.addAttribute("maximumNumber", "0");
        checkConfig.addAttribute("maximumDepth", "1");
        final String[] expected = {};
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }

    public void testEmptyStatements()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "EMPTY_STAT");
        checkConfig.addAttribute("limitedTokens", "EMPTY_STAT");
        checkConfig.addAttribute("maximumNumber", "0");
        checkConfig.addAttribute("maximumDepth", "0");
        checkConfig.addAttribute("maximumMessage", "Empty statement.");

        final String[] expected = {
           "12:7: Empty statement.",
           "17:7: Empty statement.",
           "22:19: Empty statement.",
           "26:10: Empty statement.",
           "29:16: Empty statement.",
           "33:10: Empty statement.",
           "43:10: Empty statement.",
           "49:13: Empty statement.",
           "51:13: Empty statement.",
           "54:19: Empty statement.",
           "58:10: Empty statement.",
           "61:9: Empty statement.",
           "66:10: Empty statement.",
           "72:10: Empty statement.",
           "76:10: Empty statement.",
           "80:10: Empty statement.",
        };

        verify(checkConfig, getPath("InputEmptyStatement.java"), expected);
    }

    public void testMissingSwitchDefault() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_SWITCH");
        checkConfig.addAttribute("limitedTokens", "LITERAL_DEFAULT");
        checkConfig.addAttribute("minimumNumber", "1");
        checkConfig.addAttribute("maximumDepth", "2");
        checkConfig.addAttribute("minimumMessage", "switch without \"default\" clause.");

        final String[] expected = {
            "15:9: switch without \"default\" clause.",
        };

        verify(checkConfig, getPath("InputMissingSwitchDefault.java"), expected);
    }

    public void testStringLiteralEquality() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "EQUAL,NOT_EQUAL");
        checkConfig.addAttribute("limitedTokens", "STRING_LITERAL");
        checkConfig.addAttribute("maximumNumber", "0");
        checkConfig.addAttribute("maximumDepth", "1");
        checkConfig.addAttribute("maximumMessage", "Literal Strings should be compared using equals(), not ''==''.");

        final String[] expected = {
            "11:18: Literal Strings should be compared using equals(), not '=='.",
            "16:20: Literal Strings should be compared using equals(), not '=='.",
            "21:22: Literal Strings should be compared using equals(), not '=='.",
        };
        verify(checkConfig, getPath("coding" + File.separator + "InputStringLiteralEquality.java"), expected);
    }

    public void testIllegalTokenDefault() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_SWITCH, POST_INC, POST_DEC");
        checkConfig.addAttribute("limitedTokens", "LITERAL_SWITCH, POST_INC, POST_DEC");
        checkConfig.addAttribute("maximumNumber", "0");
        checkConfig.addAttribute("maximumDepth", "0");
        checkConfig.addAttribute("maximumMessage", "Using ''{2}'' is not allowed.");

        final String[] expected = {
            "11:9: Using 'LITERAL_SWITCH' is not allowed.",
            "14:18: Using 'POST_DEC' is not allowed.",
            "15:18: Using 'POST_INC' is not allowed.",
        };
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }
    
    public void testIllegalTokenNative() throws Exception
     {
         final DefaultConfiguration checkConfig =
             createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_NATIVE");
        checkConfig.addAttribute("limitedTokens", "LITERAL_NATIVE");
        checkConfig.addAttribute("maximumNumber", "0");
        checkConfig.addAttribute("maximumDepth", "0");
        checkConfig.addAttribute("maximumMessage", "Using ''{2}'' is not allowed.");

         final String[] expected = {
             "20:12: Using 'LITERAL_NATIVE' is not allowed.",
         };
         verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
     }

    public void testReturnFromCatch() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(DescendantTokenCheck.class);

        checkConfig.addAttribute("tokens", "LITERAL_CATCH");
        checkConfig.addAttribute("limitedTokens", "LITERAL_RETURN");
        checkConfig.addAttribute("maximumNumber", "0");
        checkConfig.addAttribute("maximumMessage", "Return from catch is not allowed.");

        String[] expected = {
            "7:11: Return from catch is not allowed.",
            "15:11: Return from catch is not allowed."
        };

        verify(checkConfig,
               getPath("coding" + File.separator + "InputReturnFromCatchCheck.java"),
               expected);
    }

    public void testReturnFromFinally() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(DescendantTokenCheck.class);

        checkConfig.addAttribute("tokens", "LITERAL_FINALLY");
        checkConfig.addAttribute("limitedTokens", "LITERAL_RETURN");
        checkConfig.addAttribute("maximumNumber", "0");
        checkConfig.addAttribute("maximumMessage", "Return from finally is not allowed.");

        String[] expected = {
            "7:11: Return from finally is not allowed.",
            "15:11: Return from finally is not allowed."
        };

        verify(checkConfig,
               getPath("coding" + File.separator + "InputReturnFromFinallyCheck.java"),
               expected);
    }
}
