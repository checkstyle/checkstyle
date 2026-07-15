///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.annotation;

import static com.puppycrawl.tools.checkstyle.checks.annotation.SuppressWarningsCheck.MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class SuppressWarningsCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/annotation/suppresswarnings";
    }

    /**
     * Tests SuppressWarnings with default regex.
     */
    @Test
    public void testSingleDefault() throws Exception {

        final String[] expected = {
            "19:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "23:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "66:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "78:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "82:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "88:46: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "94:60: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "100:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "100:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    "),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsSingle1.java"), expected);
    }

    /**
     * Tests SuppressWarnings all warnings disabled on everything.
     */
    @Test
    public void testSingleAll() throws Exception {

        final String[] expected = {
            "16:19: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "20:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "24:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "31:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "35:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "41:31: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "46:35: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "55:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "invalid"),
            "62:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "invalid"),
            "67:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "74:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "78:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "81:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "88:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "88:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "93:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "93:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "101:46: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "101:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "101:65: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "109:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "109:60: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "109:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "115:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "116:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "116:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "116:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    "),
            "116:71: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsSingle2.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on everything.
     */
    @Test
    public void testSingleNoUnchecked() throws Exception {

        final String[] expected = {
            "16:19: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "40:35: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "69:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "72:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "79:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "83:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "89:65: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "95:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "101:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsSingle3.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on certain tokens.
     */
    @Test
    public void testSingleNoUncheckedTokens() throws Exception {

        final String[] expected = {
            "14:19: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "38:35: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "75:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "84:65: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "90:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "96:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsSingle4.java"), expected);
    }

    /**
     * Tests SuppressWarnings un* warning disabled on everything.
     */
    @Test
    public void testSingleNoUnWildcard() throws Exception {

        final String[] expected = {
            "16:19: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "29:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "33:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "39:31: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "43:35: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "53:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unknown"),
            "60:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unknown"),
            "65:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "75:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "78:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "85:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "89:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "94:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "94:65: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "99:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "99:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "105:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "106:71: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsSingle5.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked, unused warning disabled on everything.
     */
    @Test
    public void testSingleNoUncheckedUnused() throws Exception {

        final String[] expected = {
            "16:19: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "29:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "38:31: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "42:35: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "62:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "72:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "75:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "82:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "86:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "91:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "91:65: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "96:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "96:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "102:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "103:71: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsSingle6.java"), expected);
    }

    /**
     * Tests SuppressWarnings *, unchecked, unused warning disabled on everything.
     */
    @Test
    public void testSingleNoUncheckedUnusedAll() throws Exception {

        final String[] expected = {
            "16:19: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "20:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "24:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "31:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "35:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "41:31: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "45:35: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "55:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "invalid"),
            "62:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "invalid"),
            "67:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "74:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "78:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "81:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "89:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "89:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "94:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "94:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "102:46: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "102:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "102:65: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "110:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "110:60: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "110:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "116:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "117:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "117:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "117:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    "),
            "117:71: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsSingle7.java"), expected);
    }

    /**
     * Tests SuppressWarnings with default regex.
     */
    @Test
    public void testCompactDefault() throws Exception {

        final String[] expected = {
            "19:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "23:41: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "57:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "67:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsCompact1.java"), expected);
    }

    @Test
    public void testCompactDefaultNonConstant() throws Exception {

        final String[] expected = {
            "19:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "23:41: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "56:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "67:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),

            "78:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "78:76: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),

            "82:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "88:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "89:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "94:61: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "101:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "101:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "103:69: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "105:29: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputSuppressWarningsCompactNonConstant1.java"), expected);
    }

    /**
     * Tests SuppressWarnings all warnings disabled on everything.
     */
    @Test
    public void testCompactAll() throws Exception {

        final String[] expected = {
            "17:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "17:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "20:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "25:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "25:41: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "30:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "35:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "35:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "40:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "44:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "54:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "invalid"),
            "62:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "67:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "67:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "ignore"),
            "73:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "77:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "80:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsCompact2.java"), expected);
    }

    @Test
    public void testCompactAllNonConstant() throws Exception {

        final String[] expected = {
            "17:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "17:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "21:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "26:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "26:41: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "33:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "38:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "38:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "44:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "48:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "58:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "invalid"),
            "65:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "71:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "71:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "ignore"),
            "78:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "82:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "85:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "95:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "95:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "95:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "95:76: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),

            "100:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "100:43: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "108:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "108:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "108:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "109:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "109:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "109:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "120:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "120:61: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "120:69: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "130:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "131:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "131:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "131:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "131:70: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "132:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "132:69: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "133:21: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "133:29: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "134:21: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputSuppressWarningsCompactNonConstant2.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on everything.
     */
    @Test
    public void testCompactNoUnchecked() throws Exception {

        final String[] expected = {
            "16:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "23:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "41:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "70:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "73:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsCompact3.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on certain tokens.
     */
    @Test
    public void testCompactNoUncheckedTokens() throws Exception {

        final String[] expected = {
            "14:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsCompact4.java"), expected);
    }

    @Test
    public void testCompactNoUncheckedTokensNonConstant() throws Exception {

        final String[] expected = {
            "14:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "73:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "73:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputSuppressWarningsCompactNonConstant3.java"), expected);
    }

    /**
     * Tests SuppressWarnings un* warning disabled on everything.
     */
    @Test
    public void testCompactNoUnWildcard() throws Exception {

        final String[] expected = {
            "17:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "17:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "22:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "29:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "33:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "39:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "43:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "63:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "73:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "76:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsCompact5.java"), expected);
    }

    @Test
    public void testCompactNoUnWildcardNonConstant() throws Exception {

        final String[] expected = {
            "17:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "17:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "22:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "29:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "33:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "39:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "43:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "63:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "72:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "75:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "82:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "86:43: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "91:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "91:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "92:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "92:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "97:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "97:69: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),

            "103:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "105:70: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "107:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "109:21: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputSuppressWarningsCompactNonConstant4.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked, unused warning disabled on everything.
     */
    @Test
    public void testCompactNoUncheckedUnused() throws Exception {

        final String[] expected = {
            "17:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "17:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "22:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "29:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "38:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "42:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "62:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "72:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "75:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsCompact6.java"), expected);
    }

    @Test
    public void testCompactNoUncheckedUnusedNonConstant() throws Exception {

        final String[] expected = {
            "16:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "16:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "22:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "29:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "38:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "42:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "62:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "72:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "75:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "82:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "86:43: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "93:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "93:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "94:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "94:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "104:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "104:69: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "110:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "112:70: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "114:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "117:21: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputSuppressWarningsCompactNonConstant5.java"), expected);
    }

    /**
     * Tests SuppressWarnings *, unchecked, unused warning disabled on everything.
     */
    @Test
    public void testCompactNoUncheckedUnusedAll() throws Exception {

        final String[] expected = {
            "17:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "17:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "20:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "25:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "25:41: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "30:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "35:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "35:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "39:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "42:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "52:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "invalid"),
            "59:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "65:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "65:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "ignore"),
            "70:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "74:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsCompact7.java"), expected);
    }

    @Test
    public void testCompactNoUncheckedUnusedAllNonConstant() throws Exception {

        final String[] expected = {
            "16:20: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "16:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "19:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "24:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "24:41: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "31:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "36:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "36:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "42:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "46:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "56:24: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "invalid"),
            "63:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "69:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "69:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "ignore"),
            "76:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "80:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "83:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "93:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "93:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "93:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "93:76: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "98:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "98:43: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "106:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "106:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "106:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "107:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "107:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "107:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "119:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "119:61: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "119:69: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "130:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "131:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "131:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "131:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "131:70: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "132:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "132:69: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "133:21: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "133:29: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "134:21: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputSuppressWarningsCompactNonConstant6.java"), expected);
    }

    /**
     * Tests SuppressWarnings with default regex.
     */
    @Test
    public void testExpandedDefault() throws Exception {

        final String[] expected = {
            "19:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "23:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "56:29: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "67:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsExpanded1.java"), expected);
    }

    @Test
    public void testExpandedDefaultNonConstant() throws Exception {

        final String[] expected = {
            "19:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "23:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "56:29: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "67:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "79:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "79:82: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "83:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "89:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "90:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "97:67: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "103:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "103:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "105:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "105:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputSuppressWarningsExpandedNonConstant1.java"), expected);
    }

    /**
     * Tests SuppressWarnings all warnings disabled on everything.
     */
    @Test
    public void testExpandedAll() throws Exception {

        final String[] expected = {
            "17:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "17:39: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "21:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "26:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "26:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "33:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "38:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "38:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "44:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "48:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "58:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "invalid"),
            "65:29: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "71:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "71:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "ignore"),
            "78:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "82:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "85:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsExpanded2.java"), expected);
    }

    @Test
    public void testExpandedAllNonConstant() throws Exception {

        final String[] expected = {
            "17:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "17:39: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "21:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "26:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "26:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "33:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "38:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "38:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "44:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "48:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "58:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "invalid"),
            "65:29: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "71:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "71:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "ignore"),
            "78:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "82:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "85:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "96:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "96:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "97:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "97:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "102:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "102:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "110:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "110:61: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "110:72: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "111:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "111:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "111:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "122:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "122:67: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "122:75: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "133:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "134:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "134:58: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "134:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "134:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "135:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "137:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "137:58: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "137:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "137:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputSuppressWarningsExpandedNonConstant2.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on everything.
     */
    @Test
    public void testExpandedNoUnchecked() throws Exception {

        final String[] expected = {
            "16:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "23:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "41:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "70:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "73:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsExpanded3.java"), expected);
    }

    @Test
    public void testExpandedNoUncheckedNonConstant() throws Exception {

        final String[] expected = {
            "16:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "23:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "41:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "70:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "73:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "80:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "80:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "84:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "90:72: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "91:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "98:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "104:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "106:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputSuppressWarningsExpandedNonConstant3.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked warning disabled on certain tokens.
     */
    @Test
    public void testExpandedNoUncheckedTokens() throws Exception {

        final String[] expected = {
            "14:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsExpanded4.java"), expected);
    }

    @Test
    public void testExpandedNoUncheckedTokensNonConstant() throws Exception {

        final String[] expected = {
            "14:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),

            "74:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "74:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputSuppressWarningsExpandedNonConstant4.java"), expected);
    }

    /**
     * Tests SuppressWarnings un* warning disabled on everything.
     */
    @Test
    public void testExpandedNoUnWildcard() throws Exception {

        final String[] expected = {
            "15:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "15:39: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "22:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "29:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "33:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "39:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "43:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "63:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "73:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "76:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsExpanded5.java"), expected);
    }

    @Test
    public void testExpandedNoUnWildcardNonConstant() throws Exception {

        final String[] expected = {
            "17:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "17:39: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "24:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "31:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "35:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "41:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "45:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "65:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "75:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "78:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "85:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "85:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "90:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "97:61: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "97:72: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "98:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "98:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "107:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "107:75: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "113:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "115:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "117:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "119:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputSuppressWarningsExpandedNonConstant5.java"), expected);
    }

    /**
     * Tests SuppressWarnings unchecked, unused warning disabled on everything.
     */
    @Test
    public void testExpandedNoUncheckedUnused() throws Exception {

        final String[] expected = {
            "15:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "15:39: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "22:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "29:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "38:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "42:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "62:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "72:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "75:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsExpanded6.java"), expected);
    }

    @Test
    public void testExpandedNoUncheckedUnusedNonConstant() throws Exception {

        final String[] expected = {
            "17:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "17:39: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "24:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "31:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "40:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "44:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "64:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "74:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "77:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "84:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "84:68: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "89:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "96:61: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "96:72: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "97:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "97:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "106:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "106:75: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "112:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "114:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "116:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "118:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputSuppressWarningsExpandedNonConstant6.java"), expected);
    }

    /**
     * Tests SuppressWarnings *, unchecked, unused warning disabled on everything.
     */
    @Test
    public void testExpandedNoUncheckedUnusedAll() throws Exception {

        final String[] expected = {
            "17:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "17:39: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "21:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "26:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "26:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "33:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "38:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "38:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "44:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "48:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "58:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "invalid"),
            "65:29: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "71:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "71:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "ignore"),
            "78:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "82:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "85:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsExpanded7.java"), expected);
    }

    @Test
    public void testExpandedNoUncheckedUnusedAllNonConstant() throws Exception {

        final String[] expected = {
            "17:26: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "17:39: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "21:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "26:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "26:47: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "33:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "38:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "38:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "44:38: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "48:42: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "58:30: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "invalid"),
            "65:29: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "71:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "71:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "ignore"),
            "78:33: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "82:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "85:55: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "93:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "93:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "94:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "94:37: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "103:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "103:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "111:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "111:61: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "111:72: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "112:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "112:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "112:51: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "123:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "123:67: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "123:75: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "134:54: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "135:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "135:58: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "135:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "135:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "136:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "138:53: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "138:58: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "foo"),
            "138:66: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "138:74: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputSuppressWarningsExpandedNonConstant7.java"), expected);
    }

    @Test
    public void testUncheckedInConstant() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsConstants.java"), expected);
    }

    @Test
    public void testValuePairAnnotation() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsValuePair.java"), expected);
    }

    @Test
    public void testWorkingProperlyOnComplexAnnotations() throws Exception {

        final String[] expected = {
            "31:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "38:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "46:5: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsHolder.java"), expected);
    }

    @Test
    public void testWorkingProperlyOnComplexAnnotationsNonConstant() throws Exception {

        final String[] expected = {
            "31:34: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "38:23: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "43:48: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "49:5: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputSuppressWarningsHolderNonConstant.java"), expected);
    }

    @Test
    public void testSuppressWarningsRecords() throws Exception {

        final String[] expected = {
            "19:28: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "19:41: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "25:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "25:45: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "28:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "   "),
            "33:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "33:49: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "40:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "45:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unforgiven"),
            "45:50: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "    un"),
            "52:40: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "58:32: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "invalid"),
            "65:31: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "71:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "71:46: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "something else"),
            "78:35: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "82:36: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "87:57: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsRecords.java"), expected);
    }

    @Test
    public void testSuppressWarningsPatternVariables() throws Exception {

        final String[] expected = {
            "18:57: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
            "21:62: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unused"),
            "24:44: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, "unchecked"),
            "27:27: " + getCheckMessage(MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED, ""),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputSuppressWarningsPatternVariables.java"),
                expected);
    }

}
