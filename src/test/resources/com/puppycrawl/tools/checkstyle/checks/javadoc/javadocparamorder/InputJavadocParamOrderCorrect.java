/** A block comment in the syntax tree with no parent. */
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

/** Javadoc for import */
import java.util.*;

/**
 * Config: default
 *
 * @param <A> A type param
 * @param <B1> Another type param
 * @param <C456>> Another type param
 * @author Nobody
 * @version 1.0
 */
class InputJavadocParamOrderCorrect<A,B1,C456 extends Comparable> {

    /**
     * The client's first name.
     * @serial
     */
    private String sSecondName;

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
     *            Parameter 3 desc. // ok, extra param
     * @return p1 xyz
     * @see more javadoc. // ok
     */
    public <T> String test(String p1, String p2, T p3) { // ok
        return p1;
    }

    /**
     * Description
     *
     * @param input descInputTestCorrect // ok
     * @param output desc // ok
     */
    public void test2(String input, int output) { // ok
    }

    /**
     * Description
     *
     * @param p4
     *            Parameter 3 desc. // ok
     * @author Some javadoc. // ok
     * @return int
     */
    int test3() { // ok
        return 9;
    }

    /**
     * Description.
     *
     * @version Some javadoc. // ok
     * @param <T>
     *            Parameter T desc.
     * @param <E>
     *            Parameter E desc.
     * @param array
     *            Parameter array desc.
     * @see Some javadoc. // ok
     * @param str
     *            Parameter str desc.
     * @param e
     *            Parameter e desc.
     * @return Some number. // ok
     */
    public static <T, E> int sampleMethod(T[] array, String str, E e ) { // ok
        return 2021;
    }

    /**
     * Description.
     *
     * @param t
     *            Parameter t desc.
     */
    public void add(int t, String s) { // ok
    }

    /** @param algorithm desc*/ // ok
    public void setAlgo(String algorithm) { // ok
    }

}

/**
 * Some text.
 *
 * @param aString Some text.
 */
