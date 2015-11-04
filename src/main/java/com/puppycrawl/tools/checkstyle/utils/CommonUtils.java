////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.utils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.beanutils.ConversionException;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

/**
 * Contains utility methods.
 *
 * @author <a href="mailto:nesterenko-aleksey@list.ru">Aleksey Nesterenko</a>
 */
public final class CommonUtils {

    /**
     * The empty String {@code ""}.
     * @since 2.0
     */
    public static final String EMPTY = "";

    /**
     * An empty immutable {@code byte} array.
     */
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    /**
     * An empty immutable {@code double} array.
     */
    public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];

    /**
     * An empty immutable {@code int} array.
     */
    public static final int[] EMPTY_INT_ARRAY = new int[0];

    /**
     * An empty immutable {@code Integer} array.
     */
    public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];

    /**
     * An empty immutable {@code Object} array.
     */
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    /**
     * An empty immutable {@code String} array.
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * <p>
     * Is {@code true} if this is Windows.
     * </p>
     * <p>
     * The field will return {@code false} if {@code OS_NAME} is {@code null}.
     * </p>
     *
     * @since 2.0
     */
    public static final boolean IS_OS_WINDOWS;

    /**
     * <p>
     * The {@code os.name} System Property. Operating system name.
     * </p>
     * <p>
     * Defaults to {@code null} if the runtime does not have security access to read this property or the property
     * does not exist.
     * </p>
     * <p>
     * This value is initialized when the class is loaded. If {@link System#setProperty(String,String)} or
     * {@link System#setProperties(java.util.Properties)} is called after this class is loaded, the value will be
     * out of sync with that System property.
     * </p>
     *
     * @since Java 1.1
     */
    public static final String OS_NAME = getSystemProperty("os.name");

    /**
     * The prefix String for all Windows OS.
     */
    private static final String OS_NAME_WINDOWS_PREFIX = "Windows";

    /** Prefix for the exception when unable to find resource. */
    private static final String UNABLE_TO_FIND_EXCEPTION_PREFIX = "Unable to find: ";

    /** Stop instances being created. **/
    private CommonUtils() {

    }

    static {
        IS_OS_WINDOWS = getOSMatchesName(OS_NAME_WINDOWS_PREFIX);
    }

    /**
     * Returns whether the file extension matches what we are meant to process.
     *
     * @param file
     *            the file to be checked.
     * @param fileExtensions
     *            files extensions, empty property in config makes it matches to all.
     * @return whether there is a match.
     */
    public static boolean matchesFileExtension(File file, String... fileExtensions) {
        boolean result = false;
        if (fileExtensions == null || fileExtensions.length == 0) {
            result = true;
        }
        else {
            // normalize extensions so all of them have a leading dot
            final String[] withDotExtensions = new String[fileExtensions.length];
            for (int i = 0; i < fileExtensions.length; i++) {
                final String extension = fileExtensions[i];
                if (startsWithChar(extension, '.')) {
                    withDotExtensions[i] = extension;
                }
                else {
                    withDotExtensions[i] = "." + extension;
                }
            }

            final String fileName = file.getName();
            for (final String fileExtension : withDotExtensions) {
                if (fileName.endsWith(fileExtension)) {
                    result = true;
                }
            }
        }

        return result;
    }

    /**
     * Returns whether the specified string contains only whitespace up to the specified index.
     *
     * @param index
     *            index to check up to
     * @param line
     *            the line to check
     * @return whether there is only whitespace
     */
    public static boolean hasWhitespaceBefore(int index, String line) {
        for (int i = 0; i < index; i++) {
            if (!Character.isWhitespace(line.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the length of a string ignoring all trailing whitespace.
     * It is a pity that there is not a trim() like
     * method that only removed the trailing whitespace.
     *
     * @param line
     *            the string to process
     * @return the length of the string ignoring all trailing whitespace
     **/
    public static int lengthMinusTrailingWhitespace(String line) {
        int len = line.length();
        for (int i = len - 1; i >= 0; i--) {
            if (!Character.isWhitespace(line.charAt(i))) {
                break;
            }
            len--;
        }
        return len;
    }

    /**
     * Returns the length of a String prefix with tabs expanded.
     * Each tab is counted as the number of characters is
     * takes to jump to the next tab stop.
     *
     * @param inputString
     *            the input String
     * @param toIdx
     *            index in string (exclusive) where the calculation stops
     * @param tabWidth
     *            the distance between tab stop position.
     * @return the length of string.substring(0, toIdx) with tabs expanded.
     */
    public static int lengthExpandedTabs(String inputString,
                                         int toIdx,
                                         int tabWidth) {
        int len = 0;
        for (int idx = 0; idx < toIdx; idx++) {
            if (inputString.charAt(idx) == '\t') {
                len = (len / tabWidth + 1) * tabWidth;
            }
            else {
                len++;
            }
        }
        return len;
    }

    /**
     * Validates whether passed string is a valid pattern or not.
     *
     * @param pattern
     *            string to validate
     * @return true if the pattern is valid false otherwise
     */
    public static boolean isPatternValid(String pattern) {
        try {
            Pattern.compile(pattern);
        }
        catch (final PatternSyntaxException ignored) {
            return false;
        }
        return true;
    }

    /**
     * Helper method to create a regular expression.
     *
     * @param pattern
     *            the pattern to match
     * @return a created regexp object
     * @throws ConversionException
     *             if unable to create Pattern object.
     **/
    public static Pattern createPattern(String pattern) {
        return createPattern(pattern, 0);
    }

    /**
     * Helper method to create a regular expression with a specific flags.
     *
     * @param pattern
     *            the pattern to match
     * @param flags
     *            the flags to set
     * @return a created regexp object
     * @throws ConversionException
     *             if unable to create Pattern object.
     **/
    public static Pattern createPattern(String pattern, int flags) {
        try {
            return Pattern.compile(pattern, flags);
        }
        catch (final PatternSyntaxException e) {
            throw new ConversionException(
                    "Failed to initialise regular expression " + pattern, e);
        }
    }

    /**
     * @param type
     *            the fully qualified name. Cannot be null
     * @return the base class name from a fully qualified name
     */
    public static String baseClassName(String type) {
        final int index = type.lastIndexOf('.');

        if (index == -1) {
            return type;
        }
        else {
            return type.substring(index + 1);
        }
    }

    /**
     * Constructs a normalized relative path between base directory and a given path.
     *
     * @param baseDirectory
     *            the base path to which given path is relativized
     * @param path
     *            the path to relativize against base directory
     * @return the relative normalized path between base directory and
     *     path or path if base directory is null.
     */
    public static String relativizeAndNormalizePath(final String baseDirectory, final String path) {
        if (baseDirectory == null) {
            return path;
        }
        final Path pathAbsolute = Paths.get(path).normalize();
        final Path pathBase = Paths.get(baseDirectory).normalize();
        return pathBase.relativize(pathAbsolute).toString();
    }

    /**
     * Tests if this string starts with the specified prefix.
     * <p>
     * It is faster version of {@link String#startsWith(String)} optimized for
     *  one-character prefixes at the expense of
     * some readability. Suggested by SimplifyStartsWith PMD rule:
     * http://pmd.sourceforge.net/pmd-5.3.1/pmd-java/rules/java/optimizations.html#SimplifyStartsWith
     * </p>
     *
     * @param value
     *            the {@code String} to check
     * @param prefix
     *            the prefix to find
     * @return {@code true} if the {@code char} is a prefix of the given {@code String};
     *  {@code false} otherwise.
     */
    public static boolean startsWithChar(String value, char prefix) {
        return !value.isEmpty() && value.charAt(0) == prefix;
    }

    /**
     * Tests if this string ends with the specified suffix.
     * <p>
     * It is faster version of {@link String#endsWith(String)} optimized for
     *  one-character suffixes at the expense of
     * some readability. Suggested by SimplifyStartsWith PMD rule:
     * http://pmd.sourceforge.net/pmd-5.3.1/pmd-java/rules/java/optimizations.html#SimplifyStartsWith
     * </p>
     *
     * @param value
     *            the {@code String} to check
     * @param suffix
     *            the suffix to find
     * @return {@code true} if the {@code char} is a suffix of the given {@code String};
     *  {@code false} otherwise.
     */
    public static boolean endsWithChar(String value, char suffix) {
        return !value.isEmpty() && value.charAt(value.length() - 1) == suffix;
    }

    /**
     * Gets constructor of targetClass.
     * @param targetClass
     *            from which constructor is returned
     * @param parameterTypes
     *            of constructor
     * @return constructor of targetClass or {@link IllegalStateException} if any exception occurs
     * @see Class#getConstructor(Class[])
     */
    public static Constructor<?> getConstructor(Class<?> targetClass, Class<?>... parameterTypes) {
        try {
            return targetClass.getConstructor(parameterTypes);
        }
        catch (NoSuchMethodException ex) {
            throw new IllegalStateException(ex);
        }
    }

    /**
     * @param constructor
     *            to invoke
     * @param parameters
     *            to pass to constructor
     * @param <T>
     *            type of constructor
     * @return new instance of class or {@link IllegalStateException} if any exception occurs
     * @see Constructor#newInstance(Object...)
     */
    public static <T> T invokeConstructor(Constructor<T> constructor, Object... parameters) {
        try {
            return constructor.newInstance(parameters);
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            throw new IllegalStateException(ex);
        }
    }

    /**
     * Closes a stream re-throwing IOException as IllegalStateException.
     *
     * @param closeable
     *            Closeable object
     */
    public static void close(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        }
        catch (IOException e) {
            throw new IllegalStateException("Cannot close the stream", e);
        }
    }

    /**
     * Resolve the specified filename to a URI.
     * @param filename name os the file
     * @return resolved header file URI
     * @throws CheckstyleException on failure
     */
    public static URI getUriByFilename(String filename) throws CheckstyleException {
        // figure out if this is a File or a URL
        URI uri;
        try {
            final URL url = new URL(filename);
            uri = url.toURI();
        }
        catch (final URISyntaxException | MalformedURLException ignored) {
            uri = null;
        }

        if (uri == null) {
            final File file = new File(filename);
            if (file.exists()) {
                uri = file.toURI();
            }
            else {
                // check to see if the file is in the classpath
                try {
                    final URL configUrl = CommonUtils.class
                            .getResource(filename);
                    if (configUrl == null) {
                        throw new CheckstyleException(UNABLE_TO_FIND_EXCEPTION_PREFIX + filename);
                    }
                    uri = configUrl.toURI();
                }
                catch (final URISyntaxException e) {
                    throw new CheckstyleException(UNABLE_TO_FIND_EXCEPTION_PREFIX + filename, e);
                }
            }
        }

        return uri;
    }

    /**
     * Puts part of line, which matches regexp into given template
     * on positions $n where 'n' is number of matched part in line.
     * @param template the string to expand.
     * @param lineToPlaceInTemplate contains expression which should be placed into string.
     * @param regexp expression to find in comment.
     * @return the string, based on template filled with given lines
     */
    public static String fillTemplateWithStringsByRegexp(
            String template, String lineToPlaceInTemplate, Pattern regexp) {
        final Matcher matcher = regexp.matcher(lineToPlaceInTemplate);
        String result = template;
        if (matcher.find()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                // $n expands comment match like in Pattern.subst().
                result = result.replaceAll("\\$" + i, matcher.group(i));
            }
        }
        return result;
    }

    /**
     * <p>Clones an array returning a typecast result and handling
     * {@code null}.</p>
     *
     * <p>This method returns {@code null} for a {@code null} input array.</p>
     *
     * @param array  the array to clone, may be {@code null}
     * @return the cloned array, {@code null} if {@code null} input
     */
    public static int[] clone(final int... array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * <p>Converts an array of object Integers to primitives.</p>
     *
     * <p>This method returns {@code null} for a {@code null} input array.</p>
     *
     * @param array  a {@code Integer} array, may be {@code null}
     * @return an {@code int} array, {@code null} if null array input
     * @throws NullPointerException if array content is {@code null}
     */
    public static int[] toPrimitive(final Integer... array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_INT_ARRAY;
        }
        final int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * <p>Joins the elements of the provided {@code Iterator} into
     * a single String containing the provided elements.</p>
     *
     * <p>No delimiter is added before or after the list. Null objects or empty
     * strings within the iteration are represented by empty strings.</p>
     *
     * @param iterator  the {@code Iterator} of values to join together, may be null
     * @param separator  the separator character to use
     * @return the joined String, {@code null} if null iterator input
     * @since 2.0
     */
    public static String join(final Iterator<?> iterator, final char separator) {

        // handle null, zero and one elements before building a buffer
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return EMPTY;
        }
        final Object first = iterator.next();
        if (!iterator.hasNext()) {
            @SuppressWarnings( "deprecation" ) // ObjectUtils.toString(Object) has been deprecated in 3.2
            final
            String result = toString(first);
            return result;
        }

        // two or more elements
        final StringBuilder buf = new StringBuilder(256); // Java default is 16, probably too small
        if (first != null) {
            buf.append(first);
        }

        while (iterator.hasNext()) {
            buf.append(separator);
            final Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }

        return buf.toString();
    }

    /**
     * <p>Joins the elements of the provided {@code Iterator} into
     * a single String containing the provided elements.</p>
     *
     * <p>No delimiter is added before or after the list.
     * A {@code null} separator is the same as an empty String ("").</p>
     *
     * @param iterator  the {@code Iterator} of values to join together, may be null
     * @param separator  the separator character to use, null treated as ""
     * @return the joined String, {@code null} if null iterator input
     */
    public static String join(final Iterator<?> iterator, final String separator) {

        // handle null, zero and one elements before building a buffer
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return EMPTY;
        }
        final Object first = iterator.next();
        if (!iterator.hasNext()) {
            @SuppressWarnings( "deprecation" ) // ObjectUtils.toString(Object) has been deprecated in 3.2
            final String result = toString(first);
            return result;
        }

        // two or more elements
        final StringBuilder buf = new StringBuilder(256); // Java default is 16, probably too small
        if (first != null) {
            buf.append(first);
        }

        while (iterator.hasNext()) {
            if (separator != null) {
                buf.append(separator);
            }
            final Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }

    /**
     * <p>Joins the elements of the provided {@code Iterable} into
     * a single String containing the provided elements.</p>
     *
     * <p>No delimiter is added before or after the list. Null objects or empty
     * strings within the iteration are represented by empty strings.</p>
     *
     * @param iterable  the {@code Iterable} providing the values to join together, may be null
     * @param separator  the separator character to use
     * @return the joined String, {@code null} if null iterator input
     * @since 2.3
     */
    public static String join(final Iterable<?> iterable, final char separator) {
        if (iterable == null) {
            return null;
        }
        return join(iterable.iterator(), separator);
    }

    /**
     * <p>Joins the elements of the provided {@code Iterable} into
     * a single String containing the provided elements.</p>
     *
     * <p>No delimiter is added before or after the list.
     * A {@code null} separator is the same as an empty String ("").</p>
     *
     * @param iterable  the {@code Iterable} providing the values to join together, may be null
     * @param separator  the separator character to use, null treated as ""
     * @return the joined String, {@code null} if null iterator input
     * @since 2.3
     */
    public static String join(final Iterable<?> iterable, final String separator) {
        if (iterable == null) {
            return null;
        }
        return join(iterable.iterator(), separator);
    }

    /**
     * <p>Checks if a CharSequence is not empty (""), not null and not whitespace only.</p>
     *
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is
     *  not empty and not null and not whitespace
     * @since 2.0
     * @since 3.0 Changed signature from isNotBlank(String) to isNotBlank(CharSequence)
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * <p>Checks if a CharSequence is whitespace, empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace
     * @since 2.0
     * @since 3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    // ToString
    //-----------------------------------------------------------------------
    /**
     * <p>Gets the {@code toString} of an {@code Object} returning
     * an empty string ("") if {@code null} input.</p>
     *
     * <pre>
     * ObjectUtils.toString(null)         = ""
     * ObjectUtils.toString("")           = ""
     * ObjectUtils.toString("bat")        = "bat"
     * ObjectUtils.toString(Boolean.TRUE) = "true"
     * </pre>
     * @see String#valueOf(Object)
     * @param obj  the Object to {@code toString}, may be null
     * @return the passed in Object's toString, or {@code ""} if {@code null} input
     * @since 2.0
     * @deprecated this method has been replaced by {@code java.util.Objects.toString(Object)} in Java 7 and will be
     * removed in future releases. Note however that said method will return "null" for null references, while this
     * method returns and empty String. To preserve behavior use {@code java.util.Objects.toString(myObject, "")}
     */
    @Deprecated
    public static String toString(final Object obj) {
        return obj == null ? "" : obj.toString();
    }

    /**
     * Decides if the operating system matches.
     *
     * @param osNamePrefix the prefix for the os name
     * @return true if matches, or false if not or can't determine
     */
    private static boolean getOSMatchesName(final String osNamePrefix) {
        return isOSNameMatch(OS_NAME, osNamePrefix);
    }

    // -----------------------------------------------------------------------
    /**
     * <p>
     * Gets a System property, defaulting to {@code null} if the property cannot be read.
     * </p>
     * <p>
     * If a {@code SecurityException} is caught, the return value is {@code null} and a message is written to
     * {@code System.err}.
     * </p>
     *
     * @param property the system property name
     * @return the system property value or {@code null} if a security problem occurs
     */
    private static String getSystemProperty(final String property) {
        try {
            return System.getProperty(property);
        } catch (final SecurityException ex) {
            // we are not allowed to look at this property
            System.err.println("Caught a SecurityException reading the system property '" + property
                    + "'; the SystemUtils property value will default to null.");
            return null;
        }
    }
    /**
     * Decides if the operating system matches.
     * <p>
     * This method is package private instead of private to support unit test invocation.
     * </p>
     *
     * @param osName the actual OS name
     * @param osNamePrefix the prefix for the expected OS name
     * @return true if matches, or false if not or can't determine
     */
    static boolean isOSNameMatch(final String osName, final String osNamePrefix) {
        if (osName == null) {
            return false;
        }
        return osName.startsWith(osNamePrefix);
    }
}
