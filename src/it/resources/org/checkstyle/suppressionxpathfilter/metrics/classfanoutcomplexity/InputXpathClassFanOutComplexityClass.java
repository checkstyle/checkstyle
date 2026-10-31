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

package org.checkstyle.suppressionxpathfilter.metrics.classfanoutcomplexity;

import java.util.Date;
import java.util.Timer;

// violation below 'Class Fan-Out Complexity is 4 (max allowed is 0).'
public class InputXpathClassFanOutComplexityClass
        extends ParentClass
        implements SomeInterface {
    @Override
    public void exampleMethod() {
        Timer timer = new Timer();
    }

    public Date returnTypeViolatesCheck(){
        int x = 1;
        return new Date();
    }
}

class ParentClass {
    public void exampleMethod () {
        Character c = 'c';
    }
    static class InnerClass {

    }
}

interface SomeInterface {

}
