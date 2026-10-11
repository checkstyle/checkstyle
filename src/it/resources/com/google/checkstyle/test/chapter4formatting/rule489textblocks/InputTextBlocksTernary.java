package com.google.checkstyle.test.chapter4formatting.rule489textblocks;

/** Some javadoc. */
public class InputTextBlocksTernary {

  /** Some javadoc. */
  public void method() {
    final boolean flag1 = true;

    // 2 violations 4 lines below:
    // 'Opening quotes (""") of text-block must be on the new line'
    // 'Each line of text in the text block must be indented'
    final String a = flag1
        ? """
        Text Block 1
        """ :
        """
        Text Block 2
        """;
    // violation 4 lines above 'Text-block quotes are not vertically aligned'

    final String correctA = flag1
        ?
        """
        Text Block 1
        """
        :
        """
        Text Block 2
        """;

    final boolean flag2 = false;

    // 2 violations 6 lines below:
    // 'Opening quotes (""") of text-block must be on the new line'
    // 'Each line of text in the text block must be indented'
    //  violation 5 lines below 'Text-block quotes are not vertically aligned'
    //  violation 5 lines below ''?' should be on a new line.'
    final String b = !flag1
        ? """
        Text Block 1
        """ :
        flag2 ?
        """
        Text Block 2
        """
        : """
          Text Block 3
          """;
    // violation 3 lines above 'Opening quotes (""") of text-block must be on the new line'

    final String correctB = !flag1
        ?
        """
        Text Block 1
        """
        : flag2
        ?
        """
        Text Block 2
        """
        :
        """
        Text Block 3
        """;

    // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
    final String c = flag2
        ? """
          Text Block 1
          """
          + "Text"
        :
        """
        Text Block 2
        """;

    final String correctC = flag2
        ?
        """
        Text Block 1
        """
        + "Text"
        :
        """
        Text Block 2
        """;

    // 2 violations 4 lines below:
    // 'Opening quotes (""") of text-block must be on the new line'
    // 'Each line of text in the text block must be indented'
    // violation 3 lines below 'Text-block quotes are not vertically aligned'
    final String d = flag2 && flag2 ? """
        Text Block 1
        """
        :
            """
            Text Block 2
            """;

    final String correctD = flag2 && flag2
        ?
        """
        Text
        """
        :
        """
        Text Block 2
        """;

  }
}
