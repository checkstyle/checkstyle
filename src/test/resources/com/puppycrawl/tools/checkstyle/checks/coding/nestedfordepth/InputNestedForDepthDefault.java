/*
NestedForDepth
max = (default)1


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nestedfordepth;

public class InputNestedForDepthDefault {

  public void method() {
    for (int i = 1; i < 5; i++) {
      for (int j = i; j < 5; j++) {
      }
    }
  }

  public void method2() {
    for (int i = 1; i < 5; i++) {
    }
  }

  public void method3() {
    for (int i = 1; i < 5; i++) {
      for (int j = 4; j < 5; j++) {
        for (int k = 3; k < 5; k++) {
      // violation above 'Nested for depth is 2 (max allowed is 1)'
        }
      }
    }
  }
}
