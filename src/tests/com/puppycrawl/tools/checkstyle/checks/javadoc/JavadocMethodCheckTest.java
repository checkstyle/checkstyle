package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Scope;

import java.io.File;

public class JavadocMethodCheckTest
    extends BaseCheckTestCase
{
    public void testTags()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocMethodCheck.class);
        final String[] expected = {
            "14:5: Missing a Javadoc comment.",
            "18: Unused @param tag for 'unused'.",
            "24: Expected an @return tag.",
            "33: Expected an @return tag.",
            "40:16: Expected @throws tag for 'Exception'.",
            "49:16: Expected @throws tag for 'Exception'.",
            "53: Unused @throws tag for 'WrongException'.",
            "55:16: Expected @throws tag for 'Exception'.",
            "55:27: Expected @throws tag for 'NullPointerException'.",
            "60:22: Expected @param tag for 'aOne'.",
            "68:22: Expected @param tag for 'aOne'.",
            "72: Unused @param tag for 'WrongParam'.",
            "73:23: Expected @param tag for 'aOne'.",
            "73:33: Expected @param tag for 'aTwo'.",
            "78: Unused @param tag for 'Unneeded'.",
            "79: Unused Javadoc tag.",
            "87: Duplicate @return tag.",
            "109:23: Expected @param tag for 'aOne'.",
            "109:55: Expected @param tag for 'aFour'.",
            "109:66: Expected @param tag for 'aFive'.",
            "178: Unused @throws tag for 'ThreadDeath'.",
            "179: Unused @throws tag for 'ArrayStoreException'.",
            "236: Unused @throws tag for 'java.io.FileNotFoundException'.",
            "254: Unused @throws tag for 'java.io.FileNotFoundException'.",
            "256:28: Expected @throws tag for 'IOException'.",
            "262: Unused @param tag for 'aParam'.",
            "320:9: Missing a Javadoc comment.",
            "329:5: Missing a Javadoc comment.",
            "333: Unused Javadoc tag.",
        };

        verify(checkConfig, getPath("InputTags.java"), expected);
    }

    public void testTagsWithResolver()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("allowUndeclaredRTE", "true");
        final String[] expected = {
            "14:5: Missing a Javadoc comment.",
            "18: Unused @param tag for 'unused'.",
            "24: Expected an @return tag.",
            "33: Expected an @return tag.",
            "40:16: Expected @throws tag for 'Exception'.",
            "49:16: Expected @throws tag for 'Exception'.",
            "53: Unable to get class information for @throws tag 'WrongException'.",
            "53: Unused @throws tag for 'WrongException'.",
            "55:16: Expected @throws tag for 'Exception'.",
            "55:27: Expected @throws tag for 'NullPointerException'.",
            "60:22: Expected @param tag for 'aOne'.",
            "68:22: Expected @param tag for 'aOne'.",
            "72: Unused @param tag for 'WrongParam'.",
            "73:23: Expected @param tag for 'aOne'.",
            "73:33: Expected @param tag for 'aTwo'.",
            "78: Unused @param tag for 'Unneeded'.",
            "79: Unused Javadoc tag.",
            "87: Duplicate @return tag.",
            "109:23: Expected @param tag for 'aOne'.",
            "109:55: Expected @param tag for 'aFour'.",
            "109:66: Expected @param tag for 'aFive'.",
            "236: Unused @throws tag for 'java.io.FileNotFoundException'.",
            "254: Unused @throws tag for 'java.io.FileNotFoundException'.",
            "256:28: Expected @throws tag for 'IOException'.",
            "262: Unused @param tag for 'aParam'.",
            "320:9: Missing a Javadoc comment.",
            "329:5: Missing a Javadoc comment.",
            "333: Unused Javadoc tag.",
        };
        verify(checkConfig, getPath("InputTags.java"), expected);
    }

    public void testStrictJavadoc()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocMethodCheck.class);
        final String[] expected = {
            "12:9: Missing a Javadoc comment.",
            "18:13: Missing a Javadoc comment.",
            "25:13: Missing a Javadoc comment.",
            "38:9: Missing a Javadoc comment.",
            "49:5: Missing a Javadoc comment.",
            "54:5: Missing a Javadoc comment.",
            "59:5: Missing a Javadoc comment.",
            "64:5: Missing a Javadoc comment.",
            "69:5: Missing a Javadoc comment.",
            "74:5: Missing a Javadoc comment.",
            "79:5: Missing a Javadoc comment.",
            "84:5: Missing a Javadoc comment.",
            "94:32: Expected @param tag for 'aA'."
        };
        verify(checkConfig, getPath("InputPublicOnly.java"), expected);
    }

    public void testNoJavadoc()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.NOTHING.getName());
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputPublicOnly.java"), expected);
    }

    // pre 1.4 relaxed mode is roughly equivalent with check=protected
    public void testRelaxedJavadoc()
        throws Exception
    {

        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "59:5: Missing a Javadoc comment.",
            "64:5: Missing a Javadoc comment.",
            "79:5: Missing a Javadoc comment.",
            "84:5: Missing a Javadoc comment."
        };
        verify(checkConfig, getPath("InputPublicOnly.java"), expected);
    }


    public void testScopeInnerInterfacesPublic()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.PUBLIC.getName());
        final String[] expected = {
            "43:9: Missing a Javadoc comment.",
            "44:9: Missing a Javadoc comment."
        };
        verify(checkConfig, getPath("InputScopeInnerInterfaces.java"), expected);
    }

    public void testScopeAnonInnerPrivate()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.PRIVATE.getName());
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputScopeAnonInner.java"), expected);
    }

    public void testScopeAnonInnerAnonInner()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.ANONINNER.getName());
        final String[] expected = {
            "26:9: Missing a Javadoc comment.",
            "39:17: Missing a Javadoc comment.",
            "53:17: Missing a Javadoc comment.",
        };
        verify(checkConfig, getPath("InputScopeAnonInner.java"), expected);
    }

    public void testScopeAnonInnerWithResolver()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("allowUndeclaredRTE", "true");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputScopeAnonInner.java"), expected);
    }

    public void testTagsWithSubclassesAllowed()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("allowThrowsTagsForSubclasses", "true");
        final String[] expected = {
            "14:5: Missing a Javadoc comment.",
            "18: Unused @param tag for 'unused'.",
            "24: Expected an @return tag.",
            "33: Expected an @return tag.",
            "40:16: Expected @throws tag for 'Exception'.",
            "49:16: Expected @throws tag for 'Exception'.",
            "53: Unable to get class information for @throws tag 'WrongException'.",
            "53: Unused @throws tag for 'WrongException'.",
            "55:16: Expected @throws tag for 'Exception'.",
            "55:27: Expected @throws tag for 'NullPointerException'.",
            "60:22: Expected @param tag for 'aOne'.",
            "68:22: Expected @param tag for 'aOne'.",
            "72: Unused @param tag for 'WrongParam'.",
            "73:23: Expected @param tag for 'aOne'.",
            "73:33: Expected @param tag for 'aTwo'.",
            "78: Unused @param tag for 'Unneeded'.",
            "79: Unused Javadoc tag.",
            "87: Duplicate @return tag.",
            "109:23: Expected @param tag for 'aOne'.",
            "109:55: Expected @param tag for 'aFour'.",
            "109:66: Expected @param tag for 'aFive'.",
            "178: Unused @throws tag for 'ThreadDeath'.",
            "179: Unused @throws tag for 'ArrayStoreException'.",
            "256:28: Expected @throws tag for 'IOException'.",
            "262: Unused @param tag for 'aParam'.",
            "320:9: Missing a Javadoc comment.",
            "329:5: Missing a Javadoc comment.",
            "333: Unused Javadoc tag.",
        };
        verify(checkConfig, getPath("InputTags.java"), expected);
    }

    public void testScopes() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocMethodCheck.class);
        final String[] expected = {
            "8:5: Missing a Javadoc comment.",
            "9:5: Missing a Javadoc comment.",
            "10:5: Missing a Javadoc comment.",
            "11:5: Missing a Javadoc comment.",
            "19:9: Missing a Javadoc comment.",
            "20:9: Missing a Javadoc comment.",
            "21:9: Missing a Javadoc comment.",
            "22:9: Missing a Javadoc comment.",
            "31:9: Missing a Javadoc comment.",
            "32:9: Missing a Javadoc comment.",
            "33:9: Missing a Javadoc comment.",
            "34:9: Missing a Javadoc comment.",
            "43:9: Missing a Javadoc comment.",
            "44:9: Missing a Javadoc comment.",
            "45:9: Missing a Javadoc comment.",
            "46:9: Missing a Javadoc comment.",
            "56:5: Missing a Javadoc comment.",
            "57:5: Missing a Javadoc comment.",
            "58:5: Missing a Javadoc comment.",
            "59:5: Missing a Javadoc comment.",
            "67:9: Missing a Javadoc comment.",
            "68:9: Missing a Javadoc comment.",
            "69:9: Missing a Javadoc comment.",
            "70:9: Missing a Javadoc comment.",
            "79:9: Missing a Javadoc comment.",
            "80:9: Missing a Javadoc comment.",
            "81:9: Missing a Javadoc comment.",
            "82:9: Missing a Javadoc comment.",
            "91:9: Missing a Javadoc comment.",
            "92:9: Missing a Javadoc comment.",
            "93:9: Missing a Javadoc comment.",
            "94:9: Missing a Javadoc comment.",
            "103:9: Missing a Javadoc comment.",
            "104:9: Missing a Javadoc comment.",
            "105:9: Missing a Javadoc comment.",
            "106:9: Missing a Javadoc comment.",
        };
        verify(checkConfig,
               getPath("javadoc" + File.separator +"InputNoJavadoc.java"),
               expected);
    }

    public void testScopes2() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "8:5: Missing a Javadoc comment.",
            "9:5: Missing a Javadoc comment.",
            "19:9: Missing a Javadoc comment.",
            "20:9: Missing a Javadoc comment.",
        };
        verify(checkConfig,
               getPath("javadoc" + File.separator +"InputNoJavadoc.java"),
               expected);
    }

    public void testExcludeScope() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.PRIVATE.getName());
        checkConfig.addAttribute("excludeScope", Scope.PROTECTED.getName());
        final String[] expected = {
            "10:5: Missing a Javadoc comment.",
            "11:5: Missing a Javadoc comment.",
            "21:9: Missing a Javadoc comment.",
            "22:9: Missing a Javadoc comment.",
            "31:9: Missing a Javadoc comment.",
            "32:9: Missing a Javadoc comment.",
            "33:9: Missing a Javadoc comment.",
            "34:9: Missing a Javadoc comment.",
            "43:9: Missing a Javadoc comment.",
            "44:9: Missing a Javadoc comment.",
            "45:9: Missing a Javadoc comment.",
            "46:9: Missing a Javadoc comment.",
            "56:5: Missing a Javadoc comment.",
            "57:5: Missing a Javadoc comment.",
            "58:5: Missing a Javadoc comment.",
            "59:5: Missing a Javadoc comment.",
            "67:9: Missing a Javadoc comment.",
            "68:9: Missing a Javadoc comment.",
            "69:9: Missing a Javadoc comment.",
            "70:9: Missing a Javadoc comment.",
            "79:9: Missing a Javadoc comment.",
            "80:9: Missing a Javadoc comment.",
            "81:9: Missing a Javadoc comment.",
            "82:9: Missing a Javadoc comment.",
            "91:9: Missing a Javadoc comment.",
            "92:9: Missing a Javadoc comment.",
            "93:9: Missing a Javadoc comment.",
            "94:9: Missing a Javadoc comment.",
            "103:9: Missing a Javadoc comment.",
            "104:9: Missing a Javadoc comment.",
            "105:9: Missing a Javadoc comment.",
            "106:9: Missing a Javadoc comment.",
        };
        verify(checkConfig,
               getPath("javadoc" + File.separator +"InputNoJavadoc.java"),
               expected);
    }
}
