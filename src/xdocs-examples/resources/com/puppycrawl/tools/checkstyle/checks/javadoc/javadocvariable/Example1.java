/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocVariable"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

// xdoc section - start
public class Example1 {
  private int a; // violation 'Missing a Javadoc comment for 'a'.'

  /**
   * Some description here
   */
  private int b;
  protected int c; // violation 'Missing a Javadoc comment for 'c'.'
  public int d; // violation 'Missing a Javadoc comment for 'd'.'
  /*package*/ int e; // violation 'Missing a Javadoc comment for 'e'.'

  public enum PublicEnum {
    CONSTANT // violation 'Missing a Javadoc comment for 'CONSTANT'.'
  }

  private enum PrivateEnum {
    CONSTANT // violation 'Missing a Javadoc comment for 'CONSTANT'.'
  }
}
// xdoc section - end
