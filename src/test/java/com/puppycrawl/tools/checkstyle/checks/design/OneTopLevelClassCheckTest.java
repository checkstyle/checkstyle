///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.design;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.design.OneTopLevelClassCheck.MSG_KEY;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

class OneTopLevelClassCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/design/onetoplevelclass";
    }

    @Test
    void getRequiredTokens() {
        final OneTopLevelClassCheck checkObj = new OneTopLevelClassCheck();
        final int[] expected = {TokenTypes.COMPILATION_UNIT};
        assertWithMessage("Required tokens are invalid.")
                .that(checkObj.getRequiredTokens())
                .isEqualTo(expected);
    }

    @Test
    void clearState() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(OneTopLevelClassCheck.class);
        final String firstInputFilePath = getPath("InputOneTopLevelClassDeclarationOrder.java");
        final String secondInputFilePath = getPath("InputOneTopLevelClassInterface2.java");

        final File[] inputs = {
            new File(firstInputFilePath),
            new File(secondInputFilePath),
        };

        final List<String> expectedFirstInput = Arrays.asList(
            "16:1: " + getCheckMessage(MSG_KEY, "InputDeclarationOrderEnum"),
            "26:1: " + getCheckMessage(MSG_KEY, "InputDeclarationOrderAnnotation"));
        final List<String> expectedSecondInput = Arrays.asList(
            "9:1: " + getCheckMessage(MSG_KEY, "InputOneTopLevelClassInterface2inner1"),
            "17:1: " + getCheckMessage(MSG_KEY, "InputOneTopLevelClassInterface2inner2"));

        verify(createChecker(checkConfig), inputs,
            ImmutableMap.of(firstInputFilePath, expectedFirstInput,
                secondInputFilePath, expectedSecondInput));
    }

    @Test
    void acceptableTokens() {
        final OneTopLevelClassCheck check = new OneTopLevelClassCheck();
        final int[] expected = {TokenTypes.COMPILATION_UNIT};
        assertWithMessage("Default required tokens are invalid")
            .that(check.getAcceptableTokens())
            .isEqualTo(expected);
    }

    @Test
    void fileWithOneTopLevelClass() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputOneTopLevelClass.java"), expected);
    }

    @Test
    void fileWithOneTopLevelInterface() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputOneTopLevelClassInterface.java"), expected);
    }

    @Test
    void fileWithOneTopLevelEnum() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputOneTopLevelClassEnum.java"), expected);
    }

    @Test
    void fileWithOneTopLevelAnnotation() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputOneTopLevelClassAnnotation.java"), expected);
    }

    @Test
    void fileWithNoPublicTopLevelClass() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_KEY, "InputOneTopLevelClassNoPublic2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputOneTopLevelClassNoPublic.java"), expected);
    }

    @Test
    void fileWithThreeTopLevelInterface() throws Exception {
        final String[] expected = {
            "9:1: " + getCheckMessage(MSG_KEY, "InputOneTopLevelClassInterface3inner1"),
            "17:1: " + getCheckMessage(MSG_KEY, "InputOneTopLevelClassInterface3inner2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputOneTopLevelClassInterface3.java"), expected);
    }

    @Test
    void fileWithThreeTopLevelEnum() throws Exception {
        final String[] expected = {
            "9:1: " + getCheckMessage(MSG_KEY, "InputOneTopLevelClassEnum2inner1"),
            "17:1: " + getCheckMessage(MSG_KEY, "InputOneTopLevelClassEnum2inner2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputOneTopLevelClassEnum2.java"), expected);
    }

    @Test
    void fileWithThreeTopLevelAnnotation() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_KEY, "InputOneTopLevelClassAnnotation2A"),
            "20:1: " + getCheckMessage(MSG_KEY, "InputOneTopLevelClassAnnotation2B"),
        };
        verifyWithInlineConfigParser(
                getPath("InputOneTopLevelClassAnnotation2.java"), expected);
    }

    @Test
    void fileWithFewTopLevelClasses() throws Exception {
        final String[] expected = {
            "31:1: " + getCheckMessage(MSG_KEY, "NoSuperClone"),
            "35:1: " + getCheckMessage(MSG_KEY, "InnerClone"),
            "39:1: " + getCheckMessage(MSG_KEY, "CloneWithTypeArguments"),
            "43:1: " + getCheckMessage(MSG_KEY, "CloneWithTypeArgumentsAndNoSuper"),
            "47:1: " + getCheckMessage(MSG_KEY, "MyClassWithGenericSuperMethod"),
            "51:1: " + getCheckMessage(MSG_KEY, "AnotherClass"),
            "54:1: " + getCheckMessage(MSG_KEY, "NativeTest"),
        };
        verifyWithInlineConfigParser(
                getPath("InputOneTopLevelClassClone.java"), expected);
    }

    @Test
    void fileWithSecondEnumTopLevelClass() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(MSG_KEY, "InputDeclarationOrderEnum2"),
            "26:1: " + getCheckMessage(MSG_KEY, "InputDeclarationOrderAnnotation2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputOneTopLevelClassDeclarationOrder2.java"), expected);
    }

    @Test
    void packageInfoWithNoTypesDeclared() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("package-info.java"), expected);
    }

    @Test
    void fileWithMultipleSameLine() throws Exception {
        final String[] expected = {
            "9:47: " + getCheckMessage(MSG_KEY, "ViolatingSecondType"),
        };
        verifyWithInlineConfigParser(
                getPath("InputOneTopLevelClassSameLine.java"), expected);
    }

    @Test
    void fileWithIndentation() throws Exception {
        final String[] expected = {
            "13:2: " + getCheckMessage(MSG_KEY, "ViolatingIndentedClass1"),
            "17:5: " + getCheckMessage(MSG_KEY, "ViolatingIndentedClass2"),
            "21:1: " + getCheckMessage(MSG_KEY, "ViolatingNonIndentedInterface"),
        };
        verifyWithInlineConfigParser(
                getPath("InputOneTopLevelClassIndentation.java"), expected);
    }

    @Test
    void oneTopLevelClassRecords() throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_KEY, "TestRecord1"),
            "17:1: " + getCheckMessage(MSG_KEY, "TestRecord2"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputOneTopLevelClassRecords.java"), expected);
    }

    @Test
    void oneTopLevelClassEmpty() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputOneTopLevelClassEmpty.java"), expected);
    }
}
