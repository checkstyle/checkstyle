package com.google.checkstyle.test.chapter4formatting.rule489textblocks;

/** Some javadoc. */
public class InputFormattedTextBlocksTernary {

  /** Some javadoc. */
  public void method() {
    final boolean flag1 = true;

    // until https://github.com/google/google-java-format/issues/1378
    // 2 violations 6 lines below:
    // 'Opening quotes (""") of text-block must be on the new line'
    // 'Each line of text in the text block must be indented'
    // violation 5 lines below 'Text-block quotes are not vertically aligned'
    final String a =
        flag1
            ? """
            Text Block 1
            """
            : """
            Text Block 2
            """;
    // 2 violations 3 lines above:
    // 'Opening quotes (""") of text-block must be on the new line'
    // 'Each line of text in the text block must be indented'
    // violation 4 lines above 'Text-block quotes are not vertically aligned'

    // until https://github.com/google/google-java-format/issues/1378
    // 2 violations 6 lines below:
    // 'Opening quotes (""") of text-block must be on the new line'
    // 'Each line of text in the text block must be indented'
    // violation 5 lines below 'Text-block quotes are not vertically aligned'
    final String correctA =
        flag1
            ? """
            Text Block 1
            """
            : """
            Text Block 2
            """;
    // 2 violations 3 lines above:
    // 'Opening quotes (""") of text-block must be on the new line'
    // 'Each line of text in the text block must be indented'
    // violation 4 lines above 'Text-block quotes are not vertically aligned'

    final boolean flag2 = false;

    // until https://github.com/google/google-java-format/issues/1378
    // 2 violations 10 lines below:
    // 'Opening quotes (""") of text-block must be on the new line'
    // 'Each line of text in the text block must be indented'
    //  violation 9 lines below 'Text-block quotes are not vertically aligned'
    //  2 violations 10 lines below:
    // 'Opening quotes (""") of text-block must be on the new line'
    // 'Each line of text in the text block must be indented'
    //  violation 9 lines below 'Text-block quotes are not vertically aligned'
    final String b =
        !flag1
            ? """
            Text Block 1
            """
            : flag2
                ? """
                Text Block 2
                """
                : """
                Text Block 3
                """;
    // 2 violations 3 lines above:
    // 'Opening quotes (""") of text-block must be on the new line'
    // 'Each line of text in the text block must be indented'
    //  violation 4 lines above 'Text-block quotes are not vertically aligned'

    // until https://github.com/google/google-java-format/issues/1378
    // 2 violations 10 lines below:
    // 'Opening quotes (""") of text-block must be on the new line'
    // 'Each line of text in the text block must be indented'
    //  violation 9 lines below 'Text-block quotes are not vertically aligned'
    //  2 violations 10 lines below:
    // 'Opening quotes (""") of text-block must be on the new line'
    // 'Each line of text in the text block must be indented'
    //  violation 9 lines below 'Text-block quotes are not vertically aligned'
    final String correctB =
        !flag1
            ? """
            Text Block 1
            """
            : flag2
                ? """
                Text Block 2
                """
                : """
                Text Block 3
                """;
    // 2 violations 3 lines above:
    // 'Opening quotes (""") of text-block must be on the new line'
    // 'Each line of text in the text block must be indented'
    //  violation 4 lines above 'Text-block quotes are not vertically aligned'

    // until https://github.com/google/google-java-format/issues/1378
    // 2 violations 6 lines below:
    // 'Opening quotes (""") of text-block must be on the new line'
    // 'Each line of text in the text block must be indented'
    // violation 5 lines below 'Text-block quotes are not vertically aligned'
    final String c =
        flag2
            ? """
            Text Block 1
            """
                + "Text"
            : """
            Text Block 2
            """;
    // 2 violations 3 lines above:
    // 'Opening quotes (""") of text-block must be on the new line'
    // 'Each line of text in the text block must be indented'
    // violation 4 lines above 'Text-block quotes are not vertically aligned'

    // until https://github.com/google/google-java-format/issues/1378
    // 2 violations 6 lines below:
    // 'Opening quotes (""") of text-block must be on the new line'
    // 'Each line of text in the text block must be indented'
    // violation 5 lines below 'Text-block quotes are not vertically aligned'
    final String correctC =
        flag2
            ? """
            Text Block 1
            """
                + "Text"
            : """
            Text Block 2
            """;
    // 2 violations 3 lines above:
    // 'Opening quotes (""") of text-block must be on the new line'
    // 'Each line of text in the text block must be indented'
    // violation 4 lines above 'Text-block quotes are not vertically aligned'

    // until https://github.com/google/google-java-format/issues/1378
    // 2 violations 6 lines below:
    // 'Opening quotes (""") of text-block must be on the new line'
    // 'Each line of text in the text block must be indented'
    // violation 5 lines below 'Text-block quotes are not vertically aligned'
    final String d =
        flag2 && flag2
            ? """
            Text Block 1
            """
            : """
            Text Block 2
            """;
    // 2 violations 3 lines above:
    // 'Opening quotes (""") of text-block must be on the new line'
    // 'Each line of text in the text block must be indented'
    // violation 4 lines above 'Text-block quotes are not vertically aligned'

    // until https://github.com/google/google-java-format/issues/1378
    // 2 violations 6 lines below:
    // 'Opening quotes (""") of text-block must be on the new line'
    // 'Each line of text in the text block must be indented'
    // violation 5 lines below 'Text-block quotes are not vertically aligned'
    final String correctD =
        flag2 && flag2
            ? """
            Text
            """
            : """
            Text Block 2
            """;
    // 2 violations 3 lines above:
    // 'Opening quotes (""") of text-block must be on the new line'
    // 'Each line of text in the text block must be indented'
    // violation 4 lines above 'Text-block quotes are not vertically aligned'

  }
}
