package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class GenericWhitespaceCheckTest
    extends BaseCheckTestCase
{
    private DefaultConfiguration mCheckConfig;

    @Override
    public void setUp()
    {
        mCheckConfig = createCheckConfig(GenericWhitespaceCheck.class);
    }

    public void testDefault() throws Exception
    {
        final String[] expected = {
            "12:13: '<' is preceded with whitespace.",
            "12:15: '<' is followed by whitespace.",
            "12:23: '>' is preceded with whitespace.",
            "12:43: '<' is preceded with whitespace.",
            "12:45: '<' is followed by whitespace.",
            "12:53: '>' is preceded with whitespace.",
            "13:13: '<' is preceded with whitespace.",
            "13:15: '<' is followed by whitespace.",
            "13:20: '<' is preceded with whitespace.",
            "13:22: '<' is followed by whitespace.",
            "13:30: '>' is preceded with whitespace.",
            "13:32: '>' is followed by whitespace.",
            "13:32: '>' is preceded with whitespace.",
            "13:52: '<' is preceded with whitespace.",
            "13:54: '<' is followed by whitespace.",
            "13:59: '<' is preceded with whitespace.",
            "13:61: '<' is followed by whitespace.",
            "13:69: '>' is preceded with whitespace.",
            "13:71: '>' is followed by whitespace.",
            "13:71: '>' is preceded with whitespace.",
        };
        verify(mCheckConfig,
                getPath("whitespace/InputGenericWhitespaceCheck.java"),
                expected);
    }
}
