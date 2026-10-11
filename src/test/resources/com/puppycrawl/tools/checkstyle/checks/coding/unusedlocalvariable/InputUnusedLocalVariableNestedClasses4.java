/*
UnusedLocalVariable
allowUnnamedVariables = false
jdkVersion = (default)22

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableNestedClasses4 {
  int a = 12;

  void foo() {
    int a = 12; // violation 'Unused local variable 'a'.'
    int ab = 12; // violation 'Unused local variable 'ab'.'

    class asd {
      Test a = new Test() {
        void asd() {
          System.out.println(a);
        }
      };
    }
  }
}
