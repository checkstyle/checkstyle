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

    public void returnKeyword(int k) { //indent:4 exp:4
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

    public void switchNotonStartOfTheLine () { //indent:4 exp:4
        Integer i = 0; //indent:8 exp:8
        if (!switch (i) { //indent:8 exp:8
            default: //indent:12 exp:12
                if (!("o1" instanceof String str)) { //indent:16 exp:16
                    yield false; //indent:20 exp:20
                } //indent:16 exp:16
                if (str.isEmpty()) { //indent:16 exp:16
                    yield true; //indent:20 exp:20
                } //indent:16 exp:16
                yield true; //indent:16 exp:16
        }) { //indent:8 exp:8
            System.out.println("throw"); //indent:12 exp:12
        } //indent:8 exp:8
    } //indent:4 exp:4

    int correctSwitchExpr(Object inT, int x) { //indent:4 exp:4
        if (x == 1) { //indent:8 exp:8
            return switch (inT) { //indent:12 exp:12
                case Integer i -> 1; //indent:16 exp:16
                case String s -> 2; //indent:16 exp:16
                default -> 3; //indent:16 exp:16
            }; //indent:12 exp:12
        } //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0
