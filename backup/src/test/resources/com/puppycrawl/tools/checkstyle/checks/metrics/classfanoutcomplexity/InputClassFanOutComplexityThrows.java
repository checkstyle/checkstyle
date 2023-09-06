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

import java.sql.SQLException;

public class InputClassFanOutComplexityThrows {
    // violation above 'Class Fan-Out Complexity is 2 (max allowed is 0)'
    private static void foo1() throws RuntimeException, SQLException, OneMoreException {
    }
}
class OneMoreException extends Exception {}
