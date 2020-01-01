////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocMethodCheck.MSG_JAVADOC_MISSING;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class MissingJavadocMethodCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/missingjavadocmethod";
    }

    @Test
    public void testGetAcceptableTokens() {
        final MissingJavadocMethodCheck missingJavadocMethodCheck = new MissingJavadocMethodCheck();

        final int[] actual = missingJavadocMethodCheck.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
        };

        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

    @Test
    public void testGetRequiredTokens() {
        final MissingJavadocMethodCheck missingJavadocMethodCheck = new MissingJavadocMethodCheck();
        final int[] actual = missingJavadocMethodCheck.getRequiredTokens();
        final int[] expected = CommonUtil.EMPTY_INT_ARRAY;
        assertArrayEquals(expected, actual, "Required tokens are invalid");
    }

    @Test
    public void extendAnnotationTest() throws Exception {
        final DefaultConfiguration config = createModuleConfig(MissingJavadocMethodCheck.class);
        config.addAttribute("allowedAnnotations", "MyAnnotation, Override");
        config.addAttribute("scope", "private");
        config.addAttribute("minLineCount", "2");
        final String[] expected = {
            "44:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(config, getPath("InputMissingJavadocMethodExtendAnnotation.java"), expected);
    }

    @Test
    public void newTest() throws Exception {
        final DefaultConfiguration config = createModuleConfig(MissingJavadocMethodCheck.class);
        config.addAttribute("allowedAnnotations", "MyAnnotation, Override");
        config.addAttribute("scope", "private");
        config.addAttribute("minLineCount", "2");
        final String[] expected = {
            "57:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(config, getPath("InputMissingJavadocMethodSmallMethods.java"), expected);
    }

    @Test
    public void allowedAnnotationsTest() throws Exception {
        final DefaultConfiguration config = createModuleConfig(MissingJavadocMethodCheck.class);
        config.addAttribute("allowedAnnotations", "Override,ThisIsOk, \t\n\t ThisIsOkToo");
        config.addAttribute("scope", "private");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(config, getPath("InputMissingJavadocMethodAllowedAnnotations.java"), expected);
    }

    @Test
    public void testTags() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addAttribute("scope", "private");
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "328:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "337:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };

        verify(checkConfig, getPath("InputMissingJavadocMethodTags.java"), expected);
    }

    @Test
    public void testTagsWithResolver() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addAttribute("scope", "private");
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "328:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "337:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocMethodTags.java"), expected);
    }

    @Test
    public void testStrictJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addAttribute("scope", "private");
        final String[] expected = {
            "12:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "18:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "25:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "49:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "54:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "59:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "64:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "69:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "74:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "79:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "84:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocMethodPublicOnly.java"), expected);
    }

    @Test
    public void testNoJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.NOTHING.getName());
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMissingJavadocMethodPublicOnly.java"), expected);
    }

    // pre 1.4 relaxed mode is roughly equivalent with check=protected
    @Test
    public void testRelaxedJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "59:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "64:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "79:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "84:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocMethodPublicOnly.java"), expected);
    }

    @Test
    public void testScopeInnerInterfacesPublic() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.PUBLIC.getName());
        final String[] expected = {
            "43:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "44:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocMethodScopeInnerInterfaces.java"),
                expected);
    }

    @Test
    public void testScopeAnonInnerPrivate() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.PRIVATE.getName());
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMissingJavadocMethodScopeAnonInner.java"), expected);
    }

    @Test
    public void testScopeAnonInnerAnonInner() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.ANONINNER.getName());
        final String[] expected = {
            "26:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "39:17: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "53:17: " + getCheckMessage(MSG_JAVADOC_MISSING), };
        verify(checkConfig, getPath("InputMissingJavadocMethodScopeAnonInner.java"), expected);
    }

    @Test
    public void testScopeAnonInnerWithResolver() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addAttribute("scope", "private");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMissingJavadocMethodScopeAnonInner.java"), expected);
    }

    @Test
    public void testTagsWithSubclassesAllowed() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addAttribute("scope", "private");
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "328:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "337:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocMethodTags.java"), expected);
    }

    @Test
    public void testScopes() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addAttribute("scope", "private");
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "11:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "12:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "13:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "21:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "22:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "23:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "24:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "33:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "34:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "35:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "36:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "45:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "46:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "47:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "48:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "58:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "59:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "60:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "61:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "69:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "70:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "71:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "72:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "81:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "82:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "83:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "84:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "93:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "94:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "95:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "96:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "105:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "106:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "107:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "108:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "119:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocMethodNoJavadoc.java"), expected);
    }

    @Test
    public void testScopes2() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "11:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "21:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "22:9: " + getCheckMessage(MSG_JAVADOC_MISSING), };
        verify(checkConfig, getPath("InputMissingJavadocMethodNoJavadoc.java"), expected);
    }

    @Test
    public void testExcludeScope() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.PRIVATE.getName());
        checkConfig.addAttribute("excludeScope", Scope.PROTECTED.getName());
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "12:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "13:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "33:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "35:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "36:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "45:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "47:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "48:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "58:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "60:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "61:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "69:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "71:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "72:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "81:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "83:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "84:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "93:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "95:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "96:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "105:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "107:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "108:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "119:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocMethodNoJavadoc.java"), expected);
    }

    @Test
    public void testDoAllowMissingJavadocTagsByDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addAttribute("scope", "private");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMissingJavadocMethodMissingJavadocTags.java"), expected);
    }

    @Test
    public void testSetterGetterOff() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addAttribute("scope", "private");
        final String[] expected = {
            "7:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "12:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "17:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "22:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "28:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "32:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "37:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "43:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "48:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "53:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "55:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "59:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "63:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "67:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "69:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "74:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "76:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocMethodSetterGetter.java"), expected);
    }

    @Test
    public void testSetterGetterOn() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addAttribute("scope", "private");
        checkConfig.addAttribute("allowMissingPropertyJavadoc", "true");
        final String[] expected = {
            "17:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "22:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "28:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "32:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "37:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "43:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "53:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "55:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "59:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "63:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "67:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "69:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "74:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "76:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocMethodSetterGetter.java"), expected);
    }

    @Test
    public void test11684081() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addAttribute("scope", "private");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMissingJavadocMethod_01.java"), expected);
    }

    @Test
    public void test11684082() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addAttribute("scope", "private");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMissingJavadocMethod_02.java"), expected);
    }

    @Test
    public void testSkipCertainMethods() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addAttribute("scope", "private");
        checkConfig.addAttribute("ignoreMethodNamesRegex", "^foo.*$");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMissingJavadocMethodIgnoreNameRegex.java"), expected);
    }

    @Test
    public void testNotSkipAnythingWhenSkipRegexDoesNotMatch() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addAttribute("scope", "private");
        checkConfig.addAttribute("ignoreMethodNamesRegex", "regexThatDoesNotMatch");
        final String[] expected = {
            "5:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "9:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "13:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocMethodIgnoreNameRegex.java"), expected);
    }

    @Test
    public void testAllowToSkipOverridden() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addAttribute("scope", "private");
        checkConfig.addAttribute("allowedAnnotations", "MyAnnotation");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMissingJavadocMethodsNotSkipWritten.java"), expected);
    }

    @Test
    public void testJava8ReceiverParameter() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addAttribute("scope", "private");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMissingJavadocMethodReceiverParameter.java"), expected);
    }

    @Test
    public void testJavadocInMethod() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addAttribute("scope", "private");
        final String[] expected = {
            "4:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "6:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "9:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "13:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocMethodJavadocInMethod.java"), expected);
    }

    @Test
    public void testConstructor() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addAttribute("scope", "private");
        final String[] expected = {
            "5:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocMethodConstructor.java"), expected);
    }
}
