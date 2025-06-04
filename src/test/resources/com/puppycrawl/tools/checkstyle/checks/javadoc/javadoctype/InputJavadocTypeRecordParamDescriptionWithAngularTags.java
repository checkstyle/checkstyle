/*
JavadocType


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

/**
 *
 * @param <T> stuff <stuff>
 * @param value
 */
public record InputJavadocTypeRecordParamDescriptionWithAngularTags<T>(String value) {

}

/**
 *
 * @param    <T>    🐦‍🔥stuff<stuff>stu🐦‍🔥ff</stuff>stuff🐦‍🔥
 * @param     a     <stuff>
 */
record Record11<T>(int a) {}

/**
 *
 * @param <T> &lt;stuff&gt;
 * @param a stUff&lt;stuff&gt;sutff&lt;🐦‍🔥&gt;🐦‍🔥
 * @param b <>>>>><<<<
 */
record Record12<T>(int a, int b) {
    /**
     *
     * @param <T>
     * @param a <//< <stuff></> stuff >>  <stuff>st<u>ff</stuff> >><
     * @param b stuff <><><<stuff></></></> stuff
     * @param c /-+`[]:^^<stuff **%%$>##(sutff)stuff()</stuff> @@
     */
    record Record13<T>(int a, int b, int c) {}

    /**
     *
     * @param <V> [(<>{@code stuff<stuff>&lt;stuff&gt;}</>)]{@code {&lt;stuff&gt;}}
     */
    record Record14<V>() {}
}

/**
 * @param <T>
 * @param    <P>     stuff <><><<stuff></></></> stuff // violation,'Unused @param tag for '<P>'.'
 */
record Record15<T, U>() {} // violation, 'Type Javadoc comment is missing @param <U> tag.'

/**
 *
 * @param region [(<>{@code stuff<stuff🐦‍🔥>🐦‍🔥&lt;stuff&gt;}</>)]{@code {&lt;stuff&gt;}}
 * // violation above, 'Unused @param tag for 'region'.'
 */
record Record16(int a) {} // violation, 'Type Javadoc comment is missing @param a tag.'

/**
 *
 * @param <T>
 * @param a 🐦‍🔥<><🐦‍🔥><<stuff></></></> stuff🐦‍🔥
 * @param b
 */
record Record17<T>(int a, int b) {}

/**
 * @param a <<></>></><<></>></>
 * @param b stuff<stuff>:<>:<>:<🐦‍🔥<<🐦‍🔥>>🐦‍🔥>
 * @param e [(<>{@code stuff<stuff🐦[(‍{🔥}])>🐦‍🔥&lt;stuff&gt;}</>)]{@code {&lt;stuff&gt;}}
 * // violation above, 'Unused @param tag for 'e'.'
 */
record Record18(int a, int b, int c) { // violation, 'Type Javadoc comment is missing @param c tag.'
}

/**
 *
 * @param a [(<>{@code stuff<stuff🐦‍🔥>🐦‍🔥&lt;stuff&gt;}</>)]
 */
record Record19(int a) {}

/**
 *
 * @param a <><><><><><><><>
 * @param b >><>>><><><<<><<
 * @param c {@code <{[(<stu<f>f>)]}>}
 */
record Record20(int a, int b, int c) {}

/**
 * One Transaction.
 *
 * @param transactionId  unique ID of the transaction in format: {@code <first Code>:<second Code>}
 */
record MyTransaction(String transactionId) {
}
