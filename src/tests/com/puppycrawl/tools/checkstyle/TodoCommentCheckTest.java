package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.TodoCommentCheck;

public class TodoCommentCheckTest
    extends BaseCheckTestCase
{
    public void testIt()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TodoCommentCheck.class);
        checkConfig.addAttribute("format", "FIXME:");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSimple.java");
        final String[] expected = {
            "161: Comment matches to-do format 'FIXME:'.",
            "162: Comment matches to-do format 'FIXME:'.",
            "163: Comment matches to-do format 'FIXME:'.",
            "167: Comment matches to-do format 'FIXME:'.",
        };
        verify(c, fname, expected);
    }
}
