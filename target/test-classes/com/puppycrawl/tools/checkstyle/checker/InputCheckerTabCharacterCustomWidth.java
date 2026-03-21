/*xml
<module name="Checker">
  <property name="tabWidth" value="4" />
  <module
    name="com.puppycrawl.tools.checkstyle.internal.testmodules.VerifyPositionAfterLastTabFileSet" />
</module>
*/

package com.puppycrawl.tools.checkstyle.checker;

public class InputCheckerTabCharacterCustomWidth {
  public int foo(int numOne, int numTwo) {

    // ok, has no tabs
    int sum = numOne + numTwo;

    // has a tab after  ';'
    numOne ++;	// violation 'violation'

    // violation 2 lines below 'violation'
    // has 2 tabs after ';' and after 'comment'
    numTwo ++;		// comment		violation

    return sum;
  }
}
