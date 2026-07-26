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
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.Example4;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.Example5;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.UseCase4;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.deeper.UseCase2;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.deeper.Example3;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.deeper.UseCase3;

class UseCase6 {
  // Ignored, located inside classdataabstractioncoupling.ignore package
  Example4 example4 = new Example4();
  UseCase4 useCase4 = new UseCase4();
  Example5 example5 = new Example5();

  // Counted, located outside of classdataabstractioncoupling.ignore package
  Example1 example1 = new Example1();
  UseCase1 useCase1 = new UseCase1();
  Example2 example2 = new Example2();
  UseCase2 useCase2 = new UseCase2();
  Example3 example3 = new Example3();
  UseCase3 useCase3 = new UseCase3();
  Data data = new Data(); // Counted 7

  class Data {
    public Data() {
      UseCase6 useCase6 = new UseCase6(); // Ignored same file
    }
  }
}
// xdoc section -- end
