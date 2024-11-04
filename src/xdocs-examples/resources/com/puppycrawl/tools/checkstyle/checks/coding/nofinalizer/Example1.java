/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoFinalizer"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.nofinalizer;

// xdoc section -- start
public class Example1 {
  // violation below, 'Avoid using finalizer method'
  protected void finalize() throws Throwable {
    try {
      System.out.println("overriding finalize()");
    } catch (Throwable t) {
      throw t;
    } finally {
      super.finalize();
    }
  }
}
// xdoc section -- end

