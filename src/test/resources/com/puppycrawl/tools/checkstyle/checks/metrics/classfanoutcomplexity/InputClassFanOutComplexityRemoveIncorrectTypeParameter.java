/*
ClassFanOutComplexity
max = 1
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

import java.util.Collection;
import java.util.SortedSet;

import javax.annotation.Nullable;

public class InputClassFanOutComplexityRemoveIncorrectTypeParameter<E> {
  private static <E> Collection<E> typePreservingCollection(
      Collection<E> collection, @Nullable Object mutex) {
    if (collection instanceof SortedSet) {
      return sortedSet((SortedSet<E>) collection, mutex);
    }

    return collection(collection, mutex);
  }

  private static <E> Collection<E> collection(Collection<E> collection, Object mutex) {
    return null;
  }

  private static <E> Collection<E> sortedSet(SortedSet<E> collection, Object mutex) {
    return null;
  }

}
