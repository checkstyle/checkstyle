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

package com.puppycrawl.tools.checkstyle.checks.annotation;

import static com.puppycrawl.tools.checkstyle.checks.annotation.SuppressWarningsCheck.MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class SuppressWarningsCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/annotation/suppresswarnings";
    }

    /**
     * Tests SuppressWarnings with default regex.
     */
    @Test
    public void testSingleDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);

        final String[] expected = {
            "18:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "21:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "63:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "74:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "77:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "82:46: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "87:60: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "93:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "93:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    "),
        };

        verify(checkConfig, getPath("InputSuppressWarningsSingle1.java"), expected);
    }

    /**
     * Tests SuppressWarnings all warnings disabled on everything.
     */
    @Test
    public void testSingleAll() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", ".*");

        final String[] expected = {
            "15:19: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "18:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "21:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "27:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "30:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "35:31: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "39:35: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "47:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "54:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "57:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "63:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "66:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "74:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "74:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "77:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "77:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:46: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "82:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:65: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "87:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "87:60: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "87:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "92:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "93:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "93:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "93:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    "),
            "93:71: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsSingle2.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on everything.
     */
    @Test
    public void testSingleNoUnchecked() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*");

        final String[] expected = {
            "15:19: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "39:35: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "66:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "74:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:65: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "87:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "92:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsSingle3.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on certain tokens.
     */
    @Test
    public void testSingleNoUncheckedTokens() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*");
        checkConfig.addAttribute("tokens", "CLASS_DEF,METHOD_DEF");

        final String[] expected = {
            "13:19: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "37:35: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "72:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "80:65: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "85:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "90:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsSingle4.java"), expected);
    }

    /**
     * Tests SuppressWarnings un* warning disabled on everything.
     */
    @Test
    public void testSingleNoUnWildcard() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", ".*un.*");

        final String[] expected = {
            "15:19: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "27:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "30:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "35:31: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "39:35: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "47:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "54:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "57:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "66:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "74:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:65: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "87:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "87:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "92:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "93:71: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsSingle5.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked, unused warning disabled on everything.
     */
    @Test
    public void testSingleNoUncheckedUnused() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$");

        final String[] expected = {
            "15:19: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "27:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "35:31: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "39:35: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "57:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "66:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "74:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:65: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "87:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "87:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "92:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "93:71: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsSingle6.java"), expected);
    }

    /**
     * Tests SuppressWarnings *, unchecked, unused warning disabled on everything.
     */
    @Test
    public void testSingleNoUncheckedUnusedAll() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$*|.*");

        final String[] expected = {
            "15:19: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "18:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "21:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "27:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "30:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "35:31: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "39:35: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "47:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "54:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "57:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "63:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "66:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "74:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "74:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "77:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "77:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:46: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "82:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:65: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "87:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "87:60: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "87:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "92:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "93:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "93:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "93:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    "),
            "93:71: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsSingle7.java"), expected);
    }

    /**
     * Tests SuppressWarnings with default regex.
     */
    @Test
    public void testCompactDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);

        final String[] expected = {
            "18:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "21:41: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "54:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "63:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
        };

        verify(checkConfig, getPath("InputSuppressWarningsCompact1.java"), expected);
    }

    @Test
    public void testCompactDefaultNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);

        final String[] expected = {
            "18:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "21:41: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "54:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "63:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),

            "74:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "74:76: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),

            "77:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "82:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "83:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "88:61: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "94:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "94:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "95:69: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "96:29: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsCompactNonConstant1.java"), expected);
    }

    /**
     * Tests SuppressWarnings all warnings disabled on everything.
     */
    @Test
    public void testCompactAll() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", ".*");

        final String[] expected = {
            "15:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "15:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "18:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "21:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "21:41: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "27:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "30:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "30:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "35:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "39:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "47:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "54:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "57:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "57:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "bleh"),
            "63:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "66:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsCompact2.java"), expected);
    }

    @Test
    public void testCompactAllNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", ".*");

        final String[] expected = {
            "15:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "15:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "18:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "21:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "21:41: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "27:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "30:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "30:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "35:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "39:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "47:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "54:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "57:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "57:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "bleh"),
            "63:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "66:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "74:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "74:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "74:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "74:76: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),

            "77:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "77:43: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "82:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "82:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "83:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "83:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "83:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "88:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "88:61: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "88:69: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),

            "93:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "94:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "94:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "94:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "94:70: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "95:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "95:69: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "96:21: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "96:29: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "97:21: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsCompactNonConstant2.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on everything.
     */
    @Test
    public void testCompactNoUnchecked() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*");

        final String[] expected = {
            "15:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "21:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "39:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "66:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsCompact3.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on certain tokens.
     */
    @Test
    public void testCompactNoUncheckedTokens() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*");
        checkConfig.addAttribute("tokens", "CLASS_DEF");

        final String[] expected = {
            "13:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsCompact4.java"), expected);
    }

    @Test
    public void testCompactNoUncheckedTokensNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*");
        checkConfig.addAttribute("tokens", "CLASS_DEF");

        final String[] expected = {
            "13:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "72:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "72:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsCompactNonConstant3.java"), expected);
    }

    /**
     * Tests SuppressWarnings un* warning disabled on everything.
     */
    @Test
    public void testCompactNoUnWildcard() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "un.*");

        final String[] expected = {
            "15:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "15:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "21:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "27:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "30:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "35:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "39:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "57:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "66:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsCompact5.java"), expected);
    }

    @Test
    public void testCompactNoUnWildcardNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "un.*");

        final String[] expected = {
            "15:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "15:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "21:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "27:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "30:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "35:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "39:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "57:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "66:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "74:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "74:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "77:43: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "82:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "83:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "83:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "88:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "88:69: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),

            "93:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "94:70: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "95:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "97:21: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsCompactNonConstant4.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked, unused warning disabled on everything.
     */
    @Test
    public void testCompactNoUncheckedUnused() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$");

        final String[] expected = {
            "15:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "15:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "21:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "27:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "35:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "39:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "57:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "66:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsCompact6.java"), expected);
    }

    @Test
    public void testCompactNoUncheckedUnusedNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$");

        final String[] expected = {
            "15:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "15:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "21:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "27:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "35:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "39:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "57:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "66:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "74:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "74:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:43: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "83:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "83:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "88:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "88:69: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "93:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "94:70: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "95:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "97:21: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsCompactNonConstant5.java"), expected);
    }

    /**
     * Tests SuppressWarnings *, unchecked, unused warning disabled on everything.
     */
    @Test
    public void testCompactNoUncheckedUnusedAll() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$*|.*");

        final String[] expected = {
            "15:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "15:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "18:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "21:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "21:41: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "27:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "30:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "30:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "35:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "39:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "47:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "54:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "57:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "57:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "bleh"),
            "63:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "66:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsCompact7.java"), expected);
    }

    @Test
    public void testCompactNoUncheckedUnusedAllNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$*|.*");

        final String[] expected = {
            "15:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "15:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "18:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "21:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "21:41: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "27:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "30:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "30:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "35:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "39:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "47:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "54:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "57:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "57:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "bleh"),
            "63:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "66:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "74:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "74:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "74:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "74:76: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),

            "77:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "77:43: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "82:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "82:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "83:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "83:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "83:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "88:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "88:61: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "88:69: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),

            "93:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "94:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "94:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "94:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "94:70: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "95:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "95:69: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "96:21: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "96:29: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "97:21: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsCompactNonConstant6.java"), expected);
    }

    /**
     * Tests SuppressWarnings with default regex.
     */
    @Test
    public void testExpandedDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);

        final String[] expected = {
            "18:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "21:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "54:29: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "63:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
        };

        verify(checkConfig, getPath("InputSuppressWarningsExpanded1.java"), expected);
    }

    @Test
    public void testExpandedDefaultNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);

        final String[] expected = {
            "18:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "21:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "54:29: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "63:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "74:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "74:82: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "77:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "82:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "83:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "88:67: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "94:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "94:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "96:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "96:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsExpandedNonConstant1.java"), expected);
    }

    /**
     * Tests SuppressWarnings all warnings disabled on everything.
     */
    @Test
    public void testExpandedAll() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", ".*");

        final String[] expected = {
            "15:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "15:39: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "18:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "21:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "21:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "27:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "30:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "30:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "35:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "39:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "47:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "54:29: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "57:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "57:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "bleh"),
            "63:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "66:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsExpanded2.java"), expected);
    }

    @Test
    public void testExpandedAllNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", ".*");

        final String[] expected = {
            "15:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "15:39: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "18:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "21:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "21:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "27:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "30:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "30:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "35:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "39:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "47:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "54:29: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "57:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "57:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "bleh"),
            "63:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "66:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "74:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "74:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "74:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "74:82: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "77:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "77:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "82:61: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:72: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "83:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "83:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "83:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "88:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "88:67: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "88:75: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "93:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "94:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "94:58: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "94:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "94:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "95:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "96:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "96:58: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "96:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "96:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsExpandedNonConstant2.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on everything.
     */
    @Test
    public void testExpandedNoUnchecked() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*");

        final String[] expected = {
            "15:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "21:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "39:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "66:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsExpanded3.java"), expected);
    }

    @Test
    public void testExpandedNoUncheckedNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*");

        final String[] expected = {
            "15:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "21:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "39:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "66:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "74:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "74:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:72: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "83:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "88:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "93:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "95:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsExpandedNonConstant3.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on certain tokens.
     */
    @Test
    public void testExpandedNoUncheckedTokens() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*");
        checkConfig.addAttribute("tokens", "CLASS_DEF");

        final String[] expected = {
            "13:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsExpanded4.java"), expected);
    }

    @Test
    public void testExpandedNoUncheckedTokensNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*");
        checkConfig.addAttribute("tokens", "CLASS_DEF");

        final String[] expected = {
            "13:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "72:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "72:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsExpandedNonConstant4.java"), expected);
    }

    /**
     * Tests SuppressWarnings un* warning disabled on everything.
     */
    @Test
    public void testExpandedNoUnWildcard() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "un.*");

        final String[] expected = {
            "15:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "15:39: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "21:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "27:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "30:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "35:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "39:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "57:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "66:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsExpanded5.java"), expected);
    }

    @Test
    public void testExpandedNoUnWildcardNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "un.*");

        final String[] expected = {
            "15:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "15:39: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "21:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "27:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "30:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "35:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "39:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "57:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "66:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "74:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "74:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:61: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:72: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "83:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "83:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "88:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "88:75: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "93:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "94:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "95:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "96:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsExpandedNonConstant5.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked, unused warning disabled on everything.
     */
    @Test
    public void testExpandedNoUncheckedUnused() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$");

        final String[] expected = {
            "15:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "15:39: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "21:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "27:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "35:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "39:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "57:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "66:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsExpanded6.java"), expected);
    }

    @Test
    public void testExpandedNoUncheckedUnusedNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$");

        final String[] expected = {
            "15:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "15:39: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "21:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "27:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "35:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "39:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "57:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "66:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "74:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "74:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:61: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:72: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "83:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "83:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "88:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "88:75: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "93:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "94:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "95:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "96:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsExpandedNonConstant6.java"), expected);
    }

    /**
     * Tests SuppressWarnings *, unchecked, unused warning disabled on everything.
     */
    @Test
    public void testExpandedNoUncheckedUnusedAll() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$*|.*");

        final String[] expected = {
            "15:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "15:39: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "18:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "21:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "21:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "27:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "30:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "30:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "35:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "39:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "47:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "54:29: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "57:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "57:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "bleh"),
            "63:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "66:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsExpanded7.java"), expected);
    }

    @Test
    public void testExpandedNoUncheckedUnusedAllNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$*|.*");

        final String[] expected = {
            "15:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "15:39: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "18:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "21:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "21:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "27:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "30:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "30:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "35:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "39:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "47:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "54:29: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "57:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "57:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "bleh"),
            "63:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "66:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "74:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "74:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "74:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "74:82: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "77:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "77:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "82:61: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:72: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "83:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "83:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "83:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "88:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "88:67: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "88:75: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "93:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "94:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "94:58: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "94:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "94:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "95:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "96:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "96:58: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "96:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "96:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsExpandedNonConstant7.java"), expected);
    }

    @Test
    public void testUncheckedInConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputSuppressWarningsConstants.java"), expected);
    }

    @Test
    public void testValuePairAnnotation() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputSuppressWarningsValuePair.java"), expected);
    }

    @Test
    public void testWorkingProperlyOnComplexAnnotations() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);

        final String[] expected = {
            "20:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "26:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "33:5: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
        };

        verify(checkConfig, getPath("InputSuppressWarningsHolder.java"), expected);
    }

    @Test
    public void testWorkingProperlyOnComplexAnnotationsNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);

        final String[] expected = {
            "20:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "26:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "30:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "36:5: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsHolderNonConstant.java"), expected);
    }

    @Test
    public void testSuppressWarningsRecords() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$*|.*");

        final String[] expected = {
            "24:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "24:41: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "28:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "28:45: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "30:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "33:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "33:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "39:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "42:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "42:50: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "48:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "53:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "60:31: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "63:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "63:46: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "something else"),
            "69:35: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "72:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "76:57: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig,
            getNonCompilablePath("InputSuppressWarningsRecords.java"), expected);
    }

}
