/*
ReturnCount
max = (default)2
maxForVoid = (default)1
format = (default)^equals$
tokens = METHOD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.returncount;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Supplier;


public class InputReturnCountLambda3 {

    Runnable fieldWithOneReturnInLambda = () -> {
        return;
    };

    Callable<Integer> fieldWithTwoReturnInLambda = () -> {
        if (hashCode() == 0) return 0;
        else return 1;
    };

    Optional<Integer> methodWithOneReturnInLambda() {
        return Optional.of(hashCode()).filter(i -> {
            return i > 0;
        });
    }

    Optional<Integer> methodWithTwoReturnInLambda() { // violation 'Return count is 3'
        return Optional.of(hashCode()).filter(i -> {
            if (i > 0) return true;
            else return false;
        });
    }

    Optional<Object> methodWithThreeReturnInLambda(int number) { // violation 'Return count is 4'
        return Optional.of(number).map(i -> {
            if (i == 42) return true;
            else if (i == 7) return true;
            else return false;
        });
    }

    int methodWithTwoReturnWithLambdas(final int number) { // violation 'Return count is 4'
        if (hashCode() > 0) {
            new Thread(
                () -> {
                }
            ).start();
            return number;
        } else {
            return Optional.of(hashCode()).orElseGet(() -> {
                if (number > 0) return number;
                else return 0;
            });
        }
    }

    Supplier<Supplier<Integer>> methodWithOneReturnPerLambda() { // violation 'Return count is 3'
        return () -> {
            return () -> {
                return 1;
            };
        };
    }
}
