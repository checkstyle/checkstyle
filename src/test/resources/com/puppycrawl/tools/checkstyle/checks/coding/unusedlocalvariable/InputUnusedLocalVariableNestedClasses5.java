/*
UnusedLocalVariable
allowUnnamedVariables = false
jdkVersion = (default)22

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableNestedClasses5 {
  int a = 12;

  void foo() {
    int a = 12; // violation 'Unused local variable'
    int ab = 12; // violation 'Unused local variable'

    class abc {
      Test a = new Test() {
        void abc() {
          System.out.println(a);
          int abc = 10; // violation 'Unused local variable'

          class def {
            Test abc = new Test() {
              void def() {
                System.out.println(abc);
              }
            };
          }
        }
      };
    }
  }
}
