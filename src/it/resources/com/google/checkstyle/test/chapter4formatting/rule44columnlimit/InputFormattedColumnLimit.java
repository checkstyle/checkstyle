package com.google.checkstyle.test.chapter4formatting.rule44columnlimit; // ok

final class InputFormattedColumnLimit {

  private int[] testing =
      new int[] {
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
        26, 27
      };

  /**
   * Some javadoc.
   *
   * @param badFormat1 bad format
   * @param badFormat2 bad format
   * @param badFormat3 bad format
   * @return hack
   * @throws Exception abc
   */
  int test1(int badFormat1, int badFormat2, final int badFormat3) throws Exception {
    return 0;
  }

  /**
   * Some javadoc. Very long url with https:
   * https://github.com/checkstyle/checkstyle/blob/master/src/main/java/com/puppycrawl/tools/checkstyle/checks/AvoidEscapedUnicodeCharactersCheck.java
   */
  String https = "200 OK";

  /**
   * Some javadoc. Very long url with http:
   * http://github.com/checkstyle/checkstyle/blob/master/src/main/java/com/puppycrawl/tools/checkstyle/checks/AvoidEscapedUnicodeCharactersCheck.java
   */
  String http = "200 OK";

  // Very long url with ftp:
  // ftp://ftp.example.com/areallyyyyyyyyyyyylongggggggggggggggggggggggurlllll.text
  int ftp = 0;

  // Very long url with valid href:
  // href="www.google.com/search?hl=en&q=java+style+guide+checkstyle+check+href+length+limit&btnG=Google+Search"
  int validHref = 54;
}
