////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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

public class SuppressWarningsTest extends BaseCheckTestSupport
{
    /**
     * Tests SuppressWarnings with default regex.
     * @throws Exception
     */
    @Test
    public void testSingleDefault() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);

        final String[] expected = {
            "8:23: The warning '   ' cannot be suppressed at this location.",
            "11:27: The warning '' cannot be suppressed at this location.",
            "53:27: The warning '' cannot be suppressed at this location.",
            "64:47: The warning '' cannot be suppressed at this location.",
            "67:37: The warning '' cannot be suppressed at this location.",
            "72:46: The warning '   ' cannot be suppressed at this location.",
            "77:60: The warning '   ' cannot be suppressed at this location.",
            "82:93: The warning '' cannot be suppressed at this location.",
            "82:106: The warning '    ' cannot be suppressed at this location.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsSingle.java"), expected);
    }

    /**
     * Tests SuppressWarnings all warnings disabled on everything.
     * @throws Exception
     */
    @Test
    public void testSingleAll() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", ".*");

        final String[] expected = {
            "5:19: The warning 'unchecked' cannot be suppressed at this location.",
            "8:23: The warning '   ' cannot be suppressed at this location.",
            "11:27: The warning '' cannot be suppressed at this location.",
            "17:23: The warning 'unused' cannot be suppressed at this location.",
            "20:27: The warning 'unforgiven' cannot be suppressed at this location.",
            "25:31: The warning 'unused' cannot be suppressed at this location.",
            "29:35: The warning 'unchecked' cannot be suppressed at this location.",
            "37:23: The warning 'abcun' cannot be suppressed at this location.",
            "44:23: The warning 'abcun' cannot be suppressed at this location.",
            "47:27: The warning 'unused' cannot be suppressed at this location.",
            "53:27: The warning '' cannot be suppressed at this location.",
            "56:27: The warning 'unchecked' cannot be suppressed at this location.",
            "59:48: The warning 'unchecked' cannot be suppressed at this location.",
            "64:33: The warning 'unchecked' cannot be suppressed at this location.",
            "64:47: The warning '' cannot be suppressed at this location.",
            "67:37: The warning '' cannot be suppressed at this location.",
            "67:42: The warning 'unchecked' cannot be suppressed at this location.",
            "72:46: The warning '   ' cannot be suppressed at this location.",
            "72:54: The warning 'unused' cannot be suppressed at this location.",
            "72:65: The warning 'unchecked' cannot be suppressed at this location.",
            "77:37: The warning 'unchecked' cannot be suppressed at this location.",
            "77:60: The warning '   ' cannot be suppressed at this location.",
            "77:68: The warning 'unused' cannot be suppressed at this location.",
            "82:47: The warning 'unchecked' cannot be suppressed at this location.",
            "82:93: The warning '' cannot be suppressed at this location.",
            "82:98: The warning 'foo' cannot be suppressed at this location.",
            "82:106: The warning '    ' cannot be suppressed at this location.",
            "82:115: The warning 'unused' cannot be suppressed at this location.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsSingle.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on everything.
     * @throws Exception
     */
    @Test
    public void testSingleNoUnchecked() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*");

        final String[] expected = {
            "5:19: The warning 'unchecked' cannot be suppressed at this location.",
            "29:35: The warning 'unchecked' cannot be suppressed at this location.",
            "56:27: The warning 'unchecked' cannot be suppressed at this location.",
            "59:48: The warning 'unchecked' cannot be suppressed at this location.",

            "64:33: The warning 'unchecked' cannot be suppressed at this location.",
            "67:42: The warning 'unchecked' cannot be suppressed at this location.",
            "72:65: The warning 'unchecked' cannot be suppressed at this location.",
            "77:37: The warning 'unchecked' cannot be suppressed at this location.",
            "82:47: The warning 'unchecked' cannot be suppressed at this location.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsSingle.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on certain tokens.
     * @throws Exception
     */
    @Test
    public void testSingleNoUncheckedTokens() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*");
        checkConfig.addAttribute("tokens", "CLASS_DEF,METHOD_DEF");

        final String[] expected = {
            "5:19: The warning 'unchecked' cannot be suppressed at this location.",
            "29:35: The warning 'unchecked' cannot be suppressed at this location.",

            "64:33: The warning 'unchecked' cannot be suppressed at this location.",
            "72:65: The warning 'unchecked' cannot be suppressed at this location.",
            "77:37: The warning 'unchecked' cannot be suppressed at this location.",
            "82:47: The warning 'unchecked' cannot be suppressed at this location.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsSingle.java"), expected);
    }

    /**
     * Tests SuppressWarnings un* warning disabled on everything.
     * @throws Exception
     */
    @Test
    public void testSingleNoUnWildcard() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", ".*un.*");

        final String[] expected = {
            "5:19: The warning 'unchecked' cannot be suppressed at this location.",
            "17:23: The warning 'unused' cannot be suppressed at this location.",
            "20:27: The warning 'unforgiven' cannot be suppressed at this location.",
            "25:31: The warning 'unused' cannot be suppressed at this location.",
            "29:35: The warning 'unchecked' cannot be suppressed at this location.",
            "37:23: The warning 'abcun' cannot be suppressed at this location.",
            "44:23: The warning 'abcun' cannot be suppressed at this location.",
            "47:27: The warning 'unused' cannot be suppressed at this location.",
            "56:27: The warning 'unchecked' cannot be suppressed at this location.",
            "59:48: The warning 'unchecked' cannot be suppressed at this location.",

            "64:33: The warning 'unchecked' cannot be suppressed at this location.",
            "67:42: The warning 'unchecked' cannot be suppressed at this location.",
            "72:54: The warning 'unused' cannot be suppressed at this location.",
            "72:65: The warning 'unchecked' cannot be suppressed at this location.",
            "77:37: The warning 'unchecked' cannot be suppressed at this location.",
            "77:68: The warning 'unused' cannot be suppressed at this location.",
            "82:47: The warning 'unchecked' cannot be suppressed at this location.",
            "82:115: The warning 'unused' cannot be suppressed at this location.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsSingle.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked, unused warning disabled on everything.
     * @throws Exception
     */
    @Test
    public void testSingleNoUncheckedUnused() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$");

        final String[] expected = {
            "5:19: The warning 'unchecked' cannot be suppressed at this location.",
            "17:23: The warning 'unused' cannot be suppressed at this location.",
            "25:31: The warning 'unused' cannot be suppressed at this location.",
            "29:35: The warning 'unchecked' cannot be suppressed at this location.",
            "47:27: The warning 'unused' cannot be suppressed at this location.",
            "56:27: The warning 'unchecked' cannot be suppressed at this location.",
            "59:48: The warning 'unchecked' cannot be suppressed at this location.",

            "64:33: The warning 'unchecked' cannot be suppressed at this location.",
            "67:42: The warning 'unchecked' cannot be suppressed at this location.",
            "72:54: The warning 'unused' cannot be suppressed at this location.",
            "72:65: The warning 'unchecked' cannot be suppressed at this location.",
            "77:37: The warning 'unchecked' cannot be suppressed at this location.",
            "77:68: The warning 'unused' cannot be suppressed at this location.",
            "82:47: The warning 'unchecked' cannot be suppressed at this location.",
            "82:115: The warning 'unused' cannot be suppressed at this location.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsSingle.java"), expected);
    }

    /**
     * Tests SuppressWarnings *, unchecked, unused warning disabled on everything.
     * @throws Exception
     */
    @Test
    public void testSingleNoUncheckedUnusedAll() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$*|.*");

        final String[] expected = {
            "5:19: The warning 'unchecked' cannot be suppressed at this location.",
            "8:23: The warning '   ' cannot be suppressed at this location.",
            "11:27: The warning '' cannot be suppressed at this location.",
            "17:23: The warning 'unused' cannot be suppressed at this location.",
            "20:27: The warning 'unforgiven' cannot be suppressed at this location.",
            "25:31: The warning 'unused' cannot be suppressed at this location.",
            "29:35: The warning 'unchecked' cannot be suppressed at this location.",
            "37:23: The warning 'abcun' cannot be suppressed at this location.",
            "44:23: The warning 'abcun' cannot be suppressed at this location.",
            "47:27: The warning 'unused' cannot be suppressed at this location.",
            "53:27: The warning '' cannot be suppressed at this location.",
            "56:27: The warning 'unchecked' cannot be suppressed at this location.",
            "59:48: The warning 'unchecked' cannot be suppressed at this location.",
            "64:33: The warning 'unchecked' cannot be suppressed at this location.",
            "64:47: The warning '' cannot be suppressed at this location.",
            "67:37: The warning '' cannot be suppressed at this location.",
            "67:42: The warning 'unchecked' cannot be suppressed at this location.",
            "72:46: The warning '   ' cannot be suppressed at this location.",
            "72:54: The warning 'unused' cannot be suppressed at this location.",
            "72:65: The warning 'unchecked' cannot be suppressed at this location.",
            "77:37: The warning 'unchecked' cannot be suppressed at this location.",
            "77:60: The warning '   ' cannot be suppressed at this location.",
            "77:68: The warning 'unused' cannot be suppressed at this location.",
            "82:47: The warning 'unchecked' cannot be suppressed at this location.",
            "82:93: The warning '' cannot be suppressed at this location.",
            "82:98: The warning 'foo' cannot be suppressed at this location.",
            "82:106: The warning '    ' cannot be suppressed at this location.",
            "82:115: The warning 'unused' cannot be suppressed at this location.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsSingle.java"), expected);
    }

    /**
     * Tests SuppressWarnings with default regex.
     * @throws Exception
     */
    @Test
    public void testCompactDefault() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);

        final String[] expected = {
            "8:24: The warning '   ' cannot be suppressed at this location.",
            "11:41: The warning '' cannot be suppressed at this location.",
            "44:23: The warning '' cannot be suppressed at this location.",
            "53:27: The warning '' cannot be suppressed at this location.",

            "64:48: The warning '' cannot be suppressed at this location.",
            "64:76: The warning '' cannot be suppressed at this location.",

            "67:38: The warning '' cannot be suppressed at this location.",
            "72:47: The warning '   ' cannot be suppressed at this location.",
            "72:98: The warning '   ' cannot be suppressed at this location.",
            "77:61: The warning '   ' cannot be suppressed at this location.",
            "82:94: The warning '' cannot be suppressed at this location.",
            "82:107: The warning '   ' cannot be suppressed at this location.",
            "82:181: The warning '' cannot be suppressed at this location.",
            "82:194: The warning '   ' cannot be suppressed at this location.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsCompact.java"), expected);
    }

    /**
     * Tests SuppressWarnings all warnings disabled on everything.
     * @throws Exception
     */
    @Test
    public void testCompactAll() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", ".*");

        final String[] expected = {
            "5:20: The warning 'unchecked' cannot be suppressed at this location.",
            "5:33: The warning 'unused' cannot be suppressed at this location.",
            "8:24: The warning '   ' cannot be suppressed at this location.",
            "11:28: The warning 'unchecked' cannot be suppressed at this location.",
            "11:41: The warning '' cannot be suppressed at this location.",
            "17:24: The warning 'unused' cannot be suppressed at this location.",
            "20:28: The warning 'unforgiven' cannot be suppressed at this location.",
            "20:42: The warning '    un' cannot be suppressed at this location.",
            "25:32: The warning 'unused' cannot be suppressed at this location.",
            "29:36: The warning 'unchecked' cannot be suppressed at this location.",
            "37:24: The warning 'abcun' cannot be suppressed at this location.",
            "44:23: The warning '' cannot be suppressed at this location.",
            "47:28: The warning 'unused' cannot be suppressed at this location.",
            "47:38: The warning 'bleh' cannot be suppressed at this location.",
            "53:27: The warning '' cannot be suppressed at this location.",
            "56:28: The warning 'unchecked' cannot be suppressed at this location.",
            "59:49: The warning 'unchecked' cannot be suppressed at this location.",

            "64:34: The warning 'unchecked' cannot be suppressed at this location.",
            "64:48: The warning '' cannot be suppressed at this location.",
            "64:62: The warning 'unchecked' cannot be suppressed at this location.",
            "64:76: The warning '' cannot be suppressed at this location.",

            "67:38: The warning '' cannot be suppressed at this location.",
            "67:43: The warning 'unchecked' cannot be suppressed at this location.",

            "72:47: The warning '   ' cannot be suppressed at this location.",
            "72:55: The warning 'unused' cannot be suppressed at this location.",
            "72:66: The warning 'unchecked' cannot be suppressed at this location.",
            "72:98: The warning '   ' cannot be suppressed at this location.",
            "72:106: The warning 'unused' cannot be suppressed at this location.",
            "72:117: The warning 'unchecked' cannot be suppressed at this location.",

            "77:38: The warning 'unchecked' cannot be suppressed at this location.",
            "77:61: The warning '   ' cannot be suppressed at this location.",
            "77:69: The warning 'unused' cannot be suppressed at this location.",

            "82:48: The warning 'unchecked' cannot be suppressed at this location.",
            "82:94: The warning '' cannot be suppressed at this location.",
            "82:99: The warning 'foo' cannot be suppressed at this location.",
            "82:107: The warning '   ' cannot be suppressed at this location.",
            "82:115: The warning 'unused' cannot be suppressed at this location.",
            "82:135: The warning 'unchecked' cannot be suppressed at this location.",
            "82:181: The warning '' cannot be suppressed at this location.",
            "82:186: The warning 'foo' cannot be suppressed at this location.",
            "82:194: The warning '   ' cannot be suppressed at this location.",
            "82:202: The warning 'unused' cannot be suppressed at this location.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsCompact.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on everything.
     * @throws Exception
     */
    @Test
    public void testCompactNoUnchecked() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*");

        final String[] expected = {
            "5:20: The warning 'unchecked' cannot be suppressed at this location.",
            "11:28: The warning 'unchecked' cannot be suppressed at this location.",
            "29:36: The warning 'unchecked' cannot be suppressed at this location.",
            "56:28: The warning 'unchecked' cannot be suppressed at this location.",
            "59:49: The warning 'unchecked' cannot be suppressed at this location.",
            "64:34: The warning 'unchecked' cannot be suppressed at this location.",
            "64:62: The warning 'unchecked' cannot be suppressed at this location.",
            "67:43: The warning 'unchecked' cannot be suppressed at this location.",
            "72:66: The warning 'unchecked' cannot be suppressed at this location.",
            "72:117: The warning 'unchecked' cannot be suppressed at this location.",
            "77:38: The warning 'unchecked' cannot be suppressed at this location.",
            "82:48: The warning 'unchecked' cannot be suppressed at this location.",
            "82:135: The warning 'unchecked' cannot be suppressed at this location.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsCompact.java"), expected);

    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on certain tokens.
     * @throws Exception
     */
    @Test
    public void testCompactNoUncheckedTokens() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*");
        checkConfig.addAttribute("tokens", "CLASS_DEF");

        final String[] expected = {
            "5:20: The warning 'unchecked' cannot be suppressed at this location.",

            "64:34: The warning 'unchecked' cannot be suppressed at this location.",
            "64:62: The warning 'unchecked' cannot be suppressed at this location.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsCompact.java"), expected);
    }

    /**
     * Tests SuppressWarnings un* warning disabled on everything.
     * @throws Exception
     */
    @Test
    public void testCompactNoUnWildcard() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "un.*");

        final String[] expected = {
            "5:20: The warning 'unchecked' cannot be suppressed at this location.",
            "5:33: The warning 'unused' cannot be suppressed at this location.",
            "11:28: The warning 'unchecked' cannot be suppressed at this location.",
            "17:24: The warning 'unused' cannot be suppressed at this location.",
            "20:28: The warning 'unforgiven' cannot be suppressed at this location.",
            "25:32: The warning 'unused' cannot be suppressed at this location.",
            "29:36: The warning 'unchecked' cannot be suppressed at this location.",
            "47:28: The warning 'unused' cannot be suppressed at this location.",
            "56:28: The warning 'unchecked' cannot be suppressed at this location.",
            "59:49: The warning 'unchecked' cannot be suppressed at this location.",

            "64:34: The warning 'unchecked' cannot be suppressed at this location.",
            "64:62: The warning 'unchecked' cannot be suppressed at this location.",

            "67:43: The warning 'unchecked' cannot be suppressed at this location.",

            "72:55: The warning 'unused' cannot be suppressed at this location.",
            "72:66: The warning 'unchecked' cannot be suppressed at this location.",
            "72:106: The warning 'unused' cannot be suppressed at this location.",
            "72:117: The warning 'unchecked' cannot be suppressed at this location.",

            "77:38: The warning 'unchecked' cannot be suppressed at this location.",
            "77:69: The warning 'unused' cannot be suppressed at this location.",

            "82:48: The warning 'unchecked' cannot be suppressed at this location.",
            "82:115: The warning 'unused' cannot be suppressed at this location.",
            "82:135: The warning 'unchecked' cannot be suppressed at this location.",
            "82:202: The warning 'unused' cannot be suppressed at this location.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsCompact.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked, unused warning disabled on everything.
     * @throws Exception
     */
    @Test
    public void testCompactNoUncheckedUnused() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$");

        final String[] expected = {
            "5:20: The warning 'unchecked' cannot be suppressed at this location.",
            "5:33: The warning 'unused' cannot be suppressed at this location.",
            "11:28: The warning 'unchecked' cannot be suppressed at this location.",
            "17:24: The warning 'unused' cannot be suppressed at this location.",
            "25:32: The warning 'unused' cannot be suppressed at this location.",
            "29:36: The warning 'unchecked' cannot be suppressed at this location.",
            "47:28: The warning 'unused' cannot be suppressed at this location.",
            "56:28: The warning 'unchecked' cannot be suppressed at this location.",
            "59:49: The warning 'unchecked' cannot be suppressed at this location.",
            "64:34: The warning 'unchecked' cannot be suppressed at this location.",
            "64:62: The warning 'unchecked' cannot be suppressed at this location.",
            "67:43: The warning 'unchecked' cannot be suppressed at this location.",
            "72:55: The warning 'unused' cannot be suppressed at this location.",
            "72:66: The warning 'unchecked' cannot be suppressed at this location.",
            "72:106: The warning 'unused' cannot be suppressed at this location.",
            "72:117: The warning 'unchecked' cannot be suppressed at this location.",
            "77:38: The warning 'unchecked' cannot be suppressed at this location.",
            "77:69: The warning 'unused' cannot be suppressed at this location.",
            "82:48: The warning 'unchecked' cannot be suppressed at this location.",
            "82:115: The warning 'unused' cannot be suppressed at this location.",
            "82:135: The warning 'unchecked' cannot be suppressed at this location.",
            "82:202: The warning 'unused' cannot be suppressed at this location.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsCompact.java"), expected);
    }

    /**
     * Tests SuppressWarnings *, unchecked, unused warning disabled on everything.
     * @throws Exception
     */
    @Test
    public void testCompactNoUncheckedUnusedAll() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$*|.*");

        final String[] expected = {
            "5:20: The warning 'unchecked' cannot be suppressed at this location.",
            "5:33: The warning 'unused' cannot be suppressed at this location.",
            "8:24: The warning '   ' cannot be suppressed at this location.",
            "11:28: The warning 'unchecked' cannot be suppressed at this location.",
            "11:41: The warning '' cannot be suppressed at this location.",
            "17:24: The warning 'unused' cannot be suppressed at this location.",
            "20:28: The warning 'unforgiven' cannot be suppressed at this location.",
            "20:42: The warning '    un' cannot be suppressed at this location.",
            "25:32: The warning 'unused' cannot be suppressed at this location.",
            "29:36: The warning 'unchecked' cannot be suppressed at this location.",
            "37:24: The warning 'abcun' cannot be suppressed at this location.",
            "44:23: The warning '' cannot be suppressed at this location.",
            "47:28: The warning 'unused' cannot be suppressed at this location.",
            "47:38: The warning 'bleh' cannot be suppressed at this location.",
            "53:27: The warning '' cannot be suppressed at this location.",
            "56:28: The warning 'unchecked' cannot be suppressed at this location.",
            "59:49: The warning 'unchecked' cannot be suppressed at this location.",

            "64:34: The warning 'unchecked' cannot be suppressed at this location.",
            "64:48: The warning '' cannot be suppressed at this location.",
            "64:62: The warning 'unchecked' cannot be suppressed at this location.",
            "64:76: The warning '' cannot be suppressed at this location.",

            "67:38: The warning '' cannot be suppressed at this location.",
            "67:43: The warning 'unchecked' cannot be suppressed at this location.",

            "72:47: The warning '   ' cannot be suppressed at this location.",
            "72:55: The warning 'unused' cannot be suppressed at this location.",
            "72:66: The warning 'unchecked' cannot be suppressed at this location.",
            "72:98: The warning '   ' cannot be suppressed at this location.",
            "72:106: The warning 'unused' cannot be suppressed at this location.",
            "72:117: The warning 'unchecked' cannot be suppressed at this location.",

            "77:38: The warning 'unchecked' cannot be suppressed at this location.",
            "77:61: The warning '   ' cannot be suppressed at this location.",
            "77:69: The warning 'unused' cannot be suppressed at this location.",

            "82:48: The warning 'unchecked' cannot be suppressed at this location.",
            "82:94: The warning '' cannot be suppressed at this location.",
            "82:99: The warning 'foo' cannot be suppressed at this location.",
            "82:107: The warning '   ' cannot be suppressed at this location.",
            "82:115: The warning 'unused' cannot be suppressed at this location.",
            "82:135: The warning 'unchecked' cannot be suppressed at this location.",
            "82:181: The warning '' cannot be suppressed at this location.",
            "82:186: The warning 'foo' cannot be suppressed at this location.",
            "82:194: The warning '   ' cannot be suppressed at this location.",
            "82:202: The warning 'unused' cannot be suppressed at this location.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsCompact.java"), expected);
    }

    /**
     * Tests SuppressWarnings with default regex.
     * @throws Exception
     */
    @Test
    public void testExpandedDefault() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);

        final String[] expected = {
            "8:30: The warning '   ' cannot be suppressed at this location.",
            "11:47: The warning '' cannot be suppressed at this location.",
            "44:29: The warning '' cannot be suppressed at this location.",
            "53:33: The warning '' cannot be suppressed at this location.",
            "64:54: The warning '' cannot be suppressed at this location.",
            "64:82: The warning '' cannot be suppressed at this location.",
            "67:44: The warning '' cannot be suppressed at this location.",
            "72:53: The warning '   ' cannot be suppressed at this location.",
            "72:104: The warning '   ' cannot be suppressed at this location.",
            "77:67: The warning '   ' cannot be suppressed at this location.",
            "82:100: The warning '' cannot be suppressed at this location.",
            "82:113: The warning '   ' cannot be suppressed at this location.",
            "82:187: The warning '' cannot be suppressed at this location.",
            "82:200: The warning '   ' cannot be suppressed at this location.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsExpanded.java"), expected);
    }

    /**
     * Tests SuppressWarnings all warnings disabled on everything.
     * @throws Exception
     */
    @Test
    public void testExpandedAll() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", ".*");

        final String[] expected = {
            "5:26: The warning 'unchecked' cannot be suppressed at this location.",
            "5:39: The warning 'unused' cannot be suppressed at this location.",
            "8:30: The warning '   ' cannot be suppressed at this location.",
            "11:34: The warning 'unchecked' cannot be suppressed at this location.",
            "11:47: The warning '' cannot be suppressed at this location.",
            "17:30: The warning 'unused' cannot be suppressed at this location.",
            "20:34: The warning 'unforgiven' cannot be suppressed at this location.",
            "20:48: The warning '    un' cannot be suppressed at this location.",
            "25:38: The warning 'unused' cannot be suppressed at this location.",
            "29:42: The warning 'unchecked' cannot be suppressed at this location.",
            "37:30: The warning 'abcun' cannot be suppressed at this location.",
            "44:29: The warning '' cannot be suppressed at this location.",
            "47:34: The warning 'unused' cannot be suppressed at this location.",
            "47:44: The warning 'bleh' cannot be suppressed at this location.",
            "53:33: The warning '' cannot be suppressed at this location.",
            "56:34: The warning 'unchecked' cannot be suppressed at this location.",
            "59:55: The warning 'unchecked' cannot be suppressed at this location.",

            "64:40: The warning 'unchecked' cannot be suppressed at this location.",
            "64:54: The warning '' cannot be suppressed at this location.",
            "64:68: The warning 'unchecked' cannot be suppressed at this location.",
            "64:82: The warning '' cannot be suppressed at this location.",
            "67:44: The warning '' cannot be suppressed at this location.",
            "67:49: The warning 'unchecked' cannot be suppressed at this location.",
            "72:53: The warning '   ' cannot be suppressed at this location.",
            "72:61: The warning 'unused' cannot be suppressed at this location.",
            "72:72: The warning 'unchecked' cannot be suppressed at this location.",
            "72:104: The warning '   ' cannot be suppressed at this location.",
            "72:112: The warning 'unused' cannot be suppressed at this location.",
            "72:123: The warning 'unchecked' cannot be suppressed at this location.",
            "77:44: The warning 'unchecked' cannot be suppressed at this location.",
            "77:67: The warning '   ' cannot be suppressed at this location.",
            "77:75: The warning 'unused' cannot be suppressed at this location.",
            "82:54: The warning 'unchecked' cannot be suppressed at this location.",
            "82:100: The warning '' cannot be suppressed at this location.",
            "82:105: The warning 'foo' cannot be suppressed at this location.",
            "82:113: The warning '   ' cannot be suppressed at this location.",
            "82:121: The warning 'unused' cannot be suppressed at this location.",
            "82:141: The warning 'unchecked' cannot be suppressed at this location.",
            "82:187: The warning '' cannot be suppressed at this location.",
            "82:192: The warning 'foo' cannot be suppressed at this location.",
            "82:200: The warning '   ' cannot be suppressed at this location.",
            "82:208: The warning 'unused' cannot be suppressed at this location.",

        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsExpanded.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on everything.
     * @throws Exception
     */
    @Test
    public void testExpandedNoUnchecked() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*");

        final String[] expected = {
            "5:26: The warning 'unchecked' cannot be suppressed at this location.",
            "11:34: The warning 'unchecked' cannot be suppressed at this location.",
            "29:42: The warning 'unchecked' cannot be suppressed at this location.",
            "56:34: The warning 'unchecked' cannot be suppressed at this location.",
            "59:55: The warning 'unchecked' cannot be suppressed at this location.",

            "64:40: The warning 'unchecked' cannot be suppressed at this location.",
            "64:68: The warning 'unchecked' cannot be suppressed at this location.",
            "67:49: The warning 'unchecked' cannot be suppressed at this location.",
            "72:72: The warning 'unchecked' cannot be suppressed at this location.",
            "72:123: The warning 'unchecked' cannot be suppressed at this location.",
            "77:44: The warning 'unchecked' cannot be suppressed at this location.",
            "82:54: The warning 'unchecked' cannot be suppressed at this location.",
            "82:141: The warning 'unchecked' cannot be suppressed at this location.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsExpanded.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on certain tokens.
     * @throws Exception
     */
    @Test
    public void testExpandedNoUncheckedTokens() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*");
        checkConfig.addAttribute("tokens", "CLASS_DEF");

        final String[] expected = {
            "5:26: The warning 'unchecked' cannot be suppressed at this location.",

            "64:40: The warning 'unchecked' cannot be suppressed at this location.",
            "64:68: The warning 'unchecked' cannot be suppressed at this location.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsExpanded.java"), expected);
    }

    /**
     * Tests SuppressWarnings un* warning disabled on everything.
     * @throws Exception
     */
    @Test
    public void testExpandedNoUnWildcard() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "un.*");

        final String[] expected = {
            "5:26: The warning 'unchecked' cannot be suppressed at this location.",
            "5:39: The warning 'unused' cannot be suppressed at this location.",
            "11:34: The warning 'unchecked' cannot be suppressed at this location.",
            "17:30: The warning 'unused' cannot be suppressed at this location.",
            "20:34: The warning 'unforgiven' cannot be suppressed at this location.",
            "25:38: The warning 'unused' cannot be suppressed at this location.",
            "29:42: The warning 'unchecked' cannot be suppressed at this location.",
            "47:34: The warning 'unused' cannot be suppressed at this location.",
            "56:34: The warning 'unchecked' cannot be suppressed at this location.",
            "59:55: The warning 'unchecked' cannot be suppressed at this location.",

            "64:40: The warning 'unchecked' cannot be suppressed at this location.",
            "64:68: The warning 'unchecked' cannot be suppressed at this location.",
            "67:49: The warning 'unchecked' cannot be suppressed at this location.",
            "72:61: The warning 'unused' cannot be suppressed at this location.",
            "72:72: The warning 'unchecked' cannot be suppressed at this location.",
            "72:112: The warning 'unused' cannot be suppressed at this location.",
            "72:123: The warning 'unchecked' cannot be suppressed at this location.",
            "77:44: The warning 'unchecked' cannot be suppressed at this location.",
            "77:75: The warning 'unused' cannot be suppressed at this location.",
            "82:54: The warning 'unchecked' cannot be suppressed at this location.",
            "82:121: The warning 'unused' cannot be suppressed at this location.",
            "82:141: The warning 'unchecked' cannot be suppressed at this location.",
            "82:208: The warning 'unused' cannot be suppressed at this location.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsExpanded.java"), expected);
    }


    /**
     * Tests SuppressWarnings unchecked, unused warning disabled on everything.
     * @throws Exception
     */
    @Test
    public void testExpandedNoUncheckedUnused() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$");

        final String[] expected = {
            "5:26: The warning 'unchecked' cannot be suppressed at this location.",
            "5:39: The warning 'unused' cannot be suppressed at this location.",
            "11:34: The warning 'unchecked' cannot be suppressed at this location.",
            "17:30: The warning 'unused' cannot be suppressed at this location.",
            "25:38: The warning 'unused' cannot be suppressed at this location.",
            "29:42: The warning 'unchecked' cannot be suppressed at this location.",
            "47:34: The warning 'unused' cannot be suppressed at this location.",
            "56:34: The warning 'unchecked' cannot be suppressed at this location.",
            "59:55: The warning 'unchecked' cannot be suppressed at this location.",

            "64:40: The warning 'unchecked' cannot be suppressed at this location.",
            "64:68: The warning 'unchecked' cannot be suppressed at this location.",
            "67:49: The warning 'unchecked' cannot be suppressed at this location.",
            "72:61: The warning 'unused' cannot be suppressed at this location.",
            "72:72: The warning 'unchecked' cannot be suppressed at this location.",
            "72:112: The warning 'unused' cannot be suppressed at this location.",
            "72:123: The warning 'unchecked' cannot be suppressed at this location.",
            "77:44: The warning 'unchecked' cannot be suppressed at this location.",
            "77:75: The warning 'unused' cannot be suppressed at this location.",
            "82:54: The warning 'unchecked' cannot be suppressed at this location.",
            "82:121: The warning 'unused' cannot be suppressed at this location.",
            "82:141: The warning 'unchecked' cannot be suppressed at this location.",
            "82:208: The warning 'unused' cannot be suppressed at this location.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsExpanded.java"), expected);
    }

    /**
     * Tests SuppressWarnings *, unchecked, unused warning disabled on everything.
     * @throws Exception
     */
    @Test
    public void testExpandedNoUncheckedUnusedAll() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(SuppressWarningsCheck.class);
        checkConfig.addAttribute("format", "^unchecked$*|^unused$*|.*");

        final String[] expected = {
            "5:26: The warning 'unchecked' cannot be suppressed at this location.",
            "5:39: The warning 'unused' cannot be suppressed at this location.",
            "8:30: The warning '   ' cannot be suppressed at this location.",
            "11:34: The warning 'unchecked' cannot be suppressed at this location.",
            "11:47: The warning '' cannot be suppressed at this location.",
            "17:30: The warning 'unused' cannot be suppressed at this location.",
            "20:34: The warning 'unforgiven' cannot be suppressed at this location.",
            "20:48: The warning '    un' cannot be suppressed at this location.",
            "25:38: The warning 'unused' cannot be suppressed at this location.",
            "29:42: The warning 'unchecked' cannot be suppressed at this location.",
            "37:30: The warning 'abcun' cannot be suppressed at this location.",
            "44:29: The warning '' cannot be suppressed at this location.",
            "47:34: The warning 'unused' cannot be suppressed at this location.",
            "47:44: The warning 'bleh' cannot be suppressed at this location.",
            "53:33: The warning '' cannot be suppressed at this location.",
            "56:34: The warning 'unchecked' cannot be suppressed at this location.",
            "59:55: The warning 'unchecked' cannot be suppressed at this location.",

            "64:40: The warning 'unchecked' cannot be suppressed at this location.",
            "64:54: The warning '' cannot be suppressed at this location.",
            "64:68: The warning 'unchecked' cannot be suppressed at this location.",
            "64:82: The warning '' cannot be suppressed at this location.",
            "67:44: The warning '' cannot be suppressed at this location.",
            "67:49: The warning 'unchecked' cannot be suppressed at this location.",
            "72:53: The warning '   ' cannot be suppressed at this location.",
            "72:61: The warning 'unused' cannot be suppressed at this location.",
            "72:72: The warning 'unchecked' cannot be suppressed at this location.",
            "72:104: The warning '   ' cannot be suppressed at this location.",
            "72:112: The warning 'unused' cannot be suppressed at this location.",
            "72:123: The warning 'unchecked' cannot be suppressed at this location.",
            "77:44: The warning 'unchecked' cannot be suppressed at this location.",
            "77:67: The warning '   ' cannot be suppressed at this location.",
            "77:75: The warning 'unused' cannot be suppressed at this location.",
            "82:54: The warning 'unchecked' cannot be suppressed at this location.",
            "82:100: The warning '' cannot be suppressed at this location.",
            "82:105: The warning 'foo' cannot be suppressed at this location.",
            "82:113: The warning '   ' cannot be suppressed at this location.",
            "82:121: The warning 'unused' cannot be suppressed at this location.",
            "82:141: The warning 'unchecked' cannot be suppressed at this location.",
            "82:187: The warning '' cannot be suppressed at this location.",
            "82:192: The warning 'foo' cannot be suppressed at this location.",
            "82:200: The warning '   ' cannot be suppressed at this location.",
            "82:208: The warning 'unused' cannot be suppressed at this location.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SuppressWarningsExpanded.java"), expected);
    }
}
