/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavaNCSS">
      <property name="recordMaximum" value="5"/>
    </module>
  </module>
</module>
*/
// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.metrics.javancss;

public class Example5 {
  // This record has NCSS = 5, which is OK
  record ValidRecord(int a, int b) {
    public ValidRecord {
      System.out.println("Line 1");
      System.out.println("Line 2");
      System.out.println("Line 3");
    }
  }

  // violation below, 'NCSS for this record is 6 (max allowed is 5)'
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

