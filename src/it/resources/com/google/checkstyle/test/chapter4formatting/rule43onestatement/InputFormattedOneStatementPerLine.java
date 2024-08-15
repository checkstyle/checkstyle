package com.google.checkstyle.test.chapter4formatting.rule43onestatement;

// Two import statements on the same line are illegal.

/** Some javadoc. */
public class InputFormattedOneStatementPerLine {

  /** Dummy variable to work on. */
  private int one = 0;

  /** Dummy variable to work on. */
  private int two = 0;

  /** Simple legal method. */
  public void doLegal() {
    one = 1;
    two = 2;
  }

  /** The illegal format is used within a String. Therefor the whole method is legal. */
  public void doLegalString() {
    one = 1;
    two = 2;
    System.identityHashCode("one = 1; two = 2");
  }

  /** Within the for-header there are 3 Statements, but this is legal. */
  public void doLegalForLoop() {
    for (int i = 0, j = 0, k = 1; i < 20; i++) { // it's ok.
      one = i;
    }
  }

  /** Simplest form of illegal layouts. */
  public void doIllegal() {
    one = 1;
    two = 2;
    if (one == 1) {
      one++;
      two++;
    }
    if (one != 1) {
      one++;
    } else {
      one--;
    }
    int n = 10;

    doLegal();
    doLegal();
    while (one == 1) {
      one++;
      two--;
    }
  }

  /**
   * While theoretically being distributed over two lines, this is a sample of 2 statements on one
   * line.
   */
  public void doIllegal2() {
    one = 1;
    two = 2;
  }

  class Inner {
    /** Dummy variable to work on. */
    private int one = 0;

    /** Dummy variable to work on. */
    private int two = 0;

    /** Simple legal method. */
    public void doLegal() {
      one = 1;
      two = 2;
    }

    /** Simplest form a an illegal layout. */
    public void doIllegal() {
      one = 1;
      two = 2;
      if (one == 1) {
        one++;
        two++;
      }
      if (one != 1) {
        one++;
      } else {
        one--;
      }
      int n = 10;

      doLegal();
      doLegal();
      while (one == 1) {
        one++;
        two--;
      }
    }
  }

  /** Two declaration statements on the same line are illegal. */
  int aaaa;

  int bbb;

  /** Two declaration statements which are not on the same line are legal. */
  int ccc;

  int dddd;

  /** Two assignment (declaration) statements on the same line are illegal. */
  int dog = 1;

  int cow = 2;

  /** Two assignment (declaration) statements on the different lines are legal. */
  int test1 = 1;

  int test2 = 2;

  /** This method contains two object creation statements on the same line. */
  private void foo() {
    // Two object creation statements on the same line are illegal.
    Object obj1 = new Object();
    Object obj2 = new Object();
  }

  int blah;

  int seven = 2;

  /** Two statements on the same line (they both are distributed over two lines) are illegal. */
  int var6 = 5;

  /** Two statements on the same line (they both are distributed over two lines) are illegal. */
  private void foo2() {
    toString();
    toString();
  }

  int var11 = 2;

  /** Multiline for loop statement is legal. */
  private void foo3() {
    for (int n = 0, k = 1; n < 5; n++, k--) {}
  }

  /** This method contains break and while loop statements. */
  private void foo4() {
    int var9 = 0;
    int var10 = 0;

    do {
      var9++;
      if (var10 > 4) {
        break; // legal
      }
      var11++;
      var9++;
    } while (var11 < 7); // legal

    /*
     * One statement inside for block is legal
     */
    for (int i = 0; i < 10; i++) {
      one = 5;
    } // legal

    /*
     * One statement inside for block where
     * increment expression is empty is legal
     */
    for (int i = 0; i < 10; ) {
      one = 5;
    } // legal

    /*
     One statement inside for block where
     increment and conditional expressions are empty
     (forever loop) is legal
    */
    for (int i = 0; ; ) {
      one = 5;
    } // legal
  }

  /** Some javadoc. */
  public void foo5() {
    // a "forever" loop.
    for (; ; ) {} // legal
  }

  /** Some javadoc. */
  public void foo6() {
    // One statement inside for block is legal
    for (; ; ) {
      one = 5;
    } // legal
  }

  int var1 = 0;
  int var2 = 0;

  /** One statement inside multiline for loop is legal. */
  private void foo7() {
    for (int n = 0, k = 1; n < 5; n++, k--) {
      var1++;
    } // legal
  }

  /** Two statements on the same lne inside multiline for loop are illegal. */
  private void foo8() {
    for (int n = 0, k = 1; n < 5; n++, k--) {
      var1++;
      var2++;
    }
  }
}
