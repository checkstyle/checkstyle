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

package com.puppycrawl.tools.checkstyle.filters;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class SuppressWithNearbyCommentFilterExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppresswithnearbycommentfilter";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expectedWithFilter = {

        };
        final String[] expectedWithoutFilter = {
            "13:27: 'int' is followed by whitespace.",
        };

        verifyFilterWithInlineConfigParser(getPath("Example1.java"), expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expectedWithFilter = {
            "17:30: Name 'array' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        };
        final String[] expectedWithoutFilter = {
            "17:30: Name 'array' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "19:27: Name 'lowerCaseConstant' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        };

        verifyFilterWithInlineConfigParser(getPath("Example2.java"), expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expectedWithFilter = {

        };
        final String[] expectedWithoutFilter = {
            "27:5: Catching 'RuntimeException' is not allowed.",
        };

        verifyFilterWithInlineConfigParser(getPath("Example3.java"), expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expectedWithFilter = {

        };
        final String[] expectedWithoutFilter = {
            "15:27: 'int' is followed by whitespace.",
        };

        verifyFilterWithInlineConfigParser(getPath("Example4.java"), expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expectedWithFilter = {
            "15:27: 'int' is followed by whitespace.",
        };
        final String[] expectedWithoutFilter = {
            "15:27: 'int' is followed by whitespace.",
        };

        verifyFilterWithInlineConfigParser(getPath("Example5.java"), expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expectedWithFilter = {
            "19:27: 'int' is followed by whitespace.",
        };
        final String[] expectedWithoutFilter = {
            "19:27: 'int' is followed by whitespace.",
        };

        verifyFilterWithInlineConfigParser(getPath("Example6.java"), expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase1() throws Exception {
        final String[] expectedWithFilter = {
            "26:20: Name 'lowerCaseConstant5' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        };
        final String[] expectedWithoutFilter = {
            "19:20: Name 'lowerCaseConstant1' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "21:20: Name 'lowerCaseConstant2' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "23:20: Name 'lowerCaseConstant3' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "25:20: Name 'lowerCaseConstant4' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "26:20: Name 'lowerCaseConstant5' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        };

        verifyFilterWithInlineConfigParser(getPath("UseCase1.java"), expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase2() throws Exception {
        final String[] expectedWithFilter = {

        };
        final String[] expectedWithoutFilter = {
            "17:15: Name 'D2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };

        verifyFilterWithInlineConfigParser(getPath("UseCase2.java"), expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase3() throws Exception {
        final String[] expectedWithFilters = {

        };
        final String[] expectedWithoutFilters = {
            "19:27: 'int' is followed by whitespace.",
            "19:30: Name 'array' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        };

        verifyFilterWithInlineConfigParser(getPath("UseCase3.java"), expectedWithoutFilters,
                expectedWithFilters);
    }

    @Test
    public void testUseCase4() throws Exception {
        final String[] expectedWithFilter = {

        };
        final String[] expectedWithoutFilter = {

        };

        verifyFilterWithInlineConfigParser(getPath("UseCase4.java"), expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase5() throws Exception {
        final String[] expectedWithFilter = {

        };
        final String[] expectedWithoutFilter = {
            "21:1: Class Data Abstraction Coupling is 2 (max allowed is 1) "
                    + "classes [Example1, Example2].",
            "24:23: '10022' is a magic number.",
        };

        verifyFilterWithInlineConfigParser(getPath("UseCase5.java"), expectedWithoutFilter,
                expectedWithFilter);
    }

}
