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
excludedPackages = com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity.\
                   inputs.a.aa, com.puppycrawl.tools.checkstyle.checks.metrics.\
                   classfanoutcomplexity.inputs.a.ab, com.puppycrawl.tools.checkstyle.checks.\
                   metrics.classfanoutcomplexity.inputs.b, com.puppycrawl.tools.checkstyle.\
                   checks.metrics.classfanoutcomplexity.inputs.c


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity;

public class InputClassFanOutComplexityExcludedPackagesAllIgnored { // violation
    public static class AAClass { // ok
    }
    public AAClass aa; // ok
    public static class ABClass { // ok
    }
    public ABClass ab; // ok

    class Inner { // violation
        public class BClass { // ok
        }
        public BClass b; // ok
        public class CClass { // ok
        }
        public CClass c; // ok
    }
}

class InputClassFanOutComplexityExcludedPackagesAllIgnoredHidden { // violation
    public class CClass { // ok
    }
    public CClass c; // ok
}


