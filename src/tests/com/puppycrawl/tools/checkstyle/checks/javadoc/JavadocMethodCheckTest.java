////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Scope;
import java.io.File;
import org.junit.Before;
import org.junit.Test;

public class JavadocMethodCheckTest extends BaseCheckTestSupport
{
    private DefaultConfiguration mCheckConfig;

    @Before
    public void setUp()
    {
        mCheckConfig = createCheckConfig(JavadocMethodCheck.class);
    }

    @Test
    public void testTags() throws Exception
    {
        final String[] expected = {
            "14:5: Missing a Javadoc comment.",
            "18:9: Unused @param tag for 'unused'.",
            "24: Expected an @return tag.",
            "33: Expected an @return tag.",
            "40:16: Expected @throws tag for 'Exception'.",
            "49:16: Expected @throws tag for 'Exception'.",
            "53:9: Unused @throws tag for 'WrongException'.",
            "55:16: Expected @throws tag for 'Exception'.",
            "55:27: Expected @throws tag for 'NullPointerException'.",
            "60:22: Expected @param tag for 'aOne'.",
            "68:22: Expected @param tag for 'aOne'.",
            "72:9: Unused @param tag for 'WrongParam'.",
            "73:23: Expected @param tag for 'aOne'.",
            "73:33: Expected @param tag for 'aTwo'.",
            "78:8: Unused @param tag for 'Unneeded'.",
            "79: Unused Javadoc tag.",
            "87:8: Duplicate @return tag.",
            "109:23: Expected @param tag for 'aOne'.",
            "109:55: Expected @param tag for 'aFour'.",
            "109:66: Expected @param tag for 'aFive'.",
            "178:8: Unused @throws tag for 'ThreadDeath'.",
            "179:8: Unused @throws tag for 'ArrayStoreException'.",
            "236:8: Unused @throws tag for 'java.io.FileNotFoundException'.",
            "254:8: Unused @throws tag for 'java.io.FileNotFoundException'.",
            "256:28: Expected @throws tag for 'IOException'.",
            "262:8: Unused @param tag for 'aParam'.",
            "320:9: Missing a Javadoc comment.",
            "329:5: Missing a Javadoc comment.",
            "333: Unused Javadoc tag.",
        };

        verify(mCheckConfig, getSrcPath("checks/javadoc/InputTags.java"), expected);
    }

    @Test
    public void testTagsWithResolver() throws Exception
    {
        mCheckConfig.addAttribute("allowUndeclaredRTE", "true");
        final String[] expected = {
            "14:5: Missing a Javadoc comment.",
            "18:9: Unused @param tag for 'unused'.",
            "24: Expected an @return tag.",
            "33: Expected an @return tag.",
            "40:16: Expected @throws tag for 'Exception'.",
            "49:16: Expected @throws tag for 'Exception'.",
            "55:16: Expected @throws tag for 'Exception'.",
            "55:27: Expected @throws tag for 'NullPointerException'.",
            "60:22: Expected @param tag for 'aOne'.",
            "68:22: Expected @param tag for 'aOne'.",
            "72:9: Unused @param tag for 'WrongParam'.",
            "73:23: Expected @param tag for 'aOne'.",
            "73:33: Expected @param tag for 'aTwo'.",
            "78:8: Unused @param tag for 'Unneeded'.",
            "79: Unused Javadoc tag.",
            "87:8: Duplicate @return tag.",
            "109:23: Expected @param tag for 'aOne'.",
            "109:55: Expected @param tag for 'aFour'.",
            "109:66: Expected @param tag for 'aFive'.",
            "236:8: Unused @throws tag for 'java.io.FileNotFoundException'.",
            "254:8: Unused @throws tag for 'java.io.FileNotFoundException'.",
            "256:28: Expected @throws tag for 'IOException'.",
            "262:8: Unused @param tag for 'aParam'.",
            "320:9: Missing a Javadoc comment.",
            "329:5: Missing a Javadoc comment.",
            "333: Unused Javadoc tag.", };
        verify(mCheckConfig, getSrcPath("checks/javadoc/InputTags.java"), expected);
    }

    @Test
    public void testStrictJavadoc() throws Exception
    {
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
            "94:32: Expected @param tag for 'aA'.",
        };
        verify(mCheckConfig, getPath("InputPublicOnly.java"), expected);
    }

    @Test
    public void testNoJavadoc() throws Exception
    {
        mCheckConfig.addAttribute("scope", Scope.NOTHING.getName());
        final String[] expected = {};
        verify(mCheckConfig, getPath("InputPublicOnly.java"), expected);
    }

    // pre 1.4 relaxed mode is roughly equivalent with check=protected
    @Test
    public void testRelaxedJavadoc() throws Exception
    {
        mCheckConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "59:5: Missing a Javadoc comment.",
            "64:5: Missing a Javadoc comment.",
            "79:5: Missing a Javadoc comment.",
            "84:5: Missing a Javadoc comment.",
        };
        verify(mCheckConfig, getPath("InputPublicOnly.java"), expected);
    }

    @Test
    public void testScopeInnerInterfacesPublic() throws Exception
    {
        mCheckConfig.addAttribute("scope", Scope.PUBLIC.getName());
        final String[] expected = {
            "43:9: Missing a Javadoc comment.",
            "44:9: Missing a Javadoc comment.",
        };
        verify(mCheckConfig, getPath("InputScopeInnerInterfaces.java"), expected);
    }

    @Test
    public void testScopeAnonInnerPrivate() throws Exception
    {
        mCheckConfig.addAttribute("scope", Scope.PRIVATE.getName());
        final String[] expected = {};
        verify(mCheckConfig, getPath("InputScopeAnonInner.java"), expected);
    }

    @Test
    public void testScopeAnonInnerAnonInner() throws Exception
    {
        mCheckConfig.addAttribute("scope", Scope.ANONINNER.getName());
        final String[] expected = {
            "26:9: Missing a Javadoc comment.",
            "39:17: Missing a Javadoc comment.",
            "53:17: Missing a Javadoc comment.", };
        verify(mCheckConfig, getPath("InputScopeAnonInner.java"), expected);
    }

    @Test
    public void testScopeAnonInnerWithResolver() throws Exception
    {
        mCheckConfig.addAttribute("allowUndeclaredRTE", "true");
        final String[] expected = {};
        verify(mCheckConfig, getPath("InputScopeAnonInner.java"), expected);
    }

    @Test
    public void testTagsWithSubclassesAllowed() throws Exception
    {
        mCheckConfig.addAttribute("allowThrowsTagsForSubclasses", "true");
        final String[] expected = {
            "14:5: Missing a Javadoc comment.",
            "18:9: Unused @param tag for 'unused'.",
            "24: Expected an @return tag.",
            "33: Expected an @return tag.",
            "40:16: Expected @throws tag for 'Exception'.",
            "49:16: Expected @throws tag for 'Exception'.",
            "55:16: Expected @throws tag for 'Exception'.",
            "55:27: Expected @throws tag for 'NullPointerException'.",
            "60:22: Expected @param tag for 'aOne'.",
            "68:22: Expected @param tag for 'aOne'.",
            "72:9: Unused @param tag for 'WrongParam'.",
            "73:23: Expected @param tag for 'aOne'.",
            "73:33: Expected @param tag for 'aTwo'.",
            "78:8: Unused @param tag for 'Unneeded'.",
            "79: Unused Javadoc tag.",
            "87:8: Duplicate @return tag.",
            "109:23: Expected @param tag for 'aOne'.",
            "109:55: Expected @param tag for 'aFour'.",
            "109:66: Expected @param tag for 'aFive'.",
            "178:8: Unused @throws tag for 'ThreadDeath'.",
            "179:8: Unused @throws tag for 'ArrayStoreException'.",
            "256:28: Expected @throws tag for 'IOException'.",
            "262:8: Unused @param tag for 'aParam'.",
            "320:9: Missing a Javadoc comment.",
            "329:5: Missing a Javadoc comment.",
            "333: Unused Javadoc tag.", };
        verify(mCheckConfig, getSrcPath("checks/javadoc/InputTags.java"), expected);
    }

    @Test
    public void testScopes() throws Exception
    {
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
            "106:9: Missing a Javadoc comment.", };
        verify(mCheckConfig, getPath("javadoc" + File.separator
                                    + "InputNoJavadoc.java"), expected);
    }

    @Test
    public void testScopes2() throws Exception
    {
        mCheckConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "8:5: Missing a Javadoc comment.",
            "9:5: Missing a Javadoc comment.",
            "19:9: Missing a Javadoc comment.",
            "20:9: Missing a Javadoc comment.", };
        verify(mCheckConfig, getPath("javadoc" + File.separator
                                    + "InputNoJavadoc.java"), expected);
    }

    @Test
    public void testExcludeScope() throws Exception
    {
        mCheckConfig.addAttribute("scope", Scope.PRIVATE.getName());
        mCheckConfig.addAttribute("excludeScope", Scope.PROTECTED.getName());
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
            "106:9: Missing a Javadoc comment.", };
        verify(mCheckConfig, getPath("javadoc" + File.separator
                                    + "InputNoJavadoc.java"), expected);
    }

    @Test
    public void testAllowMissingJavadoc() throws Exception
    {
        mCheckConfig.addAttribute("allowMissingJavadoc", "true");
        final String[] expected = {};
        verify(mCheckConfig, getPath("javadoc" + File.separator
                                    + "InputNoJavadoc.java"), expected);
    }

    @Test
    public void testSetterGetterOff() throws Exception
    {
        final String[] expected = {
            "5:5: Missing a Javadoc comment.",
            "10:5: Missing a Javadoc comment.",
            "15:5: Missing a Javadoc comment.",
            "20:5: Missing a Javadoc comment.",
            "26:5: Missing a Javadoc comment.",
            "30:5: Missing a Javadoc comment.",
            "35:5: Missing a Javadoc comment.",
            "41:5: Missing a Javadoc comment.",
            "46:5: Missing a Javadoc comment.", };
        verify(mCheckConfig, getPath("javadoc" + File.separator
                                    + "InputSetterGetter.java"), expected);
    }

    @Test
    public void testSetterGetterOn() throws Exception
    {
        mCheckConfig.addAttribute("allowMissingPropertyJavadoc", "true");
        final String[] expected = {
            "15:5: Missing a Javadoc comment.",
            "20:5: Missing a Javadoc comment.",
            "26:5: Missing a Javadoc comment.",
            "30:5: Missing a Javadoc comment.",
            "35:5: Missing a Javadoc comment.",
            "41:5: Missing a Javadoc comment.", };
        verify(mCheckConfig, getPath("javadoc" + File.separator
                                    + "InputSetterGetter.java"), expected);
    }

    @Test
    public void testTypeParamsTags() throws Exception
    {
        final String[] expected = {
            "26:8: Unused @param tag for '<BB>'.",
            "28:13: Expected @param tag for '<Z>'.", };
        verify(mCheckConfig, getPath("InputTypeParamsTags.java"), expected);
    }

    @Test
    public void test_1168408_1() throws Exception
    {
        final String[] expected = {};
        verify(mCheckConfig, getSrcPath("checks/javadoc/Input_01.java"), expected);
    }

    @Test
    public void test_1168408_2() throws Exception
    {
        final String[] expected = {};
        verify(mCheckConfig, getSrcPath("checks/javadoc/Input_02.java"), expected);
    }

    @Test
    public void test_1168408_3() throws Exception
    {
        mCheckConfig.addAttribute("allowThrowsTagsForSubclasses", "true");
        mCheckConfig.addAttribute("allowUndeclaredRTE", "true");
        final String[] expected = {};
        verify(mCheckConfig, getSrcPath("checks/javadoc/Input_03.java"), expected);
    }

    @Test
    public void test_generics_1() throws Exception
    {
        mCheckConfig.addAttribute("allowThrowsTagsForSubclasses", "true");
        mCheckConfig.addAttribute("allowUndeclaredRTE", "true");
        final String[] expected = {
            "15:34: Expected @throws tag for 'RE'.",
            "31:13: Expected @param tag for '<NPE>'.",
            "38:12: Unused @throws tag for 'E'.",
            "41:38: Expected @throws tag for 'RuntimeException'.",
            "42:13: Expected @throws tag for 'java.lang.RuntimeException'.",
        };
        verify(mCheckConfig, getPath("javadoc/TestGenerics.java"), expected);
    }

    @Test
    public void test_generics_2() throws Exception
    {
        mCheckConfig.addAttribute("allowThrowsTagsForSubclasses", "true");
        final String[] expected = {
            "15:34: Expected @throws tag for 'RE'.",
            "31:13: Expected @param tag for '<NPE>'.",
            "38:12: Unused @throws tag for 'E'.",
            "41:38: Expected @throws tag for 'RuntimeException'.",
            "42:13: Expected @throws tag for 'java.lang.RuntimeException'.",
        };
        verify(mCheckConfig, getPath("javadoc/TestGenerics.java"), expected);
    }

    @Test
    public void test_generics_3() throws Exception
    {
        final String[] expected = {
            "6:8: Unused @throws tag for 'RE'.",
            "15:34: Expected @throws tag for 'RE'.",
            "31:13: Expected @param tag for '<NPE>'.",
            "38:12: Unused @throws tag for 'E'.",
            "41:38: Expected @throws tag for 'RuntimeException'.",
            "42:13: Expected @throws tag for 'java.lang.RuntimeException'.",
        };
        verify(mCheckConfig, getPath("javadoc/TestGenerics.java"), expected);
    }

    @Test
    public void test_1379666() throws Exception
    {
        mCheckConfig.addAttribute("allowThrowsTagsForSubclasses", "true");
        mCheckConfig.addAttribute("allowUndeclaredRTE", "true");
        final String[] expected = {};
        verify(mCheckConfig, getSrcPath("checks/javadoc/Input_1379666.java"), expected);
    }

    @Test
    public void testInheritDoc() throws Exception
    {
        final String[] expected = {
            "4:5: Invalid use of the {@inheritDoc} tag.",
            "9:5: Invalid use of the {@inheritDoc} tag.",
            "29:5: Invalid use of the {@inheritDoc} tag.",
            "34:5: Invalid use of the {@inheritDoc} tag.",
            "39:5: Invalid use of the {@inheritDoc} tag.",
            "44:5: Invalid use of the {@inheritDoc} tag.",
        };
        verify(mCheckConfig, getPath("javadoc/InputInheritDoc.java"), expected);
    }
}
