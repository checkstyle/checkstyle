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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import com.google.common.io.Closeables;
import com.google.common.io.Flushables;
import com.puppycrawl.tools.checkstyle.api.Configuration;

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
 */
final class PropertyCacheFile {

    /**
     * The property key to use for storing the hashcode of the
     * configuration. To avoid nameclashes with the files that are
     * checked the key is chosen in such a way that it cannot be a
     * valid file name.
     */
    private static final String CONFIG_HASH_KEY = "configuration*?";

    /** hex digits */
    private static final char[] HEX_CHARS = {
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
    };

    /** mask for last byte */
    private static final int MASK_0X0F = 0x0F;

    /** bit shift */
    private static final int SHIFT_4 = 4;

    /** the details on files **/
    private final Properties details = new Properties();

    /** configuration object **/
    private final Configuration config;

    /** file name of cache **/
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
     * load cached values from file
     * @throws IOException when there is a problems with file read
     */
    void load() throws IOException {
        // get the current config so if the file isn't found
        // the first time the hash will be added to output file
        final String currentConfigHash = getConfigHashCode(config);
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
    void persist() throws IOException {
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
     * @param uncheckedFileName the file to check
     * @param timestamp the timestamp of the file to check
     * @return whether the specified file has already been checked ok
     */
    boolean inCache(String uncheckedFileName, long timestamp) {
        final String lastChecked = details.getProperty(uncheckedFileName);
        return lastChecked != null
            && lastChecked.equals(Long.toString(timestamp));
    }

    /**
     * Records that a file checked ok.
     * @param checkedFileName name of the file that checked ok
     * @param timestamp the timestamp of the file
     */
    void put(String checkedFileName, long timestamp) {
        details.setProperty(checkedFileName, Long.toString(timestamp));
    }

    /**
     * Calculates the hashcode for a GlobalProperties.
     *
     * @param object the GlobalProperties
     * @return the hashcode for {@code object}
     */
    private static String getConfigHashCode(Serializable object) {
        try {
            // im-memory serialization of Configuration

            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
            }
            finally {
                flushAndCloseOutStream(oos);
            }

            // Instead of hexEncoding baos.toByteArray() directly we
            // use a message digest here to keep the length of the
            // hashcode reasonable

            final MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(baos.toByteArray());

            return hexEncode(md.digest());
        }
        catch (final IOException | NoSuchAlgorithmException ex) {
            // rethrow as unchecked exception
            throw new IllegalStateException("Unable to calculate hashcode.", ex);
        }
    }

    /**
     * Hex-encodes a byte array.
     * @param byteArray the byte array
     * @return hex encoding of {@code byteArray}
     */
    private static String hexEncode(byte... byteArray) {
        final StringBuilder buf = new StringBuilder(2 * byteArray.length);
        for (final byte b : byteArray) {
            final int low = b & MASK_0X0F;
            final int high = b >> SHIFT_4 & MASK_0X0F;
            buf.append(HEX_CHARS[high]);
            buf.append(HEX_CHARS[low]);
        }
        return buf.toString();
    }
}
