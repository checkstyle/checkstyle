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
public class InputOneStatementPerLineSingleLine {
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
    //one = 1; two = 2; // ok
    two = 2;
    /*
     * one = 1; two = 2; // ok
     */
  }

  /**
   * The illegal format is used within a String. Therefor the whole method is legal.
   */
  public void doLegalString() {
    one = 1;
    two = 2;
    System.identityHashCode("one = 1; two = 2"); // ok
  }

  /**
   * Within the for-header there are 3 Statements, but this is legal.
   */
  public void doLegalForLoop() {
    for (int i = 0; i < 20; i++) { // ok
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

  /**
   * While theoretically being distributed over two lines, this is a sample
   * of 2 statements on one line.
   */
  public void doIllegal2() {
    one = 1
    ; two = 2; // violation
  }

  /**
   * The StringBuffer is a Java-API-class that permits smalltalk-style concatenation
   * on the <code>append</code>-method.
   */
  public void doStringBuffer() {
    StringBuffer sb = new StringBuffer();
    sb.append("test ");
    sb.append("test2 ").append("test3 ");
    appendToSpringBuffer(sb, "test4");
  }

  /**
   * indirect stringbuffer-method. Used only internally.
   * @param sb The stringbuffer we want to append something
   * @param text The text to append
   */
  private void appendToSpringBuffer(StringBuffer sb, String text) {
    sb.append(text);
  }

  /**
   * Two declaration statements on the same line are illegal.
   */
  int a; int b; // violation

  /**
   * Two declaration statements which are not on the same line
   * are legal.
   */
  int c;
  int d;

  /**
   * Two assignment (declaration) statements on the same line are illegal.
   */
  int e = 1; int f = 2; // violation

  /**
   * Two assignment (declaration) statements on the different lines
   * are legal.
   */
  int g = 1;
  int h = 2;

  /**
   * This method contains two increment statements
   * and two object creation statements on the same line.
   */
  private void foo() {
    //This is two assignment (declaration)
    //statements on different lines
    int var1 = 1;
    int var2 = 2;

    //Two increment statements on the same line are illegal.
    var1++; var2++; // violation

    //Two object creation statements on the same line are illegal.
    Object obj1 = new Object(); Object obj2 = new Object(); // violation
  }

  /**
   * This method contains break, while-loop
   * and for-loop statements.
   */
  private void foo3() {
    do {
      one++;
      if (two > 4) {
        break; //legal
      }
      one++;
      two++;
    } while (two < 7); //legal

    /**
     *  One statement inside for block is legal.
     */
    for (int i = 0; i < 10; i++) one = 5;

    /**
     *  One statement inside for block where
     *  increment expression is empty is legal.
     */
    for (int i = 0; i < 10;) one = 5;

    /**
     *  One statement inside for block where
     *  increment and conditional expressions are empty
     *  (forever loop) is legal
     */
    for (int i = 0;;) one = 5;
  }

  public void foo4() {
    /**
     * a "forever" loop.
     */
    for(;;){} //legal
  }

  public void foo5() {
    /**
     *  One statement inside for block is legal
     */
    for (;;) { one = 5; }
  }

  public void foo6() {
      bar(() -> {
          return;}, () -> {return;});
  }

  void bar(Runnable r1, Runnable r2) { }
}
