/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeName"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.typename;

// xdoc section -- start
class Example1 {
  public interface FirstName {}
  protected class SecondName {}
  enum Third_Name {} // violation 'Name 'Third_Name' must match pattern'
  private class FourthName_ {} // violation 'Name 'FourthName_' must match pattern'
}
// xdoc section -- end
