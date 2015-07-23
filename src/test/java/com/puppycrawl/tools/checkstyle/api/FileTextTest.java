////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class FileTextTest {

    @Test
    public void testUnsupportedCharset() throws IOException {
        // just to make UT coverage 100%
        String charsetName = "STRANGE_CHARSET";
        try {
            FileText o = new FileText(new File("any name"), charsetName);
            fail();
        }
        catch (UnsupportedEncodingException e) {
            assertEquals(e.getMessage(), "Unsupported charset: " + charsetName);
        }

    }

    @Test
    public void testSupportedCharset() throws IOException {
        // just to make UT coverage 100%
        String charsetName = "ISO-8859-1";
        try {
            FileText o = new FileText(new File("src/test/resources/com/puppycrawl/tools/"
                     + "checkstyle/imports/import-control_complete.xml"), charsetName);
            assertEquals(o.getCharset().name(), charsetName);
        }
        catch (UnsupportedEncodingException e) {
            fail();
        }

    }
}
