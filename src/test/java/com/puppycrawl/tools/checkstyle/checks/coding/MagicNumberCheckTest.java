///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
                getPath("InputMagicNumberLocalVariables.java"), expected);
    }

    @Test
    public void testLocalVariables2()
            throws Exception {
        final String[] expected = {
            "27:17: " + getCheckMessage(MSG_KEY, "8"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberLocalVariables2.java"), expected);
    }

    @Test
    public void testDefault1()
            throws Exception {
        final String[] expected = {
            "56:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "57:32: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "58:27: " + getCheckMessage(MSG_KEY, "3"),
            "58:31: " + getCheckMessage(MSG_KEY, "4"),
            "60:29: " + getCheckMessage(MSG_KEY, "3"),
            "62:23: " + getCheckMessage(MSG_KEY, "3"),
            "63:26: " + getCheckMessage(MSG_KEY, "1.5"),
            "65:22: " + getCheckMessage(MSG_KEY, "3"),
            "65:29: " + getCheckMessage(MSG_KEY, "5"),
            "65:37: " + getCheckMessage(MSG_KEY, "3"),
            "69:26: " + getCheckMessage(MSG_KEY, "3"),
            "70:39: " + getCheckMessage(MSG_KEY, "3"),
            "74:25: " + getCheckMessage(MSG_KEY, "010"),
            "75:25: " + getCheckMessage(MSG_KEY, "011"),
            "77:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "78:30: " + getCheckMessage(MSG_KEY, "011l"),
            "81:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "82:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "84:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "85:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "98:28: " + getCheckMessage(MSG_KEY, "3"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberDefault1.java"), expected);
    }

    @Test
    public void testDefault2()
            throws Exception {
        final String[] expected = {
            "23:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "30:30: " + getCheckMessage(MSG_KEY, "+3"),
            "31:29: " + getCheckMessage(MSG_KEY, "-2"),
            "32:35: " + getCheckMessage(MSG_KEY, "+3.5"),
            "33:36: " + getCheckMessage(MSG_KEY, "-2.5"),
            "40:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "41:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "44:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "45:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "52:20: " + getCheckMessage(MSG_KEY, "378"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberDefault2.java"), expected);
    }

    @Test
    public void testDefault3()
            throws Exception {
        final String[] expected = {
            "22:16: " + getCheckMessage(MSG_KEY, "31"),
            "27:16: " + getCheckMessage(MSG_KEY, "42"),
            "32:16: " + getCheckMessage(MSG_KEY, "13"),
            "36:15: " + getCheckMessage(MSG_KEY, "21"),
            "40:15: " + getCheckMessage(MSG_KEY, "37"),
            "44:15: " + getCheckMessage(MSG_KEY, "101"),
            "47:42: " + getCheckMessage(MSG_KEY, "42"),
            "51:48: " + getCheckMessage(MSG_KEY, "43"),
            "55:42: " + getCheckMessage(MSG_KEY, "-44"),
            "59:48: " + getCheckMessage(MSG_KEY, "-45"),
            "76:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "77:14: " + getCheckMessage(MSG_KEY,
                    "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "87:21: " + getCheckMessage(MSG_KEY, "122"),

        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberDefault3.java"), expected);
    }

    @Test
    public void testIgnoreSome1()
            throws Exception {
        final String[] expected = {
            "38:25: " + getCheckMessage(MSG_KEY, "2"),
            "44:35: " + getCheckMessage(MSG_KEY, "2"),
            "46:24: " + getCheckMessage(MSG_KEY, "2"),
            "48:29: " + getCheckMessage(MSG_KEY, "2.0"),
            "50:29: " + getCheckMessage(MSG_KEY, "2"),
            "52:17: " + getCheckMessage(MSG_KEY, "2"),
            "54:19: " + getCheckMessage(MSG_KEY, "2.0"),
            "58:32: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "59:31: " + getCheckMessage(MSG_KEY, "4"),
            "64:26: " + getCheckMessage(MSG_KEY, "1.5"),
            "66:29: " + getCheckMessage(MSG_KEY, "5"),
            "77:25: " + getCheckMessage(MSG_KEY, "011"),
            "80:30: " + getCheckMessage(MSG_KEY, "011l"),
            "85:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "88:29: " + getCheckMessage(MSG_KEY, "0X11l"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIgnoreSome1.java"), expected);
    }

    @Test
    public void testIgnoreSome2()
            throws Exception {
        final String[] expected = {
            "23:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "32:29: " + getCheckMessage(MSG_KEY, "-2"),
            "33:35: " + getCheckMessage(MSG_KEY, "+3.5"),
            "34:36: " + getCheckMessage(MSG_KEY, "-2.5"),
            "39:34: " + getCheckMessage(MSG_KEY, "0xffffffff"),
            "40:36: " + getCheckMessage(MSG_KEY, "0xffffffffffffffffL"),
            "41:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "42:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "43:36: " + getCheckMessage(MSG_KEY, "037777777777"),
            "44:38: " + getCheckMessage(MSG_KEY, "01777777777777777777777L"),
            "45:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "46:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "51:20: " + getCheckMessage(MSG_KEY, "378"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIgnoreSome2.java"), expected);
    }

    @Test
    public void testIgnoreSome3()
            throws Exception {
        final String[] expected = {
            "23:16: " + getCheckMessage(MSG_KEY, "31"),
            "28:16: " + getCheckMessage(MSG_KEY, "42"),
            "33:16: " + getCheckMessage(MSG_KEY, "13"),
            "37:15: " + getCheckMessage(MSG_KEY, "21"),
            "41:15: " + getCheckMessage(MSG_KEY, "37"),
            "45:15: " + getCheckMessage(MSG_KEY, "101"),
            "77:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "78:14: " + getCheckMessage(MSG_KEY,
                    "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "88:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIgnoreSome3.java"), expected);
    }

    @Test
    public void testIgnoreNone1()
            throws Exception {
        final String[] expected = {
            "43:24: " + getCheckMessage(MSG_KEY, "1"),
            "44:25: " + getCheckMessage(MSG_KEY, "2"),
            "45:26: " + getCheckMessage(MSG_KEY, "0L"),
            "46:26: " + getCheckMessage(MSG_KEY, "0l"),
            "47:30: " + getCheckMessage(MSG_KEY, "0D"),
            "48:30: " + getCheckMessage(MSG_KEY, "0d"),
            "50:35: " + getCheckMessage(MSG_KEY, "2"),
            "52:20: " + getCheckMessage(MSG_KEY, "1"),
            "52:24: " + getCheckMessage(MSG_KEY, "2"),
            "53:21: " + getCheckMessage(MSG_KEY, "1"),
            "54:23: " + getCheckMessage(MSG_KEY, "1.0"),
            "54:29: " + getCheckMessage(MSG_KEY, "2.0"),
            "56:22: " + getCheckMessage(MSG_KEY, "0"),
            "56:29: " + getCheckMessage(MSG_KEY, "2"),
            "58:13: " + getCheckMessage(MSG_KEY, "1"),
            "58:17: " + getCheckMessage(MSG_KEY, "2"),
            "60:13: " + getCheckMessage(MSG_KEY, "1.0"),
            "60:19: " + getCheckMessage(MSG_KEY, "2.0"),
            "63:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "64:32: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "65:27: " + getCheckMessage(MSG_KEY, "3"),
            "65:31: " + getCheckMessage(MSG_KEY, "4"),
            "67:29: " + getCheckMessage(MSG_KEY, "3"),
            "69:23: " + getCheckMessage(MSG_KEY, "3"),
            "70:26: " + getCheckMessage(MSG_KEY, "1.5"),
            "72:22: " + getCheckMessage(MSG_KEY, "3"),
            "72:29: " + getCheckMessage(MSG_KEY, "5"),
            "72:37: " + getCheckMessage(MSG_KEY, "3"),
            "76:26: " + getCheckMessage(MSG_KEY, "3"),
            "77:39: " + getCheckMessage(MSG_KEY, "3"),
            "81:25: " + getCheckMessage(MSG_KEY, "00"),
            "82:25: " + getCheckMessage(MSG_KEY, "010"),
            "83:25: " + getCheckMessage(MSG_KEY, "011"),
            "85:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "86:30: " + getCheckMessage(MSG_KEY, "011l"),
            "89:23: " + getCheckMessage(MSG_KEY, "0x0"),
            "90:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "91:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "92:28: " + getCheckMessage(MSG_KEY, "0x0L"),
            "93:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "94:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "107:28: " + getCheckMessage(MSG_KEY, "3"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIgnoreNone1.java"), expected);
    }

    @Test
    public void testIgnoreNone2()
            throws Exception {
        final String[] expected = {
            "21:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "28:30: " + getCheckMessage(MSG_KEY, "+3"),
            "29:29: " + getCheckMessage(MSG_KEY, "-2"),
            "30:35: " + getCheckMessage(MSG_KEY, "+3.5"),
            "31:36: " + getCheckMessage(MSG_KEY, "-2.5"),
            "36:34: " + getCheckMessage(MSG_KEY, "0xffffffff"),
            "37:36: " + getCheckMessage(MSG_KEY, "0xffffffffffffffffL"),
            "38:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "39:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "40:36: " + getCheckMessage(MSG_KEY, "037777777777"),
            "41:38: " + getCheckMessage(MSG_KEY, "01777777777777777777777L"),
            "42:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "43:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "49:20: " + getCheckMessage(MSG_KEY, "378"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIgnoreNone2.java"), expected);
    }

    @Test
    public void testIgnoreNone3()
            throws Exception {
        final String[] expected = {
            "22:16: " + getCheckMessage(MSG_KEY, "31"),
            "27:16: " + getCheckMessage(MSG_KEY, "42"),
            "32:16: " + getCheckMessage(MSG_KEY, "13"),
            "36:15: " + getCheckMessage(MSG_KEY, "21"),
            "40:15: " + getCheckMessage(MSG_KEY, "37"),
            "44:15: " + getCheckMessage(MSG_KEY, "101"),
            "76:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "77:14: " + getCheckMessage(MSG_KEY,
                    "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "87:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIgnoreNone3.java"), expected);
    }

    @Test
    public void testIntegersOnly1()
            throws Exception {
        final String[] expected = {
            "57:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "59:27: " + getCheckMessage(MSG_KEY, "3"),
            "59:31: " + getCheckMessage(MSG_KEY, "4"),
            "61:29: " + getCheckMessage(MSG_KEY, "3"),
            "63:23: " + getCheckMessage(MSG_KEY, "3"),
            "66:22: " + getCheckMessage(MSG_KEY, "3"),
            "66:29: " + getCheckMessage(MSG_KEY, "5"),
            "66:37: " + getCheckMessage(MSG_KEY, "3"),
            "70:26: " + getCheckMessage(MSG_KEY, "3"),
            "71:39: " + getCheckMessage(MSG_KEY, "3"),
            "76:25: " + getCheckMessage(MSG_KEY, "010"),
            "77:25: " + getCheckMessage(MSG_KEY, "011"),
            "79:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "80:30: " + getCheckMessage(MSG_KEY, "011l"),
            "84:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "85:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "87:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "88:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "101:28: " + getCheckMessage(MSG_KEY, "3"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIntegersOnly1.java"), expected);
    }

    @Test
    public void testIntegersOnly2()
            throws Exception {
        final String[] expected = {
            "22:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "30:30: " + getCheckMessage(MSG_KEY, "+3"),
            "31:29: " + getCheckMessage(MSG_KEY, "-2"),
            "40:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "41:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "44:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "45:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "51:20: " + getCheckMessage(MSG_KEY, "378"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIntegersOnly2.java"), expected);
    }

    @Test
    public void testIntegersOnly3()
            throws Exception {
        final String[] expected = {
            "22:16: " + getCheckMessage(MSG_KEY, "31"),
            "27:16: " + getCheckMessage(MSG_KEY, "42"),
            "32:16: " + getCheckMessage(MSG_KEY, "13"),
            "36:15: " + getCheckMessage(MSG_KEY, "21"),
            "40:15: " + getCheckMessage(MSG_KEY, "37"),
            "44:15: " + getCheckMessage(MSG_KEY, "101"),
            "76:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "77:14: " + getCheckMessage(MSG_KEY,
                    "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "87:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIntegersOnly3.java"), expected);
    }

    @Test
    public void testIgnoreNegativeOctalHex1() throws Exception {
        final String[] expected = {
            "57:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "59:27: " + getCheckMessage(MSG_KEY, "3"),
            "59:31: " + getCheckMessage(MSG_KEY, "4"),
            "61:29: " + getCheckMessage(MSG_KEY, "3"),
            "63:23: " + getCheckMessage(MSG_KEY, "3"),
            "66:22: " + getCheckMessage(MSG_KEY, "3"),
            "66:29: " + getCheckMessage(MSG_KEY, "5"),
            "66:37: " + getCheckMessage(MSG_KEY, "3"),
            "70:26: " + getCheckMessage(MSG_KEY, "3"),
            "71:39: " + getCheckMessage(MSG_KEY, "3"),
            "76:25: " + getCheckMessage(MSG_KEY, "010"),
            "77:25: " + getCheckMessage(MSG_KEY, "011"),
            "79:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "80:30: " + getCheckMessage(MSG_KEY, "011l"),
            "84:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "85:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "87:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "88:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "101:28: " + getCheckMessage(MSG_KEY, "3"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIgnoreNegativeOctalHex1.java"), expected);
    }

    @Test
    public void testIgnoreNegativeOctalHex2() throws Exception {
        final String[] expected = {
            "22:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "30:30: " + getCheckMessage(MSG_KEY, "+3"),
            "52:20: " + getCheckMessage(MSG_KEY, "378"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIgnoreNegativeOctalHex2.java"), expected);
    }

    @Test
    public void testIgnoreNegativeOctalHex3() throws Exception {
        final String[] expected = {
            "22:16: " + getCheckMessage(MSG_KEY, "31"),
            "27:16: " + getCheckMessage(MSG_KEY, "42"),
            "32:16: " + getCheckMessage(MSG_KEY, "13"),
            "36:15: " + getCheckMessage(MSG_KEY, "21"),
            "40:15: " + getCheckMessage(MSG_KEY, "37"),
            "44:15: " + getCheckMessage(MSG_KEY, "101"),
            "77:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "78:14: " + getCheckMessage(MSG_KEY,
                    "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "88:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIgnoreNegativeOctalHex3.java"), expected);
    }

    @Test
    public void testIgnoreHashCodeMethod() throws Exception {
        final String[] expected = {
            "57:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "58:32: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "59:27: " + getCheckMessage(MSG_KEY, "3"),
            "59:31: " + getCheckMessage(MSG_KEY, "4"),
            "61:29: " + getCheckMessage(MSG_KEY, "3"),
            "63:23: " + getCheckMessage(MSG_KEY, "3"),
            "64:26: " + getCheckMessage(MSG_KEY, "1.5"),
            "66:22: " + getCheckMessage(MSG_KEY, "3"),
            "66:29: " + getCheckMessage(MSG_KEY, "5"),
            "66:37: " + getCheckMessage(MSG_KEY, "3"),
            "70:26: " + getCheckMessage(MSG_KEY, "3"),
            "71:39: " + getCheckMessage(MSG_KEY, "3"),
            "76:25: " + getCheckMessage(MSG_KEY, "010"),
            "77:25: " + getCheckMessage(MSG_KEY, "011"),
            "79:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "80:30: " + getCheckMessage(MSG_KEY, "011l"),
            "84:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "85:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "87:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "88:29: " + getCheckMessage(MSG_KEY, "0X11l"),
            "101:28: " + getCheckMessage(MSG_KEY, "3"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIgnoreHashCodeMethod1.java"), expected);
    }

    @Test
    public void testIgnoreHashCodeMethod2() throws Exception {
        final String[] expected = {
            "22:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "29:30: " + getCheckMessage(MSG_KEY, "+3"),
            "30:29: " + getCheckMessage(MSG_KEY, "-2"),
            "31:35: " + getCheckMessage(MSG_KEY, "+3.5"),
            "32:36: " + getCheckMessage(MSG_KEY, "-2.5"),
            "39:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "40:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "43:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "44:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "51:20: " + getCheckMessage(MSG_KEY, "378"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIgnoreHashCodeMethod2.java"), expected);
    }

    @Test
    public void testIgnoreHashCodeMethod3() throws Exception {
        final String[] expected = {
            "27:16: " + getCheckMessage(MSG_KEY, "42"),
            "32:16: " + getCheckMessage(MSG_KEY, "13"),
            "36:15: " + getCheckMessage(MSG_KEY, "21"),
            "40:15: " + getCheckMessage(MSG_KEY, "37"),
            "44:15: " + getCheckMessage(MSG_KEY, "101"),
            "77:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "78:14: " + getCheckMessage(MSG_KEY,
                    "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "88:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIgnoreHashCodeMethod3.java"), expected);
    }

    @Test
    public void testIgnoreFieldDeclaration1()
            throws Exception {
        final String[] expected = {
            "57:26: " + getCheckMessage(MSG_KEY, "3_000"),
            "58:32: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "59:27: " + getCheckMessage(MSG_KEY, "3"),
            "59:31: " + getCheckMessage(MSG_KEY, "4"),
            "61:29: " + getCheckMessage(MSG_KEY, "3"),
            "63:23: " + getCheckMessage(MSG_KEY, "3"),
            "64:26: " + getCheckMessage(MSG_KEY, "1.5"),
            "66:22: " + getCheckMessage(MSG_KEY, "3"),
            "66:29: " + getCheckMessage(MSG_KEY, "5"),
            "66:37: " + getCheckMessage(MSG_KEY, "3"),
            "70:26: " + getCheckMessage(MSG_KEY, "3"),
            "71:39: " + getCheckMessage(MSG_KEY, "3"),
            "76:25: " + getCheckMessage(MSG_KEY, "010"),
            "77:25: " + getCheckMessage(MSG_KEY, "011"),
            "79:30: " + getCheckMessage(MSG_KEY, "0_10L"),
            "80:30: " + getCheckMessage(MSG_KEY, "011l"),
            "84:24: " + getCheckMessage(MSG_KEY, "0x10"),
            "85:24: " + getCheckMessage(MSG_KEY, "0X011"),
            "87:29: " + getCheckMessage(MSG_KEY, "0x10L"),
            "88:29: " + getCheckMessage(MSG_KEY, "0X11l"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIgnoreFieldDeclaration1.java"), expected);
    }

    @Test
    public void testIgnoreFieldDeclaration2()
            throws Exception {
        final String[] expected = {
            "51:20: " + getCheckMessage(MSG_KEY, "378"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIgnoreFieldDeclaration2.java"), expected);
    }

    @Test
    public void testIgnoreFieldDeclaration3()
            throws Exception {
        final String[] expected = {
            "22:16: " + getCheckMessage(MSG_KEY, "31"),
            "27:16: " + getCheckMessage(MSG_KEY, "42"),
            "32:16: " + getCheckMessage(MSG_KEY, "13"),
            "36:15: " + getCheckMessage(MSG_KEY, "21"),
            "40:15: " + getCheckMessage(MSG_KEY, "37"),
            "44:15: " + getCheckMessage(MSG_KEY, "101"),
            "47:42: " + getCheckMessage(MSG_KEY, "42"),
            "51:48: " + getCheckMessage(MSG_KEY, "43"),
            "55:42: " + getCheckMessage(MSG_KEY, "-44"),
            "59:48: " + getCheckMessage(MSG_KEY, "-45"),
            "88:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIgnoreFieldDeclaration3.java"), expected);
    }

    @Test
    public void testIgnoreFieldDeclaration4()
            throws Exception {
        final String[] expected = {
            "32:27: " + getCheckMessage(MSG_KEY, "5"),
            "39:26: " + getCheckMessage(MSG_KEY, "86400_000"),
            "48:31: " + getCheckMessage(MSG_KEY, "5"),
            "49:32: " + getCheckMessage(MSG_KEY, "69"),
            "58:27: " + getCheckMessage(MSG_KEY, "5"),
            "65:26: " + getCheckMessage(MSG_KEY, "86400_000"),
            "74:31: " + getCheckMessage(MSG_KEY, "5"),
            "75:32: " + getCheckMessage(MSG_KEY, "69"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIgnoreFieldDeclaration4.java"), expected);
    }

    @Test
    public void testIgnoreFieldDeclaration5()
            throws Exception {
        final String[] expected = {
            "20:32: " + getCheckMessage(MSG_KEY, "22"),
            "20:37: " + getCheckMessage(MSG_KEY, "7.0"),
            "26:20: " + getCheckMessage(MSG_KEY, "10"),
            "27:20: " + getCheckMessage(MSG_KEY, "10"),
            "27:25: " + getCheckMessage(MSG_KEY, "20"),
            "31:30: " + getCheckMessage(MSG_KEY, "4"),
            "31:33: " + getCheckMessage(MSG_KEY, "5"),
            "31:36: " + getCheckMessage(MSG_KEY, "6"),
            "31:39: " + getCheckMessage(MSG_KEY, "7"),
            "38:26: " + getCheckMessage(MSG_KEY, "2023"),
            "38:32: " + getCheckMessage(MSG_KEY, "11"),
            "38:36: " + getCheckMessage(MSG_KEY, "11"),
            "38:40: " + getCheckMessage(MSG_KEY, "11"),
            "45:16: " + getCheckMessage(MSG_KEY, "11"),
            "45:20: " + getCheckMessage(MSG_KEY, "11"),
            "45:24: " + getCheckMessage(MSG_KEY, "11"),
            "51:41: " + getCheckMessage(MSG_KEY, "3"),
            "52:61: " + getCheckMessage(MSG_KEY, "4"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIgnoreFieldDeclaration5.java"), expected);
    }

    @Test
    public void testIgnoreFieldDeclaration6()
            throws Exception {
        final String[] expected = {
            "19:38: " + getCheckMessage(MSG_KEY, "10"),
            "20:46: " + getCheckMessage(MSG_KEY, "15"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIgnoreFieldDeclaration6.java"), expected);
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
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberWaiverParentToken1.java"), expected);
    }

    @Test
    public void testWaiverParentToken2()
            throws Exception {
        final String[] expected = {
            "20:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "28:30: " + getCheckMessage(MSG_KEY, "+3"),
            "29:29: " + getCheckMessage(MSG_KEY, "-2"),
            "30:35: " + getCheckMessage(MSG_KEY, "+3.5"),
            "31:36: " + getCheckMessage(MSG_KEY, "-2.5"),
            "38:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "39:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "42:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "43:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "49:20: " + getCheckMessage(MSG_KEY, "378"),
            "54:52: " + getCheckMessage(MSG_KEY, "27"),
            "57:57: " + getCheckMessage(MSG_KEY, "3"),
            "57:60: " + getCheckMessage(MSG_KEY, "3"),
            "57:63: " + getCheckMessage(MSG_KEY, "3"),
            "57:66: " + getCheckMessage(MSG_KEY, "3"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberWaiverParentToken2.java"), expected);
    }

    @Test
    public void testWaiverParentToken3()
            throws Exception {
        final String[] expected = {
            "20:16: " + getCheckMessage(MSG_KEY, "31"),
            "25:16: " + getCheckMessage(MSG_KEY, "42"),
            "30:16: " + getCheckMessage(MSG_KEY, "13"),
            "34:15: " + getCheckMessage(MSG_KEY, "21"),
            "38:15: " + getCheckMessage(MSG_KEY, "37"),
            "42:15: " + getCheckMessage(MSG_KEY, "101"),
            "45:42: " + getCheckMessage(MSG_KEY, "42"),
            "49:48: " + getCheckMessage(MSG_KEY, "43"),
            "53:42: " + getCheckMessage(MSG_KEY, "-44"),
            "57:48: " + getCheckMessage(MSG_KEY, "-45"),
            "70:54: " + getCheckMessage(MSG_KEY, "62"),
            "75:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "76:14: " + getCheckMessage(MSG_KEY,
                    "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "86:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberWaiverParentToken3.java"), expected);
    }

    @Test
    public void testMagicNumberRecordsDefault()
            throws Exception {
        final String[] expected = {
            "21:11: " + getCheckMessage(MSG_KEY, "6"),
            "23:36: " + getCheckMessage(MSG_KEY, "7"),
            "27:29: " + getCheckMessage(MSG_KEY, "8"),
            "31:29: " + getCheckMessage(MSG_KEY, "8"),
            "35:20: " + getCheckMessage(MSG_KEY, "10"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMagicNumberRecordsDefault.java"), expected);
    }

    @Test
    public void testMagicNumberIgnoreFieldDeclarationRecords()
            throws Exception {
        final String[] expected = {
            "21:11: " + getCheckMessage(MSG_KEY, "6"),
            "27:29: " + getCheckMessage(MSG_KEY, "8"),
            "31:29: " + getCheckMessage(MSG_KEY, "8"),
            "35:20: " + getCheckMessage(MSG_KEY, "10"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMagicNumberIgnoreFieldDeclarationRecords.java"),
                expected);
    }

    @Test
    public void testMagicNumberIgnoreFieldDeclarationWithAnnotation()
            throws Exception {
        final String[] expected = {
            "18:38: " + getCheckMessage(MSG_KEY, "3"),
            "22:40: " + getCheckMessage(MSG_KEY, "60"),
            "23:34: " + getCheckMessage(MSG_KEY, "20"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIgnoreFieldDeclarationWithAnnotation.java"),
                expected);
    }

    @Test
    public void testIgnoreInAnnotationElementDefault() throws Exception {
        final String[] expected = {
            "20:29: " + getCheckMessage(MSG_KEY, "10"),
            "21:33: " + getCheckMessage(MSG_KEY, "11"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberAnnotationElement.java"), expected);
    }

    @Test
    public void testMagicNumber()
            throws Exception {
        final String[] expected = {
            "40:29: " + getCheckMessage(MSG_KEY, "3.0"),
            "41:32: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "42:19: " + getCheckMessage(MSG_KEY, "3.0"),
            "43:26: " + getCheckMessage(MSG_KEY, "1.5"),
            "51:50: " + getCheckMessage(MSG_KEY, "5"),
            "52:42: " + getCheckMessage(MSG_KEY, "3"),
            "53:44: " + getCheckMessage(MSG_KEY, "3"),
            "54:37: " + getCheckMessage(MSG_KEY, "8"),
            "57:33: " + getCheckMessage(MSG_KEY, "3.0"),
            "58:36: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "59:23: " + getCheckMessage(MSG_KEY, "3.0"),
            "60:30: " + getCheckMessage(MSG_KEY, "1.5"),
            "70:33: " + getCheckMessage(MSG_KEY, "3.0"),
            "71:36: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "72:23: " + getCheckMessage(MSG_KEY, "3.0"),
            "73:30: " + getCheckMessage(MSG_KEY, "1.5"),
            "80:33: " + getCheckMessage(MSG_KEY, "3.0"),
            "81:36: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "82:23: " + getCheckMessage(MSG_KEY, "3.0"),
            "83:30: " + getCheckMessage(MSG_KEY, "1.5"),
            "92:33: " + getCheckMessage(MSG_KEY, "3.0"),
            "93:36: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "94:23: " + getCheckMessage(MSG_KEY, "3.0"),
            "95:30: " + getCheckMessage(MSG_KEY, "1.5"),
            "102:33: " + getCheckMessage(MSG_KEY, "3.0"),
            "103:36: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "104:23: " + getCheckMessage(MSG_KEY, "3.0"),
            "105:30: " + getCheckMessage(MSG_KEY, "1.5"),
            "114:33: " + getCheckMessage(MSG_KEY, "3.0"),
            "115:36: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "116:23: " + getCheckMessage(MSG_KEY, "3.0"),
            "117:30: " + getCheckMessage(MSG_KEY, "1.5"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberMagicNumber.java"), expected);
    }

    @Test
    public void testMagicNumber2() throws Exception {
        final String[] expected = {
            "27:17: " + getCheckMessage(MSG_KEY, "9"),
            "29:20: " + getCheckMessage(MSG_KEY, "5.5"),
            "30:19: " + getCheckMessage(MSG_KEY, "12.2f"),
            "35:28: " + getCheckMessage(MSG_KEY, "45"),
            "43:21: " + getCheckMessage(MSG_KEY, "9"),
            "45:24: " + getCheckMessage(MSG_KEY, "5.5"),
            "46:23: " + getCheckMessage(MSG_KEY, "12.2f"),
            "51:46: " + getCheckMessage(MSG_KEY, "5"),
            "52:38: " + getCheckMessage(MSG_KEY, "5"),
            "53:40: " + getCheckMessage(MSG_KEY, "5"),
            "54:31: " + getCheckMessage(MSG_KEY, "5"),
            "55:49: " + getCheckMessage(MSG_KEY, "5"),
            "68:50: " + getCheckMessage(MSG_KEY, "5"),
            "69:42: " + getCheckMessage(MSG_KEY, "5"),
            "70:44: " + getCheckMessage(MSG_KEY, "5"),
            "71:35: " + getCheckMessage(MSG_KEY, "5"),
            "72:53: " + getCheckMessage(MSG_KEY, "5"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberMagicNumber2.java"), expected);
    }

    @Test
    public void testMagicNumber3() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberMagicNumber3.java"), expected);
    }

    @Test
    public void testMagicNumberInGuards() throws Exception {
        final String[] expected = {
            "23:63: " + getCheckMessage(MSG_KEY, "3"),
            "23:72: " + getCheckMessage(MSG_KEY, "8"),
            "29:58: " + getCheckMessage(MSG_KEY, "3"),
            "29:68: " + getCheckMessage(MSG_KEY, "6"),
            "33:50: " + getCheckMessage(MSG_KEY, "10.88"),
            "35:46: " + getCheckMessage(MSG_KEY, "6"),
            "41:59: " + getCheckMessage(MSG_KEY, "0.5"),
            "41:67: " + getCheckMessage(MSG_KEY, "5"),
            "46:23: " + getCheckMessage(MSG_KEY, "6"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMagicNumberMagicNumberInGuards.java"), expected);
    }

    @Test
    public void testMagicNumberWithUnnamedVariables() throws Exception {
        final String[] expected = {
            "22:17: " + getCheckMessage(MSG_KEY, "9"),
            "24:21: " + getCheckMessage(MSG_KEY, "17"),
            "25:20: " + getCheckMessage(MSG_KEY, "3.1415"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMagicNumberMagicNumberWithUnnamedVariables.java"),
                expected);
    }

    @Test
    public void testMagicNumberWithOperatorsWithIgnoreFieldsTrue() throws Exception {
        final String[] expected = {
            "35:35: " + getCheckMessage(MSG_KEY, "3"),
            "36:35: " + getCheckMessage(MSG_KEY, "3"),
            "37:36: " + getCheckMessage(MSG_KEY, "3"),
            "38:34: " + getCheckMessage(MSG_KEY, "3"),
            "39:34: " + getCheckMessage(MSG_KEY, "3"),
            "40:34: " + getCheckMessage(MSG_KEY, "3"),
            "41:34: " + getCheckMessage(MSG_KEY, "3"),
            "42:31: " + getCheckMessage(MSG_KEY, "3"),
            "55:25: " + getCheckMessage(MSG_KEY, "3"),
            "54:25: " + getCheckMessage(MSG_KEY, "3"),
            "56:26: " + getCheckMessage(MSG_KEY, "3"),
            "57:24: " + getCheckMessage(MSG_KEY, "3"),
            "58:24: " + getCheckMessage(MSG_KEY, "3"),
            "59:24: " + getCheckMessage(MSG_KEY, "3"),
            "60:24: " + getCheckMessage(MSG_KEY, "3"),
            "61:21: " + getCheckMessage(MSG_KEY, "3"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberWithOperatorsWithIgnoreFieldsTrue.java"),
                expected);
    }

    @Test
    public void testMagicNumberWithOperatorsWithIgnoreFieldsFalse() throws Exception {
        final String[] expected = {
            "54:25: " + getCheckMessage(MSG_KEY, "3"),
            "55:25: " + getCheckMessage(MSG_KEY, "3"),
            "56:26: " + getCheckMessage(MSG_KEY, "3"),
            "57:24: " + getCheckMessage(MSG_KEY, "3"),
            "58:24: " + getCheckMessage(MSG_KEY, "3"),
            "59:24: " + getCheckMessage(MSG_KEY, "3"),
            "60:24: " + getCheckMessage(MSG_KEY, "3"),
            "61:21: " + getCheckMessage(MSG_KEY, "3"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberWithOperatorsWithIgnoreFieldsFalse.java"),
                expected);
    }
}
