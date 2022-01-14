////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import static com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck.MSG_KEY;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class LineLengthCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/sizes/linelength";
    }

    @Test
    public void testSimple()
            throws Exception {
        final String[] expected = {
            "22: " + getCheckMessage(MSG_KEY, 80, 81),
            "149: " + getCheckMessage(MSG_KEY, 80, 83),
        };
        verifyWithInlineConfigParser(
                getPath("InputLineLengthSimple.java"), expected);
    }

    @Test
    public void shouldLogActualLineLength()
            throws Exception {
        final String[] expected = {
            "23: 80,81",
            "150: 80,83",
        };
        verifyWithInlineConfigParser(
                getPath("InputLineLengthSimple1.java"), expected);
    }

    @Test
    public void shouldNotLogLongImportStatements() throws Exception {
        final String[] expected = {
            "18: " + getCheckMessage(MSG_KEY, 80, 100),
        };
        verifyWithInlineConfigParser(
                getPath("InputLineLengthLongImportStatements.java"), expected);
    }

    @Test
    public void shouldNotLogLongPackageStatements() throws Exception {
        final String[] expected = {
            "17: " + getCheckMessage(MSG_KEY, 80, 100),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputLineLengthLongPackageStatement.java"),
                expected);
    }

    @Test
    public void shouldNotLogLongLinks() throws Exception {
        final String[] expected = {
            "13: " + getCheckMessage(MSG_KEY, 80, 111),
        };
        verifyWithInlineConfigParser(
                getPath("InputLineLengthLongLink.java"), expected);
    }

    @Test
    public void countUnicodePointsOnce() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(LineLengthCheck.class);
        checkConfig.addProperty("max", "100");
        // we need to set charset to let test pass when default charset is not UTF-8
        final DefaultConfiguration checkerConfig = createRootConfig(checkConfig);
        checkerConfig.addProperty("charset", StandardCharsets.UTF_8.name());

        final String[] expected = {
            "15: " + getCheckMessage(MSG_KEY, 100, 149),
            "16: " + getCheckMessage(MSG_KEY, 100, 149),
        };

        verify(checkerConfig, getPath("InputLineLengthUnicodeChars.java"), expected);

    }

}
