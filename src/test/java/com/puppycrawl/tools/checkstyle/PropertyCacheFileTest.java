///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteStreams;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class PropertyCacheFileTest extends AbstractPathTestSupport {

    @TempDir
    public File temporaryFolder;

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/propertycachefile";
    }

    @Test
    public void testCtor() {
        try {
            final Object test = new PropertyCacheFile(null, "");
            assertWithMessage("exception expected but got " + test).fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("config can not be null");
        }
        final Configuration config = new DefaultConfiguration("myName");
        try {
            final Object test = new PropertyCacheFile(config, null);
            assertWithMessage("exception expected but got " + test).fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("fileName can not be null");
        }
    }

    @Test
    public void testInCache() {
        final Configuration config = new DefaultConfiguration("myName");
        final String uniqueFileName = "junit_" + UUID.randomUUID() + ".java";
        final File filePath = new File(temporaryFolder, uniqueFileName);
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath.toString());
        cache.put("myFile", 1);
        assertWithMessage("Should return true when file is in cache")
                .that(cache.isInCache("myFile", 1))
                .isTrue();
        assertWithMessage("Should return false when file is not in cache")
                .that(cache.isInCache("myFile", 2))
                .isFalse();
        assertWithMessage("Should return false when file is not in cache")
                .that(cache.isInCache("myFile1", 1))
                .isFalse();
    }

    @Test
    public void testResetIfFileDoesNotExist() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final PropertyCacheFile cache = new PropertyCacheFile(config, "fileDoesNotExist.txt");

        cache.load();

        assertWithMessage("Config hash key should not be null")
            .that(cache.get(PropertyCacheFile.CONFIG_HASH_KEY))
            .isNotNull();
    }

    @Test
    public void testPopulateDetails() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final PropertyCacheFile cache = new PropertyCacheFile(config,
                getPath("InputPropertyCacheFile"));
        cache.load();

        final String hash = cache.get(PropertyCacheFile.CONFIG_HASH_KEY);
        assertWithMessage("Config hash key should not be null")
            .that(hash)
            .isNotNull();
        assertWithMessage("Should return null if key is not in cache")
            .that(cache.get("key"))
            .isNull();

        cache.load();

        assertWithMessage("Invalid config hash key")
            .that(cache.get(PropertyCacheFile.CONFIG_HASH_KEY))
            .isEqualTo(hash);
        assertWithMessage("Invalid cache value")
            .that(cache.get("key"))
            .isEqualTo("value");
        assertWithMessage("Config hash key should not be null")
            .that(cache.get(PropertyCacheFile.CONFIG_HASH_KEY))
            .isNotNull();
    }

    @Test
    public void testConfigHashOnReset() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final String uniqueFileName = "junit_" + UUID.randomUUID() + ".java";
        final File filePath = new File(temporaryFolder, uniqueFileName);
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath.toString());

        cache.load();

        final String hash = cache.get(PropertyCacheFile.CONFIG_HASH_KEY);
        assertWithMessage("Config hash key should not be null")
            .that(hash)
            .isNotNull();

        cache.reset();

        assertWithMessage("Invalid config hash key")
            .that(cache.get(PropertyCacheFile.CONFIG_HASH_KEY))
            .isEqualTo(hash);
    }

    @Test
    public void testConfigHashRemainsOnResetExternalResources() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final String uniqueFileName = "file_" + UUID.randomUUID() + ".java";
        final File filePath = new File(temporaryFolder, uniqueFileName);
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath.toString());

        // create cache with one file
        cache.load();
        cache.put("myFile", 1);

        final String hash = cache.get(PropertyCacheFile.CONFIG_HASH_KEY);
        assertWithMessage("Config hash key should not be null")
            .that(hash)
            .isNotNull();

        // apply new external resource to clear cache
        final Set<String> resources = new HashSet<>();
        resources.add("dummy");
        cache.putExternalResources(resources);

        assertWithMessage("Invalid config hash key")
            .that(cache.get(PropertyCacheFile.CONFIG_HASH_KEY))
            .isEqualTo(hash);
        assertWithMessage("Should return false in file is not in cache")
                .that(cache.isInCache("myFile", 1))
                .isFalse();
    }

    @Test
    public void testCacheRemainsWhenExternalResourceTheSame() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final String externalFile = "junit_" + UUID.randomUUID() + ".java";
        final File externalResourcePath = new File(temporaryFolder, externalFile);
        externalResourcePath.createNewFile();
        final String uniqueFileName = "junit_" + UUID.randomUUID() + ".java";
        final File filePath = new File(temporaryFolder, uniqueFileName);
        filePath.createNewFile();
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath.toString());

        // pre-populate with cache with resources

        cache.load();

        final Set<String> resources = new HashSet<>();
        resources.add(externalResourcePath.toString());
        cache.putExternalResources(resources);

        cache.persist();

        // test cache with same resources and new file

        cache.load();
        cache.put("myFile", 1);
        cache.putExternalResources(resources);

        assertWithMessage("Should return true in file is in cache")
                .that(cache.isInCache("myFile", 1))
                .isTrue();
    }

    @Test
    public void testExternalResourceIsSavedInCache() throws Exception {
        final Configuration config = new DefaultConfiguration("myName");
        final String uniqueFileName = "junit_" + UUID.randomUUID() + ".java";
        final File filePath = new File(temporaryFolder, uniqueFileName);
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath.toString());

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

        assertWithMessage("Hashes are not equal")
            .that(cache.get("module-resource*?:" + pathToResource))
            .isEqualTo(expected);
    }

    @Test
    public void testCacheDirectoryDoesNotExistAndShouldBeCreated() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final String filePath = String.format(Locale.ENGLISH, "%s%2$stemp%2$scache.temp",
            temporaryFolder, File.separator);
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath);

        // no exception expected, cache directory should be created
        cache.persist();

        assertWithMessage("cache exists in directory")
                .that(new File(filePath).exists())
                .isTrue();
    }

    @Test
    public void testPathToCacheContainsOnlyFileName() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final String fileName = "temp.cache";
        final Path filePath = Path.of(fileName);
        final PropertyCacheFile cache = new PropertyCacheFile(config, fileName);

        // no exception expected
        cache.persist();

        assertWithMessage("Cache file does not exist")
                .that(Files.exists(filePath))
                .isTrue();
        Files.delete(filePath);
    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
    public void testPersistWithSymbolicLinkToDirectory() throws IOException {
        final Path tempDirectory = temporaryFolder.toPath();
        final Path symbolicLinkDirectory = temporaryFolder.toPath()
                .resolve("symbolicLink");
        Files.createSymbolicLink(symbolicLinkDirectory, tempDirectory);

        final Configuration config = new DefaultConfiguration("myName");
        final String cacheFilePath = symbolicLinkDirectory.resolve("cache.temp").toString();
        final PropertyCacheFile cache = new PropertyCacheFile(config, cacheFilePath);

        cache.persist();

        final Path expectedFilePath = tempDirectory.resolve("cache.temp");
        assertWithMessage("Cache file should be created in the actual directory")
                .that(Files.exists(expectedFilePath))
                .isTrue();
    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
    public void testSymbolicLinkResolution() throws IOException {
        final Path tempDirectory = temporaryFolder.toPath();
        final Path symbolicLinkDirectory = temporaryFolder.toPath()
                .resolve("symbolicLink");
        Files.createSymbolicLink(symbolicLinkDirectory, tempDirectory);

        final Configuration config = new DefaultConfiguration("myName");
        final String cacheFilePath = symbolicLinkDirectory.resolve("cache.temp").toString();
        final PropertyCacheFile cache = new PropertyCacheFile(config, cacheFilePath);

        cache.persist();

        final Path expectedFilePath = tempDirectory.resolve("cache.temp");
        assertWithMessage(
                "Cache file should be created in the actual directory.")
                .that(Files.exists(expectedFilePath))
                .isTrue();
    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
    public void testSymbolicLinkToNonDirectory() throws IOException {
        final String uniqueFileName = "tempFile_" + UUID.randomUUID() + ".java";
        final File tempFile = new File(temporaryFolder, uniqueFileName);
        tempFile.createNewFile();
        final Path symbolicLinkDirectory = temporaryFolder.toPath();
        final Path symbolicLink = symbolicLinkDirectory.resolve("symbolicLink");
        Files.createSymbolicLink(symbolicLink, tempFile.toPath());

        final Configuration config = new DefaultConfiguration("myName");
        final String cacheFilePath = symbolicLink.resolve("cache.temp").toString();
        final PropertyCacheFile cache = new PropertyCacheFile(config, cacheFilePath);

        final IOException thrown = getExpectedThrowable(IOException.class, cache::persist);

        final String expectedMessage = "Resolved symbolic link " + symbolicLink
                + " is not a directory.";

        assertWithMessage(
                "Expected IOException when symbolicLink is not a directory")
                .that(thrown.getMessage())
                .contains(expectedMessage);
    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
    public void testMultipleSymbolicLinkResolution() throws IOException {
        final Path actualDirectory = temporaryFolder.toPath();
        final Path firstSymbolicLink = temporaryFolder.toPath()
                .resolve("firstLink");
        Files.createSymbolicLink(firstSymbolicLink, actualDirectory);

        final Path secondSymbolicLink = temporaryFolder.toPath()
                .resolve("secondLink");
        Files.createSymbolicLink(secondSymbolicLink, firstSymbolicLink);

        final Configuration config = new DefaultConfiguration("myName");
        final String cacheFilePath = secondSymbolicLink.resolve("cache.temp").toString();
        final PropertyCacheFile cache = new PropertyCacheFile(config, cacheFilePath);

        cache.persist();

        final Path expectedFilePath = actualDirectory.resolve("cache.temp");
        assertWithMessage("Cache file should be created in the final actual directory")
                .that(Files.exists(expectedFilePath))
                .isTrue();
    }

    @Test
    public void testChangeInConfig() throws Exception {
        final DefaultConfiguration config = new DefaultConfiguration("myConfig");
        config.addProperty("attr", "value");
        final String uniqueFileName = "junit_" + UUID.randomUUID() + ".java";
        final File cacheFile = new File(temporaryFolder, uniqueFileName);
        final PropertyCacheFile cache = new PropertyCacheFile(config, cacheFile.getPath());
        cache.load();

        final String expectedInitialConfigHash = "D5BB1747FC11B2BB839C80A6C28E3E7684AF9940";
        final String actualInitialConfigHash = cache.get(PropertyCacheFile.CONFIG_HASH_KEY);
        assertWithMessage("Invalid config hash")
            .that(actualInitialConfigHash)
            .isEqualTo(expectedInitialConfigHash);

        cache.persist();

        final Properties details = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
            details.load(reader);
        }
        assertWithMessage("Invalid details size")
            .that(details)
            .hasSize(1);

        // change in config
        config.addProperty("newAttr", "newValue");

        final PropertyCacheFile cacheAfterChangeInConfig =
            new PropertyCacheFile(config, cacheFile.getPath());
        cacheAfterChangeInConfig.load();

        final String expectedConfigHashAfterChange = "714876AE38C069EC52BF86889F061B3776E526D3";
        final String actualConfigHashAfterChange =
            cacheAfterChangeInConfig.get(PropertyCacheFile.CONFIG_HASH_KEY);
        assertWithMessage("Invalid config hash")
            .that(actualConfigHashAfterChange)
            .isEqualTo(expectedConfigHashAfterChange);

        cacheAfterChangeInConfig.persist();

        final Properties detailsAfterChangeInConfig = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
            detailsAfterChangeInConfig.load(reader);
        }
        assertWithMessage("Invalid cache size")
            .that(detailsAfterChangeInConfig)
            .hasSize(1);
    }

    @Test
    public void testNonExistentResource() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final String uniqueFileName = "junit_" + UUID.randomUUID() + ".java";
        final File filePath = new File(temporaryFolder, uniqueFileName);
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath.toString());

        // create cache with one file
        cache.load();
        final String myFile = "myFile";
        cache.put(myFile, 1);

        final String hash = cache.get(PropertyCacheFile.CONFIG_HASH_KEY);
        assertWithMessage("Config hash key should not be null")
                .that(hash)
                .isNotNull();

        // apply new external resource to clear cache
        final Set<String> resources = new HashSet<>();
        final String resource = getPath("InputPropertyCacheFile.header");
        resources.add(resource);
        cache.putExternalResources(resources);

        assertWithMessage("Should return false in file is not in cache")
                .that(cache.isInCache(myFile, 1))
                .isFalse();

        assertWithMessage("Should return false in file is not in cache")
                .that(cache.isInCache(resource, 1))
                .isFalse();
    }

    @Test
    public void testExceptionNoSuchAlgorithmException() {
        final Configuration config = new DefaultConfiguration("myName");
        final String uniqueFileName = "junit_" + UUID.randomUUID() + ".java";
        final File filePath = new File(temporaryFolder, uniqueFileName);
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath.toString());
        cache.put("myFile", 1);

        try (MockedStatic<MessageDigest> messageDigest = mockStatic(MessageDigest.class)) {
            messageDigest.when(() -> MessageDigest.getInstance("SHA-1"))
                    .thenThrow(NoSuchAlgorithmException.class);

            final ReflectiveOperationException ex =
                getExpectedThrowable(ReflectiveOperationException.class, () -> {
                    TestUtil.invokeStaticMethod(PropertyCacheFile.class,
                            "getHashCodeBasedOnObjectContent", config);
                });
            assertWithMessage("Invalid exception cause")
                .that(ex)
                    .hasCauseThat()
                        .hasCauseThat()
                        .isInstanceOf(NoSuchAlgorithmException.class);
            assertWithMessage("Invalid exception message")
                .that(ex)
                    .hasCauseThat()
                        .hasMessageThat()
                        .isEqualTo("Unable to calculate hashcode.");
        }
    }

    /**
     * This test invokes {@code putExternalResources} twice to invalidate cache.
     * And asserts that two different exceptions produces different content,
     * but two exceptions with same message produces one shared content.
     *
     * @param rawMessages exception messages separated by ';'
     */
    @ParameterizedTest
    @ValueSource(strings = {"Same;Same", "First;Second"})
    public void testPutNonExistentExternalResource(String rawMessages) throws Exception {
        final String uniqueFileName = "junit_" + UUID.randomUUID() + ".java";
        final File cacheFile = new File(temporaryFolder, uniqueFileName);
        final String[] messages = rawMessages.split(";");
        // We mock getUriByFilename method of CommonUtil to guarantee that it will
        // throw CheckstyleException with the specific content.
        try (MockedStatic<CommonUtil> commonUtil = mockStatic(CommonUtil.class)) {
            final int numberOfRuns = messages.length;
            final String[] configHashes = new String[numberOfRuns];
            final String[] externalResourceHashes = new String[numberOfRuns];
            for (int i = 0; i < numberOfRuns; i++) {
                commonUtil.when(() -> CommonUtil.getUriByFilename(any(String.class)))
                        .thenThrow(new CheckstyleException(messages[i]));
                final Configuration config = new DefaultConfiguration("myConfig");
                final PropertyCacheFile cache = new PropertyCacheFile(config, cacheFile.getPath());
                cache.load();

                configHashes[i] = cache.get(PropertyCacheFile.CONFIG_HASH_KEY);
                assertWithMessage("Config hash key should not be null")
                        .that(configHashes[i])
                        .isNotNull();

                final Set<String> nonExistentExternalResources = new HashSet<>();
                final String externalResourceFileName = "non_existent_file.xml";
                nonExistentExternalResources.add(externalResourceFileName);
                cache.putExternalResources(nonExistentExternalResources);

                externalResourceHashes[i] = cache.get(PropertyCacheFile.EXTERNAL_RESOURCE_KEY_PREFIX
                        + externalResourceFileName);
                assertWithMessage("External resource hashes should not be null")
                        .that(externalResourceHashes[i])
                        .isNotNull();

                cache.persist();

                final Properties cacheDetails = new Properties();
                try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
                    cacheDetails.load(reader);
                }

                assertWithMessage("Unexpected number of objects in cache")
                        .that(cacheDetails)
                        .hasSize(2);
            }

            assertWithMessage("Invalid config hash")
                    .that(configHashes[0])
                    .isEqualTo(configHashes[1]);
            final boolean sameException = messages[0].equals(messages[1]);
            assertWithMessage("Invalid external resource hashes")
                    .that(externalResourceHashes[0].equals(externalResourceHashes[1]))
                    .isEqualTo(sameException);
        }
    }

}
