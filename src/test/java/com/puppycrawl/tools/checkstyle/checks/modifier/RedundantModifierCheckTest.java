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

package com.puppycrawl.tools.checkstyle.checks.modifier;

import static com.puppycrawl.tools.checkstyle.checks.modifier.RedundantModifierCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class RedundantModifierCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/modifier/redundantmodifier";
    }

    @Test
    public void testClassesInsideOfInterfaces() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RedundantModifierCheck.class);
        final String[] expected = {
            "19:5: " + getCheckMessage(MSG_KEY, "static"),
            "25:5: " + getCheckMessage(MSG_KEY, "public"),
            "28:5: " + getCheckMessage(MSG_KEY, "public"),
            "34:5: " + getCheckMessage(MSG_KEY, "static"),
        };
        verify(checkConfig, getPath("InputRedundantModifierClassesInsideOfInterfaces.java"),
            expected);
    }

    @Test
    public void testIt() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RedundantModifierCheck.class);
        final String[] expected = {
            "57:12: " + getCheckMessage(MSG_KEY, "static"),
            "60:9: " + getCheckMessage(MSG_KEY, "public"),
            "66:9: " + getCheckMessage(MSG_KEY, "abstract"),
            "69:9: " + getCheckMessage(MSG_KEY, "public"),
            // "72:9: Redundant 'abstract' modifier.",
            "75:9: " + getCheckMessage(MSG_KEY, "final"),
            "82:13: " + getCheckMessage(MSG_KEY, "final"),
            "91:12: " + getCheckMessage(MSG_KEY, "final"),
            "102:1: " + getCheckMessage(MSG_KEY, "abstract"),
            "119:5: " + getCheckMessage(MSG_KEY, "public"),
            "120:5: " + getCheckMessage(MSG_KEY, "final"),
            "121:5: " + getCheckMessage(MSG_KEY, "static"),
            "123:5: " + getCheckMessage(MSG_KEY, "public"),
            "124:5: " + getCheckMessage(MSG_KEY, "abstract"),
        };
        verify(checkConfig, getPath("InputRedundantModifierIt.java"), expected);
    }

    @Test
    public void testStaticMethodInInterface()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RedundantModifierCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRedundantModifierStaticMethodInInterface.java"),
            expected);
    }

    @Test
    public void testFinalInInterface()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RedundantModifierCheck.class);
        final String[] expected = {
            "13:9: " + getCheckMessage(MSG_KEY, "final"),
        };
        verify(checkConfig, getPath("InputRedundantModifierFinalInInterface.java"), expected);
    }

    @Test
    public void testEnumConstructorIsImplicitlyPrivate() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RedundantModifierCheck.class);
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_KEY, "private"),
        };
        verify(checkConfig, getPath("InputRedundantModifierConstructorModifier.java"), expected);
    }

    @Test
    public void testInnerTypeInInterfaceIsImplicitlyStatic() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RedundantModifierCheck.class);
        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_KEY, "static"),
            "16:5: " + getCheckMessage(MSG_KEY, "static"),
        };
        verify(checkConfig, getPath("InputRedundantModifierStaticInInnerTypeOfInterface.java"),
            expected);
    }

    @Test
    public void testNotPublicClassConstructorHasNotPublicModifier() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RedundantModifierCheck.class);

        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_KEY, "public"),
        };
        verify(checkConfig, getPath("InputRedundantModifierPublicModifierInNotPublicClass.java"),
            expected);
    }

    @Test
    public void testNestedClassConsInPublicInterfaceHasValidPublicModifier() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RedundantModifierCheck.class);

        final String[] expected = {
            "22:17: " + getCheckMessage(MSG_KEY, "public"),
            "26:21: " + getCheckMessage(MSG_KEY, "public"),
            "37:12: " + getCheckMessage(MSG_KEY, "public"),
            "45:17: " + getCheckMessage(MSG_KEY, "public"),
        };

        verify(checkConfig,
            getPath("InputRedundantModifierNestedClassInPublicInterfaceRedundantModifiers.java"),
            expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final RedundantModifierCheck redundantModifierCheckObj = new RedundantModifierCheck();
        final int[] actual = redundantModifierCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.METHOD_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RESOURCE,
        };
        assertArrayEquals(expected, actual, "Invalid acceptable tokens");
    }

    @Test
    public void testGetRequiredTokens() {
        final RedundantModifierCheck redundantModifierCheckObj = new RedundantModifierCheck();
        final int[] actual = redundantModifierCheckObj.getRequiredTokens();
        final int[] expected = CommonUtil.EMPTY_INT_ARRAY;
        assertArrayEquals(expected, actual, "Invalid required tokens");
    }

    @Test
    public void testNestedStaticEnum() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RedundantModifierCheck.class);
        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_KEY, "static"),
            "16:9: " + getCheckMessage(MSG_KEY, "static"),
            "20:9: " + getCheckMessage(MSG_KEY, "static"),
        };
        verify(checkConfig, getPath("InputRedundantModifierStaticModifierInNestedEnum.java"),
            expected);
    }

    @Test
    public void testFinalInAnonymousClass()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RedundantModifierCheck.class);
        final String[] expected = {
            "22:20: " + getCheckMessage(MSG_KEY, "final"),
        };
        verify(checkConfig, getPath("InputRedundantModifierFinalInAnonymousClass.java"),
            expected);
    }

    @Test
    public void testFinalInTryWithResource() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RedundantModifierCheck.class);
        final String[] expected = {
            "30:14: " + getCheckMessage(MSG_KEY, "final"),
            "35:14: " + getCheckMessage(MSG_KEY, "final"),
            "36:17: " + getCheckMessage(MSG_KEY, "final"),
        };
        verify(checkConfig, getPath("InputRedundantModifierFinalInTryWithResource.java"),
            expected);
    }

    @Test
    public void testFinalInAbstractMethods() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RedundantModifierCheck.class);
        final String[] expected = {
            "12:33: " + getCheckMessage(MSG_KEY, "final"),
            "16:49: " + getCheckMessage(MSG_KEY, "final"),
            "19:17: " + getCheckMessage(MSG_KEY, "final"),
            "24:24: " + getCheckMessage(MSG_KEY, "final"),
            "33:33: " + getCheckMessage(MSG_KEY, "final"),
        };
        verify(checkConfig, getPath("InputRedundantModifierFinalInAbstractMethods.java"),
            expected);
    }

    @Test
    public void testEnumMethods() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RedundantModifierCheck.class);
        final String[] expected = {
            "15:16: " + getCheckMessage(MSG_KEY, "final"),
            "30:16: " + getCheckMessage(MSG_KEY, "final"),
        };
        verify(checkConfig, getPath("InputRedundantModifierFinalInEnumMethods.java"), expected);
    }

    @Test
    public void testEnumStaticMethodsInPublicClass() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RedundantModifierCheck.class);
        final String[] expected = {
            "20:23: " + getCheckMessage(MSG_KEY, "final"),
        };
        verify(checkConfig,
            getPath("InputRedundantModifierFinalInEnumStaticMethods.java"), expected);
    }

    @Test
    public void testAnnotationOnEnumConstructor() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RedundantModifierCheck.class);
        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_KEY, "private"),
        };
        verify(checkConfig, getPath("InputRedundantModifierAnnotationOnEnumConstructor.java"),
                expected);
    }

    @Test
    public void testPrivateMethodInPrivateClass() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RedundantModifierCheck.class);
        final String[] expected = {
            "13:17: " + getCheckMessage(MSG_KEY, "final"),
        };
        verify(checkConfig, getPath("InputRedundantModifierPrivateMethodInPrivateClass.java"),
                expected);
    }

    @Test
    public void testTryWithResourcesBlock() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RedundantModifierCheck.class);
        final String[] expected = {
            "18:19: " + getCheckMessage(MSG_KEY, "final"),
        };
        verify(checkConfig, getNonCompilablePath("InputRedundantModifierTryWithResources.java"),
                expected);
    }

}
