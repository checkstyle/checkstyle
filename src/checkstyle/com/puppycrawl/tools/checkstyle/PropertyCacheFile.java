////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001  Oliver Burn
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class maintains a persistent store of the files that have checked ok and
 * their associated timestamp. It uses a property file for storage.
 *
 * @author <a href="mailto:oliver-work@puppycrawl.com">Oliver Burn</a>
 */
class PropertyCacheFile
{
    /** name of file to store details **/
    private final String mDetailsFile;
    /** the details on files **/
    private final Properties mDetails = new Properties();

    /**
     * Creates a new <code>PropertyCacheFile</code> instance.
     *
     * @param aFileName name of properties file that contains details. if the
                        file does not exist, it will be created
     */
    PropertyCacheFile(String aFileName)
    {
        boolean setInActive = true;
        if (aFileName != null) {
            try {
                mDetails.load(new FileInputStream(aFileName));
                setInActive = false;
            }
            catch (FileNotFoundException e) {
                // Ignore, the cache does not exist
                setInActive = false;
            }
            catch (IOException e) {
                System.out.println("Unable to open cache file, ignoring.");
                e.printStackTrace(System.out);
            }
        }
        mDetailsFile = (setInActive) ? null : aFileName;
    }

    /** Cleans up the object **/
    void destroy()
    {
        if (mDetailsFile != null) {
            try {
                mDetails.store(new FileOutputStream(mDetailsFile), null);
            }
            catch (IOException e) {
                System.out.println("Unable to save cache file");
                e.printStackTrace(System.out);
            }
        }
    }

    /**
     * @return whether the specified file has already been checked ok
     * @param aFileName the file to check
     * @param aTimestamp the timestamp of the file to check
     */
    boolean alreadyChecked(String aFileName, long aTimestamp)
    {
        final String lastChecked = mDetails.getProperty(aFileName);
        return (lastChecked != null) &&
            (lastChecked.equals(Long.toString(aTimestamp)));
    }

    /**
     * Records that a file checked ok.
     * @param aFileName name of the file that checked ok
     * @param aTimestamp the timestamp of the file
     */
    void checkedOk(String aFileName, long aTimestamp)
    {
        mDetails.put(aFileName, Long.toString(aTimestamp));
    }
}

