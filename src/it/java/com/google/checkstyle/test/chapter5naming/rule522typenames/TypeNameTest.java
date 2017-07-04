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

package com.google.checkstyle.test.chapter5naming.rule522typenames;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class TypeNameTest extends BaseCheckTestSupport {

    @Override
    protected String getPath(String fileName) throws IOException {
        return super.getPath("chapter5naming" + File.separator + "rule522typenames"
                + File.separator + fileName);
    }

    @Test
    public void typeNameTest() throws Exception {

        final Configuration checkConfig = getCheckConfig("TypeName");
        final String msgKey = "name.invalidPattern";
        final String format = "^[A-Z][a-zA-Z0-9]*$";

        final String[] expected = {
            "3:7: " + getCheckMessage(checkConfig.getMessages(), msgKey, "inputHeaderClass",
                format),
            "5:22: " + getCheckMessage(checkConfig.getMessages(), msgKey, "InputHeader___Interface",
                format),
            "7:17: " + getCheckMessage(checkConfig.getMessages(), msgKey, "inputHeaderEnum",
                format),
            "9:11: " + getCheckMessage(checkConfig.getMessages(), msgKey, "NoValid$Name",
                format),
            "11:11: " + getCheckMessage(checkConfig.getMessages(), msgKey, "$NoValidName",
                format),
            "13:11: " + getCheckMessage(checkConfig.getMessages(), msgKey, "NoValidName$",
                format),
            "19:7: " + getCheckMessage(checkConfig.getMessages(), msgKey, "_ValidName", format),
            "21:7: " + getCheckMessage(checkConfig.getMessages(), msgKey, "Valid_Name", format),
            "23:7: " + getCheckMessage(checkConfig.getMessages(), msgKey, "ValidName_", format),
            "27:11: " + getCheckMessage(checkConfig.getMessages(), msgKey, "_Foo", format),
            "29:11: " + getCheckMessage(checkConfig.getMessages(), msgKey, "Fo_o", format),
            "31:11: " + getCheckMessage(checkConfig.getMessages(), msgKey, "Foo_", format),
            "33:11: " + getCheckMessage(checkConfig.getMessages(), msgKey, "$Foo", format),
            "35:11: " + getCheckMessage(checkConfig.getMessages(), msgKey, "Fo$o", format),
            "37:11: " + getCheckMessage(checkConfig.getMessages(), msgKey, "Foo$", format),
            "41:6: " + getCheckMessage(checkConfig.getMessages(), msgKey, "_FooEnum", format),
            "43:6: " + getCheckMessage(checkConfig.getMessages(), msgKey, "Foo_Enum", format),
            "45:6: " + getCheckMessage(checkConfig.getMessages(), msgKey, "FooEnum_", format),
            "47:6: " + getCheckMessage(checkConfig.getMessages(), msgKey, "$FooEnum", format),
            "49:6: " + getCheckMessage(checkConfig.getMessages(), msgKey, "Foo$Enum", format),
            "51:6: " + getCheckMessage(checkConfig.getMessages(), msgKey, "FooEnum$", format),
            "53:7: " + getCheckMessage(checkConfig.getMessages(), msgKey, "aaa", format),
            "55:11: " + getCheckMessage(checkConfig.getMessages(), msgKey, "bbb", format),
            "57:6: " + getCheckMessage(checkConfig.getMessages(), msgKey, "ccc", format),
            "61:12: " + getCheckMessage(checkConfig.getMessages(), msgKey, "_Annotation", format),
            "63:12: " + getCheckMessage(checkConfig.getMessages(), msgKey, "Annot_ation", format),
            "65:12: " + getCheckMessage(checkConfig.getMessages(), msgKey, "Annotation_", format),
            "67:12: " + getCheckMessage(checkConfig.getMessages(), msgKey, "$Annotation", format),
            "69:12: " + getCheckMessage(checkConfig.getMessages(), msgKey, "Annot$ation", format),
            "71:12: " + getCheckMessage(checkConfig.getMessages(), msgKey, "Annotation$", format),
        };

        final String filePath = getPath("InputTypeName.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
