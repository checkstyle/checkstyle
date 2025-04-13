///
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
 * Checks the number of other types a given class/record/interface/enum/annotation
 * relies on. Also, the square of this has been shown to indicate the amount
 * of maintenance required in functional programs (on a file basis) at least.
 * </div>
 *
 * <p>
 * This check processes files in the following way:
 * </p>
 * <ol>
 * <li>
 * Iterates over all tokens that might contain type reference.
 * </li>
 * <li>
 * If a class was imported with direct import (i.e. {@code import java.math.BigDecimal}),
 * or the class was referenced with the package name (i.e. {@code java.math.BigDecimal value})
 * and the package was added to the {@code excludedPackages} parameter,
 * the class does not increase complexity.
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
 * Default value is {@code 20}.
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
 * {@code classFanOutComplexity}
 * </li>
 * </ul>
 *
 * @since 3.4
 */
public final class ClassFanOutComplexityCheck extends AbstractClassCouplingCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "classFanOutComplexity";

    /** Default value of max value. */
    private static final int DEFAULT_MAX = 20;

    /** Creates new instance of this check. */
    public ClassFanOutComplexityCheck() {
        super(DEFAULT_MAX);
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.CLASS_DEF,
            TokenTypes.EXTENDS_CLAUSE,
            TokenTypes.IMPLEMENTS_CLAUSE,
            TokenTypes.ANNOTATION,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.TYPE,
            TokenTypes.LITERAL_NEW,
            TokenTypes.LITERAL_THROWS,
            TokenTypes.ANNOTATION_DEF,
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
