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
  record ValidRecord(int a, int b) { // Statement 1
    public ValidRecord { // Statement 2
      System.out.println("Line 1"); // Statement 3
      System.out.println("Line 2"); // Statement 4
      System.out.println("Line 3"); // Statement 5
    }
  }

  // violation below, 'NCSS for this record is 6 (max allowed is 5)'
  record InvalidRecord(int x, int y) { // Statement 1
    public InvalidRecord { // Statement 2
      System.out.println("Line 1"); // Statement 3
      System.out.println("Line 2"); // Statement 4
      System.out.println("Line 3"); // Statement 5
      System.out.println("Line 4"); // Statement 6
    }
  }
}
// xdoc section -- end

