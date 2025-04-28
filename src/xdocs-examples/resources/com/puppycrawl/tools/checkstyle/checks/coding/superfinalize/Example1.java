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
    super.finalize(); // ok, calls super.finalize()
  }
}
class InvalidExample {
  // violation below, 'Method 'finalize' should call 'super.finalize''
  protected void finalize() throws Throwable {
  }
}
// xdoc section -- end
