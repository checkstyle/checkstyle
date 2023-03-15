/*
ClassDataAbstractionCoupling
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
excludedPackages = com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.\
                   inputs.c, com.puppycrawl.tools.checkstyle.checks.metrics.\
                   classdataabstractioncoupling.inputs.b


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling;

import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.inputs.a.aa.AAClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.inputs.a.ab.ABClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.inputs.b.BClass;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.inputs.c.CClass;

public class InputClassDataAbstractionCouplingExcludedPackagesDirectPackages { // violation
    public AAClass aa = new AAClass();
    public ABClass ab = new ABClass();

    class Inner { // total: ok
        public BClass b = new BClass(); // ok
        public CClass c = new CClass(); // ok
    }
}

class InputClassDataAbstractionCouplingExcludedPackagesDirectPackagesHidden { // total: ok
    public CClass c = new CClass(); // ok
}
