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

package com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity;

import javax.naming.*;
import java.util.*;
import java.util.stream.*;

public class InputClassFanOutComplexity { // violation
    private class InnerClass { //singleline comment
        public List _list = new ArrayList();
    }

    private class AnotherInnerClass {
        public String _string = "";
    }

    public Set _set = /*block comment*/new HashSet();
    public Map _map = new HashMap();
    public String _string = "";
    public int[] _intArray = new int[0];
    public InnerClass _innerClass = new InnerClass();
    public AnotherInnerClass _anotherInnerClass = new AnotherInnerClass();

    public void foo() throws NamingException {
    }

}

enum InnerEnum {
    VALUE1;

    private InnerEnum()
    {
        map2 = new HashMap();
    }
    private Set map1 = new HashSet();
    private Map map2;
}

class InputThrows { // violation

    public void get() throws NamingException, IllegalArgumentException {
        new java.lang.ref.ReferenceQueue<Integer>();
    }
}

class InputMultiDimensionalArray {
    public  Object[][] get() {
        return new Object[][]{};
    }
}

class InputCollectionsExt {
    private Collection col;
    private EnumSet enumSet;
    private LinkedHashMap map;
    private LinkedHashSet set;
}

class InputOptionals {
    private Optional<Long> op1;
    private OptionalInt op2;
    private OptionalLong op3;
    private OptionalDouble op4;
}

class InputStreams {
    private Stream s1;
    private IntStream s2;
    private LongStream s3;
    private DoubleStream s4;
}
