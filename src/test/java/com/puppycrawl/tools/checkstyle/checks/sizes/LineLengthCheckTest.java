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
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/sizes/linelength";
    }

    @Test
    public void testSimpleOne()
            throws Exception {
        final String[] expected = {
            "22: " + getCheckMessage(MSG_KEY, 80, 81),
        };
        verifyWithInlineConfigParser(
                getPath("InputLineLengthSimpleOne.java"), expected);
    }

    @Test
    public void testSimpleTwo()
            throws Exception {
        final String[] expected = {
            "88: " + getCheckMessage(MSG_KEY, 80, 83),
        };
        verifyWithInlineConfigParser(
                getPath("InputLineLengthSimpleTwo.java"), expected);
    }

    @Test
    public void shouldLogActualLineLengthOne()
            throws Exception {
        final String[] expected = {
            "23: 80,81",
        };
        verifyWithInlineConfigParser(
                getPath("InputLineLengthSimple1One.java"), expected);
    }

    @Test
    public void shouldLogActualLineLengthTwo()
            throws Exception {
        final String[] expected = {
            "89: 80,83",
        };
        verifyWithInlineConfigParser(
                getPath("InputLineLengthSimple1Two.java"), expected);
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
        final String[] expected = {
            "11: " + getCheckMessage(MSG_KEY, 100, 149),
            "16: " + getCheckMessage(MSG_KEY, 100, 149),
        };
        verifyWithInlineConfigParser(getPath("InputLineLengthUnicodeChars.java"), expected);

    }

    @Test
    public void testLineLengthIgnoringPackageStatements() throws Exception {
        final String[] expected = {
            "13: " + getCheckMessage(MSG_KEY, 75, 76),
            "22: " + getCheckMessage(MSG_KEY, 75, 86),
            "26: " + getCheckMessage(MSG_KEY, 75, 76),
            "34: " + getCheckMessage(MSG_KEY, 75, 77),
        };

        verifyWithInlineConfigParser(
            getNonCompilablePath("InputLineLengthIgnoringPackageStatements.java"), expected);
    }

    @Test
    public void testLineLengthIgnoringImportStatements() throws Exception {
        final String[] expected = {
            "12: " + getCheckMessage(MSG_KEY, 75, 79),
            "21: " + getCheckMessage(MSG_KEY, 75, 81),
            "25: " + getCheckMessage(MSG_KEY, 75, 84),
            "33: " + getCheckMessage(MSG_KEY, 75, 77),
        };

        verifyWithInlineConfigParser(
            getNonCompilablePath("InputLineLengthIgnoringImportStatements.java"), expected);
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
    public void testUnmappableCharacters() throws Exception {
        final String[] expected = {
            "4: " + getCheckMessage(MSG_KEY, 75, 238),
        };

        final DefaultConfiguration checkConfig = createModuleConfig(LineLengthCheck.class);
        checkConfig.addProperty("max", "75");

        final DefaultConfiguration checkerConfig = createRootConfig(checkConfig);
        checkerConfig.addProperty("charset", "IBM1098");

        verify(checkerConfig, getPath("InputLineLengthUnmappableCharacters.java"), expected);
    }
}
