////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
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
    private static final String CONFIG_HASH_KEY = "configuration*?";

    /** Size of buffer which is used to read external configuration resources. */
    private static final int BUFFER_SIZE = 1024;

    /** The details on files. **/
    private final Properties details = new Properties();

    /** Configuration object. **/
    private final Configuration config;

    /** File name of cache. **/
    private final String fileName;

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
        final String currentConfigHash = getHashCodeBasedOnObjectContent(config);
        if (new File(fileName).exists()) {
            FileInputStream inStream = null;
            try {
                inStream = new FileInputStream(fileName);
                details.load(inStream);
                final String cachedConfigHash = details.getProperty(CONFIG_HASH_KEY);
                if (!currentConfigHash.equals(cachedConfigHash)) {
                    // Detected configuration change - clear cache
                    details.clear();
                    details.setProperty(CONFIG_HASH_KEY, currentConfigHash);
                }
            }
            finally {
                Closeables.closeQuietly(inStream);
            }
        }
        else {
            // put the hash in the file if the file is going to be created
            details.setProperty(CONFIG_HASH_KEY, currentConfigHash);
        }
    }

    /**
     * Cleans up the object and updates the cache file.
     * @throws IOException  when there is a problems with file save
     */
    public void persist() throws IOException {
        try {
            final Path directory = Paths.get(fileName).getParent();
            if (directory != null) {
                Files.createDirectories(directory);
            }
        }
        catch (InvalidPathException | AccessDeniedException ex) {
            throw new IllegalStateException(ex.getMessage(), ex);
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
     * Clears the cache.
     */
    public void clear() {
        details.clear();
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
        return lastChecked != null
            && lastChecked.equals(Long.toString(timestamp));
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
     * Calculates the hashcode for the serializable object based on its content.
     * @param object serializable object.
     * @return the hashcode for serializable object.
     */
    private static String getHashCodeBasedOnObjectContent(Serializable object) {
        try {
            // im-memory serialization of Configuration

            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(outputStream);
                oos.writeObject(object);
            }
            finally {
                flushAndCloseOutStream(oos);
            }

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
     * Puts external resources in cache.
     * If at least one external resource changed, clears the cache.
     * @param locations locations of external resources.
     */
    public void putExternalResources(Set<String> locations) {
        final Set<ExternalResource> resources = loadExternalResources(locations);
        if (areExternalResourcesChanged(resources)) {
            details.clear();
        }
        fillCacheWithExternalResources(resources);
    }

    /**
     * Loads a set of {@link ExternalResource} based on their locations.
     * @param resourceLocations locations of external configuration resources.
     * @return a set of {@link ExternalResource}.
     */
    private Set<ExternalResource> loadExternalResources(Set<String> resourceLocations) {
        final Set<ExternalResource> resources = Sets.newHashSet();
        for (String location : resourceLocations) {
            String contentHashSum = null;
            try {
                final byte[] content = loadExternalResource(location);
                contentHashSum = getHashCodeBasedOnObjectContent(content);
            }
            catch (CheckstyleException ex) {
                // if exception happened (configuration resource was not found, connection is not
                // available, resouce is broken, etc), we need to calculate hash sum based on
                // exception object content in order to check whether problem is resolved later
                // and/or the configuration is changed.
                contentHashSum = getHashCodeBasedOnObjectContent(ex);
            }
            finally {
                resources.add(new ExternalResource(location, contentHashSum));
            }
        }
        return resources;
    }

    /**
     * Loads the content of external resource.
     * @param location external resource location.
     * @return array of bytes which respresents the content of external resource in binary form.
     * @throws CheckstyleException if error while loading occurs.
     */
    private byte[] loadExternalResource(String location) throws CheckstyleException {
        byte[] content = null;
        final URI uri = CommonUtils.getUriByFilename(location);
        InputStream resourceReader = null;
        try {
            resourceReader = new BufferedInputStream(uri.toURL().openStream());
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final byte[] data = new byte[BUFFER_SIZE];
            int bytesRead = resourceReader.read(data, 0, data.length);
            while (bytesRead != -1) {
                outputStream.write(data, 0, bytesRead);
                bytesRead = resourceReader.read(data, 0, data.length);
            }
            outputStream.flush();
            content = outputStream.toByteArray();
        }
        catch (IOException ex) {
            throw new CheckstyleException("Unable to load external resource file " + location, ex);
        }
        finally {
            Closeables.closeQuietly(resourceReader);
        }
        return content;
    }

    /**
     * Checks whether the contents of external configuration resources were changed.
     * @param resources a set of {@link ExternalResource}.
     * @return true if the contents of external configuration resources were changed.
     */
    private boolean areExternalResourcesChanged(Set<ExternalResource> resources) {
        return Iterables.tryFind(resources, new Predicate<ExternalResource>() {
            @Override
            public boolean apply(ExternalResource resource) {
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
            }
        }).isPresent();
    }

    /**
     * Fills cache with a set of {@link ExternalResource}.
     * If external resource from the set is already in cache, it will be skipped.
     * @param externalResources a set of {@link ExternalResource}.
     */
    private void fillCacheWithExternalResources(Set<ExternalResource> externalResources) {
        for (ExternalResource resource : externalResources) {
            if (!isResourceLocationInCache(resource.location)) {
                details.setProperty(resource.location, resource.contentHashSum);
            }
        }
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
