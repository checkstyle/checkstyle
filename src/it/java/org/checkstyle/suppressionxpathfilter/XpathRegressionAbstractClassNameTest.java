////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractClassNameCheck;

public class XpathRegressionAbstractClassNameTest extends AbstractXpathTestSupport {

    private final String checkName = AbstractClassNameCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testClassNameTop() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionAbstractClassNameTop.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AbstractClassNameCheck.class);

        final String[] expectedViolation = {
            "3:1: " + getCheckMessage(AbstractClassNameCheck.class,
                AbstractClassNameCheck.MSG_ILLEGAL_ABSTRACT_CLASS_NAME,
                    "SuppressionXpathRegressionAbstractClassNameTop", "^Abstract.+$"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionAbstractClassNameTop']]",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionAbstractClassNameTop']]"
                        + "/MODIFIERS",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionAbstractClassNameTop']]"
                        + "/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testClassNameInner() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionAbstractClassNameInner.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AbstractClassNameCheck.class);

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(AbstractClassNameCheck.class,
                AbstractClassNameCheck.MSG_ILLEGAL_ABSTRACT_CLASS_NAME,
                    "MyClass", "^Abstract.+$"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionAbstractClassNameInner']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='MyClass']]",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionAbstractClassNameInner']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='MyClass']]/MODIFIERS",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionAbstractClassNameInner']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='MyClass']]/MODIFIERS/ABSTRACT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testClassNameNoModifier() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionAbstractClassNameNoModifier.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AbstractClassNameCheck.class);

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(AbstractClassNameCheck.class,
                AbstractClassNameCheck.MSG_NO_ABSTRACT_CLASS_MODIFIER,
                    "AbstractMyClass"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text='"
                        + "SuppressionXpathRegressionAbstractClassNameNoModifier']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='AbstractMyClass']]",
                "/CLASS_DEF[./IDENT[@text='"
                        + "SuppressionXpathRegressionAbstractClassNameNoModifier']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='AbstractMyClass']]/MODIFIERS",
                "/CLASS_DEF[./IDENT[@text='"
                        + "SuppressionXpathRegressionAbstractClassNameNoModifier']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='AbstractMyClass']]/LITERAL_CLASS"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
