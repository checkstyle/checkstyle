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

import java.io.Reader;
import java.io.IOException;

/**
 * A Reader that reads from an underlying String array, assuming each
 * array element corresponds to one line of text.
 *
 * @author <a href="mailto:lkuehne@users.sourceforge.net">Lars Kühne</a>
 */
class StringArrayReader extends Reader
{
    /** the underlying String array */
    private final String[] mUnderlyingArray;

    /** the index of the currently read String */
    private int mArrayIdx = 0;

    /** the index of the next character to be read */
    private int mStringIdx = 0;

    /** flag to tell whether an implicit newline has to be reported */
    private boolean mUnreportedNewline = false;

    /** flag to tell if the reader has been closed */
    private boolean mClosed = false;

    /**
     * Creates a new StringArrayReader.
     *
     * @param aUnderlyingArray the underlying String array.
     */
    StringArrayReader(String[] aUnderlyingArray)
    {
        final int length = aUnderlyingArray.length;
        mUnderlyingArray = new String[length];
        System.arraycopy(aUnderlyingArray, 0, mUnderlyingArray, 0, length);
    }

    /** @see Reader */
    public void close()
    {
        mClosed = true;
    }

    /** @return whether data is available that has not yet been read. */
    private boolean dataAvailable()
    {
        return (mUnderlyingArray.length > mArrayIdx);
    }

    /** @see Reader */
    public int read( char[] aCbuf, int aOff, int aLen ) throws IOException
    {
        if (mClosed) {
            throw new IOException("already closed");
        }

        int retVal = 0;

        if (!mUnreportedNewline && (mUnderlyingArray.length <= mArrayIdx)) {
            return -1;
        }

        while ((retVal < aLen) && (mUnreportedNewline || dataAvailable())) {
            if (mUnreportedNewline) {
                aCbuf[aOff + retVal] = '\n';
                retVal++;
                mUnreportedNewline = false;
            }

            if ((retVal < aLen) && dataAvailable()) {
                final String currentStr = mUnderlyingArray[mArrayIdx];
                final int srcEnd = Math.min(currentStr.length(),
                                            mStringIdx + aLen - retVal);
                currentStr.getChars(mStringIdx, srcEnd, aCbuf, aOff + retVal);
                retVal += srcEnd - mStringIdx;
                mStringIdx = srcEnd;

                if (mStringIdx >= currentStr.length()) {
                    mArrayIdx++;
                    mStringIdx = 0;
                    mUnreportedNewline = true;
                }
            }
        }
        return retVal;
    }
}
