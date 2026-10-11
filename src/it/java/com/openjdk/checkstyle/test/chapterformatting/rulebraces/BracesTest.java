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

package com.openjdk.checkstyle.test.chapterformatting.rulebraces;

import org.junit.jupiter.api.Test;

import com.openjdk.checkstyle.test.base.AbstractOpenJdkModuleTestSupport;

public class BracesTest extends AbstractOpenJdkModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/openjdk/checkstyle/test/chapterformatting/rulebraces";
    }

    @Test
    public void testLeftCurlyValid() throws Exception {
        verifyWithWholeConfig(getPath("InputBracesLeftCurlyValid.java"));
    }

    @Test
    public void testLeftCurlyInValid() throws Exception {
        verifyWithWholeConfig(getPath("InputBracesLeftCurlyInValid.java"));
    }

    @Test
    public void testNeedBracesValid() throws Exception {
        verifyWithWholeConfig(getPath("InputBracesNeedBracesValid.java"));
    }

    @Test
    public void testNeedBracesInValid() throws Exception {
        verifyWithWholeConfig(getPath("InputBracesNeedBracesInvalid.java"));
    }

}
