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
        switch(day) { // violation
            case SAT, SUN:
                today = "Weekend day";
                break;
            default:
                throw new IllegalArgumentException("Invalid day: " + day.name());
        }
        return today;
    }

    String whatIsToday2(Day day) {
        var today = "";
        switch(day) { // violation
            case MON, TUS, WED, THU, FRI -> today = "Working day";
            default ->throw new IllegalArgumentException(); // violation
        }
        return today;
    }

    void switchWithPatternMatching() {
        Object o = "";
        switch(o) { // violation
            case String s ->s.trim(); // violation
            case Integer a ->a.intValue(); // violation
            default -> throw new IllegalArgumentException(); // ok
        }
    }

    void normalSwitch() {
        final int a = 12;
        switch(a) { // violation
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
