////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class MagicNumberCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "coding" + File.separator + filename);
    }

    @Test
    public void testDefault()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MagicNumberCheck.class);
        final String[] expected = {
            "41:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "42:32: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "43:27: " + getCheckMessage(MSG_KEY, "3"),
            "43:31: " + getCheckMessage(MSG_KEY, "4"),
            "45:29: " + getCheckMessage(MSG_KEY, "3"),
            "47:23: " + getCheckMessage(MSG_KEY, "3"),
            "48:26: " + getCheckMessage(MSG_KEY, "1.5"),
            "50:22: " + getCheckMessage(MSG_KEY, "3"),
            "50:29: " + getCheckMessage(MSG_KEY, "5"),
            "50:37: " + getCheckMessage(MSG_KEY, "3"),
            "54:26: " + getCheckMessage(MSG_KEY, "3"),
            "55:39: " + getCheckMessage(MSG_KEY, "3"),
            "60:25: " + getCheckMessage(MSG_KEY, "010"),
            "61:25: " + getCheckMessage(MSG_KEY, "011"),
            "63:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "64:30: " + getCheckMessage(MSG_KEY, "011l"),
            "68:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "69:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "71:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "72:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "85:28: " + getCheckMessage(MSG_KEY, "3"),
            "92:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "100:30: " + getCheckMessage(MSG_KEY, "+3"),
            "101:29: " + getCheckMessage(MSG_KEY, "-2"),
            "102:35: " + getCheckMessage(MSG_KEY, "+3.5"),
            "103:36: " + getCheckMessage(MSG_KEY, "-2.5"),
            "111:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "112:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "115:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "116:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "131:20: " + getCheckMessage(MSG_KEY, "378"),
            "160:16: " + getCheckMessage(MSG_KEY, "31"),
            "165:16: " + getCheckMessage(MSG_KEY, "42"),
            "170:16: " + getCheckMessage(MSG_KEY, "13"),
            "174:15: " + getCheckMessage(MSG_KEY, "21"),
            "178:15: " + getCheckMessage(MSG_KEY, "37"),
            "182:15: " + getCheckMessage(MSG_KEY, "101"),
            "185:31: " + getCheckMessage(MSG_KEY, "42"),
            "189:37: " + getCheckMessage(MSG_KEY, "43"),
            "193:31: " + getCheckMessage(MSG_KEY, "-44"),
            "197:37: " + getCheckMessage(MSG_KEY, "-45"),
        };
        verify(checkConfig, getPath("InputMagicNumber.java"), expected);
    }

    @Test
    public void testIgnoreSome()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MagicNumberCheck.class);
        checkConfig.addAttribute("ignoreNumbers", "0, 1, 3.0, 8, 16, 3000");
        checkConfig.addAttribute("ignoreAnnotation", "true");
        final String[] expected = {
            "22:25: " + getCheckMessage(MSG_KEY, "2"),
            "28:35: " + getCheckMessage(MSG_KEY, "2"),
            "30:24: " + getCheckMessage(MSG_KEY, "2"),
            "32:29: " + getCheckMessage(MSG_KEY, "2.0"),
            "34:29: " + getCheckMessage(MSG_KEY, "2"),
            "36:17: " + getCheckMessage(MSG_KEY, "2"),
            "38:19: " + getCheckMessage(MSG_KEY, "2.0"),
            "42:32: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "43:31: " + getCheckMessage(MSG_KEY, "4"),
            "48:26: " + getCheckMessage(MSG_KEY, "1.5"),
            "50:29: " + getCheckMessage(MSG_KEY, "5"),
            "61:25: " + getCheckMessage(MSG_KEY, "011"),
            "64:30: " + getCheckMessage(MSG_KEY, "011l"),
            "69:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "72:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "92:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "101:29: " + getCheckMessage(MSG_KEY, "-2"),
            "102:35: " + getCheckMessage(MSG_KEY, "+3.5"),
            "103:36: " + getCheckMessage(MSG_KEY, "-2.5"),
            "109:34: " + getCheckMessage(MSG_KEY, "0xffffffff"),
            "110:36: " + getCheckMessage(MSG_KEY, "0xffffffffffffffffL"),
            "111:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "112:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "113:36: " + getCheckMessage(MSG_KEY, "037777777777"),
            "114:38: " + getCheckMessage(MSG_KEY, "01777777777777777777777L"),
            "115:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "116:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "131:20: " + getCheckMessage(MSG_KEY, "378"),
            "160:16: " + getCheckMessage(MSG_KEY, "31"),
            "165:16: " + getCheckMessage(MSG_KEY, "42"),
            "170:16: " + getCheckMessage(MSG_KEY, "13"),
            "174:15: " + getCheckMessage(MSG_KEY, "21"),
            "178:15: " + getCheckMessage(MSG_KEY, "37"),
            "182:15: " + getCheckMessage(MSG_KEY, "101"),
        };
        verify(checkConfig, getPath("InputMagicNumber.java"), expected);
    }

    @Test
    public void testIgnoreNone()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MagicNumberCheck.class);
        checkConfig.addAttribute("ignoreNumbers", "");
        checkConfig.addAttribute("ignoreAnnotation", "true");
        final String[] expected = {
            "21:24: " + getCheckMessage(MSG_KEY, "1"),
            "22:25: " + getCheckMessage(MSG_KEY, "2"),
            "23:26: " + getCheckMessage(MSG_KEY, "0L"),
            "24:26: " + getCheckMessage(MSG_KEY, "0l"),
            "25:30: " + getCheckMessage(MSG_KEY, "0D"),
            "26:30: " + getCheckMessage(MSG_KEY, "0d"),
            "28:35: " + getCheckMessage(MSG_KEY, "2"),
            "30:20: " + getCheckMessage(MSG_KEY, "1"),
            "30:24: " + getCheckMessage(MSG_KEY, "2"),
            "31:21: " + getCheckMessage(MSG_KEY, "1"),
            "32:23: " + getCheckMessage(MSG_KEY, "1.0"),
            "32:29: " + getCheckMessage(MSG_KEY, "2.0"),
            "34:22: " + getCheckMessage(MSG_KEY, "0"),
            "34:29: " + getCheckMessage(MSG_KEY, "2"),
            "36:13: " + getCheckMessage(MSG_KEY, "1"),
            "36:17: " + getCheckMessage(MSG_KEY, "2"),
            "38:13: " + getCheckMessage(MSG_KEY, "1.0"),
            "38:19: " + getCheckMessage(MSG_KEY, "2.0"),
            "41:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "42:32: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "43:27: " + getCheckMessage(MSG_KEY, "3"),
            "43:31: " + getCheckMessage(MSG_KEY, "4"),
            "45:29: " + getCheckMessage(MSG_KEY, "3"),
            "47:23: " + getCheckMessage(MSG_KEY, "3"),
            "48:26: " + getCheckMessage(MSG_KEY, "1.5"),
            "50:22: " + getCheckMessage(MSG_KEY, "3"),
            "50:29: " + getCheckMessage(MSG_KEY, "5"),
            "50:37: " + getCheckMessage(MSG_KEY, "3"),
            "54:26: " + getCheckMessage(MSG_KEY, "3"),
            "55:39: " + getCheckMessage(MSG_KEY, "3"),
            "59:25: " + getCheckMessage(MSG_KEY, "00"),
            "60:25: " + getCheckMessage(MSG_KEY, "010"),
            "61:25: " + getCheckMessage(MSG_KEY, "011"),
            "63:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "64:30: " + getCheckMessage(MSG_KEY, "011l"),
            "67:23: " + getCheckMessage(MSG_KEY, "0x0"),
            "68:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "69:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "70:28: " + getCheckMessage(MSG_KEY, "0x0L"),
            "71:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "72:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "85:28: " + getCheckMessage(MSG_KEY, "3"),
            "92:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "100:30: " + getCheckMessage(MSG_KEY, "+3"),
            "101:29: " + getCheckMessage(MSG_KEY, "-2"),
            "102:35: " + getCheckMessage(MSG_KEY, "+3.5"),
            "103:36: " + getCheckMessage(MSG_KEY, "-2.5"),
            "109:34: " + getCheckMessage(MSG_KEY, "0xffffffff"),
            "110:36: " + getCheckMessage(MSG_KEY, "0xffffffffffffffffL"),
            "111:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "112:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "113:36: " + getCheckMessage(MSG_KEY, "037777777777"),
            "114:38: " + getCheckMessage(MSG_KEY, "01777777777777777777777L"),
            "115:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "116:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "131:20: " + getCheckMessage(MSG_KEY, "378"),
            "160:16: " + getCheckMessage(MSG_KEY, "31"),
            "165:16: " + getCheckMessage(MSG_KEY, "42"),
            "170:16: " + getCheckMessage(MSG_KEY, "13"),
            "174:15: " + getCheckMessage(MSG_KEY, "21"),
            "178:15: " + getCheckMessage(MSG_KEY, "37"),
            "182:15: " + getCheckMessage(MSG_KEY, "101"),
        };
        verify(checkConfig, getPath("InputMagicNumber.java"), expected);
    }

    @Test
    public void testIntegersOnly()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MagicNumberCheck.class);
        checkConfig.addAttribute("tokens", "NUM_INT, NUM_LONG");
        checkConfig.addAttribute("ignoreAnnotation", "true");
        final String[] expected = {
            "41:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "43:27: " + getCheckMessage(MSG_KEY, "3"),
            "43:31: " + getCheckMessage(MSG_KEY, "4"),
            "45:29: " + getCheckMessage(MSG_KEY, "3"),
            "47:23: " + getCheckMessage(MSG_KEY, "3"),
            "50:22: " + getCheckMessage(MSG_KEY, "3"),
            "50:29: " + getCheckMessage(MSG_KEY, "5"),
            "50:37: " + getCheckMessage(MSG_KEY, "3"),
            "54:26: " + getCheckMessage(MSG_KEY, "3"),
            "55:39: " + getCheckMessage(MSG_KEY, "3"),
            "60:25: " + getCheckMessage(MSG_KEY, "010"),
            "61:25: " + getCheckMessage(MSG_KEY, "011"),
            "63:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "64:30: " + getCheckMessage(MSG_KEY, "011l"),
            "68:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "69:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "71:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "72:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "85:28: " + getCheckMessage(MSG_KEY, "3"),
            "92:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "100:30: " + getCheckMessage(MSG_KEY, "+3"),
            "101:29: " + getCheckMessage(MSG_KEY, "-2"),
            "111:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "112:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "115:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "116:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "131:20: " + getCheckMessage(MSG_KEY, "378"),
            "160:16: " + getCheckMessage(MSG_KEY, "31"),
            "165:16: " + getCheckMessage(MSG_KEY, "42"),
            "170:16: " + getCheckMessage(MSG_KEY, "13"),
            "174:15: " + getCheckMessage(MSG_KEY, "21"),
            "178:15: " + getCheckMessage(MSG_KEY, "37"),
            "182:15: " + getCheckMessage(MSG_KEY, "101"),
        };
        verify(checkConfig, getPath("InputMagicNumber.java"), expected);
    }

    @Test
    public void testIgnoreNegativeOctalHex() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MagicNumberCheck.class);
        checkConfig.addAttribute("ignoreNumbers", "-9223372036854775808, -2147483648, -1, 0, 1, 2");
        checkConfig.addAttribute("tokens", "NUM_INT, NUM_LONG");
        checkConfig.addAttribute("ignoreAnnotation", "true");
        final String[] expected = {
            "41:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "43:27: " + getCheckMessage(MSG_KEY, "3"),
            "43:31: " + getCheckMessage(MSG_KEY, "4"),
            "45:29: " + getCheckMessage(MSG_KEY, "3"),
            "47:23: " + getCheckMessage(MSG_KEY, "3"),
            "50:22: " + getCheckMessage(MSG_KEY, "3"),
            "50:29: " + getCheckMessage(MSG_KEY, "5"),
            "50:37: " + getCheckMessage(MSG_KEY, "3"),
            "54:26: " + getCheckMessage(MSG_KEY, "3"),
            "55:39: " + getCheckMessage(MSG_KEY, "3"),
            "60:25: " + getCheckMessage(MSG_KEY, "010"),
            "61:25: " + getCheckMessage(MSG_KEY, "011"),
            "63:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "64:30: " + getCheckMessage(MSG_KEY, "011l"),
            "68:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "69:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "71:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "72:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "85:28: " + getCheckMessage(MSG_KEY, "3"),
            "92:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "100:30: " + getCheckMessage(MSG_KEY, "+3"),
            "101:29: " + getCheckMessage(MSG_KEY, "-2"),
            "131:20: " + getCheckMessage(MSG_KEY, "378"),
            "160:16: " + getCheckMessage(MSG_KEY, "31"),
            "165:16: " + getCheckMessage(MSG_KEY, "42"),
            "170:16: " + getCheckMessage(MSG_KEY, "13"),
            "174:15: " + getCheckMessage(MSG_KEY, "21"),
            "178:15: " + getCheckMessage(MSG_KEY, "37"),
            "182:15: " + getCheckMessage(MSG_KEY, "101"),
        };
        verify(checkConfig, getPath("InputMagicNumber.java"), expected);
    }

    @Test
    public void testIgnoreHashCodeMethod() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MagicNumberCheck.class);
        checkConfig.addAttribute("ignoreHashCodeMethod", "true");
        checkConfig.addAttribute("ignoreAnnotation", "true");
        final String[] expected = {
            "41:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "42:32: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "43:27: " + getCheckMessage(MSG_KEY, "3"),
            "43:31: " + getCheckMessage(MSG_KEY, "4"),
            "45:29: " + getCheckMessage(MSG_KEY, "3"),
            "47:23: " + getCheckMessage(MSG_KEY, "3"),
            "48:26: " + getCheckMessage(MSG_KEY, "1.5"),
            "50:22: " + getCheckMessage(MSG_KEY, "3"),
            "50:29: " + getCheckMessage(MSG_KEY, "5"),
            "50:37: " + getCheckMessage(MSG_KEY, "3"),
            "54:26: " + getCheckMessage(MSG_KEY, "3"),
            "55:39: " + getCheckMessage(MSG_KEY, "3"),
            "60:25: " + getCheckMessage(MSG_KEY, "010"),
            "61:25: " + getCheckMessage(MSG_KEY, "011"),
            "63:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "64:30: " + getCheckMessage(MSG_KEY, "011l"),
            "68:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "69:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "71:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "72:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "85:28: " + getCheckMessage(MSG_KEY, "3"),
            "92:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "100:30: " + getCheckMessage(MSG_KEY, "+3"),
            "101:29: " + getCheckMessage(MSG_KEY, "-2"),
            "102:35: " + getCheckMessage(MSG_KEY, "+3.5"),
            "103:36: " + getCheckMessage(MSG_KEY, "-2.5"),
            "111:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "112:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "115:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "116:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "131:20: " + getCheckMessage(MSG_KEY, "378"),
            "165:16: " + getCheckMessage(MSG_KEY, "42"),
            "170:16: " + getCheckMessage(MSG_KEY, "13"),
            "174:15: " + getCheckMessage(MSG_KEY, "21"),
            "178:15: " + getCheckMessage(MSG_KEY, "37"),
            "182:15: " + getCheckMessage(MSG_KEY, "101"),
        };
        verify(checkConfig, getPath("InputMagicNumber.java"), expected);
    }

    @Test
    public void testIgnoreFieldDeclaration()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MagicNumberCheck.class);
        checkConfig.addAttribute("ignoreFieldDeclaration", "true");
        final String[] expected = {
            "41:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "42:32: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "43:27: " + getCheckMessage(MSG_KEY, "3"),
            "43:31: " + getCheckMessage(MSG_KEY, "4"),
            "45:29: " + getCheckMessage(MSG_KEY, "3"),
            "47:23: " + getCheckMessage(MSG_KEY, "3"),
            "48:26: " + getCheckMessage(MSG_KEY, "1.5"),
            "50:22: " + getCheckMessage(MSG_KEY, "3"),
            "50:29: " + getCheckMessage(MSG_KEY, "5"),
            "50:37: " + getCheckMessage(MSG_KEY, "3"),
            "54:26: " + getCheckMessage(MSG_KEY, "3"),
            "55:39: " + getCheckMessage(MSG_KEY, "3"),
            "60:25: " + getCheckMessage(MSG_KEY, "010"),
            "61:25: " + getCheckMessage(MSG_KEY, "011"),
            "63:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "64:30: " + getCheckMessage(MSG_KEY, "011l"),
            "68:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "69:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "71:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "72:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "131:20: " + getCheckMessage(MSG_KEY, "378"),
            "160:16: " + getCheckMessage(MSG_KEY, "31"),
            "165:16: " + getCheckMessage(MSG_KEY, "42"),
            "170:16: " + getCheckMessage(MSG_KEY, "13"),
            "174:15: " + getCheckMessage(MSG_KEY, "21"),
            "178:15: " + getCheckMessage(MSG_KEY, "37"),
            "182:15: " + getCheckMessage(MSG_KEY, "101"),
            "185:31: " + getCheckMessage(MSG_KEY, "42"),
            "189:37: " + getCheckMessage(MSG_KEY, "43"),
            "193:31: " + getCheckMessage(MSG_KEY, "-44"),
            "197:37: " + getCheckMessage(MSG_KEY, "-45"),
        };
        verify(checkConfig, getPath("InputMagicNumber.java"), expected);
    }

    @Test
    public void testWaiverParentToken()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MagicNumberCheck.class);
        checkConfig.addAttribute("constantWaiverParentToken", "ASSIGN, ARRAY_INIT,"
                + " EXPR, UNARY_PLUS, UNARY_MINUS, TYPECAST, ELIST, STAR, DIV, PLUS, MINUS");
        final String[] expected = {
            "41:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "42:32: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "43:27: " + getCheckMessage(MSG_KEY, "3"),
            "43:31: " + getCheckMessage(MSG_KEY, "4"),
            "45:29: " + getCheckMessage(MSG_KEY, "3"),
            "47:23: " + getCheckMessage(MSG_KEY, "3"),
            "48:26: " + getCheckMessage(MSG_KEY, "1.5"),
            "50:22: " + getCheckMessage(MSG_KEY, "3"),
            "50:29: " + getCheckMessage(MSG_KEY, "5"),
            "50:37: " + getCheckMessage(MSG_KEY, "3"),
            "54:26: " + getCheckMessage(MSG_KEY, "3"),
            "55:39: " + getCheckMessage(MSG_KEY, "3"),
            "60:25: " + getCheckMessage(MSG_KEY, "010"),
            "61:25: " + getCheckMessage(MSG_KEY, "011"),
            "63:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "64:30: " + getCheckMessage(MSG_KEY, "011l"),
            "68:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "69:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "71:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "72:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "85:28: " + getCheckMessage(MSG_KEY, "3"),
            "92:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "100:30: " + getCheckMessage(MSG_KEY, "+3"),
            "101:29: " + getCheckMessage(MSG_KEY, "-2"),
            "102:35: " + getCheckMessage(MSG_KEY, "+3.5"),
            "103:36: " + getCheckMessage(MSG_KEY, "-2.5"),
            "111:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "112:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "115:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "116:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "131:20: " + getCheckMessage(MSG_KEY, "378"),
            "140:52: " + getCheckMessage(MSG_KEY, "27"),
            "143:53: " + getCheckMessage(MSG_KEY, "3"),
            "143:56: " + getCheckMessage(MSG_KEY, "3"),
            "143:59: " + getCheckMessage(MSG_KEY, "3"),
            "143:62: " + getCheckMessage(MSG_KEY, "3"),
            "160:16: " + getCheckMessage(MSG_KEY, "31"),
            "165:16: " + getCheckMessage(MSG_KEY, "42"),
            "170:16: " + getCheckMessage(MSG_KEY, "13"),
            "174:15: " + getCheckMessage(MSG_KEY, "21"),
            "178:15: " + getCheckMessage(MSG_KEY, "37"),
            "182:15: " + getCheckMessage(MSG_KEY, "101"),
            "185:31: " + getCheckMessage(MSG_KEY, "42"),
            "189:37: " + getCheckMessage(MSG_KEY, "43"),
            "193:31: " + getCheckMessage(MSG_KEY, "-44"),
            "197:37: " + getCheckMessage(MSG_KEY, "-45"),
            "209:63: " + getCheckMessage(MSG_KEY, "62"),
        };
        verify(checkConfig, getPath("InputMagicNumber.java"), expected);
    }
}
