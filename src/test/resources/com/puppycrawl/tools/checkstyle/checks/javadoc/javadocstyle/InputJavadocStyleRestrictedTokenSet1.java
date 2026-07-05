/*
JavadocStyle
checkEmptyJavadoc = (default)false
checkFirstSentence = (default)true
endOfSentenceFormat = (default)([.?!][ \t\n\r\f<])|([.?!]$)
excludeScope = (default)null
scope = public
violateExecutionOnNonTightHtml = (default)false
tokens = METHOD_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

public class InputJavadocStyleRestrictedTokenSet1
{
   // This is OK. We don't flag missing javadoc.  That's left for other checks.
   private String first;

   /** This Javadoc is missing an ending period */
   private String second;

   /**
    * We don't want {@link com.puppycrawl.tools.checkstyle.checks.JavadocStyleCheck}
    * tags to stop the scan for the end of sentence.
    * @see Something
    */
   public InputJavadocStyleRestrictedTokenSet1() {}

   /**
    * This is ok!
    */
   private void method1() {}

   /**
    * This is ok?
    */
   private void method2() {}

   /**
    * And This is ok.<br>
    */
   private void method3() {}

   /**
    * This should fail even.though.there are embedded periods
    */
   private void method4() {}

   /**
    * Protected check should fail.
    */
   protected void method6() {}

   /**
    * Package protected check should fail.
    */
   void method7() {}

   // violation below 'First sentence should end with a period.'
   /**
    * Public check should fail
    */
   public void method8() {}

   /** {@inheritDoc} **/
   public void method9() {}

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
    private void method11() {}
}
