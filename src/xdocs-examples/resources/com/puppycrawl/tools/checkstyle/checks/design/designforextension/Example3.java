/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DesignForExtension">
      <property name="requiredJavadocPhrase" value="This implementation"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

// xdoc section -- start
public abstract class Example3 {
  private int bar;

  public int m1() {return 2;}  // violation

  public int m2() {return 8;}  // violation

  private void m3() {m4();}   . Private method.

  protected void m4() { }   . No implementation.

  public abstract void m5();   . Abstract method.

  /**
   * This implementation ...
   @return some int value.
   */
  public int m6() {return 1;}   . Have required javadoc.

  /**
   * Some comments ...
   */
  public int m7() {return 1;}  // violation

  /**
   * This
   * implementation ...
   */
  public int m8() {return 2;}  // violation

  @Override                    // violation
  public String toString() {
    return "";
  }
}
// xdoc section -- end
