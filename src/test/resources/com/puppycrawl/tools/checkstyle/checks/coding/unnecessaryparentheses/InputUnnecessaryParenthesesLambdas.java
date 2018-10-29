package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.Function;

public class InputUnnecessaryParenthesesLambdas {
    int foo(int y) {
        MathOperation case1 = (x) -> x + x;
        MathOperation case2 = (x) -> { return x + x; };
        MathOperation case3 = (int x) -> x + x;
        MathOperation case4 = x -> x + x;
        MathOperation2 case5 = (a, b) -> a + b;
        MathOperation2 case6 = (int a, int b) -> a + b;
        MathOperation2 case7 = (int a, int b) -> { return a + b; };
        Objects.requireNonNull(null, () -> "message");
        call((x) -> x + x);
        new HashSet<Integer>().stream().filter((filter) -> filter > 0);
        return y;
    }

    static <T> CheckedFunction1<T, T> identity() {
        return t -> t;
    }

    public interface CheckedFunction2<T1, T2, R> extends Lambda<R> {
        R apply(T1 t1, T2 t2) throws Throwable;

        default CheckedFunction1<T2, R> apply(T1 t1) {
            return (T2 t2) -> apply(t1, t2);
        }
        @Override
        default Function1<T1, CheckedFunction1<T2, R>> curried() {
                    return t1 -> t2 -> apply(t1, t2);
        }
        default Function1<T1, CheckedFunction1<T2, R>> curried2() {
            return (t1) -> (t2) -> apply(t1, t2);
        }
        default Function1<T1, CheckedFunction1<T2, R>> curried3() {
            return (t1) -> t2 -> apply(t1, t2);
        }
        default Function1<T1, CheckedFunction1<T2, R>> curried4() {
            return t1 -> (t2) -> apply(t1, t2);
        }
    }

    private void call(MathOperation o) {
        o.operation(1);
    }

    interface MathOperation {
        int operation(int a);
    }

    interface MathOperation2 {
        int operation(int a, int b);
    }

    interface Lambda<R> extends Serializable {
        Lambda<?> curried();
    }

    public interface Function1<T1, R> extends Lambda<R>, Function<T1, R> {
        @Override
        default Function1<T1, R> curried() {
            return this;
        }
    }

    public interface CheckedFunction1<T1, R> extends Lambda<R> {
        R apply(T1 t1) throws Throwable;

        @Override
        default CheckedFunction1<T1, R> curried() {
            return this;
        }
    }
}
