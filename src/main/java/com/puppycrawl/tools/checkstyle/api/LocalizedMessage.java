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

package com.puppycrawl.tools.checkstyle.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

/**
 * Represents a message that can be localised. The translations come from
 * message.properties files. The underlying implementation uses
 * java.text.MessageFormat.
 *
 * @author Oliver Burn
 * @author lkuehne
 */
public final class LocalizedMessage
    implements Comparable<LocalizedMessage>, Serializable {
    private static final long serialVersionUID = 5675176836184862150L;

    /**
     * A cache that maps bundle names to ResourceBundles.
     * Avoids repetitive calls to ResourceBundle.getBundle().
     */
    private static final Map<String, ResourceBundle> BUNDLE_CACHE =
        Collections.synchronizedMap(new HashMap<String, ResourceBundle>());

    /** The default severity level if one is not specified. */
    private static final SeverityLevel DEFAULT_SEVERITY = SeverityLevel.ERROR;

    /** The locale to localise messages to. **/
    private static Locale sLocale = Locale.getDefault();

    /** The line number. **/
    private final int lineNo;
    /** The column number. **/
    private final int columnNo;

    /** The severity level. **/
    private final SeverityLevel severityLevel;

    /** The id of the module generating the message. */
    private final String moduleId;

    /** Key for the message format. **/
    private final String key;

    /** Arguments for MessageFormat. **/
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
     * @param lineNo line number associated with the message
     * @param columnNo column number associated with the message
     * @param bundle resource bundle name
     * @param key the key to locate the translation
     * @param args arguments for the translation
     * @param severityLevel severity level for the message
     * @param moduleId the id of the module the message is associated with
     * @param sourceClass the Class that is the source of the message
     * @param customMessage optional custom message overriding the default
     */
    public LocalizedMessage(int lineNo,
                            int columnNo,
                            String bundle,
                            String key,
                            Object[] args,
                            SeverityLevel severityLevel,
                            String moduleId,
                            Class<?> sourceClass,
                            String customMessage) {
        this.lineNo = lineNo;
        this.columnNo = columnNo;
        this.key = key;

        if (args == null) {
            this.args = null;
        }
        else {
            this.args = Arrays.copyOf(args, args.length);
        }
        this.bundle = bundle;
        this.severityLevel = severityLevel;
        this.moduleId = moduleId;
        this.sourceClass = sourceClass;
        this.customMessage = customMessage;
    }

    /**
     * Creates a new {@code LocalizedMessage} instance.
     *
     * @param lineNo line number associated with the message
     * @param columnNo column number associated with the message
     * @param bundle resource bundle name
     * @param key the key to locate the translation
     * @param args arguments for the translation
     * @param moduleId the id of the module the message is associated with
     * @param sourceClass the Class that is the source of the message
     * @param customMessage optional custom message overriding the default
     */
    public LocalizedMessage(int lineNo,
                            int columnNo,
                            String bundle,
                            String key,
                            Object[] args,
                            String moduleId,
                            Class<?> sourceClass,
                            String customMessage) {
        this(lineNo,
                columnNo,
             bundle,
             key,
             args,
             DEFAULT_SEVERITY,
             moduleId,
             sourceClass,
             customMessage);
    }

    /**
     * Creates a new {@code LocalizedMessage} instance.
     *
     * @param lineNo line number associated with the message
     * @param bundle resource bundle name
     * @param key the key to locate the translation
     * @param args arguments for the translation
     * @param severityLevel severity level for the message
     * @param moduleId the id of the module the message is associated with
     * @param sourceClass the source class for the message
     * @param customMessage optional custom message overriding the default
     */
    public LocalizedMessage(int lineNo,
                            String bundle,
                            String key,
                            Object[] args,
                            SeverityLevel severityLevel,
                            String moduleId,
                            Class<?> sourceClass,
                            String customMessage) {
        this(lineNo, 0, bundle, key, args, severityLevel, moduleId,
                sourceClass, customMessage);
    }

    /**
     * Creates a new {@code LocalizedMessage} instance. The column number
     * defaults to 0.
     *
     * @param lineNo line number associated with the message
     * @param bundle name of a resource bundle that contains error messages
     * @param key the key to locate the translation
     * @param args arguments for the translation
     * @param moduleId the id of the module the message is associated with
     * @param sourceClass the name of the source for the message
     * @param customMessage optional custom message overriding the default
     */
    public LocalizedMessage(
        int lineNo,
        String bundle,
        String key,
        Object[] args,
        String moduleId,
        Class<?> sourceClass,
        String customMessage) {
        this(lineNo, 0, bundle, key, args, DEFAULT_SEVERITY, moduleId,
                sourceClass, customMessage);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final LocalizedMessage localizedMessage = (LocalizedMessage) object;
        return Objects.equals(lineNo, localizedMessage.lineNo)
                && Objects.equals(columnNo, localizedMessage.columnNo)
                && Objects.equals(severityLevel, localizedMessage.severityLevel)
                && Objects.equals(moduleId, localizedMessage.moduleId)
                && Objects.equals(key, localizedMessage.key)
                && Objects.equals(bundle, localizedMessage.bundle)
                && Objects.equals(sourceClass, localizedMessage.sourceClass)
                && Objects.equals(customMessage, localizedMessage.customMessage)
                && Arrays.equals(args, localizedMessage.args);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineNo, columnNo, severityLevel, moduleId, key, bundle, sourceClass,
                customMessage, Arrays.hashCode(args));
    }

    /** Clears the cache. */
    public static void clearCache() {
        synchronized (BUNDLE_CACHE) {
            BUNDLE_CACHE.clear();
        }
    }

    /**
     * Gets the translated message.
     * @return the translated message
     */
    public String getMessage() {
        String message = getCustomMessage();

        if (message == null) {
            try {
                // Important to use the default class loader, and not the one in
                // the GlobalProperties object. This is because the class loader in
                // the GlobalProperties is specified by the user for resolving
                // custom classes.
                final ResourceBundle resourceBundle = getBundle(bundle);
                final String pattern = resourceBundle.getString(key);
                final MessageFormat formatter = new MessageFormat(pattern, Locale.ROOT);
                message = formatter.format(args);
            }
            catch (final MissingResourceException ignored) {
                // If the Check author didn't provide i18n resource bundles
                // and logs error messages directly, this will return
                // the author's original message
                final MessageFormat formatter = new MessageFormat(key, Locale.ROOT);
                message = formatter.format(args);
            }
        }
        return message;
    }

    /**
     * Returns the formatted custom message if one is configured.
     * @return the formatted custom message or {@code null}
     *          if there is no custom message
     */
    private String getCustomMessage() {

        if (customMessage == null) {
            return null;
        }
        final MessageFormat formatter = new MessageFormat(customMessage, Locale.ROOT);
        return formatter.format(args);
    }

    /**
     * Find a ResourceBundle for a given bundle name. Uses the classloader
     * of the class emitting this message, to be sure to get the correct
     * bundle.
     * @param bundleName the bundle name
     * @return a ResourceBundle
     */
    private ResourceBundle getBundle(String bundleName) {
        synchronized (BUNDLE_CACHE) {
            ResourceBundle resourceBundle = BUNDLE_CACHE
                    .get(bundleName);
            if (resourceBundle == null) {
                resourceBundle = ResourceBundle.getBundle(bundleName, sLocale,
                        sourceClass.getClassLoader(), new Utf8Control());
                BUNDLE_CACHE.put(bundleName, resourceBundle);
            }
            return resourceBundle;
        }
    }

    /**
     * Gets the line number.
     * @return the line number
     */
    public int getLineNo() {
        return lineNo;
    }

    /**
     * Gets the column number.
     * @return the column number
     */
    public int getColumnNo() {
        return columnNo;
    }

    /**
     * Gets the severity level.
     * @return the severity level
     */
    public SeverityLevel getSeverityLevel() {
        return severityLevel;
    }

    /**
     * @return the module identifier.
     */
    public String getModuleId() {
        return moduleId;
    }

    /**
     * Returns the message key to locate the translation, can also be used
     * in IDE plugins to map error messages to corrective actions.
     *
     * @return the message key
     */
    public String getKey() {
        return key;
    }

    /**
     * Gets the name of the source for this LocalizedMessage.
     * @return the name of the source for this LocalizedMessage
     */
    public String getSourceName() {
        return sourceClass.getName();
    }

    /**
     * Sets a locale to use for localization.
     * @param locale the locale to use for localization
     */
    public static void setLocale(Locale locale) {
        if (Locale.ENGLISH.getLanguage().equals(locale.getLanguage())) {
            sLocale = Locale.ROOT;
        }
        else {
            sLocale = locale;
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Interface Comparable methods
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public int compareTo(LocalizedMessage other) {
        int result = Integer.compare(lineNo, other.lineNo);

        if (lineNo == other.lineNo) {
            if (columnNo == other.columnNo) {
                result = getMessage().compareTo(other.getMessage());
            }
            else {
                result = Integer.compare(columnNo, other.columnNo);
            }
        }
        return result;
    }

    /**
     * <p>
     * Custom ResourceBundle.Control implementation which allows explicitly read
     * the properties files as UTF-8
     * </p>
     *
     * @author <a href="mailto:nesterenko-aleksey@list.ru">Aleksey Nesterenko</a>
     */
    protected static class Utf8Control extends Control {
        @Override
        public ResourceBundle newBundle(String aBaseName, Locale aLocale, String aFormat,
                 ClassLoader aLoader, boolean aReload) throws IOException {
            // The below is a copy of the default implementation.
            final String bundleName = toBundleName(aBaseName, aLocale);
            final String resourceName = toResourceName(bundleName, "properties");
            InputStream stream = null;
            if (aReload) {
                final URL url = aLoader.getResource(resourceName);
                if (url != null) {
                    final URLConnection connection = url.openConnection();
                    if (connection != null) {
                        connection.setUseCaches(false);
                        stream = connection.getInputStream();
                    }
                }
            }
            else {
                stream = aLoader.getResourceAsStream(resourceName);
            }
            ResourceBundle resourceBundle = null;
            if (stream != null) {
                final Reader streamReader = new InputStreamReader(stream, "UTF-8");
                try {
                    // Only this line is changed to make it to read properties files as UTF-8.
                    resourceBundle = new PropertyResourceBundle(streamReader);
                }
                finally {
                    stream.close();
                }
            }
            return resourceBundle;
        }
    }
}
