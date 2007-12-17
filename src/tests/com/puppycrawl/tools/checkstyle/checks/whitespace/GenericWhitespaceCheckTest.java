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
            "14:13: '<' is preceded with whitespace.",
            "14:15: '<' is followed by whitespace.",
            "14:23: '>' is preceded with whitespace.",
            "14:43: '<' is preceded with whitespace.",
            "14:45: '<' is followed by whitespace.",
            "14:53: '>' is preceded with whitespace.",
            "15:13: '<' is preceded with whitespace.",
            "15:15: '<' is followed by whitespace.",
            "15:20: '<' is preceded with whitespace.",
            "15:22: '<' is followed by whitespace.",
            "15:30: '>' is preceded with whitespace.",
            "15:32: '>' is followed by whitespace.",
            "15:32: '>' is preceded with whitespace.",
            "15:52: '<' is preceded with whitespace.",
            "15:54: '<' is followed by whitespace.",
            "15:59: '<' is preceded with whitespace.",
            "15:61: '<' is followed by whitespace.",
            "15:69: '>' is preceded with whitespace.",
            "15:71: '>' is followed by whitespace.",
            "15:71: '>' is preceded with whitespace.",
            "28:17: '<' is not preceded with whitespace.",
            "28:21: '>' is followed by an illegal character.",
        };
        verify(mCheckConfig,
                getPath("whitespace/InputGenericWhitespaceCheck.java"),
                expected);
    }
}
