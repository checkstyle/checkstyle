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
package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.MessageDispatcher;
import com.puppycrawl.tools.checkstyle.api.Utils;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.List;

/**
 * Checks to see if a file contains a tab character.
 * @author oliverb
 */
public class FileTabCharacterCheck extends AbstractFileSetCheck
{
    /** Indicates whether to report once per file, or for each line. */
    private boolean mEachLine;
    /** {@inheritDoc} */
    public void process(final List<File> aFiles)
    {
        final MessageDispatcher msgDispatcher = getMessageDispatcher();
        for (final File file : aFiles) {
            final String path = file.getPath();
            msgDispatcher.fireFileStarted(path);
            LineNumberReader lnr = null;
            try {
                lnr = new LineNumberReader(new FileReader(file));
                while (true) {
                    final String line = lnr.readLine();
                    if (null == line) {
                        break;
                    }
                    final int tabPosition = line.indexOf('\t');
                    if (tabPosition != -1) {
                        if (mEachLine) {
                            log(lnr.getLineNumber(), tabPosition + 1,
                                "containsTab");
                        }
                        else {
                            log(lnr.getLineNumber(), tabPosition + 1,
                                "file.containsTab");
                            break;
                        }
                    }
                }
            }
            catch (IOException ioe) {
                Utils.getExceptionLogger().debug("IOException occured.", ioe);
                log(0, "Unable to read file: " + ioe);
            }
            finally {
                Utils.closeQuietly(lnr);
            }
            fireErrors(path);
            msgDispatcher.fireFileFinished(path);
        }
    }

    /**
     * Whether report on each line containing a tab.
     * @param aEachLine Whether report on each line containing a tab.
     */
    public void setEachLine(boolean aEachLine)
    {
        mEachLine = aEachLine;
    }
}
