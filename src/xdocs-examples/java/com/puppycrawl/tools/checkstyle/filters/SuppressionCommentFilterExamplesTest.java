///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.filters;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class SuppressionCommentFilterExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppressioncommentfilter";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "16:7: Name 'VAR1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "22:27: Name 'var3' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "32:5: Catching 'Exception' is not allowed.",
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "21:7: Name 'VAR1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "24:7: Name 'VAR2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "27:27: Name 'var3' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "37:5: Catching 'Exception' is not allowed.",
            "42:5: Catching 'Exception' is not allowed.",
            "43:5: Catching 'Error' is not allowed.",
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "21:7: Name 'VAR1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "24:7: Name 'VAR2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "27:27: Name 'var3' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "31:27: Name 'var4' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "38:5: Catching 'Exception' is not allowed.",
            "44:5: Catching 'Error' is not allowed.",
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "20:7: Name 'VAR1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "26:27: Name 'var3' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "36:5: Catching 'Exception' is not allowed.",
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "20:7: Name 'VAR1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "26:27: Name 'var3' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "30:27: Name 'var4' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "37:5: Catching 'Exception' is not allowed.",
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "25:7: Name 'VAR1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "31:27: Name 'var3' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "41:5: Catching 'Exception' is not allowed.",
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "19:7: Name 'VAR1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "25:27: Name 'var3' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expected = {
            "18:7: Name 'VAR1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "21:7: Name 'VAR2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "24:27: Name 'var3' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "34:5: Catching 'Exception' is not allowed.",
            "39:5: Catching 'Exception' is not allowed.",
            "40:5: Catching 'Error' is not allowed.",
        };

        verifyWithInlineConfigParser(getPath("Example8.java"), expected);
    }
}
