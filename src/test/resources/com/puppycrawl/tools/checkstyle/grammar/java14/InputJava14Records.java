/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/


package com.puppycrawl.tools.checkstyle.grammar.java14;

import java.lang.annotation.Native;
import java.util.Objects;

import org.w3c.dom.Node;

/**
 * Input for Java 14 records.
 */
public class InputJava14Records // ok
{
    public static int getRecord() {
        return record;
    }

    public static void setRecord(int record) {
        InputJava14Records.record = record;
    }

    // Simple Annotated Record components
    public @interface NonNull1 {}
    public record AnnotatedBinaryNode(@Native @NonNull1 Node left, @NonNull1 Node right) { }

    public interface Coords {
        public double x();
        public double y();
    }

    //Implements interface
    public record Polar(double r, double theta) implements Coords {
        @Override
        public double x() {
            return r * Math.cos(theta);
        }

        @Override
        public double y() {
            return r * Math.sin(theta);
        }
    }

    // Generic record
    public record Holder<T>(T t) { }

    public record HolderG<G>(G g) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HolderG<?> holderG = (HolderG<?>) o;
            return Objects.equals(g, holderG.g);
        }

        @Override
        public int hashCode() {
            return Objects.hash(g);
        }
    }

    // Record declaration with body and no-arg constructor
    public record Thing(String name1, String name2) {
        public Thing {
            Objects.requireNonNull(name1);
            Objects.requireNonNull(name2);
        }
    }

    public record ThingAnnotatedConstructor(String name1, String name2) {
        @NonNull1
        public ThingAnnotatedConstructor {
            Objects.requireNonNull(name1);
            Objects.requireNonNull(name2);
        }
    }

    private static int record = 2;
}
