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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;


/**
 * Checks a file name against a {@link java.util.regex.Pattern regular expression}. Several
 * flags can be set to control what part of the filename the check is applied to, how the
 * expression is supposed to be applied, and how to interpret the match. See documentation
 * for further information.
 *
 * @author Thomas Jensen
 */
public class RegexpOnFilenameCheck
    extends AbstractFileSetCheck
{
    /**
     * regexp applied to the canonical file name in order to determine if the file is applicable for
     * the check
     */
    private Pattern mSelection;

    /**
     * if <code>true</code>, a violation is logged if the regexp does <i>not</i> match;<br/> if
     * <code>false</code>, a violation is logged if the regexp matched ("illegal")
     */
    private boolean mRequired;

    /**
     * if <code>true</code>, only the simple name of the file will be checked against the
     * regexp;<br/> if <code>false</code>, the entire canonical path will be checked
     */
    private boolean mSimple;

    /**
     * if <code>true</code>, the regexp is used for a substring search;<br/> if <code>false</code>,
     * the regexp is applied to the entire canonical path
     */
    private boolean mSubstring;

    /** the given regexp */
    private Pattern mRegexp;



    public void setSelection(final String aRegexp)
    {
        mSelection = Pattern.compile(aRegexp);
    }



    public void setRequired(final boolean aRequired)
    {
        mRequired = aRequired;
    }



    public void setSimple(final boolean aSimple)
    {
        mSimple = aSimple;
    }



    public void setSubstring(final boolean aSubstring)
    {
        mSubstring = aSubstring;
    }



    public void setRegexp(final String aRegexp)
    {
        mRegexp = Pattern.compile(aRegexp);
    }



    @Override
    protected void processFiltered(final File aFile, final List<String> aLines)
    {
        String filePath = null;
        try {
            filePath = aFile.getCanonicalPath();
        }
        catch (IOException e) {
            filePath = aFile.getAbsolutePath();
        }

        boolean ok = true;
        if (mRegexp != null && (mSelection == null || mSelection.matcher(filePath).matches())) {

            if (mSimple) {
                filePath = aFile.getName();
            }

            final Matcher m = mRegexp.matcher(filePath);
            if (mSubstring) {
                ok = m.find();
            }
            else {
                ok = m.matches();
            }

            if (!mRequired) {
                ok = !ok;
            }
        }

        if (!ok) {
            String msgKey = "regexp.filepath.";
            msgKey += mRequired ? "required" : "illegal";
            if (mSubstring) {
                msgKey += ".sub";
            }
            log(0, msgKey, filePath, mRegexp.pattern());
        }
    }
}
