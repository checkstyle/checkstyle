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

// TODO: check that this class is in the right package
// as soon as architecture has settled. At the time of writing
// this class is not necessary as a part of the public api

import java.util.ResourceBundle;
import java.util.Locale;
import java.util.MissingResourceException;
import java.text.MessageFormat;

/**
 * Represents message that can be localised. The translations come from
 * message.properties files. The underlying implementation uses
 * java.text.MessageFormat.
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
public class LocalizedMessage
    implements Comparable
{
    /** the locale to localise messages to **/
    private static Locale sLocale = Locale.getDefault();

    /** the line number **/
    private final int mLineNo;
    /** the column number **/
    private final int mColNo;

    /** key for the message format **/
    private final String mKey;

    /** arguments for MessageFormat **/
    private final Object[] mArgs;

    /** name of the resource bundle to get messages from **/
    private final String mBundle;


    /**
     * Creates a new <code>LocalizedMessage</code> instance.
     *
     * @param aLineNo line number associated with the message
     * @param aColNo column number associated with the message
     * @param aBundle resource bundle name
     * @param aKey the key to locate the translation
     * @param aArgs arguments for the translation
     */
    public LocalizedMessage(int aLineNo,
                            int aColNo,
                            String aBundle,
                            String aKey,
                            Object[] aArgs)
    {
        mLineNo = aLineNo;
        mColNo = aColNo;
        mKey = aKey;
        mArgs = aArgs;
        mBundle = aBundle;
    }

    /**
     * Creates a new <code>LocalizedMessage</code> instance. The column number
     * defaults to 0.
     *
     * @param aLineNo line number associated with the message
     * @param aBundle name of a resource bundle that contains error messages
     * @param aKey the key to locate the translation
     * @param aArgs arguments for the translation
     */
    public LocalizedMessage(
            int aLineNo, String aBundle, String aKey, Object[] aArgs)
    {
        this(aLineNo, 0, aBundle, aKey, aArgs);
    }

    /** @return the translated message **/
    public String getMessage()
    {
        try {
            // PERF: Very simple approach - wait for performance problems.
            // Important to use the default class loader, and not the one in the
            // Configuration object. This is because the class loader in the
            // Configuration is specified by the user for resolving custom
            // classes.
            final ResourceBundle bundle =
                    ResourceBundle.getBundle(mBundle, sLocale);
            final String pattern = bundle.getString(mKey);
            return MessageFormat.format(pattern, mArgs);
        }
        catch (MissingResourceException ex) {
            // If the Check author didn't provide i18n resource bundles
            // and logs error messages directly, this will return
            // the author's original message
            return MessageFormat.format(mKey, mArgs);
        }
    }

    /** @return the line number **/
    public int getLineNo()
    {
        return mLineNo;
    }

    /** @return the column number **/
    public int getColumnNo()
    {
        return mColNo;
    }

    /** @param aLocale the locale to use for localization **/
    public static void setLocale(Locale aLocale)
    {
        sLocale = aLocale;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Interface Comparable methods
    ////////////////////////////////////////////////////////////////////////////

    /** @see java.lang.Comparable **/
    public int compareTo(Object aOther)
    {
        final LocalizedMessage lt = (LocalizedMessage) aOther;
        if (getLineNo() == lt.getLineNo()) {
            if (getColumnNo() == lt.getColumnNo()) {
                return 0;
            }
            return (getColumnNo() < lt.getColumnNo()) ? -1 : 1;
        }

        return (getLineNo() < lt.getLineNo()) ? -1 : 1;
    }
}
