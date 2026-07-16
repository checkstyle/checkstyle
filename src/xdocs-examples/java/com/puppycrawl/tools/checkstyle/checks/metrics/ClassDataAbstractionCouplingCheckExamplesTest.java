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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import static com.puppycrawl.tools.checkstyle.checks.metrics.ClassDataAbstractionCouplingCheck.MSG_KEY;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class ClassDataAbstractionCouplingCheckExamplesTest
        extends AbstractExamplesModuleTestSupport {

    /**
     * Creates a new {@code ClassDataAbstractionCouplingCheckExamplesTest} instance.
     */
    public ClassDataAbstractionCouplingCheckExamplesTest() {
        // no code by default
    }

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/metrics/classdataabstractioncoupling";
    }

    @Test
    public void testExample1() throws Exception {
        final String expectedClasses = List.of(
            "AtomicInteger",
            "BigDecimal",
            "BigInteger",
            "BufferedReader",
            "ByteArrayInputStream",
            "CharArrayWriter",
            "MathContext",
            "PipedReader",
            "UseCase1"
        ).toString();

        final String[] expected = {
            "26:1: " + getCheckMessage(MSG_KEY, 9, 7, expectedClasses),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testUseCase1() throws Exception {
        final String expectedClasses = List.of(
            "AtomicInteger",
            "BigInteger",
            "Example1",
            "Example3",
            "Example5",
            "Example7",
            "UseCase2",
            "UseCase3"
        ).toString();

        final String[] expected = {
            "25:1: " + getCheckMessage(MSG_KEY, 8, 7, expectedClasses),
        };

        verifyWithInlineConfigParser(getPath("UseCase1.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String expectedClasses = List.of(
            "AtomicInteger",
            "BigDecimal",
            "BigInteger",
            "BufferedReader",
            "ByteArrayInputStream",
            "CharArrayWriter",
            "Example1",
            "MathContext",
            "PipedReader",
            "UseCase1"
        ).toString();

        final String[] expected = {
            "28:1: " + getCheckMessage(MSG_KEY, 10, 9, expectedClasses),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testUseCase2() throws Exception {
        final String[] expected = {
            "22:1: " + getCheckMessage(MSG_KEY, 3, 2, "[AtomicInteger, BigDecimal, BigInteger]"),
        };

        verifyWithInlineConfigParser(getPath("ignore/deeper/UseCase2.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String expectedClasses = List.of(
            "AtomicInteger",
            "BigDecimal",
            "BigInteger",
            "BufferedReader",
            "ByteArrayInputStream",
            "CharArrayWriter",
            "MathContext",
            "Object",
            "PipedReader",
            "UseCase1",
            "byte"
        ).toString();

        final String[] expected = {
            "32:1: " + getCheckMessage(MSG_KEY, 11, 7, expectedClasses),
        };

        verifyWithInlineConfigParser(getPath("ignore/deeper/Example5.java"), expected);
    }

    @Test
    public void testUseCase3() throws Exception {
        final String expectedClasses = List.of(
            "AtomicInteger",
            "BigInteger",
            "Example3",
            "Example5",
            "Example7",
            "UseCase1",
            "UseCase2",
            "UseCase4"
        ).toString();

        final String[] expected = {
            "27:1: " + getCheckMessage(MSG_KEY, 8, 7, expectedClasses),
        };

        verifyWithInlineConfigParser(getPath("ignore/deeper/UseCase3.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String expectedClasses = List.of(
            "AtomicInteger",
            "BigDecimal",
            "BigInteger",
            "ByteArrayInputStream",
            "CharArrayWriter",
            "Example1",
            "MathContext",
            "UseCase1"
        ).toString();

        final String[] expected = {
            "31:1: " + getCheckMessage(MSG_KEY, 8, 7, expectedClasses),
        };

        verifyWithInlineConfigParser(getPath("ignore/Example7.java"), expected);
    }

    @Test
    public void testUseCase4() throws Exception {
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

        verifyWithInlineConfigParser(getPath("ignore/UseCase4.java"), expected);
    }

    @Test
    public void testExample9() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(getPath("ignore/Example9.java"), expected);
    }

    @Test
    public void testUseCase5() throws Exception {
        final String expectedClasses = List.of(
                "AtomicInteger",
                "BigDecimal",
                "BigInteger",
                "Example3",
                "MathContext",
                "UseCase1",
                "UseCase2",
                "UseCase3"
        ).toString();

        final String[] expected = {
            "33:1: " + getCheckMessage(MSG_KEY, 8, 7, expectedClasses),
        };

        verifyWithInlineConfigParser(getPath("ignore/UseCase5.java"), expected);
    }

    @Test
    public void testUseCase6() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(getPath("UseCase6.java"), expected);
    }

}
