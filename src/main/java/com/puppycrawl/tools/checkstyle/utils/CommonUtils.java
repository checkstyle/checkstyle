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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.beanutils.ConversionException;

/**
 * Contains utility methods.
 *
 * @author <a href="mailto:nesterenko-aleksey@list.ru">Aleksey Nesterenko</a>
 */
public final class CommonUtils {

    /** Stop instances being created **/
    private CommonUtils() {

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
    public static boolean fileExtensionMatches(File file, String... fileExtensions) {
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
    public static boolean whitespaceBefore(int index, String line) {
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
        try {
            return Pattern.compile(pattern);
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
    public static String baseClassname(String type) {
        final int i = type.lastIndexOf('.');

        if (i == -1) {
            return type;
        }
        else {
            return type.substring(i + 1);
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
     *  path or path if base directory is null
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
}
