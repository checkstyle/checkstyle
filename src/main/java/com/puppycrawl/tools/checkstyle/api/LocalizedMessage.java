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
 * @noinspection SerializableHasSerializationMethods
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

    /** The default severity level if one is not specified. */
    private static final SeverityLevel DEFAULT_SEVERITY = SeverityLevel.ERROR;

    /** The locale to localise messages to. **/
    private static Locale sLocale = Locale.getDefault();

    /** The severity level. **/
    private final SeverityLevel severityLevel;

    /** The id of the module generating the message. */
    private final String moduleId;

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

    /**
     * Creates a new {@code LocalizedMessage} instance.
     *
     * @param bundle resource bundle name
     * @param key the key to locate the translation
     * @param args arguments for the translation
     * @param severityLevel severity level for the message
     * @param moduleId the id of the module the message is associated with
     * @param sourceClass the Class that is the source of the message
     * @noinspection ConstructorWithTooManyParameters
     */
    // -@cs[ParameterNumber] Class is immutable, we need that amount of arguments.
    public LocalizedMessage(String bundle,
                            String key,
                            Object[] args,
                            SeverityLevel severityLevel,
                            String moduleId,
                            Class<?> sourceClass) {
        this.bundle = bundle;
        this.key = key;
        if (args == null) {
            this.args = null;
        }
        else {
            this.args = Arrays.copyOf(args, args.length);
        }
        this.severityLevel = severityLevel;
        this.moduleId = moduleId;
        this.sourceClass = sourceClass;
    }

    /**
     * Creates a new {@code LocalizedMessage} instance. The severity level
     * defaults to DEFAULT_SEVERITY.
     *
     * @param bundle name of a resource bundle that contains audit event messages
     * @param key the key to locate the translation
     * @param args arguments for the translation
     * @param moduleId the id of the module the message is associated with
     * @param sourceClass the name of the source for the message
     */
    public LocalizedMessage(String bundle,
                            String key,
                            Object[] args,
                            String moduleId,
                            Class<?> sourceClass) {
        this(bundle, key, args, DEFAULT_SEVERITY, moduleId,
                sourceClass);
    }

    /**
     * Gets the severity level.
     *
     * @return the severity level
     */
    public SeverityLevel getSeverityLevel() {
        return severityLevel;
    }

    /**
     * Returns id of module.
     *
     * @return the module identifier.
     */
    public String getModuleId() {
        return moduleId;
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
        // Important to use the default class loader, and not the one in
        // the GlobalProperties object. This is because the class loader in
        // the GlobalProperties is specified by the user for resolving
        // custom classes.
        final ResourceBundle resourceBundle = getBundle(bundle);
        final String pattern = resourceBundle.getString(key);
        final MessageFormat formatter = new MessageFormat(pattern, Locale.ROOT);

        return formatter.format(args);
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
