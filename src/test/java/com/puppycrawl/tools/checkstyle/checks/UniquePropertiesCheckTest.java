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
package com.puppycrawl.tools.checkstyle.checks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseFileSetCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;

/**
 * JUnit tests for Unique Properties check.
 */
public class UniquePropertiesCheckTest extends BaseFileSetCheckTestSupport
{

    private String msg = getCheckMessage(UniquePropertiesCheck.MSG_KEY);
    private String ioMsg = getCheckMessage(UniquePropertiesCheck.IO_EXCEPTION_KEY);

    private DefaultConfiguration mCheckConfig;

    @Before
    public void setUp()
    {
        mCheckConfig = createCheckConfig(UniquePropertiesCheck.class);
    }

    /**
     * Tests the ordinal work of a check.
     * @throws Exception
     *             on error occurres
     */
    @Test
    public void testDefault() throws Exception
    {
        final String[] expected =
        {
            buildMesssage(3, "general.exception", 2),
            buildMesssage(5, "DefaultLogger.auditStarted", 2),
            buildMesssage(11, "onlineManual", 3),
            buildMesssage(22, "time stamp", 3),
            buildMesssage(28, "Support Link ", 2),
            buildMesssage(34, "failed", 2),
        };
        verify(mCheckConfig, getPath("InputUniquePropertiesCheck.properties"),
                expected);
    }

    /**
     * Tests the {@link UniquePropertiesCheck#getLineNumber(List, String)}
     * method return value
     * @throws Exception
     *             on error occurs
     */
    @Test
    public void testNotFoundKey() throws Exception
    {
        final UniquePropertiesCheck check = new UniquePropertiesCheck();
        final List<String> testStrings = new ArrayList<String>(3);
        testStrings.add("");
        testStrings.add("0 = 0");
        testStrings.add("445");
        final int stringNumber =
                check.getLineNumber(testStrings,
                        "some key");
        Assert.assertEquals(stringNumber, 0);
    }

    /**
     * Tests IO exception, that can orrur during reading of properties file.
     * @throws Exception
     *             on error occurs
     */
    @Test
    public void testIOException() throws Exception
    {
        final UniquePropertiesCheck check = new UniquePropertiesCheck();
        check.configure(mCheckConfig);
        final String fileName =
                getPath("InputUniquePropertiesCheckNotExisting.properties");
        final File file = new File(fileName);
        final TreeSet<LocalizedMessage> messages =
                check.process(file, Collections.<String>emptyList());
        Assert.assertEquals("Wrong messages count: " + messages.size(),
                messages.size(), 1);
        final LocalizedMessage message = messages.iterator().next();
        final String retrievedMessage = messages.iterator().next().getKey();
        Assert.assertEquals("Message key '" + retrievedMessage
                + "' is not valid", retrievedMessage,
                "unable.open.cause");
        Assert.assertEquals("Message '" + message.getMessage()
                + "' is not valid", message.getMessage(),
                buildIOMessage(fileName, getFileNotFoundDetail(file)));
    }

    /**
     * Method generates FileNotFound exception details. It tries to open file,
     * that does not exist.
     * @param file
     * @return detail message of {@link FileNotFoundException}
     * @throws Exception
     *             on file exists
     */
    private String getFileNotFoundDetail(File file) throws Exception
    {
        // Create exception to know detail message we should wait in
        // LocalisedMessage
        try {
            final InputStream stream = new FileInputStream(file);
            stream.close();
            throw new Exception("File " + file.getPath() + " should not exist");
        }
        catch (FileNotFoundException ex) {
            return ex.getLocalizedMessage();
        }
    }

    private String buildMesssage(int lineNumber, String keyName,
            int nOccurrences)
    {
        return lineNumber + ": "
                + MessageFormat.format(msg, keyName, nOccurrences);
    }

    private String buildIOMessage(String filename, String exceptionDetails)
    {
        return MessageFormat.format(ioMsg, filename, exceptionDetails);
    }

}
