////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.api;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Contains utility methods.
 *
 * @author Oliver Burn
 * @version 1.0
 */
public final class Utils
{
    /** Map of all created regular expressions **/
    private static final Map<String, Pattern> CREATED_RES = Maps.newHashMap();
    /** Shared instance of logger for exception logging. */
    private static final Log EXCEPTION_LOG =
        LogFactory.getLog("com.puppycrawl.tools.checkstyle.ExceptionLog");

    ///CLOVER:OFF
    /** stop instances being created **/
    private Utils()
    {
    }
    ///CLOVER:ON

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
     * @param aIndex index to check up to
     * @param aLine the line to check
     * @return whether there is only whitespace
     */
    public static boolean whitespaceBefore(int aIndex, String aLine)
    {
        for (int i = 0; i < aIndex; i++) {
            if (!Character.isWhitespace(aLine.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the length of a string ignoring all trailing whitespace. It is a
     * pity that there is not a trim() like method that only removed the
     * trailing whitespace.
     * @param aLine the string to process
     * @return the length of the string ignoring all trailing whitespace
     **/
    public static int lengthMinusTrailingWhitespace(String aLine)
    {
        int len = aLine.length();
        for (int i = len - 1; i >= 0; i--) {
            if (!Character.isWhitespace(aLine.charAt(i))) {
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
     * @param aString the input String
     * @param aToIdx index in aString (exclusive) where the calculation stops
     * @param aTabWidth the distance between tab stop position.
     * @return the length of aString.substring(0, aToIdx) with tabs expanded.
     */
    public static int lengthExpandedTabs(String aString,
                                         int aToIdx,
                                         int aTabWidth)
    {
        int len = 0;
        final char[] chars = aString.toCharArray();
        for (int idx = 0; idx < aToIdx; idx++) {
            if (chars[idx] == '\t') {
                len = (len / aTabWidth + 1) * aTabWidth;
            }
            else {
                len++;
            }
        }
        return len;
    }

    /**
     * This is a factory method to return an Pattern object for the specified
     * regular expression. It calls {@link #getPattern(String, int)} with the
     * compile flags defaults to 0.
     * @return an Pattern object for the supplied pattern
     * @param aPattern the regular expression pattern
     * @throws PatternSyntaxException an invalid pattern was supplied
     **/
    public static Pattern getPattern(String aPattern)
        throws PatternSyntaxException
    {
        return getPattern(aPattern, 0);
    }

    /**
     * This is a factory method to return an Pattern object for the specified
     * regular expression and compile flags.
     * <p>
     * This method is not MT safe, but neither are the returned Pattern objects.
     * @return an Pattern object for the supplied pattern
     * @param aPattern the regular expression pattern
     * @param aCompileFlags the compilation flags
     * @throws PatternSyntaxException an invalid pattern was supplied
     **/
    public static Pattern getPattern(String aPattern, int aCompileFlags)
        throws PatternSyntaxException
    {
        final String key = aPattern + ":flags-" + aCompileFlags;
        Pattern retVal = CREATED_RES.get(key);
        if (retVal == null) {
            retVal = Pattern.compile(aPattern, aCompileFlags);
            CREATED_RES.put(key, retVal);
        }
        return retVal;
    }

    /**
     * Loads the contents of a file in a String array.
     * @return the lines in the file
     * @param aFileName the name of the file to load
     * @throws IOException error occurred
     * @deprecated consider using {@link FileText} instead
     **/
    @Deprecated
    public static String[] getLines(String aFileName)
        throws IOException
    {
        return getLines(
            aFileName,
            System.getProperty("file.encoding", "UTF-8"));
    }

    /**
     * Loads the contents of a file in a String array using
     * the named charset.
     * @return the lines in the file
     * @param aFileName the name of the file to load
     * @param aCharsetName the name of a supported charset
     * @throws IOException error occurred
     * @deprecated consider using {@link FileText} instead
     **/
    @Deprecated
    public static String[] getLines(String aFileName, String aCharsetName)
        throws IOException
    {
        final List<String> lines = Lists.newArrayList();
        final FileInputStream fr = new FileInputStream(aFileName);
        LineNumberReader lnr = null;
        try {
            lnr = new LineNumberReader(new InputStreamReader(fr, aCharsetName));
        }
        catch (final UnsupportedEncodingException ex) {
            final String message = "unsupported charset: " + ex.getMessage();
            throw new UnsupportedEncodingException(message);
        }
        try {
            while (true) {
                final String l = lnr.readLine();
                if (l == null) {
                    break;
                }
                lines.add(l);
            }
        }
        finally {
            Utils.closeQuietly(lnr);
        }
        return lines.toArray(new String[lines.size()]);
    }

    /**
     * Helper method to create a regular expression.
     * @param aPattern the pattern to match
     * @return a created regexp object
     * @throws ConversionException if unable to create Pattern object.
     **/
    public static Pattern createPattern(String aPattern)
        throws ConversionException
    {
        Pattern retVal = null;
        try {
            retVal = getPattern(aPattern);
        }
        catch (final PatternSyntaxException e) {
            throw new ConversionException(
                "Failed to initialise regexp expression " + aPattern, e);
        }
        return retVal;
    }

    /**
     * @return the base class name from a fully qualified name
     * @param aType the fully qualified name. Cannot be null
     */
    public static String baseClassname(String aType)
    {
        final int i = aType.lastIndexOf(".");
        return (i == -1) ? aType : aType.substring(i + 1);
    }

    /**
     * Create a stripped down version of a filename.
     * @param aBasedir the prefix to strip off the original filename
     * @param aFileName the original filename
     * @return the filename where an initial prefix of basedir is stripped
     */
    public static String getStrippedFileName(
            final String aBasedir, final String aFileName)
    {
        final String stripped;
        if ((aBasedir == null) || !aFileName.startsWith(aBasedir)) {
            stripped = aFileName;
        }
        else {
            // making the assumption that there is text after basedir
            final int skipSep = aBasedir.endsWith(File.separator) ? 0 : 1;
            stripped = aFileName.substring(aBasedir.length() + skipSep);
        }
        return stripped;
    }

    /**
     * Closes the supplied {@link Closeable} object ignoring an
     * {@link IOException} if it is thrown. Honestly, what are you going to
     * do if you cannot close a file.
     * @param aShutting the object to be closed.
     */
    public static void closeQuietly(Closeable aShutting)
    {
        if (null != aShutting) {
            try {
                aShutting.close();
            }
            catch (IOException e) {
                ; // ignore
            }
        }
    }
}
