////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.doclets;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Tag;

/**
 * Doclet which is used to write property file with short descriptions
 * (first sentences) of TokenTypes' constants.
 * Request: 724871
 * For ide plugins (like the eclipse plugin) it would be useful to have
 * programmatic access to the first sentence of the TokenType constants,
 * so they can use them in their configuration gui.
 * @author o_sukhodolsky
 */
public final class TokenTypesDoclet {
    /** Command line option to specify file to write output of the doclet. */
    private static final String DEST_FILE_OPT = "-destfile";

    /** Stop instances being created. */
    private TokenTypesDoclet() {
    }

    /**
     * The doclet's starter method.
     * @param root {@code RootDoc} given to the doclet
     * @return true if the given {@code RootDoc} is processed.
     * @exception FileNotFoundException will be thrown if the doclet
     *            will be unable to write to the specified file.
     */
    public static boolean start(RootDoc root)
            throws FileNotFoundException {
        final String fileName = getDestFileName(root.options());
        final FileOutputStream fos = new FileOutputStream(fileName);
        final Writer osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        final PrintWriter writer = new PrintWriter(osw, false);

        try {
            final ClassDoc[] classes = root.classes();
            final FieldDoc[] fields = classes[0].fields();
            for (final FieldDoc field : fields) {
                if (field.isStatic() && field.isPublic() && field.isFinal()
                    && "int".equals(field.type().qualifiedTypeName())) {
                    // We have to filter "Text" tags because of jdk parsing bug
                    // till Oracle reference id: 9050448
                    if (field.firstSentenceTags().length != 1
                            && Arrays.stream(field.firstSentenceTags())
                            .filter(tag -> !"Text".equals(tag.name())).count() != 1) {
                        final List<Tag> tags = Arrays.asList(field.firstSentenceTags());
                        final String joinedTags = tags
                            .stream()
                            .map(tag -> tag.toString())
                            .collect(Collectors.joining("\", \"", "[\"", "\"]"));
                        final String message = String.format(Locale.ROOT,
                                "Should be only one tag for %s. Tags %s.",
                                field.toString(), joinedTags);
                        throw new IllegalArgumentException(message);
                    }
                    writer.println(field.name() + "="
                            + field.firstSentenceTags()[0].text());
                }
            }
        }
        finally {
            writer.close();
        }

        return true;
    }

    /**
     * Returns option length (how many parts are in option).
     * @param option option name to process
     * @return option length (how many parts are in option).
     */
    public static int optionLength(String option) {
        int length = 0;
        if (DEST_FILE_OPT.equals(option)) {
            length = 2;
        }
        return length;
    }

    /**
     * Checks that only valid options was specified.
     * @param options all parsed options
     * @param reporter the reporter to report errors.
     * @return true if only valid options was specified
     */
    public static boolean checkOptions(String[][] options, DocErrorReporter reporter) {
        boolean foundDestFileOption = false;
        boolean onlyOneDestFileOption = true;
        for (final String[] opt : options) {
            if (DEST_FILE_OPT.equals(opt[0])) {
                if (foundDestFileOption) {
                    reporter.printError("Only one -destfile option allowed.");
                    onlyOneDestFileOption = false;
                    break;
                }
                foundDestFileOption = true;
            }
        }
        if (!foundDestFileOption) {
            reporter.printError("Usage: javadoc -destfile file -doclet TokenTypesDoclet ...");
        }
        return onlyOneDestFileOption && foundDestFileOption;
    }

    /**
     * Reads destination file name.
     * @param options all specified options.
     * @return destination file name
     */
    private static String getDestFileName(String[]... options) {
        String fileName = null;
        for (final String[] opt : options) {
            if (DEST_FILE_OPT.equals(opt[0])) {
                fileName = opt[1];
            }
        }
        return fileName;
    }
}
