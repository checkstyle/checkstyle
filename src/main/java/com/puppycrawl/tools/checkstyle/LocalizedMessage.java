////
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

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import com.puppycrawl.tools.checkstyle.utils.UnmodifiableCollectionUtil;

/**
 * Represents a message that can be localised. The translations come from
 * message.properties files. The underlying implementation uses
 * java.text.MessageFormat.
 */
public class LocalizedMessage {

    /** The locale to localise messages to. **/
    private static Locale sLocale = Locale.getDefault();

    /** Name of the resource bundle to get messages from. **/
    private final String bundle;

    /** Class of the source for this message. */
    private final Class<?> sourceClass;

    /**
     * Key for the message format.
     **/
    private final String key;

    /**
     * Arguments for java.text.MessageFormat, that is why type is Object[].
     *
     * <p>Note: Changing types from Object[] will be huge breaking compatibility, as Module
     * messages use some type formatting already, so better to keep it as Object[].
     * </p>
     */
    private final Object[] args;

    /**
     * Creates a new {@code LocalizedMessage} instance.
     *
     * @param bundle resource bundle name
     * @param sourceClass the Class that is the source of the message
     * @param key the key to locate the translation.
     * @param args arguments for the translation.
     */
    public LocalizedMessage(String bundle, Class<?> sourceClass, String key,
                            Object... args) {
        this.bundle = bundle;
        this.sourceClass = sourceClass;
        this.key = key;
        if (args == null) {
            this.args = null;
        }
        else {
            this.args = UnmodifiableCollectionUtil.copyOfArray(args, args.length);
        }
    }

    /**
     * Sets a locale to use for localization.
     *
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

    /**
     * Gets the translated message.
     *
     * @return the translated message.
     */
    public String getMessage() {
        String result;
        try {
            // Important to use the default class loader, and not the one in
            // the GlobalProperties object. This is because the class loader in
            // the GlobalProperties is specified by the user for resolving
            // custom classes.
            final ResourceBundle resourceBundle = getBundle();
            final String pattern = resourceBundle.getString(key);
            final MessageFormat formatter = new MessageFormat(pattern, Locale.ROOT);
            result = formatter.format(args);
        }
        catch (final MissingResourceException ignored) {
            // If the Check author didn't provide i18n resource bundles
            // and logs audit event messages directly, this will return
            // the author's original message
            final MessageFormat formatter = new MessageFormat(key, Locale.ROOT);
            result = formatter.format(args);
        }
        return result;
    }

    /**
     * Obtain the ResourceBundle. Uses the classloader
     * of the class emitting this message, to be sure to get the correct
     * bundle.
     *
     * @return a ResourceBundle.
     */
    private ResourceBundle getBundle() {
        return ResourceBundle.getBundle(bundle, sLocale, sourceClass.getClassLoader(),
            new Utf8Control());
    }

    /**
     * Custom ResourceBundle.Control implementation which allows explicitly read
     * the properties files as UTF-8.
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
