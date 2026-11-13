/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/


package com.puppycrawl.tools.checkstyle.grammar.java14;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Input for Java 14 records: constructors, static members and record as a name.
 */
public class InputJava14Records2 // ok
{
    // Basic Record declaration
    public record Car(String color, String model) {}

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

    public record Tricky(int record) {}

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

    public boolean isLoggable(LogRecord record) {
        String packageName = null;
        return record.getLoggerName().startsWith(packageName);
    }

    private static void assertEquals(Level info, Level level) {
    }

    private static void record(LogRecord... logArray) {
        for (LogRecord record : logArray) {
            record.getLevel();
        }
    }

    private static void checkRecord() {
        LogRecord record;
        record = new LogRecord(Level.ALL, "abc");
        assertEquals(Level.INFO, record.getLevel());
    }

    private static int record = 2;

    public static void main (String... args) {
        String recordString = "record";
        recordString = recordString.substring(record, 5);
        Car sedan = new Car("rec", "sedan");
        String s = UnknownRecord.UNKNOWN;
        Person.unnamed("100 Linda Ln.");
    }
}
