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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Measures the number of distinct classes that are instantiated
 * within the given class or record. This type of coupling is not caused by inheritance or
 * the object-oriented paradigm. Generally speaking, any data type with other
 * data types as members or local variable that is an instantiation (object)
 * of another class has data abstraction coupling (DAC). The higher the DAC,
 * the more complex the structure of the class.
 * </div>
 *
 * <p>
 * This check processes files in the following way:
 * </p>
 * <ol>
 * <li>
 * Iterates over the list of tokens (defined below) and counts all mentioned classes.
 * <ul>
 * <li>
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#IMPORT">
 * PACKAGE_DEF</a>
 * </li>
 * <li>
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#IMPORT">
 * IMPORT</a>
 * </li>
 * <li>
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CLASS_DEF">
 * CLASS_DEF</a>
 * </li>
 * <li>
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INTERFACE_DEF">
 * INTERFACE_DEF</a>
 * </li>
 * <li>
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_DEF">
 * ENUM_DEF</a>
 * </li>
 * <li>
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_NEW">
 * LITERAL_NEW</a>
 * </li>
 * <li>
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RECORD_DEF">
 * RECORD_DEF</a>
 * </li>
 * </ul>
 * </li>
 * <li>
 * If a class was imported with direct import (i.e. {@code import java.math.BigDecimal}),
 * or the class was referenced with the package name (i.e. {@code java.math.BigDecimal value})
 * and the package was added to the {@code excludedPackages} parameter, the class
 * does not increase complexity.
 * </li>
 * <li>
 * If a class name was added to the {@code excludedClasses} parameter,
 * the class does not increase complexity.
 * </li>
 * </ol>
 * <ul>
 * <li>
 * Property {@code excludeClassesRegexps} - Specify user-configured regular
 * expressions to ignore classes.
 * Type is {@code java.util.regex.Pattern[]}.
 * Default value is {@code ^$}.
 * </li>
 * <li>
 * Property {@code excludedClasses} - Specify user-configured class names to ignore.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code ArrayIndexOutOfBoundsException, ArrayList, Boolean, Byte,
 * Character, Class, Collection, Deprecated, Deque, Double, DoubleStream, EnumSet, Exception,
 * Float, FunctionalInterface, HashMap, HashSet, IllegalArgumentException, IllegalStateException,
 * IndexOutOfBoundsException, IntStream, Integer, LinkedHashMap, LinkedHashSet, LinkedList, List,
 * Long, LongStream, Map, NullPointerException, Object, Optional, OptionalDouble, OptionalInt,
 * OptionalLong, Override, Queue, RuntimeException, SafeVarargs, SecurityException, Set, Short,
 * SortedMap, SortedSet, Stream, String, StringBuffer, StringBuilder, SuppressWarnings, Throwable,
 * TreeMap, TreeSet, UnsupportedOperationException, Void, boolean, byte, char, double,
 * float, int, long, short, var, void}.
 * </li>
 * <li>
 * Property {@code excludedPackages} - Specify user-configured packages to ignore.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code ""}.
 * </li>
 * <li>
 * Property {@code max} - Specify the maximum threshold allowed.
 * Type is {@code int}.
 * Default value is {@code 7}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code classDataAbstractionCoupling}
 * </li>
 * </ul>
 *
 * @since 3.4
 *
 */
public final class ClassDataAbstractionCouplingCheck
    extends AbstractClassCouplingCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "classDataAbstractionCoupling";

    /** Default allowed complexity. */
    private static final int DEFAULT_MAX = 7;

    /** Creates bew instance of the check. */
    public ClassDataAbstractionCouplingCheck() {
        super(DEFAULT_MAX);
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.LITERAL_NEW,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    protected String getLogMessageId() {
        return MSG_KEY;
    }

}
