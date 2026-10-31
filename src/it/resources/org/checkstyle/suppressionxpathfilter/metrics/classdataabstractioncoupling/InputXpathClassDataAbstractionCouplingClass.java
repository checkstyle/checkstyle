/*
ClassDataAbstractionCoupling
max = (default)7
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

package org.checkstyle.suppressionxpathfilter.metrics.classdataabstractioncoupling;

import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.StringWriter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import java.util.concurrent.atomic.AtomicInteger;

// violation below 'Class Data Abstraction Coupling is 8 (max allowed is 7)'
public class InputXpathClassDataAbstractionCouplingClass {
    AtomicInteger atomicInteger = new AtomicInteger();
    BigInteger bigInteger = new BigInteger("0");
    BigDecimal bigDecimal = new BigDecimal("0");
    MathContext mathContext = new MathContext(0);
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[1]);
    CharArrayWriter charArrayWriter = new CharArrayWriter();
    StringWriter stringWriter = new StringWriter();
    File file = new File("path");
}
