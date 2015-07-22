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

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class OneTopLevelClassCheckTest extends BaseCheckTestSupport {

    @Test
    public void testAcceptableTokens() throws Exception {
        final OneTopLevelClassCheck check =  new OneTopLevelClassCheck();
        check.getAcceptableTokens();
        // ZERO tokens as Check do Traverse of Tree himself, he does not need to subscribed to Tokens
        Assert.assertEquals(0, check.getAcceptableTokens().length);
    }

    @Test
    public void testFileWithOneTopLevelClass() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OneTopLevelClassCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("design" + File.separator + "InputOneTopLevelClass.java"), expected);
    }

    @Test
    public void testFileWithOneTopLevelInterface() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(OneTopLevelClassCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("design" + File.separator + "InputOneTopLevelInterface.java"), expected);
    }

    @Test
    public void testFileWithOneTopLevelEnum() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(OneTopLevelClassCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("design" + File.separator + "InputOneTopLevelEnum.java"), expected);
    }

    @Test
    public void testFileWithNoPublicTopLevelClass() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(OneTopLevelClassCheck.class);
        final String[] expected = {
            "8: " + getCheckMessage(MSG_KEY, "InputOneTopLevelClassNoPublic2"),
        };
        verify(checkConfig, getPath("design" + File.separator + "InputOneTopLevelClassNoPublic.java"), expected);
    }

    @Test
    public void testFileWithThreeTopLevelInterface() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(OneTopLevelClassCheck.class);
        final String[] expected = {
            "3: " + getCheckMessage(MSG_KEY, "InputOneTopLevelInterface2inner1"),
            "11: " + getCheckMessage(MSG_KEY, "InputOneTopLevelInterface2inner2"),
        };
        verify(checkConfig, getPath("design" + File.separator + "InputOneTopLevelInterface2.java"), expected);
    }

    @Test
    public void testFileWithThreeTopLevelEnum() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(OneTopLevelClassCheck.class);
        final String[] expected = {
            "3: " + getCheckMessage(MSG_KEY, "InputOneTopLevelEnum2inner1"),
            "11: " + getCheckMessage(MSG_KEY, "InputOneTopLevelEnum2inner2"),
        };
        verify(checkConfig, getPath("design" + File.separator + "InputOneTopLevelEnum2.java"), expected);
    }

    @Test
    public void testFileWithFewTopLevelClasses() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OneTopLevelClassCheck.class);
        final String[] expected = {
            "25: " + getCheckMessage(MSG_KEY, "NoSuperClone"),
            "33: " + getCheckMessage(MSG_KEY, "InnerClone"),
            "50: " + getCheckMessage(MSG_KEY, "CloneWithTypeArguments"),
            "58: " + getCheckMessage(MSG_KEY, "CloneWithTypeArgumentsAndNoSuper"),
            "67: " + getCheckMessage(MSG_KEY, "MyClassWithGenericSuperMethod"),
            "84: " + getCheckMessage(MSG_KEY, "AnotherClass"),
            "97: " + getCheckMessage(MSG_KEY, "NativeTest"),
        };
        verify(checkConfig, getPath("coding" + File.separator + "InputClone.java"), expected);
    }

    @Test
    public void testFileWithSecondEnumTopLevelClass() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OneTopLevelClassCheck.class);
        final String[] expected = {
            "83: " + getCheckMessage(MSG_KEY, "InputDeclarationOrderEnum"),
        };
        verify(checkConfig, getPath("coding" + File.separator + "InputDeclarationOrder.java"), expected);
    }

    @Test
    public void testPackageInfoWithNoTypesDeclared() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(OneTopLevelClassCheck.class);
        final String[] expected = {
        };
        verify(checkConfig, getPath("design" + File.separator + "package-info.java"), expected);
    }
}
