////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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

import java.io.File;
import java.util.Iterator;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.MessageDispatcher;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;

/**
 * <p>
 * Checks that all packages have a package documentation.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="PackageHtml"/&gt;
 * </pre>
 * @author lkuehne
 */
public class PackageHtmlCheck extends AbstractFileSetCheck
{
    /**
     * Checks that each java file in the fileset has a package.html sibling
     * and fires errors for the missing files.
     * @param aFiles a set of files
     */
    public void process(File[] aFiles)
    {
        Set directories = getParentDirs(aFiles);
        for (Iterator it = directories.iterator(); it.hasNext();) {
            File dir = (File) it.next();
            File packageHtml = new File(dir, "package.html");
            MessageDispatcher dispatcher = getMessageDispatcher();
            final String path = packageHtml.getPath();
            dispatcher.fireFileStarted(path);
            if (!packageHtml.exists()) {
                LocalizedMessage[] errors = new LocalizedMessage[1];
                final String bundle =
                    this.getClass().getPackage().getName() + ".messages";
                errors[0] = new LocalizedMessage(
                        0, bundle, "javadoc.packageHtml", null);
                getMessageDispatcher().fireErrors(path, errors);
            }
            dispatcher.fireFileFinished(path);
        }
    }
}
