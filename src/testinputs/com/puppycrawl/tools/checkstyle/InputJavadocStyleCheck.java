////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2003
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

/**
 * Test input for the JavadocStyleCheck.  This check is used to perform 
 * some additional Javadoc validations.  
 * 
 * @author Chris Stillwell
 * @version 1.0
 */
public class InputJavadocStyleCheck
{
   // This is OK. We don't flag missing javadoc.  That's left for other checks.
   private String first;
   
   /** This Javadoc is missing an ending period */
   private String second;
   
   /**
    * We don't want {@link com.puppycrawl.tools.checkstyle.checks.JavadocStyleCheck} 
    * tags to stop the scan for the end of sentence. 
    * @see Somthing
    */
   public InputJavadocStyleCheck()
   {
   }
   
   /**
    * This is ok!
    */
   private void method1()
   {
   }
   
   /**
    * This is ok?
    */
   private void method2()
   {
   }
   
   /**
    * And This is ok.<br>
    */
   private void method3()
   {
   }
   
   /**
    * This should fail even.though.there are embedded periods
    */
   private void method4()
   {
   }
   
   /**
    * Test HTML in Javadoc comment
    * <dl>
    * <dt><b>This guy is missing end of bold tag
    * <dd>The dt and dd don't require end tags.
    * </dl>
    * </td>Extra tag shouldn't be here
    * 
    * @param arg1 <code>dummy.
    */
   private void method5(int arg1)
   {
   }
   
   /**
    * Protected check <b>should fail
    */
   protected void method6()
   {
   }
   
   /**
    * Package protected check <b>should fail
    */
   void method7()
   {
   }
   
   /**
    * Public check should fail</code>
    * should fail <
    */
   public void method8()
   {
   }
   
   /** {@inheritDoc} **/
   public void method9()
   {
   }

    
    // Testcases to excercize the Tag parser (bug 843887)

    /**
     * Real men don't use XHTML.
     * <br />
     * <hr/>
     * < br/>
     * <img src="schattenparker.jpg"/></img>
     */
    private void method10()
    { // </img> should be the only error
    }

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
    private void method11()
    { // JavadocStyle should not report any error for this method
    }

    /**
     * Tags for two lines.
     * <a href="some_link"
     * >Link Text</a>
     */
    private void method12()
    {// JavadocStyle should not report any error for this method
    }

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
    private void method13()
    {// JavadocStyle should not report any error for this method
    }

    /**
     * Some problematic javadoc. Sample usage:
     * <blockquote>
     */

    private void method14()
    { // empty line between javadoc and method is critical (bug 841942)
    }

    /**
     * Empty line between javadoc and method declaration cause wrong
     * line number for reporting error (bug 841942)
     */

    private void method15()
    { // should report unended first sentance (check line number of the error)
    }

    /** Description of field: {@value}. */
    public static final int dummy = 4911;

    /**
     */
    public void method16()
    { // should report empty javadoc
    }

    /**
     * @param a A parameter
     */
    protected void method17(String a)
    { // should report empty javadoc (no text before parameter)
    }

    /**
     * @exception RuntimeException shoul be thrown
     */
    void method18(String a)
    { // should report empty javadoc (no text before exception)
    }

    /** 
     */
    private static int ASDF = 0;
    // should report empty javdoc

    /** @see java.lang.Object */
    public void method19()
    {  // should report empty javadoc (no text before see tag)
    }

    public enum Test
        //Should complain about no javadoc
    {
        /**
         * Value 1 without a period
         */
        value1,

        /**
         * Value 2 with a period.
         */
        value2,
    }

    /**
    * A test class.
    * @param <T1> this is NOT an unclosed T1 tag
    * @param <KEY_T> for bug 1649020.
    * @author <a href="mailto:foo@nomail.com">Foo Bar</a>
    */
    public class TestClass<T1, KEY_T>
    {
        /**
        * Retrieves X.
        * @return a value
        */
        public T1 getX()
        {
            return null;
        }

        /**
        * Retrieves Y.
        * @param <V> this is not an unclosed V tag
        * @return a value
        */
        public <V> V getY()
        {
            return null;
        }
        
        /**
         * Retrieves Z.
         * 
         * @param <KEY_T1> this is not an unclosed KEY_T tag
         * @return a value
         */
        public <KEY_T1> KEY_T getZ_1649020_1()
        {
            return null;
        }
        
        /**
         * Retrieves something.
         * 
         * @param <KEY_T_$_1_t> strange type
         * @return a value
         */
        public <KEY_T_$_1_t> KEY_T_$_1_t getEh_1649020_2() {
            return null;
        }
        
        /**
         * Retrieves more something.
         * 
         * @param <$_12_xY_z> strange type
         * @return a value
         */
        public <$_12_xY_z> $_12_xY_z getUmmm_1649020_3() {
            return null;
        }
    }

    /**
     * Checks if the specified IClass needs to be
     * annotated with the @Type annotation.
     */
    public void foo_1291847_1() {
    }

    /**
     * Returns the string containing the properties of
     * <code>@Type</code> annotation.
     */
    public void foo_1291847_2() {
    }

		/**
		 * Checks generics javadoc.
		 * 
		 * @param strings this is a List<String>
		 * @param test Map<String, List<String>> a map indexed on String of Lists of Strings.
		 */
		public void method20() {
		}

		/**
		 * Checks HTML tags in javadoc.
		 * 
		 * HTML no good tag
		 * <string>Tests</string>
		 *
		 */
		public void method21() {
		}

}
