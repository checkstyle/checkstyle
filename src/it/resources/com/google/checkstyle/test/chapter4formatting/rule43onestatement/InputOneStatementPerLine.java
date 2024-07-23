package com.google.checkstyle.test.chapter4formatting.rule43onestatement;

// Two import statements on the same line are illegal.
import java.io.BufferedReader; import java.io.EOFException;
// violation above 'Only one statement per line allowed.'

/** Some javadoc. */
public class InputOneStatementPerLine {

  /**
   * Dummy variable to work on.
   */
  private int one = 0;

  /**
   * Dummy variable to work on.
   */
  private int two = 0;

  /**
   * Simple legal method.
   */
  public void doLegal() {
    one = 1;
    two = 2;
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
    for (int i = 0, j = 0, k = 1; i < 20; i++) { //it's ok.
      one = i;
    }
  }

  /**
   * Simplest form of illegal layouts.
   */
  public void doIllegal() {
    one = 1; two = 2; // violation 'Only one statement per line allowed.'
    if (one == 1) {
      one++; two++; // violation 'Only one statement per line allowed.'
    }
    if (one != 1) { one++; } else { one--; }
    // 3 violations above:
    //  ''{' at column 19 should have line break after.'
    //  ''{' at column 35 should have line break after.'
    //  'Only one statement per line allowed.'
    int n = 10;

    doLegal(); doLegal(); // violation 'Only one statement per line allowed.'
    while (one == 1) { one++; two--; }
    // 3 violations above:
    //  ''{' at column 22 should have line break after.'
    //  'Only one statement per line allowed.'
    //  ''}' at column 38 should be alone on a line.'

  }

  /**
   * While theoretically being distributed over two lines, this is a sample
   * of 2 statements on one line.
   */
  public void doIllegal2() {
    one = 1
    ; two = 2; // violation 'Only one statement per line allowed.'
  }

  class Inner {
    /**
     * Dummy variable to work on.
     */
    private int one = 0;

    /**
     * Dummy variable to work on.
     */
    private int two = 0;

    /**
     * Simple legal method.
     */
    public void doLegal() {
      one = 1;
      two = 2;
    }

    /**
     * Simplest form a an illegal layout.
     */
    public void doIllegal() {
      one = 1; two = 2; // violation 'Only one statement per line allowed.'
      if (one == 1) {
        one++; two++; // violation 'Only one statement per line allowed.'
      }
      if (one != 1) { one++; } else { one--; }
      // 3 violations above:
      //  ''{' at column 21 should have line break after.'
      //  ''{' at column 37 should have line break after.'
      //  'Only one statement per line allowed.'
      int n = 10;

      doLegal(); doLegal(); // violation 'Only one statement per line allowed.'
      while (one == 1) {
        one++; two--; // violation 'Only one statement per line allowed.'
      }

    }
  }

  /**
   * Two declaration statements on the same line are illegal.
   */
  int aaaa; int bbb;
  // 2 violations above:
  //  'Only one variable definition per line allowed.'
  //  'Only one statement per line allowed.'

  /**
   * Two declaration statements which are not on the same line
   * are legal.
   */
  int ccc;
  int dddd;

  /**
   * Two assignment (declaration) statements on the same line are illegal.
   */
  int dog = 1; int cow = 2;
  // 2 violations above:
  //  'Only one variable definition per line allowed.'
  //  'Only one statement per line allowed.'

  /**
   * Two assignment (declaration) statements on the different lines
   * are legal.
   */
  int test1 = 1;
  int test2 = 2;

  /**
   * This method contains
   * two object creation statements on the same line.
   */
  private void foo() {
    //Two object creation statements on the same line are illegal.
    Object obj1 = new Object(); Object obj2 = new Object();
    // 2 violations above:
    //  'Only one variable definition per line allowed.'
    //  'Only one statement per line allowed.'
  }

  /**
   * One multiline  assignment (declaration) statement
   * is legal.
   */
  int ijk = 1, jkl = 2, // violation 'Each variable declaration must be in its own statement.'
          klm = 5;

  /**
   * One multiline  assignment (declaration) statement
   * is legal.
   */
  int lmn = 1, // violation 'Each variable declaration must be in its own statement.'
          mnp = 2,
          npq = 5;

  /**
   * One multiline  assignment (declaration) statement
   * is legal.
   */
  int wxy = 1, // violation 'Each variable declaration must be in its own statement.'
          xyzz = 2,
          yys = 5
                  ;

  /**
   * Two multiline  assignment (declaration) statements
   * are illegal.
   */
  // 2 violations 3 lines below:
  //  'Each variable declaration must be in its own statement.'
  //  'Only one variable definition per line allowed.'
  int abc = 1, pqr = 2,
          xyz = 5; int blah; // violation 'Only one statement per line allowed.'

  /**
   * Two assignment (declaration) statement
   * which are not on the same lines and are legal.
   */
  int four = 1, // violation 'Each variable declaration must be in its own statement.'
          five = 5
                  ;
  int seven = 2;

  /**
   * Two statements on the same line
   * (they both are distributed over two lines)
   * are illegal.
   */
  // 2 violations 3 lines below:
  //  'Each variable declaration must be in its own statement.'
  //  'Only one variable definition per line allowed.'
  int var1 = 5,
      var4 = 5; int var2 = 6, // violation 'Each variable declaration must be in its own statement.'
          var3 = 5; // violation 'Only one statement per line allowed.'

  /**
   * Two statements on the same line
   * (they both are distributed over two lines)
   * are illegal.
   */
  // 2 violations 3 lines below:
  //  'Only one variable definition per line allowed.'
  //  'Each variable declaration must be in its own statement.'
  int var6 = 5; int var7 = 6,
      var8 = 5; // violation 'Only one statement per line allowed.'

  /**
   * Two statements on the same line
   * (they both are distributed over two lines)
   * are illegal.
   */
  private void foo2() {
    toString(

    ); toString(// violation ''method call' .* incorrect indentation level 4, expected .* 6.'

        );
    // 2 violations above:
    // ''method call rparen' has incorrect indentation level 8, expected level should be 4.'
    // 'Only one statement per line allowed.'
  }


  /**
   * While theoretically being distributed over two lines, this is a sample
   * of 2 statements on one line.
   */
  int var9 = 1, var10 = 5 // violation 'Each variable declaration must be in its own statement.'
      ; int var11 = 2; // violation 'Only one statement per line allowed.'


  /**
   * Multiline for loop statement is legal.
   */
  private void foo3() {
    for (int n = 0,
        k = 1;
        n < 5; n++,
           k--) {

    }
  }

  /**
   * Two multiline statements (which are not on the same line)
   * are legal.
   * Violations are not related to "OneStatementPerLine", but to "MultipleVariableDeclarations".
   */
  int var12, // violation 'Each variable declaration must be in its own statement.'
      var13 = 12;
  int var14 = 5, // violation 'Each variable declaration must be in its own statement.'
      var15 = 6;

  /**
   * This method contains break and while loop statements.
   */
  private void foo4() {
    do {
      var9++;
      if (var10 > 4) {
        break; //legal
      }
      var11++;
      var9++;
    } while (var11 < 7); //legal

    /*
     * One statement inside for block is legal
     */
    for (int i = 0; i < 10; i++) { one = 5; } //legal
    // 2 violations above:
    //  ''{' at column 34 should have line break after.'
    //  ''}' at column 45 should be alone on a line.'

    /*
     * One statement inside for block where
     * increment expression is empty is legal
     */
    for (int i = 0; i < 10;) { one = 5; } //legal
    // 2 violations above:
    //  ''{' at column 30 should have line break after.'
    //  ''}' at column 41 should be alone on a line.'

    /*
      One statement inside for block where
      increment and conditional expressions are empty
      (forever loop) is legal
     */
    for (int i = 0;;) { one = 5; } //legal
    // 2 violations above:
    //  ''{' at column 23 should have line break after.'
    //  ''}' at column 34 should be alone on a line.'
  }

  /** Some javadoc. */
  public void foo5() {
    // a "forever" loop.
    for (;;){} //legal
  }

  /** Some javadoc. */
  public void foo6() {
    // One statement inside for block is legal
    for (;;) {
      one = 5;
    } //legal
  }

  /**
   * One statement inside multiline for loop is legal.
   */
  private void foo7() {
    for (int n = 0,
        k = 1
        ; n < 5
        ;
        n++, k--) {
      var1++;
    } //legal
  }

  /**
   * Two statements on the same lne
   * inside multiline for loop are illegal.
   */
  private void foo8() {
    for (int n = 0,
        k = 1
        ; n < 5
        ;
        n++, k--) {
      var1++; var2++; // violation 'Only one statement per line allowed.'
    }
  }
}
