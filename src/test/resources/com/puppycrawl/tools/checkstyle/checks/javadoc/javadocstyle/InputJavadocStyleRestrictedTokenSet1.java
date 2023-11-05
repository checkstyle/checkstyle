/*
JavadocStyle
scope = public
excludeScope = (default)null
checkFirstSentence = (default)true
endOfSentenceFormat = (default)([.?!][ \t\n\r\f<])|([.?!]$)
checkEmptyJavadoc = (default)false
checkHtml = false
tokens = METHOD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

public class InputJavadocStyleRestrictedTokenSet1 // ok
{
   // This is OK. We don't flag missing javadoc.  That's left for other checks.
   private String first; // ok

   /** This Javadoc is missing an ending period */
   private String second; // ok

   /**
    * We don't want {@link com.puppycrawl.tools.checkstyle.checks.JavadocStyleCheck}
    * tags to stop the scan for the end of sentence.
    * @see Something
    */
   public InputJavadocStyleRestrictedTokenSet1() {} // ok

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

   /**
    * This should fail even.though.there are embedded periods
    */
   private void method4() {} // ok

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

   /**
    * Protected check <b>should fail
    */
   protected void method6() {} // ok

   /**
    * Package protected check <b>should fail
    */
   void method7() {} // ok

   // violation below 'First sentence should end with a period.'
   /**
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
}
