package com.google.checkstyle.test.chapter4formatting.rule43onestatement;

public class OneStatementPerLineInput {

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
    for (int i = 0, j = 0, k = 1; i < 20; i++) {  one = i; } //warn
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
       * Simple legal method
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
        for (int i = 0, j = 0, k = 1; i < 20; i++) {  one = i; } //warn
      }
  }
}