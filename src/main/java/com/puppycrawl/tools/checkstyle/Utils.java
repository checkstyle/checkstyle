////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
package com.puppycrawl.tools.checkstyle;

import java.io.File;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Contains utility methods.
 *
 * @author <a href="mailto:nesterenko-aleksey@list.ru">Aleksey Nesterenko</a>
 */
public final class Utils
{
    /** Shared instance of logger for exception logging. */
    private static final Log EXCEPTION_LOG =
        LogFactory.getLog("com.puppycrawl.tools.checkstyle.ExceptionLog");

    /** stop instances being created **/
    private Utils()
    {
    }

    /**
     * Returns whether the file extension matches what we are meant to
     * process.
     * @param file the file to be checked.
     * @param fileExtensions files extensions, empty property in config makes it matches to all.
     * @return whether there is a match.
     */
    public static boolean fileExtensionMatches(File file, String[] fileExtensions)
    {
        boolean result = false;
        if (fileExtensions == null || fileExtensions.length == 0) {
            result = true;
        }
        else {
            // normalize extensions so all of them have a leading dot
            final String[] withDotExtensions = new String[fileExtensions.length];
            for (int i = 0; i < fileExtensions.length; i++) {
                final String extension = fileExtensions[i];
                if (extension.startsWith(".")) {
                    withDotExtensions[i] = extension;
                }
                else {
                    withDotExtensions[i] = "." + extension;
                }
            }

            final String fileName = file.getName();
            for (final String fileExtension : withDotExtensions) {
                if (fileName.endsWith(fileExtension)) {
                    result = true;
                }
            }
        }

        return result;
    }

    /**
     * Accessor for shared instance of logger which should be
     * used to log all exceptions occurred during <code>FileSetCheck</code>
     * work (<code>debug()</code> should be used).
     * @return shared exception logger.
     */
    public static Log getExceptionLogger()
    {
        return EXCEPTION_LOG;
    }

    /**
     * Returns whether the specified string contains only whitespace up to the
     * specified index.
     *
     * @param index index to check up to
     * @param line the line to check
     * @return whether there is only whitespace
     */
    public static boolean whitespaceBefore(int index, String line)
    {
        for (int i = 0; i < index; i++) {
            if (!Character.isWhitespace(line.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the length of a string ignoring all trailing whitespace. It is a
     * pity that there is not a trim() like method that only removed the
     * trailing whitespace.
     * @param line the string to process
     * @return the length of the string ignoring all trailing whitespace
     **/
    public static int lengthMinusTrailingWhitespace(String line)
    {
        int len = line.length();
        for (int i = len - 1; i >= 0; i--) {
            if (!Character.isWhitespace(line.charAt(i))) {
                break;
            }
            len--;
        }
        return len;
    }

    /**
     * Returns the length of a String prefix with tabs expanded.
     * Each tab is counted as the number of characters is takes to
     * jump to the next tab stop.
     * @param string the input String
     * @param toIdx index in string (exclusive) where the calculation stops
     * @param tabWidth the distance between tab stop position.
     * @return the length of string.substring(0, toIdx) with tabs expanded.
     */
    public static int lengthExpandedTabs(String string,
                                         int toIdx,
                                         int tabWidth)
    {
        int len = 0;
        for (int idx = 0; idx < toIdx; idx++) {
            if (string.charAt(idx) == '\t') {
                len = (len / tabWidth + 1) * tabWidth;
            }
            else {
                len++;
            }
        }
        return len;
    }

    /**
     * Validates whether passed string is a valid pattern or not.
     * @param pattern
     *        string to validate
     * @return true if the pattern is valid false otherwise
     */
    public static boolean isPatternValid(String pattern)
    {
        try {
            Pattern.compile(pattern);
        }
        catch (final PatternSyntaxException e) {
            return false;
        }
        return true;
    }

    /**
     * Helper method to create a regular expression.
     * @param pattern the pattern to match
     * @return a created regexp object
     * @throws ConversionException if unable to create Pattern object.
     **/
    public static Pattern createPattern(String pattern)
        throws ConversionException
    {
        try {
            return Pattern.compile(pattern);
        }
        catch (final PatternSyntaxException e) {
            throw new ConversionException(
                "Failed to initialise regular expression " + pattern, e);
        }
    }

    /**
     * @return the base class name from a fully qualified name
     * @param type the fully qualified name. Cannot be null
     */
    public static String baseClassname(String type)
    {
        final int i = type.lastIndexOf(".");
        return i == -1 ? type : type.substring(i + 1);
    }

    /**
     * Create a stripped down version of a filename.
     * @param basedir the prefix to strip off the original filename
     * @param fileName the original filename
     * @return the filename where an initial prefix of basedir is stripped
     */
    public static String getStrippedFileName(
            final String basedir, final String fileName)
    {
        final String stripped;
        if (basedir == null || !fileName.startsWith(basedir)) {
            stripped = fileName;
        }
        else {
            // making the assumption that there is text after basedir
            final int skipSep = basedir.endsWith(File.separator) ? 0 : 1;
            stripped = fileName.substring(basedir.length() + skipSep);
        }
        return stripped;
    }
}
