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

package com.puppycrawl.tools.checkstyle.checks;

import static org.junit.Assert.assertArrayEquals;

import java.io.File;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class SuppressWarningsHolderTest extends BaseCheckTestSupport {

    @Test
    public void testGetRequiredTokens() {
        SuppressWarningsHolder checkObj = new SuppressWarningsHolder();
        int[] expected = new int[] {TokenTypes.ANNOTATION};
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testOnComplexAnnotations() throws Exception {
        Configuration checkConfig = createCheckConfig(SuppressWarningsHolder.class);

        String[] expected = {
        };

        verify(checkConfig, getPath("InputSuppressWarningsHolder.java"), expected);
    }

    @Test
    public void testCustomAnnotation() throws Exception {
        Configuration checkConfig = createCheckConfig(SuppressWarningsHolder.class);

        final String[] expected = {
        };

        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                + "checkstyle/InputSuppressWarningsHolder.java").getCanonicalPath(), expected);
    }
}
