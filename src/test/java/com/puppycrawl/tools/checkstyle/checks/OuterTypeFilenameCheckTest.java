////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class OuterTypeFilenameCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator + filename);
    }

    @Override
    protected String getNonCompilablePath(String filename) throws IOException {
        return super.getNonCompilablePath("checks" + File.separator + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final OuterTypeFilenameCheck checkObj = new OuterTypeFilenameCheck();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
        };
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testGood1() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OuterTypeFilenameCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }

    @Test
    public void testGood2() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OuterTypeFilenameCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("Input15Extensions.java"), expected);
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
        };
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testNestedClass() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(OuterTypeFilenameCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputOuterTypeFilename1.java"), expected);
    }

    @Test
    public void testFinePublic() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(OuterTypeFilenameCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputOuterTypeFilename2.java"), expected);
    }

    @Test
    public void testFineDefault() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(OuterTypeFilenameCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputOuterTypeFilename3.java"), expected);
    }

    @Test
    public void testWrongDefault() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(OuterTypeFilenameCheck.class);
        final String[] expected = {
            "4: " + getCheckMessage("type.file.mismatch"),
        };
        verify(checkConfig, getPath("InputOuterTypeFilename5.java"), expected);
    }

    @Test
    public void testPackageAnnotation() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(OuterTypeFilenameCheck.class);

        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getNonCompilablePath("package-info.java"), expected);
    }
}
