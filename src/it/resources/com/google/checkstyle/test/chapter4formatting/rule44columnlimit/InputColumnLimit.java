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

  // @cs-: LineLength for +1 lines
  int aaaarealllllllllllllllllyyyyyyyyyyylllllllloooooooooooooooonnnnnnnnnnnnnnnnnggggggggggvvvvvvaarriaaabllee = 99; // ok

  // @cs-: LineLength for +1 lines
  void longggggggggggggggggggggmethoooooooooooooooooooooodddddddddddddddddddddddddddddddddddddd(int x, int y) {} // ok

  // @cs-: LineLength for +1 lines
  void testingParametersNames(int areallllllyyyyyyyyyyyyyyyyyylonnnnggggggggggname, int anotherlongnameblahblahblah) {} // ok
  // Above code is wrap-able. User should not use suppression for such cases

  // @cs-: LineLength for +1 lines
  InputColumnLimit inputColumnLimit = (InputColumnLimit) ((Object) new com.google.checkstyle.test.chapter4formatting.rule44columnlimit.InputColumnLimit()); // ok
  // Above code is wrap-able. User should not use suppression for such cases

  void testing() {
    try {
      throwExceptionBasedOnCondition();
    // @cs-: LineLength for +1 lines
    } catch (IOException | NullPointerException | ArrayIndexOutOfBoundsException | ClassNotFoundException ex) {
      // Above code is wrap-able. User should not use suppression for such cases
      System.out.println(ex.getMessage());
    }
  }

  // @cs-: LineLength for +2 lines
  void throwExceptionBasedOnCondition()
      throws IOException, NullPointerException, ArrayIndexOutOfBoundsException, ClassNotFoundException { // ok
    // Above code is wrap-able. User should not use suppression for such cases
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

  // @cs-: LineLength for +1 lines
  class LonggggggggggggggggggggggggggggClassssssssssssssssssssssssssNameeeeeeeeeeeeeeSoooooBoooooorrriinngggg {
    void longggggggggggggggggggggmethoooooooooooooooooooooodddddddddddddddddddddddddddddddddddddd(int x, int y) {} // ok
    // @cs-: LineLength for -1 lines

    int aaaarealllllllllllllllllyyyyyyyyyyylllllllloooooooooooooooonnnnnnnnnnnnnnnnnggggggggggvvvvvvaarriaaabllee = 99; // ok
    // @cs-: LineLength for -1 lines
  }
}
