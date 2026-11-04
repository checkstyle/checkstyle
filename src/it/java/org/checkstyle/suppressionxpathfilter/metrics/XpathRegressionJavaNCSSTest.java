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

import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.metrics.JavaNCSSCheck;

// -@cs[AbbreviationAsWordInName] Test should be named as its main class.
public class XpathRegressionJavaNCSSTest extends AbstractXpathTestSupport {

    private final String checkName = JavaNCSSCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/metrics/javancss";
    }

    @Test
    public void testOne() throws Exception {
        final String[] expectedViolation = {
            "15:5: " + getCheckMessage(JavaNCSSCheck.class,
                    JavaNCSSCheck.MSG_METHOD, 51, 50),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathJavaNCSSOne']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]",

                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathJavaNCSSOne']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS",

                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathJavaNCSSOne']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathJavaNCSSOne.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testTwo() throws Exception {
        final String[] expectedViolation = {
            "13:1: " + getCheckMessage(JavaNCSSCheck.class,
                    JavaNCSSCheck.MSG_CLASS, 51, 50),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathJavaNCSSTwo']]",

                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathJavaNCSSTwo']]/MODIFIERS",

                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathJavaNCSSTwo']]"
                + "/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathJavaNCSSTwo.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testThree() throws Exception {
        final String[] expectedViolation = {
            "11:1: " + getCheckMessage(JavaNCSSCheck.class,
                    JavaNCSSCheck.MSG_FILE, 51, 50),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT",
                "/COMPILATION_UNIT/PACKAGE_DEF"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathJavaNCSSThree.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
