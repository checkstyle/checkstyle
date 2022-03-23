////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.api;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

/**
 * Provides and caches instances of {@link ResourceBundle}s.
 */
public final class BundleCache {

    /**
     * Custom {@link ResourceBundle.Control} implementation which allows explicitly read
     * the properties files as UTF-8.
     */
    public static final Control UTF8_CONTROL = new Utf8Control();

    /**
     * A cache that maps bundle names to {@link ResourceBundle}s.
     * Avoids repetitive calls to {@link ResourceBundle#getBundle()}.
     */
    private static final Map<String, ResourceBundle> BUNDLE_CACHE =
        Collections.synchronizedMap(new HashMap<>());

    private BundleCache() {
    }

    /**
     * Find a {@link ResourceBundle} for a given bundle name. Uses the classloader
     * of the class emitting this violation, to be sure to get the correct
     * bundle.
     *
     * @param bundleName the bundle name
     * @param locale the locale to the message load
     * @param loader the classloader emitting this violation
     * @return a {@link ResourceBundle}
     */
    public static ResourceBundle getBundle(String bundleName, Locale locale, ClassLoader loader) {
        return BUNDLE_CACHE.computeIfAbsent(bundleName,
                                            name -> ResourceBundle.getBundle(name, locale, loader, UTF8_CONTROL));
    }

    /**
     * Clears the cache.
     */
    public static void clear() {
        BUNDLE_CACHE.clear();
    }

    /**
     * Custom {@link ResourceBundle.Control} implementation which allows explicitly read
     * the properties files as UTF-8.
     */
    private static final class Utf8Control extends Control {

        private Utf8Control() {
        }

        @Override
        public ResourceBundle newBundle(String baseName, Locale locale, String format,
                                        ClassLoader loader, boolean reload) throws IOException {
            // The below is a copy of the default implementation.
            final String bundleName = toBundleName(baseName, locale);
            final String resourceName = toResourceName(bundleName, "properties");
            final URL url = loader.getResource(resourceName);
            ResourceBundle resourceBundle = null;
            if (url != null) {
                final URLConnection connection = url.openConnection();
                if (connection != null) {
                    connection.setUseCaches(!reload);
                    try (Reader streamReader = new InputStreamReader(connection.getInputStream(),
                                                                     StandardCharsets.UTF_8)) {
                        // Only this line is changed to make it read property files as UTF-8.
                        resourceBundle = new PropertyResourceBundle(streamReader);
                    }
                }
            }
            return resourceBundle;
        }

    }

}