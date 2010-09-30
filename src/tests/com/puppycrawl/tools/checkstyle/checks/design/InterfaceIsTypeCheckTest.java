package com.puppycrawl.tools.checkstyle.checks.design;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class InterfaceIsTypeCheckTest
    extends BaseCheckTestSupport
{
    @Test
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

    @Test
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
