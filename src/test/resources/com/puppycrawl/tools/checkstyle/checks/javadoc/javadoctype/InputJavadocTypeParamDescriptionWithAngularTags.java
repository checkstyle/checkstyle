/*
JavadocType


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

/**
 *
 * @param <T> <stuff>
 */
public class InputJavadocTypeParamDescriptionWithAngularTags<T> {}

/**
 *
 * @param <T> stuff
 */
interface Ronin<T> {}

/**
 *
 * @param <T> &lt;stuff&gt;
 * @param <U> stUff&lt;stuff&gt;sutff
 */
class Shogun<T, U> {
    /**
     *
     * @param <T> some description
     * @param <U> another description
     * @param <V> yet another description
     */
    interface Mandate<T, U, V> {}

    /**
     *
     * @param <V> {@code stuff}
     */
    class Minowara<V> extends Shogun<T, U> implements Mandate<T, U, V> {}
}

/**
 * @param    <T>
 * @param <U> stuff
 * @param <P> stuff // violation, 'Unused @param tag for '<P>'.'
 */
interface Regent<T, U> {}

/**
 *
 * @param region {@code stuff}
 * // violation above, 'Unused @param tag for 'region'.'
 */
class Fief {}
