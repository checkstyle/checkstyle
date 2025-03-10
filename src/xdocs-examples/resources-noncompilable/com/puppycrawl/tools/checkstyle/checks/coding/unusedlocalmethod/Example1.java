/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnusedLocalMethod">
    </module>
  </module>
</module>
*/
//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalmethod;

// xdoc section -- start
public class Example1 {
  public void unusedPublic() {
  }

  protected void unusedProtected() {
  }

  void unusedPackageProtected() {
  }

  private void unusedPrivate() { // violation, "Unused local method 'unusedPrivate'"
  }
}
// xdoc section -- end
