/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisSameParameters {

    private int index;

    int field1;

    public InputRequireThisSameParameters(int index,int field1){
         index = index; //violation '.* variable 'index' needs "this.".'
         field1 += field1; // violation '.* variable 'field1' needs "this.".'
         field1 += 2+field1; // violation '.* variable 'field1' needs "this.".'
         field1 += 12+field1; // violation '.* variable 'field1' needs "this.".'
         field1 += 12+field1; // violation '.* variable 'field1' needs "this.".'
         field1 += 12/index+field1; // violation '.* variable 'field1' needs "this.".'

         field1 += 12/index+field1; // violation '.* variable 'field1' needs "this.".'
         field1 += 12/index+field1+index; // violation '.* variable 'field1' needs "this.".'


    }

public class Test {
  private int a;
  private int b;
  private int c;

  public Test(int a) {
    // overlapping by constructor argument
    this.a = a;
    b = 0; // violation '.* variable 'b' needs "this.".'

  }

   public void foo(int c) {
    // overlapping by method argument
    c = c;     // violation '.* variable 'c' needs "this.".'
  }


}
}