///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.MagicNumberCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class MagicNumberCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/magicnumber";
    }

    @Test
    public void testLocalVariables()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMagicNumber_8.java"), expected);
    }

    @Test
    public void testLocalVariables2()
            throws Exception {
        final String[] expected = {
            "25:17: " + getCheckMessage(MSG_KEY, "8"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumber_9.java"), expected);
    }

    @Test
    public void testDefault()
            throws Exception {
        final String[] expected = {
            "54:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "55:32: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "56:27: " + getCheckMessage(MSG_KEY, "3"),
            "56:31: " + getCheckMessage(MSG_KEY, "4"),
            "58:29: " + getCheckMessage(MSG_KEY, "3"),
            "60:23: " + getCheckMessage(MSG_KEY, "3"),
            "61:26: " + getCheckMessage(MSG_KEY, "1.5"),
            "63:22: " + getCheckMessage(MSG_KEY, "3"),
            "63:29: " + getCheckMessage(MSG_KEY, "5"),
            "63:37: " + getCheckMessage(MSG_KEY, "3"),
            "67:26: " + getCheckMessage(MSG_KEY, "3"),
            "68:39: " + getCheckMessage(MSG_KEY, "3"),
            "72:25: " + getCheckMessage(MSG_KEY, "010"),
            "73:25: " + getCheckMessage(MSG_KEY, "011"),
            "75:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "76:30: " + getCheckMessage(MSG_KEY, "011l"),
            "79:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "80:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "82:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "83:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "96:28: " + getCheckMessage(MSG_KEY, "3"),
            "103:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "111:30: " + getCheckMessage(MSG_KEY, "+3"),
            "112:29: " + getCheckMessage(MSG_KEY, "-2"),
            "113:35: " + getCheckMessage(MSG_KEY, "+3.5"),
            "114:36: " + getCheckMessage(MSG_KEY, "-2.5"),
            "122:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "123:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "126:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "127:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "142:20: " + getCheckMessage(MSG_KEY, "378"),
            "171:16: " + getCheckMessage(MSG_KEY, "31"),
            "176:16: " + getCheckMessage(MSG_KEY, "42"),
            "181:16: " + getCheckMessage(MSG_KEY, "13"),
            "185:15: " + getCheckMessage(MSG_KEY, "21"),
            "189:15: " + getCheckMessage(MSG_KEY, "37"),
            "193:15: " + getCheckMessage(MSG_KEY, "101"),
            "196:42: " + getCheckMessage(MSG_KEY, "42"),
            "200:48: " + getCheckMessage(MSG_KEY, "43"),
            "204:42: " + getCheckMessage(MSG_KEY, "-44"),
            "208:48: " + getCheckMessage(MSG_KEY, "-45"),
            "225:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "226:14: " + getCheckMessage(MSG_KEY,
                    "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "236:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumber_1.java"), expected);
    }

    @Test
    public void testIgnoreSome()
            throws Exception {
        final String[] expected = {
            "36:25: " + getCheckMessage(MSG_KEY, "2"),
            "42:35: " + getCheckMessage(MSG_KEY, "2"),
            "44:24: " + getCheckMessage(MSG_KEY, "2"),
            "46:29: " + getCheckMessage(MSG_KEY, "2.0"),
            "48:29: " + getCheckMessage(MSG_KEY, "2"),
            "50:17: " + getCheckMessage(MSG_KEY, "2"),
            "52:19: " + getCheckMessage(MSG_KEY, "2.0"),
            "56:32: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "57:31: " + getCheckMessage(MSG_KEY, "4"),
            "62:26: " + getCheckMessage(MSG_KEY, "1.5"),
            "64:29: " + getCheckMessage(MSG_KEY, "5"),
            "75:25: " + getCheckMessage(MSG_KEY, "011"),
            "78:30: " + getCheckMessage(MSG_KEY, "011l"),
            "83:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "86:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "106:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "115:29: " + getCheckMessage(MSG_KEY, "-2"),
            "116:35: " + getCheckMessage(MSG_KEY, "+3.5"),
            "117:36: " + getCheckMessage(MSG_KEY, "-2.5"),
            "123:34: " + getCheckMessage(MSG_KEY, "0xffffffff"),
            "124:36: " + getCheckMessage(MSG_KEY, "0xffffffffffffffffL"),
            "125:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "126:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "127:36: " + getCheckMessage(MSG_KEY, "037777777777"),
            "128:38: " + getCheckMessage(MSG_KEY, "01777777777777777777777L"),
            "129:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "130:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "145:20: " + getCheckMessage(MSG_KEY, "378"),
            "174:16: " + getCheckMessage(MSG_KEY, "31"),
            "179:16: " + getCheckMessage(MSG_KEY, "42"),
            "184:16: " + getCheckMessage(MSG_KEY, "13"),
            "188:15: " + getCheckMessage(MSG_KEY, "21"),
            "192:15: " + getCheckMessage(MSG_KEY, "37"),
            "196:15: " + getCheckMessage(MSG_KEY, "101"),
            "228:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "229:14: " + getCheckMessage(MSG_KEY,
                    "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "239:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumber_2.java"), expected);
    }

    @Test
    public void testIgnoreNone()
            throws Exception {
        final String[] expected = {
            "41:24: " + getCheckMessage(MSG_KEY, "1"),
            "42:25: " + getCheckMessage(MSG_KEY, "2"),
            "43:26: " + getCheckMessage(MSG_KEY, "0L"),
            "44:26: " + getCheckMessage(MSG_KEY, "0l"),
            "45:30: " + getCheckMessage(MSG_KEY, "0D"),
            "46:30: " + getCheckMessage(MSG_KEY, "0d"),
            "48:35: " + getCheckMessage(MSG_KEY, "2"),
            "50:20: " + getCheckMessage(MSG_KEY, "1"),
            "50:24: " + getCheckMessage(MSG_KEY, "2"),
            "51:21: " + getCheckMessage(MSG_KEY, "1"),
            "52:23: " + getCheckMessage(MSG_KEY, "1.0"),
            "52:29: " + getCheckMessage(MSG_KEY, "2.0"),
            "54:22: " + getCheckMessage(MSG_KEY, "0"),
            "54:29: " + getCheckMessage(MSG_KEY, "2"),
            "56:13: " + getCheckMessage(MSG_KEY, "1"),
            "56:17: " + getCheckMessage(MSG_KEY, "2"),
            "58:13: " + getCheckMessage(MSG_KEY, "1.0"),
            "58:19: " + getCheckMessage(MSG_KEY, "2.0"),
            "61:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "62:32: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "63:27: " + getCheckMessage(MSG_KEY, "3"),
            "63:31: " + getCheckMessage(MSG_KEY, "4"),
            "65:29: " + getCheckMessage(MSG_KEY, "3"),
            "67:23: " + getCheckMessage(MSG_KEY, "3"),
            "68:26: " + getCheckMessage(MSG_KEY, "1.5"),
            "70:22: " + getCheckMessage(MSG_KEY, "3"),
            "70:29: " + getCheckMessage(MSG_KEY, "5"),
            "70:37: " + getCheckMessage(MSG_KEY, "3"),
            "74:26: " + getCheckMessage(MSG_KEY, "3"),
            "75:39: " + getCheckMessage(MSG_KEY, "3"),
            "79:25: " + getCheckMessage(MSG_KEY, "00"),
            "80:25: " + getCheckMessage(MSG_KEY, "010"),
            "81:25: " + getCheckMessage(MSG_KEY, "011"),
            "83:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "84:30: " + getCheckMessage(MSG_KEY, "011l"),
            "87:23: " + getCheckMessage(MSG_KEY, "0x0"),
            "88:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "89:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "90:28: " + getCheckMessage(MSG_KEY, "0x0L"),
            "91:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "92:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "105:28: " + getCheckMessage(MSG_KEY, "3"),
            "112:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "120:30: " + getCheckMessage(MSG_KEY, "+3"),
            "121:29: " + getCheckMessage(MSG_KEY, "-2"),
            "122:35: " + getCheckMessage(MSG_KEY, "+3.5"),
            "123:36: " + getCheckMessage(MSG_KEY, "-2.5"),
            "129:34: " + getCheckMessage(MSG_KEY, "0xffffffff"),
            "130:36: " + getCheckMessage(MSG_KEY, "0xffffffffffffffffL"),
            "131:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "132:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "133:36: " + getCheckMessage(MSG_KEY, "037777777777"),
            "134:38: " + getCheckMessage(MSG_KEY, "01777777777777777777777L"),
            "135:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "136:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "151:20: " + getCheckMessage(MSG_KEY, "378"),
            "180:16: " + getCheckMessage(MSG_KEY, "31"),
            "185:16: " + getCheckMessage(MSG_KEY, "42"),
            "190:16: " + getCheckMessage(MSG_KEY, "13"),
            "194:15: " + getCheckMessage(MSG_KEY, "21"),
            "198:15: " + getCheckMessage(MSG_KEY, "37"),
            "202:15: " + getCheckMessage(MSG_KEY, "101"),
            "234:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "235:14: " + getCheckMessage(MSG_KEY,
                    "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "245:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumber_3.java"), expected);
    }

    @Test
    public void testIntegersOnly()
            throws Exception {
        final String[] expected = {
            "55:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "57:27: " + getCheckMessage(MSG_KEY, "3"),
            "57:31: " + getCheckMessage(MSG_KEY, "4"),
            "59:29: " + getCheckMessage(MSG_KEY, "3"),
            "61:23: " + getCheckMessage(MSG_KEY, "3"),
            "64:22: " + getCheckMessage(MSG_KEY, "3"),
            "64:29: " + getCheckMessage(MSG_KEY, "5"),
            "64:37: " + getCheckMessage(MSG_KEY, "3"),
            "68:26: " + getCheckMessage(MSG_KEY, "3"),
            "69:39: " + getCheckMessage(MSG_KEY, "3"),
            "74:25: " + getCheckMessage(MSG_KEY, "010"),
            "75:25: " + getCheckMessage(MSG_KEY, "011"),
            "77:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "78:30: " + getCheckMessage(MSG_KEY, "011l"),
            "82:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "83:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "85:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "86:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "99:28: " + getCheckMessage(MSG_KEY, "3"),
            "106:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "114:30: " + getCheckMessage(MSG_KEY, "+3"),
            "115:29: " + getCheckMessage(MSG_KEY, "-2"),
            "125:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "126:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "129:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "130:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "145:20: " + getCheckMessage(MSG_KEY, "378"),
            "174:16: " + getCheckMessage(MSG_KEY, "31"),
            "179:16: " + getCheckMessage(MSG_KEY, "42"),
            "184:16: " + getCheckMessage(MSG_KEY, "13"),
            "188:15: " + getCheckMessage(MSG_KEY, "21"),
            "192:15: " + getCheckMessage(MSG_KEY, "37"),
            "196:15: " + getCheckMessage(MSG_KEY, "101"),
            "228:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "229:14: " + getCheckMessage(MSG_KEY,
                    "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "239:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumber_4.java"), expected);
    }

    @Test
    public void testIgnoreNegativeOctalHex() throws Exception {
        final String[] expected = {
            "55:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "57:27: " + getCheckMessage(MSG_KEY, "3"),
            "57:31: " + getCheckMessage(MSG_KEY, "4"),
            "59:29: " + getCheckMessage(MSG_KEY, "3"),
            "61:23: " + getCheckMessage(MSG_KEY, "3"),
            "64:22: " + getCheckMessage(MSG_KEY, "3"),
            "64:29: " + getCheckMessage(MSG_KEY, "5"),
            "64:37: " + getCheckMessage(MSG_KEY, "3"),
            "68:26: " + getCheckMessage(MSG_KEY, "3"),
            "69:39: " + getCheckMessage(MSG_KEY, "3"),
            "74:25: " + getCheckMessage(MSG_KEY, "010"),
            "75:25: " + getCheckMessage(MSG_KEY, "011"),
            "77:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "78:30: " + getCheckMessage(MSG_KEY, "011l"),
            "82:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "83:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "85:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "86:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "99:28: " + getCheckMessage(MSG_KEY, "3"),
            "106:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "114:30: " + getCheckMessage(MSG_KEY, "+3"),
            "145:20: " + getCheckMessage(MSG_KEY, "378"),
            "174:16: " + getCheckMessage(MSG_KEY, "31"),
            "179:16: " + getCheckMessage(MSG_KEY, "42"),
            "184:16: " + getCheckMessage(MSG_KEY, "13"),
            "188:15: " + getCheckMessage(MSG_KEY, "21"),
            "192:15: " + getCheckMessage(MSG_KEY, "37"),
            "196:15: " + getCheckMessage(MSG_KEY, "101"),
            "228:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "229:14: " + getCheckMessage(MSG_KEY,
                    "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "239:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumber_5.java"), expected);
    }

    @Test
    public void testIgnoreHashCodeMethod() throws Exception {
        final String[] expected = {
            "55:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "56:32: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "57:27: " + getCheckMessage(MSG_KEY, "3"),
            "57:31: " + getCheckMessage(MSG_KEY, "4"),
            "59:29: " + getCheckMessage(MSG_KEY, "3"),
            "61:23: " + getCheckMessage(MSG_KEY, "3"),
            "62:26: " + getCheckMessage(MSG_KEY, "1.5"),
            "64:22: " + getCheckMessage(MSG_KEY, "3"),
            "64:29: " + getCheckMessage(MSG_KEY, "5"),
            "64:37: " + getCheckMessage(MSG_KEY, "3"),
            "68:26: " + getCheckMessage(MSG_KEY, "3"),
            "69:39: " + getCheckMessage(MSG_KEY, "3"),
            "74:25: " + getCheckMessage(MSG_KEY, "010"),
            "75:25: " + getCheckMessage(MSG_KEY, "011"),
            "77:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "78:30: " + getCheckMessage(MSG_KEY, "011l"),
            "82:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "83:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "85:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "86:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "99:28: " + getCheckMessage(MSG_KEY, "3"),
            "106:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "114:30: " + getCheckMessage(MSG_KEY, "+3"),
            "115:29: " + getCheckMessage(MSG_KEY, "-2"),
            "116:35: " + getCheckMessage(MSG_KEY, "+3.5"),
            "117:36: " + getCheckMessage(MSG_KEY, "-2.5"),
            "125:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "126:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "129:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "130:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "145:20: " + getCheckMessage(MSG_KEY, "378"),
            "179:16: " + getCheckMessage(MSG_KEY, "42"),
            "184:16: " + getCheckMessage(MSG_KEY, "13"),
            "188:15: " + getCheckMessage(MSG_KEY, "21"),
            "192:15: " + getCheckMessage(MSG_KEY, "37"),
            "196:15: " + getCheckMessage(MSG_KEY, "101"),
            "228:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "229:14: " + getCheckMessage(MSG_KEY,
                    "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "239:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumber_6.java"), expected);
    }

    @Test
    public void testIgnoreFieldDeclaration()
            throws Exception {
        final String[] expected = {
            "55:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "56:32: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "57:27: " + getCheckMessage(MSG_KEY, "3"),
            "57:31: " + getCheckMessage(MSG_KEY, "4"),
            "59:29: " + getCheckMessage(MSG_KEY, "3"),
            "61:23: " + getCheckMessage(MSG_KEY, "3"),
            "62:26: " + getCheckMessage(MSG_KEY, "1.5"),
            "64:22: " + getCheckMessage(MSG_KEY, "3"),
            "64:29: " + getCheckMessage(MSG_KEY, "5"),
            "64:37: " + getCheckMessage(MSG_KEY, "3"),
            "68:26: " + getCheckMessage(MSG_KEY, "3"),
            "69:39: " + getCheckMessage(MSG_KEY, "3"),
            "74:25: " + getCheckMessage(MSG_KEY, "010"),
            "75:25: " + getCheckMessage(MSG_KEY, "011"),
            "77:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "78:30: " + getCheckMessage(MSG_KEY, "011l"),
            "82:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "83:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "85:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "86:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "145:20: " + getCheckMessage(MSG_KEY, "378"),
            "174:16: " + getCheckMessage(MSG_KEY, "31"),
            "179:16: " + getCheckMessage(MSG_KEY, "42"),
            "184:16: " + getCheckMessage(MSG_KEY, "13"),
            "188:15: " + getCheckMessage(MSG_KEY, "21"),
            "192:15: " + getCheckMessage(MSG_KEY, "37"),
            "196:15: " + getCheckMessage(MSG_KEY, "101"),
            "199:42: " + getCheckMessage(MSG_KEY, "42"),
            "203:48: " + getCheckMessage(MSG_KEY, "43"),
            "207:42: " + getCheckMessage(MSG_KEY, "-44"),
            "211:48: " + getCheckMessage(MSG_KEY, "-45"),
            "239:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumber_7.java"), expected);
    }

    @Test
    public void testWaiverParentToken()
            throws Exception {
        final String[] expected = {
            "55:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "56:32: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "57:27: " + getCheckMessage(MSG_KEY, "3"),
            "57:31: " + getCheckMessage(MSG_KEY, "4"),
            "59:29: " + getCheckMessage(MSG_KEY, "3"),
            "61:23: " + getCheckMessage(MSG_KEY, "3"),
            "62:26: " + getCheckMessage(MSG_KEY, "1.5"),
            "64:22: " + getCheckMessage(MSG_KEY, "3"),
            "64:29: " + getCheckMessage(MSG_KEY, "5"),
            "64:37: " + getCheckMessage(MSG_KEY, "3"),
            "68:26: " + getCheckMessage(MSG_KEY, "3"),
            "69:39: " + getCheckMessage(MSG_KEY, "3"),
            "74:25: " + getCheckMessage(MSG_KEY, "010"),
            "75:25: " + getCheckMessage(MSG_KEY, "011"),
            "77:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "78:30: " + getCheckMessage(MSG_KEY, "011l"),
            "82:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "83:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "85:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "86:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "99:28: " + getCheckMessage(MSG_KEY, "3"),
            "106:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "114:30: " + getCheckMessage(MSG_KEY, "+3"),
            "115:29: " + getCheckMessage(MSG_KEY, "-2"),
            "116:35: " + getCheckMessage(MSG_KEY, "+3.5"),
            "117:36: " + getCheckMessage(MSG_KEY, "-2.5"),
            "125:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "126:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "129:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "130:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "145:20: " + getCheckMessage(MSG_KEY, "378"),
            "154:52: " + getCheckMessage(MSG_KEY, "27"),
            "157:53: " + getCheckMessage(MSG_KEY, "3"),
            "157:56: " + getCheckMessage(MSG_KEY, "3"),
            "157:59: " + getCheckMessage(MSG_KEY, "3"),
            "157:62: " + getCheckMessage(MSG_KEY, "3"),
            "174:16: " + getCheckMessage(MSG_KEY, "31"),
            "179:16: " + getCheckMessage(MSG_KEY, "42"),
            "184:16: " + getCheckMessage(MSG_KEY, "13"),
            "188:15: " + getCheckMessage(MSG_KEY, "21"),
            "192:15: " + getCheckMessage(MSG_KEY, "37"),
            "196:15: " + getCheckMessage(MSG_KEY, "101"),
            "199:42: " + getCheckMessage(MSG_KEY, "42"),
            "203:48: " + getCheckMessage(MSG_KEY, "43"),
            "207:42: " + getCheckMessage(MSG_KEY, "-44"),
            "211:48: " + getCheckMessage(MSG_KEY, "-45"),
            "223:63: " + getCheckMessage(MSG_KEY, "62"),
            "228:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "229:14: " + getCheckMessage(MSG_KEY,
                    "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "239:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumber.java"), expected);
    }

    @Test
    public void testMagicNumberRecordsDefault()
            throws Exception {
        final String[] expected = {
            "19:11: " + getCheckMessage(MSG_KEY, "6"),
            "21:36: " + getCheckMessage(MSG_KEY, "7"),
            "25:29: " + getCheckMessage(MSG_KEY, "8"),
            "29:29: " + getCheckMessage(MSG_KEY, "8"),
            "33:20: " + getCheckMessage(MSG_KEY, "10"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMagicNumberRecordsDefault.java"), expected);
    }

    @Test
    public void testMagicNumberIgnoreFieldDeclarationRecords()
            throws Exception {
        final String[] expected = {
            "19:11: " + getCheckMessage(MSG_KEY, "6"),
            "25:29: " + getCheckMessage(MSG_KEY, "8"),
            "29:29: " + getCheckMessage(MSG_KEY, "8"),
            "33:20: " + getCheckMessage(MSG_KEY, "10"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMagicNumberIgnoreFieldDeclarationRecords.java"),
                expected);
    }

    @Test
    public void testIgnoreInAnnotationElementDefault() throws Exception {
        final String[] expected = {
            "18:29: " + getCheckMessage(MSG_KEY, "10"),
            "19:33: " + getCheckMessage(MSG_KEY, "11"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberAnnotationElement.java"), expected);
    }
}
