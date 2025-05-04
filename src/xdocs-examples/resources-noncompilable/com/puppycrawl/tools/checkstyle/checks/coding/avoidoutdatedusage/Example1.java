/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidOutdatedUsage">
    </module>
  </module>
</module>
*/
//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.avoidoutdatedusage;

// xdoc section -- start
public class Example1 {
  public void unusedPublic() {
    Collectors.toList(); // violation, Avoid ''.collect(Collectors.toList()) -> .toList()''.
    // Stream.of(FOO).toList()); // ok, as successor
  }
}
// xdoc section -- end
