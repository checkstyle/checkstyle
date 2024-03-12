/*
NoWhitespaceBeforeCaseDefaultColon


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebeforecasedefaultcolon;

class InputNoWhitespaceBeforeCaseDefaultColonEnumAndStrings {

    enum Day {
        MON,
        TUE,
        WED,
        THU,
        FRI,
        SAT,
        SUN,
    }

    String whatIsToday(Day day) {
        return switch (day) {
            case SAT, SUN -> "Weekend";
            case MON, TUE, WED, THU, FRI -> "Working day";
            default -> throw new IllegalArgumentException("Invalid day: " + day.name());
        };
    }

    int assignment(Day day) {
        int numLetters = -1;
        switch (day) {
            case MON,
                    FRI: numLetters = 6;
                            break;
            case TUE : numLetters = 7; // violation
                       break;
            case THU, SAT
                    : numLetters = 8; // violation
                         break;
            case WED,
                    SUN : numLetters = 9; // violation
                          break;
        };
        return numLetters;
    }

    int complexCase(Day day) {
        int someInt = -1;
        return switch (day) {
            case MON,
                    TUE: {
                int l = day.toString().length();
                yield l + someInt;
            }
            case WED: {
                int l = day.toString().length();
                yield l + someInt * 2;
            }
            default
                   : { // violation
                int l = day.toString().length();
                yield l;
            }
        };
    }

    int numberOfDays(String month) {
        int days = -1;
        switch (month) {
            case "FEB": days = 28;
                        break;
            case "JAN", "MAR"
                       , "MAY" : days = 31; break; // violation
            case "APR", "JUN": days = 30;
                                break;
        };
        return days;
    }

    enum T {
        A, B, C;
    }

    static {
        int x;
        T e = T.B;

        boolean t8 = (switch (e) {
            case A
                : // violation
                x = 1;
                yield true;
            case B : // violation
                yield (x = 1) == 1 || true;
            default
                    :yield false; // violation
        }) && x == 1;

        if (!t8) {
            throw new IllegalStateException("Unexpected result.");
        }
    }
}
