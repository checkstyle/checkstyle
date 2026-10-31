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

import java.io.CharArrayWriter;
import java.io.File;
import java.io.StringWriter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

// violation below 'Class Data Abstraction Coupling is 8 (max allowed is 7)'
public enum InputXpathClassDataAbstractionCouplingEnum {
    Dummy;

    Thread create() {
       return new Thread(new Runnable() {
            @Override
            public void run() {
                CharArrayWriter charArrayWriter = new CharArrayWriter();
                StringWriter stringWriter = new StringWriter();
            }
        });
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            BigInteger bigInteger = new BigInteger("2");
            MathContext mathContext = new MathContext(2);
        }
    };

    BigDecimal bigDecimal = new BigDecimal("0");
    File file = new File("path");
}

