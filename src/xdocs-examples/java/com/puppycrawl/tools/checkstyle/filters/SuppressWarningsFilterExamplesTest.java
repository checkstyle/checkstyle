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

public class SuppressWarningsFilterExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppresswarningsfilter";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expectedWithoutFilter = {
            "16:7: Name 'J' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "17:7: Name 'JJ' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "20:7: 'int' is followed by whitespace.",
            "20:10: Name 'ARRAY' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "23:7: 'int' is followed by whitespace.",
            "23:10: Name 'ARRAY2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };

        final String[] expectedWithFilter = {
            "17:7: Name 'JJ' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "23:7: 'int' is followed by whitespace.",
            "23:10: Name 'ARRAY2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };

        verifyFilterWithInlineConfigParser(getPath("Example1.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expectedWithoutFilter = {
            "9: Dont use System.out/err, use SLF4J instead.",
            "23: Dont use System.out/err, use SLF4J instead.",
            "26: Dont use System.out/err, use SLF4J instead.",
        };

        final String[] expectedWithFilter = {
            "9: Dont use System.out/err, use SLF4J instead.",
            "26: Dont use System.out/err, use SLF4J instead.",
        };

        verifyFilterWithInlineConfigParser(getPath("Example2.java"),
                expectedWithoutFilter, expectedWithFilter);
    }
}
