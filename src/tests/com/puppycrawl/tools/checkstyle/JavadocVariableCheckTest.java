package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.checks.JavadocVariableCheck;


public class JavadocVariableCheckTest
    extends BaseCheckTestCase
{
    public void testDefault()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(JavadocVariableCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputTags.java");
        final String[] expected = {
            "11:5: Missing a Javadoc comment.",
        };
        verify(c, fname, expected);
    }

    public void testAnother()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(JavadocVariableCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputInner.java");
        final String[] expected = {
            "17:9: Missing a Javadoc comment.",
            "24:9: Missing a Javadoc comment.",
            "30:13: Missing a Javadoc comment.",
        };
        verify(c, fname, expected);
    }

    public void testAnother2()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(JavadocVariableCheck.class.getName());
        checkConfig.addProperty("scope", Scope.PUBLIC.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputInner.java");
        final String[] expected = {
        };
        verify(c, fname, expected);
    }

    public void testAnother3()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(JavadocVariableCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputPublicOnly.java");
        final String[] expected = {
            "11:9: Missing a Javadoc comment.",
            "16:13: Missing a Javadoc comment.",
            "36:9: Missing a Javadoc comment.",
            "43:5: Missing a Javadoc comment.",
            "44:5: Missing a Javadoc comment.",
            "45:5: Missing a Javadoc comment.",
            "46:5: Missing a Javadoc comment.",
        };
        verify(c, fname, expected);
    }
    public void testAnother4()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(JavadocVariableCheck.class.getName());
        checkConfig.addProperty("scope", Scope.PUBLIC.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputPublicOnly.java");
        final String[] expected = {
            "46:5: Missing a Javadoc comment.",
        };
        verify(c, fname, expected);
    }
}
