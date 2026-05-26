package com.google.checkstyle.test.chapter4formatting.rule489textblocks;

/** Somejavadoc. */
public class InputFormattedTextBlocksTernary {

  /** Somejavadoc. */
  public void method() {

    final boolean first = true;




    String a =
        first
            ?
            """
            line 1
            line 2
            """
            :
            """
            other
            """;





    String b =
        first
            ?
            """
              valid
            invalid
            """


            :
            """
            fallback
            """;

    final boolean second = false;




    String c =
        second
            ?
            """
              a
            b
              c
            """
            :
            """
            d
            e
            """;


  }
}
