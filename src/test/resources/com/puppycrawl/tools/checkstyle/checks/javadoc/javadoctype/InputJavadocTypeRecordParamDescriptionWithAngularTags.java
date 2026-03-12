/*
JavadocType


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

/**
 *
 * @param <T> stuff
 * @param value
 */
public record InputJavadocTypeRecordParamDescriptionWithAngularTags<T>(String value) {

}

/**
 *
 * @param    <T>    stuff
 * @param     a     stuff
 */
record Record1<T>(int a) {}

/**
 *
 * @param <T> &lt;stuff&gt;
 * @param a stuff
 * @param b stuff
 */
record Record2<T>(int a, int b) {
    /**
     *
     * @param <T>
     * @param a some description
     * @param b another description
     * @param c yet another description
     */
    record Record3<T>(int a, int b, int c) {}

    /**
     *
     * @param <V> stuff
     */
    record Record4<V>() {}
}

/**
 * @param <T>
 * @param    <P>     stuff // violation, 'Unused @param tag for '<P>'.'
 */
record Record5<T>() {}

/**
 *
 * @param region stuff
 * // violation above, 'Unused @param tag for 'region'.'
 */
record Record6() {}

/**
 *
 *
 * @param <T> // violation, 'Unused @param tag for '<T>'.'
 * @param a some description
 * @param b
 */
record Record7(int a, int b) {}
/**
 * @param a some description // violation, 'Unused @param tag for 'a'.'
 * @param b
 */
record Record8(int b) {}

/**
 *
 *
 *
 * @param a // violation, 'Unused @param tag for 'a'.'
 */
record Record9() {}
