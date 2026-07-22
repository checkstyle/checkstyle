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

package com.puppycrawl.tools.checkstyle.checks.regexp;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class RegexpCheckExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/regexp/regexp";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {

        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "15: Found duplicate pattern '// \\(c\\) MyCompany'.",
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "7: Line matches the illegal pattern 'Avoid using System.out.println'.",
            "21: Line matches the illegal pattern 'Avoid using System.out.println'.",
            "22: Line matches the illegal pattern 'Avoid using System.out.println'.",
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "22: Line matches the illegal pattern"
                + " 'The error limit has been exceeded, the check is aborting,"
                + " there may be more unreported errors.fix me\\.'.",
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testUseCase1() throws Exception {
        final String[] expected = {
            "1: Required pattern '// This code is copyrighted\\.' missing in file.",
        };

        verifyWithInlineConfigParser(getPath("UseCase1.java"), expected);
    }

    @Test
    public void testUseCase2() throws Exception {
        final String[] expected = {

        };

        verifyWithInlineConfigParser(getPath("UseCase2.java"), expected);
    }

    @Test
    public void testUseCase3() throws Exception {
        final String[] expected = {
            "20: Found duplicate pattern '// This code is copyrighted\\n// \\(c\\) MyCompany'.",
        };

        verifyWithInlineConfigParser(getPath("UseCase3.java"), expected);
    }

    @Test
    public void testUseCase4() throws Exception {
        final String[] expected = {
            "1: Required pattern 'Copyright' missing in file.",
        };

        verifyWithInlineConfigParser(getPath("UseCase4.java"), expected);
    }

    @Test
    public void testUseCase9() throws Exception {
        final String[] expected = {
            "23: Line matches the illegal pattern 'System\\.out\\.println'.",
            "24: Line matches the illegal pattern 'System\\.out\\.println'.",
        };

        verifyWithInlineConfigParser(getPath("UseCase9.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "20: Line matches the illegal pattern 'System\\.out\\.println'.",
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "19: Line matches the illegal pattern 'System\\.out\\.println'.",
            "20: Line matches the illegal pattern 'System\\.out\\.println'.",
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

    @Test
    public void testUseCase5() throws Exception {
        final String[] expected = {
            "17: Line matches the illegal pattern 'Trailing whitespace'.",
        };

        verifyWithInlineConfigParser(getPath("UseCase5.java"), expected);
    }

    @Test
    public void testUseCase6() throws Exception {
        final String[] expected = {
            "17: Line matches the illegal pattern '(?i)fix me\\.'.",
        };

        verifyWithInlineConfigParser(getPath("UseCase6.java"), expected);
    }

    @Test
    public void testUseCase8() throws Exception {
        final String[] expected = {
            "26: Line matches the illegal pattern"
                + " 'The error limit has been exceeded, the check is aborting,"
                + " there may be more unreported errors.(?i)fix me\\.'.",
        };

        verifyWithInlineConfigParser(getPath("UseCase8.java"), expected);
    }

    @Test
    public void testUseCase7() throws Exception {
        final String[] expected = {

        };

        verifyWithInlineConfigParser(getPath("UseCase7.java"), expected);
    }

}
