/*
JavadocTagContinuationIndentation
violateExecutionOnNonTightHtml = (default)false
offset = (default)4


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

/**
 * Some javadoc.
 *
 * @since Some javadoc.
 * @version 1.0
 * @see Some javadoc.
 *     Some javadoc.
 *     Some javadoc.
 * @see Some javadoc.
 *    Some javadoc. // violation, 'Line continuation .* expected level should be 4'
 * @author max
 */
enum InputJavadocTagContinuationIndentationOthers {}

/**
 * Some javadoc.
 *
 * @version 1.0
 * @since Some javadoc.
 *     Some javadoc.
 * @serialData Some javadoc.
 *   Line below is empty on purpose.
 * @see Some Text. // violation above 'Line continuation .* expected level should be 4'
 * L. // violation, 'Line continuation .* expected level should be 4'
 *
 * @author max
 * @customTag {@link com.puppycrawl.tools.checkstyle.AllChecksPresentOnAvailableChecksPageTest
 *   some description} // ok, as this is just inline tag description
 */
interface FooIn1 {}

/**
 * <p>Testing javadoc with spanning tag {@linkplain #DEFAULT default mapping
 * factory}.</p>
 */
interface FooIn2 {}

class ShortNextLine {
    /**
     * Test.
     *
     * @return Test
     * tt <code>null</code>. // violation, 'Line continuation .* expected level should be 4'
     */
    public void example() {
    }
}
