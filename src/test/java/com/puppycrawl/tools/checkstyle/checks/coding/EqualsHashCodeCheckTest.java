////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.coding.EqualsHashCodeCheck.MSG_KEY_EQUALS;
import static com.puppycrawl.tools.checkstyle.checks.coding.EqualsHashCodeCheck.MSG_KEY_HASHCODE;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        final DefaultConfiguration checkConfig =
            createModuleConfig(EqualsHashCodeCheck.class);
        final String[] expected = {
            "94:13: " + getCheckMessage(MSG_KEY_HASHCODE),
        };
        verify(checkConfig, getPath("InputEqualsHashCodeSemantic.java"), expected);
    }

    @Test
    public void testNoEquals() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(EqualsHashCodeCheck.class);
        final String[] expected = {
            "4:5: " + getCheckMessage(MSG_KEY_EQUALS),
        };
        verify(checkConfig, getPath("InputEqualsHashCodeNoEquals.java"), expected);
    }

    @Test
    public void testBooleanMethods() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(EqualsHashCodeCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputEqualsHashCode.java"), expected);
    }

    @Test
    public void testMultipleInputs() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(EqualsHashCodeCheck.class);

        final List<String> expectedFirstInputErrors = Collections.singletonList(
            "4:5: " + getCheckMessage(MSG_KEY_EQUALS)
        );
        final List<String> expectedSecondInputErrors = Collections.singletonList(
            "94:13: " + getCheckMessage(MSG_KEY_HASHCODE)
        );
        final List<String> expectedThirdInputErrors =
            Arrays.asList(CommonUtil.EMPTY_STRING_ARRAY);

        final String firstInput = getPath("InputEqualsHashCodeNoEquals.java");
        final String secondInput = getPath("InputEqualsHashCodeSemantic.java");
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
        final DefaultConfiguration checkConfig =
            createModuleConfig(EqualsHashCodeCheck.class);
        final String[] expected = {
            "10:9: " + getCheckMessage(MSG_KEY_EQUALS),
            "18:9: " + getCheckMessage(MSG_KEY_HASHCODE),
            "48:9: " + getCheckMessage(MSG_KEY_HASHCODE),
            "53:9: " + getCheckMessage(MSG_KEY_EQUALS),
            "65:9: " + getCheckMessage(MSG_KEY_EQUALS),
            "68:9: " + getCheckMessage(MSG_KEY_HASHCODE),
            "75:9: " + getCheckMessage(MSG_KEY_EQUALS),
            "82:9: " + getCheckMessage(MSG_KEY_HASHCODE),
            "97:9: " + getCheckMessage(MSG_KEY_EQUALS),
        };
        verify(checkConfig, getPath("InputEqualsHashCodeEqualsParameter.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final EqualsHashCodeCheck check = new EqualsHashCodeCheck();
        assertNotNull(check.getAcceptableTokens(), "Acceptable tokens should not be null");
        assertNotNull(check.getDefaultTokens(), "Default tokens should not be null");
        assertNotNull(check.getRequiredTokens(), "Required tokens should not be null");
    }

}
