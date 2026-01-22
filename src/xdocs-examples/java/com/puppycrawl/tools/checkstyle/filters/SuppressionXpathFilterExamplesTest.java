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

public class SuppressionXpathFilterExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppressionxpathfilter";
    }

    @Test
    public void testExample1() throws Exception {

        final String[] expectedWithoutFilter = {
            "21:3: Cyclomatic Complexity is 4 (max allowed is 3).",
        };

        final String[] expectedWithFilter = {};

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("Example1.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample2() throws Exception {

        final String[] expectedWithoutFilter = {
            "14:1: 'package' should be separated from previous line.",
            "15:1: 'CLASS_DEF' should be separated from previous line.",
        };

        final String[] expectedWithFilter = {
            "15:1: 'CLASS_DEF' should be separated from previous line.",
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("Example2.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample3() throws Exception {

        final String[] expectedWithoutFilter = {
            "21:31: '{' at column 31 should be on a new line.",
            "26:31: '{' at column 31 should be on a new line.",
        };

        final String[] expectedWithFilter = {
            "26:31: '{' at column 31 should be on a new line.",
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("Example3.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample4() throws Exception {

        final String[] expectedWithoutFilter = {
            "17:3: 'VARIABLE_DEF' should be separated from previous line.",
            "18:3: 'METHOD_DEF' should be separated from previous line.",
        };

        final String[] expectedWithFilter = {
            "17:3: 'VARIABLE_DEF' should be separated from previous line.",
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("Example4.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample5() throws Exception {

        final String[] expectedWithoutFilter = {
            "17:15: Name 'SetSomeVar' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "19:15: Name 'TestMethod' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };

        final String[] expectedWithFilter = {
            "19:15: Name 'TestMethod' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("Example5.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample6() throws Exception {

        final String[] expectedWithoutFilter = {
            "19:9: Name 'TestVariable' must match pattern '^([a-z][a-zA-Z0-9]*|_)$'.",
            "21:9: Name 'WeirdName' must match pattern '^([a-z][a-zA-Z0-9]*|_)$'.",
        };

        final String[] expectedWithFilter = {
            "21:9: Name 'WeirdName' must match pattern '^([a-z][a-zA-Z0-9]*|_)$'.",
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("Example6.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample7() throws Exception {

        final String[] expectedWithoutFilter = {
            "17:15: Name 'DoMATH' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "18:15: Name 'DoEng' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "22:19: '11' is a magic number.",
            "23:8: Name 'FOO' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };

        final String[] expectedWithFilter = {
            "18:15: Name 'DoEng' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "22:19: '11' is a magic number.",
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("Example7.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample8() throws Exception {

        final String[] expectedWithoutFilter = {
            "22:5: Reference to instance variable 'age' needs \"this.\".",
            "26:12: Reference to instance variable 'age' needs \"this.\".",
        };

        final String[] expectedWithFilter = {
            "26:12: Reference to instance variable 'age' needs \"this.\".",
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("Example8.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample9() throws Exception {

        final String[] expectedWithoutFilter = {
            "17:37: Throwing 'RuntimeException' is not allowed.",
            "21:37: Throwing 'RuntimeException' is not allowed.",
        };

        final String[] expectedWithFilter = {
            "21:37: Throwing 'RuntimeException' is not allowed.",
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("Example9.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample10() throws Exception {

        final String[] expectedWithoutFilter = {
            "17:9: 'public' modifier out of order with the JLS suggestions.",
            "18:14: 'abstract' modifier out of order with the JLS suggestions.",
            "23:14: 'abstract' modifier out of order with the JLS suggestions.",
        };

        final String[] expectedWithFilter = {
            "23:14: 'abstract' modifier out of order with the JLS suggestions.",
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("Example10.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample11() throws Exception {

        final String[] expectedWithoutFilter = {
            "16:27: '11' is a magic number.",
        };

        final String[] expectedWithFilter = {};

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("Example11.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample12() throws Exception {

        final String[] expectedWithoutFilter = {
            "16:27: '11' is a magic number.",
        };

        final String[] expectedWithFilter = {};

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("Example12.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample13() throws Exception {

        final String[] expectedWithoutFilter = {
            "18:15: Name 'Test1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "22:15: Name 'Test2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };

        final String[] expectedWithFilter = {};

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("Example13.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample14() throws Exception {

        final String[] expectedWithoutFilter = {
            "18:15: Name 'Test1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "22:15: Name 'Test2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };

        final String[] expectedWithFilter = {
            "18:15: Name 'Test1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("Example14.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

}
