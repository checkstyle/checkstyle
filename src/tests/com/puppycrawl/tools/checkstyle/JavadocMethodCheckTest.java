package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.checks.JavadocMethodCheck;


public class JavadocMethodCheckTest
    extends BaseCheckTestCase
{
    public void testTags()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(JavadocMethodCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputTags.java");
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
        };

        verify(c, fname, expected);
    }

    public void testTagsWithResolver()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(JavadocMethodCheck.class.getName());
        checkConfig.addProperty("checkUnusedThrows", "true");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputTags.java");
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
        };
        verify(c, fname, expected);
    }

    public void testStrictJavadoc()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(JavadocMethodCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputPublicOnly.java");
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
        verify(c, fname, expected);
    }

    public void testNoJavadoc()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(JavadocMethodCheck.class.getName());
        checkConfig.addProperty("scope", Scope.NOTHING.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputPublicOnly.java");
        final String[] expected = {
        };
        verify(c, fname, expected);
    }

    // pre 1.4 relaxed mode is roughly equivalent with check=protected
    public void testRelaxedJavadoc()
        throws Exception
    {

        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(JavadocMethodCheck.class.getName());
        checkConfig.addProperty("scope", Scope.PROTECTED.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputPublicOnly.java");
        final String[] expected = {
            "59:5: Missing a Javadoc comment.",
            "64:5: Missing a Javadoc comment.",
            "79:5: Missing a Javadoc comment.",
            "84:5: Missing a Javadoc comment."
        };
        verify(c, fname, expected);
    }


    public void testScopeInnerInterfacesPublic()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(JavadocMethodCheck.class.getName());
        checkConfig.addProperty("scope", Scope.PUBLIC.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputScopeInnerInterfaces.java");
        final String[] expected = {
            "43:9: Missing a Javadoc comment.",
            "44:9: Missing a Javadoc comment."
        };
        verify(c, fname, expected);
    }

    public void testScopeAnonInnerPrivate()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(JavadocMethodCheck.class.getName());
        checkConfig.addProperty("scope", Scope.PRIVATE.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputScopeAnonInner.java");
        final String[] expected = {
        };
        verify(c, fname, expected);
    }

    public void testScopeAnonInnerAnonInner()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(JavadocMethodCheck.class.getName());
        checkConfig.addProperty("scope", Scope.ANONINNER.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputScopeAnonInner.java");
        final String[] expected = {
            "26:9: Missing a Javadoc comment.",
            "39:17: Missing a Javadoc comment.",
            "53:17: Missing a Javadoc comment.",
        };
        verify(c, fname, expected);
    }

        public void testScopeAnonInnerWithResolver()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(JavadocMethodCheck.class.getName());
        checkConfig.addProperty("checkUnusedThrows", "true");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputScopeAnonInner.java");
        final String[] expected = {
        };
        verify(c, fname, expected);
    }
}
