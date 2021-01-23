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

package com.puppycrawl.tools.checkstyle.checks.design;

import static com.puppycrawl.tools.checkstyle.checks.design.OneTopLevelClassCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class OneTopLevelClassCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/design/onetoplevelclass";
    }

    @Test
    public void testGetRequiredTokens() {
        final OneTopLevelClassCheck checkObj = new OneTopLevelClassCheck();
        assertArrayEquals(CommonUtil.EMPTY_INT_ARRAY, checkObj.getRequiredTokens(),
                "Required tokens array is not empty");
    }

    @Test
    public void testClearState() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(OneTopLevelClassCheck.class);
        final String firstInputFilePath = getPath("InputOneTopLevelClassDeclarationOrder.java");
        final String secondInputFilePath = getPath("InputOneTopLevelClassInterface2.java");

        final File[] inputs = {
            new File(firstInputFilePath),
            new File(secondInputFilePath),
        };

        final List<String> expectedFirstInput = Arrays.asList(
            "10:1: " + getCheckMessage(MSG_KEY, "InputDeclarationOrderEnum"),
            "20:1: " + getCheckMessage(MSG_KEY, "InputDeclarationOrderAnnotation"));
        final List<String> expectedSecondInput = Arrays.asList(
            "3:1: " + getCheckMessage(MSG_KEY, "InputOneTopLevelClassInterface2inner1"),
            "11:1: " + getCheckMessage(MSG_KEY, "InputOneTopLevelClassInterface2inner2"));

        verify(createChecker(checkConfig), inputs,
            ImmutableMap.of(firstInputFilePath, expectedFirstInput,
                secondInputFilePath, expectedSecondInput));
    }

    @Test
    public void testAcceptableTokens() {
        final OneTopLevelClassCheck check = new OneTopLevelClassCheck();
        check.getAcceptableTokens();
        // ZERO tokens as Check do Traverse of Tree himself, he does not need to subscribed to
        // Tokens
        assertEquals(0, check.getAcceptableTokens().length,
                "Acceptable tokens array size larger than 0");
    }

    @Test
    public void testFileWithOneTopLevelClass() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(OneTopLevelClassCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputOneTopLevelClass.java"), expected);
    }

    @Test
    public void testFileWithOneTopLevelInterface() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(OneTopLevelClassCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputOneTopLevelClassInterface.java"), expected);
    }

    @Test
    public void testFileWithOneTopLevelEnum() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(OneTopLevelClassCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputOneTopLevelClassEnum.java"), expected);
    }

    @Test
    public void testFileWithOneTopLevelAnnotation() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(OneTopLevelClassCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputOneTopLevelClassAnnotation.java"), expected);
    }

    @Test
    public void testFileWithNoPublicTopLevelClass() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(OneTopLevelClassCheck.class);
        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY, "InputOneTopLevelClassNoPublic2"),
        };
        verify(checkConfig, getPath("InputOneTopLevelClassNoPublic.java"), expected);
    }

    @Test
    public void testFileWithThreeTopLevelInterface() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(OneTopLevelClassCheck.class);
        final String[] expected = {
            "3:1: " + getCheckMessage(MSG_KEY, "InputOneTopLevelClassInterface2inner1"),
            "11:1: " + getCheckMessage(MSG_KEY, "InputOneTopLevelClassInterface2inner2"),
        };
        verify(checkConfig, getPath("InputOneTopLevelClassInterface2.java"), expected);
    }

    @Test
    public void testFileWithThreeTopLevelEnum() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(OneTopLevelClassCheck.class);
        final String[] expected = {
            "3:1: " + getCheckMessage(MSG_KEY, "InputOneTopLevelClassEnum2inner1"),
            "11:1: " + getCheckMessage(MSG_KEY, "InputOneTopLevelClassEnum2inner2"),
        };
        verify(checkConfig, getPath("InputOneTopLevelClassEnum2.java"), expected);
    }

    @Test
    public void testFileWithThreeTopLevelAnnotation() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(OneTopLevelClassCheck.class);
        final String[] expected = {
            "9:1: " + getCheckMessage(MSG_KEY, "InputOneTopLevelClassAnnotation2A"),
            "14:1: " + getCheckMessage(MSG_KEY, "InputOneTopLevelClassAnnotation2B"),
        };
        verify(checkConfig, getPath("InputOneTopLevelClassAnnotation2.java"), expected);
    }

    @Test
    public void testFileWithFewTopLevelClasses() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(OneTopLevelClassCheck.class);
        final String[] expected = {
            "25:1: " + getCheckMessage(MSG_KEY, "NoSuperClone"),
            "29:1: " + getCheckMessage(MSG_KEY, "InnerClone"),
            "33:1: " + getCheckMessage(MSG_KEY, "CloneWithTypeArguments"),
            "37:1: " + getCheckMessage(MSG_KEY, "CloneWithTypeArgumentsAndNoSuper"),
            "41:1: " + getCheckMessage(MSG_KEY, "MyClassWithGenericSuperMethod"),
            "45:1: " + getCheckMessage(MSG_KEY, "AnotherClass"),
            "48:1: " + getCheckMessage(MSG_KEY, "NativeTest"),
        };
        verify(checkConfig, getPath("InputOneTopLevelClassClone.java"), expected);
    }

    @Test
    public void testFileWithSecondEnumTopLevelClass() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(OneTopLevelClassCheck.class);
        final String[] expected = {
            "10:1: " + getCheckMessage(MSG_KEY, "InputDeclarationOrderEnum"),
            "20:1: " + getCheckMessage(MSG_KEY, "InputDeclarationOrderAnnotation"),
        };
        verify(checkConfig, getPath("InputOneTopLevelClassDeclarationOrder.java"), expected);
    }

    @Test
    public void testPackageInfoWithNoTypesDeclared() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OneTopLevelClassCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("package-info.java"), expected);
    }

    @Test
    public void testFileWithMultipleSameLine() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(OneTopLevelClassCheck.class);
        final String[] expected = {
            "3:47: " + getCheckMessage(MSG_KEY, "ViolatingSecondType"),
        };
        verify(checkConfig, getPath("InputOneTopLevelClassSameLine.java"), expected);
    }

    @Test
    public void testFileWithIndentation() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(OneTopLevelClassCheck.class);
        final String[] expected = {
            "7:2: " + getCheckMessage(MSG_KEY, "ViolatingIndentedClass1"),
            "11:5: " + getCheckMessage(MSG_KEY, "ViolatingIndentedClass2"),
            "15:1: " + getCheckMessage(MSG_KEY, "ViolatingNonIndentedInterface"),
        };
        verify(checkConfig, getPath("InputOneTopLevelClassIndentation.java"), expected);
    }

    @Test
    public void testOneTopLevelClassRecords() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(OneTopLevelClassCheck.class);
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_KEY, "TestRecord1"),
            "15:1: " + getCheckMessage(MSG_KEY, "TestRecord2"),
            };
        verify(checkConfig, getNonCompilablePath("InputOneTopLevelClassRecords.java"), expected);
    }

}
