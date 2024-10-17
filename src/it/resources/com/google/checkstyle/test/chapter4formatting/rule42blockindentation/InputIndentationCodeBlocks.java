package com.google.checkstyle.test.chapter4formatting.rule42blockindentation;

/** Some javadoc. */
public class InputIndentationCodeBlocks {
  private static void test(int condition) {
    {
      System.out.println("True");
    }
  }

  {
    System.out.println("False");
  }

    { // violation ''block lcurly' has incorrect indentation level 4, expected level should be 2.'
      System.out.println("False"); // violation '.* incorrect indentation level 6, expected .* 4.'
    } // violation ''block rcurly' has incorrect indentation level 4, expected level should be 2.'

  class Inner {
    void test() {
      {
        System.out.println("True");
      }
    }
  }
}
