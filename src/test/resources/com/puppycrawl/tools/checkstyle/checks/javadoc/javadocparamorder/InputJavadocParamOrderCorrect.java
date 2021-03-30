package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamordercheck;

/**
 * Config: default
 * Some Javadoc.
 *
 * @since something
 */
class InputJavadocParamOrderCorrect {

    /**
     * Description.
     *
     * @version Some javadoc.
     * @param <T>
     *            Parameter T desc.
     * @param p1
     *            Parameter 1 desc.
     * @param p2
     *            Parameter 2 desc.
     * @param p3
     *            Parameter 3 desc.
     * @param p4
     *            Parameter 3 desc. // OK
     * @return p1 xyz
     * @see more javadoc. // OK
     */
    public <T> String test(String p1, String p2, T p3) {
        return p1;
    }

    /**
     * Description
     *
     * @param input descInputTestCorrect // ok
     * @param output desc // ok
     */
    public void test2(String input, int output) {
    }

    /**
     * Description
     *
     * @param p4
     *            Parameter 3 desc. // OK
     * @author Some javadoc. // OK
     */
    public void test3() {
    }

    /**
     * Description.
     *
     * @version Some javadoc. // OK
     * @param <T>
     *            Parameter T desc.
     * @param <E>
     *            Parameter E desc.
     * @param array
     *            Parameter array desc.
     * @see Some javadoc. // OK
     * @param str
     *            Parameter str desc.
     * @param e
     *            Parameter e desc.
     * @return Some number. // OK
     */
    public static <T, E> int sampleMethod(T[] array, String str, E e ) {
        return 2021;
    }

    /**
     * Description.
     *
     * @param t
     *            Parameter t desc.
     */
    public void add(int t, String s) {}
}
