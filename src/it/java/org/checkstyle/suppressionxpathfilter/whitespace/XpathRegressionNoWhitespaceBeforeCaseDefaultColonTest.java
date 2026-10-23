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

package org.checkstyle.suppressionxpathfilter.whitespace;

import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceBeforeCaseDefaultColonCheck;

public class XpathRegressionNoWhitespaceBeforeCaseDefaultColonTest
        extends AbstractXpathTestSupport {

    private final String checkName = NoWhitespaceBeforeCaseDefaultColonCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/whitespace/"
            + "nowhitespacebeforecasedefaultcolon";
    }

    @Test
    public void testOne() throws Exception {
        final String[] expectedViolation = {
            "12:20: " + getCheckMessage(NoWhitespaceBeforeCaseDefaultColonCheck.class,
                    NoWhitespaceBeforeCaseDefaultColonCheck.MSG_KEY, ":"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                    + "'InputXpathNoWhitespaceBeforeCaseDefaultColonOne']]"
                    + "/OBJBLOCK/INSTANCE_INIT/SLIST/LITERAL_SWITCH/CASE_GROUP/LITERAL_CASE/COLON"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathNoWhitespaceBeforeCaseDefaultColonOne.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testTwo() throws Exception {
        final String[] expectedViolation = {
            "16:21: " + getCheckMessage(NoWhitespaceBeforeCaseDefaultColonCheck.class,
                    NoWhitespaceBeforeCaseDefaultColonCheck.MSG_KEY, ":"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                    + "'InputXpathNoWhitespaceBeforeCaseDefaultColonTwo']]"
                    + "/OBJBLOCK/INSTANCE_INIT/SLIST/LITERAL_SWITCH/CASE_GROUP"
                    + "/LITERAL_DEFAULT/COLON"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathNoWhitespaceBeforeCaseDefaultColonTwo.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testThree() throws Exception {
        final String[] expectedViolation = {
            "13:21: " + getCheckMessage(NoWhitespaceBeforeCaseDefaultColonCheck.class,
                        NoWhitespaceBeforeCaseDefaultColonCheck.MSG_KEY, ":"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                    + "'InputXpathNoWhitespaceBeforeCaseDefaultColonThree']]"
                    + "/OBJBLOCK/INSTANCE_INIT/SLIST/LITERAL_SWITCH/CASE_GROUP"
                    + "/LITERAL_CASE/COLON"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathNoWhitespaceBeforeCaseDefaultColonThree.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testFour() throws Exception {
        final String[] expectedViolation = {
            "17:21: " + getCheckMessage(NoWhitespaceBeforeCaseDefaultColonCheck.class,
                        NoWhitespaceBeforeCaseDefaultColonCheck.MSG_KEY, ":"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                    + "'InputXpathNoWhitespaceBeforeCaseDefaultColonFour']]"
                    + "/OBJBLOCK/INSTANCE_INIT/SLIST/LITERAL_SWITCH/CASE_GROUP"
                    + "/LITERAL_DEFAULT/COLON"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathNoWhitespaceBeforeCaseDefaultColonFour.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
