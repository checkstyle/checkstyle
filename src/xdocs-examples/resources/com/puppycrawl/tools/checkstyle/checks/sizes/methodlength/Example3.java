/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodLength">
      <property name="tokens" value="METHOD_DEF"/>
      <property name="max" value="4"/>
      <property name="countEmpty" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.sizes.methodlength;

// xdoc section -- start
public class Example3 {

  //ok, CTOR_DEF is not configured tokens
  public Example3()  {
    int var1 = 2;
    int var2 = 4;
    int sum = var1 + var2;
  }

  public void firstMethod() {
    // countEmpty property is false,so this line doesn't count
    System.out.println("counted as line 2");
  }

  public void secondMethod() {

    //countEmpty property is false,so this line and the empty line above don't count
    System.out.println("counted as line 2");
  }

  // violation below, 'Method thirdMethod length is 6 lines (max allowed is 4)'
  public void thirdMethod() {
    int index = 0;
    if (index < 5) {
      index++;
    }
  }

}
// xdoc section -- end
