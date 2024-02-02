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

  public Example3()  {
    int var1 = 2;
    int var2 = 4;
    int sum = var1 + var2;
  } // line 5, OK, constructor is not mentioned in the tokens

  public void firstMethod() {
    // comment - not counted as line
    System.out.println("line 2");
  } // line 3, OK, as it allows at most 4 lines

  public void secondMethod() {

    // comment - not counted as line
    System.out.println("line 2");
  } // line 3, OK, as it allows at most 4 lines

  // violation below, 'Method thirdMethod length is 6 lines (max allowed is 4)'
  public void thirdMethod() {
    int index = 0;
    if (index < 5) {
      index++;
    }
  }

}
// xdoc section -- end
