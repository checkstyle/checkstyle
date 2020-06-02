//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.grammar.java14;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.w3c.dom.Node;

/**
 * Input for Java 14 records.
 */
public class InputJava14Records
{
    // Simple Annotated Record components
    public @interface NonNull1 {}
    public record AnnotatedBinaryNode(@NonNull1 Node left, @NonNull1 Node right) { }

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

    // Basic Record declaration
    public record Car(String color, String model) {}

    // Record declaration with body and no-arg constructor
    public record Thing(String name1, String name2) {
        public Thing {
            Objects.requireNonNull(name1);
            Objects.requireNonNull(name2);
        }
    }

    // Various other usages of records
    public record OtherThing(String name, String address) {
        public OtherThing(String name) {
            this(name, "Unknown");
        }
    }

    public record Thing2(String name, String address) {
        public Thing2(String name, String address) {
            this.name = name;
            this.address = address;
        }
    }

    // Records with static variable member
    public record UnknownRecord(String known, String unknown) {
        private static final String UNKNOWN = "Unknown";
    }

    public record Person(String name, String address) {
        public static Person unnamed(String address) {
            return new Person("Unnamed", address);
        }
    }

    // Record definition with throws constructor
    public record FXOrder(int units,
                          String side,
                          double price,
                          LocalDateTime sentAt,
                          int ttl) {
        public FXOrder {
            if (units < 1) {
                throw new IllegalArgumentException(
                        "FXOrder units must be positive");
            }
            if (ttl < 0) {
                throw new IllegalArgumentException(
                        "FXOrder TTL must be positive, or 0 for market orders");
            }
            if (price <= 0.0) {
                throw new IllegalArgumentException(
                        "FXOrder price must be positive");
            }
        }
    }

    public static void main (String... args) {
        Car sedan = new Car("rec", "sedan");
        String s = UnknownRecord.UNKNOWN;
        Person.unnamed("100 Linda Ln.");
        LogRecord record;
        record = new LogRecord(Level.ALL, "abc");
    }
}
