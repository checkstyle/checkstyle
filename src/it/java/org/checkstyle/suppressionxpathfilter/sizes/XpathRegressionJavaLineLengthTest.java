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

package org.checkstyle.suppressionxpathfilter.sizes;

import static com.puppycrawl.tools.checkstyle.checks.sizes.JavaLineLengthCheck.MSG_KEY;

import java.io.File;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.sizes.JavaLineLengthCheck;

public class XpathRegressionJavaLineLengthTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return JavaLineLengthCheck.class.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/sizes/javalinelength";
    }

    @Test
    public void testCustomMax() throws Exception {
        final String filePath = getPath("InputXpathJavaLineLengthDefault.java");
        final File fileToProcess = new File(filePath);

        final DefaultConfiguration moduleConfig =
                createModuleConfig(JavaLineLengthCheck.class);
        moduleConfig.addProperty("max", "50");

        final String[] expectedViolations = {
            "5:5: " + getCheckMessage(JavaLineLengthCheck.class, MSG_KEY, 50, 87),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF" +
                        "[./IDENT[@text='InputXpathJavaLineLengthDefault']]" +
                        "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='s']]",
                "/COMPILATION_UNIT/CLASS_DEF" +
                        "[./IDENT[@text='InputXpathJavaLineLengthDefault']]" +
                        "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='s']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF" +
                        "[./IDENT[@text='InputXpathJavaLineLengthDefault']]" +
                        "/OBJBLOCK/VARIABLE_DEF" +
                        "[./IDENT[@text='s']]/TYPE[./IDENT[@text='String']]",
                "/COMPILATION_UNIT/CLASS_DEF" +
                        "[./IDENT[@text='InputXpathJavaLineLengthDefault']]" +
                        "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='s']]/TYPE/IDENT[@text='String']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQueries);
    }

    @Test
    public void testTextBlock() throws Exception {
        final String filePath = getPath("InputXpathJavaLineLengthBlock.java");
        final File fileToProcess = new File(filePath);

        final DefaultConfiguration moduleConfig =
                createModuleConfig(JavaLineLengthCheck.class);
        moduleConfig.addProperty("max", "60");

        final String[] expectedViolations = {
            "8:5: " + getCheckMessage(JavaLineLengthCheck.class, MSG_KEY, 60, 75),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF" +
                        "[./IDENT[@text='InputXpathJavaLineLengthBlock']]" +
                        "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='s2']]",
                "/COMPILATION_UNIT/CLASS_DEF" +
                        "[./IDENT[@text='InputXpathJavaLineLengthBlock']]" +
                        "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='s2']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF" +
                        "[./IDENT[@text='InputXpathJavaLineLengthBlock']]" +
                        "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='s2']]/TYPE" +
                        "[./IDENT[@text='String']]",
                "/COMPILATION_UNIT/CLASS_DEF" +
                        "[./IDENT[@text='InputXpathJavaLineLengthBlock']]" +
                        "/OBJBLOCK/VARIABLE_DEF" +
                        "[./IDENT[@text='s2']]/TYPE/IDENT[@text='String']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQueries);
    }
}
