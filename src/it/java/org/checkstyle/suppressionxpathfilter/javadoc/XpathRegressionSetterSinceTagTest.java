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

package org.checkstyle.suppressionxpathfilter.javadoc;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.javadoc.SetterSinceTagCheck;

public class XpathRegressionSetterSinceTagTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return SetterSinceTagCheck.class.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/javadoc/settersincetag";
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathSetterSinceTagOneCheck.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(SetterSinceTagCheck.class);

        final String[] expectedViolation = {
            "24:5: " + getCheckMessage(SetterSinceTagCheck.class,
                SetterSinceTagCheck.MSG_MISSING_SINCE_TAG, "setId2"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathSetterSinceTagOneCheck']]/OBJBLOCK/METHOD_DEF"
                        + "[./IDENT[@text='setId2']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathSetterSinceTagOneCheck']]/OBJBLOCK/METHOD_DEF"
                        + "[./IDENT[@text='setId2']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathSetterSinceTagOneCheck']]/OBJBLOCK/METHOD_DEF"
                        + "[./IDENT[@text='setId2']]/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathSetterSinceTagTwoFilter.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(SetterSinceTagCheck.class);

        final String[] expectedViolation = {
            "14:9: " + getCheckMessage(SetterSinceTagCheck.class,
                SetterSinceTagCheck.MSG_MISSING_SINCE_TAG, "setId"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathSetterSinceTagTwoFilter']]/OBJBLOCK/CLASS_DEF"
                        + "[./IDENT[@text='InnerClassFilter']]/OBJBLOCK/METHOD_DEF"
                        + "[./IDENT[@text='setId']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathSetterSinceTagTwoFilter']]/OBJBLOCK/CLASS_DEF"
                        + "[./IDENT[@text='InnerClassFilter']]/OBJBLOCK/METHOD_DEF"
                        + "[./IDENT[@text='setId']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathSetterSinceTagTwoFilter']]/OBJBLOCK/CLASS_DEF"
                        + "[./IDENT[@text='InnerClassFilter']]/OBJBLOCK/METHOD_DEF"
                        + "[./IDENT[@text='setId']]/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
