package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Before;
import org.junit.Test;

public class TrailingCommentCheckTest extends BaseCheckTestSupport
{
    DefaultConfiguration mCheckConfig;

    @Before
    public void setUp() {
        mCheckConfig = createCheckConfig(TrailingCommentCheck.class);
    }

    @Test
    public void testDefaults() throws Exception
    {
        final String[] expected = {
            "2: Don't use trailing comments.",
            "5: Don't use trailing comments.",
            "6: Don't use trailing comments.",
            "16: Don't use trailing comments.",
            "17: Don't use trailing comments.",
            "27: Don't use trailing comments.",
        };
        verify(mCheckConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testLegalComment() throws Exception
    {
        mCheckConfig.addAttribute("legalComment", "^NOI18N$");
        final String[] expected = {
            "2: Don't use trailing comments.",
            "5: Don't use trailing comments.",
            "6: Don't use trailing comments.",
            "16: Don't use trailing comments.",
            "17: Don't use trailing comments.",
        };
        verify(mCheckConfig, getPath("InputTrailingComment.java"), expected);
    }
}
