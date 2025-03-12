/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuperFinalize"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.superfinalize;

// xdoc section -- start
class Example1 {
  protected void finalize() throws Throwable {
    super.finalize(); // OK, calls super.finalize()
  }
}
class InvalidExample {
  // violation below, 'Overriding finalize() method must invoke super.finalize() to ensure proper finalization.'
  protected void finalize() throws Throwable {
  }
}
// xdoc section -- end
