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

  // violation below ''m1' does not have javadoc that explains how to do that safely'
  public int m1() {return 2;}

  // violation below ''m2' does not have javadoc that explains how to do that safely'
  public int m2() {return 8;}

  private void m3() {m4();}  // ok, Private method.

  protected void m4() { }  // ok, No implementation.

  public abstract void m5();  // ok, Abstract method.

  /**
   * This implementation ...
   @return some int value.
   */
  public int m6() {return 1;}  // ok, Have required javadoc.

  /**
   * Some comments ...
   */ // violation below ''m7' does not have javadoc that explains
  public int m7() {return 1;}

  /**
   * This
   * implementation ...
   */ // violation below ''m8' does not have javadoc that explains
  public int m8() {return 2;}
  // violation below ''toString' does not have javadoc that explains
  @Override
  public String toString() {
    return "";
  }
}
// xdoc section -- end
