package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

/**
 * Config: default
 * Some Javadoc.
 *
 * @since something
 */
class InputFile1 {

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
     * @return p1 xyz
     * @see more javadoc. // OK
     */
    public <T> String test(String p1, String p2, T p3) {
        return p1;
    }

    /**
     * Description
     *
     * @param input this is the first tag. // ok
     * @param output this is the second tag. // ok
     */
    public void test2(String input, int output) {
    }

    /**
     * Description
     *
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
     * @param ele
     *            Parameter ele desc.
     * @return Some number. // OK
     */
    public static <T, E> int sampleMethod(T[] array, String str, E ele ) {
        return 2021;
    }

    /**
     * Description.
     *
     * @param t
     *            Parameter t desc.
     */
    public void add(int t, String s) {

    }

}
