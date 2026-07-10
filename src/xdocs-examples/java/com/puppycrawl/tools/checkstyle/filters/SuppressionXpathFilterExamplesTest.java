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
    public void testExample0() throws Exception {

        final String[] expectedWithoutFilter = {
            "41:3: Cyclomatic Complexity is 4 (max allowed is 3).",
        };

        final String[] expectedWithFilter = {};

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
                + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("Example0.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample1() throws Exception {

        final String[] expectedWithoutFilter = {
            "41:3: Cyclomatic Complexity is 4 (max allowed is 3).",
        };

        final String[] expectedWithFilter = {
            "41:3: Cyclomatic Complexity is 4 (max allowed is 3).",
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("Example1.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase4() throws Exception {

        final String[] expectedWithoutFilter = {
            "13:1: 'package' should be separated from previous line.",
            "21:3: 'VARIABLE_DEF' should be separated from previous line.",
            "22:3: 'METHOD_DEF' should be separated from previous line.",
            "23:3: 'METHOD_DEF' should be separated from previous line.",
        };

        final String[] expectedWithFilter = {
            "21:3: 'VARIABLE_DEF' should be separated from previous line.",
            "22:3: 'METHOD_DEF' should be separated from previous line.",
            "23:3: 'METHOD_DEF' should be separated from previous line.",
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase4.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase5() throws Exception {

        final String[] expectedWithoutFilter = {
            "19:23: '{' at column 23 should be on a new line.",
            "27:36: '{' at column 36 should be on a new line.",
            "31:27: '{' at column 27 should be on a new line.",
            "35:28: '{' at column 28 should be on a new line.",
            "40:31: '{' at column 31 should be on a new line.",
            "41:35: '{' at column 35 should be on a new line.",
            "44:23: '{' at column 23 should be on a new line.",
        };

        final String[] expectedWithFilter = {
            "27:36: '{' at column 36 should be on a new line.",
            "31:27: '{' at column 27 should be on a new line.",
            "35:28: '{' at column 28 should be on a new line.",
            "40:31: '{' at column 31 should be on a new line.",
            "41:35: '{' at column 35 should be on a new line.",
            "44:23: '{' at column 23 should be on a new line.",
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase5.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase6() throws Exception {

        final String[] expectedWithoutFilter = {
            "20:3: 'VARIABLE_DEF' should be separated from previous line.",
            "21:3: 'METHOD_DEF' should be separated from previous line.",
            "22:3: 'METHOD_DEF' should be separated from previous line.",
        };

        final String[] expectedWithFilter = {
            "20:3: 'VARIABLE_DEF' should be separated from previous line.",
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase6.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase7() throws Exception {

        final String[] expectedWithoutFilter = {
            "20:15: Name 'SetSomeVar' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "21:15: Name 'DoMATH' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "48:15: Name 'Test1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "51:15: Name 'Test2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };

        final String[] expectedWithFilter = {
            "21:15: Name 'DoMATH' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "48:15: Name 'Test1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "51:15: Name 'Test2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase7.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase8() throws Exception {

        final String[] expectedWithoutFilter = {
            "34:9: Name 'TestVariable' must match pattern '^([a-z][a-zA-Z0-9]*|_)$'.",
            "35:9: Name 'WeirdName' must match pattern '^([a-z][a-zA-Z0-9]*|_)$'.",
        };

        final String[] expectedWithFilter = {
            "35:9: Name 'WeirdName' must match pattern '^([a-z][a-zA-Z0-9]*|_)$'.",
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase8.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase9() throws Exception {

        final String[] expectedWithoutFilter = {
            "19:13: '23' is a magic number.",
            "20:27: '11' is a magic number.",
            "21:15: Name 'SetSomeVar' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "22:15: Name 'DoMATH' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "31:11: '24' is a magic number.",
            "49:15: Name 'Test1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "52:15: Name 'Test2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "56:19: '11' is a magic number.",
            "57:8: Name 'FOO' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };

        final String[] expectedWithFilter = {
            "19:13: '23' is a magic number.",
            "20:27: '11' is a magic number.",
            "21:15: Name 'SetSomeVar' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "31:11: '24' is a magic number.",
            "49:15: Name 'Test1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "52:15: Name 'Test2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "56:19: '11' is a magic number.",
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase9.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase10() throws Exception {

        final String[] expectedWithoutFilter = {
            "32:5: Reference to instance variable 'age' needs \"this.\".",
            "41:9: Reference to instance variable 'age' needs \"this.\".",
            "41:20: Reference to instance variable 'wordCount' needs \"this.\".",
            "44:14: Reference to instance variable 'age' needs \"this.\".",
        };

        final String[] expectedWithFilter = {
            "41:9: Reference to instance variable 'age' needs \"this.\".",
            "41:20: Reference to instance variable 'wordCount' needs \"this.\".",
            "44:14: Reference to instance variable 'age' needs \"this.\".",
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase10.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase11() throws Exception {

        final String[] expectedWithoutFilter = {
            "23:37: Throwing 'RuntimeException' is not allowed.",
        };

        final String[] expectedWithFilter = {};

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase11.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase12() throws Exception {

        final String[] expectedWithoutFilter = {
            "25:9: 'public' modifier out of order with the JLS suggestions.",
            "26:14: 'abstract' modifier out of order with the JLS suggestions.",
        };

        final String[] expectedWithFilter = {};

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase12.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase13() throws Exception {
        final String[] expectedWithoutFilter = {
            "18:13: '23' is a magic number.",
            "19:27: '11' is a magic number.",
            "30:11: '24' is a magic number.",
        };

        final String[] expectedWithFilter = {
            "18:13: '23' is a magic number.",
            "30:11: '24' is a magic number.",
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase13.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase1() throws Exception {

        final String[] expectedWithoutFilter = {
            "18:13: '23' is a magic number.",
            "19:27: '11' is a magic number.",
            "30:11: '24' is a magic number.",
        };

        final String[] expectedWithFilter = {
            "18:13: '23' is a magic number.",
            "30:11: '24' is a magic number.",
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase1.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase2() throws Exception {

        final String[] expectedWithoutFilter = {
            "19:15: Name 'SetSomeVar' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "20:15: Name 'DoMATH' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "47:15: Name 'Test1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "50:15: Name 'Test2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };

        final String[] expectedWithFilter = {
            "19:15: Name 'SetSomeVar' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "20:15: Name 'DoMATH' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase2.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase3() throws Exception {

        final String[] expectedWithoutFilter = {
            "19:15: Name 'SetSomeVar' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "20:15: Name 'DoMATH' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "47:15: Name 'Test1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "50:15: Name 'Test2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };

        final String[] expectedWithFilter = {
            "19:15: Name 'SetSomeVar' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "20:15: Name 'DoMATH' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "47:15: Name 'Test1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase3.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

}
