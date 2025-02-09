/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocVariable">
      <property name="accessModifiers" value="private,package"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

// xdoc section -- start
public class Example3 {
  private int a; // violation, 'Missing a Javadoc comment'

  /**
   * Some description here
   */
  private int b;
  protected int c;
  public int d;
  /*package*/ int e; // violation, 'Missing a Javadoc comment'
}
// xdoc section -- end
