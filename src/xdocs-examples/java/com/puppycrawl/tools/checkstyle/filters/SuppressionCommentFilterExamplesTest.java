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

import static com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;

public class SuppressionCommentFilterExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppressioncommentfilter";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expectedWithoutFilter = {
            "16:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR1", "^[a-z][a-zA-Z0-9]*$"),
            "19:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR2", "^[a-z][a-zA-Z0-9]*$"),
            "23:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "var3", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "30:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Exception"),
            "31:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Error"),
            "36:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR4", "^[a-z][a-zA-Z0-9]*$"),
            "40:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "varC", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        };

        final String[] expectedWithFilter = {
            "16:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR1", "^[a-z][a-zA-Z0-9]*$"),
            "23:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "var3", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "30:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Exception"),
            "31:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Error"),
            "36:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR4", "^[a-z][a-zA-Z0-9]*$"),
        };

        verifyFilterWithInlineConfigParser(getPath("Example1.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expectedWithoutFilter = {
            "21:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR1", "^[a-z][a-zA-Z0-9]*$"),
            "24:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR2", "^[a-z][a-zA-Z0-9]*$"),
            "28:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "var3", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "35:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Exception"),
            "36:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Error"),
            "41:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR4", "^[a-z][a-zA-Z0-9]*$"),
            "45:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "varC", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        };

        final String[] expectedWithFilter = {
            "21:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR1", "^[a-z][a-zA-Z0-9]*$"),
            "24:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR2", "^[a-z][a-zA-Z0-9]*$"),
            "35:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Exception"),
            "36:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Error"),
            "41:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR4", "^[a-z][a-zA-Z0-9]*$"),
            "45:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "varC", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        };

        verifyFilterWithInlineConfigParser(getPath("Example2.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expectedWithoutFilter = {
            "21:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR1", "^[a-z][a-zA-Z0-9]*$"),
            "24:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR2", "^[a-z][a-zA-Z0-9]*$"),
            "28:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "var3", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "35:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Exception"),
            "36:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Error"),
            "41:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR4", "^[a-z][a-zA-Z0-9]*$"),
            "45:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "varC", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        };

        final String[] expectedWithFilter = {
            "21:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR1", "^[a-z][a-zA-Z0-9]*$"),
            "24:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR2", "^[a-z][a-zA-Z0-9]*$"),
            "28:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "var3", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "36:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Error"),
            "41:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR4", "^[a-z][a-zA-Z0-9]*$"),
            "45:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "varC", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        };

        verifyFilterWithInlineConfigParser(getPath("Example3.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expectedWithoutFilter = {
            "17:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR1", "^[a-z][a-zA-Z0-9]*$"),
            "20:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR2", "^[a-z][a-zA-Z0-9]*$"),
            "24:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "var3", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "31:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Exception"),
            "32:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Error"),
            "37:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR4", "^[a-z][a-zA-Z0-9]*$"),
            "41:27: " + getCheckMessage(ConstantNameCheck.class,
                        MSG_INVALID_PATTERN, "varC", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        };

        final String[] expectedWithFilter = {
            "17:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR1", "^[a-z][a-zA-Z0-9]*$"),
            "24:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "var3", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "31:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Exception"),
            "32:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Error"),
            "37:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR4", "^[a-z][a-zA-Z0-9]*$"),
            "41:27: " + getCheckMessage(ConstantNameCheck.class,
                        MSG_INVALID_PATTERN, "varC", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        };

        verifyFilterWithInlineConfigParser(getPath("Example4.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expectedWithoutFilter = {
            "17:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR1", "^[a-z][a-zA-Z0-9]*$"),
            "20:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR2", "^[a-z][a-zA-Z0-9]*$"),
            "24:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "var3", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "31:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Exception"),
            "32:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Error"),
            "37:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR4", "^[a-z][a-zA-Z0-9]*$"),
            "41:27: " + getCheckMessage(ConstantNameCheck.class,
                        MSG_INVALID_PATTERN, "varC", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        };

        final String[] expectedWithFilter = {
            "17:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR1", "^[a-z][a-zA-Z0-9]*$"),
            "24:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "var3", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "31:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Exception"),
            "32:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Error"),
            "37:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR4", "^[a-z][a-zA-Z0-9]*$"),
            "41:27: " + getCheckMessage(ConstantNameCheck.class,
                        MSG_INVALID_PATTERN, "varC", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        };

        verifyFilterWithInlineConfigParser(getPath("Example5.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testUseCase1() throws Exception {
        final String[] expectedWithoutFilter = {
            "20:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR1", "^[a-z][a-zA-Z0-9]*$"),
            "23:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR2", "^[a-z][a-zA-Z0-9]*$"),
            "26:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "var3", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "30:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "var4", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "36:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Exception"),
            "41:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Exception"),
            "43:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Error"),
        };

        final String[] expectedWithFilter = {
            "20:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR1", "^[a-z][a-zA-Z0-9]*$"),
            "26:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "var3", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "36:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Exception"),
        };

        verifyFilterWithInlineConfigParser(getPath("UseCase1.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testUseCase2() throws Exception {
        final String[] expectedWithoutFilter = {
            "20:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR1", "^[a-z][a-zA-Z0-9]*$"),
            "23:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR2", "^[a-z][a-zA-Z0-9]*$"),
            "26:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "var3", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "30:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "var4", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "37:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Exception"),
            "42:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Exception"),
            "44:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Error"),
        };

        final String[] expectedWithFilter = {
            "20:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR1", "^[a-z][a-zA-Z0-9]*$"),
            "26:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "var3", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "30:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "var4", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "37:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Exception"),
        };

        verifyFilterWithInlineConfigParser(getPath("UseCase2.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expectedWithoutFilter = {
            "25:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR1", "^[a-z][a-zA-Z0-9]*$"),
            "28:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR2", "^[a-z][a-zA-Z0-9]*$"),
            "32:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "var3", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "39:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Exception"),
            "40:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Error"),
            "45:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR4", "^[a-z][a-zA-Z0-9]*$"),
            "49:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "varC", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        };

        final String[] expectedWithFilter = {
            "25:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR1", "^[a-z][a-zA-Z0-9]*$"),
            "28:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR2", "^[a-z][a-zA-Z0-9]*$"),
            "32:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "var3", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "39:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Exception"),
            "40:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Error"),
            "49:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "varC", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        };

        verifyFilterWithInlineConfigParser(getPath("Example6.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testUseCase3() throws Exception {
        final String[] expectedWithoutFilter = {
            "19:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR1", "^[a-z][a-zA-Z0-9]*$"),
            "22:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR2", "^[a-z][a-zA-Z0-9]*$"),
            "25:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "var3", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "31:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "var4", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "36:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Exception"),
            "40:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Exception"),
            "42:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Error"),
        };

        final String[] expectedWithFilter = {
            "19:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR1", "^[a-z][a-zA-Z0-9]*$"),
            "25:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "var3", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        };

        verifyFilterWithInlineConfigParser(getPath("UseCase3.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expectedWithoutFilter = {
            "18:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR1", "^[a-z][a-zA-Z0-9]*$"),
            "21:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR2", "^[a-z][a-zA-Z0-9]*$"),
            "25:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "var3", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "32:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Exception"),
            "33:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Error"),
            "38:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR4", "^[a-z][a-zA-Z0-9]*$"),
            "42:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "varC", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        };

        final String[] expectedWithFilter = {
            "18:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR1", "^[a-z][a-zA-Z0-9]*$"),
            "21:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR2", "^[a-z][a-zA-Z0-9]*$"),
            "25:27: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "var3", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "32:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Exception"),
            "33:5: " + getCheckMessage(IllegalCatchCheck.class, MSG_KEY, "Error"),
            "38:7: " + getCheckMessage(MemberNameCheck.class,
                    MSG_INVALID_PATTERN, "VAR4", "^[a-z][a-zA-Z0-9]*$"),
        };

        verifyFilterWithInlineConfigParser(getPath("Example8.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

}
