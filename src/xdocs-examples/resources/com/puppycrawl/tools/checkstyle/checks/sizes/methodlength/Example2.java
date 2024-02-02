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

  public Example2()  {
    int var1 = 2;
    int var2 = 4;
    int sum = var1 + var2;
  } // line 5, OK, constructor is not mentioned in the tokens

  // violation below, 'Method firstMethod length is 6 lines (max allowed is 4)'
  public void firstMethod() {
    int index = 0;
    if (index < 5) {
      index++;
    }
  }

  public void secondMethod() {
    // comment
    System.out.println("line 3");
  } // line 4, OK, as it allows at most 4 lines

  //violation below, 'Method thirdMethod length is 5 lines (max allowed is 4)'
  public void thirdMethod() {

    // comment
    System.out.println("line 4");
  }

}
// xdoc section -- end
