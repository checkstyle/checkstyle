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

// violation below, 'NCSS for this record is 6 (max allowed is 5)'
record Example5(int x, int y) { // Statement 1

  public Example5 { // Statement 2
    System.out.println("Line 1"); // Statement 3
    System.out.println("Line 2"); // Statement 4
    System.out.println("Line 3"); // Statement 5
    System.out.println("Line 4"); // Statement 6
  }
}
// xdoc section -- end

