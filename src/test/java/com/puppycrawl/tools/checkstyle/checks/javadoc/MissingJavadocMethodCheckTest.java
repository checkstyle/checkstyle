////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
            TokenTypes.COMPACT_CTOR_DEF,
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
        config.addProperty("allowedAnnotations", "MyAnnotation, Override");
        config.addProperty("scope", "private");
        config.addProperty("minLineCount", "2");
        final String[] expected = {
            "60:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(config, getPath("InputMissingJavadocMethodExtendAnnotation.java"), expected);
    }

    @Test
    public void newTest() throws Exception {
        final DefaultConfiguration config = createModuleConfig(MissingJavadocMethodCheck.class);
        config.addProperty("allowedAnnotations", "MyAnnotation, Override");
        config.addProperty("scope", "private");
        config.addProperty("minLineCount", "2");
        final String[] expected = {
            "70:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(config, getPath("InputMissingJavadocMethodSmallMethods.java"), expected);
    }

    @Test
    public void allowedAnnotationsTest() throws Exception {
        final DefaultConfiguration config = createModuleConfig(MissingJavadocMethodCheck.class);
        config.addProperty("allowedAnnotations", "Override,ThisIsOk, \t\n\t ThisIsOkToo");
        config.addProperty("scope", "private");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(config, getPath("InputMissingJavadocMethodAllowedAnnotations.java"), expected);
    }

    @Test
    public void testTags() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addProperty("scope", "private");
        final String[] expected = {
            "23:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "337:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "346:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };

        verify(checkConfig, getPath("InputMissingJavadocMethodTags.java"), expected);
    }

    @Test
    public void testStrictJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addProperty("scope", "private");
        final String[] expected = {
            "24:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "30:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "37:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "50:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "60:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "64:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "68:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "72:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "76:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "80:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "84:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "88:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocMethodPublicOnly.java"), expected);
    }

    @Test
    public void testNoJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addProperty("scope", Scope.NOTHING.getName());
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMissingJavadocMethodPublicOnly2.java"), expected);
    }

    // pre 1.4 relaxed mode is roughly equivalent with check=protected
    @Test
    public void testRelaxedJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addProperty("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "65:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "69:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "81:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "85:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocMethodPublicOnly3.java"), expected);
    }

    @Test
    public void testScopeInnerInterfacesPublic() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addProperty("scope", Scope.PUBLIC.getName());
        final String[] expected = {
            "52:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "53:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocMethodScopeInnerInterfaces.java"),
                expected);
    }

    @Test
    public void testInterfaceMemberScopeIsPublic() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addProperty("scope", Scope.PUBLIC.getName());
        final String[] expected = {
            "20:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "28:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocMethodInterfaceMemberScopeIsPublic.java"),
                expected);
    }

    @Test
    public void testEnumCtorScopeIsPrivate() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addProperty("scope", Scope.PACKAGE.getName());
        final String[] expected = {
            "24:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocMethodEnumCtorScopeIsPrivate.java"),
                expected);
    }

    @Test
    public void testScopeAnonInnerPrivate() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addProperty("scope", Scope.PRIVATE.getName());
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMissingJavadocMethodScopeAnonInner.java"), expected);
    }

    @Test
    public void testScopeAnonInnerAnonInner() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addProperty("scope", Scope.ANONINNER.getName());
        final String[] expected = {
            "34:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "47:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "61:13: " + getCheckMessage(MSG_JAVADOC_MISSING), };
        verify(checkConfig, getPath("InputMissingJavadocMethodScopeAnonInner2.java"), expected);
    }

    @Test
    public void testScopes() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addProperty("scope", "private");
        final String[] expected = {
            "26:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "27:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "28:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "29:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "37:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "39:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "40:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "49:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "50:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "51:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "52:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "61:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "62:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "63:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "64:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "74:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "75:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "76:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "77:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "85:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "86:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "87:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "88:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "97:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "98:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "99:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "100:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "109:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "110:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "111:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "112:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "121:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "122:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "123:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "124:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "135:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocMethodNoJavadoc.java"), expected);
    }

    @Test
    public void testScopes2() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addProperty("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "26:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "27:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "37:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING), };
        verify(checkConfig, getPath("InputMissingJavadocMethodNoJavadoc2.java"), expected);
    }

    @Test
    public void testExcludeScope() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addProperty("scope", Scope.PRIVATE.getName());
        checkConfig.addProperty("excludeScope", Scope.PROTECTED.getName());
        final String[] expected = {
            "27:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "29:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "30:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "50:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "52:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "53:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "62:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "64:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "65:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "75:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "77:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "78:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "86:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "88:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "89:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "98:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "100:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "101:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "110:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "112:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "113:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "122:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "124:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "125:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "136:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocMethodNoJavadoc3.java"), expected);
    }

    @Test
    public void testDoAllowMissingJavadocTagsByDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addProperty("scope", "private");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMissingJavadocMethodMissingJavadocTags.java"), expected);
    }

    @Test
    public void testSetterGetterOff() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addProperty("scope", "private");
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "25:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "30:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "35:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "41:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "45:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "50:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "56:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "61:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "66:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "68:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "72:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "76:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "80:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "82:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "87:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "89:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocMethodSetterGetter.java"), expected);
    }

    @Test
    public void testSetterGetterOn() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addProperty("scope", "private");
        checkConfig.addProperty("allowMissingPropertyJavadoc", "true");
        final String[] expected = {
            "30:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "35:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "41:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "45:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "50:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "56:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "66:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "68:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "72:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "76:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "80:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "82:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "87:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "89:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocMethodSetterGetter2.java"), expected);
    }

    @Test
    public void test11684081() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addProperty("scope", "private");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMissingJavadocMethod_01.java"), expected);
    }

    @Test
    public void test11684082() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addProperty("scope", "private");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMissingJavadocMethod_02.java"), expected);
    }

    @Test
    public void testSkipCertainMethods() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addProperty("scope", "private");
        checkConfig.addProperty("ignoreMethodNamesRegex", "^foo.*$");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMissingJavadocMethodIgnoreNameRegex.java"), expected);
    }

    @Test
    public void testNotSkipAnythingWhenSkipRegexDoesNotMatch() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addProperty("scope", "private");
        checkConfig.addProperty("ignoreMethodNamesRegex", "regexThatDoesNotMatch");
        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "26:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "30:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocMethodIgnoreNameRegex2.java"), expected);
    }

    @Test
    public void testAllowToSkipOverridden() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addProperty("scope", "private");
        checkConfig.addProperty("allowedAnnotations", "MyAnnotation");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMissingJavadocMethodsNotSkipWritten.java"), expected);
    }

    @Test
    public void testJava8ReceiverParameter() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addProperty("scope", "private");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMissingJavadocMethodReceiverParameter.java"), expected);
    }

    @Test
    public void testJavadocInMethod() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addProperty("scope", "private");
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "22:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "25:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "29:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocMethodJavadocInMethod.java"), expected);
    }

    @Test
    public void testConstructor() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addProperty("scope", "private");
        final String[] expected = {
            "21:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocMethodConstructor.java"), expected);
    }

    @Test
    public void testNotPublicInterfaceMethods() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        checkConfig.addProperty("scope", "public");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath(
            "InputMissingJavadocMethodInterfacePrivateMethod.java"), expected);
    }

    @Test
    public void testPublicMethods() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                MissingJavadocMethodCheck.class);
        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "24:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "28:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "31:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "35:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocMethodPublicMethods.java"), expected);

    }

    @Test
    public void testMissingJavadocMethodRecordsAndCompactCtors() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
            MissingJavadocMethodCheck.class);
        final String[] expected = {
            "22:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "27:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "31:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "44:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "48:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            };
        verify(checkConfig,
            getNonCompilablePath("InputMissingJavadocMethodRecordsAndCtors.java"), expected);
    }

    @Test
    public void testMissingJavadocMethodRecordsAndCompactCtorsMinLineCount() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
            MissingJavadocMethodCheck.class);
        checkConfig.addProperty("minLineCount", "2");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig,
            getNonCompilablePath("InputMissingJavadocMethodRecordsAndCtorsMinLineCount.java"),
            expected);
    }
}
