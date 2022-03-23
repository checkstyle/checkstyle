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

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Represents a violation that can be localised. The translations come from
 * {@code message.properties} files. The underlying implementation uses
 * {@link java.text.MessageFormat}.
 *
 * <p>The actual violation messages are read in {@code message.properties} files
 * for English-messages, if not custom-provided. Those files might also feature
 * translation into other languages as {@code message_de.properties},
 * {@code message_pt.properties} files and so on.</p>
 *
 * <p>This class is immutable. So, each instance of this class holds the data
 * necessary for locating the violation. I.E., which was the line, the column, the
 * token type involved, the class responsible for its processing and so on. And
 * obviously, the violation message itself and its {@link Locale}.</p>
 *
 * @implNote
 * Also, it is important to note that the {@link Locale} and the message are defined
 * in actual messages just when an instance is created. Although It could be useful
 * to just set the template message during the instantiation and then reading it in
 * whatever {@link Locale} needed afterwards, this could potentially offend the
 * class' immutability and might have the side-effect of subtly and unexpectedly
 * breaking {@link #equals(Object)}, {@link #compareTo(Violation)} and
 * {@link hashCode()}. Although that might be fixable, it is a too small benefit in
 * practice being worth the trouble.
 *
 * @noinspection SerializableHasSerializationMethods, ClassWithTooManyConstructors
 */
public final class Violation
    implements Comparable<Violation>, Serializable {

    /**
     * A unique serial version identifier.
     */
    private static final long serialVersionUID = 5675176836184862151L;

    /**
     * The default severity level if one is not specified.
     */
    private static final SeverityLevel DEFAULT_SEVERITY = SeverityLevel.ERROR;

    /**
     * The locale to localize violations to.
     **/
    private static ThreadLocal<Locale> sLocale = ThreadLocal.withInitial(Locale::getDefault);

    /**
     * The locale to localize the violation.
     **/
    private final Locale locale;

    // Perhaps add the fileName here in the future?

    /**
     * The violation text.
     **/
    private final String violationText;

    /**
     * The line number.
     **/
    private final int lineNo;

    /**
     * The column number.
     **/
    private final int columnNo;

    /**
     * The column char index.
     **/
    private final int columnCharIndex;

    /**
     * The token type constant. See {@link TokenTypes}.
     **/
    private final int tokenType;

    /**
     * The severity level.
     **/
    private final SeverityLevel severityLevel;

    /**
     * The id of the module generating the violation. Can be null.
     */
    private final String moduleId; // Change to never be null in the future.

    /**
     * Key for the violation format.
     **/
    private final String key;

    /**
     * Arguments for MessageFormat.
     *
     * @noinspection NonSerializableFieldInSerializableClass
     */
    private final Object[] args; // Change to String[] in the future.

    /**
     * Class name of the source for this Violation.
     */
    private final String sourceName;

    /**
     * Creates a new {@code Violation} instance.
     *
     * @param lineNo          line number associated with the violation
     * @param columnNo        column number associated with the violation
     * @param columnCharIndex column char index associated with the violation
     * @param tokenType       token type of the event associated with violation. See
     *                        {@link TokenTypes}
     * @param bundle          resource bundle name
     * @param key             the key to locate the translation
     * @param args            arguments for the translation
     * @param severityLevel   severity level for the violation
     * @param moduleId        the id of the module the violation is associated with
     * @param sourceClass     the Class that is the source of the violation
     * @param customMessage   optional custom violation overriding the default
     * @noinspection ConstructorWithTooManyParameters
     */
    // -@cs[ParameterNumber] Class is immutable, we need that amount of arguments.
    @Deprecated // FOR REMOVAL!
    public Violation(
        int lineNo,
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
        this(sLocale.get(), lineNo, columnNo, columnCharIndex, tokenType,

             // This mess is temporary. We should refactor tests that set to null things that
             // shouldn't be null and also see if somebody still uses this constructor.
             // After that, we should delete this mess and the entire constructor altogether.
             bundle == null ? "" : bundle,
             key == null ? "" : key,
             args == null ? CommonUtil.EMPTY_STRING_ARRAY : args,
             severityLevel == null ? DEFAULT_SEVERITY : severityLevel,
             moduleId,
             sourceClass == null ? Violation.class : sourceClass,
             (bundle == null && customMessage == null) ? "" : customMessage);
    }

    /**
     * Creates a new {@code Violation} instance.
     *
     * @param lineNo        line number associated with the violation
     * @param columnNo      column number associated with the violation
     * @param tokenType     token type of the event associated with violation. See
     *                      {@link TokenTypes}
     * @param bundle        resource bundle name
     * @param key           the key to locate the translation
     * @param args          arguments for the translation
     * @param severityLevel severity level for the violation
     * @param moduleId      the id of the module the violation is associated with
     * @param sourceClass   the Class that is the source of the violation
     * @param customMessage optional custom violation overriding the default
     * @noinspection ConstructorWithTooManyParameters
     * @deprecated Prefer to use the static builder methods.
     */
    // -@cs[ParameterNumber] Class is immutable, we need that amount of arguments.
    @Deprecated // FOR REMOVAL!
    public Violation(
        int lineNo,
        int columnNo,
        int tokenType,
        String bundle,
        String key,
        Object[] args,
        SeverityLevel severityLevel,
        String moduleId,
        Class<?> sourceClass,
        String customMessage) {
        this(sLocale.get(), lineNo, columnNo, columnNo, tokenType,

             // This mess is temporary. We should refactor tests that set to null things that
             // shouldn't be null and also see if somebody still uses this constructor.
             // After that, we should delete this mess and the entire constructor altogether.
             bundle == null ? "" : bundle,
             key == null ? "" : key,
             args == null ? CommonUtil.EMPTY_STRING_ARRAY : args,
             severityLevel == null ? DEFAULT_SEVERITY : severityLevel,
             moduleId,
             sourceClass == null ? Violation.class : sourceClass,
             (bundle == null && customMessage == null) ? "" : customMessage);
    }

    /**
     * Creates a new {@code Violation} instance.
     *
     * @param lineNo        line number associated with the violation
     * @param columnNo      column number associated with the violation
     * @param bundle        resource bundle name
     * @param key           the key to locate the translation
     * @param args          arguments for the translation
     * @param severityLevel severity level for the violation
     * @param moduleId      the id of the module the violation is associated with
     * @param sourceClass   the Class that is the source of the violation
     * @param customMessage optional custom violation overriding the default
     * @noinspection ConstructorWithTooManyParameters
     * @deprecated Prefer to use the static builder methods.
     */
    // -@cs[ParameterNumber] Class is immutable, we need that amount of arguments.
    @Deprecated // FOR REMOVAL!
    public Violation(
        int lineNo,
        int columnNo,
        String bundle,
        String key,
        Object[] args,
        SeverityLevel severityLevel,
        String moduleId,
        Class<?> sourceClass,
        String customMessage) {
        this(sLocale.get(), lineNo, columnNo, columnNo, 0,

             // This mess is temporary. We should refactor tests that set to null things that
             // shouldn't be null and also see if somebody still uses this constructor.
             // After that, we should delete this mess and the entire constructor altogether.
             bundle == null ? "" : bundle,
             key == null ? "" : key,
             args == null ? CommonUtil.EMPTY_STRING_ARRAY : args,
             severityLevel == null ? DEFAULT_SEVERITY : severityLevel,
             moduleId,
             sourceClass == null ? Violation.class : sourceClass,
             (bundle == null && customMessage == null) ? "" : customMessage);
    }

    /**
     * Creates a new {@code Violation} instance.
     *
     * @param lineNo        line number associated with the violation
     * @param columnNo      column number associated with the violation
     * @param bundle        resource bundle name
     * @param key           the key to locate the translation
     * @param args          arguments for the translation
     * @param moduleId      the id of the module the violation is associated with
     * @param sourceClass   the Class that is the source of the violation
     * @param customMessage optional custom violation overriding the default
     * @noinspection ConstructorWithTooManyParameters
     * @deprecated Prefer to use the static builder methods.
     */
    // -@cs[ParameterNumber] Class is immutable, we need that amount of arguments.
    @Deprecated // FOR REMOVAL!
    public Violation(
        int lineNo,
        int columnNo,
        String bundle,
        String key,
        Object[] args,
        String moduleId,
        Class<?> sourceClass,
        String customMessage) {
        this(sLocale.get(), lineNo, columnNo, columnNo, 0,

             // This mess is temporary. We should refactor tests that set to null things that
             // shouldn't be null and also see if somebody still uses this constructor.
             // After that, we should delete this mess and the entire constructor altogether.
             bundle == null ? "" : bundle,
             key == null ? "" : key,
             args == null ? CommonUtil.EMPTY_STRING_ARRAY : args,
             DEFAULT_SEVERITY,
             moduleId,
             sourceClass == null ? Violation.class : sourceClass,
             (bundle == null && customMessage == null) ? "" : customMessage);
    }

    /**
     * Creates a new {@code Violation} instance.
     *
     * @param lineNo        line number associated with the violation
     * @param bundle        resource bundle name
     * @param key           the key to locate the translation
     * @param args          arguments for the translation
     * @param severityLevel severity level for the violation
     * @param moduleId      the id of the module the violation is associated with
     * @param sourceClass   the source class for the violation
     * @param customMessage optional custom violation overriding the default
     * @noinspection ConstructorWithTooManyParameters
     * @deprecated Prefer to use the static builder methods.
     */
    // -@cs[ParameterNumber] Class is immutable, we need that amount of arguments.
    @Deprecated // FOR REMOVAL!
    public Violation(
        int lineNo,
        String bundle,
        String key,
        Object[] args,
        SeverityLevel severityLevel,
        String moduleId,
        Class<?> sourceClass,
        String customMessage) {
        this(sLocale.get(), lineNo, 0, 0, 0, bundle, key, args, severityLevel, moduleId,
             sourceClass, customMessage);
    }

    /**
     * Creates a new {@code Violation} instance. The column number
     * defaults to 0.
     *
     * @param lineNo        line number associated with the violation
     * @param bundle        name of a resource bundle that contains audit event violations
     * @param key           the key to locate the translation
     * @param args          arguments for the translation
     * @param moduleId      the id of the module the violation is associated with
     * @param sourceClass   the name of the source for the violation
     * @param customMessage optional custom violation overriding the default
     * @deprecated Prefer to use the static builder methods.
     */

    @Deprecated
    public Violation(
        int lineNo,
        String bundle,
        String key,
        Object[] args,
        String moduleId,
        Class<?> sourceClass,
        String customMessage) {
        this(sLocale.get(), lineNo, 0, 0, 0,

             // This mess is temporary. We should refactor tests that set to null things that
             // shouldn't be null and also see if somebody still uses this constructor.
             // After that, we should delete this mess and the entire constructor altogether.
             bundle == null ? "" : bundle,
             key == null ? "" : key,
             args == null ? CommonUtil.EMPTY_STRING_ARRAY : args,
             DEFAULT_SEVERITY,
             moduleId,
             sourceClass == null ? Violation.class : sourceClass,
             (bundle == null && customMessage == null) ? "" : customMessage);
    }

    /**
     * Creates a new {@code Violation} instance.
     *
     * @param locale          The locale associated with the violation.
     * @param lineNo          The line number associated with the violation.
     * @param columnNo        The column number associated with the violation.
     * @param columnCharIndex The column char index associated with the violation.
     * @param tokenType       The token type of the event associated with violation. See
     *                        {@link TokenTypes}.
     * @param bundle          The resource bundle name.
     *                        Can be {@code null} if the {@code customMessage} isn't {@code null}.
     * @param key             The key to locate the translation.
     * @param args            The arguments for the translation.
     * @param severityLevel   The severity level for the violation.
     * @param moduleId        The id of the module the violation is associated with. Can be
     *                        {@code null}.
     * @param sourceClass     The {@link Class} that is the source of the violation.
     * @param customMessage   Optional custom violation overriding the default.
     *                        Can be {@code null} if the {@code bundle} isn't {@code null}.
     * @throws IllegalArgumentException If {@code locale}, {@code key}, {@code args},
     *                                  {@code severityLevel} or {@code sourceClass} are {@code
     *                                  null} or if {@code bundle} and
     *                                  {@code customMessage} are both {@code null}.
     * @noinspection ConstructorWithTooManyParameters
     */
    // -@cs[ParameterNumber] Class is immutable, we need that amount of arguments.
    private Violation(
        Locale locale,
        int lineNo,
        int columnNo,
        int columnCharIndex,
        int tokenType,
        String bundle,
        String key,
        Object[] args, // Change to String[] in the future.
        SeverityLevel severityLevel,
        String moduleId,
        Class<?> sourceClass,
        String customMessage) {
        if (locale == null) {
            throw new IllegalArgumentException("Misusing the Violation class - locale is null");
        }
        if (key == null) {
            throw new IllegalArgumentException("Misusing the Violation class - key is null");
        }
        if (args == null) {
            throw new IllegalArgumentException("Misusing the Violation class - args is null");
        }
        if (sourceClass == null) {
            throw new IllegalArgumentException(
                "Misusing the Violation class - sourceClass is null");
        }
        if (severityLevel == null) {
            throw new IllegalArgumentException(
                "Misusing the Violation class - severityLevel is null");
        }
        if (bundle == null && customMessage == null) {
            throw new IllegalArgumentException(
                "Misusing the Violation class - bundle and customMessage ae null");
        }

        this.locale = locale;
        this.lineNo = lineNo;
        this.columnNo = columnNo;
        this.columnCharIndex = columnCharIndex;
        this.tokenType = tokenType;
        this.key = key;
        this.args = Arrays.copyOf(args, args.length);
        this.severityLevel = severityLevel;
        this.moduleId = moduleId;
        this.sourceName = sourceClass.getName();
        this.violationText = prepareViolationText(
            bundle, locale, sourceClass, key, args, customMessage);
    }

    /**
     * Creates a new {@code Violation} instance with all the details about its location
     * set to be empty. This is intended for general level messages rather than one
     * in some specific line of some source file.
     *
     * @param bundle        resource bundle name
     * @param key           the key to locate the translation
     * @param args          arguments for the translation
     * @param moduleId      the id of the module the violation is associated with
     * @param sourceClass   the Class that is the source of the violation
     * @param customMessage optional custom violation overriding the default
     * @return An instance of a {@code Violation}.
     */
    public static Violation createGeneralMessage(
        String bundle,
        String key,
        String[] args,
        String moduleId,
        Class<?> sourceClass,
        String customMessage) {
        return new Violation(
            sLocale.get(),
            0, // lineNo
            0, // columnNo
            0, // columnCharIndex
            0, // tokenType
            bundle, key, args,
            DEFAULT_SEVERITY,
            moduleId, sourceClass, customMessage);
    }

    /**
     * Creates a new {@code Violation} instance with its location set to a particular
     * line, but without column information.
     *
     * @param lineNo        line number associated with the violation
     * @param bundle        resource bundle name
     * @param key           the key to locate the translation
     * @param args          arguments for the translation
     * @param moduleId      the id of the module the violation is associated with
     * @param sourceClass   the Class that is the source of the violation
     * @param customMessage optional custom violation overriding the default
     * @return An instance of a {@code Violation}.
     */
    public static Violation createLineViolation(
        int lineNo,
        String bundle,
        String key,
        Object[] args, // Change to String[] in the future.
        String moduleId,
        Class<?> sourceClass,
        String customMessage) {
        return new Violation(
            sLocale.get(),
            lineNo,
            0, // columnNo
            0, // columnCharIndex
            0, // tokenType
            bundle, key, args,
            DEFAULT_SEVERITY,
            moduleId, sourceClass, customMessage);
    }

    /**
     * Creates a new {@code Violation} instance with all the detail data.
     *
     * @param lineNo          line number associated with the violation
     * @param columnNo        column number associated with the violation
     * @param columnCharIndex column char index associated with the violation
     * @param tokenType       token type of the event associated with violation. See
     *                        {@link TokenTypes}
     * @param bundle          resource bundle name
     * @param key             the key to locate the translation
     * @param args            arguments for the translation
     * @param severityLevel   severity level for the violation
     * @param moduleId        the id of the module the violation is associated with
     * @param sourceClass     the Class that is the source of the violation
     * @param customMessage   optional custom violation overriding the default
     * @return An instance of a {@code Violation}.
     * @throws IllegalArgumentException If locale or fileName are {@code null}.
     */
    public static Violation createDetailedViolation(
        int lineNo,
        int columnNo,
        int columnCharIndex,
        int tokenType,
        String bundle,
        String key,
        Object[] args, // Change to String[] in the future.
        SeverityLevel severityLevel,
        String moduleId,
        Class<?> sourceClass,
        String customMessage) {
        return new Violation(sLocale.get(), lineNo, columnNo, columnCharIndex, tokenType,
                             bundle, key, args, severityLevel, moduleId, sourceClass,
                             customMessage);
    }

    /**
     * Gets the locale for this Violation.
     *
     * @return the locale for this Violation
     */
    public Locale getLocale() {
        return locale;
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
     * @return the module identifier. Can be null.
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
        return sourceName;
    }

    /**
     * Sets a locale to use for localization.
     *
     * @param locale the locale to use for localization
     * @deprecated Use the {@link #setDefaultLocale(Locale)} method instead.
     */
    @Deprecated // FOR REMOVAL!
    public static void setLocale(Locale locale) {
        setDefaultLocale(locale);
    }

    /**
     * Sets a locale to use for localization.
     *
     * @param locale the locale to use for localization
     */
    public static void setDefaultLocale(Locale locale) {
        if (locale == null) {
            throw new IllegalArgumentException("Locale can't be null");
        }
        BundleCache.clear();
        if (Locale.ENGLISH.getLanguage().equals(locale.getLanguage())) {
            sLocale.set(Locale.ROOT);
        }
        else {
            sLocale.set(locale);
        }
    }

    /**
     * Gets a locale to use for localization.
     *
     * @return the locale to use for localization
     */
    public static Locale getDefaultLocale() {
        return sLocale.get();
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * Suppression on enumeration is needed so code stays consistent.
     *
     * @param object The object to which this one should be compared.
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
        return Objects.equals(locale, violation.locale)
            && Objects.equals(lineNo, violation.lineNo)
            && Objects.equals(columnNo, violation.columnNo)
            && Objects.equals(columnCharIndex, violation.columnCharIndex)
            && Objects.equals(tokenType, violation.tokenType)
            && Objects.equals(severityLevel, violation.severityLevel)
            && Objects.equals(moduleId, violation.moduleId)
            && Objects.equals(key, violation.key)
            && Objects.equals(sourceName, violation.sourceName)
            && Objects.equals(violationText, violation.violationText)
            && Arrays.equals(args, violation.args);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locale, lineNo, columnNo, columnCharIndex, tokenType, severityLevel,
                            moduleId, key, sourceName, violationText, Arrays.hashCode(args));
    }

    ////////////////////////////////////////////////////////////////////////////
    // Interface Comparable methods
    ////////////////////////////////////////////////////////////////////////////

    private static String nonNull(String value) {
        if (value == null) return "";
        return value;
    }

    @Override
    public int compareTo(Violation other) {
        final int result1 = locale.toString().compareTo(other.locale.toString());
        final int result2 = nonNull(moduleId).compareTo(nonNull(other.moduleId));
        final int toReturn;
        if (result1 != 0) {
            toReturn = result1;
        }
        else if (lineNo != other.lineNo) {
            toReturn = Integer.compare(lineNo, other.lineNo);
        }
        else if (columnNo != other.columnNo) {
            toReturn = Integer.compare(columnNo, other.columnNo);
        }
        else if (result2 != 0) {
            toReturn = result2;
        }
        else {
            toReturn = violationText.compareTo(other.violationText);
        }
        return toReturn;
    }

    /**
     * Gets the translated violation.
     *
     * @return the translated violation
     */
    public String getViolation() {
        return violationText;
    }

    /**
     * Gets the translated violation.
     *
     * @param bundle resource bundle name
     * @param locale The locale associated with the violation
     * @param sourceClass the Class that is the source of the violation
     * @param key the key to locate the translation
     * @param args arguments for the translation
     * @param customMessage optional custom violation overriding the default
     * @return the translated violation
     */
    private static String prepareViolationText(
        String bundle,
        Locale locale,
        Class<?> sourceClass,
        String key,
        Object[] args,
        String customMessage) {
        String violation;
        if (customMessage != null) {
            final MessageFormat formatter = new MessageFormat(customMessage, Locale.ROOT);
            violation = formatter.format(args);
        }
        else {
            try {
                // Important to use the default class loader, and not the one in
                // the GlobalProperties object. This is because the class loader in
                // the GlobalProperties is specified by the user for resolving
                // custom classes.
                final ResourceBundle resourceBundle = BundleCache
                    .getBundle(bundle, locale, sourceClass.getClassLoader());
                final String pattern = resourceBundle.getString(key);
                final MessageFormat formatter = new MessageFormat(pattern, Locale.ROOT);
                violation = formatter.format(args);
            }
            catch (final MissingResourceException ignored) {
                // If the Check author didn't provide i18n resource bundles
                // and logs audit event violations directly, this will return
                // the author's original violation.
                final MessageFormat formatter = new MessageFormat(key, Locale.ROOT);
                violation = formatter.format(args);
            }
        }
        return violation;
    }
}
