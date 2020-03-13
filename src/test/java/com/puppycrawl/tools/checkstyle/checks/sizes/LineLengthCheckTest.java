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
        final DefaultConfiguration checkConfig =
            createModuleConfig(LineLengthCheck.class);
        checkConfig.addAttribute("max", "80");
        checkConfig.addAttribute("ignorePattern", "^.*is OK.*regexp.*$");
        final String[] expected = {
            "18: " + getCheckMessage(MSG_KEY, 80, 81),
            "145: " + getCheckMessage(MSG_KEY, 80, 83),
        };
        verify(checkConfig, getPath("InputLineLengthSimple.java"), expected);
    }

    @Test
    public void shouldLogActualLineLength()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(LineLengthCheck.class);
        checkConfig.addAttribute("max", "80");
        checkConfig.addAttribute("ignorePattern", "^.*is OK.*regexp.*$");
        checkConfig.addMessage("maxLineLen", "{0},{1}");
        final String[] expected = {
            "18: 80,81",
            "145: 80,83",
        };
        verify(checkConfig, getPath("InputLineLengthSimple.java"), expected);
    }

    @Test
    public void shouldNotLogLongImportStatements() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(LineLengthCheck.class);
        checkConfig.addAttribute("max", "80");
        final String[] expected = {
            "9: " + getCheckMessage(MSG_KEY, 80, 87),
        };
        verify(checkConfig, getPath("InputLineLengthLongImportStatements.java"), expected);
    }

    @Test
    public void shouldNotLogLongPackageStatements() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(LineLengthCheck.class);
        checkConfig.addAttribute("max", "80");
        final String[] expected = {
            "7: " + getCheckMessage(MSG_KEY, 80, 88),
        };
        verify(checkConfig, getNonCompilablePath("InputLineLengthLongPackageStatement.java"),
                expected);
    }

    @Test
    public void shouldNotLogLongLinks() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(LineLengthCheck.class);
        checkConfig.addAttribute("max", "80");
        checkConfig.addAttribute("ignorePattern",
            "^ *\\* *([^ ]+|\\{@code .*|<a href=\"[^\"]+\">)$");
        final String[] expected = {
            "4: " + getCheckMessage(MSG_KEY, 80, 98),
        };
        verify(checkConfig, getPath("InputLineLengthLongLink.java"), expected);
    }

    @Test
    public void countUnicodePointsOnce() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(LineLengthCheck.class);
        checkConfig.addAttribute("max", "100");
        // we need to set charset to let test pass when default charset is not UTF-8
        final DefaultConfiguration checkerConfig = createRootConfig(checkConfig);
        checkerConfig.addAttribute("charset", StandardCharsets.UTF_8.name());

        final String[] expected = {
            "6: " + getCheckMessage(MSG_KEY, 100, 136),
            "7: " + getCheckMessage(MSG_KEY, 100, 136),
        };

        verify(checkerConfig, getPath("InputLineLengthUnicodeChars.java"), expected);

    }

}
