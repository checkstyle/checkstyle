/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavaNCSS">
      <property name="classMaximum" value="10"/>
    </module>
  </module>
</module>
*/
// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.metrics.javancss;

class Example3 {
  // violation above, 'NCSS for this class is 11 (max allowed is 10)'
  public void testMethod1() {
    System.out.println("m-1:Line 1");
    System.out.println("m-1:Line 2");
    System.out.println("m-1:Line 3");
    System.out.println("m-1:Line 4");
    System.out.println("m-1:Line 5");
  }

  public void testMethod2() {
    System.out.println("m-2:Line 1");
    System.out.println("m-2:Line 2");
    System.out.println("m-2:Line 3");
  }
}
// xdoc section -- end
