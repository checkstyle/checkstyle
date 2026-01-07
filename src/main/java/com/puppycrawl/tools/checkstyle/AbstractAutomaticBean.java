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

package com.puppycrawl.tools.checkstyle;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.Arrays;
import java.util.Optional;
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
    private Configuration configuration;

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
    public final void configure(Configuration config) throws CheckstyleException {
        configuration = config;
        for (final String key : config.getPropertyNames()) {
            tryCopyProperty(key, config.getProperty(key), true);
        }
        finishLocalSetup();
        for (final Configuration childConfig : config.getChildren()) {
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
        try {
            // BeanUtilsBean.copyProperties silently ignores missing setters
            // for key, so we have to go through great lengths here to
            // figure out if the bean property really exists.
            if (recheck && PropertyUtils.getPropertyDescriptor(this, key) == null) {
                throw new CheckstyleException(getLocalizedMessage(
                        "AbstractAutomaticBean.doesNotExist", key));
            }
            // finally we can set the bean property
            createBeanUtilsBean().copyProperty(this, key, value);
        }
        catch (final InvocationTargetException | IllegalAccessException
                | NoSuchMethodException exc) {
            // There is no way to catch IllegalAccessException | NoSuchMethodException
            // as we do PropertyUtils.getPropertyDescriptor before beanUtils.copyProperty,
            // so we have to join these exceptions with InvocationTargetException
            // to satisfy UTs coverage
            throw new CheckstyleException(getLocalizedMessage(
                    "AbstractAutomaticBean.cannotSet", key, value), exc);
        }
        catch (final IllegalArgumentException | ConversionException exc) {
            throw new CheckstyleException(getLocalizedMessage(
                    "AbstractAutomaticBean.illegalValue", value, key), exc);
        }
    }

    /**
     * Implements the Contextualizable interface using bean introspection.
     *
     * @see Contextualizable
     */
    @Override
    public final void contextualize(Context context) throws CheckstyleException {
        for (final String key : context.getAttributeNames()) {
            tryCopyProperty(key, context.get(key), false);
        }
    }

    /**
     * Returns the configuration that was used to configure this component.
     *
     * @return the configuration that was used to configure this component.
     */
    protected final Configuration getConfiguration() {
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
    protected void setupChild(Configuration childConf) throws CheckstyleException {
        if (childConf != null) {
            throw new CheckstyleException(getLocalizedMessage(
                    "AbstractAutomaticBean.disallowedChild", childConf.getName(),
                configuration.getName()));
        }
    }
    /**
     * Extracts localized messages from properties files.
     *
     * @param messageKey the key pointing to localized message in respective properties file.
     * @param args       the arguments of message in respective properties file.
     * @return a string containing extracted localized message
     */

    private static String getLocalizedMessage(String messageKey, Object... args) {
        return new LocalizedMessage(
            Definitions.CHECKSTYLE_BUNDLE, AbstractAutomaticBean.class,
                    messageKey, args).getMessage();
    }

    /** A converter that converts a string to a pattern. */
    private static final class PatternConverter implements Converter {

        @Override
        @SuppressWarnings("unchecked")
        public Object convert(Class type, Object value) {
            return CommonUtil.createPattern(value.toString());
        }

    }

    /** A converter that converts a comma-separated string into an array of patterns. */
    private static final class PatternArrayConverter implements Converter {

        @Override
        @SuppressWarnings("unchecked")
        public Object convert(Class type, Object value) {
            return Arrays.stream(value.toString().split(COMMA_SEPARATOR))
                    .map(String::trim)
                    .map(CommonUtil::createPattern)
                    .toArray(Pattern[]::new);
        }
    }

    /** A converter that converts strings to severity level. */
    private static final class SeverityLevelConverter implements Converter {

        @Override
        @SuppressWarnings("unchecked")
        public Object convert(Class type, Object value) {
            return SeverityLevel.getInstance(value.toString());
        }

    }

    /** A converter that converts strings to scope. */
    private static final class ScopeConverter implements Converter {

        @Override
        @SuppressWarnings("unchecked")
        public Object convert(Class type, Object value) {
            return Scope.getInstance(value.toString());
        }

    }

    /** A converter that converts strings to uri. */
    private static final class UriConverter implements Converter {

        @Nullable
        @Override
        @SuppressWarnings("unchecked")
        public Object convert(Class type, Object value) {
            return Optional.of(value.toString())
                    .filter(urlStr -> !CommonUtil.isBlank(urlStr))
                    .map(urlStr -> {
                        try {
                            return CommonUtil.getUriByFilename(urlStr);
                        }
                        catch (CheckstyleException exc) {
                            throw new IllegalArgumentException(exc);
                        }
                    })
                    .orElse(null);
        }
    }

    /**
     * A converter that does not care whether the array elements contain String
     * characters like '*' or '_'. The normal ArrayConverter class has problems
     * with these characters.
     */
    private static final class RelaxedStringArrayConverter implements Converter {

        @Override
        @SuppressWarnings("unchecked")
        public Object convert(Class type, Object value) {
            return Arrays.stream(value.toString().trim().split(COMMA_SEPARATOR))
                    .map(String::trim)
                    .toArray(String[]::new);
        }
    }

    /**
     * A converter that converts strings to {@link AccessModifierOption}.
     * This implementation does not care whether the array elements contain characters like '_'.
     * The normal {@link ArrayConverter} class has problems with this character.
     */
    private static final class RelaxedAccessModifierArrayConverter implements Converter {

        @Override
        @SuppressWarnings("unchecked")
        public Object convert(Class type, Object value) {
            return Arrays.stream(value.toString().trim().split(COMMA_SEPARATOR))
                    .map(String::trim)
                    .map(AccessModifierOption::getInstance)
                    .toArray(AccessModifierOption[]::new);
        }
    }

}
