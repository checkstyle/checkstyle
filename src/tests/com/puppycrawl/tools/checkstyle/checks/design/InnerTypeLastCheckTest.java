package com.puppycrawl.tools.checkstyle.checks.design;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class InnerTypeLastCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testMembersBeforeInner() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(InnerTypeLastCheck.class);
        final String[] expected = {
                "15:17: Fields and methods should be before inner classes.",
                "25:17: Fields and methods should be before inner classes.",
                "26:17: Fields and methods should be before inner classes.",
                "39:25: Fields and methods should be before inner classes.",
                "40:25: Fields and methods should be before inner classes.",
                "44:9: Fields and methods should be before inner classes.",
                "60:25: Fields and methods should be before inner classes.",
                "61:25: Fields and methods should be before inner classes.",
                "65:9: Fields and methods should be before inner classes.",
                "69:9: Fields and methods should be before inner classes.",
                "78:5: Fields and methods should be before inner classes.", };
        verify(checkConfig, getPath("design" + File.separator
                + "InputInnerClassCheck.java"), expected);
    }
}
