/*
OneStatementPerLine
treatTryResourcesAsStatement = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

/**
 * Two import statements on the same line are illegal.
 */
import java.io.EOFException; import java.io.BufferedReader; // violation

/**
 * This Class contains no logic, but serves as test-input for the unit tests for the
 * <code>OneStatementPerLineCheck</code>-checkstyle enhancement.
 * @author Alexander Jesse
 * @see com.puppycrawl.tools.checkstyle.checks.coding.OneStatementPerLineCheck
 */
public class InputOneStatementPerLineSingleLineOne {
  /**
   * Dummy innerclass to test the behaviour in the case of a smalltalk-style
   * statements (<code>myObject.firstMethod().secondMethod().thirdMethod()</code>).
   * For this programming style each method must return the object itself <code>this</code>.
   */
  class SmallTalkStyle {
    SmallTalkStyle doSomething1() {
      return this;
    }

    SmallTalkStyle doSomething2() {
      return this;
    }

    SmallTalkStyle doSomething3() {
      return this;
    }
  }

  /**
   * Dummy variable to work on.
   */
  private int one = 0;

  /**
   * Dummy variable to work on.
   */
  private int two = 0;

  /**
   * Simple legal method
   */
  public void doLegal() {
    one = 1;
    two = 2;
  }

  /**
   * The illegal format is used in a comment. Therefor the whole method is legal.
   */
  public void doLegalComment() {
    one = 1;
    //one = 1; two = 2;
    two = 2;
    /*
     * one = 1; two = 2;
     */
  }

  /**
   * The illegal format is used within a String. Therefor the whole method is legal.
   */
  public void doLegalString() {
    one = 1;
    two = 2;
    System.identityHashCode("one = 1; two = 2");
  }

  /**
   * Within the for-header there are 3 Statements, but this is legal.
   */
  public void doLegalForLoop() {
    for (int i = 0; i < 20; i++) {
      one = i;
    }
  }

  /**
   * Simplest form of an illegal layout.
   */
  public void doIllegal() {
    one = 1; two = 2; // violation
  }

  /**
   * Smalltalk-style is considered as one statement.
   */
  public void doIllegalSmallTalk() {
    SmallTalkStyle smalltalker = new SmallTalkStyle();
    smalltalker.doSomething1().doSomething2().doSomething3();
  }

  /**
   * Smalltalk-style is considered as one statement.
   */
  public void doIllegalSmallTalk2() {
    SmallTalkStyle smalltalker = new SmallTalkStyle();
    smalltalker.doSomething1()
        .doSomething2()
        .doSomething3();
  }

}
