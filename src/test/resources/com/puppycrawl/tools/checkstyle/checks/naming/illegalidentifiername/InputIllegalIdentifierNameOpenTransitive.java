/*
IllegalIdentifierName
format = (?i)^(?!(record|yield|var|permits|sealed|open|transitive)$).+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, ENUM_CONSTANT_DEF, PATTERN_VARIABLE_DEF, \
         RECORD_DEF, RECORD_COMPONENT_DEF, LAMBDA


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.naming.illegalidentifiername;

import java.util.logging.LogRecord;

public class InputIllegalIdentifierNameOpenTransitive {
    public Class<Record[]> getRecordType() {
        return Record[].class;
    }

    // violation below 'Name 'record' must match pattern'
    private static void record(LogRecord... logArray) {
        // violation below 'Name 'record' must match pattern'
        for (LogRecord record : logArray) {
            record.getLevel();
        }
    }

    class openClass {
        // violation below 'Name 'open' must match pattern'
        int open = 6;

        // violation below 'Name 'transitive' must match pattern'
        public void transitive() {

        }
    }

    enum Day {
        MON,
        TUE,
        WED,
        THU,
        FRI,
        SAT,
        SUN,
    }

    // violation below 'Name 'yield' must match pattern'
    int yield(Day day) {
        return switch (day) {
            case MON, TUE -> Math.addExact(0, 1);
            case WED -> Math.addExact(1, 1);
            case THU, SAT, FRI, SUN -> 0;
            default -> {
                yield Math.addExact(2, 1); // ok, yield statement
            }
        };
    }

    public static void main(String... args) {
        // violation below 'Name 'var' must match pattern'
        var var = 4;

        // violation below 'Name 'record' must match pattern'
        int record = 15;

        // violation below 'Name 'yield' must match pattern'
        String yield = "yield";

        // violation below 'Name 'Record' must match pattern'
        record Record
                // violation below 'Name 'transitive' must match pattern'
                (Record transitive) {
                                      // used as an identifier.
        }

        String yieldString = "yieldString"; // ok, part of another word
        record MyRecord() {
        } // ok, part of another word
        var variable = 2; // ok, part of another word

        // violation below 'Name 'Transitive' must match pattern'
        String Transitive = "record";
        Transitive = Transitive.substring(record, 20);

        record MyOtherRecord(String transitive, String yield, String...var) {
        // 3 violations above:
        //    'Name 'transitive' must match pattern'
        //    'Name 'yield' must match pattern'
        //    'Name 'var' must match pattern'
        }

        int requires = 3;

    }
}

