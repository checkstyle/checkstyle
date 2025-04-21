/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CovariantEquals"/>
  </module>
</module>
*/
//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.covariantequals;

// xdoc section -- start
public class Example1 {
  public boolean equals(Example1 same) {  // violation 'Covariant Equals'
    return false;
  }

  record Test(String str) {
    public boolean equals(Test same) {  // violation 'Covariant Equals'
      return false;
    }
  }
}
// xdoc section -- end
