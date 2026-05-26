package com.google.checkstyle.test.chapter4formatting.rule489textblocks;

/** Some javadoc. */
public class InputFormattedTextBlocksTernary {

  /** Some javadoc. */
  public void method() {
    final boolean flag1 = true;




    final String a =
        flag1
            ?
            """
            Text Block 1
            """
            :
            """
            Text Block 2
            """;


    final boolean flag2 = false;






    final String b =
        !flag1
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



    final String c =
        flag2
            ?
            """
            Text Block 1
            """
                + "Text"
            :
            """
            Text Block 2
            """;



    final String d =
        flag2 && flag2
            ?
            """
            Text Block 1
            """
            :
            """
            Text Block 2
            """;


  }
}
