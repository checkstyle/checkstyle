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

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.EqualsAvoidNullCheck.MSG_EQUALS_AVOID_NULL;
import static com.puppycrawl.tools.checkstyle.checks.coding.EqualsAvoidNullCheck.MSG_EQUALS_IGNORE_CASE_AVOID_NULL;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class EqualsAvoidNullCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/equalsavoidnull";
    }

    @Test
    public void testEqualsWithDefault() throws Exception {

        final String[] expected = {
            "44:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "46:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "48:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "50:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "52:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "54:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "76:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "78:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "80:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "82:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "84:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "86:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "245:21: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "251:24: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "252:21: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "254:24: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "255:27: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "256:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "257:29: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "258:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "275:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "276:35: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "277:40: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "279:40: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "280:45: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "281:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "302:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "307:29: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "308:29: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "315:25: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "318:25: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "319:34: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "321:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "322:45: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "323:33: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "327:43: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "328:51: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "329:43: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "330:55: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "343:28: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "344:36: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "345:42: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "346:34: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "347:37: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "351:36: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "352:44: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "353:42: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "354:45: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "365:35: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "376:30: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "402:35: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "423:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "424:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "425:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "429:22: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNull.java"), expected);
    }

    @Test
    public void testEqualsWithoutEqualsIgnoreCase() throws Exception {

        final String[] expected = {
            "245:21: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "251:24: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "252:21: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "254:24: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "255:27: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "256:33: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "257:29: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "258:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "275:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "276:35: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "277:41: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "279:40: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "280:45: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "281:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "302:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "307:29: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "308:29: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "315:25: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "318:25: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "319:34: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "321:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "322:45: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "323:33: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "326:66: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "327:51: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "328:53: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "329:55: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "342:28: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "343:37: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "344:42: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "345:34: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "346:37: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "350:36: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "351:45: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "352:42: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "353:45: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "364:35: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "375:30: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "401:35: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "422:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "423:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "424:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "428:22: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullIgnoreCase.java"), expected);
    }

    @Test
    public void testEqualsOnTheSameLine() throws Exception {

        final String[] expected = {
            "14:28: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "21:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullOnTheSameLine.java"), expected);
    }

    @Test
    public void testEqualsNested() throws Exception {

        final String[] expected = {
            "25:34: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "26:34: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "27:34: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "33:34: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "36:39: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "39:39: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "42:39: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "45:39: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullNested.java"), expected);
    }

    @Test
    public void testEqualsSuperClass() throws Exception {

        final String[] expected = {
            "23:35: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullSuperClass.java"), expected);
    }

    @Test
    public void testInputEqualsAvoidNullEnhancedInstanceof() throws Exception {

        final String[] expected = {
            "15:45: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "18:36: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "26:50: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "27:38: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "28:38: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "29:35: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputEqualsAvoidNullEnhancedInstanceof.java"),
                expected);
    }

    @Test
    public void testMisc() throws Exception {

        final String[] expected = {
            "20:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullMisc.java"), expected);
    }

    @Test
    public void testRecordsAndCompactCtors() throws Exception {

        final String[] expected = {
            "15:23: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "22:34: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "34:33: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "41:33: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "49:33: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputEqualsAvoidNullRecordsAndCompactCtors.java"),
                expected);
    }

    @Test
    public void testEqualsAvoidNullTextBlocks() throws Exception {

        final String[] expected = {
            "13:24: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "15:24: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "22:19: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "32:31: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputEqualsAvoidNullTextBlocks.java"),
            expected);
    }

    @Test
    public void testTokensNotNull() {
        final EqualsAvoidNullCheck check = new EqualsAvoidNullCheck();
        assertWithMessage("Acceptable tokens should not be null")
                .that(check.getAcceptableTokens())
                .isNotNull();
        assertWithMessage("Default tokens should not be null")
                .that(check.getDefaultTokens())
                .isNotNull();
        assertWithMessage("Required tokens should not be null")
                .that(check.getRequiredTokens())
                .isNotNull();
    }

}
