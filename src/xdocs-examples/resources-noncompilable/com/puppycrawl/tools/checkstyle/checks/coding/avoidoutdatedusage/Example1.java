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
    java.util.stream.Stream.of("Foo").collect(java.util.stream.Collectors.toList());
    java.util.stream.Stream.of("Foo").toList(); // ok, as modern
    java.util.List.of("Foo"); // ok, as modern
    // violation below, Avoid using ''.collect(Collectors.toList())''.
    java.util.stream.Collectors.toList();
  }
}
// xdoc section -- end
