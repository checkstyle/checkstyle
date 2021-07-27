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
excludedPackages = com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity;

import com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity.inputs.a.aa.AAClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity.inputs.a.ab.ABClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity.inputs.b.BClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity.inputs.c.CClass;

public class InputClassFanOutComplexityExcludedPackagesCommonPackage { // violation
    public AAClass aa;
    public ABClass ab;

    class Inner { // violation
        public BClass b;
        public CClass c;
    }
}

class InputClassFanOutComplexityExcludedPackagesCommonPackageHidden { // violation
    public CClass c;
}
