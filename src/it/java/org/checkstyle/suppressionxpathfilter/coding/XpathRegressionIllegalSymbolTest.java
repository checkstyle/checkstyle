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
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalSymbolCheck;

public class XpathRegressionIllegalSymbolTest extends AbstractXpathTestSupport {

    private final String checkName = IllegalSymbolCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/illegalsymbol";
    }

    @Test
    public void testEmoji() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathIllegalSymbolEmoji.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IllegalSymbolCheck.class);
        moduleConfig.addProperty("symbolCodes", "0x2705");

        final String[] expectedViolation = {
            "4:18: " + getCheckMessage(IllegalSymbolCheck.class, IllegalSymbolCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathIllegalSymbolEmoji']]"
                + "/OBJBLOCK/SINGLE_LINE_COMMENT/COMMENT_CONTENT[@text=' \u2705 // warn\\r\\n']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testAsciiOnly() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathIllegalSymbolAsciiOnly.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IllegalSymbolCheck.class);
        moduleConfig.addProperty("asciiOnly", "true");

        final String[] expectedViolation = {
            "4:18: " + getCheckMessage(IllegalSymbolCheck.class, IllegalSymbolCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathIllegalSymbolAsciiOnly']]"
                + "/OBJBLOCK/SINGLE_LINE_COMMENT/COMMENT_CONTENT[@text=' caf\u00E9 // warn\\n']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
