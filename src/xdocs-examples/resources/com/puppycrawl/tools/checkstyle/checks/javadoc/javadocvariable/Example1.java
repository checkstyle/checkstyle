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
  private int a; // violation, 'Missing a Javadoc comment'

  /**
   * Some description here
   */
  private int b;
  protected int c; // violation, 'Missing a Javadoc comment'
  public int d; // violation, 'Missing a Javadoc comment'
  /*package*/ int e; // violation, 'Missing a Javadoc comment'

}
// xdoc section -- end
