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

import java.util.Collections;
import java.util.ArrayList;

/**
 * Collection of messages.
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
public class LocalizedMessages
{
    /** contains the messages logged **/
    private final ArrayList mMessages = new ArrayList();
    /** the tabwidth to calculate columns **/
    private final int mTabWidth;
    /** the lines of the file being checked **/
    private String[] mLines;

    /**
     * Creates a new <code>LocalizedMessages</code> instance.
     *
     * @param aTabWidth the tab width to calculate columns with
     */
    public LocalizedMessages(int aTabWidth)
    {
        mTabWidth = aTabWidth;
    }

    /** @param aLines the lines to record messages against **/
    public void setLines(String[] aLines)
    {
        mLines = aLines;
    }

    /** @return the logged messages **/
    public LocalizedMessage[] getMessages()
    {
        Collections.sort(mMessages);
        return (LocalizedMessage[])
            mMessages.toArray(new LocalizedMessage[mMessages.size()]);
    }

    /** Reset the object **/
    public void reset()
    {
        mMessages.clear();
        mLines = null;
    }

    /**
     * Logs a message to be reported
     * @param aMsg the message to log
     **/
    public void add(LocalizedMessage aMsg)
    {
        mMessages.add(aMsg);
    }

    /**
     * Helper method to log a LocalizedMessage. Column defaults to 0.
     *
     * @param aLineNo line number to associate with the message
     * @param aKey key to locale message format
     * @param aArgs arguments for message
     */
    public void add(int aLineNo, String aKey, Object[] aArgs)
    {
        add(new LocalizedMessage(aLineNo, 0, aKey, aArgs));
    }

    /**
     * Helper method to log a LocalizedMessage. Column defaults to 0.
     *
     * @param aLineNo line number to associate with the message
     * @param aKey key to locale message format
     */
    public void add(int aLineNo, String aKey)
    {
        add(aLineNo, aKey, new Object[0]);
    }

    /**
     * Helper method to log a LocalizedMessage. Column defaults to 0.
     *
     * @param aLineNo line number to associate with the message
     * @param aKey key to locale message format
     * @param aArg0 first argument
     */
    public void add(int aLineNo, String aKey, Object aArg0)
    {
        add(aLineNo, aKey, new Object[] {aArg0});
    }

    /**
     * Helper method to log a LocalizedMessage. Column defaults to 0.
     *
     * @param aLineNo line number to associate with the message
     * @param aKey key to locale message format
     * @param aArg0 first argument
     * @param aArg1 second argument
     */
    public void add(int aLineNo, String aKey, Object aArg0, Object aArg1)
    {
        add(aLineNo, aKey, new Object[] {aArg0, aArg1});
    }

    /**
     * Helper method to log a LocalizedMessage. Column defaults to 0.
     *
     * @param aLineNo line number to associate with the message
     * @param aKey key to locale message format
     * @param aArg0 first argument
     * @param aArg1 second argument
     * @param aArg2 third argument
     */
    public void add(int aLineNo, String aKey,
             Object aArg0, Object aArg1, Object aArg2)
    {
        add(aLineNo, aKey, new Object[] {aArg0, aArg1, aArg2});
    }

    /**
     * Helper method to log a LocalizedMessage.
     *
     * @param aLineNo line number to associate with the message
     * @param aColNo column number to associate with the message
     * @param aKey key to locale message format
     * @param aArgs arguments for message
     */
    public void add(int aLineNo, int aColNo, String aKey, Object[] aArgs)
    {
        final int col = 1 + Utils.lengthExpandedTabs(
            mLines[aLineNo - 1], aColNo, mTabWidth);
        mMessages.add(new LocalizedMessage(aLineNo, col, aKey, aArgs));
    }

    /**
     * Helper method to log a LocalizedMessage.
     *
     * @param aLineNo line number to associate with the message
     * @param aColNo column number to associate with the message
     * @param aKey key to locale message format
     */
    public void add(int aLineNo, int aColNo, String aKey)
    {
        add(aLineNo, aColNo, aKey, new Object[0]);
    }

    /**
     * Helper method to log a LocalizedMessage.
     *
     * @param aLineNo line number to associate with the message
     * @param aColNo column number to associate with the message
     * @param aKey key to locale message format
     * @param aArg0 first argument
     */
    public void add(int aLineNo, int aColNo, String aKey, Object aArg0)
    {
        add(aLineNo, aColNo, aKey, new Object[] {aArg0});
    }

    /**
     * Helper method to log a LocalizedMessage.
     *
     * @param aLineNo line number to associate with the message
     * @param aColNo column number to associate with the message
     * @param aKey key to locale message format
     * @param aArg0 first argument
     * @param aArg1 second argument
     */
    public void add(int aLineNo, int aColNo, String aKey,
             Object aArg0, Object aArg1)
    {
        add(aLineNo, aColNo, aKey, new Object[] {aArg0, aArg1});
    }

    /**
     * Helper method to log a LocalizedMessage.
     *
     * @param aLineNo line number to associate with the message
     * @param aColNo column number to associate with the message
     * @param aKey key to locale message format
     * @param aArg0 first argument
     * @param aArg1 second argument
     * @param aArg2 third argument
     */
    void add(int aLineNo, int aColNo, String aKey,
             Object aArg0, Object aArg1, Object aArg2)
    {
        add(aLineNo, aColNo, aKey, new Object[] {aArg0, aArg1, aArg2});
    }
}
