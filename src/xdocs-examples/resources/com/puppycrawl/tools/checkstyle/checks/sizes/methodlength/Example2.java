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
  public Example2()  {          // constructor (line 1)
    int var1 = 2;           // line 2
    int var2 = 4;           // line 3
    int sum = var1 + var2;  // line 4
  } // line 5, OK, constructor is not mentioned in the tokens

  public void firstMethod() { // line 1
      // comment (line 2)
    System.out.println("line 3");
  } // line 4, OK, as it allows at most 4 lines

  public void secondMethod() { // line 1
    int index = 0;   // line 2
    if (index < 5) { // line 3
        index++;     // line 4
      }                // line 5
  } // line 6, violation, as it is over 4 lines

  public void thirdMethod() { // line 1

      // comment (line 3)
    System.out.println("line 4");
  } // line 5, violation, as it is over 4 lines
}
// xdoc section -- end
