////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.OverloadMethodsDeclarationOrderCheck.MSG_KEY;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class OverloadMethodsDeclarationOrderCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "coding" + File.separator + filename);
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OverloadMethodsDeclarationOrderCheck.class);

        final String[] expected = {
            "28: " + getCheckMessage(MSG_KEY, 17),
            "56: " + getCheckMessage(MSG_KEY, 45),
            "68: " + getCheckMessage(MSG_KEY, 66),
            "111: " + getCheckMessage(MSG_KEY, 100),
        };
        verify(checkConfig, getPath("InputOverloadMethodsDeclarationOrder.java"), expected);
    }

    @Test
    public void testBackwardsCompatibilityCatchAllGroup() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OverloadMethodsDeclarationOrderCheck.class);
        checkConfig.addAttribute("modifierGroups", ".*");

        final String[] expected = {
            "28: " + getCheckMessage(MSG_KEY, 17),
            "56: " + getCheckMessage(MSG_KEY, 45),
            "68: " + getCheckMessage(MSG_KEY, 66),
            "111: " + getCheckMessage(MSG_KEY, 100),
        };
        verify(checkConfig, getPath("InputOverloadMethodsDeclarationOrder.java"), expected);
    }

    @Test
    public void groupOverloadsByModifierTestBackwardsDefaultGroup() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OverloadMethodsDeclarationOrderCheck.class);
        checkConfig.addAttribute("modifierGroups", "static");
        final String[] expected = {
            "28: " + getCheckMessage(MSG_KEY, 17),
            "56: " + getCheckMessage(MSG_KEY, 45),
            "68: " + getCheckMessage(MSG_KEY, 66),
            "111: " + getCheckMessage(MSG_KEY, 100),
        };
        verify(checkConfig, getPath("InputOverloadMethodsDeclarationOrder.java"), expected);
    }

    @Test
    public void groupOverloadsByModifierTestGrouping1Static() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OverloadMethodsDeclarationOrderCheck.class);
        checkConfig.addAttribute("modifierGroups", "static");
        final String[] expected = {
            "22: " + getCheckMessage(MSG_KEY, 18),
            "29: " + getCheckMessage(MSG_KEY, 27),
            "30: " + getCheckMessage(MSG_KEY, 28),
            "38: " + getCheckMessage(MSG_KEY, 36),
            "39: " + getCheckMessage(MSG_KEY, 37),
            "47: " + getCheckMessage(MSG_KEY, 45),
            "50: " + getCheckMessage(MSG_KEY, 48),
            "57: " + getCheckMessage(MSG_KEY, 55),
            "68: " + getCheckMessage(MSG_KEY, 64),
            "81: " + getCheckMessage(MSG_KEY, 77),
        };
        verify(checkConfig, getPath("InputOverloadMethodsDeclarationOrder2.java"), expected);
    }

    @Test
    public void groupOverloadsByModifierTestGroupingPrivate() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OverloadMethodsDeclarationOrderCheck.class);
        checkConfig.addAttribute("modifierGroups", "private");
        final String[] expected = {
            "13: " + getCheckMessage(MSG_KEY, 11),
            "22: " + getCheckMessage(MSG_KEY, 20),
            "30: " + getCheckMessage(MSG_KEY, 28),
            "31: " + getCheckMessage(MSG_KEY, 27),
            "40: " + getCheckMessage(MSG_KEY, 36),
            "49: " + getCheckMessage(MSG_KEY, 45),
            "57: " + getCheckMessage(MSG_KEY, 55),
            "70: " + getCheckMessage(MSG_KEY, 64),
            "71: " + getCheckMessage(MSG_KEY, 69),
            "83: " + getCheckMessage(MSG_KEY, 77),
            "84: " + getCheckMessage(MSG_KEY, 82),
        };
        verify(checkConfig, getPath("InputOverloadMethodsDeclarationOrder2.java"), expected);
    }

    @Test
    public void groupOverloadsByModifierTestGroupingPublic() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OverloadMethodsDeclarationOrderCheck.class);
        checkConfig.addAttribute("modifierGroups", "public");
        final String[] expected = {
            "13: " + getCheckMessage(MSG_KEY, 11),
            "22: " + getCheckMessage(MSG_KEY, 20),
            "31: " + getCheckMessage(MSG_KEY, 27),
            "39: " + getCheckMessage(MSG_KEY, 37),
            "40: " + getCheckMessage(MSG_KEY, 38),
            "49: " + getCheckMessage(MSG_KEY, 45),
            "57: " + getCheckMessage(MSG_KEY, 55),
            "66: " + getCheckMessage(MSG_KEY, 64),
            "70: " + getCheckMessage(MSG_KEY, 68),
            "71: " + getCheckMessage(MSG_KEY, 69),
            "72: " + getCheckMessage(MSG_KEY, 70),
            "79: " + getCheckMessage(MSG_KEY, 77),
            "83: " + getCheckMessage(MSG_KEY, 81),
            "84: " + getCheckMessage(MSG_KEY, 82),
            "85: " + getCheckMessage(MSG_KEY, 83),
        };
        verify(checkConfig, getPath("InputOverloadMethodsDeclarationOrder2.java"), expected);
    }

    @Test
    public void groupOverloadsByModifierTestGroupingProtected() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OverloadMethodsDeclarationOrderCheck.class);
        checkConfig.addAttribute("modifierGroups", "protected");
        final String[] expected = {
            "13: " + getCheckMessage(MSG_KEY, 11),
            "22: " + getCheckMessage(MSG_KEY, 20),
            "29: " + getCheckMessage(MSG_KEY, 27),
            "30: " + getCheckMessage(MSG_KEY, 28),
            "31: " + getCheckMessage(MSG_KEY, 29),
            "38: " + getCheckMessage(MSG_KEY, 36),
            "39: " + getCheckMessage(MSG_KEY, 37),
            "47: " + getCheckMessage(MSG_KEY, 45),
            "57: " + getCheckMessage(MSG_KEY, 55),
            "66: " + getCheckMessage(MSG_KEY, 64),
            "79: " + getCheckMessage(MSG_KEY, 77),
        };
        verify(checkConfig, getPath("InputOverloadMethodsDeclarationOrder2.java"), expected);
    }

    @Test
    public void groupOverloadsByModifierTestGroupingPackage() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OverloadMethodsDeclarationOrderCheck.class);
        checkConfig.addAttribute("modifierGroups", "package");
        final String[] expected = {
            "13: " + getCheckMessage(MSG_KEY, 11),
            "22: " + getCheckMessage(MSG_KEY, 20),
            "29: " + getCheckMessage(MSG_KEY, 27),
            "31: " + getCheckMessage(MSG_KEY, 29),
            "38: " + getCheckMessage(MSG_KEY, 36),
            "40: " + getCheckMessage(MSG_KEY, 38),
            "47: " + getCheckMessage(MSG_KEY, 45),
            "57: " + getCheckMessage(MSG_KEY, 55),
            "59: " + getCheckMessage(MSG_KEY, 57),
            "69: " + getCheckMessage(MSG_KEY, 64),
            "72: " + getCheckMessage(MSG_KEY, 68),
            "82: " + getCheckMessage(MSG_KEY, 77),
            "85: " + getCheckMessage(MSG_KEY, 81),
        };
        verify(checkConfig, getPath("InputOverloadMethodsDeclarationOrder2.java"), expected);
    }

    @Test
    public void groupOverloadsByModifierTestGroupingFinal() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OverloadMethodsDeclarationOrderCheck.class);
        checkConfig.addAttribute("modifierGroups", "final");
        final String[] expected = {
            "13: " + getCheckMessage(MSG_KEY, 11),
            "22: " + getCheckMessage(MSG_KEY, 20),
            "29: " + getCheckMessage(MSG_KEY, 27),
            "30: " + getCheckMessage(MSG_KEY, 28),
            "31: " + getCheckMessage(MSG_KEY, 29),
            "38: " + getCheckMessage(MSG_KEY, 36),
            "39: " + getCheckMessage(MSG_KEY, 37),
            "47: " + getCheckMessage(MSG_KEY, 45),
            "57: " + getCheckMessage(MSG_KEY, 55),
            "66: " + getCheckMessage(MSG_KEY, 64),
            "70: " + getCheckMessage(MSG_KEY, 66),
            "71: " + getCheckMessage(MSG_KEY, 69),
            "72: " + getCheckMessage(MSG_KEY, 70),
            "79: " + getCheckMessage(MSG_KEY, 77),
            "82: " + getCheckMessage(MSG_KEY, 79),
            "84: " + getCheckMessage(MSG_KEY, 81),
            "85: " + getCheckMessage(MSG_KEY, 83),
        };
        verify(checkConfig, getPath("InputOverloadMethodsDeclarationOrder2.java"), expected);
    }

    @Test
    public void groupOverloadsByModifierTestGroupingSynchronizedWhichDoesNotExistInFile()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OverloadMethodsDeclarationOrderCheck.class);
        checkConfig.addAttribute("modifierGroups", "synchronized");
        final String[] expected = {
            "13: " + getCheckMessage(MSG_KEY, 11),
            "22: " + getCheckMessage(MSG_KEY, 20),
            "29: " + getCheckMessage(MSG_KEY, 27),
            "30: " + getCheckMessage(MSG_KEY, 28),
            "31: " + getCheckMessage(MSG_KEY, 29),
            "38: " + getCheckMessage(MSG_KEY, 36),
            "39: " + getCheckMessage(MSG_KEY, 37),
            "40: " + getCheckMessage(MSG_KEY, 38),
            "47: " + getCheckMessage(MSG_KEY, 45),
            "57: " + getCheckMessage(MSG_KEY, 55),
            "66: " + getCheckMessage(MSG_KEY, 64),
            "79: " + getCheckMessage(MSG_KEY, 77),
        };
        verify(checkConfig, getPath("InputOverloadMethodsDeclarationOrder2.java"), expected);
    }

    @Test
    public void groupOverloadsByModifierTestGroupingPublicStatic() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OverloadMethodsDeclarationOrderCheck.class);
        checkConfig.addAttribute("modifierGroups", "public static");
        final String[] expected = {
            "13: " + getCheckMessage(MSG_KEY, 11),
            "22: " + getCheckMessage(MSG_KEY, 20),
            "29: " + getCheckMessage(MSG_KEY, 27),
            "30: " + getCheckMessage(MSG_KEY, 28),
            "31: " + getCheckMessage(MSG_KEY, 29),
            "38: " + getCheckMessage(MSG_KEY, 36),
            "39: " + getCheckMessage(MSG_KEY, 37),
            "40: " + getCheckMessage(MSG_KEY, 38),
            "47: " + getCheckMessage(MSG_KEY, 45),
            "57: " + getCheckMessage(MSG_KEY, 55),
            "66: " + getCheckMessage(MSG_KEY, 64),
            "79: " + getCheckMessage(MSG_KEY, 77),
        };
        verify(checkConfig, getPath("InputOverloadMethodsDeclarationOrder2.java"), expected);
    }

    @Test
    public void groupOverloadsByModifierTestGroupingStaticPublic() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OverloadMethodsDeclarationOrderCheck.class);
        checkConfig.addAttribute("modifierGroups", "static public");
        final String[] expected = {
            "13: " + getCheckMessage(MSG_KEY, 11),
            "22: " + getCheckMessage(MSG_KEY, 20),
            "29: " + getCheckMessage(MSG_KEY, 27),
            "30: " + getCheckMessage(MSG_KEY, 28),
            "38: " + getCheckMessage(MSG_KEY, 36),
            "39: " + getCheckMessage(MSG_KEY, 37),
            "40: " + getCheckMessage(MSG_KEY, 38),
            "47: " + getCheckMessage(MSG_KEY, 45),
            "57: " + getCheckMessage(MSG_KEY, 55),
            "66: " + getCheckMessage(MSG_KEY, 64),
            "79: " + getCheckMessage(MSG_KEY, 77),
        };
        verify(checkConfig, getPath("InputOverloadMethodsDeclarationOrder2.java"), expected);
    }

    @Test
    public void groupOverloadsByModifierTestGrouping1Protected2Private3Package() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OverloadMethodsDeclarationOrderCheck.class);
        checkConfig.addAttribute("modifierGroups", "protected, private, package");
        final String[] expected = {
            "13: " + getCheckMessage(MSG_KEY, 11),
            "22: " + getCheckMessage(MSG_KEY, 20),
            "31: " + getCheckMessage(MSG_KEY, 27),
            "49: " + getCheckMessage(MSG_KEY, 45),
            "57: " + getCheckMessage(MSG_KEY, 55),
            "70: " + getCheckMessage(MSG_KEY, 64),
            "71: " + getCheckMessage(MSG_KEY, 69),
            "72: " + getCheckMessage(MSG_KEY, 68),
            "83: " + getCheckMessage(MSG_KEY, 77),
            "84: " + getCheckMessage(MSG_KEY, 82),
            "85: " + getCheckMessage(MSG_KEY, 81),
        };
        verify(checkConfig, getPath("InputOverloadMethodsDeclarationOrder2.java"), expected);
    }

    @Test
    public void groupOverloadsByModifierTestGrouping1Public2PublicFinal3Static() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OverloadMethodsDeclarationOrderCheck.class);
        checkConfig.addAttribute("modifierGroups", "public, public final, static");
        final String[] expected = {
            "22: " + getCheckMessage(MSG_KEY, 18),
            "31: " + getCheckMessage(MSG_KEY, 27),
            "39: " + getCheckMessage(MSG_KEY, 37),
            "50: " + getCheckMessage(MSG_KEY, 45),
            "57: " + getCheckMessage(MSG_KEY, 55),
            "68: " + getCheckMessage(MSG_KEY, 64),
            "70: " + getCheckMessage(MSG_KEY, 68),
            "71: " + getCheckMessage(MSG_KEY, 69),
            "72: " + getCheckMessage(MSG_KEY, 70),
            "81: " + getCheckMessage(MSG_KEY, 77),
            "83: " + getCheckMessage(MSG_KEY, 81),
            "84: " + getCheckMessage(MSG_KEY, 82),
            "85: " + getCheckMessage(MSG_KEY, 83),
        };
        verify(checkConfig, getPath("InputOverloadMethodsDeclarationOrder2.java"), expected);
    }

    @Test
    public void groupOverloadsByModifierTestGroupingComplexRegex() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OverloadMethodsDeclarationOrderCheck.class);
        //^public .*, (protected|package), private, ^static$
        checkConfig.addAttribute("modifierGroups",
            "^public .*, (protected|package), private, ^static$");
        final String[] expected = {
            "13: " + getCheckMessage(MSG_KEY, 11),
            "22: " + getCheckMessage(MSG_KEY, 20),
            "31: " + getCheckMessage(MSG_KEY, 27),
            "49: " + getCheckMessage(MSG_KEY, 45),
            "57: " + getCheckMessage(MSG_KEY, 55),
            "70: " + getCheckMessage(MSG_KEY, 64),
            "72: " + getCheckMessage(MSG_KEY, 68),
            "83: " + getCheckMessage(MSG_KEY, 77),
            "85: " + getCheckMessage(MSG_KEY, 81),
        };
        verify(checkConfig, getPath("InputOverloadMethodsDeclarationOrder2.java"), expected);
    }

    @Test
    public void groupOverloadsByModifierTestGroupingComplexRegexAsMultipleArguments()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OverloadMethodsDeclarationOrderCheck.class);
        //^public .*, (protected|package), private, ^static$
        checkConfig.addAttribute("modifierGroups", "^public .*");
        checkConfig.addAttribute("modifierGroups", "(protected|package)");
        checkConfig.addAttribute("modifierGroups", "private");
        checkConfig.addAttribute("modifierGroups", "^static$");
        final String[] expected = {
            "13: " + getCheckMessage(MSG_KEY, 11),
            "22: " + getCheckMessage(MSG_KEY, 20),
            "31: " + getCheckMessage(MSG_KEY, 27),
            "49: " + getCheckMessage(MSG_KEY, 45),
            "57: " + getCheckMessage(MSG_KEY, 55),
            "70: " + getCheckMessage(MSG_KEY, 64),
            "72: " + getCheckMessage(MSG_KEY, 68),
            "83: " + getCheckMessage(MSG_KEY, 77),
            "85: " + getCheckMessage(MSG_KEY, 81),
        };
        verify(checkConfig, getPath("InputOverloadMethodsDeclarationOrder2.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final OverloadMethodsDeclarationOrderCheck check =
            new OverloadMethodsDeclarationOrderCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
    }

}
