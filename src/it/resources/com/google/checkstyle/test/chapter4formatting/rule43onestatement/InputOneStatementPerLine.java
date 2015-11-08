package com.google.checkstyle.test.chapter4formatting.rule43onestatement;

/**
 * Two import statements on the same line are illegal.
 */
import java.io.EOFException; import java.io.BufferedReader; //warn

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
    System.out.println("one = 1; two = 2");
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
    one = 1; two = 2; //warn
    if (one == 1) {
        one++; two++; //warn
    }
    if (one != 1) { one++; } else { one--; } //warn
    int n = 10;

    doLegal(); doLegal(); //warn
    while (one == 1) {one++; two--;} //warn

  }

  /**
   * While theoretically being distributed over two lines, this is a sample
   * of 2 statements on one line.
   */
  public void doIllegal2() {
    one = 1
    ; two = 2; //warn
  }

  class Inner
  {
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
        one = 1; two = 2; //warn
        if (one == 1) {
            one++; two++; //warn
        }
        if (one != 1) { one++; } else { one--; } //warn
        int n = 10;

        doLegal(); doLegal(); //warn
        while (one == 1) {one++; two--;} //warn

      }
  }

  /**
   * Two declaration statements on the same line are illegal.
   */
  int a; int b; //warn

  /**
   * Two declaration statements which are not on the same line
   * are legal.
   */
  int c;
  int d;

  /**
   * Two assignment (declaration) statements on the same line are illegal.
   */
  int e = 1; int f = 2; //warn

  /**
   * Two assignment (declaration) statements on the different lines
   * are legal.
   */
  int g = 1;
  int h = 2;

  /**
   * This method contains
   * two object creation statements on the same line.
   */
  private void foo() {
    //Two object creation statements on the same line are illegal.
    Object obj1 = new Object(); Object obj2 = new Object(); //warn
  }

  /**
   * One multiline  assignment (declaration) statement
   * is legal.
   */
  int i = 1, j = 2,
      k = 5;

  /**
   * One multiline  assignment (declaration) statement
   * is legal.
   */
  int l = 1,
      m = 2,
      n = 5;

  /**
   * One multiline  assignment (declaration) statement
   * is legal.
   */
  int w = 1,
      x = 2,
      y = 5
          ;

  /**
   * Two multiline  assignment (declaration) statements
   * are illegal.
   */
  int o = 1, p = 2,
      r = 5; int t; //warn

  /**
   * Two assignment (declaration) statement
   * which are not on the same lines and are legal.
   */
  int four = 1,
      five = 5
          ;
  int seven = 2;

  /**
   * Two statements on the same line
   * (they both are distributed over two lines)
   * are illegal.
   */
  int var1 = 5,
      var4 = 5; int var2 = 6,
      var3 = 5; //warn

  /**
   * Two statements on the same line
   * (they both are distributed over two lines)
   * are illegal.
   */
  int var6 = 5; int var7 = 6,
      var8 = 5; //warn

  /**
   * Two statements on the same line
   * (they both are distributed over two lines)
   * are illegal.
   */
  private void foo2() {
    toString(

    ); toString (

    ); //warn
  }


  /**
   * While theoretically being distributed over two lines, this is a sample
   * of 2 statements on one line.
   */
  int var9 = 1, var10 = 5
      ; int var11 = 2; //warn


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
   */
  int var12,
      var13 = 12;
  int var14 = 5,
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

    /**
     * One statement inside for block is legal
     */
    for (int i = 0; i < 10; i++) one = 5; //legal

    /**
     * One statement inside for block where
     * increment expression is empty is legal
     */
    for (int i = 0; i < 10;) one = 5; //legal

    /**
     * One statement inside for block where
     * increment and conditional expressions are empty
     * (forever loop) is legal
     */
    for (int i = 0;;) one = 5; //legal
  }

  public void foo5() {
    /**
     * a "forever" loop.
     */
    for(;;){} //legal
  }

  public void foo6() {
  /**
   * One statement inside for block is legal
   */
    for (;;) { one = 5; } //legal
  }

  /**
   * One statement inside multiline for loop is legal.
   */
  private void foo7() {
    for(int n = 0,
            k = 1
            ; n<5
            ;
            n++, k--) { var1++; } //legal
    }

  /**
   * Two statements on the same lne
   * inside multiline for loop are illegal.
   */
  private void foo8() {
    for(int n = 0,
            k = 1
            ; n<5
            ;
            n++, k--) { var1++; var2++; } //warn
    }
}
