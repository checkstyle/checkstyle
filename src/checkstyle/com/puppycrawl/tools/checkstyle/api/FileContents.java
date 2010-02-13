////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2010  Oliver Burn
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

import com.google.common.collect.ImmutableMap;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.puppycrawl.tools.checkstyle.grammars.CommentListener;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Represents the contents of a file.
 *
 * @author Oliver Burn
 * @version 1.0
 */
public final class FileContents implements CommentListener
{
    /**
     * the pattern to match a single line comment containing only the comment
     * itself -- no code.
     */
    private static final String MATCH_SINGLELINE_COMMENT_PAT = "^\\s*//.*$";
    /** compiled regexp to match a single-line comment line */
    private static final Pattern MATCH_SINGLELINE_COMMENT = Pattern
            .compile(MATCH_SINGLELINE_COMMENT_PAT);

    /** the file name */
    private final String mFilename;

    /** the lines */
    private final String[] mLines;

    /** map of the Javadoc comments indexed on the last line of the comment.
     * The hack is it assumes that there is only one Javadoc comment per line.
     */
    private final Map<Integer, TextBlock> mJavadocComments = Maps.newHashMap();
    /** map of the C++ comments indexed on the first line of the comment. */
    private final Map<Integer, TextBlock> mCPlusPlusComments =
        Maps.newHashMap();

    /**
     * map of the C comments indexed on the first line of the comment to a list
     * of comments on that line
     */
    private final Map<Integer, List<TextBlock>> mCComments = Maps.newHashMap();

    /**
     * Creates a new <code>FileContents</code> instance.
     *
     * @param aFilename name of the file
     * @param aLines the contents of the file
     */
    public FileContents(String aFilename, String[] aLines)
    {
        mFilename = aFilename;
        mLines = aLines.clone();
    }

    /** {@inheritDoc} */
    public void reportSingleLineComment(String aType, int aStartLineNo,
            int aStartColNo)
    {
        reportCppComment(aStartLineNo, aStartColNo);
    }

    /** {@inheritDoc} */
    public void reportBlockComment(String aType, int aStartLineNo,
            int aStartColNo, int aEndLineNo, int aEndColNo)
    {
        reportCComment(aStartLineNo, aStartColNo, aEndLineNo, aEndColNo);
    }

    /**
     * Report the location of a C++ style comment.
     * @param aStartLineNo the starting line number
     * @param aStartColNo the starting column number
     **/
    public void reportCppComment(int aStartLineNo, int aStartColNo)
    {
        final String line = mLines[aStartLineNo - 1];
        final String[] txt = new String[] {line.substring(aStartColNo)};
        final Comment comment = new Comment(txt, aStartColNo, aStartLineNo,
                line.length() - 1);
        mCPlusPlusComments.put(aStartLineNo, comment);
    }

    /**
     * Returns a map of all the C++ style comments. The key is a line number,
     * the value is the comment {@link TextBlock} at the line.
     * @return the Map of comments
     */
    public ImmutableMap<Integer, TextBlock> getCppComments()
    {
        return ImmutableMap.copyOf(mCPlusPlusComments);
    }

    /**
     * Report the location of a C-style comment.
     * @param aStartLineNo the starting line number
     * @param aStartColNo the starting column number
     * @param aEndLineNo the ending line number
     * @param aEndColNo the ending column number
     **/
    public void reportCComment(int aStartLineNo, int aStartColNo,
            int aEndLineNo, int aEndColNo)
    {
        final String[] cc = extractCComment(aStartLineNo, aStartColNo,
                aEndLineNo, aEndColNo);
        final Comment comment = new Comment(cc, aStartColNo, aEndLineNo,
                aEndColNo);

        // save the comment
        if (mCComments.containsKey(aStartLineNo)) {
            final List<TextBlock> entries = mCComments.get(aStartLineNo);
            entries.add(comment);
        }
        else {
            final List<TextBlock> entries = Lists.newArrayList();
            entries.add(comment);
            mCComments.put(aStartLineNo, entries);
        }

        // Remember if possible Javadoc comment
        if (mLines[aStartLineNo - 1].indexOf("/**", aStartColNo) != -1) {
            mJavadocComments.put(aEndLineNo - 1, comment);
        }
    }

    /**
     * Returns a map of all C style comments. The key is the line number, the
     * value is a {@link List} of C style comment {@link TextBlock}s
     * that start at that line.
     * @return the map of comments
     */
    public ImmutableMap<Integer, List<TextBlock>> getCComments()
    {
        return ImmutableMap.copyOf(mCComments);
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
            retVal[retVal.length - 1] = mLines[aEndLineNo - 1].substring(0,
                    aEndColNo + 1);
        }
        return retVal;
    }

    /**
     * Returns the Javadoc comment before the specified line.
     * A return value of <code>null</code> means there is no such comment.
     * @return the Javadoc comment, or <code>null</code> if none
     * @param aLineNo the line number to check before
     **/
    public TextBlock getJavadocBefore(int aLineNo)
    {
        // Lines start at 1 to the callers perspective, so need to take off 2
        int lineNo = aLineNo - 2;

        // skip blank lines
        while ((lineNo > 0) && (lineIsBlank(lineNo) || lineIsComment(lineNo))) {
            lineNo--;
        }

        return mJavadocComments.get(lineNo);
    }

    /** @return the lines in the file */
    public String[] getLines()
    {
        return mLines.clone();
    }

    /** @return the name of the file */
    public String getFilename()
    {
        return mFilename;
    }

    /**
     * Checks if the specified line is blank.
     * @param aLineNo the line number to check
     * @return if the specified line consists only of tabs and spaces.
     **/
    public boolean lineIsBlank(int aLineNo)
    {
        // possible improvement: avoid garbage creation in trim()
        return "".equals(mLines[aLineNo].trim());
    }

    /**
     * Checks if the specified line is a single-line comment without code.
     * @param aLineNo  the line number to check
     * @return if the specified line consists of only a single line comment
     *         without code.
     **/
    public boolean lineIsComment(int aLineNo)
    {
        return MATCH_SINGLELINE_COMMENT.matcher(mLines[aLineNo]).matches();
    }

    /**
     * Checks if the specified position intersects with a comment.
     * @param aStartLineNo the starting line number
     * @param aStartColNo the starting column number
     * @param aEndLineNo the ending line number
     * @param aEndColNo the ending column number
     * @return true if the positions intersects with a comment.
     **/
    public boolean hasIntersectionWithComment(int aStartLineNo,
            int aStartColNo, int aEndLineNo, int aEndColNo)
    {
        // Check C comments (all comments should be checked)
        final Collection<List<TextBlock>> values = mCComments.values();
        for (final List<TextBlock> row : values) {
            for (final TextBlock comment : row) {
                if (comment.intersects(aStartLineNo, aStartColNo, aEndLineNo,
                        aEndColNo))
                {
                    return true;
                }
            }
        }

        // Check CPP comments (line searching is possible)
        for (int lineNumber = aStartLineNo; lineNumber <= aEndLineNo;
             lineNumber++)
        {
            final TextBlock comment = mCPlusPlusComments.get(lineNumber);
            if ((comment != null)
                    && comment.intersects(aStartLineNo, aStartColNo,
                            aEndLineNo, aEndColNo))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the current file is a package-info.java file.
     * @return true if the package file.
     */
    public boolean inPackageInfo()
    {
        return this.getFilename().endsWith("package-info.java");
    }
}
