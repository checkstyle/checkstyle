////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Measures the number of instantiations of other classes
 * within the given class. This type of coupling is not caused by inheritance or
 * the object oriented paradigm. Generally speaking, any data type with other
 * data types as members or local variable that is an instantiation (object)
 * of another class has data abstraction coupling (DAC). The higher the DAC,
 * the more complex the structure of the class.
 * </p>
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
 * Property {@code max} - Specify the maximum threshold allowed.
 * Default value is {@code 7}.
 * </li>
 * <li>
 * Property {@code excludedClasses} - Specify user-configured class names to ignore.
 * Default value is {@code ArrayIndexOutOfBoundsException, ArrayList, Boolean, Byte,
 * Character, Class, Deprecated, Deque, Double, Exception, Float, FunctionalInterface,
 * HashMap, HashSet, IllegalArgumentException, IllegalStateException,
 * IndexOutOfBoundsException, Integer, LinkedList, List, Long, Map, NullPointerException,
 * Object, Override, Queue, RuntimeException, SafeVarargs, SecurityException, Set, Short,
 * SortedMap, SortedSet, String, StringBuffer, StringBuilder, SuppressWarnings, Throwable,
 * TreeMap, TreeSet, UnsupportedOperationException, Void, boolean, byte, char, double,
 * float, int, long, short, void}.
 * </li>
 * <li>
 * Property {@code excludeClassesRegexps} - Specify user-configured regular
 * expressions to ignore classes.
 * Default value is {@code ^$}.
 * </li>
 * <li>
 * Property {@code excludedPackages} - Specify user-configured packages to ignore.
 * Default value is {@code {}}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="ClassDataAbstractionCoupling"/&gt;
 * </pre>
 * <p>
 * To configure the check with a threshold of 5:
 * </p>
 * <pre>
 * &lt;module name="ClassDataAbstractionCoupling"&gt;
 *   &lt;property name="max" value="5"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check with two excluded classes {@code HashMap} and {@code HashSet}:
 * </p>
 * <pre>
 * &lt;module name="ClassDataAbstractionCoupling"&gt;
 *   &lt;property name="excludedClasses" value="HashMap, HashSet"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check with two regular expressions {@code ^Array.*} and {@code .*Exception$}:
 * </p>
 * <pre>
 * &lt;module name="ClassDataAbstractionCoupling"&gt;
 *   &lt;property name="excludeClassesRegexps"
 *     value="^Array.*, .*Exception$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * The following example demonstrates usage of <b>excludedClasses</b> and
 * <b>excludeClassesRegexps</b> properties
 * </p>
 * <p>
 * Expected result is one class {@code Date}
 * </p>
 * <pre>
 * &lt;module name="ClassDataAbstractionCoupling"&gt;
 *   &lt;property name="excludedClasses" value="ArrayList"/&gt;
 *   &lt;property name="excludeClassesRegexps" value="^Hash.*"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * public class InputClassCoupling {
 *   public Set _set = new HashSet();
 *   public Map _map = new HashMap();
 *   public List&lt;String&gt; _list = new ArrayList&lt;&gt;();
 *   public Date _date = new Date();
 * }
 * </pre>
 * <p>
 * To configure the check with two excluded classes {@code HashMap} and {@code HashSet}:
 * </p>
 * <pre>
 * &lt;module name="ClassDataAbstractionCoupling"&gt;
 *   &lt;property name="excludedClasses" value="HashMap, HashSet"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check with two regular expressions {@code ^Array.*} and {@code .*Exception$}:
 * </p>
 * <pre>
 * &lt;module name="ClassDataAbstractionCoupling"&gt;
 *   &lt;property name="excludeClassesRegexps" value="^Array.*, .*Exception$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * The following example demonstrates usage of <b>excludedClasses</b> and
 * <b>excludeClassesRegexps</b> properties
 * </p>
 * <p>
 * Expected result is one class {@code Date}
 * </p>
 * <pre>
 * &lt;module name="ClassDataAbstractionCoupling"&gt;
 *   &lt;property name="excludedClasses" value="ArrayList"/&gt;
 *   &lt;property name="excludeClassesRegexps" value="^Hash.*"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * public class InputClassCoupling {
 *   public Set _set = new HashSet();
 *   public Map _map = new HashMap();
 *   public List&lt;String&gt; _list = new ArrayList&lt;&gt;();
 *   public Date _date = new Date();
 * }
 * </pre>
 * <p>
 * Override property {@code excludedPackages} to mark some packages as excluded.
 * Each member of {@code excludedPackages} should be a valid identifier:
 * </p>
 * <ul>
 * <li>
 * {@code java.util} - valid, excludes all classes inside {@code java.util},
 * but not from the subpackages.
 * </li>
 * <li>
 * {@code java.util.} - invalid, should not end with a dot.
 * </li>
 * <li>
 * {@code java.util.*} - invalid, should not end with a star.
 * </li>
 * </ul>
 * <p>
 * Note, that checkstyle will ignore all classes from the {@code java.lang}
 * package and its subpackages, even if the {@code java.lang} was not listed
 * in the {@code excludedPackages} parameter.
 * </p>
 * <p>
 * Also note, that {@code excludedPackages} will not exclude classes, imported
 * via wildcard (e.g. {@code import java.math.*}). Instead of wildcard import
 * you should use direct import (e.g. {@code import java.math.BigDecimal}).
 * </p>
 * <p>
 * Also note, that checkstyle will not exclude classes within the same file
 * even if it was listed in the {@code excludedPackages} parameter.
 * For example, assuming the config is
 * </p>
 * <pre>
 * &lt;module name="ClassDataAbstractionCoupling"&gt;
 *   &lt;property name="excludedPackages" value="a.b"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * And the file {@code a.b.Foo.java} is:
 * </p>
 * <pre>
 * package a.b;
 *
 * import a.b.Bar;
 * import a.b.c.Baz;
 *
 * public class Foo {
 *   public Bar bar; // Will be ignored, located inside ignored a.b package
 *   public Baz baz; // Will not be ignored, located inside a.b.c package
 *   public Data data; // Will not be ignored, same file
 *
 *   class Data {
 *     public Foo foo; // Will not be ignored, same file
 *   }
 * }
 * </pre>
 * <p>
 * The {@code bar} member will not be counted, since the {@code a.b} added
 * to the {@code excludedPackages}. The {@code baz} member will be counted,
 * since the {@code a.b.c} was not added to the {@code excludedPackages}.
 * The {@code data} and {@code foo} members will be counted, as they are inside same file.
 * </p>
 * <p>
 * Example of usage:
 * </p>
 * <pre>
 * &lt;module name="ClassDataAbstractionCoupling"&gt;
 *   &lt;property name="excludedPackages" value="java.util, java.math"/&gt;
 * &lt;/module&gt;
 * </pre>
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
