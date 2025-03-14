/*
MagicNumber
ignoreNumbers = -2, -1, 0, 1, 2, 100
ignoreHashCodeMethod = true
ignoreFieldDeclaration = true
constantWaiverParentToken = ARRAY_INIT, ASSIGN, ELIST, EXPR

*/

package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

import java.util.concurrent.Callable;
import java.util.function.BiFunction;

public class InputMagicNumberIgnoreFieldDeclaration4 {
    public final int radius = 10;
    public final double area = 22 / 7.0 * radius * radius;
    public final int a[] = {4, 5};

    public int x = 10;
    public int y = 10 * 20;
    public int[] z = {4, 5};

    private static final Callable<Void> SLEEP_FOR_A_DAY = () -> {
        Thread.sleep(86400_000);
        return null;
    };
    private static final BiFunction<Integer, Integer, Integer> ADD_AND_SQUARE = (a, b) -> {
        int sum = a + b + 5; // violation ''5' is a magic number'
        return sum * sum * 69;
    };

    private static final Callable<Void> SLEEP_FOR_A_DAY_EXP = new Callable<Void>() {
        @Override
        public Void call() throws InterruptedException {
            Thread.sleep(86400_000); // violation ''86400_000' is a magic number'
            return null;
        }
    };

    private static final BiFunction<Integer, Integer, Integer>
            ADD_AND_SQUARE_EXP = new BiFunction<Integer, Integer, Integer>() {
        @Override
        public Integer apply(Integer a, Integer b) {
            int sum = a + b + 5; // violation ''5' is a magic number'
            return sum * sum * 69; // violation ''69' is a magic number'
        }
    };

    private final Callable<Void> SLEEP_FOR_A_DAY_NS = () -> {
        Thread.sleep(86400_000);
        return null;
    };
    private final BiFunction<Integer, Integer, Integer> ADD_AND_SQUARE_NS = (a, b) -> {
        int sum = a + b + 5; // violation ''5' is a magic number'
        return sum * sum * 69;
    };

    private final Callable<Void> SLEEP_FOR_A_DAY_EXP_NS = new Callable<Void>() {
        @Override
        public Void call() throws InterruptedException {
            Thread.sleep(86400_000); // violation ''86400_000' is a magic number'
            return null;
        }
    };

    private final BiFunction<Integer, Integer, Integer>
            ADD_AND_SQUARE_EXP_NS = new BiFunction<Integer, Integer, Integer>() {
        @Override
        public Integer apply(Integer a, Integer b) {
            int sum = a + b + 5; // violation ''5' is a magic number'
            return sum * sum * 69; // violation ''69' is a magic number'
        }
    };

}