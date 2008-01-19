package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class IllegalTokenCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(IllegalTokenCheck.class);
        final String[] expected = {
            "11:9: Using 'switch' is not allowed.",
            "14:18: Using '--' is not allowed.",
            "15:18: Using '++' is not allowed.",
        };
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }

    @Test
    public void testNative()
         throws Exception
     {
         final DefaultConfiguration checkConfig =
             createCheckConfig(IllegalTokenCheck.class);
         checkConfig.addAttribute("tokens", "LITERAL_NATIVE");
         final String[] expected = {
             "20:12: Using 'native' is not allowed.",
         };
         verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
     }

}

