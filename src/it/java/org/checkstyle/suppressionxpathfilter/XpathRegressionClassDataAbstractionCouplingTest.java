///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.metrics.ClassDataAbstractionCouplingCheck;

public class XpathRegressionClassDataAbstractionCouplingTest extends AbstractXpathTestSupport {

    private final String checkName = ClassDataAbstractionCouplingCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testClassDataAbstractCouplingDefaultMax() throws Exception {
        final File classPath =
                new File(getPath("InputXpathClassDataAbstractionCouplingDefaultMax.java"));

        final DefaultConfiguration configuration =
                createModuleConfig(ClassDataAbstractionCouplingCheck.class);

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
            "14:1: " + getCheckMessage(ClassDataAbstractionCouplingCheck.class,
                ClassDataAbstractionCouplingCheck.MSG_KEY, 8, 7, expectedClasses),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
            + "[@text='InputXpathClassDataAbstractionCouplingDefaultMax']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
            + "[@text='InputXpathClassDataAbstractionCouplingDefaultMax']]"
            + "/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
            + "[@text='InputXpathClassDataAbstractionCouplingDefaultMax']]"
            + "/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(configuration, classPath, expectedViolations,
                expectedXpathQueries);
    }

    @Test
    public void testClassDataAbstractCouplingNestedMembers() throws Exception {
        final File classPath =
                new File(getPath("InputXpathClassDataAbstractionCouplingNested.java"));

        final DefaultConfiguration configuration =
                createModuleConfig(ClassDataAbstractionCouplingCheck.class);

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
            "14:1: " + getCheckMessage(ClassDataAbstractionCouplingCheck.class,
                ClassDataAbstractionCouplingCheck.MSG_KEY, 8, 7, expectedClasses),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
            + "[@text='InputXpathClassDataAbstractionCouplingNested']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
            + "[@text='InputXpathClassDataAbstractionCouplingNested']]"
            + "/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
            + "[@text='InputXpathClassDataAbstractionCouplingNested']]"
            + "/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(configuration, classPath, expectedViolations,
                expectedXpathQueries);
    }
}
