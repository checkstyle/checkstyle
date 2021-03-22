/** A block comment in the syntax tree with no parent. */
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

/** Javadoc for import */
import java.util.*;

/**
 * Config: default
 *
 * @param <A> A type param            // ok
 * @param <B1> Another type param     // ok
 * @param <C456>> Another type param  // ok
 * @author Nobody
 * @version 1.0
 */
class InputJavadocParamOrderCorrect<A,B1,C456 extends Comparable> { // ok

    /**
     * The client's first name.
     * @serial
     */
    private String sSecondName;

    /**
     * Description.
     *
     * @version Some javadoc.
     * @param <T> Parameter T desc.  // ok
     * @param p1 Parameter 1 desc.   // ok
     * @param p2 Parameter 2 desc.   // ok
     * @param p3 Parameter 3 desc.   // ok
     * @param p4 Parameter 3 desc.   // ok, extra param
     * @return p1 xyz
     * @see more javadoc.
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
     * @param p4 Parameter 3 desc.  // ok
     * @author Some javadoc.
     * @return int
     */
    int test3() { // ok
        return 9;
    }

    /**
     * Description.
     *
     * @version Some javadoc.
     * @param <T> Parameter T desc.        // ok
     * @param <E> Parameter E desc.        // ok
     * @param array Parameter array desc.  // ok
     * @see Some javadoc.
     * @param str Parameter str desc.      // ok
     * @param e Parameter e desc.          // ok
     * @return Some number.
     */
    public static <T, E> int sampleMethod(T[] array, String str, E e ) { // ok
        return 2021;
    }

    /**
     * Description.
     *
     * @param t Parameter t desc.  // ok
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
