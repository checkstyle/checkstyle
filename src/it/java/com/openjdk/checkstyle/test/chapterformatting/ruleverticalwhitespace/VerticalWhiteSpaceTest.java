///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.openjdk.checkstyle.test.chapterformatting.ruleverticalwhitespace;

import org.junit.jupiter.api.Test;

import com.openjdk.checkstyle.test.base.AbstractOpenJdkModuleTestSupport;

public class VerticalWhiteSpaceTest extends AbstractOpenJdkModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/openjdk/checkstyle/test/chapterformatting/ruleverticalwhitespace";
    }

    @Test
    public void testVerticalWhiteSpaceOne() throws Exception {
        verifyWithWholeConfig(getPath("InputVerticalWhiteSpaceOne.java"));
    }

    @Test
    public void testVerticalWhiteSpaceTwo() throws Exception {
        verifyWithWholeConfig(getPath("InputVerticalWhiteSpaceTwo.java"));
    }

    @Test
    public void testVerticalWhiteSpaceThree() throws Exception {
        verifyWithWholeConfig(getPath("InputVerticalWhiteSpaceThree.java"));
    }

    @Test
    public void testVerticalWhiteSpaceFour() throws Exception {
        verifyWithWholeConfig(getPath("InputVerticalWhiteSpaceFour.java"));
    }

}
