/*
ClassFanOutComplexity
max = 0
excludedClasses = (default)ArrayIndexOutOfBoundsException, ArrayList, Boolean, Byte, \
                  Character, Class, Collection, Deprecated, Deque, Double, DoubleStream, \
                  EnumSet, Exception, Float, FunctionalInterface, HashMap, HashSet, \
                  IllegalArgumentException, IllegalStateException, IndexOutOfBoundsException, \
                  IntStream, Integer, LinkedHashMap, LinkedHashSet, LinkedList, List, Long, \
                  LongStream, Map, NullPointerException, Object, Optional, OptionalDouble, \
                  OptionalInt, OptionalLong, Override, Queue, RuntimeException, SafeVarargs, \
                  SecurityException, Set, Short, SortedMap, SortedSet, Stream, String, \
                  StringBuffer, StringBuilder, SuppressWarnings, Throwable, TreeMap, TreeSet, \
                  UnsupportedOperationException, Void, boolean, byte, char, double, float, \
                  int, long, short, var, void
excludeClassesRegexps = (default)^$
excludedPackages = (default)


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import java.util.Calendar;

/* This input file is intended to be used on strict configuration: max=0 */
// violation below 'Class Fan-Out Complexity is 2 (max allowed is 0).'
public class InputClassFanOutComplexityAnnotations {

    private int dayOfWeek = Calendar.MONDAY;

    public void foo1(@TypeAnnotation char a) {}

    public void foo2(final char @TypeAnnotation [] a) {}

    @MethodAnnotation
    public void foo3() {}

    @Override
    public String toString() {
        return super.toString();
    }

    // violation below 'Class Fan-Out Complexity is 2 (max allowed is 0).'
    @MyAnnotation
    public class InnerClass {

        @MyAnnotation
        @MethodAnnotation
        public void innerClassMethod() {}

    }

    // violation below 'Class Fan-Out Complexity is 3 (max allowed is 0).'
    public class InnerClass2 {

        @MethodAnnotation
        @MyAnnotation
        public String innerClass2Method(@TypeAnnotation String parameter) {
            return parameter.trim();
        }

    }

    // violation below 'Class Fan-Out Complexity is 2 (max allowed is 0).'
    public class InnerClass3 {

        @TypeAnnotation
        private final String warningsType = "boxing";

        @MyAnnotation
        @SuppressWarnings(value = warningsType)
        public String innerClass3Method() {
            return new Integer(5).toString();
        }

    }

}

// violation below 'Class Fan-Out Complexity is 1 (max allowed is 0).'
class OuterClass {

    private static final String name = "1";

    private static final String value = "4";

    @TwoParametersAnnotation(value = "4", dayOfWeek = 1)
    public static final String EMPTY_STRING = "";

    @TwoParametersAnnotation(value = value, dayOfWeek = Calendar.TUESDAY)
    public static final String TAB = "\t";

}

@Target(ElementType.TYPE_USE)
@interface TypeAnnotation {}

@Target(ElementType.METHOD)
@interface MethodAnnotation {}

// violation below 'Class Fan-Out Complexity is 1 (max allowed is 0).'
@MyAnnotation
class MyClass {}

// violation below 'Class Fan-Out Complexity is 1 (max allowed is 0).'
@MyAnnotation
interface MyInterface {}

@interface MyAnnotation {}

@interface TwoParametersAnnotation {

    String value();

    int dayOfWeek();

}
