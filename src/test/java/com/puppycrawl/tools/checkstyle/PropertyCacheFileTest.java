////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.puppycrawl.tools.checkstyle.api.Configuration;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PropertyCacheFile.class, PropertyCacheFileTest.class })
public class PropertyCacheFileTest {

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testNonAccessibleFile() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final File file = temporaryFolder.newFile("file.output");
        file.setReadable(true, false);
        file.setWritable(false, false);
        try {
            new PropertyCacheFile(config, file.getAbsolutePath()).persist();
            fail("FileNotFoundException is expected, since access to the file was denied!");
        }
        catch (FileNotFoundException ex) {
            assertThat(ex.getMessage(), anyOf(endsWith("file.output (Permission denied)"),
                endsWith("file.output (Access is denied)")));
        }
    }

    @Test
    public void testCtor() {
        try {
            new PropertyCacheFile(null, "");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("config can not be null", ex.getMessage());
        }
        try {
            final Configuration config = new DefaultConfiguration("myName");
            new PropertyCacheFile(config, null);
        }
        catch (IllegalArgumentException ex) {
            assertEquals("fileName can not be null", ex.getMessage());
        }
    }

    @Test
    public void testInCache() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final String filePath = temporaryFolder.newFile().getPath();
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath);
        cache.put("myFile", 1);
        assertTrue(cache.isInCache("myFile", 1));
        assertFalse(cache.isInCache("myFile", 2));
        assertFalse(cache.isInCache("myFile1", 1));
    }

    @Test
    public void testCacheDirectoryDoesNotExistAndShouldBeCreated() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final String filePath = String.format(Locale.getDefault(), "%s%2$stemp%2$scache.temp",
            temporaryFolder.getRoot(), File.separator);
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath);
        try {
            cache.persist();
        }
        catch (FileNotFoundException ex) {
            fail("Exception is not expected. Cache directory should be created successfully!");
        }
    }

    @Test
    public void testPathToCacheContainsOnlyFileName() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final String filePath = "temp.cache";
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath);

        try {
            cache.persist();
        }
        catch (FileNotFoundException ex) {
            fail("Exception is not expected!");
        }

        if (Files.exists(Paths.get(filePath))) {
            Files.delete(Paths.get(filePath));
        }
    }

    @Test
    public void testPathToCacheFileContainsIllegalCharacters() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final String filePath = "\\\0:FOO\\server.properties";
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath);
        try {
            cache.persist();
            fail("Exception is expected!");
        }
        catch (IllegalStateException ex) {
            assertThat(ex.getCause(), instanceOf(InvalidPathException.class));
            assertThat(ex.getMessage(), endsWith("server.properties"));
        }
    }

    @Test
    public void testNonAccessibleDirectory() throws Exception {

        final PropertyCacheFile cache;
        final String failMessage;

        // That works fine on Linux/Unix, but ....
        // It's not possible to make a directory/file unreadable in Windows NTFS for owner, that
        // is why we use mock for testing on OS Windows.
        // http://stackoverflow.com/a/4354686
        // https://github.com/google/google-oauth-java-client/issues/55#issuecomment-69403681
        if (System.getProperty("os.name").toLowerCase(Locale.ENGLISH).startsWith("windows")) {
            // We use mock on Windows just to satisfy coverage rate
            cache = mock(PropertyCacheFile.class);
            final String mockExceptionMessage = "...cache";
            final AccessDeniedException mockException =
                new AccessDeniedException(mockExceptionMessage);
            doThrow(new IllegalStateException(mockException)).when(cache).persist();
            failMessage = "AccessDeniedException is expected since we use the mock object.";

        }
        else {
            final Configuration config = new DefaultConfiguration("myName");
            final File directory = temporaryFolder.newFolder("directory");
            directory.setReadable(true, false);
            directory.setWritable(false, false);
            final String filePath = String.format(Locale.getDefault(), "%s%2$sscache%2$stemp.cache",
                directory.getAbsolutePath(), File.separator);
            cache = new PropertyCacheFile(config, filePath);
            failMessage = "AccessDeniedException is expected since directory is readonly.";
        }

        try {
            cache.persist();
            fail(failMessage);
        }
        catch (IllegalStateException ex) {
            assertTrue(ex.getCause() instanceof AccessDeniedException);
            assertThat(ex.getMessage(), endsWith("cache"));
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testExceptionNoSuchAlgorithmException() throws Exception {

        final Configuration config = new DefaultConfiguration("myName");
        final String filePath = temporaryFolder.newFile().getPath();
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath);
        cache.put("myFile", 1);
        mockStatic(MessageDigest.class);

        when(MessageDigest.getInstance("SHA-1"))
                .thenThrow(NoSuchAlgorithmException.class);

        final Class<?>[] param = new Class<?>[1];
        param[0] = Serializable.class;
        final Method method = PropertyCacheFile.class.getDeclaredMethod("getConfigHashCode", param);
        method.setAccessible(true);
        try {
            method.invoke(cache, config);
            fail("InvocationTargetException is expected");
        }
        catch (InvocationTargetException ex) {
            assertTrue(ex.getCause().getCause() instanceof NoSuchAlgorithmException);
            assertEquals("Unable to calculate hashcode.", ex.getCause().getMessage());
        }
    }
}
