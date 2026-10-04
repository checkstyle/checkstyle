/*
ClassHeaderWrapOpenjdk


*/
package org.checkstyle.suppressionxpathfilter.indentation.classheaderwrapopenjdk;

public class InputXpathClassHeaderWrapOpenjdkClass {
    abstract static class Nested<T>
            extends java.util.AbstractList<T> implements Comparable<Nested<T>> {
        // violation above """The 'implements' clause should be on a new line when the
        // class header is wrapped."""
    }
}
