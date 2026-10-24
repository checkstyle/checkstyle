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

package org.checkstyle.suppressionxpathfilter.sizes;

import static com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck.MSG_KEY;

import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck;

public class XpathRegressionParameterNumberTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return ParameterNumberCheck.class.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/sizes/parameternumber";
    }

    @Test
    public void testDefault() throws Exception {
        final String filePath =
                getPath("InputXpathParameterNumberDefault.java");

        final String[] expectedViolations = {
            "16:10: " + getCheckMessage(ParameterNumberCheck.class, MSG_KEY, 7, 11),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathParameterNumberDefault']]"
                + "/OBJBLOCK/METHOD_DEF/IDENT[@text='myMethod']"
        );

        verifyXpathWithInlineConfigParser(filePath, expectedXpathQueries, expectedViolations);
    }

    @Test
    public void testMethods() throws Exception {
        final String filePath =
                getPath("InputXpathParameterNumberMethods.java");

        final String[] expectedViolations = {
            "18:10: " + getCheckMessage(ParameterNumberCheck.class, MSG_KEY, 10, 11),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
               "/COMPILATION_UNIT/CLASS_DEF"
               + "[./IDENT[@text='InputXpathParameterNumberMethods']]"
               + "/OBJBLOCK/METHOD_DEF/IDENT[@text='myMethod']"
        );

        verifyXpathWithInlineConfigParser(filePath, expectedXpathQueries, expectedViolations);
    }

    @Test
    public void testIgnoreOverriddenMethods() throws Exception {
        final String filePath =
                getPath("InputXpathParameterNumberIgnoreOverriddenMethods.java");

        final String[] expectedViolations = {
            "17:13: " + getCheckMessage(ParameterNumberCheck.class, MSG_KEY, 7, 8),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathParameterNumberIgnoreOverriddenMethods']]"
                + "/OBJBLOCK/CTOR_DEF/IDENT"
                + "[@text='InputXpathParameterNumberIgnoreOverriddenMethods']"
        );

        verifyXpathWithInlineConfigParser(filePath, expectedXpathQueries, expectedViolations);
    }

    @Test
    public void testIgnoreAnnotatedBy() throws Exception {
        final String filePath =
                getPath("InputXpathParameterNumberIgnoreAnnotatedBy.java");

        final String[] expectedViolations = {
            "26:34: " + getCheckMessage(ParameterNumberCheck.class, MSG_KEY, 2, 3),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathParameterNumberIgnoreAnnotatedBy']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='InnerClass']]"
                + "/OBJBLOCK/STATIC_INIT/SLIST/EXPR/LITERAL_NEW[./IDENT[@text='Object']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                + "/SLIST/LITERAL_IF/SLIST/EXPR/LITERAL_NEW[./IDENT[@text='Object']]"
                + "/OBJBLOCK/METHOD_DEF/IDENT[@text='checkedMethod']"
        );

        verifyXpathWithInlineConfigParser(filePath, expectedXpathQueries, expectedViolations);
    }

}
