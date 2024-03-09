///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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
    public void testMagicNumberCheckLocalVariablesPrimary()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberCheckLocalVariablesPrimary.java"), expected);
    }

    @Test
    public void testMagicNumberCheckLocalVariablesSecondary()
            throws Exception {
        final String[] expected = {
            "25:17: " + getCheckMessage(MSG_KEY, "8"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberCheckLocalVariablesSecondary.java"), expected);
    }

    @Test
    public void testMagicNumberCheckDefault()
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
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberCheckDefault.java"), expected);
    }

    @Test
    public void testMagicNumberCheckSignedAndHexValue()
            throws Exception {
        final String[] expected = {
            "21:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "28:30: " + getCheckMessage(MSG_KEY, "+3"),
            "29:29: " + getCheckMessage(MSG_KEY, "-2"),
            "30:35: " + getCheckMessage(MSG_KEY, "+3.5"),
            "31:36: " + getCheckMessage(MSG_KEY, "-2.5"),
            "38:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "39:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "42:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "43:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "50:20: " + getCheckMessage(MSG_KEY, "378"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberCheckSignedAndHexValue.java"), expected);
    }

    @Test
    public void testMagicNumberCheckHashCodeValue()
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
            "74:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "75:14: " + getCheckMessage(MSG_KEY,
                    "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "85:21: " + getCheckMessage(MSG_KEY, "122"),

        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberCheckHashCodeValue.java"), expected);
    }

    @Test
    public void testIgnoreMagicNumberIgnoreCheckNumericViolation()
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
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIgnoreCheckNumericViolation.java"), expected);
    }

    @Test
    public void testMagicNumberIgnoreCheckHexSignedAndOctalValue()
            throws Exception {
        final String[] expected = {
            "21:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "30:29: " + getCheckMessage(MSG_KEY, "-2"),
            "31:35: " + getCheckMessage(MSG_KEY, "+3.5"),
            "32:36: " + getCheckMessage(MSG_KEY, "-2.5"),
            "37:34: " + getCheckMessage(MSG_KEY, "0xffffffff"),
            "38:36: " + getCheckMessage(MSG_KEY, "0xffffffffffffffffL"),
            "39:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "40:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "41:36: " + getCheckMessage(MSG_KEY, "037777777777"),
            "42:38: " + getCheckMessage(MSG_KEY, "01777777777777777777777L"),
            "43:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "44:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "49:20: " + getCheckMessage(MSG_KEY, "378"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIgnoreCheckHexSignedAndOctalValue.java"), expected);
    }

    @Test
    public void testIgnoreMagicNumberIgnoreCheckBinaryAndAnnotationHandler()
            throws Exception {
        final String[] expected = {
            "21:16: " + getCheckMessage(MSG_KEY, "31"),
            "26:16: " + getCheckMessage(MSG_KEY, "42"),
            "31:16: " + getCheckMessage(MSG_KEY, "13"),
            "35:15: " + getCheckMessage(MSG_KEY, "21"),
            "39:15: " + getCheckMessage(MSG_KEY, "37"),
            "43:15: " + getCheckMessage(MSG_KEY, "101"),
            "75:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "76:14: " + getCheckMessage(MSG_KEY,
                    "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "86:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIgnoreCheckBinaryAndAnnotationHandler.java"), expected);
    }

    @Test
    public void testIgnoreMagicNumberIgnoreCheckNoneViolationHandler()
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
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIgnoreCheckNoneViolationHandler.java"), expected);
    }

    @Test
    public void testIgnoreMagicNumberIgnoreCheckNoneHexSignedAndOctalValue()
            throws Exception {
        final String[] expected = {
            "19:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "26:30: " + getCheckMessage(MSG_KEY, "+3"),
            "27:29: " + getCheckMessage(MSG_KEY, "-2"),
            "28:35: " + getCheckMessage(MSG_KEY, "+3.5"),
            "29:36: " + getCheckMessage(MSG_KEY, "-2.5"),
            "34:34: " + getCheckMessage(MSG_KEY, "0xffffffff"),
            "35:36: " + getCheckMessage(MSG_KEY, "0xffffffffffffffffL"),
            "36:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "37:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "38:36: " + getCheckMessage(MSG_KEY, "037777777777"),
            "39:38: " + getCheckMessage(MSG_KEY, "01777777777777777777777L"),
            "40:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "41:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "47:20: " + getCheckMessage(MSG_KEY, "378"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberIgnoreCheckNoneHexSignedAndOctalValue.java"), expected);
    }

    @Test
    public void testIgnoreMagicNumberIgnoreCheckNoneBinaryAndAnnotationHandler()
            throws Exception {
        final String[] expected = {
            "20:16: " + getCheckMessage(MSG_KEY, "31"),
            "25:16: " + getCheckMessage(MSG_KEY, "42"),
            "30:16: " + getCheckMessage(MSG_KEY, "13"),
            "34:15: " + getCheckMessage(MSG_KEY, "21"),
            "38:15: " + getCheckMessage(MSG_KEY, "37"),
            "42:15: " + getCheckMessage(MSG_KEY, "101"),
            "74:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "75:14: " + getCheckMessage(MSG_KEY,
                    "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "85:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verifyWithInlineConfigParser(
            getPath("InputMagicNumberIgnoreCheckNoneBinaryAndAnnotationHandler.java"), expected);
    }

    @Test
    public void testMagicNumberCheckIntegersOnlyViolationHandler()
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
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberCheckIntegersOnlyViolationHandler.java"), expected);
    }

    @Test
    public void testInputMagicNumberCheckIntegersOnlyHexSignedAndOctal()
            throws Exception {
        final String[] expected = {
            "20:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "28:30: " + getCheckMessage(MSG_KEY, "+3"),
            "29:29: " + getCheckMessage(MSG_KEY, "-2"),
            "38:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "39:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "42:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "43:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "49:20: " + getCheckMessage(MSG_KEY, "378"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberCheckIntegersOnlyHexSignedAndOctal.java"), expected);
    }

    @Test
    public void testInputMagicNumberCheckIntegersOnlyBinaryAndAnnotation()
            throws Exception {
        final String[] expected = {
            "20:16: " + getCheckMessage(MSG_KEY, "31"),
            "25:16: " + getCheckMessage(MSG_KEY, "42"),
            "30:16: " + getCheckMessage(MSG_KEY, "13"),
            "34:15: " + getCheckMessage(MSG_KEY, "21"),
            "38:15: " + getCheckMessage(MSG_KEY, "37"),
            "42:15: " + getCheckMessage(MSG_KEY, "101"),
            "74:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "75:14: " + getCheckMessage(MSG_KEY,
                    "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "85:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberCheckIntegersOnlyBinaryAndAnnotation.java"), expected);
    }

    @Test
    public void testMagicNumberCheckIgnoreNegativeOctalHex1() throws Exception {
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
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberCheckIgnoreNegativeOctalHex1.java"), expected);
    }

    @Test
    public void testMagicNumberCheckIgnoreNegativeOctalHex2() throws Exception {
        final String[] expected = {
            "20:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "28:30: " + getCheckMessage(MSG_KEY, "+3"),
            "50:20: " + getCheckMessage(MSG_KEY, "378"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberCheckIgnoreNegativeOctalHex2.java"), expected);
    }

    @Test
    public void testMagicNumberCheckIgnoreNegativeOctalHex3() throws Exception {
        final String[] expected = {
            "20:16: " + getCheckMessage(MSG_KEY, "31"),
            "25:16: " + getCheckMessage(MSG_KEY, "42"),
            "30:16: " + getCheckMessage(MSG_KEY, "13"),
            "34:15: " + getCheckMessage(MSG_KEY, "21"),
            "38:15: " + getCheckMessage(MSG_KEY, "37"),
            "42:15: " + getCheckMessage(MSG_KEY, "101"),
            "75:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "76:14: " + getCheckMessage(MSG_KEY,
                    "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "86:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberCheckIgnoreNegativeOctalHex3.java"), expected);
    }

    @Test
    public void testMagicNumberCheckIgnoreHashCodeMethod1() throws Exception {
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
                getPath("InputMagicNumberCheckIgnoreHashCodeMethod1.java"), expected);
    }

    @Test
    public void testMagicNumberCheckIgnoreHashCodeMethod2() throws Exception {
        final String[] expected = {
            "20:14: " + getCheckMessage(MSG_KEY, "0xffffffffL"),
            "27:30: " + getCheckMessage(MSG_KEY, "+3"),
            "28:29: " + getCheckMessage(MSG_KEY, "-2"),
            "29:35: " + getCheckMessage(MSG_KEY, "+3.5"),
            "30:36: " + getCheckMessage(MSG_KEY, "-2.5"),
            "37:35: " + getCheckMessage(MSG_KEY, "0x80000000"),
            "38:36: " + getCheckMessage(MSG_KEY, "0x8000000000000000L"),
            "41:37: " + getCheckMessage(MSG_KEY, "020000000000"),
            "42:38: " + getCheckMessage(MSG_KEY, "01000000000000000000000L"),
            "49:20: " + getCheckMessage(MSG_KEY, "378"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberCheckIgnoreHashCodeMethod2.java"), expected);
    }

    @Test
    public void testMagicNumberCheckIgnoreHashCodeMethod3() throws Exception {
        final String[] expected = {
            "25:16: " + getCheckMessage(MSG_KEY, "42"),
            "30:16: " + getCheckMessage(MSG_KEY, "13"),
            "34:15: " + getCheckMessage(MSG_KEY, "21"),
            "38:15: " + getCheckMessage(MSG_KEY, "37"),
            "42:15: " + getCheckMessage(MSG_KEY, "101"),
            "75:20: " + getCheckMessage(MSG_KEY, "0b101"),
            "76:14: " + getCheckMessage(MSG_KEY,
                    "0b1010000101000101101000010100010110100001010001011010000101000101L"),
            "86:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberCheckIgnoreHashCodeMethod3.java"), expected);
    }

    @Test
    public void testMagicNumberCheckIgnoreFieldDeclarationViolationHandler()
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
        };
        verifyWithInlineConfigParser(
            getPath("InputMagicNumberCheckIgnoreFieldDeclarationViolationHandler.java"), expected);
    }

    @Test
    public void testMagicNumberCheckIgnoreFieldDeclarationHexSignedAndOctal()
            throws Exception {
        final String[] expected = {
            "49:20: " + getCheckMessage(MSG_KEY, "378"),
        };
        verifyWithInlineConfigParser(
            getPath("InputMagicNumberCheckIgnoreFieldDeclarationHexSignedAndOctal.java"), expected);
    }

    @Test
    public void testMagicNumberCheckIgnoreFieldDeclarationBinaryAnnotation()
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
            "86:21: " + getCheckMessage(MSG_KEY, "122"),
        };
        verifyWithInlineConfigParser(
            getPath("InputMagicNumberCheckIgnoreFieldDeclarationBinaryAnnotation.java"), expected);
    }

    @Test
    public void testMagicNumberCheckWaiverParentTokenViolationHandler()
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
                getPath("InputMagicNumberCheckWaiverParentTokenViolationHandler.java"), expected);
    }

    @Test
    public void testMagicNumberCheckWaiverParentTokenHexSignedAndOctal()
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
                getPath("InputMagicNumberCheckWaiverParentTokenHexSignedAndOctal.java"), expected);
    }

    @Test
    public void testMagicNumberCheckWaiverParentTokenBinaryAnnotation()
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
                getPath("InputMagicNumberCheckWaiverParentTokenBinaryAnnotation.java"), expected);
    }

    @Test
    public void testMagicNumberCheckRecordsDefault()
            throws Exception {
        final String[] expected = {
            "19:11: " + getCheckMessage(MSG_KEY, "6"),
            "21:36: " + getCheckMessage(MSG_KEY, "7"),
            "25:29: " + getCheckMessage(MSG_KEY, "8"),
            "29:29: " + getCheckMessage(MSG_KEY, "8"),
            "33:20: " + getCheckMessage(MSG_KEY, "10"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMagicNumberCheckRecordsDefault.java"), expected);
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

    @Test
    public void testMagicNumberCheckMagicNumberProcessor()
            throws Exception {
        final String[] expected = {
            "38:29: " + getCheckMessage(MSG_KEY, "3.0"),
            "39:32: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "40:19: " + getCheckMessage(MSG_KEY, "3.0"),
            "41:26: " + getCheckMessage(MSG_KEY, "1.5"),
            "49:50: " + getCheckMessage(MSG_KEY, "5"),
            "50:42: " + getCheckMessage(MSG_KEY, "3"),
            "51:44: " + getCheckMessage(MSG_KEY, "3"),
            "52:37: " + getCheckMessage(MSG_KEY, "8"),
            "55:33: " + getCheckMessage(MSG_KEY, "3.0"),
            "56:36: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "57:23: " + getCheckMessage(MSG_KEY, "3.0"),
            "58:30: " + getCheckMessage(MSG_KEY, "1.5"),
            "68:33: " + getCheckMessage(MSG_KEY, "3.0"),
            "69:36: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "70:23: " + getCheckMessage(MSG_KEY, "3.0"),
            "71:30: " + getCheckMessage(MSG_KEY, "1.5"),
            "78:33: " + getCheckMessage(MSG_KEY, "3.0"),
            "79:36: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "80:23: " + getCheckMessage(MSG_KEY, "3.0"),
            "81:30: " + getCheckMessage(MSG_KEY, "1.5"),
            "90:33: " + getCheckMessage(MSG_KEY, "3.0"),
            "91:36: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "92:23: " + getCheckMessage(MSG_KEY, "3.0"),
            "93:30: " + getCheckMessage(MSG_KEY, "1.5"),
            "100:33: " + getCheckMessage(MSG_KEY, "3.0"),
            "101:36: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "102:23: " + getCheckMessage(MSG_KEY, "3.0"),
            "103:30: " + getCheckMessage(MSG_KEY, "1.5"),
            "112:33: " + getCheckMessage(MSG_KEY, "3.0"),
            "113:36: " + getCheckMessage(MSG_KEY, "1.5_0"),
            "114:23: " + getCheckMessage(MSG_KEY, "3.0"),
            "115:30: " + getCheckMessage(MSG_KEY, "1.5"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberCheckMagicNumberProcessor.java"), expected);
    }

    @Test
    public void testMagicNumberCheckMagicNumberNumericAnalyzer() throws Exception {
        final String[] expected = {
            "25:17: " + getCheckMessage(MSG_KEY, "9"),
            "27:20: " + getCheckMessage(MSG_KEY, "5.5"),
            "28:19: " + getCheckMessage(MSG_KEY, "12.2f"),
            "33:28: " + getCheckMessage(MSG_KEY, "45"),
            "41:21: " + getCheckMessage(MSG_KEY, "9"),
            "43:24: " + getCheckMessage(MSG_KEY, "5.5"),
            "44:23: " + getCheckMessage(MSG_KEY, "12.2f"),
            "49:46: " + getCheckMessage(MSG_KEY, "5"),
            "50:38: " + getCheckMessage(MSG_KEY, "5"),
            "51:40: " + getCheckMessage(MSG_KEY, "5"),
            "52:31: " + getCheckMessage(MSG_KEY, "5"),
            "53:49: " + getCheckMessage(MSG_KEY, "5"),
            "66:50: " + getCheckMessage(MSG_KEY, "5"),
            "67:42: " + getCheckMessage(MSG_KEY, "5"),
            "68:44: " + getCheckMessage(MSG_KEY, "5"),
            "69:35: " + getCheckMessage(MSG_KEY, "5"),
            "70:53: " + getCheckMessage(MSG_KEY, "5"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberCheckMagicNumberNumericAnalyzer.java"), expected);
    }

    @Test
    public void testMagicNumberCheckMagicNumberNumericalDataHandler() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMagicNumberCheckMagicNumberNumericalDataHandler.java"), expected);
    }
}
