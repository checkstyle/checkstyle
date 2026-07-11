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

  // violation below 'Class 'Example3' looks like designed for extension'
  public int m1() {return 2;}

  // violation below 'Class 'Example3' looks like designed for extension'
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
   */
  // violation below 'Class 'Example3' looks like designed for extension'
  public int m7() {return 1;}

  /**
   * This
   * implementation ...
   */
  // violation below 'Class 'Example3' looks like designed for extension'
  public int m8() {return 2;}

  // violation below 'Class 'Example3' looks like designed for extension'
  @Override
  public String toString() {
    return "";
  }
}
// xdoc section -- end
