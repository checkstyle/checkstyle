package com.puppycrawl.tools.checkstyle.checks.design;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class ThrowsCountCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testDefault() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(ThrowsCountCheck.class);

        String[] expected = {
            "14:20: Throws count is 2 (max allowed is 1).",
            "18:20: Throws count is 2 (max allowed is 1).",
            "22:20: Throws count is 3 (max allowed is 1).",
        };

        verify(checkConfig, getPath("design" + File.separator + "InputThrowsCount.java"), expected);
    }

    @Test
    public void testMax() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(ThrowsCountCheck.class);
        checkConfig.addAttribute("max", "2");

        String[] expected = {
            "22:20: Throws count is 3 (max allowed is 2).",
        };

        verify(checkConfig, getPath("design" + File.separator + "InputThrowsCount.java"), expected);
    }
}
