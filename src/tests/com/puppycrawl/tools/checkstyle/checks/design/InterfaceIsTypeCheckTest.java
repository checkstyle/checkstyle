package com.puppycrawl.tools.checkstyle.checks.design;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class InterfaceIsTypeCheckTest
    extends BaseCheckTestCase
{
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(InterfaceIsTypeCheck.class);
        final String[] expected = {
            "25: interfaces should describe a type and hence have methods.",
        };
        verify(checkConfig, getPath("InputInterfaceIsType.java"), expected);
    }

    public void testAllowMarker()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(InterfaceIsTypeCheck.class);
        checkConfig.addAttribute("allowMarkerInterfaces", "false");
        final String[] expected = {
            "20: interfaces should describe a type and hence have methods.",
            "25: interfaces should describe a type and hence have methods.",
        };
        verify(checkConfig, getPath("InputInterfaceIsType.java"), expected);
    }

}
