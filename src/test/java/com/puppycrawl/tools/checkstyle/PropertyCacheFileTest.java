////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
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
import org.mockito.ArgumentMatchers;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteStreams;
import com.google.common.io.Closeables;
import com.google.common.io.Flushables;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PropertyCacheFile.class, ByteStreams.class,
        CommonUtil.class, Closeables.class, Flushables.class})
public class PropertyCacheFileTest extends AbstractPathTestSupport {

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/propertycachefile";
    }

    @Test
    public void testCtor() {
        try {
            final Object test = new PropertyCacheFile(null, "");
            fail("exception expected but got " + test);
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Invalid exception message",
                    "config can not be null", ex.getMessage());
        }
        try {
            final Configuration config = new DefaultConfiguration("myName");
            final Object test = new PropertyCacheFile(config, null);
            fail("exception expected but got " + test);
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Invalid exception message",
                    "fileName can not be null", ex.getMessage());
        }
    }

    @Test
    public void testInCache() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final String filePath = temporaryFolder.newFile().getPath();
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath);
        cache.put("myFile", 1);
        assertTrue("Should return true when file is in cache",
                cache.isInCache("myFile", 1));
        assertFalse("Should return false when file is not in cache",
                cache.isInCache("myFile", 2));
        assertFalse("Should return false when file is not in cache",
                cache.isInCache("myFile1", 1));
    }

    @Test
    public void testResetIfFileDoesNotExist() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final PropertyCacheFile cache = new PropertyCacheFile(config, "fileDoesNotExist.txt");

        cache.load();

        assertNotNull("Config hash key should not be null",
                cache.get(PropertyCacheFile.CONFIG_HASH_KEY));
    }

    @Test
    public void testCloseAndFlushOutputStreamAfterCreatingHashCode() throws IOException {
        mockStatic(Closeables.class);
        doNothing().when(Closeables.class);
        Closeables.close(any(ObjectOutputStream.class), ArgumentMatchers.eq(false));
        mockStatic(Flushables.class);
        doNothing().when(Flushables.class);
        Flushables.flush(any(ObjectOutputStream.class), ArgumentMatchers.eq(false));

        final Configuration config = new DefaultConfiguration("myName");
        final PropertyCacheFile cache = new PropertyCacheFile(config, "fileDoesNotExist.txt");
        cache.load();

        verifyStatic(Closeables.class, times(1));
        Closeables.close(any(ObjectOutputStream.class), ArgumentMatchers.eq(false));

        verifyStatic(Flushables.class, times(1));
        Flushables.flush(any(ObjectOutputStream.class), ArgumentMatchers.eq(false));
    }

    @Test
    public void testPopulateDetails() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final PropertyCacheFile cache = new PropertyCacheFile(config,
                getPath("InputPropertyCacheFile"));
        cache.load();

        final String hash = cache.get(PropertyCacheFile.CONFIG_HASH_KEY);
        assertNotNull("Config hash key should not be null", hash);
        assertNull("Should return null if key is not in cache", cache.get("key"));

        cache.load();

        assertEquals("Invalid config hash key", hash, cache.get(PropertyCacheFile.CONFIG_HASH_KEY));
        assertEquals("Invalid cache value", "value", cache.get("key"));
        assertNotNull("Config hash key should not be null",
                cache.get(PropertyCacheFile.CONFIG_HASH_KEY));
    }

    @Test
    public void testConfigHashOnReset() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final String filePath = temporaryFolder.newFile().getPath();
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath);

        cache.load();

        final String hash = cache.get(PropertyCacheFile.CONFIG_HASH_KEY);
        assertNotNull("Config hash key should not be null", hash);

        cache.reset();

        assertEquals("Invalid config hash key",
                hash, cache.get(PropertyCacheFile.CONFIG_HASH_KEY));
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
        assertNotNull("Config hash key should not be null", hash);

        // apply new external resource to clear cache
        final Set<String> resources = new HashSet<>();
        resources.add("dummy");
        cache.putExternalResources(resources);

        assertEquals("Invalid config hash key",
                hash, cache.get(PropertyCacheFile.CONFIG_HASH_KEY));
        assertFalse("Should return false in file is not in cache",
                cache.isInCache("myFile", 1));
    }

    @Test
    public void testExternalResourceIsSavedInCache() throws Exception {
        final Configuration config = new DefaultConfiguration("myName");
        final String filePath = temporaryFolder.newFile().getPath();
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath);

        cache.load();

        final Set<String> resources = new HashSet<>();
        final String pathToResource = getPath("InputPropertyCacheFileExternal.properties");
        resources.add(pathToResource);
        cache.putExternalResources(resources);

        final MessageDigest digest = MessageDigest.getInstance("SHA-1");
        final URI uri = CommonUtil.getUriByFilename(pathToResource);
        final byte[] input =
                ByteStreams.toByteArray(new BufferedInputStream(uri.toURL().openStream()));
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(input);
        }
        digest.update(out.toByteArray());
        final String expected = BaseEncoding.base16().upperCase().encode(digest.digest());

        assertEquals("Hashes are not equal", expected,
                cache.get("module-resource*?:" + pathToResource));
    }

    /**
     * This SuppressWarning("unchecked") required to suppress
     * "Unchecked generics array creation for varargs parameter" during mock.
     * @throws IOException when smth wrong with file creation or cache.load
     */
    @Test
    public void testNonExistentResource() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final String filePath = temporaryFolder.newFile().getPath();
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath);

        // create cache with one file
        cache.load();
        final String myFile = "myFile";
        cache.put(myFile, 1);

        final String hash = cache.get(PropertyCacheFile.CONFIG_HASH_KEY);
        assertNotNull("Config hash key should not be null", hash);

        mockStatic(ByteStreams.class);

        when(ByteStreams.toByteArray(any(BufferedInputStream.class)))
                .thenThrow(IOException.class);

        // apply new external resource to clear cache
        final Set<String> resources = new HashSet<>();
        final String resource = getPath("InputPropertyCacheFile.header");
        resources.add(resource);
        cache.putExternalResources(resources);

        assertFalse("Should return false in file is not in cache",
                cache.isInCache(myFile, 1));
        assertFalse("Should return false in file is not in cache",
                cache.isInCache(resource, 1));
    }

    @Test
    public void testFlushAndCloseCacheFileOutputStream() throws IOException {
        mockStatic(Closeables.class);
        doNothing().when(Closeables.class);
        Closeables.close(any(OutputStream.class), ArgumentMatchers.eq(false));
        mockStatic(Flushables.class);
        doNothing().when(Flushables.class);
        Flushables.flush(any(OutputStream.class), ArgumentMatchers.eq(false));

        final Configuration config = new DefaultConfiguration("myName");
        final PropertyCacheFile cache = new PropertyCacheFile(config,
            temporaryFolder.newFile().getPath());

        cache.put("CheckedFileName.java", System.currentTimeMillis());
        cache.persist();

        verifyStatic(Closeables.class, times(1));
        Closeables.close(any(OutputStream.class), ArgumentMatchers.eq(false));
        verifyStatic(Flushables.class, times(1));
        Flushables.flush(any(OutputStream.class), ArgumentMatchers.eq(false));
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
        assertTrue("Cache file does not exist", Files.exists(Paths.get(filePath)));
        Files.delete(Paths.get(filePath));
    }

    @Test
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
            assertTrue("Invalid exception cause",
                    ex.getCause().getCause() instanceof NoSuchAlgorithmException);
            assertEquals("Invalid exception message",
                    "Unable to calculate hashcode.", ex.getCause().getMessage());
        }
    }

    @Test
    public void testPutNonExistentExternalResourceSameExceptionBetweenRuns() throws Exception {
        final File cacheFile = temporaryFolder.newFile();

        // We mock getUriByFilename method of CommonUtil to guarantee that it will
        // throw CheckstyleException with the specific content.
        mockStatic(CommonUtil.class);
        final CheckstyleException mockException =
            new CheckstyleException("Cannot get URL for cache file " + cacheFile.getAbsolutePath());
        when(CommonUtil.getUriByFilename(any(String.class)))
            .thenThrow(mockException);

        // We invoke 'putExternalResources' twice to invalidate cache
        // and have two identical exceptions which the equal content
        final int numberOfRuns = 2;
        final String[] configHashes = new String[numberOfRuns];
        final String[] externalResourceHashes = new String[numberOfRuns];
        for (int i = 0; i < numberOfRuns; i++) {
            final Configuration config = new DefaultConfiguration("myConfig");
            final PropertyCacheFile cache = new PropertyCacheFile(config, cacheFile.getPath());
            cache.load();

            configHashes[i] = cache.get(PropertyCacheFile.CONFIG_HASH_KEY);
            assertNotNull("Config hash key should not be null", configHashes[i]);

            final Set<String> nonExistentExternalResources = new HashSet<>();
            final String externalResourceFileName = "non_existent_file.xml";
            nonExistentExternalResources.add(externalResourceFileName);
            cache.putExternalResources(nonExistentExternalResources);

            externalResourceHashes[i] = cache.get(PropertyCacheFile.EXTERNAL_RESOURCE_KEY_PREFIX
                    + externalResourceFileName);
            assertNotNull("External resource hashes should not be null",
                    externalResourceHashes[i]);

            cache.persist();

            final Properties cacheDetails = new Properties();
            cacheDetails.load(Files.newBufferedReader(cacheFile.toPath()));

            final int expectedNumberOfObjectsInCacheFile = 2;
            assertEquals("Unexpected number of objects in cache",
                    expectedNumberOfObjectsInCacheFile, cacheDetails.size());
        }

        assertEquals("Invalid config hash", configHashes[0], configHashes[1]);
        assertEquals("Invalid external resource hashes",
                externalResourceHashes[0], externalResourceHashes[1]);
    }

    /**
     * It is OK to have long test method name here as it describes the test purpose.
     */
    @Test
    public void testPutNonExistentExternalResourceDifferentExceptionsBetweenRuns()
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

            // We mock getUriByFilename method of CommonUtil to guarantee that it will
            // throw CheckstyleException with the specific content.
            mockStatic(CommonUtil.class);
            final CheckstyleException mockException = new CheckstyleException("Exception #" + i);
            when(CommonUtil.getUriByFilename(any(String.class)))
                .thenThrow(mockException);

            cache.load();

            configHashes[i] = cache.get(PropertyCacheFile.CONFIG_HASH_KEY);
            assertNotNull("Config hash key should not be null", configHashes[i]);

            final Set<String> nonExistentExternalResources = new HashSet<>();
            final String externalResourceFileName = "non_existent_file.xml";
            nonExistentExternalResources.add(externalResourceFileName);
            cache.putExternalResources(nonExistentExternalResources);

            externalResourceHashes[i] = cache.get(PropertyCacheFile.EXTERNAL_RESOURCE_KEY_PREFIX
                    + externalResourceFileName);
            assertNotNull("External resource hashes should not be null",
                    externalResourceHashes[i]);

            cache.persist();

            final Properties cacheDetails = new Properties();
            cacheDetails.load(Files.newBufferedReader(cacheFile.toPath()));

            final int expectedNumberOfObjectsInCacheFile = 2;
            assertEquals("Unexpected number of objects in cache",
                    expectedNumberOfObjectsInCacheFile, cacheDetails.size());
        }

        assertEquals("Invalid config hash", configHashes[0], configHashes[1]);
        assertNotEquals("Invalid external resource hashes",
                externalResourceHashes[0], externalResourceHashes[1]);
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
        assertEquals("Invalid config hash", expectedInitialConfigHash, actualInitialConfigHash);

        cache.persist();

        final Properties details = new Properties();
        details.load(Files.newBufferedReader(cacheFile.toPath()));
        assertEquals("Invalid details size", 1, details.size());

        // change in config
        config.addAttribute("newAttr", "newValue");

        final PropertyCacheFile cacheAfterChangeInConfig =
            new PropertyCacheFile(config, cacheFile.getPath());
        cacheAfterChangeInConfig.load();

        final String expectedConfigHashAfterChange = "4CF5EC78955B81D76153ACC2CA6D60CB77FDCB2A";
        final String actualConfigHashAfterChange =
            cacheAfterChangeInConfig.get(PropertyCacheFile.CONFIG_HASH_KEY);
        assertEquals("Invalid config hash",
                expectedConfigHashAfterChange, actualConfigHashAfterChange);

        cacheAfterChangeInConfig.persist();

        final Properties detailsAfterChangeInConfig = new Properties();
        detailsAfterChangeInConfig.load(Files.newBufferedReader(cacheFile.toPath()));
        assertEquals("Invalid cache size", 1, detailsAfterChangeInConfig.size());
    }

}
