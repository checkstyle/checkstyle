package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.checks.JavadocTypeCheck;

/**
 * @author Oliver.Burn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class JavadocTypeCheckTest extends BaseCheckTestCase
{
    public void testTags() throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(JavadocTypeCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputTags.java");
        final String[] expected =
        {
            "8: Missing a Javadoc comment.",
        };
        verify(c, fname, expected);
    }

    public void testInner() throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(JavadocTypeCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputInner.java");
        final String[] expected =
        {
            "14: Missing a Javadoc comment.",
            "21: Missing a Javadoc comment.",
            "27: Missing a Javadoc comment.",
        };
        verify(c, fname, expected);
    }

    public void testStrict() throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(JavadocTypeCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputPublicOnly.java");
        final String[] expected =
        {
            "7: Missing a Javadoc comment.",
            "9: Missing a Javadoc comment.",
            "14: Missing a Javadoc comment.",
            "34: Missing a Javadoc comment.",
        };
        verify(c, fname, expected);
    }

    public void testProtected() throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(JavadocTypeCheck.class.getName());
        checkConfig.addProperty("scope", Scope.PROTECTED.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputPublicOnly.java");
        final String[] expected =
        {
            "7: Missing a Javadoc comment.",
        };
        verify(c, fname, expected);
    }

    public void testPublic() throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(JavadocTypeCheck.class.getName());
        checkConfig.addProperty("scope", Scope.PUBLIC.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputScopeInnerInterfaces.java");
        final String[] expected =
        {
            "7: Missing a Javadoc comment.",
            "38: Missing a Javadoc comment.",
        };
        verify(c, fname, expected);
    }

    public void testProtest() throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(JavadocTypeCheck.class.getName());
        checkConfig.addProperty("scope", Scope.PROTECTED.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputScopeInnerInterfaces.java");
        final String[] expected =
        {
            "7: Missing a Javadoc comment.",
            "29: Missing a Javadoc comment.",
            "38: Missing a Javadoc comment.",
        };
        verify(c, fname, expected);
    }

    public void testPkg() throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(JavadocTypeCheck.class.getName());
        checkConfig.addProperty(
            "scope",
            Scope.getInstance("package").getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputScopeInnerClasses.java");
        final String[] expected =
        {
            "18: Missing a Javadoc comment.",
            "20: Missing a Javadoc comment.",
            "22: Missing a Javadoc comment.",
        };
        verify(c, fname, expected);
    }

    public void testEclipse() throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(JavadocTypeCheck.class.getName());
        checkConfig.addProperty(
            "scope",
            Scope.getInstance("public").getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputScopeInnerClasses.java");
        final String[] expected =
        {
            "18: Missing a Javadoc comment.",
        };
        verify(c, fname, expected);
    }

    public void testAts() throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(JavadocTypeCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputWhitespace.java");
        final String[] expected =
        {
            "13: Type Javadoc comment is missing an @author tag.",
        };
        verify(c, fname, expected);
    }

    public void testNoAuthor()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(JavadocTypeCheck.class.getName());
        checkConfig.addProperty("allowNoAuthor", "false");
        checkConfig.addProperty("requireVersion", "false");

        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputJavadoc.java");
        final String[] expected = {
            "11: Type Javadoc comment is missing an @author tag."
        };
        verify(c, fname, expected);
    }

    public void testNoVersion()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(JavadocTypeCheck.class.getName());
        checkConfig.addProperty("allowNoAuthor", "true");
        checkConfig.addProperty("requireVersion", "true");

        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputJavadoc.java");
        final String[] expected = {
            "11: Type Javadoc comment is missing an @version tag."
        };
        verify(c, fname, expected);
    }
}
