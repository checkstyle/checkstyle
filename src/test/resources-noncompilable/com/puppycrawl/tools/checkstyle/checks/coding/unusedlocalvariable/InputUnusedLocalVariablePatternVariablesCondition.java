/*
UnusedLocalVariable
allowUnnamedVariables = false
jdkVersion = (default)22

*/
// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariablePatternVariablesCondition {

    sealed abstract static class Ball permits RedBall, GreenBall {}
    static final class RedBall   extends Ball {}
    static final class GreenBall extends Ball {}
    record Box<T extends Ball>(T content) {}
    record ColoredPoint(int x, int y, String color) {}
    record Rectangle(ColoredPoint upperLeft, ColoredPoint lowerRight) {}

    public void patternVariableConditionalStatements(Ball ball) {
        if (ball instanceof RedBall redBall) { // violation, unused local variable 'redBall'
            process(ball);
        } else if (ball instanceof GreenBall greenBall) {
            stopProcessing(ball); // violation above, unused local variable 'greenBall'
        }
        if (ball instanceof RedBall _) {
            process(ball);
        } else if (ball instanceof GreenBall _) {
            stopProcessing(ball);
        }
    }

    public void patternVariableNegatedInstanceof(Ball ball) {
        if (!(ball instanceof RedBall redBall)) {
            process(ball);
        } else {
            process(redBall);
        }
    }

    public void patternVariableNegatedInstanceofUnused(Ball ball) {
        if (!(ball instanceof RedBall redBall)) { // violation, unused local variable 'redBall'
            process(ball);
        }
    }

    public void patternVariableNegatedInstanceofRecord(Box<? extends Ball> box) {
        if (!(box instanceof Box(RedBall redBall))) {
            process(box);
        } else {
            process(redBall);
        }
    }

    public void patternVariableNegatedInstanceofRecordUnused(Box<? extends Ball> box) {
        if (!(box instanceof Box(RedBall redBall))) { // violation, unused local variable 'redBall'
            process(box);
        }
    }

    public void patternVariableInRecordPatternConditional(Box<? extends Ball> box) {
        if (box instanceof Box(RedBall redBall)) { // violation, unused local variable 'redBall'
            process(box);
        } else if (box instanceof Box(GreenBall greenBall)) {
            stopProcessing(box); // violation above, unused local variable 'greenBall'
        }
        if (box instanceof Box(RedBall _)) {
            process(box);
        } else if (box instanceof Box(GreenBall _)) {
            stopProcessing(box);
        }
    }

    public void patternVariableInRecordPatternUsed(Box<? extends Ball> box) {
        if (box instanceof Box(RedBall redBall)) {
            process(redBall);
        }
    }

    public void patternVariableNestedRecordPatterns(Object o) {
        if (o instanceof Rectangle(ColoredPoint(int p, int x, String c),
                ColoredPoint lr)) { // violation, unused local variable 'lr'
            System.out.println(p + "" + x + c);
        }
        if (o instanceof Rectangle(ColoredPoint(int p, int x, String c), ColoredPoint _)) {
            System.out.println(p + "" + x + c);
        }
        if (o instanceof Rectangle(ColoredPoint(int p, int x, String c), _)) {
            System.out.println(p + "" + x + c);
        }
    }

    void process(Object o) {}
    void stopProcessing(Object o) {}
}
