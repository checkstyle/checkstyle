/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodCount">
      <property name="maxPrivate" value="1"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.sizes.methodcount;

// xdoc section -- start
class Example4 { // violation, 'Number of private methods is 2 (max allowed is 1)'

  public void outerMethod1(int i) {}
  public void outerMethod2() {}
  public void outerMethod3(String str) {}

  private void outerMethod4() {
    Runnable r = (new Runnable() {
      public void run() {} // NOT counted towards Example1
    });
  }

  private void outerMethod5(int i) {}
  void outerMethod6(int i, int j) {}

  public static class InnerExample{
    public void innerMethod1() {} // NOT counted towards Example1
    public void innerMethod2() {} // NOT counted towards Example1
  }
}
// xdoc section -- end
