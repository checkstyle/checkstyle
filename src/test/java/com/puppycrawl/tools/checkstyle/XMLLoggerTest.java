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
        @Test
        public void testVerifyEncode() throws Exception {
            verifyWithInlineConfigParserAndXmlLogger("InputEncode.xml", "ExpectedEncode.xml");
        }
    
        @Test
        public void testVerifyIsReference() throws Exception {
            verifyWithInlineConfigParserAndXmlLogger("InputIsReference.xml", "ExpectedIsReference.xml");
        }
    
        @Test
        public void testVerifyCloseStream() throws Exception {
            verifyWithInlineConfigParserAndXmlLogger("InputCloseStream.xml", "ExpectedCloseStream.xml");
        }
    
        @Test
        public void testVerifyNoCloseStream() throws Exception {
            verifyWithInlineConfigParserAndXmlLogger("InputNoCloseStream.xml", "ExpectedNoCloseStream.xml");
        }
    
        @Test
        public void testVerifyFileStarted() throws Exception {
            verifyWithInlineConfigParserAndXmlLogger("InputFileStarted.xml", "ExpectedFileStarted.xml");
        }
    
        @Test
        public void testVerifyFileFinished() throws Exception {
            verifyWithInlineConfigParserAndXmlLogger("InputFileFinished.xml", "ExpectedFileFinished.xml");
        }
    
        @Test
        public void testVerifyAddError() throws Exception {
            verifyWithInlineConfigParserAndXmlLogger("InputAddError.xml", "ExpectedAddError.xml");
        }
    
        @Test
        public void testVerifyAddErrorWithNullFileName() throws Exception {
            verifyWithInlineConfigParserAndXmlLogger("InputAddErrorNullFileName.xml", "ExpectedAddErrorNullFileName.xml");
        }
    
        @Test
        public void testVerifyAddException() throws Exception {
            verifyWithInlineConfigParserAndXmlLogger("InputAddException.xml", "ExpectedAddException.xml");
        }
    
        @Test
        public void testVerifyAuditFinishedWithoutFileFinished() throws Exception {
            verifyWithInlineConfigParserAndXmlLogger("InputAuditFinishedWithoutFileFinished.xml", "ExpectedAuditFinishedWithoutFileFinished.xml");
        }
    
        @Test
        public void testVerifyLogger() throws Exception {
            verifyWithInlineConfigParserAndXmlLogger("InputXMLLogger.java", "ExpectedXMLLoggerWithChecker.xml");
        }
    
        @Test
        public void testVerifyLoggerWithMultipleInput() throws Exception {
            verifyWithInlineConfigParserAndXmlLogger(
                    "InputXMLLogger.java",
                    "ExpectedXMLLoggerDuplicatedFile.xml",
                    List.of("InputXMLLogger.java", "InputXMLLogger.java"));
        }
    
        @Test
        public void testNullOutputStreamOptions() {
            try {
                final XMLLogger logger = new XMLLogger(outStream, (OutputStreamOptions) null);
                assertWithMessage("Null instance").that(logger).isNotNull();
                assertWithMessage("Exception was expected").fail();
            } catch (IllegalArgumentException exception) {
                assertWithMessage("Invalid error message").that(exception.getMessage())
                        .isEqualTo("Parameter outputStreamOptions can not be null");
            }
        }
    
        @Test
        public void testFinishLocalSetup() {
            final XMLLogger logger = new XMLLogger(outStream, OutputStreamOptions.CLOSE);
            logger.finishLocalSetup();
            logger.auditStarted(null);
            logger.auditFinished(null);
            assertWithMessage("instance should not be null").that(logger).isNotNull();
        }
    }
