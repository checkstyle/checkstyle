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

import static java.util.Locale.ENGLISH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.NewlineAtEndOfFileCheck;

public class NewlineAtEndOfFileCheckPowerTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/newlineatendoffile";
    }

    @Test
    public void testWrongSeparatorLength() throws Exception {
        final NewlineAtEndOfFileCheck check = new NewlineAtEndOfFileCheck();
        final DefaultConfiguration checkConfig = createModuleConfig(NewlineAtEndOfFileCheck.class);
        check.configure(checkConfig);

        final Method method = NewlineAtEndOfFileCheck.class
                .getDeclaredMethod("endsWithNewline", RandomAccessFile.class);
        method.setAccessible(true);
        final RandomAccessFile file = mock(RandomAccessFile.class);
        when(file.length()).thenReturn(2_000_000L);
        try {
            method.invoke(new NewlineAtEndOfFileCheck(), file);
            fail("Exception is expected");
        }
        catch (InvocationTargetException ex) {
            assertTrue("Error type is unexpected",
                    ex.getCause() instanceof IOException);
            if (System.getProperty("os.name").toLowerCase(ENGLISH).startsWith("windows")) {
                assertEquals("Error message is unexpected",
                        "Unable to read 2 bytes, got 0", ex.getCause().getMessage());
            }
            else {
                assertEquals("Error message is unexpected",
                        "Unable to read 1 bytes, got 0", ex.getCause().getMessage());
            }
        }
    }

}
