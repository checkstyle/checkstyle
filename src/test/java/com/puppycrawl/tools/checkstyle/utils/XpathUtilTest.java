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

package com.puppycrawl.tools.checkstyle.utils;

import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static com.puppycrawl.tools.checkstyle.utils.XpathUtil.getTextAttributeValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class XpathUtilTest {

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue("Constructor is not private",
                isUtilsClassHasPrivateConstructor(XpathUtil.class, true));
    }

    @Test
    public void testSupportsTextAttribute() {
        assertTrue("Should return true for supported token types",
                XpathUtil.supportsTextAttribute(createDetailAST(TokenTypes.IDENT)));
        assertTrue("Should return true for supported token types",
                XpathUtil.supportsTextAttribute(createDetailAST(TokenTypes.NUM_INT)));
        assertTrue("Should return true for supported token types",
                XpathUtil.supportsTextAttribute(createDetailAST(TokenTypes.STRING_LITERAL)));
        assertTrue("Should return true for supported token types",
                XpathUtil.supportsTextAttribute(createDetailAST(TokenTypes.CHAR_LITERAL)));
        assertTrue("Should return true for supported token types",
                XpathUtil.supportsTextAttribute(createDetailAST(TokenTypes.NUM_DOUBLE)));
        assertFalse("Should return false for unsupported token types",
                XpathUtil.supportsTextAttribute(createDetailAST(TokenTypes.VARIABLE_DEF)));
        assertFalse("Should return false for unsupported token types",
                XpathUtil.supportsTextAttribute(createDetailAST(TokenTypes.OBJBLOCK)));
        assertFalse("Should return true for supported token types",
                XpathUtil.supportsTextAttribute(createDetailAST(TokenTypes.LITERAL_CHAR)));
    }

    @Test
    public void testGetValue() {
        assertEquals("Returned value differs from expected", "HELLO WORLD", getTextAttributeValue(
                createDetailAST(TokenTypes.STRING_LITERAL, "\"HELLO WORLD\"")));
        assertEquals("Returned value differs from expected", "123",
                getTextAttributeValue(createDetailAST(TokenTypes.NUM_INT, "123")));
        assertEquals("Returned value differs from expected", "HELLO WORLD",
                getTextAttributeValue(createDetailAST(TokenTypes.IDENT, "HELLO WORLD")));
        assertNotEquals("Returned value differs from expected", "HELLO WORLD",
                getTextAttributeValue(createDetailAST(TokenTypes.STRING_LITERAL, "HELLO WORLD")));
    }

    private static DetailAST createDetailAST(int type) {
        final DetailAST detailAST = new DetailAstImpl();
        detailAST.setType(type);
        return detailAST;
    }

    private static DetailAST createDetailAST(int type, String text) {
        final DetailAST detailAST = new DetailAstImpl();
        detailAST.setType(type);
        detailAST.setText(text);
        return detailAST;
    }
}
