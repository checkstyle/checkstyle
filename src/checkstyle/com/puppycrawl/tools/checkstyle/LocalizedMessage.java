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
package com.puppycrawl.tools.checkstyle;

import java.util.ResourceBundle;
import java.text.MessageFormat;

/**
 * Represents message that can be localised. The translations come from
 * message.properties files. The underlying implementation uses
 * java.text.MessageFormat.
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
class LocalizedMessage
    implements Comparable
{
    /** name of the resource bundle to get messages from **/
    private static final String MESSAGE_BUNDLE =
        "com.puppycrawl.tools.checkstyle.messages";

    /** the line number **/
    private final int mLineNo;
    /** the column number **/
    private final int mColNo;

    /** key for the message format **/
    private final String mKey;

    /** arguments for MessageFormat **/
    private final Object[] mArgs;

    /**
     * Creates a new <code>LocalizedMessage</code> instance.
     *
     * @param aLineNo line number associated with the message
     * @param aColNo column number associated with the message
     * @param aKey the key to locate the translation
     * @param aArgs arguments for the translation
     */
    LocalizedMessage(int aLineNo, int aColNo, String aKey, Object[] aArgs)
    {
        mLineNo = aLineNo;
        mColNo = aColNo;
        mKey = aKey;
        mArgs = aArgs;
    }

    /**
     * Creates a new <code>LocalizedMessage</code> instance. The column number
     * defaults to 0.
     *
     * @param aLineNo line number associated with the message
     * @param aKey the key to locate the translation
     * @param aArgs arguments for the translation
     */
    LocalizedMessage(int aLineNo, String aKey, Object[] aArgs)
    {
        this(aLineNo, 0, aKey, aArgs);
    }

    /** @return the translated message **/
    String getMessage()
    {
        // Very simple approach - wait for performance problems
        final ResourceBundle bundle = ResourceBundle.getBundle(MESSAGE_BUNDLE);
        final String pattern = bundle.getString(mKey);
        return MessageFormat.format(pattern, mArgs);
    }

    /** @return the line number **/
    int getLineNo()
    {
        return mLineNo;
    }

    /** @return the column number **/
    public int getColumnNo()
    {
        return mColNo;
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
