package com.puppycrawl.tools.checkstyle.checks.design;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class VisibilityModifierCheckTest
    extends BaseCheckTestSupport
{
    private Checker getChecker() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(VisibilityModifierCheck.class);
        checkConfig.addAttribute("publicMemberPattern", "^f[A-Z][a-zA-Z0-9]*$");
        return createChecker(checkConfig);
    }

    @Test
    public void testInner()
        throws Exception
    {
        final String[] expected = {
            "30:24: Variable 'rData' must be private and have accessor methods.",
            "33:27: Variable 'protectedVariable' must be private and have accessor methods.",
            "36:17: Variable 'packageVariable' must be private and have accessor methods.",
            "41:29: Variable 'sWeird' must be private and have accessor methods.",
            "43:19: Variable 'sWeird2' must be private and have accessor methods.",
            "77:20: Variable 'someValue' must be private and have accessor methods.",
        };
        verify(getChecker(), getPath("InputInner.java"), expected);
    }

    @Test
    public void testIgnoreAccess()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(VisibilityModifierCheck.class);
        checkConfig.addAttribute("publicMemberPattern", "^r[A-Z]");
        checkConfig.addAttribute("protectedAllowed", "true");
        checkConfig.addAttribute("packageAllowed", "true");
        final String[] expected = {
            "17:20: Variable 'fData' must be private and have accessor methods.",
            "77:20: Variable 'someValue' must be private and have accessor methods.",
        };
        verify(checkConfig, getPath("InputInner.java"), expected);
    }

    @Test
    public void testSimple() throws Exception {
        final String[] expected = {
            "39:19: Variable 'mNumCreated2' must be private and have accessor methods.",
            "49:23: Variable 'sTest1' must be private and have accessor methods.",
            "51:26: Variable 'sTest3' must be private and have accessor methods.",
            "53:16: Variable 'sTest2' must be private and have accessor methods.",
            "56:9: Variable 'mTest1' must be private and have accessor methods.",
            "58:16: Variable 'mTest2' must be private and have accessor methods.",
        };
        verify(getChecker(), getPath("InputSimple.java"), expected);
    }

    @Test
    public void testStrictJavadoc()
            throws Exception
    {
        final String[] expected = {
            "44:9: Variable 'mLen' must be private and have accessor methods.",
            "45:19: Variable 'mDeer' must be private and have accessor methods.",
            "46:16: Variable 'aFreddo' must be private and have accessor methods.",
        };
        verify(getChecker(), getPath("InputPublicOnly.java"), expected);
    }
}
