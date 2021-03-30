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

package com.puppycrawl.tools.checkstyle.api;

import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.utils.TokenUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TokenTypesTest {

    @Test
    public void testAllTokenTypesHasDescription() {
        final String tokenTypes = "com.puppycrawl.tools.checkstyle.api.tokentypes";
        final ResourceBundle bundle = ResourceBundle.getBundle(tokenTypes, Locale.ROOT);

        final Set<String> expected = Arrays.stream(TokenUtil.getAllTokenIds())
            .mapToObj(TokenUtil::getTokenName)
            .filter(name -> name.charAt(0) != '$')
            .collect(Collectors.toSet());
        final Set<String> actual = bundle.keySet();
        assertEquals(expected, actual, "TokenTypes without description");
    }

    @Test
    public void testAllDescriptionsEndsWithPeriod() {
        final Set<String> badDescriptions = Arrays.stream(TokenUtil.getAllTokenIds())
            .mapToObj(TokenUtil::getTokenName)
            .filter(name -> name.charAt(0) != '$')
            .map(TokenUtil::getShortDescription)
            .filter(desc -> desc.charAt(desc.length() - 1) != '.').collect(Collectors.toSet());
        assertEquals(Collections.emptySet(), badDescriptions, "Malformed TokenType descriptions");
    }

    @ParameterizedTest(name = "{index} => expected: ''{0}'', token: ''{1}''")
    @MethodSource("testGetShortDescriptionProvider")
    public void testGetShortDescription(String expected, String token) {
        assertEquals(expected, TokenUtil.getShortDescription(token), "short description for " + token);
    }

    static Stream<Arguments> testGetShortDescriptionProvider() {
    return Stream.of(
            arguments("The <code>==</code> (equal) operator.", "EQUAL"),
            arguments("The <code>&&</code> (conditional AND) operator.", "LAND"),
            arguments("A left curly brace (<code>{</code>).", "LCURLY"),
            arguments("The <code>>>=</code> (signed right shift assignment) operator.", "SR_ASSIGN"),
            arguments("The <code><<</code> (shift left) operator.", "SL"),
            arguments("The <code>>>></code> (unsigned shift right) operator.", "BSR")
    );
}

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue(isUtilsClassHasPrivateConstructor(TokenTypes.class, true),
                "Constructor is not private");
    }

}
