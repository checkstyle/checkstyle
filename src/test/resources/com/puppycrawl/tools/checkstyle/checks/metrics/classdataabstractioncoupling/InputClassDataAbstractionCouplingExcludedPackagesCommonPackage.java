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
excludedPackages = org.apache.commons.lang3.builder


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.text.StrTokenizer;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

// violation below, 'Class Data Abstraction Coupling is 2 (max allowed is 0)'
public class InputClassDataAbstractionCouplingExcludedPackagesCommonPackage {
    public ImmutablePair<String, Integer> aa = new ImmutablePair<>("test", 1);
    public StrTokenizer ab = new StrTokenizer();

    class Inner { // violation, 'Class Data Abstraction Coupling is 2 (max allowed is 0)'
        public MutablePair<String, String> b = new MutablePair<>("key", "value");
        public BasicThreadFactory c = new BasicThreadFactory.Builder().build();
    }
}

// violation below, 'Class Data Abstraction Coupling is 1 (max allowed is 0)'
class InputClassDataAbstractionCouplingExcludedPackagesCommonPackageHidden {
    public BasicThreadFactory c = new BasicThreadFactory.Builder().build();
}

