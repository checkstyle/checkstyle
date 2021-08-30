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
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class AdjacentAnnotationCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/annotation/adjacentannotation";
    }

    @Test
    public void testGetRequiredTokens() {
        final AnnotationOnSameLineCheck check = new AnnotationOnSameLineCheck();
        assertArrayEquals(CommonUtil.EMPTY_INT_ARRAY, check.getRequiredTokens(),
                "AnnotationOnSameLineCheck#getRequiredTokens should return empty array by default");
    }

    @Test
    public void testGetAcceptableTokens() {
        final AnnotationOnSameLineCheck constantNameCheckObj = new AnnotationOnSameLineCheck();
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
        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

    @Test
    public void testCheck() throws Exception {
        final DefaultConfiguration config = createModuleConfig(AdjacentAnnotationCheck.class);
        config.addAttribute("tokens", "CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, "
                                              + "CTOR_DEF, VARIABLE_DEF, PARAMETER_DEF, "
                                              + "ANNOTATION_DEF, TYPECAST, LITERAL_THROWS, "
                                              + "IMPLEMENTS_CLAUSE, TYPE_ARGUMENT, "
                                              + "LITERAL_NEW, DOT, ANNOTATION_FIELD_DEF");
        final String[] expected = {
                "12:1: " + getCheckMessage(MSG_KEY_ANNOTATION_NOT_ADJACENT, "Bar", 14),
                "17:5: " + getCheckMessage(MSG_KEY_ANNOTATION_NOT_ADJACENT, "Bar", 19),
                "22:5: " + getCheckMessage(MSG_KEY_ANNOTATION_NOT_ADJACENT, "Bar", 25),
                "28:5: " + getCheckMessage(MSG_KEY_ANNOTATION_NOT_ADJACENT, "Bar", 30),
                "32:5: " + getCheckMessage(MSG_KEY_ANNOTATION_NOT_ADJACENT, "Foo", 34),
                "34:5: " + getCheckMessage(MSG_KEY_ANNOTATION_NOT_ADJACENT, "Bar", 36),
                "46:14: " + getCheckMessage(MSG_KEY_ANNOTATION_NOT_ADJACENT, "Foo", 48),
                };
        verify(config, getPath("InputAdjacentAnnotationCheck.java"), expected);
    }
}
