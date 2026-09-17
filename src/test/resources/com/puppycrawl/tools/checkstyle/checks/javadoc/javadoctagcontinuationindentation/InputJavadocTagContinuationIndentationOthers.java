/*
JavadocTagContinuationIndentation
violateExecutionOnNonTightHtml = (default)false
offset = (default)4


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

// violation 10 lines below 'Line continuation .* expected level should be 4'
/**
 * Some javadoc.
 *
 * @since Some javadoc.
 * @version 1.0
 * @see Some javadoc.
 *     Some javadoc.
 *     Some javadoc.
 * @see Some javadoc.
 *    Some javadoc.
 * @author max
 */
enum InputJavadocTagContinuationIndentationOthers {}

// violation 9 lines below 'Line continuation .* expected level should be 4'
// violation 10 lines below 'Line continuation .* expected level should be 4'
/**
 * Some javadoc.
 *
 * @version 1.0
 * @since Some javadoc.
 *     Some javadoc.
 * @serialData Some javadoc.
 *   Line below is empty on purpose.
 * @see Some Text.
 * L.
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
    // violation 5 lines below 'Line continuation .* expected level should be 4'
    /**
     * Test.
     *
     * @return Test
     * tt <code>null</code>.
     */
    public void example() {
    }
}
