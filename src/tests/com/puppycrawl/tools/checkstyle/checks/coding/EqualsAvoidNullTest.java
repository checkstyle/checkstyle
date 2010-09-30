package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class EqualsAvoidNullTest
extends BaseCheckTestSupport
{
    @Test
    public void testIt() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EqualsAvoidNullCheck.class);

        final String[] expected = {
        	"18:17: String literal expressions should be on the left side of an equals comparison.",
        	"20:17: String literal expressions should be on the left side of an equals comparison.",
        	"22:17: String literal expressions should be on the left side of an equals comparison.",
        	"24:17: String literal expressions should be on the left side of an equals comparison.",
        	"26:17: String literal expressions should be on the left side of an equals comparison.",
        	"28:17: String literal expressions should be on the left side of an equals comparison.",
        };
        verify(checkConfig, getPath("coding" + File.separator + "InputEqualsAvoidNull.java"), expected);
    }
}
