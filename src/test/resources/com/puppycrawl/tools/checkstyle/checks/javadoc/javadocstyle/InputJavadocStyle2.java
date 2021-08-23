/*
JavadocStyle
scope = (default)private
excludeScope = (default)null
checkFirstSentence = (default)true
endOfSentenceFormat = (default)([.?!][ \t\n\r\f<])|([.?!]$)
checkEmptyJavadoc = (default)false
checkHtml = false
tokens = (default)ANNOTATION_DEF, ANNOTATION_FIELD_DEF, CLASS_DEF, CTOR_DEF, \
         ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, METHOD_DEF, PACKAGE_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

public class InputJavadocStyle2
{
   // This is OK. We don't flag missing javadoc.  That's left for other checks.
   private String first; // ok

   /** This Javadoc is missing an ending period */ // violation
   private String second;

   /**
    * We don't want {@link com.puppycrawl.tools.checkstyle.checks.JavadocStyleCheck}
    * tags to stop the scan for the end of sentence.
    * @see Something
    */
   public InputJavadocStyle2() {} // ok

   /**
    * This is ok!
    */
   private void method1() {} // ok

   /**
    * This is ok?
    */
   private void method2() {} // ok

   /**
    * And This is ok.<br>
    */
   private void method3() {} // ok

   /** // violation
    * This should fail even.though.there are embedded periods
    */
   private void method4() {}

   /**
    * Test HTML in Javadoc comment
    * <dl>
    * <dt><b>This guy is missing end of bold tag
    * <dd>The dt and dd don't require end tags.
    * </dl>
    * </td>Extra tag shouldn't be here
    * <style>this tag isn't supported in Javadoc</style>
    * @param arg1 <code>dummy.
    */
   private void method5(int arg1) {} // ok

   /** // violation
    * Protected check <b>should fail
    */
   protected void method6() {}

   /** // violation
    * Package protected check <b>should fail
    */
   void method7() {}

   /** // violation
    * Public check should fail</code>
    * should fail <
    */
   public void method8() {}

   /** {@inheritDoc} **/
   public void method9() {} // ok


    // Testcases to exercise the Tag parser (bug 843887)

    /**
     * Real men don't use XHTML.
     * <br />
     * <hr/>
     * < br/>
     * <img src="schattenparker.jpg"/></img>
     */
    private void method10() {} // ok

    /**
     * Tag content can be really mean.
     * <p>
     * Sometimes a p tag is closed.
     * </p>
     * <p>
     * Sometimes it's not.
     *
     * <span style="font-family:'Times New Roman',Times,serif;font-size:200%">
     * Attributes can contain spaces and nested quotes.
     * </span>
     * <img src="slashesCanOccurWithin/attributes.jpg"/>
     * <img src="slashesCanOccurWithin/attributes.jpg">
     * <!-- comments <div> should not be checked. -->
     */
    private void method11() {} // ok

    /**
     * Tags for two lines.
     * <a href="some_link"
     * >Link Text</a>
     */
    private void method12() {} // ok

    /**
     * First sentence.
     * <pre>
     * +--LITERAL_DO (do)
     *     |
     *     +--SLIST ({)
     *         |
     *         +--EXPR
     *             |
     *             +--ASSIGN (=)
     *                 |
     *                 +--IDENT (x)
     *                 +--METHOD_CALL (()
     *                     |
     *                     +--DOT (.)
     *                         |
     *                         +--IDENT (rand)
     *                         +--IDENT (nextInt)
     *                     +--ELIST
     *                         |
     *                         +--EXPR
     *                             |
     *                             +--NUM_INT (10)
     *                     +--RPAREN ())
     *         +--SEMI (;)
     *         +--RCURLY (})
     *     +--LPAREN (()
     *     +--EXPR
     *         |
     *         +--LT (<)
     *             |
     *             +--IDENT (x)
     *             +--NUM_INT (5)
     *     +--RPAREN ())
     *     +--SEMI (;)
     * </pre>
     */
    private void method13() {} // ok

    /**
     * Some problematic javadoc. Sample usage:
     * <blockquote>
     */

    private void method14() {} // ok

    /** // violation
     * Empty line between javadoc and method declaration cause wrong
     * line number for reporting error (bug 841942)
     */

    private void method15() {}

    /** Description of field: {@value}. */
    public static final int dummy = 4911; // ok

    /**
     */
    public void method16() {} // ok

    /**
     * @param a A parameter
     */
    protected void method17(String a) {} // ok

    /**
     * @exception RuntimeException should be thrown
     */
    void method18(String a) {} // ok

    /**
     */
    private static int ASDF = 0; // ok

    /** @see Object */
    public void method19() {} // ok

    public enum Test // ok
    {
        /** // violation
         * Value 1 without a period
         */
        value1,

        /**
         * Value 2 with a period.
         */
        value2, // ok
    }

    /**
    * A test class.
    * @param <T1> this is NOT an unclosed T1 tag
    * @param <KEY_T> for bug 1649020.
    * @author <a href="mailto:foo@nomail.com">Foo Bar</a>
    */
    public class TestClass<T1, KEY_T> // ok
    {
        /**
        * Retrieves X.
        * @return a value
        */
        public T1 getX() // ok
        {
            return null;
        }

        /**
        * Retrieves Y.
        * @param <V> this is not an unclosed V tag
        * @return a value
        */
        public <V> V getY() // ok
        {
            return null;
        }

        /**
         * Retrieves Z.
         *
         * @param <KEY_T1> this is not an unclosed KEY_T tag
         * @return a value
         */
        public <KEY_T1> KEY_T getZ_1649020_1() // ok
        {
            return null;
        }

        /**
         * Retrieves something.
         *
         * @param <KEY_T_$_1_t> strange type
         * @return a value
         */
        public <KEY_T_$_1_t> KEY_T_$_1_t getEh_1649020_2() { // ok
            return null;
        }

        /**
         * Retrieves more something.
         *
         * @param <$_12_xY_z> strange type
         * @return a value
         */
        public <$_12_xY_z> $_12_xY_z getUmmm_1649020_3() { // ok
            return null;
        }
    }

    /**
     * Checks if the specified IClass needs to be
     * annotated with the @Type annotation.
     */
    public void foo_1291847_1() {} // ok

    /**
     * Returns the string containing the properties of
     * <code>@Type</code> annotation.
     */
    public void foo_1291847_2() {} // ok

    /**
     * Checks generics javadoc.
     *
     * @param strings this is a List<String>
     * @param test Map<String, List<String>> a map indexed on String of Lists of Strings.
     */
    public void method20() {} // ok

    /**
     * Checks HTML tags in javadoc.
     *
     * HTML no good tag
     * <string>Tests</string>
     *
     */
    public void method21() {} // ok

    /**
     * First sentence.
     * <
     * /a>
     */
    void tagClosedInNextLine() {} // ok

    /**
     * Link to some page in two lines.
     * <a
     * href="someLink"/>
     */
    void tagInTwoLines() {} // ok

    /**
     * This Javadoc contains unclosed tag.
     * <code>unclosed 'code' tag<code>
     */
    private void unclosedTag() {} // ok

    void javadocLikeCommentInMethod() { // ok
        /**
         * It pretends to be Javadoc without dot, but it's just comment in method
         */
        final int i = 0; // ok
    }
    // violation below
    /**
     * {@inheritDoc}
     */
    private void inheritDoc() {}

    /**
     * <p><b>Note:<b> it's unterminated tag.</p>
     */
    private void unterminatedTag() {} // ok

    /** // violation
     * Javadoc without dot
     */
    public interface TestInterface {
        /** // violation
         * Javadoc without dot
         */
        void method();
    }

    static class TestStaticClass { // ok
        /** // violation
         * Javadoc without dot
         */
        public int field;
    }

    /**
     * .
     * @throws Exception if an error occurs
     */
    void foo() throws Exception {} // ok

    /** // violation
     * text /
     * @throws Exception if an error occurs
     */
    void bar() throws Exception {}

    /**
     * {@inheritDoc}
     * <p>
     * @throws IllegalArgumentException with errMsg as its message
     */
    void inheritDocWithThrows() {} // ok

    /** // violation
     * /
     *
     **
     * @param s
     * @return Return
     */
    public int test(String s) { return 0; }

    /** Set of all class field names.*/
    public String field; // ok

    /**
     * <p>Test.</p>
     * <pre class="body">
         for (
          ; i &lt; j; i++, j--) {}
       </pre>
     */
    public void test2() {} // ok

    /**
     * <p>Test.</p>
     * <pre><code>&#064;ExtendWith(SpotBugsExtension.class)
public class SampleTest {
}</code></pre>
     */
    public void test3() {} // ok
}
