package com.google.checkstyle.test.chapter4formatting.rule44columnlimit;

// ok above, longer package statements are allowed

// ok below, longer imports are allowed
import com.google.checkstyle.test.chapter3filestructure.toolongpackagetotestcoveragegooglesjavastylerule.PackageStatementTest;
import java.io.IOException;

final class InputFormattedColumnLimit {
  // Long line
  // ----------------------------------------------------------------------------------------------------
  // violation above 'Line is longer than 100 characters (found 105).'

  PackageStatementTest pckgStmt = new PackageStatementTest();

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
   * @throws java.lang.Exception abc
   */
  int test1(int badFormat1, int badFormat2, final int badFormat3) throws java.lang.Exception {
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

  // violation 2 lines below 'Line is longer than 100 characters (found 111).'
  // Very long url with invalid href:
  // href="www.google.com/search?hl=en&q=java+style+guide+checkstyle+check+href+length+limit&btnG=Google+Search
  int invalidHref = 88;

  // Very long url with valid href:
  // href="www.google.com/search?hl=en&q=java+style+guide+checkstyle+check+href+length+limit&btnG=Google+Search"
  int validHref = 54;

  // violation 2 lines below 'Line is longer than 100 characters (found 107).'
  // Very long url with valid href: href    =
  // "www.google.com/search?hl=en&q=java+style+guide+checkstyle+check+href+length+limit&btnG=Google+Search"
  int validHrefWithWhiteSpaces = 54;

  // violation 2 lines below 'Line is longer than 100 characters (found 114).'
  int
      aaaarealllllllllllllllllyyyyyyyyyyylllllllloooooooooooooooonnnnnnnnnnnnnnnnnggggggggggvvvvvvaarriaaabllee1 =
          99;

  // CHECKSTYLE.SUPPRESS: LineLength for +2 lines
  int
      aaaarealllllllllllllllllyyyyyyyyyyylllllllloooooooooooooooonnnnnnnnnnnnnnnnnggggggggggvvvvvvaarriaaabllee2 =
          99;

  // suppressed above.

  // violation 2 lines below 'Line is longer than 100 characters (found 108).'
  void
      longggggggggggggggggggggmethoooooooooooooooooooooodddddddddddddddddddddddddddddddddddddddddddddd1() {}

  // CHECKSTYLE.SUPPRESS: LineLength for +2 lines
  void
      longggggggggggggggggggggmethoooooooooooooooooooooodddddddddddddddddddddddddddddddddddddddddddddd2() {}

  // suppressed above.

  final boolean isValid = true;
  final boolean isValid2 = true;
  final boolean isValid3 = true;
  final boolean isValid4 = true;
  final boolean isValid5 = true;

  void testingNestedIf1() {
    if (isValid) {
      if (isValid2) {
        if (isValid3) {
          if (isValid4) {
            if (isValid5) {
              // CHECKSTYLE.SUPPRESS: LineLength for +1 lines
              longggggggggggggggggggggmethoooooooooooooooooooooodddddddddddddddddddddddddddddddddddddddddddddd1();
            }
          }
        }
      }
    }
  }

  void testingNestedIf2() {
    if (isValid) {
      if (isValid2) {
        if (isValid3) {
          if (isValid4) {
            if (isValid5) {
              // violation below 'Line is longer than 100 characters (found 114).'
              longggggggggggggggggggggmethoooooooooooooooooooooodddddddddddddddddddddddddddddddddddddddddddddd2();
            }
          }
        }
      }
    }
  }

  // violation 3 lines below 'Line is longer than 100 characters (found 118).'
  void testingParametersNames1(
      int
          areallllllyyyyyyyyyyyyyyyyyylonnnnggggggggggnameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee,
      int anotherlongnameblahblahblah) {}

  // CHECKSTYLE.SUPPRESS: LineLength for +3 lines
  void testingParametersNames2(
      int
          areallllllyyyyyyyyyyyyyyyyyylonnnnggggggggggnameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee,
      int anotherlongnameblahblahblah) {}

  // suppressed above.

  InputColumnLimit inputColumnLimit1 =
      (InputColumnLimit)
          ((Object)
              new com.google.checkstyle.test.chapter4formatting.rule44columnlimit
                  .InputColumnLimit());

  // CHECKSTYLE.SUPPRESS: LineLength for +1 lines
  InputColumnLimit inputColumnLimit2 =
      (InputColumnLimit)
          ((Object)
              new com.google.checkstyle.test.chapter4formatting.rule44columnlimit
                  .InputColumnLimit());

  // suppressed above.
  // Above code is wrap-able, user should not use suppression for such cases

  void testing1() {
    try {
      throwExceptionBasedOnCondition1();
    } catch (IOException
        | NullPointerException
        | ArrayIndexOutOfBoundsException
        | ClassNotFoundException ex) {
      System.out.println(ex.getMessage());
    }
  }

  void testing2() {
    try {
      throwExceptionBasedOnCondition2();
      // CHECKSTYLE.SUPPRESS: LineLength for +1 lines
    } catch (IOException
        | NullPointerException
        | ArrayIndexOutOfBoundsException
        | ClassNotFoundException ex) {
      // Above code is wrap-able. User should not use suppression for such cases
      System.out.println(ex.getMessage());
    }
  }

  void throwExceptionBasedOnCondition1()
      throws IOException,
          NullPointerException,
          ArrayIndexOutOfBoundsException,
          ClassNotFoundException {
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

  // CHECKSTYLE.SUPPRESS: LineLength for +2 lines
  void throwExceptionBasedOnCondition2()
      throws IOException,
          NullPointerException,
          ArrayIndexOutOfBoundsException,
          ClassNotFoundException {
    // suppressed above.
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

  // CHECKSTYLE.SUPPRESS: LineLength for +1 lines
  class LonggggggggggggggggggggggggggggClassssssssssssssssssssssssssNameeeeeeeeeeeeeeSoooooBoooooorrriinngggg1 {
    // ok below, as it is suppressed.
    void
        longggggggggggggggggggggmethooooooooooooooooooooooddddddddddddddddddddddddddddddddddddddddddddddddddddddd(
            int x, int y) {}

    // CHECKSTYLE.SUPPRESS: LineLength for -3 lines

    // ok below, as it is suppressed.
    // violation 4 lines below '.* indentation should be the same level as line 231.'
    int
        aaaarealllllllllllllllllyyyyyyyyyyylllllllloooooooooooooooonnnnnnnnnnnnnnnnnggggggggggvvvvvvaarriaaablleeeeeeeeeeee =
            99;
    // CHECKSTYLE.SUPPRESS: LineLength for -2 lines
  }

  // violation below 'Line is longer than 100 characters (found 112).'
  class LonggggggggggggggggggggggggggggClassssssssssssssssssssssssssNameeeeeeeeeeeeeeSoooooBoooooorrriinngggg2 {
    // violation 2 lines below 'Line is longer than 100 characters (found 114).'
    void
        longggggggggggggggggggggmethooooooooooooooooooooooddddddddddddddddddddddddddddddddddddddddddddddddddddddd(
            int x, int y) {}

    // violation 2 lines below 'Line is longer than 100 characters (found 125).'
    int
        aaaarealllllllllllllllllyyyyyyyyyyylllllllloooooooooooooooonnnnnnnnnnnnnnnnnggggggggggvvvvvvaarriaaablleeeeeeeeeeee =
            99;
  }
}
