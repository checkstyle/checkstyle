/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocVariable">
      <property name="tokens" value="VARIABLE_DEF"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

// xdoc section -- start
public class Example3 {
  private int a;     // violation 'Missing a Javadoc comment'

  /**
   * Some description here
   */
  private int b;
  protected int c;   // violation 'Missing a Javadoc comment'
  public int d;      // violation 'Missing a Javadoc comment'
  /*package*/ int e; // violation 'Missing a Javadoc comment'

  public enum PublicEnum {
    CONSTANT
  }

  private enum PrivateEnum {
    CONSTANT
  }
}
// xdoc section -- end
