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
public class Example4 {

  record Test(String str) {
    public boolean equals(Test r) {  // no violation
      return false;
    }

    public boolean equals(Object r) {
      return false;
    }
  }
}
// xdoc section -- end





