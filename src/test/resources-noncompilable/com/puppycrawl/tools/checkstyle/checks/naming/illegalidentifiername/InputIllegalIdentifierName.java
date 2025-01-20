/*
IllegalIdentifierName
format = (default)^(?!var$|.*\\$).+
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, ENUM_CONSTANT_DEF, PATTERN_VARIABLE_DEF, \
         RECORD_DEF, RECORD_COMPONENT_DEF, LAMBDA


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.naming.illegalidentifiername;

import java.util.logging.LogRecord;

public class InputIllegalIdentifierName {
    public Class<Record[]> getRecordType() {
        return Record[].class;
    }

    private static void record(LogRecord... logArray) {
        for (LogRecord record : logArray) {
            record.getLevel();
        }
    }

    class yieldClass {
        int yield = 6;

        public void yield() {

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

    int yield(Day day) {
        return switch (day) {
            case MON, TUE -> Math.addExact(0, 1);
            case WED -> Math.addExact(1, 1);
            case THU, SAT, FRI, SUN -> 0;
            default -> {
                yield Math.addExact(2, 1);
            }
        };
    }

    public static void main(String... args) {
        var var = 4; // violation, 'Name 'var' must match pattern'

        int $amt = 15; // violation, 'must match pattern'

        String yield = "yield";

        record Record
                (Record record) {
        }

        String yieldString = "yield$String"; // ok, part of another word
        record MyRecord() {}
        var variable = 2; // ok, part of another word

        String recordString = "record";
        recordString = recordString.substring($amt, 20);

        record MyOtherRecord(String record, String yield$text, String... var) {
        // 2 violations above:
        //            'must match pattern'
        //            'Name 'var' must match pattern'
        }
    }
}

