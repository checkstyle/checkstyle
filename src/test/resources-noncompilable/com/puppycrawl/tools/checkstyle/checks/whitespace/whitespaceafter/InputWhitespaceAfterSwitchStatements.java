/*
WhitespaceAfter
tokens = LITERAL_SWITCH, LAMBDA

*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterSwitchStatements {
    enum Day {
        NONE, SAT, SUN, MON,
        TUS, WED, THU, FRI
    }

    String whatIsToday(Day day) {
        String today;
        switch(day) { // violation ''switch' is not followed by whitespace'
            case SAT, SUN:
                today = "Weekend day";
                break;
            default:
                throw new IllegalArgumentException("Invalid day: " + day.name());
        }
        return today;
    }

    String whatIsToday2(Day day) {
        IllegalArgumentException exc = new IllegalArgumentException();
        var today = "";
        switch(day) { // violation ''switch' is not followed by whitespace'
            case MON, TUS, WED, THU, FRI -> today = "Working day";
            default ->throw exc; // violation ''->' is not followed by whitespace'
        }
        return today;
    }

    void switchWithPatternMatching() {
        Object o = "";
        switch(o) { // violation ''switch' is not followed by whitespace'
            case String s ->s.trim(); // violation ''->' is not followed by whitespace'
            case Integer a ->a.intValue(); // violation ''->' is not followed by whitespace'
            default -> throw new IllegalArgumentException(); // ok
        }
    }

    void normalSwitch() {
        final int a = 12;
        switch(a) { // violation ''switch' is not followed by whitespace'
            case 1:
                break;
            case 2:
            case 3:
                break;
            default:
                break;
        }
    }
}
