///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.NoCloneCheck;

public class XpathRegressionNoCloneTest extends AbstractXpathTestSupport {

    private final String checkName = NoCloneCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testMethod() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathNoCloneMethod.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(NoCloneCheck.class);

        final String[] expectedViolation = {
            "5:5: " + getCheckMessage(NoCloneCheck.class, NoCloneCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNoCloneMethod']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='clone']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNoCloneMethod']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='clone']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathNoCloneMethod']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='clone']]/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathNoCloneInnerClass.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(NoCloneCheck.class);

        final String[] expectedViolation = {
            "6:5: " + getCheckMessage(NoCloneCheck.class, NoCloneCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathNoCloneInnerClass']]"
                + "/OBJBLOCK"
                + "/CLASS_DEF[./IDENT[@text='InnerClass']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='clone']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathNoCloneInnerClass']]"
                + "/OBJBLOCK"
                + "/CLASS_DEF[./IDENT[@text='InnerClass']]/OBJBLOCK/"
                + "METHOD_DEF[./IDENT[@text='clone']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathNoCloneInnerClass']]"
                + "/OBJBLOCK"
                + "/CLASS_DEF[./IDENT[@text='InnerClass']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='clone']]/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }
}
