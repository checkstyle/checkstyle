/*
JavadocStyle
checkEmptyJavadoc = (default)false
checkFirstSentence = (default)true
endOfSentenceFormat = (default)([.?!][ \t\n\r\f<])|([.?!]$)
excludeScope = (default)null
scope = (default)private
violateExecutionOnNonTightHtml = (default)false
tokens = (default)ANNOTATION_DEF, ANNOTATION_FIELD_DEF, CLASS_DEF, CTOR_DEF, \
         ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, METHOD_DEF, PACKAGE_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

public class InputJavadocStyleDefaultSettingsFour
{
    /**
     * Link to some page in two lines.
     * <a
     * href="someLink"/>
     */
    void tagInTwoLines() {}

    void javadocLikeCommentInMethod() {
        /**
         * It pretends to be Javadoc without dot, but it's just comment in method
         */
        final int i = 0;
    }

    /**
     * {@inheritDoc}
     */
    private void inheritDoc() {}

    // violation below 'First sentence should end with a period.'
    /**
     * Javadoc without dot
     */
    public interface TestInterface {
        // violation below 'First sentence should end with a period.'
        /**
         * Javadoc without dot
         */
        void method();
    }

    static class TestStaticClass {
        // violation below 'First sentence should end with a period.'
        /**
         * Javadoc without dot
         */
        public int field;
    }

    /**
     * .
     * @throws Exception if an error occurs
     */
    void foo() throws Exception {}

    // violation below 'First sentence should end with a period.'
    /**
     * text /
     * @throws Exception if an error occurs
     */
    void bar() throws Exception {}

    /**
     * {@inheritDoc}
     * <p>
     * @throws IllegalArgumentException with errMsg as its message
     */
    void inheritDocWithThrows() {}

    // violation below 'First sentence should end with a period.'
    /**
     * /
     *
     **
     * @param s
     * @return Return
     */
    public int test(String s) { return 0; }

    /**
     * <p>Test.</p>
     * <pre class="body">
     for (
     ; i &lt; j; i++, j--) {}
     </pre>
     */
    public void test2() {}

    /**
     * <p>Test.</p>
     * <pre><code>&#064;ExtendWith(SpotBugsExtension.class)
     public class SampleTest {
     }</code></pre>
     */
    public void test3() {}
}
