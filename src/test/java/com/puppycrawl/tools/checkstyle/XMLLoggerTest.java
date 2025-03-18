///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean.OutputStreamOptions;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.internal.utils.CloseAndFlushTestByteArrayOutputStream;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

/**
 * Enter a description of class XMLLoggerTest.java.
 */
// -@cs[AbbreviationAsWordInName] Test should be named as its main class.
public class XMLLoggerTest extends AbstractXmlTestSupport {

    /**
     * Output stream to hold the test results. The IntelliJ IDEA issues the AutoCloseableResource
     * warning here, so it needs to be suppressed. The {@code ByteArrayOutputStream} does not hold
     * any resources that need to be released.
     */
    private final CloseAndFlushTestByteArrayOutputStream outStream =
        new CloseAndFlushTestByteArrayOutputStream();

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/xmllogger";
    }

    @Test
    public void testEncode()
            throws IOException {
        final XMLLogger test = new XMLLogger(outStream, OutputStreamOptions.NONE);
        assertWithMessage("should be able to create XMLLogger without issue")
            .that(test)
            .isNotNull();
        final String[][] encodings = {
            {"<", "&lt;"},
            {">", "&gt;"},
            {"'", "&apos;"},
            {"\"", "&quot;"},
            {"&", "&amp;"},
          
        };
        for (String[] encoding : encodings) {
            final String encoded = XMLLogger.encode(encoding[0]);
            assertWithMessage("\"" + encoding[0] + "\"")
                .that(encoded).isEqualTo(encoding[1]);
        }
        outStream.close();
    }

    @Test
    public void testCloseStream()
            throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, OutputStreamOptions.CLOSE);
        logger.auditStarted(null);
        logger.auditFinished(null);

        assertWithMessage("Invalid close count")
            .that(outStream.getCloseCount())
            .isEqualTo(1);

        verifyXml(getPath("ExpectedXMLLoggerEmpty.xml"), outStream);
    }
    @Test
    public void testFileStartedAndFinished() throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, OutputStreamOptions.CLOSE);
        logger.auditStarted(null);
        final AuditEvent ev = new AuditEvent(this, "Test.java");
        logger.fileStarted(ev);
        logger.fileFinished(ev);
        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLogger.xml"), outStream);
    }

    private static final class TestException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        private TestException(String msg, Throwable cause) {
            super(msg, cause);
        }

        @Override
        public void printStackTrace(PrintWriter printWriter) {
            printWriter.print("stackTrace\r\nexample");
        }

    }

}
