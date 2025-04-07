/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocVariable"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

// xdoc section -- start
public class Example6 {
  public enum PublicEnum {
    CONSTANT // violation, 'Missing a Javadoc comment'
  }

  private enum PrivateEnum {
    CONSTANT
  }
}
// xdoc section -- end
