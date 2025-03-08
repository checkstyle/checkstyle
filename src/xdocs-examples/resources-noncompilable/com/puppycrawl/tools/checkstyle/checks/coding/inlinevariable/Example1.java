/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnusedLocalMethod">
    </module>
  </module>
</module>
*/
//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.inlinevariable;

// xdoc section -- start
public class Example1 {
  private String used() {
    String inlineVariable1 = used2();
    String inlineVariable2 = used2();
    String inlineVariable3 = used2();
    String in = used2(); // violation, Inline1 variable 'in' for immediate return or throw.
    return in;
  }

  private String used2() {
    return "String";
  }
}
// xdoc section -- end
