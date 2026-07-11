/*xml
<module name="Checker">
  <module
    name="com.puppycrawl.tools.checkstyle.internal.testmodules.VerifyPositionAfterLastTabFileSet" />
</module>
*/

package com.puppycrawl.tools.checkstyle.checker;

public class InputCheckerTabCharacter {
  public int foo(int numOne, int numTwo) {

    // ok, has no tabs
    int sum = numOne + numTwo;

    // has a tab after  ';'
    // violation below 'violation'
    numOne ++;	// has tab

    // has 2 tabs after ';' and after 'comment'
    // violation below 'violation'
    numTwo ++;		// comment		last tab

    return sum;
  }
}
