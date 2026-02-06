/*
JavadocMethod

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodEmptyJavadocDetection {

    /** */
    /** Method with empty javadoc followed by real javadoc.
     * @param x value
     */
    public void methodWithEmptyThenRealJavadoc(int x) {
    }

    /**  */
    /** Another empty javadoc with spaces followed by real javadoc.
     * @return value
     */
    public int methodWithEmptySpacesThenRealJavadoc() {
        return 0;
    }

    /**
     *
     */
    /** Empty javadoc with newlines followed by real javadoc.
     * @param y value
     */
    public void methodWithEmptyNewlinesThenRealJavadoc(String y) {
    }

    /** * */
    /** Empty javadoc with asterisk followed by real javadoc. */
    public void methodWithEmptyAsteriskThenRealJavadoc() {
    }

    /** a
     * @param z value
     */
    public void methodWithNonEmptyJavadoc(int z) {
    }

    /** text
     */
    // violation below 'Expected @param tag for 'value''
    public void methodWithTextJavadoc(String value) {
    }

    /**
     * Non-empty javadoc with content.
     * @param data value
     */
    public void methodWithProperJavadoc(Object data) {
    }

    /**   *  * */
    /** All whitespace and asterisks followed by real javadoc.
     * @return result
     */
    public int methodWithAllWhitespaceAsterisks() {
        return 1;
    }
}


