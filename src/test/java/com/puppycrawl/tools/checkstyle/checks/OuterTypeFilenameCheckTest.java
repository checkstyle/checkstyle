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

package com.puppycrawl.tools.checkstyle.checks;

import static com.puppycrawl.tools.checkstyle.checks.OuterTypeFilenameCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

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
        assertArrayEquals(expected, checkObj.getRequiredTokens(),
                "Required tokens array differs from expected");
    }

    @Test
    public void testGood1() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(OuterTypeFilenameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputOuterTypeFilenameIllegalTokens.java"), expected);
    }

    @Test
    public void testGood2() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(OuterTypeFilenameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputOuterTypeFilename15Extensions.java"), expected);
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
        assertArrayEquals(expected, actual, "Acceptable tokens array differs from expected");
    }

    @Test
    public void testNestedClass() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OuterTypeFilenameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputOuterTypeFilename1.java"), expected);
    }

    @Test
    public void testNestedClass2() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OuterTypeFilenameCheck.class);
        final String[] expected = {
            "3:1: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputOuterTypeFilename1a.java"), expected);
    }

    @Test
    public void testFinePublic() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OuterTypeFilenameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputOuterTypeFilename2.java"), expected);
    }

    @Test
    public void testPublicClassIsNotFirst() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OuterTypeFilenameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputOuterTypeFilenameCheckPublic.java"), expected);
    }

    @Test
    public void testNoPublicClasses() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OuterTypeFilenameCheck.class);
        final String[] expected = {
            "3:1: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputOuterTypeFilenameNoPublic.java"), expected);
    }

    @Test
    public void testFineDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OuterTypeFilenameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputOuterTypeFilename3.java"), expected);
    }

    @Test
    public void testWrongDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OuterTypeFilenameCheck.class);
        final String[] expected = {
            "4:2: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputOuterTypeFilename5.java"), expected);
    }

    @Test
    public void testPackageAnnotation() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OuterTypeFilenameCheck.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getNonCompilablePath("package-info.java"), expected);
    }

    @Test
    public void testOuterTypeFilenameRecords() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OuterTypeFilenameCheck.class);

        final String[] expected = {
            "7:1: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig,
                getNonCompilablePath("InputOuterTypeFilenameRecord.java"), expected);
    }

    @Test
    public void testOuterTypeFilenameRecordsMethodRecordDef() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OuterTypeFilenameCheck.class);

        final String[] expected = {
            "7:1: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig,
                getNonCompilablePath("InputOuterTypeFilenameRecord.java"), expected);
    }

}
