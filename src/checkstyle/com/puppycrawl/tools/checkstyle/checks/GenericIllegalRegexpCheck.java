////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2008  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * A generic check for code problems, the user can search for any pattern.
 * This is similar to a recursive grep, only that it's integrated in checkstyle.
 * </p>
 * <p>
 * Rationale: This Check can be used to prototype checks and to find common
 * bad pratice such as calling
 * ex.printStacktrace(), System.out.println(), System.exit(), etc.
 * </p>
 * <p>
 * An example of how to configure the check for calls to
 * <code>System.out.println</code> is:
 * </p>
 * <pre>
 * &lt;module name="GenericIllegalRegexp"&gt;
 *    &lt;property name="format" value="System\.out\.println"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author lkuehne
 * @author <a href="mailto:bschneider@vecna.com">Bill Schneider</a>
 * @author Daniel Grenner
 */
public class GenericIllegalRegexpCheck extends AbstractFormatCheck
{
    /**
     * Custom message for report if illegal regexp found
     * ignored if empty.
     */
    private String mMessage = "";

    /** Ignore comments in code? **/
    private boolean mIgnoreComments;

    /**
     * Setter for message property.
     * @param aMessage custom message which should be used
     *                 to report about violations.
     */

    public void setMessage(String aMessage)
    {
        if (aMessage == null) {
            aMessage = "";
        }
        mMessage = aMessage;
    }

    /**
     * Getter for message property.
     * @return custom message which should be used
     * to report about violations.
     */
    public String getMessage()
    {
        return mMessage;
    }

    /**
     * Set whether or not the match is case sensitive.
     * @param aCaseInsensitive true if the match is case insensitive.
     */
    public void setIgnoreCase(boolean aCaseInsensitive)
    {
        if (aCaseInsensitive) {
            setCompileFlags(Pattern.CASE_INSENSITIVE);
        }
    }

    /**
     * Sets if comments should be ignored.
     * @param aIgnoreComments True if comments should be ignored.
     */
    public void setIgnoreComments(boolean aIgnoreComments)
    {
        mIgnoreComments = aIgnoreComments;
    }

    /**
     * Instantiates an new GenericIllegalRegexpCheck.
     */
    public GenericIllegalRegexpCheck()
    {
        super("$^"); // the empty language
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[0];
    }

    @Override
    public void beginTree(DetailAST aRootAST)
    {
        final String[] lines = getLines();

        for (int i = 0; i < lines.length; i++) {

            final String line = lines[i];
            final boolean foundMatch;
            if (mIgnoreComments) {
                foundMatch = findNonCommentMatch(line, i + 1, 0);
            }
            else {
                foundMatch = getRegexp().matcher(line).find();
            }
            if (foundMatch) {
                if ("".equals(mMessage)) {
                    log(i + 1, "illegal.regexp", getFormat());
                }
                else {
                    log(i + 1, mMessage);
                }
            }
        }
    }

    /**
     * Finds matches that are not inside comments.
     * @param aLine The text that should be matched.
     * @param aLineNumber The current line number.
     * @param aStartPosition The position to start searching from.
     * @return true if a match is done where there is no comment.
     */
    private boolean findNonCommentMatch(
            String aLine, int aLineNumber, int aStartPosition)
    {
        final Pattern pattern = getRegexp();
        final Matcher matcher = pattern.matcher(aLine);
        final boolean foundMatch = matcher.find(aStartPosition);
        if (!foundMatch) {
            return false;
        }
        // match is found, check for intersection with comment
        final int startCol = matcher.start(0);
        final int endCol = matcher.end(0);
        // Note that Matcher.end(int) returns he offset AFTER the
        // last matched character, but hasIntersectionWithComment()
        // needs column number of the last character.
        // So we need to use (endCol - 1) here.
        final boolean intersectsWithComment = getFileContents()
            .hasIntersectionWithComment(aLineNumber, startCol,
                                        aLineNumber, endCol - 1);
        if (intersectsWithComment) {
            if (endCol < aLine.length()) {
                // check if the expression is on the rest of the line
                return findNonCommentMatch(aLine, aLineNumber, endCol);
            }
            // end of line reached
            return false;
        }
        // not intersecting with comment
        return true;
    }
}
