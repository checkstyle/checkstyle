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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import static com.puppycrawl.tools.checkstyle.checks.metrics.ClassDataAbstractionCouplingCheck.MSG_KEY;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class ClassDataAbstractionCouplingCheckExamplesTest
        extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/metrics/classdataabstractioncoupling";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String expectedClasses = List.of(
            "AtomicInteger",
            "BigInteger",
            "Example1",
            "Example3",
            "Example4",
            "Example5",
            "Example6",
            "Example7"
        ).toString();

        final String[] expected = {
            "25:1: " + getCheckMessage(MSG_KEY, 8, 7, expectedClasses),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "22:1: " + getCheckMessage(MSG_KEY, 3, 2, "[AtomicInteger, BigDecimal, BigInteger]"),
        };

        verifyWithInlineConfigParser(getPath("ignore/deeper/Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(getPath("ignore/deeper/Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String expectedClasses = List.of(
            "AtomicInteger",
            "BigInteger",
            "Example2",
            "Example3",
            "Example4",
            "Example5",
            "Example7",
            "Example8"
        ).toString();

        final String[] expected = {
            "27:1: " + getCheckMessage(MSG_KEY, 8, 7, expectedClasses),
        };

        verifyWithInlineConfigParser(getPath("ignore/deeper/Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(getPath("ignore/Example7.java"), expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String expectedClasses = List.of(
                "AtomicInteger",
                "BigDecimal",
                "BigInteger",
                "ByteArrayInputStream",
                "CharArrayWriter",
                "File",
                "MathContext",
                "StringWriter"
        ).toString();

        final String[] expected = {
            "28:1: " + getCheckMessage(MSG_KEY, 8, 7, expectedClasses),
        };

        verifyWithInlineConfigParser(getPath("ignore/Example8.java"), expected);
    }

    @Test
    public void testExample9() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(getPath("ignore/Example9.java"), expected);
    }

    @Test
    public void testExample10() throws Exception {
        final String expectedClasses = List.of(
                "AtomicInteger",
                "BigDecimal",
                "BigInteger",
                "Example2",
                "Example3",
                "Example4",
                "Example6",
                "MathContext"
        ).toString();

        final String[] expected = {
            "33:1: " + getCheckMessage(MSG_KEY, 8, 7, expectedClasses),
        };

        verifyWithInlineConfigParser(getPath("ignore/Example10.java"), expected);
    }

    @Test
    public void testExample11() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(getPath("Example11.java"), expected);
    }
}
