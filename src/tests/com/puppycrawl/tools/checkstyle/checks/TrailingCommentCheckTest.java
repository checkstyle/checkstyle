package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class TrailingCommentCheckTest extends BaseCheckTestCase
{
    public void testDefaultTokens()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TrailingCommentCheck.class);
//          checkConfig.addAttribute("tokens", "CTOR_DEF");
        final String[] expected = {
            "2: Don't use trailing comments.",
            "5: Don't use trailing comments.",
            "6: Don't use trailing comments.",
            "16: Too many comments.",
            "17: Too many comments.",
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }
}
