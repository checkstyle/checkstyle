/*
DuplicateElseIfCondition


*/

package com.puppycrawl.tools.checkstyle.checks.coding.duplicateelseifcondition;

public class InputDuplicateElseIfConditionIfExpressions {
    void logicalAnd(int a, int b) {
        if (a > 0 && b > 0) {
        } else if (a > 0 && b > 0) {
            // violation above 'Duplicate condition, identical to the condition at line '11'.'
        }
    }

    void logicalOr(int a, int b) {
        if (a == 0 || b == 0) {
        } else if (a == 0 || b == 0) {
            // violation above 'Duplicate condition, identical to the condition at line '18'.'
        }
    }

    void logicalNot(boolean flag) {
        if (!flag) {
        } else if (!flag) {
            // violation above 'Duplicate condition, identical to the condition at line '25'.'
        }
    }

    void binaryArithmetic(int x) {
        if (x + 1 > 5) {
        } else if (x + 1 > 5) {
            // violation above 'Duplicate condition, identical to the condition at line '32'.'
        }
    }

    void ternaryOperator(int a, int b) {
        if ((a > 0 ? a : b) == 1) {
        } else if ((a > 0 ? a : b) == 1) {
            // violation above 'Duplicate condition, identical to the condition at line '39'.'
        }
    }

    void instanceofPattern(Object obj) {
        if (obj instanceof String s) {
        } else if (obj instanceof String s) {
            // violation above 'Duplicate condition, identical to the condition at line '46'.'
        }
    }

    void commutativityNotNormalized(int a, int b) {
        if (a + b > 0) {
        } else if (b + a > 0) {
        }
    }

    void logicalCommutativityNotNormalized(boolean a, boolean b) {
        if (a && b) {
        } else if (b && a) {
        }
    }

    void differentIdentText(int a, int b) {
        if (a > 0) {
        } else if (b > 0) {
        }
    }

    void differentTypeOrLiteral(int a) {
        if (a > 0) {
        } else if (a > 1) {
        } else if (a < 0) {
        }
    }

    void methodDifferentArgs(int a, int b) {
        if (compute(a)) {
        } else if (compute(a, b)) {
        } else if (compute(a)) {
            // violation above 'Duplicate condition, identical to the condition at line '78'.'
        }

        if (compute(a, b)) {
        } else if (compute(a)) {
        } else if (compute(a)) {
            // violation above 'Duplicate condition, identical to the condition at line '85'.'
        }
    }

    void prefixVsPostfixIncrement(int a) {
        if (++a > 0) {
        } else if (a++ > 0) {
        }
    }

    void prefixVsPostfixDecrement(int a) {
        if (--a > 0) {
        } else if (a-- > 0) {
        }
    }

    boolean compute(int x) { return true; }
    boolean compute(int x, int y) { return true; }
}
