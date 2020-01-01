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
            "8:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "11:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "53:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "64:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "67:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "72:46: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "77:60: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "83:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "83:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    "),
        };

        verify(checkConfig, getPath("InputSuppressWarningsSingle.java"), expected);
    }

    /**
     * Tests SuppressWarnings all warnings disabled on everything.
     */
    @Test
    public void testSingleAll() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", ".*");

        final String[] expected = {
            "5:19: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "8:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "11:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "17:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "20:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "25:31: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "29:35: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "37:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "44:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "47:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "53:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "56:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "59:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "64:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "64:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "67:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "67:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "72:46: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "72:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "72:65: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:60: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "77:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "83:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "83:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "83:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    "),
            "83:71: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsSingle.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on everything.
     */
    @Test
    public void testSingleNoUnchecked() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*");

        final String[] expected = {
            "5:19: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "29:35: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "56:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "59:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "64:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "67:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "72:65: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsSingle.java"), expected);
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
            "5:19: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "29:35: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "64:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "72:65: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsSingle.java"), expected);
    }

    /**
     * Tests SuppressWarnings un* warning disabled on everything.
     */
    @Test
    public void testSingleNoUnWildcard() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", ".*un.*");

        final String[] expected = {
            "5:19: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "17:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "20:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "25:31: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "29:35: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "37:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "44:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "47:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "56:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "59:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "64:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "67:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "72:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "72:65: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "83:71: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsSingle.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked, unused warning disabled on everything.
     */
    @Test
    public void testSingleNoUncheckedUnused() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$");

        final String[] expected = {
            "5:19: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "17:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "25:31: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "29:35: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "47:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "56:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "59:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "64:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "67:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "72:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "72:65: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "83:71: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsSingle.java"), expected);
    }

    /**
     * Tests SuppressWarnings *, unchecked, unused warning disabled on everything.
     */
    @Test
    public void testSingleNoUncheckedUnusedAll() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$*|.*");

        final String[] expected = {
            "5:19: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "8:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "11:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "17:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "20:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "25:31: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "29:35: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "37:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "44:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "47:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "53:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "56:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "59:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "64:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "64:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "67:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "67:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "72:46: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "72:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "72:65: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:60: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "77:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "83:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "83:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "83:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    "),
            "83:71: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsSingle.java"), expected);
    }

    /**
     * Tests SuppressWarnings with default regex.
     */
    @Test
    public void testCompactDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);

        final String[] expected = {
            "8:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "11:41: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "44:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "53:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
        };

        verify(checkConfig, getPath("InputSuppressWarningsCompact.java"), expected);
    }

    @Test
    public void testCompactDefaultNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);

        final String[] expected = {
            "8:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "11:41: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "44:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "53:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),

            "64:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "64:76: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),

            "67:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "72:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "73:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "78:61: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "84:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "84:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "85:69: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "85:82: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsCompactNonConstant.java"), expected);
    }

    /**
     * Tests SuppressWarnings all warnings disabled on everything.
     */
    @Test
    public void testCompactAll() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", ".*");

        final String[] expected = {
            "5:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "5:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "8:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "11:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "11:41: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "17:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "20:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "20:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "25:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "29:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "37:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "44:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "47:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "47:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "bleh"),
            "53:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "56:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "59:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsCompact.java"), expected);
    }

    @Test
    public void testCompactAllNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", ".*");

        final String[] expected = {
            "5:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "5:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "8:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "11:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "11:41: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "17:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "20:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "20:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "25:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "29:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "37:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "44:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "47:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "47:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "bleh"),
            "53:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "56:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "59:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "64:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "64:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "64:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "64:76: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),

            "67:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "67:43: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "72:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "72:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "72:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "73:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "73:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "73:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "78:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "78:61: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "78:69: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),

            "83:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "84:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "84:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "84:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "84:70: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "85:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "85:69: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "85:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "85:82: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "85:90: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsCompactNonConstant.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on everything.
     */
    @Test
    public void testCompactNoUnchecked() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*");

        final String[] expected = {
            "5:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "11:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "29:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "56:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "59:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsCompact.java"), expected);
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
            "5:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsCompact.java"), expected);
    }

    @Test
    public void testCompactNoUncheckedTokensNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*");
        checkConfig.addAttribute("tokens", "CLASS_DEF");

        final String[] expected = {
            "5:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "64:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "64:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsCompactNonConstant.java"), expected);
    }

    /**
     * Tests SuppressWarnings un* warning disabled on everything.
     */
    @Test
    public void testCompactNoUnWildcard() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "un.*");

        final String[] expected = {
            "5:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "5:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "11:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "17:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "20:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "25:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "29:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "47:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "56:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "59:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsCompact.java"), expected);
    }

    @Test
    public void testCompactNoUnWildcardNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "un.*");

        final String[] expected = {
            "5:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "5:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "11:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "17:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "20:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "25:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "29:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "47:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "56:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "59:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "64:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "64:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "67:43: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "72:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "72:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "73:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "73:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "78:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "78:69: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),

            "83:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "84:70: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "85:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "85:90: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsCompactNonConstant.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked, unused warning disabled on everything.
     */
    @Test
    public void testCompactNoUncheckedUnused() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$");

        final String[] expected = {
            "5:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "5:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "11:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "17:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "25:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "29:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "47:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "56:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "59:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsCompact.java"), expected);
    }

    @Test
    public void testCompactNoUncheckedUnusedNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$");

        final String[] expected = {
            "5:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "5:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "11:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "17:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "25:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "29:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "47:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "56:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "59:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "64:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "64:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "67:43: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "72:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "72:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "73:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "73:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "78:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "78:69: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "83:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "84:70: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "85:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "85:90: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsCompactNonConstant.java"), expected);
    }

    /**
     * Tests SuppressWarnings *, unchecked, unused warning disabled on everything.
     */
    @Test
    public void testCompactNoUncheckedUnusedAll() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$*|.*");

        final String[] expected = {
            "5:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "5:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "8:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "11:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "11:41: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "17:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "20:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "20:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "25:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "29:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "37:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "44:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "47:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "47:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "bleh"),
            "53:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "56:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "59:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsCompact.java"), expected);
    }

    @Test
    public void testCompactNoUncheckedUnusedAllNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$*|.*");

        final String[] expected = {
            "5:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "5:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "8:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "11:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "11:41: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "17:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "20:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "20:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "25:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "29:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "37:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "44:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "47:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "47:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "bleh"),
            "53:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "56:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "59:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "64:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "64:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "64:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "64:76: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),

            "67:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "67:43: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "72:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "72:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "72:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "73:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "73:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "73:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "78:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "78:61: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "78:69: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),

            "83:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "84:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "84:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "84:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "84:70: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "85:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "85:69: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "85:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "85:82: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "85:90: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsCompactNonConstant.java"), expected);
    }

    /**
     * Tests SuppressWarnings with default regex.
     */
    @Test
    public void testExpandedDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);

        final String[] expected = {
            "8:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "11:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "44:29: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "53:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
        };

        verify(checkConfig, getPath("InputSuppressWarningsExpanded.java"), expected);
    }

    @Test
    public void testExpandedDefaultNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);

        final String[] expected = {
            "8:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "11:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "44:29: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "53:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "64:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "64:82: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "67:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "72:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "73:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "78:67: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "84:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "84:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "86:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "86:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsExpandedNonConstant.java"), expected);
    }

    /**
     * Tests SuppressWarnings all warnings disabled on everything.
     */
    @Test
    public void testExpandedAll() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", ".*");

        final String[] expected = {
            "5:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "5:39: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "8:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "11:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "11:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "17:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "20:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "20:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "25:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "29:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "37:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "44:29: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "47:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "47:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "bleh"),
            "53:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "56:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "59:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsExpanded.java"), expected);
    }

    @Test
    public void testExpandedAllNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", ".*");

        final String[] expected = {
            "5:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "5:39: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "8:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "11:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "11:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "17:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "20:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "20:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "25:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "29:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "37:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "44:29: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "47:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "47:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "bleh"),
            "53:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "56:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "59:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "64:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "64:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "64:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "64:82: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "67:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "67:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "72:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "72:61: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "72:72: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "73:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "73:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "73:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "78:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "78:67: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "78:75: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "83:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "84:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "84:58: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "84:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "84:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "85:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "86:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "86:58: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "86:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "86:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsExpandedNonConstant.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on everything.
     */
    @Test
    public void testExpandedNoUnchecked() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*");

        final String[] expected = {
            "5:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "11:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "29:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "56:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "59:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsExpanded.java"), expected);
    }

    @Test
    public void testExpandedNoUncheckedNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*");

        final String[] expected = {
            "5:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "11:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "29:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "56:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "59:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "64:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "64:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "67:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "72:72: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "73:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "78:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "83:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "85:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsExpandedNonConstant.java"), expected);
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
            "5:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsExpanded.java"), expected);
    }

    @Test
    public void testExpandedNoUncheckedTokensNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*");
        checkConfig.addAttribute("tokens", "CLASS_DEF");

        final String[] expected = {
            "5:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "64:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "64:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsExpandedNonConstant.java"), expected);
    }

    /**
     * Tests SuppressWarnings un* warning disabled on everything.
     */
    @Test
    public void testExpandedNoUnWildcard() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "un.*");

        final String[] expected = {
            "5:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "5:39: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "11:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "17:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "20:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "25:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "29:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "47:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "56:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "59:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsExpanded.java"), expected);
    }

    @Test
    public void testExpandedNoUnWildcardNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "un.*");

        final String[] expected = {
            "5:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "5:39: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "11:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "17:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "20:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "25:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "29:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "47:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "56:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "59:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "64:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "64:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "67:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "72:61: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "72:72: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "73:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "73:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "78:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "78:75: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "83:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "84:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "85:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "86:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsExpandedNonConstant.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked, unused warning disabled on everything.
     */
    @Test
    public void testExpandedNoUncheckedUnused() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$");

        final String[] expected = {
            "5:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "5:39: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "11:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "17:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "25:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "29:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "47:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "56:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "59:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsExpanded.java"), expected);
    }

    @Test
    public void testExpandedNoUncheckedUnusedNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$");

        final String[] expected = {
            "5:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "5:39: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "11:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "17:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "25:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "29:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "47:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "56:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "59:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "64:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "64:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "67:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "72:61: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "72:72: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "73:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "73:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "78:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "78:75: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "83:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "84:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "85:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "86:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsExpandedNonConstant.java"), expected);
    }

    /**
     * Tests SuppressWarnings *, unchecked, unused warning disabled on everything.
     */
    @Test
    public void testExpandedNoUncheckedUnusedAll() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$*|.*");

        final String[] expected = {
            "5:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "5:39: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "8:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "11:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "11:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "17:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "20:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "20:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "25:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "29:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "37:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "44:29: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "47:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "47:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "bleh"),
            "53:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "56:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "59:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("InputSuppressWarningsExpanded.java"), expected);
    }

    @Test
    public void testExpandedNoUncheckedUnusedAllNonConstant() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$*|.*");

        final String[] expected = {
            "5:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "5:39: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "8:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "11:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "11:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "17:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "20:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "20:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "25:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "29:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "37:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "abcun"),
            "44:29: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "47:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "47:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "bleh"),
            "53:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "56:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "59:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "64:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "64:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "64:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "64:82: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "67:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "67:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "72:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "72:61: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "72:72: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "73:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "73:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "73:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "78:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "78:67: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "78:75: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "83:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "84:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "84:58: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "84:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "84:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "85:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "86:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "86:58: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "86:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "86:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig,
                getNonCompilablePath("InputSuppressWarningsExpandedNonConstant.java"), expected);
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

}
