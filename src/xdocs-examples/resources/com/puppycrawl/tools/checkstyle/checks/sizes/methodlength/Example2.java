/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodLength">
      <property name="tokens" value="METHOD_DEF"/>
      <property name="max" value="4"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.sizes.methodlength;

// xdoc section -- start
public class Example2 {

  // ok, CTOR_DEF is not in configured tokens
  public Example2()  {
    int var1 = 2;
    int var2 = 4;
    int sum = var1 + var2;
  }

  // ok, CTOR_DEF is not in configured tokens
  public Example2(int a)  {
    int var1 = 2;
    int sum = var1 + a;
  }

  // violation below, 'Method firstMethod length is 6 lines (max allowed is 4)'
  public void firstMethod() {
    int index = 0;
    if (index < 5) {
      index++;
    }
  }

  public void secondMethod() {
    // comments are counted by default
    System.out.println("line 3");
  }

  // violation below, 'Method thirdMethod length is 5 lines (max allowed is 4)'
  public void thirdMethod() {

    // empty line above is counted by default,just like this comment
    System.out.println("line 4");
  }

}
// xdoc section -- end
