/*
ClassFanOutComplexity
max = 2
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

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity;

import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class InputClassFanOutComplexityRecords { // violation
    private class InnerClass {
        public List _list = new ArrayList();
    }

    private class AnotherInnerClass {
        public String _string = "";
    }

    public static MyRecord2 myRecord2 = new MyRecord2();
    public Set _set = /*block comment*/new HashSet();
    public Map _map = new HashMap();
    public String _string = "";
    public int[] _intArray = new int[0];
    public InnerClass _innerClass;
    public AnotherInnerClass _anotherInnerClass;

    public void foo() throws NamingException {
    }
}

record MyRecord1(boolean a, boolean b) { // violation
    private class InnerClass {
        public List _list = new ArrayList();
    }

    private class AnotherInnerClass {
        public String _string = "";
    }

    public static MyRecord2 myRecord2 = new MyRecord2();
    public static Set _set = /*block comment*/new HashSet();
    public static Map _map = new HashMap();
    public static String _string = "";
    public static int[] _intArray = new int[0];
    public static InnerClass _innerClass;
    public static AnotherInnerClass _anotherInnerClass;

    public void foo() throws NamingException {
    }
}

record MyRecord2(){}

record MyRecord3(int x){}
