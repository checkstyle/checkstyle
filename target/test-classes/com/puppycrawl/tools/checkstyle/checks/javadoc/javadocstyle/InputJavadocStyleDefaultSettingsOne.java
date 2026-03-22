/*
JavadocStyle
scope = (default)private
excludeScope = (default)null
checkFirstSentence = (default)true
endOfSentenceFormat = (default)([.?!][ \t\n\r\f<])|([.?!]$)
checkEmptyJavadoc = (default)false
checkHtml = (default)true
tokens = (default)ANNOTATION_DEF, ANNOTATION_FIELD_DEF, CLASS_DEF, CTOR_DEF, \
         ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, METHOD_DEF, PACKAGE_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

public class InputJavadocStyleDefaultSettingsOne
{
   // This is OK. We don't flag missing javadoc.  That's left for other checks.
   private String first;

   // violation below 'First sentence should end with a period.'
   /** This Javadoc is missing an ending period */
   private String second;

   /**
    * We don't want {@link com.puppycrawl.tools.checkstyle.checks.JavadocStyleCheck}
    * tags to stop the scan for the end of sentence.
    * @see Something
    */
   public InputJavadocStyleDefaultSettingsOne() {}

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

   // violation below 'First sentence should end with a period.'
   /**
    * This should fail even.though.there are embedded periods
    */
   private void method4() {}

   // violation 7 lines below 'Unclosed HTML tag found: <b>'
   // violation 9 lines below 'Extra HTML tag found: </td>'
   // violation 9 lines below 'Extra HTML tag found: </style>'
   // violation 9 lines below 'Unclosed HTML tag found: <code>dummy'
   /**
    * Test HTML in Javadoc comment
    * <dl>
    * <dt><b>
    * <dd>The dt and dd don't require end tags.
    * </dl>
    * </td>
    * <style>this tag isn't supported in Javadoc</style>
    * @param arg1 <code>dummy
    */
   private void method5(int arg1) {}

   // violation 2 lines below 'First sentence should end with a period.'
   // violation 2 lines below 'Unclosed HTML tag found: <b>'
   /**
    * Protected check <b>
    */
   protected void method6() {}

   // violation 2 lines below 'First sentence should end with a period.'
   // violation 2 lines below 'Unclosed HTML tag found: <b>'
   /**
    * Package protected check <b>
    */
   void method7() {}

   // violation 3 lines below 'First sentence should end with a period.'
   // violation 3 lines below 'Extra HTML tag found: </code>'
   // violation 3 lines below 'should fail <'
   /**
    * Public check should fail</code>
    * should fail <
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
    private void method10() {}
    /**
     * Tags for two lines.
     * <a href="some_link"
     * >Link Text</a>
     */
    private void method11() {}
}
