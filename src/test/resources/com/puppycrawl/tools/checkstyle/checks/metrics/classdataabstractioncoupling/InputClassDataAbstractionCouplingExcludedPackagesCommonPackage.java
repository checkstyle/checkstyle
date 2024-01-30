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
excludedPackages = org.apache.hc.core5.http.support


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling;

import org.apache.hc.core5.http.ssl.TlsCiphers;
import org.apache.hc.core5.http.protocol.BasicHttpContext;
import org.apache.hc.core5.http.nio.ssl.BasicClientTlsStrategy;
import org.apache.hc.core5.http.nio.command.CommandSupport;

public class InputClassDataAbstractionCouplingExcludedPackagesCommonPackage { // violation
    public TlsCiphers aa = new TlsCiphers();
    public BasicHttpContext ab = new BasicHttpContext();

    class Inner { // violation
        public BasicClientTlsStrategy b = new BasicClientTlsStrategy();
        public CommandSupport c = new CommandSupport();
    }
}

class InputClassDataAbstractionCouplingExcludedPackagesCommonPackageHidden { // violation
    public CommandSupport c = new CommandSupport();
}
