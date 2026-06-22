/*
UnusedLocalVariable
allowUnnamedVariables = false
allowNamedPatternVariables = false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariablePatternVariablesCondition2 {

    record ColoredPoint(int x, int y, String color) {}
    record Rectangle(ColoredPoint upperLeft, ColoredPoint lowerRight) {}

    public void patternVariableNegatedNestedRecordUsedInElse(Object o) {
        if (!(o instanceof Rectangle(ColoredPoint ul, ColoredPoint lr))) {
            System.out.println("not a rectangle");
        } else {
            System.out.println(ul + "" + lr);
        }
    }

    public void patternVariableNegatedNestedRecordOneUnused(Object o) {
        if (!(o instanceof Rectangle(ColoredPoint ul, ColoredPoint lr))) { // violation, 'lr'
            System.out.println("not a rectangle");
        } else {
            System.out.println(ul);
        }
    }

    public void patternVariableUsedAfterNestedBlock(Object o) {
        String result = null;
        if (o instanceof String s) {
            if (s.length() > 5) {
                result = s.substring(0, 5);
            } else {
                result = s;
            }
        }
        System.out.println(result);
    }

    public void patternVariableUnusedWithNestedBlock(Object o) {
        if (o instanceof String s) { // violation, unused local variable 's'
            System.out.println("it is a string");
            if (o.hashCode() > 0) {
                System.out.println("positive hash");
            }
        }
    }

    public void patternVariableNegatedWithOr(Object o) {
        if (!(o instanceof String s) || s.isEmpty()) {
            System.out.println("not a non-empty string");
        }
    }

    public void patternVariableMixedUsage(Object o1, Object o2) {
        if (o1 instanceof String s1) { // violation, unused local variable 's1'
            System.out.println("string 1");
        }
        if (o2 instanceof Integer i2) {
            System.out.println(i2);
        }
    }

    public void patternVariableUsedInNestedInnerBlock(Object o) {
        if (!(o instanceof String s)) {
            System.out.println("not a string");
        } else {
            if (s.length() > 0) {
                System.out.println(s);
            }
        }
    }
}
