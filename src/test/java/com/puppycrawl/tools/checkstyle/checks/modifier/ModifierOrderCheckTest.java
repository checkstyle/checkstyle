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

package com.puppycrawl.tools.checkstyle.checks.modifier;

import static com.puppycrawl.tools.checkstyle.checks.modifier.ModifierOrderCheck.MSG_ANNOTATION_ORDER;
import static com.puppycrawl.tools.checkstyle.checks.modifier.ModifierOrderCheck.MSG_MODIFIER_ORDER;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ModifierOrderCheckTest
    extends BaseCheckTestSupport {
    @Test
    public void testIt() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ModifierOrderCheck.class);
        final String[] expected = {
            "14:10: " + getCheckMessage(MSG_MODIFIER_ORDER, "final"),
            "18:12: " + getCheckMessage(MSG_MODIFIER_ORDER, "private"),
            "24:14: " + getCheckMessage(MSG_MODIFIER_ORDER, "private"),
            "34:13: " + getCheckMessage(MSG_ANNOTATION_ORDER, "@MyAnnotation2"),
            "39:13: " + getCheckMessage(MSG_ANNOTATION_ORDER, "@MyAnnotation2"),
            "49:35: " + getCheckMessage(MSG_ANNOTATION_ORDER, "@MyAnnotation4"),
        };
        verify(checkConfig, getPath("InputModifier.java"), expected);
    }

    @Test
    public void testDefaultMethods()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(ModifierOrderCheck.class);
        final String[] expected = {
        };
        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/tools"
                  + "/checkstyle/InputModifier2.java").getCanonicalPath(), expected);
    }

    @Test
    public void testGetDefaultTokens() {
        ModifierOrderCheck modifierOrderCheckObj = new ModifierOrderCheck();
        int[] actual = modifierOrderCheckObj.getDefaultTokens();
        int[] expected = new int[] {TokenTypes.MODIFIERS};
        int[] unexpectedEmptyArray = new int[] {};
        int[] unexpectedArray = new int[] {
            TokenTypes.MODIFIERS,
            TokenTypes.OBJBLOCK,
        };
        Assert.assertArrayEquals(expected, actual);
        Assert.assertNotSame(unexpectedEmptyArray, actual);
        Assert.assertNotSame(unexpectedArray, actual);
        Assert.assertNotNull(actual);
    }

    @Test
    public void testGetAcceptableTokens() {
        ModifierOrderCheck modifierOrderCheckObj = new ModifierOrderCheck();
        int[] actual = modifierOrderCheckObj.getAcceptableTokens();
        int[] expected = new int[] {TokenTypes.MODIFIERS};
        int[] unexpectedEmptyArray = new int[] {};
        int[] unexpectedArray = new int[] {
            TokenTypes.MODIFIERS,
            TokenTypes.OBJBLOCK,
        };
        Assert.assertArrayEquals(expected, actual);
        Assert.assertNotSame(unexpectedEmptyArray, actual);
        Assert.assertNotSame(unexpectedArray, actual);
        Assert.assertNotNull(actual);
    }
}
