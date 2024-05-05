/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocVariable"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

// xdoc section -- start
public class Example1 {
  private int a; // violation

  /**
   * Some description here
   */
  private int b;
  protected int c; // violation
  public int d; // violation
  /*package*/ int e; // violation

}
// xdoc section -- end
