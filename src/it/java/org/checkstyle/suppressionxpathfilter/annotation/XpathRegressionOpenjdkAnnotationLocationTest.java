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

package org.checkstyle.suppressionxpathfilter.annotation;

import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.annotation.OpenjdkAnnotationLocationCheck;

public class XpathRegressionOpenjdkAnnotationLocationTest extends AbstractXpathTestSupport {

    private final String checkName = OpenjdkAnnotationLocationCheck.class.getSimpleName();

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/annotation/openjdkannotationlocation";
    }

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testClass() throws Exception {
        final String[] expectedViolation = {
            "15:25: " + getCheckMessage(OpenjdkAnnotationLocationCheck.class,
                    OpenjdkAnnotationLocationCheck.MSG_KEY_ANNOTATION_ON_TARGET_LINE,
                    "InputXpathOpenjdkAnnotationLocation1"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                     + "[@text='InputXpathOpenjdkAnnotationLocation1']]/LITERAL_CLASS"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathOpenjdkAnnotationLocation1.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testEnum() throws Exception {
        final String[] expectedViolation = {
            "15:24: " + getCheckMessage(OpenjdkAnnotationLocationCheck.class,
                    OpenjdkAnnotationLocationCheck.MSG_KEY_ANNOTATION_ON_TARGET_LINE,
                    "InputXpathOpenjdkAnnotationLocation2"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/ENUM_DEF[./IDENT"
                        + "[@text='InputXpathOpenjdkAnnotationLocation2']]/ENUM"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathOpenjdkAnnotationLocation2.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testMethod() throws Exception {
        final String[] expectedViolation = {
            "14:30: " + getCheckMessage(OpenjdkAnnotationLocationCheck.class,
                    OpenjdkAnnotationLocationCheck.MSG_KEY_ANNOTATION_ON_TARGET_LINE,
                    "foo1"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathOpenjdkAnnotationLocation3']"
                        + "]/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo1']]/TYPE",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathOpenjdkAnnotationLocation3']"
                        + "]/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo1']]/TYPE/LITERAL_VOID"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathOpenjdkAnnotationLocation3.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
