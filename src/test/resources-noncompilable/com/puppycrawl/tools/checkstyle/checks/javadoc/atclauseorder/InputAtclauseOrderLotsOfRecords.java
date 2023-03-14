/*
AtclauseOrder
violateExecutionOnNonTightHtml = (default)false
target = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, \
         CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF
tagOrder = @author, @version, @param, @return, @throws, @exception, @see, @since, @serial, \
           @serialField, @serialData, @deprecated


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.javadoc.atclauseorder;

import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Native;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.w3c.dom.Node;

public class InputAtclauseOrderLotsOfRecords {
    public static int getRecord() {
        return record;
    }

    // Simple Annotated Record components
    public @interface NonNull1 {
    }


    /**
     * Javadoc
     *
     */
    public record AnnotatedBinaryNode(@Native @NonNull1 Node left, @NonNull1 Node right) {
    }

    public interface Coords {
        public double x();

        public double y();
    }

    /**
     * @param r     not really a param
     * @param theta not really a param
     * @return
     */
    public record Polar(double r, double theta) implements Coords { // ok
        @Override
        public double x() {
            return r * Math.cos(theta);
        }

        /**
         * Javadoc
         */
        @Override
        public double y() {
            return r * Math.sin(theta);
        }
    }

    /**
     * Javadoc
     */
    public record Holder<T>(T t) {
    }

    public record HolderG<G>(G g) {

        /**
         * Javadoc here too
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HolderG<?> holderG = (HolderG<?>) o;
            return Objects.equals(g, holderG.g);
        }

        /**
         *
         * @return
         */
        @Override
        public int hashCode() {
            return Objects.hash(g);
        }
    }


    // Basic Record declaration
    public record Car(String color, String model) {
    }

    /**
     * @param name1 not really a param
     * @param name2 again
     * @return
     */
    public record Thing(String name1, String name2) {
        public Thing {
            Objects.requireNonNull(name1);
            Objects.requireNonNull(name2);
        }
    }

    public record ThingAnnotatedConstructor(String name1, String name2) {
        /**
         *
         * @param name1
         * @param name2
         */
        @NonNull1
        public ThingAnnotatedConstructor {
            Objects.requireNonNull(name1);
            Objects.requireNonNull(name2);
        }
    }

    /**
     * Javadoc here
     */
    public record OtherThing(String name, String address) {

        /**
         *
         * @param name
         * @param address
         */
        @Deprecated
        public OtherThing{
        }
    }

    public record Thing2(String name, String address) {
        /**
         * @param name
         * @param address
         * @deprecated
         */
        @Deprecated
        public Thing2(String name, String address) {
            this.name = name;
            this.address = address;
        }
    }

    public record Tricky(int record) {
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
        /**
         * @param units  some unit
         * @param side   some side
         * @param price  some price
         * @param sentAt some other thing
         * @param ttl    other thing
         */
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

    record NoComps() {
    }

    record Record(Record record) {
    }

    record R5(String...args) {
    }

    record R6(long l, String...args) implements java.io.Serializable {
    }

    record R7(String s1, String s2, String...args) {
    }

    record RI(int...xs) {
    }

    record RII(int x, int...xs) {
    }

    /**
     * Javadoc here
     */
    record RX(int[]xs) {
    }

    private static int record = 2;

    public Class<Record[]> getRecordType() {
        return Record[].class;
    }

    class LocalRecordHelper {
        Class<?> m(int x) {
            record R76(int x) {
            }
            return R.class;
        }

        private class R {
            public R(int x) {
            }
        }
    }

    /**
     * @deprecated
     */
    @Deprecated
            record R1() implements Serializable {
        private static final TimeUnit Path = null;
        private static final long serialVersionUID = -2911897846173867769L;

        public R1 {

        }
    }

    record RR3(String...args) implements Serializable {
        private static final boolean firstDataSetCreated = false;
        private static final long serialVersionUID = -5626758281412733319L;

        public RR3 {
            if (firstDataSetCreated) {
                ProcessHandle.current();
            }
        }
    }

    public static void main(String... args) {
        String recordString = "record";
        recordString = recordString.substring(record, 5);
        Car sedan = new Car("rec", "sedan");
        String s = UnknownRecord.UNKNOWN;
        Person.unnamed("100 Linda Ln.");
    }
}
