/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ClassDataAbstractionCoupling">
      <property name="excludedPackages" value="com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling;

// xdoc section -- start
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.Example7;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.Example8;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.Example9;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.deeper.Example4;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.deeper.Example5;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.deeper.Example6;

class Example11 {
  // Will be ignored, located inside classdataabstractioncoupling.ignore package
  Example7 baz7 = new Example7();
  Example8 baz8 = new Example8();
  Example9 baz9 = new Example9();

  // Counted, located outside of classdataabstractioncoupling.ignore package
  Example1 example1 = new Example1();
  Example2 example2 = new Example2();
  Example3 example3 = new Example3();
  Example4 example4 = new Example4();
  Example5 baz5 = new Example5();
  Example6 baz6 = new Example6();
  Data data = new Data(); // Not ignored - Counted 7

  class Data {
    public Data() {
      Example11 foo = new Example11(); // Ignored, same file
    }
  }
}
// xdoc section -- end
