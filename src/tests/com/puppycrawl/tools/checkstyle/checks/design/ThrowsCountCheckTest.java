package com.puppycrawl.tools.checkstyle.checks.design;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import java.io.File;

public class ThrowsCountCheckTest extends BaseCheckTestCase {
    public void testDefault() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(ThrowsCountCheck.class);

        String[] expected = {
            "14:20: Throws count is 2 (max allowed is 1).",
            "18:20: Throws count is 2 (max allowed is 1).",
            "22:20: Throws count is 3 (max allowed is 1).",
        };

        verify(checkConfig, getPath("design" + File.separator + "InputThrowsCount.java"), expected);
    }

    public void testMax() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(ThrowsCountCheck.class);
        checkConfig.addAttribute("max", "2");

        String[] expected = {
            "22:20: Throws count is 3 (max allowed is 2).",
        };

        verify(checkConfig, getPath("design" + File.separator + "InputThrowsCount.java"), expected);
    }
}
