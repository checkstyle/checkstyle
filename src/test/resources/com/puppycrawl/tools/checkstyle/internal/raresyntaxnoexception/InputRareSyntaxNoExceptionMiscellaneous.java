package com.puppycrawl.tools.checkstyle.internal.raresyntaxnoexception;

import static java.lang.Math.PI;

import java.util.ArrayList;
import java.util.List;

/* Block comment covering block comment tokens. */
public abstract class InputRareSyntaxNoExceptionMiscellaneous {
    private boolean flag = true;
    private byte small = 1;
    private char letter = 'a';
    private short tiny = 2;
    private int count = 3;
    private long big = 4L;
    private float ratio = 1.5f;
    private double precise = 2.5;
    private double explicit = 2.5d;
    private transient String cache;
    private volatile boolean ready;
    protected String protectedField = "protected";

    protected abstract String describe();

    public native void nativeOperation();

    public strictfp double area(double radius) {
        return PI * radius * radius;
    }

    public synchronized void guarded() {
        count = count + 1;
    }

    @SuppressWarnings(value = "unused")
    public int operators(int left, int right) {
        int result = left;
        result += right;
        result -= 1;
        result *= 2;
        result /= 2;
        result %= 3;
        result <<= 1;
        result >>= 1;
        result >>>= 1;
        result &= 7;
        result ^= 3;
        result |= 1;
        boolean check = left > 0 && right < 10 || left == right;
        check = check & true | false ^ true;
        int shifted = left << 2 | right >> 1;
        int unsigned = left >>> 1;
        int remainder = left % right;
        int quotient = left / right;
        int difference = left - right;
        int negated = -left;
        int positive = +right;
        int grown = left++;
        int shrunk = --right;
        int inverted = ~left;
        boolean flipped = !check;
        boolean equal = left != right;
        boolean less = left <= right;
        String text = check ? "yes" : "no";
        if (check) {
            ++result;
            result++;
        }
        else {
            result--;
        }
        for (int index = 0; index < 3; index++) {
            if (index == 1) {
                continue;
            }
            if (index == 2) {
                break;
            }
            result += index;
        }
        ;
        assert result >= 0 : "result must not be negative";
        return result + grown + shrunk + inverted + shifted + unsigned + remainder + quotient
                + difference + negated + positive;
    }

    public String choose(int value) {
        String result;
        switch (value) {
            case 1:
            case 2:
                result = "small";
                break;
            case 3:
                result = "three";
                break;
            default:
                result = "other";
                break;
        }
        return result;
    }

    public String yieldChoose(int value) {
        return switch (value) {
            case 1 -> {
                yield "one";
            }
            case 2 -> "two";
            default -> "other";
        };
    }

    public Object casted(Object value) {
        String text = (String) value;
        int[] numbers = new int[] {1, 2, 3};
        int first = numbers[0];
        return text + first;
    }
}
