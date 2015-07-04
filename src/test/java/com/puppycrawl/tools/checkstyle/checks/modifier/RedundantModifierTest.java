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

import static com.puppycrawl.tools.checkstyle.checks.modifier.RedundantModifierCheck.MSG_KEY;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class RedundantModifierTest
    extends BaseCheckTestSupport {
    @Test
    public void testIt() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RedundantModifierCheck.class);
        final String[] expected = {
            "54:12: " + getCheckMessage(MSG_KEY, "static"),
            "57:9: " + getCheckMessage(MSG_KEY, "public"),
            "63:9: " + getCheckMessage(MSG_KEY, "abstract"),
            "66:9: " + getCheckMessage(MSG_KEY, "public"),
            //"69:9: Redundant 'abstract' modifier.",
            "72:9: " + getCheckMessage(MSG_KEY, "final"),
            "79:13: " + getCheckMessage(MSG_KEY, "final"),
            "88:12: " + getCheckMessage(MSG_KEY, "final"),
            "99:1: " + getCheckMessage(MSG_KEY, "abstract"),
            "116:5: " + getCheckMessage(MSG_KEY, "public"),
            "117:5: " + getCheckMessage(MSG_KEY, "final"),
            "118:5: " + getCheckMessage(MSG_KEY, "static"),
            "120:5: " + getCheckMessage(MSG_KEY, "public"),
            "121:5: " + getCheckMessage(MSG_KEY, "abstract"),
        };
        verify(checkConfig, getPath("InputModifier.java"), expected);
    }

    @Test
    public void testStaticMethodInInterface()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RedundantModifierCheck.class);
        final String[] expected = {
        };
        verify(checkConfig,
                new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                        + "checkstyle/InputStaticModifierInInterface.java").getCanonicalPath(),
                expected);
    }

    @Test
    public void testFinalInInterface()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RedundantModifierCheck.class);
        final String[] expected = {
            "3:9: " + getCheckMessage(MSG_KEY, "final"),
        };
        verify(checkConfig,
                new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                        + "checkstyle/InputFinalInDefaultMethods.java").getCanonicalPath(),
                expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        RedundantModifierCheck redundantModifierCheckObj = new RedundantModifierCheck();
        int[] actual = redundantModifierCheckObj.getAcceptableTokens();
        int[] expected = new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.INTERFACE_DEF,
        };
        Assert.assertNotNull(actual);
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testGetRequiredTokens() {
        RedundantModifierCheck redundantModifierCheckObj = new RedundantModifierCheck();
        int[] actual = redundantModifierCheckObj.getRequiredTokens();
        int[] expected = new int[] {};
        Assert.assertNotNull(actual);
        Assert.assertArrayEquals(expected, actual);
    }
}
