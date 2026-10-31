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

package org.checkstyle.suppressionxpathfilter.metrics;

import java.util.Arrays;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.metrics.ClassDataAbstractionCouplingCheck;

public class XpathRegressionClassDataAbstractionCouplingTest extends AbstractXpathTestSupport {

    private final String checkName = ClassDataAbstractionCouplingCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/metrics/classdataabstractioncoupling";
    }

    @Test
    public void testClassDataAbstractCouplingClass() throws Exception {
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

        final String[] expectedViolations = {
            "35:1: " + getCheckMessage(ClassDataAbstractionCouplingCheck.class,
                ClassDataAbstractionCouplingCheck.MSG_KEY, 8, 7, expectedClasses),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
            + "[@text='InputXpathClassDataAbstractionCouplingClass']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
            + "[@text='InputXpathClassDataAbstractionCouplingClass']]"
            + "/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
            + "[@text='InputXpathClassDataAbstractionCouplingClass']]"
            + "/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathClassDataAbstractionCouplingClass.java"),
                expectedXpathQueries,
                expectedViolations);
    }

    @Test
    public void testClassDataAbstractCouplingEnum() throws Exception {
        final String expectedClasses = List.of(
            "BigDecimal",
            "BigInteger",
            "CharArrayWriter",
            "File",
            "MathContext",
            "Runnable",
            "StringWriter",
            "Thread"
        ).toString();

        final String[] expectedViolations = {
            "32:1: " + getCheckMessage(ClassDataAbstractionCouplingCheck.class,
                ClassDataAbstractionCouplingCheck.MSG_KEY, 8, 7, expectedClasses),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/ENUM_DEF[./IDENT"
            + "[@text='InputXpathClassDataAbstractionCouplingEnum']]",
            "/COMPILATION_UNIT/ENUM_DEF[./IDENT"
            + "[@text='InputXpathClassDataAbstractionCouplingEnum']]"
            + "/MODIFIERS",
            "/COMPILATION_UNIT/ENUM_DEF[./IDENT"
            + "[@text='InputXpathClassDataAbstractionCouplingEnum']]"
            + "/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathClassDataAbstractionCouplingEnum.java"),
                expectedXpathQueries,
                expectedViolations);
    }

    @Test
    public void testClassDataAbstractCouplingInterface() throws Exception {
        final String expectedClasses = List.of(
            "BigDecimal",
            "BigInteger",
            "CharArrayWriter",
            "File",
            "MathContext",
            "Runnable",
            "StringWriter",
            "Thread"
        ).toString();

        final String[] expectedViolations = {
            "32:1: " + getCheckMessage(ClassDataAbstractionCouplingCheck.class,
                ClassDataAbstractionCouplingCheck.MSG_KEY, 8, 7, expectedClasses),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
            + "[@text='InputXpathClassDataAbstractionCouplingInterface']]",
            "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
            + "[@text='InputXpathClassDataAbstractionCouplingInterface']]"
            + "/MODIFIERS",
            "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
            + "[@text='InputXpathClassDataAbstractionCouplingInterface']]"
            + "/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathClassDataAbstractionCouplingInterface.java"),
                expectedXpathQueries,
                expectedViolations);
    }

}
