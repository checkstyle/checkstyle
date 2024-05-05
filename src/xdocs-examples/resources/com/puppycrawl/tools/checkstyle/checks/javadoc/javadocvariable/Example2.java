/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocVariable">
      <property name="scope" value="public"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

// xdoc section -- start
public class Example2 {
  private int a;

  /**
   * Some description here
   */
  private int b;
  protected int c;
  public int d; // violation
  /*package*/ int e;
}
// xdoc section -- end
