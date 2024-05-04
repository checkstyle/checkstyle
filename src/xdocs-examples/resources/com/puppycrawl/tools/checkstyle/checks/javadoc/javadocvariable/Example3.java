/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocVariable">
      <property name="scope" value="private"/>
      <property name="excludeScope" value="protected"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

// xdoc section -- start
public class Example3 {
  private int a; // violation

  /**
   * Some description here
   */
  private int b;
  protected int c;
  public int d;
  /*package*/ int e; // violation
}
// xdoc section -- end
