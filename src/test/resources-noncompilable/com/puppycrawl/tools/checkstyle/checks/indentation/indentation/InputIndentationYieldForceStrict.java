//non-compiled with javac: Compilable with Java17                                   //indent:0 exp:0

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;             //indent:0 exp:0

/* Config:                                                                          //indent:0 exp:0
 * forceStrictCondition = true                                                     //indent:1 exp:1
 */                                                                                 //indent:1 exp:1

class InputIndentationYieldForceStrict { //indent:0 exp:0
    public static void main(final String[] args) { //indent:4 exp:4
        final int today = 0; //indent:8 exp:8
        final boolean isWeekDay = switch (today) { //indent:8 exp:8
            case 2 -> { //indent:12 exp:12
            System.out.println("Monday"); //indent:12 exp:16 warn
            yield true; //indent:12 exp:16 warn
            } //indent:12 exp:12
            case 3 -> { //indent:12 exp:12
                System.out.println("Tuesday"); //indent:16 exp:16
                yield true; //indent:16 exp:16
            } //indent:12 exp:12
            default -> { //indent:12 exp:12
                yield true; //indent:16 exp:16
            } //indent:12 exp:12
        }; //indent:8 exp:8

        final boolean isWeekend = switch (today) { //indent:8 exp:8
            case 0: //indent:12 exp:12
            System.out.println("Saturday"); //indent:12 exp:16 warn
            yield true; //indent:12 exp:16 warn
            case 1: //indent:12 exp:12
                System.out.println("Sunday"); //indent:16 exp:16
                yield true; //indent:16 exp:16
            default: //indent:12 exp:12
    yield true; //indent:4 exp:16 warn
        }; //indent:8 exp:8
    } //indent:4 exp:4

    public void foo(int k) { //indent:4 exp:4
        return switch (k) { //indent:8 exp:8
            case 1 -> { //indent:12 exp:12
                yield false; //indent:16 exp:16
            } //indent:12 exp:12
            case 2 -> { //indent:12 exp:12
              yield true; //indent:14 exp:16 warn
            } //indent:12 exp:12
            case 3 -> { //indent:12 exp:12
                   yield true; //indent:19 exp:16 warn
            } //indent:12 exp:12
            default -> { //indent:12 exp:12
        yield false; //indent:8 exp:16 warn
            } //indent:12 exp:12
        }; //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0
