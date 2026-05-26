package com.google.checkstyle.test.chapter4formatting.rule489textblocks;

/** Somejavadoc. */
public class InputTextBlocksTernary {

  /** Somejavadoc. */
  public void method() {

    final boolean first = true;


    // 2 violations 4 lines below:
    // 'Opening quotes (""") of text-block must be on the new line'
    // 'Each line of text in the text block must be indented'
    String a = first
            ? """
             line 1
             line 2
              """
            : """
              other
              """;
    // violation 3 lines above 'Opening quotes (""") of text-block must be on the new line'

    // 2 violations 3 lines below:
    // 'Opening quotes (""") of text-block must be on the new line'
    // 'Each line of text in the text block must be indented'
    String b = first ? """
              valid
            invalid
              """
                // violation above 'Text-block quotes are not vertically aligned'
                // violation below 'Opening quotes (""") of text-block must be on the new line'
                : """
                  fallback
                  """;

    final boolean second = false;

    // 2 violations 4 lines below:
    // 'Opening quotes (""") of text-block must be on the new line'
    // 'Each line of text in the text block must be indented'
    String c = second
            ? """
              a
            b
              c
              """
            : """
              d
              e
              """;
    // violation 4 lines above 'Opening quotes (""") of text-block must be on the new line'

  }
}
