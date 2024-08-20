/*xml
<module name="Checker">
  <property name = "tabWidth" value = "4" />
  <module name = "com.puppycrawl.tools.checkstyle.internal.testmodules.VerifyPositionAfterTabFileSet" />
</module>
*/

package com.puppycrawl.tools.checkstyle.checker;

public class InputCheckerTabCharacter {
  public int foo(int numOne, int numTwo) {

    // ok, has no tabs
    int sum = numOne + numTwo;

    // has a tab after  ';'
    numOne ++;	// violation

    // violation 2 lines below
    // has 2 tabs after ';' and after 'comment'
    numTwo ++;		// comment		violation

    return sum;
  }
}
