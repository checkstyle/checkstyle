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
  public Example3()  {          // constructor (line 1)
    int var1 = 2;           // line 2
    int var2 = 4;           // line 3
    int sum = var1 + var2;  // line 4
  } // line 5, OK, constructor is not mentioned in the tokens

  public void firstMethod() { // line 1
    // comment - not counted as line
    System.out.println("line 2");
  } // line 3, OK, as it allows at most 4 lines

  // violation below, 'Method length is 6 lines (max allowed is 4).'
  public void secondMethod() {
    int index = 0;   // line 2
    if (index < 5) { // line 3
      index++;     // line 4
    }                // line 5
  } // line 6, violation, as it is over 4 lines

  public void thirdMethod() { // line 1

    // comment - not counted as line
    System.out.println("line 2");
  } // line 3, OK, as it allows at most 4 lines
}
// xdoc section -- end
