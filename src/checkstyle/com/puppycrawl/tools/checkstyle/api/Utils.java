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

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.beanutils.ConversionException;
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

    ///CLOVER:OFF
    /** stop instances being created **/
    private Utils()
    {
    }
    ///CLOVER:ON

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
     * Loads the contents of a file in a String array.
     * @return the lines in the file
     * @param aFileName the name of the file to load
     * @throws IOException error occurred
     **/
    public static String[] getLines(String aFileName)
        throws IOException
    {
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

    /**
     * Helper method to create a regular expression.
     * @param aPattern the pattern to match
     * @return a created regexp object
     * @throws ConversionException if unable to create RE object.
     **/
    public static RE createRE(String aPattern)
        throws ConversionException
    {
        RE retVal = null;
        try {
            retVal = getRE(aPattern);
        }
        catch (RESyntaxException e) {
            throw new ConversionException(
                "Failed to initialise regexp expression " + aPattern, e);
        }
        return retVal;
    }

    /**
     * Replaces <code>${xxx}</code> style constructions in the given value
     * with the string value of the corresponding data types.
     *
     * @param aValue The string to be scanned for property references.
     *              May be <code>null</code>, in which case this
     *              method returns immediately with no effect.
     * @param aProps  Mapping (String to String) of property names to their
     *              values. Must not be <code>null</code>.
     *
     * @throws CheckstyleException if the string contains an opening
     *                           <code>${</code> without a closing
     *                           <code>}</code>
     * @return the original string with the properties replaced, or
     *         <code>null</code> if the original string is <code>null</code>.
     *
     * Code copied from ant -
     * http://cvs.apache.org/viewcvs/jakarta-ant/src/main/org/apache/tools/ant/ProjectHelper.java
     */
    public static String replaceProperties(String aValue,  Properties aProps)
            throws CheckstyleException
    {
        if (aValue == null) {
            return null;
        }

        final List fragments = new ArrayList();
        final List propertyRefs = new ArrayList();
        parsePropertyString(aValue, fragments, propertyRefs);

        final StringBuffer sb = new StringBuffer();
        final Iterator i = fragments.iterator();
        final Iterator j = propertyRefs.iterator();
        while (i.hasNext()) {
            String fragment = (String) i.next();
            if (fragment == null) {
                final String propertyName = (String) j.next();
                if (!aProps.containsKey(propertyName)) {
                    throw new CheckstyleException("Property ${"
                        + propertyName + "} has not been set");
                }
                fragment = (aProps.containsKey(propertyName))
                    ? (String) aProps.get(propertyName)
                    : "${" + propertyName + "}";
            }
            sb.append(fragment);
        }

        return sb.toString();
    }

    /**
     * Parses a string containing <code>${xxx}</code> style property
     * references into two lists. The first list is a collection
     * of text fragments, while the other is a set of string property names.
     * <code>null</code> entries in the first list indicate a property
     * reference from the second list.
     *
     * @param aValue     Text to parse. Must not be <code>null</code>.
     * @param aFragments List to add text fragments to.
     *                  Must not be <code>null</code>.
     * @param aPropertyRefs List to add property names to.
     *                     Must not be <code>null</code>.
     *
     * @throws CheckstyleException if the string contains an opening
     *                           <code>${</code> without a closing
     *                           <code>}</code>
     * Code copied from ant -
     * http://cvs.apache.org/viewcvs/jakarta-ant/src/main/org/apache/tools/ant/ProjectHelper.java
     */
    public static void parsePropertyString(String aValue,
                                           List aFragments,
                                           List aPropertyRefs)
        throws CheckstyleException
    {
        int prev = 0;
        int pos;
        //search for the next instance of $ from the 'prev' position
        while ((pos = aValue.indexOf("$", prev)) >= 0) {

            //if there was any text before this, add it as a fragment
            //TODO, this check could be modified to go if pos>prev;
            //seems like this current version could stick empty strings
            //into the list
            if (pos > 0) {
                aFragments.add(aValue.substring(prev, pos));
            }
            //if we are at the end of the string, we tack on a $
            //then move past it
            if (pos == (aValue.length() - 1)) {
                aFragments.add("$");
                prev = pos + 1;
            }
            else if (aValue.charAt(pos + 1) != '{') {
                //peek ahead to see if the next char is a property or not
                //not a property: insert the char as a literal
                /*
                fragments.addElement(value.substring(pos + 1, pos + 2));
                prev = pos + 2;
                */
                if (aValue.charAt(pos + 1) == '$') {
                    //backwards compatibility two $ map to one mode
                    aFragments.add("$");
                    prev = pos + 2;
                }
                else {
                    //new behaviour: $X maps to $X for all values of X!='$'
                    aFragments.add(aValue.substring(pos, pos + 2));
                    prev = pos + 2;
                }

            }
            else {
                //property found, extract its name or bail on a typo
                final int endName = aValue.indexOf('}', pos);
                if (endName < 0) {
                    throw new CheckstyleException("Syntax error in property: "
                                                    + aValue);
                }
                final String propertyName = aValue.substring(pos + 2, endName);
                aFragments.add(null);
                aPropertyRefs.add(propertyName);
                prev = endName + 1;
            }
        }
        //no more $ signs found
        //if there is any tail to the file, append it
        if (prev < aValue.length()) {
            aFragments.add(aValue.substring(prev));
        }
    }
}
