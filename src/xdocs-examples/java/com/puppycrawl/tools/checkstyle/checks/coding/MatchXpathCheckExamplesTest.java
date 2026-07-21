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

package com.puppycrawl.tools.checkstyle.checks.coding;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class MatchXpathCheckExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/matchxpath";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "20:3: " + "Private methods must appear after public methods",
            "23:3: " + "Private methods must appear after public methods",
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testUseCase1() throws Exception {
        final String[] expected = {
            "20:3: " + "Parameterized constructors are not allowed",
            "22:3: " + "Parameterized constructors are not allowed",
        };

        verifyWithInlineConfigParser(getPath("UseCase1.java"), expected);
    }

    @Test
    public void testUseCase2() throws Exception {
        final String[] expected = {
            "18:3: " + "Method name should not be test or foo",
            "21:3: " + "Method name should not be test or foo",
        };

        verifyWithInlineConfigParser(getPath("UseCase2.java"), expected);
    }

    @Test
    public void testUseCase3() throws Exception {
        final String[] expected = {
            "20:5: " + "New instances should be created via var keyword",
        };

        verifyWithInlineConfigParser(getPath("UseCase3.java"), expected);
    }

    @Test
    public void testUseCase4() throws Exception {
        final String[] expected = {
            "19:3: " + "Classes with more than 1 constructor are not allowed",
        };

        verifyWithInlineConfigParser(getPath("UseCase4.java"), expected);
    }

    @Test
    public void testUseCase5() throws Exception {
        final String[] expected = {
            "19:17: " + "Array initialization should contain at most 10 elements",
        };

        verifyWithInlineConfigParser(getPath("UseCase5.java"), expected);
    }

}
