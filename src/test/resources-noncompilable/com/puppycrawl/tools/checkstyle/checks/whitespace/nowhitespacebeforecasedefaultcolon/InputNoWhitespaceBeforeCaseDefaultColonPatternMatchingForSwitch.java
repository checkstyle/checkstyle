/*
NoWhitespaceBeforeCaseDefaultColon


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebeforecasedefaultcolon;

public class InputNoWhitespaceBeforeCaseDefaultColonPatternMatchingForSwitch {

    void test(Object obj) {
        switch (obj) {
            case ColoredPoint(int a, int b, _) when (a >= b) : {} break; // violation
            case ColoredPoint(int a, int b, _) when (b > 100)
                    : {} break; // violation
            case ColoredPoint(int a, int b, _) when (b != 1000)
                    :  // violation
            {} break;
            case ColoredPoint(int a, int b, _) when (b != 100000) : // violation
            {} break;
            case
                    Rectangle(_,_) :  // violation
            {}
            default : System.out.println("none"); // violation
        }
    }

    void test2(Object obj) {
        switch (obj) {
            case ColoredPoint(int a, int b, _) when (a >= b): {} break;
            case ColoredPoint(int a, int b, _) when (b > 100): {} break;
            case ColoredPoint(int a, int b, _) when (b != 1000): {} break;
            case ColoredPoint(int a, int b, _) when (b != 100000): {} break;
            case Rectangle(_, _): {}
            default: System.out.println("none");
        }
    }

    record ColoredPoint(int p, int x, boolean c) { }
    record Rectangle(ColoredPoint upperLeft, ColoredPoint lowerRight) { }
}
