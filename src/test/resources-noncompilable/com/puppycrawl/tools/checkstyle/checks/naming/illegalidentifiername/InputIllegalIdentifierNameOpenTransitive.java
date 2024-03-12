/*
IllegalIdentifierName
format = (?i)^(?!(record|yield|var|permits|sealed|open|transitive)$).+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, ENUM_CONSTANT_DEF, PATTERN_VARIABLE_DEF, \
         RECORD_DEF, RECORD_COMPONENT_DEF, LAMBDA


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.naming.illegalidentifiername;

import java.util.logging.LogRecord;

public class InputIllegalIdentifierNameOpenTransitive {
    public Class<Record[]> getRecordType() {
        return Record[].class;
    }

    private static void record(LogRecord... logArray) { // violation
        for (LogRecord record : logArray) { // violation
            record.getLevel();
        }
    }

    class openClass {
        int open = 6; // violation

        public void transitive() { // violation

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

    int yield(Day day) { // violation
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
        var var = 4; // violation

        int record = 15; // violation

        String yield = "yield"; // violation

        record Record // violation
                (Record transitive) { // violation
                                      // used as an identifier.
        }

        String yieldString = "yieldString"; // ok, part of another word
        record MyRecord() {
        } // ok, part of another word
        var variable = 2; // ok, part of another word

        String Transitive = "record"; // violation
        Transitive = Transitive.substring(record, 20);

        record MyOtherRecord(String transitive, String yield, String...var) { // 3 violations
        }

        int requires = 3;

    }
}

