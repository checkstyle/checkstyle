////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.common.io.ByteStreams;
import com.google.common.io.Closeables;
import com.google.common.io.Flushables;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PropertyCacheFile.class, ByteStreams.class,
        CommonUtils.class, Closeables.class, Flushables.class})
public class PropertyCacheFileTest {

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

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
    public void testResetIfFileDoesNotExist() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final PropertyCacheFile cache = new PropertyCacheFile(config, "fileDoesNotExist.txt");

        cache.load();

        assertNotNull(cache.get(PropertyCacheFile.CONFIG_HASH_KEY));
    }

    @Test
    public void testCloseAndFlushOutputStreamAfterCreatingHashCode() throws IOException {
        mockStatic(Closeables.class);
        doNothing().when(Closeables.class);
        Closeables.close(any(ObjectOutputStream.class), Matchers.eq(false));
        mockStatic(Flushables.class);
        doNothing().when(Flushables.class);
        Flushables.flush(any(ObjectOutputStream.class), Matchers.eq(false));

        final Configuration config = new DefaultConfiguration("myName");
        final PropertyCacheFile cache = new PropertyCacheFile(config, "fileDoesNotExist.txt");
        cache.load();

        verifyStatic(times(1));

        Closeables.close(any(ObjectOutputStream.class), Matchers.eq(false));
        verifyStatic(times(1));
        Flushables.flush(any(ObjectOutputStream.class), Matchers.eq(false));
    }

    @Test
    public void testPopulateDetails() throws IOException {
        mockStatic(Closeables.class);
        doNothing().when(Closeables.class);
        Closeables.closeQuietly(any(FileInputStream.class));

        final Configuration config = new DefaultConfiguration("myName");
        final PropertyCacheFile cache = new PropertyCacheFile(config,
                "src/test/resources/com/puppycrawl/tools/checkstyle/cache.tmp");
        cache.load();

        final String hash = cache.get(PropertyCacheFile.CONFIG_HASH_KEY);
        assertNotNull(hash);
        assertNull(cache.get("key"));

        cache.load();

        assertEquals(hash, cache.get(PropertyCacheFile.CONFIG_HASH_KEY));
        assertEquals("value", cache.get("key"));
        assertNotNull(cache.get(PropertyCacheFile.CONFIG_HASH_KEY));

        verifyStatic(times(2));
        Closeables.closeQuietly(any(FileInputStream.class));
    }

    @Test
    public void testConfigHashOnReset() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final String filePath = temporaryFolder.newFile().getPath();
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath);

        cache.load();

        final String hash = cache.get(PropertyCacheFile.CONFIG_HASH_KEY);
        assertNotNull(hash);

        cache.reset();

        assertEquals(hash, cache.get(PropertyCacheFile.CONFIG_HASH_KEY));
    }

    @Test
    public void testConfigHashRemainsOnResetExternalResources() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final String filePath = temporaryFolder.newFile().getPath();
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath);

        // create cache with one file
        cache.load();
        cache.put("myFile", 1);

        final String hash = cache.get(PropertyCacheFile.CONFIG_HASH_KEY);
        assertNotNull(hash);

        // apply new external resource to clear cache
        final Set<String> resources = new HashSet<>();
        resources.add("dummy");
        cache.putExternalResources(resources);

        assertEquals(hash, cache.get(PropertyCacheFile.CONFIG_HASH_KEY));
        assertFalse(cache.isInCache("myFile", 1));
    }

    @Test
    public void testExternalResourseIsSavedInCache() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final String filePath = temporaryFolder.newFile().getPath();
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath);

        cache.load();

        final Set<String> resources = new HashSet<>();
        final String pathToResource =
                "src/test/resources/com/puppycrawl/tools/checkstyle/externalResourse.tmp";
        resources.add(pathToResource);
        cache.putExternalResources(resources);

        assertFalse(cache.get("module-resource*?:" + pathToResource).isEmpty());
    }

    /**
     * This SuppressWarning("unchecked") required to suppress
     * "Unchecked generics array creation for varargs parameter" during mock
     * @throws IOException when smth wrong with file creation or cache.load
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testNonExistingResource() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final String filePath = temporaryFolder.newFile().getPath();
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath);

        // create cache with one file
        cache.load();
        final String myFile = "myFile";
        cache.put(myFile, 1);

        final String hash = cache.get(PropertyCacheFile.CONFIG_HASH_KEY);
        assertNotNull(hash);

        mockStatic(ByteStreams.class);

        when(ByteStreams.toByteArray(any(BufferedInputStream.class)))
                .thenThrow(IOException.class);

        // apply new external resource to clear cache
        final Set<String> resources = new HashSet<>();
        final String resource = "/com/puppycrawl/tools/checkstyle/java.header";
        resources.add(resource);
        cache.putExternalResources(resources);

        assertFalse(cache.isInCache(myFile, 1));
        assertFalse(cache.isInCache(resource, 1));
    }

    @Test
    public void testFlushAndCloseCacheFileOutputStream() throws IOException {
        mockStatic(Closeables.class);
        doNothing().when(Closeables.class);
        Closeables.close(any(FileOutputStream.class), Matchers.eq(false));
        mockStatic(Flushables.class);
        doNothing().when(Flushables.class);
        Flushables.flush(any(FileOutputStream.class), Matchers.eq(false));

        final Configuration config = new DefaultConfiguration("myName");
        final PropertyCacheFile cache = new PropertyCacheFile(config,
            temporaryFolder.newFile().getPath());

        cache.put("CheckedFileName.java", System.currentTimeMillis());
        cache.persist();

        verifyStatic(times(1));
        Closeables.close(any(FileOutputStream.class), Matchers.eq(false));
        verifyStatic(times(1));
        Flushables.flush(any(FileOutputStream.class), Matchers.eq(false));
    }

    @Test
    public void testCacheDirectoryDoesNotExistAndShouldBeCreated() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final String filePath = String.format(Locale.getDefault(), "%s%2$stemp%2$scache.temp",
            temporaryFolder.getRoot(), File.separator);
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath);

        // no exception expected, cache directory should be created
        cache.persist();

        assertTrue("cache exists in directory", new File(filePath).exists());
    }

    @Test
    public void testPathToCacheContainsOnlyFileName() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final String filePath = "temp.cache";
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath);

        // no exception expected
        cache.persist();

        if (Files.exists(Paths.get(filePath))) {
            Files.delete(Paths.get(filePath));
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
        final Method method =
            PropertyCacheFile.class.getDeclaredMethod("getHashCodeBasedOnObjectContent", param);
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

    @Test
    public void testPutNonExsistingExternalResourceSameExceptionBetweenRuns() throws Exception {
        final File cacheFile = temporaryFolder.newFile();

        // We mock getUriByFilename method of CommonUtils to garantee that it will
        // throw CheckstyleException with the specific content.
        mockStatic(CommonUtils.class);
        final CheckstyleException mockException =
            new CheckstyleException("Cannot get URL for cache file " + cacheFile.getAbsolutePath());
        when(CommonUtils.getUriByFilename(any(String.class)))
            .thenThrow(mockException);

        // We invoke 'putExternalResources' twice to invalidate cache
        // and have two identical exceptions whith the equal content
        final int numberOfRuns = 2;
        final String[] configHashes = new String[numberOfRuns];
        final String[] externalResourceHashes = new String[numberOfRuns];
        for (int i = 0; i < numberOfRuns; i++) {
            final Configuration config = new DefaultConfiguration("myConfig");
            final PropertyCacheFile cache = new PropertyCacheFile(config, cacheFile.getPath());
            cache.load();

            configHashes[i] = cache.get(PropertyCacheFile.CONFIG_HASH_KEY);
            assertNotNull(configHashes[i]);

            final Set<String> nonExistingExternalResources = new HashSet<>();
            final String externalResourceFileName = "non_existing_file.xml";
            nonExistingExternalResources.add(externalResourceFileName);
            cache.putExternalResources(nonExistingExternalResources);

            externalResourceHashes[i] = cache.get(PropertyCacheFile.EXTERNAL_RESOURCE_KEY_PREFIX
                    + externalResourceFileName);
            assertNotNull(externalResourceHashes[i]);

            cache.persist();

            final Properties cacheDetails = new Properties();
            cacheDetails.load(Files.newBufferedReader(cacheFile.toPath()));

            final int expectedNumberOfObjectsInCacheFile = 2;
            assertEquals(expectedNumberOfObjectsInCacheFile, cacheDetails.size());
        }

        assertEquals(configHashes[0], configHashes[1]);
        assertEquals(externalResourceHashes[0], externalResourceHashes[1]);
    }

    /**
     * It is OK to have long test method name here as it describes the test purpose.
     * @noinspection InstanceMethodNamingConvention
     */
    @Test
    public void testPutNonExsistingExternalResourceDifferentExceptionsBetweenRuns()
            throws Exception {

        final File cacheFile = temporaryFolder.newFile();

        // We invoke 'putExternalResources' twice to invalidate cache
        // and have two different exceptions with different content.
        final int numberOfRuns = 2;
        final String[] configHashes = new String[numberOfRuns];
        final String[] externalResourceHashes = new String[numberOfRuns];
        for (int i = 0; i < numberOfRuns; i++) {
            final Configuration config = new DefaultConfiguration("myConfig");
            final PropertyCacheFile cache = new PropertyCacheFile(config, cacheFile.getPath());

            // We mock getUriByFilename method of CommonUtils to garantee that it will
            // throw CheckstyleException with the specific content.
            mockStatic(CommonUtils.class);
            final CheckstyleException mockException = new CheckstyleException("Exception #" + i);
            when(CommonUtils.getUriByFilename(any(String.class)))
                .thenThrow(mockException);

            cache.load();

            configHashes[i] = cache.get(PropertyCacheFile.CONFIG_HASH_KEY);
            assertNotNull(configHashes[i]);

            final Set<String> nonExistingExternalResources = new HashSet<>();
            final String externalResourceFileName = "non_existing_file.xml";
            nonExistingExternalResources.add(externalResourceFileName);
            cache.putExternalResources(nonExistingExternalResources);

            externalResourceHashes[i] = cache.get(PropertyCacheFile.EXTERNAL_RESOURCE_KEY_PREFIX
                    + externalResourceFileName);
            assertNotNull(externalResourceHashes[i]);

            cache.persist();

            final Properties cacheDetails = new Properties();
            cacheDetails.load(Files.newBufferedReader(cacheFile.toPath()));

            final int expectedNumberOfObjectsInCacheFile = 2;
            assertEquals(expectedNumberOfObjectsInCacheFile, cacheDetails.size());
        }

        assertEquals(configHashes[0], configHashes[1]);
        assertNotEquals(externalResourceHashes[0], externalResourceHashes[1]);
    }

    @Test
    public void testChangeInConfig() throws Exception {
        final DefaultConfiguration config = new DefaultConfiguration("myConfig");
        config.addAttribute("attr", "value");

        final File cacheFile = temporaryFolder.newFile();
        final PropertyCacheFile cache = new PropertyCacheFile(config, cacheFile.getPath());
        cache.load();

        final String expectedInitialConfigHash = "91753B970AFDF9F5F3DFA0D258064841949D3C6B";
        final String actualInitialConfigHash = cache.get(PropertyCacheFile.CONFIG_HASH_KEY);
        assertEquals(expectedInitialConfigHash, actualInitialConfigHash);

        cache.persist();

        final Properties details = new Properties();
        details.load(Files.newBufferedReader(cacheFile.toPath()));
        assertEquals(1, details.size());

        // change in config
        config.addAttribute("newAttr", "newValue");

        final PropertyCacheFile cacheAfterChangeInConfig =
            new PropertyCacheFile(config, cacheFile.getPath());
        cacheAfterChangeInConfig.load();

        final String expectedConfigHashAfterChange = "4CF5EC78955B81D76153ACC2CA6D60CB77FDCB2A";
        final String actualConfigHashAfterChange =
            cacheAfterChangeInConfig.get(PropertyCacheFile.CONFIG_HASH_KEY);
        assertEquals(expectedConfigHashAfterChange, actualConfigHashAfterChange);

        cacheAfterChangeInConfig.persist();

        final Properties detailsAfterChangeInConfig = new Properties();
        detailsAfterChangeInConfig.load(Files.newBufferedReader(cacheFile.toPath()));
        assertEquals(1, detailsAfterChangeInConfig.size());
    }
}
