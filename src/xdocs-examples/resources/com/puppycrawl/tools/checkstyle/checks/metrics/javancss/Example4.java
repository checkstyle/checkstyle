/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavaNCSS">
      <property name="fileMaximum" value="10"/>
    </module>
  </module>
</module>
*/
// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.metrics.javancss;
// violation above, 'NCSS for this file is 19 (max allowed is 10)'
class Example4 {

  public void testMethod1() {
    System.out.println("m-1:Line 1");
    System.out.println("m-1:Line 2");
    System.out.println("m-1:Line 3");
    System.out.println("m-1:Line 4");
    System.out.println("m-1:Line 5");
  }

  // ok, NCSS for this record is 5 (max allowed is 10)
  record ValidRecord(int a, int b) {
    public ValidRecord {
      System.out.println("Line 1");
      System.out.println("Line 2");
      System.out.println("Line 3");
    }
  }

  // ok, NCSS for this record is 6 (max allowed is 10)
  record InvalidRecord(int x, int y) {
    public InvalidRecord {
      System.out.println("Line 1");
      System.out.println("Line 2");
      System.out.println("Line 3");
      System.out.println("Line 4");
    }
  }
}
// xdoc section -- end
