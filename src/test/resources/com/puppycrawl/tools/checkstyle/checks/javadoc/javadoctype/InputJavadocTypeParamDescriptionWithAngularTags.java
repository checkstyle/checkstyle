/*
JavadocType
allowUnknownTags = (default)false
authorFormat = (default)
scope = (default)private
excludeScope = (default)
versionFormat = (default)
tokens = (default)INTERFACE_DEF,CLASS_DEF,ENUM_DEF,ANNOTATION_DEF,RECORD_DEF
allowMissingParamTags = (default)false
allowedAnnotations = (default)Generated

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

/**
 *
 * @param <T> <stuff>
 */
public class InputJavadocTypeParamDescriptionWithAngularTags<T> {}

/**
 *
 * @param <T> 🐦‍🔥stuff<stuff>stu🐦‍🔥ff</stuff>stuff🐦‍🔥
 */
interface Ronin<T> {}

/**
 *
 * @param <T> &lt;stuff&gt;
 * @param <U> stUff&lt;stuff&gt;sutff&lt;🐦‍🔥&gt;🐦‍🔥
 */
class Shogun<T, U> {
    /**
     *
     * @param <T> <//< <stuff></> stuff >>  <stuff>st<u>ff</stuff> >><
     * @param    <U>     stuff <><><<stuff></></></> stuff
     * @param <V> /-+`[]:^^<stuff **%%$>##(sutff){stuff}{}()stuff</stuff> @@
     */
    interface Mandate<T, U, V> {}

    /**
     *
     * @param <V> [(<>{@code stuff<stuff>&lt;stuff&gt;}</>)]{@code {&lt;stuff&gt;}}
     */
    class Minowara<V> extends Shogun<T, U> implements Mandate<T, U, V> {}
}

/**
 * @param    <T>
 * @param <P> stuff <><><<stuff></></></> stuff // violation, 'Unused @param tag for '<P>'.'
 */
interface Regent<T, U> {} // violation, 'Type Javadoc comment is missing @param <U> tag.'

/**
 *
 * @param region [(<>{@code stuff<stuff🐦‍🔥@@@>🐦‍🔥&lt;{stuff}&gt;}</>@)]{@code {&lt;stuff&gt;}}
 * // violation above, 'Unused @param tag for 'region'.'
 */
class Fief {}
