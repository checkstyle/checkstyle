package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Scope;


public class JavadocVariableCheckTest
    extends BaseCheckTestCase
{
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocVariableCheck.class);
        final String[] expected = {
            "11:5: Missing a Javadoc comment.",
        };
        verify(checkConfig, getPath("InputTags.java"), expected);
    }

    public void testAnother()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocVariableCheck.class);
        final String[] expected = {
            "17:9: Missing a Javadoc comment.",
            "24:9: Missing a Javadoc comment.",
            "30:13: Missing a Javadoc comment.",
        };
        verify(checkConfig, getPath("InputInner.java"), expected);
    }

    public void testAnother2()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocVariableCheck.class);
        checkConfig.addAttribute("scope", Scope.PUBLIC.getName());
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputInner.java"), expected);
    }

    public void testAnother3()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocVariableCheck.class);
        final String[] expected = {
            "11:9: Missing a Javadoc comment.",
            "16:13: Missing a Javadoc comment.",
            "36:9: Missing a Javadoc comment.",
            "43:5: Missing a Javadoc comment.",
            "44:5: Missing a Javadoc comment.",
            "45:5: Missing a Javadoc comment.",
            "46:5: Missing a Javadoc comment.",
        };
        verify(checkConfig, getPath("InputPublicOnly.java"), expected);
    }
    public void testAnother4()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocVariableCheck.class);
        checkConfig.addAttribute("scope", Scope.PUBLIC.getName());
        final String[] expected = {
            "46:5: Missing a Javadoc comment.",
        };
        verify(checkConfig, getPath("InputPublicOnly.java"), expected);
    }
}
