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
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class SuppressWithPlainTextCommentFilterExamplesTest
        extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppresswithplaintextcommentfilter";
    }

    @Test
    public void testExample1() throws Exception {
        final String fileWithConfig = getPath("Example1.java");
        final String targetFile = getPath("Example1.properties");

        final String[] expectedWithoutFilter = {
            "2: Duplicated property 'keyB' (2 occurrence(s)).",
            "6: Duplicated property 'keyC' (2 occurrence(s)).",
        };

        final String[] expectedWithFilter = {
            "6: Duplicated property 'keyC' (2 occurrence(s)).",
        };

        verifyFilterWithInlineConfigParserSeparateConfigAndTarget(fileWithConfig, targetFile,
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample2() throws Exception {
        final String fileWithConfig = getPath("Example2.java");
        final String targetFile = getPath("Example2.properties");

        final String[] expectedWithoutFilter = {
            "2: Duplicated property 'keyB' (2 occurrence(s)).",
            "6: Duplicated property 'keyC' (2 occurrence(s)).",
        };

        final String[] expectedWithFilter = {
            "6: Duplicated property 'keyC' (2 occurrence(s)).",
        };

        verifyFilterWithInlineConfigParserSeparateConfigAndTarget(fileWithConfig, targetFile,
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample3() throws Exception {
        final String fileWithConfig = getPath("Example3.java");
        final String targetFile = getPath("Example3.properties");

        final String[] expectedWithoutFilter = {
            "3: Duplicated property 'keyB' (2 occurrence(s)).",
            "6: Property key 'keyA' is not in the right order with previous property 'keyB'.",
            "9: Duplicated property 'keyC' (2 occurrence(s)).",
        };

        final String[] expectedWithFilter = {
            "6: Property key 'keyA' is not in the right order with previous property 'keyB'.",
            "9: Duplicated property 'keyC' (2 occurrence(s)).",
        };

        verifyFilterWithInlineConfigParserSeparateConfigAndTarget(fileWithConfig, targetFile,
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample4() throws Exception {
        final String fileWithConfig = getPath("Example4.java");
        final String targetFile = getPath("Example4.xml");

        final String[] expectedWithoutFilter = {
            "6: Type code is not allowed. Use type raw instead.",
            "13: Type code is not allowed. Use type raw instead.",
        };

        final String[] expectedWithFilter = {
            "13: Type code is not allowed. Use type raw instead.",
        };

        verifyFilterWithInlineConfigParserSeparateConfigAndTarget(fileWithConfig, targetFile,
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample5() throws Exception {
        final String fileWithConfig = getPath("Example5.java");
        final String targetFile = getPath("Example5.xml");

        final String[] expectedWithoutFilter = {
            "6: Type code is not allowed. Use type raw instead.",
            "13: Type code is not allowed. Use type raw instead.",
        };

        final String[] expectedWithFilter = {
            "6: Type code is not allowed. Use type raw instead.",
            "13: Type code is not allowed. Use type raw instead.",
        };

        verifyFilterWithInlineConfigParserSeparateConfigAndTarget(fileWithConfig, targetFile,
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample6() throws Exception {
        final String fileWithConfig = getPath("Example6.java");
        final String targetFile = getPath("Example6.xml");

        final String[] expectedWithoutFilter = {
            "6: Type config is not allowed in this file.",
            "12: Type code is not allowed. Use type raw instead.",
        };

        final String[] expectedWithFilter = {
            "6: Type config is not allowed in this file.",
        };

        verifyFilterWithInlineConfigParserSeparateConfigAndTarget(fileWithConfig, targetFile,
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample7() throws Exception {
        final String fileWithConfig = getPath("Example7.java");
        final String targetFile = getPath("Example7.xml");

        final String[] expectedWithoutFilter = {
            "6: Type config is not allowed in this file.",
            "12: Type code is not allowed. Use type raw instead.",
        };

        final String[] expectedWithFilter = {
            "6: Type config is not allowed in this file.",
        };

        verifyFilterWithInlineConfigParserSeparateConfigAndTarget(fileWithConfig, targetFile,
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample8() throws Exception {
        final String fileWithConfig = getPath("Example8.java");
        final String targetFile = getPath("Example8.sql");

        final String[] expectedWithoutFilter = {
            "7: Line is longer than 60 characters (found 66).",
        };

        final String[] expectedWithFilter = CommonUtil.EMPTY_STRING_ARRAY;

        verifyFilterWithInlineConfigParserSeparateConfigAndTarget(fileWithConfig, targetFile,
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample9() throws Exception {

        final String[] expectedWithoutFilter = {
            "23: Line is longer than 100 characters (found 147).",
            "24: Line is longer than 100 characters (found 133).",
            "25: Line is longer than 100 characters (found 116).",
            "32: Line is longer than 100 characters (found 183).",
        };

        final String[] expectedWithFilter = {
            "32: Line is longer than 100 characters (found 183).",
        };

        verifyFilterWithInlineConfigParser(getPath("Example9.java"),
                expectedWithoutFilter, expectedWithFilter);
    }
}
