////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2004  Oliver Burn
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

import org.apache.regexp.RE;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

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
 */
public class GenericIllegalRegexpCheck extends AbstractFormatCheck
{
    /**
     * Custom message for report if illegal regexp found
     * ignored if empty.
     */
    private String mMessage = "";

    /** case insensitive? **/
    private boolean mIgnoreCase;

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
        mIgnoreCase = aCaseInsensitive;
    }

    /**
     * Instantiates an new GenericIllegalRegexpCheck.
     */
    public GenericIllegalRegexpCheck()
    {
        super("$^"); // the empty language
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[0];
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void beginTree(DetailAST aRootAST)
    {
        final String[] lines = getLines();
        for (int i = 0; i < lines.length; i++) {

            final String line = lines[i];
            if (getRegexp().match(line)) {
                if ("".equals(mMessage)) {
                    log(i + 1, "illegal.regexp", getFormat());
                }
                else {
                    log(i + 1, mMessage);
                }
            }
        }
    }

    /** @return the regexp to match against */
    public RE getRegexp()
    {
        final RE regexp = super.getRegexp();

        // we should explicitly set match flags because
        // we caching RE and another check (or instance
        // of this check could change match flags.
        if (mIgnoreCase) {
            regexp.setMatchFlags(RE.MATCH_CASEINDEPENDENT);
        }
        else {
            regexp.setMatchFlags(RE.MATCH_NORMAL);
        }
        return regexp;
    }
}
