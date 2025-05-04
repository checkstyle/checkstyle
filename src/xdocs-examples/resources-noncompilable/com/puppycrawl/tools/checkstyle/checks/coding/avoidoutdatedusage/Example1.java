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
  public void avoidUsing() {
    // violation below, Avoid using ''.collect(Collectors.toList())''.
    Stream.of(FOO).collect(Collectors.toList());
    Stream.of(FOO).toList(); // ok, as modern
    List.of(FOO); // ok, as modern
    // violation below, Avoid using ''.collect(Collectors.toList())''.
    Collectors.toList();
  }
}
// xdoc section -- end
