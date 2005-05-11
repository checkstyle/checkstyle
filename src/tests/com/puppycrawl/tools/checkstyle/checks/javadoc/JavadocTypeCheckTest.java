package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Scope;

import java.io.File;

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
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        final String[] expected =
        {
            "8: Missing a Javadoc comment.",
            "302: Missing a Javadoc comment.",
            "327: Missing a Javadoc comment.",
        };
        verify(checkConfig, getPath("InputTags.java"), expected);
    }

    public void testInner() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        final String[] expected =
        {
            "14: Missing a Javadoc comment.",
            "21: Missing a Javadoc comment.",
            "27: Missing a Javadoc comment.",
        };
        verify(checkConfig, getPath("InputInner.java"), expected);
    }

    public void testStrict() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        final String[] expected =
        {
            "7: Missing a Javadoc comment.",
            "9: Missing a Javadoc comment.",
            "14: Missing a Javadoc comment.",
            "34: Missing a Javadoc comment.",
        };
        verify(checkConfig, getPath("InputPublicOnly.java"), expected);
    }

    public void testProtected() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected =
        {
            "7: Missing a Javadoc comment.",
        };
        verify(checkConfig, getPath("InputPublicOnly.java"), expected);
    }

    public void testPublic() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PUBLIC.getName());
        final String[] expected =
        {
            "7: Missing a Javadoc comment.",
            "38: Missing a Javadoc comment.",
        };
        verify(checkConfig, getPath("InputScopeInnerInterfaces.java"), expected);
    }

    public void testProtest() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected =
        {
            "7: Missing a Javadoc comment.",
            "29: Missing a Javadoc comment.",
            "38: Missing a Javadoc comment.",
            "65: Missing a Javadoc comment.",
        };
        verify(checkConfig, getPath("InputScopeInnerInterfaces.java"), expected);
    }

    public void testPkg() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute(
            "scope",
            Scope.getInstance("package").getName());
        final String[] expected =
        {
            "18: Missing a Javadoc comment.",
            "20: Missing a Javadoc comment.",
            "22: Missing a Javadoc comment.",
        };
        verify(checkConfig, getPath("InputScopeInnerClasses.java"), expected);
    }

    public void testEclipse() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute(
            "scope",
            Scope.getInstance("public").getName());
        final String[] expected =
        {
            "18: Missing a Javadoc comment.",
        };
        verify(checkConfig, getPath("InputScopeInnerClasses.java"), expected);
    }

    public void testAuthorRequired() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("authorFormat", "\\S");
        final String[] expected =
        {
            "13: Type Javadoc comment is missing an @author tag.",
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }
    
    public void testAuthorRegularEx()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("authorFormat", "0*");
        final String[] expected = {
            "22: Type Javadoc comment is missing an @author tag.",
        };
        verify(checkConfig, getPath("InputJavadoc.java"), expected);
    }

    public void testAuthorRegularExError()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("authorFormat", "ABC");
        final String[] expected = {
            "13: Type Javadoc tag @author must match pattern 'ABC'.",
            "22: Type Javadoc comment is missing an @author tag.",
            "31: Type Javadoc tag @author must match pattern 'ABC'.",
        };
        verify(checkConfig, getPath("InputJavadoc.java"), expected);
    }

    public void testVersionRequired()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("versionFormat", "\\S");
        final String[] expected = {
            "13: Type Javadoc comment is missing an @version tag.",
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }
    
    public void testVersionRegularEx()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("versionFormat", "^\\p{Digit}+\\.\\p{Digit}+$");
        final String[] expected = {
            "22: Type Javadoc comment is missing an @version tag.",
        };
        verify(checkConfig, getPath("InputJavadoc.java"), expected);
    }
    
    public void testVersionRegularExError()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("versionFormat", "\\$Revision.*\\$");
        final String[] expected = {
            "13: Type Javadoc tag @version must match pattern '\\$Revision.*\\$'.",
            "22: Type Javadoc comment is missing an @version tag.",
            "31: Type Javadoc tag @version must match pattern '\\$Revision.*\\$'.",
            "40: Type Javadoc tag @version must match pattern '\\$Revision.*\\$'.",
        };
        verify(checkConfig, getPath("InputJavadoc.java"), expected);
    }

    public void testScopes() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        final String[] expected = {
            "1: Missing a Javadoc comment.",
            "13: Missing a Javadoc comment.",
            "25: Missing a Javadoc comment.",
            "37: Missing a Javadoc comment.",
            "50: Missing a Javadoc comment.",
            "61: Missing a Javadoc comment.",
            "73: Missing a Javadoc comment.",
            "85: Missing a Javadoc comment.",
            "97: Missing a Javadoc comment.",
        };
        verify(checkConfig,
               getPath("javadoc" + File.separator +"InputNoJavadoc.java"),
               expected);
    }

    public void testScopes2() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "1: Missing a Javadoc comment.",
            "13: Missing a Javadoc comment.",
        };
        verify(checkConfig,
               getPath("javadoc" + File.separator +"InputNoJavadoc.java"),
               expected);
    }

    public void testExcludeScope() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PRIVATE.getName());
        checkConfig.addAttribute("excludeScope", Scope.PROTECTED.getName());
        final String[] expected = {
            "25: Missing a Javadoc comment.",
            "37: Missing a Javadoc comment.",
            "50: Missing a Javadoc comment.",
            "61: Missing a Javadoc comment.",
            "73: Missing a Javadoc comment.",
            "85: Missing a Javadoc comment.",
            "97: Missing a Javadoc comment.",
        };
        verify(checkConfig,
               getPath("javadoc" + File.separator +"InputNoJavadoc.java"),
               expected);
    }
}
