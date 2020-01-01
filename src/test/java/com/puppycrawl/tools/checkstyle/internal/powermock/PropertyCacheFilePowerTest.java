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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.common.io.ByteStreams;
import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.PropertyCacheFile;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PropertyCacheFile.class, ByteStreams.class,
        CommonUtil.class})
public class PropertyCacheFilePowerTest extends AbstractPathTestSupport {

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/propertycachefile";
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
            try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
                cacheDetails.load(reader);
            }

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
            try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
                cacheDetails.load(reader);
            }

            final int expectedNumberOfObjectsInCacheFile = 2;
            assertEquals("Unexpected number of objects in cache",
                    expectedNumberOfObjectsInCacheFile, cacheDetails.size());
        }

        assertEquals("Invalid config hash", configHashes[0], configHashes[1]);
        assertNotEquals("Invalid external resource hashes",
                externalResourceHashes[0], externalResourceHashes[1]);
    }

}
