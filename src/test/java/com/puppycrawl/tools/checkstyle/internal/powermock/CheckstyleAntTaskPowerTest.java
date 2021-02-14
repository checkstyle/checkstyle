////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.Definitions;
import com.puppycrawl.tools.checkstyle.ant.CheckstyleAntTask;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.internal.powermock.testmodules.CheckstyleAntTaskLogStub;
import com.puppycrawl.tools.checkstyle.internal.powermock.testmodules.CheckstyleAntTaskStub;
import com.puppycrawl.tools.checkstyle.internal.powermock.testmodules.MessageLevelPair;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CheckstyleAntTask.class)
public class CheckstyleAntTaskPowerTest extends AbstractPathTestSupport {

    private static final String FLAWLESS_INPUT =
            "InputCheckstyleAntTaskFlawless.java";
    private static final String CONFIG_FILE =
            "InputCheckstyleAntTaskTestChecks.xml";

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/ant/checkstyleanttask/";
    }

    @Test
    public final void testExecuteLogOutput() throws Exception {
        final CheckstyleAntTaskLogStub antTask = new CheckstyleAntTaskLogStub();
        final URL url = new File(getPath(CONFIG_FILE)).toURI().toURL();
        antTask.setProject(new Project());
        antTask.setConfig(url.toString());
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));

        mockStatic(System.class);
        when(System.currentTimeMillis()).thenReturn(1L);

        antTask.execute();

        final LocalizedMessage auditStartedMessage = new LocalizedMessage(1,
                Definitions.CHECKSTYLE_BUNDLE, "DefaultLogger.auditStarted",
                null, null,
                getClass(), null);
        final LocalizedMessage auditFinishedMessage = new LocalizedMessage(1,
                Definitions.CHECKSTYLE_BUNDLE, "DefaultLogger.auditFinished",
                null, null,
                getClass(), null);

        final List<MessageLevelPair> expectedList = Arrays.asList(
                new MessageLevelPair("checkstyle version ", Project.MSG_VERBOSE),
                new MessageLevelPair("Adding standalone file for audit", Project.MSG_VERBOSE),
                new MessageLevelPair("To locate the files took 0 ms.", Project.MSG_VERBOSE),
                new MessageLevelPair("Running Checkstyle ", Project.MSG_INFO),
                new MessageLevelPair("Using configuration ", Project.MSG_VERBOSE),
                new MessageLevelPair(auditStartedMessage.getMessage(), Project.MSG_DEBUG),
                new MessageLevelPair(auditFinishedMessage.getMessage(), Project.MSG_DEBUG),
                new MessageLevelPair("To process the files took 0 ms.", Project.MSG_VERBOSE),
                new MessageLevelPair("Total execution took 0 ms.", Project.MSG_VERBOSE)
        );

        final List<MessageLevelPair> loggedMessages = antTask.getLoggedMessages();

        assertEquals("Amount of log messages is unexpected",
                expectedList.size(), loggedMessages.size());
        for (int i = 0; i < expectedList.size(); i++) {
            final MessageLevelPair expected = expectedList.get(i);
            final MessageLevelPair actual = loggedMessages.get(i);
            assertTrue("Log messages were expected",
                    actual.getMsg().startsWith(expected.getMsg()));
            assertEquals("Log messages were expected",
                    expected.getLevel(), actual.getLevel());
        }
    }

    @Test
    public void testCheckerException() throws IOException {
        final CheckstyleAntTask antTask = new CheckstyleAntTaskStub();
        antTask.setConfig(getPath(CONFIG_FILE));
        antTask.setProject(new Project());
        antTask.setFile(new File(""));
        try {
            antTask.execute();
            fail("Exception is expected");
        }
        catch (BuildException ex) {
            assertTrue("Error message is unexpected",
                    ex.getMessage().startsWith("Unable to process files:"));
        }
    }
}
