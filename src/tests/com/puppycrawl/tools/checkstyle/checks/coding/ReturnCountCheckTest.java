package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;
public class ReturnCountCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testDefault() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ReturnCountCheck.class);
        final String[] expected = {
            "18:5: Return count is 7 (max allowed is 2).",
            "35:17: Return count is 6 (max allowed is 2).",
        };
        verify(checkConfig, getPath("coding" + File.separator + "InputReturnCount.java"), expected);
    }

    @Test
    public void testFormat() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ReturnCountCheck.class);
        checkConfig.addAttribute("format", "^$");
        final String[] expected = {
            "5:5: Return count is 7 (max allowed is 2).",
            "18:5: Return count is 7 (max allowed is 2).",
            "35:17: Return count is 6 (max allowed is 2).",
        };
        verify(checkConfig, getPath("coding" + File.separator + "InputReturnCount.java"), expected);
    }
}
