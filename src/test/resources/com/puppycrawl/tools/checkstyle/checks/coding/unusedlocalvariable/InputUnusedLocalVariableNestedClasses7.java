/*
UnusedLocalVariable
allowUnnamedVariables = false
jdkVersion = (default)22

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableNestedClasses7 {
  void foo() {
    int a = 12; // violation 'Unused local variable'
    int ab = 12; // violation 'Unused local variable'

    class Ghi {
      void foo() {
        int a = 12;
        int ab = 12; // violation 'Unused local variable'
        System.out.println(a);
      }
    }

    class Def {
      void foo() {
        int a = 12; // violation 'Unused local variable'
        int ab = 12; // violation 'Unused local variable'

        class InnerDef {
          void foo() {
            int a = 12; // violation 'Unused local variable'
            int ab = 12;
            System.out.println(ab);
          }
        }
      }
    }
  }
}
