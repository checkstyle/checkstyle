package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class OuterTypeFilenameCheckTest extends BaseCheckTestSupport {

    @Test
    public void testGood() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OuterTypeFilenameCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }
    @Test
    public void testBad() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OuterTypeFilenameCheck.class);
        final String[] expected = {
            "3: The name of the outer type and the file do not match."
        };
        verify(checkConfig, getPath("Input15Extensions.java"), expected);
    }
}