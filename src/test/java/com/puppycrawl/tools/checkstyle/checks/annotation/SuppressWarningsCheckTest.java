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

package com.puppycrawl.tools.checkstyle.checks.annotation;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

import static com.puppycrawl.tools.checkstyle.checks.annotation.SuppressWarningsCheck.MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED;

public class SuppressWarningsCheckTest extends BaseCheckTestSupport {
    /**
     * Tests SuppressWarnings with default regex.
     * @throws Exception
     */
    @Test
    public void testSingleDefault() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);

        final String[] expected = {
            "8:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "11:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "53:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "64:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "67:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "72:46: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "77:60: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "82:93: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "82:106: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    "),
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsSingle.java"), expected);
    }

    /**
     * Tests SuppressWarnings all warnings disabled on everything.
     * @throws Exception
     */
    @Test
    public void testSingleAll() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
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
            "82:93: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "82:98: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "82:106: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    "),
            "82:115: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsSingle.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on everything.
     * @throws Exception
     */
    @Test
    public void testSingleNoUnchecked() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
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

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsSingle.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on certain tokens.
     * @throws Exception
     */
    @Test
    public void testSingleNoUncheckedTokens() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
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

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsSingle.java"), expected);
    }

    /**
     * Tests SuppressWarnings un* warning disabled on everything.
     * @throws Exception
     */
    @Test
    public void testSingleNoUnWildcard() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
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
            "82:115: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsSingle.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked, unused warning disabled on everything.
     * @throws Exception
     */
    @Test
    public void testSingleNoUncheckedUnused() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
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
            "82:115: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsSingle.java"), expected);
    }

    /**
     * Tests SuppressWarnings *, unchecked, unused warning disabled on everything.
     * @throws Exception
     */
    @Test
    public void testSingleNoUncheckedUnusedAll() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
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
            "82:93: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "82:98: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "82:106: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    "),
            "82:115: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsSingle.java"), expected);
    }

    /**
     * Tests SuppressWarnings with default regex.
     * @throws Exception
     */
    @Test
    public void testCompactDefault() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);

        final String[] expected = {
            "8:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "11:41: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "44:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "53:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),

            "64:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "64:76: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),

            "67:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "72:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "72:98: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "77:61: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "82:94: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "82:107: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "82:181: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "82:194: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsCompact.java"), expected);
    }

    /**
     * Tests SuppressWarnings all warnings disabled on everything.
     * @throws Exception
     */
    @Test
    public void testCompactAll() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
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
            "72:98: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "72:106: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "72:117: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "77:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:61: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "77:69: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),

            "82:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:94: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "82:99: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "82:107: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "82:115: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:135: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:181: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "82:186: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "82:194: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "82:202: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsCompact.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on everything.
     * @throws Exception
     */
    @Test
    public void testCompactNoUnchecked() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*");

        final String[] expected = {
            "5:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "11:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "29:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "56:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "59:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "64:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "64:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "67:43: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "72:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "72:117: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:135: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsCompact.java"), expected);

    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on certain tokens.
     * @throws Exception
     */
    @Test
    public void testCompactNoUncheckedTokens() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*");
        checkConfig.addAttribute("tokens", "CLASS_DEF");

        final String[] expected = {
            "5:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "64:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "64:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsCompact.java"), expected);
    }

    /**
     * Tests SuppressWarnings un* warning disabled on everything.
     * @throws Exception
     */
    @Test
    public void testCompactNoUnWildcard() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
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
            "72:106: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "72:117: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "77:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:69: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),

            "82:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:115: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:135: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:202: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsCompact.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked, unused warning disabled on everything.
     * @throws Exception
     */
    @Test
    public void testCompactNoUncheckedUnused() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
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
            "72:106: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "72:117: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:69: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:115: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:135: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:202: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsCompact.java"), expected);
    }

    /**
     * Tests SuppressWarnings *, unchecked, unused warning disabled on everything.
     * @throws Exception
     */
    @Test
    public void testCompactNoUncheckedUnusedAll() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
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
            "72:98: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "72:106: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "72:117: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "77:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:61: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "77:69: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),

            "82:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:94: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "82:99: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "82:107: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "82:115: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:135: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:181: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "82:186: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "82:194: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "82:202: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsCompact.java"), expected);
    }

    /**
     * Tests SuppressWarnings with default regex.
     * @throws Exception
     */
    @Test
    public void testExpandedDefault() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);

        final String[] expected = {
            "8:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "11:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "44:29: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "53:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "64:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "64:82: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "67:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "72:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "72:104: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "77:67: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "82:100: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "82:113: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "82:187: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "82:200: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsExpanded.java"), expected);
    }

    /**
     * Tests SuppressWarnings all warnings disabled on everything.
     * @throws Exception
     */
    @Test
    public void testExpandedAll() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
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
            "72:104: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "72:112: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "72:123: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:67: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "77:75: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:100: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "82:105: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "82:113: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "82:121: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:141: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:187: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "82:192: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "82:200: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "82:208: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),

        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsExpanded.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on everything.
     * @throws Exception
     */
    @Test
    public void testExpandedNoUnchecked() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
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
            "72:123: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:141: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsExpanded.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on certain tokens.
     * @throws Exception
     */
    @Test
    public void testExpandedNoUncheckedTokens() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*");
        checkConfig.addAttribute("tokens", "CLASS_DEF");

        final String[] expected = {
            "5:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "64:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "64:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsExpanded.java"), expected);
    }

    /**
     * Tests SuppressWarnings un* warning disabled on everything.
     * @throws Exception
     */
    @Test
    public void testExpandedNoUnWildcard() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
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
            "72:112: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "72:123: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:75: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:121: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:141: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:208: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsExpanded.java"), expected);
    }


    /**
     * Tests SuppressWarnings unchecked, unused warning disabled on everything.
     * @throws Exception
     */
    @Test
    public void testExpandedNoUncheckedUnused() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
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
            "72:112: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "72:123: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:75: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:121: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:141: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:208: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsExpanded.java"), expected);
    }

    /**
     * Tests SuppressWarnings *, unchecked, unused warning disabled on everything.
     * @throws Exception
     */
    @Test
    public void testExpandedNoUncheckedUnusedAll() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
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
            "72:104: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "72:112: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "72:123: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:67: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "77:75: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:100: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "82:105: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "82:113: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "82:121: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "82:141: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:187: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "82:192: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "82:200: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "82:208: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsExpanded.java"), expected);
    }

    @Test
    public void testUncheckedInConstant() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);

        String[] expected = {

        };

        verify(checkConfig, getPath("annotation" + File.separator
                + "SuppressWarningsConstants.java"), expected);
    }

    @Test
    public void testValuePairAnnotation() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);

        String[] expected = {
        };

        verify(checkConfig, getPath("annotation" + File.separator
                + "SuppressWarningsValuePair.java"), expected);
    }

    @Test
    public void testWorkingProperlyOnComplexAnnotations() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);

        String[] expected = {
            "18:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "24:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "28:52: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "33:5: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
        };

        verify(checkConfig, getPath("InputSuppressWarningsHolder.java"), expected);
    }
}
