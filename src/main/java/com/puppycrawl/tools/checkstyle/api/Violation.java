////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

/**
 * Represents a violation that can be localised. The translations come from
 * message.properties files. The underlying implementation uses
 * java.text.MessageFormat.
 *
 * @noinspection SerializableHasSerializationMethods, ClassWithTooManyConstructors
 */
public final class Violation
    implements Comparable<Violation>, Serializable {

    /** A unique serial version identifier. */
    private static final long serialVersionUID = 5675176836184862150L;

    /**
     * A cache that maps bundle names to ResourceBundles.
     * Avoids repetitive calls to ResourceBundle.getBundle().
     */
    private static final Map<String, ResourceBundle> BUNDLE_CACHE =
        Collections.synchronizedMap(new HashMap<>());

    /** The default severity level if one is not specified. */
    private static final SeverityLevel DEFAULT_SEVERITY = SeverityLevel.ERROR;

    /** The locale to localise violations to. **/
    private static Locale sLocale = Locale.getDefault();

    /** The line number. **/
    private final int lineNo;
    /** The column number. **/
    private final int columnNo;
    /** The column char index. **/
    private final int columnCharIndex;
    /** The token type constant. See {@link TokenTypes}. **/
    private final int tokenType;

    /** The severity level. **/
    private final SeverityLevel severityLevel;

    /** The id of the module generating the violation. */
    private final String moduleId;

    /** Key for the violation format. **/
    private final String key;

    /**
     * Arguments for MessageFormat.
     *
     * @noinspection NonSerializableFieldInSerializableClass
     */
    private final Object[] args;

    /** Name of the resource bundle to get violations from. **/
    private final String bundle;

    /** Class of the source for this Violation. */
    private final Class<?> sourceClass;

    /** A custom violation overriding the default violation from the bundle. */
    private final String customMessage;

    /**
     * Creates a new {@code Violation} instance.
     *
     * @param lineNo line number associated with the violation
     * @param columnNo column number associated with the violation
     * @param columnCharIndex column char index associated with the violation
     * @param tokenType token type of the event associated with violation. See {@link TokenTypes}
     * @param bundle resource bundle name
     * @param key the key to locate the translation
     * @param args arguments for the translation
     * @param severityLevel severity level for the violation
     * @param moduleId the id of the module the violation is associated with
     * @param sourceClass the Class that is the source of the violation
     * @param customMessage optional custom violation overriding the default
     * @noinspection ConstructorWithTooManyParameters
     */
    // -@cs[ParameterNumber] Class is immutable, we need that amount of arguments.
    public Violation(int lineNo,
                            int columnNo,
                            int columnCharIndex,
                            int tokenType,
                            String bundle,
                            String key,
                            Object[] args,
                            SeverityLevel severityLevel,
                            String moduleId,
                            Class<?> sourceClass,
                            String customMessage) {
        this.lineNo = lineNo;
        this.columnNo = columnNo;
        this.columnCharIndex = columnCharIndex;
        this.tokenType = tokenType;
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
     * Creates a new {@code Violation} instance.
     *
     * @param lineNo line number associated with the violation
     * @param columnNo column number associated with the violation
     * @param tokenType token type of the event associated with violation. See {@link TokenTypes}
     * @param bundle resource bundle name
     * @param key the key to locate the translation
     * @param args arguments for the translation
     * @param severityLevel severity level for the violation
     * @param moduleId the id of the module the violation is associated with
     * @param sourceClass the Class that is the source of the violation
     * @param customMessage optional custom violation overriding the default
     * @noinspection ConstructorWithTooManyParameters
     */
    // -@cs[ParameterNumber] Class is immutable, we need that amount of arguments.
    public Violation(int lineNo,
                            int columnNo,
                            int tokenType,
                            String bundle,
                            String key,
                            Object[] args,
                            SeverityLevel severityLevel,
                            String moduleId,
                            Class<?> sourceClass,
                            String customMessage) {
        this(lineNo, columnNo, columnNo, tokenType, bundle, key, args, severityLevel, moduleId,
                sourceClass, customMessage);
    }

    /**
     * Creates a new {@code Violation} instance.
     *
     * @param lineNo line number associated with the violation
     * @param columnNo column number associated with the violation
     * @param bundle resource bundle name
     * @param key the key to locate the translation
     * @param args arguments for the translation
     * @param severityLevel severity level for the violation
     * @param moduleId the id of the module the violation is associated with
     * @param sourceClass the Class that is the source of the violation
     * @param customMessage optional custom violation overriding the default
     * @noinspection ConstructorWithTooManyParameters
     */
    // -@cs[ParameterNumber] Class is immutable, we need that amount of arguments.
    public Violation(int lineNo,
                            int columnNo,
                            String bundle,
                            String key,
                            Object[] args,
                            SeverityLevel severityLevel,
                            String moduleId,
                            Class<?> sourceClass,
                            String customMessage) {
        this(lineNo, columnNo, 0, bundle, key, args, severityLevel, moduleId, sourceClass,
                customMessage);
    }

    /**
     * Creates a new {@code Violation} instance.
     *
     * @param lineNo line number associated with the violation
     * @param columnNo column number associated with the violation
     * @param bundle resource bundle name
     * @param key the key to locate the translation
     * @param args arguments for the translation
     * @param moduleId the id of the module the violation is associated with
     * @param sourceClass the Class that is the source of the violation
     * @param customMessage optional custom violation overriding the default
     * @noinspection ConstructorWithTooManyParameters
     */
    // -@cs[ParameterNumber] Class is immutable, we need that amount of arguments.
    public Violation(int lineNo,
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
     * Creates a new {@code Violation} instance.
     *
     * @param lineNo line number associated with the violation
     * @param bundle resource bundle name
     * @param key the key to locate the translation
     * @param args arguments for the translation
     * @param severityLevel severity level for the violation
     * @param moduleId the id of the module the violation is associated with
     * @param sourceClass the source class for the violation
     * @param customMessage optional custom violation overriding the default
     * @noinspection ConstructorWithTooManyParameters
     */
    // -@cs[ParameterNumber] Class is immutable, we need that amount of arguments.
    public Violation(int lineNo,
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
     * Creates a new {@code Violation} instance. The column number
     * defaults to 0.
     *
     * @param lineNo line number associated with the violation
     * @param bundle name of a resource bundle that contains audit event violations
     * @param key the key to locate the translation
     * @param args arguments for the translation
     * @param moduleId the id of the module the violation is associated with
     * @param sourceClass the name of the source for the violation
     * @param customMessage optional custom violation overriding the default
     */
    public Violation(
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

    /**
     * Gets the line number.
     *
     * @return the line number
     */
    public int getLineNo() {
        return lineNo;
    }

    /**
     * Gets the column number.
     *
     * @return the column number
     */
    public int getColumnNo() {
        return columnNo;
    }

    /**
     * Gets the column char index.
     *
     * @return the column char index
     */
    public int getColumnCharIndex() {
        return columnCharIndex;
    }

    /**
     * Gets the token type.
     *
     * @return the token type
     */
    public int getTokenType() {
        return tokenType;
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
     * Returns the violation key to locate the translation, can also be used
     * in IDE plugins to map audit event violations to corrective actions.
     *
     * @return the violation key
     */
    public String getKey() {
        return key;
    }

    /**
     * Gets the name of the source for this Violation.
     *
     * @return the name of the source for this Violation
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
     * Indicates whether some other object is "equal to" this one.
     * Suppression on enumeration is needed so code stays consistent.
     *
     * @noinspection EqualsCalledOnEnumConstant
     */
    // -@cs[CyclomaticComplexity] equals - a lot of fields to check.
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final Violation violation = (Violation) object;
        return Objects.equals(lineNo, violation.lineNo)
                && Objects.equals(columnNo, violation.columnNo)
                && Objects.equals(columnCharIndex, violation.columnCharIndex)
                && Objects.equals(tokenType, violation.tokenType)
                && Objects.equals(severityLevel, violation.severityLevel)
                && Objects.equals(moduleId, violation.moduleId)
                && Objects.equals(key, violation.key)
                && Objects.equals(bundle, violation.bundle)
                && Objects.equals(sourceClass, violation.sourceClass)
                && Objects.equals(customMessage, violation.customMessage)
                && Arrays.equals(args, violation.args);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineNo, columnNo, columnCharIndex, tokenType, severityLevel, moduleId,
                key, bundle, sourceClass, customMessage, Arrays.hashCode(args));
    }

    ////////////////////////////////////////////////////////////////////////////
    // Interface Comparable methods
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public int compareTo(Violation other) {
        final int result;

        if (lineNo == other.lineNo) {
            if (columnNo == other.columnNo) {
                if (Objects.equals(moduleId, other.moduleId)) {
                    result = getViolation().compareTo(other.getViolation());
                }
                else if (moduleId == null) {
                    result = -1;
                }
                else if (other.moduleId == null) {
                    result = 1;
                }
                else {
                    result = moduleId.compareTo(other.moduleId);
                }
            }
            else {
                result = Integer.compare(columnNo, other.columnNo);
            }
        }
        else {
            result = Integer.compare(lineNo, other.lineNo);
        }
        return result;
    }

    /**
     * Gets the translated violation.
     *
     * @return the translated violation
     */
    public String getViolation() {
        String violation = getCustomViolation();

        if (violation == null) {
            try {
                // Important to use the default class loader, and not the one in
                // the GlobalProperties object. This is because the class loader in
                // the GlobalProperties is specified by the user for resolving
                // custom classes.
                final ResourceBundle resourceBundle = getBundle(bundle);
                final String pattern = resourceBundle.getString(key);
                final MessageFormat formatter = new MessageFormat(pattern, Locale.ROOT);
                violation = formatter.format(args);
            }
            catch (final MissingResourceException ignored) {
                // If the Check author didn't provide i18n resource bundles
                // and logs audit event violations directly, this will return
                // the author's original violation
                final MessageFormat formatter = new MessageFormat(key, Locale.ROOT);
                violation = formatter.format(args);
            }
        }
        return violation;
    }

    /**
     * Returns the formatted custom violation if one is configured.
     *
     * @return the formatted custom violation or {@code null}
     *          if there is no custom violation
     */
    private String getCustomViolation() {
        String violation = null;
        if (customMessage != null) {
            final MessageFormat formatter = new MessageFormat(customMessage, Locale.ROOT);
            violation = formatter.format(args);
        }
        return violation;
    }

    /**
     * Find a ResourceBundle for a given bundle name. Uses the classloader
     * of the class emitting this violation, to be sure to get the correct
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
