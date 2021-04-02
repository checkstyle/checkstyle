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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.MagicNumberCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class MagicNumberCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/magicnumber";
    }

    @Test
    public void testDefault()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MagicNumberCheck.class);
        final String[] expected = {
            "42:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "43:32: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "44:27: " + getCheckMessage(MSG_KEY, "3"),
            "44:31: " + getCheckMessage(MSG_KEY, "4"),
            "46:29: " + getCheckMessage(MSG_KEY, "3"),
            "48:23: " + getCheckMessage(MSG_KEY, "3"),
            "49:26: " + getCheckMessage(MSG_KEY, "1.5"),
            "51:22: " + getCheckMessage(MSG_KEY, "3"),
            "51:29: " + getCheckMessage(MSG_KEY, "5"),
            "51:37: " + getCheckMessage(MSG_KEY, "3"),
            "55:26: " + getCheckMessage(MSG_KEY, "3"),
            "56:39: " + getCheckMessage(MSG_KEY, "3"),
            "60:25: " + getCheckMessage(MSG_KEY, "010"),
            "61:25: " + getCheckMessage(MSG_KEY, "011"),
            "63:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "64:30: " + getCheckMessage(MSG_KEY, "011l"),
            "67:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "68:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "70:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "71:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "84:28: " + getCheckMessage(MSG_KEY, "3"),
            "91:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "99:30: " + getCheckMessage(MSG_KEY, "+3"),
            "100:29: " + getCheckMessage(MSG_KEY, "-2"),
            "101:35: " + getCheckMessage(MSG_KEY, "+3.5"),
            "102:36: " + getCheckMessage(MSG_KEY, "-2.5"),
            "110:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "111:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "114:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "115:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "130:20: " + getCheckMessage(MSG_KEY, "378"),
            "159:16: " + getCheckMessage(MSG_KEY, "31"),
            "164:16: " + getCheckMessage(MSG_KEY, "42"),
            "169:16: " + getCheckMessage(MSG_KEY, "13"),
            "173:15: " + getCheckMessage(MSG_KEY, "21"),
            "177:15: " + getCheckMessage(MSG_KEY, "37"),
            "181:15: " + getCheckMessage(MSG_KEY, "101"),
            "184:42: " + getCheckMessage(MSG_KEY, "42"),
            "188:48: " + getCheckMessage(MSG_KEY, "43"),
            "192:42: " + getCheckMessage(MSG_KEY, "-44"),
            "196:48: " + getCheckMessage(MSG_KEY, "-45"),
            "213:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "214:14: " + getCheckMessage(MSG_KEY,
                "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "224:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verify(checkConfig, getPath("InputMagicNumber_1.java"), expected);
    }

    @Test
    public void testIgnoreSome()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MagicNumberCheck.class);
        checkConfig.addAttribute("ignoreNumbers", "0, 1, 3.0, 8, 16, 3000");
        checkConfig.addAttribute("ignoreAnnotation", "true");
        final String[] expected = {
            "28:25: " + getCheckMessage(MSG_KEY, "2"),
            "34:35: " + getCheckMessage(MSG_KEY, "2"),
            "36:24: " + getCheckMessage(MSG_KEY, "2"),
            "38:29: " + getCheckMessage(MSG_KEY, "2.0"),
            "40:29: " + getCheckMessage(MSG_KEY, "2"),
            "42:17: " + getCheckMessage(MSG_KEY, "2"),
            "44:19: " + getCheckMessage(MSG_KEY, "2.0"),
            "48:32: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "49:31: " + getCheckMessage(MSG_KEY, "4"),
            "54:26: " + getCheckMessage(MSG_KEY, "1.5"),
            "56:29: " + getCheckMessage(MSG_KEY, "5"),
            "67:25: " + getCheckMessage(MSG_KEY, "011"),
            "70:30: " + getCheckMessage(MSG_KEY, "011l"),
            "75:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "78:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "98:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "107:29: " + getCheckMessage(MSG_KEY, "-2"),
            "108:35: " + getCheckMessage(MSG_KEY, "+3.5"),
            "109:36: " + getCheckMessage(MSG_KEY, "-2.5"),
            "115:34: " + getCheckMessage(MSG_KEY, "0xffffffff"),
            "116:36: " + getCheckMessage(MSG_KEY, "0xffffffffffffffffL"),
            "117:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "118:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "119:36: " + getCheckMessage(MSG_KEY, "037777777777"),
            "120:38: " + getCheckMessage(MSG_KEY, "01777777777777777777777L"),
            "121:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "122:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "137:20: " + getCheckMessage(MSG_KEY, "378"),
            "166:16: " + getCheckMessage(MSG_KEY, "31"),
            "171:16: " + getCheckMessage(MSG_KEY, "42"),
            "176:16: " + getCheckMessage(MSG_KEY, "13"),
            "180:15: " + getCheckMessage(MSG_KEY, "21"),
            "184:15: " + getCheckMessage(MSG_KEY, "37"),
            "188:15: " + getCheckMessage(MSG_KEY, "101"),
            "220:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "221:14: " + getCheckMessage(MSG_KEY,
                "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "231:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verify(checkConfig, getPath("InputMagicNumber_2.java"), expected);
    }

    @Test
    public void testIgnoreNone()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MagicNumberCheck.class);
        checkConfig.addAttribute("ignoreNumbers", "");
        checkConfig.addAttribute("ignoreAnnotation", "true");
        final String[] expected = {
            "27:24: " + getCheckMessage(MSG_KEY, "1"),
            "28:25: " + getCheckMessage(MSG_KEY, "2"),
            "29:26: " + getCheckMessage(MSG_KEY, "0L"),
            "30:26: " + getCheckMessage(MSG_KEY, "0l"),
            "31:30: " + getCheckMessage(MSG_KEY, "0D"),
            "32:30: " + getCheckMessage(MSG_KEY, "0d"),
            "34:35: " + getCheckMessage(MSG_KEY, "2"),
            "36:20: " + getCheckMessage(MSG_KEY, "1"),
            "36:24: " + getCheckMessage(MSG_KEY, "2"),
            "37:21: " + getCheckMessage(MSG_KEY, "1"),
            "38:23: " + getCheckMessage(MSG_KEY, "1.0"),
            "38:29: " + getCheckMessage(MSG_KEY, "2.0"),
            "40:22: " + getCheckMessage(MSG_KEY, "0"),
            "40:29: " + getCheckMessage(MSG_KEY, "2"),
            "42:13: " + getCheckMessage(MSG_KEY, "1"),
            "42:17: " + getCheckMessage(MSG_KEY, "2"),
            "44:13: " + getCheckMessage(MSG_KEY, "1.0"),
            "44:19: " + getCheckMessage(MSG_KEY, "2.0"),
            "47:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "48:32: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "49:27: " + getCheckMessage(MSG_KEY, "3"),
            "49:31: " + getCheckMessage(MSG_KEY, "4"),
            "51:29: " + getCheckMessage(MSG_KEY, "3"),
            "53:23: " + getCheckMessage(MSG_KEY, "3"),
            "54:26: " + getCheckMessage(MSG_KEY, "1.5"),
            "56:22: " + getCheckMessage(MSG_KEY, "3"),
            "56:29: " + getCheckMessage(MSG_KEY, "5"),
            "56:37: " + getCheckMessage(MSG_KEY, "3"),
            "60:26: " + getCheckMessage(MSG_KEY, "3"),
            "61:39: " + getCheckMessage(MSG_KEY, "3"),
            "65:25: " + getCheckMessage(MSG_KEY, "00"),
            "66:25: " + getCheckMessage(MSG_KEY, "010"),
            "67:25: " + getCheckMessage(MSG_KEY, "011"),
            "69:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "70:30: " + getCheckMessage(MSG_KEY, "011l"),
            "73:23: " + getCheckMessage(MSG_KEY, "0x0"),
            "74:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "75:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "76:28: " + getCheckMessage(MSG_KEY, "0x0L"),
            "77:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "78:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "91:28: " + getCheckMessage(MSG_KEY, "3"),
            "98:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "106:30: " + getCheckMessage(MSG_KEY, "+3"),
            "107:29: " + getCheckMessage(MSG_KEY, "-2"),
            "108:35: " + getCheckMessage(MSG_KEY, "+3.5"),
            "109:36: " + getCheckMessage(MSG_KEY, "-2.5"),
            "115:34: " + getCheckMessage(MSG_KEY, "0xffffffff"),
            "116:36: " + getCheckMessage(MSG_KEY, "0xffffffffffffffffL"),
            "117:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "118:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "119:36: " + getCheckMessage(MSG_KEY, "037777777777"),
            "120:38: " + getCheckMessage(MSG_KEY, "01777777777777777777777L"),
            "121:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "122:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "137:20: " + getCheckMessage(MSG_KEY, "378"),
            "166:16: " + getCheckMessage(MSG_KEY, "31"),
            "171:16: " + getCheckMessage(MSG_KEY, "42"),
            "176:16: " + getCheckMessage(MSG_KEY, "13"),
            "180:15: " + getCheckMessage(MSG_KEY, "21"),
            "184:15: " + getCheckMessage(MSG_KEY, "37"),
            "188:15: " + getCheckMessage(MSG_KEY, "101"),
            "220:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "221:14: " + getCheckMessage(MSG_KEY,
                "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "231:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verify(checkConfig, getPath("InputMagicNumber_3.java"), expected);
    }

    @Test
    public void testIntegersOnly()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MagicNumberCheck.class);
        checkConfig.addAttribute("tokens", "NUM_INT, NUM_LONG");
        checkConfig.addAttribute("ignoreAnnotation", "true");
        final String[] expected = {
            "47:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "49:27: " + getCheckMessage(MSG_KEY, "3"),
            "49:31: " + getCheckMessage(MSG_KEY, "4"),
            "51:29: " + getCheckMessage(MSG_KEY, "3"),
            "53:23: " + getCheckMessage(MSG_KEY, "3"),
            "56:22: " + getCheckMessage(MSG_KEY, "3"),
            "56:29: " + getCheckMessage(MSG_KEY, "5"),
            "56:37: " + getCheckMessage(MSG_KEY, "3"),
            "60:26: " + getCheckMessage(MSG_KEY, "3"),
            "61:39: " + getCheckMessage(MSG_KEY, "3"),
            "66:25: " + getCheckMessage(MSG_KEY, "010"),
            "67:25: " + getCheckMessage(MSG_KEY, "011"),
            "69:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "70:30: " + getCheckMessage(MSG_KEY, "011l"),
            "74:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "75:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "77:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "78:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "91:28: " + getCheckMessage(MSG_KEY, "3"),
            "98:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "106:30: " + getCheckMessage(MSG_KEY, "+3"),
            "107:29: " + getCheckMessage(MSG_KEY, "-2"),
            "117:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "118:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "121:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "122:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "137:20: " + getCheckMessage(MSG_KEY, "378"),
            "166:16: " + getCheckMessage(MSG_KEY, "31"),
            "171:16: " + getCheckMessage(MSG_KEY, "42"),
            "176:16: " + getCheckMessage(MSG_KEY, "13"),
            "180:15: " + getCheckMessage(MSG_KEY, "21"),
            "184:15: " + getCheckMessage(MSG_KEY, "37"),
            "188:15: " + getCheckMessage(MSG_KEY, "101"),
            "220:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "221:14: " + getCheckMessage(MSG_KEY,
                "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "231:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verify(checkConfig, getPath("InputMagicNumber_4.java"), expected);
    }

    @Test
    public void testIgnoreNegativeOctalHex() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MagicNumberCheck.class);
        checkConfig.addAttribute("ignoreNumbers",
            "-9223372036854775808, -2147483648, -1, 0, 1, 2, -2");
        checkConfig.addAttribute("tokens", "NUM_INT, NUM_LONG");
        checkConfig.addAttribute("ignoreAnnotation", "true");
        final String[] expected = {
            "48:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "50:27: " + getCheckMessage(MSG_KEY, "3"),
            "50:31: " + getCheckMessage(MSG_KEY, "4"),
            "52:29: " + getCheckMessage(MSG_KEY, "3"),
            "54:23: " + getCheckMessage(MSG_KEY, "3"),
            "57:22: " + getCheckMessage(MSG_KEY, "3"),
            "57:29: " + getCheckMessage(MSG_KEY, "5"),
            "57:37: " + getCheckMessage(MSG_KEY, "3"),
            "61:26: " + getCheckMessage(MSG_KEY, "3"),
            "62:39: " + getCheckMessage(MSG_KEY, "3"),
            "67:25: " + getCheckMessage(MSG_KEY, "010"),
            "68:25: " + getCheckMessage(MSG_KEY, "011"),
            "70:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "71:30: " + getCheckMessage(MSG_KEY, "011l"),
            "75:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "76:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "78:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "79:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "92:28: " + getCheckMessage(MSG_KEY, "3"),
            "99:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "107:30: " + getCheckMessage(MSG_KEY, "+3"),
            "138:20: " + getCheckMessage(MSG_KEY, "378"),
            "167:16: " + getCheckMessage(MSG_KEY, "31"),
            "172:16: " + getCheckMessage(MSG_KEY, "42"),
            "177:16: " + getCheckMessage(MSG_KEY, "13"),
            "181:15: " + getCheckMessage(MSG_KEY, "21"),
            "185:15: " + getCheckMessage(MSG_KEY, "37"),
            "189:15: " + getCheckMessage(MSG_KEY, "101"),
            "221:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "222:14: " + getCheckMessage(MSG_KEY,
                "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "232:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verify(checkConfig, getPath("InputMagicNumber_5.java"), expected);
    }

    @Test
    public void testIgnoreHashCodeMethod() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MagicNumberCheck.class);
        checkConfig.addAttribute("ignoreHashCodeMethod", "true");
        checkConfig.addAttribute("ignoreAnnotation", "true");
        final String[] expected = {
            "47:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "48:32: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "49:27: " + getCheckMessage(MSG_KEY, "3"),
            "49:31: " + getCheckMessage(MSG_KEY, "4"),
            "51:29: " + getCheckMessage(MSG_KEY, "3"),
            "53:23: " + getCheckMessage(MSG_KEY, "3"),
            "54:26: " + getCheckMessage(MSG_KEY, "1.5"),
            "56:22: " + getCheckMessage(MSG_KEY, "3"),
            "56:29: " + getCheckMessage(MSG_KEY, "5"),
            "56:37: " + getCheckMessage(MSG_KEY, "3"),
            "60:26: " + getCheckMessage(MSG_KEY, "3"),
            "61:39: " + getCheckMessage(MSG_KEY, "3"),
            "66:25: " + getCheckMessage(MSG_KEY, "010"),
            "67:25: " + getCheckMessage(MSG_KEY, "011"),
            "69:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "70:30: " + getCheckMessage(MSG_KEY, "011l"),
            "74:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "75:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "77:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "78:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "91:28: " + getCheckMessage(MSG_KEY, "3"),
            "98:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "106:30: " + getCheckMessage(MSG_KEY, "+3"),
            "107:29: " + getCheckMessage(MSG_KEY, "-2"),
            "108:35: " + getCheckMessage(MSG_KEY, "+3.5"),
            "109:36: " + getCheckMessage(MSG_KEY, "-2.5"),
            "117:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "118:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "121:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "122:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "137:20: " + getCheckMessage(MSG_KEY, "378"),
            "171:16: " + getCheckMessage(MSG_KEY, "42"),
            "176:16: " + getCheckMessage(MSG_KEY, "13"),
            "180:15: " + getCheckMessage(MSG_KEY, "21"),
            "184:15: " + getCheckMessage(MSG_KEY, "37"),
            "188:15: " + getCheckMessage(MSG_KEY, "101"),
            "220:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "221:14: " + getCheckMessage(MSG_KEY,
                "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "231:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verify(checkConfig, getPath("InputMagicNumber_6.java"), expected);
    }

    @Test
    public void testIgnoreFieldDeclaration()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MagicNumberCheck.class);
        checkConfig.addAttribute("ignoreFieldDeclaration", "true");
        final String[] expected = {
            "46:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "47:32: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "48:27: " + getCheckMessage(MSG_KEY, "3"),
            "48:31: " + getCheckMessage(MSG_KEY, "4"),
            "50:29: " + getCheckMessage(MSG_KEY, "3"),
            "52:23: " + getCheckMessage(MSG_KEY, "3"),
            "53:26: " + getCheckMessage(MSG_KEY, "1.5"),
            "55:22: " + getCheckMessage(MSG_KEY, "3"),
            "55:29: " + getCheckMessage(MSG_KEY, "5"),
            "55:37: " + getCheckMessage(MSG_KEY, "3"),
            "59:26: " + getCheckMessage(MSG_KEY, "3"),
            "60:39: " + getCheckMessage(MSG_KEY, "3"),
            "65:25: " + getCheckMessage(MSG_KEY, "010"),
            "66:25: " + getCheckMessage(MSG_KEY, "011"),
            "68:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "69:30: " + getCheckMessage(MSG_KEY, "011l"),
            "73:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "74:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "76:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "77:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "136:20: " + getCheckMessage(MSG_KEY, "378"),
            "165:16: " + getCheckMessage(MSG_KEY, "31"),
            "170:16: " + getCheckMessage(MSG_KEY, "42"),
            "175:16: " + getCheckMessage(MSG_KEY, "13"),
            "179:15: " + getCheckMessage(MSG_KEY, "21"),
            "183:15: " + getCheckMessage(MSG_KEY, "37"),
            "187:15: " + getCheckMessage(MSG_KEY, "101"),
            "190:42: " + getCheckMessage(MSG_KEY, "42"),
            "194:48: " + getCheckMessage(MSG_KEY, "43"),
            "198:42: " + getCheckMessage(MSG_KEY, "-44"),
            "202:48: " + getCheckMessage(MSG_KEY, "-45"),
            "230:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verify(checkConfig, getPath("InputMagicNumber_7.java"), expected);
    }

    @Test
    public void testWaiverParentToken()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MagicNumberCheck.class);
        checkConfig.addAttribute("constantWaiverParentToken", "ASSIGN, ARRAY_INIT,"
                + " EXPR, UNARY_PLUS, UNARY_MINUS, TYPECAST, ELIST, STAR, DIV, PLUS, MINUS");
        final String[] expected = {
            "47:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "48:32: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "49:27: " + getCheckMessage(MSG_KEY, "3"),
            "49:31: " + getCheckMessage(MSG_KEY, "4"),
            "51:29: " + getCheckMessage(MSG_KEY, "3"),
            "53:23: " + getCheckMessage(MSG_KEY, "3"),
            "54:26: " + getCheckMessage(MSG_KEY, "1.5"),
            "56:22: " + getCheckMessage(MSG_KEY, "3"),
            "56:29: " + getCheckMessage(MSG_KEY, "5"),
            "56:37: " + getCheckMessage(MSG_KEY, "3"),
            "60:26: " + getCheckMessage(MSG_KEY, "3"),
            "61:39: " + getCheckMessage(MSG_KEY, "3"),
            "66:25: " + getCheckMessage(MSG_KEY, "010"),
            "67:25: " + getCheckMessage(MSG_KEY, "011"),
            "69:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "70:30: " + getCheckMessage(MSG_KEY, "011l"),
            "74:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "75:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "77:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "78:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "91:28: " + getCheckMessage(MSG_KEY, "3"),
            "98:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "106:30: " + getCheckMessage(MSG_KEY, "+3"),
            "107:29: " + getCheckMessage(MSG_KEY, "-2"),
            "108:35: " + getCheckMessage(MSG_KEY, "+3.5"),
            "109:36: " + getCheckMessage(MSG_KEY, "-2.5"),
            "117:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "118:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "121:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "122:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "137:20: " + getCheckMessage(MSG_KEY, "378"),
            "146:52: " + getCheckMessage(MSG_KEY, "27"),
            "149:53: " + getCheckMessage(MSG_KEY, "3"),
            "149:56: " + getCheckMessage(MSG_KEY, "3"),
            "149:59: " + getCheckMessage(MSG_KEY, "3"),
            "149:62: " + getCheckMessage(MSG_KEY, "3"),
            "166:16: " + getCheckMessage(MSG_KEY, "31"),
            "171:16: " + getCheckMessage(MSG_KEY, "42"),
            "176:16: " + getCheckMessage(MSG_KEY, "13"),
            "180:15: " + getCheckMessage(MSG_KEY, "21"),
            "184:15: " + getCheckMessage(MSG_KEY, "37"),
            "188:15: " + getCheckMessage(MSG_KEY, "101"),
            "191:42: " + getCheckMessage(MSG_KEY, "42"),
            "195:48: " + getCheckMessage(MSG_KEY, "43"),
            "199:42: " + getCheckMessage(MSG_KEY, "-44"),
            "203:48: " + getCheckMessage(MSG_KEY, "-45"),
            "215:63: " + getCheckMessage(MSG_KEY, "62"),
            "220:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "221:14: " + getCheckMessage(MSG_KEY,
                "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "231:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verify(checkConfig, getPath("InputMagicNumber.java"), expected);
    }

    @Test
    public void testMagicNumberRecordsDefault()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MagicNumberCheck.class);
        final String[] expected = {
            "14:11: " + getCheckMessage(MSG_KEY, "6"),
            "16:36: " + getCheckMessage(MSG_KEY, "7"),
            "20:29: " + getCheckMessage(MSG_KEY, "8"),
            "24:29: " + getCheckMessage(MSG_KEY, "8"),
            "28:20: " + getCheckMessage(MSG_KEY, "10"),

        };
        verify(checkConfig,
                getNonCompilablePath("InputMagicNumberRecordsDefault.java"), expected);
    }

    @Test
    public void testMagicNumberIgnoreFieldDeclarationRecords()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MagicNumberCheck.class);
        checkConfig.addAttribute("ignoreFieldDeclaration", "true");
        final String[] expected = {
            "14:11: " + getCheckMessage(MSG_KEY, "6"),
            "20:29: " + getCheckMessage(MSG_KEY, "8"),
            "24:29: " + getCheckMessage(MSG_KEY, "8"),
            "28:20: " + getCheckMessage(MSG_KEY, "10"),
        };
        verify(checkConfig,
                getNonCompilablePath("InputMagicNumberIgnoreFieldDeclarationRecords.java"),
                expected);
    }

    @Test
    public void testIgnoreInAnnotationElementDefault() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MagicNumberCheck.class);
        checkConfig.addAttribute("ignoreAnnotationElementDefaults", "false");
        final String[] expected = {
            "12:29: " + getCheckMessage(MSG_KEY, "10"),
            "13:33: " + getCheckMessage(MSG_KEY, "11"),
        };
        verify(checkConfig, getPath("InputMagicNumberAnnotationElement.java"), expected);
    }
}
