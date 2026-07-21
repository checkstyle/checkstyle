package com.openjdk.checkstyle.test.chapterprogrammingpractices.ruleprogrammingpractices;

// violation first line 'Header mismatch'

import java.util.function.IntBinaryOperator;
import java.util.function.IntPredicate;

public class InputProgrammingPracticesParameterMutation {

    // violation below 'Assignment of parameter 'a' is not allowed.'
    IntPredicate obj = a -> ++a == 12;
    IntBinaryOperator obj2 = (int a, int b) -> {
        a++;     // violation 'Assignment of parameter 'a' is not allowed.'
        b += 12; // violation 'Assignment of parameter 'b' is not allowed.'
        return a + b;
    };

    IntPredicate obj3 = a -> {
        int b = a;
        return ++b == 12;
    };

    int methodOne(int parameter) {
        if (parameter <= 0) {
            throw new IllegalArgumentException("A positive value is expected");
        }
        // violation below 'Assignment of parameter 'parameter' is not allowed.'
        parameter -= 2;
        return parameter;
    }

    int methodTwo(int parameter) {
        if (parameter <= 0) {
            throw new IllegalArgumentException("A positive value is expected");
        }
        int local = parameter;
        local -= 2;
        return local;
    }
}
