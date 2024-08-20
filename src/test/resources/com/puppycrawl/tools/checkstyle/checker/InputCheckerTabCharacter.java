/*xml
<module name="Checker">
  <module name = "com.puppycrawl.tools.checkstyle.internal.testmodules.VerifyPositionAfterTabFileSet" />
  <property name = "tabWidth" value = "4" />
</module>
*/

package com.puppycrawl.tools.checkstyle.checker;

public class InputCheckerTabCharacter {
  public int foo(int numOne, int numTwo) {

    // ok, has no tabs
    int sum = numOne + numTwo;

    // has a tab
      numOne ++;      // violation

    // has 2 tabs
        numTwo ++;  // violation    // violation

    return sum;
  }
}
