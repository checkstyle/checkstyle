///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.ArrayConverter;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.ByteConverter;
import org.apache.commons.beanutils.converters.CharacterConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortConverter;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configurable;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.Context;
import com.puppycrawl.tools.checkstyle.api.Contextualizable;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.checks.naming.AccessModifierOption;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * A Java Bean that implements the component lifecycle interfaces by
 * calling the bean's setters for all configuration attributes.
 */
public abstract class AbstractAutomaticBean
    implements Configurable, Contextualizable {

    /**
     * Enum to specify behaviour regarding ignored modules.
     */
    public enum OutputStreamOptions {

        /**
         * Close stream in the end.
         */
        CLOSE,

        /**
         * Do nothing in the end.
         */
        NONE,

    }

    /** Comma separator for StringTokenizer. */
    private static final String COMMA_SEPARATOR = ",";

    /** The configuration of this bean. */
    private @MonotonicNonNull Configuration configuration;

    /**
     * Provides a hook to finish the part of this component's setup that
     * was not handled by the bean introspection.
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     *
     * @throws CheckstyleException if there is a configuration error.
     */
    protected abstract void finishLocalSetup() throws CheckstyleException;

    /**
     * Creates a BeanUtilsBean that is configured to use
     * type converters that throw a ConversionException
     * instead of using the default value when something
     * goes wrong.
     *
     * @return a configured BeanUtilsBean
     */
    private static BeanUtilsBean createBeanUtilsBean() {
        final ConvertUtilsBean cub = new ConvertUtilsBean();

        registerIntegralTypes(cub);
        registerCustomTypes(cub);

        return new BeanUtilsBean(cub, new PropertyUtilsBean());
    }

    /**
     * Register basic types of JDK like boolean, int, and String to use with BeanUtils. All these
     * types are found in the {@code java.lang} package.
     *
     * @param cub
     *            Instance of {@link ConvertUtilsBean} to register types with.
     */
    private static void registerIntegralTypes(ConvertUtilsBean cub) {
        cub.register(new BooleanConverter(), Boolean.TYPE);
        cub.register(new BooleanConverter(), Boolean.class);
        cub.register(new ArrayConverter(
            boolean[].class, new BooleanConverter()), boolean[].class);
        cub.register(new ByteConverter(), Byte.TYPE);
        cub.register(new ByteConverter(), Byte.class);
        cub.register(new ArrayConverter(byte[].class, new ByteConverter()),
            byte[].class);
        cub.register(new CharacterConverter(), Character.TYPE);
        cub.register(new CharacterConverter(), Character.class);
        cub.register(new ArrayConverter(char[].class, new CharacterConverter()),
            char[].class);
        cub.register(new DoubleConverter(), Double.TYPE);
        cub.register(new DoubleConverter(), Double.class);
        cub.register(new ArrayConverter(double[].class, new DoubleConverter()),
            double[].class);
        cub.register(new FloatConverter(), Float.TYPE);
        cub.register(new FloatConverter(), Float.class);
        cub.register(new ArrayConverter(float[].class, new FloatConverter()),
            float[].class);
        cub.register(new IntegerConverter(), Integer.TYPE);
        cub.register(new IntegerConverter(), Integer.class);
        cub.register(new ArrayConverter(int[].class, new IntegerConverter()),
            int[].class);
        cub.register(new LongConverter(), Long.TYPE);
        cub.register(new LongConverter(), Long.class);
        cub.register(new ArrayConverter(long[].class, new LongConverter()),
            long[].class);
        cub.register(new ShortConverter(), Short.TYPE);
        cub.register(new ShortConverter(), Short.class);
        cub.register(new ArrayConverter(short[].class, new ShortConverter()),
            short[].class);
        cub.register(new RelaxedStringArrayConverter(), String[].class);

        // BigDecimal, BigInteger, Class, Date, String, Time, TimeStamp
        // do not use defaults in the default configuration of ConvertUtilsBean
    }

    /**
     * Register custom types of JDK like URI and Checkstyle specific classes to use with BeanUtils.
     * None of these types should be found in the {@code java.lang} package.
     *
     * @param cub
     *            Instance of {@link ConvertUtilsBean} to register types with.
     */
    private static void registerCustomTypes(ConvertUtilsBean cub) {
        cub.register(new PatternConverter(), Pattern.class);
        cub.register(new PatternArrayConverter(), Pattern[].class);
        cub.register(new SeverityLevelConverter(), SeverityLevel.class);
        cub.register(new ScopeConverter(), Scope.class);
        cub.register(new UriConverter(), URI.class);
        cub.register(new RelaxedAccessModifierArrayConverter(), AccessModifierOption[].class);
    }

    /**
     * Implements the Configurable interface using bean introspection.
     *
     * <p>Subclasses are allowed to add behaviour. After the bean
     * based setup has completed first the method
     * {@link #finishLocalSetup finishLocalSetup}
     * is called to allow completion of the bean's local setup,
     * after that the method {@link #setupChild setupChild}
     * is called for each {@link Configuration#getChildren child Configuration}
     * of {@code configuration}.
     *
     * @see Configurable
     */
    @Override
    public final void configure(Configuration config)
            throws CheckstyleException {
        configuration = config;

        final String[] attributes = config.getPropertyNames();

        for (final String key : attributes) {
            final String value = config.getProperty(key);

            tryCopyProperty(key, value, true);
        }

        finishLocalSetup();

        final Configuration[] childConfigs = config.getChildren();
        for (final Configuration childConfig : childConfigs) {
            setupChild(childConfig);
        }
    }

    /**
     * Recheck property and try to copy it.
     *
     * @param key key of value
     * @param value value
     * @param recheck whether to check for property existence before copy
     * @throws CheckstyleException when property defined incorrectly
     */
    private void tryCopyProperty(String key, Object value, boolean recheck)
            throws CheckstyleException {
        final BeanUtilsBean beanUtils = createBeanUtilsBean();

        try {
            if (recheck) {
                // BeanUtilsBean.copyProperties silently ignores missing setters
                // for key, so we have to go through great lengths here to
                // figure out if the bean property really exists.
                final PropertyDescriptor descriptor =
                        PropertyUtils.getPropertyDescriptor(this, key);
                if (descriptor == null) {
                    final String message = String.format(Locale.ROOT, "Property '%s' "
                            + "does not exist, please check the documentation", key);
                    throw new CheckstyleException(message);
                }
            }
            // finally we can set the bean property
            beanUtils.copyProperty(this, key, value);
        }
        catch (final InvocationTargetException | IllegalAccessException
                | NoSuchMethodException ex) {
            // There is no way to catch IllegalAccessException | NoSuchMethodException
            // as we do PropertyUtils.getPropertyDescriptor before beanUtils.copyProperty,
            // so we have to join these exceptions with InvocationTargetException
            // to satisfy UTs coverage
            final String message = String.format(Locale.ROOT,
                    "Cannot set property '%s' to '%s'", key, value);
            throw new CheckstyleException(message, ex);
        }
        catch (final IllegalArgumentException | ConversionException ex) {
            final String message = String.format(Locale.ROOT, "illegal value '%s' for property "
                    + "'%s'", value, key);
            throw new CheckstyleException(message, ex);
        }
    }

    /**
     * Implements the Contextualizable interface using bean introspection.
     *
     * @see Contextualizable
     */
    @Override
    public final void contextualize(Context context)
            throws CheckstyleException {
        final Collection<String> attributes = context.getAttributeNames();

        for (final String key : attributes) {
            final Object value = context.get(key);

            tryCopyProperty(key, value, false);
        }
    }

    /**
     * Returns the configuration that was used to configure this component.
     *
     * @return the configuration that was used to configure this component.
     * @throws IllegalArgumentException throw exception when configuration is null.
     */
    @EnsuresNonNull("configuration")
    protected final Configuration getConfiguration() {
        if (configuration == null) {
            throw new IllegalArgumentException("configuration should not be null");
        }
        return configuration;
    }

    /**
     * Called by configure() for every child of this component's Configuration.
     *
     * <p>
     * The default implementation throws {@link CheckstyleException} if
     * {@code childConf} is {@code null} because it doesn't support children. It
     * must be overridden to validate and support children that are wanted.
     * </p>
     *
     * @param childConf a child of this component's Configuration
     * @throws CheckstyleException if there is a configuration error.
     * @see Configuration#getChildren
     */
    @RequiresNonNull("configuration")
    protected void setupChild(Configuration childConf)
            throws CheckstyleException {
        if (childConf != null) {
            throw new CheckstyleException(childConf.getName() + " is not allowed as a child in "
                    + configuration.getName() + ". Please review 'Parent Module' section "
                    + "for this Check in web documentation if Check is standard.");
        }
    }

    /** A converter that converts a string to a pattern. */
    private static final class PatternConverter implements Converter {

        @SuppressWarnings("unchecked")
        @Override
        public Object convert(Class type, Object value) {
            return CommonUtil.createPattern(value.toString());
        }

    }

    /** A converter that converts a comma-separated string into an array of patterns. */
    private static final class PatternArrayConverter implements Converter {

        @SuppressWarnings("unchecked")
        @Override
        public Object convert(Class type, Object value) {
            final StringTokenizer tokenizer = new StringTokenizer(
                    value.toString(), COMMA_SEPARATOR);
            final List<Pattern> result = new ArrayList<>();

            while (tokenizer.hasMoreTokens()) {
                final String token = tokenizer.nextToken();
                result.add(CommonUtil.createPattern(token.trim()));
            }

            return result.toArray(new Pattern[0]);
        }
    }

    /** A converter that converts strings to severity level. */
    private static final class SeverityLevelConverter implements Converter {

        @SuppressWarnings("unchecked")
        @Override
        public Object convert(Class type, Object value) {
            return SeverityLevel.getInstance(value.toString());
        }

    }

    /** A converter that converts strings to scope. */
    private static final class ScopeConverter implements Converter {

        @SuppressWarnings("unchecked")
        @Override
        public Object convert(Class type, Object value) {
            return Scope.getInstance(value.toString());
        }

    }

    /** A converter that converts strings to uri. */
    private static final class UriConverter implements Converter {

        @SuppressWarnings("unchecked")
        @Override
        @Nullable
        public Object convert(Class type, Object value) {
            final String url = value.toString();
            URI result = null;

            if (!CommonUtil.isBlank(url)) {
                try {
                    result = CommonUtil.getUriByFilename(url);
                }
                catch (CheckstyleException ex) {
                    throw new IllegalArgumentException(ex);
                }
            }

            return result;
        }

    }

    /**
     * A converter that does not care whether the array elements contain String
     * characters like '*' or '_'. The normal ArrayConverter class has problems
     * with these characters.
     */
    private static final class RelaxedStringArrayConverter implements Converter {

        @SuppressWarnings("unchecked")
        @Override
        public Object convert(Class type, Object value) {
            final StringTokenizer tokenizer = new StringTokenizer(
                value.toString().trim(), COMMA_SEPARATOR);
            final List<String> result = new ArrayList<>();

            while (tokenizer.hasMoreTokens()) {
                final String token = tokenizer.nextToken();
                result.add(token.trim());
            }

            return result.toArray(CommonUtil.EMPTY_STRING_ARRAY);
        }

    }

    /**
     * A converter that converts strings to {@link AccessModifierOption}.
     * This implementation does not care whether the array elements contain characters like '_'.
     * The normal {@link ArrayConverter} class has problems with this character.
     */
    private static final class RelaxedAccessModifierArrayConverter implements Converter {

        /** Constant for optimization. */
        private static final AccessModifierOption[] EMPTY_MODIFIER_ARRAY =
                new AccessModifierOption[0];

        @SuppressWarnings("unchecked")
        @Override
        public Object convert(Class type, Object value) {
            // Converts to a String and trims it for the tokenizer.
            final StringTokenizer tokenizer = new StringTokenizer(
                value.toString().trim(), COMMA_SEPARATOR);
            final List<AccessModifierOption> result = new ArrayList<>();

            while (tokenizer.hasMoreTokens()) {
                final String token = tokenizer.nextToken();
                result.add(AccessModifierOption.getInstance(token));
            }

            return result.toArray(EMPTY_MODIFIER_ARRAY);
        }

    }

}
