////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;

/**
 * <p>
 * Checks that the whitespace around the Generic tokens (angle brackets)
 * "&lt;" and "&gt;" are correct to the <i>typical</i> convention.
 * The convention is not configurable.
 * </p>
 * <br>
 * <p>
 * Left angle bracket ("&lt;"):
 * </p>
 * <br>
 * <ul>
 * <li> should be preceded with whitespace only
 *   in generic methods definitions.</li>
 * <li> should not be preceded with whitespace
 *   when it is precede method name or following type name.</li>
 * <li> should not be followed with whitespace in all cases.</li>
 * </ul>
 * <br>
 * <p>
 * Right angle bracket ("&gt;"):
 * </p>
 * <br>
 * <ul>
 * <li> should not be preceded with whitespace in all cases.</li>
 * <li> should be followed with whitespace in almost all cases,
 *   except diamond operators and when preceding method name.</li></ul>
 * <br>
 * <p>
 * Examples with correct spacing:
 * </p>
 * <br>
 * <pre>
 * public void &lt;K, V extends Number&gt; boolean foo(K, V) {}  // Generic methods definitions
 * class name&lt;T1, T2, ..., Tn&gt; {}                          // Generic type definition
 * OrderedPair&lt;String, Box&lt;Integer&gt;&gt; p;              // Generic type reference
 * boolean same = Util.&lt;Integer, String&gt;compare(p1, p2);   // Generic preceded method name
 * Pair&lt;Integer, String&gt; p1 = new Pair&lt;&gt;(1, "apple");// Diamond operator
 * </pre>
 * @author Oliver Burn
 */
public class GenericWhitespaceCheck extends Check
{
    /** Used to count the depth of a Generic expression. */
    private int mDepth;

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.GENERIC_START, TokenTypes.GENERIC_END};
    }

    @Override
    public void beginTree(DetailAST aRootAST)
    {
        // Reset for each tree, just incase there are errors in preceeding
        // trees.
        mDepth = 0;
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        if (aAST.getType() == TokenTypes.GENERIC_START) {
            processStart(aAST);
            mDepth++;
        }
        else if (aAST.getType() == TokenTypes.GENERIC_END) {
            processEnd(aAST);
            mDepth--;
        }
    }

    /**
     * Checks the token for the end of Generics.
     * @param aAST the token to check
     */
    private void processEnd(DetailAST aAST)
    {
        final String line = getLine(aAST.getLineNo() - 1);
        final int before = aAST.getColumnNo() - 1;
        final int after = aAST.getColumnNo() + 1;

        if ((0 <= before) && Character.isWhitespace(line.charAt(before))
                && !Utils.whitespaceBefore(before, line))
        {
            log(aAST.getLineNo(), before, "ws.preceded", ">");
        }

        if (after < line.length()) {

            // Check if the last Generic, in which case must be a whitespace
            // or a '(),[.'.
            if (1 == mDepth) {
                final char charAfter = line.charAt(after);

                // Need to handle a number of cases. First is:
                //    Collections.<Object>emptySet();
                //                        ^
                //                        +--- whitespace not allowed
                if ((aAST.getParent().getType() == TokenTypes.TYPE_ARGUMENTS)
                    && (aAST.getParent().getParent().getType()
                        == TokenTypes.DOT)
                    && (aAST.getParent().getParent().getParent().getType()
                        == TokenTypes.METHOD_CALL))
                {
                    if (Character.isWhitespace(charAfter)) {
                        log(aAST.getLineNo(), after, "ws.followed", ">");
                    }
                }
                else if (!Character.isWhitespace(charAfter)
                    && ('(' != charAfter) && (')' != charAfter)
                    && (',' != charAfter) && ('[' != charAfter)
                    && ('.' != charAfter))
                {
                    log(aAST.getLineNo(), after, "ws.illegalFollow", ">");
                }
            }
            else {
                // In a nested Generic type, so can only be a '>' or ',' or '&'

                // In case of several extends definitions:
                //
                //   class IntEnumValueType<E extends Enum<E> & IntEnum>
                //                                          ^
                //   should be whitespace if followed by & -+
                //
                final int indexOfAmp = line.indexOf('&', after);
                if ((indexOfAmp != -1)
                    && whitespaceBetween(after, indexOfAmp, line))
                {
                    if (indexOfAmp - after == 0) {
                        log(aAST.getLineNo(), after, "ws.notPreceded", "&");
                    }
                    else if (indexOfAmp - after != 1) {
                        log(aAST.getLineNo(), after, "ws.followed", ">");
                    }
                }
                else if (line.charAt(after) == ' ') {
                    log(aAST.getLineNo(), after, "ws.followed", ">");
                }
            }
        }
    }

    /**
     * Checks the token for the start of Generics.
     * @param aAST the token to check
     */
    private void processStart(DetailAST aAST)
    {
        final String line = getLine(aAST.getLineNo() - 1);
        final int before = aAST.getColumnNo() - 1;
        final int after = aAST.getColumnNo() + 1;

        // Need to handle two cases as in:
        //
        //   public static <T> Callable<T> callable(Runnable task, T result)
        //                 ^           ^
        //      ws reqd ---+           +--- whitespace NOT required
        //
        if (0 <= before) {
            // Detect if the first case
            final DetailAST parent = aAST.getParent();
            final DetailAST grandparent = parent.getParent();
            if ((TokenTypes.TYPE_PARAMETERS == parent.getType())
                && ((TokenTypes.CTOR_DEF == grandparent.getType())
                    || (TokenTypes.METHOD_DEF == grandparent.getType())))
            {
                // Require whitespace
                if (!Character.isWhitespace(line.charAt(before))) {
                    log(aAST.getLineNo(), before, "ws.notPreceded", "<");
                }
            }
            // Whitespace not required
            else if (Character.isWhitespace(line.charAt(before))
                && !Utils.whitespaceBefore(before, line))
            {
                log(aAST.getLineNo(), before, "ws.preceded", "<");
            }
        }

        if ((after < line.length())
                && Character.isWhitespace(line.charAt(after)))
        {
            log(aAST.getLineNo(), after, "ws.followed", "<");
        }
    }

    /**
     * Returns whether the specified string contains only whitespace between
     * specified indices.
     *
     * @param aFromIndex the index to start the search from. Inclusive
     * @param aToIndex the index to finish the search. Exclusive
     * @param aLine the line to check
     * @return whether there are only whitespaces (or nothing)
     */
    private static boolean whitespaceBetween(
        int aFromIndex, int aToIndex, String aLine)
    {
        for (int i = aFromIndex; i < aToIndex; i++) {
            if (!Character.isWhitespace(aLine.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
