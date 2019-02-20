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

package com.puppycrawl.tools.checkstyle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteStreams;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

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
    public void testCacheRemainsWhenExternalResourceTheSame() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final String externalResourcePath = temporaryFolder.newFile().getPath();
        final String filePath = temporaryFolder.newFile().getPath();
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath);

        // pre-populate with cache with resources

        cache.load();

        final Set<String> resources = new HashSet<>();
        resources.add(externalResourcePath);
        cache.putExternalResources(resources);

        cache.persist();

        // test cache with same resources and new file

        cache.load();
        cache.put("myFile", 1);
        cache.putExternalResources(resources);

        assertTrue("Should return true in file is in cache",
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
