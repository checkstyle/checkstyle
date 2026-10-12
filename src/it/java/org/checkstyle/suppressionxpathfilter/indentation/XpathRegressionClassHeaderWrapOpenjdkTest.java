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

package org.checkstyle.suppressionxpathfilter.indentation;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.indentation.ClassHeaderWrapOpenjdkCheck;

public class XpathRegressionClassHeaderWrapOpenjdkTest extends AbstractXpathTestSupport {

    private static final Class<ClassHeaderWrapOpenjdkCheck> CLASS =
            ClassHeaderWrapOpenjdkCheck.class;

    @Override
    protected String getCheckName() {
        return CLASS.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/indentation/"
                + "classheaderwrapopenjdk";
    }

    @Test
    public void testClass() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathClassHeaderWrapOpenjdkClass.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(CLASS);
        final String[] expectedViolation = {
            "5:47: " + getCheckMessage(CLASS,
                    ClassHeaderWrapOpenjdkCheck.MSG_KEY_NOT_ON_NEW_LINE, "implements"),
        };
        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathClassHeaderWrapOpenjdkClass']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Nested']]"
                        + "/IMPLEMENTS_CLAUSE[./IDENT[@text='Comparable']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testInterface() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathClassHeaderWrapOpenjdkInterface.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(CLASS);
        final String[] expectedViolation = {
            "4:22: " + getCheckMessage(CLASS,
                    ClassHeaderWrapOpenjdkCheck.MSG_KEY_NOT_ON_NEW_LINE, "extends"),
        };
        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/INTERFACE_DEF"
                        + "[./IDENT[@text='InputXpathClassHeaderWrapOpenjdkInterface']]"
                        + "/OBJBLOCK/INTERFACE_DEF[./IDENT[@text='Nested']]"
                        + "/EXTENDS_CLAUSE[./IDENT[@text='Comparable']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testEnum() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathClassHeaderWrapOpenjdkEnum.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(CLASS);
        final String[] expectedViolation = {
            "4:17: " + getCheckMessage(CLASS,
                    ClassHeaderWrapOpenjdkCheck.MSG_KEY_NOT_ON_NEW_LINE, "implements"),
        };
        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathClassHeaderWrapOpenjdkEnum']]"
                        + "/OBJBLOCK/ENUM_DEF[./IDENT[@text='Nested']]"
                        + "/IMPLEMENTS_CLAUSE[./IDENT[@text='Comparable']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

}
