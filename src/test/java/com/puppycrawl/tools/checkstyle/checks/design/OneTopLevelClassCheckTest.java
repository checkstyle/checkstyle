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

package com.puppycrawl.tools.checkstyle.checks.design;

import static com.puppycrawl.tools.checkstyle.checks.design.OneTopLevelClassCheck.MSG_KEY;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class OneTopLevelClassCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "design" + File.separator + filename);
    }

    @Override
    protected String getNonCompilablePath(String filename) throws IOException {
        return super.getNonCompilablePath("checks" + File.separator
                + "design" + File.separator + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final OneTopLevelClassCheck checkObj = new OneTopLevelClassCheck();
        assertArrayEquals(ArrayUtils.EMPTY_INT_ARRAY, checkObj.getRequiredTokens());
    }

    @Test
    public void testAcceptableTokens() throws Exception {
        final OneTopLevelClassCheck check =  new OneTopLevelClassCheck();
        check.getAcceptableTokens();
        // ZERO tokens as Check do Traverse of Tree himself, he does not need to subscribed to
        // Tokens
        Assert.assertEquals(0, check.getAcceptableTokens().length);
    }

    @Test
    public void testFileWithOneTopLevelClass() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OneTopLevelClassCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputOneTopLevelClass.java"), expected);
    }

    @Test
    public void testFileWithOneTopLevelInterface() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(OneTopLevelClassCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputOneTopLevelInterface.java"), expected);
    }

    @Test
    public void testFileWithOneTopLevelEnum() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(OneTopLevelClassCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputOneTopLevelEnum.java"), expected);
    }

    @Test
    public void testFileWithNoPublicTopLevelClass() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(OneTopLevelClassCheck.class);
        final String[] expected = {
            "8: " + getCheckMessage(MSG_KEY, "InputOneTopLevelClassNoPublic2"),
        };
        verify(checkConfig, getPath("InputOneTopLevelClassNoPublic.java"), expected);
    }

    @Test
    public void testFileWithThreeTopLevelInterface() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(OneTopLevelClassCheck.class);
        final String[] expected = {
            "3: " + getCheckMessage(MSG_KEY, "InputOneTopLevelInterface2inner1"),
            "11: " + getCheckMessage(MSG_KEY, "InputOneTopLevelInterface2inner2"),
        };
        verify(checkConfig, getPath("InputOneTopLevelInterface2.java"), expected);
    }

    @Test
    public void testFileWithThreeTopLevelEnum() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(OneTopLevelClassCheck.class);
        final String[] expected = {
            "3: " + getCheckMessage(MSG_KEY, "InputOneTopLevelEnum2inner1"),
            "11: " + getCheckMessage(MSG_KEY, "InputOneTopLevelEnum2inner2"),
        };
        verify(checkConfig, getPath("InputOneTopLevelEnum2.java"), expected);
    }

    @Test
    public void testFileWithFewTopLevelClasses() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OneTopLevelClassCheck.class);
        final String[] expected = {
            "25: " + getCheckMessage(MSG_KEY, "NoSuperClone"),
            "29: " + getCheckMessage(MSG_KEY, "InnerClone"),
            "33: " + getCheckMessage(MSG_KEY, "CloneWithTypeArguments"),
            "37: " + getCheckMessage(MSG_KEY, "CloneWithTypeArgumentsAndNoSuper"),
            "41: " + getCheckMessage(MSG_KEY, "MyClassWithGenericSuperMethod"),
            "45: " + getCheckMessage(MSG_KEY, "AnotherClass"),
            "48: " + getCheckMessage(MSG_KEY, "NativeTest"),
        };
        verify(checkConfig, getPath("InputClone.java"), expected);
    }

    @Test
    public void testFileWithSecondEnumTopLevelClass() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OneTopLevelClassCheck.class);
        final String[] expected = {
            "10: " + getCheckMessage(MSG_KEY, "InputDeclarationOrderEnum"),
        };
        verify(checkConfig, getPath("InputDeclarationOrder.java"), expected);
    }

    @Test
    public void testPackageInfoWithNoTypesDeclared() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(OneTopLevelClassCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("package-info.java"), expected);
    }
}
