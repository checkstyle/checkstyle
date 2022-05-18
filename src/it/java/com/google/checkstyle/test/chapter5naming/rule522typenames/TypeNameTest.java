///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

package com.google.checkstyle.test.chapter5naming.rule522typenames;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class TypeNameTest extends AbstractGoogleModuleTestSupport {

    public static final String MSG = "Type name ''{0}'' must match pattern ''{1}''.";

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter5naming/rule522typenames";
    }

    @Test
    public void testTypeName() throws Exception {
        final Configuration checkConfig = getModuleConfig("TypeName");
        final String format = "^[A-Z][a-zA-Z0-9]*$";

        final String[] expected = {
            "3:7: " + getCheckMessage(MSG, "inputHeaderClass", format),
            "5:22: " + getCheckMessage(MSG, "InputHeader___Interface", format),
            "7:17: " + getCheckMessage(MSG, "inputHeaderEnum", format),
            "9:11: " + getCheckMessage(MSG, "NoValid$Name", format),
            "11:11: " + getCheckMessage(MSG, "$NoValidName", format),
            "13:11: " + getCheckMessage(MSG, "NoValidName$", format),
            "19:7: " + getCheckMessage(MSG, "_ValidName", format),
            "21:7: " + getCheckMessage(MSG, "Valid_Name", format),
            "23:7: " + getCheckMessage(MSG, "ValidName_", format),
            "27:11: " + getCheckMessage(MSG, "_Foo", format),
            "29:11: " + getCheckMessage(MSG, "Fo_o", format),
            "31:11: " + getCheckMessage(MSG, "Foo_", format),
            "33:11: " + getCheckMessage(MSG, "$Foo", format),
            "35:11: " + getCheckMessage(MSG, "Fo$o", format),
            "37:11: " + getCheckMessage(MSG, "Foo$", format),
            "41:6: " + getCheckMessage(MSG, "_FooEnum", format),
            "43:6: " + getCheckMessage(MSG, "Foo_Enum", format),
            "45:6: " + getCheckMessage(MSG, "FooEnum_", format),
            "47:6: " + getCheckMessage(MSG, "$FooEnum", format),
            "49:6: " + getCheckMessage(MSG, "Foo$Enum", format),
            "51:6: " + getCheckMessage(MSG, "FooEnum$", format),
            "53:7: " + getCheckMessage(MSG, "aaa", format),
            "55:11: " + getCheckMessage(MSG, "bbb", format),
            "57:6: " + getCheckMessage(MSG, "ccc", format),
            "61:12: " + getCheckMessage(MSG, "_Annotation", format),
            "63:12: " + getCheckMessage(MSG, "Annot_ation", format),
            "65:12: " + getCheckMessage(MSG, "Annotation_", format),
            "67:12: " + getCheckMessage(MSG, "$Annotation", format),
            "69:12: " + getCheckMessage(MSG, "Annot$ation", format),
            "71:12: " + getCheckMessage(MSG, "Annotation$", format),
        };

        final String filePath = getPath("InputTypeName.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
