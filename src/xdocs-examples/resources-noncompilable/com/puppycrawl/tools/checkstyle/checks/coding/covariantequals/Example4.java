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
public class Example4Wrapper {

  record Example4(String str) {
    public boolean equals(Example4 r) {  // no violation
      return false;
    }

    public boolean equals(Object r) {
      return false;
    }
  }
}
// xdoc section -- end





