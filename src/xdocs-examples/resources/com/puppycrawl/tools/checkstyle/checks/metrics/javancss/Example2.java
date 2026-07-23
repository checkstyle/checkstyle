/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavaNCSS">
      <property name="methodMaximum" value="4"/>
    </module>
  </module>
</module>
*/
// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.metrics.javancss;

class Example2 {
  // violation below 'NCSS for this method is 6 (max allowed is 4)'
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

  // violation 2 lines below 'NCSS for this method is 5 (max allowed is 4)'
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
