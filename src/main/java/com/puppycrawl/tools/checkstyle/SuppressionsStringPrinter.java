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

package com.puppycrawl.tools.checkstyle;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.xpath.XpathQueryGenerator;

/**
 * Class for constructing xpath queries to suppress nodes
 * with specified line and column number.
 */
public final class SuppressionsStringPrinter {

    /** Line and column number config value pattern. */
    private static final Pattern VALID_SUPPRESSION_LINE_COLUMN_NUMBER_REGEX =
            Pattern.compile("^(\\d+):(\\d+)$");

    /** OS specific line separator. */
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /** Prevent instances. */
    private SuppressionsStringPrinter() {
        // no code
    }

    /**
     * Prints generated suppressions.
     *
     * @param file the file to process.
     * @param suppressionLineColumnNumber line and column number of the suppression
     * @param tabWidth length of the tab character
     * @return generated suppressions.
     * @throws IOException if the file could not be read.
     * @throws IllegalStateException if suppressionLineColumnNumber is not of a valid format.
     * @throws CheckstyleException if the file is not a Java source.
     */
    public static String printSuppressions(File file, String suppressionLineColumnNumber,
                                           int tabWidth) throws IOException, CheckstyleException {
        final Matcher matcher =
                VALID_SUPPRESSION_LINE_COLUMN_NUMBER_REGEX.matcher(suppressionLineColumnNumber);
        if (!matcher.matches()) {
            final String exceptionMsg = String.format(Locale.ROOT,
                    "%s does not match valid format 'line:column'.",
                    suppressionLineColumnNumber);
            throw new IllegalStateException(exceptionMsg);
        }

        final FileText fileText = new FileText(file,
                System.getProperty("file.encoding", StandardCharsets.UTF_8.name()));
        final DetailAST detailAST =
                JavaParser.parseFileText(fileText, JavaParser.Options.WITH_COMMENTS);
        final int lineNumber = Integer.parseInt(matcher.group(1));
        final int columnNumber = Integer.parseInt(matcher.group(2));
        return generate(fileText, detailAST, lineNumber, columnNumber, tabWidth);
    }

    /**
     * Creates {@code XpathQueryGenerator} instance and generates suppressions.
     *
     * @param fileText {@code FileText} object.
     * @param detailAST {@code DetailAST} object.
     * @param lineNumber line number.
     * @param columnNumber column number.
     * @param tabWidth length of the tab character.
     * @return generated suppressions.
     */
    private static String generate(FileText fileText, DetailAST detailAST, int lineNumber,
                                   int columnNumber, int tabWidth) {
        final XpathQueryGenerator queryGenerator =
                new XpathQueryGenerator(detailAST, lineNumber, columnNumber, fileText,
                        tabWidth);
        final List<String> suppressions = queryGenerator.generate();
        return suppressions.stream().collect(Collectors.joining(LINE_SEPARATOR,
                "", LINE_SEPARATOR));
    }
}
