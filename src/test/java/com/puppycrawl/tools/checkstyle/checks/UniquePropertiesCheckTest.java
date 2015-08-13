////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.UniquePropertiesCheck.IO_EXCEPTION_KEY;
import static com.puppycrawl.tools.checkstyle.checks.UniquePropertiesCheck.MSG_KEY;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseFileSetCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;

/**
 * JUnit tests for Unique Properties check.
 */
public class UniquePropertiesCheckTest extends BaseFileSetCheckTestSupport {

    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createCheckConfig(UniquePropertiesCheck.class);
    }

    /**
     * Tests the ordinal work of a check.
     */
    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "3: " + getCheckMessage(MSG_KEY, "general.exception", 2),
            "5: " + getCheckMessage(MSG_KEY, "DefaultLogger.auditStarted", 2),
            "11: " + getCheckMessage(MSG_KEY, "onlineManual", 3),
            "22: " + getCheckMessage(MSG_KEY, "time stamp", 3),
            "28: " + getCheckMessage(MSG_KEY, "Support Link ", 2),
            "34: " + getCheckMessage(MSG_KEY, "failed", 2),
        };
        verify(checkConfig, getPath("InputUniquePropertiesCheck.properties"),
                expected);
    }

    /**
     * Tests the {@link UniquePropertiesCheck#getLineNumber(List, String)}
     * method return value
     */
    @Test
    public void testNotFoundKey() throws Exception {
        final List<String> testStrings = new ArrayList<>(3);
        testStrings.add("");
        testStrings.add("0 = 0");
        testStrings.add("445");
        final int stringNumber =
                UniquePropertiesCheck.getLineNumber(testStrings,
                        "some key");
        Assert.assertEquals(stringNumber, 0);
    }

    /**
     * Tests IO exception, that can orrur during reading of properties file.
     */
    @Test
    public void testIOException() throws Exception {
        final UniquePropertiesCheck check = new UniquePropertiesCheck();
        check.configure(checkConfig);
        final String fileName =
                getPath("InputUniquePropertiesCheckNotExisting.properties");
        final File file = new File(fileName);
        final SortedSet<LocalizedMessage> messages =
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
                getCheckMessage(IO_EXCEPTION_KEY, fileName, getFileNotFoundDetail(file)));
    }

    /**
     * Method generates FileNotFound exception details. It tries to open file,
     * that does not exist.
     * @param file to be opened
     * @return detail message of {@link FileNotFoundException}
     */
    private String getFileNotFoundDetail(File file) throws Exception {
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
}
