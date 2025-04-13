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
import com.puppycrawl.tools.checkstyle.checks.design.DesignForExtensionCheck;

public class XpathRegressionDesignForExtensionTest extends AbstractXpathTestSupport {

    private final String checkName = DesignForExtensionCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void test() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathDesignForExtensionClass.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(DesignForExtensionCheck.class);
        moduleConfig.addProperty("ignoredAnnotations", "Override");

        final String[] expected = {
            "7:5: " + getCheckMessage(DesignForExtensionCheck.class,
                DesignForExtensionCheck.MSG_KEY,
                "InputXpathDesignForExtensionClass",
                "calculateValue"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathDesignForExtensionClass']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='calculateValue']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathDesignForExtensionClass']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='calculateValue']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathDesignForExtensionClass']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='calculateValue']]/MODIFIERS/LITERAL_PUBLIC"
        );

        // Run the verifications
        runVerifications(moduleConfig, fileToProcess, expected, expectedXpathQueries);

    }

    @Test
    public void test2() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathDesignForExtensionWithEnum.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(DesignForExtensionCheck.class);
        moduleConfig.addProperty("ignoredAnnotations", "Override");
        moduleConfig.addProperty("requiredJavadocPhrase", "This[\\s\\S]*implementation");

        final String[] expected = {
            "23:5: " + getCheckMessage(DesignForExtensionCheck.class,
                DesignForExtensionCheck.MSG_KEY,
                "InputXpathDesignForExtensionWithEnum",
                "processData"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathDesignForExtensionWithEnum']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='processData']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathDesignForExtensionWithEnum']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='processData']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathDesignForExtensionWithEnum']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='processData']]/MODIFIERS/LITERAL_PUBLIC"
        );

        // Run the verifications
        runVerifications(moduleConfig, fileToProcess, expected, expectedXpathQueries);

    }
}
