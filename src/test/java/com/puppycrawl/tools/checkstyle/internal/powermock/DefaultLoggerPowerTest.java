////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.internal.powermock;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.Definitions;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class DefaultLoggerPowerTest {

    @Test
    public void testNewCtor() throws Exception {
        final OutputStream infoStream = spy(new ByteArrayOutputStream());
        final ByteArrayOutputStream errorStream = spy(new ByteArrayOutputStream());
        final DefaultLogger dl = new DefaultLogger(infoStream,
                AutomaticBean.OutputStreamOptions.CLOSE, errorStream,
                AutomaticBean.OutputStreamOptions.CLOSE);
        dl.auditStarted(null);
        dl.addException(new AuditEvent(5000, "myfile"), new IllegalStateException("upsss"));
        dl.auditFinished(new AuditEvent(6000, "myfile"));
        final String output = errorStream.toString(StandardCharsets.UTF_8.name());
        final LocalizedMessage addExceptionMessage = new LocalizedMessage(1,
                Definitions.CHECKSTYLE_BUNDLE, DefaultLogger.ADD_EXCEPTION_MESSAGE,
                new String[] {"myfile"}, null,
                getClass(), null);
        final LocalizedMessage startMessage = new LocalizedMessage(1,
                Definitions.CHECKSTYLE_BUNDLE, DefaultLogger.AUDIT_STARTED_MESSAGE,
                CommonUtil.EMPTY_STRING_ARRAY, null,
                getClass(), null);
        final LocalizedMessage finishMessage = new LocalizedMessage(1,
                Definitions.CHECKSTYLE_BUNDLE, DefaultLogger.AUDIT_FINISHED_MESSAGE,
                CommonUtil.EMPTY_STRING_ARRAY, null,
                getClass(), null);

        verify(infoStream, times(1)).close();
        verify(errorStream, times(1)).close();
        final String infoOutput = infoStream.toString();
        assertTrue("Message should contain exception info, but was " + infoOutput,
                infoOutput.contains(startMessage.getMessage()));
        assertTrue("Message should contain exception info, but was " + infoOutput,
                infoOutput.contains(finishMessage.getMessage()));
        assertTrue("Message should contain exception info, but was " + output,
                output.contains(addExceptionMessage.getMessage()));
        assertTrue("Message should contain exception info, but was " + output,
                output.contains("java.lang.IllegalStateException: upsss"));
    }

}
