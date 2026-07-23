/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnusedTryResourceShouldBeUnnamed">
    </module>
  </module>
</module>
*/
// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.coding.unusedtryresourceshouldbeunnamed;
// xdoc section -- start
public class Example1 {

  static AutoCloseable lock() {
    return () -> {};
  }

  void example() throws Exception {
    // violation below 'Unused try resource 'a' should be unnamed'
    try (var a = lock()) {
      System.out.println("locked");
    }

    // violation below 'Unused try resource 'res' should be unnamed'
    try (AutoCloseable res = lock()) {
      System.out.println("locked");
    }

    // ok, resource 'b' is used inside the block
    try (var b = lock()) {
      System.out.println(b);
    }

    // ok, already declared as unnamed variable
    try (var _ = lock()) {
      System.out.println("locked");
    }
  }
}
// xdoc section -- end
