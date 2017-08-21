////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.internal.TestUtils.assertUtilsClassHasPrivateConstructor;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.utils.TokenUtils;

public class TokenTypesTest {
    @Test
    public void testGetShortDescription() {
        assertEquals("short description for EQUAL",
                "The <code>==</code> (equal) operator.",
                TokenUtils.getShortDescription("EQUAL"));

        assertEquals("short description for LAND",
                "The <code>&amp;&amp;</code> (conditional AND) operator.",
                TokenUtils.getShortDescription("LAND"));

        assertEquals("short description for LCURLY",
                "A left (curly) brace (<code>&#123;</code>).",
                TokenUtils.getShortDescription("LCURLY"));

        assertEquals("short description for SR_ASSIGN",
                "The <code>&gt;&gt;=</code> (signed right shift assignment)",
                TokenUtils.getShortDescription("SR_ASSIGN"));

        assertEquals("short description for SL",
                "The <code>&lt;&lt;</code> (shift left) operator.",
                TokenUtils.getShortDescription("SL"));

        assertEquals("short description for BSR",
                "The <code>&gt;&gt;&gt;</code> (unsigned shift right) operator.",
                TokenUtils.getShortDescription("BSR"));
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertUtilsClassHasPrivateConstructor(TokenTypes.class, true);
    }
}
