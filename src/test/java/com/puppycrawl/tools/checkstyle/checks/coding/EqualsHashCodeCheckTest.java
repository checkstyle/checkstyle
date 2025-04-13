///
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
///

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.EqualsHashCodeCheck.MSG_KEY_EQUALS;
import static com.puppycrawl.tools.checkstyle.checks.coding.EqualsHashCodeCheck.MSG_KEY_HASHCODE;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class EqualsHashCodeCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/equalshashcode";
    }

    @Test
    public void testSemantic() throws Exception {
        final String[] expected = {
            "37:13: " + getCheckMessage(MSG_KEY_HASHCODE),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsHashCodeSemanticTwo.java"), expected);
    }

    @Test
    public void testNoEquals() throws Exception {
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_KEY_EQUALS),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsHashCodeNoEquals.java"), expected);
    }

    @Test
    public void testBooleanMethods() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputEqualsHashCode.java"), expected);
    }

    @Test
    public void testMultipleInputs() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(EqualsHashCodeCheck.class);

        final List<String> expectedFirstInputErrors = Collections.singletonList(
            "10:5: " + getCheckMessage(MSG_KEY_EQUALS)
        );
        final List<String> expectedSecondInputErrors = Collections.singletonList(
            "37:13: " + getCheckMessage(MSG_KEY_HASHCODE)
        );
        final List<String> expectedThirdInputErrors =
            Arrays.asList(CommonUtil.EMPTY_STRING_ARRAY);

        final String firstInput = getPath("InputEqualsHashCodeNoEquals.java");
        final String secondInput = getPath("InputEqualsHashCodeSemanticTwo.java");
        final String thirdInput = getPath("InputEqualsHashCode.java");

        final File[] inputs = {
            new File(firstInput),
            new File(secondInput),
            new File(thirdInput),
        };

        verify(createChecker(checkConfig), inputs, ImmutableMap.of(
            firstInput, expectedFirstInputErrors,
            secondInput, expectedSecondInputErrors,
            thirdInput, expectedThirdInputErrors
        ));
    }

    @Test
    public void testEqualsParameter() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_KEY_EQUALS),
            "24:9: " + getCheckMessage(MSG_KEY_HASHCODE),
            "54:9: " + getCheckMessage(MSG_KEY_HASHCODE),
            "59:9: " + getCheckMessage(MSG_KEY_EQUALS),
            "71:9: " + getCheckMessage(MSG_KEY_EQUALS),
            "74:9: " + getCheckMessage(MSG_KEY_HASHCODE),
            "81:9: " + getCheckMessage(MSG_KEY_EQUALS),
            "88:9: " + getCheckMessage(MSG_KEY_HASHCODE),
            "103:9: " + getCheckMessage(MSG_KEY_EQUALS),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsHashCodeEqualsParameter.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final EqualsHashCodeCheck check = new EqualsHashCodeCheck();
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
