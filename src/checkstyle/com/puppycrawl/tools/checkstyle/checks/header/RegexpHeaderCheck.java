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

package com.puppycrawl.tools.checkstyle.checks.header;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Checks the header of the source against a header file that contains a
 * {@link java.util.regex.Pattern regular expression}
 * for each line of the source header.
 *
 * @author Lars Kühne
 * @author o_sukhodolsky
 */
public class RegexpHeaderCheck extends AbstractHeaderCheck
{
    /**
     * A HeaderViolationMonitor that is used when running a Check,
     * as a subcomponents of TreeWalker.
     */
    private final class CheckViolationMonitor implements HeaderViolationMonitor
    {
        /** {@inheritDoc} */
        public void reportHeaderMismatch(int aLineNo, String aHeaderLine)
        {
            log(aLineNo, "header.mismatch", aHeaderLine);
        }

        /** {@inheritDoc} */
        public void reportHeaderMissing()
        {
            log(1, "header.missing");
        }
    }


    /** A delegate for the actual checking functionality. */
    private RegexpHeaderChecker mRegexpHeaderChecker;


    /**
     * Provides typesafe access to the subclass specific HeaderInfo.
     *
     * @return the result of {@link #createHeaderInfo()}
     */
    protected RegexpHeaderInfo getRegexpHeaderInfo()
    {
        return (RegexpHeaderInfo) getHeaderInfo();
    }

    /**
     * Set the lines numbers to repeat in the header check.
     * @param aList comma separated list of line numbers to repeat in header.
     */
    public void setMultiLines(int[] aList)
    {
        getRegexpHeaderInfo().setMultiLines(aList);
    }

    @Override
    public void init()
    {
        super.init();
        mRegexpHeaderChecker = new RegexpHeaderChecker(
                getRegexpHeaderInfo(), new CheckViolationMonitor());
    }

    @Override
    public void beginTree(DetailAST aRootAST)
    {
        final String[] lines = getLines();
        mRegexpHeaderChecker.checkLines(lines);
    }

    @Override
    protected HeaderInfo createHeaderInfo()
    {
        return new RegexpHeaderInfo();
    }
}
