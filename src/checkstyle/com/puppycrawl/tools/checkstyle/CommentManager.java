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

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Checks can query the CommentManager if they want to analyse comments.
 * <p>
 * The AST tree does not contain comments as they can occur at arbitrary places.
 * Adding them to the AST tree would make it very hard to write checks, as you would
 * always have to beware of comment nodes.
 * </p>
 * <p>
 * Instead, during Java parsing comments are registered with the CommentManager, and
 * Checks that need to analyse comments can use the CommentManager to access that data.
 * </p>
 * <p>
 * Sample Checks that use the CommentManager are the to-do check and the javadoc check.
 * </p>
 * @author Lars Kühne
 */
public class CommentManager
{
    private String[] mLines;

    /** maps end line numbers of javadoc comments to the comment contents. */
    private final SortedMap mJavaDoc = new TreeMap();

    /** maps end line numbers of C comments to the comment contents. */
        private final SortedMap mCComments = new TreeMap();

    /** maps line numbers of CPP comments to the comment contents. */
    private final SortedMap mCppComments = new TreeMap();

    private static class CommentIterator implements Iterator
    {
        public boolean hasNext()
        {
            // TODO: implement this for real
            return false;
        }

        public Object next()
        {
            // TODO: implement this for real
            return null;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * creates a new CommentManager instance.
     * This should be called for every file that is checked.
     * @param aLines all lines in a file.
     */
    CommentManager(String[] aLines)
    {
        mLines = aLines;
    }

    /**
     * Report the location of a CPP-style comment.
     * @param aStartLineNo the starting line number
     * @param aStartColNo the starting column number
     **/
    void reportCPPComment(int aStartLineNo, int aStartColNo)
    {
        final String cmt = mLines[aStartLineNo - 1].substring(aStartColNo);
        Integer line = new Integer(aStartLineNo);
        mCppComments.put(line, cmt);
    }

    /**
     * Report the location of a C-style comment.
     * @param aStartLineNo the starting line number
     * @param aStartColNo the starting column number
     * @param aEndLineNo the ending line number
     * @param aEndColNo the ending column number
     **/
    void reportCComment(int aStartLineNo, int aStartColNo,
                        int aEndLineNo, int aEndColNo)
    {
        final String[] cc = extractCComment(aStartLineNo, aStartColNo,
                                            aEndLineNo, aEndColNo);

        Integer endline = new Integer(aEndLineNo - 1);

        if (mLines[aStartLineNo - 1].indexOf("/**", aStartColNo) != -1) {
            mJavaDoc.put(endline, cc);
        }
        else {
            mCComments.put(endline, cc);
        }
    }

    /**
     * Returns the specified C comment as a String array.
     * @return C comment as a array
     * @param aStartLineNo the starting line number
     * @param aStartColNo the starting column number
     * @param aEndLineNo the ending line number
     * @param aEndColNo the ending column number
     **/
    private String[] extractCComment(int aStartLineNo, int aStartColNo,
                                     int aEndLineNo, int aEndColNo)
    {
        String[] retVal;
        if (aStartLineNo == aEndLineNo) {
            retVal = new String[1];
            retVal[0] = mLines[aStartLineNo - 1].substring(aStartColNo,
                                                           aEndColNo + 1);
        }
        else {
            retVal = new String[aEndLineNo - aStartLineNo + 1];
            retVal[0] = mLines[aStartLineNo - 1].substring(aStartColNo);
            for (int i = aStartLineNo; i < aEndLineNo; i++) {
                retVal[i - aStartLineNo + 1] = mLines[i];
            }
            retVal[retVal.length - 1] =
                mLines[aEndLineNo - 1].substring(0, aEndColNo + 1);
        }
        return retVal;
    }

    // PUBLIC API

    /**
     * An iterator over all comments.
     * @return an iterator that contains entries of type String (CPP style comments)
     *  or String[] (C style comments)
     */
    public Iterator iterator()
    {
      return new CommentIterator();
    }

    /**
     * Returns the Javadoc comment before the specified line. null is none.
     * @return the Javadoc comment, or <code>null</code> if none
     * @param aLineNo the line number to check before
     **/
    public String[] getJavadocBefore(int aLineNo)
    {
        Object key = mJavaDoc.headMap(new Integer(aLineNo)).lastKey();
        return (String[]) mJavaDoc.get(key);
    }

}
