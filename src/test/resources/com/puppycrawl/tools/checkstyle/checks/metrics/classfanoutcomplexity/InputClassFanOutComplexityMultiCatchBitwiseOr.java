/*
ClassFanOutComplexity
max = 4
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class InputClassFanOutComplexityMultiCatchBitwiseOr { // violation 'Complexity is 5'
    public static void main(String[] args) {
        try {
            System.out.println(args[7]);
            File myFile = new File("myfile.txt");
            InputStream stream = myFile.toURL().openStream();
            foo1();
        } catch (ArrayIndexOutOfBoundsException | IOException
                | SQLException | SecurityException | OneMoreException e) {

        }
    }

    private static void foo1() throws RuntimeException, SQLException, OneMoreException {

    }

    private class SQLException extends Exception {

    }

    private class OneMoreException extends Exception {

    }
}
