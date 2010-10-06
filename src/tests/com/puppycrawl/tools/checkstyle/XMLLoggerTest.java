////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2010  Oliver Burn
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
package com.puppycrawl.tools.checkstyle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.Utils;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Pattern;
import org.junit.Test;

/**
 * Enter a description of class XMLLoggerTest.java.
 * @author Rick Giles
 * @version 11-Dec-2002
 */
public class XMLLoggerTest
{
    private final ByteArrayOutputStream outStream = new ByteArrayOutputStream();

    @Test
    public void testEncode()
        throws IOException
    {
        final XMLLogger logger = new XMLLogger(outStream, false);
        final String[][] encodings = {
            {"<", "&lt;"},
            {">", "&gt;"},
            {"'", "&apos;"},
            {"\"", "&quot;"},
            {"&", "&amp;"},
            {"&lt;", "&lt;"},
            {"abc;", "abc;"},
        };
        for (int i = 0; i < encodings.length; i++) {
            final String encoded = logger.encode(encodings[i][0]);
            assertEquals("\"" + encodings[i][0] + "\"", encodings[i][1], encoded);
        }
        outStream.close();
    }

    @Test
    public void testIsReference()
        throws IOException
    {
        final XMLLogger logger = new XMLLogger(outStream, false);
        final String[] reference = {
            "&#0;",
            "&#x0;",
        };
        for (int i = 0; i < reference.length; i++) {
            assertTrue("reference: " + reference[i],
                       logger.isReference(reference[i]));
        }
        final String[] noReference = {
            "&",
            "&;",
            "&#;",
            "&#a;",
            "&#X0;",
            "&#x;",
            "&#xg;",
        };
        for (int i = 0; i < noReference.length; i++) {
            assertFalse("no reference: " + noReference[i],
                       logger.isReference(noReference[i]));
        }

        outStream.close();
    }

    @Test
    public void testCloseStream()
        throws IOException
    {
        final XMLLogger logger = new XMLLogger(outStream, true);
        logger.auditStarted(null);
        logger.auditFinished(null);
        final String[] expectedLines = {};
        verifyLines(expectedLines);
    }

    @Test
    public void testNoCloseStream()
        throws IOException
    {
        final XMLLogger logger = new XMLLogger(outStream, false);
        logger.auditStarted(null);
        logger.auditFinished(null);
        outStream.close();
        final String[] expectedLines = {};
        verifyLines(expectedLines);
    }

    @Test
    public void testFileStarted()
        throws IOException
    {
        final XMLLogger logger = new XMLLogger(outStream, true);
        logger.auditStarted(null);
        final AuditEvent ev = new AuditEvent(this, "Test.java");
        logger.fileStarted(ev);
        logger.auditFinished(null);
        final String[] expectedLines = {"<file name=\"Test.java\">"};
        verifyLines(expectedLines);
    }

    @Test
    public void testFileFinished()
        throws IOException
    {
        final XMLLogger logger = new XMLLogger(outStream, true);
        logger.auditStarted(null);
        final AuditEvent ev = new AuditEvent(this, "Test.java");
        logger.fileFinished(ev);
        logger.auditFinished(null);
        final String[] expectedLines = {"</file>"};
        verifyLines(expectedLines);
    }

    @Test
    public void testAddError() throws IOException
    {
        final XMLLogger logger = new XMLLogger(outStream, true);
        logger.auditStarted(null);
        final LocalizedMessage message =
            new LocalizedMessage(1, 1,
                "messages.properties", "key", null, SeverityLevel.ERROR, null,
                this.getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "Test.java", message);
        logger.addError(ev);
        logger.auditFinished(null);
        final String[] expectedLines = {
            "<error line=\"1\" column=\"1\" severity=\"error\" message=\"key\" source=\"com.puppycrawl.tools.checkstyle.XMLLoggerTest\"/>",
        };
        verifyLines(expectedLines);
    }

    @Test
    public void testAddException()
        throws IOException
    {
        final XMLLogger logger = new XMLLogger(outStream, true);
        logger.auditStarted(null);
        final LocalizedMessage message =
            new LocalizedMessage(1, 1,
                "messages.properties", null, null, null, this.getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "Test.java", message);
        logger.addException(ev, new TestThrowable());
        logger.auditFinished(null);
        final String[] expectedLines = {
            "&lt;exception&gt;",
            "&lt;![CDATA[",
            "stackTrace]]&gt;",
            "&lt;/exception&gt;",
            "",
        };
        verifyLines(expectedLines);
    }

    private String[] getOutStreamLines()
        throws IOException
    {
        final byte[] bytes = outStream.toByteArray();
        final ByteArrayInputStream inStream =
            new ByteArrayInputStream(bytes);
        final BufferedReader reader =
            new BufferedReader(new InputStreamReader(inStream));
        final List<String> lineList = Lists.newArrayList();
        while (true) {
            final String line = reader.readLine();
            if (line == null) {
                break;
            }
            lineList.add(line);
        }
        reader.close();
        return lineList.toArray(new String[lineList.size()]);
    }

    /**
     * Verify output lines from auditStart to auditEnd.
     * Take into consideration checkstyle element (first and last lines).
     * @param aExpectedLines expected error report lines
     */
    private void verifyLines(String[] aExpectedLines)
        throws IOException
    {
        final String[] lines = getOutStreamLines();
        assertEquals("length.", aExpectedLines.length + 3, lines.length);
        assertEquals("first line.",
                     "<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
                     lines[0]);
        Pattern checkstyleOpenTag = Utils.getPattern("^<checkstyle version=\".*\">$");
        assertTrue("second line.", checkstyleOpenTag.matcher(lines[1]).matches());
        for (int i = 0; i < aExpectedLines.length; i++) {
            assertEquals("line " + i + ".", aExpectedLines[i], lines[i + 2]);
        }
        assertEquals("last line.", "</checkstyle>", lines[lines.length - 1]);
    }

    private static class TestThrowable extends Exception
    {
        @Override
        public void printStackTrace(PrintWriter s)
        {
            s.print("stackTrace");
        }
    }
}
