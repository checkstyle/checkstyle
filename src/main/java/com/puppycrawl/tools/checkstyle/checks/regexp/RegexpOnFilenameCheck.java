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
package com.puppycrawl.tools.checkstyle.checks.regexp;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;


/**
 * Checks the canonical file name against a
 * {@link java.util.regex.Pattern regular expression}. A violation is reported if a match is found.
 *
 * @author Thomas Jensen
 */
public class RegexpOnFilenameCheck
    extends AbstractFileSetCheck
{
    /** the regexp to apply to the file name as set by the property */
    private Pattern mRegexp;



    /**
     * Set the regular expression to be applied to the file names.
     *
     * @param aRegexp the regexp to match
     * @throws java.util.regex.PatternSyntaxException an invalid pattern was supplied
     */
    public void setRegexp(final String aRegexp)
    {
        mRegexp = Pattern.compile(aRegexp);
    }



    @Override
    protected void processFiltered(final File aFile, final List<String> aLines)
    {
        if (mRegexp == null) {
            // no regexp given
            return;
        }

        String filePath = null;
        try {
            filePath = aFile.getCanonicalPath();
        }
        catch (IOException e) {
            filePath = aFile.getAbsolutePath();
        }

        if (mRegexp.matcher(filePath).matches()) {
            log(0, "regexp.filepath", filePath, mRegexp.pattern());
        }
    }
}
