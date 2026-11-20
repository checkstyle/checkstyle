package com.sun.checkstyle.test.chapter8whitespace.rule81blanklines; // violation first line 'Header mismatch'
import java.util.List; // violation ''import' should be separated.*'

/**
 * Test input with missing blank lines between source file sections, type
 * definitions and methods.
 */
public final class InputBlankLinesInvalid {

    /**
     * First valid nested class, followed by two blank lines.
     */
    private static final class FirstValid {
    }


    /**
     * Second valid nested class, after two blank lines.
     */
    private static final class SecondValid {
    }

    /**
     * First invalid nested class.
     */
    private static final class FirstInvalid {
    }
    /**
     * Second invalid nested class.
     */
    private static final class SecondInvalid {
        // violation above ''CLASS_DEF' should be separated from previous line.'
    }

    /**
     * Returns the first value.
     *
     * @param values dummy values.
     * @return first value.
     */
    public int first(final List<Integer> values) {
        return values.get(0);
    }

    /**
     * Returns the last value.
     *
     * @param values dummy values.
     * @return last value.
     */
    public int last(final List<Integer> values) {
        return values.get(values.size() - 1);
    }

    /**
     * Returns the second value.
     *
     * @param values dummy values.
     * @return second value.
     */
    public int second(final List<Integer> values) {
        return values.get(1);
    }
    /**
     * Returns the value count.
     *
     * @param values dummy values.
     * @return value count.
     */
    public int count(final List<Integer> values) {
        // violation above ''METHOD_DEF' should be separated.*'
        return values.size();
    }

}
