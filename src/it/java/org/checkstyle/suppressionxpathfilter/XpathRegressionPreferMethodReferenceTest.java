////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.PreferMethodReferenceCheck;

public class XpathRegressionPreferMethodReferenceTest
        extends AbstractXpathTestSupport {

    private final Class<PreferMethodReferenceCheck> clazz = PreferMethodReferenceCheck.class;
    private final String checkName = clazz.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess = new File(
                getPath("SuppressionXpathRegressionPreferMethodReference.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(clazz);
        final String[] expectedViolation = {
            "9:39: " + getCheckMessage(clazz, PreferMethodReferenceCheck.MSG_METHOD_REF),
        };
        final List<String> expectedXpathQueries = Collections.singletonList(
            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionPreferMethodReference']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='bad']]/ASSIGN/LAMBDA[./IDENT[@text='arg']]"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
