/*
JavadocStyle
checkEmptyJavadoc = true
checkFirstSentence = false
endOfSentenceFormat = (default)([.?!][ \t\n\r\f<])|([.?!]$)
excludeScope = (default)null
scope = (default)private
violateExecutionOnNonTightHtml = (default)false
tokens = (default)ANNOTATION_DEF, ANNOTATION_FIELD_DEF, CLASS_DEF, CTOR_DEF, \
         ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, METHOD_DEF, PACKAGE_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

public class InputJavadocStyleEmptyJavadoc1 {
    // This is OK. We don't flag missing javadoc.  That's left for other checks.
   private String first;

   /** This Javadoc is missing an ending period */
   private String second;

   /**
    * We don't want {@link com.puppycrawl.tools.checkstyle.checks.JavadocStyleCheck}
    * tags to stop the scan for the end of sentence.
    * @see Something
    */
   public InputJavadocStyleEmptyJavadoc1() {}

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

   /**
    * Public check should fail.
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

    /**
     * Tags for two lines.
     * <a href="some_link"
     * >Link Text</a>
     */
    private void method12() {}
}
