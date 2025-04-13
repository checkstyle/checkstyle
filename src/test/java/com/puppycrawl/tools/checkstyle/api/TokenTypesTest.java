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

package com.puppycrawl.tools.checkstyle.api;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

public class TokenTypesTest {

    @Test
    public void testAllTokenTypesHasDescription() {
        final String tokenTypes = "com.puppycrawl.tools.checkstyle.api.tokentypes";
        final ResourceBundle bundle = ResourceBundle.getBundle(tokenTypes, Locale.ROOT);

        final Set<String> expected = Arrays.stream(TokenUtil.getAllTokenIds())
            .mapToObj(TokenUtil::getTokenName)
            .filter(name -> name.charAt(0) != '$')
            .collect(Collectors.toUnmodifiableSet());
        final Set<String> actual = bundle.keySet();
        assertWithMessage("TokenTypes without description")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testAllDescriptionsEndsWithPeriod() {
        final Set<String> badDescriptions = Arrays.stream(TokenUtil.getAllTokenIds())
            .mapToObj(TokenUtil::getTokenName)
            .filter(name -> name.charAt(0) != '$')
            .map(TokenUtil::getShortDescription)
            .filter(desc -> desc.charAt(desc.length() - 1) != '.')
            .collect(Collectors.toUnmodifiableSet());
        assertWithMessage("Malformed TokenType descriptions")
                .that(badDescriptions)
                .isEqualTo(Collections.emptySet());
    }

    @Test
    public void testGetShortDescription() {
        assertWithMessage("short description for EQUAL")
                .that(TokenUtil.getShortDescription("EQUAL"))
                .isEqualTo("The <code>==</code> (equal) operator.");

        assertWithMessage("short description for LAND")
                .that(TokenUtil.getShortDescription("LAND"))
                .isEqualTo("The <code>&&</code> (conditional AND) operator.");

        assertWithMessage("short description for LCURLY")
                .that(TokenUtil.getShortDescription("LCURLY"))
                .isEqualTo("A left curly brace (<code>{</code>).");

        assertWithMessage("short description for SR_ASSIGN")
                .that(TokenUtil.getShortDescription("SR_ASSIGN"))
                .isEqualTo("The <code>>>=</code> (signed right shift assignment) operator.");

        assertWithMessage("short description for SL")
                .that(TokenUtil.getShortDescription("SL"))
                .isEqualTo("The <code><<</code> (shift left) operator.");

        assertWithMessage("short description for BSR")
                .that(TokenUtil.getShortDescription("BSR"))
                .isEqualTo("The <code>>>></code> (unsigned shift right) operator.");
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertWithMessage("Constructor is not private")
                .that(isUtilsClassHasPrivateConstructor(TokenTypes.class))
                .isTrue();
    }

}
