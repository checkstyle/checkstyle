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

package org.checkstyle.suppressionxpathfilter.naming;

import java.io.File;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.GoogleMemberNameCheck;

public class XpathRegressionGoogleMemberNameTest extends AbstractXpathTestSupport {

    private static final Class<GoogleMemberNameCheck> CLASS = GoogleMemberNameCheck.class;

    @Override
    protected String getCheckName() {
        return CLASS.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/naming/googlemembername";
    }

    @Test
    public void testMemberName() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathGoogleMemberName.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(CLASS);

        final String[] expectedViolation = {
            "5:9: " + getCheckMessage(CLASS, GoogleMemberNameCheck.MSG_KEY_INVALID_FORMAT, "Foo"),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathGoogleMemberName']]"
                + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='Foo']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testInvalidUnderscore() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathGoogleMemberNameInvalidUnderscore.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(CLASS);
        final String[] expectedViolation = {
            "5:9: " + getCheckMessage(CLASS, GoogleMemberNameCheck.MSG_KEY_INVALID_UNDERSCORE,
                    "gradle_9_5_1"),
        };
        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathGoogleMemberNameInvalidUnderscore']]"
                + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='gradle_9_5_1']"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
