////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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

import static org.hamcrest.CoreMatchers.endsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.SortedSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.puppycrawl.tools.checkstyle.AbstractXmlTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.Definitions;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.MessageDispatcher;
import com.puppycrawl.tools.checkstyle.checks.TranslationCheck;

@RunWith(PowerMockRunner.class)
public class TranslationCheckPowerTest extends AbstractXmlTestSupport {

    @Captor
    private ArgumentCaptor<SortedSet<LocalizedMessage>> captor;

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/translation";
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testLogIoExceptionFileNotFound() throws Exception {
        //I can't put wrong file here. Checkstyle fails before check started.
        //I saw some usage of file or handling of wrong file in Checker, or somewhere
        //in checks running part. So I had to do it with reflection to improve coverage.
        final TranslationCheck check = new TranslationCheck();
        final DefaultConfiguration checkConfig = createModuleConfig(TranslationCheck.class);
        final MessageDispatcher dispatcher = mock(MessageDispatcher.class);
        check.configure(checkConfig);
        check.setMessageDispatcher(dispatcher);

        final Method loadKeys =
            check.getClass().getDeclaredMethod("getTranslationKeys", File.class);
        loadKeys.setAccessible(true);
        final Set<String> keys = (Set<String>) loadKeys.invoke(check, new File(".no.such.file"));
        assertTrue("Translation keys should be empty when File is not found", keys.isEmpty());

        Mockito.verify(dispatcher, times(1)).fireErrors(any(String.class), captor.capture());
        final String actual = captor.getValue().first().getMessage();
        final LocalizedMessage localizedMessage = new LocalizedMessage(1,
                Definitions.CHECKSTYLE_BUNDLE, "general.fileNotFound",
                null, null, getClass(), null);
        assertEquals("Invalid message", localizedMessage.getMessage(), actual);
    }

    @Test
    public void testLogIoException() throws Exception {
        //I can't put wrong file here. Checkstyle fails before check started.
        //I saw some usage of file or handling of wrong file in Checker, or somewhere
        //in checks running part. So I had to do it with reflection to improve coverage.
        final TranslationCheck check = new TranslationCheck();
        final DefaultConfiguration checkConfig = createModuleConfig(TranslationCheck.class);
        final MessageDispatcher dispatcher = mock(MessageDispatcher.class);
        check.configure(checkConfig);
        check.setMessageDispatcher(dispatcher);

        final Method logException = check.getClass().getDeclaredMethod("logException",
                Exception.class,
                File.class);
        logException.setAccessible(true);
        final File file = new File("");
        logException.invoke(check, new IOException("test exception"), file);

        Mockito.verify(dispatcher, times(1)).fireErrors(any(String.class), captor.capture());
        final String actual = captor.getValue().first().getMessage();
        assertThat("Invalid message: " + actual, actual, endsWith("test exception"));
    }

}
