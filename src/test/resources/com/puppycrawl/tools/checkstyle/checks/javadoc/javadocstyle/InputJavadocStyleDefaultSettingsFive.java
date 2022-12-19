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

      /************************* building *********************************/

      /*
       * <li>Only public method used during the tokenization process
       * <li>Requires that the input ByteRange sort after the previous, and therefore after all previous
       * inputs
       * <li>Only looks at bytes of the input array that align with this node's token
       */
      public void demoTest() {}
}

/** // violation
* not ok
*/
class Demo { }

/*warn*//** // violation
       * <a href="mailto:vlad@htmlbook.ru"/>
       */
class InnerInputCorrectJavaDocParagraphCheck {
      void /** warn */ method20() {}
      void method21 /** warn */ () {}
      void method22(/** warn */) {}
      void method23() /** warn */ {}
      void method24() { /** warn */ }
      void method25() { /** warn */ int variable; }

      /**Javadoc*/ // violation
      enum/**nope*/ AAAAA1/**nope-1*/ {}
      enum  demo {}

/** The italic action identifier // violation
*/
      public static final String  ITALIC_ACTION = "html-italic-action";

      /************************************************
      *
      * Construction
      *
      ************************************************/

     /** // violation
      * Constructs XMenuWindow for specified XMenuPeer
      * null for XPopupMenuWindow
      */
      public void demoMethod() {

      }
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

class InputAbstract {
      public enum MatchCode {
            /**
            * These codes are used by the ScanQueryMatcher
            */

           /**
            * Done with the row, seek there.
            */
            SEEK_NEXT_COL
      }
}
