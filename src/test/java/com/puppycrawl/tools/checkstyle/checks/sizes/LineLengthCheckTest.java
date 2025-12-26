///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.sizes;

import static com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck.MSG_KEY;

import java.nio.charset.CodingErrorAction;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import de.thetaphi.forbiddenapis.SuppressForbidden;

public class LineLengthCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/sizes/linelength";
    }

    @Test
    void simpleOne()
            throws Exception {
        final String[] expected = {
            "22: " + getCheckMessage(MSG_KEY, 80, 131),
        };
        verifyWithInlineConfigParser(
                getPath("InputLineLengthSimpleOne.java"), expected);
    }

    @Test
    void simpleTwo()
            throws Exception {
        final String[] expected = {
            "88: " + getCheckMessage(MSG_KEY, 80, 133),
        };
        verifyWithInlineConfigParser(
                getPath("InputLineLengthSimpleTwo.java"), expected);
    }

    @Test
    void shouldLogActualLineLengthOne()
            throws Exception {
        final String[] expected = {
            "23: 80,90",
        };
        verifyWithInlineConfigParser(
                getPath("InputLineLengthSimple1One.java"), expected);
    }

    @Test
    void shouldLogActualLineLengthTwo()
            throws Exception {
        final String[] expected = {
            "89: 80,92",
        };
        verifyWithInlineConfigParser(
                getPath("InputLineLengthSimple1Two.java"), expected);
    }

    @Test
    void shouldNotLogLongImportStatements() throws Exception {
        final String[] expected = {
            "18: " + getCheckMessage(MSG_KEY, 80, 150),
        };
        verifyWithInlineConfigParser(
                getPath("InputLineLengthLongImportStatements.java"), expected);
    }

    @Test
    void shouldNotLogLongPackageStatements() throws Exception {
        final String[] expected = {
            "17: " + getCheckMessage(MSG_KEY, 80, 88),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputLineLengthLongPackageStatement.java"),
                expected);
    }

    @Test
    void shouldNotLogLongLinks() throws Exception {
        final String[] expected = {
            "13: " + getCheckMessage(MSG_KEY, 80, 161),
        };
        verifyWithInlineConfigParser(
                getPath("InputLineLengthLongLink.java"), expected);
    }

    @Test
    void countUnicodePointsOnce() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(MSG_KEY, 100, 200),
            "16: " + getCheckMessage(MSG_KEY, 100, 200),
        };
        verifyWithInlineConfigParser(getPath("InputLineLengthUnicodeChars.java"), expected);

    }

    @Test
    void lineLengthIgnoringPackageStatements() throws Exception {
        final String[] expected = {
            "17: " + getCheckMessage(MSG_KEY, 75, 86),
            "21: " + getCheckMessage(MSG_KEY, 75, 76),
            "29: " + getCheckMessage(MSG_KEY, 75, 77),
        };

        verifyWithInlineConfigParser(
            getPath("InputLineLengthIgnoringPackageStatements.java"), expected);
    }

    @Test
    void lineLengthIgnoringImportStatements() throws Exception {
        final String[] expected = {
            "18: " + getCheckMessage(MSG_KEY, 75, 81),
            "22: " + getCheckMessage(MSG_KEY, 75, 84),
            "30: " + getCheckMessage(MSG_KEY, 75, 77),
        };

        verifyWithInlineConfigParser(
            getPath("InputLineLengthIgnoringImportStatements.java"), expected);
    }

    /**
     * This tests that the check does not fail when the file contains unmappable characters.
     * Unmappable characters should be replaced with the default replacement character
     * {@link CodingErrorAction#REPLACE}. For example, the 0x80 (hex.) character is unmappable
     * in the IBM1098 charset.
     *
     * @throws Exception exception
     */
    @SuppressForbidden
    @Test
    void unmappableCharacters() throws Exception {
        final String[] expected = {
            "4: " + getCheckMessage(MSG_KEY, 75, 288),
        };

        final DefaultConfiguration checkConfig = createModuleConfig(LineLengthCheck.class);
        checkConfig.addProperty("max", "75");

        final DefaultConfiguration checkerConfig = createRootConfig(checkConfig);
        checkerConfig.addProperty("charset", "IBM1098");

        verify(checkerConfig, getPath("InputLineLengthUnmappableCharacters.java"), expected);
    }
}
