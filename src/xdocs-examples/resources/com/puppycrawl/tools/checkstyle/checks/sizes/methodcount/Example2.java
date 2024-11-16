/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodCount">
      <property name="maxTotal" value="5"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.sizes.methodcount;

// xdoc section -- start
class Example2 { // violation, 'Total number of methods is 6 (max allowed is 5)'

  public void outerMethod1(int i){}
  public void outerMethod2(){}
  public void outerMethod3(String str){}

  private void outerMethod4(){
    Runnable r = (new Runnable() {
      public void run() {} // NOT counted towards ExampleClass
    });
  }

  private void outerMethod5(int i){}
  void outerMethod6(int i , int j){}

  public static class InnerExample{
    public void InnerMethod1(){} // NOT counted towards ExampleClass,
    public void InnerMethod2(){} // NOT counted towards ExampleClass,
  }
}
// xdoc section -- end
