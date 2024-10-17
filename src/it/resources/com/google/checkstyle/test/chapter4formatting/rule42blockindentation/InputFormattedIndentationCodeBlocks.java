package com.google.checkstyle.test.chapter4formatting.rule42blockindentation;

/** Some javadoc. */
public class InputFormattedIndentationCodeBlocks {
  private static void test(int condition) {
    {
      System.out.println("True");
    }
  }

  {
    System.out.println("False");
  }

  {
    System.out.println("False");
  }

  class Inner {
    void test() {
      {
        System.out.println("True");
      }
    }
  }
}
