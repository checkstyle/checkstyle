////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.ArrayList;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.FileReader;

import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

/**
 * Contains utility methods.
 *
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
public final class Utils
{
    /** Map of all created regular expressions **/
    private static final Map CREATED_RES = new HashMap();

    /** stop instances being created **/
    private Utils()
    {
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
     * @param aTabWidth the distance betweeen tab stop position.
     * @return the length of aString.substring(0, aToIdx) with tabs expanded.
     */
    public static int lengthExpandedTabs(String aString,
                                         int aToIdx,
                                         int aTabWidth)
    {
        int len = 0;
        for (int idx = 0; idx < aToIdx; idx++) {
            if (aString.charAt(idx) == '\t') {
                len = (len / aTabWidth + 1) * aTabWidth;
            }
            else {
                len++;
            }
        }
        return len;
    }

    /**
     * This is a factory method to return an RE object for the specified
     * regular expression. This method is not MT safe, but neither are the
     * returned RE objects.
     * @return an RE object for the supplied pattern
     * @param aPattern the regular expression pattern
     * @throws RESyntaxException an invalid pattern was supplied
     **/
    public static RE getRE(String aPattern)
        throws RESyntaxException
    {
        RE retVal = (RE) CREATED_RES.get(aPattern);
        if (retVal == null) {
            retVal = new RE(aPattern);
            CREATED_RES.put(aPattern, retVal);
        }
        return retVal;
    }

    /**
     * Conditionally add a String to a set of properties.
     * @param aProps the Properties to add to
     * @param aKey the key to add the property under
     * @param aValue if not null, then the value to add the property with
     */
    public static void addObjectString(Properties aProps,
                                       String aKey,
                                       Object aValue)
    {
        if (aValue != null) {
            aProps.put(aKey, aValue.toString());
        }
    }

    /**
     * Add a Set add a String to a set of properties. The Set will be encoded
     * by seperating the Set with ",".
     * @param aProps the Properties to add to
     * @param aKey the key to add the property under
     * @param aSet the Set to encode
     */
    public static void addSetString(Properties aProps, String aKey, Set aSet)
    {
        final StringBuffer buf = new StringBuffer();
        final Iterator it = aSet.iterator();
        while (it.hasNext()) {
            buf.append(it.next().toString());
            if (it.hasNext()) {
                buf.append(",");
            }
        }
        aProps.put(aKey, buf.toString());
    }

    /**
     * Loads the contents of a file in a String array.
     * @return the lines in the file
     * @param aFileName the name of the file to load
     * @throws IOException error occurred
     **/
    public static String[] getLines(String aFileName)
        throws IOException
    {
        System.out.println("aFileName = " + aFileName);
        final LineNumberReader lnr =
            new LineNumberReader(new FileReader(aFileName));
        final ArrayList lines = new ArrayList();
        while (true) {
            final String l = lnr.readLine();
            if (l == null) {
                break;
            }
            lines.add(l);
        }

        return (String[]) lines.toArray(new String[0]);
    }
}
