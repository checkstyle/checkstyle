////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
import java.io.PrintStream;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.RootDoc;

/**
 * Doclet which is used to write property file with short descriptions
 * (first sentences) of TokenTypes' constants.
 *
 * @author o_sukhodolsky
 */
public final class TokenTypesDoclet
{
    /** Command line option to specify file to write output of the doclet. */
    private static final String DEST_FILE_OPT = "-destfile";

    /** Stop instances being created. */
    private TokenTypesDoclet()
    {
    }

    /**
     * The doclet's starter method.
     * @param aRoot <code>RootDoc</code> given to the doclet
     * @exception FileNotFoundException will be thrown if the doclet
     *            will be unable to write to the specified file.
     * @return true if the given <code>RootDoc</code> is processed.
     */
    public static boolean start(RootDoc aRoot) throws FileNotFoundException
    {
        final String fileName = getDestFileName(aRoot.options());
        final FileOutputStream fos = new FileOutputStream(fileName);
        PrintStream ps = null;
        try {
            ps = new PrintStream(fos);
            final ClassDoc[] classes = aRoot.classes();
            if ((classes.length != 1)
                || !"TokenTypes".equals(classes[0].name()))
            {
                final String message =
                    "The doclet should be used for TokenTypes only";
                throw new IllegalArgumentException(message);
            }

            final FieldDoc[] fields = classes[0].fields();
            for (final FieldDoc field : fields) {
                if (field.isStatic() && field.isPublic() && field.isFinal()
                    && "int".equals((field.type().qualifiedTypeName())))
                {
                    if (field.firstSentenceTags().length != 1) {
                        final String message = "Should be only one tag.";
                        throw new IllegalArgumentException(message);
                    }
                    ps.println(field.name() + "="
                        + field.firstSentenceTags()[0].text());
                }
            }
        }
        finally {
            if (ps != null) {
                ps.close();
            }
        }

        return true;
    }

    /**
     * Returns option length (how many parts are in option).
     * @param aOption option name to process
     * @return option length (how many parts are in option).
     */
    public static int optionLength(String aOption)
    {
        if (DEST_FILE_OPT.equals(aOption)) {
            return 2;
        }
        return 0;
    }

    /**
     * Checks that only valid options was specified.
     * @param aOptions all parsed options
     * @param aReporter the reporter to report errors.
     * @return true if only valid options was specified
     */
    public static boolean validOptions(String aOptions[][],
                                       DocErrorReporter aReporter)
    {
        boolean foundDestFileOption = false;
        for (final String[] opt : aOptions) {
            if (DEST_FILE_OPT.equals(opt[0])) {
                if (foundDestFileOption) {
                    aReporter.printError("Only one -destfile option allowed.");
                    return false;
                }
                foundDestFileOption = true;
            }
        }
        if (!foundDestFileOption) {
            final String message =
                "Usage: javadoc -destfile file -doclet TokenTypesDoclet ...";
            aReporter.printError(message);
        }
        return foundDestFileOption;
    }

    /**
     * Reads destination file name.
     * @param aOptions all specified options.
     * @return destination file name
     */
    private static String getDestFileName(String[][] aOptions)
    {
        String fileName = null;
        for (final String[] opt : aOptions) {
            if (DEST_FILE_OPT.equals(opt[0])) {
                fileName = opt[1];
            }
        }
        return fileName;
    }
}
