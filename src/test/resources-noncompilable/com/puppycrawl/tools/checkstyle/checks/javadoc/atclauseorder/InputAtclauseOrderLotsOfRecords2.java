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

public class InputAtclauseOrderLotsOfRecords2 {

    public @interface NonNull1 {
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

    private static int record = 2;
    public static void main(String... args) {
        String recordString = "record";
        recordString = recordString.substring(record, 5);
        Car sedan = new Car("rec", "sedan");
        String s = UnknownRecord.UNKNOWN;
        Person.unnamed("100 Linda Ln.");
    }
}
