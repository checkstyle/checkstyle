/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocVariable">
      <property name="accessModifiers" value="public"/>
      <property name="considerEnclosingScope" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

// xdoc section - start
public class Example5 {
  private int a;
  private int log;

  /**
   * Some description here
   */
  private int b;
  protected int c;
  public int d; // violation 'Missing a Javadoc comment for 'd'.'
  /*package*/ int e;

  public enum PublicEnum {
    CONSTANT // violation 'Missing a Javadoc comment for 'CONSTANT'.'
  }

  private enum PrivateEnum {
    CONSTANT
  }

  private static class Hidden {
    public int f;
  }
}
// xdoc section - end
