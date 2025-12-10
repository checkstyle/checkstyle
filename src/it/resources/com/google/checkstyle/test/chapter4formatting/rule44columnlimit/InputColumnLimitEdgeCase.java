package com.google.checkstyle.test.chapter4formatting.rule44columnlimit;

/** Some javadoc. */
public class InputColumnLimitEdgeCase {
  void testMethod1() {
    String s3 =
        "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical";
    // violation above 'Line is longer than 100 characters (found 114)'

    String s1 =
        """
        Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical
        """
            + getSampleTest();

    String s2 =
        """
        Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical\
        """;
  }

  /** Some javadoc. */
  String getSampleTest() {
    return "String";
  }
}
