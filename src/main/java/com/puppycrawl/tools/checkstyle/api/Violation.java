///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.api;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

import javax.annotation.Nullable;

import com.puppycrawl.tools.checkstyle.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.utils.UnmodifiableCollectionUtil;

/**
 * Represents a violation that can be localised. The translations come from
 * message.properties files. The underlying implementation uses
 * java.text.MessageFormat.
 *
 * @noinspection ClassWithTooManyConstructors
 * @noinspectionreason ClassWithTooManyConstructors - immutable nature of class requires a
 *      bunch of constructors
 */
public final class Violation
    implements Comparable<Violation> {

    /** The default severity level if one is not specified. */
    private static final SeverityLevel DEFAULT_SEVERITY = SeverityLevel.ERROR;

    /** The line number. */
    private final int lineNo;
    /** The column number. */
    private final int columnNo;
    /** The column char index. */
    private final int columnCharIndex;
    /** The token type constant. See {@link TokenTypes}. */
    private final int tokenType;

    /** The severity level. */
    private final SeverityLevel severityLevel;

    /** The id of the module generating the violation. */
    private final String moduleId;

    /** Key for the violation format. */
    private final String key;

    /** Arguments for MessageFormat. */
    private final Object[] args;

    /** Name of the resource bundle to get violations from. */
    private final String bundle;

    /** Class of the source for this Violation. */
    private final Class<?> sourceClass;

    /** A custom violation overriding the default violation from the bundle. */
    @Nullable
    private final String customMessage;

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
        @Nullable String customMessage) {
        this(lineNo, 0, bundle, key, args, DEFAULT_SEVERITY, moduleId,
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
     * @param moduleId the id of the module the violation is associated with
     * @param sourceClass the Class that is the source of the violation
     * @param customMessage optional custom violation overriding the default
     * @noinspection ConstructorWithTooManyParameters
     * @noinspectionreason ConstructorWithTooManyParameters - immutable class requires a large
     *      number of arguments
     */
    // -@cs[ParameterNumber] Class is immutable, we need that amount of arguments.
    public Violation(int lineNo,
                            int columnNo,
                            String bundle,
                            String key,
                            Object[] args,
                            String moduleId,
                            Class<?> sourceClass,
                            @Nullable String customMessage) {
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
     * @noinspectionreason ConstructorWithTooManyParameters - immutable class requires a large
     *      number of arguments
     */
    // -@cs[ParameterNumber] Class is immutable, we need that amount of arguments.
    public Violation(int lineNo,
                            String bundle,
                            String key,
                            Object[] args,
                            SeverityLevel severityLevel,
                            String moduleId,
                            Class<?> sourceClass,
                            @Nullable String customMessage) {
        this(lineNo, 0, bundle, key, args, severityLevel, moduleId,
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
     * @noinspectionreason ConstructorWithTooManyParameters - immutable class requires a large
     *      number of arguments
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
                            @Nullable String customMessage) {
        this(lineNo, columnNo, 0, bundle, key, args, severityLevel, moduleId, sourceClass,
                customMessage);
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
     * @noinspectionreason ConstructorWithTooManyParameters - immutable class requires a large
     *      number of arguments
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
                            @Nullable String customMessage) {
        this(lineNo, columnNo, columnNo, tokenType, bundle, key, args, severityLevel, moduleId,
                sourceClass, customMessage);
    }

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
     * @noinspectionreason ConstructorWithTooManyParameters - immutable class requires a large
     *      number of arguments
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
                            @Nullable String customMessage) {
        this.lineNo = lineNo;
        this.columnNo = columnNo;
        this.columnCharIndex = columnCharIndex;
        this.tokenType = tokenType;
        this.key = key;

        if (args == null) {
            this.args = null;
        }
        else {
            this.args = UnmodifiableCollectionUtil.copyOfArray(args, args.length);
        }
        this.bundle = bundle;
        this.severityLevel = severityLevel;
        this.moduleId = moduleId;
        this.sourceClass = sourceClass;
        this.customMessage = customMessage;
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
     * Indicates whether some other object is "equal to" this one.
     * Suppression on enumeration is needed so code stays consistent.
     *
     * @noinspection EqualsCalledOnEnumConstant
     * @noinspectionreason EqualsCalledOnEnumConstant - enumeration is needed to keep
     *      code consistent
     */
    // -@cs[CyclomaticComplexity] equals - a lot of fields to check.
    @Override
    @SuppressWarnings("OverlyComplexBooleanExpression")
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final Violation violation = (Violation) object;
        return lineNo == violation.lineNo
                && columnNo == violation.columnNo
                && columnCharIndex == violation.columnCharIndex
                && tokenType == violation.tokenType
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

    /**
     * {@inheritDoc}
     *
     * <p>The same fields as in {@link #equals(Object)} take part in the
     * comparison, so that a result of zero is consistent with equality, as the
     * {@link Comparable} contract recommends. Line, column, module id, source
     * class, and the rendered violation stay the leading criteria; the
     * remaining fields only break a tie between violations that would
     * otherwise be reported at the same place with the same text.
     */
    @Override
    public int compareTo(Violation other) {
        int result = compareLocation(other);
        if (result == 0) {
            result = compareContent(other);
        }
        return result;
    }

    /**
     * Compares the place and the text a violation is reported with.
     *
     * @param other the violation to compare against
     * @return the value {@code 0} if the violations are reported the same way
     */
    private int compareLocation(Violation other) {
        int result = Integer.compare(lineNo, other.lineNo);
        if (result == 0) {
            result = Integer.compare(columnNo, other.columnNo);
        }
        if (result == 0) {
            result = compareNullable(moduleId, other.moduleId);
        }
        if (result == 0) {
            result = compareNullable(getSourceClassName(), other.getSourceClassName());
        }
        if (result == 0) {
            result = getViolation().compareTo(other.getViolation());
        }
        return result;
    }

    /**
     * Compares the fields that two violations reported at the same place with
     * the same text can still differ in.
     *
     * @param other the violation to compare against
     * @return the value {@code 0} if the violations carry the same content
     */
    private int compareContent(Violation other) {
        int result = Integer.compare(columnCharIndex, other.columnCharIndex);
        if (result == 0) {
            result = Integer.compare(tokenType, other.tokenType);
        }
        if (result == 0) {
            result = severityLevel.compareTo(other.severityLevel);
        }
        if (result == 0) {
            result = compareNullable(key, other.key);
        }
        if (result == 0) {
            result = compareNullable(bundle, other.bundle);
        }
        if (result == 0) {
            result = compareNullable(customMessage, other.customMessage);
        }
        if (result == 0) {
            result = Arrays.deepToString(args).compareTo(Arrays.deepToString(other.args));
        }
        return result;
    }

    /**
     * Compares two values that may be {@code null}, ordering {@code null}
     * before any other value.
     *
     * @param first the first value
     * @param second the second value
     * @return a negative integer, zero, or a positive integer as the first
     *     value is less than, equal to, or greater than the second
     */
    private static int compareNullable(@Nullable String first, @Nullable String second) {
        final int result;
        if (first == null) {
            if (second == null) {
                result = 0;
            }
            else {
                result = -1;
            }
        }
        else if (second == null) {
            result = 1;
        }
        else {
            result = first.compareTo(second);
        }
        return result;
    }

    /**
     * Gets the source class name or {@code null} when no source class is set.
     *
     * @return the source class name or {@code null}
     */
    @Nullable
    private String getSourceClassName() {
        final String name;
        if (sourceClass == null) {
            name = null;
        }
        else {
            name = sourceClass.getName();
        }
        return name;
    }

    /**
     * Gets the translated violation.
     *
     * @return the translated violation
     */
    public String getViolation() {
        final String violation;

        if (customMessage != null) {
            violation = new MessageFormat(customMessage, Locale.ROOT).format(args);
        }
        else {
            violation = new LocalizedMessage(bundle, sourceClass, key, args).getMessage();
        }

        return violation;
    }

}
