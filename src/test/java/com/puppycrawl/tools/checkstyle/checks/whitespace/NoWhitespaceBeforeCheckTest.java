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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceBeforeCheck.MSG_KEY;

public class NoWhitespaceBeforeCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/nowhitespacebefore";
    }

    @Test
    @Disabled // make work then replace with #testInputNoWhitespaceBefore22
    public void testInputNoWhitespaceBefore() throws Exception {
        verifyWithInlineConfigParser(
            getPath("InputNoWhitespaceBefore.java"), new String[] {
                "11:13: " + getCheckMessage(MSG_KEY, ".")
            });
    }
    @Test
    @Disabled
    public void testMethodCallViolations() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceBefore22.java"), new String[]{
                        "19:13: " + getCheckMessage(MSG_KEY, "."),
                        "20:13: " + getCheckMessage(MSG_KEY, "."),
                        "21:13: " + getCheckMessage(MSG_KEY, "."),
                        "22:13: " + getCheckMessage(MSG_KEY, "."),
                        "23:13: " + getCheckMessage(MSG_KEY, "."),
                        "24:13: " + getCheckMessage(MSG_KEY, "."),
                        "25:13: " + getCheckMessage(MSG_KEY, ".")
                });
    }

    @Test
    @Disabled
    public void testVarAssignmentViolations() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceBefore22.java"), new String[]{
                        "32:13: " + getCheckMessage(MSG_KEY, "."),
                        "33:13: " + getCheckMessage(MSG_KEY, "."),
                        "34:13: " + getCheckMessage(MSG_KEY, ".")
                });
    }

    @Test
    @Disabled
    public void testVarDeclarationViolations() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceBefore22.java"), new String[]{
                        "45:13: " + getCheckMessage(MSG_KEY, "."),
                        "46:13: " + getCheckMessage(MSG_KEY, "."),
                        "47:13: " + getCheckMessage(MSG_KEY, "."),
                        "48:13: " + getCheckMessage(MSG_KEY, "."),
                        "49:13: " + getCheckMessage(MSG_KEY, "."),
                        "50:13: " + getCheckMessage(MSG_KEY, ".")
                });
    }

    @Test
    @Disabled
    public void testArrayAccessViolations() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceBefore22.java"), new String[]{
                        "60:13: " + getCheckMessage(MSG_KEY, "."),
                        "61:13: " + getCheckMessage(MSG_KEY, "."),
                        "62:13: " + getCheckMessage(MSG_KEY, ".")
                });
    }

    @Test
    @Disabled
    public void testGenericsViolations() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceBefore22.java"), new String[]{
                        "68:13: " + getCheckMessage(MSG_KEY, "."),
                        "69:13: " + getCheckMessage(MSG_KEY, ".")
                });
    }

    @Test
    @Disabled
    public void testLambdaViolations() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceBefore22.java"), new String[]{
                        "76:13: " + getCheckMessage(MSG_KEY, ".")
                });
    }

    @Test
    @Disabled
    public void testMethodReferenceViolations() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceBefore22.java"), new String[]{
                        "84:13: " + getCheckMessage(MSG_KEY, ".")
                });
    }

    @Test
    @Disabled
    public void testNestedCallsViolations() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceBefore22.java"), new String[]{
                        "92:13: " + getCheckMessage(MSG_KEY, "."),
                        "93:13: " + getCheckMessage(MSG_KEY, ".")
                });
    }

    @Test
    @Disabled
    public void testMultipleDotsViolations() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceBefore22.java"), new String[]{
                        "100:13: " + getCheckMessage(MSG_KEY, "."),
                        "101:13: " + getCheckMessage(MSG_KEY, ".")
                });
    }

    @Test
    @Disabled
    public void testWithOtherOperatorsViolations() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceBefore22.java"), new String[]{
                        "108:13: " + getCheckMessage(MSG_KEY, ".")
                });
    }

    @Test
    @Disabled
    public void testInControlStructuresViolations() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceBefore22.java"), new String[]{
                        "116:13: " + getCheckMessage(MSG_KEY, "."),
                        "120:13: " + getCheckMessage(MSG_KEY, ".")
                });
    }

    @Test
    @Disabled
    public void testInTryCatchViolations() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceBefore22.java"), new String[]{
                        "128:13: " + getCheckMessage(MSG_KEY, ".")
                });
    }

    @Test
    @Disabled
    public void testInAnnotationsViolations() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceBefore22.java"), new String[]{
                        "136:13: " + getCheckMessage(MSG_KEY, ".")
                });
    }

    @Test
    @Disabled
    public void testInTypeCastViolations() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceBefore22.java"), new String[]{
                        "144:13: " + getCheckMessage(MSG_KEY, ".")
                });
    }

    @Test
    @Disabled
    public void testInSwitchViolations() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceBefore22.java"), new String[]{
                        "152:13: " + getCheckMessage(MSG_KEY, ".")
                });
    }

    @Test
    @Disabled
    public void testInSynchronizedViolations() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceBefore22.java"), new String[]{
                        "160:13: " + getCheckMessage(MSG_KEY, ".")
                });
    }

    @Test
    @Disabled
    public void testInAssertViolations() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceBefore22.java"), new String[]{
                        "168:13: " + getCheckMessage(MSG_KEY, ".")
                });
    }

    @Test
    @Disabled
    public void testInReturnViolations() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceBefore22.java"), new String[]{
                        "176:13: " + getCheckMessage(MSG_KEY, ".")
                });
    }

    @Test
    @Disabled
    public void testInThrowViolations() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceBefore22.java"), new String[]{
                        "184:13: " + getCheckMessage(MSG_KEY, ".")
                });
    }

    @Test
    @Disabled
    public void testInArrayInitializerViolations() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceBefore22.java"), new String[]{
                        "192:13: " + getCheckMessage(MSG_KEY, "."),
                        "193:13: " + getCheckMessage(MSG_KEY, ".")
                });
    }

}
