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

package com.puppycrawl.tools.checkstyle.checks.annotation;

import static com.puppycrawl.tools.checkstyle.checks.annotation.AdjacentAnnotationCheck.MSG_KEY_ANNOTATION_NOT_ADJACENT;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class AdjacentAnnotationCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/annotation/adjacentannotation";
    }

    @Test
    public void testGetRequiredTokens() {
        final AdjacentAnnotationCheck check = new AdjacentAnnotationCheck();
        Assertions.assertArrayEquals(CommonUtil.EMPTY_INT_ARRAY, check.getRequiredTokens(),
            "AdjacentAnnotationCheck#getRequiredTokens should return empty array by "
                     + "default");
    }

    @Test
    public void testGetAcceptableTokens() {
        final AdjacentAnnotationCheck constantNameCheckObj = new AdjacentAnnotationCheck();
        final int[] actual = constantNameCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.TYPECAST,
            TokenTypes.LITERAL_THROWS,
            TokenTypes.IMPLEMENTS_CLAUSE,
            TokenTypes.TYPE_ARGUMENT,
            TokenTypes.LITERAL_NEW,
            TokenTypes.DOT,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
        Assertions.assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

    @Test
    public void testCheck() throws Exception {
        final String[] expected = {
            "21:1: " + getCheckMessage(MSG_KEY_ANNOTATION_NOT_ADJACENT, "Bar", 23),
            "26:5: " + getCheckMessage(MSG_KEY_ANNOTATION_NOT_ADJACENT, "Bar", 28),
            "31:5: " + getCheckMessage(MSG_KEY_ANNOTATION_NOT_ADJACENT, "Bar", 34),
            "37:5: " + getCheckMessage(MSG_KEY_ANNOTATION_NOT_ADJACENT, "Bar", 39),
            "41:5: " + getCheckMessage(MSG_KEY_ANNOTATION_NOT_ADJACENT, "Foo", 43),
            "43:5: " + getCheckMessage(MSG_KEY_ANNOTATION_NOT_ADJACENT, "Bar", 45),
            "55:14: " + getCheckMessage(MSG_KEY_ANNOTATION_NOT_ADJACENT, "Foo", 57),
        };
        verifyWithInlineConfigParser(
                getPath("InputAdjacentAnnotationCheck.java"),
                expected);
    }
}
