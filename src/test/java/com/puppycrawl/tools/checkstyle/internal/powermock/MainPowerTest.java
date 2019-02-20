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

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.puppycrawl.tools.checkstyle.Main;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Main.class, CommonUtil.class})
public class MainPowerTest {

    private static final String SHORT_USAGE = String.format(Locale.ROOT,
            "Usage: checkstyle [OPTIONS]... FILES...%n"
            + "Try 'checkstyle --help' for more information.%n");

    private static final String EOL = System.getProperty("line.separator");

    @Rule
    public final SystemErrRule systemErr = new SystemErrRule().enableLog().mute();
    @Rule
    public final SystemOutRule systemOut = new SystemOutRule().enableLog().mute();

    /**
     * This test is a workaround for the Jacoco limitations. A call to {@link System#exit(int)}
     * will never return, so Jacoco coverage probe will be missing. By mocking the {@code System}
     * class we turn {@code System.exit()} to noop and the Jacoco coverage probe should succeed.
     *
     * @throws Exception if error occurs
     * @see <a href="https://github.com/jacoco/jacoco/issues/117">Jacoco issue 117</a>
     */
    @Test
    public void testJacocoWorkaround() throws Exception {
        final String expected = "Missing required parameter: <files>" + EOL + SHORT_USAGE;
        mockStatic(System.class);
        Main.main();
        assertEquals("Unexpected output log", "", systemOut.getLog());
        assertEquals("Unexpected system error log", expected, systemErr.getLog());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testListFilesNotFile() throws Exception {
        final Class<?> optionsClass = Class.forName(Main.class.getName());
        final Method method = optionsClass.getDeclaredMethod("listFiles", File.class, List.class);
        method.setAccessible(true);

        final File fileMock = mock(File.class);
        when(fileMock.canRead()).thenReturn(true);
        when(fileMock.isDirectory()).thenReturn(false);
        when(fileMock.isFile()).thenReturn(false);

        final List<File> result = (List<File>) method.invoke(null, fileMock,
                new ArrayList<Pattern>());
        assertEquals("Invalid result size", 0, result.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testListFilesDirectoryWithNull() throws Exception {
        final Class<?> optionsClass = Class.forName(Main.class.getName());
        final Method method = optionsClass.getDeclaredMethod("listFiles", File.class, List.class);
        method.setAccessible(true);

        final File fileMock = mock(File.class);
        when(fileMock.canRead()).thenReturn(true);
        when(fileMock.isDirectory()).thenReturn(true);
        when(fileMock.listFiles()).thenReturn(null);

        final List<File> result = (List<File>) method.invoke(null, fileMock,
                new ArrayList<Pattern>());
        assertEquals("Invalid result size", 0, result.size());
    }

}
