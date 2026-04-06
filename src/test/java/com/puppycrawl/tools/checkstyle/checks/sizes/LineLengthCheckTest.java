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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import static com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck.MSG_KEY;

import java.nio.charset.StandardCharsets;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import de.thetaphi.forbiddenapis.SuppressForbidden;

public class LineLengthCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/sizes/linelength";
    }

    @Test
    public void testSimpleOne()
            throws Exception {
        final String[] expected = {
            "22: " + getCheckMessage(MSG_KEY, 80, 131),
        };
        verifyWithInlineConfigParser(
                getPath("InputLineLengthSimpleOne.java"), expected);
    }

    @Test
    public void testSimpleTwo()
            throws Exception {
        final String[] expected = {
            "88: " + getCheckMessage(MSG_KEY, 80, 133),
        };
        verifyWithInlineConfigParser(
                getPath("InputLineLengthSimpleTwo.java"), expected);
    }

    @Test
    public void shouldLogActualLineLengthOne()
            throws Exception {
        final String[] expected = {
            "23: 80,90",
        };
        verifyWithInlineConfigParser(
                getPath("InputLineLengthSimple1One.java"), expected);
    }

    @Test
    public void shouldLogActualLineLengthTwo()
            throws Exception {
        final String[] expected = {
            "89: 80,92",
        };
        verifyWithInlineConfigParser(
                getPath("InputLineLengthSimple1Two.java"), expected);
    }

    @Test
    public void shouldNotLogLongImportStatements() throws Exception {
        final String[] expected = {
            "18: " + getCheckMessage(MSG_KEY, 80, 150),
        };
        verifyWithInlineConfigParser(
                getPath("InputLineLengthLongImportStatements.java"), expected);
    }

    @Test
    public void shouldNotLogLongPackageStatements() throws Exception {
        final String[] expected = {
            "17: " + getCheckMessage(MSG_KEY, 80, 88),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputLineLengthLongPackageStatement.java"),
                expected);
    }

    @Test
    public void shouldNotLogLongLinks() throws Exception {
        final String[] expected = {
            "13: " + getCheckMessage(MSG_KEY, 80, 161),
        };
        verifyWithInlineConfigParser(
                getPath("InputLineLengthLongLink.java"), expected);
    }

    @Test
    public void countUnicodePointsOnce() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(MSG_KEY, 100, 200),
            "16: " + getCheckMessage(MSG_KEY, 100, 200),
        };
        verifyWithInlineConfigParser(getPath("InputLineLengthUnicodeChars.java"), expected);

    }

    @Test
    public void testLineLengthIgnoringPackageStatements() throws Exception {
        final String[] expected = {
            "17: " + getCheckMessage(MSG_KEY, 75, 86),
            "29: " + getCheckMessage(MSG_KEY, 75, 77),
        };

        verifyWithInlineConfigParser(
            getPath("InputLineLengthIgnoringPackageStatements.java"), expected);
    }

    @Test
    public void testLineLengthIgnoringImportStatements() throws Exception {
        final String[] expected = {
            "18: " + getCheckMessage(MSG_KEY, 75, 81),
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
    public void testUnmappableCharacters() throws Exception {
        final String[] expected = {
            "11: " + getCheckMessage(MSG_KEY, 75, 288),
        };

        final DefaultConfiguration checkConfig = createModuleConfig(LineLengthCheck.class);
        checkConfig.addProperty("max", "75");

        final DefaultConfiguration checkerConfig = createRootConfig(checkConfig);
        checkerConfig.addProperty("charset", "IBM1098");

        verify(checkerConfig, getPath("InputLineLengthUnmappableCharacters.java"), expected);
    }

    @Test
    public void testTextBlockContentIsIgnored() throws Exception {
        final String[] expected = {
            "19: " + getCheckMessage(MSG_KEY, 80, 131),
        };

        verifyWithInlineConfigParser(
                getPath("InputLineLengthTextBlock.java"), expected);
    }

    @Test
    public void testEscapedTextBlockDelimiterDoesNotCloseBlock() throws Exception {
        final String[] expected = {
            "20: " + getCheckMessage(MSG_KEY, 80, 92),
        };

        verifyWithInlineConfigParser(
                getPath("InputLineLengthEscapedDelimiter.java"), expected);
    }

    @Test
    public void testEvenBackslashesBeforeDelimiterAtLineStart(@TempDir Path tempDir)
            throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_KEY, 80, 88),
        };

        final Path inputFile = tempDir.resolve("InputLineLengthEscapedDelimiterAtLineStart.java");
        final String input = String.join(System.lineSeparator(),
                "/*",
                "LineLength",
                "fileExtensions = (default)null",
                "ignorePattern = ^(package|import) .*$",
                "max = (default)80",
                "tabWidth = (default)0",
                "",
                "*/",
                "package com.puppycrawl.tools.checkstyle.checks.sizes.linelength;",
                "",
                "public class InputLineLengthEscapedDelimiterAtLineStart {",
                "String text = \"\"\"",
                "\\\\\"\"\"",
                "String longLine = \"This is a regular string that is long enough to trigger a violation\";",
                "// violation above, 'Line is longer than 80 characters (found 88).'",
                "}",
                "");
        Files.writeString(inputFile, input, StandardCharsets.UTF_8);
        verifyWithInlineConfigParser(inputFile.toString(), expected);
    }

    @Test
    public void testNonJavaFileStillCheckedForLineLength() throws Exception {
        final String[] expected = {
            "3: " + getCheckMessage(MSG_KEY, 80, 90),
        };

        verifyWithInlineConfigParserSeparateConfigAndTarget(
                getPath("InputLineLengthNonJavaFileConfig.java"),
                getPath("InputLineLengthNonJavaFile.txt"),
                expected);
    }
}
