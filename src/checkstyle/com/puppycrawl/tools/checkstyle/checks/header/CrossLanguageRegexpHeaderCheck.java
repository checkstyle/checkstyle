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

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.beanutils.ConversionException;

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.MessageDispatcher;
import com.puppycrawl.tools.checkstyle.api.Utils;

/**
 * A FileSetCheck similar to {@link RegexpHeaderCheck},
 * but works for all text files, not just java code.
 *
 * @author lk
 *
 */
public final class CrossLanguageRegexpHeaderCheck extends AbstractFileSetCheck
{

    /**
     * HeaderViolationmonitor that delegates to the
     * FileSetcheck methods for reporting violations.
     */
    private final class FileSetCheckViolationMonitor
        implements HeaderViolationMonitor
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

    /** information about the expected header file. */
    private final RegexpHeaderInfo mHeaderInfo = new RegexpHeaderInfo();

    /**
     * Creates a new instance and initializes the file extentions
     * to the default value, which represents most of the typical
     * text files that require a copyright header.
     */
    public CrossLanguageRegexpHeaderCheck()
    {
        setFileExtensions(new String[]{
            "java", "txt", "properties", "html", "jsp", "xml", "wsdl",
            "js", "css",
            "idl", "c", "cc", "cpp", "cxx", "h", "cs",
            "sh", "py", "bat",
        });
    }

    /**
     * Set the lines numbers to repeat in the header check.
     * @param aList comma separated list of line numbers to repeat in header.
     */
    public void setMultiLines(int[] aList)
    {
        mHeaderInfo.setMultiLines(aList);
    }

    /**
     * Set the header file to check against.
     * @param aFileName the file that contains the header to check against.
     * @throws ConversionException if the file cannot be loaded
     */
    public void setHeaderFile(String aFileName)
        throws ConversionException
    {
        mHeaderInfo.setHeaderFile(aFileName);
    }

    /**
     * Set the header to check against. Individual lines in the header
     * must be separated by '\n' characters.
     * @param aHeader header content to check against.
     * @throws ConversionException if the header cannot be interpreted
     */
    public void setHeader(String aHeader)
    {
        mHeaderInfo.setHeader(aHeader);
    }

    @Override
    protected void finishLocalSetup() throws CheckstyleException
    {
        if (mHeaderInfo.getHeaderLines() == null) {
            throw new CheckstyleException(
                    "property 'headerFile' is missing or invalid in module "
                    + getConfiguration().getName());
        }
    }

    /** {@inheritDoc} */
    public void process(List<File> aFiles)
    {

        final MessageDispatcher msgDispatcher = getMessageDispatcher();
        final RegexpHeaderChecker regexpHeaderChecker =
            new RegexpHeaderChecker(
                    mHeaderInfo, new FileSetCheckViolationMonitor());
        final List<File> files = filter(aFiles);
        for (final File file : files) {
            final String path = file.getPath();
            msgDispatcher.fireFileStarted(path);
            try {
                final String[] lines = Utils.getLines(path);
                regexpHeaderChecker.checkLines(lines);
            }
            catch (IOException ex) {
                log(0, "unable to open file: " + ex);
            }
            fireErrors(path);
            msgDispatcher.fireFileFinished(path);
        }
    }

}
