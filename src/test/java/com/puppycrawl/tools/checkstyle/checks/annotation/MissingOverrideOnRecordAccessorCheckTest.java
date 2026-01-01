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

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.annotation.MissingOverrideOnRecordAccessorCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class MissingOverrideOnRecordAccessorCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/annotation/missingoverrideonrecordaccessor";
    }

    @Test
    public void testGetAcceptableTokens() {
        final MissingOverrideOnRecordAccessorCheck check =
            new MissingOverrideOnRecordAccessorCheck();
        assertWithMessage("Acceptable tokens should equal required tokens")
            .that(check.getAcceptableTokens())
            .isEqualTo(check.getRequiredTokens());
    }

    @Test
    public void testRecordAccessorWithoutOverride() throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingOverrideOnRecordAccessorViolation.java"),
                expected);
    }

    @Test
    public void testRecordAccessorWithOverride() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMissingOverrideOnRecordAccessorValid.java"),
                expected);
    }

    @Test
    public void testMultipleComponentsWithoutOverride() throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_KEY),
            "18:5: " + getCheckMessage(MSG_KEY),
            "23:5: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingOverrideOnRecordAccessorMultiple.java"),
                expected);
    }

    @Test
    public void testRecordWithImplementsClause() throws Exception {
        final String[] expected = {
            "18:5: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingOverrideOnRecordAccessorWithImplements.java"),
                expected);
    }

    @Test
    public void testNonAccessorMethodInRecord() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMissingOverrideOnRecordAccessorNonAccessor.java"),
                expected);
    }

    @Test
    public void testMethodWithParameters() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMissingOverrideOnRecordAccessorWithParams.java"),
                expected);
    }

    @Test
    public void testNestedRecord() throws Exception {
        final String[] expected = {
            "20:9: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingOverrideOnRecordAccessorNested.java"),
                expected);
    }

    @Test
    public void testRecordInsideClass() throws Exception {
        final String[] expected = {
            "14:9: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingOverrideOnRecordAccessorInClass.java"),
                expected);
    }

    @Test
    public void testGenericRecord() throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingOverrideOnRecordAccessorGeneric.java"),
                expected);
    }

    @Test
    public void testRegularClass() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMissingOverrideOnRecordAccessorRegularClass.java"),
                expected);
    }
}
