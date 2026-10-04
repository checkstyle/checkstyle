package org.checkstyle.suppressionxpathfilter.indentation.classheaderwrapopenjdk;

public class InputXpathClassHeaderWrapOpenjdkClass {
    abstract static class Nested<T>
            extends java.util.AbstractList<T> implements Comparable<Nested<T>> { // warn
    }
}
