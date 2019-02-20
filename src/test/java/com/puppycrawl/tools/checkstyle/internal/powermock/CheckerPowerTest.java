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

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOError;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.powermock.api.mockito.PowerMockito;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

public class CheckerPowerTest {

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testCatchErrorInProcessFilesMethod() throws Exception {
        // The idea of the test is to satisfy coverage rate.
        // An Error indicates serious problems that a reasonable application should not try to
        // catch, but due to issue https://github.com/checkstyle/checkstyle/issues/2285
        // we catch errors in 'processFiles' method. Most such errors are abnormal conditions,
        // that is why we use PowerMockito to reproduce them.
        final File mock = PowerMockito.mock(File.class);
        // Assume that I/O error is happened when we try to invoke 'lastModified()' method.
        final String errorMessage = "Java Virtual Machine is broken"
            + " or has run out of resources necessary for it to continue operating.";
        final Error expectedError = new IOError(new InternalError(errorMessage));
        when(mock.lastModified()).thenThrow(expectedError);
        when(mock.getAbsolutePath()).thenReturn("testFile");
        final Checker checker = new Checker();
        final List<File> filesToProcess = new ArrayList<>();
        filesToProcess.add(mock);
        try {
            checker.process(filesToProcess);
            fail("IOError is expected!");
        }
        // -@cs[IllegalCatchExtended] Testing for catch Error is part of 100% coverage.
        catch (Error error) {
            assertThat("Error cause differs from IOError",
                    error.getCause(), instanceOf(IOError.class));
            assertThat("Error cause is not InternalError",
                    error.getCause().getCause(), instanceOf(InternalError.class));
            assertEquals("Error message is not expected",
                    errorMessage, error.getCause().getCause().getMessage());
        }
    }

    @Test
    public void testCatchErrorWithNoFileName() throws Exception {
        // The idea of the test is to satisfy coverage rate.
        // An Error indicates serious problems that a reasonable application should not try to
        // catch, but due to issue https://github.com/checkstyle/checkstyle/issues/2285
        // we catch errors in 'processFiles' method. Most such errors are abnormal conditions,
        // that is why we use PowerMockito to reproduce them.
        final File mock = PowerMockito.mock(File.class);
        // Assume that I/O error is happened when we try to invoke 'lastModified()' method.
        final String errorMessage = "Java Virtual Machine is broken"
            + " or has run out of resources necessary for it to continue operating.";
        final Error expectedError = new IOError(new InternalError(errorMessage));
        when(mock.lastModified()).thenThrow(expectedError);
        final Checker checker = new Checker();
        final List<File> filesToProcess = new ArrayList<>();
        filesToProcess.add(mock);
        try {
            checker.process(filesToProcess);
            fail("IOError is expected!");
        }
        // -@cs[IllegalCatchExtended] Testing for catch Error is part of 100% coverage.
        catch (Error error) {
            assertThat("Error cause differs from IOError",
                    error.getCause(), instanceOf(IOError.class));
            assertThat("Error cause is not InternalError",
                    error.getCause().getCause(), instanceOf(InternalError.class));
            assertEquals("Error message is not expected",
                    errorMessage, error.getCause().getCause().getMessage());
        }
    }

    @Test
    public void testCatchErrorWithCache() throws Exception {
        final File cacheFile = temporaryFolder.newFile();

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("configuration");
        checkerConfig.addAttribute("charset", StandardCharsets.UTF_8.name());
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());

        final File mock = PowerMockito.mock(File.class);
        final String errorMessage = "Java Virtual Machine is broken"
            + " or has run out of resources necessary for it to continue operating.";
        final Error expectedError = new IOError(new InternalError(errorMessage));
        when(mock.getAbsolutePath()).thenReturn("testFile");
        when(mock.getAbsoluteFile()).thenThrow(expectedError);
        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        final List<File> filesToProcess = new ArrayList<>();
        filesToProcess.add(mock);
        try {
            checker.process(filesToProcess);
            fail("IOError is expected!");
        }
        // -@cs[IllegalCatchExtended] Testing for catch Error is part of 100% coverage.
        catch (Error error) {
            assertThat("Error cause differs from IOError",
                    error.getCause(), instanceOf(IOError.class));
            assertEquals("Error message is not expected",
                    errorMessage, error.getCause().getCause().getMessage());

            // destroy is called by Main
            checker.destroy();

            final Properties cache = new Properties();
            cache.load(Files.newBufferedReader(cacheFile.toPath()));

            assertEquals("Cache has unexpected size",
                    1, cache.size());
            assertNull("testFile is not in cache",
                    cache.getProperty("testFile"));
        }
    }

    @Test
    public void testCatchErrorWithCacheWithNoFileName() throws Exception {
        final File cacheFile = temporaryFolder.newFile();

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("configuration");
        checkerConfig.addAttribute("charset", StandardCharsets.UTF_8.name());
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());

        final File mock = PowerMockito.mock(File.class);
        final String errorMessage = "Java Virtual Machine is broken"
            + " or has run out of resources necessary for it to continue operating.";
        final Error expectedError = new IOError(new InternalError(errorMessage));
        when(mock.getAbsolutePath()).thenThrow(expectedError);
        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        final List<File> filesToProcess = new ArrayList<>();
        filesToProcess.add(mock);
        try {
            checker.process(filesToProcess);
            fail("IOError is expected!");
        }
        // -@cs[IllegalCatchExtended] Testing for catch Error is part of 100% coverage.
        catch (Error error) {
            assertThat("Error cause differs from IOError",
                    error.getCause(), instanceOf(IOError.class));
            assertEquals("Error message is not expected",
                    errorMessage, error.getCause().getCause().getMessage());

            // destroy is called by Main
            checker.destroy();

            final Properties cache = new Properties();
            cache.load(Files.newBufferedReader(cacheFile.toPath()));

            assertEquals("Cache has unexpected size",
                    1, cache.size());
        }
    }

    @Test
    public void testExceptionWithNoFileName() {
        // The idea of the test is to satisfy coverage rate.
        // An Error indicates serious problems that a reasonable application should not try to
        // catch, but due to issue https://github.com/checkstyle/checkstyle/issues/2285
        // we catch errors in 'processFiles' method. Most such errors are abnormal conditions,
        // that is why we use PowerMockito to reproduce them.
        final File mock = PowerMockito.mock(File.class);
        final String errorMessage = "Security Exception";
        final Exception expectedError = new SecurityException(errorMessage);
        when(mock.getAbsolutePath()).thenThrow(expectedError);
        final Checker checker = new Checker();
        final List<File> filesToProcess = new ArrayList<>();
        filesToProcess.add(mock);
        try {
            checker.process(filesToProcess);
            fail("IOError is expected!");
        }
        catch (CheckstyleException ex) {
            assertThat("Error cause differs from SecurityException",
                    ex.getCause(), instanceOf(SecurityException.class));
            assertEquals("Error message is not expected",
                    errorMessage, ex.getCause().getMessage());
        }
    }

    @Test
    public void testExceptionWithCacheAndNoFileName() throws Exception {
        final File cacheFile = temporaryFolder.newFile();

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("configuration");
        checkerConfig.addAttribute("charset", StandardCharsets.UTF_8.name());
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());

        // The idea of the test is to satisfy coverage rate.
        // An Error indicates serious problems that a reasonable application should not try to
        // catch, but due to issue https://github.com/checkstyle/checkstyle/issues/2285
        // we catch errors in 'processFiles' method. Most such errors are abnormal conditions,
        // that is why we use PowerMockito to reproduce them.
        final File mock = PowerMockito.mock(File.class);
        final String errorMessage = "Security Exception";
        final Exception expectedError = new SecurityException(errorMessage);
        when(mock.getAbsolutePath()).thenThrow(expectedError);
        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        final List<File> filesToProcess = new ArrayList<>();
        filesToProcess.add(mock);
        try {
            checker.process(filesToProcess);
            fail("IOError is expected!");
        }
        catch (CheckstyleException ex) {
            assertThat("Error cause differs from SecurityException",
                    ex.getCause(), instanceOf(SecurityException.class));
            assertEquals("Error message is not expected",
                    errorMessage, ex.getCause().getMessage());

            // destroy is called by Main
            checker.destroy();

            final Properties cache = new Properties();
            cache.load(Files.newBufferedReader(cacheFile.toPath()));

            assertEquals("Cache has unexpected size",
                    1, cache.size());
        }
    }

}
