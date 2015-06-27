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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.security.MessageDigest;


import com.google.common.io.Closeables;
import com.google.common.io.Flushables;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
    /** Logger for PropertyCacheFile */
    private static final Log LOG = LogFactory.getLog(PropertyCacheFile.class);

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

    /** name of file to store details **/
    private final String detailsFile;
    /** the details on files **/
    private final Properties details = new Properties();

    /**
     * Creates a new <code>PropertyCacheFile</code> instance.
     *
     * @param currentConfig the current configuration, not null
     * @param fileName the cache file
     */
    PropertyCacheFile(Configuration currentConfig, String fileName) {
        boolean setInActive = true;
        if (fileName != null) {
            FileInputStream inStream = null;
            // get the current config so if the file isn't found
            // the first time the hash will be added to output file
            final String currentConfigHash = getConfigHashCode(currentConfig);
            try {
                inStream = new FileInputStream(fileName);
                details.load(inStream);
                final String cachedConfigHash =
                    details.getProperty(CONFIG_HASH_KEY);
                setInActive = false;
                if (cachedConfigHash == null
                    || !cachedConfigHash.equals(currentConfigHash)) {
                    // Detected configuration change - clear cache
                    details.clear();
                    details.put(CONFIG_HASH_KEY, currentConfigHash);
                }
            }
            catch (final FileNotFoundException e) {
                // Ignore, the cache does not exist
                setInActive = false;
                // put the hash in the file if the file is going to be created
                details.put(CONFIG_HASH_KEY, currentConfigHash);
            }
            catch (final IOException e) {
                LOG.debug("Unable to open cache file, ignoring.", e);
            }
            finally {
                Closeables.closeQuietly(inStream);
            }
        }
        detailsFile = setInActive ? null : fileName;
    }

    /** Cleans up the object and updates the cache file. **/
    void destroy() {
        if (detailsFile != null) {
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(detailsFile);
                details.store(out, null);
            }
            catch (final IOException e) {
                LOG.debug("Unable to save cache file.", e);
            }
            finally {
                if (out != null) {
                    this.flushAndCloseOutStream(out);
                }
            }
        }
    }

    /**
     * Flushes and closes output stream.
     * @param stream the output stream
     */
    private void flushAndCloseOutStream(OutputStream stream) {
        try {
            Flushables.flush(stream, false);
            Closeables.close(stream, false);
        }
        catch (final IOException ex) {
            LOG.debug("Unable to flush and close output stream.", ex);
        }
    }

    /**
     * @param fileName the file to check
     * @param timestamp the timestamp of the file to check
     * @return whether the specified file has already been checked ok
     */
    boolean alreadyChecked(String fileName, long timestamp) {
        final String lastChecked = details.getProperty(fileName);
        return lastChecked != null
            && lastChecked.equals(Long.toString(timestamp));
    }

    /**
     * Records that a file checked ok.
     * @param fileName name of the file that checked ok
     * @param timestamp the timestamp of the file
     */
    void checkedOk(String fileName, long timestamp) {
        details.put(fileName, Long.toString(timestamp));
    }

    /**
     * Calculates the hashcode for a GlobalProperties.
     *
     * @param configuration the GlobalProperties
     * @return the hashcode for <code>configuration</code>
     */
    private String getConfigHashCode(Serializable configuration) {
        try {
            // im-memory serialization of Configuration

            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(baos);
                oos.writeObject(configuration);
            }
            finally {
                this.flushAndCloseOutStream(oos);
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
     * @return hex encoding of <code>byteArray</code>
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
