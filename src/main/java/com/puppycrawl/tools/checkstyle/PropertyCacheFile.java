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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import com.google.common.io.ByteStreams;
import com.google.common.io.Closeables;
import com.google.common.io.Flushables;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * This class maintains a persistent(on file-system) store of the files
 * that have checked ok(no validation events) and their associated
 * timestamp. It is used to optimize Checkstyle between few launches.
 * It is mostly useful for plugin and extensions of Checkstyle.
 * It uses a property file
 * for storage.  A hashcode of the Configuration is stored in the
 * cache file to ensure the cache is invalidated when the
 * configuration has changed.
 *
 * @author Oliver Burn
 * @author Andrei Selkin
 */
final class PropertyCacheFile {

    /**
     * The property key to use for storing the hashcode of the
     * configuration. To avoid name clashes with the files that are
     * checked the key is chosen in such a way that it cannot be a
     * valid file name.
     */
    public static final String CONFIG_HASH_KEY = "configuration*?";

    /**
     * The property prefix to use for storing the hashcode of an
     * external resource. To avoid name clashes with the files that are
     * checked the prefix is chosen in such a way that it cannot be a
     * valid file name and makes it clear it is a resource.
     */
    public static final String EXTERNAL_RESOURCE_KEY_PREFIX = "module-resource*?:";

    /** The details on files. **/
    private final Properties details = new Properties();

    /** Configuration object. **/
    private final Configuration config;

    /** File name of cache. **/
    private final String fileName;

    /** Generated configuration hash. **/
    private String configHash;

    /**
     * Creates a new {@code PropertyCacheFile} instance.
     *
     * @param config the current configuration, not null
     * @param fileName the cache file
     */
    PropertyCacheFile(Configuration config, String fileName) {
        if (config == null) {
            throw new IllegalArgumentException("config can not be null");
        }
        if (fileName == null) {
            throw new IllegalArgumentException("fileName can not be null");
        }
        this.config = config;
        this.fileName = fileName;
    }

    /**
     * Load cached values from file.
     * @throws IOException when there is a problems with file read
     */
    public void load() throws IOException {
        // get the current config so if the file isn't found
        // the first time the hash will be added to output file
        configHash = getHashCodeBasedOnObjectContent(config);
        if (new File(fileName).exists()) {
            FileInputStream inStream = null;
            try {
                inStream = new FileInputStream(fileName);
                details.load(inStream);
                final String cachedConfigHash = details.getProperty(CONFIG_HASH_KEY);
                if (!configHash.equals(cachedConfigHash)) {
                    // Detected configuration change - clear cache
                    reset();
                }
            }
            finally {
                Closeables.closeQuietly(inStream);
            }
        }
        else {
            // put the hash in the file if the file is going to be created
            reset();
        }
    }

    /**
     * Cleans up the object and updates the cache file.
     * @throws IOException  when there is a problems with file save
     */
    public void persist() throws IOException {
        final Path directory = Paths.get(fileName).getParent();
        if (directory != null) {
            Files.createDirectories(directory);
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(fileName);
            details.store(out, null);
        }
        finally {
            flushAndCloseOutStream(out);
        }
    }

    /**
     * Resets the cache to be empty except for the configuration hash.
     */
    public void reset() {
        details.clear();
        details.setProperty(CONFIG_HASH_KEY, configHash);
    }

    /**
     * Flushes and closes output stream.
     * @param stream the output stream
     * @throws IOException  when there is a problems with file flush and close
     */
    private static void flushAndCloseOutStream(OutputStream stream) throws IOException {
        if (stream != null) {
            Flushables.flush(stream, false);
        }
        Closeables.close(stream, false);
    }

    /**
     * Checks that file is in cache.
     * @param uncheckedFileName the file to check
     * @param timestamp the timestamp of the file to check
     * @return whether the specified file has already been checked ok
     */
    public boolean isInCache(String uncheckedFileName, long timestamp) {
        final String lastChecked = details.getProperty(uncheckedFileName);
        return Objects.equals(lastChecked, Long.toString(timestamp));
    }

    /**
     * Records that a file checked ok.
     * @param checkedFileName name of the file that checked ok
     * @param timestamp the timestamp of the file
     */
    public void put(String checkedFileName, long timestamp) {
        details.setProperty(checkedFileName, Long.toString(timestamp));
    }

    /**
     * Retrieves the hash of a specific file.
     * @param name The name of the file to retrieve.
     * @return The has of the file or {@code null}.
     */
    public String get(String name) {
        return details.getProperty(name);
    }

    /**
     * Removed a specific file from the cache.
     * @param checkedFileName The name of the file to remove.
     */
    public void remove(String checkedFileName) {
        details.remove(checkedFileName);
    }

    /**
     * Calculates the hashcode for the serializable object based on its content.
     * @param object serializable object.
     * @return the hashcode for serializable object.
     */
    private static String getHashCodeBasedOnObjectContent(Serializable object) {
        try {
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            // in-memory serialization of Configuration
            serialize(object, outputStream);
            // Instead of hexEncoding outputStream.toByteArray() directly we
            // use a message digest here to keep the length of the
            // hashcode reasonable

            final MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(outputStream.toByteArray());

            return DatatypeConverter.printHexBinary(digest.digest());
        }
        catch (final IOException | NoSuchAlgorithmException ex) {
            // rethrow as unchecked exception
            throw new IllegalStateException("Unable to calculate hashcode.", ex);
        }
    }

    /**
     * Serializes object to output stream.
     * @param object object to be erialized
     * @param outputStream serialization stream
     * @throws IOException if an error occurs
     */
    private static void serialize(Serializable object,
                                  OutputStream outputStream) throws IOException {
        final ObjectOutputStream oos = new ObjectOutputStream(outputStream);
        try {
            oos.writeObject(object);
        }
        finally {
            flushAndCloseOutStream(oos);
        }
    }

    /**
     * Puts external resources in cache.
     * If at least one external resource changed, clears the cache.
     * @param locations locations of external resources.
     */
    public void putExternalResources(Set<String> locations) {
        final Set<ExternalResource> resources = loadExternalResources(locations);
        if (areExternalResourcesChanged(resources)) {
            reset();
        }
        fillCacheWithExternalResources(resources);
    }

    /**
     * Loads a set of {@link ExternalResource} based on their locations.
     * @param resourceLocations locations of external configuration resources.
     * @return a set of {@link ExternalResource}.
     */
    private static Set<ExternalResource> loadExternalResources(Set<String> resourceLocations) {
        final Set<ExternalResource> resources = new HashSet<>();
        for (String location : resourceLocations) {
            String contentHashSum = null;
            try {
                final byte[] content = loadExternalResource(location);
                contentHashSum = getHashCodeBasedOnObjectContent(content);
            }
            catch (CheckstyleException ex) {
                // if exception happened (configuration resource was not found, connection is not
                // available, resource is broken, etc), we need to calculate hash sum based on
                // exception object content in order to check whether problem is resolved later
                // and/or the configuration is changed.
                contentHashSum = getHashCodeBasedOnObjectContent(ex);
            }
            finally {
                resources.add(new ExternalResource(EXTERNAL_RESOURCE_KEY_PREFIX + location,
                        contentHashSum));
            }
        }
        return resources;
    }

    /**
     * Loads the content of external resource.
     * @param location external resource location.
     * @return array of bytes which represents the content of external resource in binary form.
     * @throws CheckstyleException if error while loading occurs.
     */
    private static byte[] loadExternalResource(String location) throws CheckstyleException {
        final byte[] content;
        final URI uri = CommonUtils.getUriByFilename(location);

        try {
            content = ByteStreams.toByteArray(new BufferedInputStream(uri.toURL().openStream()));
        }
        catch (IOException ex) {
            throw new CheckstyleException("Unable to load external resource file " + location, ex);
        }

        return content;
    }

    /**
     * Checks whether the contents of external configuration resources were changed.
     * @param resources a set of {@link ExternalResource}.
     * @return true if the contents of external configuration resources were changed.
     */
    private boolean areExternalResourcesChanged(Set<ExternalResource> resources) {
        return resources.stream().filter(resource -> {
            boolean changed = false;
            if (isResourceLocationInCache(resource.location)) {
                final String contentHashSum = resource.contentHashSum;
                final String cachedHashSum = details.getProperty(resource.location);
                if (!cachedHashSum.equals(contentHashSum)) {
                    changed = true;
                }
            }
            else {
                changed = true;
            }
            return changed;
        }).findFirst().isPresent();
    }

    /**
     * Fills cache with a set of {@link ExternalResource}.
     * If external resource from the set is already in cache, it will be skipped.
     * @param externalResources a set of {@link ExternalResource}.
     */
    private void fillCacheWithExternalResources(Set<ExternalResource> externalResources) {
        externalResources.stream()
            .filter(resource -> !isResourceLocationInCache(resource.location))
            .forEach(resource -> details.setProperty(resource.location, resource.contentHashSum));
    }

    /**
     * Checks whether resource location is in cache.
     * @param location resource location.
     * @return true if resource location is in cache.
     */
    private boolean isResourceLocationInCache(String location) {
        final String cachedHashSum = details.getProperty(location);
        return cachedHashSum != null;
    }

    /**
     * Class which represents external resource.
     * @author Andrei Selkin
     */
    private static class ExternalResource {
        /** Location of resource. */
        private final String location;
        /** Hash sum which is calculated based on resource content. */
        private final String contentHashSum;

        /**
         * Creates an instance.
         * @param location resource location.
         * @param contentHashSum content hash sum.
         */
        ExternalResource(String location, String contentHashSum) {
            this.location = location;
            this.contentHashSum = contentHashSum;
        }
    }
}
