package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

/**
 * Config: default
 * Some Javadoc.
 *
 * @since 8.36 // ok
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
     * xyz
     *
     * @param input this is the first tag. // ok
     * @param output this is the second tag. // ok
     */
    public void test2(String input, int output) {
    }

    /**
     * xyz
     *
     * @author Some javadoc. // OK
     */
    public void test3() {
    }

}
