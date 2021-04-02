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
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

/**
 * Represents a message that can be localised. The translations come from
 * message.properties files. The underlying implementation uses
 * java.text.MessageFormat.
 *
 * @noinspection SerializableHasSerializationMethods, ClassWithTooManyConstructors
 */
public final class LocalizedMessage
    implements Serializable {

    private static final long serialVersionUID = 5675176836184862150L;

    /**
     * A cache that maps bundle names to ResourceBundles.
     * Avoids repetitive calls to ResourceBundle.getBundle().
     */
    private static final Map<String, ResourceBundle> BUNDLE_CACHE =
        Collections.synchronizedMap(new HashMap<>());

    /** The locale to localise messages to. **/
    private static Locale sLocale = Locale.getDefault();

    /** Key for the message format. **/
    private final String key;

    /**
     * Arguments for MessageFormat.
     *
     * @noinspection NonSerializableFieldInSerializableClass
     */
    private final Object[] args;

    /** Name of the resource bundle to get messages from. **/
    private final String bundle;

    /** Class of the source for this LocalizedMessage. */
    private final Class<?> sourceClass;

    /** A custom message overriding the default message from the bundle. */
    private final String customMessage;

    /**
     * Creates a new {@code LocalizedMessage} instance.
     *
     * @param bundle resource bundle name
     * @param key the key to locate the translation
     * @param args arguments for the translation
     * @param sourceClass the Class that is the source of the message
     * @param customMessage optional custom message overriding the default
     * @noinspection ConstructorWithTooManyParameters
     */
    // -@cs[ParameterNumber] Class is immutable, we need that amount of arguments.
    public LocalizedMessage(String bundle,
                            String key,
                            Object[] args,
                            Class<?> sourceClass,
                            String customMessage) {
        this.key = key;

        if (args == null) {
            this.args = null;
        }
        else {
            this.args = Arrays.copyOf(args, args.length);
        }
        this.bundle = bundle;
        this.sourceClass = sourceClass;
        this.customMessage = customMessage;
    }

    /**
     * Returns the message key to locate the translation, can also be used
     * in IDE plugins to map audit event messages to corrective actions.
     *
     * @return the message key
     */
    public String getKey() {
        return key;
    }

    /**
     * Gets the name of the source for this LocalizedMessage.
     *
     * @return the name of the source for this LocalizedMessage
     */
    public String getSourceName() {
        return sourceClass.getName();
    }

    /**
     * Sets a locale to use for localization.
     *
     * @param locale the locale to use for localization
     */
    public static void setLocale(Locale locale) {
        clearCache();
        if (Locale.ENGLISH.getLanguage().equals(locale.getLanguage())) {
            sLocale = Locale.ROOT;
        }
        else {
            sLocale = locale;
        }
    }

    /** Clears the cache. */
    public static void clearCache() {
        BUNDLE_CACHE.clear();
    }

    /**
     * Gets the translated message.
     *
     * @return the translated message
     */
    public String getMessage() {
        String message = getCustomMessage();

        if (message == null) {
            // Important to use the default class loader, and not the one in
            // the GlobalProperties object. This is because the class loader in
            // the GlobalProperties is specified by the user for resolving
            // custom classes.
            final ResourceBundle resourceBundle = getBundle(bundle);
            final String pattern = resourceBundle.getString(key);
            final MessageFormat formatter = new MessageFormat(pattern, Locale.ROOT);
            message = formatter.format(args);
        }

        return message;
    }

    /**
     * Returns the formatted custom message if one is configured.
     *
     * @return the formatted custom message or {@code null}
     *          if there is no custom message
     */
    private String getCustomMessage() {
        String message = null;
        if (customMessage != null) {
            final MessageFormat formatter = new MessageFormat(customMessage, Locale.ROOT);
            message = formatter.format(args);
        }
        return message;
    }

    /**
     * Find a ResourceBundle for a given bundle name. Uses the classloader
     * of the class emitting this message, to be sure to get the correct
     * bundle.
     *
     * @param bundleName the bundle name
     * @return a ResourceBundle
     */
    private ResourceBundle getBundle(String bundleName) {
        return BUNDLE_CACHE.computeIfAbsent(bundleName, name -> {
            return ResourceBundle.getBundle(
                name, sLocale, sourceClass.getClassLoader(), new Utf8Control());
        });
    }

    /**
     * <p>
     * Custom ResourceBundle.Control implementation which allows explicitly read
     * the properties files as UTF-8.
     * </p>
     */
    public static class Utf8Control extends Control {

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
                            StandardCharsets.UTF_8.name())) {
                        // Only this line is changed to make it read property files as UTF-8.
                        resourceBundle = new PropertyResourceBundle(streamReader);
                    }
                }
            }
            return resourceBundle;
        }

    }

}
