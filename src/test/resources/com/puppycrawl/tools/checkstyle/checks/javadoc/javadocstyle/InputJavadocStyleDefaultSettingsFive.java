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

public class InputJavadocStyleDefaultSettingsFive {
      /** Used for component-specific logging: */

      /** Logger for everything */ // violation
      private final int logger = 5;

      /*
       * @see TestCase#setUp()
      */
      public void demo() {}

      /*
       * @see TestCase#setUp()
      */

      /** Monitor guarding all access */ // violation
      public void demo2() {}

      /** warn */
      /** valid */ // violation
      private int demo = 5;
}

/** // violation
 * not ok
 */
class Demo { }

/*warn*//** // violation
       * <a href="mailto:vlad@htmlbook.ru"/>
       */
class InnerInputCorrectJavaDocParagraphCheck {
      void /** warn */ method20() {} // violation
      void method21 /** warn */ () {} // violation
      void method22(/** warn */) {} // violation
      void method23() /** warn */ {} // violation
      void method24() { /** warn */ } // violation
      void method25() { /** warn */ int variable; }

      /**Javadoc*/ // violation
      enum/**nope*/ AAAAA1/**nope-1*/ {} // violation
      enum  demo {}
}

/*
    * Config: NonTightHtmlTagIntolerantCheck
    */

   /**
   * <body>
   * <p> This class is only meant for testing. </p>
   * <p> In html, closing all tags is not necessary.
   * <li> neither is opening every tag <p> </li>
   * // violation below
   * </body>
   *
   * @see "https://www.w3.org/TR/html51/syntax.html#optional-start-and-end-tags"
   */
  class InputAbstractJavadocParsingErrors2 {
      void singletonTag() {
      }
  }
