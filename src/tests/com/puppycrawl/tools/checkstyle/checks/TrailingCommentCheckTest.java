package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class TrailingCommentCheckTest extends BaseCheckTestCase
{
    public void testDefaults()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TrailingCommentCheck.class);
        final String[] expected = {
            "2: Don't use trailing comments.",
            "5: Don't use trailing comments.",
            "6: Don't use trailing comments.",
            "16: Don't use trailing comments.",
            "17: Don't use trailing comments.",
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }
}
