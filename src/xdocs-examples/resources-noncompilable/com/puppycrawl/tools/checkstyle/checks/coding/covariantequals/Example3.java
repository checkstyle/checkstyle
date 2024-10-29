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
public class Example3Wrapper {

  record Example3(String str) {
    public boolean equals(Example3 r) {  // violation
      return false;
    }
  }
}
// xdoc section -- end


