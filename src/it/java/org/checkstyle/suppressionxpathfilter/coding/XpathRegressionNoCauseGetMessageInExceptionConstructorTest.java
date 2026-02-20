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

package org.checkstyle.suppressionxpathfilter.coding;

import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.coding.NoCauseGetMessageInExceptionConstructorCheck;

public class XpathRegressionNoCauseGetMessageInExceptionConstructorTest
    extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return NoCauseGetMessageInExceptionConstructorCheck.class.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding"
            + "/nocausegetmessageinexceptionconstructor";
    }

    @Test
    public void testOne() throws Exception {
        final String[] expectedViolation = {
            "15:17: " + getCheckMessage(NoCauseGetMessageInExceptionConstructorCheck.class,
                NoCauseGetMessageInExceptionConstructorCheck.MSG_KEY, "ex"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                + "'InputXpathNoCauseGetMessageInExceptionConstructorOne']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST/LITERAL_TRY"
                + "/LITERAL_CATCH/SLIST/LITERAL_THROW"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathNoCauseGetMessageInExceptionConstructorOne.java"),
                expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testTwo() throws Exception {
        final String[] expectedViolation = {
            "17:13: " + getCheckMessage(NoCauseGetMessageInExceptionConstructorCheck.class,
                NoCauseGetMessageInExceptionConstructorCheck.MSG_KEY, "exception"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                + "'InputXpathNoCauseGetMessageInExceptionConstructorTwo']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_TRY"
                + "/LITERAL_CATCH/SLIST/LITERAL_THROW"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathNoCauseGetMessageInExceptionConstructorTwo.java"),
                expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testThree() throws Exception {
        final String[] expectedViolation = {
            "17:13: " + getCheckMessage(NoCauseGetMessageInExceptionConstructorCheck.class,
                NoCauseGetMessageInExceptionConstructorCheck.MSG_KEY, "exception"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                + "'InputXpathNoCauseGetMessageInExceptionConstructorThree']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='process']]/SLIST/LITERAL_TRY"
                + "/LITERAL_CATCH/SLIST/LITERAL_THROW"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathNoCauseGetMessageInExceptionConstructorThree.java"),
                expectedXpathQueries, expectedViolation);
    }

}
