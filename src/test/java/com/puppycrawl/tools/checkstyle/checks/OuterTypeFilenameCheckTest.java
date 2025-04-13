///
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
///

package com.puppycrawl.tools.checkstyle.checks;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.OuterTypeFilenameCheck.MSG_KEY;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class OuterTypeFilenameCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/outertypefilename";
    }

    @Test
    public void testGetRequiredTokens() {
        final OuterTypeFilenameCheck checkObj = new OuterTypeFilenameCheck();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.RECORD_DEF,
        };
        assertWithMessage("Required tokens array differs from expected")
                .that(checkObj.getRequiredTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testGood1() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputOuterTypeFilenameIllegalTokens.java"), expected);
    }

    @Test
    public void testGood2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputOuterTypeFilename15Extensions.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final OuterTypeFilenameCheck check = new OuterTypeFilenameCheck();
        final int[] actual = check.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.RECORD_DEF,
        };
        assertWithMessage("Acceptable tokens array differs from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testNestedClass() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputOuterTypeFilename1.java"), expected);
    }

    @Test
    public void testNestedClass2() throws Exception {
        final String[] expected = {
            "9:1: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputOuterTypeFilename1a.java"), expected);
    }

    @Test
    public void testFinePublic() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputOuterTypeFilename2.java"), expected);
    }

    @Test
    public void testPublicClassIsNotFirst() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputOuterTypeFilenameCheckPublic.java"), expected);
    }

    @Test
    public void testNoPublicClasses() throws Exception {
        final String[] expected = {
            "9:1: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputOuterTypeFilenameNoPublic.java"), expected);
    }

    @Test
    public void testFineDefault() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputOuterTypeFilename3.java"), expected);
    }

    @Test
    public void testWrongDefault() throws Exception {
        final String[] expected = {
            "10:2: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputOuterTypeFilename5.java"), expected);
    }

    @Test
    public void testPackageAnnotation() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getNonCompilablePath("package-info.java"), expected);
    }

    @Test
    public void testOuterTypeFilenameRecords() throws Exception {

        final String[] expected = {
            "10:1: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputOuterTypeFilenameRecordMethodRecordDef.java"),
                expected);
    }

    @Test
    public void testOuterTypeFilenameRecordsMethodRecordDef() throws Exception {

        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputOuterTypeFilenameRecord.java"), expected);
    }

    @Test
    public void testStateIsClearedOnBeginTree2() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OuterTypeFilenameCheck.class);
        final String file1 = getPath(
                "InputOuterTypeFilenameBeginTree1.java");
        final String file2 = getPath(
                "InputOuterTypeFilenameBeginTree2.java");
        final List<String> expectedFirstInput = List.of(CommonUtil.EMPTY_STRING_ARRAY);
        final List<String> expectedSecondInput = List.of(CommonUtil.EMPTY_STRING_ARRAY);
        final File[] inputs = {new File(file1), new File(file2)};

        verify(createChecker(checkConfig), inputs, ImmutableMap.of(
            file1, expectedFirstInput,
            file2, expectedSecondInput));
    }

    @Test
    public void testStateIsClearedOnBeginTree3() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OuterTypeFilenameCheck.class);
        final String file1 = getPath(
                "InputOuterTypeFilenameBeginTree1.java");
        final String file2 = getPath(
                "InputOuterTypeFilename1a.java");
        final List<String> expectedFirstInput = List.of(CommonUtil.EMPTY_STRING_ARRAY);
        final List<String> expectedSecondInput = List.of(
                "9:1: " + getCheckMessage(MSG_KEY)
        );
        final File[] inputs = {new File(file1), new File(file2)};

        verify(createChecker(checkConfig), inputs, ImmutableMap.of(
            file1, expectedFirstInput,
            file2, expectedSecondInput));
    }
}
