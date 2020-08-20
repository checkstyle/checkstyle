//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* Config:

 * max = 1
 * excludedClasses = {ArrayIndexOutOfBoundsException, ArrayList, Boolean, Byte, Character,
 *  Class, Deprecated, Deque, Double, Exception, Float, FunctionalInterface, HashMap,
 *  HashSet, IllegalArgumentException, IllegalStateException, IndexOutOfBoundsException,
 *  Integer, LinkedList, List, Long, Map, NullPointerException, Object, Override, Queue,
 *  RuntimeException, SafeVarargs, SecurityException, Set, Short, SortedMap, SortedSet,
 *  String, StringBuffer, StringBuilder, SuppressWarnings, Throwable,
 * TreeMap, TreeSet, UnsupportedOperationException, Void, boolean, byte, char, double,
 *  float, int, long, short, void}
 * excludeClassesRegexps = ^$
 * excludedPackages = {}
 */
class InputClassDataAbstractionCouplingRecords {
    Date date = new Date(); // Counted, 1
    Time time = new Time(2, 2, 2); // violation
}

record MyRecord1(boolean a, boolean b) {

    private boolean myBool() {
        Date date = new Date(); // Counted, 1
        Time time = new Time(2, 2, 2); // should be violation
        return true;
    }

}

record MyRecord2(String myString, boolean a, boolean b) {

    // in compact ctor
    public MyRecord2 {
        Date date = new Date(); // Counted, 1
        Time time = new Time(2, 2, 2); // should be violation
    }
}

record MyRecord3(int x) {

    // in ctor
    MyRecord3() {
        this(4);
        Date date = new Date(); // Counted, 1
        Time time = new Time(2, 2, 2); // should be violation

    }
}

record MyRecord4(int y) {
    private record MyRecord5(int z) {
        static Set<Integer> set = new HashSet<>(); // HashSet ignored
        static Map<String, Integer> map = new HashMap<>(); // HashMap ignored
        static Date date = new Date(); // Counted, 1
        static Time time = new Time(); // should be violation
        static Place place = new Place(); // should be violation

    }

    static class Time {
        Time(int h, int m,  int s) {

        }
        Time(){}
    }

    static class Date {
    }

    static class Place {
    }

}
