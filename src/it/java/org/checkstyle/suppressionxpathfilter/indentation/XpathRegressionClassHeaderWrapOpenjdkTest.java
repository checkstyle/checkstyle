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

import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.indentation.ClassHeaderWrapOpenjdkCheck;

public class XpathRegressionClassHeaderWrapOpenjdkTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return ClassHeaderWrapOpenjdkCheck.class.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/indentation/"
                + "classheaderwrapopenjdk";
    }

    @Test
    public void testClass() throws Exception {
        final String[] expectedViolation = {
            "10:47: " + getCheckMessage(ClassHeaderWrapOpenjdkCheck.class,
                    ClassHeaderWrapOpenjdkCheck.MSG_KEY_NOT_ON_NEW_LINE, "implements"),
        };
        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathClassHeaderWrapOpenjdkClass']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Nested']]"
                        + "/IMPLEMENTS_CLAUSE[./IDENT[@text='Comparable']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathClassHeaderWrapOpenjdkClass.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testInterface() throws Exception {
        final String[] expectedViolation = {
            "9:22: " + getCheckMessage(ClassHeaderWrapOpenjdkCheck.class,
                    ClassHeaderWrapOpenjdkCheck.MSG_KEY_NOT_ON_NEW_LINE, "extends"),
        };
        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/INTERFACE_DEF"
                        + "[./IDENT[@text='InputXpathClassHeaderWrapOpenjdkInterface']]"
                        + "/OBJBLOCK/INTERFACE_DEF[./IDENT[@text='Nested']]"
                        + "/EXTENDS_CLAUSE[./IDENT[@text='Comparable']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathClassHeaderWrapOpenjdkInterface.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testEnum() throws Exception {
        final String[] expectedViolation = {
            "9:17: " + getCheckMessage(ClassHeaderWrapOpenjdkCheck.class,
                    ClassHeaderWrapOpenjdkCheck.MSG_KEY_NOT_ON_NEW_LINE, "implements"),
        };
        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathClassHeaderWrapOpenjdkEnum']]"
                        + "/OBJBLOCK/ENUM_DEF[./IDENT[@text='Nested']]"
                        + "/IMPLEMENTS_CLAUSE[./IDENT[@text='Comparable']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathClassHeaderWrapOpenjdkEnum.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
