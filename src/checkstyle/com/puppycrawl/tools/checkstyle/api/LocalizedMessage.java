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

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Represents a message that can be localised. The translations come from
 * message.properties files. The underlying implementation uses
 * java.text.MessageFormat.
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @author lkuehne
 * @version 1.0
 */
public final class LocalizedMessage
    implements Comparable
{
    /** the locale to localise messages to **/
    private static Locale sLocale = Locale.getDefault();

    /**
     * A cache that maps bundle names to RessourceBundles.
     * Avoids repetitive calls to ResourceBundle.getBundle().
     * TODO: The cache should be cleared at some point.
     */
    private static Map sBundleCache = new HashMap();

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

    /** @see Object#equals */
    public boolean equals(Object aObject)
    {
        if (this == aObject) {
            return true;
        }
        if (!(aObject instanceof LocalizedMessage)) {
            return false;
        }

        final LocalizedMessage localizedMessage = (LocalizedMessage) aObject;

        if (mColNo != localizedMessage.mColNo) {
            return false;
        }
        if (mLineNo != localizedMessage.mLineNo) {
            return false;
        }
        if (!mKey.equals(localizedMessage.mKey)) {
            return false;
        }

        // ignoring mArgs and mBundle for perf reasons.

        // we currently never load the same error from different bundles or
        // fire the same error for the same location with different arguments.

        return true;
    }

    /**
     * @see Object#hashCode
     */
    public int hashCode()
    {
        int result;
        result = mLineNo;
        result = 29 * result + mColNo;
        result = 29 * result + mKey.hashCode();
        return result;
    }

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
            // Important to use the default class loader, and not the one in
            // the GlobalProperties object. This is because the class loader in
            // the GlobalProperties is specified by the user for resolving
            // custom classes.
            final ResourceBundle bundle = getBundle(mBundle);
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

    /**
     * Find a ResourceBundle for a given bundle name.
     * @param aBundleName the bundle name
     * @return a ResourceBundle
     */
    private static ResourceBundle getBundle(String aBundleName)
    {
        ResourceBundle bundle = (ResourceBundle) sBundleCache.get(aBundleName);
        if (bundle == null) {
            bundle = ResourceBundle.getBundle(aBundleName, sLocale);
            sBundleCache.put(aBundleName, bundle);
        }
        return bundle;
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

    /**
     * Returns the message key to locate the translation, can also be used
     * in IDE plugins to map error messages to corrective actions.
     *
     * @return the message key
     */
    public String getKey()
    {
        return mKey;
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
                return mKey.compareTo(lt.mKey);
            }
            return (getColumnNo() < lt.getColumnNo()) ? -1 : 1;
        }

        return (getLineNo() < lt.getLineNo()) ? -1 : 1;
    }
}
