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

public class InputAtclauseOrderLotsOfRecords3 {

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
}
