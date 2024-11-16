/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodCount">
      <property name="maxPublic" value="2"/>
      <property name="maxTotal" value="6"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.sizes.methodcount;

// xdoc section -- start
class Example3 { // violation, 'Number of public methods is 3 (max allowed is 2)'

  public void outerMethod1(int i) {}
  public void outerMethod2() {}
  public void outerMethod3(String str) {}

  private void outerMethod4(){
    Runnable r = (new Runnable() {
      public void run() {} // NOT counted towards Example3
    });
  }

  private void outerMethod5(int i) {}
  void outerMethod6(int i , int j) {}

  public static class InnerExample{
    public void innerMethod1() {} // NOT counted towards Example3
    public void innerMethod2() {} // NOT counted towards Example3
  }
}
// xdoc section -- end
