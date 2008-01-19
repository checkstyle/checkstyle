package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

/**
 * Testcase for the JavaNCSS-Check.
 *
 * @author Lars Ködderitzsch
 */
public class JavaNCSSCheckTest extends BaseCheckTestSupport
{

    @Test
    public void test() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(JavaNCSSCheck.class);

        checkConfig.addAttribute("methodMaximum", "0");
        checkConfig.addAttribute("classMaximum", "0");
        checkConfig.addAttribute("fileMaximum", "0");

        String[] expected = {
                "2:1: NCSS for this file is 35 (max allowed is 0).",
                "9:1: NCSS for this class is 22 (max allowed is 0).",
                "14:5: NCSS for this method is 2 (max allowed is 0).",
                "21:5: NCSS for this method is 4 (max allowed is 0).",
                "30:5: NCSS for this method is 12 (max allowed is 0).",
                "42:13: NCSS for this method is 2 (max allowed is 0).",
                "49:5: NCSS for this class is 2 (max allowed is 0).",
                "56:1: NCSS for this class is 10 (max allowed is 0).",
                "61:5: NCSS for this method is 8 (max allowed is 0).",};

        verify(checkConfig, getPath("metrics" + File.separator
                + "JavaNCSSCheckTestInput.java"), expected);
    }
}
