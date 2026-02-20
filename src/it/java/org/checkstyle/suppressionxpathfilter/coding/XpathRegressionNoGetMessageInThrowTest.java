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

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.NoGetMessageInThrowCheck;

public class XpathRegressionNoGetMessageInThrowTest extends AbstractXpathTestSupport {

    private final String checkName = NoGetMessageInThrowCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/nogetmessageinthrow";
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathNoGetMessageInThrowOne.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(NoGetMessageInThrowCheck.class);

        final String[] expectedViolation = {
            "8:17: " + getCheckMessage(NoGetMessageInThrowCheck.class,
                NoGetMessageInThrowCheck.MSG_KEY, "ex"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathNoGetMessageInThrowOne']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST/LITERAL_TRY"
                + "/LITERAL_CATCH/SLIST/LITERAL_THROW"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathNoGetMessageInThrowTwo.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(NoGetMessageInThrowCheck.class);

        final String[] expectedViolation = {
            "10:13: " + getCheckMessage(NoGetMessageInThrowCheck.class,
                NoGetMessageInThrowCheck.MSG_KEY, "exception"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathNoGetMessageInThrowTwo']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_TRY"
                + "/LITERAL_CATCH/SLIST/LITERAL_THROW"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }
}
