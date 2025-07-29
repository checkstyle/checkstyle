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
excludedPackages = org.apache.commons.lang3.tuple, org.apache.commons.lang3.text, \
org.apache.commons.lang3.concurrent


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.text.StrTokenizer;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.commons.lang3.tuple.MutablePair;

public class InputClassDataAbstractionCouplingExcludedPackagesAllIgnored { // violation
    public ImmutablePair<String, Integer> aa = new ImmutablePair<>("test", 1);
    public StrTokenizer ab = new StrTokenizer();

    class Inner { // violation
        public MutablePair<String, String> b = new MutablePair<>("key", "value");
        public BasicThreadFactory c = new BasicThreadFactory.Builder().build();
    }
}

class InputClassDataAbstractionCouplingExcludedPackagesAllIgnoredHidden { // violation
    public BasicThreadFactory c = new BasicThreadFactory.Builder().build();
}
