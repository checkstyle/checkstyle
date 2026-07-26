/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ClassDataAbstractionCoupling"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling;

import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.Example4;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.deeper.Example3;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.deeper.UseCase2;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.deeper.UseCase3;

import java.math.BigInteger;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

// xdoc section -- start
// violation below "Class Data Abstraction Coupling is 8 (max allowed is 7)."
public class UseCase1 {
  Set set = new HashSet(); // Ignored by default
  Map map = new HashMap(); // Ignored by default

  AtomicInteger atomicInteger = new AtomicInteger(); // Counted 1
  BigInteger bigInteger = new BigInteger("0");
  Example1 example1 = new Example1();
  Example2 example2 = new Example2();
  UseCase2 useCase2 = new UseCase2();
  Example3 example3 = new Example3();
  UseCase3 useCase3 = new UseCase3();
  Example4 example4 = new Example4(); // Counted 8
}
// xdoc section -- end
