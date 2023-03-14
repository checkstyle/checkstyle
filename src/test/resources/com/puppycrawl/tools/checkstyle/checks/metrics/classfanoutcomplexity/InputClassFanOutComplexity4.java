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
excludeClassesRegexps =
excludedPackages = (default)


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import javax.naming.NamingException;

public class InputClassFanOutComplexity4 { // violation
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

enum InnerEnum4 {
    VALUE1;

    private InnerEnum4()
    {
        map2 = new HashMap();
    }
    private Set map1 = new HashSet();
    private Map map2;
}

class InputThrows4 { // violation

    public void get() throws NamingException, IllegalArgumentException {
        new java.lang.ref.ReferenceQueue<Integer>();
    }
}

class InputMultiDimensionalArray4 {
    public  Object[][] get() {
        return new Object[][]{};
    }
}

class InputCollectionsExt4 {
    private Collection col; // ok
    private EnumSet enumSet; // ok
    private LinkedHashMap map; // ok
    private LinkedHashSet set; // ok
}

class InputOptionals4 {
    private Optional<Long> op1; // ok
    private OptionalInt op2; // ok
    private OptionalLong op3; // ok
    private OptionalDouble op4; // ok
}

class InputStreams4 {
    private Stream s1; // ok
    private IntStream s2; // ok
    private LongStream s3; // ok
    private DoubleStream s4; // ok
}
