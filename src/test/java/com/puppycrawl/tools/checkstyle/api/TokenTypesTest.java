////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

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
        assertEquals("TokenTypes without description", expected, actual);
    }

    @Test
    public void testAllDescriptionsEndsWithPeriod() {
        final Set<String> badDescriptions = Arrays.stream(TokenUtil.getAllTokenIds())
            .mapToObj(TokenUtil::getTokenName)
            .filter(name -> name.charAt(0) != '$')
            .map(TokenUtil::getShortDescription)
            .filter(desc -> desc.charAt(desc.length() - 1) != '.').collect(Collectors.toSet());
        assertEquals("Malformed TokenType descriptions", Collections.emptySet(), badDescriptions);
    }

    @Test
    public void testGetShortDescription() {
        assertEquals("short description for EQUAL",
                "The <code>==</code> (equal) operator.",
                TokenUtil.getShortDescription("EQUAL"));

        assertEquals("short description for LAND",
                "The <code>&&</code> (conditional AND) operator.",
                TokenUtil.getShortDescription("LAND"));

        assertEquals("short description for LCURLY",
                "A left curly brace (<code>{</code>).",
                TokenUtil.getShortDescription("LCURLY"));

        assertEquals("short description for SR_ASSIGN",
                "The <code>>>=</code> (signed right shift assignment) operator.",
                TokenUtil.getShortDescription("SR_ASSIGN"));

        assertEquals("short description for SL",
                "The <code><<</code> (shift left) operator.",
                TokenUtil.getShortDescription("SL"));

        assertEquals("short description for BSR",
                "The <code>>>></code> (unsigned shift right) operator.",
                TokenUtil.getShortDescription("BSR"));
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue("Constructor is not private",
                isUtilsClassHasPrivateConstructor(TokenTypes.class, true));
    }

}
