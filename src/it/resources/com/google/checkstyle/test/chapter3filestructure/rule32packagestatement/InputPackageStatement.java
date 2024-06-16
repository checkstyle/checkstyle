package com.google.checkstyle.test. // violation 'package statement should not be line-wrapped.'
        chapter3filestructure.rule32packagestatement;

import com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck; //ok

import javax.accessibility. // violation 'import statement should not be line-wrapped.'
        AccessibleAttributeSequence;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater; //ok

import static java.math. // violation 'import statement should not be line-wrapped.'
        BigInteger.ONE;

import static java.math.BigInteger.ZERO; //ok

public class InputPackageStatement {
  // Long line ---------------------------------------------------------------------------------------- // violation 'Line is longer than 100 characters (found 166).'
  // Contains a tab ->    <-
  // Contains trailing whitespace ->

  // Name format tests
  //
  /** Invalid format **/
  public static final int badConstant = 2;
  /** Valid format **/
  public static final int MAX_ROWS = 2;

  /** Invalid format **/
  private static int badStatic = 2;
  /** Valid format **/
  private static int sNumCreated = 0;

  /** Invalid format **/
  private int badMember = 2;
  /** Valid format **/
  private int mNumCreated1 = 0;
  /** Valid format **/
  protected int mNumCreated2 = 0;

  /** commas are wrong **/
  private int[] mInts = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24}; // violation 'Line is longer than 100 characters (found 237).'

  /**
   * Very long url: https://github.com/checkstyle/checkstyle/blob/master/src/main/java/com/puppycrawl/tools/checkstyle/checks/AvoidEscapedUnicodeCharactersCheck.java
   */
  public void fooMethod() {}

  /**
   * Long url without wrapping: http://ftp.dlink.ru/pub/D-Link_Solutions/D-Link_Solutions_for_Business.pdf
   */
  public void fooMethodLongFtp() {}

  public void fooLongStringUrl() {
    String url = "https://github.com/checkstyle/checkstyle/blob/master/src/main/java/com/puppycrawl/tools/checkstyle/checks/AvoidEscapedUnicodeCharactersCheck.java"; //ok
    processUrl("https://github.com/checkstyle/checkstyle/blob/master/src/main/java/com/puppycrawl/tools/checkstyle/checks/AvoidEscapedUnicodeCharactersCheck.java"); //ok
    processUrl("some line"
            + "https://github.com/checkstyle/checkstyle/blob/master/src/main/java/com/puppycrawl/tools/checkstyle/checks/AvoidEscapedUnicodeCharactersCheck.java" //ok
            + "+ long fooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo00000000000o line"); // violation 'Line is longer than 100 characters (found 183).'
    processUrl("Some long foooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo line"); // violation 'Line is longer than 100 characters (found 176).'
    String[] soooooooooooooooooooooooooooooooooooolongfooooooooooooooooooooooooooooooooooooooooooo = { // violation 'Line is longer than 100 characters (found 165).'
            "http://github.com/checkstyle/checkstyle/blob/master/src/main/java/com/puppycrawl/tools/checkstyle/checks/AvoidEscapedUnicodeCharactersCheck.java", //ok
            "Some long foooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo line", // violation 'Line is longer than 100 characters (found 172).'
    };

    String fakehttps = "Some long foooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo line"; // violation 'Line is longer than 100 characters (found 183).'

    processUrl(new String[] {
            "http://github.com/checkstyle/checkstyle/blob/master/src/main/java/com/puppycrawl/tools/checkstyle/checks/AvoidEscapedUnicodeCharactersCheck.java", //ok
            "Some long foooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo line", // violation 'Line is longer than 100 characters (found 172).'
    });

    String s = "text"
            + "text"
            + "text something more.. <a href=\"https://groups.google.com/forum/#!topic/checkstyle-devel/E0z89fzvxGs%5B226-250-false%5D\">long url name, long url name, long url name</a>" //ok
            + "other text";
  }

  /**
   *
   * @param url
   */
  public void processUrl(String url) { }

  /**
   *
   * @param urls
   */
  public void processUrl(String[] urls) { }
}
