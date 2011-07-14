////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.regexp;

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import java.io.File;
import java.util.List;

/**
 * Implementation of a check that looks for a single line in any file type.
 * @author Oliver Burn
 */
public class RegexpSinglelineCheck extends AbstractFileSetCheck
{
    /** The detection options to use. */
    private DetectorOptions mOptions = new DetectorOptions(0, this);
    /** The detector to use. */
    private SinglelineDetector mDetector;

    @Override
    public void beginProcessing(String aCharset)
    {
        super.beginProcessing(aCharset);
        mDetector = new SinglelineDetector(mOptions);
    }

    @Override
    protected void processFiltered(File aFile, List<String> aLines)
    {
        mDetector.processLines(aLines);
    }

    /**
     * Set the format of the regular expression to match.
     * @param aFormat the format of the regular expression to match.
     */
    public void setFormat(String aFormat)
    {
        mOptions.setFormat(aFormat);
    }

    /**
     * Set the message to report for a match.
     * @param aMessage the message to report for a match.
     */
    public void setMessage(String aMessage)
    {
        mOptions.setMessage(aMessage);
    }

    /**
     * Set the minimum number of matches required per file.
     * @param aMinimum the minimum number of matches required per file.
     */
    public void setMinimum(int aMinimum)
    {
        mOptions.setMinimum(aMinimum);
    }

    /**
     * Set the maximum number of matches required per file.
     * @param aMaximum the maximum number of matches required per file.
     */
    public void setMaximum(int aMaximum)
    {
        mOptions.setMaximum(aMaximum);
    }

    /**
     * Set whether to ignore case when matching.
     * @param aIgnore whether to ignore case when matching.
     */
    public void setIgnoreCase(boolean aIgnore)
    {
        mOptions.setIgnoreCase(aIgnore);
    }
}
