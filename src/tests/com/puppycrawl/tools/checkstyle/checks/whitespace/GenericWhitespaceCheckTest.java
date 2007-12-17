package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.util.HashMap;
import java.util.Map;

public class GenericWhitespaceCheckTest
    extends BaseCheckTestCase
{
    private DefaultConfiguration mCheckConfig;

    @Override
    public void setUp()
    {
        mCheckConfig = createCheckConfig(GenericWhitespaceCheck.class);
        Map<Class<?>, Integer> x = new HashMap<Class<?>, Integer>();
        for (final Map.Entry<Class<?>, Integer> entry : x.entrySet()) {
            entry.getValue();
        }
        //for (final Entry<Class<?>, Integer> entry : entrySet())
    }

    public void testDefault() throws Exception
    {
        final String[] expected = {
            "16:13: '<' is preceded with whitespace.",
            "16:15: '<' is followed by whitespace.",
            "16:23: '>' is preceded with whitespace.",
            "16:43: '<' is preceded with whitespace.",
            "16:45: '<' is followed by whitespace.",
            "16:53: '>' is preceded with whitespace.",
            "17:13: '<' is preceded with whitespace.",
            "17:15: '<' is followed by whitespace.",
            "17:20: '<' is preceded with whitespace.",
            "17:22: '<' is followed by whitespace.",
            "17:30: '>' is preceded with whitespace.",
            "17:32: '>' is followed by whitespace.",
            "17:32: '>' is preceded with whitespace.",
            "17:52: '<' is preceded with whitespace.",
            "17:54: '<' is followed by whitespace.",
            "17:59: '<' is preceded with whitespace.",
            "17:61: '<' is followed by whitespace.",
            "17:69: '>' is preceded with whitespace.",
            "17:71: '>' is followed by whitespace.",
            "17:71: '>' is preceded with whitespace.",
            "30:17: '<' is not preceded with whitespace.",
            "30:21: '>' is followed by an illegal character.",
        };
        verify(mCheckConfig,
                getPath("whitespace/InputGenericWhitespaceCheck.java"),
                expected);
    }
}
