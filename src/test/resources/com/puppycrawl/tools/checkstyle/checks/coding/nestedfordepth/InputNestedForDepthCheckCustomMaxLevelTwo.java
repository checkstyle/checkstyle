/*
NestedForDepth
max = 2


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nestedfordepth;

/**
 * This Class contains no logic, but serves as test-input for the unit tests for the
 * <code>NestedForDepthCheck</code>-checkstyle enhancement.
 * @author Alexander Jesse
 * @see com.puppycrawl.tools.checkstyle.checks.coding.NestedForDepthCheck
 */
public class InputNestedForDepthCheckCustomMaxLevelTwo {

  /**
   * Dummy method containing 5 layers of for-statements.
   */
  public void nestedForFiveLevel() {
    int i = 0;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    int i5 = 0;

    for (i1 = 0; i1 < 10; i1++) {
      for (i2 = 0; i2 < 10; i2++) {
        for (i3 = 0; i3 < 10; i3++) {
          for (i4 = 0; i4 < 10; i4++) { // violation 'Nested for depth'
            for (i5 = 0; i5 < 10; i5++) { // violation 'Nested for depth'
              i += 1;
            }
            for (int i5a = 0; i5a < 10; i5a++) { // violation 'Nested for depth'
                i += 1;
              }
          }
        }
      }
    }
  }
}
