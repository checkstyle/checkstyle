/*
ReturnCount
max = 1
maxForVoid = (default)1
format = (default)^equals$
tokens = (default)CTOR_DEF, METHOD_DEF, LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.coding.returncount;

import java.lang.Integer;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Supplier;


public class InputReturnCountLambda {

    Runnable fieldWithOneReturnInLambda = () -> {
        return;
    };

    Callable<Integer> fieldWithTwoReturnInLambda = () -> { // violation 'Return count is 2'
        if (hashCode() == 0) return 0;
        else return 1;
    };

    Optional<Integer> methodWithOneReturnInLambda() {
        return Optional.of(hashCode()).filter(i -> {
            return i > 0;
        });
    }

    Optional<Integer> methodWithTwoReturnInLambda() {
        return Optional.of(hashCode()).filter(i -> { // violation 'Return count is 2'
            if (i > 0) return true;
            else return false;
        });
    }

    Optional<Object> methodWithThreeReturnInLambda(int number) {
        return Optional.of(number).map(i -> { // violation 'Return count is 3'
            if (i == 42) return true;
            else if (i == 7) return true;
            else return false;
        });
    }

    int methodWithTwoReturnWithLambdas(final int number) { // violation 'Return count is 2'
        if (hashCode() > 0) {
            new Thread(
                () -> {
                }
            ).start();
            return number;
        } else {
            return Optional.of(hashCode()).orElseGet(() -> { // violation 'Return count is 2'
                if (number > 0) return number;
                else return 0;
            });
        }
    }

    Supplier<Supplier<Integer>> methodWithOneReturnPerLambda() {
        return () -> {
            return () -> {
                return 1;
            };
        };
    }
}
