////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.internal.utils.CloseAndFlushTestByteArrayOutputStream;

public class MetadataGeneratorLoggerTest {

    @Test
    public void testIgnoreSeverityLevel() throws Exception {
        final OutputStream outputStream = new ByteArrayOutputStream();
        final MetadataGeneratorLogger logger = new MetadataGeneratorLogger(outputStream,
                AutomaticBean.OutputStreamOptions.CLOSE);
        final AuditEvent event = new AuditEvent(this, "fileName",
                new Violation(1, 2, "bundle", "key",
                        null, SeverityLevel.IGNORE, null, getClass(), "customViolation"));
        logger.finishLocalSetup();
        logger.addError(event);
        logger.auditFinished(event);
        assertWithMessage("Expected Output")
                .that(outputStream.toString())
                .isEmpty();
    }

    @Test
    public void testAddException() {
        final OutputStream outputStream = new ByteArrayOutputStream();
        final MetadataGeneratorLogger logger = new MetadataGeneratorLogger(outputStream,
                AutomaticBean.OutputStreamOptions.CLOSE);
        final AuditEvent event = new AuditEvent(1);
        logger.addException(event, new IllegalStateException("Test Exception"));
        logger.auditFinished(event);
        assertWithMessage("Violation should contain exception message")
                .that(outputStream.toString())
                .contains("java.lang.IllegalStateException: Test Exception");
    }

    @Test
    public void testClose() throws IOException {
        try (CloseAndFlushTestByteArrayOutputStream outputStream =
                     new CloseAndFlushTestByteArrayOutputStream()) {
            final MetadataGeneratorLogger logger = new MetadataGeneratorLogger(outputStream,
                    AutomaticBean.OutputStreamOptions.CLOSE);
            logger.auditFinished(new AuditEvent(1));
            assertWithMessage("Unexpected close count")
                    .that(outputStream.getCloseCount())
                    .isEqualTo(1);
        }
    }

    @Test
    public void testCloseOutputStreamOptionNone() throws IOException {
        try (CloseAndFlushTestByteArrayOutputStream outputStream =
                     new CloseAndFlushTestByteArrayOutputStream()) {
            final MetadataGeneratorLogger logger = new MetadataGeneratorLogger(outputStream,
                    AutomaticBean.OutputStreamOptions.NONE);
            final AuditEvent event = new AuditEvent(1);
            logger.auditFinished(event);
            assertWithMessage("Unexpected close count")
                    .that(outputStream.getCloseCount())
                    .isEqualTo(0);
        }
    }

    @Test
    public void testFlushStreams() throws Exception {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(100);
             OutputStream outputStream = new BufferedOutputStream(byteArrayOutputStream)) {
            outputStream.write(new byte[] {8});
            final MetadataGeneratorLogger logger = new MetadataGeneratorLogger(outputStream,
                    AutomaticBean.OutputStreamOptions.NONE);
            final AuditEvent event = new AuditEvent(this);
            logger.auditStarted(event);
            assertWithMessage("Stream should be flushed")
                    .that(byteArrayOutputStream.toByteArray())
                    .isEqualTo(new byte[] {8});
            outputStream.write(new byte[] {9});
            logger.fileFinished(event);
            assertWithMessage("Stream should be flushed")
                    .that(byteArrayOutputStream.toByteArray())
                    .isEqualTo(new byte[] {8, 9});
            outputStream.write(new byte[] {10});
            logger.auditFinished(event);
            assertWithMessage("Stream should be flushed")
                    .that(byteArrayOutputStream.toByteArray())
                    .isEqualTo(new byte[] {8, 9, 10});
        }
    }
}
