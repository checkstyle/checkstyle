package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class PackageNameCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testSpecified()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(PackageNameCheck.class);
        checkConfig.addAttribute("format", "[A-Z]+");
        final String[] expected = {
            "6:42: Name 'com.puppycrawl.tools.checkstyle' must match pattern '[A-Z]+'.",
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }

    @Test
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(PackageNameCheck.class);
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }
}
