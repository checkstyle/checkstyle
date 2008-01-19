package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class TodoCommentCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testIt()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TodoCommentCheck.class);
        checkConfig.addAttribute("format", "FIXME:");
        final String[] expected = {
            "161: Comment matches to-do format 'FIXME:'.",
            "162: Comment matches to-do format 'FIXME:'.",
            "163: Comment matches to-do format 'FIXME:'.",
            "167: Comment matches to-do format 'FIXME:'.",
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }
}
