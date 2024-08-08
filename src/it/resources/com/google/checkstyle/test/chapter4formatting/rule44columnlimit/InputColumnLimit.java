package com.google.checkstyle.test.chapter4formatting.rule44columnlimit; // ok

import com.google.checkstyle.test.chapter3filestructure.toolongpackagetotestcoveragegooglesjavastylerule.PackageStatementTest; // ok
import java.io.IOException;

final class InputColumnLimit {
  // Long line
  // ----------------------------------------------------------------------------------------------------
  // violation above 'Line is longer than 100 characters (found 105).'

  private int[] testing =
      new int[] {
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27
        // violation above 'Line is longer than 100 characters (found 105).'
      };

  /**
   * Some javadoc.
   *
   * @param badFormat1 bad format
   * @param badFormat2 bad format
   * @param badFormat3 bad format
   * @return hack
   * @throws java.lang.Exception abc
   */
  int test1(int badFormat1, int badFormat2, final int badFormat3) throws java.lang.Exception {
    return 0;
  }

  /**
   * Some javadoc.
   * Very long url with https: https://github.com/checkstyle/checkstyle/blob/master/src/main/java/com/puppycrawl/tools/checkstyle/checks/AvoidEscapedUnicodeCharactersCheck.java
   */
  String https = "200 OK";

  /**
   * Some javadoc.
   * Very long url with http: http://github.com/checkstyle/checkstyle/blob/master/src/main/java/com/puppycrawl/tools/checkstyle/checks/AvoidEscapedUnicodeCharactersCheck.java
   */
  String http = "200 OK";

  // Very long url with ftp: ftp://ftp.example.com/areallyyyyyyyyyyyylongggggggggggggggggggggggurlllll.text
  int ftp = 0;

  // violation below 'Line is longer than 100 characters (found 144).'
  // Very long url with invalid href: href="www.google.com/search?hl=en&q=java+style+guide+checkstyle+check+href+length+limit&btnG=Google+Search
  int invalidHref = 88;

  // Very long url with valid href: href="www.google.com/search?hl=en&q=java+style+guide+checkstyle+check+href+length+limit&btnG=Google+Search"
  int validHref = 54;

  // Very long url with valid href: href    = "www.google.com/search?hl=en&q=java+style+guide+checkstyle+check+href+length+limit&btnG=Google+Search"
  int validHrefWithWhiteSpaces = 54;

  @SuppressWarnings({"LineLength"})
  int aaaarealllllllllllllllllyyyyyyyyyyylllllllloooooooooooooooonnnnnnnnnnnnnnnnnggggggggggvvvvvvaarriaaabllee = 99; // ok, suppression used, check name in camelcase

  @SuppressWarnings({"linelength"})
  int aaaarealllllllllllllllllyyyyyyyyyyylllllllloooooooooooooooonnnnnnnnnnnnnnnnnggggggggggvvvvvvaarriaaabllee2 = 99; // ok, suppression used, check name in lowercase

  @SuppressWarnings({"linelength"})
  void longggggggggggggggggggggmethoooooooooooooooooooooodddddddddddddddddddddddddddddddddddddd(int x, int y) {} // ok

  @SuppressWarnings({"linelength"})
  void testingParametersNames(int areallllllyyyyyyyyyyyyyyyyyylonnnnggggggggggname, int anotherlongnameblahblahblah) {} // ok

  @SuppressWarnings({"linelength"})
  InputColumnLimit inputColumnLimit = (InputColumnLimit) ((Object) new com.google.checkstyle.test.chapter4formatting.rule44columnlimit.InputColumnLimit()); // ok

  @SuppressWarnings({"linelength"})
  void testing() {
    try {
      throwExceptionBasedOnCondition();

    } catch (IOException | NullPointerException | ArrayIndexOutOfBoundsException | ClassNotFoundException ex) { // ok, got suppressed
      System.out.println(ex.getMessage());
    }
  }

  @SuppressWarnings({"linelength"})
  void throwExceptionBasedOnCondition()
      throws IOException, NullPointerException, ArrayIndexOutOfBoundsException, ClassNotFoundException { // ok, got suppressed
    // Above is violation of the rule, user should not use suppression for such cases
    int condition = new java.util.Random().nextInt(3);
    switch (condition) {
      case 0:
        throw new IOException("Test IOException");
      case 1:
        throw new NullPointerException("Test NullPointerException");
      case 2:
        throw new ArrayIndexOutOfBoundsException("Test ArrayIndexOutOfBoundsException");
      case 3:
        throw new ClassNotFoundException("Test ClassNotFoundException");
      default:
    }
  }

  @SuppressWarnings({"linelength"})
  class LonggggggggggggggggggggggggggggClassssssssssssssssssssssssssNameeeeeeeeeeeeeeSoooooBoooooorrriinngggg {
    void longggggggggggggggggggggmethoooooooooooooooooooooodddddddddddddddddddddddddddddddddddddd(int x, int y) {} // ok

    int aaaarealllllllllllllllllyyyyyyyyyyylllllllloooooooooooooooonnnnnnnnnnnnnnnnnggggggggggvvvvvvaarriaaabllee = 99; // ok
  }
}
